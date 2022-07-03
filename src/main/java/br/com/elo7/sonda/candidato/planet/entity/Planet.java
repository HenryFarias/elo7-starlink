package br.com.elo7.sonda.candidato.planet.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Planet {

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
    @OneToMany(mappedBy = "planet")
    private List<StrangeObject> objects;

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

    public void setWidth(int sizeX) {
        this.width = sizeX;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int sizeY) {
        this.height = sizeY;
    }

    public List<StrangeObject> getObjects() {
        return objects;
    }

    public void setObjects(List<StrangeObject> objects) {
        this.objects = objects;
    }
}
