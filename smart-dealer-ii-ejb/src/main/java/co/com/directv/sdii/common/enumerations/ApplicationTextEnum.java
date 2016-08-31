package co.com.directv.sdii.common.enumerations;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.PropertiesException;

public enum ApplicationTextEnum {

	//AssignmentSkillCoordinator
	RESULT("result"),
	ORDER_CONFIG("order_config"), 
	DEALERS_FOUND("dealers_found"),
	DEALERS_ARE("dealers_are"),
	
	//DealerBuildingsFileProcessor
	DEALER_CODE("dealer_code"),
	DEALER_DEPOT_CODE("dealer_depot_code"),
	POSTAL_CODE("postal_code"),
	BUILDING_CODE("building_code"),
	
	//DealerCoverageFileProcessor
	PERMANENT("permanent"),
	PRIORITY("priority"),
	
	//DealerConfCoverageFileProcessor	
	BUSINESS_AREA_CODE("business_area_code"),
	CUSTOMER_CATEGORY_CODE("customer_category_code"),
	
	//DealerDetailsFileProcessor
	DEPOT_CODE("depot_code"),
	TECHNICAL_WOX("technical_wox"),
	COMP_WOX("comp_wox"),
	ED_ATTEND("ed_attend"),
	PAIR_OR_ODD_ATTEND("pair_or_odd_attend"),
	
	//DealerServiceSubcatWthPcFileProcessor
	SERVICE_SUBCAT_CODE("service_subcat_code"),
	
	//ExcelGenerator
	EXCEL_REPORT("excel_report"),
	PDF_REPORT("pdf_report"),
	
	//ReferenceMailSender
	NEW_INCONSISTENCY("new_inconsistency"),
	TYPE_INCONSISTENCY("type_inconsistency"),
	OBSERVATIONS_MADE("observations_made"),
	DATE_REGISTRATION_INCONSISTENCY("date_registration_inconsistency"),
	INCONSISTENT_ELEMENTS("inconsistent_elements"),
	NAME("name"),
	SERIAL("serial"),
	SERIAL_LINKED("serial_linked"),
	QUANTITY("quantity"),
	CLOSE_INCONSISTENCY_REMISSION("close_inconsistency"),
	GENERATE_NEW_INCONSISTENCY("generate_new_inconsistency"),
	CLOSE_INCONSISTENCY("close_inconsistency"),
	ELEMENTS_ASSOCIATED_REMISSION("elements_associated_remission"),
	NEW_ELEMENTS_REMISSION("new_elements_remission"),	
	
	//ResourceBundleReader
	APPLICATION_TEXT("application_text"),
	
	//UtilsBusiness
	WO("wo"),
	CLIENT_IBS("client_ibs"),
	CLIENT_DOCUMENT("client_document"),
	CLIENT("client"),
	CLIENT_TYPE("client_type"),
	TELEPHONE_HOUSE("telephone_house"),
	TELEPHONE_OFFICE("telephone_office"),
	CELL_PHONE("cell_phone"),
	MAIL("mail"),
	FAX("fax"),
	ADDRESS("address"),
	ADDITIONAL_INDICATIONS("additional_indications"),
	SELLER("seller"),
	DEPOT_SELLER("depot_seller"),
	HSP_STATUS("hsp_status"),
	IBS_STATUS_CODE("ibs_status_code"),
	IBS_STATUS("ibs_status"),
	REASON("reason"),
	LAST_MODIFIED_DATE("last_modified_date"),
	ELEMENT_MODEL("element_model"),
	DECOS_QUANTITY("decos_quantity"),
	PREVIOUS_VISITS("previous_visits"),
	RETIRED_IRD_SERIE("retired_ird_serie"),
	RETIRED_SC_SERIE("retired_sc_serie"),
	INSTALLED_IRD_SERIE("installed_ird_serie"),
	INSTALLED_SC_SERIE("installed_sc_serie"),
	CREATION_DATE("creation_date"),
	PUBLICATION_DATE("publication_date"),
	ASSIGNATION_DATE("assignation_date"),
	CONTACT("contact"),
	PROGRAMATION_JOURNEY("programation_journey"),
	PROGRAMATION_DATE("programation_date"),
	ATTENTION_DATE("attention_date"),
	FINALIZATION_DATE("finalization_date"),
	COMPANY_OBSERVATION("company_observation"),
	WO_DESCRIPTION("wo_description"),
	ACTION("action"),
	CUSTOMER_CLASS("customer_class"),
	SERVICE_CATEGORY("service_category"),
	SERVICE_CATEGORY_CODE("service_category_code"),
	SERVICE_CODE("service_code"),
	SERVICE_NAME("service_name"),
	DEPOT_INSTALLER_COMPANY("depot_installer_company"),
	INSTALLER_COMPANY_CODE("installer_company_code"),
	INSTALLER_COMPANY("installer_company"),
	DEPARTMENT("department"),
	CITY("city"),
	PERIMETER("perimeter"),
	RESPONSABLE_DOCUMENT("responsable_document"),
	TECHNICAL_CODE("technical_code"),
	RESPONSABLE_CREW("responsable_crew"),
	AUXILIAR_CREW("auxiliar_crew"),
	DISPATCHER_NAME("dispatcher_name"),
	DISPATCHER_USER("dispatcher_user"),
	LAST_GESTION_REASON("last_gestion_reason"),
	PROBLEM_DESCRIPTION("problem_description"),
	SERIAL_NUMBER("serial_number"),
	LINKED_SERIAL_NUMBER("linked_serial_number"),
	OPTIMUS_STATUS("optimus_status"),
	OPTIMUS_STATUS_DATE("optimus_status_date"),
	OPTIMUS_DECLINE_REASON("optimus_decline_reason"),
	OPTIMUS_DECLINE_DATE("optimus_decline_date"),
	
