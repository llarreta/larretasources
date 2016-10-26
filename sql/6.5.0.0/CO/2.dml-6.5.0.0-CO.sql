declare

v_count number;

begin

select count(*) into v_count from work_order_reasons where workorder_reason_name = 'RA04- Reasignado por error de carga';


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
select count(*) into v_count from SCHEDULE_REPORT_TYPES where code = 'CM';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	insert into SCHEDULE_REPORT_TYPES
	values (SEQ_SCHEDULE_REPORT_TYPES.nextval, 'CM', 'U', 'CREW_MOVEMENTS','50000','CREW_MOVEMENTS');
end if;


select count(*) into v_count from ftp_configurations where code = 'CM';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	INSERT INTO ftp_configurations 
	VALUES (SEQ_SCHEDULE_REPORT_TYPES.nextval,'FTP_CREW_MOVEMENTS','CM','172.22.126.120','ftpsdii','directv.123','/ftpsdii/HSPQAEC/CREW_MOVEMENTS',(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = 'EC'),21,'N');
end if;

	
	
---- reporte de WO atendidas por técnico	
select count(*) into v_count from SCHEDULE_REPORT_TYPES where code = 'WT';	

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte work order technical');
else
	insert into SCHEDULE_REPORT_TYPES
	values (SEQ_SCHEDULE_REPORT_TYPES.nextval, 'WT', 'U', 'WO_TECHNICAL','50000','WO_TECHNICAL');
end if;

	
select count(*) into v_count from ftp_configurations where code = 'WT';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	INSERT INTO ftp_configurations 
	VALUES (SEQ_SCHEDULE_REPORT_TYPES.nextval,'FTP_WO_TECHNICAL','WT','172.22.126.120','ftpsdii','directv.123','/ftpsdii/HSPQAEC/WO_TECHNICAL',(SELECT ID FROM COUNTRIES WHERE COUNTRY_CODE = 'EC'),21,'N');	
end if;

---- Desasociar técnico
select count(*) into v_count from ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE018';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro en la tabla ERROR_MESSAGES con código DE018');
else
	INSERT INTO ERROR_MESSAGES
	VALUES(SEQ_ERROR_MESSAGE.nextval,
	(select id from error_categories where category_code = 'DE'),
	'sdii_CODE_DE018',
	'DE018',
	'No es posible desasociar el colaborador de la cuadrilla ya que esta se encuentra asignada a la(s) siguiente(s) Work Order(s): {0}.');
end if;


select count(*) into v_count from ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE019';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro en la tabla ERROR_MESSAGES con código DE019');
else
	INSERT INTO ERROR_MESSAGES
	VALUES(SEQ_ERROR_MESSAGE.nextval,
	(select id from error_categories where category_code = 'DE'),
	'sdii_CODE_DE019',
	'DE019',
	'No es posible desasociar el empleado, ya que la cuadrilla tiene la(s) siguiente(s) Remision(es) sin autorizar: {0}.');
end if;


select count(*) into v_count from ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE020';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE020 en la tabla ERROR_MESSAGES');
else
	INSERT INTO ERROR_MESSAGES
	VALUES(SEQ_ERROR_MESSAGE.nextval,
	(select id from error_categories where category_code = 'DE'),
	'sdii_CODE_DE020',
	'DE020',
	'Para poder desasociar el empleado la cuadrilla debe tener un técnico responsable');
end if;


select count(*) into v_count from ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE021';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE021 en la tabla ERROR_MESSAGES');
else
	INSERT INTO ERROR_MESSAGES
	VALUES(SEQ_ERROR_MESSAGE.nextval,
	(select id from error_categories where category_code = 'DE'),
	'sdii_CODE_DE021',
	'DE021',
	'No es posible desasociar el empleado, ya que la cuadrilla tiene el(los) siguiente(s) Ajuste(s) sin procesar o autorizar: {0}.');
end if;

	
select count(*) into v_count from ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE022';
	
if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE022 en la tabla ERROR_MESSAGES');
else
	INSERT INTO ERROR_MESSAGES
	VALUES(SEQ_ERROR_MESSAGE.nextval,
	(select id from error_categories where category_code = 'DE'),
	'sdii_CODE_DE022',
	'DE022',
	'No es posible activar la cuadrilla, ya que el vehiculo se encuentra asociado a una cuadrilla Activa');
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
select count(*) into v_count from error_messages where ERROR_KEY  = 'sdii_CODE_crew_vehicle_assing_other_crew';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE0199 en la tabla ERROR_MESSAGES');
else
	INSERT INTO error_messages (ID, COMPONENT_ID, ERROR_KEY,  ERROR_CODE, ERROR_MESSAGE) 
	VALUES (SEQ_ERROR_MESSAGE.NEXTVAL, (select id from error_categories where CATEGORY_CODE = 'BL'),'sdii_CODE_crew_vehicle_assing_other_crew', 'DE0199','El vehiculo que intenta asignar ya se encuentra asignado a una cuadrilla.');
end if;

	
end;
/