package br.com.elo7.starlink.domains.user.service;

import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.domains.user.entity.User;
import br.com.elo7.starlink.domains.user.repository.UserRepository;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO findByEmail(String email) throws ApplicationException {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException("User not found", HttpStatus.BAD_REQUEST));
        return modelMapper.map(user, UserDTO.class);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public void save(UserDTO userDTO) {
        this.repository.save(modelMapper.map(userDTO, User.class));
    }

    public List<UserDTO> findAll() {
        return this.repository
                .findAll()
                .stream()
                .map(planet -> modelMapper.map(planet, UserDTO.class))
                .collect(Collectors.toList());
    }
}