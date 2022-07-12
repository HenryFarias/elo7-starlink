package br.com.elo7.starlink.domains.user.validations;

import br.com.elo7.starlink.domains.mock.ConstraintValidatorContextMock;
import br.com.elo7.starlink.domains.user.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAlreadyExistsTest {

    @InjectMocks
    private UserAlreadyExists userAlreadyExists;

    @Mock
    private UserService userService;

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_email_is_valid_success() {
        var email = generator.nextObject(String.class);
        when(userService.existsByEmail(email))
                .thenReturn(false);
        var isValid = userAlreadyExists.isValid(email, new ConstraintValidatorContextMock());
        assertTrue(isValid);
    }

    @Test
    public void should_email_is_invalid_success() {
        var email = generator.nextObject(String.class);
        when(userService.existsByEmail(email))
                .thenReturn(true);
        var isValid = userAlreadyExists.isValid(email, new ConstraintValidatorContextMock());
        assertFalse(isValid);
    }
}
