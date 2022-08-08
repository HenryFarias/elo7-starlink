package br.com.elo7.starlink.domains.position;

import br.com.elo7.starlink.domains.Converter;
import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public class Position {

    private Long id;
    private String mapBusy;

    public boolean isBusy(Converter converter, Area area) throws RuntimeException {
        var busy = toMap(converter);
        return busy.get(area.toString()) != null;
    }

    public void save(PositionRepository repository, Converter converter, Area newArea, Area oldArea, String name) throws RuntimeException {
        if (isBusy(converter, newArea)) {
            throw new ApplicationException("Position Busy =( " + newArea);
        }
        var busy = toMap(converter);
        busy.remove(oldArea.toString());
        busy.put(newArea.toString(), name);
        mapBusy = converter.serialize(busy);
        repository.save(this);
    }

    private Map<String, String> toMap(Converter converter) throws RuntimeException {
        if (this.mapBusy == null) {
            return new HashMap<>();
        }
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
        return converter.deserialize(this.mapBusy, typeRef);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapBusy() {
        return mapBusy;
    }

    public void setMapBusy(String mapBusy) {
        this.mapBusy = mapBusy;
    }
}
