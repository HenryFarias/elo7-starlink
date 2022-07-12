package br.com.elo7.starlink.domains.planet.controller;

import br.com.elo7.starlink.domains.IntegrationTest;
import br.com.elo7.starlink.domains.planet.dto.PlanetDTO;
import br.com.elo7.starlink.domains.planet.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanetControllerTest extends IntegrationTest {

    @MockBean
    private PlanetRepository planetRepository;

    @Test
    public void should_save_planet_success() throws Exception {
        var planet = generator.nextObject(PlanetDTO.class);
        mvc.perform(post("/planet", planet)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_find_all_planets_success() throws Exception {
        mvc.perform(get("/planet")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}