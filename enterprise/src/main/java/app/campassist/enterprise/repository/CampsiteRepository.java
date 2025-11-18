package app.campassist.enterprise.repository;

import app.campassist.enterprise.model.Campsite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, UUID> {
    List<Campsite> findByState(String state);
}
