declare

v_count number;

begin

---------------------Requerimiento Inactivar Técnico
----- reporte de movimiento de cuadrillas	
select count(*) into v_count from SCHEDULE_REPORT_TYPES where code = 'CM';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte Crew movements');
else
	insert into SCHEDULE_REPORT_TYPES
	values (SEQ_SCHEDULE_REPORT_TYPES.nextval, 'CM', 'U', 'CREW_MOVEMENTS','50000','CREW_MOVEMENTS');
end if;

---- reporte de WO atendidas por técnico	
select count(*) into v_count from SCHEDULE_REPORT_TYPES where code = 'WT';	

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el tipo de reporte work order technical');
else
	insert into SCHEDULE_REPORT_TYPES
	values (SEQ_SCHEDULE_REPORT_TYPES.nextval, 'WT', 'U', 'WO_TECHNICAL','50000','WO_TECHNICAL');
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

	  
-----vehiculos - cuadrillas
select count(*) into v_count from error_messages where ERROR_KEY  = 'sdii_CODE_crew_vehicle_assing_other_crew';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Ya existe el registro con código DE0199 en la tabla ERROR_MESSAGES');
else
	INSERT INTO error_messages (ID, COMPONENT_ID, ERROR_KEY,  ERROR_CODE, ERROR_MESSAGE) 
	VALUES (SEQ_ERROR_MESSAGE.NEXTVAL, (select id from error_categories where CATEGORY_CODE = 'BL'),'sdii_CODE_crew_vehicle_assing_other_crew', 'DE0199','El vehiculo que intenta asignar ya se encuentra asignado a una cuadrilla.');
end if;

---- Actualización para regularizar las patentes de los vehículos
update VEHICLES
set plate = UPPER(replace(replace(replace(plate,'-',''),' ',''),'_',''))
where STATUS_ID=1;
	
commit;

end;
/