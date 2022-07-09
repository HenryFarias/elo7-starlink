package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.planet.entity.Planet;
import br.com.elo7.starlink.exception.ApplicationException;
import br.com.elo7.starlink.domains.planet.repository.PlanetRepository;
import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.planet.entity.Planet;
import br.com.elo7.starlink.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public void save(PlanetDTO planetDTO) {
        this.repository.save(modelMapper.map(planetDTO, Planet.class));
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