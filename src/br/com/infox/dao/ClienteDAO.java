
package br.com.infox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.infox.model.Cliente;

/**
 * Classe responsável por realizar operações de CRUD para a entidade Cliente.
 * Este DAO faz a comunicação direta com o banco de dados, permitindo inserir,
 * listar, atualizar e remover registros da tabela tbclientes.
 *
 * Cada método utiliza a conexão provida pelo ModuloConexao, garantindo que
 * o sistema consiga manipular os dados de clientes de forma organizada e segura.
 *
 * @author Wanderson Santos Lemos
 */

public class ClienteDAO implements CrudDAO<Cliente> {

    private Connection conexao;

    public ClienteDAO() {
        conexao = ModuloConexao.conector();
    }

    @Override
    public boolean adicionar(Cliente cliente) {
        String sql = "insert into tbclientes(nomecli,endcli,fonecli,emailcli) values(?,?,?,?)";
        try {
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEndereco());
            pst.setString(3, cliente.getFone());
            pst.setString(4, cliente.getEmail());
            int adicionado = pst.executeUpdate();
            return adicionado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
            return false;
        }
    }


public List<Cliente> listarPorNome(String nome) {
    List<Cliente> lista = new ArrayList<Cliente>();
    String sql = "select idcli, nomecli, endcli, fonecli, emailcli from tbclientes where nomecli like ?";

    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, nome + "%");
        java.sql.ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Cliente c = new Cliente();
            c.setId(rs.getString("idcli"));
            c.setNome(rs.getString("nomecli"));
            c.setEndereco(rs.getString("endcli"));
            c.setFone(rs.getString("fonecli"));
            c.setEmail(rs.getString("emailcli"));
            lista.add(c);
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        System.out.println("Erro ao listar clientes: " + e.getMessage());
    }

    return lista;
}
public boolean alterar(Cliente cliente) {
    String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=? where idcli=?";
    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, cliente.getNome());
        pst.setString(2, cliente.getEndereco());
        pst.setString(3, cliente.getFone());
        pst.setString(4, cliente.getEmail());
        pst.setString(5, cliente.getId());
        int atualizado = pst.executeUpdate();
        return atualizado > 0;
    } catch (SQLException e) {
        System.out.println("Erro ao alterar cliente: " + e.getMessage());
        return false;
    }
}

public boolean remover(String id) {
    String sql = "delete from tbclientes where idcli=?";
    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, id);
        int apagado = pst.executeUpdate();
        return apagado > 0;
    } catch (SQLException e) {
        System.out.println("Erro ao remover cliente: " + e.getMessage());
        return false;
    }
}
}