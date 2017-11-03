CREATE DATABASE banco_cinema;
USE banco_cinema;

CREATE TABLE Sala(
	codSala INT AUTO_INCREMENT,
	nroSala INT,
	qtdAssentos INT,
    PRIMARY KEY (codSala)
);

CREATE TABLE Filme(
	codFilme INT AUTO_INCREMENT,
    nomeFilme VARCHAR(60),
    nomeDiretor VARCHAR(80),
    linguagemFilme VARCHAR(50),
    categoriaFilme VARCHAR(50),
    anoLancamento INT,
    PRIMARY KEY (codFilme)
); 

CREATE TABLE Sessao(
	dataSessao DATE,
    codSala INT,            
	codFilme INT,
	horaInicioSessao TIME, 
    horaFimSessao TIME,
    qtdPoltronasDisponiveis INT,
    valorSessao FLOAT(5,2),
    PRIMARY KEY (dataSessao, horaInicioSessao, codSala),
    FOREIGN KEY (codSala) REFERENCES Sala (codSala),
    FOREIGN KEY (codFilme) REFERENCES Filme (codFilme)
);

CREATE TABLE Ingresso(
	codIngresso INT AUTO_INCREMENT,
    dataSessao DATE,
    horaInicioSessao TIME,    
    codSala INT,    
    codFilme INT,
    tipoIngresso VARCHAR(8),
    valorTotal FLOAT(5,2),
    PRIMARY KEY (codIngresso, dataSessao, horaInicioSessao, codSala, codFilme),
    FOREIGN KEY (dataSessao, horaInicioSessao) REFERENCES Sessao (dataSessao, horaInicioSessao),
    FOREIGN KEY (codSala) REFERENCES Sala (codSala),
    FOREIGN KEY (codFilme) REFERENCES Filme (codFilme)
);

SELECT * FROM Sala;
SELECT * FROM Filme;
SELECT * FROM Sessao;
SELECT * FROM Ingresso;

DROP DATABASE banco_cinema;
DROP TABLE Sala;
DROP TABLE Filme;
DROP TABLE Sessao;
DROP TABLE Ingresso;

	/* INSERINDO ALGUMAS SALAS*/
INSERT INTO Sala(nroSala, qtdAssentos)
	VALUES(1, 100);
INSERT INTO Sala(qtdAssentos)
	VALUES(100);
INSERT INTO Sala(qtdAssentos)
	VALUES(100);
    
    /* INSERINDO ALGUNS FILMES */
INSERT INTO Filme(nomeFilme, nomeDiretor, linguagemFilme, categoriaFilme, anoLancamento)
	VALUES('Logan', 'Prof Xavier', 'Dublado', 'Ação', 2017);
    
INSERT INTO Filme(nomeFilme, nomeDiretor, linguagemFilme, categoriaFilme, anoLancamento)
	VALUES('Pele Eterno', 'CBF', 'Dublado', 'Esportes', 2017);
    
INSERT INTO Filme(nomeFilme, nomeDiretor, linguagemFilme, categoriaFilme, anoLancamento)
	VALUES('A bela e a fera', 'Smith Kane', 'Legendado', 'Romance', 2017);

INSERT INTO Filme(nomeFilme, nomeDiretor, linguagemFilme, categoriaFilme, anoLancamento)
	VALUES('Velozes e Furiosos', 'Karl Lewis', 'Legendado', 'Ação', 2017);    

	/* INSERINDO ALGUMAS SESSÕES */
	/*Sala 1   Filme A bela e a fera*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 1, 3, '19:30:00', '21:30:00', 100, 26);
    
    /*Sala 2   Filme Pelé Eterno*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 2, 2, '21:45:00', '23:30:00', 100, 26);
    
    /*Sala 3   Filme Logan*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 3, 1, '21:45:00', '23:30:00', 100, 26);    
    
	/*Sala 1   Filme Logan*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 1, 1, '19:15:00', '21:00:00', 100, 26);

	/*Sala 1   Filme Pele Eterno*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 1, 2, '15:30:00', '17:15:00', 100, 26);

	/*Sala 1   Filme Pele Eterno*/
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, qtdPoltronasDisponiveis, valorSessao)
	VALUES('2016-07-21', 1, 2, '10:30', '12:00', 100, 26);
    
    /* INSERINDO ALGUNS INGRESSOS */
    /*Assistiu No Horario 19:15   na sala 1    o filme    Logan*/
INSERT INTO Ingresso(dataSessao, tipoIngresso, horaInicioSessao, nroSala, codFilme)
	VALUES('2016-07-21', 'Meia', '19:15:00', 1, 1);
    
    /*Assistiu No Horario 15:30   na sala 1    o filme    Pelé Eterno*/
INSERT INTO Ingresso(dataSessao, tipoIngresso, horaInicioSessao, nroSala, codFilme)
	VALUES('2016-07-21', 'Inteira', '15:30:00', 1, 1);
    
	/*Assistiu No Horario 21:45   na sala 2    o filme    Pelé Eterno*/
