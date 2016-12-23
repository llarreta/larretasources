declare

v_count number;

begin
--TABLA PARA REPORTE DE MOVIMIENTO DE CUADRILLAS
select count(*) into v_count from all_tables where table_name = 'CREW_MODIFICATIONS' AND owner = '{{schema}}';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la tabla CREW_MODIFICATIONS');
end if;

--CONSTRAINS
select count(*) into v_count from all_constraints where table_name = 'CREW_MODIFICATIONS' and owner = '{{schema}}' and constraint_name = 'MODIFICATION_CREW_DATE_PK';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la constrain MODIFICATION_CREW_DATE_PK');
end if;
--	
select count(*) into v_count from all_constraints where table_name = 'CREW_MODIFICATIONS' and owner = '{{schema}}' and constraint_name = 'CREW_MOVEMENTS_VEHICLE_FK';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la contrain CREW_MOVEMENTS_VEHICLE_FK');
end if;
	
--	
select count(*) into v_count from all_constraints where table_name = 'CREW_MODIFICATIONS' and owner = '{{schema}}' and constraint_name = 'CREW_MOVEMENTS_USERS_FK';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la contrain CREW_MOVEMENTS_USERS_FK');
end if;

-- SECUENCIA	  
select count(*) into v_count from all_sequences where sequence_name = 'SEQ_CREW_MODIFICATIONS' and sequence_owner = '{{schema}}';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la secuencia SEQ_CREW_MODIFICATIONS');
end if;

-- INDICE	
select count(*) into v_count from all_indexes WHERE index_name = 'CREW_MODIFICATIONS_IDX01' AND owner = '{{schema}}';
	
if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice CREW_MODIFICATIONS_IDX01');
end if;	
	
select count(*) into v_count from all_indexes WHERE index_name = 'CREW_MODIFICATIONS_IDX02' AND owner = '{{schema}}';
	
if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice CREW_MODIFICATIONS_IDX02');
end if;

--TABLA PARA REPORTE DE WORK ORDERS ATENDIDAS
select count(*) into v_count from all_tables where table_name = 'WORK_ORDER_CREW_ATTENTIONS' AND owner = '{{schema}}';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la tabla WORK_ORDER_CREW_ATTENTIONS');
end if;

--CONSTRAINT

select count(*) into v_count from all_constraints where table_name = 'WORK_ORDER_CREW_ATTENTIONS' and owner = '{{schema}}' and constraint_name = 'WORK_ORDER_CREW_ATTENTIONS_PK';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la contrain WORK_ORDER_CREW_ATTENTIONS_PK');
end if;

-- SECUENCIA	  
select count(*) into v_count from all_sequences where sequence_name = 'SEQ_WORK_ORDER_CREW_ATTENTIONS' and sequence_owner = '{{schema}}';

if (v_count >= 1) then 

	DBMS_OUTPUT.Put_line ('Se creo correctamente la secuencia SEQ_WORK_ORDER_CREW_ATTENTIONS');
end if;

--INDICE	
select count(*) into v_count from all_indexes WHERE index_name = 'WO_CREW_ATTENTIONS_IDX01' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice WO_CREW_ATTENTIONS_IDX01');
end if;

select count(*) into v_count from all_indexes WHERE index_name = 'WO_CREW_ATTENTIONS_IDX02' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice WO_CREW_ATTENTIONS_IDX02');
end if;

select count(*) into v_count from all_indexes WHERE index_name = 'WO_CREW_ATTENTIONS_IDX03' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice WO_CREW_ATTENTIONS_IDX03');
end if;

select count(*) into v_count from all_indexes WHERE index_name = 'WAREHOUSE_ELEMENTS_IDX13' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice WAREHOUSE_ELEMENTS_IDX13');
end if;
	
select count(*) into v_count from all_indexes WHERE index_name = 'UPLOAD_FILE_PARAM_IDX1' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice UPLOAD_FILE_PARAM_IDX1');
end if;

select count(*) into v_count from all_indexes WHERE index_name = 'FILE_DETAIL_PROCESSES_IDX1' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice FILE_DETAIL_PROCESSES_IDX1');
end if;

select count(*) into v_count from all_indexes WHERE index_name = 'ADJUSTMENT_ELEMENTS_IDX02' AND owner = '{{schema}}';

if (v_count >= 1) then 
	DBMS_OUTPUT.Put_line ('Se creo correctamente el indice ADJUSTMENT_ELEMENTS_IDX02');
end if;

commit;

end;
/