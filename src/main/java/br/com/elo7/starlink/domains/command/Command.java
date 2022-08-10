package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.Converter;
import br.com.elo7.starlink.domains.planet.Planet;
import br.com.elo7.starlink.domains.position.PositionRepository;
import br.com.elo7.starlink.domains.probe.Probe;
import br.com.elo7.starlink.domains.probe.ProbeRepository;

public class Command {

    final private Planet planet;
    final private Converter converter;
    final private PositionRepository positionRepository;
    final Probe probe;
    final ProbeRepository probeRepository;

    public Command(Probe probe, ProbeRepository probeRepository, Planet planet,
                Converter converter, PositionRepository positionRepository) {
        this.planet = planet;
        this.converter = converter;
        this.positionRepository = positionRepository;
        this.probe = probe;
        this.probeRepository = probeRepository;
    }

    private boolean isMove(char cmd) {
        return cmd == 'M';
    }

    public void run(String commands) {
        var direction = probe.getDirection();
        var area = probe.getArea();
        var oldArea = probe.getArea().generateNewArea();
        for (char cmd : commands.toCharArray()) {
            direction = Turn.find(cmd)
                    .execute(direction);
            if (isMove(cmd)) {
                area.calculateNewArea(direction);
                area.validCoordinatesAreOutOfPlanet(planet);
                planet.getPosition().save(positionRepository, converter, area, oldArea, probe.getName());
            }
            probe.update(probeRepository, direction, area);
        }
    }
}
