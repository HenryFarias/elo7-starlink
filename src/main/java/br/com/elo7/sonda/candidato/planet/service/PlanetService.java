package br.com.elo7.sonda.candidato.planet.service;

import br.com.elo7.sonda.candidato.planet.dto.ObjectDTO;
import br.com.elo7.sonda.candidato.planet.dto.PlanetDTO;
import br.com.elo7.sonda.candidato.planet.entity.Planet;
import br.com.elo7.sonda.candidato.planet.entity.StrangeObject;
import br.com.elo7.sonda.candidato.planet.repository.PlanetRepository;
import br.com.elo7.sonda.candidato.planet.repository.StrangeObjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository repository;

    @Autowired
    private StrangeObjectRepository objectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void save(PlanetDTO planetDTO) {
        this.repository.save(modelMapper.map(planetDTO, Planet.class));
    }

    public PlanetDTO find(Long id) throws Exception {
        return this.repository
                .findById(id)
                .map(planet -> modelMapper.map(planet, PlanetDTO.class))
                .orElseThrow(() -> new Exception("Planet not found"));
    }

    public List<PlanetDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(planet -> modelMapper.map(planet, PlanetDTO.class))
                .collect(Collectors.toList());
    }

    public void receiveObject(ObjectDTO objectDTO) {
        // validar se o objeto está fora do planeta
        // validar se já existe um objeto nessa área
//        StrangeObject object = objectRepository.findById(objectDTO.getObjectId())
//                .orElseGet(() -> objectRepository.save(modelMapper.map(objectDTO, StrangeObject.class)));
        StrangeObject object = objectRepository.findById(objectDTO.getObjectId())
                .orElse(null);
        if (object == null) {
            objectRepository.save(modelMapper.map(objectDTO, StrangeObject.class));
        } else {
            object.setX(objectDTO.getX());
            object.setY(objectDTO.getY());
            objectRepository.save(object);
        }
    }
}
