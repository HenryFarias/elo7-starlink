package br.com.elo7.starlink.domains.planet.repository;

import br.com.elo7.starlink.domains.planet.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectRepository extends JpaRepository<Object, Long> {
    Optional<Object> findByObjectIdAndPlanet_Id(String objectId, Long planetId);
    boolean existsByObjectIdAndPlanet_Id(String objectId, Long planetId);
}
