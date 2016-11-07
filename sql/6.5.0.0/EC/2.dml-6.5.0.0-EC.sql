declare

v_count number;

begin

select count(*) into v_count from work_order_reasons where workorder_reason_name = 'RA04- Reasignado por error de carga';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro en la tabla WorkOrderReasons ');
else

INSERT
INTO work_order_reasons VALUES
   (
     SEQ_WORKORDER_REASONS.nextval ,
     'RA04- Reasignado por error de carga',
     '6434',
     (SELECT id
     FROM work_order_reason_categories
     WHERE wo_reason_category_code = 'RA'),
     'S',
     1,
     (SELECT id FROM work_order_status WHERE wo_state_code = 'RA')
   );
end if;
   
-- Actualizacion de Version.
select count(*) into v_count from HSP_DATABASE_VERSIONS where NAME_SCRIPT = '20160819_v6_5_0_0_EC';	

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro que indica la versión de la base 6.5.0.0');
else
	INSERT INTO HSP_DATABASE_VERSIONS 
	(ID, NAME_SCRIPT, DATE_EXECUTED) 
	VALUES 
	(SEQ_HSP_DATABASE_VERSIONS.NEXTVAL, '20160819_v6_5_0_0_EC', SYSDATE);
end if;

	
---------------------Requerimiento Inactivar Técnico
----- reporte de movimiento de cuadrillas	


select count(*) into v_count from ftp_configurations where code = 'CM';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	INSERT INTO ftp_configurations 
	VALUES (SEQ_SCHEDULE_REPORT_TYPES.nextval,'FTP_CREW_MOVEMENTS','CM','172.22.126.120','ftpsdii','directv.123','/ftpsdii/HSPQAEC/CREW_MOVEMENTS',(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = 'EC'),21,'N');
end if;


	
select count(*) into v_count from ftp_configurations where code = 'WT';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	INSERT INTO ftp_configurations 
	VALUES (SEQ_SCHEDULE_REPORT_TYPES.nextval,'FTP_WO_TECHNICAL','WT','172.22.126.120','ftpsdii','directv.123','/ftpsdii/HSPQAEC/WO_TECHNICAL',(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = 'EC'),21,'N');	
end if;

	
select count(*) into v_count from SYSTEM_PARAMETERS where PARAMETER_NAME  = 'max_thread_files';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro MAX_THREAD_FILES ya existe en la tabla SYSTEM_PARAMETERS');
else
	INSERT
	INTO SYSTEM_PARAMETERS
	(
		ID,
		PARAMETER_NAME,
		VALUE,
		PARAMETER_CODE,
		PARAMETER_TYPE_ID,
		COUNTRY_ID
	)
	VALUES
	(
		SEQ_SYSTEM_PARAMETERS.NEXTVAL,
		'max_thread_files',
		'2',
		'0205',
		(SELECT ID FROM PARAMETER_TYPES WHERE PARAMETER_TYPE_CODE='04'
		),
		(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = 'EC')
	);
end if;

-----vehiculos - cuadrillas
select count(*) into v_count from DBHSPCO.error_messages where ERROR_KEY  = 'sdii_CODE_crew_vehicle_assing_other_crew';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE0199 en la tabla ERROR_MESSAGES');
else
	INSERT INTO DBHSPCO.error_messages (ID, COMPONENT_ID, ERROR_KEY,  ERROR_CODE, ERROR_MESSAGE) 
	VALUES (DBHSPCO.SEQ_ERROR_MESSAGE.NEXTVAL, (select id from DBHSPCO.error_categories where CATEGORY_CODE = 'BL'),'sdii_CODE_crew_vehicle_assing_other_crew', 'DE0199','El vehiculo que intenta asignar ya se encuentra asignado a una cuadrilla.');
end if;

---- Actualización para regularizar las patentes de los vehículos
update DBHSPCO.VEHICLES
set plate = UPPER(replace(replace(replace(plate,'-',''),' ',''),'_',''))
where STATUS_ID=1;

-- Nuevo estado Ficheros.
select count(*) into v_count from DBHSPCO.FILE_STATUS where CODE  = 'FEC';
if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe estado FEC en la tabla FILE_STATUS');
else
	INSERT INTO DBHSPCO.FILE_STATUS (ID, CODE, NAME) 
	VALUES (DBHSPCO.SEQ_FILE_STATUS.NEXTVAL,'FEC','Pendiente de procesamiento');
end if;	

end;
/