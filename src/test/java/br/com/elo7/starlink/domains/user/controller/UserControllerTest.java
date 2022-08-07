package br.com.elo7.starlink.domains.user.controller;

import br.com.elo7.starlink.domains.IntegrationTest;
import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.domains.user.service.UserService;
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

public class UserControllerTest extends IntegrationTest {

    @MockBean
    private UserService service;

    @Test
    public void should_save_user_success() throws Exception {
        var userDTO = generator.nextObject(UserDTO.class);
        mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(userDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void should_save_user_validation_error() throws Exception {
        var request = generator.nextObject(UserDTO.class);
        request.setName(null);
        request.setPassword(null);
        request.setEmail(null);

        var result = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var response = gson.fromJson(result, ErrorInfo.class);

        assertTrue(response.getMessage().contains("email=Email is required"));
        assertTrue(response.getMessage().contains("name=Name is required"));
        assertTrue(response.getMessage().contains("password=Password is required"));
    }

    @Test
    public void should_find_all_users_success() throws Exception {
        var usersExpected = Arrays.asList(
            generator.nextObject(UserDTO.class),
            generator.nextObject(UserDTO.class)
        );

        when(service.findAll())
                .thenReturn(usersExpected);

        var result = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var listType = new TypeToken<ArrayList<UserDTO>>(){}.getType();
        List<UserDTO> response = gson.fromJson(result, listType);

        assertEquals(response.size(), usersExpected.size());
    }

    @Test
    public void should_find_all_users_return_null_success() throws Exception {
        when(service.findAll())
                .thenReturn(null);

        var result = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("", result);
    }
}