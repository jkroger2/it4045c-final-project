/* Simple SPA Router + Mocked API (local JSON) */
const state = {
  search: {
    destination: "",
    checkIn: "",
    checkOut: "",
    guests: 1,
    rigLength: 25,
    rigType: "travel-trailer",
    power: null,
  },
  selection: null, // the currently selected site
  booking: {
    contact: { firstName: "", lastName: "", email: "", phone: "" },
    rig: { type: "travel-trailer", length: 25, hasTowVehicle: true },
    requests: "",
    acceptPolicy: false,
    promo: "",
  },
  payment: {
    cardNumber: "",
    exp: "",
    cvc: "",
    idempotencyKey: null, // used to prevent double charges
  },
  confirmation: null, // { bookingId, total, email }
};

/* Utilities */
const $$ = (sel, ctx = document) => ctx.querySelector(sel);
const $all = (sel, ctx = document) => Array.from(ctx.querySelectorAll(sel));
const live = (msg) => { const el = document.getElementById("sr-live"); if (el) el.textContent = msg; };
const formatMoney = (n) => `$${n.toFixed(2)}`;
const nightsBetween = (a, b) => {
  const A = new Date(a); const B = new Date(b);
  return Math.max(0, Math.round((B - A) / (1000*60*60*24)));
};
const validateDateRange = (checkIn, checkOut) => {
  if (!checkIn || !checkOut) return "Please select both check-in and check-out.";
  if (new Date(checkOut) <= new Date(checkIn)) return "Check-out must be after check-in.";
  return null;
};

/* Simple mock API */
async function mockFetchSites() {
  const res = await fetch("./mock-data.json");
  return res.json(); // returns an array of sites
}

function filterSites(sites, params) {
  return sites.filter(s => {
    const fitsLength = params.rigLength <= s.maxLength;
    const hasPower = params.power ? s.power.includes(params.power) : true;
    const available = (s.blockedDates || []).every(range => {
      const from = new Date(range[0]), to = new Date(range[1]);
      // overlap if checkIn < to && checkOut > from
      const overlap = new Date(params.checkIn) < to && new Date(params.checkOut) > from;
      return !overlap;
    });
    return fitsLength && hasPower && available;
  });
}

/* Pricing + promos */
function calcPrice(site, checkIn, checkOut, promo) {
  const nights = nightsBetween(checkIn, checkOut);
  const subtotal = site.pricePerNight * nights;
  const tax = subtotal * 0.10;
  const serviceFee = 6;
  let discount = 0;
  if (promo) {
    if (promo.toUpperCase() === "FALL10") discount = subtotal * 0.10;
    else throw new Error("Code expired or invalid");
  }
  const total = subtotal - discount + tax + serviceFee;
  return { nights, subtotal, tax, serviceFee, discount, total };
}

/* Refund logic */
function computeRefund(site, checkIn, now) {
  const hoursBefore = (new Date(checkIn) - now) / (1000*60*60);
  const freeWindow = site.cancelFreeHours ?? 72;
  if (hoursBefore >= freeWindow) return { policy: "Full refund", refundType: "full" };
  // penalty = first night
  return { policy: "Penalty: first night", refundType: "minusFirstNight" };
}

/* Router */
const routes = {
  "/search": renderSearch,
  "/results": renderResults,
  "/details": renderDetails,
  "/booking": renderBooking,
  "/payment": renderPayment,
  "/confirm": renderConfirm,
  "/manage": renderManage,
};
function navigate(path){ location.hash = path; }
window.addEventListener("hashchange", () => mount());
window.addEventListener("DOMContentLoaded", () => { if(!location.hash) navigate("/search"); mount(); });

function mount(){
  const path = (location.hash || "#/search").replace("#","");
  const view = routes[path] || renderSearch;
  const app = document.getElementById("app");
  app.innerHTML = "";
  app.appendChild(view());
  app.focus();
}

