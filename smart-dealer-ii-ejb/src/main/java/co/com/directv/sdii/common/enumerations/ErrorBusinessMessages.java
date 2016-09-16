package co.com.directv.sdii.common.enumerations;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;
import co.com.ig.common.error.ErrorMessageManager;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de codigos de errores y mensajes de negocio.
 * 
 * Fecha de Creacion: 20/03/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public enum ErrorBusinessMessages {

	SERVICE_LOCATOR_NOT_INSTANCE("sdii_CODE_service_locator_not_instance","sdii_MESSAGE_service_locator_not_instance"),
	SERVICE_LOCATOR_URL_MALFORMED("sdii_CODE_service_locator_url_malformed","sdii_MESSAGE_service_locator_url_malformed"),
	NOT_RESPONSE_OBTAINED_SERVICE("sdii_CODE_not_response_obtained_service","sdii_MESSAGE_not_response_obtained_service"),
	COMUNICATION_IBS_FAIL("sdii_CODE_service_comunication_ibs_fail","sdii_MESSAGE_service_comunication_ibs_fail"),
    SESSION_NULL("sdii_CODE_session_null", "sdii_MESSAGE_session_null"),
    CONSTRAINT_VIOLATION("sdii_CODE_constraint_violation", "sdii_MESSAGE_constraint_violation"),
    ERROR_DATA("sdii_CODE_error_data", "sdii_MESSAGE_error_data"),
    ERROR_INVALID_DATE("sdii_CODE_error_invalid_date", "sdii_MESSAGE_error_invalid_date"),
    GENERIC_JDBC("sdii_CODE_generic_jdbc", "sdii_MESSAGE_generic_jdbc"),
    JDBC_CONNECTION("sdii_CODE_jdbc_connection", "sdii_MESSAGE_jdbc_connection"),
    LOCK_ACQUISITION("sdii_CODE_lock_acquisition", "sdii_MESSAGE_lock_acquisition"),
    SQL_GRAMMAR("sdii_CODE_sql_grammar", "sdii_MESSAGE_sql_grammar"),
    ERROR_SERVICE("sdii_CODE_error_service", "sdii_MESSAGE_error_service"),
    ERROR_INVOQUE_SERVICE_IBS("sdii_CODE_invoque_web_service_exception_ibs", "sdii_MESSAGE_invoque_web_service_exception_ibs"),
    DATASOURCE_NOT_FOUND("sdii_CODE_datasource_not_found", "sdii_MESSAGE_datasource_not_found"),
    LECTURA_PROPERTIES("sdii_CODE_lectura_properties", "sdii_MESSAGE_lectura_properties"),
    ALREADY_EXISTS_DEALER("sdii_CODE_error_already_exists", "sdii_MESSAGE_error_already_exists"),
    PARAMS_NULL_OR_MISSED("sdii_CODE_params_null_or_missed", "sdii_MESSAGE_params_null_or_missed"),
    PARAMS_INVALID_EXPRESSION("sdii_CODE_params_invalid_expression", "sdii_CODE_params_invalid_expression"),
    PARAMS_INVALID_NUMBER("sdii_CODE_params_invalid_number", "sdii_CODE_params_invalid_number"),
    DATE_OR_HOUR_RANGE_INVALID("sdii_CODE_date_or_hour_range_invalid","sdii_MESSAGE_date_or_hour_range_invalid"),
    IBS_DEALER_CODES("sdii_CODE_ibs_dealer_codes","sdii_MESSAGE_ibs_dealer_codes"), 
    IBS_DEALER_INFO("sdii_CODE_ibs_dealer_info","sdii_MESSAGE_ibs_dealer_info"),   
    HANDMADE_WORKORDER_REASON_DOES_NOT_EXIST("sdii_CODE_handmade_workorder_reason_does_not_exist","sdii_MESSAGE_handmade_workorder_reason_does_not_exist"), 
    CUSTOMER_DOES_NOT_EXIST("sdii_CODE_customer_does_not_exist","sdii_MESSAGE_customer_does_not_exist"),
    
    CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST("sdii_CODE_customer_class_or_type_does_not_exist","sdii_MESSAGE_customer_class_or_type_does_not_exist"),
    
    CUSTOMER_ALREADY_EXIST("sdii_CODE_customer_already_exist","sdii_MESSAGE_customer_already_exist"),
    WORKORDER_DOES_NOT_EXIST("sdii_CODE_workorder_does_not_exist","sdii_MESSAGE_workorder_does_not_exist"),
    WORKORDER_ALREADY_EXIST("sdii_CODE_workorder_already_exist","sdii_MESSAGE_workorder_already_exist"),
    WORKORDER_CUSTOMERID_AND_CUSTOMERID_DOES_NOT_MATCH("sdii_CODE_workorder_customerid_and_customerid_does_not_match","sdii_MESSAGE_workorder_customerid_and_customerid_does_not_match"), 
    WORKORDER_REASON_DOES_NOT_EXIST("sdii_CODE_workorder_reason_does_not_exist","sdii_MESSAGE_workorder_reason_does_not_exist"), 
    WORKORDER_STATUS_DOES_NOT_EXIST("sdii_CODE_workorder_status_does_not_exist","sdii_MESSAGE_workorder_status_does_not_exist"), 
    WORKORDER_STATUS_FINISHED_OR_REALIZED("sdii_CODE_work_order_status_realized_or_finished","sdii_MESSAGE_work_order_status_realized_or_finished"),
    WORKORDER_ASSIGMENT_DOES_NOT_EXIST("sdii_CDODE_wo_assigment_does_not_exist","sdii_MESSAGE_wo_assigment_does_not_exist"),
    WORKORDER_STATE_CHANGE_NOT_ALLOWED("sdii_CODE_wo_state_change_not_allowed","sdii_MESSAGE_wo_state_change_not_allowed"),   
    WORKORDER_AGENDA_DOES_NOT_EXIST("sdii_CODE_workorder_agenda_does_not_exist","sdii_MESSAGE_workorder_agenda_does_not_exist"),
    WORKORDER_NOT_REALIZED("sdii_CODE_wo_not_realized","sdii_MESSAGE_wo_not_realized"),
    WORKORDER_NOT_ACTIVE("sdii_CODE_wo_not_active","sdii_MESSAGE_wo_not_active"),
    WORKORDER_NOT_FOUND_IBS("sdii_CODE_work_order_not_found_ibs","sdii_MESSAGE_work_order_not_found_ibs"),
    WORKORDER_REQUIRED_ELEMENTS_NOT_FOUND("sdii_CODE_service_required_element_not_found","sdii_MESSAGE_service_required_element_not_found"),
    
    WORKORDER_STATUS_INVALID("sdii_CODE_wo_status_invalid","sdii_CODE_wo_status_invalid"),
    
    SERVICE_DOES_NOT_EXIST("sdii_CODE_service_does_not_exist","sdii_MESSAGE_service_does_not_exist"), 
    OBJECT_ALREADY_EXIST("sdii_CODE_object_already_exist","sdii_MESSAGE_object_already_exist"),     
    DEALER_PARTICIPATION_POINT_ALREADY_EXIST("sdii_CDODE_delaer_participation_point_already_exist","sdii_MESSAGE_delaer_participation_point_already_exist"),
    VALUE_RANGE_INVALID("sdii_CODE_value_range_invalid","sdii_MESSAGE_value_range_invalid"),
    DECIMAL_NUMBER_INVALID("sdii_CODE_decimal_value_invalid","sdii_MESSAGE_decimal_value_invalid"),
    CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY("sdii_CODE_code_name_already_exist","sdii_MESSAGE_code_name_already_exist"),
    REQUIRED_CAPACITY_SERVICE_DOES_NOT_EXIST("sdii_CODE_required_capacity_service_does_not_exist","sdii_MESSAGE_required_capacity_service_does_not_exist"),
    DEALER_SERVICE_CAPACITY_DOES_NOT_EXIST("sdii_CODE_dealer_service_capacity_does_not_exist","sdii_MESSAGE_dealer_service_capacity_does_not_exist"),
    WO_NO_CAN_CHANGE_STATUS("",""),
    CREW_NOT_UPDATE_PARAMS_NULL_OR_MISSED("sdii_CODE_not_update_crew","sdii_MESSAGE_not_update_crew"),
    CREW_NOT_ASSIGNMENT_EMPLOYEE("sdii_CODE_not_assignment_employee_crew","sdii_MESSAGE_not_assignment_employee_crew"), 
    UNKNOW_ERROR("sdii_CODE_unknow_error","sdii_MESSAGE_unknow_error"),
    ENTITY_NOT_FOUND("sdii_CODE_entity_not_found","sdii_MESSAGE_entity_not_found"), 
    WORKORDER_DONT_HAVE_ASIGNED_CREW("sdii_CODE_one_of_selected_wo_dont_have_asigned_crew","sdii_MESSAGE_one_of_selected_wo_dont_have_asigned_crew"),
    
    CREW_NOT_RESPONSIBLE_SPECIFIED("sdii_CODE_crew_not_responsible_specified","sdii_MESSAGE_crew_not_responsible_specified"),
    CREW_MULTIPLE_RESPONSIBLE_SPECIFIED("sdii_CODE_crew_multiple_responsible_specified","sdii_MESSAGE_crew_multiple_responsible_specified"),
    CREW_NO_TECNICIAN_SPECIFIED("sdii_CODE_crew_no_tecnician_specified","sdii_MESSAGE_crew_no_tecnician_specified"),
    CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW("sdii_CODE_crew_employee_responsible_in_other_crew","sdii_MESSAGE_crew_employee_responsible_in_other_crew"),
    CREW_CANNOT_CREATE_INACTIVE("sdii_CODE_crew_cannot_create_inactive","sdii_MESSAGE_crew_cannot_create_inactive"),
    CREW_DOES_NOT_EXIST("sdii_CODE_crew_does_not_exist","sdii_MESSAGE_crew_does_not_exist"),
    MAX_WORK_ORDES_PER_PDF_FILE_EXCEEDED("sdii_CODE_max_work_orders_per_pdf_file_exceeded","sdii_MESSAGE_max_work_orders_per_pdf_file_exceeded"),
    CREW_NOT_RESPONSIBLE("sdii_CODE_not_responsible_crew","sdii_MESSAGE_not_responsible_crew"), 
    INSUFFICIENT_DEALERS_QTY("sdii_CODE_insufficient_dealer_qty","sdii_MESSAGE_insufficient_dealer_qty"),
    ONE_OR_MORE_DEALERS_QTY("sdii_CODE_one_or_more_dealer_qty","sdii_MESSAGE_one_or_more_dealer_qty"),
    NONE_DEALER_QTY("sdii_CODE_none_dealer_qty","sdii_MESSAGE_none_dealer_qty"), 
    DEALER_SERVICE_CAPACITY_NOT_FOUND("sdii_CODE_dealer_service_capacity_not_found","sdii_MESSAGE_dealer_service_capacity_not_found"), 
    SERVICE_HOUR_BY_COUNTRY_NOT_FOUND("sdii_CODE_service_hour_by_country_not_found","sdii_MESSAGE_service_hour_by_country_not_found"),
    DEALER_DOES_NOT_EXIST("sdii_CODE_dealer_does_not_exist","sdii_MESSAGE_dealer_does_not_exist"),
    BUILDING_DOES_NOT_EXIST("sdii_CODE_building_does_not_exist","sdii_MESSAGE_building_does_not_exist"),
    SYSTEM_PARAMETER_NOT_FOUND("sdii_CODE_system_parameter_not_found","sdii_MESSAGE_system_parameter_not_found"), 
    VALID_CUSTOMER_NOT_FOUND("sdii_CODE_valid_customer_not_found","sdii_MESSAGE_valid_customer_not_found"), 
    IBS_CORE_PARAM_NULL("sdii_CODE_core_ibs_param_null","sdii_MESSAGE_core_ibs_param_null"),
    POSTAL_CODE_DOES_NOT_EXIST("sdii_CODE_postal_code_does_not_exist","sdii_MESSAGE_postal_code_does_not_exist"),
    
    POSTAL_CODE_DOES_NOT_EXIST_VISTA360("sdii_CODE_postal_code_does_not_exist_vista360","sdii_MESSAGE_postal_code_does_not_exist_vista360"),
    
    INVALID_DATE("sdii_CODE_invalid_date","sdii_MESSAGE_invalid_date"),
    INVALID_DATE_WITH_LOCK_PRIOR_CALENDAR("sdii_CODE_invalid_date_with_lock_prior_calendar","sdii_MESSAGE_invalid_date_with_lock_prior_calendar"),
    REPORT_PATH_INVALID("sdii_CODE_report_path_invalid","sdii_MESSAGE_report_path_invalid"),
    DELAER_SERVICE_CAPACITY_IS_NOT_VALID("sdii_CODE_dealer_service_capacidy_is_not_valid","sdii_MESSAGE_dealer_service_capacidy_is_not_valid"),
    DEALER_SERVICE_CAPACITY_NOT_ENOUGHT("sdii_CODE_dealer_service_capacidy_is_not_enought","sdii_MESSAGE_dealer_service_capacidy_is_not_enought"),
    DEALER_IS_NOT_ALLOCABLE("sdii_CODE_dealer_is_not_allocable","sdii_MESSAGE_dealer_is_not_allocable"), 
    INVALID_EMAIL("sdii_CODE_invalid_mail","sdii_MESSAGE_invalid_mail"),
    IBS_DEALERS_PARAM_NULL("sdii_CODE_dealers_ibs_param_null","sdii_MESSAGE_dealers_ibs_param_null"), 
    IBS_SERVICE_PARAM_NULL("sdii_CODE_null_parameter_ws_ibs","sdii_MESSAGE_null_parameter_ws_ibs"), 
    SHIPPING_ORDER_DOES_NOT_EXIST("sdii_CODE_shipping_order_does_not_exist","sdii_MESSAGE_shipping_order_does_not_exist"),
    DRIVER_ID_NULL_OR_MISSED("sdii_CODE_driver_id_null_or_missed","sdii_MESSAGE_driver_id_null_or_missed"),
    DEALERS_STATUS_NOT_EXIST("sdii_CODE_dealer_status_not_exist","sdii_MESSAGE_dealer_status_not_exist"),
    DEALERS_CHANNEL_NOT_EXIST("sdii_CODE_dealer_channel_not_exist","sdii_MESSAGE_dealer_channel_not_exist"),
    DEALERS_TYPE_NOT_EXIST("sdii_CODE_dealer_type_not_exist","sdii_MESSAGE_dealer_type_not_exist"),
    DEALER_TYPE_HAS_RELATED_DATA("sdii_CODE_dealer_type_has_related_data","sdii_MESSAGE_dealer_type_has_related_data"),
    DEALERS_POSTAL_CODE_NOT_EXIST("sdii_CODE_dealer_postal_code_not_exist","sdii_MESSAGE_dealer_postal_code_not_exist"),
    POSITION_BY_NAME_AND_DEALER_ALREADY_EXIST("sdii_CODE_position_by_name_and_dealer_already_exist","sdii_MESSAGE_position_by_name_and_dealer_already_exist"),
    POSITION_HAS_RELATED_DATA("sdii_CODE_position_has_related_data","sdii_MESSAGE_position_has_related_data"),
    POSITION_BY_CODE_AND_DEALER_ALREADY_EXIST("sdii_CODE_position_by_code_and_dealer_already_exist","sdii_MESSAGE_position_by_code_and_dealer_already_exist"),
    SUPPLIER_NIT_ALREADY_EXIST("sdii_CODE_supplier_nit_already_exist","sdii_MESSAGE_supplier_nit_already_exist"),
    ELEMENT_STATUS_NOT_SETTED("sdii_CODE_element_status_not_setted","sdii_MESSAGE_element_status_not_setted"),
    ELEMENT_LOT_NULL("sdii_CODE_element_lot_null","sdii_MESSAGE_element_lot_null"),
    IMPORT_LOG_BY_SUPPLIER_AND_PURCHASE_ORDER_ALREADY_EXIST("sdii_CODE_import_log_by_supplier_and_purchase_order_already_exist","sdii_MESSAGE_import_log_by_supplier_and_purchase_order_already_exist"),
    ELEMENT_SERIAL_NULL("sdii_CODE_element_serial_null","sdii_MESSAGE_element_serial_null"),
    IMPORT_LOG_STATUS_IS_NOT_VALID("sdii_CODE_import_log_status_is_not_valid","sdii_MESSAGE_import_log_status_is_not_valid"),
    WO_SERVICE_ELEMENTS_HAVE_NOT_BEEN_SPECIFIED("sdii_CODE_wo_service_elements_havent_been_specified","sdii_MESSAGE_wo_service_elements_havent_been_specified"),
    USER_NOT_EXIST("sdii_CODE_user_not_exist","sdii_MESSAGE_user_not_exist"),
    ELEMENT_IS_REPEATED("sdii_CODE_element_is_repeated","sdii_MESSAGE_element_is_repeated"),
    ELEMENT_REPLACE_NOT_DEFINED("sdii_CODE_element_replace_not_defined","sdii_MESSAGE_element_replace_not_defined"),
    WAREHOUSE_ELEMENT_TARGET_ARE_REPEATED("sdii_CODE_warehouse_element_target_are_repeated","sdii_MESSAGE_warehouse_element_target_are_repeated"), 
    INVALID_NUMBER("sdii_CODE_invalid_number","sdii_MESSAGE_invalid_number"),
    IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID("sdii_CODE_import_log_not_exist_or_status_invalid","sdii_MESSAGE_import_log_not_exist_or_status_invalid"),
    IMPORT_LOG_ITEM_IS_INVALID("sdii_CODE_import_log_item_is_invalid","sdii_MESSAGE_import_log_item_is_invalid"),
    IMPORT_LOG_ITEM_NOT_EXISTS_IN_IMPORT_LOG("sdii_CODE_import_log_item_not_exists_in_import_log","sdii_MESSAGE_import_log_item_not_exists_in_import_log"),
    AN_IMPORT_LOG_ITEM_IS_NOT_SERIALIZED("sdii_CODE_an_import_log_item_is_not_serialized","sdii_MESSAGE_an_import_log_item_is_not_serialized"),
    IMPORT_LOG_ITEM_STATUS_IS_INVALID("sdii_CODE_import_log_item_status_is_invalid","sdii_MESSAGE_import_log_item_status_is_invalid"),
    ELEMENT_NOT_EXIST("sdii_CODE_element_not_exist","sdii_MESSAGE_element_not_exist"),
    PLAIN_FILE_INCORRECT("sdii_CODE_plain_file_incorrect","sdii_MESSAGE_plain_file_incorrect"),
    NOT_SER_ELEMENTS_NOT_EXIST("sdii_mouvement_not_ser_elements_not_exist","sdii_mouvement_not_ser_elements_not_exist"),
    IBS_INVENTORY_RESPONSE("sdii_CODE_ibs_inventory_codes","sdii_MESSAGE_ibs_inventory_codes"),
    NOT_ELEMENTS_EXIST("sdii_mouvement_elements_not_exist","sdii_mouvement_elements_not_exist"), 
    REFERENCE_STATUS_DOES_NOT_EXIST("sdii_CODE_reference_status_does_not_exist","sdii_MESSAGE_reference_status_does_not_exist"),
    ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT("sdii_mouvement_elements_actual_quantity_lower","sdii_mouvement_elements_actual_quantity_lower"),
    
    RESPONSIBLE_MAIL_IS_NOT_CORRECT("sdii_CODE_resposible_mail_incorrect","sdii_CODE_resposible_mail_incorrect"),
    NO_RECIPIENTS_TO_SEND_MAIL("sdii_CODE_no_recipient_to_send","sdii_CODE_no_recipient_to_send"),
    EMAIL_SENDING_ERROR("sdii_CODE_error_sending_mail","sdii_CODE_error_sending_mail"),
    
    NO_REFERENCE_EXIT("sdii_references_not_exist","sdii_references_not_exist"), 
    ANY_OF_ELEMENTS_HAVE_LINKED_ELEMENT("sdii_CODE_any_of_elements_have_linked_element","sdii_MESSAGE_any_of_elements_have_linked_element"), 
    INVALID_WH_ELEMENT_STATUS("sdii_CODE_invalid_wh_elelemt_status","sdii_MESSAGE_invalid_wh_elelemt_status"), 
    WH_ELELEMTS_ARENT_LINKED("sdii_CODE_wh_elelemts_arent_linked","sdii_MESSAGE_wh_elelemts_arent_linked"), 
    WAREHOUSE_ELEMENT_DOESNT_EXIST("sdii_CODE_wh_element_does_not_exist","sdii_MESSAGE_wh_element_does_not_exist"),
    WAREHOUSE_ELEMENT_ISNT_THE_FINAL("sdii_CODE_wh_element_is_not_the_final","sdii_MESSAGE_wh_element_is_not_the_final"),
    WAREHOUSE_ELEMENT_ALREADY_WENT_OUT_FROM_WH("sdii_CODE_wh_element_already_went_out_from_wh","sdii_MESSAGE_wh_element_already_went_out_from_wh"), 
    WAREHOUSE_DOESNT_EXIST("sdii_CODE_warehouse_does_not_exist","sdii_MESSAGE_warehouse_does_not_exist"), 
    WAREHOUSE_CREW_STATUS_INVALID("sdii_CODE_ware_crew_status_invalid","sdii_MESSAGE_ware_crew_status_invalid"), 
    
    DEALER_ISNT_LOGISTIC_OPERATOR("sdii_CODE_dealer_isnt_logistic_operator","sdii_MESSAGE_dealer_isnt_logistic_operator"), 
    INVALID_TARGET_WAREHOUSE("sdii_CODE_invalid_target_warehouse","sdii_MESSAGE_invalid_target_warehouse"),
    INVENTORY_SERIAL_RID_REQUIRED("sdii_CODE_serialized_rid_required","sdii_MESSAGE_serialized_rid_required"),
    INVENTORY_INCONSISTENCY_IS_INVALID("sdii_CODE_inconsistency_import_log_status_is_invalid","sdii_MESSAGE_inconsistency_import_log_status_is_invalid"),
    INVENTORY_ELEMENT_STATUS_IS_CONFIRMED("sdii_CODE_elements_status_is_confirmed","sdii_MESSAGE_elements_status_is_confirmed"),
    INVENTORY_IMPORT_LOG_STATUS_IS_INCONSISTENCY("sdii_CODE_import_log_status_is_inconsistency","sdii_MESSAGE_import_log_status_is_inconsistency"),
    INVENTORY_AMOUNT_EXPECTED_CONFIRMED_SERIALIZED("sdii_CODE_amount_expected_comfirmed_serialized","sdii_MESSAGE_amount_expected_comfirmed_serialized"),
    INVENTORY_AMOUNT_PENDING_CONFIRMED_SERIALIZED("sdii_CODE_amount_pending_comfirmed_serialized","sdii_MESSAGE_amount_pending_comfirmed_serialized"),
    INVENTORY_ONLY_ELEMENT_SERIALIZED_IMPORT_LOG("sdii_CODE_only_serialized_element","sdii_MESSAGE_only_serialized_element"),
    INVENTORY_ONLY_ONE_SERIALIZED_CONFIRMED("sdii_CODE_only_one_serialized_confirmed","sdii_MESSAGE_only_one_serialized_confirmed"),
    INVENTORY_ONLY_ONE_SERIALIZED_PENDING("sdii_CODE_only_one_serialized_pending","sdii_MESSAGE_only_one_serialized_pending"),
    REFERENCE_NOT_CONFIRMED_BY_DELIVERIES("sdii_CODE_reference_not_confirmed_by_deliveries","sdii_MESSAGE_reference_not_confirmed_by_deliveries"),
    
	REFERENCE_CONFIRMATIONS_GREATER_THAN_MOV_QUANTITY("sdii_CODE_reference_confirmation_greater_than_mov_quantity","sdii_MESSAGE_reference_confirmation_greater_than_mov_quantity"),
	REFERENCE_DELIVERIES_GREATER_THAN_MOV_QUANTITY("sdii_CODE_reference_deliveries_greater_than_mov_quantity","sdii_MESSAGE_reference_deliveries_greater_than_mov_quantity"),
	REFERENCE_DELIVERIES_WRONG_DATE("sdii_CODE_reference_deliveries_wrong_date","sdii_MESSAGE_reference_deliveries_wrong_date"),
	
	WORK_ORDER_HAS_INACTIVE_SERVICE("sdii_CODE_workorder_has_inactive_service","sdii_MESSAGE_workorder_has_inactive_service"), 
	WORKORDER_ALREADY_HAS_BEEN_ASSIGNED("sdii_CODE_workorder_already_has_been_assigned","sdii_MESSAGE_workorder_already_has_been_assigned"),
	WORKORDER_HAS_BEEN_ASSIGNED("sdii_CODE_work_order_assigned","sdii_MESSAGE_work_order_assigned"),
	
	REFERENCE_ELEMENT_ITEM_CONFIRMATION_ERROR("sdii_CODE_reference_element_item_confirmation_error","sdii_MESSAGE_reference_element_item_confirmation_error"),
	
	IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR("sdii_CODE_import_log_item_update_quantity_error","sdii_MESSAGE_import_log_item_update_quantity_error"),
	IMPORT_LOG_ITEM_DELETE_ERROR("sdii_CODE_import_log_item_delete_error","sdii_MESSAGE_import_log_item_delete_error"),
	
	DELAER_DONT_HAVE_ONLY_ONE_WAREHOUSE ("sdii_CODE_dealer_dont_have_only_one_ware_house_error","sdii_MESSAGE_dealer_dont_have_only_one_ware_house_error"),
	CUSTOMER_DONT_HAVE_ONLY_ONE_WAREHOUSE ("sdii_CODE_customer_dont_have_only_one_ware_house_error","sdii_MESSAGE_customer_dont_have_only_one_ware_house_error"),
	WH_DONT_HAVE_ONLY_ONE_ELEMENT ("sdii_CODE_wh_dont_have_only_one_element_error","sdii_MESSAGE_wh_dont_have_only_one_element_error"),
	IS_NOT_LAST_RECORD ("sdii_CODE_is_not_last_record_error","sdii_MESSAGE_is_not_last_record_error"),
	
	ELEMENT_ALREADY_HAVE_LINKED_ELEMENT ("sdii_CODE_element_have_linked_element","sdii_MESSAGE_element_have_linked_element"),
	ELEMENT_ISNT_SERIALIZED ("sdii_CODE_isnt_serialized","sdii_MESSAGE_isnt_serialized"),
	ELEMENT_ISNT_NOT_SERIALIZED("sdii_CODE_isnt_not_serialized","sdii_MESSAGE_isnt_not_serialized"),
	ELEMENT_IS_THE_SAME ("sdii_CODE_same_element","sdii_MESSAGE_same_element"), 
	
	WAREHOUSE_DOESNT_HAS_THE_SPECIFIED_ELEMENT("sdii_CODE_warehouse_doesnt_has_the_specified_element","sdii_MESSAGE_warehouse_doesnt_has_the_specified_element"), 
	
	LINKED_ELEMENT_NOT_EXIST_OR_INVALID ("sdii_CODE_linked_element_not_exist_or_invalid","sdii_MESSAGE_linked_element_not_exist_or_invalid"),
	
	REFERENCE_CONFIRMATIONS_IS_ZERO ("sdii_CODE_reference_confirmation_is_zero","sdii_MESSAGE_reference_confirmation_is_zero"),
	REFERENCE_CONFIRMATIONS_WRONG_DATE("sdii_CODE_reference_confirmation_wrong_date","sdii_MESSAGE_reference_confirmation_wrong_date"),
	REFERENCE_CONFIRMATIONS_EMPTY("sdii_CODE_reference_confirmation_empty","sdii_MESSAGE_reference_confirmation_empty"),
	
	DEALER_DONT_HAVE_ADJUST_WH("sdii_CODE_dealer_dont_have_adjust_wh","sdii_MESSAGE_dealer_dont_have_adjust_wh"),
	DEALER_DONT_HAVE_QUALITY_WH("sdii_CODE_dealer_dont_have_quality_wh","sdii_MESSAGE_dealer_dont_have_quality_wh"),
	DEALER_DONT_HAVE_DEVOLUTION_WH("sdii_CODE_dealer_dont_have_devolution_wh","sdii_MESSAGE_dealer_dont_have_devolution_wh"),
	DEALER_DONT_HAVE_AVAILABLE_WH("sdii_CODE_dealer_dont_have_available_wh","sdii_MESSAGE_dealer_dont_have_available_wh"),
	DEALER_DONT_HAVE_LOGISITIC_OPERATOR_WH("sdii_CODE_dealer_dont_have_logistic_operator_wh","sdii_MESSAGE_dealer_dont_have_logistic_operator_wh"),
	CUSTOMER_DONT_HAVE_WH("sdii_CODE_customer_dont_have_wh","sdii_MESSAGE_customer_dont_have_wh"),
	
	REFERENCE_DETAILS_NULL_VALUES("sdii_CODE_reference_details_null_values","sdii_MESSAGE_reference_details_null_values"), 
	CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY("sdii_CODE_confirmed_qty_exceeds_the_expected_qty","sdii_MESSAGE_confirmed_qty_exceeds_the_expected_qty"),
	
	ELEMENT_DOESNT_EXIST_ON_LOGISTIC_OPERATOR_WH("sdii_CODE_element_doesnt_exist_on_logistic_operator_wh","sdii_MESSAGE_element_doesnt_exist_on_logistic_operator_wh"),
	
	SPECIAL_COMMENT_NOT_NULL("sdii_CODE_special_comment_not_null","sdii_MESSAGE_special_comment_not_null"),
	
	SER_QUANTITY_CANNOT_BE_GREATER_THAN_ONE("sdii_CODE_ser_quantity_not_greater_than_one","sdii_MESSAGE_ser_quantity_not_greater_than_one"),
	
	CANT_CONFIRM_REFERENCE("sdii_CODE_cant_confirm_reference","sdii_MESSAGE_cant_confirm_reference"), 
	WORKORDER_DOESNT_HAS_BUILDING_CODE("sdii_CODE_workorder_doesnt_has_building_code","sdii_MESSAGE_workorder_doesnt_has_building_code"),
	
	INVALID_WH_TYPE("sdii_CODE_invalid_wh_type","sdii_MESSAGE_invalid_wh_type"),
	
	REFERENCE_ELEM_ITEMS_CONFIRMED("sdii_CODE_reference_elem_items_confirmed","sdii_MESSAGE_reference_elem_items_confirmed"),
	
	REFERENCE_ELEM_ITEMS_NOT_SERIALIZE_DOESNT_EXIST("sdii_CODE_reference_elem_items_not_serialized_doesnt_exist","sdii_MESSAGE_reference_elem_items_not_serialized_doesnt_exist"),
	REFERENCE_ELEM_ITEMS_SERIALIZE_DOESNT_EXIST("sdii_CODE_reference_elem_items_serialized_doesnt_exist","sdii_MESSAGE_reference_elem_items_serialized_doesnt_exist"),
	
	REFERENCE_STATUS_CONFIMED("sdii_CODE_reference_already_confirmed","sdii_MESSAGE_reference_already_confirmed"),
	
	SYSTEM_PARAMETER_DOESNT_EXIST("sdii_CODE_system_parameter_doesnt_exist","sdii_MESSAGE_system_parameter_doesnt_exist"),
	
	TRANSIT_WH_NOT_FOUND("sdii_CODE_transit_wh_not_found","sdii_MESSAGE_transit_wh_not_found"),
	DEALER_LOGICTIC_OPERATOR_DOESNT_EXIST("sdii_CODE_dealer_logistic_operator_doesnt_exist","sdii_MESSAGE_dealer_logistic_operator_doesnt_exist"),
	WORKORDER_COULDNT_BE_ATTENDED("sdii_CODE_work_order_couldnt_be_attended","sdii_MESSAGE_work_order_couldnt_be_attended"),
	
	ERROR_FILE_INCORRECT("sdii_CODE_file_incorrect","sdii_MESSAGE_file_incorrect"),
	
	ERROR_SOURCE_WH_EQUALS_TARGET_WH("sdii_CODE_source_wh_equals_target_wh","sdii_MESSAGE_source_wh_equals_target_wh"),
	
	IMPORT_LOG_ITEM_QUANTITY_INVALID("sdii_CODE_import_log_item_quantity_invalid","sdii_MESSAGE_import_log_item_quantity_invalid"), 
	
	NONE_OF_SELECTED_WORK_ORDERS_ARE_ASSIGNED_TO_SELECTED_CREW("sdii_CODE_none_of_selected_work_orders_are_assigned_to_selected_crew","sdii_MESSAGE_none_of_selected_work_orders_are_assigned_to_selected_crew"),
	
	CORE_NOT_EXIST_CONFIG_CONTACT("sdii_CODE_core_not_exist_config_contact","sdii_MESSAGE_core_not_exist_config_contact"),
	
	CORE_NOT_EXIST_CONTACT_STATUS("sdii_CODE_core_not_exist_contact_status","sdii_MESSAGE_core_not_exist_config_contact"),
	
	CORE_NOT_EXIST_IBS_CONTACT_REASON("sdii_CODE_core_not_exist_ibs_contact_reason","sdii_MESSAGE_core_not_exist_ibs_contact_reason"),
	
	LDAP_ERROR_AUTHENTICATOR("sdii_CODE_ldap_authenticator","sdii_MESSAGE_ldap_authenticator"),
	
	WORK_ORDER_COMPLETED_OR_TERMINATED_STATED("sdii_CODE_some_work_order_is_completed_or_terminated_state","sdii_MESSAGE_some_work_order_is_completed_or_terminated_state"), 
	
	WORK_ORDER_FINISHED_STATED("sdii_CODE_some_work_order_is_completed_or_terminated_state","sdii_MESSAGE_some_work_order_is_completed_or_terminated_state"), 
	
	WEB_SERVICE_EXCEPTION("sdii_CODE_invoque_web_service_exception", "sdii_MESSAGE_invoque_web_service_exception"),
	
	DEALER_LOGISTIC_ANALYST_USER_DOESNT_EXIST("sdii_CODE_dealer_logistic_analyst_user_doesnt_exist", "sdii_MESSAGE_dealer_logistic_analyst_user_doesnt_exist"),
	
	FILE_ELEMENTS_DONT_MATCH("sdii_CODE_file_elements_dont_match","sdii_MESSAGE_file_elements_dont_match"),
	
	FILE_OUT_DONT_FOUND_BY_ABORT("sdii_CODE_file_out_dont_find_by_abort","sdii_MESSAGE_file_out_dont_find_by_abort"),
	
	ELEMENTS_CANT_HAVE_THE_SAME_TYPE("sdii_CODE_element_cant_have_same_type","sdii_MESSAGE_element_cant_have_same_type"),
	
	REFERENCE_STATUS_NOT_IN_CREATION_PROCESS("sdii_CODE_referece_status_not_in_creation_process","sdii_MESSAGE_referece_status_not_in_creation_process"), 
	
	REFERENCE_ELEMENT_ITEM_REMOVE_ERROR("sdii_CODE_referece_element_item_remove_error","sdii_MESSAGE_referece_element_item_remove_error"),
	
	NO_ELEMENTS_TO_QUALITY_CONTROL("sdii_CODE_no_elements_to_quality_control", "sdii_MESSAGE_no_elements_to_quality_control"),
	
	SHIPPING_ORDER_TYPE_ALREADY_EXIST("sdii_CODE_shipping_order_type_already_exist", "sdii_MESSAGE_shipping_order_type_already_exist"),
	SHIPPING_ORDER_STATUS_ALREADY_EXIST("sdii_CODE_shipping_order_status_already_exist", "sdii_MESSAGE_shipping_order_status_already_exist"), 
		
	PROGRAM_HAS_ASSOCIATED_DATA("sdii_CODE_program_has_associated_data", "sdii_MESSAGE_program_has_associated_data"), 	
	PROGRAM_ALREADY_EXIST("sdii_CODE_program_already_exist", "sdii_MESSAGE_program_already_exist"), 
	USER_ALREADY_EXIST("sdii_CODE_user_already_exist", "sdii_MESSAGE_user_already_exist"),
	
	ELEMENT_DOESNT_EXIST_IN_CUSTOMER_WH("sdii_CODE_element_for_attention_doesnt_exist_in_customer_wh", "sdii_MESSAGE_element_for_attention_doesnt_exist_in_customer_wh"),
	
	WO_TYPE_DOESNT_HAVE_SERVICE_TYPE("sdii_CODE_wo_type_doesnt_have_service_type","sdii_MESSAGE_wo_type_doesnt_have_service_type"),
	
	SERVICE_TYPE_DOESNT_HAVE_WO_TYPE("sdii_CODE_service_type_doesnt_have_wo_type","sdii_MESSAGE_service_type_doesnt_have_wo_type"),
	
	DATE_RANGE_CANNOT_BE_GREATER_THAN_TWO("sdii_CODE_date_range_cannot_be_greater_than_two","sdii_MESSAGE_date_range_cannot_be_greater_than_two"),
	TRAY_INIT_DATE_AFTER_END_DATE("sdii_CODE_tray_init_date_after_end_date","sdii_MESSAGE_tray_init_date_after_end_date"),
	
	//CC053 - HSP Programacion de reportes.
	COUNTRY_NOT_EXIST("sdii_CODE_country_not_exist","sdii_MESSAGE_country_not_exist"),
	MAX_REPORTS_BY_COUNTRY("sdii_CODE_max_reports_by_country","sdii_MESSAGE_max_reports_by_country"),
	REPORT_TYPE_NOT_EXIST("sdii_CODE_report_type_not_exist","sdii_MESSAGE_report_type_not_exist"),
	HOUR_PROGRAM_PARAMETER_NOT_IN_RANGE("sdii_CODE_hour_program_parameter_not_in_range","sdii_MESSAGE_hour_program_parameter_not_in_range"),
	
	
	//NEGOCIO
	BUSINESS_BL186("sdii_CODE_BL186","sdii_MESSAGE_BL186"),
	BUSINESS_BL187("sdii_CODE_BL187","sdii_MESSAGE_BL187"),
	BUSINESS_BL188("sdii_CODE_BL188","sdii_MESSAGE_BL188"),
	BUSINESS_BL189("sdii_CODE_BL189","sdii_MESSAGE_BL189"),
	
	//INVENTARIOS
	INVENTORY_IN333("sdii_CODE_IN333", "sdii_MESSAGE_IN333"),
	ADJUSTMENT_AUTHORIZING("sdii_CODE_ADJUSTMENT_AUTHORIZING", "sdii_MESSAGE_ADJUSTMENT_AUTHORIZING"),
	
	//CORE
	CORE_CR015("sdii_CODE_CR015","sdii_MESSAGE_CR015"),
	CORE_CR016("sdii_CODE_CR016","sdii_MESSAGE_CR016"),
	CORE_CR017("sdii_CODE_CR017","sdii_MESSAGE_CR017"),
	CORE_CR018("sdii_CODE_CR018","sdii_MESSAGE_CR018"),
	CORE_CR019("sdii_CODE_CR019","sdii_MESSAGE_CR019"),
	CORE_CR020("sdii_CODE_CR020","sdii_MESSAGE_CR020"),
	CORE_CR021("sdii_CODE_CR021","sdii_MESSAGE_CR021"),
	CORE_CR024("sdii_CODE_CR024","sdii_MESSAGE_CR024"),
	CORE_CR025("sdii_CODE_CR025","sdii_MESSAGE_CR025"),
	CORE_CR026("sdii_CODE_CR026", "sdii_MESSAGE_CR026"),
	CORE_CR027("sdii_CODE_CR027","sdii_MESSAGE_CR027"),
	CORE_CR028("sdii_CODE_CR028","sdii_MESSAGE_CR028"),
	CORE_CR029("sdii_CODE_CR029","sdii_MESSAGE_CR029"),
	CORE_CR030("sdii_CODE_CR030","sdii_MESSAGE_CR030"),
	CORE_CR031("sdii_CODE_CR031","sdii_MESSAGE_CR031"),
	CORE_CR032("sdii_CODE_CR032","sdii_MESSAGE_CR032"),
	CORE_CR033("sdii_CODE_CR033","sdii_MESSAGE_CR033"),
	CORE_CR034("sdii_CODE_CR034","sdii_MESSAGE_CR034"),
	CORE_CR035("sdii_CODE_CR035","sdii_MESSAGE_CR035"),
	CORE_CR036("sdii_CODE_CR036","sdii_MESSAGE_CR036"),
	CORE_CR037("sdii_CODE_CR037","sdii_MESSAGE_CR037"),
	CORE_CR038("sdii_CODE_CR038","sdii_MESSAGE_CR038"),
	CORE_CR039("sdii_CODE_CR039","sdii_MESSAGE_CR039"),
	CORE_CR040("sdii_CODE_CR040","sdii_MESSAGE_CR040"),
	CORE_CR041("sdii_CODE_CR041","sdii_MESSAGE_CR041"),
	CORE_CR042("sdii_CODE_CR042","sdii_MESSAGE_CR042"),
	CORE_CR043("sdii_CODE_CR043","sdii_MESSAGE_CR043"),
	CORE_CR044("sdii_CODE_CR044","sdii_MESSAGE_CR044"),
	CORE_CR045("sdii_CODE_CR045","sdii_MESSAGE_CR045"),
	CORE_CR046("sdii_CODE_CR046","sdii_MESSAGE_CR046"),
	CORE_CR047("sdii_CODE_CR047","sdii_MESSAGE_CR047"),
	CORE_CR048("sdii_CODE_CR048","sdii_MESSAGE_CR048"),
	CORE_CR049("sdii_CODE_CR049","sdii_MESSAGE_CR049"),
	CORE_CR050("sdii_CODE_CR050","sdii_MESSAGE_CR050"),
	CORE_CR051("sdii_CODE_CR051","sdii_MESSAGE_CR051"),
	CORE_CR052("sdii_CODE_CR052","sdii_MESSAGE_CR052"),
	CORE_CR053("sdii_CODE_CR053","sdii_MESSAGE_CR053"),
	CORE_CR054("sdii_CODE_CR054","sdii_MESSAGE_CR054"),
	CORE_CR055("sdii_CODE_CR055","sdii_MESSAGE_CR055"),
	CORE_CR056("sdii_CODE_CR056","sdii_MESSAGE_CR056"),
	CORE_CR057("sdii_CODE_CR057","sdii_MESSAGE_CR057"),
	CORE_CR058("sdii_CODE_CR058","sdii_MESSAGE_CR058"),
	CORE_CR059("sdii_CODE_CR059","sdii_MESSAGE_CR059"),
	CORE_CR060("sdii_CODE_CR060","sdii_MESSAGE_CR060"),
	CORE_CR061("sdii_CODE_CR061","sdii_MESSAGE_CR061"),
	CORE_CR062("sdii_CODE_CR062","sdii_MESSAGE_CR062"),
	CORE_CR063("sdii_CODE_CR063","sdii_MESSAGE_CR063"),
	CORE_CR064("sdii_CODE_CR064","sdii_MESSAGE_CR064"),
	CORE_CR065("sdii_CODE_CR065","sdii_MESSAGE_CR065"),
	CORE_CR071("sdii_CODE_CR071","sdii_MESSAGE_CR071"),
	CORE_CR072("sdii_CODE_CR072","sdii_MESSAGE_CR072"),
	CORE_CR073("sdii_CODE_CR073","sdii_MESSAGE_CR073"),
	CORE_CR076("sdii_CODE_adress_error_postal_code","sdii_CODE_adress_error_postal_code"),
	CORE_CR106("sdii_CODE_CR106","sdii_MESSAGE_CR106"),
	CORE_CR107("sdii_CODE_CR107","sdii_MESSAGE_CR107"),
	EMPLOYEE_HAS_RELATED_DATA("sdii_CODE_employee_has_related_data","sdii_MESSAGE_employee_has_related_data"),
	
	
	//Dealer
	EMPLOYEE_HAS_A_VEHICLE("sdii_CODE_DE007","sdii_MESSAGE_DE007"),
	EMPLOYEE_HAS_A_CREW("sdii_CODE_DE008","sdii_MESSAGE_DE008"),
	EMPLOYEE_HAS_A_VEHICLE_AND_CREW("sdii_CODE_DE009","sdii_MESSAGE_DE009"),
	EMPLOYEE_ACTIVE_ALREADY_EXIST("sdii_CODE_DE010","sdii_MESSAGE_DE010"),
	EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW("sdii_CODE_DE011","sdii_MESSAGE_DE011"),
	
	ALLOCATOR_AL003("sdii_CODE_AL003","sdii_MESSAGE_AL003"),
	ALLOCATOR_AL004("sdii_CODE_AL004","sdii_MESSAGE_AL004"),	
	ALLOCATOR_AL005("sdii_CODE_AL005","sdii_MESSAGE_AL005"),
	ALLOCATOR_AL006("sdii_CODE_AL006","sdii_MESSAGE_AL006"),
	ALLOCATOR_AL007("sdii_CODE_AL007","sdii_MESSAGE_AL007"),
	ALLOCATOR_AL008("sdii_CODE_AL008","sdii_MESSAGE_AL008"),
	ALLOCATOR_AL009("sdii_CODE_AL009","sdii_MESSAGE_AL009"),
	ALLOCATOR_AL010("sdii_CODE_AL010","sdii_MESSAGE_AL010"),
	ALLOCATOR_AL011("sdii_CODE_AL011","sdii_MESSAGE_AL011"),
	ALLOCATOR_AL012("sdii_CODE_AL012","sdii_MESSAGE_AL012"),
	ALLOCATOR_AL013("sdii_CODE_AL013","sdii_MESSAGE_AL013"), 
	KPI_CALCULATION_ERROR("sdii_CODE_AL014","sdii_MESSAGE_AL014"), 
	INVALID_PARAMETER("sdii_CODE_AL015","sdii_MESSAGE_AL015"), 
	DEALER_DOES_NOT_HAVE_DETAILS("sdii_CODE_AL016","sdii_MESSAGE_AL016"), 
	DEALER_DOES_NOT_HAVE_WEEK_CONFIG("sdii_CODE_AL017","sdii_MESSAGE_AL017"),
	ALLOCATOR_AL018("sdii_CODE_AL018","sdii_MESSAGE_AL018"),
	ALLOCATOR_AL019("sdii_CODE_AL019","sdii_MESSAGE_AL019"),
	//El canal de venta ya existe
	ALLOCATOR_AL020("sdii_CODE_AL020","sdii_MESSAGE_AL020"),
	ALLOCATOR_AL021("sdii_CODE_AL021","sdii_MESSAGE_AL021"),
	//Parametro de skill_configuration nulo
	ALLOCATOR_AL023("sdii_CODE_AL023","sdii_MESSAGE_AL023"),
	ALLOCATOR_AL024("sdii_CODE_AL024","sdii_MESSAGE_AL024"),
	
	ALLOCATOR_AL029("sdii_CODE_AL029","sdii_MESSAGE_AL029"),
	ALLOCATOR_AL030("sdii_CODE_AL030","sdii_MESSAGE_AL030"),
	ALLOCATOR_AL031("sdii_CODE_AL031","sdii_MESSAGE_AL031"),
	ALLOCATOR_AL032("sdii_CODE_AL032","sdii_MESSAGE_AL032"),
	ALLOCATOR_AL033("sdii_CODE_AL033","sdii_MESSAGE_AL033"),
	ALLOCATOR_AL034("sdii_CODE_AL034","sdii_MESSAGE_AL034"),
	ALLOCATOR_AL035("sdii_CODE_AL035","sdii_MESSAGE_AL035"),
	ALLOCATOR_AL036("sdii_CODE_AL036","sdii_MESSAGE_AL036"),
	ALLOCATOR_AL037("sdii_CODE_AL037","sdii_MESSAGE_AL037"),
	ALLOCATOR_AL038("sdii_CODE_AL038","sdii_MESSAGE_AL038"),
	ALLOCATOR_AL039("sdii_CODE_AL039","sdii_MESSAGE_AL039"),
	ALLOCATOR_AL040("sdii_CODE_AL040","sdii_MESSAGE_AL040"),
	ALLOCATOR_AL041("sdii_CODE_AL041","sdii_MESSAGE_AL041"),
	ALLOCATOR_AL042("sdii_CODE_AL042","sdii_MESSAGE_AL042"),
	ALLOCATOR_AL043("sdii_CODE_AL043","sdii_MESSAGE_AL043"),
	ALLOCATOR_AL044("sdii_CODE_AL044","sdii_MESSAGE_AL044"),
	ALLOCATOR_AL045("sdii_CODE_AL045","sdii_MESSAGE_AL045"),
	ALLOCATOR_AL046("sdii_CODE_AL046","sdii_MESSAGE_AL046"),
	ALLOCATOR_AL047("sdii_CODE_AL047","sdii_MESSAGE_AL047"),
	ALLOCATOR_AL048("sdii_CODE_AL048","sdii_MESSAGE_AL048"),
	ALLOCATOR_AL049("sdii_CODE_AL049","sdii_MESSAGE_AL049"),
	ALLOCATOR_AL050("sdii_CODE_AL050","sdii_MESSAGE_AL050"),
	ALLOCATOR_AL051("sdii_CODE_AL051","sdii_MESSAGE_AL051"),
	
	//Config 
	CONFIG_CF3("sdii_CODE_CF3","sdii_MESSAGE_CF3"),//
	
	//Dealers
	DEALERS_DE012("sdii_CODE_DE012","sdii_MESSAGE_DE012"),
	DEALERS_DE013("sdii_CODE_DE013","sdii_MESSAGE_DE013"),
	DEALERS_DE014("sdii_CODE_DE014","sdii_MESSAGE_DE014"),
	DEALERS_DE015("sdii_CODE_DE015","sdii_MESSAGE_DE015"),
	DEALERS_DE016("sdii_CODE_DE016", "sdii_MESSAGE_DE016"),
	DEALERS_DE017("sdii_CODE_DE017", "sdii_MESSAGE_DE017"),
	DEALERS_DE018("sdii_CODE_DE018", "sdii_MESSAGE_DE018"),
	DEALERS_DE019("sdii_CODE_DE019", "sdii_MESSAGE_DE019"),
	DEALERS_DE020("sdii_CODE_DE020", "sdii_MESSAGE_DE020"),
	DEALERS_DE021("sdii_CODE_DE021", "sdii_MESSAGE_DE021"),
	
	//Customer
	CUSTOMER_CS001("sdii_CODE_CS001","sdii_MESSAGE_CS001"), 
	
	
	//----------------------MÓDULO INVENTARIOS-------------------------------
	
	//gfandino-31/05/2011 Se adiciona por INV103 v2.2
	STOCK_IN334("sdii_CODE_IN334", "sdii_MESSAGE_IN334"),
	//gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
	STOCK_IN335("sdii_CODE_IN335", "sdii_MESSAGE_IN335"),
	STOCK_IN336("sdii_CODE_IN336", "sdii_MESSAGE_IN336"),
	STOCK_IN337("sdii_CODE_IN337", "sdii_MESSAGE_IN337"),
	STOCK_IN338("sdii_CODE_IN338", "sdii_MESSAGE_IN338"),
	STOCK_IN339("sdii_CODE_IN339", "sdii_MESSAGE_IN339"),
	//gfandino-02/06/2011 Se adiciona por INV106 v2.3
	MOVEMENT_TYPE_ALREADY_EXIST("sdii_CODE_IN340", "sdii_MESSAGE_IN340"),
	
	// jvelez -- 07/06/2011 -- Se adiciona para el módulo de inventarios
	WAREHOUSE_ELEMENT_ALREADY_IN_WH("sdii_CODE_warehouse_element_already_in_wh","sdii_MESSAGE_warehouse_element_already_in_wh"),
	ELEMENT_TYPE_NOT_EXIST("sdii_CODE_element_type_not_exist","sdii_MESSAGE_element_type_not_exist"),
	MOVEMENT_TYPE_NOT_EXIST("sdii_CODE_movement_type_not_exist","sdii_MESSAGE_movement_type_not_exist"),
	RECORD_STATUS_NOT_EXIST("sdii_CODE_record_status_not_exist","sdii_MESSAGE_record_status_not_exist"),
	// jvelez -- 21/06/2011 -- Se adiciona para la configuración de movimientos hacia IBS.
	MOV_CMD_CONFIG_NOT_EXIST("sdii_CODE_MOV_CMD_CONFIG_NOT_EXIST","sdii_MESSAGE_MOV_CMD_CONFIG_NOT_EXIST"),
	MOV_CMD_SUCCESS("sdii_CODE_IN456","sdii_MESSAGE_MOV_CMD_SUCCESS"),
	MOV_CMD_CONFIG_INACTIVE("sdii_CODE_IN457","sdii_MESSAGE_MOV_CMD_CONFIG_INACTIVE"),
	MOV_CMD_STATUS_NOT_EXIST("sdii_CODE_MOV_CMD_STATUS_NOT_EXIST","sdii_MESSAGE_MOV_CMD_STATUS_NOT_EXIST"),
	MOV_CMD_IBS_STATUS_NOT_EXIST("sdii_CODE_MOV_CMD_IBS_STATUS_NOT_EXIST","sdii_MESSAGE_MOV_CMD_IBS_STATUS_NOT_EXIST"),
	DEALER_OR_CUSTOMER_ARE_REQUIRED("sdii_CODE_DEALER_OR_CUSTOMER_ARE_REQUIRED","sdii_MESSAGE_DEALER_OR_CUSTOMER_ARE_REQUIRED"),
	MOV_CMD_QUEUE_RECORD_HAVENT_COUNTRY("sdii_CODE_MOV_CMD_QUEUE_RECORD_HAVENT_COUNTRY","sdii_MESSAGE_MOV_CMD_QUEUE_RECORD_HAVENT_COUNTRY"),

	
	STOCK_IN340("sdii_CODE_IN340", "sdii_MESSAGE_IN340"),
	//gfandino-03/06/2011 Se adiciona por INV107 v2.1
	STOCK_IN341("sdii_CODE_IN341", "sdii_MESSAGE_IN341"),
	//gfandino-04/06/2011 Se adiciona por INV108 v2.1
	STOCK_IN342("sdii_CODE_IN342", "sdii_MESSAGE_IN342"),
	//gfandino-07/06/2011 Se adiciona por INV109 v 2.4
	STOCK_IN343("sdii_CODE_IN343", "sdii_MESSAGE_IN343"),
	STOCK_IN344("sdii_CODE_IN344", "sdii_MESSAGE_IN344"),
	//gfandino-07/06/2011 Se adiciona por INV110 v 2.5
	STOCK_IN347("sdii_CODE_IN347", "sdii_MESSAGE_IN347"),
	STOCK_IN346("sdii_CODE_IN346", "sdii_MESSAGE_IN346"),
	STOCK_IN348("sdii_CODE_IN348", "sdii_MESSAGE_IN348"),
	STOCK_IN350("sdii_CODE_IN350", "sdii_MESSAGE_IN350"),
	STOCK_IN351("sdii_CODE_IN351", "sdii_MESSAGE_IN351"),
	//gfandino-07/06/2011 Se adiciona por INV111 2.2
	STOCK_IN354("sdii_CODE_IN354", "sdii_MESSAGE_IN354"),
	//hcorredor-21/06/2011 Se Adiciona por INV001 4.7
	STOCK_IN355("sdii_CODE_IN355", "sdii_MESSAGE_IN355"),
	//gfandino Se Adiciona por INV129
	STOCK_IN356("sdii_CODE_IN356", "sdii_MESSAGE_IN356"),
	STOCK_IN357("sdii_CODE_IN357", "sdii_MESSAGE_IN357"),
	//hcorredor Se Adiciona por INV1	
	STOCK_IN358("sdii_CODE_IN358", "sdii_MESSAGE_IN358"),
	// gfandino INV30 2.6 
	STOCK_IN362("sdii_CODE_IN362", "sdii_MESSAGE_IN362"),
	STOCK_IN363("sdii_CODE_IN363", "sdii_MESSAGE_IN363"),
	STOCK_IN364("sdii_CODE_IN364", "sdii_MESSAGE_IN364"),
	STOCK_IN367("sdii_CODE_IN367", "sdii_MESSAGE_IN367"),
	//hcorredor Se Adiciona por INV1	
	STOCK_IN365("sdii_CODE_IN365", "sdii_MESSAGE_IN365"),
	STOCK_IN366("sdii_CODE_IN366", "sdii_MESSAGE_IN366"),
	//gfandino 22/07/2011 Registros de archivos procesados
	STOCK_IN368("sdii_CODE_IN368", "sdii_MESSAGE_IN368"),
	//gfandino 22/07/2011 INV120 Procesando archivo
	STOCK_IN369("sdii_CODE_IN369", "sdii_MESSAGE_IN369"),
	STOCK_IN370("sdii_CODE_IN370", "sdii_MESSAGE_IN370"),
	STOCK_IN371("sdii_CODE_IN371", "sdii_MESSAGE_IN371"),
	STOCK_IN372("sdii_CODE_IN372", "sdii_MESSAGE_IN372"),
	STOCK_IN373("sdii_CODE_IN373", "sdii_MESSAGE_IN373"),
	STOCK_IN374("sdii_CODE_IN374", "sdii_MESSAGE_IN374"),
	STOCK_IN375("sdii_CODE_IN375", "sdii_MESSAGE_IN375"),
	STOCK_IN376("sdii_CODE_IN376", "sdii_MESSAGE_IN376"),
	STOCK_IN377("sdii_CODE_IN377", "sdii_MESSAGE_IN377"),
	STOCK_IN378("sdii_CODE_IN378", "sdii_MESSAGE_IN378"),
	STOCK_IN379("sdii_CODE_IN379", "sdii_MESSAGE_IN379"),
	STOCK_IN380("sdii_CODE_IN380", "sdii_MESSAGE_IN380"),
	STOCK_IN381("sdii_CODE_IN381", "sdii_MESSAGE_IN381"),
	
	STOCK_IN382("sdii_CODE_IN382", "sdii_MESSAGE_IN382"),
	STOCK_IN383("sdii_CODE_IN383", "sdii_MESSAGE_IN383"),
	STOCK_IN384("sdii_CODE_IN384", "sdii_MESSAGE_IN384"),
	STOCK_IN385("sdii_CODE_IN385", "sdii_MESSAGE_IN385"),
	STOCK_IN386("sdii_CODE_IN386", "sdii_MESSAGE_IN386"),
	STOCK_IN387("sdii_CODE_IN387", "sdii_MESSAGE_IN387"),
	STOCK_IN389("sdii_CODE_IN389", "sdii_MESSAGE_IN389"),
	STOCK_IN390("sdii_CODE_IN390", "sdii_MESSAGE_IN390"),
	STOCK_IN393("sdii_CODE_IN393", "sdii_MESSAGE_IN393"),
	
	//wjimenez 22/07/2011 INV36 registro de inconsistencias de remisiones
	STOCK_IN388("sdii_CODE_IN388", "sdii_MESSAGE_IN388"),
	STOCK_IN391("sdii_CODE_IN391", "sdii_MESSAGE_IN391"),
	STOCK_IN392("sdii_CODE_IN392", "sdii_MESSAGE_IN392"),
	
	//waguilera 22/08/2011 INV 136 Elementos serializados ajuste salida
	STOCK_IN394("sdii_CODE_IN394", "sdii_MESSAGE_IN394"),
	STOCK_IN395("sdii_CODE_IN395", "sdii_MESSAGE_IN395"),
	
	STOCK_IN396("sdii_CODE_IN396", "sdii_MESSAGE_IN396"),
	
	//hcorredor 23/08/2011 CU INV 1, Validar que no encuentre una numero de orden
	// de compra repetido con el mismo proveedor.
	STOCK_IN397("sdii_CODE_IN397", "sdii_MESSAGE_IN397"),
	//hcorredor 26/08/2011 se agrega mensaje de validacion para caracteres especiales.
	STOCK_IN398("sdii_CODE_IN398", "sdii_MESSAGE_IN398"),
	
	//jnova 29/08/2011 se agregan mensajes de error para confirmacion parcial de elementos de remision
	STOCK_IN399("sdii_CODE_IN399", "sdii_MESSAGE_IN399"),
	STOCK_IN400("sdii_CODE_IN400", "sdii_MESSAGE_IN400"),
	
	//hcorredor 29/08/2011 se agrega mensaje de error enel caso de almacenar registros de importacion sin crear la bodega de transito.
	STOCK_IN401("sdii_CODE_IN401", "sdii_MESSAGE_IN401"),
	
	//jnova 30/08/2011 se agregan mensajes para CU 42-43
	STOCK_IN402("sdii_CODE_IN402", "sdii_MESSAGE_IN402"),
	STOCK_IN403("sdii_CODE_IN403", "sdii_MESSAGE_IN403"),
	STOCK_IN404("sdii_CODE_IN404", "sdii_MESSAGE_IN404"),
	
	//waguilera 31/08/2011 se agregan errores para CU - 53
	STOCK_IN405("sdii_CODE_IN405", "sdii_MESSAGE_IN405"),
	STOCK_IN406("sdii_CODE_IN406", "sdii_MESSAGE_IN406"),
	STOCK_IN408("sdii_CODE_IN408", "sdii_MESSAGE_IN408"),
	STOCK_IN410("sdii_CODE_IN410", "sdii_MESSAGE_IN410"),
	STOCK_IN411("sdii_CODE_IN411", "sdii_MESSAGE_IN411"),
	STOCK_IN412("sdii_CODE_IN412", "sdii_MESSAGE_IN412"),
	STOCK_IN413("sdii_CODE_IN413", "sdii_MESSAGE_IN413"),
	
	//hcorredor 31/08/2011 se agrega mensaje para incidencia Nro 62739
	STOCK_IN407("sdii_CODE_IN407", "sdii_MESSAGE_IN407"),
	
	//hcorredor 02/09/2011 Se agrega mensaje para validar la eliminacion de un registro de
	//importacion CU - 02 Eliminar Registro de Importacion.
	STOCK_IN414("sdii_CODE_IN414", "sdii_MESSAGE_IN414"),
	STOCK_IN415("sdii_CODE_IN415", "sdii_MESSAGE_IN415"),
	
	STOCK_IN416("sdii_CODE_IN416", "sdii_MESSAGE_IN416"),
	
	STOCK_IN417("sdii_CODE_IN417", "sdii_MESSAGE_IN417"),
	
	STOCK_IN418("sdii_CODE_IN418", "sdii_MESSAGE_IN418"),
	STOCK_IN419("sdii_CODE_IN419", "sdii_MESSAGE_IN419"),
	STOCK_IN420("sdii_CODE_IN420", "sdii_MESSAGE_IN420"),
	STOCK_IN421("sdii_CODE_IN421", "sdii_MESSAGE_IN421"),
	
	STOCK_IN422("sdii_CODE_IN422", "sdii_MESSAGE_IN422"),
	STOCK_IN423("sdii_CODE_IN423", "sdii_MESSAGE_IN423"),
	STOCK_IN424("sdii_CODE_IN424", "sdii_MESSAGE_IN424"),
	STOCK_IN425("sdii_CODE_IN425", "sdii_MESSAGE_IN425"),
	STOCK_IN426("sdii_CODE_IN426", "sdii_MESSAGE_IN426"),
	STOCK_IN427("sdii_CODE_IN427", "sdii_MESSAGE_IN427"),
	STOCK_IN428("sdii_CODE_IN428", "sdii_MESSAGE_IN428"),
	STOCK_IN429("sdii_CODE_IN429", "sdii_MESSAGE_IN429"),
	STOCK_IN430("sdii_CODE_IN430", "sdii_MESSAGE_IN430"),
	STOCK_IN431("sdii_CODE_IN431", "sdii_MESSAGE_IN431"),
	STOCK_IN432("sdii_CODE_IN432", "sdii_MESSAGE_IN432"),
	STOCK_IN433("sdii_CODE_IN433", "sdii_MESSAGE_IN433"),
	STOCK_IN434("sdii_CODE_IN434", "sdii_MESSAGE_IN434"),
	STOCK_IN435("sdii_CODE_IN435", "sdii_MESSAGE_IN435"),
	STOCK_IN436("sdii_CODE_IN436", "sdii_MESSAGE_IN436"),
	STOCK_IN437("sdii_CODE_IN437", "sdii_MESSAGE_IN437"),
	STOCK_IN438("sdii_CODE_IN438", "sdii_MESSAGE_IN438"),
	STOCK_IN439("sdii_CODE_IN439", "sdii_MESSAGE_IN439"),
	STOCK_IN440("sdii_CODE_IN440", "sdii_MESSAGE_IN440"),
	STOCK_IN441("sdii_CODE_IN441", "sdii_MESSAGE_IN441"),
	STOCK_IN442("sdii_CODE_IN442", "sdii_MESSAGE_IN442"),
	STOCK_IN443("sdii_CODE_IN443", "sdii_MESSAGE_IN443"),
	STOCK_IN444("sdii_CODE_IN444", "sdii_MESSAGE_IN444"),
	STOCK_IN445("sdii_CODE_IN445", "sdii_MESSAGE_IN445"),
	STOCK_IN446("sdii_CODE_IN446", "sdii_MESSAGE_IN446"),
	STOCK_IN447("sdii_CODE_IN447", "sdii_MESSAGE_IN447"),
	STOCK_IN448("sdii_CODE_IN448", "sdii_MESSAGE_IN448"),
	STOCK_IN449("sdii_CODE_IN449", "sdii_MESSAGE_IN449"),
	STOCK_IN450("sdii_CODE_IN450", "sdii_MESSAGE_IN450"),
	STOCK_IN451("sdii_CODE_IN451", "sdii_MESSAGE_IN451"),
	STOCK_IN453("sdii_CODE_IN453", "sdii_MESSAGE_IN453"),
	STOCK_IN454("sdii_CODE_IN454", "sdii_MESSAGE_IN454"), //indica que se intenta agregar una inconsistencia de remisión de menos elementos con una cantidad no válida
	STOCK_IN455("sdii_CODE_IN455", "sdii_MESSAGE_IN455"), //indica que no hay elementos no serializados en la bodega de origen de la remisión
	STOCK_IN458("sdii_CODE_IN458", "sdii_MESSAGE_IN458"), //El elemento ya se encuentra registrado en una inconsistencia
	STOCK_IN459("sdii_CODE_IN459", "sdii_MESSAGE_IN459"),
	STOCK_IN460("sdii_CODE_IN460", "sdii_MESSAGE_IN460"),
	STOCK_IN461("sdii_CODE_IN461", "sdii_MESSAGE_IN461"),
	STOCK_IN462("sdii_CODE_IN462", "sdii_MESSAGE_IN462"),
	STOCK_IN463("sdii_CODE_IN463", "sdii_MESSAGE_IN463"),
	STOCK_IN464("sdii_CODE_IN464", "sdii_MESSAGE_IN464"),
	STOCK_IN465("sdii_CODE_IN465", "sdii_MESSAGE_IN465"),
	STOCK_IN467("sdii_CODE_IN467", "sdii_MESSAGE_IN467"),
	STOCK_IN468("sdii_CODE_IN468", "sdii_MESSAGE_IN468"),
	STOCK_IN469("sdii_CODE_IN469", "sdii_MESSAGE_IN469"),
	STOCK_IN470("sdii_CODE_IN470", "sdii_MESSAGE_IN470"),
	STOCK_IN471("sdii_CODE_IN471", "sdii_MESSAGE_IN471"),
	STOCK_IN472("sdii_CODE_IN472", "sdii_MESSAGE_IN472"),
	STOCK_IN473("sdii_CODE_IN473", "sdii_MESSAGE_IN473"),
	STOCK_IN474("sdii_CODE_IN474", "sdii_MESSAGE_IN474"),
	STOCK_IN475("sdii_CODE_IN475", "sdii_MESSAGE_IN475"),
	STOCK_IN476("sdii_CODE_IN476", "sdii_MESSAGE_IN476"),
	STOCK_IN477("sdii_CODE_IN477", "sdii_MESSAGE_IN477"),
	STOCK_IN478("sdii_CODE_IN478", "sdii_MESSAGE_IN478"),
	STOCK_IN479("sdii_CODE_IN479", "sdii_MESSAGE_IN479"),
	STOCK_IN480("sdii_CODE_IN480", "sdii_MESSAGE_IN480"),
	STOCK_IN481("sdii_CODE_IN481", "sdii_MESSAGE_IN481"),
	STOCK_IN482("sdii_CODE_IN482", "sdii_MESSAGE_IN482"),
	STOCK_IN483("sdii_CODE_IN483", "sdii_MESSAGE_IN483"),
	STOCK_IN484("sdii_CODE_IN484", "sdii_MESSAGE_IN484"),
	STOCK_IN485("sdii_CODE_IN485", "sdii_MESSAGE_IN485"),
	STOCK_IN486("sdii_CODE_IN486", "sdii_MESSAGE_IN486"),
	STOCK_IN487("sdii_CODE_IN487", "sdii_MESSAGE_IN487"),
	STOCK_IN488("sdii_CODE_IN488", "sdii_MESSAGE_IN488"),
	STOCK_IN489("sdii_CODE_IN489", "sdii_MESSAGE_IN489"),
	STOCK_IN490("sdii_CODE_IN490", "sdii_MESSAGE_IN490"),
	STOCK_IN491("sdii_CODE_IN491", "sdii_MESSAGE_IN491"),
	STOCK_IN492("sdii_CODE_IN492", "sdii_MESSAGE_IN492"),
	STOCK_IN493("sdii_CODE_IN493", "sdii_MESSAGE_IN493"),
	STOCK_IN494("sdii_CODE_IN494", "sdii_MESSAGE_IN494"),
	STOCK_IN495("sdii_CODE_IN495", "sdii_MESSAGE_IN495"),
	STOCK_IN496("sdii_CODE_IN496", "sdii_MESSAGE_IN496"),
	STOCK_IN497("sdii_CODE_IN497", "sdii_MESSAGE_IN497"),
	STOCK_IN498("sdii_CODE_IN498", "sdii_MESSAGE_IN498"),
	STOCK_IN499("sdii_CODE_IN499", "sdii_MESSAGE_IN499"),
	STOCK_IN500("sdii_CODE_IN500", "sdii_MESSAGE_IN500"),
	STOCK_IN501("sdii_CODE_IN501", "sdii_MESSAGE_IN501"),
	STOCK_IN502("sdii_CODE_IN502", "sdii_MESSAGE_IN502"),
	STOCK_IN503("sdii_CODE_IN503", "sdii_MESSAGE_IN503"),
	STOCK_IN504("sdii_CODE_IN504", "sdii_MESSAGE_IN504"),
	STOCK_IN506("sdii_CODE_IN506", "sdii_MESSAGE_IN506"),
	STOCK_IN507("sdii_CODE_IN507", "sdii_MESSAGE_IN507"),
	STOCK_IN508("sdii_CODE_IN508", "sdii_MESSAGE_IN508"),
	STOCK_IN509("sdii_CODE_IN509", "sdii_MESSAGE_IN509"),
	STOCK_IN510("sdii_CODE_IN510", "sdii_MESSAGE_IN510"),
	STOCK_IN511("sdii_CODE_IN511", "sdii_MESSAGE_IN511"),
	
	GENERAL_BL192("sdii_CODE_BL192", "sdii_MESSAGE_IN511"),
	ERROR_VALIDATION("sdii_CODE_ERROR_VALIDATION", "sdii_CODE_ERROR_VALIDATION"),
	
	QUERY_SIZE_EXCEEDED("sdii_CODE_BL_QUERY_SIZE_EXCEEDED", "sdii_MESSAGE_BL_QUERY_SIZE_EXCEEDED"),
	QUERY_SIZE_DAYS_RANGE_EXCEEDED("SDII_CODE_BL_QUERY_DAYS_RANGE_SIZE_EXCEEDED", "sdii_MESSAGE_BL_QUERY_SIZE_EXCEEDED"),
	START_DATE_REQUIRED("sdii_CODE_start_date_required","sdii_MESSAGE_start_date_required"),
	FINAL_DATE_REQUIRED("sdii_CODE_final_date_required","sdii_MESSAGE_final_date_required"),
	COUNTRY_REQUIRED("sdii_CODE_country_required","sdii_MESSAGE_country_required"),
	INIT_DATE_AFTER_END_DATE("sdii_CODE_init_date_after_end_date","sdii_MESSAGE_init_date_after_end_date"),
	WORK_ORDER_REQUIRED("sdii_CODE_work_order_required","sdii_CODE_work_order_required"),	
	ERROR_DEALER_SALE_CREATE("sdii_CODE_error_dealer_sale_create","sdii_CODE_error_dealer_sale_create"),
	
	CORE_ALLOCATOR_PARALLEL_1("sdii_CODE_CORE_ALLOCATOR_PARALLEL_1", "sdii_CODE_CORE_ALLOCATOR_PARALLEL_1"),
	CORE_ALLOCATOR_PARALLEL_2("sdii_CODE_CORE_ALLOCATOR_PARALLEL_2", "sdii_CODE_CORE_ALLOCATOR_PARALLEL_2"),
	
	CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL("sdii_CODE_CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL", "sdii_CODE_CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL"),
	CORE_THE_WORK_ORDER_SERVICE_HAS_NOT_SERIALS("sdii_CODE_CORE_THE_WORK_ORDER_SERVICE_HAS_NOT_SERIALS", "sdii_CODE_CORE_THE_WORK_ORDER_SERVICE_HAS_NOT_SERIALS"),

	CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST("sdii_CODE_CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST", "sdii_CODE_CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST"),
	CORE_THE_WORK_ORDER_DOES_NOT_EXIST("sdii_CODE_CORE_THE_WORK_ORDER_DOES_NOT_EXIST", "sdii_CODE_CORE_THE_WORK_ORDER_DOES_NOT_EXIST"),
	
	//27/08/2013 ialessan Modificación por HSP + IST Colombia IN22854
	SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION_NOT_EXIST("sdii_SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION_NOT_EXIST","sdii_SYSTEM_PARAM_IBS_ELEMENTS_NOTIFICATION_NOT_EXIST"),
	
	REFERENCE_IS_NOT_IN_CREATED_STATE("sdii_REFERENCE_IS_NOT_IN_CREATED_STATE","sdii_REFERENCE_IS_NOT_IN_CREATED_STATE"),
	REFERENCE_HAS_FILES_IN_PROCESSING_STATUS("sdii_CODE_reference_has_files_in_processing_status","sdii_CODE_reference_has_files_in_processing_status"),
	
	/*ialessan IN317430 - Inconsistencia en remisiones
	se agregan mensajes de error al intentar agregar o eliminar elementos en estados de remision invalidos*/
	STOCK_IN_ADD_ELE("sdii_CODE_ref_add_ele_invalid_status", "sdii_MESSAGE_ref_add_ele_invalid_status"),
	STOCK_IN_REM_ELE("sdii_CODE_ref_remove_ele_invalid_status", "sdii_MESSAGE__ref_remove_ele_invalid_status"),	
	;
	

    private String code;
    private String message;
    private final static Logger log = UtilsBusiness.getLog4J(ErrorBusinessMessages.class);

    ErrorBusinessMessages(String pErrorCode, String pEMessage) {
        this.code = pErrorCode;
        this.message = pEMessage;
    }


    /**
     * 
     * Metodo: Retorna el codigo del error, 
     * obtenido de el archivo de propiedades.
     * Properties File:  BusinessMessages.properties
     * @return String
     * @author Joan Lopez
     */
    public String getCode() {
    	String errorCode = null;
    	try {
    		ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
        	errorCode = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorCode();
        } catch (Exception e) {
            log.error("Error en la lectura de mensajes de error", e);
        }
        return errorCode;
    }

    /**
     *
     * Metodo: Retorna el mensaje del error,
     * obtenido de el archivo de propiedades.
     * Properties File:  BusinessMessages.properties
     * @return String
     * @author Joan Lopez
     */
    public String getMessage() {
    	String errorMessage = null;
        try {           
        	ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
        	errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage();
        } catch (Exception e) {
            log.error("Error en la lectura de mensajes de error", e);
        }
        return errorMessage;
    }
    
    /**
    *
    * Metodo: Retorna el codigo+mensaje del error,
    * obtenido de el archivo de propiedades.
    * Properties File:  BusinessMessages.properties
    * @return String
    * @author Joan Lopez
    */
   public String getMessageCode() {
	   
	   StringBuffer buffer = new StringBuffer();
	   String errorCode, errorMessage;
       try {   	       	   
    	   ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
    	   errorCode = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorCode();
    	   errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage();
    	   
    	   buffer.append(errorCode);
           buffer.append(":::");
           buffer.append(errorMessage);    	   
       } catch (Exception e) {
           log.error("Error en la lectura de mensajes de error", e);
       }
       return buffer.toString();
   }
   
   /**
    * 
    * Metodo: Retorna el mensaje del error,
    * obtenido de el archivo de propiedades.
    * @param locale LocaleVO - especifica la region geografica para internacionalizacion de los
	* mensajes retornados por la capa de negocio.
    * @param params[] Object - parametros para reemplazar en el mensaje
    * @return String - retorna el mensaje.
    * @author Joan Lopez
    */
  public String getMessage(Object params[]) {
  	String errorMessage = null;
      try {           	  	
    	  	ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
        	errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage();
      	 	errorMessage = getMessageResourceString(errorMessage,params);
      } catch (Exception e) {
          log.error("Error en la lectura de mensajes de error", e);
      }
      return errorMessage;
  }
  
  /**
   * 
   * Metodo: Retorna el mensaje del error,
   * obtenido de el archivo de propiedades.
   * @param locale LocaleVO - especifica la region geografica para internacionalizacion de los
	* mensajes retornados por la capa de negocio.
   * @param params[] Object - parametros para reemplazar en el mensaje
   * @return String - retorna el mensaje.
   * @author Joan Lopez
   */
 public String getMessageCode(Object params[]) {
	StringBuffer buffer = new StringBuffer();
	String errorCode, errorMessage; 	
     try {           	  	
   	  	ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
       	errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage();
       	errorCode = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorCode();
     	errorMessage = getMessageResourceString(errorMessage,params);
     	 
     	buffer.append(errorCode);
        buffer.append(":::");
        buffer.append(errorMessage);    	 
     } catch (Exception e) {
         log.error("Error en la lectura de mensajes de error", e);
     }
     return buffer.toString();
 }
   
   /**
    * 
    * Metodo: Permite dar formato a un mensaje por medio
    * de parametros asignados.
    * @param text String - Cadena con el mensaje
    * @param params[] Object - parametros para reemplazar en el mensaje
    * @return String - retorna el mensaje formateado.
    * @author Joan Lopez
    */
   private static String getMessageResourceString(String text, Object params[]) {
				
		if (text!= null && params != null) {
			if(!text.equals("")){
				MessageFormat mf = new MessageFormat(text);
				text = mf.format(params, new StringBuffer(), null).toString();
			}
		}
		
		return text;
	}
   
}
