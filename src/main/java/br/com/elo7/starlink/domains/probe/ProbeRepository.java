package br.com.elo7.starlink.domains.probe;

import java.util.List;

public interface ProbeRepository {
    Probe findById(Long id);
    void save(Probe probe);
    List<Probe> findAll();
}
