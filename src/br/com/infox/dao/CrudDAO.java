
package br.com.infox.dao;

/**
 * Interface genérica para operações básicas de persistência (CRUD).
 * Define o contrato mínimo que qualquer DAO do sistema deve implementar,
 * garantindo padronização nas operações de cadastro e manipulação de dados.
 *
 * @param <T> tipo da entidade que será manipulada pelo DAO
 *
 * Interface utilizada por classes como ClienteDAO, UsuarioDAO e OrdemServicoDAO.
 * Cada implementação define como os dados serão inseridos, atualizados,
 * consultados e removidos do banco de dados.
 * 
 * @author Wanderson Santos Lemos
 */


public interface CrudDAO<T> {
    boolean adicionar(T obj);
}