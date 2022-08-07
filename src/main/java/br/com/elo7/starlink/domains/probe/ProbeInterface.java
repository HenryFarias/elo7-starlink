package br.com.elo7.starlink.domains.probe;

import br.com.elo7.starlink.domains.area.AreaDTO;
import br.com.elo7.starlink.domains.command.CommandDTO;

import java.util.List;

public interface ProbeInterface {
    List<ProbeDTO> findAll();
    ProbeDTO find(Long id);
    void save(ProbeDTO planetDTO);
    void sendCommand(Long probeId, CommandDTO commandDTO);
    void moveProbe(ProbeDTO probe, String commands);
    void sendToPlanet(Long probeId, AreaDTO area);
}
