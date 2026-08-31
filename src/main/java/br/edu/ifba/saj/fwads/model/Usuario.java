package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

public class Usuario extends AbstractModel<UUID> {
    private String login;
    private String senha;
    private String email;

    public Usuario() {
    }

    public Usuario(String login, String senha, String email) {
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }
}
