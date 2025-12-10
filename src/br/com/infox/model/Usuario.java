package br.com.infox.model; 

/**
 * Classe que representa um usuário do sistema. Herda os atributos básicos
 * da classe Pessoa e adiciona informações específicas, como login, senha
 * e perfil de acesso.
 *
 * Esta classe é utilizada para autenticação, controle de permissões
 * e gerenciamento de usuários dentro do sistema.
 *
 * O método getIdentificacao() retorna uma identificação formatada,
 * útil para exibição em telas e relatórios.
 *
 * @author Wanderson Santos Lemos
 */

public class Usuario extends Pessoa 
{ 
    private String login; 
    private String senha; 
    private String perfil;

    public String getLogin() { 
        return login; 
    } 
    public void setLogin(String login) { 
        this.login = login; 
    } 
    public String getSenha() { 
        return senha; 
    } 
    public void setSenha(String senha) { 
        this.senha = senha; 
    } 
    public String getPerfil() { 
        return perfil; 
    } 
    public void setPerfil(String perfil) { 
        this.perfil = perfil; 
    } 
    @Override 
    public String getIdentificacao() { 
        return "Usuário: " + getNome(); 
    } 
}
