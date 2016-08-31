/**
 * Creado 18/02/2011 15:58:18
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * Encapsula la información de WorkOrders de un cliente 
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class EditCustomerWorkOrderDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3976659975583641594L;
	
	private String woCode;
	private String countryCode;
	private Long dealerCode;
	private String ibsTechnicalDocument;
	
	private Long ibsTechnical;
	
	private String reasonCode;
	private Date agendationDate;
	private String ibs6StateCode;
	private String woDescription;

	public Long getIbsTechnical() {
		return ibsTechnical;
	}

	public void setIbsTechnical(Long ibsTechnical) {
		this.ibsTechnical = ibsTechnical;
	}

	public String getIbsTechnicalDocument() {
		return ibsTechnicalDocument;
	}

	public void setIbsTechnicalDocument(String ibsTechnicalDocument) {
		this.ibsTechnicalDocument = ibsTechnicalDocument;
	}

	/**
	 * Constructor: Construye el objeto EditCustomerWorkOrderDTO los datos obligatorios son woCode,countryCode,reasonCode
	 * @param woCode
	 * @param countryCode
	 * @param dealerCode
	 * @param ibsTechnical
	 * @param reasonCode
	 * @param agendationDate
	 * @author
	 */
	public EditCustomerWorkOrderDTO(String woCode, 
			                        String countryCode,
			                        Long dealerCode, 
			                        String ibsTechnicalDocument, 
			                        Long ibsTechnical,
			                        String reasonCode,
			                        Date agendationDate
			                        ) {
		super();
		this.woCode = woCode;
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.ibsTechnicalDocument = ibsTechnicalDocument;
		this.reasonCode = reasonCode;
		this.agendationDate = agendationDate;
		this.ibsTechnical=ibsTechnical;
	}
	
	public EditCustomerWorkOrderDTO(String woCode, String countryCode,
			Long dealerCode, Long ibsTechnical,
			String reasonCode, Date agendationDate) {
		super();
		this.woCode = woCode;
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.reasonCode = reasonCode;
		this.agendationDate = agendationDate;
		this.ibsTechnical = ibsTechnical;
	}
	
	/**
	 * Constructor: Construye el objeto EditCustomerWorkOrderDTO los datos obligatorios son woCode,countryCode,reasonCode
	 * @param woCode
	 * @param countryCode
	 * @param dealerCode
	 * @param ibsTechnical
	 * @param reasonCode
	 * @param agendationDate
	 * @author
	 */
	public EditCustomerWorkOrderDTO(String woCode, 
			                        String countryCode,
			                        Long dealerCode, 
			                        String ibsTechnicalDocument, 
			                        String reasonCode,
			                        Date agendationDate,
			                        String woDescription) {
		super();
		this.woCode = woCode;
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.ibsTechnicalDocument = ibsTechnicalDocument;
		this.reasonCode = reasonCode;
		this.agendationDate = agendationDate;
		this.woDescription = woDescription;
	}
	
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getAgendationDate() {
		return agendationDate;
	}
	public void setAgendationDate(Date agendationDate) {
		this.agendationDate = agendationDate;
	}
	public String getIbs6StateCode() {
		return ibs6StateCode;
	}
	public void setIbs6StateCode(String ibs6StateCode) {
		this.ibs6StateCode = ibs6StateCode;
	}

	public String getWoDescription() {
		return woDescription;
	}

	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}
	
}
