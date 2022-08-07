package br.com.elo7.starlink.e2e;

import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.area.AreaDTO;
import br.com.elo7.starlink.domains.command.CommandDTO;
import br.com.elo7.starlink.domains.probe.ProbeDTO;
import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.infra.security.dto.LoginDTO;
import br.com.elo7.starlink.infra.security.dto.UserAuthDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jeasy.random.EasyRandom;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static br.com.elo7.starlink.domains.direction.Direction.N;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MainFlow {

    protected final long PLANET_ID = 1L;
    protected final long PROBE_ID = 1L;

    private final MockMvc mockMvc;
    private final EasyRandom generator;
    private final Gson gson;

    private UserDTO user;
    private PlanetDTO planet;
    private ProbeDTO probe;
    private String token;

    public MainFlow(MockMvc mockMvc, EasyRandom generator, Gson gson) {
        this.mockMvc = mockMvc;
        this.generator = generator;
        this.gson = gson;
    }

    public MainFlow createAUser() throws Exception {
        user = generator.nextObject(UserDTO.class);
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow doLoginWithThisUserAndSaveToken() throws Exception {
        var result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new LoginDTO(user.getEmail(), user.getPassword()))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        token = "Bearer" + gson.fromJson(result, UserAuthDTO.class).getToken();
        assertNotNull(token);
        assertNotEquals("", token);
        return this;
    }

    public MainFlow shouldFindAUserWithThisTokenAndExistsOneUser() throws Exception {
        var result = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var listType = new TypeToken<ArrayList<UserDTO>>(){}.getType();
        List<UserDTO> response = gson.fromJson(result, listType);

        assertNotNull(response);
        assertEquals(1, response.size());
        return this;
    }

    public MainFlow thenCreateAPlanetWithThisSize(int planetWidth, int planetHeight) throws Exception {
        planet = generator.nextObject(PlanetDTO.class);
        planet.setId(PLANET_ID);
        planet.setHeight(planetHeight);
        planet.setWidth(planetWidth);

        mockMvc.perform(post("/planet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(planet)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow shouldFindThisPlanet() throws Exception {
        var result = mockMvc.perform(get("/planet/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, PlanetDTO.class);
        assertEquals(planet.getHeight(), response.getHeight());
        assertEquals(planet.getWidth(), response.getWidth());
        return this;
    }

    public MainFlow thenCreateAProbe() throws Exception {
        probe = generator.nextObject(ProbeDTO.class);
        probe.setId(PROBE_ID);
        mockMvc.perform(post("/probe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(probe)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow sendThisProbeToThisPlanetWithThisCoordinates(int initialX, int initialY) throws Exception {
        var area = generator.nextObject(AreaDTO.class);
        area.setPlanetId(planet.getId());
        area.setX(initialX);
        area.setY(initialY);
        mockMvc.perform(post("/probe/" + PROBE_ID + "/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(area)))
                .andExpect(status().isOk());
        return this;
    }

    public MainFlow shouldFindThisPlanetWithOneObjectAndTheseCoordinates(int initialX, int initialY) throws Exception {
        var result = mockMvc.perform(get("/planet/" + planet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, PlanetDTO.class);
        assertEquals(probe.getId().toString(), response.getObjects().get(0).getObjectId());
        assertEquals(initialX, response.getObjects().get(0).getX());
        assertEquals(initialY, response.getObjects().get(0).getY());
        return this;
    }

    public MainFlow sendACommandToThisProbe(String command) throws Exception {
        var commandDTO = generator.nextObject(CommandDTO.class);
        commandDTO.setCommands(command);
        commandDTO.setPlanetId(planet.getId());
        commandDTO.setDirection(N);
        mockMvc.perform(post("/probe/" + probe.getId() +  "/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(commandDTO)))
                .andExpect(status().isOk());
        return this;
    }

    public MainFlow thisProbeShouldBeWithTheseCoordinates(int expectedX, int expectedY) throws Exception {
        var result = mockMvc.perform(get("/probe/" + probe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, ProbeDTO.class);
        assertEquals(expectedY, response.getY());
        assertEquals(expectedX, response.getX());
        return this;
    }

    public void theObjectInThisPlanetShouldBeWithTheseCoordinates(int expectedX, int expectedY) throws Exception {
        var result = mockMvc.perform(get("/planet/" + planet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, PlanetDTO.class);
        assertEquals(expectedY, response.getObjects().get(0).getY());
        assertEquals(expectedX, response.getObjects().get(0).getX());
    }
}