package br.com.elo7.starlink.domains.probe.dto;

import javax.validation.constraints.NotNull;

public class AreaDTO {

    @NotNull(message = "x is required")
    private int x;

    @NotNull(message = "y is required")
    private int y;

    @NotNull(message = "planetId is required")
    private Long planetId;

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

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }
}
