DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA= {{schema}}';

      --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'CREW_MODIFICATIONS_IDX01'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX CREW_MODIFICATIONS_IDX01';
    dbms_output.put_line('Se elimino el indice CREW_MODIFICATIONS_IDX01.');
  ELSE
    dbms_output.put_line('El indice CREW_MODIFICATIONS_IDX01 no existe');
  END IF;
  
      --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'CREW_MODIFICATIONS_IDX02'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX CREW_MODIFICATIONS_IDX02';
    dbms_output.put_line('Se elimino el indice CREW_MODIFICATIONS_IDX02.');
  ELSE
    dbms_output.put_line('El indice CREW_MODIFICATIONS_IDX02 no existe');
  END IF;

    
    --  CONSTRAIN
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'CREW_MOVEMENTS_VEHICLE_FK'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'ALTER TABLE "CREW_MODIFICATIONS" DROP CONSTRAINT "CREW_MOVEMENTS_VEHICLE_FK" ';
    dbms_output.put_line('Se elimino la constrain CREW_MOVEMENTS_VEHICLE_FK.');
  ELSE
    dbms_output.put_line('La constrain CREW_MOVEMENTS_VEHICLE_FK no existe');
  END IF;

  --  CONSTRAIN
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'CREW_MOVEMENTS_USERS_FK'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'ALTER TABLE "CREW_MODIFICATIONS" DROP CONSTRAINT "CREW_MOVEMENTS_USERS_FK" ';
    dbms_output.put_line('Se elimino la constrain CREW_MOVEMENTS_USERS_FK.');
  ELSE
    dbms_output.put_line('La constrain CREW_MOVEMENTS_USERS_FK no existe');
  END IF;
  
  --  PRIMARY KEY
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'MODIFICATION_CREW_DATE_PK'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'ALTER TABLE CREW_MODIFICATIONS DROP CONSTRAINT MODIFICATION_CREW_DATE_PK';
    dbms_output.put_line('Se elimino la constrain MODIFICATION_CREW_DATE_PK.');
  ELSE
    dbms_output.put_line('La constrain MODIFICATION_CREW_DATE_PK no existe');
  END IF;
  
    --  TABLA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_TABLES
  WHERE TABLE_NAME = 'CREW_MODIFICATIONS'
  AND owner        ='{{schema}}';	
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP TABLE CREW_MODIFICATIONS';
    dbms_output.put_line('Se elimino la tabla CREW_MODIFICATIONS.');
  ELSE
    dbms_output.put_line('La tabla CREW_MODIFICATIONS no existe');
  END IF;

    -- SECUENCIA
    SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SEQUENCES
  WHERE SEQUENCE_NAME = 'SEQ_CREW_MODIFICATIONS'
  AND SEQUENCE_OWNER  = '{{schema}}';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP SEQUENCE SEQ_CREW_MODIFICATIONS';
    dbms_output.put_line('Se elimino la secuencia SEQ_CREW_MODIFICATIONS.');
  ELSE
    dbms_output.put_line('La tabla SEQ_CREW_MODIFICATIONS no existe');
  END IF;  

 
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX01'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX WO_CREW_ATTENTIONS_IDX01';
    dbms_output.put_line('Se elimino el indice WO_CREW_ATTENTIONS_IDX01.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX01 no existe');
  END IF;
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX02'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX WO_CREW_ATTENTIONS_IDX02';
    dbms_output.put_line('Se elimino el indice WO_CREW_ATTENTIONS_IDX02.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX02 no existe');
  END IF;
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX03'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX WO_CREW_ATTENTIONS_IDX03';
    dbms_output.put_line('Se elimino el indice WO_CREW_ATTENTIONS_IDX03.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX03 no existe');
  END IF;

  -- PRIMARY KEY
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'WORK_ORDER_CREW_ATTENTIONS_PK'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'ALTER TABLE WORK_ORDER_CREW_ATTENTIONS DROP CONSTRAINT WORK_ORDER_CREW_ATTENTIONS_PK';
    dbms_output.put_line('Se elimino la constrain WORK_ORDER_CREW_ATTENTIONS_PK.');
  ELSE
    dbms_output.put_line('La constrain WORK_ORDER_CREW_ATTENTIONS_PK no existe');
  END IF;
  
  
    --  TABLA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_TABLES
  WHERE TABLE_NAME = 'WORK_ORDER_CREW_ATTENTIONS'
  AND owner        ='{{schema}}';	
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP TABLE WORK_ORDER_CREW_ATTENTIONS';
    dbms_output.put_line('Se elimino la tabla WORK_ORDER_CREW_ATTENTIONS.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDER_CREW_ATTENTIONS no existe');
  END IF;

    -- SECUENCIA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SEQUENCES
  WHERE SEQUENCE_NAME = 'SEQ_WORK_ORDER_CREW_ATTENTIONS'
  AND SEQUENCE_OWNER  = '{{schema}}';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP SEQUENCE SEQ_WORK_ORDER_CREW_ATTENTIONS';
    dbms_output.put_line('Se elimino la secuencia SEQ_WORK_ORDER_CREW_ATTENTIONS.');
  ELSE
    dbms_output.put_line('La tabla SEQ_WORK_ORDER_CREW_ATTENTIONS no existe');
  END IF;

    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'ADJUSTMENT_ELEMENTS_IDX01'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX ADJUSTMENT_ELEMENTS_IDX01';
    dbms_output.put_line('Se elimino el indice ADJUSTMENT_ELEMENTS_IDX01.');
  ELSE
    dbms_output.put_line('El indice ADJUSTMENT_ELEMENTS_IDX01 no existe');
  END IF;

    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WAREHOUSE_ELEMENTS_IDX13'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX WAREHOUSE_ELEMENTS_IDX13';
    dbms_output.put_line('Se elimino el indice WAREHOUSE_ELEMENTS_IDX13.');
  ELSE
    dbms_output.put_line('El indice WAREHOUSE_ELEMENTS_IDX13 no existe');
  END IF;
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'UPLOAD_FILE_PARAM_IDX1'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX UPLOAD_FILE_PARAM_IDX1';
    dbms_output.put_line('Se elimino el indice UPLOAD_FILE_PARAM_IDX1.');
  ELSE
    dbms_output.put_line('El indice UPLOAD_FILE_PARAM_IDX1 no existe');
  END IF;
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'FILE_DETAIL_PROCESSES_IDX1'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX FILE_DETAIL_PROCESSES_IDX1';
    dbms_output.put_line('Se elimino el indice FILE_DETAIL_PROCESSES_IDX1.');
  ELSE
    dbms_output.put_line('El indice FILE_DETAIL_PROCESSES_IDX1 no existe');
  END IF;  
  
    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'ADJUSTMENT_ELEMENTS_IDX02'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX ADJUSTMENT_ELEMENTS_IDX02';
    dbms_output.put_line('Se elimino el indice ADJUSTMENT_ELEMENTS_IDX02.');
  ELSE
    dbms_output.put_line('El indice ADJUSTMENT_ELEMENTS_IDX02 no existe');
  END IF;  

    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WAREHOUSE_ELEMENTS_IDX3'
  AND owner        ='{{schema}}';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP INDEX WAREHOUSE_ELEMENTS_IDX3';
	EXECUTE immediate 'CREATE INDEX WAREHOUSE_ELEMENTS_IDX3 on WAREHOUSE_ELEMENTS(RECORD_STATUS_ID)';
    dbms_output.put_line('Se elimino el indice WAREHOUSE_ELEMENTS_IDX3, y se creo el indice a la columna record_status_id.');
  ELSE
    dbms_output.put_line('El indice WAREHOUSE_ELEMENTS_IDX3 no existe');
  END IF;  
  
  commit;
  END;
/