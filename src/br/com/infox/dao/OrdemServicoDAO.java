package br.com.infox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import br.com.infox.model.OrdemServico;

/**
 * Classe responsável por realizar operações de CRUD para a entidade
 * OrdemServico. Este DAO gerencia todas as interações com a tabela tbos,
 * permitindo inserir, buscar, atualizar e excluir ordens de serviço no sistema.
 *
 * A classe utiliza a conexão fornecida pelo ModuloConexao, garantindo que
 * todas as operações sejam realizadas de forma consistente e segura.
 *
 * Os métodos implementados permitem:
 *  - cadastrar uma nova OS;
 *  - consultar uma OS específica pelo número;
 *  - atualizar os dados de uma OS existente;
 *  - excluir uma OS do banco de dados.
 *
 * Esta classe segue o padrão DAO, centralizando toda a lógica de acesso
 * aos dados relacionados às ordens de serviço.
 *
 * @author Wanderson Santos Lemos
 */

public class OrdemServicoDAO implements CrudDAO<OrdemServico> {

    private Connection conexao;

    public OrdemServicoDAO() {
        conexao = ModuloConexao.conector();
    }

    @Override
    public boolean adicionar(OrdemServico os) {
        String sql = "INSERT INTO tbos(tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli) " +
                     "VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setString(1, os.getTipo());
            pst.setString(2, os.getSituacao());
            pst.setString(3, os.getEquipamento());
            pst.setString(4, os.getDefeito());
            pst.setString(5, os.getServico());
            pst.setString(6, os.getTecnico());
            pst.setString(7, os.getValor());
            pst.setString(8, os.getIdCliente());

            int adicionado = pst.executeUpdate();
            return adicionado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public OrdemServico buscarPorNumero(int numeroOs) {
    String sql = "SELECT os, "
               + "DATE_FORMAT(data_os, '%d/%m/%Y - %H:%i') AS data_formatada, "
               + "tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli "
               + "FROM tbos WHERE os = ?";

    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setInt(1, numeroOs);
        ResultSet rs = pst.executeQuery();

       if (rs.next()) {
        OrdemServico os = new OrdemServico();
        os.setOs(rs.getString("os"));
        os.setData(rs.getString("data_formatada"));
        os.setTipo(rs.getString("tipo"));
        os.setSituacao(rs.getString("situacao"));
        os.setEquipamento(rs.getString("equipamento"));
        os.setDefeito(rs.getString("defeito"));
        os.setServico(rs.getString("servico"));
        os.setTecnico(rs.getString("tecnico"));
        os.setValor(rs.getString("valor"));
        os.setIdCliente(rs.getString("idcli"));
        return os;
    }

    return null;


    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar OS: " + e.getMessage(), e);
    }
}
public boolean alterar(OrdemServico os) {
    String sql = "UPDATE tbos SET tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? " +
                 "WHERE os=?";

    try {
        PreparedStatement pst = conexao.prepareStatement(sql);

        pst.setString(1, os.getTipo());
        pst.setString(2, os.getSituacao());
        pst.setString(3, os.getEquipamento());
        pst.setString(4, os.getDefeito());
        pst.setString(5, os.getServico());
        pst.setString(6, os.getTecnico());
        pst.setString(7, os.getValor());
        pst.setString(8, os.getOs());

        int atualizado = pst.executeUpdate();
        return atualizado > 0;

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao alterar OS: " + e.getMessage(), e);
    }
}

public boolean excluir(String numeroOs) {
    String sql = "DELETE FROM tbos WHERE os = ?";

    try {
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, numeroOs);

        int apagado = pst.executeUpdate();
        return apagado > 0;

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao excluir OS: " + e.getMessage(), e);
    }
}
}
