💻 Sistema de Ordem de Serviço (InfoX)

Aplicação completa para gestão de Ordens de Serviço, desenvolvida em Java (Swing) com banco de dados MySQL.
O sistema permite cadastrar clientes, usuários, criar ordens de serviço, atualizar, excluir e gerar relatórios em PDF usando JasperReports.

🚀 Tecnologias Utilizadas

☕ Java SE

🖼 Java Swing (Interface gráfica)

🗄 MySQL

🔌 JDBC

📄 JasperReports (.jasper)

🧪 JUnit (testes unitários)

🧰 NetBeans IDE

🖼 Ícones em PNG para a interface

📌 Funcionalidades Principais
👤 Módulo de Clientes

Cadastrar clientes

Pesquisar por nome

Editar informações

Excluir registros

Listagem dinâmica na tabela

👨‍🔧 Módulo de Usuários

Cadastro de usuários com permissão

Login e autenticação

Perfis: admin e user

CRUD completo

🛠 Ordem de Serviço

Emissão de OS

Tipo: Orçamento ou OS

Situações (na bancada, aprovado, reprovado, aguardando peças etc.)

Associação com cliente

Edição, remoção e busca por número da OS

🧾 Relatórios

Relatório de clientes

Relatório de serviços

Impressão de OS individual

Geração via JasperReports

🗂 Estrutura do Projeto
src/
 ├── br.com.infox.dao/
 │     ├── ClienteDAO.java
 │     ├── UsuarioDAO.java
 │     ├── OrdemServicoDAO.java
 │     └── ModuloConexao.java
 ├── br.com.infox.model/
 │     ├── Pessoa.java
 │     ├── Cliente.java
 │     ├── Usuario.java
 │     └── OrdemServico.java
 ├── br.com.infox.telas/
 │     ├── TelaPrincipal.java
 │     ├── TelaLogin.java
 │     ├── TelaCliente.java
 │     ├── TelaUsuario.java
 │     ├── TelaOs.java
 │     └── TelaSobre.java
 └── br.com.infox.testes/
       ├── ClienteDAOTest.java
       ├── UsuarioDAOTest.java
       └── ModuloConexaoTest.java

💾 Banco de Dados

O sistema utiliza MySQL.
Tabelas:

tbclientes

tbusuarios

tbos (ordens de serviço)

A conexão é feita pelo arquivo:

ModuloConexao.java

🧪 Testes Unitários (JUnit)

O projeto inclui testes automáticos:

Teste de conexão com o banco

Teste de inserção de cliente

Teste dos DAOs principais

🖨 Impressão de Relatórios

Os relatórios ficam em:

/reports/*.jasper


E são gerados com:

JasperPrint print = JasperFillManager.fillReport("caminho.jasper", parametros, conexao);
JasperViewer.viewReport(print, false);

▶️ Execução do Projeto

Faça o clone:

git clone https://github.com/SEU-USUARIO/NOME-DO-REPO.git


Importe no NetBeans

Configure o banco MySQL

Atualize o caminho dos relatórios

Execute a classe TelaLogin

👨‍💻 Desenvolvedor

Wanderson Santos Lemos
Sistema desenvolvido para fins de estudo e prática de Java + MySQL.
