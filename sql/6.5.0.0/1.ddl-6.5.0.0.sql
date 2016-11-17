DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
  
  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'CREW_MODIFICATIONS'
  AND owner        = {{schema}};
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'CREATE TABLE CREW_MODIFICATIONS (	
    "ID" NUMBER(*,0),
    "CREW_ID" NUMBER(38),
    "DEALER_NAME" VARCHAR2(500),
    "DEALER" VARCHAR2(500),
    "MEMBERS_CREW" VARCHAR2(4000),
    "DOCUMENT_NUMBER" VARCHAR2(4000),
    "RESPONSIBLE_CREW" VARCHAR2(200),
    "ROL_MEMBERS_CREW" VARCHAR2(4000),
    "CREATION_DATE" TIMESTAMP(6),
    "ACTIVATION_CREW_DATE" TIMESTAMP(6),
    "MODIFICATION_CREW_DATE" TIMESTAMP(6),
    "NEW_MEMBER" VARCHAR2(200),
    "DOC_NUMBER_NEW_MEMBER" VARCHAR2(200),
    "NEW_RESPONSIBLE_CREW" VARCHAR2(200),
    "ROL_NEW_MEMBER" VARCHAR2(200),
    "LOCATION_CODE" VARCHAR2(200),
    "CREW_STATUS" VARCHAR2(200),
    "VEHICLE_ID"  NUMBER(38) NOT NULL,
    "USER_ID" NUMBER(38) NOT NULL
   )';
    dbms_output.put_line('Se creo la tabla CREW_MODIFICATIONS.');
  ELSE
    dbms_output.put_line('La tabla CREW_MODIFICATIONS ya existe');
  END IF;

  --  
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'MODIFICATION_CREW_DATE_PK'
  AND owner        = {{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE CREW_MODIFICATIONS ADD CONSTRAINT MODIFICATION_CREW_DATE_PK PRIMARY KEY (id) enable';
    dbms_output.put_line('Se creo la constrain MODIFICATION_CREW_DATE_PK.');
  ELSE
    dbms_output.put_line('La constrain MODIFICATION_CREW_DATE_PK ya existe');
  END IF;

  --  SECUENCIA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SEQUENCES
  WHERE SEQUENCE_NAME = 'SEQ_CREW_MODIFICATIONS'
  AND SEQUENCE_OWNER = {{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE SEQUENCE  "SEQ_CREW_MODIFICATIONS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE';
    dbms_output.put_line('Se creo la secuencia SEQ_CREW_MODIFICATIONS.');
  ELSE
    dbms_output.put_line('La secuencia SEQ_CREW_MODIFICATIONS ya existe');
  END IF;

    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'CREW_MODIFICATIONS_IDX01'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX CREW_MODIFICATIONS_IDX01 ON CREW_MODIFICATIONS ( MODIFICATION_CREW_DATE DESC)';
    dbms_output.put_line('Se creo el indice CREW_MODIFICATIONS_IDX01.');
  ELSE
    dbms_output.put_line('El indice CREW_MODIFICATIONS_IDX01 ya existe');
  END IF;

    --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'CREW_MODIFICATIONS_IDX02'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX CREW_MODIFICATIONS_IDX02 ON CREW_MODIFICATIONS (trunc(MODIFICATION_CREW_DATE))';
    dbms_output.put_line('Se creo el indice CREW_MODIFICATIONS_IDX02.');
  ELSE
    dbms_output.put_line('El indice CREW_MODIFICATIONS_IDX02 ya existe');
  END IF;
  
  --  CONSTRAIN
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'CREW_MOVEMENTS_VEHICLE_FK'
  AND owner        = {{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE "CREW_MODIFICATIONS" ADD CONSTRAINT "CREW_MOVEMENTS_VEHICLE_FK" FOREIGN KEY ("VEHICLE_ID") REFERENCES "VEHICLES" ("ID") ENABLE';
    dbms_output.put_line('Se creo la constrain CREW_MOVEMENTS_VEHICLE_FK.');
  ELSE
    dbms_output.put_line('La constrain CREW_MOVEMENTS_VEHICLE_FK ya existe');
  END IF;

  --  CONSTRAIN
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_CONSTRAINTS
  WHERE CONSTRAINT_NAME = 'CREW_MOVEMENTS_USERS_FK'
  AND owner        = {{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'ALTER TABLE "CREW_MODIFICATIONS" ADD CONSTRAINT "CREW_MOVEMENTS_USERS_FK" FOREIGN KEY ("USER_ID") REFERENCES "USERS" ("ID") ENABLE';
    dbms_output.put_line('Se creo la constrain CREW_MOVEMENTS_USERS_FK.');
  ELSE
    dbms_output.put_line('La constrain CREW_MOVEMENTS_USERS_FK ya existe');
  END IF;

  --  TABLA
  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'WORK_ORDER_CREW_ATTENTIONS'
  AND owner        = {{schema}};
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'CREATE TABLE WORK_ORDER_CREW_ATTENTIONS 
   (	
    "ID" NUMBER(*,0), 
    "DEALER_NAME" VARCHAR2(500),
    "DEALER" VARCHAR2(500),
    "WO_CODE" VARCHAR2(50),
    "CREW_ID" NUMBER(38),
    "MEMBERS_CREW" VARCHAR2(4000),
    "NAME_TECHNICIAL_ATTENTION" VARCHAR2(100),
    "DOCUMENT_NUMBER" VARCHAR2(200),
    "IBS_TECHNICIAL" VARCHAR2(200),
    "ATTENTION_DATE" TIMESTAMP(6)
   ) ';
    dbms_output.put_line('Se creo la tabla WORK_ORDER_CREW_ATTENTIONS.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDER_CREW_ATTENTIONS ya existe');
  END IF;

  --  SECUENCIA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SEQUENCES
  WHERE SEQUENCE_NAME = 'SEQ_WORK_ORDER_CREW_ATTENTIONS'
  AND SEQUENCE_OWNER = {{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE SEQUENCE  "SEQ_WORK_ORDER_CREW_ATTENTIONS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE';
    dbms_output.put_line('Se creo la secuencia SEQ_WORK_ORDER_CREW_ATTENTIONS.');
  ELSE
    dbms_output.put_line('La secuencia SEQ_WORK_ORDER_CREW_ATTENTIONS ya existe');
  END IF;
		
  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX01'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX WO_CREW_ATTENTIONS_IDX01 ON WORK_ORDER_CREW_ATTENTIONS ( ATTENTION_DATE DESC)';
    dbms_output.put_line('Se creo el indice WO_CREW_ATTENTIONS_IDX01.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX01 ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX02'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX WO_CREW_ATTENTIONS_IDX02 ON WORK_ORDER_CREW_ATTENTIONS ( WO_CODE )';
    dbms_output.put_line('Se creo el indice WO_CREW_ATTENTIONS_IDX02.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX02 ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WO_CREW_ATTENTIONS_IDX03'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX WO_CREW_ATTENTIONS_IDX03 ON WORK_ORDER_CREW_ATTENTIONS (TRUNC(ATTENTION_DATE))';
    dbms_output.put_line('Se creo el indice WO_CREW_ATTENTIONS_IDX03.');
  ELSE
    dbms_output.put_line('El indice WO_CREW_ATTENTIONS_IDX03 ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'ADJUSTMENT_ELEMENTS_IDX01'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX ADJUSTMENT_ELEMENTS_IDX01 ON ADJUSTMENT_ELEMENTS (ADJUSTMENT_ID)';
    dbms_output.put_line('Se creo el indice ADJUSTMENT_ELEMENTS_IDX01.');
  ELSE
    dbms_output.put_line('El indice ADJUSTMENT_ELEMENTS_IDX01 ya existe');
  END IF;
  
  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WAREHOUSE_ELEMENTS_IDX13'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX WAREHOUSE_ELEMENTS_IDX13 ON WAREHOUSE_ELEMENTS (WAREHOUSE_ID,RECORD_STATUS_ID)';
    dbms_output.put_line('Se creo el indice WAREHOUSE_ELEMENTS_IDX13.');
  ELSE
    dbms_output.put_line('El indice WAREHOUSE_ELEMENTS_IDX13 ya existe');
  END IF;  

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'UPLOAD_FILE_PARAM_IDX1'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX UPLOAD_FILE_PARAM_IDX1 ON UPLOAD_FILE_PARAMS("UPLOAD_FILE_ID")';
    dbms_output.put_line('Se creo el indice UPLOAD_FILE_PARAM_IDX1.');
  ELSE
    dbms_output.put_line('El indice UPLOAD_FILE_PARAM_IDX1 ya existe');
  END IF;  
  
  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'FILE_DETAIL_PROCESSES_IDX1'
  AND owner        ={{schema}};
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX FILE_DETAIL_PROCESSES_IDX1 on FILE_DETAIL_PROCESSES("FILE_ID")';
    dbms_output.put_line('Se creo el indice FILE_DETAIL_PROCESSES_IDX1.');
  ELSE
    dbms_output.put_line('El indice FILE_DETAIL_PROCESSES_IDX1 ya existe');
  END IF;

  --  INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'ADJUSTMENT_ELEMENTS_IDX02'
  AND owner        = {{schema}} ;
  
  IF v_count       = 0 THEN
	EXECUTE immediate 'CREATE INDEX ADJUSTMENT_ELEMENTS_IDX02 ON ADJUSTMENT_ELEMENTS ( WAREHOUSE_DESTINATION_ID  )';
    dbms_output.put_line('Se creo el indice ADJUSTMENT_ELEMENTS_IDX02.');
  ELSE
    dbms_output.put_line('El indice ADJUSTMENT_ELEMENTS_IDX02 ya existe');
  END IF;
  
  -- INDICE
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_INDEXES
  WHERE INDEX_NAME = 'WAREHOUSE_ELEMENTS_IDX3'
  AND owner        = {{schema}} ;
  
  IF v_count       = 1 THEN
  	EXECUTE immediate 'DROP INDEX WAREHOUSE_ELEMENTS_IDX3';
  END IF;
  
  EXECUTE immediate 'create index WAREHOUSE_ELEMENTS_IDX3 on WAREHOUSE_ELEMENTS(RECORD_STATUS_ID,WAREHOUSE_ID,NOT_SER_ID,SER_ID)';
  
  
END;
