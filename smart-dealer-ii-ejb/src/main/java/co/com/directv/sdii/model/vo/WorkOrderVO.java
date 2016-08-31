package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Vehicle;
import co.com.directv.sdii.model.pojo.WorkOrder;

/**
 * 
 * WorkOrder Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrder
 */
public class WorkOrderVO extends WorkOrder implements Serializable {

	private static final long serialVersionUID = -4053334118232788289L;

	private Date serviceHour;
	private Date lastService;
	private String dealerInstaller;
	private String tecnicianName;
	private Vehicle serviceVehicle;
	private String sellerName;
	private String sellerCode;
	private Crew crew;
	private List<String> commentsIBS;
	private List<ShippingOrderVO> shippingOrders;
	private List<ShippingOrderElementVO> shippingOrderElements;
	private WoAssignmentVO workOrderAssignment;
	private boolean tieneServiceHour;
	private boolean tieneLastService;
	private ServiceHourVO jornada;
	
	public WoAssignmentVO getWorkOrderAssignment() {
		return workOrderAssignment;
	}
	
	public void setWorkOrderAssignment(WoAssignmentVO workOrderAssignment) {
		this.workOrderAssignment = workOrderAssignment;
	}
	
	
	public Crew getCrew() {
		return crew;
	}
	public void setCrew(Crew crew) {
		this.crew = crew;
	}
	
	public void setServiceHour(Date serviceHour) {
		this.serviceHour = serviceHour;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getServiceHour() {
		return serviceHour;
	}
	public void setLastService(Date lastService) {
		this.lastService = lastService;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getLastService() {
		return lastService;
	}
	public void setDealerInstaller(String dealerInstaller) {
		this.dealerInstaller = dealerInstaller;
	}
	public String getDealerInstaller() {
		return dealerInstaller;
	}
	public void setTecnicianName(String tecnicianName) {
		this.tecnicianName = tecnicianName;
	}
	public String getTecnicianName() {
		return tecnicianName;
	}
	public void setServiceVehicle(Vehicle serviceVehicle) {
		this.serviceVehicle = serviceVehicle;
	}
	public Vehicle getServiceVehicle() {
		return serviceVehicle;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerName() {
		return sellerName;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public void setCommentsIBS(List<String> commentsIBS) {
		this.commentsIBS = commentsIBS;
	}
	public List<String> getCommentsIBS() {
		return commentsIBS;
	}
	public List<ShippingOrderVO> getShippingOrders() {
		return shippingOrders;
	}
	public void setShippingOrders(List<ShippingOrderVO> shippingOrders) {
		this.shippingOrders = shippingOrders;
	}

	public boolean isTieneServiceHour() {
		return tieneServiceHour;
	}

	public void setTieneServiceHour(boolean tieneServiceHour) {
		this.tieneServiceHour = tieneServiceHour;
	}

	public boolean isTieneLastService() {
		return tieneLastService;
	}

	public void setTieneLastService(boolean tieneLastService) {
		this.tieneLastService = tieneLastService;
	}

	public List<ShippingOrderElementVO> getShippingOrderElements() {
		return shippingOrderElements;
	}

	public void setShippingOrderElements(
			List<ShippingOrderElementVO> shippingOrderElements) {
		this.shippingOrderElements = shippingOrderElements;
	}

	public ServiceHourVO getJornada() {
		return jornada;
	}

	public void setJornada(ServiceHourVO jornada) {
		this.jornada = jornada;
	}
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		buff.append(getWoCode() == null ? "" : getWoCode());
		buff.append("|");
		buff.append(getWorkorderStatusByActualStatusId() == null ? "" : getWorkorderStatusByActualStatusId().getWoStateCode());
		buff.append("|");
		buff.append(getWoProgrammingDate() == null ? "" : df.format(getWoProgrammingDate()));
		buff.append("|");
		buff.append(getCustomer() == null ? "" : getCustomer().getCustomerCode());
		buff.append("|");
		buff.append(getCustomer() == null ? "" : getCustomer().getFirstName());
		buff.append("|");
		buff.append(getCustomer() == null ? "" : getCustomer().getLastName());
		buff.append("|");
		buff.append(getWoAddress() == null ? "" : getWoAddress());
		buff.append("|");
		buff.append(getCrew() == null ? "" : getCrew().getId());
		return buff.toString();
	}


}