	//CSRBusiness
	ERROR_REPORT("error_report"),
	SHIPPING_ERRORS_PROCESS("shipping_errors_process"),
	
	//KpiGeneratorBusinessBean
	COMPANY_CODE("company_code"),
	COMPANY_NAME("company_name"),
	
	//CoreWorkServiceBrokerImple
	SCHEDULE_WORK_ORDER("schedule_work_order"),
	EMPTY("empty"),
	
	//CoreWorkorderImporterServiceTOA
	IBS_SCHEDULE_WORK_ORDER("ibs_schedule_work_order"),
	
	//EmailTypeBusinessBean
	ELEMENT("element"),
	DESTINATION("destination"),
	NO_TECHNICIAN_ASSIGNED("no_technician_assigned"),
	WO_NOT_SERVICE("wo_not_service"),
	TECHNICAL("technical"),
	SCHEDULED_BY_CSR("scheduled_by_csr"),
	RE_SCHEDULED_BYE_CSR("re_scheduled_bye_csr"),
	AUTOMATICALLY_RESCHEDULED("automatically_rescheduled"),
	AFTER_SCHEDULE_DETECTION_IN_WO_IMPORT("after_schedule_detection_in_wo_import"),
	AUTOMATICALLY_SCHEDULE("automatically_schedule"),
	NOT_FOUND_RESPONSIBLE_CREW("not_found_responsible_crew"),
	NO_CREW_ASSIGNED_WO("no_crew_assigned_wo"),
	OBSERVATIONS("observations"),
	USER("user"),
	DOCUMENT_IDENTIFICATION_NOVELTY("document_identification_novelty"),	
	TYPE_NOVELTY("type_novelty"),
	NOVELTY_OBSERVATIONS("novelty_observations"),
	NOVELTY_IDENTIFICATION_DOCUMENT("novelty_identification_document"),
	USER_ORIGINATES_NOVELTY("user_originates_novelty"),	
	
	//WoInfoEsbServiceBusinessBean
	WO_LOAD("wo_load"),
	AUTOMATIC_LOADING_PROCESS("automatic_loading_process"),
	
	//WoLoadBusinessBean
	BEGIN_DATE("begin_date"),
	END_DATE("end_date"),
	WOS_FOUND("wos_found"),
	WOS_PROCESSED_WARNING("wos_processed_warning"),
	WOS_PROCESSED_ERROR("wos_processed_error"),
	WO_CODE("wo_code"),
	CLIENT_CODE("client_code"),
	ERROR_CODE("error_code"),
	ERROR_MESSAGE("error_message"),
	REPORT_LOAD_WO("report_load_wo"),
	ATTENTION_FINALIZATION_WOS("attention_finalization_wos"),
	COUNTRY("country"),
	SUCCESSFULLY_PROCESSED("successfully_processed"),
	STATUS_PROCESS("status_process"),
	PROCESS("process"),
	
