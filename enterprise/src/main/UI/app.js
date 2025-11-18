/* === Simple SPA Router + Mock JSON API === */

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
  selection: null,
  booking: {
    contact: { firstName: "", lastName: "", email: "", phone: "" },
    rig: { type: "travel-trailer", length: 25 },
    requests: "",
  },
  payment: {
    idempotencyKey: null
  },
  confirmation: null
};

/* === Utility helpers === */
const $$ = (s, c=document) => c.querySelector(s);
const formatMoney = n => `$${n.toFixed(2)}`;
const nightsBetween = (a,b)=>Math.max(0,(new Date(b)-new Date(a))/(1000*60*60*24));

function live(msg){ const el=$$("#sr-live"); if(el) el.textContent=msg; }

/* === Mock API === */
async function fetchSites() {
  const r = await fetch("./mock-data.json");
  return r.json();
}

function filterSites(sites, p){
  return sites.filter(s=>{
    const fits = p.rigLength <= s.maxLength;
    const power = p.power ? s.power.includes(p.power) : true;
    const available = (s.blocked || []).every(rng=>{
      const [a,b]=rng.map(d=>new Date(d));
      const S=new Date(p.checkIn), E=new Date(p.checkOut);
      return !(S<b && E>a);
    });
    return fits && power && available;
  });
}

/* === Price calc === */
function computePrice(site, checkIn, checkOut, promo){
  const n=nightsBetween(checkIn,checkOut);
  const subtotal=site.price*n;
  const tax=subtotal*0.10;
  const fee=6;
  let discount=0;

  if (promo){
    if(promo==="FALL10") discount=subtotal*0.10;
    else throw Error("Invalid promo");
  }

  return {
    nights:n,
    subtotal,
    tax,
    fee,
    discount,
    total: subtotal - discount + tax + fee
  };
}

/* === Refund === */
function refundCalc(site, checkIn){
  const hrs = (new Date(checkIn)-new Date())/(1000*60*60);
  if (hrs >= 72) return { type:"full" };
  return { type:"minusNight" };
}

/* === Router === */
const routes={
  "/search":renderSearch,
  "/results":renderResults,
  "/details":renderDetails,
  "/booking":renderBooking,
  "/payment":renderPayment,
  "/confirm":renderConfirm,
  "/manage":renderManage
};

function navigate(p){ location.hash=p; }
window.addEventListener("hashchange", mount);
window.addEventListener("DOMContentLoaded", ()=>{
  if(!location.hash) navigate("/search");
  mount();
});

function mount(){
  const path=location.hash.replace("#","");
  const fn=routes[path]||renderSearch;
  const app=document.getElementById("app");
  app.innerHTML="";
  app.appendChild(fn());
}

/* === Views === */

