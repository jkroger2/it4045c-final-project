package app.campassist.enterprise.repository;

import app.campassist.enterprise.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByCampsiteId(UUID campsiteId);
    List<Booking> findByStartAndEndDate(LocalDate startDate, LocalDate endDate);
}
