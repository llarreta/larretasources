CREATE TABLE [SCHEMMA].supposeCompetitionScore (
	id BIGINT NOT NULL,
	idPlayer BIGINT NOT NULL,
	idPlayerSuppose BIGINT NOT NULL,
	idCompetition BIGINT NOT NULL,
	position INT,
	deleted DATETIME,
	PRIMARY KEY (id)
);

CREATE TABLE [SCHEMMA].supposeScore (
	id BIGINT NOT NULL,
	deleted DATETIME,
	idBet BIGINT NOT NULL,
	idPredictionScore BIGINT NOT NULL,
	value INT,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].supposePredictionScore (
	id BIGINT NOT NULL,
	idCompetitionScore BIGINT NOT NULL,
	idPrediction BIGINT NOT NULL,
	deleted DATETIME,
	position INT,
	accumulated INT,
	PRIMARY KEY (ID)
);


ALTER TABLE [SCHEMMA].supposeCompetitionScore
ADD CONSTRAINT fk_player_in_supposecompetitionScore
FOREIGN KEY (idPlayer)
REFERENCES [SCHEMMA].player(id);

ALTER TABLE [SCHEMMA].supposeCompetitionScore
ADD CONSTRAINT fk_playersuppose_in_supposecompetitionScore
FOREIGN KEY (idPlayerSuppose)
REFERENCES [SCHEMMA].player(id);

ALTER TABLE [SCHEMMA].supposeCompetitionScore
ADD CONSTRAINT fk_competition_in_supposecompetitionScore
FOREIGN KEY (idCompetition)
REFERENCES [SCHEMMA].competition(id);

ALTER TABLE [SCHEMMA].supposeScore
ADD CONSTRAINT fk_bet_in_supposeScore
FOREIGN KEY (idBet)
REFERENCES [SCHEMMA].bet(id);

ALTER TABLE [SCHEMMA].supposeScore
ADD CONSTRAINT fk_predictionScore_in_supposescore
FOREIGN KEY (idPredictionScore)
REFERENCES [SCHEMMA].supposePredictionScore(id);

ALTER TABLE [SCHEMMA].supposePredictionScore
ADD CONSTRAINT fk_competitionScore_in_supposepredictionScore
FOREIGN KEY (idCompetitionScore)
REFERENCES [SCHEMMA].supposeCompetitionScore(id);

ALTER TABLE [SCHEMMA].supposePredictionScore
ADD CONSTRAINT fk_prediction_in_supposePredictionScore
FOREIGN KEY (idPrediction)
REFERENCES [SCHEMMA].prediction(id);