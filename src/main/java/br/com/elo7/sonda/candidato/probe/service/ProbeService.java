package br.com.elo7.sonda.candidato.probe.service;

import elo7.starlink.elo7starlinkprobe.dto.ProbeDTO;
import elo7.starlink.elo7starlinkprobe.entity.Probe;
import elo7.starlink.elo7starlinkprobe.repository.ProbeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbeService {

    @Autowired
    private ProbeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public void save(ProbeDTO probeDTO) {
        this.repository.save(modelMapper.map(probeDTO, Probe.class));
    }

    public ProbeDTO find(Long id) throws Exception {
        return this.repository
                .findById(id)
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .orElseThrow(() -> new Exception("Probe don't exists"));
    }

    public List<ProbeDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(probe -> modelMapper.map(probe, ProbeDTO.class))
                .collect(Collectors.toList());
    }
}
