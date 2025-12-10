/*
 * The MIT License
 *
 * Copyright 2025 wande.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.infox.testes;

import br.com.infox.dao.ModuloConexao;
import br.com.infox.dao.UsuarioDAO;
import br.com.infox.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioDAOTest {

    @Test
    public void deveInserirUsuarioNaTabela() throws Exception {

        String id = "99999999";
        String nome = "Usuario JUnit";
        String fone = "34988887777";
        String login = "junit_user";
        String senha = "123456";
        String perfil = "admin";

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setFone(fone);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setPerfil(perfil);

        UsuarioDAO dao = new UsuarioDAO();
        dao.adicionar(usuario);

        try (Connection con = ModuloConexao.conector()) {

            String sql = "SELECT * FROM tbusuarios WHERE login = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();

            assertTrue(rs.next());

            String idBanco = rs.getString("iduser");

            rs.close();
            pst.close();

            PreparedStatement pstDel = con.prepareStatement("DELETE FROM tbusuarios WHERE iduser = ?");
            pstDel.setString(1, idBanco);
            pstDel.executeUpdate();
            pstDel.close();
        }
    }
}
