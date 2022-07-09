package br.com.elo7.starlink.domains.probe.controller;

import br.com.elo7.starlink.domains.probe.dto.AreaDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/probe")
public class ProbeController implements ProbeControllerDocument {

    @Autowired
    private ProbeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ProbeDTO request) {
        service.save(request);
    }

    @PostMapping("/{id}/command")
    @ResponseStatus(HttpStatus.OK)
    public void sendCommand(@PathVariable Long id, @RequestBody CommandDTO request) {
        service.sendCommand(id, request);
    }

    @PostMapping("/{id}/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendToPlanet(@PathVariable Long id, @RequestBody AreaDTO request) {
        service.sendToPlanet(id, request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProbeDTO find(@PathVariable Long id) {
        return service.find(id);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ProbeDTO> findAll() {
        return service.findAll();
    }
}
