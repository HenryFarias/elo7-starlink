package br.com.elo7.starlink.domains.user.controller;

import br.com.elo7.starlink.domains.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User")
public interface UserControllerDocument {

    @Operation(summary = "Save user")
    void save(UserDTO request);

    @Operation(summary = "Find all users")
    List<UserDTO> findAll();
}
