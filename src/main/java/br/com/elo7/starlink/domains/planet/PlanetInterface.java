package br.com.elo7.starlink.domains.planet;

import java.util.List;

public interface PlanetInterface {
    List<PlanetDTO> findAll();
    PlanetDTO find(Long id);
    void save(PlanetDTO planetDTO);
}
