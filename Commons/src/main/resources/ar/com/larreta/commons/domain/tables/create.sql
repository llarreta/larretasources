CREATE TABLE [SCHEMMA].user (
	id BIGINT NOT NULL,
	deleted DATETIME,
	nick VARCHAR(255) NOT NULL,
	password VARCHAR(255),
	email VARCHAR(255),
	idUserState BIGINT NOT NULL,
	token VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].userState (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].userAccessHistory (
	id BIGINT NOT NULL,
	deleted DATETIME,
	detail VARCHAR(255),
	idUser BIGINT NOT NULL,
	userAccessDate DATETIME NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].role (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].profile (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].hasProfiles (
	idUser BIGINT NOT NULL,
	idProfile BIGINT NOT NULL,
	PRIMARY KEY (idUser, idProfile)
);

CREATE TABLE [SCHEMMA].containsRoles (
	idProfile BIGINT NOT NULL,
	idRole BIGINT NOT NULL,
	PRIMARY KEY (idProfile, idRole)
);

CREATE TABLE [SCHEMMA].message (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	idFrom BIGINT NOT NULL,
	idTo BIGINT,
	date DATETIME,
	PRIMARY KEY (ID)
);

ALTER TABLE [SCHEMMA].hasProfiles
ADD CONSTRAINT fk_user_in_hasProfiles
FOREIGN KEY (idUser)
REFERENCES [SCHEMMA].user(id);

ALTER TABLE [SCHEMMA].hasProfiles
ADD CONSTRAINT fk_profile_in_hasProfiles
FOREIGN KEY (idProfile)
REFERENCES [SCHEMMA].profile(id);

ALTER TABLE [SCHEMMA].containsRoles
ADD CONSTRAINT fk_profile_in_containsRoles
FOREIGN KEY (idProfile)
REFERENCES [SCHEMMA].profile(id);

ALTER TABLE [SCHEMMA].containsRoles
ADD CONSTRAINT fk_role_in_containsRoles
FOREIGN KEY (idRole)
REFERENCES [SCHEMMA].role(id);

ALTER TABLE [SCHEMMA].user
ADD CONSTRAINT fk_userstate_in_user
FOREIGN KEY (idUserState)
REFERENCES [SCHEMMA].userState(id);

ALTER TABLE [SCHEMMA].userAccessHistory
ADD CONSTRAINT fk_user_in_useraccesshistory
FOREIGN KEY (idUser)
REFERENCES [SCHEMMA].user(id);

ALTER TABLE [SCHEMMA].message
ADD CONSTRAINT fk_userfrom_in_message
FOREIGN KEY (idFrom)
REFERENCES [SCHEMMA].user(id);

ALTER TABLE [SCHEMMA].message
ADD CONSTRAINT fk_userto_in_message
FOREIGN KEY (idTo)
REFERENCES [SCHEMMA].user(id);
