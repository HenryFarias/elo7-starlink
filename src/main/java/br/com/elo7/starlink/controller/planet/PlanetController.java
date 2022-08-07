package br.com.elo7.starlink.controller.planet;

import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.planet.PlanetInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/planet")
public class PlanetController implements PlanetControllerDocument {

    @Autowired
    private PlanetInterface service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid PlanetDTO request) {
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
