package co.com.directv.sdii.common.enumerations;

import co.com.directv.sdii.business.CodesBusinessEntityHelper;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de los codigos o id de las entidades de negocio, los cuales se
 * encuentran ubicados en un archivo de propiedades externo a la 
 * aplicacion.
 * 
 * Fecha de Creación: 26/03/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see properties.CodesBusinessEntityEnum.properties
 */
public enum CodesBusinessEntityEnum {

	CREW_STATUS_ACTIVE("sdii_CODE_crew_status_active","sdii_COLUMN_crew_status"),
	CREW_STATUS_INACTIVE("sdii_CODE_crew_status_inactive","sdii_COLUMN_crew_status"),
	
	CREW_ROL_TECNICIAN("sdii_CODE_crew_rol_tecnician","sdii_COLUMN_crew_rol"),
	
	EMPLOYEE_CREW_RESPONSABLE("sdii_CODE_employee_crew_responsable","sdii_COLUMN_employee_crew_responsable"),
	EMPLOYEE_CREW_NOT_RESPONSABLE("sdii_CODE_employee_crew_not_responsable","sdii_COLUMN_employee_crew_not_responsable"),
	
    CODIGO_DEALER_STATUS_ACTIVE("sdii_CODE_dealer_satus_active","sdii_COLUMN_dealer_satus"),
    CODIGO_DEALER_STATUS_INACTIVE("sdii_CODE_dealer_satus_inactive","sdii_COLUMN_dealer_satus"),
    
    NAME_TYPE_DEALER_PRINCIPAL ("sdii_name_dealer_type_principal", "sdii_name_dealer_type_principal"),
    NAME_TYPE_DEALER_BRANCH ("sdii_name_dealer_type_branch", "sdii_name_dealer_type_branch"),
    
    CODIGO_SYSTEM_PARAM_MCAPACIDAD("sdii_CODE_system_param_mcapacidad","sdii_COLUMN_system_param"),
    //Contiene el parámetro del sistema con el máximo de archivos a procesar
    CODIGO_SKILL_RATE_STATUS_ACTIVE("sdii_CODE_skill_rate_status_active","sdii_COLUMN_skill_rate_status"),
    CODIGO_SKILL_RATE_STATUS_INACTIVE("sdii_CODE_skill_rate_status_inactive","sdii_COLUMN_skill_rate_status"),
    
    CODIGO_SYSTEM_PARAM_STRING("sdii_CODE_parameter_type_string","sdii_COLUMN_parameter_type"),
    CODIGO_SYSTEM_PARAM_NUMERIC("sdii_CODE_parameter_type_numeric","sdii_COLUMN_parameter_type"),
    CODIGO_SYSTEM_PARAM_DATE("sdii_CODE_parameter_type_date","sdii_COLUMN_parameter_type"),
    CODIGO_SYSTEM_PARAM_TIME("sdii_CODE_parameter_type_time","sdii_COLUMN_parameter_type"),
           
    CODIGO_EMPLOYEE_STATUS_ACTIVE("sdii_CODE_employee_status_active","sdii_COLUMN_employee_status"),
    CODIGO_EMPLOYEE_STATUS_INACTIVE("sdii_CODE_employee_status_inactive","sdii_COLUMN_employee_status"),
    CODIGO_EMPLOYEE_STATUS_RETIRED("sdii_CODE_employee_status_retired","sdii_COLUMN_employee_status"),
    
    MEDIA_CONTACT_TYPE_TELEP_CODE("sdii_CODE_media_contact_type_home_telephone","sdii_COLUMN_media_contact_type"),
    MEDIA_CONTACT_TYPE_TELEPHWORK_CODE("sdii_CODE_media_contact_type_work_telephone","sdii_COLUMN_media_contact_type"),
    MEDIA_CONTACT_TYPE_FAX_CODE("sdii_CODE_media_contact_type_fax","sdii_COLUMN_media_contact_type"),
    MEDIA_CONTACT_TYPE_EMAIL_CODE("sdii_CODE_media_contact_type_email","sdii_COLUMN_media_contact_type"),
    MEDIA_CONTACT_TYPE_MOBILE_CODE("sdii_CODE_media_contact_type_mobile","sdii_COLUMN_media_contact_type"),
            
    ALLOCATOR_PROCESS_STATUS_NOT_STARTED("sdii_CODE_allocator_process_status_not_started","sdii_COLUMN_process_status"),
    ALLOCATOR_PROCESS_STATUS_STARTED("sdii_CODE_allocator_process_status_started","sdii_COLUMN_process_status"),
    ALLOCATOR_PROCESS_STATUS_EXECUTED_WITH_ERRORS("sdii_CODE_allocator_process_status_executed_with_errors","sdii_COLUMN_process_status"),
    ALLOCATOR_PROCESS_ASSIGNMENT_TRACE("sdii_CODE_allocator_process_ASSIGNMENT_TRACE","sdii_CODE_allocator_process_ASSIGNMENT_TRACE"),
    ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED("sdii_CODE_allocator_process_status_correct_finished","sdii_COLUMN_process_status"),
    ALLOCATOR_PROCESS_STATUS_PROCESSED_BUT_NOT_ASSIGNED("sdii_CODE_allocator_process_status_processed_but_not_assigned","sdii_COLUMN_process_status"),
    
