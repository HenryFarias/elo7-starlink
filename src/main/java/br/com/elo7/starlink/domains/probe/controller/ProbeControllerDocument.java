package br.com.elo7.starlink.domains.probe.controller;

import br.com.elo7.starlink.domains.probe.dto.AreaDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Probe")
public interface ProbeControllerDocument {

    @Operation(summary = "Save probe")
    void save(ProbeDTO request);

    @Operation(summary = "Find one probe by id")
    ProbeDTO find(Long id);

    @Operation(summary = "Find all probes")
    List<ProbeDTO> findAll();

    @Operation(summary = "Send a command to specific probe")
    void sendCommand(Long id, CommandDTO request);

    @Operation(summary = "Send a specific probe to specific planet")
    void sendToPlanet(Long id, AreaDTO request);
}
