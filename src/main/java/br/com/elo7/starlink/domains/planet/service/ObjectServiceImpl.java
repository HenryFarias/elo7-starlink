package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.planet.dto.ObjectDTO;
import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.planet.entity.Object;
import br.com.elo7.starlink.domains.planet.entity.Planet;
import br.com.elo7.starlink.domains.planet.repository.ObjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepository repository;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private ModelMapper modelMapper;

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
        var object = modelMapper.map(objectDTO, Object.class);
        object.setPlanet(modelMapper.map(planet, Planet.class));
        repository.save(object);
    }

    public void updateCoordinates(Object object, int x, int y) {
        object.setX(x);
        object.setY(y);
        repository.save(object);
    }
}
