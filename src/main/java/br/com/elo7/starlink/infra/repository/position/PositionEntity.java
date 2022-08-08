package br.com.elo7.starlink.infra.repository.position;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity(name = "position")
public class PositionEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String mapBusy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapBusy() {
        return mapBusy;
    }

    public void setMapBusy(String area) {
        this.mapBusy = area;
    }
}
