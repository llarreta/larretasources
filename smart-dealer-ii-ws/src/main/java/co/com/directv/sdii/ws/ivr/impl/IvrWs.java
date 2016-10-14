package co.com.directv.sdii.ws.ivr.impl;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.facade.ivr.IvrFacadeBeanLocal;
import co.com.directv.sdii.ws.ivr.IIvrWs;
import co.com.directv.sdii.ws.model.dto.ResponseIVRAntenaActivationDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRBooleanDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRDealerDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRShipOrderDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRValidaDecoSCDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRWoDTO;
/**
 * 
 * Clase creada para exponer servicios web que ser�n consumidos por IVR
 * 
 * Fecha de Creación: 4/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM(threshold=3072)
@WebService(serviceName="IvrWs",
		endpointInterface="co.com.directv.sdii.ws.ivr.IIvrWs",
		targetNamespace="http://ivr.ws.sdii.directv.com.co/",
		portName="IvrWsPort")	
@Stateless()
public class IvrWs implements IIvrWs {
	
	
	@EJB
	private IvrFacadeBeanLocal ivrFacadeBeanLocal;
			

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#getWorkOrderInfo(java.lang.String, java.lang.String)
	 */
	public ResponseIVRWoDTO getWorkOrderInfo(@WebParam(name = "woId")String woId, 
									   @WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.getJobCard(woId, countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#getDealerInfo(java.lang.Long, java.lang.String)
	 */
	public ResponseIVRDealerDTO getDealerInfo(@WebParam(name = "depotID")Long depotID, 
										 @WebParam(name = "depotType")String depotType){
		return null;//ivrFacadeBeanLocal.getDepot(depotID, depotType);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#getShippingOrderInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRShipOrderDTO getShippingOrderInfo(@WebParam(name = "shipOrderID")String shipOrderID,
													@WebParam(name = "woCode")String woCode, 
													@WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.getShippingOrder(shipOrderID, woCode, countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setAntennaActivation(java.lang.String, java.lang.String)
	 */
	public ResponseIVRAntenaActivationDTO setAntennaActivation(@WebParam(name = "orderID")String orderID,
															  @WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setAntenaActivation(orderID,countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#getDecoSmartCardDealerWareHouse(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public ResponseIVRValidaDecoSCDTO getDecoSmartCardDealerWareHouse(@WebParam(name = "hardwareType")String hardwareType,
											   @WebParam(name = "serialNR")String serialNR,
											   @WebParam(name = "dealerID")String dealerID, 
											   @WebParam(name = "crewID")Long crewID){
		return null;//ivrFacadeBeanLocal.getDecSC(hardwareType, serialNR, dealerID, crewID);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#validateSNProductType(java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO validateSNProductType(@WebParam(name = "productType")String productType, 
													   @WebParam(name = "serialNR")String serialNR){
		return null;//ivrFacadeBeanLocal.validateSNProductType(productType, serialNR);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setDecoSmartCardActivation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setDecoSmartCardActivation(@WebParam(name = "customerID")String customerID, 
													@WebParam(name = "orderID")String orderID, 
													@WebParam(name = "serialDecNr")String serialDecNr, 
													@WebParam(name = "serialSCNr")String serialSCNr,
													@WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setDecSCActivation(customerID, orderID, serialDecNr, serialSCNr,countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setDecoSmartCardChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setDecoSmartCardChange(@WebParam(name = "codCliente")String codCliente,
												@WebParam(name = "codJobCard")String codJobCard,
												@WebParam(name = "codDealer")String codDealer,
												@WebParam(name = "codDec")String codDec,
												@WebParam(name = "codSC")String codSC,
												@WebParam(name = "codDecNvo")String codDecNvo,
												@WebParam(name = "codSCNvo")String codSCNvo,
												@WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setDecSCChange(codCliente, codJobCard, codDealer, codDec, codSC, codDecNvo, codSCNvo, countryCode);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setSmartCardChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setSmartCardChange(@WebParam(name = "codCliente")String codCliente,
											 @WebParam(name = "codJobCard")String codJobCard,
											 @WebParam(name = "codSC")String codSC,
											 @WebParam(name = "codSCNvo")String codSCNvo,
											 @WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setSCChange(codCliente, codJobCard, codSC, codSCNvo, countryCode);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setDecoSmartCardModelChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setDecoSmartCardModelChange(@WebParam(name = "codCliente")String codCliente,
													 @WebParam(name = "oldSerialSCNr")String oldSerialSCNr,
													 @WebParam(name = "newSerialSCNr")String newSerialSCNr,
													 @WebParam(name = "jobcardID")String jobcardID,
													 @WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setDecSCModelChange(codCliente, oldSerialSCNr, newSerialSCNr, jobcardID, countryCode);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.ivr.IIvrWs#setChangeWorkOrderStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ResponseIVRBooleanDTO setChangeWorkOrderStatus(@WebParam(name = "customerID")String customerID,
											@WebParam(name = "codJobcard")String codJobcard,
											@WebParam(name = "codEstado")String codEstado,
											@WebParam(name = "reasonCode")String reasonCode,
											@WebParam(name = "countryCode")String countryCode){
		return null;//ivrFacadeBeanLocal.setJobCard(customerID, codJobcard, codEstado, reasonCode, countryCode);
	}
	
}
