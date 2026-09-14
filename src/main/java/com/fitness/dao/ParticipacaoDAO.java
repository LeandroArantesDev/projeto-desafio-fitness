package com.fitness.dao;

import com.fitness.model.Participacao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Participacao}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class ParticipacaoDAO extends MysqlDAO {

    public ParticipacaoDAO() {
        super();
    }

    public boolean existeParticipacao(Long usuarioId, Long desafioId) {
        String sql =
                "SELECT id FROM participacoes_desafio WHERE usuario_id = ? AND desafio_id = ?";
        try (ResultSet rs = super.executar(sql, usuarioId, desafioId)) {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar participacao.", e);
        }
    }

    public void inserir(Participacao participacao) {
        String sql = "INSERT INTO participacoes_desafio (usuario_id, desafio_id) VALUES (?, ?)";
        try {
            super.executarUpdate(
                    sql,
                    participacao.getUsuarioId(),
                    participacao.getDesafioId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir participacao.", e);
        }
    }
}
