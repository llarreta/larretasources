DECLARE

v_count NUMBER;

BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
    
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT = '20160321_v6_4_0_0_EC';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'INSERT INTO HSP_DATABASE_VERSIONS (ID, NAME_SCRIPT, DATE_EXECUTED) VALUES (SEQ_HSP_DATABASE_VERSIONS.NEXTVAL, ''20160321_v6_4_0_0_EC'', SYSDATE)';
    dbms_output.put_line('Se actualizo la version de la BD a la version 6.4.0.0.');
  ELSE
    dbms_output.put_line('Ya existe el registro con nombre 20160321_v6_4_0_0_EC');
  END IF;
 
commit;
  
END;
/