	//DealersCrudBean
	IBS_DEALER_CODE("ibs_dealer_code"),
	DEALER_STATUS_CODE("dealer_status_code"),
	DEALER_STATUS("dealer_status"),
	DEALER_PRINCIPAL_IBS_CODE("dealer_principal_ibs_code"),
	SCORE("score"),
	IS_PRINCIPAL("is_principal"),
	
	//FileProcessorChangeTypeSerializedElements
	ELEMENT_SERIAL("element_serial"),
	NEW_ELEMENT_MODEL_CODE("new_element_model_code"),
	NEW_ELEMENT_TYPE_CODE("new_element_type_code"),
	SERIAL_LINKED_ELEMENT("serial_linked_element"),
	MODEL_CODE_NEW_LINKED_ELEMENT("model_code_new_linked_element"),
	TYPE_CODE_LINKED_ELEMENT("type_code_linked_element"),
	
	//FileProcessorCompareWarehousePhysicalInventorySmartdealer
	ELEMENT_TYPE("element_type"),
	ELEMENT_CODE("element_code"),
	ELEMENT_STATUS("element_status"),
	UNIT_MEASURE_ITEM("unit_measure_item"),
	QUANTITY_FOUND_WAREHOUSE("quantity_found_warehouse"),
	MODEL("model"),
	SERIAL_ELEMENT("serial_element"),
	RID("rid"),
	ARCHIVE("archive"),
	HSP("hsp"),
	IS_ELEMENT_TYPE("is_element_type"),
	NOT_ELEMENT_TYPE("not_element_type"),
	
	//FileProcessorConfirmItemsLoadMassiveReference
	//@Todo("crear nueva entrada en los archivos ApplicationText_en_xx.properties"  "referral_code")
	//
	//CODE_REFERRAL("code_referral"),
	//ELEMENT_TYPE_CODE("element_type_code"),
	//SERIAL("serial"),
	//SERIAL_LINKED("serial_linked"),
	//QUANTITY("quantity"),
	
	//FileProcessorConfirmSerialItemsFromImportLog
	TYPE_ELEMENT_CODE("type_element_code"),
	WAREHOUSE_QUALITY("warehouse_quality"),
	LINKED_ELEMENT_TYPE("linked_element_type"),
	ELEMENT_IS_SERIALIZED("element_is_serialized"),
	
	//FileProcessorLoadMassiveSerializedElementsAdjustmentOutput
	LOCATION_CODE("location_code"),
	
	//FileProcessorLoadMassiveSerializedElementsAdjustmentTransfer
	ORIGIN_LOCATION_CODE("origin_location_code"),
	DESTINATION_LOCATION_CODE("destination_location_code"),
	
	//FileProcessorMassiveLinkSerializedElements
	WAREHOUSE_CODE("warehouse_code"),
	
	//FileProcessorMassiveUpReferences
	AMOUNT_REQUIRED("amount_required"),
	PREPAID("prepaid"),
	RN_NUMBER("rn_number"),
	
	//FileProcessorReferenceNotSerializedElements
	LOAD("load"),
	ELEMENT_TYPE_NOT_SERIALIZED("element_type_not_serialized"),
	
	//FileProcessorRegisterQASerializedElements
	STATUS("status"),
	
	//ReportGeneratorBusinessBean
	CONTACTS_DETAILS("contacts_details"),
	MEDIA_CONTACT_SELLER("media_contact_seller"),
	//REPORT("report"),
	WAREHOUSE_TYPE("warehouse_type"),
	COMPANY("company"),
	BRANCH("branch"),
	LOCATION("location"),
	CREW("crew"),
	MODEL_CODE_NAME("model_code_name"),
	AGE("age"),
	REFERRALS("referrals"),
	LOCATIONS("locations"),
	EXIST_CLIENT("exist_client"),
	
	//ReportGeneratorInventoryModule01BusinessBean
	INVENTORY_MOVEMENTS("inventory_movements"),
	IMPORT_RECORDS("import_records"),
	SERIALIZED_IMPORT_RECORDS("serialized_import_records"),
	NOT_SERIALIZED_IMPORT_RECORDS("not_serialized_import_records"),
	ADJUSTMENTS("adjustments"),
	ELEMENTS_SET_AUTHORIZATION("elements_set_authorization"),
	
	//CoreStockBusiness
	AVAILABLE("available"),
	
	//IbsElementsNotificationBusiness
	MOVEMENT("movement"),
	STATUS_UPDATE("status_update"),
	RECOVER_SERIALIZED_ITEM("recover_serialized_item"),
	
