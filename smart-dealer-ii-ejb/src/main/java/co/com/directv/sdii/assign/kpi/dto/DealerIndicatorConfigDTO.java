package co.com.directv.sdii.assign.kpi.dto;

/**
 * Encapsula la informaci�n de configuraci�n de un indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerIndicatorConfigDTO extends BaseDealerIndicatorDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777645371378211910L;
	/**
	 * determina si el indicador est� activo o n� para la super categor�a de servicio
	 * consultada
	 */
	private String isActive;
	/**
	 * meta del indicador
	 */
	private Double goal;
	/**
	 * ponderación del indicador para la super categor�a de servicio
	 */
	private Double weighting;
	/**
	 * código del tipo de cálculo 'OL' para en línea y 'FR' con cierta frecuencia
	 */
	private String calculateTypeCode;
	/**
	 * cadena con una expresión CRON que define la frecuencia de ejecuci�n del
	 * indicador, aplica solo si el c�digo del tipo de c�lculo es FR
	 */
	private String calculateFrecuency;

	public DealerIndicatorConfigDTO(){

	}

	public DealerIndicatorConfigDTO(Long countryId,	Long serviceSuperCategoryId, int dayCount, Long zoneTypeId,
			Long indicatorId, Long indicatorTypeId, String isActive, Double goal,
			Double weighting, String calculateTypeCode,
			String calculateFrecuency) {
		super(countryId, serviceSuperCategoryId, dayCount, zoneTypeId, indicatorId,
				indicatorTypeId);
		this.isActive = isActive;
		this.goal = goal;
		this.weighting = weighting;
		this.calculateTypeCode = calculateTypeCode;
		this.calculateFrecuency = calculateFrecuency;
	}

	public DealerIndicatorConfigDTO(String isActive, Double goal,
			Double weighting, String calculateTypeCode,
			String calculateFrecuency) {
		super();
		this.isActive = isActive;
		this.goal = goal;
		this.weighting = weighting;
		this.calculateTypeCode = calculateTypeCode;
		this.calculateFrecuency = calculateFrecuency;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}



	public Double getGoal() {
		return goal;
	}



	public void setGoal(Double goal) {
		this.goal = goal;
	}



	public Double getWeighting() {
		return weighting;
	}



	public void setWeighting(Double weighting) {
		this.weighting = weighting;
	}



	public String getCalculateTypeCode() {
		return calculateTypeCode;
	}



	public void setCalculateTypeCode(String calculateTypeCode) {
		this.calculateTypeCode = calculateTypeCode;
	}



	public String getCalculateFrecuency() {
		return calculateFrecuency;
	}



	public void setCalculateFrecuency(String calculateFrecuency) {
		this.calculateFrecuency = calculateFrecuency;
	}



	public void finalize() throws Throwable {
		super.finalize();
	}
}