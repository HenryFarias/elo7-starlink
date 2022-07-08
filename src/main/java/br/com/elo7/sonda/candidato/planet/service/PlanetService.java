package br.com.elo7.sonda.candidato.planet.service;

import br.com.elo7.sonda.candidato.planet.dto.PlanetDTO;

import java.util.List;

public interface PlanetService {
    List<PlanetDTO> findAll();
    PlanetDTO find(Long id);
    void save(PlanetDTO planetDTO);
}
