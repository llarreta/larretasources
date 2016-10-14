package co.com.directv.sdii.ejb.business.ivr.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.IvrBusinessMessagesEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.ws.model.dto.ResponseIVRAntenaActivationDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRBooleanDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRDealerDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRShipOrderDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRValidaDecoSCDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRWoDTO;
import co.com.directv.sdii.ws.model.dto.ResponseIVRWoServiceDTO;


/**
 * 
 * Clase creada para exponer servicios web que serán consumidos por IVR
 * 
 * Fecha de Creación: 6/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */

@Stateless(name="IvrBusinessBeanLocal",mappedName="ejb/IvrBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IvrBusinessBean extends BusinessBase implements IvrBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(IvrBusinessBean.class);

	@EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
	private SerializedBusinessBeanLocal businessSerialized;

	@EJB(name="CoreWOBusinessLocal",beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal businessWo;

	@EJB(name="DealersCRUDLocal",beanInterface=DealersCRUDLocal.class)
	private DealersCRUDLocal dealersCRUDBean;

	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal businessWarehouseElement;

	@EJB(name="StateMachineWOBusinessLocal", beanInterface=StateMachineWOBusinessLocal.class)
	private StateMachineWOBusinessLocal stateMachineWOBusiness;

	@EJB(name="CoreWorkOrderServiceBrokerLocal", beanInterface=CoreWorkOrderServiceBrokerLocal.class)
	private CoreWorkOrderServiceBrokerLocal coreWorkOrderServiceBroker;

	@EJB(name="ShippingOrderElementBusinessBeanLocal", beanInterface=ShippingOrderElementBusinessBeanLocal.class)
	private ShippingOrderElementBusinessBeanLocal shippingOrderElementBusiness;

	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;

	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDao;

	@EJB(name="RequiredServiceElementDAOLocal", beanInterface=RequiredServiceElementDAOLocal.class)
	private RequiredServiceElementDAOLocal requiredServiceElementDao;

	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDao;



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#getJobCard(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseIVRWoDTO getJobCard(String woCode, String countryCode){
		log.debug("== Inicia getJobCard/IvrWs ==");
		ResponseIVRWoDTO responseIVRWoDTO = new ResponseIVRWoDTO();
		WorkOrderVO workOrderVO = null;
		List<ResponseIVRWoServiceDTO> listResponseIVRWoServiceDTO = new ArrayList<ResponseIVRWoServiceDTO>();
		List<ShippingOrderVO> listShippingOrderVO = null;
		List<ShippingOrderElementVO> listShippingOrderElementVO = null;
		ShippingOrderVO shippingOrderVo = null;
		ShippingOrderElementVO shippingOrderElementVo = null;
		List<SerializedVO> listSerializedVO = null;
		SerializedVO serializedVo = null;
		try {
			UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			woCode = woCode.trim();
			countryCode = countryCode.trim();
			//buscar la work order por id y pais
			workOrderVO = businessWo.getWorkOrderByCodeAndCountry(woCode, countryCode);
			//si se encontro la work order
			if (workOrderVO != null && workOrderVO.getId() != null){
				//fecha de terminaci�n
				responseIVRWoDTO.setCompletionDate(workOrderVO.getWoRealizationDate());
				//Numero de suscripci�n del cliente
				responseIVRWoDTO.setCustomerCode(workOrderVO.getCustomer().getCustomerCode());
				//Fecha Registro
				responseIVRWoDTO.setDateRegistered(workOrderVO.getCreationDate());
				//Numero de Instalador Asociado
				responseIVRWoDTO.setInstallerDealerCode(workOrderVO.getDealerInstaller());
				//Numero identificador del work order
				responseIVRWoDTO.setJobCardId(workOrderVO.getWoCode());
				//Estado del Jobcard
				responseIVRWoDTO.setJobCardStatus(workOrderVO.getWorkorderStatusByActualStatusId().getWoStateCode());
				//Tipo de JobCard
				responseIVRWoDTO.setJobCardType(workOrderVO.getWoTypeByWoTypeId().getWoTypeCode());					
				//fecha programada de instalacion
				responseIVRWoDTO.setDateToInstall(workOrderVO.getWoProgrammingDate());
				//Servicios de la WO
				Set<WorkOrderService> setWOService = workOrderVO.getWorkOrderServices();
				//Si la work order tiene servicios
				if (setWOService != null && setWOService.size() > 0){
					for (WorkOrderService workOrderService : setWOService){
						if (workOrderService != null && workOrderService.getId() != null){
							ResponseIVRWoServiceDTO responseIVRWoServiceDTO = new ResponseIVRWoServiceDTO();
							responseIVRWoServiceDTO.setServiceCode(workOrderService.getService().getServiceCode());
							responseIVRWoServiceDTO.setServiceName(workOrderService.getService().getServiceName());
							listResponseIVRWoServiceDTO.add(responseIVRWoServiceDTO);					
						}
					}
					responseIVRWoDTO.setServices(listResponseIVRWoServiceDTO);
				}
				//busca las shipping order correspondientes a la workorder
				listShippingOrderVO = businessWo.getShippingOrdersByWOCodeAndCountry(woCode, countryCode);
				//si encontro shipping orders
				if (listShippingOrderVO != null && listShippingOrderVO.size() > 0){
					//recorre el arreglo de las shipping order
					for (int ii = 0;ii < listShippingOrderVO.size();ii++){
						shippingOrderVo = listShippingOrderVO.get(ii);
						//si la shipping order es valida
						if (shippingOrderVo != null && shippingOrderVo.getId() != null){
							//busca los shipping elements de la shipping order
							listShippingOrderElementVO = shippingOrderElementBusiness.getShippingOrderElementBySOID(shippingOrderVo.getId());
							//si encontro elementos
							if (listShippingOrderElementVO != null && listShippingOrderElementVO.size() > 0){
								//recorre el arreglo de elementos
								for (int jj = 0; jj < listShippingOrderElementVO.size();jj++){
									shippingOrderElementVo = listShippingOrderElementVO.get(jj);
									//valida el elemento
									if (shippingOrderElementVo != null 
										&& shippingOrderElementVo.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) 
										&& shippingOrderElementVo.getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){

										//busca serializados para ese elemento
										listSerializedVO = businessSerialized.getSerializedByElementId(shippingOrderElementVo.getElement().getId());
										serializedVo = listSerializedVO.get(0);
										if (serializedVo != null)
											//Serial del deco
											responseIVRWoDTO.setSerialNr(serializedVo.getSerialCode());
									}//FIN valida el elemento
								}//FIN recorre el arreglo de elementos
							}//FIN si encontro elementos
						}//FIN si la shipping order es valida
					}//FIN recorre el arreglo de las shipping order
				}
				responseIVRWoDTO.setErrorCode("0");
			//si no se encontro la work order
			}else{
				responseIVRWoDTO.setErrorCode(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getCode());
				responseIVRWoDTO.setErrorDescription(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getMessage());
				responseIVRWoDTO.setErrorReason(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.debug("== Error al tratar de ejecutar la operacion getJobCard/IvrWs", ex);
			responseIVRWoDTO.setErrorCode(ex.getMessageCode());
			responseIVRWoDTO.setErrorDescription(ex.getMessage());
			responseIVRWoDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.debug("== Error al tratar de ejecutar la operacion getJobCard/IvrWs", ex);
			responseIVRWoDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRWoDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRWoDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina getJobCard/IvrWs ==");
		}
		return responseIVRWoDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#getDepot(java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseIVRDealerDTO getDepot(Long depotID, String depotType){
		log.debug("== Inicia getDepot/IvrWs ==");
		ResponseIVRDealerDTO responseIVRDealerDTO = new ResponseIVRDealerDTO();
		DealerVO dealerVO = null;
		try {
			UtilsBusiness.assertNotNull(depotID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			//si no se tiene tipo de dealer se consulta solo por el id
			if (depotType != null && !depotType.equals("")){
				depotType = depotType.trim();
				dealerVO = dealersCRUDBean.getDealerByCodeAndType(depotID,depotType);
			}else{	
				dealerVO = dealersCRUDBean.getDealerByDepotCodeOrDealerCode(null,depotID);
			}
			//si se encontro el dealer
			if (dealerVO != null && dealerVO.getId() != null){
				//Estado del Depot
				if (dealerVO.getDealerStatus().getStatusCode() != null && !dealerVO.getDealerStatus().getStatusCode().equals(CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity())){
					responseIVRDealerDTO.setActiveYN("Y");
				}else{
					responseIVRDealerDTO.setActiveYN("N");
				}
				//CC o NIT
				responseIVRDealerDTO.setCuRefNumber(dealerVO.getNit());
				//Numero de suscripci�n en IBS
				responseIVRDealerDTO.setDepotNr(dealerVO.getDealerCode());
				//Tipo de Depot
				responseIVRDealerDTO.setDepotType(dealerVO.getDealerType().getDealerTypeCode());
				//Nombre del tipo de Depot
				responseIVRDealerDTO.setDepotTypeName(dealerVO.getDealerType().getDealerTypeName());
				//C�digo del Depot
				responseIVRDealerDTO.setDepotUserKey(dealerVO.getDepotCode());
				responseIVRDealerDTO.setErrorCode("0");
			//si no se encontro el dealer
			}else{
				responseIVRDealerDTO.setErrorCode(IvrBusinessMessagesEnum.DEALER_NOT_FOUND.getCode());
				responseIVRDealerDTO.setErrorDescription(IvrBusinessMessagesEnum.DEALER_NOT_FOUND.getMessage());
				responseIVRDealerDTO.setErrorReason(IvrBusinessMessagesEnum.DEALER_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.error("== Error en getDepot/IvrWs ==",ex);
			responseIVRDealerDTO.setErrorCode(ex.getMessageCode());
			responseIVRDealerDTO.setErrorDescription(ex.getMessage());
			responseIVRDealerDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.debug("== Error al tratar de ejecutar la operacion getJobCard/IvrWs", ex);
			responseIVRDealerDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRDealerDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRDealerDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina getDepot/IvrWs ==");
		}
		return responseIVRDealerDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#getShippingOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseIVRShipOrderDTO getShippingOrder(String shipOrderID, String woCode, String countryCode){
		log.debug("== Inicia getOrder/IvrWs ==");
		ResponseIVRShipOrderDTO responseIVRShipOrderDTO = new ResponseIVRShipOrderDTO();
		ShippingOrderVO shippingOrderVO = null;
		try {
			UtilsBusiness.assertNotNull(shipOrderID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			shipOrderID = shipOrderID.trim();
			woCode = woCode.trim();
			countryCode = countryCode.trim();
			//busca la shipping order
			shippingOrderVO = businessWo.getShippingOrderByCodeAndWOCodeAndCountry(shipOrderID, woCode, countryCode);
			//Si encontro la shipping order
			if (shippingOrderVO  != null && shippingOrderVO.getId() != null){
				WorkOrderVO workOrderVO = businessWo.getWorkOrderByID(shippingOrderVO.getWorkOrderId());
				//Si encontro la work oder de la shipping order, obtiene el cliente
				if (workOrderVO != null && workOrderVO.getCustomer() != null){
					//identificador del cliente
					responseIVRShipOrderDTO.setCustomerID(workOrderVO.getCustomer().getCustomerCode());
				}
				//Numero de orden
				responseIVRShipOrderDTO.setOrderID(shipOrderID);
				//Estado de la orden
				responseIVRShipOrderDTO.setOrderStatus(shippingOrderVO.getShippingOrderStatus().getSoStatusCode());
				//Tipo de orden
				responseIVRShipOrderDTO.setOrderType(shippingOrderVO.getShippingOrderType().getShippingOrderCode());
				responseIVRShipOrderDTO.setErrorCode("0");
			//Si no encontro la shipping order
			}else{
				responseIVRShipOrderDTO.setErrorCode(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getCode());
				responseIVRShipOrderDTO.setErrorDescription(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getMessage());
				responseIVRShipOrderDTO.setErrorReason(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.error("== Error en getOrder/IvrWs ==",ex);
			responseIVRShipOrderDTO.setErrorCode(ex.getMessageCode());
			responseIVRShipOrderDTO.setErrorDescription(ex.getMessage());
			responseIVRShipOrderDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en getOrder/IvrWs ==",ex);
			responseIVRShipOrderDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRShipOrderDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRShipOrderDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina getOrder/IvrWs ==");
		}
		return responseIVRShipOrderDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#setAntenaActivation(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseIVRAntenaActivationDTO setAntenaActivation(String orderID, String countryCode){
		log.debug("== Inicia setAntenaActivation/IvrWs ==");
		ResponseIVRAntenaActivationDTO responseIVRAntenaActivationDTO = new ResponseIVRAntenaActivationDTO();
		WorkOrderVO workOrderVo = null;
		List<WarehouseVO> listCustomerWarehouseVo = null;
		List<WarehouseVO> listCrewWarehouseVo = null;
		WarehouseVO customerWarehouseVO = null;
		WarehouseVO crewWarehouseVO = null;
		List<ShippingOrderElementVO> listShippingOrderElementVO  = null;
		ShippingOrderElementVO shippingOrderElementVo = null;
		try {
			UtilsBusiness.assertNotNull(orderID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			orderID = orderID.trim();
			countryCode = countryCode.trim();
			//busca la work order
			workOrderVo = businessWo.getWorkOrderByCodeAndCountry(orderID, countryCode);
			//si encontro la work order
			if (workOrderVo != null && workOrderVo.getId() != null){
				//consumir ws de IBS de activacion de antena -- Pendiente invocar servicio pues no se aprobo control de cambios por parte de Directv
				//busca la cuadrilla de la workorder
				WoAssignment woAssignment = woAssignmentDao.getLastDealerAssignmentByWoId(workOrderVo.getId());
				//si encontro la cuadrilla
				if (woAssignment != null && woAssignment.getCrewId() != null){
					//busca la bodega del crew
					listCrewWarehouseVo = warehouseBusinessBean.getWarehousesByDealerIdAndCrewId(workOrderVo.getDealerId(), woAssignment.getCrewId());
					if (listCrewWarehouseVo != null && listCrewWarehouseVo.size() > 0 
						&& listCrewWarehouseVo.get(0) != null){
						crewWarehouseVO = listCrewWarehouseVo.get(0);
						//busca la bodega del cliente
						listCustomerWarehouseVo = warehouseBusinessBean.getWarehousesByCustomerId(workOrderVo.getCustomer().getId());
						if (listCustomerWarehouseVo != null && listCustomerWarehouseVo.size() > 0 
							&& listCustomerWarehouseVo.get(0) != null){
							customerWarehouseVO = listCustomerWarehouseVo.get(0);
							List<ShippingOrderVO> listShippingOrderVo = businessWo.getShippingOrdersByWOCodeAndCountry(orderID, countryCode);
							//Si encontro shipping orders
							if (listShippingOrderVo != null && listShippingOrderVo.size() > 0){
								for (ShippingOrderVO shippingOrderVO : listShippingOrderVo){
									//busca los shipping elements de la shipping order
									listShippingOrderElementVO = shippingOrderElementBusiness.getShippingOrderElementBySOID(shippingOrderVO.getId());
									//si encontro elementos
									if (listShippingOrderElementVO != null && listShippingOrderElementVO.size() > 0){
										//recorre el arreglo de elementos
										for (int jj = 0; jj < listShippingOrderElementVO.size();jj++){
											shippingOrderElementVo = listShippingOrderElementVO.get(jj);
											//valida el elemento
											if (shippingOrderElementVo != null 
												&& shippingOrderElementVo.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) 
												&& shippingOrderElementVo.getElementType().getTypeElementCode().equals(CodesBusinessEntityEnum.ANTENA_ELEMENT_TYPE.getCodeEntity())){
												//Caso de uso INV 38
												Double quantity = 0D;	
												Set<WorkOrderService> woServices = workOrderVo.getWorkOrderServices();
												for (WorkOrderService workOrderService : woServices) {
													List<RequiredServiceElement> reqServElements = requiredServiceElementDao.getRequiredServiceElementByService(workOrderService.getService().getId());
													for (RequiredServiceElement requiredServiceElement : reqServElements) {
														if(requiredServiceElement.getElementType().getId().longValue() == shippingOrderElementVo.getElement().getElementType().getId().longValue()){
															quantity = requiredServiceElement.getQuantity();
														}
													}
												}
												//IVR movimiento de inventario
												//ElementMovementDTO dto = new ElementMovementDTO(crewWarehouseVO.getId(), customerWarehouseVO.getId(), shippingOrderElementVo.getElement().getElementType().getId(), quantity, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(), CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity(), false,null,null);
												//businessWarehouseElement.moveNotSerializedElementBetweenWareHouses(dto);
											}//FIN valida el elemento
										}//FIN recorre el arreglo de elementos
									}//FIN si encontro elementos
								}
							//Si no encontro shipping orders
							}else{
								responseIVRAntenaActivationDTO.setErrorCode(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getCode());
								responseIVRAntenaActivationDTO.setErrorDescription(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getMessage());
								responseIVRAntenaActivationDTO.setErrorReason(IvrBusinessMessagesEnum.SHIPPING_ORDER_NOT_FOUND.getReason());
							}							
						//no encuentra la bodega del customer
						}else{
							log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
							throw new BusinessException(IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getMessage());
						}
					//no encuentra la bodega del crew
					}else{
						log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
						throw new BusinessException(IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getMessage());
					}
				//si NO encontro la cuadrilla
				}else{
					responseIVRAntenaActivationDTO.setErrorCode(IvrBusinessMessagesEnum.CREW_NOT_FOUND.getCode());
					responseIVRAntenaActivationDTO.setErrorDescription(IvrBusinessMessagesEnum.CREW_NOT_FOUND.getMessage());
					responseIVRAntenaActivationDTO.setErrorReason(IvrBusinessMessagesEnum.CREW_NOT_FOUND.getReason());
				}
			//si no encontro la work order
			}else{
				responseIVRAntenaActivationDTO.setErrorCode(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getCode());
				responseIVRAntenaActivationDTO.setErrorDescription(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getMessage());
				responseIVRAntenaActivationDTO.setErrorReason(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getReason());
			}

			//codigo del cliente
			responseIVRAntenaActivationDTO.setCustomerID(workOrderVo.getCustomer().getCustomerCode());
			//codigo de la work order
			responseIVRAntenaActivationDTO.setOrderID(orderID);
			//codigo del estado de la work order
			responseIVRAntenaActivationDTO.setOrderStatus(workOrderVo.getWorkorderStatusByActualStatusId().getWoStateCode());
			//codigo del tipo de work order
			responseIVRAntenaActivationDTO.setOrderType(workOrderVo.getWoTypeByWoTypeId().getWoTypeCode());
			responseIVRAntenaActivationDTO.setErrorCode("0");
		}catch (BusinessException ex) {
			log.error("== Error en setAntenaActivation/IvrWs ==",ex);
			responseIVRAntenaActivationDTO.setErrorCode(ex.getMessageCode());
			responseIVRAntenaActivationDTO.setErrorDescription(ex.getMessage());
			responseIVRAntenaActivationDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en setAntenaActivation/IvrWs ==",ex);
			responseIVRAntenaActivationDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRAntenaActivationDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRAntenaActivationDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina setAntenaActivation/IvrWs ==");
		}
		return responseIVRAntenaActivationDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#getDecSC(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseIVRValidaDecoSCDTO getDecSC(String hardwareType,String serialNR,String dealerID, Long crewID){
		log.debug("== Inicia getDecSC/IvrWs ==");
		ResponseIVRValidaDecoSCDTO responseIVRValidaDecoSCDTO = new ResponseIVRValidaDecoSCDTO();
		WarehouseElementVO warehouseElementVO = null;
		try {
			UtilsBusiness.assertNotNull(hardwareType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialNR, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			//UtilsBusiness.assertNotNull(dealerID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			//UtilsBusiness.assertNotNull(crewID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			hardwareType = hardwareType.trim();
			serialNR = serialNR.trim();
			//dealerID = dealerID.trim();
			//busca el elemento de de bodega
			warehouseElementVO = businessWarehouseElement.getWarehouseElementByDealerAndCrewAndTypeAndSerial(dealerID,crewID,hardwareType,serialNR);
			//si encontro el elemento de bodega para ese dealer
			if (warehouseElementVO != null && warehouseElementVO.getId() != null){
				//Bodega en la que se encuentra el Decodificador o SmartCard.
				responseIVRValidaDecoSCDTO.setDecSCdepotn(warehouseElementVO.getWarehouseId().getWhCode());
				//Modelo del Decodificador o de la SmartCard.
				responseIVRValidaDecoSCDTO.setDecSCModel(warehouseElementVO.getSerialized().getElement().getElementType().getElementModel().getModelCode());
				//Estado del Decodificador o de la SmartCard.
				responseIVRValidaDecoSCDTO.setDecSCStatus(warehouseElementVO.getSerialized().getElement().getElementStatus().getElementStatusCode());
				//Serial del Decodificador o del SmartCard.
				responseIVRValidaDecoSCDTO.setSerialNR(serialNR);
				responseIVRValidaDecoSCDTO.setErrorCode("0");
			//si no encontro el elemento de bodega para ese dealer
			}else{
				responseIVRValidaDecoSCDTO.setErrorCode(IvrBusinessMessagesEnum.WAREHOUSE_ELEMENT_NOT_FOUND.getCode());
				responseIVRValidaDecoSCDTO.setErrorDescription(IvrBusinessMessagesEnum.WAREHOUSE_ELEMENT_NOT_FOUND.getMessage());
				responseIVRValidaDecoSCDTO.setErrorReason(IvrBusinessMessagesEnum.WAREHOUSE_ELEMENT_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.error("== Error en getDecSC/IvrWs ==",ex);
			responseIVRValidaDecoSCDTO.setErrorCode(ex.getMessageCode());
			responseIVRValidaDecoSCDTO.setErrorDescription(ex.getMessage());
			responseIVRValidaDecoSCDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en getDecSC/IvrWs ==",ex);
			responseIVRValidaDecoSCDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRValidaDecoSCDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRValidaDecoSCDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina getDecSC/IvrWs ==");
		}
		return responseIVRValidaDecoSCDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#validateSNProductType(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponseIVRBooleanDTO validateSNProductType(String productType, String serialNR,Long countryId){
		log.debug("== Inicia validateSNProductType/IvrWs ==");
		ResponseIVRBooleanDTO responseIVRBooleanDTO = new ResponseIVRBooleanDTO();
		SerializedVO serializedVO = null;
		try {
			UtilsBusiness.assertNotNull(productType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialNR, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			productType = productType.trim();
			serialNR = serialNR.trim();
			//busca el elemento serializadio por el numero de serie
			serializedVO = businessSerialized.getSerializedBySerial(serialNR,countryId);
			//si encontro el elemento
			if (serializedVO != null && serializedVO.getElement().getId() != null){
				if(serializedVO.getElement().getElementType().getTypeElementCode() != null
						&& serializedVO.getElement().getElementType().getTypeElementCode().equals(productType)){
					responseIVRBooleanDTO.setBolOk(true);
					responseIVRBooleanDTO.setErrorCode("0");
				}else{
					responseIVRBooleanDTO.setBolOk(false);
					responseIVRBooleanDTO.setErrorCode("0");
				}
			//si no encontro el elemento
			}else{
				responseIVRBooleanDTO.setBolOk(false);
				responseIVRBooleanDTO.setErrorCode(IvrBusinessMessagesEnum.SERIALIZED_NOT_FOUND.getCode());
				responseIVRBooleanDTO.setErrorDescription(IvrBusinessMessagesEnum.SERIALIZED_NOT_FOUND.getMessage());
				responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.SERIALIZED_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.error("== Error en validateSNProductType/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ex.getMessageCode());
			responseIVRBooleanDTO.setErrorDescription(ex.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en validateSNProductType/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRBooleanDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina validateSNProductType/IvrWs ==");
		}
		return responseIVRBooleanDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#setDecSCActivation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseIVRBooleanDTO setDecSCActivation(String customerID, String orderID, 
			String serialDecNr, String serialSCNr,
			String countryCode){
		log.debug("== Inicia setDecSCActivation/IvrWs ==");
		ResponseIVRBooleanDTO responseIVRBooleanDTO = new ResponseIVRBooleanDTO();
		List<String> listSerial = new ArrayList<String>();
		try {
			UtilsBusiness.assertNotNull(customerID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(orderID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialDecNr, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serialSCNr, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			orderID = orderID.trim();
			serialDecNr = serialDecNr.trim();
			serialSCNr = serialSCNr.trim();
			countryCode = countryCode.trim();
			//llamar a addActivation para el deco y la smartcard
			listSerial.add(serialDecNr);
			listSerial.add(serialSCNr);
			coreWorkOrderServiceBroker.addActivation(orderID, countryCode, listSerial);

			//caso de uso INV 39
			moveSerializedElementsBetweenWareHouses(orderID,countryCode,serialDecNr,serialSCNr,false,true);

			//caso de uso INV-42
			updateLinkedElementSerialUpdate(serialDecNr,serialSCNr,countryCode);	

			responseIVRBooleanDTO.setBolOk(true);
			responseIVRBooleanDTO.setErrorCode("0");
		}catch (BusinessException ex) {
			log.error("== Error en setDecSCActivation/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ex.getMessageCode());
			responseIVRBooleanDTO.setErrorDescription(ex.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en setDecSCActivation/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRBooleanDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina setDecSCActivation/IvrWs ==");
		}
		return responseIVRBooleanDTO;
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#setDecSCChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseIVRBooleanDTO setDecSCChange(String codCliente,
			String codJobCard,
			String codDealer,
			String codDec,
			String codSC,
			String codDecNvo,
			String codSCNvo,
			String countryCode){
		log.debug("== Inicia setDecSCChange/IvrWs ==");
		ResponseIVRBooleanDTO responseIVRBooleanDTO = new ResponseIVRBooleanDTO();
		List<String> listSerial = new ArrayList<String>();
		try {
			UtilsBusiness.assertNotNull(codCliente, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codJobCard, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codDealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codDec, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codSC, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codDecNvo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codSCNvo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			codJobCard = codJobCard.trim();
			codDec = codDec.trim();
			codSC = codSC.trim();
			codDecNvo = codDecNvo.trim();
			codSCNvo = codSCNvo.trim();
			countryCode = countryCode.trim();
			//llamar a ws addActivacion para el deco y la smartcard
			listSerial.add(codDecNvo);
			listSerial.add(codSCNvo);
			coreWorkOrderServiceBroker.addActivation(codJobCard, countryCode, listSerial);

			//caso de uso INV 39
			moveSerializedElementsBetweenWareHouses(codJobCard,countryCode,codDecNvo,codSCNvo,false,true);

			//caso de uso INV-41
			moveSerializedElementsBetweenWareHouses(codJobCard,countryCode,codDec,codSC,true,false);

			//caso de uso INV-43
			updateLinkedElementSerialUpdate(codDecNvo,codSCNvo,countryCode);

			responseIVRBooleanDTO.setBolOk(true);
			responseIVRBooleanDTO.setErrorCode("0");
		}catch (BusinessException ex) {
			log.error("== Error en setDecSCChange/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ex.getMessageCode());
			responseIVRBooleanDTO.setErrorDescription(ex.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en setDecSCChange/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRBooleanDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina setDecSCChange/IvrWs ==");
		}
		return responseIVRBooleanDTO;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.ivr.IvrBusinessBeanLocal#setJobCard(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseIVRBooleanDTO setJobCard(String customerID,
			String codJobcard,
			String codEstado,
			String reasonCode,
			String countryCode){
		log.debug("== Inicia setJobCard/IvrWs ==");
		ResponseIVRBooleanDTO responseIVRBooleanDTO = new ResponseIVRBooleanDTO();
		WorkOrderVO workOrderVO = null;
		try {
			UtilsBusiness.assertNotNull(customerID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codJobcard, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(codEstado, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(reasonCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			customerID = customerID.trim();
			codJobcard = codJobcard.trim();
			codEstado = codEstado.trim();
			reasonCode = reasonCode.trim();
			countryCode = countryCode.trim();
			//busca la work order indicada
			workOrderVO = businessWo.getWorkOrderByCodeAndCountry(codJobcard, countryCode);
			//si encontro la work order
			if (workOrderVO != null && workOrderVO.getId() != null){
				//validacion cambio de estado
				co.com.directv.sdii.model.pojo.WorkorderStatus woStatus = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(codEstado);
				co.com.directv.sdii.common.util.UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage() + " No se encontró el estado en la tabla ibs_6_status con el código " + codEstado + " y que este relacionado con un registro de la tabla workorder_status");

				if (stateMachineWOBusiness.validateStatusChangeWorkOrderByCodes(workOrderVO, woStatus.getWoStateCode())){
					//cambiar estado de la work order				
					businessWo.changeWorkOrderStatus(customerID,countryCode,codJobcard, reasonCode, codEstado);
					responseIVRBooleanDTO.setBolOk(true);
					responseIVRBooleanDTO.setErrorCode("0");
				}	
			//si no se encontro la work order
			}else{
				responseIVRBooleanDTO.setBolOk(false);
				responseIVRBooleanDTO.setErrorCode(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getCode());
				responseIVRBooleanDTO.setErrorDescription(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getMessage());
				responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getReason());
			}
		}catch (BusinessException ex) {
			log.error("== Error en setJobCard/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ex.getMessageCode());
			responseIVRBooleanDTO.setErrorDescription(ex.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}catch (Exception ex) {
			log.error("== Error en setJobCard/IvrWs ==",ex);
			responseIVRBooleanDTO.setBolOk(false);
			responseIVRBooleanDTO.setErrorCode(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			responseIVRBooleanDTO.setErrorDescription(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			responseIVRBooleanDTO.setErrorReason(IvrBusinessMessagesEnum.EXCEPTION_ERROR.getReason());
		}finally {
			log.debug("== Termina setJobCard/IvrWs ==");
		}
		return responseIVRBooleanDTO;
	}



	/**
	 * Metodo: Actualiza el elemento relacionado con un serializado a partit de sus seriales
	 * @param serialWhElement Numero de serie del elemento
	 * @param serialLinkedWhElement Numero de serie del elemento relacionado
	 * @throws BusinessException
	 * @author jforero 13/08/2010
	 */
	private void updateLinkedElementSerialUpdate(String serialWhElement, String serialLinkedWhElement,String countryCode) throws BusinessException{
		SerializedVO serializedVo = null;
		WarehouseElementVO warehouseElementV0 = null;
		//busca el serializado correspondiente al serial
		serializedVo = businessSerialized.getSerializedBySerial(serialWhElement,countryCode);
		//valida el serializado
		if (serializedVo != null && serializedVo.getElementId() != null){
			//busca el elemento de bodega que corresponde al serializado
			warehouseElementV0 = businessWarehouseElement.getWarehouseElementByID(serializedVo.getElementId());
			//si encontro el elemento de bodega
			if (warehouseElementV0 != null && warehouseElementV0.getId() != null){
				//actualiza la vinculacion entre los elementos
				String serialLinkedOld = null;
				if (warehouseElementV0.getSerialized() != null
						&& warehouseElementV0.getSerialized().getSerialized() != null){
					serialLinkedOld  = warehouseElementV0.getSerialized().getSerialized().getSerialCode();
				}
				businessWarehouseElement.updateLinkedElementSerialUpdate(warehouseElementV0.getWarehouseId().getId(), warehouseElementV0.getSerialized().getSerialCode(), warehouseElementV0.getId(), serialLinkedOld, serialLinkedWhElement, warehouseElementV0.getWarehouseId().getId(),"");
			//si NO encontro el elemento de bodega
			}else{
				log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/IvrBusinessBean ==");
				throw new BusinessException(IvrBusinessMessagesEnum.WAREHOUSE_ELEMENT_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.WAREHOUSE_ELEMENT_NOT_FOUND.getMessage());
			}
		//si el serializado no es valido
		}else{
			log.debug("== Error al tratar de ejecutar la operacion updateLinkedElementSerialUpdate/IvrBusinessBean ==");
			throw new BusinessException(IvrBusinessMessagesEnum.SERIALIZED_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.SERIALIZED_NOT_FOUND.getMessage());
		}
	}


	/**
	 * Metodo: Mueve dos elementos (smartcard y decodificador) de la bodega indicada a la bodega indicada
	 * segun los parametros customerWHSourceCrewWHTarget y crewWHSourceCustomerWHTarget
	 * @param orderID Codigo de la work order
	 * @param countryCode Codigo del pais
	 * @param serialDecNr Numero de serie del decodificador
	 * @param serialSCNr Numero de serie del smartcard
	 * @param customerWHSourceCrewWHTarget True cuando el elemento es movido de la bodega del cliente a la del crew
	 * @param crewWHSourceCustomerWHTarget True cuando el elemento es movido de la bodega del crew a la del cliente
	 * @throws BusinessException 
	 * @author jforero 13/08/2010
	 */
	private void moveSerializedElementsBetweenWareHouses(String orderID,
			String countryCode, 
			String serialDecNr, 
			String serialSCNr,
			boolean customerWHSourceCrewWHTarget,
			boolean crewWHSourceCustomerWHTarget) throws BusinessException, 
			PropertiesException,DAOSQLException,DAOServiceException{
		WorkOrderVO workOrderVo = null;
		List<WarehouseVO> listCrewWarehouseVo = null;
		List<WarehouseVO> listCustomerWarehouseVo = null;
		WarehouseVO customerWarehouseVO = null;
		WarehouseVO crewWarehouseVO = null;
		SerializedVO serializedDeco = null;
		SerializedVO serializedSC = null;
		workOrderVo = businessWo.getWorkOrderByCodeAndCountry(orderID, countryCode);
		//Si encontro la work order
		if (workOrderVo != null && workOrderVo.getId() != null){
			WoAssignment woAssigment = woAssignmentDao.getLastDealerAssignmentByWoId(workOrderVo.getId());
			if (woAssigment != null && woAssigment.getCrewId() != null){
				//busca la bodega del crew
				listCrewWarehouseVo = warehouseBusinessBean.getWarehousesByDealerIdAndCrewId(workOrderVo.getDealerId(), woAssigment.getCrewId());
				if (listCrewWarehouseVo != null && listCrewWarehouseVo.size() > 0 
					&& listCrewWarehouseVo.get(0) != null){
					crewWarehouseVO = listCrewWarehouseVo.get(0);
					//busca la bodega del cliente
					listCustomerWarehouseVo = warehouseBusinessBean.getWarehousesByCustomerId(workOrderVo.getCustomer().getId());
					if (listCustomerWarehouseVo != null && listCustomerWarehouseVo.size() > 0 
						&& listCustomerWarehouseVo.get(0) != null){
						customerWarehouseVO = listCustomerWarehouseVo.get(0);
						//busca el elemento deco
						serializedDeco = businessSerialized.getSerializedBySerial(serialDecNr,workOrderVo.getCountry().getId());
						//busca el elemento smartcard
						serializedSC = businessSerialized.getSerializedBySerial(serialSCNr,workOrderVo.getCountry().getId());
						//valida el decodificador y el SC
						if (serializedDeco != null && serializedDeco.getElementId() != null 
							&& serializedSC != null && serializedSC.getElementId() != null){
							//si la bodega de origen es la del crew y al destino la del cliente
							if (crewWHSourceCustomerWHTarget && !customerWHSourceCrewWHTarget){
								String movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity();
								String movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity();
								
								//mueve de la bodega del crew a la bodega del cliente el SC
								//IVR movimiento de inventario
								/*ElementMovementDTO dto1 = new ElementMovementDTO(crewWarehouseVO.getId(), customerWarehouseVO.getId(), serializedSC.getElementId(), movTypeCodeE, movTypeCodeS, null, null, serializedSC.getSerialCode(), null, true, null,null);
								businessWarehouseElement.moveElementToWareHouse( dto1 );*/
								
								//IVR movimiento de inventario
								//mueve de la bodega del crew a la bodega del cliente el deco
								/*ElementMovementDTO dto2 = new ElementMovementDTO(crewWarehouseVO.getId(), customerWarehouseVO.getId(), serializedDeco.getElementId(), movTypeCodeE, movTypeCodeS, null, null, serializedDeco.getSerialCode(), null, true, null,null);
								businessWarehouseElement.moveElementToWareHouse(dto2);*/
							}
							//si la bodega de origen es la del cliente y al destino la del crew
							if (!crewWHSourceCustomerWHTarget && customerWHSourceCrewWHTarget){
								String movTypeCodeE = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity();
								String movTypeCodeS = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity();
								
								//mueve de la bodega del cliente a la bodega del crew el SC
								//IVR movimiento de inventario
								/*ElementMovementDTO dto1 = new ElementMovementDTO(customerWarehouseVO.getId(), crewWarehouseVO.getId(), serializedSC.getElementId(), movTypeCodeE, movTypeCodeS, null, null, serializedSC.getSerialCode(), null, true, null,null);
								businessWarehouseElement.moveElementToWareHouse(dto1);*/
								
								
								//mueve de la bodega del cliente a la bodega del crew el deco
								//IVR movimiento de inventario
								/*ElementMovementDTO dto2 = new ElementMovementDTO(customerWarehouseVO.getId(), crewWarehouseVO.getId(), serializedDeco.getElementId(), movTypeCodeE, movTypeCodeS, null, null, serializedDeco.getSerialCode(), null, true, null,null);
								businessWarehouseElement.moveElementToWareHouse(dto2);*/
							}
						//deco o SC invalido
						}else{
							log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementsBetweenWareHouses/IvrBusinessBean ==");
							throw new BusinessException(IvrBusinessMessagesEnum.ELEMENT_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.ELEMENT_NOT_FOUND.getMessage());
						}
					//no encuentra la bodega del customer
					}else{
						log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementsBetweenWareHouses/IvrBusinessBean ==");
						throw new BusinessException(IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getMessage());
					}
				//no encuentra la bodega del crew
				}else{
					log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementsBetweenWareHouses/IvrBusinessBean ==");
					throw new BusinessException(IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getMessage());
				}
			}else{
				log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementsBetweenWareHouses/IvrBusinessBean ==");
				throw new BusinessException(IvrBusinessMessagesEnum.CREW_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CREW_NOT_FOUND.getMessage());
			}
		//si no se encontro la work order
		}else{
			log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementsBetweenWareHouses/IvrBusinessBean ==");
			throw new BusinessException(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getMessage());
		}
	}


	/**
	 * Metodo: Mueve un elemento (smartcard o decodificador) de la bodega origen a la bodega destino
	 * segun los parametros customerWHSourceCrewWHTarget y crewWHSourceCustomerWHTarget
	 * @param orderID Codigo de la work order
	 * @param countryCode Codigo del pais
	 * @param serial Numero de serie del elemento
	 * @param customerWHSourceCrewWHTarget True cuando el elemento es movido de la bodega del cliente a la del crew
	 * @param crewWHSourceCustomerWHTarget True cuando el elemento es movido de la bodega del crew a la del cliente
	 * @throws BusinessException 
	 * @author jforero 13/08/2010
	 */
	private void moveSerializedElementBetweenWareHouses(String orderID,
			String countryCode, 
			String serial, 
			boolean customerWHSourceCrewWHTarget,
			boolean crewWHSourceCustomerWHTarget) 
	throws BusinessException, PropertiesException,
	DAOSQLException, DAOServiceException{
		WorkOrderVO workOrderVo = null;
		List<WarehouseVO> listCrewWarehouseVo = null;
		List<WarehouseVO> listCustomerWarehouseVo = null;
		WarehouseVO customerWarehouseVO = null;
		WarehouseVO crewWarehouseVO = null;
		SerializedVO serialized = null;
		//busca la work order
		workOrderVo = businessWo.getWorkOrderByCodeAndCountry(orderID, countryCode);
		//Si encontro la work order
		if (workOrderVo != null && workOrderVo.getId() != null){
			WoAssignment woAssigment = woAssignmentDao.getLastDealerAssignmentByWoId(workOrderVo.getId());
			if (woAssigment != null && woAssigment.getCrewId() != null){
				//busca la bodega del crew
				listCrewWarehouseVo = warehouseBusinessBean.getWarehousesByDealerIdAndCrewId(workOrderVo.getDealerId(), woAssigment.getCrewId());
				if (listCrewWarehouseVo != null && listCrewWarehouseVo.size() > 0 
					&& listCrewWarehouseVo.get(0) != null){
					crewWarehouseVO = listCrewWarehouseVo.get(0);
					//busca la bodega del cliente
					listCustomerWarehouseVo = warehouseBusinessBean.getWarehousesByCustomerId(workOrderVo.getCustomer().getId());
					if (listCustomerWarehouseVo != null 
						&& listCustomerWarehouseVo.size() > 0 
						&& listCustomerWarehouseVo.get(0) != null){
						customerWarehouseVO = listCustomerWarehouseVo.get(0);
						//busca el elemento
						serialized = businessSerialized.getSerializedBySerial(serial,workOrderVo.getCountry().getId());
						//valida el elemento
						if(!(serialized != null && serialized.getElementId() != null)){
							log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
							throw new BusinessException(IvrBusinessMessagesEnum.ELEMENT_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.ELEMENT_NOT_FOUND.getMessage());
						}
					//no encuentra la bodega del customer
					}else{
						log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
						throw new BusinessException(IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CUSTOMER_WAREHOUSE_NOT_FOUND.getMessage());
					}
				//no encuentra la bodega del crew
				}else{
					log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
					throw new BusinessException(IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CREW_WAREHOUSE_NOT_FOUND.getMessage());
				}
			}else{
				log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
				throw new BusinessException(IvrBusinessMessagesEnum.CREW_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.CREW_NOT_FOUND.getMessage());
			}
		//si no se encontro la work order
		}else{
			log.debug("== Error al tratar de ejecutar la operacion moveSerializedElementBetweenWareHouses/IvrBusinessBean ==");
			throw new BusinessException(IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getCode(),IvrBusinessMessagesEnum.WORK_ORDER_NOT_FOUND.getMessage());
		}
	}

}
