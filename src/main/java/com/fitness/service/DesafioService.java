package com.fitness.service;

import com.fitness.dao.DesafioDAO;
import com.fitness.model.Desafio;

import java.util.List;

/**
 * SERVICE de Desafio — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so chama a API.
 */
public class DesafioService {

    private final DesafioDAO desafioDAO;

    public DesafioService() {
        this.desafioDAO = new DesafioDAO();
    }

    public List<Desafio> listar() {
        return this.desafioDAO.listarTodosDesafios();
    }

    // a API devolve todos os desafios; aqui filtra os criados pelo usuario
    public List<Desafio> listarCriadosPor(Long usuarioId) {
        return this.listar().stream()
                .filter(d -> usuarioId != null && usuarioId.equals(d.getCriadorId()))
                .toList();
    }

    // a API ja marca "participando" para o usuario do token
    public List<Desafio> listarParticipando() {
        return this.listar().stream()
                .filter(Desafio::isParticipando)
                .toList();
    }

    public Desafio buscarPorId(Long id) {
        return this.desafioDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id -> cadastro novo
     * - com id -> alteracao (desafio precisa existir)
     */
    public void salvar(Desafio desafio) {
        if (desafio == null) {
            throw new IllegalArgumentException("Desafio e obrigatorio.");
        }

        this.prepararDados(desafio);
        this.validarCamposObrigatorios(desafio);

        if (desafio.getId() == null) {
            if (!this.desafioDAO.inserir(desafio)) {
                throw new IllegalArgumentException("Erro ao cadastrar desafio.");
            }
            return;
        }

        if (this.desafioDAO.buscarPorId(desafio.getId()) == null) {
            throw new IllegalArgumentException("Desafio nao encontrado para alteracao.");
        }
        if (!this.desafioDAO.alterar(desafio)) {
            throw new IllegalArgumentException("Erro ao alterar desafio.");
        }
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - desafio precisa existir
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.desafioDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Desafio nao encontrado.");
        }
        if (!this.desafioDAO.deletar(id)) {
            throw new IllegalArgumentException("Erro ao excluir desafio.");
        }
    }

    private void prepararDados(Desafio desafio) {
        desafio.setNome(this.normalizar(desafio.getNome()));
        desafio.setDescricao(this.normalizar(desafio.getDescricao()));
        desafio.setCategoria(this.normalizar(desafio.getCategoria()));
        desafio.setTipoMeta(this.normalizar(desafio.getTipoMeta()));
        desafio.setStatus(this.normalizar(desafio.getStatus()));

        if (desafio.getStatus() == null) {
            desafio.setStatus("ativo");
        }
    }

    private void validarCamposObrigatorios(Desafio desafio) {
        if (desafio.getNome() == null) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }
        if (desafio.getTipoMeta() == null) {
            throw new IllegalArgumentException("Meta e obrigatorio.");
        }
        if (desafio.getDataInicio() == null) {
            throw new IllegalArgumentException("Data Inicial e obrigatoria.");
        }
        if (desafio.getDataFim() == null) {
            throw new IllegalArgumentException("Data Final e obrigatoria.");
        }
        if (desafio.getMetaTotal() == null) {
            throw new IllegalArgumentException("Meta Total e obrigatoria.");
        }
        if (desafio.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria e obrigatoria.");
        }

        if (!desafio.getDataFim().isAfter(desafio.getDataInicio())) {
            throw new IllegalArgumentException("Data final nao pode ser inferior a data inicial");
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
