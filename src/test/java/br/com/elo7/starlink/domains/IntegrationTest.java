package br.com.elo7.starlink.domains;

import br.com.elo7.starlink.domains.mock.AuthenticationMock;
import br.com.elo7.starlink.security.service.TokenAuthenticationService;
import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @MockBean
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private WebApplicationContext context;

    protected final EasyRandom generator = new EasyRandom();

    protected MockMvc mockMvc;

    protected Gson gson = new Gson();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        when(tokenAuthenticationService.getAuthentication(any(), any()))
                .thenReturn(new AuthenticationMock());
    }

}
