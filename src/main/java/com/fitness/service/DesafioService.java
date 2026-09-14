package com.fitness.service;

import com.fitness.dao.DesafioDAO;
import com.fitness.model.Desafio;

import java.util.List;

/**
 * SERVICE de Desafio — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so executa SQL.
 */
public class DesafioService {

    private final DesafioDAO desafioDAO;

    public DesafioService() {
        this.desafioDAO = new DesafioDAO();
    }

    public List<Desafio> listar() {
        return this.desafioDAO.listarTodosDesafios();
    }

    public List<Desafio> listarCriadosPor(Long usuarioId) {
        return this.desafioDAO.listarPorCriador(usuarioId);
    }

    public List<Desafio> listarParticipando(Long usuarioId) {
        return this.desafioDAO.listarPorParticipante(usuarioId);
    }
}
