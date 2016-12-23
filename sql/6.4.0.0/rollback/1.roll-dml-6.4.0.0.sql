DECLARE
  v_count NUMBER;
BEGIN
  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA= {{schema}} ';

  --  VERSION DE LA BASE
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT like '20160321_v6_4_0_0_%';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM HSP_DATABASE_VERSIONS WHERE NAME_SCRIPT like ''20160321_v6_4_0_0_%''';
    dbms_output.put_line('Se elimino el registro con nombre 20160321_v6_4_0_0 de la tabla HSP_DATABASE_VERSIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre 20160321_v6_4_0_0 de la tabla HSP_DATABASE_VERSIONS');
  END IF;

  --  SE ELIMINA EL REGISTRO de adjusments status
  SELECT COUNT(*)
  INTO v_count
  FROM adjustment_status
  WHERE code = 'AUTH_ING';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM adjustment_status WHERE CODE = ''AUTH_ING''';
    dbms_output.put_line('Se elimino el registro con código AUTH_ING de la tabla adjustment_status.');
  ELSE
    dbms_output.put_line('No existe el registro con código AUTH_ING en la tabla adjustment_status');
  END IF;

  --  
  SELECT COUNT(*)
  INTO v_count
  FROM ERROR_MESSAGES
  WHERE error_key = 'sdii_CODE_ADJUSTMENT_AUTHORIZING';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM ERROR_MESSAGES WHERE error_key = ''sdii_CODE_ADJUSTMENT_AUTHORIZING''';
    dbms_output.put_line('Se elimino el registro con código sdii_CODE_ADJUSTMENT_AUTHORIZING de la tabla error_messages.');
  ELSE
    dbms_output.put_line('No existe el registro con código sdii_CODE_ADJUSTMENT_AUTHORIZING de la tabla error_messages');
  END IF;
  
commit;
  
END;
/