/* Views */
function renderSearch(){
  const c = document.createElement("section");
  c.innerHTML = `
    <div class="card">
      <h2>Search availability</h2>
      <form id="searchForm" class="grid cols-3" novalidate>
        <div>
          <label for="destination">Destination or Park</label>
          <input id="destination" name="destination" placeholder="e.g., Gatlinburg, TN" required />
        </div>
        <div>
          <label for="checkIn">Check-in</label>
          <input type="date" id="checkIn" name="checkIn" required />
        </div>
        <div>
          <label for="checkOut">Check-out</label>
          <input type="date" id="checkOut" name="checkOut" required />
        </div>
        <div>
          <label for="guests">Guests</label>
          <input type="number" id="guests" min="1" value="1" />
        </div>
        <div>
          <label for="rigLength">Rig length (ft)</label>
          <input type="number" id="rigLength" min="5" max="60" value="25" />
        </div>
        <div>
          <label for="rigType">Rig type</label>
          <select id="rigType">
            <option value="travel-trailer">Travel trailer</option>
            <option value="fifth-wheel">Fifth wheel</option>
            <option value="motorhome">Motorhome</option>
            <option value="van">Van</option>
          </select>
        </div>
        <div>
          <label for="power">Power</label>
          <select id="power">
            <option value="">Any</option>
            <option value="30A">30A</option>
            <option value="50A">50A</option>
          </select>
        </div>
        <div class="right flex">
          <button type="submit" class="right">Search availability</button>
        </div>
      </form>
      <div id="searchError" class="error" aria-live="assertive" style="display:none"></div>
    </div>
  `;
  // prefill from state
  c.querySelector("#destination").value = state.search.destination || "";
  c.querySelector("#checkIn").value = state.search.checkIn || "";
  c.querySelector("#checkOut").value = state.search.checkOut || "";
  c.querySelector("#guests").value = state.search.guests || 1;
  c.querySelector("#rigLength").value = state.search.rigLength || 25;
  c.querySelector("#rigType").value = state.search.rigType || "travel-trailer";
  c.querySelector("#power").value = state.search.power || "";

  c.querySelector("#searchForm").addEventListener("submit", async (e)=>{
    e.preventDefault();
    const destination = c.querySelector("#destination").value.trim();
    const checkIn = c.querySelector("#checkIn").value;
    const checkOut = c.querySelector("#checkOut").value;
    const guests = Number(c.querySelector("#guests").value);
    const rigLength = Number(c.querySelector("#rigLength").value);
    const rigType = c.querySelector("#rigType").value;
    const power = c.querySelector("#power").value || null;

    const err = validateDateRange(checkIn, checkOut);
    const errEl = c.querySelector("#searchError");
    if (err) {
      errEl.textContent = err; errEl.style.display = "block"; live(err); return;
    }
    errEl.style.display = "none";

    state.search = { destination, checkIn, checkOut, guests, rigLength, rigType, power };

    const sites = await mockFetchSites();
    const results = filterSites(sites, state.search);
    sessionStorage.setItem("searchResults", JSON.stringify(results));
    navigate("/results");
  });

  return c;
}

