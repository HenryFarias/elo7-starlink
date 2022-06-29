package br.com.elo7.sonda.candidato.planet.controller;

import elo7.starlink.elo7starlinkplanet.dto.PlanetDTO;
import elo7.starlink.elo7starlinkplanet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class PlanetController {

    @Autowired
    private PlanetService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody PlanetDTO request) {
        this.service.save(request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanetDTO find(@PathVariable Long id) throws Exception {
        return this.service.find(id);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetDTO> findAll() {
        return this.service.findAll();
    }
}
