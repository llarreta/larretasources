package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.SwopElementsDTO;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.RequiredServiceElement;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * 
 * Implementación de métodos de negocio para exponer funcionalidades de inventarios al módulo de 
 * CORE 
 * 
 * Fecha de Creación: Nov 3, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="CoreStockBusinessLocal",mappedName="ejb/CoreStockBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreStockBusiness extends BusinessBase implements CoreStockBusinessLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreStockBusiness.class);
	
	@EJB	
	private WorkOrderBusinessBeanLocal workOrderBusiness; 
	
	@EJB(name="RequiredServiceElementDAOLocal", beanInterface=RequiredServiceElementDAOLocal.class)
	private RequiredServiceElementDAOLocal requiredServiceElementDAO;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal warehouseElementBusinessBean;
	
	@EJB(name="ElementBusinessBeanLocal", beanInterface=ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusinessBean;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal daoUser;
	
	@EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB(name = "SerializedDAOLocal", beanInterface = SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	
	public static final int CONST_SWAP_COMPLETE = 1;
	
	public static final int CONST_LEAVE_DECO_SWAP_SC = 2;
	
	public static final int CONST_LEAVE_SC_SWAP_DECO = 3;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal#getResourcesByServiceType(co.com.directv.sdii.model.dto.InventoryDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RequiredServiceElementDTO getResourcesByServiceType(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia getResourcesByServiceType/CoreStockBusiness ==");
		try{
			//Se validan los parametro necesarios para realizar la consulta
			String errorCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMsj = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(inventoryDTO, errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getServiceDTO(), errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getServiceDTO().getServiceId(), errorCode, errorMsj);
			
			RequiredServiceElementDTO dto = new RequiredServiceElementDTO();
			List<RequiredServiceElement> rqse = requiredServiceElementDAO.getRequiredServiceElementByService( inventoryDTO.getServiceDTO().getServiceId() );
			if( rqse == null || rqse.isEmpty() ){
				Object[] params = {inventoryDTO.getServiceDTO().getServiceCode()};
				ArrayList<String> paramsList = new ArrayList<String>();
				paramsList.add( inventoryDTO.getServiceDTO().getServiceCode() );
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN485.getCode(),ErrorBusinessMessages.STOCK_IN485.getMessage(params),paramsList);
			}
			RequiredServiceElementVO vo = new RequiredServiceElementVO();
			vo.setNotSerializedElements(rqse);
			dto.setRequiredServiceElement( vo );
			return dto;
		} catch (Throwable e) {
			log.error("== Error getResourcesByServiceType/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getResourcesByServiceType/CoreStockBusiness ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal#getSerializedResource(co.com.directv.sdii.model.dto.InventoryDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementDTO getSerializedResource(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia getSerializedResource/CoreStockBusiness ==");
		try{
			//Se validan los parametro necesarios para realizar la consulta
			String errorCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMsj = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			UtilsBusiness.assertNotNull(inventoryDTO, errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO, errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getElementDTO(), errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getElementDTO().getSerializedVO(), errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getElementDTO().getSerializedVO().getSerialCode(), errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getDealer(), errorCode, errorMsj);
			UtilsBusiness.assertNotNull(inventoryDTO.getDealer().getId(), errorCode, errorMsj);
			String serialToFind = inventoryDTO.getElementDTO().getSerializedVO().getSerialCode();
			Long dealerId = inventoryDTO.getDealer().getId();
			Long dealerCode = inventoryDTO.getDealer().getDealerCode();
			ElementDTO elementDTO = new ElementDTO();
			String exceptionMsj = null;
			String exceptionCode = null;
			List<String> paramsList = new ArrayList<String>();
			//En caso que se SWAP consulta tanto en la bodega de la compañia, sus sucursales, cuadrillas y en el cliente
			if( inventoryDTO.getIsSwap() != null && inventoryDTO.getIsSwap().booleanValue() ){
				List<String> whTypeCodes = new ArrayList<String>();
				whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity() );
				whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity() );
				whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity() );
				WarehouseElement pojo = this.getWarehouseElementWhBySerialAndDealerCodeOrId(serialToFind,dealerCode, null,whTypeCodes,false);
				if( pojo != null && pojo.getSerialized() != null ){
					elementDTO.setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, pojo.getSerialized()) );
					this.setDeviceTypeInventoryInterface(elementDTO);
					return elementDTO;
				}
				//Consulta el DECO en la bodega del cliente
				pojo = this.warehouseElementDAO.getElementBySerialAndCustomerCode(serialToFind,inventoryDTO.getCustomer().getCustomerCode());
				if( pojo != null && pojo.getSerialized() != null ){
					elementDTO.setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, pojo.getSerialized()) );
					this.setDeviceTypeInventoryInterface(elementDTO);
					return elementDTO;
				}
				//En caso que no haya retornado un elemento indica que no encontro el elemento
				Object[] params = null;
				params = new Object[2];
				params[0] = serialToFind;
				paramsList.add(serialToFind);
				exceptionCode = ErrorBusinessMessages.STOCK_IN486.getCode();
				exceptionMsj = ErrorBusinessMessages.STOCK_IN486.getMessage(params);
			//Caso que no sea de swap se valida
			}else {
				//Caso que se de instalacion se busca en la bodega de la compañia y sus suscrusales o cuadrilllas
				if( inventoryDTO.getIsInstallation() != null && inventoryDTO.getIsInstallation().booleanValue() ){
					List<String> whTypeCodes = new ArrayList<String>();
					whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity() );
					whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity() );
					whTypeCodes.add( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity() );
					WarehouseElement pojo = this.getWarehouseElementWhBySerialAndDealerCodeOrId(serialToFind, dealerCode,null, whTypeCodes,true);
					if( pojo != null && pojo.getSerialized() != null ){
						elementDTO.setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, pojo.getSerialized()) );
						this.setDeviceTypeInventoryInterface(elementDTO);
						return elementDTO;
					}
					Object[] params = null;
					params = new Object[2];
					params[0] = serialToFind;
					params[1] = ApplicationTextEnum.AVAILABLE.getApplicationTextValue();
					paramsList.add(serialToFind);
					paramsList.add(ApplicationTextEnum.AVAILABLE.getApplicationTextValue());
					exceptionCode = ErrorBusinessMessages.INVENTORY_IN333.getCode();
					exceptionMsj = ErrorBusinessMessages.INVENTORY_IN333.getMessage(params);
					
				} else {
					//Consulta el DECO en la bodega del cliente
					WarehouseElement pojo = this.warehouseElementDAO.getElementBySerialAndCustomerCode(serialToFind,inventoryDTO.getCustomer().getCustomerCode());
					if( pojo != null && pojo.getSerialized() != null ){
						elementDTO.setSerializedVO( UtilsBusiness.copyObject(SerializedVO.class, pojo.getSerialized()) );
						this.setDeviceTypeInventoryInterface(elementDTO);
						return elementDTO;
					}
					Object[] params = null;
					params = new Object[2];
					params[0] = serialToFind;
					params[1] = inventoryDTO.getCustomer().getCustomerCode();
					paramsList.add(serialToFind);
					paramsList.add(inventoryDTO.getCustomer().getCustomerCode());
					exceptionCode = ErrorBusinessMessages.STOCK_IN484.getCode();
					exceptionMsj = ErrorBusinessMessages.STOCK_IN484.getMessage(params);
				}
			}
			throw new BusinessException(exceptionCode,exceptionMsj,paramsList) ;
		} catch (Throwable e) {
			log.error("== Error getSerializedResource/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getSerializedResource/CoreStockBusiness ==");
		}
	}
	
	/**
	 * Metodo: Le da valor a propiedad necesaria para validaciones de modilo de CORE
	 * @param elementDTO
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jnova
	 */
	private void setDeviceTypeInventoryInterface( ElementDTO elementDTO ) throws PropertiesException{
		if( elementDTO != null && elementDTO.getSerializedVO() != null && elementDTO.getSerializedVO().getElement() != null ){
			if( elementDTO.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity() ) ){
				elementDTO.getSerializedVO().setDeviceTypeId( CodesBusinessEntityEnum.INVENTORY_INTERFACE_ELEMENT_TYPE_DECO.getCodeEntity() );
			} else if( elementDTO.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity() ) ){
				elementDTO.getSerializedVO().setDeviceTypeId( CodesBusinessEntityEnum.INVENTORY_INTERFACE_ELEMENT_TYPE_SMARTCARD.getCodeEntity() );
			} else {
				elementDTO.getSerializedVO().setDeviceTypeId( "" );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal#registerNotSerializedResources(co.com.directv.sdii.model.dto.InventoryDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EnvelopeEncapsulateResponse registerNotSerializedResources(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia registerNotSerializedResources/CoreStockBusiness ==");
		EnvelopeEncapsulateResponse response = new EnvelopeEncapsulateResponse();
		try{
			//Se validan campos obligatorios
			this.validateRegisterNotSerializedResources(inventoryDTO);
			User user = daoUser.getUserById(inventoryDTO.getUserId());
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}

			for (WorkOrderServiceVO workOrderServiceVO : inventoryDTO.getWoAttentionDTO().getWorkorderServices()) {
				this.validateRegisterNotSerializedResourceRequest( workOrderServiceVO );
				
				for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElements()) {
					this.validateRegisterNotSerializedResourceRequest(workOrderServiceElementVO);
					//Consula la bodega del cliente
					Warehouse customerWarehouse = this.getCustomerWarehouse(inventoryDTO.getCustomer().getCustomerCode(),inventoryDTO.getCountryCode());
					//Si la cantidad es positiva, consulta la bodega de tipo ‘S01’ de la cuadrilla de la entrada 1. El sistema 
					//valida que la cuadrilla tenga una bodega de tipo S01 y hace llamado al Caso de Uso INV 38 enviándole como 
					//bodega de origen la bodega de la cuadrilla de tipo ‘S01’ y bodega destino la bodega del cliente.
					if( workOrderServiceElementVO.getQuantity().doubleValue() > 0 ){
						//consulta la bodega de tipo ‘S01’ perteneciente a la cuadrilla asignada a la WO
						Long whTypeId = CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getIdEntity( WarehouseType.class.getName() );
						List<Warehouse> crewWhs = this.warehouseDAO.getWhByCrewAndWhType(inventoryDTO.getAssignCrewId(),whTypeId);
						if( crewWhs == null || crewWhs.isEmpty() ){
							Object[] params = {workOrderServiceElementVO.getElementTypeCode()};
							List<String> paramsList = new ArrayList<String>();
							paramsList.add( workOrderServiceElementVO.getElementTypeCode() );
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN471.getCode(),ErrorBusinessMessages.STOCK_IN471.getMessage(params),paramsList);
						}
						//Llamado al Caso de Uso INV 38
						
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
								CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity());

						MovementElementDTO dtoMovement = new MovementElementDTO(user,
								UtilsBusiness.copyObject(WarehouseVO.class, crewWhs.get(0)), 
								UtilsBusiness.copyObject(WarehouseVO.class,  customerWarehouse), 
								null, 
								workOrderServiceElementVO.getElementTypeId(), 
								workOrderServiceElementVO.getElementTypeCode(),
								inventoryDTO.getWorkOrderVO().getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeE(), 
								dtoGenerics.getMovTypeCodeS(), 
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(),
								workOrderServiceElementVO.getQuantity(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						this.warehouseElementBusinessBean.moveNotSerElementBetweenCustomerWhAndDealerWh( dtoMovement );
					//Si la cantidad es negativa, consulta la bodega de tipo ‘D00’ de la compañía a la cual pertenece la 
					//cuadrilla de la entrada 1. El sistema valida que la compañía a la cual pertenece la cuadrilla tiene una 
					//bodega de tipo ‘D00’ y hace llamado al Caso de Uso INV 40 enviándole como bodega de origen la bodega del 
					//cliente y como bodega destino la bodega de tipo ‘D00’ de la compañía instaladora.
					}else if( workOrderServiceElementVO.getQuantity().doubleValue() < 0 ){
						//consulta la bodega de tipo ‘D00’ perteneciente a la cuadrilla asignada a la WO
						String whTypeCode = CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity();
						//solo debe retornar un resultado dados esos parametros
						List<Warehouse> dealerWhs = this.warehouseDAO.getWhByCrewAndDealerAndWhType(inventoryDTO.getDealer().getId(), null, whTypeCode);
						if( dealerWhs == null || dealerWhs.isEmpty() ){
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN472.getCode(),ErrorBusinessMessages.STOCK_IN472.getMessage());
						}
						//Llamado al Caso de Uso INV 40
						
						
						MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
								CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity());
						
						MovementElementDTO dtoMovement = new MovementElementDTO(user,
								UtilsBusiness.copyObject(WarehouseVO.class, customerWarehouse), 
								UtilsBusiness.copyObject(WarehouseVO.class,  dealerWhs.get(0)), 
								null, 
								workOrderServiceElementVO.getElementTypeId(), 
								workOrderServiceElementVO.getElementTypeCode(),
								inventoryDTO.getWorkOrderVO().getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeE(), 
								dtoGenerics.getMovTypeCodeS(), 
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(),
								workOrderServiceElementVO.getQuantity() * -1,
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						
						this.warehouseElementBusinessBean.moveNotSerElementBetweenCustomerWhAndDealerWh(dtoMovement , true );
					}	
				}
			}
			return response;
		} catch (Throwable e) {
			log.error("== Error registerNotSerializedResources/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina registerNotSerializedResources/CoreStockBusiness ==");
		}		
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos para le CU 144
	 * @param workOrderServiceElementVO WorkOrderServiceElementVO
	 * @throws BusinessException
	 * @author jnova
	 */
	private void  validateRegisterNotSerializedResourceRequest(WorkOrderServiceElementVO workOrderServiceElementVO)throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateRegisterNotSerializedResourceRequest";		
		params[0] = "workOrderServiceElementVO";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "ElementTypeId";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO.getElementTypeId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));	
		params[0] = "ElementTypeCode";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO.getElementTypeCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Quantity";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceElementVO.getQuantity(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		if(workOrderServiceElementVO.getQuantity()!=null && workOrderServiceElementVO.getQuantity()==0L){
			throw new BusinessException(ErrorBusinessMessages.CORE_CR064.getCode(), ErrorBusinessMessages.CORE_CR064.getMessage());
		}
	}
	
	/**
	 * 
	 * Metodo: valida lo parametros requeridos para realizar la invocacion de CU 144
	 * @param inventoryDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void validateRegisterNotSerializedResources( InventoryDTO inventoryDTO) throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
		
		params[1] = "validateRegisterNotSerializedResources";		
		params[0] = "inventoryDTO";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Customer";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCustomer(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "Customer";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCustomer().getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "CountryCode";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "CountryId";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WoAttentionDTO";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WorkorderServices";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getWoAttentionDTO().getWorkorderServices(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "CrewId";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getAssignCrewId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DealerId";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getDealer(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "DealerId";
		UtilsBusiness.validateRequestResponseWebService(params,inventoryDTO.getDealer().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: valida los parametros requeridos
	 * para el consumo de la operacion RegisterNotSerialized
	 * @param workOrderServiceVO WorkOrderServiceVO 
	 * @throws BusinessException
	 * @author jalopez
	 */
	private static void  validateRegisterNotSerializedResourceRequest(WorkOrderServiceVO workOrderServiceVO)throws BusinessException{
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateRegisterNotSerializedResourceRequest";		
		params[0] = "workOrderServiceVO";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceVO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		params[0] = "WoServiceElements";
		UtilsBusiness.validateRequestResponseWebService(params,workOrderServiceVO.getWoServiceElements(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal#registerSerializedResources(co.com.directv.sdii.model.dto.InventoryDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EnvelopeEncapsulateResponse registerSerializedResources(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia registerSerializedResources/CoreStockBusiness ==");
		try{
			EnvelopeEncapsulateResponse response = new EnvelopeEncapsulateResponse();
			
			//Para cualquier movimiento se necesita la bodega del cliente, entonces se centraliza la consulta
			Warehouse customerWarehouse = this.getCustomerWarehouse(inventoryDTO.getCustomer().getCustomerCode(),inventoryDTO.getCountryCode());
			//Si el servicio es de instalacion o de service
			if ( ( inventoryDTO.getWoService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity() ) )
					|| inventoryDTO.getWoService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity()) ){
				if(inventoryDTO.getAttentionElementsList() != null && !inventoryDTO.getAttentionElementsList().isEmpty()) {
					for(WOAttentionElementsRequestDTO attentionElements : inventoryDTO.getAttentionElementsList()){
						//Valida si es un servicio de SWAP
						boolean isSwap = inventoryDTO.getWoService().getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
						
						//Si NO son de swap
						if(!isSwap){
							//Si es de solo instalacion
							this.registerSerializedResourcesForInstallation(attentionElements,inventoryDTO,customerWarehouse);
							//No se debe reportar la desconexion a ibs
							inventoryDTO.setProcessCodeIbsRecovery(CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
							//Si es de solo recuperacion
							this.registerSerializedResourcesForRecovery(attentionElements,inventoryDTO,customerWarehouse);
						}else {
							//Si es de SWAP
							this.registerSwapService(attentionElements, inventoryDTO, customerWarehouse);
						}
					}
				}
			//Caso que el servicio sea de desconexion
			}else if (inventoryDTO.getWoService().getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_DISCONNECTION.getCodeEntity()) ){
					//No se debe reportar la desconexion a ibs

				for(WOAttentionElementsRequestDTO attentionElements : inventoryDTO.getAttentionElementsList()){
					if(attentionElements.getRecoverySerializedElements() !=null){
						for(WorkOrderServiceElementVO workOrderServiceElementVO:attentionElements.getRecoverySerializedElements()){
							VerifyDesconectionSerialsDTO verifyDesconectionSerialsDTO = new VerifyDesconectionSerialsDTO();
							verifyDesconectionSerialsDTO.setPrincipalSerial(workOrderServiceElementVO.getElementSerial());
							if(inventoryDTO.getCustomer()!=null){
								verifyDesconectionSerialsDTO.setCustomerCode( inventoryDTO.getCustomer().getCustomerCode() );
							}
							if(workOrderServiceElementVO.getLinkedWOServiceElement()!=null){
								verifyDesconectionSerialsDTO.setLinkedSerial(workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial());
							}
							verifyDesconectionSerialsDTO.setWorkOrderId( inventoryDTO.getWorkOrderVO().getId() );
							//verifyDesconectionSerialsDTO.setWorkOrderServiceId( inventoryDTO.getWorkOrderServiceId());
							verifyDesconectionSerialsDTO.setWorkOrderServiceId(attentionElements.getWorkOrderServiceId());
							verifyDesconectionSerialsDTO.setCountryId(inventoryDTO.getCountryId());
							workOrderBusiness.verifyDesconectionSerials(verifyDesconectionSerialsDTO);
						}						
					}
				}
				
				for(WOAttentionElementsRequestDTO attentionElements : inventoryDTO.getAttentionElementsList()){
					inventoryDTO.setProcessCodeIbsRecovery(CodesBusinessEntityEnum.PROCESS_CODE_IBS_RECOVERY.getCodeEntity());
					this.registerSerializedResourcesForRecovery(attentionElements,inventoryDTO,customerWarehouse);
				}
			}
			
			return response;
		} catch (Throwable e) {
			log.error("== Error registerSerializedResources/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina registerSerializedResources/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Realiza la logica para realizar el flujo alternativo del CU INV 145  
	 * @param attentionElements
	 * @param customerWarehouse
	 * @param inventoryDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@Override
	public void registerSerializedResourcesForInstallation( WOAttentionElementsRequestDTO attentionElements , InventoryDTO inventoryDTO , Warehouse customerWarehouse ) throws BusinessException{
		log.debug("== Termina registerSerializedResourcesForInstallation/CoreStockBusiness ==");
		try{

			User user = daoUser.getUserById(inventoryDTO.getUserId());
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			if (attentionElements.getInstallationSerializedElements() != null && !attentionElements.getInstallationSerializedElements().isEmpty()){
				//Se obienenen los primeros elementos de la coleccion ya que en instalation solo se instala una pareja DECO-SC
				WorkOrderServiceElementVO workOrderServiceElementVO = attentionElements.getInstallationSerializedElements().get(0);
				String serial = workOrderServiceElementVO.getElementSerial();
				String serialLinked = new String(); 
				if(workOrderServiceElementVO.getLinkedWOServiceElement()!=null && workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial()!= null)
					serialLinked = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				List<String> whTypeCodes = new ArrayList<String>();
				whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity());
				whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());
				whTypeCodes.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity());
				WarehouseElement warehouseElementPojo = this.getWarehouseElementWhBySerialAndDealerCodeOrId(serial,inventoryDTO.getDealer().getDealerCode(),null,whTypeCodes,true);
				
				WarehouseElement warehouseElementLinkedPojo = new WarehouseElement();
				if(!serialLinked.isEmpty()) {
					warehouseElementLinkedPojo = this.getWarehouseElementWhBySerialAndDealerCodeOrId(serialLinked, inventoryDTO.getDealer().getDealerCode(),null,whTypeCodes,true);
					warehouseElementPojo = this.validateElementToRegisterElements(warehouseElementPojo, warehouseElementLinkedPojo, inventoryDTO, user);
				}
				
				//CU INV 39 Mover elementos serializados entre bodega de compañía y bodega de clientes para mover los elementos instalados al cliente
				MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
						 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity(),user, 
						   UtilsBusiness.copyObject(WarehouseVO.class,warehouseElementPojo.getWarehouseId()),
						   UtilsBusiness.copyObject(WarehouseVO.class, customerWarehouse), 
						   warehouseElementPojo.getSerialized(), 
						   inventoryDTO.getWorkOrderVO().getId(),
						   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
						   true, 
				           null, 
				           CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
				this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, false);
			}
		} catch (Throwable e) {
			log.error("== Error registerSerializedResourcesForInstallation/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina registerSerializedResourcesForInstallation/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Método que se encarga de validar los elementos a instalar en el cliente
	 * @param elementPrincipal
	 * @param elementLinked
	 * @param inventoryDTO
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private WarehouseElement validateElementToRegisterElements(WarehouseElement elementPrincipal, WarehouseElement elementLinked, InventoryDTO inventoryDTO, User user) throws BusinessException{
		log.debug("== Termina validateElementToInstall/CoreStockBusiness ==");
		try{
			WarehouseElement warehouseElementPrincipal = null;
			
			//valido si la sc esta siendo vinculada por el deco
			if(elementPrincipal.getSerialized().getSerialized() != null
					&&elementPrincipal.getSerialized().getSerialized().getSerialCode().equals(elementLinked.getSerialized().getSerialCode())){
				warehouseElementPrincipal = elementPrincipal;
			}else if(elementLinked.getSerialized().getSerialized() != null
					&& elementLinked.getSerialized().getSerialized().getSerialCode().equals(elementPrincipal.getSerialized().getSerialCode())){
				warehouseElementPrincipal = elementLinked;
			}else{
				//se desvincula el elemento principal
				this.unlinkedElement(elementPrincipal);
				this.unlinkedElement(elementLinked);
				
				//Si el elemento vinculado se enecuentra en una bodega diferente a la del elemento principal, 
				//el elemento vinculado se mueve a la bodega del deco
				if(elementPrincipal.getWarehouseId().getId().longValue() != elementLinked.getWarehouseId().getId().longValue()){
					//Mueve el elemento a vincular a la bodega del DECO
					MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
							 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity(),user, 
							   UtilsBusiness.copyObject(WarehouseVO.class,elementLinked.getWarehouseId()),
							   UtilsBusiness.copyObject(WarehouseVO.class, elementPrincipal.getWarehouseId()), 
							   elementLinked.getSerialized(), 
							   inventoryDTO.getWorkOrderVO().getId(),
							   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
							   true, 
					           null,
					           CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
					this.businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
				}
				this.elementBusinessBean.linkSerializedElements(elementPrincipal.getSerialized().getElementId(), elementLinked.getSerialized().getElementId());
				warehouseElementPrincipal  = this.warehouseElementDAO.getWareHouseElementByActualWhAndWhAndSerializedElement(elementPrincipal.getWarehouseId().getId(), elementPrincipal.getSerialized().getElementId());
			}
			return warehouseElementPrincipal;
		} catch (Throwable e) {
			log.error("== Error validateElementToInstall/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina validateElementToInstall/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Metodo encargado desvincular un elemento que se encuentre en una bodega
	 * @param warehouseElement
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void unlinkedElement(WarehouseElement warehouseElement) throws BusinessException{
		log.debug("== Termina unlinkedElement/CoreStockBusiness ==");
		try{
			
			// se desvincula el elemento
			if(warehouseElement.getSerialized().getSerialized()!=null){
				WarehouseElement warehouseElementLink = this.warehouseElementDAO.getWareHouseElementByActualWhAndWhAndSerializedElement(warehouseElement.getWarehouseId().getId(), warehouseElement.getSerialized().getSerialized().getElementId());
				this.warehouseElementBusinessBean.separateLinkedSerializedElementsInWh(warehouseElement.getWarehouseId().getId(),
						warehouseElement.getSerialized().getElementId(), warehouseElementLink.getSerialized().getElementId());
			}
			
			//Se verifica si el elemento esta siendo vinculado para desvincularlo
			List<Serialized> listElements = this.daoSerialized.getLinkedSerializedBySerialCode(warehouseElement.getSerialized().getSerialCode(),warehouseElement.getSerialized().getElement().getCountry().getId());
			for(Serialized ser: listElements){
				WarehouseElement warehouseElementPrincipal = this.warehouseElementDAO.getWareHouseElementByActualWhAndWhAndSerializedElement(warehouseElement.getWarehouseId().getId(), ser.getElementId());
				this.warehouseElementBusinessBean.separateLinkedSerializedElementsInWh(warehouseElementPrincipal.getWarehouseId().getId(),
						warehouseElementPrincipal.getSerialized().getElementId(), warehouseElement.getSerialized().getElementId());
			}
			
		} catch (Throwable e) {
			log.error("== Error unlinkedElement/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina unlinkedElement/CoreStockBusiness ==");
		}	
	} 
	
	/**
	 * 
	 * Metodo: Realiza la logica para realizar el flujo alternativo del CU INV 145  
	 * @param attentionElements
	 * @param inventoryDTO
	 * @param customerWarehouse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@Override
	public void registerSerializedResourcesForRecovery( WOAttentionElementsRequestDTO attentionElements , InventoryDTO inventoryDTO , Warehouse customerWarehouse) throws BusinessException{
		log.debug("== Termina registerSerializedResourcesForRecovery/CoreStockBusiness ==");
		try{
			User user = daoUser.getUserById(inventoryDTO.getUserId());
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			if ( attentionElements.getRecoverySerializedElements() != null && !attentionElements.getRecoverySerializedElements().isEmpty() ) {
				//Se obienenen los primeros elementos de la coleccion ya que en recuperacion solo se recupera una pareja DECO-SC
				WorkOrderServiceElementVO workOrderServiceElementVO = attentionElements.getRecoverySerializedElements().get(0);
				String serial = workOrderServiceElementVO.getElementSerial();
				String serialLinked = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				//SWOP THEFT
				SystemParameter theftServiceParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SWOP_THEFT_SERVICE.getCodeEntity(),inventoryDTO.getCountryId());
				SystemParameter theftWhParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SWOP_THEFT_WAREHOUSE.getCodeEntity(),inventoryDTO.getCountryId());
				List<Warehouse> dealerWhs= new ArrayList<Warehouse>();
				Warehouse warehouse = new Warehouse();
				if ( inventoryDTO.getWoService()!=null && (inventoryDTO.getWoService().getServiceCode().equals(theftServiceParam.getValue() ))){
					warehouse = this.warehouseDAO.getWarehouseByCodeAndByCountry( theftWhParam.getValue(), inventoryDTO.getCountryId() );
					if (warehouse!=null)
						dealerWhs.add(warehouse);
				}else{
					dealerWhs = this.warehouseDAO.getWarehousesByDealerCodeAndWhTypeCode(inventoryDTO.getDealer().getDealerCode(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity());
				}
				if( dealerWhs == null || dealerWhs.isEmpty() ){
					Object[] params = {inventoryDTO.getDealer().getDealerCode()};
					List<String> paramsList = new ArrayList<String>();
					paramsList.add( inventoryDTO.getDealer().getDealerCode().toString() );
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN480.getCode(),ErrorBusinessMessages.STOCK_IN480.getMessage(params),paramsList);
				}
				//Consulta el DECO en la bodega del cliente
				WarehouseElement principalElement = this.warehouseElementDAO.getElementBySerialAndCustomerCode(serial,inventoryDTO.getCustomer().getCustomerCode());
				//Valida que el DECO este en la bodega del cliente
				if( principalElement != null && principalElement.getSerialized() != null ){
					
					//Valida que no traiga serial vinculado en la desconexion
					if(serialLinked==null){
						//Se verifica si el elemento esta siendo vinculado para desvincularlo
						List<Serialized> listElements = this.daoSerialized.getLinkedSerializedBySerialCode(principalElement.getSerialized().getSerialCode(),principalElement.getSerialized().getElement().getCountry().getId());

						Serialized tempSerializedLink;
						//Si el elemento esta siendo vinculado por otro serial
						if(listElements!=null && !listElements.isEmpty()){
							tempSerializedLink=listElements.get(0);
						}else{
							tempSerializedLink=principalElement.getSerialized();
						}
						MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
								 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
								   customerWarehouse,
								   dealerWhs.get(0), 
								   tempSerializedLink, 
								   inventoryDTO.getWorkOrderVO().getId(),
								   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
								   true, 
						           null,
						           inventoryDTO.getProcessCodeIbsRecovery());
						this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, true);
						
					}else if( principalElement.getSerialized().getSerialized() != null ){
						//Valida que el DECO este vinculado con la SC enviada por parametro
						if( principalElement.getSerialized().getSerialized().getSerialCode().equalsIgnoreCase( serialLinked ) ){
							//Caso de Uso Inv_41 mover elementos serializados entre bodega Clientes y Bodega de una compañía
							MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
									 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
									   customerWarehouse,
									   dealerWhs.get(0), 
									   principalElement.getSerialized(), 
									   inventoryDTO.getWorkOrderVO().getId(),
									   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
									   true, 
							           null,
							           inventoryDTO.getProcessCodeIbsRecovery());
							this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, true);
						//Si el DECO esta vinculado con otro elemento CU INV 45 Desvincular los elementos serializados de una misma bodega
						}else {
							this.warehouseElementBusinessBean.separateLinkedSerializedElementsInWh(principalElement.getWarehouseId().getId(), principalElement.getSerialized().getElementId(), principalElement.getSerialized().getSerialized().getElementId());
							
							WarehouseElement linkedElement = null;
							//variable que indica que se realiza la busqueda de la SC como vinculada
							boolean isLinked = false;
							//Consulta la SC como vinculada
							linkedElement = this.warehouseElementDAO.getElementByLinkedSerialAndCustomerCode(serialLinked,inventoryDTO.getCustomer().getCustomerCode());
							if( linkedElement != null && linkedElement.getId().longValue() > 0){
								isLinked = true;
							} else{
								//Consulta la SC como principal
								linkedElement = this.warehouseElementDAO.getElementBySerialAndCustomerCode(serialLinked,inventoryDTO.getCustomer().getCustomerCode());
							}
							//Valida que la SC este en la bodega del cliente
							if( linkedElement == null || linkedElement.getId().longValue() <= 0){
								Object[] params = {serialLinked,inventoryDTO.getCustomer().getCustomerCode()};
								List<String> paramsList = new ArrayList<String>();
								paramsList.add( serialLinked );
								paramsList.add( inventoryDTO.getCustomer().getCustomerCode() );
								throw new BusinessException(ErrorBusinessMessages.STOCK_IN481.getCode(),ErrorBusinessMessages.STOCK_IN481.getMessage(params),paramsList);
							} else {
								Long linkedId = 0L;
								Serialized serialized = null;
								//Si fue consultada como vinculada CU INV 45 Desvincular los elementos serializados de una misma bodega
								if( isLinked && linkedElement.getSerialized().getSerialized() != null ){
									linkedId = linkedElement.getSerialized().getSerialized().getElementId();
									serialized = linkedElement.getSerialized().getSerialized();
									this.warehouseElementBusinessBean.separateLinkedSerializedElementsInWh(linkedElement.getWarehouseId().getId(), linkedElement.getSerialized().getElementId(), linkedElement.getSerialized().getSerialized().getElementId());
								} else {
									linkedId = linkedElement.getSerialized().getElementId();
									serialized = linkedElement.getSerialized();
								}
								//Realiza el movimiento del DECO y de la SC por aparte
								MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
												 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
												 customerWarehouse,
												 dealerWhs.get(0), 
												 principalElement.getSerialized(), 
												 inventoryDTO.getWorkOrderVO().getId(),
												 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
												 true, 
										         null, 
										         inventoryDTO.getProcessCodeIbsRecovery());
								
								this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, true);
								
								MovementElementDTO dto2 = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
										 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
										 customerWarehouse,
										 dealerWhs.get(0), 
										 serialized, 
										 inventoryDTO.getWorkOrderVO().getId(),
										 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
										 true, 
								         null, 
								         inventoryDTO.getProcessCodeIbsRecovery());
						
								this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto2, true);
							}
						}
					//El DECO no tiene vinculado
					}else {
						//consula la SC que va a devolver a la boleta de la compania
						WarehouseElement linkedElement = null;
						//variable que indica que se realiza la busqueda de la SC como vinculada
						boolean isLinked = false;
						//Consulta la SC como vinculada
						linkedElement = this.warehouseElementDAO.getElementByLinkedSerialAndCustomerCode(serialLinked,inventoryDTO.getCustomer().getCustomerCode());
						if( linkedElement != null && linkedElement.getId().longValue() > 0){
							isLinked = true;
						} else{
							//Consulta la SC como principal
							linkedElement = this.warehouseElementDAO.getElementBySerialAndCustomerCode(serialLinked,inventoryDTO.getCustomer().getCustomerCode());
						}
						//Valida que la SC este en la bodega del cliente
						if( linkedElement == null || linkedElement.getId().longValue() <= 0){
							Object[] params = {serialLinked,inventoryDTO.getCustomer().getCustomerCode()};
							List<String> paramsList = new ArrayList<String>();
							paramsList.add( serialLinked );
							paramsList.add( inventoryDTO.getCustomer().getCustomerCode() );
							throw new BusinessException(ErrorBusinessMessages.STOCK_IN481.getCode(),ErrorBusinessMessages.STOCK_IN481.getMessage(params),paramsList);
						}else {
							Long linkedId = 0L;
							Serialized serialized = null;
							//Si fue consultada como vinculada CU INV 45 Desvincular los elementos serializados de una misma bodega
							if( isLinked && linkedElement.getSerialized().getSerialized() != null ){
								linkedId = linkedElement.getSerialized().getSerialized().getElementId();
								serialized = linkedElement.getSerialized().getSerialized();
								this.warehouseElementBusinessBean.separateLinkedSerializedElementsInWh(linkedElement.getWarehouseId().getId(), linkedElement.getSerialized().getElementId(), linkedElement.getSerialized().getSerialized().getElementId());
							} else {
								linkedId = linkedElement.getSerialized().getElementId();
								serialized = linkedElement.getSerialized();
							}
							//Realiza el movimiento del DECO y de la SC por aparte
							MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
									 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
									 customerWarehouse,
									 dealerWhs.get(0), 
									 principalElement.getSerialized(), 
									 inventoryDTO.getWorkOrderVO().getId(),
									 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
									 true, 
							         null, 
							         inventoryDTO.getProcessCodeIbsRecovery());
					
							this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, true);
							
							MovementElementDTO dto2 = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
									 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
									 customerWarehouse,
									 dealerWhs.get(0), 
									 serialized, 
									 inventoryDTO.getWorkOrderVO().getId(),
									 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
									 true, 
							         null, 
							         inventoryDTO.getProcessCodeIbsRecovery());
					
							this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto2, true);
						}
						
						
					}
				} else {
					Object[] params = {serial,inventoryDTO.getCustomer().getCustomerCode()};
					List<String> paramsList = new ArrayList<String>();
					paramsList.add( serial );
					paramsList.add( inventoryDTO.getCustomer().getCustomerCode() );
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN481.getCode(),ErrorBusinessMessages.STOCK_IN481.getMessage(params),paramsList);
				}	
			}
		} catch (Throwable e) {
			log.error("== Error registerSerializedResourcesForRecovery/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina registerSerializedResourcesForRecovery/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Metodo: consulta la bodega del clinete
	 * @param customerCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private Warehouse getCustomerWarehouse(String customerCode,String countryCode) throws DAOServiceException, DAOSQLException, BusinessException{
		//Se consulta la bodega de tipo '01' perteneciente al cliente  
		Warehouse customerWarehouse = this.warehouseDAO.getCustomerWarehouseByCountry(customerCode,countryCode);
		//Se valida que el cliente tenga una bodega a la cual realizar el movimiento de elementos NO serializados
		if( customerWarehouse == null ){
			Object[] params = {customerCode};
			List<String> paramsList = new ArrayList<String>();
			paramsList.add( customerCode );
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN470.getCode(),ErrorBusinessMessages.STOCK_IN470.getMessage(params),paramsList);
		}
		return customerWarehouse;
	}
	
	/**
	 * Metodo: consulta elemento serializado en las bodegas de la compania, sucursales y cuadrillas a partir del codigo del dealer,
	 * tipo de bodega.
	 * @param serial
	 * @param dealerCode
	 * @param dealerId
	 * @param whTypeCodes
	 * @param throwsException
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jnova
	 */
	@Override
	public WarehouseElement getWarehouseElementWhBySerialAndDealerCodeOrId( String serial , Long dealerCode , Long dealerId , List<String> whTypeCodes, boolean throwsException )throws DAOServiceException, DAOSQLException, BusinessException{
		WarehouseElement warehouseElementPojo = null;
		boolean isDealerCode = dealerCode != null && dealerCode.longValue() > 0;
		boolean isDealerId = dealerId != null && dealerId.longValue() > 0;
		//Consulta por codigo de dealer
		if( isDealerCode ){
			warehouseElementPojo = warehouseElementDAO.getElementBySerialAndDealerCodeAndWhTypes(serial, dealerCode  , whTypeCodes);
		//consula por identificador de dealer
		}else if( isDealerId ){
			warehouseElementPojo = this.warehouseElementDAO.getElementBySerialAndDealerIdAndWhTypes(serial, dealerId, whTypeCodes);
		}
		
		//valida que haya encontrado el elemento serializado
		if( throwsException && ( warehouseElementPojo == null || warehouseElementPojo.getSerialized() == null ) ){
			Object[] params = null;
			params = new Object[2];
			params[0] = serial;
			List<String> paramsList = new ArrayList<String>();
			paramsList.add( serial );
			if( isDealerCode ){
				params[1] = dealerCode.toString();
				paramsList.add( dealerCode.toString() );
			}else if( isDealerId ){
				params[1] = dealerId.toString();
				paramsList.add( dealerId.toString() );
			}
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN479.getCode(),ErrorBusinessMessages.STOCK_IN479.getMessage(params),paramsList);
		}
		return warehouseElementPojo;
	}
	
	
	
	/**
	 * 
	 * Metodo: Permite realizar el flujo alterno del CU 145 para caso de servicios de SWAP
	 * @param attentionElements
	 * @param inventoryDTO
	 * @param customerWarehouse
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void registerSwapService(WOAttentionElementsRequestDTO attentionElements , InventoryDTO inventoryDTO , Warehouse customerWarehouse) throws BusinessException{
		log.debug("== Termina registerSwapService/CoreStockBusiness ==");
		try{
			//La variable process será la encargada de determinar el alcance del cambio
			//Tomará los siguientes valores
			//1  - Cambio de deco y de smartcard
			//2  - Unicamente se cambia smartcard
			//3  - Unicamente se cambia el deco
			int process = 0;
			
			
			if (attentionElements.getInstallationSerializedElements() != null && !attentionElements.getInstallationSerializedElements().isEmpty() 
					&& attentionElements.getRecoverySerializedElements() != null && !attentionElements.getRecoverySerializedElements().isEmpty()){
			
				//Se carga al objeto el elemento que se instala
				WorkOrderServiceElementVO workOrderServiceElementVOInstallation = attentionElements.getInstallationSerializedElements().get(0);
				
				//Se carga al objeto el elemento que se recupera
				WorkOrderServiceElementVO workOrderServiceElementVORecovery = attentionElements.getRecoverySerializedElements().get(0);
				
				
				SwopElementsDTO swopDto = new SwopElementsDTO();
				swopDto.setSerialDecoInstall(workOrderServiceElementVOInstallation.getElementSerial());
				swopDto.setSerialSCInstall(workOrderServiceElementVOInstallation.getLinkedWOServiceElement().getElementSerial());
				swopDto.setSerialDecoRecovery(workOrderServiceElementVORecovery.getElementSerial());
				swopDto.setSerialSCRecovery(workOrderServiceElementVORecovery.getLinkedWOServiceElement().getElementSerial());
				swopDto.setCustomerCode(inventoryDTO.getCustomer().getCustomerCode());
				swopDto.setDealerCode(inventoryDTO.getDealer().getDealerCode());
				swopDto.setCountryId(inventoryDTO.getCountryId());
				process = validateChangeElements(swopDto);
				
				//SWOP THEFT
				SystemParameter theftServiceParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SWOP_THEFT_SERVICE.getCodeEntity(),inventoryDTO.getCountryId());
				SystemParameter theftWhParam = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_SWOP_THEFT_WAREHOUSE.getCodeEntity(),inventoryDTO.getCountryId());
				List<Warehouse> dealerWhs= new ArrayList<Warehouse>();
				Warehouse warehouse = new Warehouse();
				if ( (inventoryDTO.getWoService().getServiceCode().equals(theftServiceParam.getValue() ))){
					warehouse = this.warehouseDAO.getWarehouseByCodeAndByCountry( theftWhParam.getValue(), inventoryDTO.getCountryId()) ;
					if (warehouse!=null)
						dealerWhs.add(warehouse);
				}else{
					dealerWhs = this.warehouseDAO.getWarehousesByDealerCodeAndWhTypeCode(inventoryDTO.getDealer().getDealerCode(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity());
				}
				if(dealerWhs == null || dealerWhs.isEmpty()){
					Object[] params = {inventoryDTO.getDealer().getDealerCode()};
					List<String> paramsList = new ArrayList<String>();
					paramsList.add(inventoryDTO.getDealer().getDealerCode().toString());
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN480.getCode(),ErrorBusinessMessages.STOCK_IN480.getMessage(params),paramsList);
				}
				
				if(process == CONST_SWAP_COMPLETE){
					//registra para instalar
					this.registerSerializedResourcesForInstallation(attentionElements,inventoryDTO,customerWarehouse);
					//No se debe reportar la desconexion a ibs
					inventoryDTO.setProcessCodeIbsRecovery(CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
					//recupera elementos
					this.registerSerializedResourcesForRecovery(attentionElements,inventoryDTO,customerWarehouse);
				}else if (process == CONST_LEAVE_DECO_SWAP_SC){
					this.leaveDecoAndRecoverSC(swopDto.getWarehouseElementDecoInstall(), 
							swopDto.getWarehouseElementSCInstall(),
							swopDto.getWarehouseElementSCRecovery(),
							dealerWhs,
							customerWarehouse, 
							inventoryDTO.getDealer().getDealerCode(),
							inventoryDTO.getWorkOrderVO().getId(),
		                    inventoryDTO.getUserId());
					
				}else if (process == CONST_LEAVE_SC_SWAP_DECO){
					this.leaveSCAndRecoverDECO(swopDto.getWarehouseElementSCInstall(),
							swopDto.getWarehouseElementDecoInstall(), 
							swopDto.getWarehouseElementDecoRecovery(),
							dealerWhs, customerWarehouse, 
							inventoryDTO.getDealer().getDealerCode(),
							inventoryDTO.getWorkOrderVO().getId(),
		                    inventoryDTO.getUserId());
					
				}else{
					List<String> paraList = new ArrayList<String>();
					paraList.add("Seriales para atender el servicio no son válidos. Los seriales a instalar deben estar en las bodegas de stock de la compañia, y los seriales a recuperar deben esta en la ubicación del cliente");
					paraList.add("Swap");
					Object[] params = new Object[2];
					params[0] = "Seriales para atender el servicio no son válidos. Los seriales a instalar deben estar en las bodegas de stock de la compañia, y los seriales a recuperar deben esta en la ubicación del cliente";
					params[1] = "Swap";
					
					throw new BusinessException(ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(), paraList);
				}

			}
		} catch (Throwable e) {
			log.error("== Error registerSwapService/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina registerSwapService/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Metodo encargado de validar el alcance del movimiento del swop
	 * 
	 * @param swopElementDTO
	 * @return  Tomará los siguientes valores
	 *		1  - Cambio de deco y de smartcard
	 *		2  - Unicamente se cambia smartcard
	 *		3  - Unicamente se cambia el deco
	 * @throws BusinessException
	 */
	private int validateChangeElements(SwopElementsDTO swopElementDTO) throws BusinessException{
		log.debug("== Inicia validateChangeElements/CoreStockBusiness ==");
		try{
			boolean existDecoInstallInDealer = false, existScInstallInDealer = false, existDecoRecoveryInCustomer = false, existsScRecoveryInCustomer = false;
			
			//Se consultan los 4 elementos en inventarios
			WarehouseElement elementDecoIntall = this.validateExistSerialCode(swopElementDTO.getSerialDecoInstall(),swopElementDTO.getCountryId());
			WarehouseElement elementSCIntall = this.validateExistSerialCode(swopElementDTO.getSerialSCInstall(),swopElementDTO.getCountryId());
			WarehouseElement elementDecoRecovery = this.validateExistSerialCode(swopElementDTO.getSerialDecoRecovery(),swopElementDTO.getCountryId());
			WarehouseElement elementSCRecovery = this.validateExistSerialCode(swopElementDTO.getSerialSCRecovery(),swopElementDTO.getCountryId());
			
			//Set de los warehouse en la dto
			swopElementDTO.setWarehouseElementDecoInstall(elementDecoIntall);
			swopElementDTO.setWarehouseElementSCInstall(elementSCIntall);
			swopElementDTO.setWarehouseElementDecoRecovery(elementDecoRecovery);
			swopElementDTO.setWarehouseElementSCRecovery(elementSCRecovery);
			
			
			existDecoInstallInDealer = this.existElementInDealer(elementDecoIntall, swopElementDTO);
			existScInstallInDealer = this.existElementInDealer(elementSCIntall, swopElementDTO);
			existDecoRecoveryInCustomer = this.existElementInCustomer(elementDecoRecovery, swopElementDTO);
			existsScRecoveryInCustomer = this.existElementInCustomer(elementSCRecovery, swopElementDTO);
			
			if(existDecoInstallInDealer && existScInstallInDealer && existDecoRecoveryInCustomer && existsScRecoveryInCustomer){
				return CONST_SWAP_COMPLETE;
			}else if(existScInstallInDealer && existsScRecoveryInCustomer && !existDecoInstallInDealer && existDecoRecoveryInCustomer){
				return CONST_LEAVE_DECO_SWAP_SC;
			}else if (existDecoInstallInDealer && existDecoRecoveryInCustomer && !existScInstallInDealer && existsScRecoveryInCustomer){
				return CONST_LEAVE_SC_SWAP_DECO;
			}else{
				return 0;
			}
		} catch (Throwable e) {
			log.error("== Error validateChangeElements/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina validateChangeElements/CoreStockBusiness ==");
		}	
	}
	
	/**
	 *  Metodo encargado de validar si el elemento se encuentra en las bodegas de stock del dealer
	 * @param warehouseElement
	 * @param swopElementsDTO
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private boolean existElementInDealer(WarehouseElement warehouseElement, SwopElementsDTO swopElementsDTO) throws BusinessException{
		log.debug("== Inicia existElementInCustomer/CoreStockBusiness ==");
		try{
			if(warehouseElement.getWarehouseId().getDealerId()!=null 
					&&warehouseElement.getWarehouseId().getDealerId().getDealerCode().equals(swopElementsDTO.getDealerCode())
					&&(warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity())
					||warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity())
					||warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity()))){
				return true;
			}else if(warehouseElement.getWarehouseId().getCustomerId()!=null 
					&&warehouseElement.getWarehouseId().getCustomerId().getCustomerCode().equals(swopElementsDTO.getCustomerCode())){
				return false;
			}else{
				List<String> params = new ArrayList<String>();
				params.add(warehouseElement.getSerialized().getSerialCode());
				params.add(swopElementsDTO.getDealerCode().toString());
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN479.getCode(), ErrorBusinessMessages.STOCK_IN479.getMessage(), params);
			}
		} catch (Throwable e) {
			log.error("== Error existElementInCustomer/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina existElementInCustomer/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Metodo encargado de validar si el elemento se encuentra en las bodegas de stock del dealer
	 * @param warehouseElement
	 * @param swopElementsDTO
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private boolean existElementInCustomer(WarehouseElement warehouseElement, SwopElementsDTO swopElementsDTO) throws BusinessException{
		log.debug("== Inicia existElementInCustomer/CoreStockBusiness ==");
		try{
			if(warehouseElement.getWarehouseId().getCustomerId()!=null 
					&&warehouseElement.getWarehouseId().getCustomerId().getCustomerCode().equals(swopElementsDTO.getCustomerCode())){
				return true;
			}else if(warehouseElement.getWarehouseId().getDealerId()!=null 
					&&warehouseElement.getWarehouseId().getDealerId().getDealerCode().equals(swopElementsDTO.getDealerCode())
					&&(warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity())
					||warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity())
					||warehouseElement.getWarehouseId().getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity()))){
				return false;
			}else{
				List<String> params = new ArrayList<String>();
				params.add(warehouseElement.getSerialized().getSerialCode());
				params.add(swopElementsDTO.getCustomerCode());
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN481.getCode(), ErrorBusinessMessages.STOCK_IN481.getMessage(), params);
			}
		} catch (Throwable e) {
			log.error("== Error existElementInCustomer/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina existElementInCustomer/CoreStockBusiness ==");
		}	
	}
	
	/**
	 * Metodo encargado de generar excepcion en caso de no encontrar el serial en el sistema
	 * @param serial
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	private WarehouseElement validateExistSerialCode(String serial,Long countryId) throws BusinessException{
		log.debug("== Inicio validateExistSerialCode/CoreStockBusiness ==");
		try{
			WarehouseElement elementWarehouse = this.warehouseElementDAO.getWarehouseElementBySerialActive(serial,countryId);
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(serial);
			Object[] params = null;
			params = new Object[1];
			params[0] = serial;
			UtilsBusiness.assertNotNull(elementWarehouse, ErrorBusinessMessages.STOCK_IN486.getMessage(), ErrorBusinessMessages.STOCK_IN486.getMessage(params), paramsList);
			return elementWarehouse;
		} catch (Throwable e) {
			log.error("== Error validateExistSerialCode/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina validateExistSerialCode/CoreStockBusiness ==");
		}	
	}

	/**
	 * Metodo: Permite dejar la SC en el cliente, descinvularla, mover el nuevo DECO y dejarla vinculada con el nuevo DECO en la bodega
	 * del cliente
	 * @param scInstallationElementCustomer
	 * @param decoInstallationElement
	 * @param decoRecoveryElement
	 * @param dealerWhs
	 * @param customerWarehouse
	 * @param dealerCode
	 * @param woId
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void leaveSCAndRecoverDECO(WarehouseElement scInstallationElementCustomer, 
			WarehouseElement decoInstallationElement, 
			WarehouseElement decoRecoveryElement,
			List<Warehouse> dealerWhs , Warehouse customerWarehouse , Long dealerCode , Long woId, Long userId) throws BusinessException{
		log.debug("== Termina leaveDecoAndRecoverSC/CoreStockBusiness ==");
		try{
			User user = daoUser.getUserById(userId);

			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			this.unlinkedElement(scInstallationElementCustomer);
			
			this.unlinkedElement(decoInstallationElement);
			
			this.unlinkedElement(decoRecoveryElement);
			
			// Mover el Deco que se desea recuperar a la bodega del dealer
			MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
					 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
					   decoRecoveryElement.getWarehouseId(),
					   dealerWhs.get(0), 
					   decoRecoveryElement.getSerialized(), 
					   woId,
					   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
					   true, 
			           null, 
			           CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
			this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, false);
			
			
			// Instalar el nuevo deco en la bodega del cleinte
			
			dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
					 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity(),user, 
					 decoInstallationElement.getWarehouseId(),
					 customerWarehouse, 
					 decoInstallationElement.getSerialized(), 
					 woId,
					 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
					 true, 
			         null, 
			         CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
			
			this.businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
			//CU INV 44 Vincular dos elementos serializados de una misma bodega 
			this.elementBusinessBean.linkSerializedElements(decoInstallationElement.getSerialized().getElementId(), scInstallationElementCustomer.getSerialized().getElementId());
			
		} catch (Throwable e) {
			log.error("== Error leaveDecoAndRecoverSC/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina leaveDecoAndRecoverSC/CoreStockBusiness ==");
		}
	}

	/**
	 * Permite dejar el DECO en el cliente, desvincularlo cuando aplique, vincularlo con la nueva SC y mover la que se retira a devoluciones
	 * @param decoInstallationElementCustomer
	 * @param scInstallationElement
	 * @param scRecoveryElement
	 * @param dealerWhs
	 * @param customerWarehouse
	 * @param dealerCode
	 * @param woId
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void leaveDecoAndRecoverSC (WarehouseElement decoInstallationElementCustomer, 
			WarehouseElement scInstallationElement ,
			WarehouseElement scRecoveryElement,
			List<Warehouse> dealerWhs, 
			Warehouse customerWarehouse, 
			Long dealerCode, 
			Long woId, 
			Long userId) throws BusinessException {
		log.debug("== Termina leaveDecoAndRecoverSC/CoreStockBusiness ==");
		try{
			User user = daoUser.getUserById(userId);

			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			
			this.unlinkedElement(scInstallationElement);
			
			this.unlinkedElement(decoInstallationElementCustomer);
			
			this.unlinkedElement(scRecoveryElement);
			
			// Mover la SC que se desea recuperar a la bodega del dealer
			MovementElementDTO dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_ENTRY.getCodeEntity(),
					 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_RECOVERY_IRD_SC_EXIT.getCodeEntity(),user, 
					   scRecoveryElement.getWarehouseId(),
					   dealerWhs.get(0), 
					   scRecoveryElement.getSerialized(), 
					   woId,
					   CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
					   true, 
			           null, 
			           CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
			this.warehouseElementBusinessBean.moveSerElementBetweenCustomerWhAndDealerWh(dto, false);
			
			
			// Instalar la sc nueva en la bodega del cleinte
			
			dto = getMovementElementSerializedDTO(CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_ENTRY.getCodeEntity(),
					 CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INVENTORY_DOWNLOAD_EXIT.getCodeEntity(),user, 
					 scInstallationElement.getWarehouseId(),
					 customerWarehouse, 
					 scInstallationElement.getSerialized(), 
					 woId,
					 CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity(), 
					 true, 
			         null, 
			         CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
			
			this.businessMovementElement.moveSerializedElementBetweenWarehouse(dto);
			
			//CU INV 44 Vincular dos elementos serializados de una misma bodega 
			this.elementBusinessBean.linkSerializedElements(decoInstallationElementCustomer.getSerialized().getElementId(), scInstallationElement.getSerialized().getElementId());
			
		} catch (Throwable e) {
			log.error("== Error leaveDecoAndRecoverSC/CoreStockBusiness ==", e);
			throw this.manageException(e);
		} finally {
			log.debug("== Termina leaveDecoAndRecoverSC/CoreStockBusiness ==");
		}
	}
	
	private MovementElementDTO getMovementElementSerializedDTO(String movementTypeCodeE, 
			                                         String movementTypeCodeS,
			                                         User user, 
			                                         Warehouse sourceWh,
			                             			 Warehouse targetWs, 
			                             			 Serialized serialized, 
			                             			 Long documentId,
			                             			 String documentClass, 
			                             			 boolean reportIbs, 
			                             			 ItemStatus itemStatus, 
			                             			 String processCode) throws BusinessException
	{			                                         
	
		MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeCodeE,
																								 movementTypeCodeS);
		
		MovementElementDTO dto = new MovementElementDTO(user, 
														UtilsBusiness.copyObject(WarehouseVO.class,sourceWh ),
														UtilsBusiness.copyObject(WarehouseVO.class,targetWs ),
														serialized, 
														documentId,
														documentClass, 
														dtoGenerics.getMovTypeCodeE(), 
														dtoGenerics.getMovTypeCodeS(),
														dtoGenerics.getRecordStatusU(), 
														dtoGenerics.getRecordStatusH(),
														reportIbs, 
														processCode,
														dtoGenerics.getMovConfigStatusPending(),
														dtoGenerics.getMovConfigStatusNoConfig());
		return dto;
	}
}
