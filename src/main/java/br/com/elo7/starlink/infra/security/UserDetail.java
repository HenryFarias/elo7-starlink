package br.com.elo7.starlink.infra.security;

import br.com.elo7.starlink.domains.user.service.UserService;
import br.com.elo7.starlink.infra.security.dto.UserAuthDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetail implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return modelMapper.map(userService.findByEmail(email), UserAuthDTO.class);
    }
}