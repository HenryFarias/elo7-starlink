package br.com.elo7.starlink.domains.user.service;

import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.infra.exception.ApplicationException;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    void save(UserDTO user);
    UserDTO findByEmail(String email) throws ApplicationException;
    boolean existsByEmail(String email);
}
