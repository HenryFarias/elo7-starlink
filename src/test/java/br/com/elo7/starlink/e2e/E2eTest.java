package br.com.elo7.starlink.e2e;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2eTest {

    protected final EasyRandom generator = new EasyRandom();

    protected MockMvc mockMvc;

    protected final Gson gson = new Gson();

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_execute_main_flow_success() throws Exception {
        var expectedX = 1;
        var expectedY = 3;
        var initialX = 1;
        var initialY = 2;
        var planetWidth = 5;
        var planetHeight = 5;
        var command = "LMLMLMLMM";

        new MainFlow(mockMvc, generator, gson)
                .createAUser()
                .doLoginWithThisUserAndSaveToken()
                .shouldFindAUserWithThisTokenAndExistsOneUser()
                .thenCreateAPlanetWithThisSize(planetWidth, planetHeight)
                .shouldFindThisPlanet()
                .thenCreateAProbe()
                .sendThisProbeToThisPlanetWithThisCoordinates(initialX, initialY)
                .shouldFindThisPlanetWithOneObjectAndTheseCoordinates(initialX, initialY)
                .sendACommandToThisProbe(command)
                .thisProbeShouldBeWithTheseCoordinates(expectedX, expectedY)
                .theObjectInThisPlanetShouldBeWithTheseCoordinates(expectedX, expectedY);
    }
}
