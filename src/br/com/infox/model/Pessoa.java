
package br.com.infox.model;


/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Serve como base para outras entidades, como Cliente e Usuario,
 * centralizando atributos comuns como id, nome, telefone e e-mail.
 *
 * As subclasses devem implementar o método abstrato getIdentificacao(),
 * que retorna uma identificação personalizada conforme o tipo de pessoa.
 *
 * Esta estrutura garante organização, reutilização de código e facilidade
 * na expansão do sistema com novos tipos de entidades.
 *
 * @author Wanderson Santos Lemos
 */

public abstract class Pessoa {

    private String id;
    private String nome;
    private String fone;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public abstract String getIdentificacao();
}

