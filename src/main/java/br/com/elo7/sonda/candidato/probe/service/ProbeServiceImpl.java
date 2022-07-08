package br.com.elo7.sonda.candidato.probe.service;

import br.com.elo7.sonda.candidato.exception.ApplicationException;
import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.planet.service.ObjectService;
import br.com.elo7.sonda.candidato.probe.dto.AreaDTO;
import br.com.elo7.sonda.candidato.probe.dto.CommandDTO;
import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.entity.Probe;
import br.com.elo7.sonda.candidato.probe.repository.ProbeRepository;
import br.com.elo7.sonda.candidato.probe.service.movements.Movement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbeServiceImpl implements ProbeService {

    private final ProbeRepository repository;
    private final ModelMapper modelMapper;
    private final List<Movement> movements;
    private final ObjectService objectService;

    @Autowired
    public ProbeServiceImpl(ProbeRepository repository, ModelMapper modelMapper, List<Movement> movements,
                            ObjectService objectService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.movements = movements;
        this.objectService = objectService;
    }

    public void save(ProbeDTO probeDTO) {
        this.repository.save(modelMapper.map(probeDTO, Probe.class));
    }

    public ProbeDTO find(Long id) {
        return this.repository
                .findById(id)
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .orElseThrow(() -> new ApplicationException("Probe don't exists", HttpStatus.BAD_REQUEST));
    }

    public List<ProbeDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .collect(Collectors.toList());
    }

    public void sendCommand(Long probeId, CommandDTO commandDTO) {
        ProbeDTO probe = find(probeId);
        probe.setDirection(commandDTO.getDirection());
        moveProbe(probe, commandDTO.getCommands());
        save(probe);
        objectService.receiveObject(new ObjectDTO(probe, commandDTO.getPlanetId()));
    }

    public void sendToPlanet(Long probeId, AreaDTO area) {
        ProbeDTO probe = find(probeId);
        probe.setX(area.getX());
        probe.setY(area.getY());
        save(probe);
        objectService.receiveObject(new ObjectDTO(probe, area.getPlanetId()));
    }

    public void moveProbe(ProbeDTO probe, String commands) {
        for (char command : commands.toCharArray()) {
            probe = movements.stream()
                    .filter(movement -> movement.findCommand().command == command)
                    .findFirst()
                    .orElseThrow(() -> new ApplicationException("Command not recognized", HttpStatus.BAD_REQUEST))
                    .setProbe(probe)
                    .moveTo()
                    .getProbe();
        }
    }
}
