package br.com.elo7.starlink.domains.probe;

import br.com.elo7.starlink.infra.entity.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
