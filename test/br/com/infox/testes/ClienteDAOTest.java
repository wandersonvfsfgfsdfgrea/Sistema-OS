
package br.com.infox.testes;

import br.com.infox.dao.ClienteDAO;
import br.com.infox.dao.ModuloConexao;
import br.com.infox.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteDAOTest {

    @Test
    public void deveInserirClienteNaTabela() throws Exception {

        String nome = "Cliente JUnit";
        String endereco = "Rua dos Testes, 123";
        String fone = "34999999999";
        String email = "cliente.junit@teste.com";

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setFone(fone);
        cliente.setEmail(email);

        ClienteDAO dao = new ClienteDAO();
        dao.adicionar(cliente);

        try (Connection con = ModuloConexao.conector()) {

            String sql = "SELECT * FROM tbclientes WHERE nomecli = ? AND fonecli = ? AND emailcli = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, fone);
            pst.setString(3, email);
            ResultSet rs = pst.executeQuery();

            assertTrue(rs.next());

            long id = rs.getLong("idcli");

            rs.close();
            pst.close();

            PreparedStatement pstDel = con.prepareStatement("DELETE FROM tbclientes WHERE idcli = ?");
            pstDel.setLong(1, id);
            pstDel.executeUpdate();
            pstDel.close();
        }
    }
}
