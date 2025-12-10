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


import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;
import br.com.infox.dao.ModuloConexao;

public class ModuloConexaoTest {

    @Test
    public void conexaoNaoDeveSerNula() throws Exception {
        System.out.println("Testando conexão com o banco...");

        Connection conexao = ModuloConexao.conector();

        assertNotNull("A conexão não pode ser nula", conexao);

        if (conexao != null) {
            conexao.close();
        }
    }
}
