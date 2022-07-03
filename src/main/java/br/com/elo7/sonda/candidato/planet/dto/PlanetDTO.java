package br.com.elo7.sonda.candidato.planet.dto;

import java.util.List;

public class PlanetDTO {
    private Long id;
    private String name;
    private String description;
    private Integer width;
    private Integer height;
    private List<ObjectDTO> objects;

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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<ObjectDTO> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectDTO> objects) {
        this.objects = objects;
    }
}
