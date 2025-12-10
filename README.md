📘 Sistema de Ordem de Serviço (X-System)

Sistema desktop desenvolvido em Java, utilizando POO, DAO, MySQL, JDBC, Swing, JUnit e JasperReports.
Criado com foco em aprendizado, organização profissional e boas práticas de desenvolvimento.
----------------------------------------------------------------------------------------------------------------------------------------------
🚀 Descrição Geral

O X-System é um sistema de controle de Ordem de Serviço (OS) voltado para pequenas empresas, assistências técnicas e prestadores de serviços.
Ele permite:

Gestão de clientes

Emissão, consulta, atualização e remoção de OS

Relatórios profissionais em PDF (JasperReports)

Controle de usuários com níveis de acesso

Tela de login com validação de credenciais

Interface desktop completa construída com Java Swing

O projeto segue uma arquitetura organizada com camadas Model, DAO e Telas, além de testes unitários aplicados com JUnit.
--------------------------------------------------------------------------------------------------------------------------------------------
🛠️ Tecnologias Utilizadas

Java 8+

Banco de Dados MySQL

JDBC (Driver do MySQL)

NetBeans (GUI Builder)

Java Swing

POO (Herança, Polimorfismo, Encapsulamento, Abstração)

DAO – Data Access Object

JUnit 4 – Testes de Unidade

JasperReports – Relatórios .jasper

DbUtils – Conversão rápida de ResultSet em TableModel
-------------------------------------------------------------------------------------------------------------------------------------------
📂 Estrutura do Projeto
src/
 └── br/com/infox/
      ├── dao/        # Conexão e classes DAO (ClienteDAO, UsuarioDAO, OrdemServicoDAO)
      ├── model/      # Classes de modelo (Cliente, Usuario, Pessoa, OrdemServico)
      ├── telas/      # Telas Swing (Login, Principal, Clientes, OS, Usuários)
      └── testes/     # Testes JUnit
-------------------------------------------------------------------------------------------------------------------------------------------
📌 Principais Funcionalidades
👥 Clientes

Cadastrar clientes

Pesquisar por nome

Alterar dados

Remover clientes

Preencher tabela dinâmica com DbUtils
--------------------------------------------------------------------------------------------------------------------------------------------
🧾 Ordem de Serviço (OS)

Definir tipo (📄 Orçamento ou 🔧 Ordem de Serviço)

Emitir OS

Consultar OS pelo número

Alterar OS

Excluir OS

Imprimir OS em PDF

Buscar cliente vinculado
--------------------------------------------------------------------------------------------------------------------------------------------
👤 Usuários

Cadastro de usuários

Consulta por ID

Edição e remoção

Níveis de acesso: admin e user
--------------------------------------------------------------------------------------------------------------------------------------------
🔐 Tela de Login

Validação de login e senha

Carregamento da permissão (admin libera menus extras)

Indicador visual de conexão com o banco (dbok / dberro)
--------------------------------------------------------------------------------------------------------------------------------------------
📄 Relatórios (JasperReports)

Relatório de clientes

Relatório de serviços

Relatório individual da OS

Modelos em:
/reports/os.jasper
/reports/clientes.jasper
/reports/servicos.jasper
--------------------------------------------------------------------------------------------------------------------------------------------
🧪 Testes com JUnit

O projeto inclui testes unitários, como:

ClienteDAOTest → insere cliente, verifica no banco e remove

Testes básicos de conexão (ModuloConexaoTest)

Testes de persistência e consistência de dados
---------------------------------------------------------------------------------------------------------------------------------------------
🏗️ Requisitos Para Rodar
✔️ 1. Banco de Dados

Criar o banco no MySQL:

CREATE DATABASE dbinfox;
USE dbinfox;


Criar as tabelas: (Clientes, Usuários, OS – se quiser eu gero aqui também)

✔️ 2. Configurar o arquivo ModuloConexao

Ajustar usuário

Ajustar senha

Ajustar porta se necessário

✔️ 3. Driver MySQL Connector

Adicionar no projeto:

mysql-connector-j-8.x.x.jar

✔️ 4. Libs obrigatórias

rs2xml.jar

JasperReports libs

JUnit 4
-------------------------------------------------------------------------------------------------------------------------------------
▶️ Como Executar

Abrir no NetBeans

Verificar o arquivo ModuloConexao

Rodar a classe TelaLogin.java

Logar com usuário cadastrado

Usar o sistema normalmente


-------------------------------------------------------------------------------------------------------------------------------------
👨‍💻 Autor

Wanderson Santos
Sistema desenvolvido para estudo, portfólio e uso real em pequenas empresas.

🏷️ Versão

X-System v1.0 – Finalizada com documentação e testes
