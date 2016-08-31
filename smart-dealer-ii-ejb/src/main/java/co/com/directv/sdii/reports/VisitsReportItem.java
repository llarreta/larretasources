package co.com.directv.sdii.reports;

/**
 * Modela un registro de un reporte plantilla de visitas todos los campos son string para formateo en el reporte
 * @author Leonardo Cardozo
 *
 */
public class VisitsReportItem {
	
	private String jobCard;
	private String client;
	private String name;
	private String address;
	private String meridian;
	private String serviceType;
	private String arriveHour;
	private String departureHour;
	private String end;
	private String comments;
	
	public VisitsReportItem() {}

	public String getJobCard() {
		return jobCard;
	}

	public void setJobCard(String jobCard) {
		this.jobCard = jobCard;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return AM | PM
	 */
	public String getMeridian() {
		return meridian;
	}
	/**
	 * 
	 * @param meridian AM | PM
	 */
	public void setMeridian(String meridian) {
		this.meridian = meridian;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * Hora de llegada
	 * @return String
	 */
	public String getArriveHour() {
		return arriveHour;
	}

	public void setArriveHour(String arriveHour) {
		this.arriveHour = arriveHour;
	}
	/**
	 * Hora de salida
	 * @return String
	 */
	public String getDepartureHour() {
		return departureHour;
	}

	public void setDepartureHour(String departureHour) {
		this.departureHour = departureHour;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
