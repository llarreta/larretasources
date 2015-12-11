CREATE TABLE [SCHEMMA].provider (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].unitType (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].material (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	idUnitType BIGINT NOT NULL,
	count BIGINT NOT NULL,
	idProvider BIGINT NOT NULL,
	idMaterialType BIGINT NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].materialType (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].container (
	id BIGINT NOT NULL,
	deleted DATETIME,
	code VARCHAR(255),
	description VARCHAR(255),
	idMaterial BIGINT NOT NULL,
	count BIGINT NOT NULL,
	elaboration DATETIME,
	expiration DATETIME,
	idParent BIGINT NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].step (
	id BIGINT NOT NULL,
	deleted DATETIME,
	description VARCHAR(255),
	execution DATETIME,
	PRIMARY KEY (ID)
);

CREATE TABLE [SCHEMMA].source (
	idStep BIGINT NOT NULL,
	idContainerSource BIGINT NOT NULL
);

CREATE TABLE [SCHEMMA].target (
	idStep BIGINT NOT NULL,
	idContainerTarget BIGINT NOT NULL
);

ALTER TABLE [SCHEMMA].material
ADD CONSTRAINT fk_unitType_in_material
FOREIGN KEY (idUnitType)
REFERENCES [SCHEMMA].unitType(id);

ALTER TABLE [SCHEMMA].material
ADD CONSTRAINT fk_provider_in_material
FOREIGN KEY (idProvider)
REFERENCES [SCHEMMA].provider(id);

ALTER TABLE [SCHEMMA].container
ADD CONSTRAINT fk_material_in_container
FOREIGN KEY (idMaterial)
REFERENCES [SCHEMMA].material(id);

ALTER TABLE [SCHEMMA].container
ADD CONSTRAINT fk_containerParent_in_container
FOREIGN KEY (idParent)
REFERENCES [SCHEMMA].container(id);

ALTER TABLE [SCHEMMA].source
ADD CONSTRAINT fk_step_in_source
FOREIGN KEY (idStep)
REFERENCES [SCHEMMA].step(id);

ALTER TABLE [SCHEMMA].source
ADD CONSTRAINT fk_container_in_source
FOREIGN KEY (idContainerSource)
REFERENCES [SCHEMMA].container(id);

ALTER TABLE [SCHEMMA].target
ADD CONSTRAINT fk_step_in_target
FOREIGN KEY (idStep)
REFERENCES [SCHEMMA].step(id);

ALTER TABLE [SCHEMMA].target
ADD CONSTRAINT fk_container_in_target
FOREIGN KEY (idContainerTarget)
REFERENCES [SCHEMMA].container(id);

ALTER TABLE [SCHEMMA].material
ADD CONSTRAINT fk_materialType_in_material
FOREIGN KEY (idMaterialType)
REFERENCES [SCHEMMA].materialType(id);
