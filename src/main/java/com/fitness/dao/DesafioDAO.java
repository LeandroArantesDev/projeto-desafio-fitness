package com.fitness.dao;

import com.fitness.model.Desafio;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object (acesso a API)
 *
 * Somente chamadas HTTP para /api/desafios e conversao JSON -> {@link Desafio}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class DesafioDAO extends ApiDAO {

    public DesafioDAO() {
        super();
    }

    // a API ja devolve participando, progresso_atual e progresso_percentual do usuario do token
    public List<Desafio> listarTodosDesafios() {
        try {
            HttpResponse<String> response = this.api.get("/desafios");
            if (response.statusCode() == 200) {
                Type tipoLista = new TypeToken<List<Desafio>>(){}.getType();
                return this.gson.fromJson(response.body(), tipoLista);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar desafios na API.", e);
        }
    }

    public Desafio buscarPorId(Long id) {
        try {
            HttpResponse<String> response = this.api.get("/desafios/" + id);
            if (response.statusCode() == 200) {
                return this.gson.fromJson(response.body(), Desafio.class);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar desafio por ID na API.", e);
        }
    }

    public boolean inserir(Desafio desafio) {
        try {
            HttpResponse<String> response = this.api.post("/desafios", this.gson.toJson(desafio));
            return response.statusCode() == 201;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir desafio na API.", e);
        }
    }

    public boolean alterar(Desafio desafio) {
        try {
            HttpResponse<String> response = this.api.put("/desafios/" + desafio.getId(), this.gson.toJson(desafio));
            return response.statusCode() == 200;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar desafio na API.", e);
        }
    }

    public boolean deletar(Long id) {
        try {
            HttpResponse<String> response = this.api.delete("/desafios/" + id);
            return response.statusCode() == 204;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar desafio na API.", e);
        }
    }
}
