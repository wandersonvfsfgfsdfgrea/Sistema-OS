
package br.com.infox.model;

/**
 * Classe que representa um cliente do sistema. Herda os atributos básicos
 * da classe Pessoa e adiciona informações específicas, como o endereço.
 *
 * Esta classe é utilizada pelos DAOs e pela interface gráfica para
 * manipulação, exibição e armazenamento dos dados relacionados aos clientes.
 *
 * A identificação do cliente é retornada de forma formatada através do
 * método getIdentificacao(), facilitando a apresentação dos dados em telas
 * e relatórios do sistema.
 *
 * @author Wanderson Santos Lemos
 */

public class Cliente extends Pessoa {
   

   private String endereco;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    @Override
    public String getIdentificacao() {
    return "Cliente: " + getNome();
}

}
    

