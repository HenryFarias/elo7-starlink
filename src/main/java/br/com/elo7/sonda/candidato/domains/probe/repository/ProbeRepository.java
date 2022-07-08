package br.com.elo7.sonda.candidato.domains.probe.repository;

import br.com.elo7.sonda.candidato.domains.probe.entity.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
