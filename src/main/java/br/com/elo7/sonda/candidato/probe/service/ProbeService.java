package br.com.elo7.sonda.candidato.probe.service;

import br.com.elo7.sonda.candidato.probe.dto.CommandDTO;
import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;

import java.util.List;

public interface ProbeService {
    List<ProbeDTO> findAll();
    ProbeDTO find(Long id) throws Exception;
    void save(ProbeDTO planetDTO);
    void sendCommand(Long probeId, CommandDTO commandDTO) throws Exception;
}