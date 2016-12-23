DECLARE

v_count NUMBER;

BEGIN

  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';
  
   --  VERSION DE LA BASE
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT like '20160816_v6_4_1_7_%';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM {{schema}}.HSP_DATABASE_VERSIONS WHERE NAME_SCRIPT like ''20160816_v6_4_1_7_%''';
    dbms_output.put_line('Se elimino el registro con nombre 20160816_v6_4_1_7 de la tabla HSP_DATABASE_VERSIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre 20160816_v6_4_1_7 de la tabla HSP_DATABASE_VERSIONS');
  END IF; 
  
commit;
  
END;
/