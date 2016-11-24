declare

	
begin

DELETE from {{schema}}.SCHEDULE_REPORT_TYPES where code = 'CM';
DELETE from {{schema}}.ftp_configurations where code = 'CM';
DELETE from {{schema}}.SCHEDULE_REPORT_TYPES where code = 'WT';	
DELETE from {{schema}}.ftp_configurations where code = 'WT';
DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE018';
DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE019';
DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE020';
DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE021';
DELETE from {{schema}}.ERROR_MESSAGES where ERROR_KEY  = 'sdii_CODE_DE022';
DELETE from {{schema}}.SYSTEM_PARAMETERS where PARAMETER_NAME  = 'max_thread_files';
DELETE from {{schema}}.error_messages where ERROR_KEY  = 'sdii_CODE_crew_vehicle_assing_other_crew';
DELETE from {{schema}}.FILE_STATUS where CODE  = 'FEC';

DELETE FROM {{schema}}.HSP_DATABASE_VERSIONS
WHERE name_script = '20160819_v6_5_0_0_EC';

commit;

end;