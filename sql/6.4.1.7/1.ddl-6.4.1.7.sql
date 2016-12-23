DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
  
  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'WORK_ORDERS_EXPORT'
  AND owner        = '{{schema}}';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'CREATE TABLE {{schema}}.WORK_ORDERS_EXPORT 
(
  ID NUMBER NOT NULL 
, CREATION_DATE TIMESTAMP
, NUM_WO NUMBER
, USER_ID NUMBER(38)
)';
    dbms_output.put_line('Se creo la tabla WORK_ORDERS_EXPORT.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDERS_EXPORT ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WORK_ORDERS_EXPORT_PK'
  AND owner        ='{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX {{schema}}.WORK_ORDERS_EXPORT_PK ON {{schema}}.WORK_ORDERS_EXPORT(ID)';
    dbms_output.put_line('Se creo el indice WORK_ORDERS_EXPORT_PK.');
  ELSE
    dbms_output.put_line('El indice WORK_ORDERS_EXPORT_PK ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WORK_ORDERS_EXPORT_IDX1'
  AND owner        ='{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX {{schema}}.WORK_ORDERS_EXPORT_IDX1 ON {{schema}}.WORK_ORDERS_EXPORT(CREATION_DATE)';
    dbms_output.put_line('Se creo el indice WORK_ORDERS_EXPORT_IDX1.');
  ELSE
    dbms_output.put_line('El indice WORK_ORDERS_EXPORT_IDX1 ya existe');
  END IF;  
  
  --  CONSTRAINTS
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'WORK_ORDERS_EXPORT_PK'
  AND owner        = '{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE {{schema}}.WORK_ORDERS_EXPORT ADD CONSTRAINT WORK_ORDERS_EXPORT_PK PRIMARY KEY (ID) USING INDEX {{schema}}.WORK_ORDERS_EXPORT_PK ENABLE';
    dbms_output.put_line('Se creo la constrain WORK_ORDERS_EXPORT_PK.');
  ELSE
    dbms_output.put_line('La constrain WORK_ORDERS_EXPORT_PK ya existe');
  END IF;  
  
  --  CONSTRAINTS
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'WORK_ORDERS_EXPORT_FK1'
  AND owner        = '{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE {{schema}}.WORK_ORDERS_EXPORT ADD CONSTRAINT WORK_ORDERS_EXPORT_FK1 FOREIGN KEY(USER_ID) REFERENCES {{schema}}.USERS (ID) ON DELETE CASCADE ENABLE';
    dbms_output.put_line('Se creo la constrain WORK_ORDERS_EXPORT_FK1.');
  ELSE
    dbms_output.put_line('La constrain WORK_ORDERS_EXPORT_FK1 ya existe');
  END IF;   
  
  
-- TABLA   
  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'WORK_ORDERS_EXPORT_DATA'
  AND owner        = '{{schema}}';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'CREATE TABLE {{schema}}.WORK_ORDERS_EXPORT_DATA
                      (WORK_ORDERS_EXPORT_ID NUMBER NOT NULL ENABLE, 
                      WO_ID NUMBER NOT NULL ENABLE)';
    dbms_output.put_line('Se creo la tabla WORK_ORDERS_EXPORT_DATA.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDERS_EXPORT_DATA ya existe');
  END IF;
  
  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WORK_ORDERS_EXPORT_DATA_IDX1'
  AND owner        ='{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX {{schema}}.WORK_ORDERS_EXPORT_DATA_IDX1 ON {{schema}}.WORK_ORDERS_EXPORT_DATA (WORK_ORDERS_EXPORT_ID)';
    dbms_output.put_line('Se creo el indice WORK_ORDERS_EXPORT_DATA_IDX1.');
  ELSE
    dbms_output.put_line('El indice WORK_ORDERS_EXPORT_DATA_IDX1 ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WORK_ORDERS_EXPORT_DATA_IDX2'
  AND owner        ='{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX {{schema}}.WORK_ORDERS_EXPORT_DATA_IDX2 ON {{schema}}.WORK_ORDERS_EXPORT_DATA (WORK_ORDERS_EXPORT_ID,WO_ID)';
    dbms_output.put_line('Se creo el indice WORK_ORDERS_EXPORT_DATA_IDX2.');
  ELSE
    dbms_output.put_line('El indice WORK_ORDERS_EXPORT_DATA_IDX2 ya existe');
  END IF;  
 

  --  CONSTRAINTS
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'WORK_ORDERS_EXPORT_DATA_FK1'
  AND owner        = '{{schema}}';
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE {{schema}}.WORK_ORDERS_EXPORT_DATA ADD CONSTRAINT WORK_ORDERS_EXPORT_DATA_FK1 FOREIGN KEY(WORK_ORDERS_EXPORT_ID) REFERENCES {{schema}}.WORK_ORDERS_EXPORT (ID) ON DELETE CASCADE ENABLE';
    dbms_output.put_line('Se creo la constrain WORK_ORDERS_EXPORT_DATA_FK1.');
  ELSE
    dbms_output.put_line('La constrain WORK_ORDERS_EXPORT_DATA_FK1 ya existe');
  END IF;


  --  JOBS
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SCHEDULER_JOBS
  WHERE OWNER = '{{schema}}'
  AND JOB_NAME = 'BORRADO_WO_EXPORT_DATA';
  
  IF v_count       = 0 THEN
	--EXECUTE immediate '';
	    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"{{schema}}"."BORRADO_WO_EXPORT_DATA"',
            job_type => 'PLSQL_BLOCK',
            job_action => 'DECLARE
  cursor id_ejecuciones is
  SELECT WO_EX.ID FROM WORK_ORDERS_EXPORT WO_EX 
WHERE WO_EX.CREATION_DATE < (SYSDATE - 2/24)
AND EXISTS (SELECT WO_EX_DATA.EXP_ID FROM WORK_ORDERS_EXPORT_DATA WO_EX_DATA WHERE WO_EX_DATA.EXP_ID = WO_EX.ID)
ORDER BY ID ASC;
BEGIN 
  DBMS_OUTPUT.ENABLE;
  for id_ex in id_ejecuciones loop
    DELETE FROM WORK_ORDERS_EXPORT_DATA WO_EX_DATA WHERE WO_EX_DATA.EXP_ID = id_ex.id;
    COMMIT;
  end loop;
END;',
            number_of_arguments => 0,
            start_date => NULL,
            repeat_interval => 'FREQ=DAILY;BYHOUR=0;BYMINUTE=15;BYSECOND=0',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'Job Borrado Datos Tabla WORK_ORDERS_EXPORT_DATA');

    DBMS_SCHEDULER.SET_ATTRIBUTE( 
            name => '"{{schema}}"."BORRADO_WO_EXPORT_DATA"', 
            attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);			
			
    DBMS_SCHEDULER.enable(
            name => '"{{schema}}"."BORRADO_WO_EXPORT_DATA"');
			
    dbms_output.put_line('Se creo el job BORRADO_WO_EXPORT_DATA.');
  ELSE
    dbms_output.put_line('No existe el Job con nombre BORRADO_WO_EXPORT_DATA');
  END IF;
    
  commit;
END;
/
