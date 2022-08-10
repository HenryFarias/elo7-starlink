package br.com.elo7.starlink.controller.planet;

import br.com.elo7.starlink.domains.planet.Planet;
import br.com.elo7.starlink.domains.planet.PlanetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/planet")
public class PlanetController implements PlanetControllerDocument {

    private final PlanetRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlanetController(PlanetRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid PlanetDTO request) {
        var planet = modelMapper.map(request, Planet.class);
        planet.save(repository);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanetDTO find(@PathVariable Long id) {
        var planet = repository.findById(id);
        return modelMapper.map(planet, PlanetDTO.class);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(planet -> modelMapper.map(planet, PlanetDTO.class))
                .toList();
    }
}
