package br.com.elo7.starlink.domains.user.service;

import br.com.elo7.starlink.domains.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    void save(UserDTO user);
    UserDTO findByEmail(String email);
}
