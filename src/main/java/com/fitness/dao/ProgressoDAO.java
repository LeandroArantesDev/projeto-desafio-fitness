package com.fitness.dao;

import com.fitness.model.Participacao;
import com.fitness.model.Progresso;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Participacao}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class ProgressoDAO extends MysqlDAO {

    public ProgressoDAO() {
        super();
    }

    public boolean verificarProgresso(Long usuarioId, Long desafioId) {
        String sql ="SELECT id FROM participacoes_desafio "
                            +"WHERE usuario_id = ? AND desafio_id = ?";
        try (ResultSet rs = super.executar(sql, usuarioId, desafioId)) {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar participacao.", e);
        }
    }
}

