package br.com.elo7.sonda.candidato.probe.controller;

import elo7.starlink.elo7starlinkprobe.dto.ProbeDTO;
import elo7.starlink.elo7starlinkprobe.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class ProbeController {

    @Autowired
    private ProbeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ProbeDTO request) {
        this.service.save(request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProbeDTO find(@PathVariable Long id) throws Exception {
        return this.service.find(id);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ProbeDTO> findAll() {
        return this.service.findAll();
    }
}
