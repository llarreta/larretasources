DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA= {{schema}} ';

  --  VERSION DE LA BASE
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT like '20160413_v6_4_1_2_%';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM HSP_DATABASE_VERSIONS WHERE NAME_SCRIPT like ''20160413_v6_4_1_2_%''';
    dbms_output.put_line('Se elimino el registro con nombre 20160413_v6_4_1_2 de la tabla HSP_DATABASE_VERSIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre 20160413_v6_4_1_2 de la tabla HSP_DATABASE_VERSIONS');
  END IF;

  --  
  SELECT COUNT(*)
  INTO v_count
  FROM SYSTEM_PARAMETERS
  WHERE PARAMETER_NAME = 'IS_CUSTOMER_INFO_MASK';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM SYSTEM_PARAMETERS WHERE PARAMETER_NAME = ''IS_CUSTOMER_INFO_MASK''';
    dbms_output.put_line('Se elimino el registro con nombre de parametro IS_CUSTOMER_INFO_MASK en la tabla SYSTEM_PARAMETERS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre de parametro IS_CUSTOMER_INFO_MASK en la tabla SYSTEM_PARAMETERS');
  END IF;
  
commit;
  
END;