    SID_STATUS_OPEN("sdii_CODE_ibs_6_status_open","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_NOT_STARTED("sdii_CODE_ibs_6_status_not_started","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_RUNNING("sdii_CODE_ibs_6_status_running","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_RE_SCHEDULED("sdii_CODE_ibs_6_status_re_scheduled","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_NOT_RUNNING("sdii_CODE_ibs_6_status_not_running","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_SUSPENDED("sdii_CODE_ibs_6_status_suspended","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_COMPLETED("sdii_CODE_ibs_6_status_completed","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_CLOSED("sdii_CODE_ibs_6_status_closed","sdii_COLUMN_ibs_6_status"),
    SID_STATUS_ABORTED("sdii_CODE_ibs_6_status_aborted","sdii_COLUMN_ibs_6_status"), 
    
    SURVEY_ISACTIVE_ACTIVE("sdii_CODE_survey_active","sdii_COLUMN_survey"),
    SURVEY_ISACTIVE_INACTIVE("sdii_CODE_survey_inactive","sdii_COLUMN_survey"),
    
    BOOLEAN_TRUE("sdii_boolean_true",""),
    BOOLEAN_FALSE("sdii_boolean_false",""), 
    
    STRING_TRUE("sdii_string_true",""),
    STRING_FALSE("sdii_string_false",""),
    
    TRUE("sdii_true",""),
    FALSE("sdii_false",""),
    
    //Parámetros del sistema
    SYSTEM_PARAM_DEPLOYMENT("sdii_CODE_sys_param_sdii_deployment","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_APPLICATION_NAME("sdii_CODE_sys_param_sdii_application_name","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAX_WORK_ORDERS_PER_PAGE("sdii_CODE_system_param_MAX_WORK_ORDERS_PER_PAGE","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_PDF_MAX_WORK_ORDERS_BY_PDF_FILE("sdii_CODE_sys_param_max_work_orders_by_pdf_file","sdii_COLUMN_system_param"), 
    SYSTEM_PARAM_WO_PDF_PRINT_ALLIANCE_FILE("sdii_CODE_sys_param_print_alliance_file","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_PDF_PRINT_CONTRACT_TELE_SALE_FILE("sdii_CODE_sys_param_print_tele_sale_file","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_PDF_PRINT_AUTO_DEBIT_FILE("sdii_CODE_sys_param_print_auto_deb_file","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_PDF_PRINT_PERMANENCE_AND_FINES("sdii_CODE_sys_param_print_permanence_and_fines","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_BACK_LOG("sdii_CODE_sys_param_back_log","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_HOUR_QTY_PER_CREW("sdii_CODE_sys_param_hour_qty_per_crew","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_SERVER_IP("sdii_CODE_sys_param_ldap_server_ip","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_SERVER_PORT("sdii_CODE_sys_param_ldap_server_port","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_SUFFIX("sdii_CODE_sys_param_ldap_suffix","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_START_DIRECTORY("sdii_CODE_sys_param_ldap_start_directory","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR("sdii_CODE_sys_param_ldap_user_admin","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR_PASSWD("sdii_CODE_sys_param_ldap_user_admin_passwd","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_SEARCH_BASE("sdii_CODE_sys_param_ldap_search_base","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_LDAP_AUTHENTICATION("sdii_CODE_sys_param_ldap_authentication","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WORKORDERS_2_ALLOC_PAGE_SIZE("sdii_CODE_sys_param_workorders_2_alloc_page_size","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_DELAY_ALLOCATOR("sdii_CODE_sys_param_delay_allocator","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_DELAY_WORKORDERS_CSR_PENDING("sdii_CODE_sys_param_delay_workorders_csr_pending","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_DEALER_DUMMY_CODE("sdii_CODE_sys_param_dealer_dummy_code","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_SMTP_HOST("sdii_CODE_system_param_mail_smpt_host", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_CREATED_JOBS("sdii_CODE_system_param_created_jobs", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_IS_IN_CLUSTER("sdii_CODE_system_param_is_in_cluster", "sdii_COLUMN_system_param"),    
    SYSTEM_PARAM_MAIL_TRANSPORT_PROTOCOL("sdii_CODE_system_param_mail_transport_protocol", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_SMTP_PORT("sdii_CODE_system_param_mail_smpt_port", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_SMTP_AUTH("sdii_CODE_system_param_mail_smpt_auth", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_SENDERNICK("sdii_CODE_system_param_mail_smpt_sender_nick", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAIL_SENDERPASS("sdii_CODE_system_param_mail_smpt_sender_passwd", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_USER_BASEDN("sdii_CODE_sys_param_ldap_user_dn", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_DEALER_WOUT_COVERAGE("sdii_CODE_sys_param_dealer_wout_coverage", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_KPI_RESULT_UPPER_LIMIT("sdii_CODE_sys_param_kpi_result_upper_limit", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_KPI_RESULT_LOWER_LIMIT("sdii_CODE_sys_param_kpi_result_lower_limit", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_INSTALACION_MUDANZA_UPGRADE("sdii_CODE_sys_param_assignment_schedule_limit_instalacion_mudanza_upgrade", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_ASISTENCIA_TECNICA("sdii_CODE_sys_param_assignment_schedule_limit_asistencia_tecnica", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_RECUPERACION("sdii_CODE_sys_param_assignment_schedule_limit_recuperacion", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_COBRANZA("sdii_CODE_sys_param_assignment_schedule_limit_cobranza", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_RESERVA_LOCAL("sdii_CODE_sys_param_assignment_schedule_limit_reserva_local", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_AVAILABLE_COLOR("sdii_CODE_sys_param_assignment_schedule_available_color", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_UNAVAILABLE_COLOR("sdii_CODE_sys_param_assignment_schedule_unavailable_color", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_DAY_COUNT_2_CALCULATE_DAILY_CAPACITY("sdii_CODE_sys_param_assignment_day_count_2_calculate_daily_capacity", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_ASSIGNMENT_KPI_SKILL_GOAL("sdii_CODE_sys_param_assignment_kpi_skill_goal", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_REASON_SWOP_NEW_HSP_TO_IBS("sdii_CODE_sys_param_wo_reason_swop_new_hsp_to_ibs","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_REASON_SWOP_OLD_HSP_TO_IBS("sdii_CODE_sys_param_wo_reason_swop_old_hsp_to_ibs","sdii_COLUMN_system_param"),
    
    SYSTEM_PARAM_REPORT_WO_SHOW_IS_MIGRATED("sdii_CODE_sys_param_sdii_report_wo_show_is_migrated","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_REPORT_WO_DISCLAMER("sdii_CODE_sys_param_sdii_report_wo_disclamer","sdii_COLUMN_system_param"),
    //Internacionalizacion
    SYSTEM_PARAM_LANGUAGE("sdii_CODE_sys_param_sdii_language",""),
    
    
    SYSTEM_PARAM_PRINT_CONTRACT_WORK_ORDER_PDF("sdii_CODE_sys_param_print_contract_work_order_pdf",""),
    
    //Contiene el parámetro del sistema con el máximo de archivos a procesar
    CODE_SYSTEM_PARAM_MAX_FILE_TO_PROCESS("sdii_CODE_system_param_max_file_to_process","sdii_COLUMN_system_param"),
    //Parametro de sistema que maneja la cantidad de registro que se leen para el job que notifica a IBs cambios de estados de elementos
    SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION("sdii_CODE_ibs_element_notification_page_size","sdii_COLUMN_system_param"),
    
    SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_MOBILE_TYPE("sdii_CODE_system_param_telephone_mc_mobile_type", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_HOME_TYPE("sdii_CODE_system_param_telephone_mc_home_type", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_WORK_TYPE("sdii_CODE_system_param_telephone_mc_work_type", "sdii_COLUMN_system_param"),
    
    SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN("sdii_CODE_sys_param_work_order_owner_user_login", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL("sdii_CODE_sys_param_wo_load_report_recipient_mail", "sdii_COLUMN_system_param"),
    //REQ002 - WO Agendadas en linea.
	SYSTEM_PARAM_SUCCEED_WO_CSR_REPORT_RECIPIENT_MAIL("sdii_CODE_sys_param_succeed_wo_csr_report_recipient_mail", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_CUSTOMER_SERVICE_NUMBER("sdii_CODE_sys_param_customer_service_number", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_SWOP_SERVICE_HOURS_BEFORE_ACTUAL_DATE("sdii_CODE_sys_param_swop_service_serv_hours_before_actual_date", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_DEALER_ADJUSMENT_TRANSIT("sdii_CODE_sys_param_dealer_adjusment_transit", "sdii_COLUMN_system_param"),
    SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE("sdii_CODE_sys_param_invoke_type_service_inventory","sdii_COLUMN_system_param"),
    
    SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_AG("sdii_CODE_workorder_process_status_agendada",""),
    SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_REAG("sdii_CODE_workorder_process_status_reagendada",""),
    //CC54 WO Pendintes por falta de materiales
    SYSTEM_PARAM_WORKORDER_REASON_RN03("sdii_CODE_sys_param_sdii_workorder_reason_rn03","sdii_COLUMN_system_param"), 
    SYSTEM_PARAM_SWOP_THEFT_SERVICE("sdii_code_sys_param_swop_theft_service","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_SWOP_THEFT_WAREHOUSE("sdii_code_sys_param_swop_theft_warehouse","sdii_COLUMN_system_param"),
    //Reportar elementos en inconsistencia al ser movidos 
    SYSTEM_PARAM_REPORT_ELEMENT_TO_IBS("sdii_CODE_sys_param_report_element_to_ibs","sdii_COLUMN_system_param"),    
    
    SYSTEM_PARAM_BTB_ELEMENTS_TYPES("sdii_CODE_sys_param_btb_elements_types","sdii_COLUMN_system_param"),
    SYSTEM_PARAM_MAX_THREAD_FILES("sdii_CODE_sys_param_max_threads_files","sdii_CODE_sys_param_max_threads_files"),
    SYSTEM_PARAM_IS_CUSTOMER_INFO_MASK("sdii_CODE_sys_param_is_customer_info_mask","sdii_COLUMN_system_param"),
    
    
    //SKILLS
    SKILL_HCO("sdii_CODE_HCO","sdii_COLUMN_HCO"),
    SKILL_HTS("sdii_CODE_HTS","sdii_COLUMN_HTS"),
    SKILL_HCA("sdii_CODE_HCA","sdii_COLUMN_HCA"),
    SKILL_HTC("sdii_CODE_HTC","sdii_COLUMN_HTC"),
    SKILL_HVI("sdii_CODE_HVI","sdii_COLUMN_HVI"),
    SKILL_HAU("sdii_CODE_HAU","sdii_COLUMN_HAU"),
    SKILL_HIN("sdii_CODE_HIN","sdii_COLUMN_HIN"),
    SKILL_HPS("sdii_CODE_HIN","sdii_COLUMN_HPS"),
    SKILL_HAE("sdii_CODE_HAE","sdii_COLUMN_HAE"),
    
    //TIPOS DE SKILLS
    SKILL_TYPE_SKD("sdii_CODE_SKD","sdii_COLUMN_SKD"),
    SKILL_TYPE_SKE("sdii_CODE_SKE","sdii_COLUMN_SKE"),
    SKILL_TYPE_SKA("sdii_CODE_SKA","sdii_COLUMN_SKA"),
    
    //Stock
    //TIPOS DE REGISTROS DE IMPORTACION
    IMPORT_LOG_STATUS_IN_CREATED("sdii_CODE_IMPORT_LOG_IN_CREATED","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_CREATED("sdii_CODE_IMPORT_LOG_CREATED","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_SEND("sdii_CODE_IMPORT_LOG_SEND","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED("sdii_CODE_IMPORT_LOG_PARTIALLY_CONFIRMED","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_RECEIVED("sdii_CODE_IMPORT_LOG_ENTERTAINMENT","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_IN_INCONSISTENCY("sdii_CODE_IMPORT_LOG_IN_INCONSISTENCY","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_IN_PROCESS("sdii_CODE_IMPORT_LOG_IN_PROCESS","sdii_COLUMN_IMPORT_LOG"),
    IMPORT_LOG_STATUS_DELETED("sdii_CODE_IMPORT_LOG_DELETED","sdii_COLUMN_IMPORT_LOG"),
    
    //ESTADOS ELEMENTOS
    ELEMENT_STATUS_NO_QLTY_CONTROL("sdii_CODE_ELEMENT_STATUS_NO_QLTY_CONTROL","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_WITH_QLTY_CONTROL("sdii_CODE_ELEMENT_STATUS_WITH_QLTY_CONTROL","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_DEFICIENT("sdii_CODE_ELEMENT_STATUS_DEFICIENT","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_IN_TRANSIT("sdii_CODE_ELEMENT_STATUS_IN_TRANSIT","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_CONFIRMED("sdii_CODE_ELEMENT_STATUS_CONFIRMED","sdii_COLUMN_ELEMENT_STATUS"),
    
    ELEMENT_STATUS_DUMMY("sdii_CODE_ELEMENT_STATUS_DUMMY","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_D01("sdii_CODE_ELEMENT_STATUS_D01","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_D03("sdii_CODE_ELEMENT_STATUS_D03","sdii_COLUMN_ELEMENT_STATUS"),    
    ELEMENT_STATUS_D05("sdii_CODE_ELEMENT_STATUS_D05","sdii_COLUMN_ELEMENT_STATUS"),
    
    ELEMENT_STATUS_R01("sdii_CODE_ELEMENT_STATUS_R01","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_R02("sdii_CODE_ELEMENT_STATUS_R02","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_R03("sdii_CODE_ELEMENT_STATUS_R03","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_R04("sdii_CODE_ELEMENT_STATUS_R04","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_R05("sdii_CODE_ELEMENT_STATUS_R05","sdii_COLUMN_ELEMENT_STATUS"),
    
    ELEMENT_STATUS_S01("sdii_CODE_ELEMENT_STATUS_S01","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_S02("sdii_CODE_ELEMENT_STATUS_S02","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_S04("sdii_CODE_ELEMENT_STATUS_S04","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_S05("sdii_CODE_ELEMENT_STATUS_S05","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_S06("sdii_CODE_ELEMENT_STATUS_S06","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_S07("sdii_CODE_ELEMENT_STATUS_S07","sdii_COLUMN_ELEMENT_STATUS"),
    
    ELEMENT_STATUS_T01("sdii_CODE_ELEMENT_STATUS_T01","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_T02("sdii_CODE_ELEMENT_STATUS_T02","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_T03("sdii_CODE_ELEMENT_STATUS_T03","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_T04("sdii_CODE_ELEMENT_STATUS_T04","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_T06("sdii_CODE_ELEMENT_STATUS_T06","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_T07("sdii_CODE_ELEMENT_STATUS_T07","sdii_COLUMN_ELEMENT_STATUS"),
  
    ELEMENT_STATUS_U01("sdii_CODE_ELEMENT_STATUS_U01","sdii_COLUMN_ELEMENT_STATUS"),   
    
    ELEMENT_STATUS_W01("sdii_CODE_ELEMENT_STATUS_W01","sdii_COLUMN_ELEMENT_STATUS"),
    
    ELEMENT_STATUS_X01("sdii_CODE_ELEMENT_STATUS_X01","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_X04("sdii_CODE_ELEMENT_STATUS_X04","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_X05("sdii_CODE_ELEMENT_STATUS_X05","sdii_COLUMN_ELEMENT_STATUS"),
    ELEMENT_STATUS_NA("sdii_CODE_ELEMENT_STATUS_NA","sdii_COLUMN_ELEMENT_STATUS"),
    

    //tipos de movimiento entre bodegas
    WH_MOVEMENT_TYPE_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSLATE_BETWEEN_WH_AND_DEALERS("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BETWEEN_WH_AND_DEALERS","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_ACTIVATION_ON_CUSTOMER("sdii_CODE_WH_MOVEMENT_TYPE_ACTIVATION_ON_CUSTOMER","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_HIGH("sdii_CODE_WH_MOVEMENT_TYPE_HIGH","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_LOW("sdii_CODE_WH_MOVEMENT_TYPE_LOW","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_RECOVERY("sdii_CODE_WH_MOVEMENT_TYPE_RECOVERY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSLATE_BETWEEN_COMPANY_AND_WH_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BETWEEN_COMPANY_AND_WH_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_DUMMY("sdii_CODE_WH_MOVEMENT_TYPE_DUMMY","sdii_COLUMN_WH_MOVEMENT_DUMMY"),
    WH_MOVEMENT_TYPE_MODEL_CHANGE_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_MODEL_CHANGE_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_MODEL_CHANGE_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_MODEL_CHANGE_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_NATIONAL_BUY("sdii_CODE_WH_MOVEMENT_TYPE_NATIONAL_BUY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INTERNATIONAL_BUY("sdii_CODE_WH_MOVEMENT_TYPE_INTERNATIONAL_BUY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_NATIONAL_BUY_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_NATIONAL_BUY_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INTERNATIONAL_BUY_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_INTERNATIONAL_BUY_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TECHNICAL_FAIL_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_TECHNICAL_FAIL_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TECHNICAL_FAIL_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_TECHNICAL_FAIL_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_CREW_ASSIGMENT_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_CREW_ASSIGMENT_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_CREW_ASSIGMENT_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_CREW_ASSIGMENT_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_WORKSHOP_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_WORKSHOP_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_WORKSHOP_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_WORKSHOP_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_BETWEEN_COMPANY_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    
    WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_TRANSLATE_BY_REF_SAME_COMPANY_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    
    WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_BETWEEN_WH_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_BETWEEN_WH_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY_UPGRADE_DOWNGRADE("sdii_CODE_WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY_UPGRADE_DOWNGRADE","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT_UPGRADE_DOWNGRADE("sdii_CODE_WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT_UPGRADE_DOWNGRADE","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_GOOD_CONDITION_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_GOOD_CONDITION_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_GOOD_CONDITION_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_GOOD_CONDITION_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_SMART_CARD_CHANGE_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_SMART_CARD_CHANGE_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_SMART_CARD_CHANGE_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_SMART_CARD_CHANGE_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_QA_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_QA_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSIT_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_TRANSIT_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_TRANSIT_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_TRANSIT_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_DISPOSABLE_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_DISPOSABLE_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_DISPOSABLE_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_DISPOSABLE_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_ADD("sdii_CODE_WH_MOVEMENT_TYPE_ADD","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_DEL("sdii_CODE_WH_MOVEMENT_TYPE_DEL","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_MOD_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_MOD_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_MOD_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_MOD_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_MOD_IMPORT_LOG_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_MOD_IMPORT_LOG_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_MOD_IMPORT_LOG_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_MOD_IMPORT_LOG_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_TO_STOCK_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_QA_TO_STOCK_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_TO_STOCK_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_QA_TO_STOCK_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_TO_RETURNS_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_QA_TO_RETURNS_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_QA_TO_RETURNS_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_QA_TO_RETURNS_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    WH_MOVEMENT_TYPE_DELETE_IMPORT_LOG("sdii_CODE_WH_MOVEMENT_TYPE_DELETE_IMPORT_LOG","sdii_COLUMN_WH_MOVEMENT_TYPE"),
    
    //allocable
    IS_ALLOCABLE("sdii_CODE_IS_ALLOCABLE","sdii_IS_ALLOCABLE"),
    IS_NOT_ALLOCABLE("sdii_CODE_IS_NOT_ALLOCABLE","sdii_IS_NOT_ALLOCABLE"),
    
    //Menu items alarmas
    MENU_ITEM_ALARM_STOCK("sdii_CODE_menu_item_alarm_stock","sdii_MENU_ITEM_ALARM"),
    MENU_ITEM_ALARM_REFERENCE("sdii_CODE_menu_item_alarm_reference","sdii_MENU_ITEM_ALARM"),
    
    //Tipos de Bodega
    WAREHOUSE_TYPE_CALIDAD("sdii_CODE_WAREHOUSE_TYPE_CALIDAD","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_DISPONIBILIDADES("sdii_CODE_WAREHOUSE_TYPE_DISPONIBILIDADES","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_DEVOLUCIONES("sdii_CODE_WAREHOUSE_TYPE_DEVOLUCIONES","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_TRANSITO("sdii_CODE_WAREHOUSE_TYPE_TRANSITO","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_CUADRILLA("sdii_CODE_WAREHOUSE_TYPE_CUADRILLA","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_CLIENTE("sdii_CODE_WAREHOUSE_TYPE_CLIENTE","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_AJUSTE("sdii_CODE_WAREHOUSE_TYPE_AJUSTE","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_LOGISTIC_OPERATOR("sdii_CODE_WAREHOUSE_TYPE_lOGISTIC_OPERATOR","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_R01("sdii_CODE_WAREHOUSE_TYPE_RO1","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_R02("sdii_CODE_WAREHOUSE_TYPE_R02","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_R03("sdii_CODE_WAREHOUSE_TYPE_R03","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_R04("sdii_CODE_WAREHOUSE_TYPE_R04","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_WHTRO722("sdii_CODE_WAREHOUSE_TYPE_WHTRO722","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_WHTRD723("sdii_CODE_WAREHOUSE_TYPE_WHTRD723","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S02("sdii_CODE_WAREHOUSE_TYPE_SO2","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S03("sdii_CODE_WAREHOUSE_TYPE_SO3","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S05("sdii_CODE_WAREHOUSE_TYPE_SO5","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S06("sdii_CODE_WAREHOUSE_TYPE_SO6","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S01P("sdii_CODE_WAREHOUSE_TYPE_S01P","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_S01("sdii_CODE_WAREHOUSE_TYPE_S01","sdii_COLUMN_WAREHOUSE_TYPE"),
    WAREHOUSE_TYPE_ADJUSTMENT_TRANSIT("sdii_CODE_WAREHOUSE_TYPE_ADJUSTMENT_TRANSIT","sdii_COLUMN_WAREHOUSE_TYPE"),
            
    //Element Type para determinar si son o nó serializados
    ELEMENT_TYPE_SERIALIZED("sdii_CODE_element_type_serialized","sdii_COLUMN_element_type"),
    ELEMENT_TYPE_NOT_SERIALIZED("sdii_CODE_element_type_not_serialized","sdii_COLUMN_element_type"),
    ELEMENT_CLASS_DECODER("sdii_CODE_element_class_decoder","sdii_COLUMN_element_type"),
    ELEMENT_CLASS_CARD("sdii_CODE_element_class_card","sdii_COLUMN_element_type"),    
    ELEMENT_TYPE_MULTISWITCH("sdii_CODE_element_type_multiswitch","sdii_COLUMN_element_type"), 
    ELEMENT_TYPE_ANTENAS("sdii_CODE_element_type_antenas","sdii_COLUMN_element_type"), 
    
    //Tipos de Dealer 
    CODE_DEALER_TYPE_LOGISTIC_OPERATOR("sdii_CODE_dealer_type_logistic_operator","sdii_COLUMN_dealer_type"),//Este se usa en el Caso de Uso Inv_13  Registrar control de calidad de elementos serializados
    CODE_DEALER_TYPE_INSTALLER("sdii_CODE_dealer_type_installer", "sdii_COLUMN_dealer_type"),
    CODE_DEALER_TYPE_SELLER("sdii_CODE_dealer_type_seller", "sdii_COLUMN_dealer_type"),
    
    //Estado de las inconsistencias
    INCONSISTENCY_STATUS_ACTIVE("sdii_CODE_inconsistency_status_active","sdii_COLUMN_inconsistency_status"),
    INCONSISTENCY_STATUS_INACTIVE("sdii_CODE_inconsistency_status_inactive","sdii_COLUMN_inconsistency_status"),
    INCONSISTENCY_STATUS_CLOSE("sdii_CODE_inconsistency_status_close","sdii_COLUMN_inconsistency_status"),
    
    //Tipos de inconsistencia
    INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY("sdii_CODE_inconsistency_type_incomplete_quantity","sdii_COLUMN_inconsistency_type"),
    INCONSISTENCY_TYPE_EXCEEDED_QUANTITY("sdii_CODE_inconsistency_type_exceeded_quantity","sdii_COLUMN_inconsistency_type"),
    
    //Tipos de inconsistencia
    INCONSISTENCY_ADD("sdii_inconsistency_add","sdii_inconsistency_add"),
    INCONSISTENCY_DEL("sdii_inconsistency_del","sdii_inconsistency_del"),
    INCONSISTENCY_UPD("sdii_inconsistency_upd","sdii_inconsistency_upd"),
        
    //Estados de Items
    ITEM_STATUS_CREATED_PROCESS("sdii_CODE_item_status_created_process","sdii_COLUMN_item_status"),
    ITEM_STATUS_CREATED("sdii_CODE_item_status_created","sdii_COLUMN_item_status"),
    ITEM_STATUS_SENDED("sdii_CODE_item_status_sended","sdii_COLUMN_item_status"),
    ITEM_STATUS_PARTIALLY_CONFIRMED("sdii_CODE_item_status_partially_confirmed","sdii_COLUMN_item_status"),
    ITEM_STATUS_RECEPTED("sdii_CODE_item_status_recepted","sdii_COLUMN_item_status"),
    ITEM_STATUS_INCONSISTENCY_PROCESS("sdii_CODE_item_status_inconsistency_process","sdii_COLUMN_item_status"),
    ITEM_STATUS_ERROR_PROCESS("sdii_CODE_item_status_error_process","sdii_COLUMN_item_status"),
    ITEM_STATUS_DELETED("sdii_CODE_item_status_error_delete","sdii_COLUMN_item_status"),
    
    //Estados de Remisiones
    REFERENCE_STATUS_CREATED_PROCESS("sdii_CODE_reference_status_created_process","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_CREATED("sdii_CODE_reference_status_created","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_SENDED("sdii_CODE_reference_status_sended","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_PARTIALLY_CONFIRMED("sdii_CODE_reference_status_partially_confirmed","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_RECEPTED("sdii_CODE_reference_status_recepted","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_INCONSISTENCY_PROCESS("sdii_CODE_reference_status_inconsistency_process","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_DELETED("sdii_CODE_reference_status_deteled","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_SENDING("sdii_CODE_reference_status_sending","sdii_COLUMN_reference_status"),
    REFERENCE_STATUS_ERROR("sdii_CODE_reference_status_error","sdii_COLUMN_reference_status"),
    
    //Estados de Work Order Agenda
    WORKORDER_AGENDA_STATUS_ACTIVE("sdii_CODE_workorder_agenda_status_active","sdii_COLUMN_workorder_agenda_status"),
    WORKORDER_AGENDA_STATUS_INACTIVE("sdii_CODE_workorder_agenda_status_inactive","sdii_COLUMN_workorder_agenda_status"),
        
    //Estados de elementos en bodega
    RECORD_STATUS_LAST("sdii_CODE_last_record","sdii_COLUMN_record_status"),
    RECORD_STATUS_HISTORIC("sdii_CODE_hitory_record","sdii_COLUMN_record_status"),
    
    //Codigos de tipos de elementos
    NA_ELEMENT_TYPE("sdii_CODE_na_element","sdii_COLUMN_element_type"),
    MULTISWITCH_ELEMENT_TYPE("sdii_CODE_multiswitch_element","sdii_COLUMN_element_type"),
    CABLERG6_ELEMENT_TYPE("sdii_CODE_cablerg6_element","sdii_COLUMN_element_type"),
    CONECTORINT_ELEMENT_TYPE("sdii_CODE_conectorint_element","sdii_COLUMN_element_type"),
    ANCLAJE_ELEMENT_TYPE("sdii_CODE_anclaje_element","sdii_COLUMN_element_type"),
    ANTENA_ELEMENT_TYPE("sdii_CODE_antena_element","sdii_COLUMN_element_type"),
    CONTROLREMOTO_ELEMENT_TYPE("sdii_CODE_controlremoto_element","sdii_COLUMN_element_type"),
    TARJETA_ELEMENT_TYPE("sdii_CODE_tarjeta_element","sdii_COLUMN_element_type"),
    DECODIFICADOR_ELEMENT_TYPE("sdii_CODE_decodificador_element","sdii_COLUMN_element_type"), 
    
    //Códigos de los tipos de modificación de remisión:
    REFERENCE_MODIFICATION_TYPE_CREATION_PROCESS("sdii_CODE_ref_modification_type_creation_process","sdii_COLUMN_ref_modification_type"),
    REFERENCE_MODIFICATION_TYPE_CREATED("sdii_CODE_ref_modification_type_creation","sdii_COLUMN_ref_modification_type"),
    REFERENCE_MODIFICATION_SHIPMENT( "sdii_CODE_ref_modification_shipment","sdii_COLUMN_ref_modification_type" ),
    REFERENCE_MODIFICATION_RECEPTED( "sdii_CODE_ref_modification_recepted","sdii_COLUMN_ref_modification_type" ),
    REFERENCE_MODIFICATION_PARTIALLY_CONFIRMED( "sdii_CODE_ref_modification_partially_confirmed","sdii_COLUMN_ref_modification_type" ),
    REFERENCE_MODIFICATION_INCONSISTENCY_PROCESS( "sdii_CODE_ref_modification_inconsistency_process","sdii_COLUMN_ref_modification_type" ),
    REFERENCE_MODIFICATION_DELETED( "sdii_CODE_ref_modification_deleted","sdii_COLUMN_ref_modification_type" ),
    
    //Estados de import log modification type
    IMPORT_LOG_MODIFICATION_TYPE_STATUS_CREATED_PROCESS("sdii_CODE_import_log_mod_type_status_created_process","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_TYPE_STATUS_CREATED("sdii_CODE_import_log_mod_type_status_created","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_STATUS_SENDED("sdii_CODE_import_log_mod_type_status_sended","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_PARTIALLY_CONFIRMED("sdii_CODE_import_log_mod_type_status_partially_confirmed","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_RECEPTED("sdii_CODE_import_log_mod_type_status_recepted","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_INCONSISTENCY_PROCESS("sdii_CODE_import_log_mod_type_status_inconsistency_process","sdii_COLUMN_import_log_mod_type_status"),
    IMPORT_LOG_MODIFICATION_INCONSISTENCY_DELETE("sdii_CODE_import_log_mod_type_status_inconsistency_delete","sdii_COLUMN_import_log_mod_type_status"),
    
    //Códigos de los tipos de novedad
    NEWS_TYPE_QUALITY_CONTROL("sdii_CODE_news_type_quality_control","sdii_COLUMN_news_type"),
    
    //tipos de inconsistencias para remisiones
    INCONSISTENCY_REFERENCE_STATUS_OPEN("sdii_CODE_inconsistency_open","sdii_COLUMN_inconsistency_status_references"),
    INCONSISTENCY_REFERENCE_STATUS_CLOSE("sdii_CODE_inconsistency_close","sdii_COLUMN_inconsistency_status_references"),
    
    //Tipos de Rol:
    ROLE_TYPE_DTV_ADMIN("sdii_CODE_role_type_dtv_admin","sdii_COLUMN_role_type"),
    ROLE_TYPE_DEALER_ADMIN("sdii_CODE_role_type_dealer_admin","sdii_COLUMN_role_type"),
    ROLE_TYPE_STOCK_ADMIN("sdii_CODE_role_type_stock_admin","sdii_COLUMN_role_type"),
    ROLE_TYPE_CONTROL_TOWER("sdii_CODE_role_type_control_tower","sdii_COLUMN_role_type"),
    ROLE_TYPE_CREW("sdii_CODE_role_type_crew","sdii_COLUMN_role_type"),
    ROLE_TYPE_REPORTS("sdii_CODE_role_type_reports","sdii_COLUMN_role_type"),
    ROLE_TYPE_KPI("sdii_CODE_role_type_kpi","sdii_COLUMN_role_type"),
    ROLE_TYPE_WAREHOUSE("sdii_CODE_role_type_warehouse_analyst","sdii_COLUMN_role_type"),
    ROLE_TYPE_BLACK_BERRY("sdii_CODE_role_type_black_berry","sdii_COLUMN_role_type"),
    ROLE_TYPE_ROOT("sdii_CODE_role_type_root","sdii_COLUMN_role_type"),
    ROLE_TYPE_LOGISTICS_ANALYST("sdii_CODE_role_type_logistics_analyst","sdii_COLUMN_role_type"),
    ROLE_TYPE_LOGISTICS_OPERATOR("sdii_CODE_role_type_logistics_operator","sdii_COLUMN_role_type"),
    ROLE_TYPE_DEALER_LOGISTICS_ANALYST("sdii_CODE_role_type_dealer_logistics_analyst","sdii_COLUMN_role_type"),
    ROLE_TYPE_BACKOFFICE("sdii_CODE_role_type_backoffice_dtv","sdii_COLUMN_role_type"),
    ROLE_TYPE_CSR("sdii_CODE_role_type_csr","sdii_COLUMN_role_type"),
    
    //Estado de proveedores
    SUPPLIER_STATUS_ACTIVE("sdii_CODE_supplier_status_active","sdii_COLUMN_supplier_status"),
    SUPPLIER_STATUS_INACTIVE("sdii_CODE_supplier_status_inactive","sdii_COLUMN_supplier_status"),
        
    //Estado de tipos de movimiento
    MOVEMENT_TYPE_STATUS_ACTIVE("sdii_CODE_movement_type_status_active","sdii_COLUMN_movement_type_status"),
    MOVEMENT_TYPE_STATUS_INACTIVE("sdii_CODE_movement_type_status_inactive","sdii_COLUMN_movement_type_status"),
    
    //Estado de Marca Elemento
    ELEMENT_BRAND_STATUS_ACTIVE("sdii_CODE_element_brand_status_active","sdii_COLUMN_element_brand_status"),
    ELEMENT_BRAND_STATUS_INACTIVE("sdii_CODE_element_brand_status_inactive","sdii_COLUMN_element_brand_status"),
    
    //Estado de Modelo Elemento
    ELEMENT_MODEL_STATUS_ACTIVE("sdii_CODE_element_model_status_active","sdii_COLUMN_element_model_status"),
    ELEMENT_MODEL_STATUS_INACTIVE("sdii_CODE_element_model_status_inactive","sdii_COLUMN_element_model_status"),
    ELEMENT_MODEL_IS_PREPAID("sdii_CODE_element_model_is_prepaid","sdii_COLUMN_element_model_prepaid"),
    ELEMENT_MODEL_IS_NOT_PREPAID("sdii_CODE_element_model_is_not_prepaid","sdii_COLUMN_element_model_prepaid"),
    
    //Estado de clase Elemento
    ELEMENT_CLASS_STATUS_ACTIVE("sdii_CODE_element_class_status_active","sdii_COLUMN_element_class_status"),
    ELEMENT_CLASS_STATUS_INACTIVE("sdii_CODE_element_class_status_inactive","sdii_COLUMN_element_class_status"), 
    IBS_BULDING_CODE_CHARACTERISTIC_NAME("sdii_CODE_ibs_building_code_characteristic_name","sdii_CODE_ibs_building_code_characteristic_name"), 
        
    //Estado de elemen type
    ELEMENT_TYPE_STATUS_ACTIVE("sdii_CODE_element_type_status_active","sdii_COLUMN_element_type_status"),
    ELEMENT_TYPE_STATUS_INACTIVE("sdii_CODE_element_type_status_inactive","sdii_COLUMN_element_type_status"),
    
    //Estado de unidad de medida
    MEASURE_UNIT_STATUS_ACTIVE("sdii_CODE_measure_unit_status_active","sdii_COLUMN_measure_unit_status"),
    MEASURE_UNIT_STATUS_INACTIVE("sdii_CODE_measure_unit_status_inactive","sdii_COLUMN_measure_unit_status"), 
    
    //Estado del tipo de email
    EMAIL_TYPE_STATUS_ACTIVE("sdii_CODE_email_type_status_active","sdii_COLUMN_email_type_status"),
    EMAIL_TYPE_STATUS_INACTIVE("sdii_CODE_email_type_status_inactive","sdii_COLUMN_email_type_status"), 
    
    //Estado de la compañía transportadora
    TRANSPORT_COMP_STATUS_ACTIVE("sdii_CODE_transport_company_status_active","sdii_COLUMN_transport_company"),
    TRANSPORT_COMP_STATUS_INACTIVE("sdii_CODE_transport_company_status_inactive","sdii_COLUMN_transport_company"), 
    
    //Estado del tipo de modificación del tipo de registro de importación
    IMPORT_LOG_MODIFICATION_TYPE_STATUS_ACTIVE("sdii_CODE_imp_log_mod_type_status_active","sdii_COLUMN_imp_log_mod_type_status_active"),
    IMPORT_LOG_MODIFICATION_TYPE_STATUS_INACTIVE("sdii_CODE_imp_log_mod_type_status_inactive","sdii_COLUMN_imp_log_mod_type_status_active"), 
    
    //Estado de los registros de tipo de inconsistencia
    INCONSISTENCY_TYPE_STATUS_ACTIVE("sdii_CODE_inconsistency_type_status_active","sdii_COLUMN_inconsistency_type_status"),
    INCONSISTENCY_TYPE_STATUS_INACTIVE("sdii_CODE_inconsistency_type_status_inactive","sdii_COLUMN_inconsistency_type_status"), 
    
    //Estado de los registros de causa de ajuste
    CAUSE_ADJUSTMENT_STATUS_ACTIVE("sdii_CODE_cause_adjustment_status_active","sdii_COLUMN_cause_adjustment_status"),
    CAUSE_ADJUSTMENT_STATUS_INACTIVE("sdii_CODE_cause_adjustment_status_inactive","sdii_COLUMN_cause_adjustment_status"),
    
    LINKED_WAREHOUSE_STATUS_ACTIVE("sdii_CODE_linked_warehouse_status_active","sdii_COLUMN_linked_warehouse_status"),
    LINKED_WAREHOUSE_STATUS_INACTIVE("sdii_CODE_linked_warehouse_status_inactive","sdii_COLUMN_linked_warehouse_status"), 
    
    CHANNEL_TYPE_INSTALLER("sdii_CODE_channel_type_installer","sdii_COLUMN_channel_type"), 
     
     //Estado de los registros de WO ASSIGMENTS
    WO_ASSIGMENT_STATUS_ACTIVE("sdii_CODE_status_active","sdii_COLUMN_status_wo_assigment"),
    WO_ASSIGMENT_STATUS_INACTIVE("sdii_CODE_status_inactive","sdii_COLUMN_status_wo_assigment"),  
    
    //tipos de inconsistencia
    INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY("sdii_CODE_inconsistency_type_import_log_inconsistency","sdii_COLUMN_inconsistency_type_import_log_inconsistency"),
    INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY_MAJOR_ITEMS("sdii_CODE_inconsistency_type_import_log_inconsistency_major_items","sdii_COLUMN_inconsistency_type_import_log_inconsistency"),
    INCONSISTENCY_TYPE_IMPORT_LOG_INCONSISTENCY_MINOR_ITEMS("sdii_CODE_inconsistency_type_import_log_inconsistency_minor_items","sdii_COLUMN_inconsistency_type_import_log_inconsistency"),
    
    SURVEY_DECODER_SECTION_CODE("sdii_CODE_survey_decoder_secction_code","sdii_COLUMN_question_class_column_code"), 
     
    PROGRAM_STATUS_ACTIVE("sdii_CODE_program_status_active","sdii_COLUMN_program_status"),
    PROGRAM_STATUS_INACTIVE("sdii_CODE_program_status_inactive","sdii_COLUMN_program_status"), 
            
    //ESTADOS DE DEALERS
    DEALER_STATUS_NORMAL("sdii_CODE_dealer_status_normal","sdii_COLUMN_dealer_status"),
    
    //Estados de los servide_hour
    SERVICE_HOUR_STATUS_ACTIVE("sdii_CODE_service_status_hour_active","sdii_COLUMN_service_hour_status"),
    SERVICE_HOUR_STATUS_INACTIVE("sdii_CODE_service_status_hour_inactive","sdii_COLUMN_service_hour_status"),
    
    //Variable que indica si un servicio puede o no agregarse a una WO
    ALLOW_ADD_SERVICE("sdii_CODE_allow_add_service","sdii_COLUMN_add_service"),
	DENY_ADD_SERVICE("sdii_CODE_deny_add_service","sdii_COLUMN_add_service"),
	
	//Generales
	/* DEPLOYMENT("sdii_deployment", "sdii_deployment"),   
	APPLICATION_NAME("sdii_application_name","sdii_application_name"),*/
	MAX_ROWS_EXPORT("sdii_max_rows_export", "sdii_max_rows_export"),
		
	//Estado de vehiculo
	ACTIVE_VEHICLE("sdii_CODE_active_vehicle", "sdii_COLUMN_vehicle_status"),
	INACTIVE_VEHICLE("sdii_CODE_inactive_vehicle", "sdii_COLUMN_vehicle_status"),
		
	//MODULO - CUSTOMER
	CUSTOMER_EMPLOYEE_TYPE("sdii_CODE_customer_type_employee","sdii_COLUMN_customerTypeCode"),
	CUSTOMER_PREPAGO_CLASS_CODE("sdii_CODE_customer_class_code_prepago","sdii_CODE_customer_class_code_prepago"),
	CUSTOMER_CODE_TYPE_DEFAULT("sdii_CODE_customer_code_type_default",""),
	CUSTOMER_CODE_CLASS_DEFAULT("sdii_CODE_customer_class_default",""),
	CUSTOMER_CODE_TYPE_MDU_BACKBONE("sdii_CODE_customer_type_mdu_backbone","sdii_COLUMN_customerTypeCode"),
	CUSTOMER_CODE_TYPE_MDU_WITHOUT_BACKBONE("sdii_CODE_customer_type_mdu_without_backbone","sdii_COLUMN_customerTypeCode"),
	
	//Customer agreement migrated
	CUSTOMER_AGREEMENT_MIGRATED("sdii_CODE_customer_migrated","CUSTOMER_AGREEMENT_MIGRATED"),
	CUSTOMER_AGREEMENT_NOT_MIGRATED("sdii_CODE_customer_not_migrated","CUSTOMER_AGREEMENT_MIGRATED"),
	
	//variable para mapear el estado activo de un elemento de un cliente
	IBS_RESOURCE_ACTIVE_STATUS("sdii_CODE_ibs_resource_active_status","sdii_COLUMN_ibs_resource_status"),
		
	//Asignador códigos de los tipos de evaluación para habilidades: Notificar dealer sin cobertura NDSC y SNDSC (sin notificar..)
	NDSC_RESULT_SKILL_EVAL("sdii_CODE_skill_evaluator_ndsc", "sdii_COLUMN_skill_evaluator"),
	SNDSC_RESULT_SKILL_EVAL("sdii_CODE_skill_evaluator_sndsc", "sdii_COLUMN_skill_evaluator"), 
	
	SKILL_MODE_TYPE_SCHEDULE("sdii_CODE_skill_mode_type_schedule", "sdii_COLUMN_skill_mode_type"),
	SKILL_MODE_TYPE_ASSIGN("sdii_CODE_skill_mode_type_assign", "sdii_COLUMN_skill_mode_type"), 
	
	KPI_CALCULATION_TYPE_ONLINE("sdii_CODE_calculation_type_online","sdii_COLUMN_calculation_type"),
	KPI_CALCULATION_TYPE_FRECUENCY("sdii_CODE_calculation_type_frecuency","sdii_COLUMN_calculation_type"),
	
	SYSTEM_PARAM_KPI_ONT_TIME_HOUR_QUANTITY("sdii_CODE_kpi_on_time_hour_quantity", "sdii_COLUMN_system_param"),
			
	DEALER_COVERAGE_STATUS_ACTIVE("sdii_CODE_dealer_coverage_status_active", "sdii_COLUMN_dealer_coverage"),
	DEALER_COVERAGE_STATUS_INACTIVE("sdii_CODE_dealer_coverage_status_inactive", "sdii_COLUMN_dealer_coverage"),
	
	KPI_CONFIGURATION_STATUS_ACTIVE("sdii_CODE_kpi_configuration_status_active", "sdii_COLUMN_kpi_configuration"),
	KPI_CONFIGURATION_STATUS_INACTIVE("sdii_CODE_kpi_configuration_status_inactive", "sdii_COLUMN_kpi_configuration"),
	
	KPI_AGING("sdii_CODE_kpi_aging", "sdii_COLUMN_kpi"),
	KPI_BACKLOG("sdii_CODE_kpi_backlog", "sdii_COLUMN_kpi"),
	KPI_CYCLETIME("sdii_CODE_kpi_cycletime", "sdii_COLUMN_kpi"),
	KPI_ONTIME("sdii_CODE_kpi_ontime", "sdii_COLUMN_kpi"),
	KPI_RETRIEVAL("sdii_CODE_kpi_retrieval", "sdii_COLUMN_kpi"),
	KPI_SOS90("sdii_CODE_kpi_sos90", "sdii_COLUMN_kpi"),
	
	SALE_CHANNEL_SELLERS_ACTIVE("sdii_CODE_sale_channel_sellers_active", "sdii_COLUMN_sale_channel_sellers"),
	SALE_CHANNEL_SELLERS_INACTIVE("sdii_CODE_sale_channel_sellers_inactive", "sdii_COLUMN_sale_channel_sellers"),
	
	SALE_CHANNEL_INSTALLERS_ACTIVE("sdii_CODE_sale_channel_installers_active", "sdii_COLUMN_sale_channel_installers"),
	SALE_CHANNEL_INSTALLERS_INACTIVE("sdii_CODE_sale_channel_installers_inactive", "sdii_COLUMN_sale_channel_installers"),
	
	KPI_TYPE_POSITIVE("sdii_CODE_kpi_type_positive", "sdii_COLUMN_kpi_type"),
	KPI_TYPE_NEGATIVE("sdii_CODE_kpi_type_negative", "sdii_COLUMN_kpi_type"), 
	
	
//	Tipos de cubrimiento de un dealer
	COVERAGE_TYPE_PERMANENT("sdii_CODE_coverageType_permanente", "sdii_COLUMN_coverageType_code"),	
	COVERAGE_TYPE_OCASSIONAL("sdii_CODE_coverageType_ocasional", "sdii_COLUMN_coverageType_code"),
	
//	Tipos de ejecucion del sistema
	EXECUTION_TYPE_ASSIGNED("sdii_CODE_skill_mode_type_assigned", "sdii_COLUMN_skill_mode_type_code"),	
	EXECUTION_TYPE_SCHEDULED("sdii_CODE_skill_mode_type_scheduled", "sdii_COLUMN_skill_mode_type_code"), 
	
	//Paises registrados
	CODE_COUNTRY_AR("sdii_CODE_country_AR","sdii_COLUMN_country"),
	CODE_COUNTRY_CL("sdii_CODE_country_CL","sdii_COLUMN_country"),
	CODE_COUNTRY_CO("sdii_CODE_country_CO","sdii_COLUMN_country"),
	CODE_COUNTRY_EC("sdii_CODE_country_EC","sdii_COLUMN_country"),
	CODE_COUNTRY_PE("sdii_CODE_country_PE","sdii_COLUMN_country"),
	CODE_COUNTRY_PR("sdii_CODE_country_PR","sdii_COLUMN_country"),
	CODE_COUNTRY_TT("sdii_CODE_country_TT","sdii_COLUMN_country"),
	CODE_COUNTRY_VE("sdii_CODE_country_VE","sdii_COLUMN_country"),	
    
	FILE_TYPE_DEALER_DETAILS("sdii_CODE_file_type_DEALER_DETAILS","sdii_COLUMN_file_type"),
	FILE_TYPE_DEALER_COVERAGE("sdii_CODE_file_type_DEALER_COVERAGE","sdii_COLUMN_file_type"),
	FILE_TYPE_DEALER_CONF_COVERAGE("sdii_CODE_file_type_DEALER_CONF_COVERAGE","sdii_COLUMN_file_type"),
	FILE_TYPE_DEALER_SERV_SUBCAT_WTH_PC("sdii_CODE_file_type_DEALER_SERV_SUBCAT_WTH_PC","sdii_COLUMN_file_type"),
	FILE_TYPE_DEALER_BUILDINGS("sdii_CODE_file_type_DEALER_BUILDINGS","sdii_COLUMN_file_type"),
	
	//MODULO Inventory
	INVENTORY_DEALER_2_DEALER_ESB_MOVEMENT_CODE("esb_CODE_inventory_dealer_2_dealer_movement_code",""), 
    INVENTORY_DEALER_ENTRANCE_ADJUSTMENT_ESB_MOVEMENT_CODE("esb_CODE_inventory_dealer_entrance_adjustment_movement_code",""),
    INVENTORY_DEALER_EXIT_ADJUSTMENT_ESB_MOVEMENT_CODE("esb_CODE_inventory_dealer_exit_adjustment_movement_code",""),
    INVENTORY_DEALER_2_CLIENT_ESB_MOVEMENT_CODE("esb_CODE_inventory_dealer_2_client_movement_code",""),
    INVENTORY_CLIENT_2_DEALER_ESB_MOVEMENT_CODE("esb_CODE_inventory_client_2_dealer_movement_code",""),
    INVENTORY_INTERFACE_ELEMENT_TYPE_DECO("sdii_CODE_device_type_deco",""),
    INVENTORY_INTERFACE_ELEMENT_TYPE_SMARTCARD("sdii_CODE_device_type_smartcard",""), 

    
    //*********************************MODULO CORE********************************************************************
    //Eventos Atencion
	CORE_SWAP_FALLA_TECNICA_OLD("sdii_CODE_swop_falla_tecnica_old", "sdii_COLUMN_swop_falla_tecnica"),
	CORE_SWAP_FALLA_TECNICA_NEW("sdii_CODE_swop_falla_tecnica_new", "sdii_COLUMN_swop_falla_tecnica"),

	//Parametro de sistema para invocacion de servicio de activacion en atención de WO
	SYSTEM_PARAM_ADD_ACTIVATION_CORE("sdii_CODE_sys_param_add_activation_core", "sdii_COLUMN_system_param"),
	
	 //WO_ATTENTION
    WO_ATTENTION_AEI("sdii_CODE_wo_attention_AEI_stage","sdii_COLUMN_attention_status_code"),   
    WO_ATTENTION_VAL("sdii_CODE_wo_attention_VAL_stage","sdii_COLUMN_attention_status_code"),    
    WO_ATTENTION_ATS("sdii_CODE_wo_attention_ATS_stage","sdii_COLUMN_attention_status_code"),  
    WO_ATTENTION_REU("sdii_CODE_wo_attention_REU_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_RER("sdii_CODE_wo_attention_RER_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ICE("sdii_CODE_wo_attention_ICE_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ACE("sdii_CODE_wo_attention_ACE_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_NSI("sdii_CODE_wo_attention_NSI_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_NWS("sdii_CODE_wo_attention_NWS_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_AFS("sdii_CODE_wo_attention_AFS_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_RNS("sdii_CODE_wo_attention_RNS_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_RES("sdii_CODE_wo_attention_RES_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_IFS("sdii_CODE_wo_attention_IFS_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ASR("sdii_CODE_wo_attention_ASR_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_FSW("sdii_CODE_wo_attention_FSW_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_VEE("sdii_CODE_wo_attention_VEE_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ERS("sdii_CODE_wo_attention_ERS_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ESI("sdii_CODE_wo_attention_ESI_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_ENI("sdii_CODE_wo_attention_ENI_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_CFW("sdii_CODE_wo_attention_CFW_stage","sdii_COLUMN_attention_status_code"),
    WO_ATTENTION_UDI("sdii_CODE_wo_attention_UDI_stage","sdii_COLUMN_attention_status_code"),
    
    //Estados de los shippments 
    SHIPPING_ORDER_STATUS_PAYMENT_RECEIVED("sdii_CODE_shipping_order_status_payment_received","sdii_COLUMN_SO_STATUS_CODE"),
    SHIPPING_ORDER_STATUS_MAY_SHIP("sdii_CODE_shipping_order_status_may_ship","sdii_COLUMN_SO_STATUS_CODE"),
    
    //Service Category
    SERVICE_CATEGORY_BASIC_INSTALLATION("sdii_CODE_service_category_basic_installation","sdii_COLUMN_service_category_code"),

    //PDF ANNEX CODES
	PDF_WORKORDER_CODE("sdii_CODE_wo_pdf_fomat_type_wo", "sdii_COLUMN_wo_pdf_fomat_type"),
	PDF_ANNEX_CODE("sdii_CODE_wo_pdf_fomat_type_ax", "sdii_COLUMN_wo_pdf_fomat_type"),
	PDF_ALL_ANNEX("sdii_CODE_wo_pdf_dealer_type_id_all", "sdii_COLUMN_wo_pdf_dealer_type_id"), 
	
	//solve by compañia instaladora de reason
	REASON_IS_SOLVE_BY_CI("sdii_CODE_is_solve_by_ci", "sdii_COLUMN_solve_by_ci"),
	REASON_NOT_SOLVE_BY_CI("sdii_CODE_not_solve_by_ci", "sdii_COLUMN_solve_by_ci"),
	//solve by compañia instaladora de reason para capa de presentacion
	REASON_IS_SOLVE_BY_CI_NET("sdii_CODE_is_solve_by_ci_net", "sdii_COLUMN_solve_by_ci_net"),
	REASON_NOT_SOLVE_BY_CI_NET("sdii_CODE_not_solve_by_ci_net", "sdii_COLUMN_solve_by_ci_net"),
	
	//Tecnologias de los productos de la WO
	TECHNOLOGY_IRD_HD_DVR("sdii_CODE_technology_ird_hd_dvr", "sdii_COLUMN_technology"),
	TECHNOLOGY_IRD_DVR("sdii_CODE_technology_ird_dvr", "sdii_COLUMN_technology"),
	TECHNOLOGY_IRD("sdii_CODE_technology_ird", "sdii_COLUMN_technology"),
	TECHNOLOGY_SC("sdii_CODE_technology_SC", "sdii_COLUMN_technology"),
	
	SUPER_CATEGORY_TECHNICAL_ASSISTANCE("sdii_CODE_super_category_technical_assistance", "sdii_COLUMN_super_category"),
	SUPER_CATEGORY_INSTALLATION("sdii_CODE_super_category_installation", "sdii_COLUMN_super_category"),
	SUPER_CATEGORY_RECOVERY("sdii_CODE_super_category_recovery", "sdii_COLUMN_super_category"),
	SUPER_CATEGORY_COBRANZAS("sdii_CODE_super_category_cobranzas", "sdii_COLUMN_super_category"),
	SUPER_CATEGORY_RESERVED_LOCAL_USE("sdii_CODE_super_category_reserved_local_use", "sdii_COLUMN_super_category"),

	//variable para manejo del nombre del jasper para generar excel
	WORKORDER_EXCEL_JASPER_FILE("sdii_CODE_wo_jasper_file","sdii_COLUMN_wo_jasper_file"),
	WORKORDER_CANCELED_EXCEL_JASPER_FILE("sdii_CODE_wo_canceled_jasper_file","sdii_CODE_wo_canceled_jasper_file"),
	//variable para manejo del nombre de archivo jasper para el procesador de compara el inventario fisico con smartDealer
	WAREHOUSE_PHYSICAL_INVENTORY_SMARTDEALER_EXCEL_JASPER_FILE("sdii_CODE_wh_physical_inventory_smartdealer_excel_jasper_file","sdii_COLUMN_wo_jasper_file"),
	JASPER_FILE_NAME_REPORT_WORKORDER_LAST_DAY("sdii_CODE_jasper_file_name_report_workorder_last_day","sdii_COLUMN_wo_jasper_file"),
	//variable para manejo del nombre del jasper de datos de contacto del cliente
	CUSTOMER_MEDIA_CONTACTS_JASPER_FILE("sdii_CODE_customer_media_contacts_file","sdii_COLUMN_customer_media_contacts_file"),
	//variable para manejo del nombre del jasper de servicio de WO
	WORKORDER_SERVICE_JASPER_FILE("sdii_CODE_wo_service_file","sdii_COLUMN_wo_service_file"),
	//variable para manejo del nombre del jasper de elementos de SO
	SO_ELEMEMTS_JASPER_FILE("sdii_CODE_so_elements_file","sdii_COLUMN_so_elements_file"),
	//variable para manejo del nombre del jasper para medios de contacto de dealer
	DEALER_MEDIA_CONTACTS_JASPER_FILE("sdii_CODE_dealer_media_contacts_file","sdii_COLUMN_dealer_media_contacts_file"),
	//variable para manejo del nombre del jasper para contacts de WO
	WO_CONTACTS_JASPER_FILE("sdii_CODE_wo_contacts_file","sdii_COLUMN_wo_contacts_file"),
	//REQ002 - WO Agendadas en linea. reporte para el manejo del nombre del jasper para Work Order Agendadas en línea
	REPORTS_SUCCEED_WORK_ORDER_CSR_JASPER_FILE("sdii_CODE_reports_work_order_csr_file", "sdii_COLUMN_wo_jasper_file"),
		
	
	SHIP_ORDER_SER_RESP_CODE_INIT_TAG("sdii_CODE_ship_order_serv_code_init_tag", "sdii_COLUMN_ship_order_serv_tag"),
	SHIP_ORDER_SER_RESP_CODE_END_TAG("sdii_CODE_ship_order_serv_code_end_tag", "sdii_COLUMN_ship_order_serv_tag"),
	SHIP_ORDER_SER_RESP_MSJ_INIT_TAG("sdii_CODE_ship_order_serv_msj_init_tag", "sdii_COLUMN_ship_order_serv_tag"),
	SHIP_ORDER_SER_RESP_MSJ_END_TAG("sdii_CODE_ship_order_serv_msj_end_tag", "sdii_COLUMN_ship_order_serv_tag"),
	SHIP_ORDER_SER_SUCCESS_CODE("sdii_CODE_ship_order_serv_success_code", "sdii_COLUMN_ship_order_serv_success_code_order"),
	
	//Variables para fechas por agrupacion por vencimiento
	EXPIRATION_GROUPING_WITHOUT("sdii_CODE_without_grouping","sdii_COLUMN_expiration_grouping"),
	EXPIRATION_GROUPING_ALL("sdii_CODE_all_grouping","sdii_COLUMN_expiration_grouping"),
			
	//Service Type
    SERVICE_TYPE_INSTALL("sdii_CODE_service_type_install","sdii_COLUMN_service_type_code"),
    SERVICE_TYPE_MOVEMENT("sdii_CODE_service_type_movement","sdii_COLUMN_service_type_code"),   
    SERVICE_TYPE_DISCONNECTION("sdii_CODE_service_type_disconnection","sdii_COLUMN_service_type_code"),
    SERVICE_TYPE_SERVICE("sdii_CODE_service_type_service","sdii_COLUMN_service_type_code"), 
    SERVICE_TYPE_RECOVERY("sdii_CODE_service_type_recovery","sdii_COLUMN_service_type_code"),
    
    //Work order Service Status
    WO_SERVICE_STATUS_ATTENDED("sdii_CODE_wo_service_status_attended","sdii_COLUMN_wo_service_status_code"),
    WO_SERVICE_STATUS_UNATTENDED("sdii_CODE_wo_service_status_unattended","sdii_COLUMN_wo_service_status_code"),

    WORKORDER_STATUS_ACTIVE("sdii_CODE_workorder_status_activa","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_ASSIGN("sdii_CODE_workorder_status_asignada","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_SCHEDULED("sdii_CODE_workorder_status_agendada","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_RESCHEDULED("sdii_CODE_workorder_status_reagendada","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_REASSIGN("sdii_CODE_workorder_status_reasignada","sdii_COLUMN_workorder_status"),    
    WORKORDER_STATUS_PENDING("sdii_CODE_workorder_status_pendiente","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_REALIZED("sdii_CODE_workorder_status_realizada","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_FINISHED("sdii_CODE_workorder_status_finalizada","sdii_COLUMN_workorder_status"),
    WORKORDER_STATUS_CANCELED("sdii_CODE_workorder_status_cancelada","sdii_COLUMN_workorder_status"),    
    WORKORDER_STATUS_REJECTED("sdii_CODE_workorder_status_rechazada","sdii_COLUMN_workorder_status"),
  
    WORKORDER_REASON_HANDMADE_ASSIGNED("sdii_CODE_workorder_reason_handmade_assigned","sdii_COLUMN_workorder_reason"), 
    WORKORDER_REASON_REALIZED("sdii_CODE_workorder_reason_realized","sdii_COLUMN_workorder_reason"),   
    WORKORDER_REASON_SCHEDULED("sdii_CODE_workorder_reason_scheduled","sdii_COLUMN_workorder_reason"),
    WORKORDER_REASON_RESCHEDULED("sdii_CODE_workorder_reason_rescheduled","sdii_COLUMN_workorder_reason"),
    WORKORDER_REASON_PENDING_TECH_RESOURCES_ARENT_ENOUGHT("sdii_CODE_workorder_reason_tech_resources_arent_enought","sdii_COLUMN_workorder_reason"),
    WORKORDER_REASON_ASSIGNED("sdii_CODE_workorder_reason_assigned","sdii_COLUMN_workorder_reason"),
    WORKORDER_REASON_REASSIGNED("sdii_CODE_workorder_reason_reassigned","sdii_COLUMN_workorder_reason"),
    WORKORDER_REASON_FINISH("sdii_CODE_workorder_reason_finish","sdii_COLUMN_workorder_reason"),
    
    WORKORDER_TYPE_SERVICE("sdii_CODE_workorder_type_service","sdii_COLUMN_wo_type"),
    WORKORDER_TYPE_DISCONNECTION("sdii_CODE_workorder_type_retrieval","sdii_COLUMN_wo_type"),
    WORKORDER_TYPE_INSTALL("sdii_CODE_workorder_type_install","sdii_CODE_workorder_type_install"),
    WORKORDER_TYPE_MOVE("sdii_CODE_workorder_type_move","sdii_CODE_workorder_type_move"),
    WORKORDER_TYPE_LOCAL_USE("sdii_CODE_workorder_type_local_use","sdii_CODE_workorder_type_local_use"),
    
    WORKORDER_IS_APPOINTMENT("sdii_CODE_workorder_is_appointment","sdii_COLUMN_wo_type"),
    WORKORDER_IS_NOT_APPOINTMENT("sdii_CODE_workorder_is_not_appointment","sdii_COLUMN_wo_type"),

    CODIGO_SERVICE_STATUS_ACTIVE("sdii_CODE_service_status_active","sdii_COLUMN_service_status"),
    CODIGO_SERVICE_STATUS_INACTIVE("sdii_CODE_service_status_inactive","sdii_COLUMN_service_status"),
    
    CODIGO_SERVICE_HOUR_STATUS_ACTIVE("sdii_CODE_service_status_hour_active","sdii_COLUMN_service_hour_status"),
    CODIGO_SERVICE_HOUR_STATUS_INACTIVE("sdii_CODE_service_status_hour_inactive","sdii_COLUMN_service_hour_status"),
    
    CODE_SERVICE_INSTALL_BASIC_HOUSE("sdii_CODE_service_install_basic_house","sdii_COLUMN_service_install"),
    CODE_SERVICE_INSTALL_BASIC_BUILDING("sdii_CODE_service_install_basic_building","sdii_COLUMN_service_install"),
    CODE_SERVICE_INSTALL_BASIC_MDU("sdii_CODE_service_install_basic_mdu","sdii_COLUMN_service_install"),
    CODE_SERVICE_INSTALL_BASIC_DEMO("sdii_CODE_service_install_basic_demo","sdii_COLUMN_service_install"),    
 
    //Categorias
    CORE_SERVICE_CATEGORY_UPGRADE("sdii_CODE_service_category_upgrade","sdii_COLUMN_service_category_upgrade"),
    CORE_SERVICE_CATEGORY_DOWNGRADE("sdii_CODE_service_category_downgrade","sdii_COLUMN_service_category_downgrade"),
    CORE_SERVICE_CATEGORY_SWAP("sdii_CODE_service_category_swap","sdii_COLUMN_service_category_swap"),
    CORE_SERVICE_CATEGORY_BASIC("sdii_CODE_service_category_basic","sdii_COLUMN_service_category_basic"),
    CORE_SERVICE_CATEGORY_ADDITIONAL_SECOND_VISIT("sdii_CODE_service_category_additional_second_visit","sdii_COLUMN_service_category_additional_second_visit"),
    CORE_SERVICE_CATEGORY_ADDITIONAL_DAY("sdii_CODE_service_category_additional_day","sdii_COLUMN_service_category_additional_day"),
    
    //Estado de Warehouse Type
    WAREHOUSE_TYPE_STATUS_ACTIVE("sdii_CODE_warehouse_type_status_active","sdii_COLUMN_warehouse_type_status"),
    WAREHOUSE_TYPE_STATUS_INACTIVE("sdii_CODE_warehouse_type_status_inactive","sdii_COLUMN_warehouse_type_status"),
    WAREHOUSE_TYPE_STATUS_VISIBLE("sdii_CODE_warehouse_type_visible","sdii_COLUMN_warehouse_type_visible"),
    WAREHOUSE_TYPE_STATUS_NOT_VISIBLE("sdii_CODE_warehouse_type_not_visible","sdii_COLUMN_warehouse_type_visible"),
    WAREHOUSE_TYPE_STATUS_VIRTUAL("sdii_CODE_warehouse_type_status_virtual","sdii_COLUMN_warehouse_type_virtual"),
    WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL("sdii_CODE_warehouse_type_status_not_virtual","sdii_COLUMN_warehouse_type_virtual"),
    //Estado de Warehouse 
    WAREHOUSE_STATUS_ACTIVE("sdii_CODE_warehouse_status_active","sdii_COLUMN_warehouse_status"),
    WAREHOUSE_STATUS_INACTIVE("sdii_CODE_warehouse_status_inactive","sdii_COLUMN_warehouse_status"),
    //Tipo de Movimiento (Autorizado, Cambia inventario, automático)
    MOVEMENT_TYPE_CHANGE_INV_YES("sdii_CODE_WH_MOVEMENT_TYPE_CHANGE_INV_YES","sdii_COLUMN_WH_MOVEMENT_TYPE_CHANGE_INV"),
	MOVEMENT_TYPE_CHANGE_INV_NO("sdii_CODE_WH_MOVEMENT_TYPE_CHANGE_INV_NO","sdii_COLUMN_WH_MOVEMENT_TYPE_CHANGE_INV"),
	MOVEMENT_TYPE_AUTHORIZED_YES("sdii_CODE_WH_MOVEMENT_TYPE_AUTHORIZED_YES","sdii_COLUMN_WH_MOVEMENT_TYPE_AUTHORIZED"),
	MOVEMENT_TYPE_AUTHORIZED_NO("sdii_CODE_WH_MOVEMENT_TYPE_AUTHORIZED_NO","sdii_COLUMN_WH_MOVEMENT_TYPE_AUTHORIZED"),
	MOVEMENT_TYPE_AUTOMATIC_YES("sdii_CODE_WH_MOVEMENT_TYPE_AUTOMATIC_YES","sdii_COLUMN_WH_MOVEMENT_TYPE_AUTOMATIC"),
	MOVEMENT_TYPE_AUTOMATIC_NO("sdii_CODE_WH_MOVEMENT_TYPE_AUTOMATIC_NO","sdii_COLUMN_WH_MOVEMENT_TYPE_AUTOMATIC"),
	MOVEMENT_TYPE_TYPE_ENTRY("sdii_CODE_WH_MOVEMENT_TYPE_ENTRY","sdii_COLUMN_WH_MOVEMENT_TYPE_CLASS"),
	MOVEMENT_TYPE_TYPE_EXIT("sdii_CODE_WH_MOVEMENT_TYPE_EXIT","sdii_COLUMN_WH_MOVEMENT_TYPE_CLASS"),
	//Element Class Se agrega el prefijo para generación de código
	ELEMENT_CLASS_CODE_PREFIX("sdii_CODE_element_class_code_prefix","sdii_COLUMN_element_class_code"),
	//Element Brand Se agrega el prefijo para generación de código
	ELEMENT_BRAND_CODE_PREFIX("sdii_CODE_element_brand_code_prefix","sdii_COLUMN_element_brand_code"),
	//MovementType Se agrega el prefijo para generación de código
	MOVEMENT_TYPE_CODE_PREFIX("sdii_CODE_wh_movement_type_code_prefix","sdii_COLUMN_wh_movement_type_code"),
	//WarehouseType Se agrega el prefijo para generación de código
	WAREHOUSE_TYPE_CODE_PREFIX("sdii_CODE_warehouse_type_code_prefix","sdii_COLUMN_wo_type_code"),
	//Warehouse Se agrega el prefijo para generación de código
	WAREHOUSE_CODE_PREFIX("sdii_CODE_warehouse_code_prefix","sdii_COLUMN_warehouse_code"),
	//MeasureUnit Se agrega el prefijo para generación de código
	MEASURE_UNIT_CODE_PREFIX("sdii_CODE_measure_unit_code_prefix","sdii_COLUMN_measure_unit_code"),
	
	// jvelez -- Configuración de los movimientos
	MOV_CMD_STATUS_PENDING("sdii_CODE_MOV_CMD_STATUS_PENDING","sdii_COLUMN_MOV_CMD_STATUS"),
	MOV_CMD_STATUS_PROCESSING("sdii_CODE_MOV_CMD_STATUS_PROCESSING","sdii_COLUMN_MOV_CMD_STATUS"),
	MOV_CMD_STATUS_ERROR("sdii_CODE_MOV_CMD_STATUS_ERROR","sdii_COLUMN_MOV_CMD_STATUS"),
	MOV_CMD_STATUS_NO_CONFIG("sdii_CODE_MOV_CMD_STATUS_NO_CONFIG","sdii_COLUMN_MOV_CMD_STATUS"), 
	MOV_CMD_STATUS_NO_IBS_STATUS("sdii_CODE_MOV_CMD_STATUS_NO_NO_IBS_STATUS","sdii_COLUMN_MOV_CMD_STATUS"),
	MOV_CMD_STATUS_PROCESSED("sdii_CODE_MOV_CMD_STATUS_PROCESSED","sdii_COLUMN_MOV_CMD_STATUS"),
	
	
	FILE_STATUS_PENDING("sdii_CODE_file_status_pending","sdii_COLUMN_file_status"),
	FILE_STATUS_PROCESSING("sdii_CODE_file_status_processing","sdii_COLUMN_file_status"),
	FILE_STATUS_PROCESS_ENDED_WITH_ERRORS("sdii_CODE_file_status_process_ended_with_errors","sdii_COLUMN_file_status"),
	FILE_STATUS_PROCESS_ENDED_WITHOUT_ERRORS("sdii_CODE_file_status_process_ended_without_errors","sdii_COLUMN_file_status"), 
	
	UPLOAD_CODE_PREFIX_FILE_OUT("sdii_CODE_upload_code_prefix_file_out","sdii_CODE_upload_code_prefix_file_out"),
	
	//Contiene la extensión por defecto para la aplicación, se debe incluir el punto (.ext)
	FILE_APPLICATION_EXT("sdii_CODE_file_application_ext","sdii_COLUMN_file_app_ext"),
    
    FILE_TYPE_ELEMENT_MOV_BET_WHS("sdii_CODE_file_type_element_mov_bet_whs","sdii_COLUMN_file_type"), 
	FILE_TYPE_ELEMENT_CONFIRM("sdii_CODE_file_type_element_confirm","sdii_COLUMN_file_type"),
	FILE_TYPE_IMPORTLOG_SERIALIZEDELEMENTS("sdii_CODE_file_type_importlog_serializaedelements","sdii_COLUMN_file_type"),
	FILE_TYPE_REFERENCE_UPLOAD_DELETE_ELEMENTS("sdii_CODE_file_type_upload_delete_reference_elements","sdii_COLUMN_file_type"),
	FILE_TYPE_REFERENCE_UPLOAD_MASSIVE("sdii_CODE_file_type_upload_massive_references","sdii_COLUMN_file_type"),
	FILE_TYPE_REFERENCE_NOT_SERIALIZED("sdii_CODE_file_type_not_serialized_references","sdii_COLUMN_file_type"),
	FILE_TYPE_REFERENCE_CONFIRM_SERIALIZED("sdii_CODE_file_type_confirm_serialized_references","sdii_COLUMN_file_type"),
	FILE_TYPE_LINK_SERIALIZED_ELEMENT("sdii_CODE_file_type_link_serialized_element","sdii_COLUMN_file_type"),
	FILE_TYPE_UNLINK_SERIALIZED_ELEMENT("sdii_CODE_file_type_un_link_serialized_element","sdii_COLUMN_file_type"),
	FILE_TYPE_SERIALIZED_ELEMENTS_ADJUSTMENT_OUTPUT("sdii_CODE_file_type_serialized_element_adjustment_output","sdii_COLUMN_file_type"),
	FILE_TYPE_SERIALIZED_ELEMENTS_ADJUSTMENT_TRANSFER("sdii_CODE_file_type_serialized_element_adjustment_transfer","sdii_COLUMN_file_type"),
	FILE_TYPE_CHANGE_ELEMENT_TYPE_SERIALIZED_ELEMENTS("sdii_CODE_file_type_change_element_type_serialized_element","sdii_COLUMN_file_type"),
	FILE_TYPE_COMPARE_WAREHOUSE_PHYSICAL_INVENTORY_WITH_SMARTDEALER("sdii_CODE_file_type_Compare_Warehouse_Physical_Inventory_with_SmartDealer","sdii_COLUMN_file_type"),
	FILE_TYPE_CONFIRM_ELEMENTS_MASSIVE_REFERENCES("sdii_CODE_file_type_confirm_elements_massive_references","sdii_COLUMN_file_type"),
	
	FILE_PARAM_ID_IMPORT_LOG("sdii_CODE_file_param_id_import_log","sdii_COLUMN_file_param"),
	FILE_PARAM_DEALER_ID("sdii_CODE_file_param_dealer_id","sdii_COLUMN_file_param"),
	FILE_PARAM_WHQUALITY("sdii_CODE_file_param_whquality","sdii_COLUMN_file_param"),
	FILE_PARAM_WHAVAILABLE("sdii_CODE_file_param_whAvailable","sdii_COLUMN_file_param"),
	FILE_PARAM_USER_ID("sdii_CODE_file_param_user_id","sdii_COLUMN_file_param"),
	FILE_PARAM_REFERENCE_ID("sdii_CODE_file_param_reference_id","sdii_COLUMN_file_param"),
	FILE_PARAM_ACTION("sdii_CODE_file_param_action","sdii_COLUMN_file_param"),
	FILE_PARAM_COUNTRY_ID("sdii_CODE_file_param_COUNTRY_ID","sdii_COLUMN_file_param"),
	FILE_PARAM_ADJUSTMENT_ID("sdii_CODE_file_param_ADJUSTMENT_ID","sdii_COLUMN_file_param"),
	FILE_PARAM_WAREHOUSE_ID("sdii_CODE_file_param_warehouse_ID","sdii_COLUMN_file_param"),
	FILE_PARAM_TRANSFER_REASON_ID("sdii_CODE_file_param_adjustment_transfer_reason_ID","sdii_COLUMN_file_param"),
	FILE_PARAM_ADJUSTMENT_COMENT_ID("sdii_CODE_file_param_adjustment_comment","sdii_COLUMN_file_param"),
	FILE_PARAM_UNIT_NAME_NOT_APPLICABLE("sdii_CODE_file_param_unit_name_not_applicable","sdii_COLUMN_file_param"),
	FILE_PARAM_IS_PREPAID("sdii_CODE_file_param_is_prepaid","sdii_COLUMN_file_param"),
	FILE_PARAM_ELEMENT_TYPE_ID("sdii_CODE_file_param_element_type_id","sdii_COLUMN_file_param"),
	//Parametro para remision en precarga o no
	REFERENCE_PRELOAD("sdii_CODE_reference_is_preload","sdii_COLUMN_reference_preload"),
	REFERENCE_NO_PRELOAD("sdii_CODE_reference_is_not_preload","sdii_COLUMN_reference_preload"),
	
	//Parametro para remision en prepago o no
	REFERENCE_PREPAID("sdii_CODE_reference_is_prepaid","sdii_COLUMN_reference_prepaid"),
	REFERENCE_NO_PREPAID("sdii_CODE_reference_is_not_prepaid","sdii_COLUMN_reference_prepaid"),
	
	REFERENCE_QUALITY_CTRL("sdii_CODE_reference_is_quality_ctrl","sdii_CODE_reference_quality_ctrl"),
	REFERENCE_NO_QUALITY_CTRL("sdii_CODE_reference_is_not_prepaid","sdii_CODE_reference_quality_ctrl"),
	
	//Tipos de ajuste
	ADJUSTMENT_TYPE_INPUT("sdii_CODE_adjustment_type_input","sdii_CODE_adjustment_type"),
	ADJUSTMENT_TYPE_OUTPUT("sdii_CODE_adjustment_type_output","sdii_CODE_adjustment_type"),
	ADJUSTMENT_TYPE_TRANSFER("sdii_CODE_adjustment_type_transfer","sdii_CODE_adjustment_type"),
	
	//Estado de ajuste
	
	ADJUSTMENT_STATUS_PROCESS("sdii_CODE_adjustment_status_process","sdii_CODE_adjustment_status"),
	ADJUSTMENT_STATUS_PENDING("sdii_CODE_adjustment_status_pending","sdii_CODE_adjustment_status"),
	ADJUSTMENT_STATUS_AUTHORIZED("sdii_CODE_adjustment_status_authorized","sdii_CODE_adjustment_status"),
	ADJUSTMENT_STATUS_CREATE("sdii_CODE_adjustment_status_create","sdii_CODE_adjustment_status"),
	ADJUSTMENT_STATUS_PARTIAL_AUTHORIZED("sdii_CODE_adjustment_status_partial_authorized","sdii_CODE_adjustment_status"),
	ADJUSTMENT_STATUS_AUTHORIZING("sdii_CODE_adjustment_status_authorizing","sdii_CODE_adjustment_status"),

	
	//Estados de los elementos del documento de ajuste
	ADJUSTMENT_ELEMENTS_STATUS_PROCESS("sdii_CODE_adjustment_elements_status_process","sdii_CODE_adjustment_status"),
	ADJUSTMENT_ELEMENTS_STATUS_PENDING("sdii_CODE_adjustment_elements_status_pending","sdii_CODE_adjustment_status"),
	ADJUSTMENT_ELEMENTS_STATUS_AUTHORIZED("sdii_CODE_adjustment_elements_status_authorized","sdii_CODE_adjustment_status"),

	
	//codigos de bodegas tipo taller
	WORKSHOP_WH_CODE_01("sdii_CODE_workshop_wh_code_01","sdii_COLUMN_workshop_wh_code"),
	WORKSHOP_WH_CODE_02("sdii_CODE_workshop_wh_code_02","sdii_COLUMN_workshop_wh_code"),
	WORKSHOP_WH_CODE_03("sdii_CODE_workshop_wh_code_03","sdii_COLUMN_workshop_wh_code"),
	WORKSHOP_WH_CODE_04("sdii_CODE_workshop_wh_code_04","sdii_COLUMN_workshop_wh_code"),
	WORKSHOP_WH_CODE_05("sdii_CODE_workshop_wh_code_05","sdii_COLUMN_workshop_wh_code"),
	
	//definen si un elemento es serializado o no
	ELEMENT_IS_SERIALIZED("sdii_CODE_element_is_serialized","sdii_COLUMN_element_serialized"),
	ELEMENT_NAME_SERIALIZED("sdii_NAME_element_is_serialized","sdii_NAME_element_is_serialized"),
	ELEMENT_IS_NOT_SERIALIZED("sdii_CODE_element_is_not_serialized","sdii_COLUMN_element_serialized"),
	ELEMENT_NAME_NOT_SERIALIZED("sdii_NAME_element_is_not_serialized","sdii_NAME_element_is_not_serialized"),
	
	//Nombres de comando de los reportes
	CMD_REPORT_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_serialized_element_by_dealer_serialcode_typeelement","sdii_report_type"),
	
	
	//Parametros de reportes
	CMD_REPORT_PARAM_COMPANY_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_company_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_BRANCH_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_branch_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_WAREHOUSE_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_warehouse_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_SERIAL_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_serial_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_CODE_TYPE_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_codetype_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_COUNTRY_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT("sdii_CODE_cmd_report_param_country_serialized_element_by_dealer_serialcode_typeelement","sdii_report_param"),
	CMD_REPORT_PARAM_CUSTOMER_IBS_CODE("sdii_CODE_cmd_report_param_customer_ibscode","sdii_report_param"),
	CMD_REPORT_PARAM_DOCUMENT_TYPE_IDENTIFICATION("sdii_CODE_cmd_report_param_customer_documenttypeid","sdii_report_param"),
	CMD_REPORT_PARAM_COMPANY_CUSTOMER_DOCUMENT_NUMBER("sdii_CODE_cmd_report_param_customer_documentnumber","sdii_report_param"),
	CMD_REPORT_PARAM_COMPANY_COUNTRY_IDENTIFICATION("sdii_CODE_cmd_report_param_customer_countryid","sdii_report_param"),
	CMD_REPORT_PARAM_SERIALCODE_OR_LINK_SERIALCODE("sdii_CODE_cmd_report_param_serialcode_or_link_serialcode","sdii_report_param"),
	
	//Parametros de reportes del CU INV 052
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DEALER_ID_BRANCH("sdii_CODE_cmd_report_quantity_warehouse_element_filter_dealeridbranch","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DEALER_ID_COMPANY("sdii_CODE_cmd_report_quantity_warehouse_element_filter_dealeridcompany","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_CREW_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_crewid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_TYPE_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_whtypeid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_whid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_MODEL_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_elementmodelid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_TYPE_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_typeelementid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVEMENT_DATE_IN("sdii_CODE_cmd_report_quantity_warehouse_element_filter_movementdatein","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVEMENT_DATE_OUT("sdii_CODE_cmd_report_quantity_warehouse_element_filter_movementdateout","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVCLASS("sdii_CODE_cmd_report_quantity_warehouse_element_filter_movclass","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVTYPE_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_movtypeid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DOCUMENT_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_documentid","sdii_report_param"),
	CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DOCUMENT_CLASS_ID("sdii_CODE_cmd_report_quantity_warehouse_element_filter_documentclassid","sdii_report_param"),

	CMD_REPORT_PARAM_CUSTOMER_ID("sdii_CODE_cmd_report_param_customer_id","sdii_report_param"),
	CMD_REPORT_PARAM_CUSTOMER_CODE("sdii_CODE_cmd_report_param_customer_code","sdii_report_param"),
	CMD_REPORT_PARAM_SERIAL("sdii_CODE_cmd_report_param_serial","sdii_report_param"),
	CMD_REPORT_PARAM_START_DATE("sdii_CODE_cmd_report_param_start_date","sdii_report_param"),
	CMD_REPORT_PARAM_END_DATE("sdii_CODE_cmd_report_param_end_date","sdii_report_param"),
	CMD_REPORT_PARAM_COUNTRY_ID("sdii_CODE_cmd_report_param_country_id","sdii_report_param"),
	CMD_REPORT_PARAM_DEALER_ID("sdii_CODE_cmd_report_param_dealer_id","sdii_report_param"),
	CMD_REPORT_PARAM_TRANSFER_REASON_NAME("sdii_CODE_cmd_report_param_transfer_reason_name","sdii_report_param"),
    
    DOCUMENT_TYPE_NIT("sdii_CODE_document_type_nit","sdii_COLUMN_document_type"),
    DOCUMENT_TYPE_CC("sdii_CODE_document_type_cc","sdii_COLUMN_document_type"),
    DOCUMENT_TYPE_RUC("sdii_CODE_document_type_ruc","sdii_COLUMN_document_type"),
    DOCUMENT_TYPE_PAS("sdii_CODE_document_type_pas","sdii_COLUMN_document_type"),
    DOCUMENT_TYPE_NA("sdii_CODE_document_type_na","sdii_COLUMN_document_type"),
    CORE_PROGRAM_CREW_IS_NULL("sdii_CODE_program_crew_is_null","sdii_CODE_program_crew_is_null"),
    CODE_CAPACITY_DATE_SERVICE_HOUR_SKILL("sdii_CODE_capacity_date_service_hour_skill","sdii_CODE_capacity_date_service_hour_skill"),
    
    LOAD_FILE_TYPE_IN("sdii_CODE_load_file_type_in","sdii_COLUMN_load_file_type"),
    LOAD_FILE_TYPE_OUT("sdii_CODE_load_file_type_out","sdii_COLUMN_load_file_type"),
    
    CODE_SERVICE_INSTALL_UPGRADE_DOWNGRADE("sdii_CODE_service_install_upgrade_downgrade","sdii_CODE_service_install_upgrade_downgrade"),
    CODE_SYS_PARAM_ENABLED_SALE_INSTALL_UPGRADE_DOWNGRADE("sdii_CODE_sys_param_enabled_sale_install_upgrade_downgrade","sdii_CODE_sys_param_enabled_sale_install_upgrade_downgrade"),
    CODE_SYS_PARAM_LOCK_PRIOR_CALENDAR("sdii_CODE_sys_param_lock_prior_calendar","sdii_CODE_sys_param_lock_prior_calendar"),
    CODE_SYS_PARAM_ENABLED_CONDITION_PREPAID("sdii_CODE_sys_param_enabled_condition_prepaid","sdii_CODE_sys_param_enabled_condition_prepaid"),
    CODE_SYS_PARAM_INSTALL_PREPAID_VALUE("sdii_CODE_installation_prepaid_value","sdii_CODE_installation_prepaid_value"),
    CODE_SYS_PARAM_SERVICE_PREPAID_VALUE("sdii_CODE_service_prepaid_value","sdii_CODE_service_prepaid_value"),
    CODE_UPLOAD_FILE_LOCATION("sdii_CODE_upload_file_location",""),    
    DOCUMENT_CLASS_REFERENCE("sdii_CODE_document_class_reference","sdii_COLUMN_document_class"),
    DOCUMENT_CLASS_IMPORT_LOG("sdii_CODE_document_class_import_log","sdii_COLUMN_document_class"),
    DOCUMENT_CLASS_ADJUSTMENT("sdii_CODE_document_class_adjustment","sdii_COLUMN_document_class"),
    DOCUMENT_CLASS_WORK_ORDER("sdii_CODE_document_class_work_order","sdii_COLUMN_document_class"),
    
    DOCUMENT_CLASS_REFERENCE_ID("sdii_CODE_document_class_reference_id","sdii_COLUMN_document_class_id"),
    DOCUMENT_CLASS_IMPORT_LOG_ID("sdii_CODE_document_class_import_log_id","sdii_COLUMN_document_class_id"),
    DOCUMENT_CLASS_ADJUSTMENT_ID("sdii_CODE_document_class_adjustment_id","sdii_COLUMN_document_class_id"),
    DOCUMENT_CLASS__WORK_ORDER_ID("sdii_CODE_document_class_work_order_id","sdii_COLUMN_document_class_id"),
    
    //variable para manejo del nombre del jasper para generar excel de remisiones
	REFERENCE_EXCEL_JASPER_FILE("sdii_CODE_reference_jasper_file","sdii_COLUMN_reference_jasper_file"),
	
	//Variables para el manejo de los tipos de proceso para reportar a IBS
	
	
	PROCESS_CODE_IBS_ADJUSMENT("sdii_CODE_ibs_process_adjustment","sdii_CODE_ibs_process_code"),
	PROCESS_CODE_IBS_QUALITY_CONTROL("sdii_CODE_ibs_process_quality_control","sdii_CODE_ibs_process_code"),
	PROCESS_CODE_IBS_IMPORT_LOG("sdii_CODE_ibs_process_import_log","sdii_CODE_ibs_process_code"),
	PROCESS_CODE_IBS_REFERENCE("sdii_CODE_ibs_process_reference","sdii_CODE_ibs_process_code"),
	PROCESS_CODE_IBS_OTHER("sdii_CODE_ibs_process_other","sdii_CODE_ibs_process_code"),
	PROCESS_CODE_IBS_RECOVERY("sdii_CODE_ibs_process_Recovery","sdii_CODE_ibs_process_code"),
	
	
	//Códigos IBS
	IBS_CODE_T01_IN_TRANSIT_FROM_PROVIDER("sdii_CODE_ibs_code_T01_in_transit_from_provider","sdii_CODE_ibs_code"),
	IBS_CODE_S04_STOCK_QUALITY_CONTROL("sdii_CODE_ibs_code_S04_stock_quality_control","sdii_CODE_ibs_code"),
	IBS_CODE_S01_STOCK("sdii_CODE_ibs_code_S01_stock","sdii_CODE_ibs_code"),
	IBS_CODE_S02_STOCK_BULK("sdii_CODE_ibs_code_S02_stock_bulk","sdii_CODE_ibs_code"),
	IBS_CODE_S05_STOCK_SERVICE("sdii_CODE_ibs_code_S05_stock_service","sdii_CODE_ibs_code"),
	IBS_CODE_S06_STOCK_HOLDER_WARRANTY_PREPAID("sdii_CODE_ibs_code_S06_stock_holder_garantia_prepago","sdii_CODE_ibs_code"),
	IBS_CODE_T02_STOCK_TRANSIT_STOCK_HOLDER("sdii_CODE_ibs_code_T02_en_transito_a_Stock_Holder","sdii_CODE_ibs_code"),
	IBS_CODE_T03_STOCK_TRANSIT_STOCK_HOLDER_SERVICE("sdii_CODE_ibs_code_T03_en_transito_a_Stock_Holder_Service","sdii_CODE_ibs_code"),
	IBS_CODE_T04_STOCK_TRANSIT_STOCK_HOLDER_BULK("sdii_CODE_ibs_code_T04_en_transito_a_Stock_Holder_Bulk","sdii_CODE_ibs_code"),
	IBS_CODE_T04_STOCK_TRANSIT_STOCK_HOLDER_BULK_WARRANTY_PREPAID("sdii_CODE_ibs_code_T06_en_transito_a_stock_holder_garantia_prepago","sdii_CODE_ibs_code"),
	
	TRANSFER_REASON_ACTIVE("sdii_CODE_tranfer_reason_active","sdii_CODE_ibs_code_tranfer_reason"),
	TRANSFER_REASON_INACTIVE("sdii_CODE_tranfer_reason_inactive","sdii_CODE_ibs_code_tranfer_reason"),
	TRANSFER_REASON_AUTHORIZED_YES("sdii_CODE_TRANSFER_REASON_AUTHORIZED_YES","sdii_CODE_ibs_code_tranfer_reason"),
	TRANSFER_REASON_AUTHORIZED_NO("sdii_CODE_TRANSFER_REASON_AUTHORIZED_NO","sdii_CODE_ibs_code_tranfer_reason"),
	TRANSFER_REASON_AUTOMATIC_YES("sdii_CODE_TRANSFER_REASON_AUTOMATIC_YES","sdii_CODE_ibs_code_tranfer_reason"),
	TRANSFER_REASON_AUTOMATIC_NO("sdii_CODE_TRANSFER_REASON_AUTOMATIC_NO","sdii_CODE_ibs_code_tranfer_reason"),
	
	TRANSFER_REASON_EXCEL_JASPER_FILE("sdii_CODE_transfer_reason_jasper_file","sdii_COLUMN_transfer_reason_jasper_file"),
	WAREHOUSES_EXCEL_JASPER_FILE("sdii_CODE_warehouses_jasper_file","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_WAREHOUSE_ELEMENT_CUSTOMER_ACTUAL("sdii_CODE_report_warehouse_element_customer_actual","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_MOVEMENT_QUEUE_HSP_TO_IBS_BY_FILTER("sdii_CODE_report_movement_queue_hsp_to_ibs_by_filter","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_IMPORT_LOG_BY_CRITERIA("sdii_CODE_report_import_log_by_criteria","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_IMPORT_LOG_SERIALIZED_ELEMENTS("sdii_CODE_report_import_log_serialized_elements","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_IMPORT_LOG_NOT_SERIALIZED_ELEMENTS("sdii_CODE_report_import_log_not_serialized_elements","sdii_COLUMN_warehouses_jasper_file"),
	SDII_JASPER_CORE_PROCES("sdii_jasper_core_proces",""),
	SDII_JASPER_ALLOCATOR_PROCES("sdii_jasper_allocator_proces",""),
	VISITS_EXCEL_JASPER_FILE ("sdii_CODE_visits_excel_jasper_file","sdii_COLUMN_visits_excel_jasper_file"),
	
	REPORT_ADJUSTMENT_ELEMENTS_SERIALIZED_AUTHORIZATION("sdii_CODE_report_adjustment_elements_serialized_authorization","sdii_COLUMN_warehouses_jasper_file"),
	REPORT_ADJUSTMENT_ELEMENTS_NOT_SERIALIZED_AUTHORIZATION("sdii_CODE_report_adjustment_elements_not_serialized_authorization","sdii_COLUMN_warehouses_jasper_file"),
	
	//Tipos de movimientos
	MOVEMENT_TYPE_TYPE_DEVOLUTION_FROM_CUST("sdii_CODE_mov_type_dev_from_cust","sdii_COLUMN_mov_type"),
	MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_IN("sdii_CODE_mov_type_warehouse_transit_in","sdii_COLUMN_mov_type"),
	MOVEMENT_TYPE_WAREHOUSE_TYPE_TRANSIT_OUT("sdii_CODE_mov_type_warehouse_transit_out","sdii_COLUMN_mov_type"),
	
	//Códigos de procesos de movimientos de inventario en interacción con IBS
	TYPE_COMUNICATION_HSP_TO_IBS("sdii_CODE_type_comunication_hsp_to_ibs","sdii_CODE_mov_inventory_hsp_ibs"),
	TYPE_COMUNICATION_IBS_TO_HSP("sdii_CODE_type_comunication_ibs_to_hsp","sdii_CODE_mov_inventory_hsp_ibs"),
	COMUNICATION_HSP_IBS_IS_COMMENT_ERROR("sdii_CODE_comunication_hsp_ibs_is_comment_error","sdii_CODE_mov_inventory_hsp_ibs"),
	COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR("sdii_CODE_comunication_hsp_ibs_is_not_comment_error","sdii_CODE_mov_inventory_hsp_ibs"),
	
	TYPE_WORK_ORDER_FOR_INVENTORY_ACTIVATION("sdii_CODE_type_work_order_for_inventory_activation","sdii_CODE_type_work_order_for_inventory"),
	TYPE_WORK_ORDER_FOR_INVENTORY_UPGRADE_AND_DOWNGRADE("sdii_CODE_type_work_order_for_inventory_upgrade_and_downgrade","sdii_CODE_type_work_order_for_inventory"),
	TYPE_WORK_ORDER_FOR_INVENTORY_SWOP("sdii_CODE_type_work_order_for_inventory_swop","sdii_CODE_type_work_order_for_inventory"),
	TYPE_WORK_ORDER_FOR_INVENTORY_DISCONNECTION("sdii_CODE_type_work_order_for_inventory_disconnection","sdii_CODE_type_work_order_for_inventory"),
	
	TYPE_MOVEMENT_BY_REASON_TO_CUSTOMER_FOR_SWAP("sdii_CODE_type_movement_by_reason_to_customer_for_swap","type_movement_by_reason_for_swap"),
	TYPE_MOVEMENT_BY_REASON_TO_COMPANY_FOR_SWAP("sdii_CODE_type_movement_by_reason_to_company_for_swap","type_movement_by_reason_for_swap"),
	CODE_WORKORDER_CSR_STATUS_PENDIENTE("sdii_CODE_workorder_csr_status_pendiente","sdii_COLUMN_workorder_csr_status"),
    CODE_WORKORDER_CSR_STATUS_AGENDADA("sdii_CODE_workorder_csr_status_agendada","sdii_COLUMN_workorder_csr_status"),
    CODE_WORKORDER_CSR_STATUS_TERMINADA("sdii_CODE_workorder_csr_status_terminada","sdii_COLUMN_workorder_csr_status"),
    
    //bandeja de ajustes
    ADJUSTMENT_REPORT_FOR_AUTHORIZATION("sdii_CODE_adjustment_report_for_authorization","sdii_COLUMN_adjustment_report_for_authorization"),

    //Configuracion de time manager
    CODE_SCHEDULER_TASK_STATUS_ACTIVE("sdii_CODE_scheduler_task_status_active",""),
    CODE_SCHEDULER_TASK_STATUS_INACTIVE("sdii_CODE_scheduler_task_status_inactive",""),
    CODE_SCHEDULER_TASK_CODE_ALLOCATOR("sdii_CODE_timer_manager_code_allocator",""),
    CODE_SCHEDULER_TASK_CODE_KPI("sdii_CODE_timer_manager_code_kpi",""),
    CODE_SCHEDULER_TASK_CODE_FILE_PROCESS("sdii_CODE_timer_manager_code_file_process",""),
    CODE_SCHEDULER_TASK_CODE_COMMAND_IBS("sdii_CODE_timer_manager_code_command_ibs",""),
    CODE_SCHEDULER_TASK_CODE_REPORT_WORKORDER("sdii_CODE_timer_manager_code_report_workOrder",""),
    CODE_SCHEDULER_TASK_CODE_CORE("sdii_CODE_timer_manager_code_core",""),
    CODE_SCHEDULER_TASK_CODE_PARALLEL_PROCESSING_CORE_ALLOCATOR("sdii_CODE_parallel_processing_core_allocator",""),
    CODE_SCHEDULER_TASK_CODE_PARALLEL_PROCESSING_CORE_ALLOCATOR_REPORT("sdii_CODE_parallel_processing_core_allocator_report",""),
    CODE_SCHEDULER_TASK_CODE_REPORTS_HSP("sdii_CODE_timer_manager_code_reports_hsp",""),
    CODE_SCHEDULER_TASK_CODE_DOWNLOAD_CUSTOMER_CONTACT("sdii_CODE_timer_manager_code_download_customer_contact",""),
    CODE_SCHEDULER_TASK_CODE_UPDATE_DEALER_BUILDING("sdii_CODE_timer_manager_code_update_dealer_building",""),
    
    CODE_SCHEDULER_TASK_DETAIL_NO_FINISHED_PROCCESS("sdii_CODE_scheduler_task_detail_no_finished_proccess",""),
    CODE_SCHEDULER_TASK_DETAIL_FINISHED_PROCCESS("sdii_CODE_scheduler_task_detail_finished_proccess",""),
    CODE_SCHEDULER_TASK_DETAIL_FINISHED_PROCCESS_WITH_ERRORS("sdii_CODE_scheduler_task_detail_finished_proccess_with_errors",""),
	CONSTANT_REPORT("sdii_constant_report","sdii_constant_report"),
	CONSTANT_REPORT_CORE_PRODUCTIVITY("sdii_CONSTANT_REPORT_CORE_PRODUCTIVITY","sdii_constant_report"),
	CONSTANT_REPORT_CORE_PENDING_SERVICES_BY_DATE("sdii_CONSTANT_REPORT_CORE_PENDING_SERVICES_BY_DATE","sdii_constant_report"),
	CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE("sdii_CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE","sdii_constant_report"),
	//REQ002 - WO Agendadas en linea.
	CONSTANT_REPORT_CORE_SUCCEED_WO("sdii_CONSTANT_REPORT_CORE_SUCCEED_WO","sdii_constant_report"),
	CONSTANT_MAX_TIME_REPORT_CORE_COMPLY_AND_SCHEDULE("sdii_MAX_TIME_IN_DAYS_CONSTANT_REPORT_CORE_COMPLY_AND_SCHEDULE","sdii_constant_report"),
	//sdii_MAX_TIME_IN_DAYS_CONSTANT_REPORT_CORE_PRODUCTIVITY
	CONSTANT_MAX_TIME_REPORT_CORE_PRODUCTIVITY("sdii_MAX_TIME_IN_DAYS_CONSTANT_REPORT_CORE_PRODUCTIVITY","sdii_constant_report"),
	CODE_REPORT_LAST_DAY_WO("sdii_code_report_last_day_wo","sdii_code_report_last_day_wo"),
	//CC053 - HSP Reportes - CRUD Programacion.
	CONSTANT_MAX_SCHEDULE_REPORTS_BY_COUNTRY("sdii_CONSTANT_MAX_SCHEDULE_REPORTS_BY_COUNTRY","sdii_constant_report"),
	//CONSTANT_MAX_RANGE_MONTHS_BETWEEN("sdii_CONSTANT_MAX_RANGE_MONTHS_BETWEEN","sdii_constant_report"),
	CONSTANT_MAX_RANGE_MONTHS_BETWEEN("sdii_CODE_max_range_months_between",""),
	//SCHEDULE REPORT   INCLUDE_LAST_TIME
	CODE_INCLUDE_LAST_TIME("sdii_CODE_include_last_time","sdii_COLUMN_include_last_time"),
	CODE_NOT_INCLUDE_LAST_TIME("sdii_CODE_not_include_last_time","sdii_COLUMN_include_last_time"),
	
	DEALER_DETAIL_ATTEND_TYPE_ODD("sdii_code_dealer_detail_attend_type_odd",""),
	DEALER_DETAIL_ATTEND_TYPE_EVEN("sdii_code_dealer_detail_attend_type_even",""),
	
	SDII_CORRECT_ID_ADDRESS_FROM_RESB("sdii_correct_id_address_from_resb",""),
	
	REASON_FOR_REMOVE_WORK_ORDER_SERVICE("sdii_reason_for_remove_work_order_service",""),
	REASON_FOR_ADD_SERVICE_TO_WORK_ORDER("sdii_reason_for_add_service_to_work_order",""),
	
	
	GET_VISTA_360_DAYS("sdii_CODE_get_vista_360_days",""),
	GET_VISTA_360_STATUS("sdii_CODE_get_vista_360_status",""),
	
	GET_VISTA_360_ADDRESS_TYPE_DEFAULT("sdii_CODE_get_vista_360_address_type_default",""),
	GET_VISTA_360_ADDRESS_TYPE_BILLING("sdii_CODE_get_vista_360_address_type_billing",""),
	GET_VISTA_360_ADDRESS_TYPE_SHIPPING("sdii_CODE_get_vista_360_address_type_shipping",""),
	GET_VISTA_360_ADDRESS_TYPE_BANK("sdii_CODE_get_vista_360_address_type_bank",""),
	GET_SDII_CODE_V360_BALANCE("sdii_CODE_V360_BALANCE",""),
	GET_SDII_CODE_V360_OVERDUE("sdii_CODE_V360_OVERDUE",""),
	GET_SDII_CODE_V360_LAST_INVOICED("sdii_CODE_V360_LAST_INVOICED",""),
	GET_SDII_CODE_V360_DOMAIN_NAME("sdii_CODE_V360_DOMAIN_NAME",""),
	GET_SDII_CODE_V360_DOMAIN_NAME_WORKORDER_TYPESERVICE("sdii_CODE_V360_DOMAIN_NAME_WORKORDER_TYPESERVICE",""),
	
	CHANGE_WORK_ORDER_WS_IBS_TO_HSP("sdii_CHANGE_WORK_ORDER_WS_IBS_TO_HSP",""),
	
	//Origen del cambio a la workorder
	//El cambio fue desde IBS
	WO_STATUS_WORKORDER_TYPE_CHANGE_IBS("sdii_CODE_wo_status_workorder_type_change_ibs",""),
	//El cambio fue desde HSP
	WO_STATUS_WORKORDER_TYPE_CHANGE_HSP("sdii_CODE_wo_status_workorder_type_change_hsp",""),
	//El cambio fue desde la operacion updateInfoWorkOrderIBSToHSP desde el servicio CoreWoWS
	WO_STATUS_WORKORDER_TYPE_CHANGE_UPDATE("sdii_CODE_wo_status_workorder_type_change_update",""),
	//El cambio fue desde la operacion updateInfoWorkOrderIBSToHSP desde el servicio CoreWoWS
	WO_STATUS_WORKORDER_TYPE_CHANGE_HSP_CSR_SCHEDULE("sdii_CODE_wo_status_workorder_type_change_hsp_csr_schedule",""),
	//WO_INFO_ESB status codes
	SDII_WO_INFO_ESB_PENDING("sdii_wo_info_esb_pending",""),
	SDII_WO_INFO_ESB_STARTED("sdii_wo_info_esb_started",""),
	SDII_WO_INFO_ESB_PENDING_REPROCESSING("sdii_wo_info_esb_pending_reprocessing",""),
	SDII_WO_INFO_ESB_FINISHED("sdii_wo_info_esb_finished",""),
	SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS("sdii_wo_info_esb_finished_with_errors",""),
	SDII_WO_INFO_ESB_CORE("sdii_wo_info_esb_core",""),
	SDII_WO_INFO_ESB_ALLOCATOR("sdii_wo_info_esb_allocator",""),
	SDII_CODE_MAX_TRY_PROCCESS_CORE_ALLOCATOR("sdii_code_max_try_proccess_core_allocator",""),
	SDII_CODE_TIME_TO_PROCCESS_CORE_ALLOCATOR("sdii_code_time_to_proccess_core_allocator",""),
	SDII_CODE_MAX_TIME_TO_REPROCCESS_CORE_ALLOCATOR("sdii_code_max_time_to_reproccess_core_allocator",""),
	SDII_PAST_DAYS_FROM_PARALLEL_CORE_ALLOCATOR_PROCCES("sdii_past_days_from_parallel_core_allocator_procces",""),
	SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE("sdii_wo_info_esb_notification_type_core",""),
	SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR("sdii_wo_info_esb_notification_type_allocator",""),
	SDII_WO_INFO_ESB_NOTIFICATION_TYPE_CORE_WARNING("sdii_wo_info_esb_notification_type_core_warning",""),
	SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR_WARNING("sdii_wo_info_esb_notification_type_allocator_warning",""),
	
	//WO_INFO_ESB status codes
	SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR("sdii_update_workOrder_origin_update_event_allocator",""),
	SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE("sdii_update_workOrder_origin_update_event_core",""),
	SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_PARALLEL("sdii_update_workOrder_origin_update_event_parallel",""),
	SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_OPTIMUS("sdii_update_workOrder_origin_update_event_optimus",""),
	
	//WO_INF_ESB delay time in millis
	SDII_WO_INFO_DELAY_TIME_MS("sdii_wo_info_esb_delay_time_ms",""),
	
		
	CUSTOMER_TITLE_NA("sdii_CODE_customer_title_na",""),
	CUSTOMER_LAST_NAME_DEFAULT("sdii_CODE_customer_last_name_default",""),
		
	WORK_ORDER_MARK_IS_ACTIVE("sdii_CODE_workorder_mark_is_active",""),
	WORK_ORDER_MARK_IS_INACTIVE("sdii_CODE_workorder_mark_is_inactive",""),
	
	CALL_SERVICE_REQUIRED_CONTRACT("sdii_CODE_call_service_required_contract",""),
	
	CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT("sdii_CODE_workorder_mark_required_contract","sdii_COLUMN_work_order_mark"),
	CODE_WORK_ORDER_MARK_PRIORITY_SERVICE("sdii_CODE_workorder_mark_priority_service","sdii_COLUMN_work_order_mark"),
	CODE_WORK_ORDER_MARK_PRIORITY_NEXUS("sdii_CODE_workorder_mark_priority_nexus","sdii_COLUMN_work_order_mark"),
	CODE_WORK_ORDER_MARK_RECLAIM("sdii_CODE_workorder_mark_reclaim","sdii_COLUMN_work_order_mark"),
	CODE_WORK_ORDER_MARK_REJECTED_BY_CREW("sdii_CODE_workorder_mark_rejected_by_crew","sdii_COLUMN_work_order_mark"),
	
	CODE_REPORTS_PROCESS_PENDING("sdii_CODE_reports_process_pending",""),
	CODE_REPORTS_PROCESS_STARTED("sdii_CODE_reports_process_started",""),
	CODE_REPORTS_PROCESS_FINISHED("sdii_CODE_reports_process_finished",""),
	
	CODE_HOUR_OF_REPORTS("sdii_CODE_hour_of_reports",""),
	
	WO_MANAGMENT_ELEMENT_CLASS_DECO("sdii_CODE_wo_managment_element_class_deco",""),
	WO_MANAGMENT_ELEMENT_CLASS_SC("sdii_CODE_wo_managment_element_class_sc",""),
	
	REPORTS_PROCESS_PERIOD_TYPE_NA("sdii_CODE_reports_process_period_type_NA",""),
	REPORTS_PROCESS_PERIOD_TYPE_DIARY("sdii_CODE_reports_process_period_type_diary",""),
	REPORTS_PROCESS_PERIOD_TYPE_MONTH("sdii_CODE_reports_process_period_type_month",""),
	
	REPORT_TYPE_CORE_MONTHLY_ACTIVITY("sdii_CODE_report_type_core_monthly_activity",""),
	REPORT_TYPE_CORE_JOIN_MONTHLY_ACTIVITY("sdii_CODE_report_type_core_join_monthly_activity",""),
	REPORT_TYPE_CORE_BACKLOG_ACTIVITY("sdii_CODE_report_type_core_backlog_activity",""),
	REPORT_TYPE_CORE_ACTIVITY_BACKLOG_DISCONNECTS("sdii_CODE_report_type_core_activity_backlog_disconnects",""),
	REPORT_TYPE_CORE_PRODUCTIVITY_DISPATCHERS("sdii_CODE_report_type_core_productivity_dispatchers",""),
	//REQ002 - WO Agendadas en linea.
	REPORT_TYPE_CORE_SUCCEED_WORK_ORDERS("sdii_CODE_report_type_core_succeed_workorders",""),

	//REQ009 - Servicio para traer información de Técnico Auxiliar
	REPORT_TYPE_CORE_AUXILIAR_EMPLOYEE("sdii_CODE_report_type_core_auxiliar_employee",""),
	REPORT_PARAMETER_WO_STATUS("sdii_CODE_report_parameter_wo_status",""),
	
	//REQxxx - Proceso de inactivación de técnico (Auditoría sobre los movimientos de cuadrillas)
	REPORT_TYPE_CREW_MOVEMENTS("sdii_CODE_report_type_crew_movements",""),
	REPORT_TYPE_WO_TECHNICIAL("sdii_CODE_report_type_wo_technicial",""),
	
	//CC54 WO pendientes por falta de materiales
	REPORT_TYPE_CORE_REJECTED_RN_03_WORK_ORDERS("sdii_CODE_report_type_core_wo_pending_lack_materials",""),
	//CC053 - HSP Reportes de inventarios.
	REPORT_TYPE_STOCK_QTY_WAREHOUSES_ELEMENTS("sdii_CODE_report_type_stock_qty_warehouses_elements",""),
	REPORT_TYPE_STOCK_WAREHOUSES_ELEMENTS_IN_DETAILS("sdii_CODE_report_type_stock_warehouses_elements_in_details",""),
	REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS_KARDEX("sdii_CODE_report_type_stock_warehouses_movements_kardex",""),
	REPORT_TYPE_STOCK_WAREHOUSES_MOVEMENTS("sdii_CODE_report_type_stock_warehouses_movements",""),

	
	REPORT_PROCESS_CLASS_USER("sdii_CODE_reports_process_class_user",""),
	REPORT_PROCESS_CLASS_SYSTEM("sdii_CODE_reports_process_class_system",""),
	//sdii_CODE_user_control_tower
	CODE_USER_CONTROL_TOWER("sdii_CODE_user_control_tower",""),
	
	//MEDIA CONTACT TYPES

	
	//REGISTRO DEL TECNICO
	
	CODE_REASON_ASSIGN_TECHNICIAN("SDII_CODE_REASON_ASSIGN_TECHNICIAN","SDII_CODE_REASON_ASSIGN_TECHNICIAN"),
	CODE_REASON_ATTENTION_TECHNICIAN("SDII_CODE_REASON_ATTENTION_TECHNICIAN","SDII_CODE_REASON_ATTENTION_TECHNICIAN"),
	DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY("CODE_DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY","CODE_DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY"),
	INDIVIDUAL_DIFICULTAD_BANDEJAAUTO("INDIVIDUAL_DIFICULTAD_BANDEJAAUTO","INDIVIDUAL_DIFICULTAD_BANDEJAAUTO"),
	INDIVIDUAL_RECHAZAR_BANDEJAAUTO("INDIVIDUAL_RECHAZAR_BANDEJAAUTO","INDIVIDUAL_RECHAZAR_BANDEJAAUTO"),
	GRUPAL_ASIGNARCUADRILLA_BANDEJAAUTO("GRUPAL_ASIGNARCUADRILLA_BANDEJAAUTO","GRUPAL_ASIGNARCUADRILLA_BANDEJAAUTO"),
	GRUPAL_ASIGNARCUADRILLADINAMICA_BANDEJAAUTO("GRUPAL_ASIGNARCUADRILLADINAMICA_BANDEJAAUTO","GRUPAL_ASIGNARCUADRILLADINAMICA_BANDEJAAUTO"),
	
	CODE_SHOW_FIELD_CUSTOMER_DOCUMENT_REPORT_EXCEL("SDII_CODE_SHOW_FIELD_CUSTOMER_DOCUMENT_REPORT_EXCEL",""),
	CODE_SHOW_FIELD_CUSTOMER_MAIL_REPORT_EXCEL("SDII_CODE_SHOW_FIELD_CUSTOMER_MAIL_REPORT_EXCEL",""),
	SYSTEM_PARAM_REPORT_WORK_ORDER_REJECT_FILTER_TYPE_WORK_ORDER("sdii_CODE_system_param_report_work_order_reject_filter_type_work_order",""),
	
	SYSTEM_PARAM_GET_CONTRACT_SYSTEM_EXTERNAL("sdii_CODE_system_param_get_contract_system_external",""),
	
	SYSTEM_PARAM_DELAY_DOWNLOAD_CUSTOMER_CONTACT("sdii_CODE_system_param_delay_download_customer_contact",""),
	SYSTEM_PARAM_BEGIN_DATE_DOWNLOAD_CUSTOMER_CONTACT("sdii_CODE_system_param_begin_date_download_customer_contact",""),
	
	CODE_ADDRESS_TYPE_DEFAULT("CODE_ADDRESS_TYPE_DEFAULT","CODE_ADDRESS_TYPE_DEFAULT"),
	CODE_ADDRESS_TYPE_BILLING("CODE_ADDRESS_TYPE_BILLING","CODE_ADDRESS_TYPE_BILLING"),
	CODE_ADDRESS_TYPE_SHIPPING("CODE_ADDRESS_TYPE_SHIPPING","CODE_ADDRESS_TYPE_SHIPPING"),
	CODE_ADDRESS_TYPE_BANK("CODE_ADDRESS_TYPE_BANK","CODE_ADDRESS_TYPE_BANK"),
	
	STATES_FOR_SEARCH_FOR_PDF_CORE("STATES_FOR_SEARCH_FOR_PDF_CORE","STATES_FOR_SEARCH_FOR_PDF_CORE"),
	
	PAGE_SIZE_REPORT_WORK_ORDER_REJECT("sdii_CODE_page_size_report_work_order_reject",""),
	
	CODE_TYPE_DOCUMENT_CONTRACT_EXTERNAL("sdii_CODE_type_document_contract_external",""),
	
	PARALLEL_PROCCES_MAX_RECORDS("sdii_parallel_procces_max_records",""),
	
	//Constantes utilizadas en el control de cambios cc039
	DOWNLOAD_CUSTOMER_CONTACT_PAGE_SIZE("sdii_DOWNLOAD_CUSTOMER_CONTACT_PAGE_SIZE",""),
	DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_THAN_KEY("sdii_DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_THAN_KEY",""),
	DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_KEY("sdii_DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_KEY",""),
	DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_VALUE_OPTION("sdii_DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_VALUE_OPTION",""),
	DOWNLOAD_CUSTOMER_CONTACT_GREATER_THAN_WORK_ORDER_ID("sdii_DOWNLOAD_CUSTOMER_CONTACT_GREATER_THAN_WORK_ORDER_ID",""),
	
	PRODUCT_STATUS_IN_SUSCRIPTOR("sdiii_sp_product_status_in_suscriptor_code",""),
	SP_VALIDATE_TECHNICAL_WITH_DOCUMENT("sdii_system_parameter_VALIDATE_TECHNICAL_WITH_DOCUMENT","sdii_system_parameter_VALIDATE_TECHNICAL_WITH_DOCUMENT"),
	
	
	UPDATE_WORK_ORDER_TYPE_PRODUCT_TECHNOLOGY("sdii_update_work_order_type_product_technology",""),
	
    //Contiene el parámetro del sistema que habilita el paso de PrePago a PostPago
    CODE_SYSTEM_PARAM_ENABLE_PREPAGO_TO_POSTPAGO("sdii_CODE_system_param_prepago_to_postpago",""),
    
    //Req-0096 - Requerimiento Consolidado Asignador
    CUSTOMER_CATEGORY_DEALER_CONF("sdii_customer_category_dealer_conf","sdii_customer_category_dealer_conf"),
    
    BUILDING_TYPE_CHANGE_DOWNLOAD("sdii_CODE_building_type_change_download",""),
    BUILDING_TYPE_CHANGE_WORK("sdii_CODE_building_type_change_work",""),
    OPTIMUS_DISPATCHER_ACTIVE("sdii_CODE_sys_param_optimus_dispatcher_active",""),
    MAX_WO_TO_ASSIGN_CREW("sdii_CODE_sys_param_max_wo_to_assign_crew",""),
    
    SYSTEM_NAME_HSP("sdii_CODE_system_name_hsp",""),
    SYSTEM_NAME_IBS("sdii_CODE_system_name_ibs",""),
    SYSTEM_NAME_OPTIMUS("sdii_CODE_system_name_optimus",""),
    SYSTEM_NAME_ALL("sdii_CODE_system_name_all",""),
    
    DISPATCH_PROPERTY_NAME("sdii_CODE_dispatch_property_name",""),
    DISPATCH_PROPERTY_CONSUMER_NAME("sdii_CODE_dispatch_property_consumer_name",""),
    DISPATCH_PROPERTY_DISPATCH_WO("sdii_CODE_dispatch_property_dispatch_wo",""),
    DISPATCH_PROPERTY_WITHDRAW_WO_FROM_TECHNICIAN("sdii_CODE_dispatch_property_withdraw_wo",""),
    
    OPTIMUS_PROPERTY_CONSUMER_NAME("sdii_CODE_optimus_status_property_consumer_name",""),
    
    INPUT_METHOD_AUTOMATIC("sdii_CODE_input_methods_automatic",""),
    CONTACT_STATUS_COMPLETE("sdii_CODE_contact_status_complete",""),

    CONNECT_TIMEOUT("sdii_CODE_connect_tiemout",""),
	REQUEST_TIMEOUT("sdii_CODE_request_timeout",""),
    
    //Req - StatusMesssage/Declinar Work Order
    CODE_MESSAGE_DECLINE("sdii_CODE_workorder_decline",""),
    CODE_MESSAGE_STATUS_WO("sdii_CODE_status_message",""),
    
    OPTIMUS_STATUS_EVENT_OK("sdii_CODE_optimus_status_event_ok","sdii_COLUMN_optimus_status_events_status"),
    OPTIMUS_STATUS_EVENT_ERROR("sdii_CODE_optimus_status_event_error","sdii_COLUMN_optimus_status_events_status"),    
    
    //Req - Reclamación de WO
    CODE_IBS_CONTACT_REASON_TECHNICAL_VISIT("sdii_CODE_ibs_contact_reason_technical_visit",""),
    CODE_IBS_CONTACT_REASON_TECHNICAL_DEFAULT("sdii_CODE_ibs_contact_reason_technical_default","")
	;
    
    private String codeEntity;
    private String columnEntity;
   
    CodesBusinessEntityEnum(String pCodeEntity,String pColumnEntity) {
        this.codeEntity = pCodeEntity;
        this.columnEntity = pColumnEntity;
    }
    
     /**
     *
     * Metodo: Retorna el codigo de la
     * entidad relacionada.
     * @return String codeEntity
     * @throws PropertiesException
     * @author Joan Lopez
     */
    public String getCodeEntity() throws PropertiesException{
    	PropertiesReader reader = null;
    
        reader = UtilsBusiness.getCodesBusinessEntityProperties();
        String codeResource = reader.getKey(this.codeEntity);
        this.codeEntity = codeResource != null ? codeResource.trim() : this.codeEntity;
        
        return this.codeEntity;
    }
    
    /**
     * 
     * Metodo: Retorna el id de la entidad enviada
     * filtrando por el codigo.
     * Invoca una consulta sobre la base de datos
     * para obtener el id de la tabla segun 
     * la entidad.
     * @param pClassName String    
     * @return Long
     * @throws HelperException
     * @throws PropertiesException 
     * @author Joan Lopez
     */
    public Long getIdEntity(String pClassName) throws HelperException,PropertiesException{
    	CodesBusinessEntityHelper helper;    
    	PropertiesReader reader = null;
    	
    	reader = UtilsBusiness.getCodesBusinessEntityProperties();
        String codeResource = reader.getKey(this.codeEntity);
        this.codeEntity = codeResource != null ? codeResource : this.codeEntity;
        
        String columnName = reader.getKey(this.columnEntity);
        this.columnEntity = columnName != null ? columnName : this.columnEntity;
    	
    	helper = new CodesBusinessEntityHelper(pClassName, this.codeEntity,  this.columnEntity);    	
    	return helper.getIdEntityByCodeEntity();
    }
    
}