INSERT INTO Ingresso(dataSessao, tipoIngresso, horaInicioSessao, nroSala, codFilme)
	VALUES('2016-07-21', 'Inteira', '21:45:00', 2, 2);

	/*Assistiu No Horario 21:45   na sala 3    o filme    Logan*/
INSERT INTO Ingresso(dataSessao, tipoIngresso, horaInicioSessao, nroSala, codFilme)
	VALUES('2016-07-21', 'Meia', '21:45:00', 3, 1);
    
    
	/*CONSULTA TODOS OS FILMES QUE FORAM PASSADOS NA SALA X  DURANTE O DIA.*/
SELECT S.nroSala, F.nomeFilme, SE.horaInicioSessao, SE.horaFimSessao
	FROM Sala S
	JOIN Sessao SE  ON S.nroSala = SE.nroSala
    JOIN Filme  F   ON SE.codFilme = F.codFilme
    WHERE S.nroSala = 1;
    
/*CONSULTA TDS OS FILMES DE TODAS AS SESSÕES*/ 
SELECT SE.dataSessao, SA.nroSala, SE.codFilme, F.nomeFilme, F.linguagemFilme, SE.horaInicioSessao, SE.horaFimSessao, 
	       SE.qtdPoltronasDisponiveis, SE.valorSessao
	   FROM Sessao SE
	   INNER JOIN Filme F
	   ON SE.codFilme = F.codFilme
       INNER JOIN Sala SA
       ON SE.codSala = SA.codSala;

    
    /*CONSULTA TDS OS INGRESSOS COMPRADOS P/ A SALA X  NO FILME PELE ETERNO ÀS 15:30*/
SELECT I.codIngresso, I.horaInicioSessao, SE.horaFimSessao, I.nroSala, I.codFilme, I.valorTotal
	FROM Ingresso I
    JOIN Sessao SE   ON I.horaInicioSessao = SE.horaInicioSessao
	WHERE I.horaInicioSessao = '15:30' AND I.nroSala = 1;		 

/*CONSULTANDO A QUANTIDADE DE POLTRONAS DISPONIVEIS EM UMA DETERMINADA SESSÃO*/
SELECT qtdPoltronasDisponiveis 
	FROM Sessao 
	WHERE dataSessao = '2017-05-23' AND horaInicioSessao = '15:30:00' AND nroSala = 3 AND codFilme = 2;

/*ATUALIZANDO A QTD DE POLTRONAS DISPONÍVEIS PARA TESTAR NA CONSULTA DE CIMA.*/
UPDATE Sessao
	SET qtdPoltronasDisponiveis = 90
    WHERE dataSessao = '2017-05-23' AND nroSala = 3 AND codFilme = 2 AND horaInicioSessao = '15:30:00';
    

/*VAI SER USADO NO EVENTO DO TEXT FIELD DE BUSCAR A SESSAO PELO NOME DO FILME na tela de vender ing*/
/*CONSULTANDO APENAS OS FILMES Q POSSUEM UMA SESSAO ATRAVES DA LETRA Q A PESSOA VAI DIGITANDO no tf*/
SELECT S.dataSessao, S.nroSala, S.codFilme, F.nomeFilme, F.linguagemFilme, S.horaInicioSessao, S.horaFimSessao, 
	       S.qtdPoltronasDisponiveis, S.valorSessao
	   FROM Sessao S
	   INNER JOIN Filme F
	   ON S.codFilme = F.codFilme
       WHERE F.nomeFilme LIKE 'l%';    
    
    
    
/*CONSULTA PARA PEGAR O VALOR TOTAL ARRECADADO DOS INGRESSOS EM CADA FILME*/    
SELECT F.codFilme, F.nomeFilme, F.anoLancamento, F.linguagemFilme, SUM(I.valorTotal) 												    
FROM Filme F 
JOIN Ingresso I
ON F.codFilme = I.codFilme
GROUP BY F.codFilme;



/*testando a parte de alteracao*/
delete from sessao where datasessao = '2017-11-04';
INSERT INTO Sessao(dataSessao, codSala, codFilme,  horaInicioSessao, horaFimSessao, 
		    qtdPoltronasDisponiveis, valorSessao)
	VALUES('2017-11-04', 3, 3, '14:00:00', '16:00:00', 100, 30);

select * from sessao;

UPDATE Sessao                               
 SET dataSessao = '2017-11-04', codSala = 3, codFilme = 3, horaInicioSessao = '14:00:00', 
	 horaFimSessao = '16:00:00', qtdPoltronasDisponiveis = 99, valorSessao = 30 
 WHERE dataSessao = '2017-11-04' AND horaInicioSessao = '14:00:00' AND codSala = 1;
 

UPDATE Sessao 
SET dataSessao = '2017-11-04', codSala = 1, codFilme = 1, horaInicioSessao = '14:00:00', 
        horaFimSessao = '16:00:00', qtdPoltronasDisponiveis = 99, valorSessao = 30 
WHERE dataSessao = '2017-11-04' AND horaInicioSessao = '14:00:00' AND codSala = 3;