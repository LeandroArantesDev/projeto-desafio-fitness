package com.fitness.dao;

import com.fitness.config.ApiSingleton;
import com.google.gson.Gson;

/**
 * Classe base dos DAOs que usam MySQL.
 *
 * Guarda referencia ao {@link MysqlSingleton} (uma conexao reutilizada).
 * Os DAOs filhos usam executar() para SELECT e executarUpdate() para INSERT/UPDATE/DELETE.
 */
public class ApiDAO {

    protected final ApiSingleton api;
    protected final Gson gson;
    public ApiDAO() {
        this.api = ApiSingleton.getInstance();
        this.gson = this.api.getGson();
    }
}
