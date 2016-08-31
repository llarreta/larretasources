package co.com.directv.sdii.ws.ivr;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.ws.model.dto.ResponseIVRAntenaActivationDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRBooleanDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRDealerDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRShipOrderDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRValidaDecoSCDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRWoDTO;


/**
 * 
 * Interface creada para exponer servicios que seran consumidos por IVR
 * 
 * Fecha de Creación: 4/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="IvrWs",targetNamespace="http://ivr.ws.sdii.directv.com.co/")
public interface IIvrWs {
	
	/**
	 * Caso de uso IVR - 01 Consulta Work Order
	 * 
	 * Metodo: Consulta una Work Order a partir de su ID y el pais
	 * @param woId Identificador de la Work Order
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRWoDTO Mensaje de respuesta del web service
	 * @author jforero 04/08/2010
	 */
	@WebMethod(operationName = "getWorkOrderInfo", action = "getWorkOrderInfo")
	public ResponseIVRWoDTO getWorkOrderInfo(@WebParam(name = "woId")String woId, 
			   						   @WebParam(name = "countryCode")String countryCode);
	
	/**
	 * Caso de uso IVR - 02 Consulta depot
	 * 
	 * Metodo: Consulta un dealer por id o por id y tipo
	 * @param depotID Identificador del Dealer
	 * @param depotType Identificador del tipo de dealer
	 * @return ResponseIVRDealerDTO Datos del dealer consultado
	 * @author jforero 13/08/2010
	 */
	@WebMethod(operationName = "getDealerInfo", action = "getDealerInfo")
	public ResponseIVRDealerDTO getDealerInfo(@WebParam(name = "depotID")Long depotID, 
			 							 @WebParam(name = "depotType")String depotType);
	
	
	/**
	 * Caso de uso IVR - 03 Consultar una Shipping order
	 * 
	 * Metodo: Consulta uns shipping order por su udentificador
	 * @param orderID Tdentificador de la shipping order
	 * @param woCode Codigo de la work order
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRShipOrderDTO Datos de la shipping order consultada
	 * @author jforero 05/08/2010
	 */
	@WebMethod(operationName = "getShippingOrderInfo", action = "getShippingOrderInfo")
	public ResponseIVRShipOrderDTO getShippingOrderInfo(@WebParam(name = "shipOrderID")String shipOrderID,
													@WebParam(name = "woCode")String woCode, 
													@WebParam(name = "countryCode")String countryCode);
	
	
	/**
	 * Metodo: Realiza la activacion de  la antena correspondiente a
	 * la Work order indicada
	 * @param orderID  Identificador de la Work order
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRAntenaActivationDTO Datos de respuesta de la Work order
	 * @author jforero 05/08/2010
	 */
	@WebMethod(operationName = "setAntennaActivation", action = "setAntennaActivation")
	public ResponseIVRAntenaActivationDTO setAntennaActivation(@WebParam(name = "orderID")String orderID,
															  @WebParam(name = "countryCode")String countryCode);
	
	/**
	 * Caso de uso IVR - 05 Validar Existencia Deco o SmartCard
	 * 
	 * Metodo: Valida la existencia de un Deco o una
	 * smartcard en la bodega del dealer especificado
	 * 
	 * @param hardwareType Tipo de elemento
	 * @param serialNR Numero de Serie
	 * @param decSCStatus Estado del elemento
	 * @param dealerID Codigo del dealer
	 * @param crewID Codigo del crew
	 * @return ResponseIVRBooleanDTO Respuesta boolean que indica true si la cosnulta fue exitosa
	 * @author jforero 05/08/2010
	 */
	@WebMethod(operationName = "getDecoSmartCardDealerWareHouse", action = "getDecoSmartCardDealerWareHouse")
	public ResponseIVRValidaDecoSCDTO getDecoSmartCardDealerWareHouse(@WebParam(name = "hardwareType")String hardwareType,
											   @WebParam(name = "serialNR")String serialNR,
											   @WebParam(name = "dealerID")String dealerID, 
											   @WebParam(name = "crewID")Long crewID);
	
	
	/**
	 * caso de uso IVR - 06 Validar Serial con Tipo de Producto
	 * 
	 * Metodo: Valida que el modelo, tipo de elemento y serial corresponden
	 * al mismo elemento
	 * 
	 * @param HWModel Modelo del elemento
	 * @param ServiceCode Tipo de elemento
	 * @param serialNR Numero de Serie
	 * @return ResponseIVRBooleanDTO respuesta boolean que devuelve true si la validacion es exitosa
	 * @author jforero 13/08/2010
	 */
	@WebMethod(operationName = "validateSNProductType", action = "validateSNProductType")
	public ResponseIVRBooleanDTO validateSNProductType(@WebParam(name = "productType")String productType, 
													   @WebParam(name = "serialNR")String serialNR);
	
	
	
	/**
	 * Caso de uso IVR - 07 Activacion de Deco y SmartCard desde IVR
	 * 
	 * Metodo: realiza la activacion de decodificador y smartcard
	 * @param customerID Identificador del cliente
	 * @param orderID Identificador de la work order
	 * @param serialDecNr Serial del decodificador
	 * @param serialSCNr Serial de la SmartCard
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRBooleanDTO respuesta boolean que devuelve true si la activacion es exitosa
	 * @author jforero 06/08/2010
	 */
	@WebMethod(operationName = "setDecoSmartCardActivation", action = "setDecoSmartCardActivation")
	public ResponseIVRBooleanDTO setDecoSmartCardActivation(@WebParam(name = "customerID")String customerID, 
													@WebParam(name = "orderID")String orderID, 
													@WebParam(name = "serialDecNr")String serialDecNr, 
													@WebParam(name = "serialSCNr")String serialSCNr,
													@WebParam(name = "countryCode")String countryCode);
	
	/**
	 * Caso de uso IVR - 08 Cambio de Deco y SmartCard desde IVR
	 * 
	 * Metodo: Realiza el cambio de deco y smartcard para un cliente
	 * @param codCliente Identificador del cliente
	 * @param codJobCard identificador del jobcard
	 * @param codDealer Identificador del dealer
	 * @param codDec Codigo del Decodificador anterior
	 * @param codSC Codigo del decodificador anterior
	 * @param codDecNvo Codigo del Decodificador nuevo
	 * @param codSCNvo Codigo del decodificador nuevo
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRBooleanDTO Respuesta  boolean que devuelve true si el cambio es exitoso
	 * @throws BusinessException 
	 * @author jforero 06/08/2010
	 */
	@WebMethod(operationName = "setDecoSmartCardChange", action = "setDecoSmartCardChange")
	public ResponseIVRBooleanDTO setDecoSmartCardChange(@WebParam(name = "codCliente")String codCliente,
												@WebParam(name = "codJobCard")String codJobCard,
												@WebParam(name = "codDealer")String codDealer,
												@WebParam(name = "codDec")String codDec,
												@WebParam(name = "codSC")String codSC,
												@WebParam(name = "codDecNvo")String codDecNvo,
												@WebParam(name = "codSCNvo")String codSCNvo,
												@WebParam(name = "countryCode")String countryCode);
	
	
	/**
	 * Caso de uso IVR - 09  Cambio de SmartCard desde IVR
	 * 
	 * Metodo: Realiza el cambio de smartcard para un cliente
	 * @param codCliente Identificador del cliente
	 * @param codJobCard identificador del jobcard
	 * @param codSC Codigo del decodificador anterior
	 * @param codSCNvo Codigo del decodificador nuevo
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRBooleanDTO Respuesta  boolean que devuelve true si el cambio es exitoso
	 * @throws BusinessException 
	 * @author jforero 06/08/2010
	 */
	@WebMethod(operationName = "setSmartCardChange", action = "setSmartCardChange")
	public ResponseIVRBooleanDTO setSmartCardChange(@WebParam(name = "codCliente")String codCliente,
											 @WebParam(name = "codJobCard")String codJobCard,
											 @WebParam(name = "codSC")String codSC,
											 @WebParam(name = "codSCNvo")String codSCNvo,
											 @WebParam(name = "countryCode")String countryCode);
	
	
	/**
	 * Caso de uso IVR - 10 Cambio de modelo de Deco y SmartCard desde IVR
	 * 
	 * Metodo: Realiza el cambio de smartcard para un cliente
	 * @param codCliente Identificador del cliente
	 * @param oldSerialSCNr Serial anterior de SmartCard
	 * @param newSerialSCNr Serial nuevo de SmartCard
	 * @param jobcardID Identificador del jobcard
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRBooleanDTO Respuesta  boolean que devuelve true si el cambio es exitoso
	 * @throws BusinessException
	 * @author
	 */
	@WebMethod(operationName = "setDecoSmartCardModelChange", action = "setDecoSmartCardModelChange")
	public ResponseIVRBooleanDTO setDecoSmartCardModelChange(@WebParam(name = "codCliente")String codCliente,
													 @WebParam(name = "oldSerialSCNr")String oldSerialSCNr,
													 @WebParam(name = "newSerialSCNr")String newSerialSCNr,
													 @WebParam(name = "jobcardID")String jobcardID,
													 @WebParam(name = "countryCode")String countryCode);
	

	/**
	 * Caso de uso IVR - 11 Cambio de Estado WorkOrder
	 * 
	 * Metodo: Realiza el cambio de estado de una work order
	 * @param customerID Identificador del cliente
	 * @param codJobcard Identificador del jobcard
	 * @param codEstado Nuevo estado del jobcard
	 * @param reasonCode Codigo del motivo de cambio de estado
	 * @param countryCode Codigo del pais
	 * @return Respuesta  boolean que devuelve true si el cambio es exitoso
	 * @throws BusinessException 
	 * @author jforero 06/08/2010
	 */
	@WebMethod(operationName = "setChangeWorkOrderStatus", action = "setChangeWorkOrderStatus")
	public ResponseIVRBooleanDTO setChangeWorkOrderStatus(@WebParam(name = "customerID")String customerID,
											@WebParam(name = "codJobcard")String codJobcard,
											@WebParam(name = "codEstado")String codEstado,
											@WebParam(name = "reasonCode")String reasonCode,
											@WebParam(name = "countryCode")String countryCode);
	

}
