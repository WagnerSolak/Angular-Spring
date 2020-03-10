CREATE TABLE categoria(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('Água');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Contabilidade');
INSERT INTO categoria (nome) values ('Energia Elétrica');
INSERT INTO categoria (nome) values ('Salários');
INSERT INTO categoria (nome) values ('Outros');

