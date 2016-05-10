
insert into [SCHEMMA].user (id, nick, password, email, idUserState) values (2, 'leonel', 'cjBaRUVoMnBlZmVhdDdLaGlZMkVSamtNMUhDeU1wcGc=', 'leonel@larreta.com.ar', 1);
insert into [SCHEMMA].player(id, name, surname) values (2, 'Leonel', 'Larreta');
insert into [SCHEMMA].hasProfiles (idUser, idProfile) values (2, 2);
insert into [SCHEMMA].hasProfiles (idUser, idProfile) values (2, 1);

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(1 ,'Aldosivi', 'Aldosivi.png', 'images/Shields');
	
INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(2 ,'Argentinos Junior', 'ArgentinosJunior.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(3 ,'Atletico Rafaela', 'AtleticoRafaela.png', 'images/Shields');
	
INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(4 ,'Banfield', 'Banfield.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(5 ,'Belgrano', 'Belgrano.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(6 ,'Boca', 'Boca.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(7 ,'Nueva Chicago', 'Chicago.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(8 ,'Colon', 'Colon.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(9 ,'Crucero del Norte', 'CruceroDelNorte.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(10 ,'Defensa y Justicia', 'DefensaYJusticia.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(11 ,'Estudiantes', 'Estudiantes.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(12 ,'Gimnasia', 'Gimnasia.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(13 ,'Godoy Cruz', 'GodoyCruz.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(14 ,'Huracan', 'Huracan.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(15 ,'Independiente', 'Independiente.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(16 ,'Lanus', 'Lanus.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(17 ,'Newells', 'Newells.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(18 ,'Olimpo', 'Olimpo.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(19 ,'Quilmes', 'Quilmes.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(20 ,'Racing', 'Racing.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(21 ,'River', 'River.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(22 ,'Rosario Central', 'RosarioCentral.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(23 ,'San Lorenzo', 'SanLorenzo.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(24 ,'San Martin', 'SanMartin.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(25 ,'Sarmiento', 'Sarmiento.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(26 ,'Temperley', 'Temperley.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(27 ,'Tigre', 'Tigre.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(28 ,'Union', 'Union.png', 'images/Shields');

INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(29 ,'Velez', 'Velez.png', 'images/Shields');
	
INSERT INTO [SCHEMMA].team(id, name, shield, library) 
	VALUES 
	(30 ,'Arsenal', 'Arsenal.png', 'images/Shields');

	
INSERT INTO [SCHEMMA].competition(id, registrationStart, registrationEnd, name)	
VALUES (1, DATE_ADD(SYSDATE(), INTERVAL -10 DAY), DATE_ADD(SYSDATE(), INTERVAL 10 DAY), "Futbol Argentino 2015 - Segunda Fase");


INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (16, 'Fecha 16', 1);	
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (17, 'Fecha 17', 1);	
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (18, 'Fecha 18', 1);	
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (19, 'Fecha 19', 1);	
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (20, 'Fecha 20', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (21, 'Fecha 21', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (22, 'Fecha 22', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (23, 'Fecha 23', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (24, 'Fecha 24', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (25, 'Fecha 25', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (26, 'Fecha 26', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (27, 'Fecha 27', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (28, 'Fecha 28', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (29, 'Fecha 29', 1);
INSERT INTO [SCHEMMA].round (id, name, idCompetition) VALUES (30, 'Fecha 30', 1);

-- Fecha 16
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (1, 16, 13, 9, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (2, 16, 30, 12, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (3, 16, 28, 23, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (4, 16, 5, 2, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (5, 16, 19, 4, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (6, 16, 25, 6, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (7, 16, 17, 20, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (8, 16, 29, 27, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (9, 16, 1, 22, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (10, 16, 15, 18, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (11, 16, 21, 26, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (12, 16, 16, 3, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (13, 16, 7, 8, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (14, 16, 14, 10, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (15, 16, 11, 24, STR_TO_DATE('12-07-2015 16:00', '%d-%m-%Y %H:%i'));

-- Fecha 17
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (16, 17, 9, 11, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (17, 17, 24, 14, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (18, 17, 10, 7, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (19, 17, 8, 16, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (20, 17, 3, 21, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (21, 17, 26, 15, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (22, 17, 18, 1, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (23, 17, 22, 29, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (24, 17, 27, 17, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (25, 17, 20, 25, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (26, 17, 6, 19, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (27, 17, 4, 5, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (28, 17, 2, 28, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (29, 17, 23, 30, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (30, 17, 12, 13, STR_TO_DATE('19-07-2015 16:00', '%d-%m-%Y %H:%i'));

-- Fecha 18
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (31, 18, 12, 9, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (32, 18, 13, 23, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (33, 18, 30, 2, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (34, 18, 28, 4, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (35, 18, 5, 6, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (36, 18, 19, 20, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (37, 18, 25, 27, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (38, 18, 17, 22, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (39, 18, 29, 18, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (40, 18, 1, 26, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (41, 18, 15, 3, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (42, 18, 21, 8, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (43, 18, 16, 10, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (44, 18, 7, 24, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (45, 18, 14, 11, STR_TO_DATE('26-07-2015 16:00', '%d-%m-%Y %H:%i'));	

-- Fecha 19
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (46, 19, 9, 14, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (47, 19, 11, 7, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (48, 19, 24, 16, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (49, 19, 10, 21, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (50, 19, 8, 15, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (51, 19, 3, 1, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (52, 19, 26, 29, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (53, 19, 18, 17, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (54, 19, 22, 25, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (55, 19, 27, 19, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (56, 19, 20, 5, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (57, 19, 6, 28, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (58, 19, 4, 30, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (59, 19, 2, 13, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (60, 19, 23, 12, STR_TO_DATE('02-08-2015 16:00', '%d-%m-%Y %H:%i'));

-- Fecha 20
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (61, 20, 23, 9, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (62, 20, 12, 2, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (63, 20, 13, 4, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (64, 20, 30, 6, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (65, 20, 28, 20, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (66, 20, 5, 27, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (67, 20, 19, 22, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (68, 20, 25, 18, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (69, 20, 17, 26, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (70, 20, 29, 3, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (71, 20, 1, 8, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (72, 20, 15, 10, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (73, 20, 21, 24, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (74, 20, 16, 11, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (75, 20, 7, 14, STR_TO_DATE('09-08-2015 16:00', '%d-%m-%Y %H:%i'));	

-- Fecha 21
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (76, 21, 9, 7, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (77, 21, 14, 16, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (78, 21, 11, 21, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (79, 21, 24, 15, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (80, 21, 10, 1, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (81, 21, 8, 29, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (82, 21, 3, 17, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (83, 21, 26, 25, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (84, 21, 18, 19, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (85, 21, 22, 5, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (86, 21, 27, 28, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (87, 21, 20, 30, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (88, 21, 6, 13, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (89, 21, 4, 12, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (90, 21, 2, 23, STR_TO_DATE('15-08-2015 16:00', '%d-%m-%Y %H:%i'));	

-- Fecha 22
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (91, 22, 2, 9, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (92, 22, 23, 4, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (93, 22, 12, 6, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (94, 22, 13, 20, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (95, 22, 30, 27, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (96, 22, 28, 22, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (97, 22, 5, 18, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (98, 22, 19, 26, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (99, 22, 25, 3, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (100, 22, 17, 8, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (101, 22, 29, 10, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (102, 22, 1, 24, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (103, 22, 15, 11, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (104, 22, 21, 14, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (105, 22, 16, 7, STR_TO_DATE('22-08-2015 16:00', '%d-%m-%Y %H:%i'));	

-- Fecha 23
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (106, 23, 9, 16, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (107, 23, 7, 21, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (108, 23, 14, 15, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (109, 23, 11, 1, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (110, 23, 24, 29, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (111, 23, 10, 17, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (112, 23, 8, 25, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (113, 23, 3, 19, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (114, 23, 26, 5, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (115, 23, 18, 28, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (116, 23, 22, 30, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (117, 23, 27, 13, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (118, 23, 20, 12, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (119, 23, 6, 23, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));
INSERT INTO [SCHEMMA].game (id, idRound, idLocal, idVisitor, startDate) VALUES (120, 23, 4, 2, STR_TO_DATE('29-08-2015 16:00', '%d-%m-%Y %H:%i'));


	
	
	
	
	
	
	
	
	
	
	
	
	
	

	