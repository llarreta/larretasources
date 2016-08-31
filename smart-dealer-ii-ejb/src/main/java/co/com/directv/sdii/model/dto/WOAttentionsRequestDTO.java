/**
 * 
 */
package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;


/**
 * Clase que encapsula los datos de las peticiones de la
 * atención de una WorkOrder.
 * 
 * @author jvelezmu
 *
 */
public class WOAttentionsRequestDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167853436045094992L;

	private WorkOrderVO workorderVo;
	private List<WorkOrderServiceVO> workorderServices;
	private String comments;
	private Boolean workOrderFinished;
	private Long dealerId;
	private TrayWOManagmentDTO principalWorkOrder;
	private TrayWOManagmentDTO associatedWorkOrder;
	private Date finalizationDate;
	private Double workingTime;
	private Long dealerCode;
	private WorkorderReason woReasonFinalization;
	private String ibsTechnicalDocument;
	
	private Long ibsTechnical;

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
	public WorkOrderVO getWorkorderVo() {
		return workorderVo;
	}
	public void setWorkorderVo(WorkOrderVO workorderVo) {
		this.workorderVo = workorderVo;
	}
	public List<WorkOrderServiceVO> getWorkorderServices() {
		return workorderServices;
	}
	public void setWorkorderServices(List<WorkOrderServiceVO> workorderServices) {
		this.workorderServices = workorderServices;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Boolean getWorkOrderFinished() {
		return workOrderFinished;
	}
	public void setWorkOrderFinished(Boolean workOrderFinished) {
		this.workOrderFinished = workOrderFinished;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public TrayWOManagmentDTO getPrincipalWorkOrder() {
		return principalWorkOrder;
	}
	public void setPrincipalWorkOrder(TrayWOManagmentDTO principalWorkOrder) {
		this.principalWorkOrder = principalWorkOrder;
	}
	public TrayWOManagmentDTO getAssociatedWorkOrder() {
		return associatedWorkOrder;
	}
	public void setAssociatedWorkOrder(TrayWOManagmentDTO associatedWorkOrder) {
		this.associatedWorkOrder = associatedWorkOrder;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getFinalizationDate() {
		return finalizationDate;
	}
	public void setFinalizationDate(Date finalizationDate) {
		this.finalizationDate = finalizationDate;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Double getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}
	public WorkorderReason getWoReasonFinalization() {
		return woReasonFinalization;
	}
	public void setWoReasonFinalization(WorkorderReason woReasonFinalization) {
		this.woReasonFinalization = woReasonFinalization;
	}

}
