package br.com.elo7.sonda.candidato.probe.repository;

import elo7.starlink.elo7starlinkprobe.entity.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
