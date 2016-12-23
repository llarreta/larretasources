DECLARE

v_count NUMBER;

BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
    
  SELECT COUNT(*)
  INTO v_count
  FROM adjustment_status
  WHERE code = 'AUTH_ING';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'insert into adjustment_status (id, code, name) values (seq_adjustment_status.nextval, ''AUTH_ING'', ''Autorizando'')';
    dbms_output.put_line('Se creo el registro con codigo AUTH_ING en la tabla ADJUSTMENT_STATUS .');
  ELSE
    dbms_output.put_line('Ya existe el registro con codigo AUTH_ING');
  END IF;
  
  
  SELECT COUNT(*)
  INTO v_count
  FROM ERROR_MESSAGES
  WHERE ERROR_KEY = 'sdii_CODE_ADJUSTMENT_AUTHORIZING';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'INSERT INTO ERROR_MESSAGES (ID, COMPONENT_ID, ERROR_KEY,  ERROR_CODE, ERROR_MESSAGE) VALUES (SEQ_ERROR_MESSAGE.NEXTVAL, (select id from ERROR_CATEGORIES where category_code=''IN''),''sdii_CODE_ADJUSTMENT_AUTHORIZING'', ''IN700'',''El ajuste se encuentra procesando.'')';
    dbms_output.put_line('Se creo el registro con codigo sdii_CODE_ADJUSTMENT_AUTHORIZING en la tabla ERROR_MESSAGES .');
  ELSE
    dbms_output.put_line('Ya existe el registro con codigo sdii_CODE_ADJUSTMENT_AUTHORIZING');
  END IF;  

commit;
  
END;
/