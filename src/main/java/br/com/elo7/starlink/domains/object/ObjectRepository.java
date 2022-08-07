package br.com.elo7.starlink.domains.object;

import br.com.elo7.starlink.infra.entity.Object;

import java.util.Optional;

public interface ObjectRepository {
    Optional<Object> findByObjectIdAndPlanet_Id(String objectId, Long planetId);
    boolean existsByObjectIdAndPlanet_Id(String objectId, Long planetId);
    void save(Object object);
}
