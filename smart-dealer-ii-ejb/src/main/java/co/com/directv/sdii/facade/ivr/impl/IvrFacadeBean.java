package co.com.directv.sdii.facade.ivr.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal;
import co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal;
import co.com.directv.sdii.ws.model.dto.ResponseIVRAntenaActivationDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRBooleanDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRDealerDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRShipOrderDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRValidaDecoSCDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRWoDTO;


@Stateless(name="IvrFacadeBeanLocal",mappedName="ejb/IvrFacadeBeanLocal")
public class IvrFacadeBean implements IvrFacadeBeanLocal {
	
	@EJB(name="IvrBusinessBeanLocal",beanInterface=IvrBusinessBeanLocal.class)
	private IvrBusinessBeanLocal business;


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#getJobCard(java.lang.String, java.lang.String)
	 */
	public ResponseIVRWoDTO getJobCard(String woId, String countryCode){
		return business.getJobCard(woId, countryCode);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#getDepot(java.lang.Long, java.lang.String)
	 */
	public ResponseIVRDealerDTO getDepot(Long depotID, String depotType){
		return business.getDepot(depotID, depotType);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#getShippingOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRShipOrderDTO getShippingOrder(String shipOrderID, String woCode, String countryCode){
		return business.getShippingOrder(shipOrderID, woCode, countryCode);
	}
	


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#setAntenaActivation(java.lang.String, java.lang.String)
	 */
	public ResponseIVRAntenaActivationDTO setAntenaActivation(String orderID, String countryCode){
		return business.setAntenaActivation(orderID,countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#getDecSC(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public ResponseIVRValidaDecoSCDTO getDecSC(String hardwareType,String serialNR,String dealerID, Long crewID){
		return business.getDecSC(hardwareType, serialNR, dealerID, crewID);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#validateSNProductType(java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO validateSNProductType(String productType, String serialNR,Long countryId){
		return business.validateSNProductType(productType, serialNR,countryId);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#setDecSCActivation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setDecSCActivation(String customerID, String orderID, String serialDecNr, String serialSCNr, String countryCode){
		return business.setDecSCActivation(customerID, orderID, serialDecNr, serialSCNr,countryCode);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#setDecSCChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setDecSCChange(String codCliente,
												String codJobCard,
												String codDealer,
												String codDec,
												String codSC,
												String codDecNvo,
												String codSCNvo,
												String countryCode){
		return business.setDecSCChange(codCliente, codJobCard, codDealer, codDec, codSC, codDecNvo, codSCNvo, countryCode);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal#setJobCard(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setJobCard(String customerID,
											String codJobcard,
											String codEstado,
											String reasonCode,
											String countryCode){
		return business.setJobCard(customerID, codJobcard, codEstado, reasonCode, countryCode);
	}

}
