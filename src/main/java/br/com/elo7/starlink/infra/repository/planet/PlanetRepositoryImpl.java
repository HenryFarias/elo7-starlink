package br.com.elo7.starlink.infra.repository.planet;

import br.com.elo7.starlink.domains.planet.Planet;
import br.com.elo7.starlink.domains.planet.PlanetRepository;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import br.com.elo7.starlink.infra.repository.position.PositionRepositorySpring;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanetRepositoryImpl implements PlanetRepository {

    @Autowired
    private PlanetRepositorySpring repository;

    @Autowired
    private PositionRepositorySpring positionRepositorySpring;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Planet planet) {
        var entity = modelMapper.map(planet, PlanetEntity.class);
        repository.save(entity);
    }

    @Override
    public Planet findById(Long id) {
        return this.repository
                .findById(id)
                .map(entity -> modelMapper.map(entity, Planet.class))
                .orElseThrow(() -> new ApplicationException("Planet not found", HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<Planet> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, Planet.class))
                .collect(Collectors.toList());
    }
}
