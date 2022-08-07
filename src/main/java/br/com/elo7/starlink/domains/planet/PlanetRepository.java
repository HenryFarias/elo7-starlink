package br.com.elo7.starlink.domains.planet;

import br.com.elo7.starlink.infra.entity.Planet;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository {
    void save(Planet planet);

    Optional<Planet> findById(Long id);

    List<Planet> findAll();
}
