DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
 
  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'WORK_ORDERS_EXPORT_DATA'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
    EXECUTE immediate 'DROP TABLE {{schema}}.WORK_ORDERS_EXPORT_DATA';
    dbms_output.put_line('Se elimino la tabla WORK_ORDERS_EXPORT_DATA.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDERS_EXPORT_DATA no existe');
  END IF;

  SELECT COUNT(*)
  INTO v_count
  FROM sys.all_tables
  WHERE table_name = 'WORK_ORDERS_EXPORT'
  AND owner        = '{{schema}}';
  
  IF v_count       = 1 THEN
    EXECUTE immediate 'DROP TABLE {{schema}}.WORK_ORDERS_EXPORT';
    dbms_output.put_line('Se elimino la tabla WORK_ORDERS_EXPORT.');
  ELSE
    dbms_output.put_line('La tabla WORK_ORDERS_EXPORT no existe');
  END IF;
 
  -- SECUENCIA
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SEQUENCES
  WHERE SEQUENCE_NAME = 'SEQ_WORK_ORDERS_EXPORT'
  AND SEQUENCE_OWNER  = '{{schema}}';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DROP SEQUENCE SEQ_WORK_ORDERS_EXPORT';
    dbms_output.put_line('Se elimino la secuencia SEQ_WORK_ORDERS_EXPORT.');
  ELSE
    dbms_output.put_line('La tabla SEQ_WORK_ORDERS_EXPORT no existe');
  END IF;

  --  JOBS
  SELECT COUNT(*)
  INTO v_count
  FROM SYS.ALL_SCHEDULER_JOBS
  WHERE OWNER = '{{schema}}'
  AND JOB_NAME = 'BORRADO_WO_EXPORT_DATA';

    IF v_count       = 1 THEN
	/*EXECUTE immediate 'DBMS_SCHEDULER.DROP_JOB (job_name => ''"{{schema}}"."BORRADO_WO_EXPORT_DATA"'' ');*/
	DBMS_SCHEDULER.DROP_JOB (job_name => '"{{schema}}"."BORRADO_WO_EXPORT_DATA"');
    dbms_output.put_line('Se elimino el job BORRADO_WO_EXPORT_DATA');
  ELSE
    dbms_output.put_line('El job BORRADO_WO_EXPORT_DATA no existe');
  END IF;
  
  commit;
END;
/