function renderSearch(){
  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>Search availability</h2>
      <form id="f" class="grid cols-3">
        <div><label>Destination<input id="d"/></label></div>
        <div><label>Check-in<input type="date" id="ci"/></label></div>
        <div><label>Check-out<input type="date" id="co"/></label></div>
        <div><label>Guests<input type="number" id="g" value="1"/></label></div>
        <div><label>Rig length<input type="number" id="rl" value="25"/></label></div>
        <div><label>Power<select id="pw">
          <option value="">Any</option>
          <option>30A</option>
          <option>50A</option>
        </select></label></div>
        <div class="right"><button>Search</button></div>
      </form>
      <div id="err" class="error" style="display:none"></div>
    </div>
  `;

  $$("#f",c).addEventListener("submit",async e=>{
    e.preventDefault();
    const d=$$("#d",c).value.trim();
    const ci=$$("#ci",c).value;
    const co=$$("#co",c).value;
    const g=$$("#g",c).value;
    const rl=$$("#rl",c).value;
    const pw=$$("#pw",c).value||null;

    if(!ci||!co||new Date(co)<=new Date(ci)){
      const err=$$("#err",c);
      err.textContent="Check-out must be after check-in.";
      err.style.display="block";
      return;
    }

    state.search={destination:d,checkIn:ci,checkOut:co,guests:g,rigLength:+rl,rigType:"travel-trailer",power:pw};

    const sites=await fetchSites();
    const res=filterSites(sites,state.search);
    sessionStorage.setItem("results",JSON.stringify(res));
    navigate("/results");
  });

  return c;
}

function renderResults(){
  const c=document.createElement("section");
  const res=JSON.parse(sessionStorage.getItem("results")||"[]");
  const n=nightsBetween(state.search.checkIn,state.search.checkOut);

  c.innerHTML=`
    <div class="card">
      <h2>Available Sites (${res.length})</h2>
      <div class="small">Nights: ${n}</div>
    </div>
    <div id="list"></div>
    <button class="ghost" onclick="navigate('/search')">Back</button>
  `;

  const list=$$("#list",c);
  res.forEach(s=>{
    const card=document.createElement("div");
    card.className="card";
    card.innerHTML=`
      <div class="flex">
        <h3>${s.name}</h3>
        <span class="right">${formatMoney(s.price)}/night</span>
      </div>
      <div class="small"><span class="badge">${s.type}</span> Max ${s.maxLength}ft</div>
      <button data-id="${s.id}">Select</button>
    `;
    $$("#list",c).appendChild(card);

    card.querySelector("button").onclick=()=>{
      state.selection=s;
      navigate("/details");
    };
  });

  return c;
}

function renderDetails(){
  if(!state.selection){ navigate("/results"); return document.createElement("div"); }
  const s=state.selection;
  const p=computePrice(s,state.search.checkIn,state.search.checkOut);

  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>${s.name}</h2>
      <div class="small"><span class="badge">${s.type}</span> Max ${s.maxLength}ft</div>
      <p>${s.policy}</p>
      <div class="card">
        <h3>Price</h3>
        <div class="kv">
          <div>Subtotal</div><div>${formatMoney(p.subtotal)}</div>
          <div>Tax</div><div>${formatMoney(p.tax)}</div>
          <div>Fee</div><div>${formatMoney(p.fee)}</div>
          <div><strong>Total</strong></div><div><strong>${formatMoney(p.total)}</strong></div>
        </div>
        <button id="go">Continue</button>
      </div>
    </div>
    <button class="ghost" onclick="navigate('/results')">Back</button>
  `;

  $$("#go",c).onclick=()=>navigate("/booking");
  return c;
}

