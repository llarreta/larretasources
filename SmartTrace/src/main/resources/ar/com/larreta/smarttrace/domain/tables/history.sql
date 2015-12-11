CREATE TABLE [SCHEMMA].materialChangeHistory (
	id BIGINT NOT NULL,
	deleted DATETIME,
	changeAction VARCHAR(255),
	changeDate DATETIME NOT NULL,
	idAuditableEntity BIGINT,
	entityValues VARCHAR(255),
	idUser BIGINT,
	PRIMARY KEY (ID)
);

ALTER TABLE [SCHEMMA].materialChangeHistory
ADD CONSTRAINT fk_material_in_materialChangeHistory
FOREIGN KEY (idAuditableEntity)
REFERENCES [SCHEMMA].material(id);

ALTER TABLE [SCHEMMA].materialChangeHistory
ADD CONSTRAINT fk_user_in_materialChangeHistory
FOREIGN KEY (idUser)
REFERENCES [SCHEMMA].user(id);

CREATE TABLE [SCHEMMA].containerChangeHistory (
	id BIGINT NOT NULL,
	deleted DATETIME,
	changeAction VARCHAR(255),
	changeDate DATETIME NOT NULL,
	idAuditableEntity BIGINT,
	entityValues VARCHAR(255),
	idUser BIGINT,
	PRIMARY KEY (ID)
);

ALTER TABLE [SCHEMMA].containerChangeHistory
ADD CONSTRAINT fk_container_in_materialChangeHistory
FOREIGN KEY (idAuditableEntity)
REFERENCES [SCHEMMA].container(id);

ALTER TABLE [SCHEMMA].containerChangeHistory
ADD CONSTRAINT fk_user_in_containerChangeHistory
FOREIGN KEY (idUser)
REFERENCES [SCHEMMA].user(id);

CREATE TABLE [SCHEMMA].stepChangeHistory (
	id BIGINT NOT NULL,
	deleted DATETIME,
	changeAction VARCHAR(255),
	changeDate DATETIME NOT NULL,
	idAuditableEntity BIGINT,
	entityValues VARCHAR(255),
	idUser BIGINT,
	PRIMARY KEY (ID)
);

ALTER TABLE [SCHEMMA].stepChangeHistory
ADD CONSTRAINT fk_step_in_materialChangeHistory
FOREIGN KEY (idAuditableEntity)
REFERENCES [SCHEMMA].step(id);

ALTER TABLE [SCHEMMA].stepChangeHistory
ADD CONSTRAINT fk_user_in_stepChangeHistory
FOREIGN KEY (idUser)
REFERENCES [SCHEMMA].user(id);