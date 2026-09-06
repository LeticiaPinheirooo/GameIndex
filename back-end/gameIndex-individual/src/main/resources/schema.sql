CREATE TABLE IF NOT EXISTS plataforma(
    idPlat INT PRIMARY KEY AUTO_INCREMENT,
    nomePlat VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categoria(
    idCat INT PRIMARY KEY AUTO_INCREMENT,
    nomeCat VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS jogo(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    dataJogou DATE NOT NULL,
    favorito BOOLEAN NOT NULL DEFAULT FALSE,

    FK_plataforma INT,
        CONSTRAINT FK_jogo_plataforma FOREIGN KEY(FK_plataforma)
            REFERENCES plataforma(idPlat),

    FK_categoria INT,
    CONSTRAINT FK_jogo_categoria FOREIGN KEY(FK_categoria)
    REFERENCES categoria(idCat)

);

INSERT INTO plataforma VALUES
(1, 'PC'),
(2, 'CONSOLE'),
(3, 'CELULAR');

INSERT INTO categoria VALUES
(1, 'Ação'),
(2, 'Aventura'),
(3, 'RPG'),
(4, 'Puzzle'),
(5, 'História Interativa'),
(6, 'FPS');


INSERT INTO jogo (nome, dataJogou, FK_plataforma, FK_categoria, favorito) VALUES
('Katana zero', '2026-09-05', 1, 1, 'true');
