package com.fitness.dao;

import com.fitness.model.Progresso;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object (acesso a API)
 *
 * Somente chamadas HTTP para /api/progressos e conversao JSON -> {@link Progresso}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class ProgressoDAO extends ApiDAO {

    public ProgressoDAO() {
        super();
    }

    // historico do usuario logado no desafio (lista vazia se nao participa)
    public List<Progresso> listarPorDesafio(Long desafioId) {
        try {
            HttpResponse<String> response = this.api.get("/progressos?desafioId=" + desafioId);
            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                Type tipoLista = new TypeToken<List<Progresso>>(){}.getType();
                return this.gson.fromJson(json.get("historico"), tipoLista);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar progresso na API.", e);
        }
    }

    public boolean inserir(Progresso progresso) {
        try {
            HttpResponse<String> response = this.api.post("/progressos", this.gson.toJson(progresso));
            return response.statusCode() == 201;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar progresso na API.", e);
        }
    }
}
