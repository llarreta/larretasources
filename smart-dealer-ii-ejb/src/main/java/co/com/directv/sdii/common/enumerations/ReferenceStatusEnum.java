package co.com.directv.sdii.common.enumerations;

/**
 * 
 * Clase de tipo Enum encargada de realizar el mapping
 * de codigos para los estados de las remisiones(reference)
 * 
 * Fecha de Creación: 20/03/2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see
 */
public enum ReferenceStatusEnum {

	IN_CREATION( "01",ApplicationTextEnum.IN_CREATION.getApplicationTextValue(),ApplicationTextEnum.CREATION_STATUS.getApplicationTextValue() ),
	CREATED( "02",ApplicationTextEnum.CREATED.getApplicationTextValue(),ApplicationTextEnum.CREATED_STATUS.getApplicationTextValue() ),
	SHIPMENT( "03",ApplicationTextEnum.ENVOY.getApplicationTextValue(),ApplicationTextEnum.SENT_STATUS.getApplicationTextValue() ),
	PARTIAL_CONFORMATION( "04",ApplicationTextEnum.PARTIAL_CONFIRMATION.getApplicationTextValue(),ApplicationTextEnum.PARTIAL_CONFIRMATION_STATUS.getApplicationTextValue() ),
	RECEIVED( "05",ApplicationTextEnum.RECEPTIONED.getApplicationTextValue(),ApplicationTextEnum.STATUS_RECEPTIONED.getApplicationTextValue() ),
	IN_INCONSISTENCY( "06",ApplicationTextEnum.IN_INCONSISTENCY.getApplicationTextValue(),ApplicationTextEnum.IN_STATUS_INCONSISTENCY.getApplicationTextValue() );
	
	private String refStatudCode;	
	private String refStatusName;
	private String refStatusDescripction;
	
	ReferenceStatusEnum(String refStatudCode, String refStatusName,String refStatusDescripction) {
		this.refStatudCode = refStatudCode;
		this.refStatusName = refStatusName;
		this.refStatusDescripction = refStatusDescripction;
	}

	public String getRefStatudCode() {
		return refStatudCode;
	}


	public String getRefStatusName() {
		return refStatusName;
	}


	public String getRefStatusDescripction() {
		return refStatusDescripction;
	}
	
}
