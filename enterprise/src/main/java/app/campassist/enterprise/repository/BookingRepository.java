package app.campassist.enterprise.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.campassist.enterprise.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
