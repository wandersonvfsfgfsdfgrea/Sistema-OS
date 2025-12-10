
package br.com.infox.dao;

import java.sql.*;

/**
 * Classe responsável por estabelecer a conexão com o banco de dados MySQL.
 * Este módulo é utilizado por todas as classes DAO do sistema para obter
 * uma conexão ativa e permitir operações de consulta, inserção, atualização
 * e exclusão no banco.
 *
 * @author Wanderson Santos Lemos
 */

public class ModuloConexao {

   
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/dbinfox?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
        String user = "dba";
        String password = "Senha@2019";
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
javax.swing.JOptionPane.showMessageDialog(null, e);
    return null;
}
        }
    }