function renderResults(){
  const c = document.createElement("section");
  const results = JSON.parse(sessionStorage.getItem("searchResults") || "[]");
  const n = nightsBetween(state.search.checkIn, state.search.checkOut);

  const noAvail = results.length === 0;
  c.innerHTML = `
    <div class="card">
      <h2>Available sites (${results.length})</h2>
      ${noAvail ? `
        <div class="error">
          No availability. Try adjusting dates, widening the radius, or clearing filters.
        </div>` : `
        <div class="flex">
          <label class="small">Sort
            <select id="sort">
              <option value="price">Price</option>
              <option value="length">Max Length</option>
            </select>
          </label>
          <div class="badge">Nights: ${n}</div>
          <div class="badge">Rig: ${state.search.rigLength} ft</div>
          ${state.search.power ? `<div class="badge">${state.search.power}</div>` : ""}
          <span class="right small">Destination: ${state.search.destination || "Any"}</span>
        </div>
      `}
    </div>
    <div id="cards"></div>
    <div class="flex">
      <button class="ghost" onclick="navigate('/search')">Back</button>
    </div>
  `;

  const list = c.querySelector("#cards");
  const renderCards = (arr)=>{
    list.innerHTML = "";
    arr.forEach(site=>{
      const card = document.createElement("div");
      card.className = "card";
      card.innerHTML = `
        <div class="flex">
          <h3>${site.name}</h3>
          <span class="right price">${formatMoney(site.pricePerNight)}/night</span>
        </div>
        <div class="flex small">
          <span class="badge">${site.type}</span>
          <span class="badge">Max ${site.maxLength} ft</span>
          <span class="badge">${site.power.join(", ")}</span>
          <span class="badge">${site.hookups.join(", ")}</span>
        </div>
        <p class="small rule">${site.policySummary}</p>
        <div class="flex">
          <button data-id="${site.id}">Select</button>
        </div>
      `;
      card.querySelector("button").addEventListener("click", ()=>{
        state.selection = site;
        navigate("/details");
      });
      list.appendChild(card);
    });
  };

  renderCards(results);

  const sortEl = c.querySelector("#sort");
  if (sortEl) {
    sortEl.addEventListener("change", ()=>{
      const sorted = [...results].sort((a,b)=>{
        if (sortEl.value === "price") return a.pricePerNight - b.pricePerNight;
        if (sortEl.value === "length") return b.maxLength - a.maxLength;
        return 0;
      });
      renderCards(sorted);
    });
  }
  return c;
}

function renderDetails(){
  const site = state.selection;
  if(!site){ navigate("/results"); return document.createElement("div"); }
  const c = document.createElement("section");
  const pricing = calcPrice(site, state.search.checkIn, state.search.checkOut, null);
  c.innerHTML = `
    <div class="card">
      <h2>${site.name}</h2>
      <div class="grid cols-2">
        <div>
          <div class="flex small">
            <span class="badge">${site.type}</span>
            <span class="badge">Max ${site.maxLength} ft</span>
            <span class="badge">${site.power.join(", ")}</span>
            <span class="badge">${site.hookups.join(", ")}</span>
          </div>
          <p>${site.description}</p>
          <p class="rule"><strong>Rules & Policy:</strong> ${site.policySummary}</p>
        </div>
        <div>
          <div class="card">
            <h3>Price breakdown</h3>
            <div class="kv">
              <div class="k">Subtotal</div><div class="v">${formatMoney(pricing.subtotal)}</div>
              <div class="k">Tax (10%)</div><div class="v">${formatMoney(pricing.tax)}</div>
              <div class="k">Service fee</div><div class="v">${formatMoney(pricing.serviceFee)}</div>
              <div class="k"><strong>Total</strong></div><div class="v"><strong>${formatMoney(pricing.total)}</strong></div>
            </div>
            <button id="toBooking">Continue to booking</button>
          </div>
        </div>
      </div>
    </div>
    <div class="flex">
      <button class="ghost" onclick="navigate('/results')">Back</button>
    </div>
  `;
  c.querySelector("#toBooking").addEventListener("click", ()=> navigate("/booking"));
  return c;
}

