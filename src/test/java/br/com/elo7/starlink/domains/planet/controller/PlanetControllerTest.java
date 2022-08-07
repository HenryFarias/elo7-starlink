package br.com.elo7.starlink.domains.planet.controller;

import br.com.elo7.starlink.domains.IntegrationTest;
import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.planet.PlanetInterface;
import br.com.elo7.starlink.infra.exception.ErrorInfo;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanetControllerTest extends IntegrationTest {

    @MockBean
    private PlanetInterface service;

    @Test
    public void should_save_planet_success() throws Exception {
        var planetDTO = generator.nextObject(PlanetDTO.class);
        mockMvc.perform(post("/planet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(planetDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void should_save_planet_with_description_null_success() throws Exception {
        var planetDTO = generator.nextObject(PlanetDTO.class);
        planetDTO.setDescription(null);
        mockMvc.perform(post("/planet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(planetDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_save_planet_validation_error() throws Exception {
        var request = generator.nextObject(PlanetDTO.class);
        request.setName(null);
        request.setWidth(null);
        request.setHeight(null);

        var result = mockMvc.perform(post("/planet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, ErrorInfo.class);

        assertTrue(response.getMessage().contains("width=Width is required"));
        assertTrue(response.getMessage().contains("name=Name is required"));
        assertTrue(response.getMessage().contains("height=Height is required"));
    }

    @Test
    public void should_find_all_planets_success() throws Exception {
        var planetsExpected = Arrays.asList(
            generator.nextObject(PlanetDTO.class),
            generator.nextObject(PlanetDTO.class)
        );

        when(service.findAll())
            .thenReturn(planetsExpected);

        var result = mockMvc.perform(get("/planet")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        var listType = new TypeToken<ArrayList<PlanetDTO>>(){}.getType();
        List<PlanetDTO> response = gson.fromJson(result, listType);

        assertEquals(response.size(), planetsExpected.size());
    }

    @Test
    public void should_find_all_planets_return_null_success() throws Exception {
        when(service.findAll())
                .thenReturn(null);

        var result = mockMvc.perform(get("/planet")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("", result);
    }

    @Test
    public void should_find_planet_by_id_success() throws Exception {
        var expectedPlanet = generator.nextObject(PlanetDTO.class);
        var planetId = 1L;

        when(service.find(planetId))
                .thenReturn(expectedPlanet);

        var result = mockMvc.perform(get("/planet/" + planetId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, PlanetDTO.class);

        assertEquals(expectedPlanet.getId(), response.getId());
        assertEquals(expectedPlanet.getDescription(), response.getDescription());
        assertEquals(expectedPlanet.getName(), response.getName());
        assertEquals(expectedPlanet.getHeight(), response.getHeight());
        assertEquals(expectedPlanet.getWidth(), response.getWidth());
        assertEquals(expectedPlanet.getObjects().size(), response.getObjects().size());
        assertEquals(expectedPlanet.getObjects().get(0).getId(), response.getObjects().get(0).getId());
    }
}