package br.com.elo7.sonda.candidato.planet.repository;

import br.com.elo7.sonda.candidato.planet.entity.StrangeObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrangeObjectRepository extends JpaRepository<StrangeObject, Long> {
}
