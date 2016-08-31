package co.com.directv.sdii.ejb.business.ivr;

import javax.ejb.Local;

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
 * Fecha de Creación: 6/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */


@Local
public interface IvrBusinessBeanLocal {

	/**
	 * Caso de uso IVR - 01 Consulta Work Order
	 * 
	 * Metodo: Consulta una Work Order a partir de su ID y el pais
	 * @param woId Identificador de la Work Order
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRWoDTO Mensaje de respuesta del web service
	 * @author jforero 04/08/2010
	 */
	public ResponseIVRWoDTO getJobCard(String woId, String countryCode);
	
	/**
	 * Caso de uso IVR - 02 Consulta depot
	 * 
	 * Metodo: Consulta un dealer por id o por id y tipo
	 * @param depotID Identificador del Dealer
	 * @param depotType Identificador del tipo de dealer
	 * @return ResponseIVRDealerDTO Datos del dealer consultado
	 * @author
	 */
	public ResponseIVRDealerDTO getDepot(Long depotID, String depotType);
	
	
	/**
	 * Caso de uso IVR - 03 Consultar una Shipping order
	 * 
	 * Metodo: Consulta uns shipping order por su udentificador
	 * @param shipOrderID Codigo de la shipping order
	 * @param woCode Codigo de la work order
	 * @param countryCode Codigo del Pais
	 * @return ResponseIVRShipOrderDTO Datos de la shipping order consultada
	 * @author jforero 05/08/2010
	 */
	public ResponseIVRShipOrderDTO getShippingOrder(String shipOrderID, String woCode, String countryCode);
	
	
	/**
	 * Metodo: Realiza la activacion de  la antena correspondiente a
	 * la Work order indicada
	 * @param orderID  Identificador de la Work order
	 * @param countryCode Codigo del pais
	 * @return ResponseIVRAntenaActivationDTO Datos de respuesta de la Work order
	 * @author jforero 05/08/2010
	 */
	public ResponseIVRAntenaActivationDTO setAntenaActivation(String orderID, String countryCode);
	
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
	public ResponseIVRValidaDecoSCDTO getDecSC(String hardwareType,String serialNR,String dealerID, Long crewID);
	
	
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
	 * @author
	 */
	public ResponseIVRBooleanDTO validateSNProductType(String productType, String serialNR,Long countryId);
	
	
	
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
	public ResponseIVRBooleanDTO setDecSCActivation(String customerID, 
													String orderID, 
													String serialDecNr, 
													String serialSCNr,
													String countryCode);
	
	
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
	public ResponseIVRBooleanDTO setDecSCChange(String codCliente,
												String codJobCard,
												String codDealer,
												String codDec,
												String codSC,
												String codDecNvo,
												String codSCNvo,
												String countryCode);
	
	
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
	public ResponseIVRBooleanDTO setJobCard(String customerID,
											String codJobcard,
											String codEstado,
											String reasonCode,
											String countryCode);
}
