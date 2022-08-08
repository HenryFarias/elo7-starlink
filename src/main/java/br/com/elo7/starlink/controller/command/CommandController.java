package br.com.elo7.starlink.controller.command;

import br.com.elo7.starlink.controller.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.Converter;
import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.domains.command.Commands;
import br.com.elo7.starlink.domains.planet.PlanetRepository;
import br.com.elo7.starlink.domains.position.PositionRepository;
import br.com.elo7.starlink.domains.probe.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Consumer;

@RestController
@RequestMapping(path = "/commands")
public class CommandController {

    private final ProbeRepository probeRepository;
    private final PlanetRepository planetRepository;
    private final Converter converter;
    private final PositionRepository positionRepository;

    @Autowired
    public CommandController(ProbeRepository probeRepository, PlanetRepository planetRepository,
                             Converter converter, PositionRepository positionRepository) {
        this.probeRepository = probeRepository;
        this.planetRepository = planetRepository;
        this.converter = converter;
        this.positionRepository = positionRepository;
    }

    @PostMapping("/planets/{planetId}/probes/{probeId}/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendCommand(@PathVariable Long planetId, @PathVariable Long probeId, @RequestBody @Valid CommandDTO request) throws Exception {
        var planet = planetRepository.findById(planetId);
        var probe = probeRepository.findById(probeId);
        var position = planet.getPosition();
        var oldArea = new Area(probe.getArea().toString());
        Consumer<Area> onMove = newArea -> {
            newArea.validCoordinatesAreOutOfPlanet(planet);
            position.save(positionRepository, converter, newArea, oldArea, probe.getName());
        };
        Commands.run(request.getCommands(), probe.getDirection(), probe.getArea(), onMove, probe.onSave(probeRepository));
    }

//    @PostMapping("planets/{planetId}/probes/{probeId}/send")
//    @ResponseStatus(HttpStatus.OK)
//    public void sendCommand(@PathVariable Long planetId, @PathVariable Long probeId, @RequestBody @Valid CommandDTO request) throws Exception {
//        var planet = planetRepository.findById(planetId);
//        var probe = probeRepository.findById(probeId);
//        new Move(probe, probeRepository, planet, converter, positionRepository)
//                .execute(request.getCommands());
//    }
}
