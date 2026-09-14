package com.fitness.dao;

import com.fitness.model.Desafio;
import com.fitness.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Usuario}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class DesafioDAO extends MysqlDAO {

    public DesafioDAO() {
        super();
    }

    private Desafio mapearDesafio(ResultSet rs) throws SQLException {
        Desafio desafio = new Desafio();
        desafio.setId(rs.getLong("id"));
        desafio.setCriadorId(rs.getLong("criador_id"));
        desafio.setNome(rs.getString("nome"));
        desafio.setDescricao(rs.getString("descricao"));
        desafio.setCategoria(rs.getString("categoria"));
        desafio.setTipoMeta(rs.getString("tipo_meta"));
        desafio.setMetaTotal(rs.getDouble("meta_total"));
        desafio.setUnidadeMedida(rs.getString("unidade_medida"));
        desafio.setDataInicio(rs.getObject("data_inicio", LocalDateTime.class));
        desafio.setDataFim(rs.getObject("data_fim", LocalDateTime.class));
        desafio.setStatus(rs.getString("status")); 
        desafio.setCriadoEm(rs.getObject("criado_em", LocalDateTime.class));
        desafio.setAtualizado_em(rs.getObject("atualizado_em", LocalDateTime.class));

        return desafio;
    }

    public List<Desafio> listarTodosDesafios() {
        String sql =
                "SELECT id, criador_id, nome, descricao, categoria, tipo_meta, meta_total, unidade_medida, data_inicio, data_fim, status, criado_em, atualizado_em "
                        + "FROM desafios";
        List<Desafio> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapearDesafio(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar desafios.", e);
        }
        return lista;
    }

    // public void inserir(Usuario usuario) {
    //     String sql = "INSERT INTO usuarios (nome, login, senha, perfil_id) VALUES (?, ?, ?, ?)";
    //     try {
    //         super.executarUpdate(
    //                 sql,
    //                 usuario.getNome(),
    //                 usuario.getEmail(),
    //                 usuario.getSenha());
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Erro ao inserir.", e);
    //     }
    // }

    // public void alterar(Usuario usuario) {
    //     String sql = "UPDATE usuarios SET nome = ?, login = ?, senha = ?, perfil_id = ? WHERE id = ?";
    //     try {
    //         super.executarUpdate(
    //                 sql,
    //                 usuario.getNome(),
    //                 usuario.getEmail(),
    //                 usuario.getSenha(),

    //                 usuario.getId());
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Erro ao alterar.", e);
    //     }
    // }

    // public void deletar(Long id) {
    //     String sql = "DELETE FROM usuarios WHERE id = ?";
    //     try {
    //         super.executarUpdate(sql, id);
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Erro ao deletar.", e);
    //     }
    // }
}
