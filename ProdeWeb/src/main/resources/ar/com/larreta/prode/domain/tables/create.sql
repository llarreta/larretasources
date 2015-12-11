
CREATE TABLE [SCHEMMA].team (
	id BIGINT NOT NULL,
	deleted DATETIME,
	name VARCHAR(255) NOT NULL,
	shield VARCHAR(255),
	library VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].game (
	id BIGINT NOT NULL,
	deleted DATETIME,
	idLocal BIGINT NOT NULL,
	idVisitor BIGINT NOT NULL,
	localGoals INT,
	visitorGoals INT,
	startDate DATETIME,
	idRound BIGINT NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].round (
	id BIGINT NOT NULL,
	deleted DATETIME,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255),
	idCompetition BIGINT NOT NULL,
	start DATETIME,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].competition (
	id BIGINT NOT NULL,
	deleted DATETIME,
	registrationStart DATETIME,
	registrationEnd DATETIME,
	name VARCHAR(255) NOT NULL,
	picture VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].bet (
	id BIGINT NOT NULL,
	deleted DATETIME,
	idGame BIGINT NOT NULL,
	localGoals INT,
	visitorGoals INT,
	idPrediction BIGINT NOT NULL,
	hardBet TINYINT(1),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].prediction (
	id BIGINT NOT NULL,
	deleted DATETIME,
	idRound BIGINT NOT NULL,
	idPlayer BIGINT NOT NULL,
	auto TINYINT(1),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].player (
	id BIGINT NOT NULL,
	name VARCHAR(255) NOT NULL,
	surname VARCHAR(255) NOT NULL,
	image BLOB(100000),
	idFriend BIGINT,
	PRIMARY KEY (id)
);

CREATE TABLE [SCHEMMA].competitionScore (
	id BIGINT NOT NULL,
	idPlayer BIGINT NOT NULL,
	idCompetition BIGINT NOT NULL,
	value INT,
	position INT,
	deleted DATETIME,
	PRIMARY KEY (id)
);

CREATE TABLE [SCHEMMA].score (
	id BIGINT NOT NULL,
	deleted DATETIME,
	idBet BIGINT NOT NULL,
	idPredictionScore BIGINT NOT NULL,
	value INT,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].predictionScore (
	id BIGINT NOT NULL,
	idCompetitionScore BIGINT NOT NULL,
	idPrediction BIGINT NOT NULL,
	idBestInCompetition BIGINT,
	idBestInRound BIGINT,
	deleted DATETIME,
	value INT,
	position INT,
	accumulated INT,
	PRIMARY KEY (ID)
);

ALTER TABLE [SCHEMMA].game
ADD CONSTRAINT fk_localteam_in_game
FOREIGN KEY (idLocal)
REFERENCES [SCHEMMA].team(id);

ALTER TABLE [SCHEMMA].game
ADD CONSTRAINT fk_visitorteam_in_game
FOREIGN KEY (idVisitor)
REFERENCES [SCHEMMA].team(id);

ALTER TABLE [SCHEMMA].game
ADD CONSTRAINT fk_round_in_game
FOREIGN KEY (idRound)
REFERENCES [SCHEMMA].round(id);

ALTER TABLE [SCHEMMA].round
ADD CONSTRAINT fk_competition_in_round
FOREIGN KEY (idCompetition)
REFERENCES [SCHEMMA].competition(id);

ALTER TABLE [SCHEMMA].bet
ADD CONSTRAINT fk_game_in_bet
FOREIGN KEY (idGame)
REFERENCES [SCHEMMA].game(id);

ALTER TABLE [SCHEMMA].prediction
ADD CONSTRAINT fk_round_in_prediction
FOREIGN KEY (idRound)
REFERENCES [SCHEMMA].round(id);

ALTER TABLE [SCHEMMA].prediction
ADD CONSTRAINT fk_player_in_prediction
FOREIGN KEY (idPlayer)
REFERENCES [SCHEMMA].player(id);

ALTER TABLE [SCHEMMA].player
ADD CONSTRAINT fk_user_in_player
FOREIGN KEY (id)
REFERENCES [SCHEMMA].user(id);

ALTER TABLE [SCHEMMA].competitionScore
ADD CONSTRAINT fk_player_in_competitionScore
FOREIGN KEY (idPlayer)
REFERENCES [SCHEMMA].player(id);

ALTER TABLE [SCHEMMA].competitionScore
ADD CONSTRAINT fk_competition_in_competitionScore
FOREIGN KEY (idCompetition)
REFERENCES [SCHEMMA].competition(id);

ALTER TABLE [SCHEMMA].score
ADD CONSTRAINT fk_bet_in_score
FOREIGN KEY (idBet)
REFERENCES [SCHEMMA].bet(id);

ALTER TABLE [SCHEMMA].score
ADD CONSTRAINT fk_predictionScore_in_score
FOREIGN KEY (idPredictionScore)
REFERENCES [SCHEMMA].predictionScore(id);

ALTER TABLE [SCHEMMA].player
ADD CONSTRAINT fk_friend_in_player
FOREIGN KEY (idFriend)
REFERENCES [SCHEMMA].player(id);

ALTER TABLE [SCHEMMA].predictionScore
ADD CONSTRAINT fk_competitionScore_in_predictionScore
FOREIGN KEY (idCompetitionScore)
REFERENCES [SCHEMMA].competitionScore(id);

ALTER TABLE [SCHEMMA].predictionScore
ADD CONSTRAINT fk_prediction_in_predictionScore
FOREIGN KEY (idPrediction)
REFERENCES [SCHEMMA].prediction(id);

ALTER TABLE [SCHEMMA].predictionScore
ADD CONSTRAINT fk_bestInCompetition_in_predictionScore
FOREIGN KEY (idBestInCompetition)
REFERENCES [SCHEMMA].competition(id);

ALTER TABLE [SCHEMMA].predictionScore
ADD CONSTRAINT fk_bestInRound_in_predictionScore
FOREIGN KEY (idBestInRound)
REFERENCES [SCHEMMA].round(id);
