package br.com.elo7.sonda.candidato.domains.probe.service;

import br.com.elo7.sonda.candidato.domains.probe.dto.AreaDTO;
import br.com.elo7.sonda.candidato.domains.probe.dto.CommandDTO;
import br.com.elo7.sonda.candidato.domains.probe.dto.ProbeDTO;

import java.util.List;

public interface ProbeService {
    List<ProbeDTO> findAll();
    ProbeDTO find(Long id);
    void save(ProbeDTO planetDTO);
    void sendCommand(Long probeId, CommandDTO commandDTO);
    void moveProbe(ProbeDTO probe, String commands);
    void sendToPlanet(Long probeId, AreaDTO area);
}
