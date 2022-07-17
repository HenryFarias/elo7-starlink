package br.com.elo7.starlink.domains.probe.service;

import br.com.elo7.starlink.domains.planet.dto.ObjectDTO;
import br.com.elo7.starlink.domains.planet.service.ObjectService;
import br.com.elo7.starlink.domains.probe.dto.AreaDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.entity.Probe;
import br.com.elo7.starlink.domains.probe.enumeration.Command;
import br.com.elo7.starlink.domains.probe.repository.ProbeRepository;
import br.com.elo7.starlink.domains.probe.service.movements.Movement;
import br.com.elo7.starlink.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void sendToPlanet(Long probeId, AreaDTO area) {
        if (objectService.existsByObjectIdAndPlanetId(probeId.toString(), area.getPlanetId())) {
            throw new ApplicationException("Probe is already on this planet", HttpStatus.BAD_REQUEST);
        }
        ProbeDTO probe = find(probeId);
        probe.setX(area.getX());
        probe.setY(area.getY());
        save(probe);
        objectService.receiveObject(new ObjectDTO(probe, area.getPlanetId()));
    }

    @Transactional
    public void sendCommand(Long probeId, CommandDTO commandDTO) {
        ProbeDTO probe = find(probeId);
        probe.setDirection(commandDTO.getDirection());
        for (char command : commandDTO.getCommands().toCharArray()) {
            moveProbe(probe, command);
            save(probe);
            if (isMoveForward(command)) {
                objectService.receiveObject(new ObjectDTO(probe, commandDTO.getPlanetId()));
            }
        }
    }

    private boolean isMoveForward(char command) {
        return command == Command.M.command;
    }

    public void moveProbe(ProbeDTO probe, char command) {
        movements.stream()
            .filter(movement -> movement.findCommand().command == command)
            .findFirst()
            .orElseThrow(() -> new ApplicationException("Command not recognized", HttpStatus.BAD_REQUEST))
            .setProbe(probe)
            .moveTo();
    }
}
