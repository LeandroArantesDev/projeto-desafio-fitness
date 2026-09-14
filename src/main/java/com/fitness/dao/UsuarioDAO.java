package com.fitness.dao;

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
public class UsuarioDAO extends MysqlDAO {

    public UsuarioDAO() {
        super();
    }

    // Corrigido
    public Usuario buscarPorLoginESenha(String email, String senha) {
        String sql =
                "SELECT id, nome, email, tipo, senha_hash, avatar_url, status, ultimo_login, criado_em, atualizado_em "
                        + "FROM usuarios "
                        + "WHERE email = ?";

        Usuario usuario = null;

        try (ResultSet rs = super.executar(sql, email)) {
            if (rs.next()) {
                usuario = this.mapearUsuario(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario.", e);
        }

        if (usuario != null && BCrypt.checkpw(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTipo(rs.getString("tipo"));
        usuario.setSenha(rs.getString("senha_hash"));
        usuario.setAvatar(rs.getString("avatar_url"));
        usuario.setStatus(rs.getString("status"));
        usuario.setUltimoLogin(rs.getObject("ultimo_login", LocalDateTime.class));
        usuario.setCriadoEm(rs.getObject("criado_em", LocalDateTime.class));
        usuario.setAtualizado_em(rs.getObject("atualizado_em", LocalDateTime.class));

        return usuario;
    }

    public List<Usuario> listarTodos() {
        String sql =
                "SELECT id, nome, email, tipo, senha_hash, avatar_url, status, ultimo_login, criado_em, atualizado_em "
                        + "FROM usuarios "
                        + "ORDER BY nome";
        List<Usuario> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios.", e);
        }
        return lista;
    }

    public Usuario buscarPorLogin(String login) {
        String sql =
                "SELECT u.id, u.nome, u.login, u.senha, u.perfil_id, p.nome AS perfil_nome "
                        + "FROM usuarios u "
                        + "INNER JOIN perfis p ON p.id = u.perfil_id "
                        + "WHERE u.login = ?";
        try (ResultSet rs = super.executar(sql, login)) {
            if (rs.next()) {
                return this.mapearUsuario(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por login.", e);
        }
        return null;
    }

    public Usuario buscarPorId(Long id) {
        String sql =
                "SELECT u.id, u.nome, u.login, u.senha, u.perfil_id, p.nome AS perfil_nome "
                        + "FROM usuarios u "
                        + "INNER JOIN perfis p ON p.id = u.perfil_id "
                        + "WHERE u.id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapearUsuario(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por id.", e);
        }
        return null;
    }

    public int contarPorPerfil(Long perfilId) {
        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE perfil_id = ?";
        try (ResultSet rs = super.executar(sql, perfilId)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar usuarios por perfil.", e);
        }
        return 0;
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, login, senha, perfil_id) VALUES (?, ?, ?, ?)";
        try {
            super.executarUpdate(
                    sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir.", e);
        }
    }

    public void alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, login = ?, senha = ?, perfil_id = ? WHERE id = ?";
        try {
            super.executarUpdate(
                    sql,
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),

                    usuario.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar.", e);
        }
    }
}
