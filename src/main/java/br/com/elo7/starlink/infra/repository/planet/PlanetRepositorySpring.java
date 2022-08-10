package br.com.elo7.starlink.infra.repository.planet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepositorySpring extends JpaRepository<PlanetEntity, Long> {
}
