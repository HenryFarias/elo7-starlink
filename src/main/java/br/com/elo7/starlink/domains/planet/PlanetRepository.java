package br.com.elo7.starlink.domains.planet;

import java.util.List;

public interface PlanetRepository {
    void save(Planet planet);
    Planet findById(Long id);
    List<Planet> findAll();
}
