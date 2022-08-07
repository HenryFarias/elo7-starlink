package br.com.elo7.starlink.domains.probe;

import br.com.elo7.starlink.domains.command.CommandDTO;
import br.com.elo7.starlink.domains.direction.Direction;
import br.com.elo7.starlink.domains.command.Movement;
import br.com.elo7.starlink.domains.object.ObjectDTO;
import br.com.elo7.starlink.domains.object.ObjectInterface;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;

public class Probe {

    private Long id;
    private String name;
    private String description;
    private int x;
    private int y;
    private Direction direction;

    private final ObjectInterface object;
    private final ProbeRepository repository;
    private final ModelMapper modelMapper;
    private final List<Movement> movements;

    public Probe(ProbeRepository repository, ModelMapper modelMapper, List<Movement> movements,
                            ObjectInterface object) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.movements = movements;
        this.object = object;
    }

    public void save(ProbeDTO probeDTO) {
        this.repository.save(modelMapper.map(probeDTO, br.com.elo7.starlink.infra.entity.Probe.class));
    }

    public ProbeDTO find(Long id) {
        return this.repository
                .findById(id)
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .orElseThrow(() -> new ApplicationException("Probe don't exists", HttpStatus.BAD_REQUEST));
    }

    public void sendCommand(Long probeId, CommandDTO commandDTO) {
        ProbeDTO probe = find(probeId);
        probe.setDirection(commandDTO.getDirection());
        moveProbe(probe, commandDTO.getCommands());
        save(probe);
        object.receiveObject(new ObjectDTO(probe, commandDTO.getPlanetId()));
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
