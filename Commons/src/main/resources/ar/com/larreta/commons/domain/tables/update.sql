-- Modificamos la tabla de mensajes para que soporte mensajes mucho mas grandes que los actuales
ALTER TABLE [SCHEMMA].message ADD extDescription LONGBLOB;
