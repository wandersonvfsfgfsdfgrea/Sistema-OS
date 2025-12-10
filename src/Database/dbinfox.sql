

CREATE DATABASE IF NOT EXISTS dbinfox;
USE dbinfox;


CREATE TABLE tbusuarios (
    iduser  INT AUTO_INCREMENT PRIMARY KEY,
    usuarios VARCHAR(50) NOT NULL,
    fone     VARCHAR(15),
    login    VARCHAR(20) NOT NULL UNIQUE,
    senha    VARCHAR(60) NOT NULL,
    perfil   ENUM('admin', 'user') NOT NULL DEFAULT 'user'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


INSERT INTO tbusuarios (usuarios, fone, login, senha, perfil) VALUES
    ('Wanderson Santos',    '9999-9999', 'wlemos', '123456', 'user'),
    ('Administrador',       '9999-5555', 'admin',  'admin',  'admin');


CREATE TABLE tbclientes (
    idcli    INT AUTO_INCREMENT PRIMARY KEY,
    nomecli  VARCHAR(50)  NOT NULL,
    endcli   VARCHAR(150),
    fonecli  VARCHAR(20)  NOT NULL,
    emailcli VARCHAR(100)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


INSERT INTO tbclientes (nomecli, endcli, fonecli, emailcli) VALUES
    ('Linus Torvalds', 'Rua Tux, nº 2015', '9999-0000', 'linus@linux.com');


CREATE TABLE tbos (
    os         INT AUTO_INCREMENT PRIMARY KEY,
    data_os    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo       VARCHAR(15)  NOT NULL,   
    situacao   VARCHAR(20)  NOT NULL,   
    equipamento VARCHAR(150) NOT NULL,
    defeito     VARCHAR(150) NOT NULL,
    servico     VARCHAR(150),
    tecnico     VARCHAR(30),
    valor       DECIMAL(10,2),
    idcli       INT NOT NULL,

    CONSTRAINT fk_tbos_tbclientes
        FOREIGN KEY (idcli) REFERENCES tbclientes(idcli)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


INSERT INTO tbos (
    tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli
) VALUES (
    'Orçamento',
    'Aprovada',
    'Notebook',
    'Não liga',
    'Troca da fonte',
    'Zé',
    87.50,
    1    
);


SELECT
    o.os,
    DATE_FORMAT(o.data_os, '%d/%m/%Y %H:%i') AS data_os,
    o.tipo,
    o.situacao,
    o.equipamento,
    o.defeito,
    o.servico,
    o.valor,
    c.nomecli,
    c.fonecli
FROM tbos AS o
INNER JOIN tbclientes AS c ON c.idcli = o.idcli;
