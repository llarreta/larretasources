package co.com.directv.sdii.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;


/**
 * WorkOrder entity. @author MyEclipse Persistence Tools
 */

public class WorkOrder implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -4187864903532259553L;
	private Long id;
	private Customer customer;
	private WorkorderStatus workorderStatusByActualStatusId;
	private WoType woTypeByPreviusWoTypeId;
	private WorkorderStatus workorderStatusByPreviusStatusId;
	private WoType woTypeByWoTypeId;
	private PostalCode postalCode;
	private String woCode;
	private String woDescription;
	private String woAction;
	private String buildingCode;
	private String woAddress;
	private Date woProgrammingDate;
	private Date woRealizationDate;
	private String isAppointment;
	private Double workingTime;
	private Long dealerId;
	private Date creationDate;
	private Long userId;
	private Long historyCodeEventId;
	private Country country;
	private Dealer saleDealer;
	private ProcessStatus processStatus;
	private Set<WorkOrderService> workOrderServices = new HashSet<WorkOrderService>(0);
	private Date finalizationDate;
	private Date importDate;
	private String woAddressCode;
	private Ibs6Status ibsActualStatus;
	private Long processSourceId;
	private Date cancelationDate;
	private String agendationExpired;

	private String manageCancelation;

	private CustomerAddresses customerAddress;

    private Long ibsTechnical;
	
    private String building;
    
    private OptimusStatus optimusStatus;
    
    private Date optimusStatusDate;

	private String optimusDeclineCode;
    
    private String optimusDeclineDescription;

	// Constructors

	public CustomerAddresses getCustomerAddress() {
		return customerAddress;
	}



	public void setCustomerAddress(CustomerAddresses customerAddress) {
		this.customerAddress = customerAddress;
	}



	public String getManageCancelation() {
		return manageCancelation;
	}



	public void setManageCancelation(String manageCancelation) {
		this.manageCancelation = manageCancelation;
	}



	/** default constructor */
	public WorkOrder() {
	}
	
	

	public WorkOrder(Long id) {
		super();
		this.id = id;
	}


	/** minimal constructor */
	public WorkOrder(Customer customer,
			WorkorderStatus workorderStatusByActualStatusId,
			WoType woTypeByWoTypeId, PostalCode postalCode, String woCode,
			String woDescription, String isAppointment, Date creationDate,
			Long userId) {
		this.customer = customer;
		this.workorderStatusByActualStatusId = workorderStatusByActualStatusId;
		this.woTypeByWoTypeId = woTypeByWoTypeId;
		this.postalCode = postalCode;
		this.woCode = woCode;
		this.woDescription = woDescription;
		this.isAppointment = isAppointment;
		this.creationDate = creationDate;
		this.userId = userId;
	}
	
	/** full constructor */
	public WorkOrder(Customer customer,
			WorkorderStatus workorderStatusByActualStatusId,
			WoType woTypeByWoTypeId, PostalCode postalCode, String woCode,
			String woDescription, String isAppointment, Date creationDate,
			Long userId,Country country, String woAddressCode) {
		this.customer = customer;
		this.workorderStatusByActualStatusId = workorderStatusByActualStatusId;
		this.woTypeByWoTypeId = woTypeByWoTypeId;
		this.postalCode = postalCode;
		this.woCode = woCode;
		this.woDescription = woDescription;
		this.isAppointment = isAppointment;
		this.creationDate = creationDate;
		this.userId = userId;
		this.country =  country;
		this.woAddressCode = woAddressCode;
	}
	
	/** full constructor */
	public WorkOrder(Customer customer,
			WorkorderStatus workorderStatusByActualStatusId,
			WoType woTypeByWoTypeId, PostalCode postalCode, String woCode,
			String woDescription, String isAppointment, Date creationDate,
			Long userId,Country country,Dealer saleDealer) {
		this.customer = customer;
		this.workorderStatusByActualStatusId = workorderStatusByActualStatusId;
		this.woTypeByWoTypeId = woTypeByWoTypeId;
		this.postalCode = postalCode;
		this.woCode = woCode;
		this.woDescription = woDescription;
		this.isAppointment = isAppointment;
		this.creationDate = creationDate;
		this.userId = userId;
		this.country =  country;
		this.saleDealer = saleDealer;
	}
	
	/** full constructor */
	public WorkOrder(Customer customer,
			WorkorderStatus workorderStatusByActualStatusId,
			WoType woTypeByWoTypeId, PostalCode postalCode, String woCode,
			String woDescription, String isAppointment, Date creationDate,
			Long userId,Country country,Dealer saleDealer,Set<WorkOrderService> workOrderServices) {
		this.customer = customer;
		this.workorderStatusByActualStatusId = workorderStatusByActualStatusId;
		this.woTypeByWoTypeId = woTypeByWoTypeId;
		this.postalCode = postalCode;
		this.woCode = woCode;
		this.woDescription = woDescription;
		this.isAppointment = isAppointment;
		this.creationDate = creationDate;
		this.userId = userId;
		this.country =  country;
		this.saleDealer = saleDealer;
		this.workOrderServices = workOrderServices;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public WorkorderStatus getWorkorderStatusByActualStatusId() {
		return this.workorderStatusByActualStatusId;
	}

	public void setWorkorderStatusByActualStatusId(
			WorkorderStatus workorderStatusByActualStatusId) {
		this.workorderStatusByActualStatusId = workorderStatusByActualStatusId;
	}

	public WoType getWoTypeByPreviusWoTypeId() {
		return this.woTypeByPreviusWoTypeId;
	}

	public void setWoTypeByPreviusWoTypeId(WoType woTypeByPreviusWoTypeId) {
		this.woTypeByPreviusWoTypeId = woTypeByPreviusWoTypeId;
	}

	public WorkorderStatus getWorkorderStatusByPreviusStatusId() {
		return this.workorderStatusByPreviusStatusId;
	}

	public void setWorkorderStatusByPreviusStatusId(
			WorkorderStatus workorderStatusByPreviusStatusId) {
		this.workorderStatusByPreviusStatusId = workorderStatusByPreviusStatusId;
	}

	public WoType getWoTypeByWoTypeId() {
		return this.woTypeByWoTypeId;
	}

	public void setWoTypeByWoTypeId(WoType woTypeByWoTypeId) {
		this.woTypeByWoTypeId = woTypeByWoTypeId;
	}

	public PostalCode getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	public String getWoCode() {
		return this.woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getWoDescription() {
		return this.woDescription;
	}

	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}

	public String getWoAction() {
		return this.woAction;
	}

	public void setWoAction(String woAction) {
		this.woAction = woAction;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getWoProgrammingDate() {
		return this.woProgrammingDate;
	}

	public void setWoProgrammingDate(Date woProgrammingDate) {
		this.woProgrammingDate = woProgrammingDate;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getWoRealizationDate() {
		return this.woRealizationDate;
	}

	public void setWoRealizationDate(Date woRealizationDate) {
		this.woRealizationDate = woRealizationDate;
	}

	public String getIsAppointment() {
		return this.isAppointment;
	}

	public void setIsAppointment(String isAppointment) {
		this.isAppointment = isAppointment;
	}

	public Double getWorkingTime() {
		return this.workingTime;
	}

	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}

	public Long getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date  creationDate) {
		this.creationDate = creationDate;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getHistoryCodeEventId() {
		return this.historyCodeEventId;
	}

	public void setHistoryCodeEventId(Long historyCodeEventId) {
		this.historyCodeEventId = historyCodeEventId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Dealer getSaleDealer() {
		return saleDealer;
	}

	public void setSaleDealer(Dealer saleDealer) {
		this.saleDealer = saleDealer;
	}
	public Set<WorkOrderService> getWorkOrderServices() {
		return this.workOrderServices;
	}

	public void setWorkOrderServices(Set<WorkOrderService> workOrderServices) {
		this.workOrderServices = workOrderServices;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public ProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public String getWoAddress() {
		return woAddress;
	}

	public void setWoAddress(String woAddress) {
		this.woAddress = woAddress;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getFinalizationDate() {
		return finalizationDate;
	}

	public void setFinalizationDate(Date finalizationDate) {
		this.finalizationDate = finalizationDate;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	
	public String getWoAddressCode() {
		return woAddressCode;
	}

	public void setWoAddressCode(String woAddressCode) {
		this.woAddressCode = woAddressCode;
	}

	public Ibs6Status getIbsActualStatus() {
		return ibsActualStatus;
	}

	public void setIbsActualStatus(Ibs6Status ibsActualStatus) {
		this.ibsActualStatus = ibsActualStatus;
	}

	public Long getProcessSourceId() {
		return processSourceId;
	}

	public void setProcessSourceId(Long processSourceId) {
		this.processSourceId = processSourceId;
	}

	public String getAgendationExpired() {
		return agendationExpired;
	}

	public void setAgendationExpired(String agendationExpired) {
		this.agendationExpired = agendationExpired;
	}

	@Override
	public String toString() {
		return "WorkOrder [id=" + id + ", woCode=" + woCode + "]";
	}

	public Date getCancelationDate() {
		return cancelationDate;
	}

	public void setCancelationDate(Date cancelationDate) {
		this.cancelationDate = cancelationDate;
	}
	
    public Long getIbsTechnical() {
        return ibsTechnical;
    }
    
    public void setIbsTechnical(Long value) {
        this.ibsTechnical = value;
    }    
    
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public OptimusStatus getOptimusStatus() {
		return optimusStatus;
	}

	public void setOptimusStatus(OptimusStatus optimusStatus) {
		this.optimusStatus = optimusStatus;
	}

    public Date getOptimusStatusDate() {
		return optimusStatusDate;
	}

	public void setOptimusStatusDate(Date optimusStatusDate) {
		this.optimusStatusDate = optimusStatusDate;
	}

	   
    public String getOptimusDeclineCode() {
		return optimusDeclineCode;
	}


	public void setOptimusDeclineCode(String optimusDeclineCode) {
		this.optimusDeclineCode = optimusDeclineCode;
	}


	public String getOptimusDeclineDescription() {
		return optimusDeclineDescription;
	}


	public void setOptimusDeclineDescription(String optimusDeclineDescription) {
		this.optimusDeclineDescription = optimusDeclineDescription;
	}
}