package br.com.elo7.sonda.candidato.planet.repository;

import elo7.starlink.elo7starlinkplanet.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
