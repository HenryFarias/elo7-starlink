package br.com.elo7.starlink.domains.planet;

import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class Planet implements PlanetInterface {

    private final PlanetRepository repository;
    private final ModelMapper modelMapper;

    public Planet(PlanetRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public void save(PlanetDTO planetDTO) {
        this.repository.save(modelMapper.map(planetDTO, br.com.elo7.starlink.infra.entity.Planet.class));
    }

    public PlanetDTO find(Long id) {
        return this.repository
                .findById(id)
                .map(planet -> modelMapper.map(planet, PlanetDTO.class))
                .orElseThrow(() -> new ApplicationException("Planet not found", HttpStatus.BAD_REQUEST));
    }

    public List<PlanetDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(planet -> modelMapper.map(planet, PlanetDTO.class))
                .collect(Collectors.toList());
    }
}
