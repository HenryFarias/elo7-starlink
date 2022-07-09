package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.planet.dto.ObjectDTO;
import br.com.elo7.starlink.domains.planet.entity.Object;

public interface ObjectService {
    void receiveObject(ObjectDTO objectDTO);
    void updateCoordinates(Object object, int x, int y);
    boolean existsByObjectIdAndPlanetId(String objectId, Long planetId);
}
