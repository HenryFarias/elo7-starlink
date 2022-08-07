package br.com.elo7.starlink.domains.object;

import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.planet.PlanetInterface;
import br.com.elo7.starlink.infra.entity.Planet;
import org.modelmapper.ModelMapper;

public class Object implements ObjectInterface {

    private final ObjectRepository repository;
    private final PlanetInterface planetService;
    private final ModelMapper modelMapper;

    public Object(ObjectRepository repository, PlanetInterface planetService, ModelMapper modelMapper) {
        this.repository = repository;
        this.planetService = planetService;
        this.modelMapper = modelMapper;
    }

    public boolean existsByObjectIdAndPlanetId(String objectId, Long planetId) {
        return repository.existsByObjectIdAndPlanet_Id(objectId, planetId);
    }

    public void receiveObject(ObjectDTO objectDTO) {
        PlanetDTO planet = planetService.find(objectDTO.getPlanetId())
                .coordinatesIsInsidePlanet(objectDTO.getX(), objectDTO.getY())
                .thereIsNoObjectAtCoordinates(objectDTO.getX(), objectDTO.getY());
        repository.findByObjectIdAndPlanet_Id(objectDTO.getObjectId(), planet.getId())
            .ifPresentOrElse(
                (object -> updateCoordinates(object, objectDTO.getX(), objectDTO.getY())),
                () -> save(objectDTO, planet)
            );
    }

    private void save(ObjectDTO objectDTO, PlanetDTO planet) {
        var object = modelMapper.map(objectDTO, br.com.elo7.starlink.infra.entity.Object.class);
        object.setPlanet(modelMapper.map(planet, Planet.class));
        repository.save(object);
    }

    public void updateCoordinates(br.com.elo7.starlink.infra.entity.Object object, int x, int y) {
        object.setX(x);
        object.setY(y);
        repository.save(object);
    }
}
