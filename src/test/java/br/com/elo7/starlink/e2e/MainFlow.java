package br.com.elo7.starlink.e2e;

import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.probe.dto.AreaDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.security.dto.LoginDTO;
import br.com.elo7.starlink.security.dto.UserAuthDTO;
import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MainFlow {

    private final MockMvc mockMvc;
    private final EasyRandom generator;
    private final Gson gson;
    private String token;

    public MainFlow(MockMvc mockMvc, EasyRandom generator, Gson gson) {
        this.mockMvc = mockMvc;
        this.generator = generator;
        this.gson = gson;
    }

    public MainFlow createAUser(UserDTO user) throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow doLoginWithThisUserAndSaveToken(UserDTO user) throws Exception {
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

    public MainFlow thenCreateAPlanet(PlanetDTO planet) throws Exception {
        mockMvc.perform(post("/planet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(planet)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow shouldFindThisPlanet(PlanetDTO planet) throws Exception {
        var result = mockMvc.perform(get("/planet/" + planet.getId())
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

    public MainFlow thenCreateAProbe(ProbeDTO probe) throws Exception {
        mockMvc.perform(post("/probe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(probe)))
                .andExpect(status().isCreated());
        return this;
    }

    public MainFlow sendThisProbeToThisPlanet(ProbeDTO probe, PlanetDTO planet) throws Exception {
        var area = generator.nextObject(AreaDTO.class);
        area.setPlanetId(planet.getId());
        area.setX(probe.getX());
        area.setY(probe.getY());
        mockMvc.perform(post("/probe/" + probe.getId() + "/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(area)))
                .andExpect(status().isOk());
        return this;
    }

    public MainFlow shouldFindThisPlanetWithOneObjectAndTheseCoordinates(PlanetDTO planet, ProbeDTO probe) throws Exception {
        var result = mockMvc.perform(get("/planet/" + planet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, PlanetDTO.class);
        assertEquals(probe.getId().toString(), response.getObjects().get(0).getObjectId());
        assertEquals(probe.getX(), response.getObjects().get(0).getX());
        assertEquals(probe.getY(), response.getObjects().get(0).getY());
        return this;
    }

    public MainFlow sendThisCommandToThisProbe(CommandDTO command, long probeId) throws Exception {
        mockMvc.perform(post("/probe/" + probeId +  "/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(command)))
                .andExpect(status().isOk());
        return this;
    }

    public MainFlow sendACommandToThisProbeAndReturnBadRequest(CommandDTO command, long probeId) throws Exception {
        var result = mockMvc.perform(post("/probe/" + probeId +  "/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(gson.toJson(command)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(result.contains("There is already an object in this area"));
        return this;
    }

    public MainFlow thisProbeShouldBeWithTheseCoordinates(long probeId, int expectedX, int expectedY) throws Exception {
        var result = mockMvc.perform(get("/probe/" + probeId)
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

    public void theObjectInThisPlanetShouldBeWithTheseCoordinates(long planetId, int expectedX, int expectedY) throws Exception {
        var result = mockMvc.perform(get("/planet/" + planetId)
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