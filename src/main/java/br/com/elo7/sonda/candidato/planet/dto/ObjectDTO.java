package br.com.elo7.sonda.candidato.planet.dto;

import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;

public class ObjectDTO {
    private Long objectId;
    private Long planetId;
    private String name;
    private String description;
    private int x;
    private int y;

    public ObjectDTO(ProbeDTO probeDTO, Long planetId) {
        this.objectId = probeDTO.getId();
        this.planetId = planetId;
        this.name = probeDTO.getName();
        this.description = probeDTO.getDescription();
        this.x = probeDTO.getArea().getX();
        this.y = probeDTO.getArea().getY();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
