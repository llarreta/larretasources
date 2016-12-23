declare

v_count number;
	
begin

  EXECUTE immediate 'ALTER SESSION SET CURRENT_SCHEMA = {{schema}} ';

  DELETE FROM SCHEDULE_REPORT_LOGS
  WHERE SCHEDULE_REPORT_ID IN
  (SELECT id
  FROM {{schema}}.SCHEDULE_REPORTS
  WHERE REPORT_TYPE_ID =(SELECT id FROM {{schema}}.SCHEDULE_REPORT_TYPES WHERE code = 'CM')
  );

  DELETE
  FROM {{schema}}.SCHEDULE_REPORTS
  WHERE REPORT_TYPE_ID =(SELECT id FROM {{schema}}.SCHEDULE_REPORT_TYPES WHERE code = 'CM');

   
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.SCHEDULE_REPORT_TYPES
  WHERE code = 'CM';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.SCHEDULE_REPORT_TYPES where code = ''CM'' ';
    dbms_output.put_line('Se elimino el registro con codigo CM de la tabla SCHEDULE_REPORT_TYPES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo CM de la tabla SCHEDULE_REPORT_TYPES');
  END IF; 


  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ftp_configurations
  WHERE code = 'CM';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ftp_configurations where code = ''CM'' ';
    dbms_output.put_line('Se elimino el registro con codigo CM de la tabla FTP_CONFIGURATIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo CM de la tabla FTP_CONFIGURATIONS');
  END IF; 
  
  
DELETE FROM SCHEDULE_REPORT_LOGS
WHERE SCHEDULE_REPORT_ID IN
  (SELECT id
  FROM {{schema}}.SCHEDULE_REPORTS
  WHERE REPORT_TYPE_ID =(SELECT id FROM {{schema}}.SCHEDULE_REPORT_TYPES WHERE code = 'WT')
  );
  
DELETE
FROM {{schema}}.SCHEDULE_REPORTS
WHERE REPORT_TYPE_ID =(SELECT id FROM {{schema}}.SCHEDULE_REPORT_TYPES WHERE code = 'WT');



  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.SCHEDULE_REPORT_TYPES
  WHERE code = 'WT';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.SCHEDULE_REPORT_TYPES where code = ''WT'' ';
    dbms_output.put_line('Se elimino el registro con codigo WT de la tabla SCHEDULE_REPORT_TYPES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo WT de la tabla SCHEDULE_REPORT_TYPES');
  END IF; 


  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ftp_configurations
  WHERE code = 'WT';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ftp_configurations where code = ''WT'' ';
    dbms_output.put_line('Se elimino el registro con codigo WT de la tabla FTP_CONFIGURATIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo WT de la tabla FTP_CONFIGURATIONS');
  END IF; 

  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ERROR_MESSAGES
  WHERE ERROR_KEY  = 'sdii_CODE_DE018';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = ''sdii_CODE_DE018'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_DE018 de la tabla ERROR_MESSAGES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_DE018 en la tabla ERROR_MESSAGES');
  END IF; 
  
  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ERROR_MESSAGES
  WHERE ERROR_KEY  = 'sdii_CODE_DE019';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = ''sdii_CODE_DE019'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_DE019 de la tabla ERROR_MESSAGES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_DE019 en la tabla ERROR_MESSAGES');
  END IF; 
  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ERROR_MESSAGES
  WHERE ERROR_KEY  = 'sdii_CODE_DE020';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = ''sdii_CODE_DE020'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_DE020 de la tabla ERROR_MESSAGES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_DE020 en la tabla ERROR_MESSAGES');
  END IF; 

  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ERROR_MESSAGES
  WHERE ERROR_KEY  = 'sdii_CODE_DE021';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = ''sdii_CODE_DE021'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_DE021 de la tabla ERROR_MESSAGES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_DE021 en la tabla ERROR_MESSAGES');
  END IF; 


  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.ERROR_MESSAGES
  WHERE ERROR_KEY  = 'sdii_CODE_DE022';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = ''sdii_CODE_DE022'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_DE022 de la tabla ERROR_MESSAGES.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_DE022 en la tabla ERROR_MESSAGES');
  END IF; 

  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.SYSTEM_PARAMETERS
  WHERE PARAMETER_NAME  = 'max_thread_files';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.SYSTEM_PARAMETERS where PARAMETER_NAME  = ''max_thread_files'' ';
    dbms_output.put_line('Se elimino el registro con nombre max_thread_files de la tabla SYSTEM_PARAMETERS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre max_thread_files en la tabla SYSTEM_PARAMETERS');
  END IF; 

  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.error_messages 
  where ERROR_KEY  = 'sdii_CODE_crew_vehicle_assing_other_crew';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.error_messages where ERROR_KEY  = ''sdii_CODE_crew_vehicle_assing_other_crew'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_crew_vehicle_assing_other_crew de la tabla error_messages.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_crew_vehicle_assing_other_crew en la tabla error_messages');
  END IF; 

  
  SELECT COUNT(*)
  INTO v_count
  FROM {{schema}}.FILE_STATUS 
  where CODE  = 'FEC';

  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE from {{schema}}.FILE_STATUS where CODE  = ''FEC'' ';
    dbms_output.put_line('Se elimino el registro con codigo sdii_CODE_crew_vehicle_assing_other_crew de la tabla error_messages.');
  ELSE
    dbms_output.put_line('No existe el registro con codigo sdii_CODE_crew_vehicle_assing_other_crew en la tabla error_messages');
  END IF; 
  

  --  VERSION DE LA BASE
  SELECT COUNT(*)
  INTO v_count
  FROM HSP_DATABASE_VERSIONS
  WHERE NAME_SCRIPT like '20160819_v6_5_0_0_%';
  
  IF v_count       = 1 THEN
	EXECUTE immediate 'DELETE FROM {{schema}}.HSP_DATABASE_VERSIONS WHERE NAME_SCRIPT like ''20160819_v6_5_0_0_%''';
    dbms_output.put_line('Se elimino el registro con nombre 20160819_v6_5_0_0 de la tabla HSP_DATABASE_VERSIONS.');
  ELSE
    dbms_output.put_line('No existe el registro con nombre 20160819_v6_5_0_0 de la tabla HSP_DATABASE_VERSIONS');
  END IF;  

commit;

end;
/