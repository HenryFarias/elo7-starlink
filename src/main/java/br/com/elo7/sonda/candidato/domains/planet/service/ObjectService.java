package br.com.elo7.sonda.candidato.domains.planet.service;

import br.com.elo7.sonda.candidato.domains.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.domains.planet.entity.Object;

public interface ObjectService {
    void receiveObject(ObjectDTO objectDTO);
    void updateCoordinates(Object object, int x, int y);
}
