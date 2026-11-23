package com.fitness.dao;

import com.fitness.config.ApiSingleton;
import com.google.gson.Gson;

/**
 * Classe base dos DAOs que usam a API.
 *
 * Guarda referencia ao {@link ApiSingleton} (cliente HTTP com o token do usuario logado).
 * Os DAOs filhos usam api.get/post/put/delete e o gson para converter o JSON.
 */
public class ApiDAO {

    protected final ApiSingleton api;
    protected final Gson gson;
    public ApiDAO() {
        this.api = ApiSingleton.getInstance();
        this.gson = this.api.getGson();
    }
}
