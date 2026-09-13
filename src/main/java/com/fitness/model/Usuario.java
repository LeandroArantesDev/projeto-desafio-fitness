package com.fitness.model;

import java.time.LocalDateTime;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha_hash;
    private String avatar_url;
    private String status;
    private LocalDateTime ultimo_login;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha_hash;
    }

    public void setSenha(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public String getAvatar() {
        return avatar_url;
    }

    public void setAvatar(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimo_login;
    }

    public void setUltimoLogin(LocalDateTime ultimo_login) {
        this.ultimo_login = ultimo_login;
    }

    public LocalDateTime getCriadoEm() {
        return criado_em;
    }

    public void setCriadoEm(LocalDateTime criado_em) {
        this.criado_em = criado_em;
    }

    public LocalDateTime getAtualizado_em() {
        return atualizado_em;
    }

    public void setAtualizado_em(LocalDateTime atualizado_em) {
        this.atualizado_em = atualizado_em;
    }
}
