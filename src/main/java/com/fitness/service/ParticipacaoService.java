package com.fitness.service;

import com.fitness.dao.DesafioDAO;
import com.fitness.dao.ParticipacaoDAO;
import com.fitness.model.Participacao;

/**
 * SERVICE de Participacao — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so chama a API.
 */
public class ParticipacaoService {

    private final ParticipacaoDAO participacaoDAO;
    private final DesafioDAO desafioDAO;

    public ParticipacaoService() {
        this.participacaoDAO = new ParticipacaoDAO();
        this.desafioDAO = new DesafioDAO();
    }

    /**
     * Regra de participacao:
     * - desafio e usuario obrigatorios
     * - desafio precisa existir
     * - usuario nao pode participar do mesmo desafio duas vezes
     */
    public void participar(Long usuarioId, Long desafioId) {
        if (usuarioId == null || desafioId == null) {
            throw new IllegalArgumentException("Usuario e desafio sao obrigatorios.");
        }

        if (this.desafioDAO.buscarPorId(desafioId) == null) {
            throw new IllegalArgumentException("Desafio nao encontrado.");
        }

        if (this.participacaoDAO.buscarPorDesafio(desafioId) != null) {
            throw new IllegalArgumentException("Voce ja participa deste desafio.");
        }

        if (!this.participacaoDAO.inserir(desafioId)) {
            throw new IllegalArgumentException("Erro ao participar do desafio.");
        }
    }

    // participacao do usuario logado no desafio (null se nao participa)
    public Participacao buscarPorDesafio(Long desafioId) {
        return this.participacaoDAO.buscarPorDesafio(desafioId);
    }

}