function renderBooking(){
  const c = document.createElement("section");
  c.innerHTML = `
    <div class="card">
      <h2>Guest & Rig Information</h2>
      <form id="bookingForm" class="grid cols-3" novalidate>
        <div>
          <label for="firstName">First name</label>
          <input id="firstName" required />
        </div>
        <div>
          <label for="lastName">Last name</label>
          <input id="lastName" required />
        </div>
        <div>
          <label for="email">Email</label>
          <input id="email" type="email" required />
        </div>
        <div>
          <label for="phone">Phone</label>
          <input id="phone" type="tel" placeholder="(555) 555-5555" required />
        </div>
        <div>
          <label for="rigType2">Rig type</label>
          <select id="rigType2">
            <option value="travel-trailer">Travel trailer</option>
            <option value="fifth-wheel">Fifth wheel</option>
            <option value="motorhome">Motorhome</option>
            <option value="van">Van</option>
          </select>
        </div>
        <div>
          <label for="rigLength2">Rig length (ft)</label>
          <input id="rigLength2" type="number" min="5" max="60" required />
        </div>
        <div class="grid cols-2">
          <label class="flex"><input id="acceptPolicy" type="checkbox" /> <span>I accept the cancellation policy.</span></label>
        </div>
        <div class="grid cols-2">
          <label for="requests">Special requests</label>
          <textarea id="requests" rows="3" placeholder="Optional"></textarea>
        </div>
        <div class="right flex">
          <button class="ghost" type="button" onclick="navigate('/details')">Back</button>
          <button type="submit">Continue to payment</button>
        </div>
      </form>
      <div id="bookErr" class="error" style="display:none"></div>
    </div>
  `;
  // Prefill
  c.querySelector("#rigType2").value = state.search.rigType;
  c.querySelector("#rigLength2").value = state.search.rigLength;

  c.querySelector("#bookingForm").addEventListener("submit",(e)=>{
    e.preventDefault();
    const phone = c.querySelector("#phone").value.trim();
    const accept = c.querySelector("#acceptPolicy").checked;
    if (!phone) {
      const errEl = c.querySelector("#bookErr");
      errEl.textContent = "Phone number is required.";
      errEl.style.display = "block";
      c.querySelector("#phone").focus();
      return;
    }
    if (!accept) {
      const errEl = c.querySelector("#bookErr");
      errEl.textContent = "You must accept the cancellation policy.";
      errEl.style.display = "block";
      c.querySelector("#acceptPolicy").focus();
      return;
    }
    // Save
    state.booking.contact.firstName = c.querySelector("#firstName").value.trim();
    state.booking.contact.lastName = c.querySelector("#lastName").value.trim();
    state.booking.contact.email = c.querySelector("#email").value.trim();
    state.booking.contact.phone = phone;
    state.booking.rig.type = c.querySelector("#rigType2").value;
    state.booking.rig.length = Number(c.querySelector("#rigLength2").value);
    state.booking.requests = c.querySelector("#requests").value;

    navigate("/payment");
  });
  return c;
}

