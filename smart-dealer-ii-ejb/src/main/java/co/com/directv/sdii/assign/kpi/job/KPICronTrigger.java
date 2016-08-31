package co.com.directv.sdii.assign.kpi.job;

/**
 * se encarga de encapsular una expresión de cron que permite la especificación de
 * una frecuencia de ejecución de un Job.
 * 
 * Lo ideal es que tenga los atributos necesarios para identificar el país, super
 * categoría de servicio, indicador y  tipo de zona a la que pertenece
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPICronTrigger {

	/**
	 * código del país
	 */
	private String countryCode;
	/**
	 * código de la super categoría de servicio
	 */
	private String serviceSuperCategoryCode;
	/**
	 * código del indicador
	 */
	private String kpiCode;

	public KPICronTrigger(){

	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getServiceSuperCategoryCode() {
		return serviceSuperCategoryCode;
	}

	public void setServiceSuperCategoryCode(String serviceSuperCategoryCode) {
		this.serviceSuperCategoryCode = serviceSuperCategoryCode;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

}