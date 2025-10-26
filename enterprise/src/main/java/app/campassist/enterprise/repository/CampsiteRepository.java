package app.campassist.enterprise.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.campassist.enterprise.model.Campsite;

@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, UUID> {

}
