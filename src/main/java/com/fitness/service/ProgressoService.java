package com.fitness.service;

import com.fitness.dao.ProgressoDAO;
import com.fitness.model.Progresso;

import java.util.List;

/**
 * SERVICE de Progresso — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so chama a API.
 */
public class ProgressoService {

    private final ProgressoDAO progressoDAO;

    public ProgressoService() {
        this.progressoDAO = new ProgressoDAO();
    }

    // historico do usuario logado no desafio
    public List<Progresso> listarPorDesafio(Long desafioId) {
        return this.progressoDAO.listarPorDesafio(desafioId);
    }

    public void registrar(Progresso progresso) {
        if (progresso == null) {
            throw new IllegalArgumentException("Progresso e obrigatorio.");
        }

        this.validarCamposObrigatorios(progresso);

        if (!this.progressoDAO.inserir(progresso)) {
            throw new IllegalArgumentException("Erro ao registrar progresso.");
        }
    }

    private void validarCamposObrigatorios(Progresso progresso) {
        if (progresso.getParticipacaoId() == null) {
            throw new IllegalArgumentException("Participacao e obrigatoria.");
        }
        if (progresso.getValorRegistrado() == null || progresso.getValorRegistrado() <= 0) {
            throw new IllegalArgumentException("Valor registrado deve ser maior que zero.");
        }
        if (progresso.getDataRegistro() == null) {
            throw new IllegalArgumentException("Data de registro e obrigatoria.");
        }
    }

    public Double totalRegistrado(List<Progresso> historico) {
        Double total = 0.0;
        for (Progresso progresso : historico) {
            total += progresso.getValorRegistrado();
        }
        return total;
    }
}
