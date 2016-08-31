package co.com.directv.sdii.ejb.business.stock.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementQueueBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementQueueHelperLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerProductDTO;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.EventIbs;
import co.com.directv.sdii.model.pojo.EventReasonIbs;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MovCmdStatusVO;
import co.com.directv.sdii.model.vo.PorccessLinkClientSerialLogVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EventIbsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EventReasonIbsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.PorccessLinkClientSerialLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.MovCmdQueueDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * EJB que implementa los métodos para realizar la configuración de los
 * informes que se realizan a IBS cuando se mueven elementos entre las
 * bodegas.
 * 
 * 
 * Session Bean implementation class MovementQueueBusinessBean
 * 
 * @author waguilera - 13/02/2012
 */

@Stateless(name = "MovementQueueBusinessBeanLocal", mappedName = "ejb/MovementQueueBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementQueueBusinessBean extends BusinessBase implements MovementQueueBusinessBeanLocal {
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB private PorccessLinkClientSerialLogDAOLocal porccessLinkClientSerialLogDAOLocal;
	
	@EJB
	private WorkOrderDAOLocal woDao;
	
	@EJB
	private ElementBusinessBeanLocal elementBusinessBean;
	
	@EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;

	@EJB(name="MovCmdLogDAOLocal", beanInterface=MovCmdLogDAOLocal.class)
	private MovCmdLogDAOLocal daoMovCmdLog;

	@EJB(name="MovCmdStatusDAOLocal", beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;
	
	@EJB(name="MovementQueueHelperLocal", beanInterface=MovementQueueHelperLocal.class)
	private MovementQueueHelperLocal helperMovementQueue;
	
	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal businessWarehouse;

	@EJB(name="UserDAOLocal", beanInterface= UserDAOLocal.class)
	private UserDAOLocal daoUser;
	
	@EJB(name="CoreStockBusinessLocal", beanInterface= CoreStockBusinessLocal.class)
	private CoreStockBusinessLocal businessCoreStock;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface= WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="WarehouseDAOLocal", beanInterface= WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="DealersDAOLocal", beanInterface= DealersDAOLocal.class)
	private DealersDAOLocal daoDealers;
	
	@EJB(name="EventIbsDAOLocal", beanInterface= EventIbsDAOLocal.class)
	private EventIbsDAOLocal daoEventIbs;
	
	@EJB(name="SerializedDAOLocal", beanInterface= SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface= WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface= CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal businessCoreWO;
	
	@EJB(name="DocumentClassDAOLocal", beanInterface= DocumentClassDAOLocal.class)
	private DocumentClassDAOLocal daoDocumentClass;
	
	@EJB(name="EventReasonIbsDAOLocal", beanInterface= EventReasonIbsDAOLocal.class)
	private EventReasonIbsDAOLocal daoEventReasonIbs;
	
	@EJB(name="MovementElementBusinessBeanLocal", beanInterface= MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB
	private WarehouseDAOLocal warehouseDao;
	
	private final static Logger log =  UtilsBusiness.getLog4J(MovementQueueBusinessBean.class);

	/**
	 * Default constructor. 
	 */
	public MovementQueueBusinessBean() {
	}

	@Override
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(MovCmdQueueFilterDTO dto, RequestCollectionInfo requestCollInfo, boolean isFromFrontal)
			throws BusinessException {
		log.debug("== Inicia getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getProcessType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		MovCmdQueueDTOResponse response = null;
		try {
			
			//Consulto el usuario 
        	User user = daoUser.getUserById(dto.getUserId());
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),	ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	
			
			response = daoMovCmdQueue.getMovementQueueHspToIbsByFilter(dto, user.getCountry().getId(), requestCollInfo);
			if(isFromFrontal){
				for(MovCmdQueueDTO dtoOut : response.getMovementlist()){
					if(dtoOut.getStatusCode().equals(CodesBusinessEntityEnum.MOV_CMD_STATUS_ERROR.getCodeEntity())
							&&dtoOut.getIsManagment().equals(CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR.getCodeEntity())){
						dtoOut.setShowButtonComments(true);
					}
					dtoOut.setListResultProcess(daoMovCmdLog.getLogByMovementId(dtoOut.getId()));
					if(dtoOut.getListResultProcess().size()>0){
						dtoOut.setShowObservations(true);
					}
				}				
			}
			return response;
		} catch (Throwable be){
			log.error("== Error getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getMovementQueueHspToIbsByFilter/MovementQueueBusinessBean ==");
		}
	}
	
	

	@Override
	public List<MovCmdStatusVO> getStatusByTypeMovement(String typeMovement)
			throws BusinessException {
		log.debug("== Inicia getStatusByTypeMovement/MovementQueueBusinessBean ==");
		List<MovCmdStatusVO> listStatusResponse = new ArrayList<MovCmdStatusVO>();
		try {
			
			List<MovCmdStatus> listStatus = new ArrayList<MovCmdStatus>();
			if(typeMovement.equals(CodesBusinessEntityEnum.TYPE_COMUNICATION_HSP_TO_IBS.getCodeEntity())){
				listStatus = daoMovCmdStatus.getMovCmdStatusHspToIbs();
			}else if(typeMovement.equals(CodesBusinessEntityEnum.TYPE_COMUNICATION_IBS_TO_HSP.getCodeEntity())){
				listStatus = daoMovCmdStatus.getMovCmdStatusIbsToHsp();
			}
			listStatusResponse = UtilsBusiness.convertList(listStatus, MovCmdStatusVO.class);
			return listStatusResponse;
		} catch (Throwable be){
			log.error("== Error getStatusByTypeMovement/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina getStatusByTypeMovement/MovementQueueBusinessBean ==");
		}
	}
	/**
	 * Operacion encargada de la creación en el log y procesamiento de los 
	 * movimientos del inventario a partir de las Wo de upgrade y downgrade
	 * @param movCmdQueue
	 * @param request
	 * @param user
	 * @throws BusinessException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void setMovCmdQueuForUpgradeOrDowngradeWO(MovCmdQueue movCmdQueue, MovementInventoryDTO request, User user) throws BusinessException{
		log.debug("== Inicia setMovCmdQueuForUpgradeOrDowngradeWO/MovementQueueBusinessBean ==");
		
		try{
			
			if(this.movementIsError(request, movCmdQueue, user)){
				return;
			}
			
			UtilsBusiness.assertNotNull(request.getEventId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(request.getReasonId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(request.getSerial(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			movCmdQueue.setHistoryEventId(request.getUpgradeAndDowngradeHistoryEvent());
			request.setSwapHistoryEvent(request.getUpgradeAndDowngradeHistoryEvent());
			if(this.isProcessHistoryEventForSwap(request.getUpgradeAndDowngradeHistoryEvent(), movCmdQueue.getCustomer().getId(), user.getCountry().getId())){
				log.info("== Este registro de upgrade o downgrade ya fue procesado por hsp. HSP lo ignora==");
			}else{
				//Consultar cliente y su ubicación 
				Customer customer = null;
				customer = businessCoreWO.getCustomerById(request.getCustomerId());
				Warehouse customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
				Dealer dealer = daoDealers.getDealerByDealerCode(request.getDealerCode());
				
				if(customerWarehouse == null){
					this.businessWarehouse.createCustomerWarehouse(customer.getCustomerCode(), user.getCountry().getCountryCode());
					customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
				}
				
				//Verificar tipo de movimiento
				EventReasonIbs eventReasonIbs = this.validateMovementToCustomerOrToCompany(request.getEventId(), request.getReasonId(), user.getCountry().getId());

				if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_COMPANY_FOR_SWAP.getCodeEntity())){
					//Obtener la ubicacion destino de la compañia
					String warehouseType = "";
					if(eventReasonIbs.getWarehouseType() == null){
						warehouseType = null;
					}else{
						warehouseType = eventReasonIbs.getWarehouseType().getWhTypeCode();
					}
					Warehouse warehouseTarget = null;
					if(warehouseType!=null){
						 warehouseTarget = this.searchWarehouseCompanyByWarehouseType(dealer, warehouseType);
					}
				
					movCmdQueue.setSourceWarehouse(customerWarehouse);
					movCmdQueue.setTargetWarehouse(warehouseTarget);
					//Recuperar el elemento del cliente
					if(!this.daoWarehouseElement.existElementInCustomer(request.getCustomerId(), request.getSerial())){
						doMovementToClient(movCmdQueue,customerWarehouse,eventReasonIbs,request,dealer,user);
					}
					this.recoveryElementOfCustomer(eventReasonIbs, request.getSerial(), customer, customerWarehouse, warehouseTarget, user, movCmdQueue, request);
				}else if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_CUSTOMER_FOR_SWAP.getCodeEntity())){
					movCmdQueue.setTargetWarehouse(customerWarehouse);
					this.registerElementInCustomer(eventReasonIbs, request.getSerial(), customerWarehouse, dealer, user, movCmdQueue, request);
				}else{
					log.error("Tipo de evento no valido para determinar el movimiento en hsp");
					throw new BusinessException("Tipo de evento no valido para determinar el movimiento en hsp");
				}
				
				//Crea el registro representando el movimiento del elemento
				this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderSuccess(movCmdQueue, user);
				
			}
		} catch (Throwable t){
			log.error("== Error setMovCmdQueuForUpgradeOrDowngradeWO/MovementQueueBusinessBean ==", t);
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderError(movCmdQueue, t.getMessage(), user);
		} finally {
			log.debug("== Termina setMovCmdQueuForUpgradeOrDowngradeWO/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * @throws BusinessException 
	 * 
	 */
	private void doMovementToClient(MovCmdQueue movCmdQueue, Warehouse customerWarehouse, EventReasonIbs eventReasonIbs, MovementInventoryDTO request, Dealer dealer, User user) throws BusinessException{
		MovCmdQueue movCmdQueueCopy = null;
		try{
			movCmdQueueCopy = UtilsBusiness.copyObject(MovCmdQueue.class, movCmdQueue);
			movCmdQueueCopy.setHistoryEventId(null);
			movCmdQueueCopy.setTargetWarehouse(customerWarehouse);
			this.registerElementInCustomer(eventReasonIbs, request.getSerial(), customerWarehouse, dealer, user, movCmdQueueCopy, request);
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderSuccess(movCmdQueueCopy, user);
		} catch (Throwable t){
			log.error("== Error setMovCmdQueuForUpgradeOrDowngradeWO/MovementQueueBusinessBean ==", t);
			if(movCmdQueueCopy!=null){
				//se coloca para que el log de auditoria de errores tome el movimiento hacia el cliente, y no el que se desea definitivo de recuperacion
				movCmdQueue = movCmdQueueCopy;
			}
			throw this.manageException(t);
		}
	}
	
	/**
	 * Metodo encargado de almacenar el registro de los movimientos pendientes a partir de la Wo descargadas
	 * @param movCmdQueueVO
	 * @throws BusinessException
	 * @author waguilera
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void createQueueMovementInventoryByWorkOrder(
			MovementInventoryListDTO request) throws BusinessException {
		log.debug("== Inicia createQueueMovementInventoryByWorkOrder/MovementQueueBusinessBean ==");
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getCustomerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		try {
			
			User user = daoUser.getUserById(request.getUserId());
    		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			
    		proccessMovementInventoryListDTO(request, user);
    		linkSerialClients(request);
    		
		} catch (Throwable be){
			log.error("== Error createQueueMovementInventoryByWorkOrder/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina createQueueMovementInventoryByWorkOrder/MovementQueueBusinessBean ==");
		}
		
	}
	
	/**
	 * Metodo encargado de generar los movimientos de inventarios de una work order y de vincular los seriales en cliente en caso de ser necesario
	 * @param request Lista de movimientos de inventario e informacion requerida para realizar la vinculacion de elementos en cliente.
	 * @param user usuario de IBS
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void proccessMovementInventoryListDTO(
			MovementInventoryListDTO request, User user) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException {
		for(MovementInventoryDTO midto: request.getMovementInventoryDTOList()){
			try{
				MovCmdQueue movCmdQueue = new MovCmdQueue();
				this.fillMovCmdQueueInitial(movCmdQueue, midto, user);
				this.evaluateTypeProcessMovementInventory(midto, movCmdQueue);
				if(midto.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_ACTIVATION.getCodeEntity())){
					movCmdQueue.setWorkOrder(new WorkOrder(midto.getWoId()));
					this.setMovCmdQueuForActivationWO(movCmdQueue, midto, user);
				}else if(midto.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_DISCONNECTION.getCodeEntity())){
					movCmdQueue.setWorkOrder(new WorkOrder(midto.getWoId()));
					this.setMovCmdQueuForRecoveryWO(movCmdQueue, midto, user);
				}else if(midto.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_SWOP.getCodeEntity())){
					this.setMovCmdQueuForSwopWO(movCmdQueue, midto, user);
				}else if(midto.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_UPGRADE_AND_DOWNGRADE.getCodeEntity())){
					this.setMovCmdQueuForUpgradeOrDowngradeWO(movCmdQueue, midto, user);
				}else{
					this.setMovCmdQueuForSwopWO(movCmdQueue, midto, user);
				}
			}catch(Throwable e){
				log.error("fallo en la operacion proccessMovementInventoryListDTO con el movimiento "+midto+" "+e);
			}

		}
	}
	
	/**
	 * Mrtodo encargado de vincular de ser posible los elementos en el cliente de acuerdo con la informacion de vista360
	 * @param request DTO con la informacion de seriales vinculados en cliente.
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author aharker
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void linkSerialClients(MovementInventoryListDTO request) throws DAOServiceException, DAOSQLException, BusinessException {
		WorkOrder wo = woDao.getWorkOrderByID(request.getWoId());

		if(wo== null){
			throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
					.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
		}

		Date dateNow = UtilsBusiness.getDateLastChangeOfUser(wo.getCountry().getId(), userDao, systemParameterDAO);
		
		List<Warehouse> wareHousesClient=this.warehouseDao.getWarehousesByCustomerId(request.getCustomerId());
		
		List<Serialized> serializedClient = new ArrayList<Serialized>();
		
		for(Warehouse wh: wareHousesClient){
			List<Serialized> serializedwh = this.daoSerialized.getSerializedsByWareHouseId(wh);
			serializedClient.addAll(serializedwh);
			
		}

		//la llave es el serial, el arreglo es de dos posiciones en la primera esta el deco y en la segunda la smartcard, cualquiera de los dos podria ser nulo
		Map<String, Serialized[]> clientSerials= new HashMap<String, Serialized[]>();

		for(Serialized s: serializedClient){
			Serialized[] linkedActualSerials = new Serialized[2];
			if(isDecoOrCard(s)){
				if(isDeco(s)){
					linkedActualSerials[0] = s;
				}else if(isCard(s)){
					linkedActualSerials[1] = s;
				}
			}
			if(s.getSerialized()!=null){
				if(isDecoOrCard(s.getSerialized())){
					if(isDeco(s.getSerialized())){
						linkedActualSerials[0] = s.getSerialized();
					}else if(isCard(s.getSerialized())){
						linkedActualSerials[1] = s.getSerialized();
					}
				}
			}
			clientSerials.put(s.getSerialCode(), linkedActualSerials);
			if(s.getSerialized()!=null){
				clientSerials.put(s.getSerialized().getSerialCode(), linkedActualSerials);
			}
		}
		
		for(CustomerProductDTO cpdto: request.getCustomerProductDTO()){
			try{
				//Se verifica si vienen ambos seriales de vista360
				if(cpdto.getProvisionedDevices()!=null
						&& cpdto.getProvisionedDevicesRelated()!=null){
					//se verifica que ambos seriales existan en la consulta que se hizo del cliente
					if(clientSerials.containsKey(cpdto.getProvisionedDevices())
							&& clientSerials.containsKey(cpdto.getProvisionedDevicesRelated())){
						
						if(clientSerials.get(cpdto.getProvisionedDevices())[0]!=null && clientSerials.get(cpdto.getProvisionedDevices())[1]!=null
								&& clientSerials.containsKey(cpdto.getProvisionedDevicesRelated())){
							if(!clientSerials.get(cpdto.getProvisionedDevices())[0].getSerialCode().equalsIgnoreCase(cpdto.getProvisionedDevicesRelated())
									&& !clientSerials.get(cpdto.getProvisionedDevices())[1].getSerialCode().equalsIgnoreCase(cpdto.getProvisionedDevicesRelated())){
								this.businessWarehouseElement.separateLinkedSerializedElementsInWh(wareHousesClient.get(0).getId(), 
										clientSerials.get(cpdto.getProvisionedDevices())[0].getElementId(), 
										clientSerials.get(cpdto.getProvisionedDevices())[1].getElementId());
								Serialized first=clientSerials.get(cpdto.getProvisionedDevices())[0];
								Serialized second=clientSerials.get(cpdto.getProvisionedDevices())[1];
								clientSerials.remove(first.getSerialCode());
								clientSerials.remove(second.getSerialCode());
								Serialized[] firstArray = new Serialized[2];
								Serialized[] secondArray = new Serialized[2];
								firstArray[0]=first;
								secondArray[1]=second;
								clientSerials.put(first.getSerialCode(), firstArray);
								clientSerials.put(second.getSerialCode(), secondArray);
							}
						}
						
						if(clientSerials.get(cpdto.getProvisionedDevicesRelated())[0]!=null && clientSerials.get(cpdto.getProvisionedDevicesRelated())[1]!=null
								&& clientSerials.containsKey(cpdto.getProvisionedDevices())){
							if(!clientSerials.get(cpdto.getProvisionedDevicesRelated())[0].getSerialCode().equalsIgnoreCase(cpdto.getProvisionedDevices())
									&& !clientSerials.get(cpdto.getProvisionedDevicesRelated())[1].getSerialCode().equalsIgnoreCase(cpdto.getProvisionedDevices())){
								this.businessWarehouseElement.separateLinkedSerializedElementsInWh(wareHousesClient.get(0).getId(), 
										clientSerials.get(cpdto.getProvisionedDevicesRelated())[0].getElementId(), 
										clientSerials.get(cpdto.getProvisionedDevicesRelated())[1].getElementId());
								Serialized first=clientSerials.get(cpdto.getProvisionedDevicesRelated())[0];
								Serialized second=clientSerials.get(cpdto.getProvisionedDevicesRelated())[1];
								clientSerials.remove(first.getSerialCode());
								clientSerials.remove(second.getSerialCode());
								Serialized[] firstArray = new Serialized[2];
								Serialized[] secondArray = new Serialized[2];
								firstArray[0]=first;
								secondArray[1]=second;
								clientSerials.put(first.getSerialCode(), firstArray);
								clientSerials.put(second.getSerialCode(), secondArray);
							}
						}
						
						if(clientSerials.get(cpdto.getProvisionedDevices())[0]!=null && clientSerials.get(cpdto.getProvisionedDevices())[1]!=null){
							
							String message = " No se vincularan los seriales provenientes de IBS ProvisionedDevices = "+cpdto.getProvisionedDevices()
								+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated()+" porque el serial "+cpdto.getProvisionedDevices()+" ya esta vinculado en HSP de la siguiente manera: "
								+ " principal: "+(clientSerials.get(cpdto.getProvisionedDevices())[0].getSerialCode())+" vinculado: "+(clientSerials.get(cpdto.getProvisionedDevices())[1].getSerialCode());
							
							PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
								, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
							
							this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
							
							log.info(message);
						}
						else{
							if(clientSerials.get(cpdto.getProvisionedDevicesRelated())[0]!=null && clientSerials.get(cpdto.getProvisionedDevicesRelated())[1]!=null){
								
								String message = " No se vincularan los seriales provenientes de IBS ProvisionedDevices = "+cpdto.getProvisionedDevices()
									+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated()+" porque el serial "+cpdto.getProvisionedDevicesRelated()+" ya esta vinculado en HSP de la siguiente manera: "
									+ " principal: "+(clientSerials.get(cpdto.getProvisionedDevices())[0].getSerialCode())+" vinculado: "+(clientSerials.get(cpdto.getProvisionedDevices())[1].getSerialCode());
							
								PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
									, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
							
								this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
								
								log.info(message);
							}
							else{
								int indexProvisionedDevicesRelated=-1;
								int indexProvisionedDevices=-1;
								if(clientSerials.get(cpdto.getProvisionedDevices())[0]!=null){
									indexProvisionedDevices = 0;
								}else if(clientSerials.get(cpdto.getProvisionedDevices())[1]!=null){
									indexProvisionedDevices = 1;
								}
								if(clientSerials.get(cpdto.getProvisionedDevicesRelated())[0]!=null){
									indexProvisionedDevicesRelated = 0;
								}else if(clientSerials.get(cpdto.getProvisionedDevicesRelated())[1]!=null){
									indexProvisionedDevicesRelated = 1;
								}
								if(indexProvisionedDevicesRelated!=-1 && indexProvisionedDevices!=-1 
										&& indexProvisionedDevicesRelated!=indexProvisionedDevices){
									if(indexProvisionedDevices==0){
										elementBusinessBean.linkSerializedElements(clientSerials.get(cpdto.getProvisionedDevices())[0].getElementId()
												,(clientSerials.get(cpdto.getProvisionedDevicesRelated())[1].getElementId()));
									}
									else{
										elementBusinessBean.linkSerializedElements(clientSerials.get(cpdto.getProvisionedDevicesRelated())[0].getElementId()
												, (clientSerials.get(cpdto.getProvisionedDevices())[1].getElementId()));
									}
									String message = " se vincularon los seriales provenientes de IBS "
										+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated()+" con getProvisionedDevices =  "+cpdto.getProvisionedDevices();
								
									PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
										, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
								
									this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
								}
							}
						}
						
					}
					else{
						
						String message = "Alguno de los dos seriales provenientes de IBS o los dos, no existen en la ubicacion del cliente ProvisionedDevices = "+cpdto.getProvisionedDevices()
							+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated();
				
						PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
							, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
				
						this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
					
						log.info(message);

					}
				}
				else{
					
					String message = " Uno de los dos seriales que venian del cliente en vista360 de IBS es nulo ProvisionedDevices = "+cpdto.getProvisionedDevices()
						+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated();
		
					PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
						, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
					
					this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
			
					log.info(message);
					
				}
			}catch (Exception e) {
				
				String message = "ocurrio un error intentado vincular los seriales ProvisionedDevices = "+cpdto.getProvisionedDevices()
					+ " ProvisionedDevicesRelated = "+cpdto.getProvisionedDevicesRelated()+e;
				
				message=message.substring(0, message.length()>3950?3950:message.length());

				PorccessLinkClientSerialLogVO messageLog = new PorccessLinkClientSerialLogVO(null, new WorkOrder(wo.getId())
					, cpdto.getProvisionedDevicesRelated(), cpdto.getProvisionedDevices(), message, new Timestamp(dateNow.getTime()));
			
				this.porccessLinkClientSerialLogDAOLocal.createPorccessLinkClientSerialLog(messageLog);
	
				log.info(message);
			}
		}
	}

	/**
	 * Metodo que identifica si un elemento serializado es deco o smartcard o si no es ninguno de los dos
	 * @param element elemento a verificar
	 * @return
	 * @throws BusinessException
	 * @author aharker
	 */
	private boolean isDecoOrCard(Serialized element) throws BusinessException{
		boolean status = false;
		try{
			String classDecoder = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity();
			String classCard = CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity();
				
			status =  (element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classDecoder) || element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classCard));
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación isDecoOrCard/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina isDecoOrCard/ElementBusinessBean ==");
        }
        return status;
	}
	
	/**
	 * Metodo que verifica si un elemento serializado es deco o no
	 * @param element elemento a verificar
	 * @return
	 * @throws BusinessException
	 * @author aharker
	 */
	private boolean isDeco(Serialized element) throws BusinessException{
		boolean status = false;
		try{
			String classDecoder = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity();
				
			status =  (element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classDecoder));
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación isDecoOrCard/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina isDecoOrCard/ElementBusinessBean ==");
        }
        return status;
	}
	
	/**
	 * Metodo que verifica si un elemento serializado es smart-card o no
	 * @param element elemento a verificar
	 * @return
	 * @throws BusinessException
	 * @author aharker
	 */
	private boolean isCard(Serialized element) throws BusinessException{
		boolean status = false;
		try{
			String classCard = CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity();
				
			status =  (element.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(classCard));
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación isDecoOrCard/ElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.info("== Termina isDecoOrCard/ElementBusinessBean ==");
        }
        return status;
	}
	
	/**
	 * Metodo encargado de la evaluar el tipo de proceso que se debe realizar
	 * según el tipo de WorkOrder.
	 * Se evalua si es de Activación, desconexión y swop
	 * @param dto
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void evaluateTypeProcessMovementInventory(MovementInventoryDTO dto, MovCmdQueue movCmdQueue) throws BusinessException{
		log.debug("== Inicia evaluateTypeProcessMovementInventory/MovementQueueBusinessBean ==");
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			String woTyperForMovementInventory = "";
			if(dto.getSwapHistoryEvent()!=null){
				woTyperForMovementInventory = CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_SWOP.getCodeEntity();
			}else if(dto.getUpgradeAndDowngradeHistoryEvent()!=null){
				if(dto.getUpgradeAndDowngradeHistoryEvent()==-1L){
					dto.setUpgradeAndDowngradeHistoryEvent(null);
				}
				woTyperForMovementInventory = CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_UPGRADE_AND_DOWNGRADE.getCodeEntity();
			}else if(dto.getWoType().equals(CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity())){
				woTyperForMovementInventory = CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_ACTIVATION.getCodeEntity();
			}else if(dto.getWoType().equals(CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity())){
				woTyperForMovementInventory = CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_DISCONNECTION.getCodeEntity();
			}
			movCmdQueue.setTypeWorkOrderForMovInv(woTyperForMovementInventory);
		} catch (Throwable be){
			log.error("== Error evaluateTypeProcessMovementInventory/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina evaluateTypeProcessMovementInventory/MovementQueueBusinessBean ==");
		}
	} 
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setCommentForMovementQueue(
			Long movementQueueId, String comment, Long userId)
			throws BusinessException {
		log.debug("== Inicia setCommentForMovementQueue/MovementQueueBusinessBean ==");
		try{
			User user = daoUser.getUserById(userId);
    		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			
			MovCmdQueue movCmdQueue =  this.daoMovCmdQueue.findById(movementQueueId);
			if(!movCmdQueue.getMovCmdStatus().getCmdStatusCode().equals(CodesBusinessEntityEnum.MOV_CMD_STATUS_ERROR.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN491.getCode(), ErrorBusinessMessages.STOCK_IN491.getMessage());
			}
			if(movCmdQueue.getIsManagment().equals(CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_COMMENT_ERROR.getCodeEntity())){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN492.getCode(), ErrorBusinessMessages.STOCK_IN492.getMessage());
			}
			movCmdQueue.setIsManagment(CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_COMMENT_ERROR.getCodeEntity());
			movCmdQueue.setManagmentUserId(user);
			movCmdQueue.setCommentManagment(comment);
			this.daoMovCmdQueue.updateMovCmdQueue(movCmdQueue);
		} catch (Throwable be){
			log.error("== Error setCommentForMovementQueue/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina setCommentForMovementQueue/MovementQueueBusinessBean ==");
		}
	}
	
	
	/**
	 * Método encargado de capturar el error y almacenarlo en la base de datos  
	 * @param errorCode
	 * @throws BusinessException
	 * @author waguilera
	 */
	private boolean movementIsError(MovementInventoryDTO request, MovCmdQueue movCmdQueue, User user) throws BusinessException{
		log.info("== Termina movementIsError/MovementQueueBusinessBean ==");
		try{
			boolean isError = false;
			if(request.getErrorCode()!=null && request.getErrorCode().length()>0){
				this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderError(movCmdQueue, request.getMessageError(), user);
				isError = true;
			}
			return isError;
		}catch(Throwable t){ 
			log.error("== Error movementIsError/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina movementIsError/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Operacion encargada de la creación en el log y procesamiento de los 
	 * movimientos del inventario a partir de las Wo de activación
	 * @param mov
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void setMovCmdQueuForActivationWO(MovCmdQueue movCmdQueue, MovementInventoryDTO request, User user) throws BusinessException{
		log.debug("== Inicia setMovCmdQueuForActivationWO/MovementQueueBusinessBean ==");
		try{
			
			if(this.movementIsError(request, movCmdQueue, user)){
				return;
			}
			UtilsBusiness.assertNotNull(request.getDealerCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			DocumentClass documentClass = daoDocumentClass.getDocumentClassByCode(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity());
			movCmdQueue.setDocumentClass(documentClass);
			
			//Consultar la ubicación del cliente
			Customer customer = null;
			customer = businessCoreWO.getCustomerById(request.getCustomerId());
			Warehouse customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
			Dealer dealer = daoDealers.getDealerByDealerCode(request.getDealerCode());
			
			if(customerWarehouse == null){
				this.businessWarehouse.createCustomerWarehouse(customer.getCustomerCode(), user.getCountry().getCountryCode());
				customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
			}
			movCmdQueue.setTargetWarehouse(customerWarehouse);
			
			//Crear objetos para la asignacion de seriales
			WOAttentionElementsRequestDTO attentionElements = new WOAttentionElementsRequestDTO();
			List<WorkOrderServiceElementVO> listElement = this.validateSerialsForWorkOrderActivation(request, dealer, movCmdQueue,user);
			
			
			attentionElements.setInstallationSerializedElements(listElement);
			
			//Llenar el objeto de dto de inventarios para realizar
			//el registro de elementos serializados
			InventoryDTO inventoryDTO = new InventoryDTO();
			inventoryDTO.setUserId(user.getId());
			inventoryDTO.setDealer(UtilsBusiness.copyObject(DealerVO.class, dealer));
			inventoryDTO.setWorkOrderVO(UtilsBusiness.copyObject(WorkOrderVO.class, new WorkOrder(request.getWoId())));
			inventoryDTO.setCustomer(UtilsBusiness.copyObject(CustomerVO.class,  customer));

				//Ejecuta la operación de registro de elementos serializados a la ubicación del cliente
			this.businessCoreStock.registerSerializedResourcesForInstallation(attentionElements, inventoryDTO, customerWarehouse);
			
			//Crea el registro representando el movimiento del elemento
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderSuccess(movCmdQueue, user);
		}catch(Throwable t){ 
			log.error("== Error setMovCmdQueuForActivationWO/MovementQueueBusinessBean ==", t);
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderError(movCmdQueue, t.getMessage(), user);
		}finally{
			log.debug("== Termina setMovCmdQueuForActivationWO/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Operacion encargada de la creación en el log y procesamiento de los 
	 * movimientos del inventario a partir de las Wo de swap
	 * @param movCmdQueue
	 * @param request
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void setMovCmdQueuForSwopWO(MovCmdQueue movCmdQueue, MovementInventoryDTO request, User user) throws BusinessException{
		log.debug("== Inicia setMovCmdQueuForSwopWO/MovementQueueBusinessBean ==");
		
		try{
			
			if(this.movementIsError(request, movCmdQueue, user)){
				return;
			}
			
			UtilsBusiness.assertNotNull(request.getSwapHistoryEvent(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(request.getEventId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(request.getReasonId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(request.getSerial(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			movCmdQueue.setHistoryEventId(request.getSwapHistoryEvent());
			if(this.isProcessHistoryEventForSwap(request.getSwapHistoryEvent(), movCmdQueue.getCustomer().getId(), user.getCountry().getId())){
				log.info("== Este registro de swap ya fue procesado por hsp. HSP lo ignora==");
			}else{
				//Consultar cliente y su ubicación 
				Customer customer = null;
				customer = businessCoreWO.getCustomerById(request.getCustomerId());
				Warehouse customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
				Dealer dealer = daoDealers.getDealerByDealerCode(request.getDealerCode());
				
				if(customerWarehouse == null){
					this.businessWarehouse.createCustomerWarehouse(customer.getCustomerCode(), user.getCountry().getCountryCode());
					customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
				}
				
				//Verificar tipo de movimiento
				EventReasonIbs eventReasonIbs = this.validateMovementToCustomerOrToCompany(request.getEventId(), request.getReasonId(), user.getCountry().getId());
				
				
				
				if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_COMPANY_FOR_SWAP.getCodeEntity())){
					//Obtener la ubicacion destino de la compañia
					String warehouseType = "";
					if(eventReasonIbs.getWarehouseType() == null){
						warehouseType = null;
					}else{
						warehouseType = eventReasonIbs.getWarehouseType().getWhTypeCode();
					}
					Warehouse warehouseTarget = null;
					if(warehouseType!=null){
						 warehouseTarget = this.searchWarehouseCompanyByWarehouseType(dealer, warehouseType);
					}
				
					movCmdQueue.setSourceWarehouse(customerWarehouse);
					movCmdQueue.setTargetWarehouse(warehouseTarget);
					//Recuperar el elemento del cliente
					this.recoveryElementOfCustomer(eventReasonIbs, request.getSerial(), customer, customerWarehouse, warehouseTarget, user, movCmdQueue, request);
				}else if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_CUSTOMER_FOR_SWAP.getCodeEntity())){
					
					movCmdQueue.setTargetWarehouse(customerWarehouse);
					this.registerElementInCustomer(eventReasonIbs, request.getSerial(), customerWarehouse, dealer, user, movCmdQueue, request);
				}else{
					log.error("Tipo de evento no valido para determinar el movimiento en hsp");
					throw new BusinessException("Tipo de evento no valido para determinar el movimiento en hsp");
				}
				
				//Crea el registro representando el movimiento del elemento
				this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderSuccess(movCmdQueue, user);
				
			}
		} catch (Throwable t){
			log.error("== Error setMovCmdQueuForSwopWO/MovementQueueBusinessBean ==", t);
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderError(movCmdQueue, t.getMessage(), user);
		} finally {
			log.debug("== Termina setMovCmdQueuForSwopWO/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de recuperar un serial de la bodega del cliente 
	 * a raiz de un proceso swap.
	 * 
	 * @param eventReasonIbs
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void recoveryElementOfCustomer(EventReasonIbs eventReasonIbs, String serial, 
			Customer customer, Warehouse warehouseCustomer, Warehouse warehouseTarget,
			User user, MovCmdQueue movCmdQueue, MovementInventoryDTO request) throws BusinessException{
		log.debug("== Termina recoveryElementInCustomer/MovementQueueBusinessBean ==");
		try{
			//Validar que el serial que se desea recuperar exista en HSP+
			Serialized serialized = this.searchElementSerializedInWarehouse(serial,user.getCountry().getId());
			
			//Validar que el serial este en la ubicación del cliente
			this.getWarehouseForSerialCustomer(serialized.getElementId(), customer);
			
			//si el elemento es el principal y tiene un vinculado se desvincula
			if(serialized.getSerialized() != null){
				this.businessWarehouseElement.separateLinkedSerializedElementsInWh(warehouseCustomer.getId(), serialized.getElementId(), serialized.getSerialized().getElementId());
			}
			
			//Se verifica que el elemento no este siendo vinculado por otro elemento
			//Si es asi se desvincula
			List<Serialized> listSerialized = this.daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
			if(listSerialized!=null && listSerialized.size()>0){
				for(Serialized serLink: listSerialized){
					this.businessWarehouseElement.separateLinkedSerializedElementsInWh(warehouseCustomer.getId(), serLink.getElementId(), serialized.getElementId());
				}
			}
			
			//Asignar El elemento al objeto
			movCmdQueue.setSerialized(serialized);
			
			String whMovementTypeInventoryDownloadExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity();
			String whMovementTypeInventoryDownloadEntry = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity();
			
			if(request.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_UPGRADE_AND_DOWNGRADE.getCodeEntity())){
				whMovementTypeInventoryDownloadExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT_UPGRADE_DOWNGRADE.getCodeEntity();
				whMovementTypeInventoryDownloadEntry = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY_UPGRADE_DOWNGRADE.getCodeEntity();
			}
			
			//realizar el movimiento del elemento a la ubicacion del cliente
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(whMovementTypeInventoryDownloadEntry,
					whMovementTypeInventoryDownloadExit);
			
			if(warehouseTarget!=null){
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseCustomer), 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseTarget), 
						serialized, 
						null, 
						null, 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						false, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			}else{
				MovementElementDTO dtoMovement = new MovementElementDTO(user, 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseCustomer), 
						serialized, 
						null, 
						null, 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						false, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity(), 
						dtoGenerics.getMovConfigStatusPending(), 
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.deleteSerializedElementInWarehouse(dtoMovement);
			}

		} catch (Throwable t){
			log.error("== Error recoveryElementInCustomer/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina recoveryElementInCustomer/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de registrar un serial en la bodega del cliente 
	 * a raiz de un proceso swap.
	 * 
	 * @param eventReasonIbs
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void registerElementInCustomer(EventReasonIbs eventReasonIbs, String serial, Warehouse customerWarehouse,
			Dealer dealer, User user, MovCmdQueue movCmdQueue, MovementInventoryDTO request) throws BusinessException{
		log.debug("== Termina registerElementInCustomer/MovementQueueBusinessBean ==");
		try{
			//Validar que el serial que se desea recuperar exista en HSP+
			Serialized serialized = this.searchElementSerializedInWarehouse(serial,user.getCountry().getId());
			
			//Validar que el serial se encuetre en la ubicacion de la compañia
			Warehouse warehouseSource = this.getWarehouseForSerialInCompany(serialized, dealer);
			movCmdQueue.setSourceWarehouse(warehouseSource);
			
			//si el elemento es el principal y tiene un vinculado se desvincula
			if(serialized.getSerialized() != null){
				this.businessWarehouseElement.separateLinkedSerializedElementsInWh(warehouseSource.getId(), serialized.getElementId(), serialized.getSerialized().getElementId());
			}
			
			//Se verifica que el elemento no este siendo vinculado por otro elemento
			//Si es asi se desvincula
			List<Serialized> listSerialized = this.daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
			if(listSerialized!=null && listSerialized.size()>0){
				for(Serialized serLink: listSerialized){
					this.businessWarehouseElement.separateLinkedSerializedElementsInWh(warehouseSource.getId(), serLink.getElementId(), serialized.getElementId());
				}
			}
			
			//Asignar el elemento al objeto
			movCmdQueue.setSerialized(serialized);
			
			//realizar el movimiento del elemento a la ubicacion del cliente
			
			String whMovementTypeInventoryDownloadExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity();
			String whMovementTypeInventoryDownloasEntry = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity();
			
			if(request.getWoId()!=null && movCmdQueue.getTypeWorkOrderForMovInv()!=null && movCmdQueue.getTypeWorkOrderForMovInv().equals(CodesBusinessEntityEnum.TYPE_WORK_ORDER_FOR_INVENTORY_UPGRADE_AND_DOWNGRADE.getCodeEntity())){
				whMovementTypeInventoryDownloadExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT_UPGRADE_DOWNGRADE.getCodeEntity();
				whMovementTypeInventoryDownloasEntry = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY_UPGRADE_DOWNGRADE.getCodeEntity();
			}
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(whMovementTypeInventoryDownloasEntry,
					whMovementTypeInventoryDownloadExit);
			
			MovementElementDTO dtoMovement = new MovementElementDTO(user,
					UtilsBusiness.copyObject(WarehouseVO.class, warehouseSource), 
					UtilsBusiness.copyObject(WarehouseVO.class, customerWarehouse), 
					serialized, 
					null, 
					null, 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(),
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(), 
					false, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			
		} catch (Throwable t){
			log.error("== Error registerElementInCustomer/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina registerElementInCustomer/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de validar el tipo de movimiento para un proceso swap con
	 * respecto el resonId y al eventId
	 * @param eventId
	 * @param reasonId
	 * @param countryId
	 * @return  returna true si el movimiento es a la bodega del cliente
	 *  		returna false si el movimiento es a alguna bodega de la compañia 
	 * @author waguilera
	 */
	private EventReasonIbs validateMovementToCustomerOrToCompany(String eventCode, String reasonCode, Long countryId) throws BusinessException{
		log.debug("== Termina isMovementToCustomerOrToCompany/MovementQueueBusinessBean ==");
		EventReasonIbs eventReasonIbs = null;
		try{
			EventIbs eventIbs = this.daoEventIbs.getEventbyCode(eventCode, countryId);
			if(eventIbs == null){
				Object[] params = null;
				params = new Object[1];
				params[0] = eventCode;
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN497.getCode(), ErrorBusinessMessages.STOCK_IN497.getMessage(params));
			}
			
			eventReasonIbs = new EventReasonIbs();
			eventReasonIbs.setEventIbs(eventIbs);
			
			//Si es un evento de recuperacion de elementos
			//buscar si tene parametrizado una bodega destino
			if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_COMPANY_FOR_SWAP.getCodeEntity())){
				List<EventReasonIbs> listEventReasonIbs = this.daoEventReasonIbs.getConfigEventReasonIbsForEvent(eventReasonIbs.getEventIbs().getId());
				//Busco la reason de la lista obtenida
				for(EventReasonIbs eveIbs: listEventReasonIbs){
					if(eveIbs.getReasonIbs().getReasonCode().equals(reasonCode)){
						eventReasonIbs.setReasonIbs(eveIbs.getReasonIbs());
						eventReasonIbs.setWarehouseType(eveIbs.getWarehouseType());
					}
				}
			}else if(eventReasonIbs.getEventIbs().getTypeEvent().equals(CodesBusinessEntityEnum.TYPE_MOVEMENT_BY_REASON_TO_CUSTOMER_FOR_SWAP.getCodeEntity()))
				log.debug("Swap que deposita el elemento en la ubicacion del cliente");
			else{
				log.error("Tipo de movimiento del evento no valido para el proceso de swap");
				throw new BusinessException("Tipo de movimiento del evento no valido para el proceso de swap");
			}
			return eventReasonIbs;
		} catch (Throwable t){
			log.error("== Error isMovementToCustomerOrToCompany/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina isMovementToCustomerOrToCompany/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de verificar mediante el HistoryEventId si ya se procesó el 
	 * registro de swap 
	 * @param historyEventId
	 * @param customerId
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private boolean isProcessHistoryEventForSwap(Long historyEventId, Long customerId, Long countryId) throws BusinessException{
		log.debug("== Termina validateHistoryEventForSwap/MovementQueueBusinessBean ==");
		try{
			Long count =  this.daoMovCmdQueue.countHistoryEvent(customerId, historyEventId, countryId);
			return count.longValue() > 0L;
		} catch (Throwable be){
			log.error("== Error validateHistoryEventForSwap/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina validateHistoryEventForSwap/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Operacion encargada de la creación en el log y procesamiento de los 
	 * movimientos del inventario a partir de las Wo de recuperacion
	 * @param mov
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void setMovCmdQueuForRecoveryWO(MovCmdQueue movCmdQueue, MovementInventoryDTO request, User user) throws BusinessException{
		log.debug("== Inicia setMovCmdQueuForRecoveryWO/MovementQueueBusinessBean ==");
		
		try{
			
			if(this.movementIsError(request, movCmdQueue, user)){
				return;
			}
				
			
			DocumentClass documentClass = daoDocumentClass.getDocumentClassByCode(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity());
			movCmdQueue.setDocumentClass(documentClass);
			
			
			Customer customer = null;
			customer = businessCoreWO.getCustomerById(request.getCustomerId());
			
			//Consultar la ubicación del cliente
			Warehouse customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
			Dealer dealer = daoDealers.getDealerByDealerCode(request.getDealerCode());
			
			if(customerWarehouse == null){
				this.businessWarehouse.createCustomerWarehouse(customer.getCustomerCode(), user.getCountry().getCountryCode());
				customerWarehouse = this.businessWarehouse.getCustomerWarehouseByCountry(request.getCustomerId(), user.getCountry().getId());
			}
			movCmdQueue.setSourceWarehouse(customerWarehouse);
			
			//Buscar la ubicación de devoluciones de la compañia instaladora
			Warehouse warehouseCompanyRecovery = this.searchWarehouseCompanyByWarehouseType(dealer, CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity());
			movCmdQueue.setTargetWarehouse(warehouseCompanyRecovery);
				
			//Crear objetos para la asignacion de seriales
			WOAttentionElementsRequestDTO attentionElements = new WOAttentionElementsRequestDTO();
			List<WorkOrderServiceElementVO> listElement = this.validateSerialsForWorkOrderRecovery(request, dealer, movCmdQueue, customer,user);
			
			attentionElements.setRecoverySerializedElements(listElement);
			
			//Llenar el objeto de dto de inventarios para realizar
			//el registro de elementos serializados
			InventoryDTO inventoryDTO = new InventoryDTO();
			inventoryDTO.setUserId(user.getId());
			inventoryDTO.setDealer(UtilsBusiness.copyObject(DealerVO.class, dealer));
			inventoryDTO.setWorkOrderVO(UtilsBusiness.copyObject(WorkOrderVO.class, new WorkOrder(request.getWoId())));
			inventoryDTO.setCustomer(UtilsBusiness.copyObject(CustomerVO.class,  customer));
			inventoryDTO.setCountryId(user.getCountry().getId());
			
			//Se debe reportar la desconexion a ibs
			inventoryDTO.setProcessCodeIbsRecovery(CodesBusinessEntityEnum.PROCESS_CODE_IBS_RECOVERY.getCodeEntity());
			
			//Ejecuta la operación para retirar los elementos de la ubicacion del cliente
			this.businessCoreStock.registerSerializedResourcesForRecovery(attentionElements, inventoryDTO, customerWarehouse);
			
			//Crea el registro representando el movimiento del elemento
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderSuccess(movCmdQueue, user);
		} catch (Throwable be){
			log.error("== Error setMovCmdQueuForRecoveryWO/MovementQueueBusinessBean ==", be);
			this.helperMovementQueue.createMovCmdQueueRegisterByWorkOrderError(movCmdQueue, be.getMessage(), user);
		} finally {
			log.debug("== Termina setMovCmdQueuForRecoveryWO/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de la busqueda y verificación de ubicaciones de una compañia,
	 * En caso de no existir arroja un mensaje con el error presentado 
	 * @param dealer
	 * @param warehouseTypeCode
	 * @return
	 * @throws BusinessException
	 */
	private Warehouse searchWarehouseCompanyByWarehouseType(Dealer dealer, String warehouseTypeCode) throws BusinessException{
		log.debug("== Termina searchWarehouseForRecovery/MovementQueueBusinessBean ==");
		try{
			Warehouse warehouseCompanyRecovery = this.daoWarehouse.getWarehousesByDealerIdAndWhTypeCode(dealer.getId(), warehouseTypeCode);
			if(warehouseCompanyRecovery == null){
				Object[] params = null;
				params = new Object[2];
				params[0] = dealer.getDepotCode()+" "+dealer.getDealerName();
				params[1] = warehouseTypeCode;
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN499.getCode(), ErrorBusinessMessages.STOCK_IN499.getMessage(params));
			}
			return warehouseCompanyRecovery;
		} catch (Throwable be){
			log.error("== Error searchWarehouseForRecovery/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina searchWarehouseForRecovery/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Operacion encargada de validar y obtener los seriales
	 * del request para el proceso de WorkOrder de activacion 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private List<WorkOrderServiceElementVO> validateSerialsForWorkOrderActivation(MovementInventoryDTO request, Dealer dealer, MovCmdQueue movCmdQueue,User user) throws BusinessException{
		log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		List<WorkOrderServiceElementVO> listElements = new ArrayList<WorkOrderServiceElementVO>();
		try{
			
			boolean isSerial1 = request.getSerial()!=null && request.getSerial().length()>0;
			boolean isSerial2 = request.getSerial2()!=null && request.getSerial2().length()>0;
			
			/*
			if(!isSerial1 && !isSerial2){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN493.getCode(), ErrorBusinessMessages.STOCK_IN493.getMessage());
			}
			if(!(isSerial1 && isSerial2)){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN494.getCode(), ErrorBusinessMessages.STOCK_IN494.getMessage());
			}
			*/
			SystemParameter btbETypes = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_BTB_ELEMENTS_TYPES.getCodeEntity(),user.getCountry().getId());
			String elementsTypes = btbETypes.getValue() ;
			//String lista = "369, 222";
			
			
			Serialized serialBtb = daoSerialized.getSerializedBySerialAndElementsTypes(request.getSerial(), elementsTypes, user.getCountry().getId());
			
			if(!isSerial1)
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN493.getCode(), ErrorBusinessMessages.STOCK_IN493.getMessage());
			if(!isSerial2 && serialBtb==null)
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN494.getCode(), ErrorBusinessMessages.STOCK_IN494.getMessage());
			
			Serialized serialized1 =  this.searchElementSerializedInWarehouse(request.getSerial(),user.getCountry().getId());
			
			Serialized serialized2 = new Serialized();
			if(isSerial2)
				serialized2 = this.searchElementSerializedInWarehouse(request.getSerial2(),user.getCountry().getId());
			
			//Validar si la workOrder ya fué procesada por hsp para no procesarlo de nuevo
			if(this.daoMovCmdQueue.validateProcessForWorkOrder(movCmdQueue.getWorkOrder().getId(), 
					serialized1.getElementId(), 
					serialized2.getElementId()).longValue()>0){
				log.info("El movimiento de inventario fue realizado previamente para esta WorkOrder "
						+movCmdQueue.getWorkOrder().getWoCode()+"- Esta petición no se procesará");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN500.getCode(), ErrorBusinessMessages.STOCK_IN500.getMessage());
			}
			
			this.daoMovCmdQueue.validateProcessForWorkOrder(request.getWoId() , serialized1.getElementId(), serialized2.getElementId());
			
			Warehouse warehouseDeco = null;
			
			WorkOrderServiceElementVO workOrderServiceElement = new WorkOrderServiceElementVO();
			WorkOrderServiceElementVO workOrderServiceElementLinked = new WorkOrderServiceElementVO();
			
			//Se valida si el serial 1 es el deco
			if(serialized1.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
				workOrderServiceElement.setElementSerial(serialized1.getSerialCode());
				workOrderServiceElementLinked.setElementSerial(serialized2.getSerialCode());
				movCmdQueue.setSerialized(serialized1);
				//movCmdQueue.setSerializedLinked(serialized2);
				warehouseDeco = this.getWarehouseForSerialInCompany(serialized1, dealer);
				if (isSerial2){
					movCmdQueue.setSerializedLinked(serialized2);
				    this.getWarehouseForSerialInCompany(serialized2, dealer);
				}
			}
			

		    //No hay segundo serial y Se valida si el serial 2 es el deco
		    if(isSerial2 
		    	&& serialized2.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())){
			    workOrderServiceElement.setElementSerial(serialized2.getSerialCode());
			    workOrderServiceElementLinked.setElementSerial(serialized1.getSerialCode());
			    movCmdQueue.setSerialized(serialized2);
			    movCmdQueue.setSerializedLinked(serialized1);
			    warehouseDeco = this.getWarehouseForSerialInCompany(serialized2, dealer);
			    this.getWarehouseForSerialInCompany(serialized1, dealer);
		     }

			
			//Asignar como origen la ubicación donde se encuentre el deco
			movCmdQueue.setSourceWarehouse(warehouseDeco);
			workOrderServiceElement.setLinkedWOServiceElement(workOrderServiceElementLinked);
			listElements.add(workOrderServiceElement);
			return listElements;
		}catch(Throwable t){ 
			log.error("== Error validateSerialsForWorkOrder/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		}
	}
	
	
	
	
	/**
	 * Operacion encargada de validar y obtener los seriales
	 * del request para el proceso de WorkOrder de activacion 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private List<WorkOrderServiceElementVO> validateSerialsForWorkOrderRecovery(MovementInventoryDTO request, Dealer dealer, MovCmdQueue movCmdQueue, Customer customer,User user) throws BusinessException{
		log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		List<WorkOrderServiceElementVO> listElements = new ArrayList<WorkOrderServiceElementVO>();
		try{
			
			boolean isSerial1 = request.getSerial()!=null && request.getSerial().length()>0;
			boolean isSerial2 = request.getSerial2()!=null && request.getSerial2().length()>0;
			
			if(!isSerial1 && !isSerial2){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN493.getCode(), ErrorBusinessMessages.STOCK_IN493.getMessage());
			}
			
			Serialized serialized1 =  this.searchElementSerializedInWarehouse(request.getSerial(),user.getCountry().getId());
			Serialized serialized2 = null;
			if(serialized1.getSerialized()!=null && !isSerial2){
				serialized2 = serialized1.getSerialized();
				request.setSerial2(serialized2.getSerialCode());
				isSerial2 = true;
			}
			
			if(isSerial2){
				serialized2 = this.searchElementSerializedInWarehouse(request.getSerial2(),user.getCountry().getId());
			}
			
			//Validar si la workOrder ya fué procesada por hsp para no procesarlo de nuevo
			if(this.daoMovCmdQueue.validateProcessForWorkOrder(movCmdQueue.getWorkOrder().getId(), 
					serialized1.getElementId(), 
					serialized2 !=null ? serialized2.getElementId():null).longValue()>0){
				log.info("El movimiento de inventario fue realizado previamente para esta WorkOrder "
						+movCmdQueue.getWorkOrder().getWoCode()+"- Esta petición no se procesará");
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN500.getCode(), ErrorBusinessMessages.STOCK_IN500.getMessage());
			}
			
			//validar la existencia de los seriales en la ubicación del cliente
			this.getWarehouseForSerialCustomer(serialized1.getElementId(), customer);
			if(isSerial2){
				this.getWarehouseForSerialCustomer(serialized2.getElementId(), customer);
			}
			
			
			WorkOrderServiceElementVO workOrderServiceElement = new WorkOrderServiceElementVO();
			WorkOrderServiceElementVO workOrderServiceElementLinked = new WorkOrderServiceElementVO();
			
			movCmdQueue.setSerialized(serialized1);
			workOrderServiceElement.setElementSerial(serialized1.getSerialCode());
			if(isSerial2){
				workOrderServiceElementLinked.setElementSerial(serialized2.getSerialCode());
				movCmdQueue.setSerializedLinked(serialized2);
			}
			workOrderServiceElement.setLinkedWOServiceElement(workOrderServiceElementLinked);
			listElements.add(workOrderServiceElement);
			return listElements;
		}catch(Throwable t){ 
			log.error("== Error validateSerialsForWorkOrder/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Operación encargada de buscar un serial en el sistema y en caso
	 * de no encontrarlos escala la excepción con el mensaje indicando el serial
	 * no encontrado
	 * @param serial
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private Serialized searchElementSerializedInWarehouse(String serial,Long countryId) throws BusinessException{
		log.debug("== Termina searchElementSerializedInWarehouse/MovementQueueBusinessBean ==");
		try{
			Serialized serialized = daoSerialized.getSerializedBySerial(serial,countryId);
			if(serialized == null){
				Object[] params = null;
				params = new Object[1];
				params[0] = serial;
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN486.getCode(), ErrorBusinessMessages.STOCK_IN486.getMessage(params));
			}
			return serialized;
		}catch(Throwable t){ 
			log.error("== Error searchElementSerializedInWarehouse/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina searchElementSerializedInWarehouse/MovementQueueBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de la consulta de la ubicación donde se enceuntra el serial en ubicaciones de
	 * stock de la compañia, si no lo encuentra  retorna la excepción con el mensaje del error  
	 * @param serial
	 * @return
	 * @throws BusinessException
	 */
	private Warehouse getWarehouseForSerialInCompany(Serialized ser, Dealer dealer) throws BusinessException{
		log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		try{
			List<String> whTypeCodes = new ArrayList<String>();
			whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity());
			whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());
			whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity());
			WarehouseElement warehouseElement = this.daoWarehouseElement.getWarehouseElementSerializedLastByElementID(ser.getElementId());
			
			if(warehouseElement == null){
				Object[] params = null;
				params = new Object[1];
				params[0] = ser.getSerialCode();
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN498.getCode(), ErrorBusinessMessages.STOCK_IN498.getMessage(params));
			}
			
			if(warehouseElement.getWarehouseId().getDealerId()!=null 
					&&warehouseElement.getWarehouseId().getDealerId().getId().longValue() == dealer.getId().longValue()){
				boolean isStock = false;
				for(String typeWarehouse: whTypeCodes){
					if(warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(typeWarehouse)){
						isStock = true;
					}
				}
				if(!isStock){
					Object[] params = null;
					params = new Object[3];
					params[0] = warehouseElement.getSerialized().getSerialCode();
					params[1] = dealer.getDepotCode()+" - "+dealer.getDealerName();
					WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElement.getWarehouseId());
					this.businessWarehouse.genWareHouseName(whVo);
					params[2] = whVo.getWarehouseName();
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN495.getCode(), ErrorBusinessMessages.STOCK_IN495.getMessage(params));
				}
			}else{
				Object[] params = null;
				params = new Object[3];
				params[0] = warehouseElement.getSerialized().getSerialCode();
				params[1] = dealer.getDepotCode()+" - "+dealer.getDealerName();
				WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElement.getWarehouseId());
				this.businessWarehouse.genWareHouseName(whVo);
				params[2] = whVo.getWarehouseName();
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN495.getCode(), ErrorBusinessMessages.STOCK_IN495.getMessage(params));
			}
			return warehouseElement.getWarehouseId();
		}catch(Throwable t){ 
			log.error("== Error validateSerialsForWorkOrder/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		}
	}
	
	
	/**
	 * Metodo encargado de la consulta de la ubicación donde se enceuntra el serial en ubicaciones de
	 * stock de la compañia, si no lo encuentra  retorna la excepción con el mensaje del error  
	 * @param serial
	 * @return
	 * @throws BusinessException
	 */
	private Warehouse getWarehouseForSerialCustomer(Long elementId, Customer customer) throws BusinessException{
		log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		try{
			
			WarehouseElement warehouseElement = this.daoWarehouseElement.getWarehouseElementSerializedLastByElementID(elementId);
			
			if(warehouseElement == null){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN496.getCode(), ErrorBusinessMessages.STOCK_IN496.getMessage());
			}
			
			if(!(warehouseElement.getWarehouseId().getCustomerId()!=null 
					&&warehouseElement.getWarehouseId().getCustomerId().getId().longValue() == customer.getId().longValue())){
				
				Object[] params = null;
				params = new Object[3];
				params[0] = warehouseElement.getSerialized().getSerialCode();
				params[1] = customer.getCustomerCode()+" - "+customer.getFirstName()+" "+customer.getLastName();
				WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, warehouseElement.getWarehouseId());
				this.businessWarehouse.genWareHouseName(whVo);
				params[2] = whVo.getWarehouseName();
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN496.getCode(), ErrorBusinessMessages.STOCK_IN496.getMessage(params));
			}
			return warehouseElement.getWarehouseId();
		}catch(Throwable t){ 
			log.error("== Error validateSerialsForWorkOrder/MovementQueueBusinessBean ==", t);
			throw this.manageException(t);
		}finally{
			log.debug("== Termina validateSerialsForWorkOrder/MovementQueueBusinessBean ==");
		}
	}
	
	

	
	
	/**
	 * Metodo encargado de llenar el objeto con los datos genericos
	 * para generar la información de un movimiento
	 * @param movCmdQueue
	 * @param request
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void fillMovCmdQueueInitial(MovCmdQueue movCmdQueue, MovementInventoryDTO request, User user) throws BusinessException{
		log.debug("== Inicia fillMovCmdQueue/MovementQueueBusinessBean ==");
		try{
			movCmdQueue.setCreationDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			movCmdQueue.setCustomer(new Customer(request.getCustomerId()));
			movCmdQueue.setTypeComunication(CodesBusinessEntityEnum.TYPE_COMUNICATION_IBS_TO_HSP.getCodeEntity());
			movCmdQueue.setCountry(user.getCountry());
			movCmdQueue.setIsManagment(CodesBusinessEntityEnum.COMUNICATION_HSP_IBS_IS_NOT_COMMENT_ERROR.getCodeEntity());
		} catch (Throwable be){
			log.error("== Error fillMovCmdQueue/MovementQueueBusinessBean ==", be);
			throw this.manageException(be);
		} finally {
			log.debug("== Termina fillMovCmdQueue/MovementQueueBusinessBean ==");
		}
	}


}
