package br.com.elo7.starlink.domains.planet;

import br.com.elo7.starlink.domains.position.Position;

public class Planet {

    private Long id;
    private String name;
    private String description;
    private int width;
    private int height;
    private Position position;

    public void save(PlanetRepository repository) {
        if (position == null) {
            position = new Position();
        }
        repository.save(this);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
