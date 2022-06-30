package br.com.elo7.sonda.candidato.probe.dto;

public class AreaDTO {
    private Integer probeId;
    private Integer planetId;
    private int x;
    private int y;

    public AreaDTO(int x, int y) {
        this.x = x;
        this.y = y;
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

    public Integer getProbeId() {
        return probeId;
    }

    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }

    public Integer getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId = planetId;
    }
}
