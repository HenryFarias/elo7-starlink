package br.com.elo7.starlink.e2e;

import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.user.dto.UserDTO;
import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static br.com.elo7.starlink.domains.probe.enumeration.Direction.N;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

        var probe = generator.nextObject(ProbeDTO.class);
        probe.setId(1L);
        probe.setX(1);
        probe.setY(2);

        var planet = generator.nextObject(PlanetDTO.class);
        planet.setId(1L);
        planet.setHeight(5);
        planet.setWidth(5);

        var command = generator.nextObject(CommandDTO.class);
        command.setCommands("LMLMLMLMM");
        command.setPlanetId(planet.getId());
        command.setDirection(N);

        var user = generator.nextObject(UserDTO.class);

        new MainFlow(mockMvc, generator, gson)
                .createAUser(user)
                .doLoginWithThisUserAndSaveToken(user)
                .thenCreateAPlanet(planet)
                .shouldFindThisPlanet(planet)
                .thenCreateAProbe(probe)
                .sendThisProbeToThisPlanet(probe, planet)
                .shouldFindThisPlanetWithOneObjectAndTheseCoordinates(planet, probe)
                .sendThisCommandToThisProbe(command, probe.getId())
                .thisProbeShouldBeWithTheseCoordinates(probe.getId(), expectedX, expectedY)
                .theObjectInThisPlanetShouldBeWithTheseCoordinates(planet.getId(), expectedX, expectedY);
    }

    @Test
    public void should_execute_main_flow_with_another_probe_on_way_error() throws Exception {
        var firstProbe = generator.nextObject(ProbeDTO.class);
        firstProbe.setId(1L);
        firstProbe.setX(0);
        firstProbe.setY(0);

        var secondProbe = generator.nextObject(ProbeDTO.class);
        secondProbe.setId(2L);
        secondProbe.setX(2);
        secondProbe.setY(1);

        var planet = generator.nextObject(PlanetDTO.class);
        planet.setId(1L);
        planet.setHeight(5);
        planet.setWidth(5);

        var command = generator.nextObject(CommandDTO.class);
        command.setCommands("MRMMM");
        command.setPlanetId(planet.getId());
        command.setDirection(N);

        var user = generator.nextObject(UserDTO.class);

        new MainFlow(mockMvc, generator, gson)
                .createAUser(user)
                .doLoginWithThisUserAndSaveToken(user)
                .thenCreateAPlanet(planet)
                .thenCreateAProbe(firstProbe)
                .thenCreateAProbe(secondProbe)
                .sendThisProbeToThisPlanet(firstProbe, planet)
                .sendThisProbeToThisPlanet(secondProbe, planet)
                .sendACommandToThisProbeAndReturnBadRequest(command, firstProbe.getId());
    }
}
