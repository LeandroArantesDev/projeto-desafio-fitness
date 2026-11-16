package com.fitness.dao;

import com.fitness.model.Usuario;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Usuario}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class UsuarioDAO extends ApiDAO {

    public UsuarioDAO() {
        super();
    }

    public Usuario logar(String email, String senha) {
        try {
            String jsonEnvio = this.gson.toJson(Map.of("email", email, "senha", senha));
            HttpResponse<String> response = this.api.post("/login", jsonEnvio);

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setNome(json.get("nome").getAsString());
                usuario.setTipo(json.get("tipo").getAsString());
                usuario.setToken(json.get("token").getAsString());
                return usuario;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar na API.", e);
        }
    }

    public boolean registrar(String nome, String email, String senha) {
        try {
            String jsonEnvio = this.gson.toJson(Map.of("nome", nome, "email", email, "senha", senha));
            HttpResponse<String> response = this.api.post("/register", jsonEnvio);
            return response.statusCode() == 201 || response.statusCode() == 200;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar usuário na API.", e);
        }
    }

    public List<Usuario> listarTodos() {
        try {
            HttpResponse<String> response = this.api.get("/usuarios");
            if (response.statusCode() == 200) {
                Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
                return this.gson.fromJson(response.body(), tipoLista);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários na API.", e);
        }
    }

    public Usuario buscarPorId(Long id) {
        try {
            HttpResponse<String> response = this.api.get("/usuarios/" + id);
            if (response.statusCode() == 200) {
                return this.gson.fromJson(response.body(), Usuario.class);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário por ID na API.", e);
        }
    }

    public void inserir(Usuario usuario) {
        try {
            String json = this.gson.toJson(usuario);
            HttpResponse<String> response = this.api.post("/usuarios", json);
            if (response.statusCode() != 201 && response.statusCode() != 200) {
                throw new RuntimeException("Erro da API: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir usuário na API.", e);
        }
    }

    public void alterar(Usuario usuario) {
        try {
            String json = this.gson.toJson(usuario);
            HttpResponse<String> response = this.api.put("/usuarios/" + usuario.getId(), json);
            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro da API: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar usuário na API.", e);
        }
    }

    public void deletar(Long id) {
        try {
            HttpResponse<String> response = this.api.delete("/usuarios/" + id);
            if (response.statusCode() != 204 && response.statusCode() != 200) {
                throw new RuntimeException("Erro da API: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário na API.", e);
        }
    }
}
