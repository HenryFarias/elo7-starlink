package br.com.elo7.starlink.domains.object;

import br.com.elo7.starlink.infra.entity.Object;

public interface ObjectInterface {
    void receiveObject(ObjectDTO objectDTO);
    void updateCoordinates(Object object, int x, int y);
    boolean existsByObjectIdAndPlanetId(String objectId, Long planetId);
}