	//ImportLogBusinessBean
	NOTIFIES("notifies"),
	INCONSISTENCIES_CREATED("inconsistencies_created"),
	INCONSISTENCIES_UPDATE("inconsistencies_update"),
	INCONSISTENCIES_CLOSED("inconsistencies_closed"),
	INCONSISTENCIES_ELIMINATED("inconsistencies_eliminated"),
	INCONSISTENCIES_CREATED_ADDITION_ELEMENT("inconsistencies_created_addition_element"),
	INCONSISTENCIES_CREATED_ELIMINATION_ELEMENT("inconsistencies_created_elimination_element"),
	CREATED_INCONSISTENCIES_UPDATING_EXPECTED_NUMBER_ITEM("created_inconsistencies_updating_expected_number_item"),
	ELEMENT_ID("element_id"),
	TYPE("type"),
	OBSERVATION("observation"),
	USER_REPORTING("user_reporting"),
	DATE("date"),
	RESPONSE("response"),
	PURCHASE_ORDER("purchase_order"),
	DOCUMENT_IMPORT("document_import"),
	IMPORT_REGISTRATION("import_registration"),
	
	//ImportLogItemBusinessBean
	ELEMENT_IS_NOT_SERIALIZED("element_is_not_serialized"),
	ELEMENTS_NOT_SERIALIZED_QUANTITY("elements_not_serialized_quantity"),
	
	//MoveElementsFromWHCrewToWHCompany
	COMPANY_IDENTIFIER("company_identifier"),
	NO_WAREHOUSE_TYPE("no_warehouse_type"),
	NO_ASSOCIATED_CREWS("no_associated_crews"),
	CREW_IDENTIFIER("crew_identifier"),
	NO_ELEMENTS_WAREHOUSE_ONE("no_elements_warehouse_one"),
	
	//RefConfirmationBusinessBean
	CONFIRMATION_REFERRAL("confirmation_referral"),
	
	//ReferenceBusinessBean
	REFERRAL_SHIPPING_RECORD("referral_shipping_record"),
	CREATED_REFERRAL_CODE("created_referral_code"),
	CODE_REFERRAL("code_referral"),
	SENT("sent"),
	SENDING_LOG_REMISSION("sending_log_remission"),
	
	//RefInconsistencyBusinessBean
	MAX_QUANTITY("max_quantity"),
	
	
	//ReportsStockBusiness
	ELEMENT_TYPE_CODE("element_type_code"),
	ELEMENT_TYPE_NAME("element_type_name"),
	
	//ReportsStockBusiness
	WAREHOUSE_LOCATION("warehouse_location"),
	INITIAL_QUANTITY("initial_quantity"),
	ENTRIES("entries"),
	DEPARTURES("departures"),
	CURRENT_BALANCE("current_balance"),
	MOVEMENT_TYPE("movement_type"),
	CAUSAL_MOTION("causal_motion"),
	DATE_AND_TIME("date_and_time"),
	
	
	//WarehouseElementBusinessBean
	QUANTITY_MOVE("quantity_move"),
	WAREHOUSE_DESTINY_CODE("warehouse_destiny_code"),
	WAREHOUSE_DESTINY_COUNTRY("warehouse_destiny_country"),
	
	//WarehouseElementStockBusinessBean
	QUANTITY_MIN_STOCK("quantity_min_stock"),
	QUANTITY_WAREHOUSE("quantity_warehouse"),
	
	//ReportsGenerator
	TEMPLATE_VISITS("template_visits"),
	TEMPLATE_SERVICES("template_services"),
	COMMENTS("comments"),

	//CMDElementsByElementType
	SERIALIZED("serialized"),
	NOT_SERIALIZED("not_serialized"),
	ID("id"),
	BATCH("batch"),
	BRAND("brand"),
	CLASS("class"),
	SUPPLIER("supplier"),
	WARRANTY_PERIOD("warranty_period"),
	UNIT_MEASURE("unit_measure"),
	
