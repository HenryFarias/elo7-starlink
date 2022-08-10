package br.com.elo7.starlink.infra.repository.planet;

import br.com.elo7.starlink.infra.repository.position.PositionEntity;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity(name = "planet")
public class PlanetEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String description;
    @Column
    private int width;
    @Column
    private int height;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private PositionEntity position;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }
}
