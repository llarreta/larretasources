package co.com.directv.sdii.assign.kpi.dto;

import java.io.Serializable;

/**
 * Encapsula la informacicÃ³n de resultado del cÃ¡lculo de un indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class ResponseSearchKpiResultsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910879270487616531L;
	
	/**
	 * Identificador del dealer
	 */
	private Long dealerId;
	/**
	 * Codigo del dealer
	 */
	private Long dealerCode;
	/**
	 * Codigo depot del dealer
	 */
	private String depotCode;
	/**
	 * Nombre del dealer
	 */
	private String dealerName;
	/**
	 * Codigo del KPI
	 */
	private String codeKpi;
	/**
	 * mes
	 */
	private String mounth;
	/**
	 * año
	 */
	private String year;
	/**
	 * valor promedio del kpi
	 */
	private Double valueKpi;
	/**
	 * valor de la meta
	 */
	private Double valueGoal;
	
	public ResponseSearchKpiResultsDTO() {
		super();
	}
	public ResponseSearchKpiResultsDTO(Long dealerId, Long dealerCode,
			String depotCode, String dealerName, String codeKpi, String mounth,
			String year, Double valueKpi,Double valueGoal) {
		super();
		this.dealerId = dealerId;
		this.dealerCode = dealerCode;
		this.depotCode = depotCode;
		this.dealerName = dealerName;
		this.codeKpi = codeKpi;
		this.mounth = mounth;
		this.year = year;
		this.valueKpi = valueKpi;
		this.valueGoal = valueGoal;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getCodeKpi() {
		return codeKpi;
	}
	public void setCodeKpi(String codeKpi) {
		this.codeKpi = codeKpi;
	}
	public String getMounth() {
		return mounth;
	}
	public void setMounth(String mounth) {
		this.mounth = mounth;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Double getValueKpi() {
		return valueKpi;
	}
	public void setValueKpi(Double valueKpi) {
		this.valueKpi = valueKpi;
	}
	
	public Double getValueGoal() {
		return valueGoal;
	}
	public void setValueGoal(Double valueGoal) {
		this.valueGoal = valueGoal;
	}
	
	@Override
	public String toString() {
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("dealerId="+ this.dealerId);
		stringBuffer.append("dealerCode=" + this.dealerCode);
		stringBuffer.append("depotCode=" + this.depotCode);
		stringBuffer.append("dealerName=" + this.dealerName);
		stringBuffer.append("codeKpi=" + this.codeKpi);
		stringBuffer.append("mounth=" + this.mounth);
		stringBuffer.append("year=" + this.year);
		stringBuffer.append("valueKpi=" + this.valueKpi);
		stringBuffer.append("valueGoal=" + this.valueGoal);
		
		return stringBuffer.toString();
	}
			
}