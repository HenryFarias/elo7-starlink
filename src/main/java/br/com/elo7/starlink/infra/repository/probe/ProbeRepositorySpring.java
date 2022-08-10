package br.com.elo7.starlink.infra.repository.probe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepositorySpring extends JpaRepository<ProbeEntity, Long> {
}
