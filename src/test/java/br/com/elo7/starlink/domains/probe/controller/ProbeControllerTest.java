package br.com.elo7.starlink.domains.probe.controller;

import br.com.elo7.starlink.domains.IntegrationTest;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.service.ProbeService;
import br.com.elo7.starlink.exception.ErrorInfo;
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

public class ProbeControllerTest extends IntegrationTest {

    @MockBean
    private ProbeService service;

    @Test
    public void should_save_probe_success() throws Exception {
        var probeDTO = generator.nextObject(ProbeDTO.class);
        mockMvc.perform(post("/probe")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(probeDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void should_save_probe_with_description_null_success() throws Exception {
        var probeDTO = generator.nextObject(ProbeDTO.class);
        probeDTO.setDescription(null);
        mockMvc.perform(post("/probe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(probeDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_save_probe_validation_error() throws Exception {
        var request = generator.nextObject(ProbeDTO.class);
        request.setName(null);

        var result = mockMvc.perform(post("/probe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, ErrorInfo.class);

        assertTrue(response.getMessage().contains("name=Name is required"));
    }

    @Test
    public void should_find_all_probes_success() throws Exception {
        var probesExpected = Arrays.asList(
            generator.nextObject(ProbeDTO.class),
            generator.nextObject(ProbeDTO.class)
        );

        when(service.findAll())
                .thenReturn(probesExpected);

        var result = mockMvc.perform(get("/probe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var listType = new TypeToken<ArrayList<ProbeDTO>>(){}.getType();
        List<ProbeDTO> response = gson.fromJson(result, listType);

        assertEquals(response.size(), probesExpected.size());
    }

    @Test
    public void should_find_all_probes_return_null_success() throws Exception {
        when(service.findAll())
                .thenReturn(null);

        var result = mockMvc.perform(get("/probe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("", result);
    }

    @Test
    public void should_find_probe_by_id_success() throws Exception {
        var expectedProbe = generator.nextObject(ProbeDTO.class);
        var probeId = 1L;

        when(service.find(probeId))
                .thenReturn(expectedProbe);

        var result = mockMvc.perform(get("/probe/" + probeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, ProbeDTO.class);

        assertEquals(expectedProbe.getId(), response.getId());
        assertEquals(expectedProbe.getDescription(), response.getDescription());
        assertEquals(expectedProbe.getName(), response.getName());
        assertEquals(expectedProbe.getDirection(), response.getDirection());
        assertEquals(expectedProbe.getX(), response.getX());
        assertEquals(expectedProbe.getY(), response.getY());
    }
}