	//CMDPreLoadImportLogToPrintByID
	BASIC_FACTS("basic_facts"),
	ELEMENTS_NOT_SERIALIZED("elements_not_serialized"),
	CONFIRMATIONS("confirmations"),
	ELEMENTS_SERIALIZED("elements_serialized"),
	MODIFICATIONS("modifications"),
	INCONSISTENCIES("inconsistencies"),
	CONSECUTIVE_IMPORT("consecutive_import"),
	ESTIMATED_DELIVERY_DATE("estimated_delivery_date"),
	SHIPPING_DATE("shipping_date"),
	REGISTRY_IMPORTS_STATUS("registry_imports_status"),
	USER_CREATION("user_creation"),
	LEGAL_DOCUMENT_TRANSPORT("legal_document_transport"),
	EXPECTED_QUANTITY("expected_quantity"),
	STATUS_ELEMENT_NOT_SERIALIZED("status_element_not_serialized"),
	PARTIAL_CONFIRMATION_DATE("partial_confirmation_date"),
	PARTIAL_QUANTITY("partial_quantity"),
	ELEMENT_RID("element_rid"),
	DATE_MODIFIED("date_modified"),
	USER_MODIFIED("user_modified"),
	MODIFICATION_TYPE("modification_type"),
	INCONSISTENCY_CODE("inconsistency_code"),
	OBSERVATIONS_INCONSISTENCY("observations_inconsistency"),
	USER_REGISTERS_INCONSISTENCY("user_registers_inconsistency"),
	RESPONSE_INCONSISTENCY("response_inconsistency"),
	USER_RESPONDED_INCONSISTENCY("user_responded_inconsistency"),
	REGISTRATION_DATE_RESPONSE_INCONSISTENCY("registration_date_response_inconsistency"),
	STATUS_INCONSISTENCY("status_inconsistency"),
	
	//CMDPreLoadReferenceToPrintByID
	MODIFICATIONS_REMISSION("modifications_remission"),
	WAREHOUSE("warehouse"),
	SHIPPING_DATA("shipping_data"),
	JOIN_DATA("join_data"),
	ELEMENTS_NOT_SERIALIZED_REMISSION("elements_not_serialized_remission"),
	ELEMENTS_SERIALIZED_REMISSION("elements_serialized_remission"),
	INCONSISTENCIES_REMISSION("inconsistencies_remission"),
	CONSECUTIVE_REFERRAL("consecutive_referral"),
	COMPLETION_DATE_CREATION_REMISSION("completion_date_creation_remission"),
	SPECIAL_COMMENTS("special_comments"),
	CREATING_USER_REMISSION("creating_user_remission"),
	STATUS_REMISSION("status_remission"),
	ORIGIN_LOCATION("origin_location"),
	LOCATION_TRANSIT("location_transit"),
	DESTINY_LOCATION("destiny_location"),
	COMPANY_ADDRESS_BRANCH_TARGET("company_address_branch_target"),
	PHONE_NUMBER("phone_number"),
	RESPONSIBLE_WAREHOUSE_RECIPIENT("responsible_warehouse_recipient"),
	DATE_DISPATCH_ITEMS("date_dispatch_items"),
	CONVEYOR_COMPANY_CODE("conveyor_company_code"),
	GUIDE_NUMBER("guide_number"),
	DRIVER_NAME("driver_name"),
	VEHICLE_PLATE("vehicle_plate"),
	NUMBER_UNITS_SHIPPED("number_units_shipped"),
	VOLUME("volume"),
	EXPECTED_DATE_DELIVERY_ITEMS("expected_date_delivery_items"),
	USER_REGISTERS_SHIPPING("user_registers_shipping"),
	DATE_RECEIPT_ITEMS("date_receipt_items"),
	NUMBER_UNITS_RECEIVED("number_units_received"),
	USER_RECORDS_REVENUE("user_records_revenue"),
	AMOUNT_REMITTED("amount_remitted"),
	
	//CMDSerializedElementbyDealerSerialCodeTypeElement
	BRANCH_CODE("branch_code"),
	BRANCH_NAME("branch_name"),
	
	//CMDSerializedElementInReference
	IRD("ird"),
	REGISTRATION_DATE("registration_date"),
	
	//CMDWarehouseElementsByFilters
	ACTUAL_QUANTITY("actual_quantity"),
	CAUSE_ADJUSTMENT("cause_adjustment"),
	DATE_ENTRY("date_entry"),
	DEPARTURE_DATE("departure_date"),
	STATE_REGISTRY("state_registry"),
	
	//CMDWarehousesByComplexFilter
	PERSON_RESPONSIBLE("person_responsible"),
	CREW_ID("crew_id"),
	IBS_CUSTOMER_CODE("ibs_customer_code"),
	
