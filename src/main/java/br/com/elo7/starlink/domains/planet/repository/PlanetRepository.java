package br.com.elo7.starlink.domains.planet.repository;

import br.com.elo7.starlink.domains.planet.entity.Planet;
import br.com.elo7.starlink.domains.planet.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
