package co.com.directv.sdii.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsula las reglas de generación de reportes anexos a la orden de trabajo
 * @author jjimenezh
 *
 */
public class WorkOrderReportInfo {

	/**
	 * Identificador de la orden de trabajo que se desea generar
	 */
	private Long workOrderId;

	/**
	 * Codigo de la orden de trabajo 
	 */
	private String woCode;	
	
	/**
	 * Identificador de el codigo del pais
	 */
	private Long countryId;	
	
	/**
	 * Tipo de dealer que a cargo de la WO
	 */
	private String dealerType;		
	
	/**
	 * Tipo de servicio asociado a la Wo
	 */
	private String serviceType;
	
	/**
	 * estado o departamento de la WO
	 */
	private String state;		
	
	/**
	 * Ciudad de la Wo
	 */
	private String city;		
	
	/**
	 * catergoria del formato del documento a traer
	 */
	private String formatCategory;		
	
	/**
	 * Determina si se debe generar el contrato con el cliente de esta order de trabajo
	 */
	private boolean generateContract;
	
	/**
	 * Determina si se debe generar el formato de autorización de débito automático
	 */
	private boolean generateAutomaticDebit;
	
	/**
	 * Determina si se debe imprimir el reporte de permanencia y multas
	 */
	private boolean generateHAppendix;
	
	/**
	 * Contiene la información de los ítems que se deben adjuntar al formato de contrato
	 */
	private Map<String, String> contractItems = new HashMap<String, String>();
	
	/**
	 * Contiene la información de los ítems que se deben adjuntar al formato de débito automático
	 */
	private Map<String, String> automaticDebitItems = new HashMap<String, String>();
	
	/**
	 * Contiene la información que se debe adjuntar al formato de anexo de permanencia y multas
	 */
	private Map<String, String> hAppendixItems = new HashMap<String, String>();
	
	/**
	 * Identificador de la orden de trabajo que se desea generar
	 */
	public Long getWorkOrderId() {
		return workOrderId;
	}
	
	/**
	 * Identificador de la orden de trabajo que se desea generar
	 */
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	
	/**
	 * Determina si se debe generar el contrato con el cliente de esta order de trabajo
	 */
	public boolean isGenerateContract() {
		return generateContract;
	}
	
	/**
	 * Determina si se debe generar el contrato con el cliente de esta order de trabajo
	 */
	public void setGenerateContract(boolean generateContract) {
		this.generateContract = generateContract;
	}
	
	/**
	 * Determina si se debe generar el formato de autorización de débito automático
	 */
	public boolean isGenerateAutomaticDebit() {
		return generateAutomaticDebit;
	}
	
	/**
	 * Determina si se debe generar el formato de autorización de débito automático
	 */
	public void setGenerateAutomaticDebit(boolean generateAutomaticDebit) {
		this.generateAutomaticDebit = generateAutomaticDebit;
	}
	
	/**
	 * Determina si se debe imprimir el reporte de permanencia y multas
	 */
	public boolean isGenerateHAppendix() {
		return generateHAppendix;
	}
	
	/**
	 * Determina si se debe imprimir el reporte de permanencia y multas
	 */
	public void setGenerateHAppendix(boolean generateHAppendix) {
		this.generateHAppendix = generateHAppendix;
	}
	
	/**
	 * Obtiene el mapa de valores para los ítems del contrato
	 * @return mapa con los valores de items para llenar el reporte
	 * de contrato
	 */
	public Map<String, String> getContractItems() {
		return contractItems;
	}
	
	/**
	 * Asigna los items con los valores para el reporte de contrato
	 * @param contractItems items con los valores para el reporte de 
	 * contrato
	 */
	public void setContractItems(Map<String, String> contractItems) {
		this.contractItems = contractItems;
	}
	
	/**
	 * obtiene el mapa con los valores de los items para el reporte
	 * de débito automático
	 * @return mapa con los valores para el reporte de débito automático
	 */
	public Map<String, String> getAutomaticDebitItems() {
		return automaticDebitItems;
	}
	
	/**
	 * Asigna los ítems para el reporte de débito automático
	 * @param automaticDebitItems items para el reporte de débito automático
	 */
	public void setAutomaticDebitItems(Map<String, String> automaticDebitItems) {
		this.automaticDebitItems = automaticDebitItems;
	}
	
	/**
	 * obtiene los items para la generación del reporte anexo h con
	 * las condiciones del contrato y las sanciones.
	 * @return mapa con los ítems para la generación del reporte
	 * anexo h
	 */
	public Map<String, String> getHAppendixItems() {
		return hAppendixItems;
	}
	
	/**
	 * asigna los ítems para la generación del reporte anexo h
	 * @param hAppendixItems ítems para la generación del reporte anexo h
	 */
	public void setHAppendixItems(Map<String, String> hAppendixItems) {
		this.hAppendixItems = hAppendixItems;
	}
	
	/** permite saber cual es el codigo del pais de lla work order a procesar
	 * @return
	 */
	public Long getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/** Permite obtener el codigo del tipo de dealer tratando la wo
	 * @return
	 */
	public String getDealerType() {
		return dealerType;
	}

	/**
	 * @param dealerType
	 */
	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	/** Permite obtener el tipo servicio asociado a la wo
	 * @return
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/** permite obtener el estado o departamento donde se esta tratando la WO
	 * @return
	 */
	public String getState() {
		return state;
	}

	/** 
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/** permite obtener ciudad donde se esta tratando la WO
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/** permite obtener la categoria del formato que se esta trayendo
	 * @return
	 */
	public String getFormatCategory() {
		return formatCategory;
	}

	public void setFormatCategory(String formatCategory) {
		this.formatCategory = formatCategory;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

}
