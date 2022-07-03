package br.com.elo7.sonda.candidato.probe.service;

import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.planet.service.StrangeObjectService;
import br.com.elo7.sonda.candidato.probe.dto.CommandDTO;
import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.entity.Probe;
import br.com.elo7.sonda.candidato.probe.repository.ProbeRepository;
import br.com.elo7.sonda.candidato.probe.service.movements.Movement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbeServiceImpl implements ProbeService {

    @Autowired
    private ProbeRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private List<Movement> movements;
    @Autowired
    private StrangeObjectService objectService;

    public void save(ProbeDTO probeDTO) {
        this.repository.save(modelMapper.map(probeDTO, Probe.class));
    }

    public ProbeDTO find(Long id) throws Exception {
        return this.repository
                .findById(id)
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .orElseThrow(() -> new Exception("Probe don't exists"));
    }

    public List<ProbeDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .collect(Collectors.toList());
    }

    public void sendCommand(Long probeId, CommandDTO commandDTO) throws Exception {
        ProbeDTO probe = find(probeId);
        for (char command : commandDTO.getCommands().toCharArray()) {
            probe = movements.stream()
                    .filter(movement -> movement.findCommand().command == command)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Command not recognized"))
                    .setProbe(probe)
                    .moveTo(commandDTO.getDirection())
                    .getProbe();
        }
        save(probe);
        objectService.receiveObject(toObjectDto(probe, commandDTO.getPlanetId()));
    }

    private ObjectDTO toObjectDto(ProbeDTO probeDTO, Long planetId) {
        return new ObjectDTO(probeDTO, planetId);
    }
}
