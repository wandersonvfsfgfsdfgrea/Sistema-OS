
package br.com.infox.model;

/**
 * Classe que representa uma Ordem de Serviço (OS) no sistema.
 * Contém todas as informações necessárias para registrar e manipular
 * uma OS, incluindo dados do equipamento, defeito, serviço executado,
 * técnico responsável, valor e vínculo com o cliente.
 *
 * Esta classe é utilizada pelo OrdemServicoDAO para operações de CRUD,
 * bem como pelas telas da interface gráfica para exibir e preencher
 * os dados de uma ordem de serviço.
 *
 * Cada campo reflete uma coluna da tabela "tbos" no banco de dados,
 * permitindo que a aplicação mantenha a estrutura consistente e organizada.
 *
 * @author Wanderson Santos Lemos
 */



public class OrdemServico {

    private String os;          
    private String data;        
    private String tipo;        
    private String situacao;
    private String equipamento;
    private String defeito;
    private String servico;
    private String tecnico;
    private String valor;       
    private String idCliente;  

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
