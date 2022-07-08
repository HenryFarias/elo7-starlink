package br.com.elo7.sonda.candidato.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserDTO {

    @JsonInclude
    private Long id;

    @JsonInclude
    private String name;

    @JsonInclude
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}