function renderPayment(){
  const site = state.selection;
  const c = document.createElement("section");
  const pricing = calcPrice(site, state.search.checkIn, state.search.checkOut, null);
  c.innerHTML = `
    <div class="card">
      <h2>Payment & Review</h2>
      <div class="grid cols-2">
        <div>
          <div class="card">
            <h3>Order summary</h3>
            <div class="kv">
              <div class="k">Site</div><div class="v">${site.name}</div>
              <div class="k">Dates</div><div class="v">${state.search.checkIn} → ${state.search.checkOut}</div>
              <div class="k">Guests</div><div class="v">${state.search.guests}</div>
              <div class="k">Rig</div><div class="v">${state.booking.rig.length} ft · ${state.booking.rig.type}</div>
            </div>
            <hr/>
            <div id="totals">
              <div class="kv">
                <div class="k">Subtotal</div><div class="v">${formatMoney(pricing.subtotal)}</div>
                <div class="k">Tax (10%)</div><div class="v">${formatMoney(pricing.tax)}</div>
                <div class="k">Service fee</div><div class="v">${formatMoney(pricing.serviceFee)}</div>
                <div class="k">Promo</div><div class="v" id="promoLine">—</div>
                <div class="k"><strong>Total</strong></div><div class="v"><strong id="totalVal">${formatMoney(pricing.total)}</strong></div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <form id="payForm" novalidate>
            <label for="promo">Promo code</label>
            <div class="flex">
              <input id="promo" placeholder="e.g., FALL10" />
              <button type="button" class="secondary" id="applyPromo">Apply</button>
            </div>
            <div id="promoMsg" class="error" style="display:none"></div>
            <hr/>
            <label for="cardNumber">Card number</label>
            <input id="cardNumber" inputmode="numeric" placeholder="4242 4242 4242 4242" required />
            <label for="exp">Expiry (MM/YY)</label>
            <input id="exp" placeholder="12/29" required />
            <label for="cvc">CVC</label>
            <input id="cvc" placeholder="123" required />
            <div class="right flex">
              <button class="ghost" type="button" onclick="navigate('/booking')">Back</button>
              <button id="payBtn" type="submit">Pay & book</button>
            </div>
          </form>
          <div id="payErr" class="error" style="display:none"></div>
          <div id="payOk" class="success" style="display:none"></div>
        </div>
      </div>
    </div>
  `;

  let currentPricing = pricing;

  c.querySelector("#applyPromo").addEventListener("click", ()=>{
    const code = c.querySelector("#promo").value.trim();
    const promoMsg = c.querySelector("#promoMsg");
    try {
      currentPricing = calcPrice(site, state.search.checkIn, state.search.checkOut, code);
      c.querySelector("#promoLine").textContent = code ? `-${formatMoney(currentPricing.discount)}` : "—";
      c.querySelector("#totalVal").textContent = formatMoney(currentPricing.total);
      promoMsg.style.display = "none";
      live("Promo code applied");
    } catch (e) {
      promoMsg.textContent = "Code expired or invalid";
      promoMsg.style.display = "block";
      currentPricing = calcPrice(site, state.search.checkIn, state.search.checkOut, null);
      c.querySelector("#promoLine").textContent = "—";
      c.querySelector("#totalVal").textContent = formatMoney(currentPricing.total);
    }
  });

  c.querySelector("#payForm").addEventListener("submit",(e)=>{
    e.preventDefault();
    const errEl = c.querySelector("#payErr");
    const okEl = c.querySelector("#payOk");
    errEl.style.display = "none"; okEl.style.display = "none";

    // Idempotency: prevent duplicates by reusing a key; second click won't re-charge
    if (!state.payment.idempotencyKey) {
      state.payment.idempotencyKey = crypto.randomUUID();
    } else {
      if (state.confirmation) { navigate("/confirm"); return; }
    }

    const card = c.querySelector("#cardNumber").value.replace(/\s+/g,"");
    if (!card) { errEl.textContent = "Enter a card number"; errEl.style.display = "block"; return; }
    if (card.startsWith("0000")) {
      errEl.textContent = "Card declined (Do not honor). Try another card.";
      errEl.style.display = "block";
      return;
    }

    const bookingId = "BK-" + Math.random().toString(36).slice(2,8).toUpperCase();
    state.confirmation = {
      bookingId,
      total: currentPricing.total,
      email: state.booking.contact.email,
      siteId: state.selection.id,
      dates: { checkIn: state.search.checkIn, checkOut: state.search.checkOut },
    };

    okEl.textContent = "Payment successful. Booking confirmed.";
    okEl.style.display = "block";
    setTimeout(()=> navigate("/confirm"), 400);
  });

  return c;
}

