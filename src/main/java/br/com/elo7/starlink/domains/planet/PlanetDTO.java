package br.com.elo7.starlink.domains.planet;

import br.com.elo7.starlink.domains.object.ObjectDTO;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PlanetDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ObjectDTO> objects;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Width is required")
    private Integer width;

    @NotNull(message = "Height is required")
    private Integer height;

    public PlanetDTO coordinatesIsInsidePlanet(int x, int y) {
        var invalid = x > this.getWidth() ||
                x < 0 ||
                y > this.getHeight() ||
                y < 0;
        if (invalid) {
            throw new ApplicationException("Object is off planet");
        }
        return this;
    }

    public PlanetDTO thereIsNoObjectAtCoordinates(int x, int y) {
        for (ObjectDTO object : this.getObjects()) {
            if (y == object.getY() && x == object.getX()) {
                throw new ApplicationException("There is already an object in this area");
            }
        }
        return this;
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
