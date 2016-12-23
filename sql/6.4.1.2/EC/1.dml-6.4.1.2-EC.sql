DECLARE

v_count NUMBER;

BEGIN

  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
    
  SELECT COUNT(*)
  INTO v_count
  FROM SYSTEM_PARAMETERS
  WHERE PARAMETER_NAME = 'IS_CUSTOMER_INFO_MASK';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'INSERT INTO SYSTEM_PARAMETERS(ID,PARAMETER_NAME,VALUE,PARAMETER_CODE,PARAMETER_TYPE_ID,COUNTRY_ID) VALUES (SEQ_SYSTEM_PARAMETERS.NEXTVAL,''IS_CUSTOMER_INFO_MASK'',''N'',''0130'',(SELECT ID FROM PARAMETER_TYPES WHERE PARAMETER_TYPE_CODE=''03''),(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = ''EC''))';
    dbms_output.put_line('Se creo el registro con nombre de parametro IS_CUSTOMER_INFO_MASK en la tabla SYSTEM_PARAMETERS .');
  ELSE
    dbms_output.put_line('Ya existe el registro con nombre de parametro IS_CUSTOMER_INFO_MASK en la tabla SYSTEM_PARAMETERS');
  END IF;

  
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT = '20160413_v6_4_1_2_EC';
  
  IF v_count       = 0 THEN
    EXECUTE immediate 'INSERT INTO HSP_DATABASE_VERSIONS (ID, NAME_SCRIPT, DATE_EXECUTED) VALUES (SEQ_HSP_DATABASE_VERSIONS.NEXTVAL, ''20160413_v6_4_1_2_EC'', SYSDATE)';
    dbms_output.put_line('Se actualizo la version de la BD a la version 6.4.1.2');
  ELSE
    dbms_output.put_line('Ya existe el registro con nombre 20160413_v6_4_1_2_EC');
  END IF;
 
commit;
  
END;
/