function renderConfirm(){
  const c = document.createElement("section");
  const conf = state.confirmation;
  if (!conf) { navigate("/search"); return document.createElement("div"); }
  c.innerHTML = `
    <div class="card">
      <h2>Booking Confirmed</h2>
      <div class="kv">
        <div class="k">Booking ID</div><div class="v">${conf.bookingId}</div>
        <div class="k">Dates</div><div class="v">${conf.dates.checkIn} → ${conf.dates.checkOut}</div>
        <div class="k">Total Paid</div><div class="v">${formatMoney(conf.total)}</div>
        <div class="k">Email</div><div class="v">${conf.email}</div>
      </div>
      <div class="flex">
        <button id="dlIcs" class="secondary">Download itinerary (.ics)</button>
        <button class="right" onclick="navigate('/manage')">Manage booking</button>
      </div>
    </div>
  `;

  c.querySelector("#dlIcs").addEventListener("click", ()=>{
    const ics = [
      "BEGIN:VCALENDAR",
      "VERSION:2.0",
      "PRODID:-//IT4045C//RV Booking//EN",
      "BEGIN:VEVENT",
      `UID:${state.confirmation.bookingId}`,
      `DTSTAMP:${new Date().toISOString().replace(/[-:]/g,'').split('.')[0]}Z`,
      `DTSTART:${state.confirmation.dates.checkIn.replace(/-/g,'')}T150000Z`,
      `DTEND:${state.confirmation.dates.checkOut.replace(/-/g,'')}T150000Z`,
      `SUMMARY:RV Stay - ${state.selection.name}`,
      "END:VEVENT",
      "END:VCALENDAR"
    ].join("\r\n");
    const blob = new Blob([ics], {type:"text/calendar"});
    const a = document.createElement("a");
    a.href = URL.createObjectURL(blob);
    a.download = `${state.confirmation.bookingId}.ics`;
    document.body.appendChild(a); a.click(); a.remove();
  });

  return c;
}

function renderManage(){
  const c = document.createElement("section");
  c.innerHTML = `
    <div class="card">
      <h2>Manage Booking</h2>
      <form id="lookupForm" class="grid cols-3" novalidate>
        <div>
          <label for="lookupId">Booking ID</label>
          <input id="lookupId" placeholder="e.g., BK-ABC123" required />
        </div>
        <div>
          <label for="lookupEmail">Email</label>
          <input id="lookupEmail" type="email" placeholder="you@example.com" required />
        </div>
        <div class="right flex">
          <button type="submit">View booking</button>
        </div>
      </form>
      <div id="view"></div>
    </div>
  `;

  c.querySelector("#lookupForm").addEventListener("submit", (e)=>{
    e.preventDefault();
    const id = c.querySelector("#lookupId").value.trim();
    const email = c.querySelector("#lookupEmail").value.trim();
    const view = c.querySelector("#view");

    // In this mock, only the current session booking is viewable
    if (!state.confirmation || state.confirmation.bookingId !== id || state.confirmation.email !== email) {
      view.innerHTML = `<div class="error">Booking not found for that ID/email (demo limitation).</div>`;
      return;
    }

    const site = state.selection;
    view.innerHTML = `
      <div class="card">
        <h3>Your booking</h3>
        <div class="kv">
          <div class="k">Site</div><div class="v">${site.name}</div>
          <div class="k">Dates</div><div class="v">${state.confirmation.dates.checkIn} → ${state.confirmation.dates.checkOut}</div>
          <div class="k">Price Paid</div><div class="v">${formatMoney(state.confirmation.total)}</div>
          <div class="k">Policy</div><div class="v">${site.policySummary}</div>
        </div>
        <hr/>
        <div class="flex">
          <button id="cancelBtn" class="secondary">Cancel booking</button>
        </div>
        <div id="cancelResult"></div>
      </div>
    `;

    view.querySelector("#cancelBtn").addEventListener("click", ()=>{
      const now = new Date();
      const refund = computeRefund(site, state.confirmation.dates.checkIn, now);
      const cancelDiv = view.querySelector("#cancelResult");
      let refundAmount = state.confirmation.total;
      if (refund.refundType === "minusFirstNight") {
        refundAmount = state.confirmation.total - site.pricePerNight;
      }
      cancelDiv.innerHTML = `
        <div class="card">
          <p>${refund.policy}. Refund shown before confirm.</p>
          <p><strong>Refund:</strong> ${formatMoney(refundAmount)}</p>
          <button id="confirmCancel">Confirm cancel</button>
        </div>
      `;
      cancelDiv.querySelector("#confirmCancel").addEventListener("click", ()=>{
        const msg = document.createElement("div");
        msg.className = "success";
        msg.textContent = "Booking canceled. Inventory returned to available.";
        view.appendChild(msg);
      });
    });
  });

  return c;
}

/* Mount initial view */
