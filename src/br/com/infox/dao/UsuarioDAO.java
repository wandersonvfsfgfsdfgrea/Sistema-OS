package br.com.infox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.infox.model.Usuario;

/**
 * Classe responsável por realizar operações de CRUD para a entidade Usuario.
 * Este DAO faz a comunicação direta com a tabela tbusuarios, permitindo
 * cadastrar, consultar, atualizar e remover usuários do sistema.
 *
 * Todos os métodos utilizam a conexão fornecida pelo ModuloConexao,
 * garantindo que as operações no banco sejam executadas de forma segura.
 *
 * Funcionalidades implementadas:
 *  - adicionar um novo usuário;
 *  - consultar um usuário pelo ID;
 *  - atualizar dados de um usuário existente;
 *  - remover um usuário do sistema.
 *
 * Esta classe segue o padrão DAO, mantendo toda a lógica de acesso a dados
 * centralizada e separada da interface gráfica.
 *
 * @author Wanderson Santos Lemos
 */
public class UsuarioDAO implements CrudDAO<Usuario> {

    private Connection conexao;

    public UsuarioDAO() {
        conexao = ModuloConexao.conector();
    }

    @Override
    public boolean adicionar(Usuario usuario) {
        String sql = "insert into tbusuarios(iduser,usuarios,fone,login,senha,perfil) values(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setString(1, usuario.getId());
            pst.setString(2, usuario.getNome());
            pst.setString(3, usuario.getFone());
            pst.setString(4, usuario.getLogin());
            pst.setString(5, usuario.getSenha());
            pst.setString(6, usuario.getPerfil());
            int adicionado = pst.executeUpdate();
            return adicionado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }


public Usuario consultarPorId(String id) {
    String sql = "select iduser, usuarios, fone, login, senha, perfil from tbusuarios where iduser = ?";

    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, id);
        java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getString("iduser"));
            usuario.setNome(rs.getString("usuarios"));
            usuario.setFone(rs.getString("fone"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setPerfil(rs.getString("perfil"));
            rs.close();
            pst.close();
            return usuario;
        } else {
            rs.close();
            pst.close();
            return null;
        }
    } catch (SQLException e) {
        System.out.println("Erro ao consultar usuário: " + e.getMessage());
        return null;
    }
}

public boolean alterar(Usuario usuario) {
    String sql = "update tbusuarios set usuarios=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, usuario.getNome());
        pst.setString(2, usuario.getFone());
        pst.setString(3, usuario.getLogin());
        pst.setString(4, usuario.getSenha());
        pst.setString(5, usuario.getPerfil());
        pst.setString(6, usuario.getId());
        int atualizado = pst.executeUpdate();
        return atualizado > 0;
    } catch (SQLException e) {
        System.out.println("Erro ao alterar usuário: " + e.getMessage());
        return false;
    }
}

public boolean remover(String id) {
    String sql = "delete from tbusuarios where iduser=?";
    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, id);
        int apagado = pst.executeUpdate();
        return apagado > 0;
    } catch (SQLException e) {
        System.out.println("Erro ao remover usuário: " + e.getMessage());
        return false;
    }
}
}
   
