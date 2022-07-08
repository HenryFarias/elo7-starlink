package br.com.elo7.starlink.domains.user.service;

import br.com.elo7.starlink.domains.user.entity.User;
import br.com.elo7.starlink.domains.user.repository.UserRepository;
import br.com.elo7.starlink.domains.user.entity.User;
import br.com.elo7.starlink.domains.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void save(User user) {
        this.repository.save(user);
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }
}