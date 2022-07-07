package br.com.elo7.sonda.candidato.planet.service;

import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.planet.entity.Object;

public interface ObjectService {
    void receiveObject(ObjectDTO objectDTO) throws Exception;
    void updateCoordinates(Object object, int x, int y);
}
