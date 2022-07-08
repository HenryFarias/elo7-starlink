package br.com.elo7.starlink.domains.planet.dto;

import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ObjectDTO {
    private Long id;
    @JsonIgnore
    private Long planetId;
    private String name;
    private String description;
    private int x;
    private int y;

    public ObjectDTO() {}

    public ObjectDTO(ProbeDTO probeDTO, Long planetId) {
        this.id = probeDTO.getId();
        this.planetId = planetId;
        this.name = probeDTO.getName();
        this.description = probeDTO.getDescription();
        this.x = probeDTO.getX();
        this.y = probeDTO.getY();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
