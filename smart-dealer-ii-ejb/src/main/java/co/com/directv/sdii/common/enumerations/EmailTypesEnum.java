package co.com.directv.sdii.common.enumerations;

/**
 * 
 * Clase de tipo Enum encargada de realizar el mapping
 * de codigos para los tipos de emails
 * 
 * Fecha de Creación: 10/08/2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see
 */
public enum EmailTypesEnum {

	SHIPMENT_OF_REGISTER_IMPORT("01","Envío de Registro de importación","Envío de Registro de importación"),
    INFORMATION_REGISTRY_HAS_BEEN_RECEIVED("02","Registro de Información ha sido recepcionado","Registro de Información ha sido recepcionado"),
	INCONSISTENCY_IN_IMPORT_REGISTRY("03","Inconsistencia en Registro de Importación","Inconsistencia en Registro de Importación"),
	QUALITY_CONTROL_CARRIED_OUT("04","Control de Calidad Realizado","Control de Calidad Realizado"),
	REFERENCE_CREATION("05","Creación de Remisión","Creación de Remisión"),
	REFERENCE_SHIPMENT("06","Envío de una Remisión","Envío de una Remisión"),
	REFERENCES_ELEMENTS_HAS_BEEN_RECEIVED("07","Recibido de los elementos de una Remisión","Recibido de los elementos de una Remisión"),
	REFERENCE_INCONSISTENCY("08","Inconsistencias en Remisión","Inconsistencias en Remisión"),
	MODIFICATIONS_TO_REFERENCE("09","Modificaciones a Remisión","Modificaciones a Remisión"),
	MODIFICATIONS_TO_IMPORT_REFERENCE("10","Modificaciones al Registro de Importación","Modificaciones al Registro de Importación"),
	REFERENCES_ELEMENTS_HAS_BEEN_PARTIALLY_SENDED("11","Recibido parcial de los elementos de una Remisión","Recibido parcial de los elementos de una Remisión"),
	IMPORT_LOG_HAS_BEEN_RECEIVED("12","Registro de Importación ha sido recepcionado","Registro de Importación ha sido recepcionado"),
	QUALITY_CONTROL_REALIZED("13","Control de calidad realizado","Control de calidad realizado"),
	IMPORT_LOG_HAS_BEEN_MODIFIED("14","Registro de Importación ha sido modificado","Registro de Importación ha sido modificado"),
	REGISTER_IMPORT_LOG_HAS_BEEN_CONFIRMED("15","Registro de Importación Confirmado","Registro de Importación Confirmado"),
	REGISTER_IMPORT_LOG_INCONSISTENCY("16","Registro de Importacion en Inconsistencia","Registro de Importacion en Inconsistencia"),
	REGISTER_IMPORT_LOG_HAS_BEEN_CREATED("17","Registro de Importación ha sido creado","Registro de Importación ha sido creado"),
	ALARM_INSUFFICIENT_STOCK_ITEMS("18","Alarma - Stock Insuficiente de Elementos","Alarma - Stock Insuficiente de Elementos"),
	WO_LOAD_REPORT("19","Reporte - Cargue de Work Orders","Reporte - Cargue de Work Orders"),
	PROCESS_ALLOCATOR_REPORT("30","Reporte - Proceso de asignador","Reporte - Proceso de asignador"),
	WO_MANAGEMENT("20","Reporte - Gestión de Work Orders","Reporte - Gestión de Work Orders"),
	BACKLOG_LIMIT("21","Límite de BackLog Superado","Límite de BackLog Superado"),
	REGISTER_QA_NOASERIALIZED_ELEMENTS("22", "Registrar Control Calidad de elementos no serializados", "Registrar Control Calidad de elementos no serializados"),	
	REGISTER_QA_SERIALIZED_ELEMENTS("23", "Registrar Control Calidad de elementos serializados", "Registrar Control Calidad de elementos serializados"),
	REFERENCE_INCONSISTENCY_CLOSED("24","Cierre de inconsistencia de Remisión","Cierre de inconsistencia de Remisión"),
	REGISTER_IMPORT_LOG_HAS_BEEN_SENT("25","Registro de Importación enviado","Registro de Importación ha sido enviado"),
	REGISTER_IMPORT_LOG_HAS_BEEN_CREATED_AND_SENT("26","Registro de Importación ha sido creado y enviado","Registro de Importación ha sido creado y enviado"),
	REGISTER_IMPORT_LOG_HAS_BEEN_MODIFIED_AND_SENT("27","Registro de Importación ha sido modificado y enviado","Registro de Importación ha sido modificado y enviado"),
	IMPORT_LOG_INCONSISTENCIES_UPDATED("28", "Las inconsistencias de un registro de importación han sido actualizadas", "Las inconsistencias de un registro de importación han sido actualizadas"),
	IMPORT_LOG_INCONSISTENCY_CLOSED("29", "Inconsistencia de un registro de importación han sido cerrada", "Inconsistencia de un registro de importación han sido cerrada");
	
	
	
	private String emailTypecode;
	private String emailTypeName;
	private String description;
	
	private EmailTypesEnum(String emailTypecode, String emailTypeName,
			String description) {
		this.emailTypecode = emailTypecode;
		this.emailTypeName = emailTypeName;
		this.description = description;
	}

	public String getEmailTypecode() {
		return emailTypecode;
	}

	public String getEmailTypeName() {
		return emailTypeName;
	}

	public String getDescription() {
		return description;
	}
	
}
