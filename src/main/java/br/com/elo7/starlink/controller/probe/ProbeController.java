package br.com.elo7.starlink.controller.probe;

import br.com.elo7.starlink.domains.area.AreaDTO;
import br.com.elo7.starlink.domains.command.CommandDTO;
import br.com.elo7.starlink.domains.object.Object;
import br.com.elo7.starlink.infra.repository.ObjectRepository;
import br.com.elo7.starlink.domains.probe.Probe;
import br.com.elo7.starlink.domains.probe.ProbeDTO;
import br.com.elo7.starlink.infra.repository.ProbeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/probe")
public class ProbeController implements ProbeControllerDocument {

    private final ProbeRepository repository;
    private final ModelMapper modelMapper;
    private final ObjectRepository objectRepository;

    @Autowired
    public ProbeController(ProbeRepository repository, ModelMapper modelMapper, ObjectRepository objectRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.objectRepository = objectRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid ProbeDTO request) {
        probe.save(request);
    }

    @PostMapping("/{id}/command")
    @ResponseStatus(HttpStatus.OK)
    public void sendCommand(@PathVariable Long id, @RequestBody @Valid CommandDTO request) {
        var object = new Object(objectRepository);
        var probe = new Probe(repository, modelMapper, );
        service.sendCommand(id, request);
    }

    @PostMapping("/{id}/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendToPlanet(@PathVariable Long id, @RequestBody @Valid AreaDTO request) {
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
