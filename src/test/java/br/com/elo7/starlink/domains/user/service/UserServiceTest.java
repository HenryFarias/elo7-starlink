package br.com.elo7.starlink.domains.user.service;

import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.domains.user.entity.User;
import br.com.elo7.starlink.domains.user.repository.UserRepository;
import br.com.elo7.starlink.exception.ApplicationException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service = new UserServiceImpl();

    @Mock
    private UserRepository repository;

    @Spy
    private ModelMapper modelMapper;

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_save_user_success() {
        service.save(generator.nextObject(UserDTO.class));
        verify(repository, times(1)).save(any());
    }

    @Test
    public void should_find_user_by_email_success() {
        var expectedUser = generator.nextObject(User.class);
        var email = generator.nextObject(String.class);
        expectedUser.setEmail(email);

        when(repository.findByEmail(email))
                .thenReturn(Optional.of(expectedUser));

        var user = service.findByEmail(email);

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getName(), user.getName());
        assertEquals(expectedUser.getPassword(), user.getPassword());
    }

    @Test
    public void should_find_user_by_email_dont_exists_error() {
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
                service.findByEmail(generator.nextObject(String.class))
        );
        assertEquals("User not found", appException.getMessage());
    }

    @Test
    public void should_find_all_users_success() {
        var usersExpected = Arrays.asList(
                generator.nextObject(User.class),
                generator.nextObject(User.class)
        );
        when(repository.findAll())
                .thenReturn(usersExpected);

        var users = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(users.size(), users.size());
        assertThat(users, contains(
                hasProperty("id", is(usersExpected.get(0).getId())),
                hasProperty("id", is(usersExpected.get(1).getId()))
        ));
    }

    @Test
    public void should_verify_user_exists_by_email_success() {
        var email = generator.nextObject(String.class);
        when(repository.existsByEmail(email))
                .thenReturn(true);
        boolean exists = service.existsByEmail(email);
        assertTrue(exists);
    }
}
