package co.com.directv.sdii.common.enumerations;

/**
 * 
 * Enumeracion en donde se almacenan las posiciones de los valores de la consulta
 * de los datos requeridos en la bandeja de workorders 
 * 
 * Fecha de Creación: 8/02/2012
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public enum TrayQueryEnum {
	
		WORK_ORDER_ID(0),
		WORK_ORDER_CODE(1),
		WORK_ORDER_DESCRIPTION(2),
		WORK_ORDER_CREATION_DATE(3),
		WORK_ORDER_STATUS_ID(4),
		WORK_ORDER_STATUS_CODE(5),
		WORK_ORDER_STATUS_NAME(6),
		WORK_ORDER_REASON_ID(7),
		WORK_ORDER_REASON_CODE(8),
		WORK_ORDER_REASON_NAME(9),
		POSTAL_CODE_ID(10),
		POSTAL_CODE_CODE(11),
		POSTAL_CODE_NAME(12),
		CITY_NAME(13),
		STATE_NAME(14),
		CUSTOMER_CODE(15),
		CUSTOMER_FIRST_NAME(16),
		CUSTOMER_LAST_NAME(17),
		CUSTOMER_ADDRESS(18),
		CUSTOMER_DOCUMENT_NUMBER(19),
		CUSTOMER_CLASS_TYPE_ID(20),
		CUSTOMER_TYPE_CODE(21),
		CUSTOMER_TYPE_NAME(22),
		DOCUMENT_TYPE_ID(23),
		DOCUMENT_TYPE_CODE(24),
		DOCUMENT_TYPE_NAME(25),
		CUSTOMER_ID(26),
		AGENDATION_DATE(27),
		SERVICE_HOUR_ID(28),
		SERVICE_HOUR_NAME(29),
		CREW_ID(30),
		RESPONSABLE_NAME(31),
		RESPONSABLE_DOCUMENT_NUMBER(32),
		PROGRAM_ASSIGNMENT(33),
		WORK_ORDER_DAYS(34),
		WORK_ORDER_TYPE_ID(35),
		WORK_ORDER_TYPE_CODE(36),
		WORK_ORDER_TYPE_NAME(37),
		DEALER_ID(38),
		DEALER_CODE(39),
		DEALER_NAME(40),
		PROCESS_STATUS_ID(41),
		PROCESS_STATUS_CODE(42),
		WORK_ORDER_PROCESS_SOURCE_DESCRIPTION(43),
		WORK_ORDER_AGENDATION_EXPIRED(44),
		TEC_CODE(45),
		TEC_NAME(46),
		SHIPPING_ORDER_CODE(47),
		IS_CUSTOMER_MIGRATED(48),
		ZONE_TYPE_NAME(49),
		WORK_ORDER_MARKS(50),

		CITY_NAME_WO(51),
		STATE_NAME_WO(52),
		POSTAL_CODE_ID_WO(53),
		POSTAL_CODE_CODE_WO(54),
		POSTAL_CODE_NAME_WO(55),
		
		/*codigos correspondientes al cambio de nexus*/
		CUSTOMER_CLASS_ID(56),
		CUSTOMER_TYPE_ID(57),
		CUSTOMER_CLASS_NAME(58),
		CUSTOMER_CLASS_CODE(59),
		
		CUSTOMER_CATEGORY_ID(60),
		CUSTOMER_CATEGORY_CODE(61),
		CUSTOMER_CATEGORY_NAME(62),
		
		OPTIMUS_STATUS_CODE(63),
		OPTIMUS_STATUS_DESCRIPTION(64),
		OPTIMUS_DECLINE_CODE(65),
		OPTIMUS_DECLINE_DESCRIPTION(66),
	;
	
	private int rowNum;
	
	private TrayQueryEnum(int rowNum){
		this.rowNum = rowNum;
	}

	public int getRowNum() {
		return rowNum;
	}
	
	
}