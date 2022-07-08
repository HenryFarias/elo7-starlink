package br.com.elo7.starlink.domains.planet.controller;

import br.com.elo7.starlink.domains.planet.service.PlanetService;
import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.planet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/planet")
public class PlanetController {

    @Autowired
    private PlanetService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody PlanetDTO request) {
        service.save(request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanetDTO find(@PathVariable Long id) {
        return service.find(id);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetDTO> findAll() {
        return service.findAll();
    }
}
