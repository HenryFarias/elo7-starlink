package br.com.elo7.sonda.candidato.planet.service;

import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;

public interface ObjectService {
    void receiveObject(ObjectDTO objectDTO) throws Exception;
}
