package br.com.elo7.starlink.infra.repository.probe;

import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.domains.probe.Probe;
import br.com.elo7.starlink.domains.probe.ProbeRepository;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProbeRepositoryImpl implements ProbeRepository {

    @Autowired
    private ProbeRepositorySpring repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Probe probe) {
        var entity = modelMapper.map(probe, ProbeEntity.class);
        entity.setArea(probe.getArea().toString());
        repository.save(entity);
    }

    @Override
    public List<Probe> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, Probe.class))
                .collect(Collectors.toList());
    }

    @Override
    public Probe findById(Long id) {
        return this.repository
                .findById(id)
                .map(entity -> {
                    var probe = modelMapper.map(entity, Probe.class);
                    probe.setArea(new Area(entity.getArea()));
                    return probe;
                })
                .orElseThrow(() -> new ApplicationException("Probe not found", HttpStatus.BAD_REQUEST));
    }
}