	//CMDWorkOrderByDealer
	SCHEDULE_DATE("schedule_date"),
	WO_STATUS("wo_status"),
	SURNAMES("surnames"),
	
	//ReportsCoreBusiness
	CLIENT_NUMBER("client_number"),
	ORDER_NUMBER("order_number"),
	CLIENT_NAME("client_name"),
	TELEPHONE("telephone"),
	JOB_CARD("job_card"),
	JOB_CARD_TYPE("job_card_type"),
	JOB_CARD_IBS_STATUS("job_card_ibs_status"),
	TECHNICAL_NAME("technical_name"),
	DAY_QUANTITY("day_quantity"),
	COMPLETION_DATE("completion_date"),
	PRODUCT("product"),
	WO_NUMBER("wo_number"),
	SERVICE_CAT("service_cat"),
	SERVICE_TYPE_NAME("service_type_name"),
	SCHEDULED("scheduled"),
	RESCHEDULED("rescheduled"),
	JOB_CARD_STATUS("job_card_status"),
	APPOINTMENT("appointment"),
	RESCHEDULING("rescheduling"),
	CONFIGURATION("configuration"),
	BACKLOG_DAYS("backlog_days"),
	CHANGE_STATUS_DATE("change_status_date"), 
	PHONE_CLIENTE("phone_client"),
	CLIENT_ADDRESS("client_address"),
	DATE_FIRST_SCHEDULING("date_first_scheduling"),
	FIRST_RESPONSIBLE_CREW_ASSIGN("first_responsible_crew_assign"),
	CODE_DEPOT_DEALER_FIRST_ASSIGN("code_depot_dealer_first_assign"),
	NAME_DEALER_FIRST_ASSIGN("name_dealer_first_assign"),
	DEALER_NAME("dealer_name"),
	WO_TYPE("wo_type"),
	DATE_REJECTION("date_rejection"),
	IBS_CLIENT_CODE("ibs_client_code"),
	IBS_WO_CODE("ibs_wo_code"),
	WO_CSR_SUCCESS("wo_csr_success"),
	NUMBER_DAYS("number_days"),
	SERVICE_TYPE("service_type"),
	SERIAL_IRD("serial_ird"),
	DEALER("dealer"),
	DEALER_ALLOCATION_DATE("dealer_allocation_date"),
	REASON_REJECTION("reason_rejection"),
	JOURNEY("journey"),
	COL_ROL_CODE("col_rol_code"),
	COL_DOC_NUMBER("col_doc_number"),
	COL_NAME("col_name"),
	
	//ReferenceStatusEnum
	IN_CREATION("in_creation"),
	CREATION_STATUS("creation_status"),
	CREATED("created"),
	CREATED_STATUS("created_status"),
	ENVOY("envoy"),
	SENT_STATUS("sent_status"),
	PARTIAL_CONFIRMATION("partial_confirmation"),
	PARTIAL_CONFIRMATION_STATUS("partial_confirmation_status"),
	RECEPTIONED("receptioned"),
	STATUS_RECEPTIONED("status_receptioned"),
	IN_INCONSISTENCY("in_inconsistency"),
	IN_STATUS_INCONSISTENCY("in_status_inconsistency"),
	
	//ClientBasicFileProcessor
	REGISTERS_IMPORTED_FILE("registers_imported_file"),
	NO_ERROR_IDENTIFIED("no_error_identified"),
	
	//getQuantityWarehouseElementsDetailsByFilters
	TYPE_DOCUMENT("type_document"),
	DOCUMENT_NUMBER("document_number"),
    
	TRY_TO_CONTACT("try_to_contact");
	
    private String applicationTextKey;
   
    private final static Logger log = UtilsBusiness.getLog4J(ApplicationTextEnum.class);
	
    ApplicationTextEnum(String applicationTextKey) {
        this.applicationTextKey = applicationTextKey;
    }
    
    /**
    *
    * Metodo: Retorna el texto de la aplicacion.
    * @return String ApplicationTextValue
    * @throws PropertiesException
    */
   public String getApplicationTextValue(){
	   try {
		   return UtilsBusiness.getResourceBundle().getString(this.applicationTextKey);
	   } catch (RuntimeException e) {
		   log.error("El texto a internacionalizar no se pudo encontrar: " + e.getMessage());
		   e.printStackTrace();
		   return "NO PROPERTY FOUND.";
	   }
   }
   //ApplicationTextEnum.SPECIAL_COMMENTS.getApplicationTextValue()
	
}
