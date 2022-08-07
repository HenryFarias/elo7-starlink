package br.com.elo7.starlink.domains.probe.dto;

import javax.validation.constraints.NotNull;

public class AreaDTO {

    @NotNull(message = "X is required")
    private Integer x;

    @NotNull(message = "Y is required")
    private Integer y;

    @NotNull(message = "Planet ID is required")
    private Long planetId;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }
}
