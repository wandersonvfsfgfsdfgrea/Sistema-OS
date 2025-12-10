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

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ExecutaTestes {

    public static void main(String[] args) {

        Result resultado = JUnitCore.runClasses(
                ModuloConexaoTest.class,
                ClienteDAOTest.class,
                UsuarioDAOTest.class
        );

        System.out.println("======================================");
        System.out.println("Total de testes: " + resultado.getRunCount());
        System.out.println("Falhas:          " + resultado.getFailureCount());
        System.out.println("Ignorados:       " + resultado.getIgnoreCount());
        System.out.println("Tempo (ms):      " + resultado.getRunTime());
        System.out.println("======================================");

        if (!resultado.wasSuccessful()) {
            System.out.println("Falhas detalhadas:");
            for (Failure falha : resultado.getFailures()) {
                System.out.println("- " + falha.getTestHeader());
                System.out.println("  " + falha.getMessage());
                falha.getException().printStackTrace(System.out);
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println(" Todos os testes passaram!");
        }
    }
}
