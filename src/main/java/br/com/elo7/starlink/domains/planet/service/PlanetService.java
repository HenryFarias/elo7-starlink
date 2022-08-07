package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;

import java.util.List;

public interface PlanetService {
    List<PlanetDTO> findAll();
    PlanetDTO find(Long id);
    void save(PlanetDTO planetDTO);
}
