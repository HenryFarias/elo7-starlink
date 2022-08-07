package br.com.elo7.starlink.controller.planet;

import br.com.elo7.starlink.domains.planet.PlanetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Planet")
public interface PlanetControllerDocument {

    @Operation(summary = "Save planet")
    void save(PlanetDTO request);

    @Operation(summary = "Find one planet by id")
    PlanetDTO find(Long id);

    @Operation(summary = "Find all planets")
    List<PlanetDTO> findAll();
}