function renderBooking(){
  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>Guest & Rig Info</h2>
      <form id="f" class="grid cols-2">
        <label>First<input id="fn" required/></label>
        <label>Last<input id="ln" required/></label>
        <label>Email<input id="em" type="email" required/></label>
        <label>Phone<input id="ph" required/></label>
        <label>Rig Length<input id="rl" type="number" value="${state.search.rigLength}" required/></label>
        <label>Requests<textarea id="rq"></textarea></label>
        <div class="right"><button>Continue</button></div>
      </form>
      <div id="err" class="error" style="display:none"></div>
    </div>
    <button class="ghost" onclick="navigate('/details')">Back</button>
  `;

  $$("#f",c).onsubmit=e=>{
    e.preventDefault();
    const ph=$$("#ph",c).value.trim();
    if(!ph){
      $$("#err",c).textContent="Phone is required.";
      $$("#err",c).style.display="block";
      return;
    }

    state.booking.contact={
      firstName:$$("#fn",c).value.trim(),
      lastName:$$("#ln",c).value.trim(),
      email:$$("#em",c).value.trim(),
      phone:ph
    };
    state.booking.rig.length=+$$("#rl",c).value;
    state.booking.requests=$$("#rq",c).value;

    navigate("/payment");
  };

  return c;
}

function renderPayment(){
  const s=state.selection;
  let pricing=computePrice(s,state.search.checkIn,state.search.checkOut);

  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>Payment</h2>
      <div class="kv">
        <div>Subtotal</div><div>${formatMoney(pricing.subtotal)}</div>
        <div>Tax</div><div>${formatMoney(pricing.tax)}</div>
        <div>Fee</div><div>${formatMoney(pricing.fee)}</div>
        <div id="promoLabel">Promo</div><div id="promoVal">—</div>
        <div><strong>Total</strong></div><div id="totalVal"><strong>${formatMoney(pricing.total)}</strong></div>
      </div>

      <label>Promo<input id="pm"/></label>
      <button id="apply" class="secondary">Apply</button>

      <label>Card<input id="card"/></label>
      <button id="pay">Pay & Book</button>

      <div id="err" class="error" style="display:none"></div>
      <div id="ok" class="success" style="display:none"></div>
    </div>
    <button class="ghost" onclick="navigate('/booking')">Back</button>
  `;

  $$("#apply",c).onclick=()=>{
    const code=$$("#pm",c).value.trim();
    try{
      pricing=computePrice(s,state.search.checkIn,state.search.checkOut,code);
      $$("#promoVal",c).textContent=`-${formatMoney(pricing.discount)}`;
      $$("#totalVal",c).textContent=formatMoney(pricing.total);
      $$("#err",c).style.display="none";
    }catch{
      $$("#err",c).textContent="Invalid promo code.";
      $$("#err",c).style.display="block";
    }
  };

  $$("#pay",c).onclick=()=>{
    const card=$$("#card",c).value.replace(/\s+/g,"");
    if(card.startsWith("0000")){
      $$("#err",c).textContent="Card declined.";
      $$("#err",c).style.display="block";
      return;
    }

    if(!state.payment.idempotencyKey)
      state.payment.idempotencyKey=crypto.randomUUID();

    const id="BK-"+Math.random().toString(36).slice(2,8).toUpperCase();
    state.confirmation={
      id,
      total:pricing.total,
      email:state.booking.contact.email,
      dates:{...state.search},
      site:state.selection
    };

    $$("#ok",c).textContent="Payment successful!";
    $$("#ok",c).style.display="block";
    setTimeout(()=>navigate("/confirm"),400);
  };

  return c;
}

function renderConfirm(){
  const x=state.confirmation;
  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>Booking Confirmed</h2>
      <div class="kv">
        <div>ID</div><div>${x.id}</div>
        <div>Total</div><div>${formatMoney(x.total)}</div>
        <div>Email</div><div>${x.email}</div>
        <div>Dates</div><div>${x.dates.checkIn} → ${x.dates.checkOut}</div>
      </div>
      <button onclick="navigate('/manage')">Manage Booking</button>
    </div>
  `;
  return c;
}

function renderManage(){
  const c=document.createElement("section");
  c.innerHTML=`
    <div class="card">
      <h2>Manage Booking</h2>
      <label>Booking ID<input id="bid"/></label>
      <label>Email<input id="bem"/></label>
      <button id="v">View</button>
      <div id="out"></div>
    </div>
    <button class="ghost" onclick="navigate('/search')">Back</button>
  `;

  $$("#v",c).onclick=()=>{
    const id=$$("#bid",c).value.trim();
    const em=$$("#bem",c).value.trim();
    const out=$$("#out",c);

    if(!state.confirmation || id!==state.confirmation.id || em!==state.confirmation.email){
      out.innerHTML=`<div class="error">Booking not found.</div>`;
      return;
    }

    const x=state.confirmation;
    out.innerHTML=`
      <div class="card">
        <h3>Your Booking</h3>
        <div class="kv">
          <div>Site</div><div>${x.site.name}</div>
          <div>Total Paid</div><div>${formatMoney(x.total)}</div>
          <div>Policy</div><div>${x.site.policy}</div>
        </div>
        <button id="c">Cancel Booking</button>
        <div id="cx"></div>
      </div>
    `;

    $$("#c",c).onclick=()=>{
      const r=refundCalc(x.site,x.dates.checkIn);
      const cx=$$("#cx",c);

      let refund=x.total;
      if(r.type==="minusNight") refund-=x.site.price;

      cx.innerHTML=`
        <div class="card">
          Refund: ${formatMoney(refund)}
          <button id="cc">Confirm Cancel</button>
        </div>
      `;

      $$("#cc",c).onclick=()=>{
        cx.innerHTML=`<div class="success">Canceled.</div>`;
      };
    };
  };

  return c;
}
