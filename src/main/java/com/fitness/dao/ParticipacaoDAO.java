package com.fitness.dao;

import com.fitness.model.Participacao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * DAO = Data Access Object (acesso a API)
 *
 * Somente chamadas HTTP e conversao JSON -> {@link Participacao}.
 * O usuario e sempre o do token (a API identifica pelo Bearer).
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class ParticipacaoDAO extends ApiDAO {

    public ParticipacaoDAO() {
        super();
    }

    // GET /progressos?desafioId= devolve a participacao do usuario logado (404 se nao participa)
    public Participacao buscarPorDesafio(Long desafioId) {
        try {
            HttpResponse<String> response = this.api.get("/progressos?desafioId=" + desafioId);
            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                return this.gson.fromJson(json.get("participacao"), Participacao.class);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar participacao na API.", e);
        }
    }

    public boolean inserir(Long desafioId) {
        try {
            String jsonEnvio = this.gson.toJson(Map.of("desafio_id", desafioId));
            HttpResponse<String> response = this.api.post("/participacoes", jsonEnvio);
            return response.statusCode() == 201;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao participar do desafio na API.", e);
        }
    }
}
