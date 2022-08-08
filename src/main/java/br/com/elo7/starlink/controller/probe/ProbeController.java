package br.com.elo7.starlink.controller.probe;

import br.com.elo7.starlink.controller.probe.dto.ProbeDTO;
import br.com.elo7.starlink.controller.probe.dto.SendToPlanetDTO;
import br.com.elo7.starlink.domains.Converter;
import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.domains.direction.Direction;
import br.com.elo7.starlink.domains.planet.PlanetRepository;
import br.com.elo7.starlink.domains.position.PositionRepository;
import br.com.elo7.starlink.domains.probe.Probe;
import br.com.elo7.starlink.domains.probe.ProbeRepository;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/probes")
public class ProbeController {

    private final ProbeRepository repository;
    private final PlanetRepository planetRepository;
    private final ModelMapper modelMapper;
    private final Converter converter;
    private final PositionRepository positionRepository;

    @Autowired
    public ProbeController(ProbeRepository repository, PlanetRepository planetRepository, ModelMapper modelMapper,
                           Converter converter, PositionRepository positionRepository) {
        this.repository = repository;
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
        this.converter = converter;
        this.positionRepository = positionRepository;
    }

//    @PostMapping("{probeId}/planets/{planetId}/command")
//    @ResponseStatus(HttpStatus.OK)
//    public void sendCommand(@PathVariable Long planetId, @PathVariable Long probeId, @RequestBody @Valid CommandDTO request) throws Exception {
//        var planet = planetRepository.findById(planetId);
//        var probe = repository.findById(probeId);
//        var oldArea = probe.getArea();
//
//        probe.calculateNewArea(request);
//
//        if (!planet.receiveObject(positionRepository, converter, probe.getArea(), oldArea, probe.getName())) {
//            throw new ApplicationException("failed to send probe to planet");
//        }
//
//        repository.save(probe);
//    }

    @PostMapping("{probeId}/planets/{planetId}/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendToPlanet(@PathVariable Long planetId, @PathVariable Long probeId, @RequestBody @Valid SendToPlanetDTO request) throws Exception {
        var area = modelMapper.map(request, Area.class);
        var planet = planetRepository.findById(planetId);
        var probe = repository.findById(probeId);
        var position = planet.getPosition();

        if (probe.alreadyExistsInPlanet(planetId)) {
            throw new ApplicationException("Probe already exists in this planet", HttpStatus.BAD_REQUEST);
        }

        area.validCoordinatesAreOutOfPlanet(planet);
        position.save(positionRepository, converter, area, area, probe.getName());

        probe.decorate(area, Direction.find(request.getDirection()), planetId);
        repository.save(probe);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid ProbeDTO request) {
        var probe = modelMapper.map(request, Probe.class);
        if (probe.getArea() == null) {
            probe.setArea(new Area());
        }
        repository.save(probe);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProbeDTO find(@PathVariable Long id) {
        var probe = repository.findById(id);
        return modelMapper.map(probe, ProbeDTO.class);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ProbeDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .toList();
    }
}
