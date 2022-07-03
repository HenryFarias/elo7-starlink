package br.com.elo7.sonda.candidato.planet.service;

import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.planet.dto.PlanetDTO;
import br.com.elo7.sonda.candidato.planet.entity.Object;
import br.com.elo7.sonda.candidato.planet.repository.ObjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepository repository;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private ModelMapper modelMapper;

    public void receiveObject(ObjectDTO objectDTO) throws Exception {
        // validar se o objeto está fora do planeta
        // validar se já existe um objeto nessa área
//        StrangeObject object = repository.findById(objectDTO.getId())
//                .orElseGet(() -> repository.save(modelMapper.map(objectDTO, StrangeObject.class)));
        PlanetDTO planet = planetService.find(objectDTO.getPlanetId());
        Object object = repository.findById(objectDTO.getId())
                .orElse(null);
        if (object == null) {
            repository.save(modelMapper.map(objectDTO, Object.class));
        } else {
            if (existsInPlanet(planet, object)) {
                throw new Exception("Could not find this object on this planet");
            }
            object.setX(objectDTO.getX());
            object.setY(objectDTO.getY());
            repository.save(object);
        }
    }

    private boolean existsInPlanet(PlanetDTO planet, Object object) {
        return !Objects.equals(planet.getId(), object.getPlanet().getId());
    }
}
