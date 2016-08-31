/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Reference;

/**
 * Objeto que encapsula la información de un Reference
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Reference    
 */
public class ReferenceVO extends Reference implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1699019743595632059L;
	private UserVO creationUser;
	private UserVO senderUser;
	private List<DeliveryVO> deliveries;
	private List<SpecialCommentVO> specialComments;
	private List<ReferenceElementItemVO> referenceElementItemNotSerialize;
	private List<ReferenceElementItemVO> referenceElementItemSerialize;
	private List<ReferenceModificationVO> referenceModifications;
	private List<RefInconsistencyVO> refInconsistencyVO;
	//CU32 - Datos de consulta de remisiones
	private String creationUserName;
	private String shippmentUserName;
	private String whSource;
	private String whTarget;
	private Long dealerIdWHSource;
	private Long dealerIdWHTarget;
	
	private RefRecieveDataVO refRecieveData;
	
	public UserVO getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(UserVO creationUser) {
		this.creationUser = creationUser;
	}
	public UserVO getSenderUser() {
		return senderUser;
	}
	public void setSenderUser(UserVO senderUser) {
		this.senderUser = senderUser;
	}
	public List<DeliveryVO> getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(List<DeliveryVO> deliveries) {
		this.deliveries = deliveries;
	}
	public List<SpecialCommentVO> getSpecialComments() {
		return specialComments;
	}
	public void setSpecialComments(List<SpecialCommentVO> specialComments) {
		this.specialComments = specialComments;
	}
	public List<ReferenceElementItemVO> getReferenceElementItemNotSerialize() {
		return referenceElementItemNotSerialize;
	}
	public void setReferenceElementItemNotSerialize(
			List<ReferenceElementItemVO> referenceElementItemNotSerialize) {
		this.referenceElementItemNotSerialize = referenceElementItemNotSerialize;
	}
	public List<ReferenceElementItemVO> getReferenceElementItemSerialize() {
		return referenceElementItemSerialize;
	}
	public void setReferenceElementItemSerialize(
			List<ReferenceElementItemVO> referenceElementItemSerialize) {
		this.referenceElementItemSerialize = referenceElementItemSerialize;
	}
	public List<ReferenceModificationVO> getReferenceModifications() {
		return referenceModifications;
	}
	public void setReferenceModifications(
			List<ReferenceModificationVO> referenceModifications) {
		this.referenceModifications = referenceModifications;
	}
	public List<RefInconsistencyVO> getRefInconsistencyVO() {
		return refInconsistencyVO;
	}
	public void setRefInconsistencyVO(List<RefInconsistencyVO> refInconsistencyVO) {
		this.refInconsistencyVO = refInconsistencyVO;
	}
	public String getCreationUserName() {
		return creationUserName;
	}
	public void setCreationUserName(String creationUserName) {
		this.creationUserName = creationUserName;
	}
	public String getWhSource() {
		return whSource;
	}
	public void setWhSource(String whSource) {
		this.whSource = whSource;
	}
	public String getWhTarget() {
		return whTarget;
	}
	public void setWhTarget(String whTarget) {
		this.whTarget = whTarget;
	}
	public String getShippmentUserName() {
		return shippmentUserName;
	}
	public void setShippmentUserName(String shippmentUserName) {
		this.shippmentUserName = shippmentUserName;
	}
	public RefRecieveDataVO getRefRecieveData() {
		return refRecieveData;
	}
	public void setRefRecieveData(RefRecieveDataVO refRecieveData) {
		this.refRecieveData = refRecieveData;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setDealerIdWHSource(Long dealerIdWHSource) {
		this.dealerIdWHSource = dealerIdWHSource;
	}
	public Long getDealerIdWHSource() {
		return dealerIdWHSource;
	}
	public void setDealerIdWHTarget(Long dealerIdWHTarget) {
		this.dealerIdWHTarget = dealerIdWHTarget;
	}
	public Long getDealerIdWHTarget() {
		return dealerIdWHTarget;
	}
	
	
	
}
