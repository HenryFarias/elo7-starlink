package br.com.elo7.starlink.domains.probe;

import br.com.elo7.starlink.controller.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.domains.command.Commands;
import br.com.elo7.starlink.domains.direction.Direction;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Probe {

    private Long id;
    private String name;
    private String description;
    private Area area;
    private Direction direction;
    private Long planetId;

    public BiConsumer<Direction, Area> onSave(ProbeRepository repository) {
        return (newDirection, newArea) -> {
            direction = newDirection;
            area = newArea;
            repository.save(this);
        };
    }

    public void update(ProbeRepository repository, Direction direction, Area area) {
        this.direction = direction;
        this.area = area;
        repository.save(this);
    }

    public void calculateNewArea(CommandDTO commandDTO) {
        for (char cmd : commandDTO.getCommands().toCharArray()) {
            var newDirection = Commands.find(cmd).turn(direction);
            direction = newDirection;
            if (Commands.isMove(cmd)) {
                area.calculateNewArea(newDirection);
            }
        }
    }

    public void decorate(Area area, Direction direction, Long planetId) {
        this.area = area;
        this.direction = direction;
        this.planetId = planetId;
    }

    public boolean alreadyExistsInPlanet(Long planetId) {
        return Objects.equals(this.planetId, planetId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }
}
