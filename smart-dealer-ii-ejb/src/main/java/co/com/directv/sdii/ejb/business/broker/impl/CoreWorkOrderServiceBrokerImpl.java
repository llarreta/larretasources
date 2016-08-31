/**
 * Creado 3/06/2010 9:51:04
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.Vista360ServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.CoreWorkOrderServiceTOA;
import co.com.directv.sdii.ejb.business.broker.toa.CoreWorkorderImporterServiceLocalTOA;
import co.com.directv.sdii.ejb.business.core.CoreWOInventoryBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWorkOrderEventInfoLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.pojo.ShippingOrderStatus;
import co.com.directv.sdii.model.pojo.ShippingOrderType;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;

import com.directvla.contract.businessdomain.serviceconfigurationandactivation.v1_0.AddActivationException;
import com.directvla.contract.businessdomain.serviceconfigurationandactivation.v1_0.ServiceConfigurationAndActivationPt;
import com.directvla.contract.crm.customer.v1.AddCustomerContactException;
import com.directvla.contract.crm.customer.v1.AddIRDChangesException;
import com.directvla.contract.crm.customer.v1.AddServiceToWorkOrderException;
import com.directvla.contract.crm.customer.v1.CancelWorkOrderException;
import com.directvla.contract.crm.customer.v1.GetNewWorkOrderServiceException;
import com.directvla.contract.crm.customer.v1.GetWorkOrderException;
import com.directvla.contract.crm.customer.v1.PtCore;
import com.directvla.contract.crm.customer.v1.RemoveWorkOrderServiceException;
import com.directvla.contract.crm.customer.v1.UpdateWorkOrderException;
import com.directvla.schema.businessdomain.serviceconfigurationandactivation.v1_0.AddActivationRequest;
import com.directvla.schema.businessdomain.serviceconfigurationandactivation.v1_0.AddActivationResponse;
import com.directvla.schema.crm.common.v1_1.Product;
import com.directvla.schema.crm.common.v1_1.RequestMetadataType;
import com.directvla.schema.crm.common.v1_1.ServiceCollection;
import com.directvla.schema.crm.common.v1_1.WorkOrderCollection;
import com.directvla.schema.crm.customer.v1.AddCustomerContact;
import com.directvla.schema.crm.customer.v1.AddCustomerContactRequest;
import com.directvla.schema.crm.customer.v1.AddCustomerContactResponse;
import com.directvla.schema.crm.customer.v1.AddCustomerContactResult;
import com.directvla.schema.crm.customer.v1.AddIRDChanges;
import com.directvla.schema.crm.customer.v1.AddIRDChangesRequest;
import com.directvla.schema.crm.customer.v1.AddIRDChangesResponse;
import com.directvla.schema.crm.customer.v1.AddIRDChangesResult;
import com.directvla.schema.crm.customer.v1.AddServiceToWorkOrderRequest;
import com.directvla.schema.crm.customer.v1.AddServiceToWorkOrderResponse;
import com.directvla.schema.crm.customer.v1.AddServiceToWorkOrderResult;
import com.directvla.schema.crm.customer.v1.CancelWorkOrderRequest;
import com.directvla.schema.crm.customer.v1.CancelWorkOrderResponse;
import com.directvla.schema.crm.customer.v1.GetNewWorkOrderService;
import com.directvla.schema.crm.customer.v1.GetNewWorkOrderServiceRequest;
import com.directvla.schema.crm.customer.v1.GetNewWorkOrderServiceResponse;
import com.directvla.schema.crm.customer.v1.GetNewWorkOrderServiceResult;
import com.directvla.schema.crm.customer.v1.GetWorkOrder;
import com.directvla.schema.crm.customer.v1.GetWorkOrderRequest;
import com.directvla.schema.crm.customer.v1.GetWorkOrderResponse;
import com.directvla.schema.crm.customer.v1.GetWorkOrderResult;
import com.directvla.schema.crm.customer.v1.RemoveWorkOrderServiceRequest;
import com.directvla.schema.crm.customer.v1.RemoveWorkOrderServiceResponse;
import com.directvla.schema.crm.customer.v1.RemoveWorkOrderServiceResult;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * Implementa las operaciones de comunicación con un sistema externo
 * en esta implementación con IBS
 * 
 * Fecha de Creación: 3/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal
 */
@Stateless(name="CoreWorkOrderServiceBrokerLocal",mappedName="ejb/CoreWorkOrderServiceBrokerLocal")
public class CoreWorkOrderServiceBrokerImpl extends IBSWSBase implements CoreWorkOrderServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(CoreWorkOrderServiceBrokerImpl.class);

	@EJB(name = "Vista360ServiceBrokerLocal", beanInterface = Vista360ServiceBrokerLocal.class)
	private Vista360ServiceBrokerLocal vista360ServiceBroker;
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countriesDao;
	
	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDao;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDao;
	
	@EJB(name="WoTypeDAOLocal", beanInterface=WoTypeDAOLocal.class)
	private WoTypeDAOLocal woTypeDao;
	
	@EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDao;
	
	@EJB(name="ShippingOrderStatusDAOLocal", beanInterface=ShippingOrderStatusDAOLocal.class)
	private ShippingOrderStatusDAOLocal shippingOrderStatusDao;
	
	@EJB(name="ShippingOrderTypeDAOLocal", beanInterface=ShippingOrderTypeDAOLocal.class)
	private ShippingOrderTypeDAOLocal shippingOrderTypeDao;
	
	@EJB(name="ElementModelDAOLocal", beanInterface=ElementModelDAOLocal.class)
	private ElementModelDAOLocal elementModelDao;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal elementTypeDao;
	
	@EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDao;
	
	@EJB(name="BuildingDAOLocal",beanInterface=BuildingDAOLocal.class)
	private BuildingDAOLocal buildingDAO;
	
	@EJB(name="TechnologyDAOLocal",beanInterface=TechnologyDAOLocal.class)
	private TechnologyDAOLocal technologyDAO;
	
	@EJB(name="CoreWorkorderImporterServiceLocalTOA",beanInterface=CoreWorkorderImporterServiceLocalTOA.class)
	private CoreWorkorderImporterServiceLocalTOA workOrderImporterTOA;
	
	@EJB(name="CoreWOInventoryBusinessLocal",beanInterface=CoreWOInventoryBusinessLocal.class)
	private CoreWOInventoryBusinessLocal coreWOInventoryBusiness;
	
	@EJB(name="Ibs6StatusDAOLocal",beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal daoIbs6Status;
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	@EJB(name="CoreWorkOrderEventInfo",beanInterface=CoreWorkOrderEventInfoLocal.class)
	private CoreWorkOrderEventInfoLocal coreWorkOrderEventInfo;

	/**
	 * Metodo: Obtiene una referencia al objeto que es capaz de invocar operaciones
	 * sobre el servicio web de core expuesto por IBS	
	 * @return Objeto que es capaz de invocar operaciones del servicio web expuesto
	 * por IBS
	 * @author jjimenezh
	 * @throws ServiceLocatorException 
	 */
	private PtCore getService() throws ServiceLocatorException{
		PtCore service = ServiceLocator.getInstance().getCoreService();
		return service;
	}
	
	/**
	 * Metodo: Reporta a IBS el cambio en seriales de IRD. Se duplica el metodo para utilizar la DTO interna de HSP+
	 * @param workOrderCode código de la work order
	 * @param oldSerials seriales antiguos
	 * @param newSerials nuevos seriales
	 * @throws BusinessException En caso de error al reportar los cambios a IBS
	 * @author jjimenezh
	 */
	@Override
	public WorkOrderDTO convertIbsWoIntoSdiiWo(co.com.directv.sdii.dto.esb.WorkOrder ibsWorkOrder, String countryCode)throws BusinessException{
		log.info("== Inicia la operación convertIbsWoIntoSdiiWo/CoreWorkOrderServiceBrokerImpl ==");
		try {
			WorkOrderDTO workOrderDto = null;
			WorkOrder sdiiWorkorder = null;
			boolean woExist = false;
			
			//Variable que almacena el estado de la wo antes de la importacion
			WorkorderStatus previusStatusWo = null;
			
			//Se obtiene el estado de ibs sobre hsp y se envia al objeto
			Ibs6Status ibs6Status = this.daoIbs6Status.getIbs6StatusByIbsStateCode(ibsWorkOrder.getWorkorderStatusByActualStatusId());
			
			
			//Validando que la work order no exista previamente:
			sdiiWorkorder = workOrderDao.getWorkOrderByCode(ibsWorkOrder.getID());
			if(sdiiWorkorder != null){
				log.info("== La WorkOrder "+ibsWorkOrder.getID()+" importada de IBS ya existe en SmartDealer ==");
				woExist = true;
				previusStatusWo = sdiiWorkorder.getWorkorderStatusByActualStatusId();
			}else{
				log.info("== La WorkOrder "+ibsWorkOrder.getID()+" importada de IBS aun no existe en SmartDealer ==");
				woExist = false;
			}
			
			try {
				workOrderDto = this.buildWorkOrderFromIBSResult(ibsWorkOrder , countryCode, woExist, sdiiWorkorder);
				if(woExist){
					workOrderDto.getWorkOrder().setId(sdiiWorkorder.getId());
					
					//Almacena en el objeto el estado previo de la WO (Antes de la importación)
					workOrderDto.setWorkorderStatusPrevius(previusStatusWo);
				}
				
				//Almacena en el objeto el estado nuevo de la WO
				workOrderDto.setWorkorderStatusNew(workOrderDto.getWorkOrder().getWorkorderStatusByActualStatusId());
				workOrderDto.setIbs6Status(ibs6Status);
			} catch (Throwable e) {
				log.error("Error al tratar de obtener la información de la work order: \"" + ibsWorkOrder.getID() + "\" desde IBS, error: " + e.getMessage(),e);
				log.error("A pesar del error con la work order se continúa con las demás");
				throw super.manageException(e);
			}
			
			return workOrderDto;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación convertIbsWoIntoSdiiWo/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.info("== Termina la operación convertIbsWoIntoSdiiWo/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#importWorkOrders()
	 */
	public List<com.directvla.schema.crm.common.v1_1.WorkOrder> importWorkOrders(String countryCode)throws BusinessException{
		
		log.debug("== Inicia la operación importWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			GetWorkOrderRequest workOrderRequest = buildGetWorkOrderRequest(countryCode);
			GetWorkOrderResponse workorderResponse = service.getWorkOrder(workOrderRequest);
			
			GetWorkOrderResult workOrderResult = workorderResponse.getGetWorkOrderResult();
			WorkOrderCollection workOrdersColl = workOrderResult.getWorkOrderList();
			
			List<com.directvla.schema.crm.common.v1_1.WorkOrder> workOrders = workOrdersColl.getWorkOrder();
			log.info("Se inicia la importación de WO desde IBS, se encontraron " + workOrders.size() + " work orders para ser creadas en SDII desde el país: " + countryCode);
			
			return workOrders;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación importWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación importWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	

	/**
	 * 
	 * Metodo: Construye la informacion de la Workorder importada
	 * desde IBS para ser persistida en HSP+, diferenciando el
	 * estado de la WorkOrder para cargar la informacion que debe
	 * ser almacenada segun el estado en que se encuentre la WO.
	 * @param workOrder co.com.directv.sdii.dto.esb.WorkOrder
	 * @param customerCountryCode String
	 * @param woExist boolean
	 * @param actualWorkOrder WorkOrder
	 * @return WorkOrderDTO
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws HelperException
	 * @throws PropertiesException
	 * @throws ParseException
	 * @throws ServiceLocatorException
	 * @author aharker, se duplica el metodo para procesamiento en paralelo
	 */
	private WorkOrderDTO buildWorkOrderFromIBSResult(co.com.directv.sdii.dto.esb.WorkOrder workOrder , String customerCountryCode, boolean woExist, WorkOrder actualWorkOrder) throws BusinessException, DAOServiceException, DAOSQLException, HelperException, PropertiesException, ParseException, ServiceLocatorException {
		
		//Variables Globales
		WorkOrderDTO resultDto = new WorkOrderDTO();
		
		//Validaciones de la WorkOrder
		this.validationsOnWorkOrders(workOrder);	
		
		//Se carga el estado de la Workorder
		String woIbsStatusCode = workOrder.getWorkorderStatusByActualStatusId();
		//WorkorderStatus status = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(woIbsStatusCode);
		WorkorderStatus status = this.workOrderStatusSearch( woIbsStatusCode );
		
		
		
		//Se valida que el estado de IBS obtenido de la WorkOrder que se importo exista en HSP+
		validateResult("No se encontró en SDII el estado de work order con el código: " + woIbsStatusCode + " obtenido en la importacion de IBS", status, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
		
		if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity()) ) {
			resultDto = this.populateActiveWorkOrderIinformation(workOrder, customerCountryCode, woExist, actualWorkOrder);
		} else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity())
				|| status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() ) ) {
			resultDto = this.populateReassignWorkOrderFromIBSResult(workOrder, customerCountryCode, woExist, actualWorkOrder,false);
		} else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
				|| status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity()) )	{
			resultDto = this.populateScheduledWorkOrderIinformation( workOrder, actualWorkOrder );
		} else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())  )	{
			resultDto = this.populatePendingWorkOrderIinformation( workOrder, actualWorkOrder );
		} else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity())  )	{
			resultDto = this.populateRealizationWorkOrderInformation( workOrder, actualWorkOrder );
		} else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()) ) {
			resultDto = this.populateFinalizationWorkOrderInformation( workOrder, actualWorkOrder );
		}else if ( status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity()) ) {
			resultDto = this.populateCancelWorkOrderIinformation( workOrder, actualWorkOrder );
		} else {
			resultDto = this.populateDefaultWorkOrderIinformation(workOrder, actualWorkOrder);
		}
		
		return resultDto;
	}

	
	/**
	 * 
	 * Metodo: Crea los DTO's necesarios en el objeto WorkOrderDTO para ser enviados a la cola que actualiza el inventario, se duplica el metodo para Procesamiento en Paralelo
	 * de HSP a partir de la informacion que se descarga de la WO
	 * @param resultDto <tipo> <descripcion>
	 * @author jnova 
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private void buildInventoryUpdateDTO(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO resultDto) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		this.workOrderImporterTOA.populateWOMovementInventoryDTO(pIbsWorkOrder,resultDto);
	}
	
	/**
	 * 
	 * Metodo: Procesa la informacion de una WO, se duplica el metodo para PP
	 * en un estado no definido en el proceso.
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jlopez
	 */
	private WorkOrderDTO populateDefaultWorkOrderIinformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto,actualWorkOrder);
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacin de la Agenda
		workOrderImporterTOA.populataWOAgendaInformation(pIbsWorkOrder, workOrderDto);	
		
		//Se pobla la informacion del origen del procesamiento de la wo
		workOrderImporterTOA.populateWorkOrderProcessSource(workOrderDto);
		
		return workOrderDto;
	}

	
	/**
	 * 
	 * Metodo: Procesa la informacion de una WO, se duplica el metodo para usar la DTO interna de HSP+
	 * en estado Cancelada
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private WorkOrderDTO populateCancelWorkOrderIinformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto,actualWorkOrder);
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacin de la fecha de cancelacion
		workOrderImporterTOA.populataWOCancelationDate(workOrderDto);
		
		//Se coloca para que una work order cancelada no la tome el proceso de asignador
		workOrderImporterTOA.populataWOCancelProcessStatus(workOrderDto);
		
		//Se pobla la informacion del origen del procesamiento de la wo
		workOrderImporterTOA.populateWorkOrderProcessSource(workOrderDto);
		
		return workOrderDto;
	}
	
	
	/**
	 * 
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * @param pIbsWorkOrder
	 * @param customerCountryCode
	 * @param woExist
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @throws HelperException
	 * @throws ServiceLocatorException
	 * @throws ParseException
	 * @author jalopez
	 */
	private WorkOrderDTO populateActiveWorkOrderIinformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, String customerCountryCode, boolean woExist, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException, HelperException, ServiceLocatorException, ParseException {
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		workOrderDto = this.populateReassignWorkOrderFromIBSResult(pIbsWorkOrder, customerCountryCode, woExist, actualWorkOrder,true);		
		
		//Se pobla la informacin de la Agenda
		workOrderImporterTOA.populataWOAgendaInformation(pIbsWorkOrder, workOrderDto);
		
		return workOrderDto;
	}
	
	/**
	 * 
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private WorkOrderDTO populateScheduledWorkOrderIinformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto,actualWorkOrder);
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
        
        /* Spira 22159 - REPORTES FTP:Campos inconsistentes
         * el metodo siguiente necesita el estado cargado en workOrderDto
         */
		
		this.workOrderImporterTOA.populateHistoryReasonEventInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacin de la Agenda
		workOrderImporterTOA.populateWOAgendaInformationForScheduledReScheduled(pIbsWorkOrder, workOrderDto);
		//workOrderImporterTOA.populataWOAgendaInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la informacion del origen del procesamiento de la wo
		workOrderImporterTOA.populateWorkOrderProcessSource(workOrderDto);
		
		return workOrderDto;
	}
	
	/**
	 * 
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private WorkOrderDTO populatePendingWorkOrderIinformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto,actualWorkOrder);
		
		workOrderImporterTOA.populateWorkOrderPendingStatusInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacion del origen del procesamiento de la wo
		workOrderImporterTOA.populateWorkOrderProcessSource(workOrderDto);
		
		return workOrderDto;
	}

	/**
	 * 
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private WorkOrderDTO populateRealizationWorkOrderInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto,actualWorkOrder);
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacin de la Agenda
		workOrderImporterTOA.populateWOAgendaInformationForRealizedAndFinalizedWO(pIbsWorkOrder, workOrderDto);		
		
		//Se pobla la fecha de atencion de la WorkOrder
		workOrderImporterTOA.populateRealizationDateInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Para el caso de atendida se pone el origen en nulo
		if( workOrderDto != null && workOrderDto.getWorkOrder() != null ){
			workOrderDto.getWorkOrder().setProcessSourceId(null);
		}
		
		//Se pobla la informacion de los elementos involucrados en la atencion de la WO 
		this.buildInventoryUpdateDTO(pIbsWorkOrder, workOrderDto);
		
		//Si la workOrder esta en un estado finalizado o atendida se desmarca
		populateUnMarkWorkOrderAttention(workOrderDto);

		//Se pobla la informacion del Técnico
		workOrderImporterTOA.populateTechnicalInformation(pIbsWorkOrder, workOrderDto);
		
		return workOrderDto;
	}

	/**
	 * 
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * @param pIbsWorkOrder
	 * @param actualWorkOrder
	 * @return WorkOrderDTO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private WorkOrderDTO populateFinalizationWorkOrderInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		//Variables Globales
		WorkOrderDTO workOrderDto = new WorkOrderDTO();
		WorkOrder workorder = new WorkOrder();		
		workOrderDto.setWorkOrder(workorder);
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacion del estado de la Workorder
		workOrderImporterTOA.populateWorkOrderStatusInformation( pIbsWorkOrder, workOrderDto, actualWorkOrder );
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacin de la Agenda
		workOrderImporterTOA.populateWOAgendaInformationForRealizedAndFinalizedWO(pIbsWorkOrder, workOrderDto);		
		
		//Se pobla la fecha de finalizacion de la WorkOrder
		workOrderImporterTOA.populateFinalizationDateInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Para el caso de atendida se pone el origen en nulo
		if( workOrderDto != null && workOrderDto.getWorkOrder() != null ){
			workOrderDto.getWorkOrder().setProcessSourceId(null);
		}
		
		//Se pobla la informacion de los elementos involucrados en la atencion de la WO
		this.buildInventoryUpdateDTO(pIbsWorkOrder,workOrderDto);
		
		//Si la workOrder esta en un estado finalizado o atendida se desmarca
		populateUnMarkWorkOrderAttention(workOrderDto);
		
		return workOrderDto;
	}
	
	private void populateUnMarkWorkOrderAttention(WorkOrderDTO workOrderDto){
		workOrderDto.setUnMarkWorkOrderAttention(new Boolean(true));
	}

	
	/**
	 * Metodo: Pobla la informacion de la WorkOrder, se duplica el metodo para usar la DTO interna de HSP+
	 * que sera persistida en HSP+.
	 * @param pIbsWorkOrder
	 * @param workOrderDto
	 * @param actualWorkOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jalopez
	 */
	private void populateTransversalWorkOrderInformation(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, WorkOrderDTO workOrderDto, WorkOrder actualWorkOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
				
		//Se pobla la informacion general del la WorkOrder
		workOrderImporterTOA.populateGeneralInformation(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacion del estado de ibs de la WO
		workOrderImporterTOA.populateWorkOrderIbsActualStatus(pIbsWorkOrder, workOrderDto, actualWorkOrder);
		
		//Se pobla la informacion del Pais y Codigo Postal		
		workOrderImporterTOA.populateLocaleInformation( pIbsWorkOrder, workOrderDto );
		
		//Se pobla la fecha de creacion de la WorkOrder
		workOrderImporterTOA.populateCreationDateInformation( pIbsWorkOrder, workOrderDto );
		
		//Se pobla la informacion del Cliente
		workOrderImporterTOA.populateCustomerInformation( pIbsWorkOrder, workOrderDto );
		
		//Se pobla la informacion del Building
		workOrderImporterTOA.populateBuildingInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla el dealer dummy
		workOrderImporterTOA.populateDealerDummy( workOrderDto );
		
		//Se pobla la informacion del Dealer
		workOrderImporterTOA.populateDealerInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la informacion del vendedor
		workOrderImporterTOA.populateDealerSaleInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la informacion del History Event
		workOrderImporterTOA.populateHistoryCodeEventInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la fecha de agendamiento de la WO
		workOrderImporterTOA.populateProgrammingDateInformation(pIbsWorkOrder, workOrderDto);
		
		//Se pobla la informacion del usuario
		workOrderImporterTOA.populateUserInformation(pIbsWorkOrder, workOrderDto);		
		
		//Se pobla la informacion de la importacion de la WO
		workOrderImporterTOA.populateWOImportationDateInformation(workOrderDto);
		
		//Se pobla la informacion de la Shipping order
		workOrderImporterTOA.populateShippingOrderInformation(pIbsWorkOrder, workOrderDto);
		
		//Informacion de los servicios de la WO obtenidos de IBS
		this.associatedServicesWorkOrder(workOrderDto,pIbsWorkOrder, workOrderDto.getWorkOrder(), workOrderDto.getShippingOrder(), workOrderDto.getShippingOrderElements());
		
		//Vinculacion del tipo de Servicio de la WO de IBS con el tipo de WO de SDII
		this.linkTypeServiceOrder(pIbsWorkOrder, workOrderDto.getWorkOrder());		
		
		//Se pobla la informacion del edificio
		workOrderImporterTOA.populateBuildingInformacion(pIbsWorkOrder, workOrderDto);		
		
		//Se pobla la informacion si requiere marcar la work order por alarma y priorizacion
		workOrderMarkBusiness.populateWorkOrderMarkPriorityService(pIbsWorkOrder,workOrderDto);
		
		workOrderMarkBusiness.populateWorkOrderMarkPriorityNexus(pIbsWorkOrder,workOrderDto);
	}
	
	/**
	 * Metodo: Le da valor a la workorder que viene en estado reasignado desde IBS
	 * @param workOrder
	 * @param customerCountryCode
	 * @param woExist
	 * @param actualWorkOrder
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws HelperException
	 * @throws PropertiesException
	 * @throws ParseException
	 * @throws ServiceLocatorException
	 * se duplica el metodo para usar la DTO interna de HSP+
	 */
	private WorkOrderDTO populateReassignWorkOrderFromIBSResult(co.com.directv.sdii.dto.esb.WorkOrder workOrder , String customerCountryCode, boolean woExist, WorkOrder actualWorkOrder,boolean isAssign) throws BusinessException, DAOServiceException, DAOSQLException, HelperException, PropertiesException, ParseException, ServiceLocatorException {

		
		//Variables Globales
		WorkOrderDTO resultDto = new WorkOrderDTO();		
		WorkOrder result = new WorkOrder();
		resultDto.setWorkOrder( result );
		
		//Se pobla la informacion comun a todos los estados de las WorkOrders
		this.populateTransversalWorkOrderInformation(workOrder, resultDto, actualWorkOrder);

		//Se asigna el estado actual de la WO
		resultDto.getWorkOrder().setWorkorderStatusByActualStatusId( this.populateWorkorderStatus(workOrder, resultDto, isAssign) );
		
		//se agrega para tomar el estado previo si la WO ya existe en SDII
		if ( woExist ) {			
			resultDto.getWorkOrder().setWorkorderStatusByPreviusStatusId( actualWorkOrder.getWorkorderStatusByActualStatusId() );
		} 
		
		//Se pobla la informacion de la Asignacion
		workOrderImporterTOA.populateWOAssignmentInformation(workOrder, resultDto, actualWorkOrder);
		
		//Se pobla el buildingCode
		this.populateWorkORderBuildingCode(workOrder,resultDto,customerCountryCode);
		
		//Se pobla la informacion del origen del procesamiento de la wo
		this.workOrderImporterTOA.populateWorkOrderProcessSource(resultDto);
		
		//Se revisa la agenda en caso que llegue asignada - reasignada
		if( resultDto.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity()) ){
			this.workOrderImporterTOA.populateWOAgendaInformationForAssign(workOrder, resultDto, customerCountryCode);
		}
		return resultDto;
	}

	/**
	 * Metodo: Pobla el estado de la WO, se duplica el metodo para usar la DTO interna de HSP+
	 * @param workOrder
	 * @param resultDto
	 * @param isAssign
	 * @return WorkorderStatus
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jnova
	 */
	public WorkorderStatus populateWorkorderStatus(co.com.directv.sdii.dto.esb.WorkOrder workOrder , WorkOrderDTO resultDto , boolean isAssign) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		Long dealerDummyCodeLong = resultDto.getDealerDummyCodeLong();
		Long dealerCode = resultDto.getDealerAssignmentCode();
		
		//Informacion del estado de la Workorder
		WorkorderStatus status = null;
		boolean isWoAssigned = false;
		
		//Si el dealer asignado a la work order no es el dealer dummy, se debe generar el registro de asignación para ese dealer porque significa que la WO fué asignada desde IBS.
		if(dealerDummyCodeLong.longValue() != dealerCode.longValue()){
			isWoAssigned = true;
		}
		
		//Determina el estado inicial de la WorkOrder	
		String woIbsStatusCode = workOrder.getWorkorderStatusByActualStatusId();
		status = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(woIbsStatusCode);
		validateResult("No se encontró en SDII el estado de work order con el código " + workOrder.getWorkorderStatusByActualStatusId(), status, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
		
		//si esta asignada se crea en estado Asignada
		if( isWoAssigned ) {
			if( isAssign ){
				status = workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
			} else {
				status = workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
			}
			validateResult("No se encontró en SDII el estado de work order reasignado con el código " + CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity(), status, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
		} else { //Si no está asignada se crea en estado Activa
			status = workorderStatusDao.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
			validateResult("No se encontró en SDII el estado de work order activo con el código " + CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity(), status, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
		}
		return status;
	}

	/**
	 * Metodo: Le da valor a la informacion del buildingCode de la WO en caso que aplique
	 * @param workOrder
	 * @param resultDto
	 * @param customerCountryCode
	 * @throws BusinessException
	 * @throws PropertiesException
	 * se duplica el metodo para usar la DTO interna de HSP+
	 */
	private void populateWorkORderBuildingCode(co.com.directv.sdii.dto.esb.WorkOrder workOrder , WorkOrderDTO resultDto , String customerCountryCode) throws BusinessException, PropertiesException{
		//obteniendo el código del edificio:
		//String buildingCode = vista360ServiceBroker.getCustomerCharacteristicFromIBS(CodesBusinessEntityEnum.IBS_BULDING_CODE_CHARACTERISTIC_NAME.getCodeEntity(),resultDto.getCustomerInfoAggregatedDTO() );
		//String buildingCode = coreWorkOrderEventInfo.getCustomerCharacteristicFromIBSInfo(CodesBusinessEntityEnum.IBS_BULDING_CODE_CHARACTERISTIC_NAME.getCodeEntity(),resultDto.getCustomerInfoAggregatedDTO() );
		String buildingCode = null;
		resultDto.getWorkOrder().setBuildingCode(buildingCode);
		
		//En caso que no sea nulo ni vacio, se crea el edificio
		if( buildingCode != null && !buildingCode.equals("") ) {
			//resultDto.setBuilding( createBuilding(buildingCode, customerCountryCode) );
			resultDto.setBuilding( createBuilding(buildingCode, customerCountryCode, workOrder) );
		}
	}

	/**
	 * 
	 * Metodo: Vincula el tipo de WO de SDII resultado
	 * del tipo de servicio que proviene en la WO de IBS,
	 * se relaciona el tipo de servicio de la categoria de
	 * la WO de IBS con el tipo de WO de SDII, se duplica el metodo para usar la DTO interna de HSP+
	 * @param workOrder
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	private void linkTypeServiceOrder(co.com.directv.sdii.dto.esb.WorkOrder workOrder, WorkOrder result) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException{
		
		String woTypeCode = null;
		String woTypeservice = null;
		
		//Se itera la lista de servicios para obtener uno de los codigos, puesto que el 
		//servicio no retorna el tipo de wo, se debe obtener uno de los codigos, se hace la
		//presuncion que todos los servicios son del mismo tipo ya que el contrato no cumple
		//con la necesidad, cambio aprobado por DTV, Responsable Milton Jimenez
		Service service = null;
		if(workOrder.getServiceList() != null ){
			if( workOrder.getServiceList().getService() != null ){
				if( !workOrder.getServiceList().getService().isEmpty() ){
					co.com.directv.sdii.dto.esb.Service ibsService = workOrder.getServiceList().getService().get(0);
					service = serviceDao.getServiceBySimpleCode( ibsService.getCode() );
					validateResult("No se encontro el Tipo de Servicio "+ibsService.getCode()+" obtenido de la WO importada de IBS en la Base de Datos", service, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
					//se obtiene el tipo de servicio de la categoria del servicio de la WO, para buscar el tipo de WO
					woTypeservice = service.getServiceCategory().getServiceType().getServiceTypeCode();		
				}
			}
		}	
		
		//Se carga el tipo de WO del servicio asociado, de lo contrario se crea la WO como SERVICE por defecto
		WoType woType = null;
		if( woTypeservice == null ){
			woTypeservice = CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity();		
		}
		woType = woTypeDao.getWoTypeByCode(woTypeservice);		
		
		validateResult("No se encontró el workOrderType por el código especificado desde IBS: " + woTypeCode, woType, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		result.setWoTypeByWoTypeId(woType);
		woTypeCode = workOrder.getWoTypeByPreviusWoTypeId() == null ? null : workOrder.getWoTypeByPreviusWoTypeId().getWoTypeCode();
		if(woTypeCode != null){
			woType = woTypeDao.getWoTypeByCode(woTypeCode);
			validateResult("No se encontró el workOrderType por el código especificado desde IBS: " + woTypeCode, woType, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			result.setWoTypeByPreviusWoTypeId(woType);
		}
	}
	
	/**
	 * 
	 * Metodo: Cuando el codigo de edificio del la WO no viene nulo se busca la informacion del edificio en IBS invocando
	 * el servicio de cliente enviando como codigo de cliente el codigo de edificio. En caso que el edificio ya exista 
	 * en SDII, se actualiza con la informacion proveniente de IBS, en caos contrario, se crea.
	 * @param buildingCode String Codigo de edificio
	 * @param countryCode String Codigo de pais para pasar como source en el servicio de customer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	//private Building createBuilding(String buildingCode, String countryCode) throws BusinessException {
	private Building createBuilding(String buildingCode, String countryCode, co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder) throws BusinessException {
		log.debug("== Inicia createBuilding/CoreWorkOrderServiceBrokerImpl ==");
		try{
			Building building = buildingDAO.getBuildingByIbsBuldingCodeAndCountryCode(new Long( buildingCode ), countryCode);
			if( building == null ){
				building = new Building();
				//vista360ServiceBroker.populateBuildingFromIbsCust(buildingCode, countryCode, building);
				coreWorkOrderEventInfo.populateBuildingFromIbsCustInfo(buildingCode, countryCode, building, pIbsWorkOrder);
			} else{
				//vista360ServiceBroker.populateBuildingFromIbsCust(buildingCode, countryCode, building);
				coreWorkOrderEventInfo.populateBuildingFromIbsCustInfo(buildingCode, countryCode, building, pIbsWorkOrder);
			}
			return building;
		} catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operacion createBuilding/CoreWorkOrderServiceBrokerImpl", e);
			throw super.manageException(e);
		} finally {
			log.debug("== Termina createBuilding/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/**
	 * 
	 * Metodo: asocia los servicios de la WO de IBS a la
	 * Wo de SDII, se duplica el metodo para usar la DTO interna de HSP+
	 * @param workOrder
	 * @param so
	 * @param soElements
	 * @author jalopez
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private void associatedServicesWorkOrder(WorkOrderDTO workOrderDto, co.com.directv.sdii.dto.esb.WorkOrder workOrder, WorkOrder result, ShippingOrder so, List<ShippingOrderElement> soElements) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		List<WorkOrderService> services = this.buildWorkOrderServices(workOrderDto,workOrder.getServiceList().getService(), workOrder, so, soElements);
		Set<WorkOrderService> servicesSet = new HashSet<WorkOrderService>();
		servicesSet.addAll(services);
		result.setWorkOrderServices(servicesSet);	
	}
	
	/**
	 * 
	 * Metodo: Valida el estado de la WO para determinar
	 * como proceder segun el estado en el cual llega de
	 * IBS.
	 * @param workOrder WorkOrder
	 * @param status WorkorderStatus
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 */
	private WorkorderStatus validateStatusWorkOrder(WorkorderStatus status, WorkOrder actualWorkOrder) throws BusinessException{
		log.debug("== Inicia validateStatusWorkOrder/CoreWOBusiness ==");
		WorkorderStatus actualStatus = status;
		try{
			//se valida que el estado que proviene de la WO de IBS sea pendiente
			if (status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())){
				//se valida que el estado de la WO actual se encuentre rechazada, si es asi se deja el  estado actual
				if( actualWorkOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()) ){
					actualStatus = actualWorkOrder.getWorkorderStatusByActualStatusId();
				}
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateStatusWorkOrder/addWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateStatusWorkOrder/CoreWOBusiness ==");
		}
		return actualStatus;
	}
	
	/**
	 * 
	 * Metodo: Se crea el registro de asignacion
	 * @param result
	 * @returnv WoAssignment
	 * @author jjimenezh
	 */
	private WoAssignment builWoAssignmentFromIbs(WorkOrder result) {
		WoAssignment woAssignment = new WoAssignment();
		woAssignment.setDealerAssignmentDate( new Date() );
		woAssignment.setDealerId( result.getDealerId() );
		woAssignment.setWorkOrder( result );
		return woAssignment;
	}
	
	
	/**
	 * 
	 * Metodo: Implementacion para la separacion del tipo
	 * de estado N-Pendiente, puesto que para SDII existe
	 * dos estados que informan el cambio a IBS como N, 
	 * que son N-Dificultad y R-Rechazo (Pendientes en IBS)
	 * @param woStatus
	 * @return WorkorderStatus
	 * @throws BusinessException
	 * @author jalopez
	 */
	private WorkorderStatus workOrderStatusSearch (String woStatus) throws BusinessException{
		log.debug("== Inicia la operación workOrderStatusSearch/CoreWorkOrderServiceBrokerImpl ==");
	    Ibs6Status ibs6Status = new Ibs6Status();
		WorkorderStatus actualStatus = new WorkorderStatus();
		
		try{
			ibs6Status.setIbs6StateCode(woStatus);
			List<WorkorderStatus>  statusList = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(ibs6Status);
			if(!statusList.isEmpty()){
				if (statusList.size() > 1) {
					//se verifica que el estado sea N-Pendiente que es el duplicado en Ibs6Status
					//para el caso de Pendiente y Rechazo
					for (WorkorderStatus workorderStatus : statusList) {
						if( workorderStatus.getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) ){
							actualStatus = workorderStatus;
						}
					}
				}else
					actualStatus = statusList.get(0);
			}
		} catch (Throwable ex) {			
			log.debug("== Error al tratar de ejecutar la operación workOrderStatusSearch/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación workOrderStatusSearch/CoreWorkOrderServiceBrokerImpl ==");
		}
		return actualStatus;
	}

	private WorkOrderAgenda buildWoAgendaFromIbs(com.directvla.schema.crm.common.v1_1.WorkOrder workOrder, WorkOrder resulWorkOrder, WoAssignment woAssignment, Customer customer) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		WorkOrderAgenda woAgenda = new WorkOrderAgenda();
		Date woAgendaDate = UtilsBusiness.dateFromGregorianCalendar(workOrder.getRequestedDeliveryDate());
		woAgenda.setAgendationDate(woAgendaDate);
		
		//TODO jjimenezh 2010-07-29 completar la información de agenda con la información del servicio de IBS, (pendiente información de jornada) además si viene agendada se debe afectar la capacidad del dealer
		//*
		woAgenda.setContactPerson(customer.getFirstName() + " " + customer.getLastName());
		woAgenda.setDescription(ApplicationTextEnum.SCHEDULE_WORK_ORDER.getApplicationTextValue());
		
		List<ServiceHour> allServiceHours = serviceHourDao.getServiceHoursByCountryCodeAndServiceHourStatusCodeAndInHourRange(resulWorkOrder.getCountry().getCountryCode(), CodesBusinessEntityEnum.CODIGO_SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity(), woAgendaDate);
		if(allServiceHours.isEmpty()){
			throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + ": No se encontró una jornada activa que pueda cubrir el horario de atención de la WO solicitado: " + UtilsBusiness.dateToString(woAgendaDate, UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
		}
		int size = allServiceHours.size();
				
		ServiceHour serviceHour = allServiceHours.get( new Random().nextInt(size) );
		woAgenda.setServiceHour(serviceHour);
		
		woAgenda.setWoAssignment(woAssignment);
		
		//*/
		return woAgenda;
	}

	/**
	 * Metodo: Obtiene una lista de elementos de shipping order por los parámetros de IBS
	 * @param shippingOrder orden de compra de ibs
	 * @param workOrder orden de trabajo de IBS
	 * @param result Orden de trabajo de SDII
	 * @return lista con los elementos de la shipping order
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	private List<ShippingOrderElement> buildShippingOrderElementsFromIbs( com.directvla.schema.crm.common.v1_1.ShippingOrder shippingOrder ) throws BusinessException, DAOServiceException, DAOSQLException {
		List<ShippingOrderElement> resultList = new ArrayList<ShippingOrderElement>();
		ShippingOrderElement soElement = null;
		
		List<Product> productInfo = shippingOrder.getProductList().getProduct();
		String elementModelCode, elementTypeCode;
		ElementModel elementModel;
		ElementType elementType;
		for (Product product : productInfo) {
			soElement = new ShippingOrderElement();
			elementModelCode = product.getModelKey();
			validateResult("product.getModelKey()", elementModelCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			elementModel = elementModelDao.getElementModelByCode(elementModelCode);
			//validateResult("No se encontró el modelo de elemento por el código especificado por IBS: " + elementModelCode, elementModel, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			soElement.setElementModel(elementModel);
			
			
			elementTypeCode = product.getType();
			validateResult("product.getType()", elementTypeCode, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			elementType = elementTypeDao.getElementTypeByCode(elementTypeCode);			
			validateResult("No se encontró el tipo de elemento por el código especificado por IBS: " + elementTypeCode, elementType, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			
			soElement.setElementType(elementType);			
			resultList.add(soElement);
		}
		return resultList;
	}
	
	/**
	 * 
	 * Metodo: Obtiene una lista de detalles de shipping order
	 * @param shippingOrder
	 * @return Lista de detalles de la shipping order
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<ShippingOrderDetail> buildShippingOrdersDetailsFromIbs( com.directvla.schema.crm.common.v1_1.ShippingOrder shippingOrder ) throws BusinessException, DAOServiceException, DAOSQLException {
		List<ShippingOrderDetail> resultList = null;// 
		
		if( shippingOrder.getProductList() != null && shippingOrder.getProductList().getProduct() != null 
				&& !shippingOrder.getProductList().getProduct().isEmpty() ){
			resultList = new ArrayList<ShippingOrderDetail>();
			List<Product> productInfo = shippingOrder.getProductList().getProduct();
			for (Product product : productInfo) {
				ShippingOrderDetail detail = new ShippingOrderDetail();
				Technology technology = technologyDAO.getTechnologyByIbsCode( product.getType() );
				if( technology != null ){
					detail.setTechnology(technology);
					detail.setSerialCode( product.getProductSerialNumber() );
					detail.setIbsModelCode( product.getModelKey() );
					resultList.add( detail );
				} else {
					log.debug("El producto "+product.getCode()+" no se pudo mapear para agregar la tecnologia en la SO con codigo "+shippingOrder.getCode());
				}
			}
		}
		
		return resultList;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param shippingOrder
	 * @param workOrder
	 * @param workOrderSdii
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private ShippingOrder buildSdiiShippingOrderFromIbs(
			com.directvla.schema.crm.common.v1_1.ShippingOrder IbsShippingOrder,
			com.directvla.schema.crm.common.v1_1.WorkOrder ibsWorkOrder, WorkOrder workOrderSdii) throws BusinessException, DAOServiceException, DAOSQLException {
		ShippingOrder result = new ShippingOrder();
		
		populateShippingOrderDummyData(IbsShippingOrder);
		
		result.setContractCode(IbsShippingOrder.getContractCode());
		validateResult("IbsShippingOrder.getContractNumber()", IbsShippingOrder.getContractNumber(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		result.setContractNumber(IbsShippingOrder.getContractNumber());
		
		Date creationEventDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDate());
		result.setCreationEventDate(creationEventDate);
		
		//result.setRealShippingDate(realShippingDate);
		
		Date shippingDate = UtilsBusiness.dateFromGregorianCalendar(IbsShippingOrder.getInteractionDateComplete());
		result.setShippingDate(shippingDate);
		
		result.setShippingMethodCode(IbsShippingOrder.getShippingMethodKey());
		result.setShippingOrderCode(IbsShippingOrder.getID());
		
		String shippingOrderStatusCode = IbsShippingOrder.getInteractionStatus();
		ShippingOrderStatus shippingOrderStatus = shippingOrderStatusDao.getShippingOrderStatusByCode(shippingOrderStatusCode);
		validateResult("No se encontró el estado shipping order en Sdii con el código de ibs: " + shippingOrderStatusCode, shippingOrderStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderStatus(shippingOrderStatus);
		
		String shippingOrderTypeCode = IbsShippingOrder.getShippingOrderKey();
		ShippingOrderType shippingOrderType = shippingOrderTypeDao.getShippingOrderTypeByCode(shippingOrderTypeCode);
		validateResult("No se encontró el tipo shipping order en Sdii con el código de ibs: " + shippingOrderTypeCode, shippingOrderType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		result.setShippingOrderType(shippingOrderType);
		
		result.setSoDescription(IbsShippingOrder.getDescription());
		result.setVendorCode(IbsShippingOrder.getSoldBy());
		
		return result;
	}

	private void populateShippingOrderDummyData(
			com.directvla.schema.crm.common.v1_1.ShippingOrder ibsShippingOrder) {
		String strEmpty = ApplicationTextEnum.EMPTY.getApplicationTextValue();
		if(ibsShippingOrder.getContractNumber() == null || ibsShippingOrder.getContractNumber().trim().length() == 0){
			ibsShippingOrder.setContractNumber(strEmpty);
		}
		
		if(ibsShippingOrder.getContractCode() == null || ibsShippingOrder.getContractCode().trim().length() == 0){
			ibsShippingOrder.setContractCode(strEmpty);
		}
		
		if(ibsShippingOrder.getSoldBy() == null || ibsShippingOrder.getSoldBy().trim().length() == 0){
			ibsShippingOrder.setSoldBy(strEmpty);
		}
	}

	/**
	 * Metodo: Pobla los servicios de la WO, se duplica el metodo para usar la DTO interna de HSP+
	 * @param ibsServices
	 * @param workOrder
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jalopez
	 * @throws PropertiesException 
	 */
	private List<WorkOrderService> buildWorkOrderServices(WorkOrderDTO workOrderDto, List<co.com.directv.sdii.dto.esb.Service> ibsServices, co.com.directv.sdii.dto.esb.WorkOrder workOrder, ShippingOrder so, List<ShippingOrderElement> soElements) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		List<WorkOrderService> result = new ArrayList<WorkOrderService>();
		WorkOrderService woService;
		for (co.com.directv.sdii.dto.esb.Service ibsService : ibsServices) {
			
			Service service = serviceDao.getServiceBySimpleCode( ibsService.getCode() );			
			// Realizando todas las validaciones necesarias para crear una WO. 
			this.validationsOnWorkOrdersServices(workOrderDto,ibsService, service, so, workOrder, soElements);			
			woService = new WorkOrderService();
			woService.setSerialNumber(ibsService.getProductSerialNumber());
			//01-08-2011 jnova se agrega funcionalidad de almanecar el serial del elemento vinculado
			//woService.setLinkedSerialNumber( this.getLinkedSerialNumbe	r( ibsService.getProductSerialNumber(), workOrderDto.getWorkOrder().getCountry().getCountryCode() ) );
			woService.setLinkedSerialNumber(this.getLinkedSerialNumber(ibsService.getProductSerialNumber(), workOrder));
			
			validateResult("No se encontró en SDII el servicio con código IBS: " + ibsService.getCode(), service, ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			
			woService.setService(service);
			woService.setServiceInclusionDate(new Date());
			woService.setIbsServiceKey( ibsService.getServiceKey() );
			boolean shippingOrderRequired = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equalsIgnoreCase(service.getIsShippingOrderRequired());
			woService.setShippingOrderRequired(shippingOrderRequired);
			result.add(woService);
		}
		return result;
	}
	
	/**
	 * 
	 * Metodo: Busca el serial del elemento vincualado a un serial dentro de la lista de productos del cliente
	 * @param serialNumber, workOrder
	 * @return <tipo> <descripcion>
	 * @author cgl
	 * 
	 */
	private String getLinkedSerialNumber(String serialNumber, co.com.directv.sdii.dto.esb.WorkOrder workOrder){	
		if(workOrder.getCustomer()!=null && workOrder.getCustomer().getProductList()!=null 
				&& workOrder.getCustomer().getProductList().getProduct()!=null
				&& !workOrder.getCustomer().getProductList().getProduct().isEmpty()){
			for(co.com.directv.sdii.dto.esb.Product product : workOrder.getCustomer().getProductList().getProduct()){
				if(serialNumber.equals(product.getProvisionedDevices())) {
					return product.getProvisionedDevicesRelated();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Metodo: Consulta el servicio de inventarios para obtener el serial del elemento vinculado a un serial
	 * @param serialCode
	 * @return <tipo> <descripcion>
	 * @author jnova
	 * @throws BusinessException 
	 */
	@Deprecated
	private String getLinkedSerialNumber(String serialCode, String countryCode) throws BusinessException{
		StringBuilder response = new StringBuilder();
		if( serialCode != null && !serialCode.equals("") ){
			InventoryDTO inventoryDTO = new InventoryDTO();
			ElementDTO elementDTO = new ElementDTO();
			SerializedVO serializedVO = new SerializedVO();
			serializedVO.setSerialCode(serialCode);
			elementDTO.setSerializedVO( serializedVO );
			inventoryDTO.setElementDTO( elementDTO );
			inventoryDTO.setCountryCode(countryCode);
			ElementDTO serviceResponse = this.coreWOInventoryBusiness.getElementBySerialCode(inventoryDTO);
			if( serviceResponse != null && serviceResponse.getSerializedVO() != null 
					&& serviceResponse.getSerializedVO().getSerialized() != null 
					&& serviceResponse.getSerializedVO().getSerialized().getSerialCode() != null){
				response.append( serviceResponse.getSerializedVO().getSerialized().getSerialCode() );
			}
		}
		return response.toString();
	}

	/** Metodo que realiza todas las validaciones para importar una WO desde el sistema IBS
	 * CU 17
	 * @param ibsService
	 * @param service
	 * @param so
	 * @param workOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * se duplica el metodo para usar la DTO interna de HSP+
	 */
	private void validationsOnWorkOrdersServices(WorkOrderDTO workOrderDto,co.com.directv.sdii.dto.esb.Service ibsService, Service service, ShippingOrder so, co.com.directv.sdii.dto.esb.WorkOrder workOrder, List<ShippingOrderElement> soElements) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		log.debug("== Inicia la operación validationsOnWorkOrdersServices/CoreWorkOrderServiceBrokerImpl ==");
				
		// Validando que el servicio que se consulta exista en SDII
		if(service == null){
			Object[] params = null;
			params = new Object[1];			
			params[0] = ibsService.getCode();
			throw new BusinessException(ErrorBusinessMessages.CORE_CR049.getCode(), ErrorBusinessMessages.CORE_CR049.getMessage(params));
		}
		
		Long idCountry = this.getCountryFromIbsWo(workOrder).getId();
		
		//Validando si el servicio requiere Shipping Order se deberá lanzar un error, especificando que el servicio incluido en la WO requiere Shipping Order
		if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equalsIgnoreCase(service.getIsShippingOrderRequired()) && workOrder.getShippingOrder() == null){
			String message = " El servicio con código: "+service.getServiceCode()+", incluido en la Work order, requiere Shipping Order";
			log.error(message);		
			//Para esta validacion no se envia correo aca, si no desde la clase CoreWOImporter
			//String countryName = getCountryFromIbsWo(workOrder).getCountryName();					
			//woLoadBusiness.sendMissingShippingOrderMail(workOrder.getID() ,workOrder.getCustomer().getID()+" "+workOrder.getCustomer().getName() , idCountry, countryName, message);
		}
		
		// Validando si existe una SO y si es el caso que esta tenga elementos
		if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equalsIgnoreCase(service.getIsShippingOrderRequired())){
			if(workOrder.getShippingOrder() != null){
				if(workOrder.getShippingOrder().getProductList() == null){
					String message = " La Work order de IBS con código: "+workOrder.getID()+", debe tener por lo menos 1 elemento en su shipping order";
					log.error(message);
					throw new BusinessException(ErrorBusinessMessages.CORE_CR028.getCode(), ErrorBusinessMessages.CORE_CR028.getMessage() + message);
				}
			}
		}
		
		//Solo se valida el estado de la ShippingOrder para los estados diferentes a
		//T - Realizada y F - Finalizada de la WorkOrder.
		String woIbsStatusCode = workOrder.getWorkorderStatusByActualStatusId();
		WorkorderStatus status = workorderStatusDao.getWorkorderStatusByIBS6StatusCode(woIbsStatusCode);
		if( !status.getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() ) 
			 && !status.getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity() )	){
			
			// Validando que las shipping orders se encuentran realcionados 
			//los estados Payment Received(Pago Recibido)(3) y May Ship(Embarcar)(1)
			String receivedPaymentState = CodesBusinessEntityEnum.SHIPPING_ORDER_STATUS_PAYMENT_RECEIVED.getCodeEntity();
			String shipState = CodesBusinessEntityEnum.SHIPPING_ORDER_STATUS_MAY_SHIP.getCodeEntity();
			if(so != null 
			   && ( !so.getShippingOrderStatus().getSoStatusCode().equalsIgnoreCase(receivedPaymentState) 
			   && !so.getShippingOrderStatus().getSoStatusCode().equalsIgnoreCase(shipState)) ){
				
				String code = ErrorBusinessMessages.CORE_CR059.getCode();
				Object[] params = {service.getServiceCode(),workOrder.getID()};
				String message = ErrorBusinessMessages.CORE_CR059.getMessage(params);
				//05/12/2011 jnova se comenta validacion de estado de WO por incidencia reportada para que si deje bajar la WO
				log.warn(message);
				//Se marca el DTO con error
				workOrderDto.setWarning(true);
				workOrderDto.setErrorCode(code);
				workOrderDto.setErrorMessage(message);
				//throw new BusinessException(ErrorBusinessMessages.CORE_CR026.getCode(), ErrorBusinessMessages.CORE_CR026.getMessage() + message);
			}
		}

		// Validando, Un código de IBS solo debe tener una WorkOrder  con estado diferente 
		//de cancelado cuando el servicio sea de subcategoría Basica
		String basicInstallationCategoty =  CodesBusinessEntityEnum.SERVICE_CATEGORY_BASIC_INSTALLATION.getCodeEntity();
		
		if( service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( basicInstallationCategoty ) ){
			Long countWorkOrders = workOrderDao.getWorkOrderByStateIbsWoCodeIbsWoCustCodeServiceCategoryCode
			(workOrder.getID(), workOrder.getCustomer().getID(), basicInstallationCategoty, idCountry);
			
			if(countWorkOrders != null && countWorkOrders != 0){
				String message = " La Work order de IBS con código: "+workOrder.getID()+", con estado diferente a cancelado o finalizado y de instalación básica ya existe en SDII";
				log.info("Informacion de la WO obtenida para la validacion [ WorkOrder: "+workOrder.getID()+" Customer: "+workOrder.getCustomer().getID()+" Service Type: "+ibsService.getCode()+"]");
				log.error(message);
				throw new BusinessException(ErrorBusinessMessages.CORE_CR027.getCode(), ErrorBusinessMessages.CORE_CR027.getMessage() + message);			
			}
		}
		log.debug("== Termina la operación validationsOnWorkOrdersServices/CoreWorkOrderServiceBrokerImpl ==");		
	}
	
	/** Metodo que realiza todas las validaciones para importar una WO desde el sistema IBS
	 * CU 17, se duplica el metodo para usar la DTO interna de HSP+
	 * @param workOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private void validationsOnWorkOrders(co.com.directv.sdii.dto.esb.WorkOrder workOrder) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		log.debug("== Inicia la operación validationsOnWorkOrders/CoreWorkOrderServiceBrokerImpl ==");
		
		//Long idCountry = getCountryFromIbsWo(workOrder).getId();
		
		//validacion de informacion general
		validateResult("workOrder.getGeographicArea()", workOrder.getGeographicArea(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		validateResult("workOrder.getGeographicArea().getCountry()", workOrder.getGeographicArea().getCountry(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		validateResult("workOrder.getGeographicArea().getCountry().getIso2Code()", workOrder.getGeographicArea().getCountry().getIso2Code(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		
		//Validando el cliente:
		validateResult("workOrder.getCustomer()", workOrder.getCustomer(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());		
		
		//obteniendo el código de la dirección de la WO.
		validateResult("workOrder.getCustomer().getAddressList()", workOrder.getCustomer().getAddressList(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		validateResult("workOrder.getCustomer().getAddressList().getUrbanPropertyAddress()", workOrder.getCustomer().getAddressList().getUrbanPropertyAddress(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		validateResult("workOrder.getInteractionDate()", workOrder.getInteractionDate(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		
		//Validacion de la direccion
		if(workOrder.getCustomer().getAddressList().getUrbanPropertyAddress().isEmpty()){
			String message = "No se encontró la información de la dirección asociada a la WO desde IBS porque la lista UrbanPropertyAddress llegó vacia";
			log.error(message);
			throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage() + message);
		}
		
		//Se elimina validacion por peticion de Directv CC064
		//validateResult("workOrder.getInitiatedBy()", workOrder.getInitiatedBy(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
		
		//Se valida que el tipo de WO venga en el servicio, de lo contrario se informa la inconsistencia
		if(workOrder.getWoTypeByWoTypeId() == null){
			Object[] params = null;
			params = new Object[1];	
			params[0] = workOrder.getID();
			String message = ErrorBusinessMessages.CORE_CR045.getMessage(params);
			log.warn(message);
			// despues de validar en produccion no se envia correo para este caso
			//String countryName = getCountryFromIbsWo(workOrder).getCountryName();				
			//woLoadBusiness.sendMissingWOTypeMail(workOrder.getID() ,workOrder.getCustomer().getID()+" "+workOrder.getCustomer().getName() , idCountry, countryName, message);
		}
		
		log.debug("== Termina la operación validationsOnWorkOrders/CoreWorkOrderServiceBrokerImpl ==");		
	}

	/** Obtiene el Pais desde una Wo del contrato
	 * @param workOrder
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	private Country getCountryFromIbsWo(co.com.directv.sdii.dto.esb.WorkOrder workOrder) throws DAOServiceException, DAOSQLException{
		String countryCode = "";
		if(workOrder.getGeographicArea().getCountry().getIso2Code() instanceof ElementNSImpl){
			countryCode = ((ElementNSImpl)workOrder.getGeographicArea().getCountry().getIso2Code()).getTextContent();
		}
		else{
			if(workOrder.getGeographicArea().getCountry().getIso2Code() instanceof String){
				countryCode = (String)workOrder.getGeographicArea().getCountry().getIso2Code();
			}
		}
		 
		Country country = countriesDao.getCountriesByCode(countryCode);
		return country;
	}
	

	/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName Nombre del atributo a ser validado o mensaje para
	 * ser escrito en el log en caso que el objeto sea nulo
	 * @param value2Validate objeto a ser validado
	 * @param errorCode código de error a ser lanzado
	 * @param errorMessage mensaje de error a ser lanzado
	 * @throws BusinessException En caso que el objeto a validar sea nulo
	 * @author jjimenezh
	 */
	private void validateResult(String parameterName, Object value2Validate, String errorCode, String errorMessage) throws BusinessException{
		try {
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage + " nombre del parámetro: " + parameterName);
		} catch (BusinessException e) {
			log.debug("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Metodo: Construye el requerimiento para la invocación de la operación
	 * para obtener las work orders
	 * @return Objeto que encapsula los datos para realizar el requerimiento
	 * de obtener la work order
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private GetWorkOrderRequest buildGetWorkOrderRequest(String countryCode) throws BusinessException {
		GetWorkOrderRequest request = new GetWorkOrderRequest();
		
		RequestMetadataType requestMetadataType = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadataType);
		
		GetWorkOrder getWorkOrder = new GetWorkOrder();
		request.setGetWorkOrder(getWorkOrder);
		
		return request;
	}
	
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#updateWorkOrderStatus(co.com.directv.sdii.model.pojo.WorkOrder, co.com.directv.sdii.model.pojo.WorkorderReason)
//	 */
//	public String updateWorkOrderStatus(WorkOrderDTO workOrderSdii, WorkorderReason workorderReason) throws BusinessException{
//		log.debug("== Inicia la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//		try {
//			PtCore service = getService();
//			
//			UpdateWorkOrderRequest workOrderRequest = CoreWorkOrderServiceTOA.buildUpdateWorkOrderRequest(workOrderSdii, workorderReason);
//			if(workOrderRequest == null){				
//				log.info("No se realizará la notificación de cambio de estado de la wo con código " + workOrderSdii.getWorkOrder().getWoCode() + " a IBS porque no se ha mapeado un estado equivalente");				
//				return null;
//			}
//			UpdateWorkOrderResponse workorderResponse = service.updateWorkOrder(workOrderRequest);
//			
//			UpdateWorkOrderResult workOrderResult = workorderResponse.getUpdateWorkOrderResult();
//			String workOrderKey = workOrderResult.getWorkOrderKey();			
//			log.info("Se ha actualizado correctamente la work order de codigo "+ workOrderSdii.getWorkOrder().getWoCode() +" en ibs, el código retornado es: " + workOrderKey);
//			return workOrderKey;
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//			throw manageServiceException(ex);
//		}finally{
//			log.debug("== Termina la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//		}
//	}
	
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#updateWorkOrderStatus(co.com.directv.sdii.model.pojo.WorkOrder, co.com.directv.sdii.model.pojo.WorkorderReason)
//	 */
//	public String updateWorkOrderStatus(String countryCode,
//											 String woCode,
//								             Date agendationDate,
//								             Long dealerCodeIbs,
//								             String workorderReasonCode,
//								             String ibs6StateCode) throws BusinessException{
//		log.debug("== Inicia la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//		try {
//			PtCore service = getService();
//			
//			UpdateWorkOrderRequest workOrderRequest = CoreWorkOrderServiceTOA.buildUpdateWorkOrderRequest(countryCode,
//																										 woCode,
//																							             agendationDate,
//																							             dealerCodeIbs,
//																							             workorderReasonCode,
//																							             ibs6StateCode);
//			if(workOrderRequest == null){				
//				log.info("No se realizará la notificación de cambio de estado de la wo con código " + woCode + " a IBS porque no se ha mapeado un estado equivalente");				
//				return null;
//			}
//			UpdateWorkOrderResponse workorderResponse = service.updateWorkOrder(workOrderRequest);
//			
//			UpdateWorkOrderResult workOrderResult = workorderResponse.getUpdateWorkOrderResult();
//			String workOrderKey = workOrderResult.getWorkOrderKey();			
//			log.info("Se ha actualizado correctamente la work order de codigo "+ woCode +" en ibs, el código retornado es: " + workOrderKey);
//			return workOrderKey;
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//			throw manageServiceException(ex);
//		}finally{
//			log.debug("== Termina la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//		}
//	}
//	
//	@Override
//	public String notifyWorkOrderAssignment(WorkOrder workOrderSdii, Long dealerCode, String workorderReasonCode) throws BusinessException{
//		log.debug("== Inicia la operación notifyWorkOrderAssignment/CoreWorkOrderServiceBrokerImpl ==");
//		try {
//			WorkorderReason workorderReason = new WorkorderReason();
//			workorderReason.setWorkorderReasonCode( workorderReasonCode );
//			
//			PtCore service = getService();
//			
//			UpdateWorkOrderRequest workOrderRequest = CoreWorkOrderServiceTOA.buildNotifyWorkOrderAssignmentRequest(workOrderSdii, workorderReason, dealerCode);
//			if(workOrderRequest == null){				
//				log.debug("No se realizará la notificación de cambio de estado de la wo con código " + workOrderSdii.getWoCode() + " a IBS porque no se ha mapeado un estado equivalente");				
//				return null;
//			}
//			UpdateWorkOrderResponse workorderResponse = service.updateWorkOrder(workOrderRequest);
//			
//			UpdateWorkOrderResult workOrderResult = workorderResponse.getUpdateWorkOrderResult();
//			String workOrderKey = workOrderResult.getWorkOrderKey();			
//			log.info("Se ha actualizado correctamente la work order en ibs, notificando el dealer asignado con código: "+dealerCode+" el key retornado es: " + workOrderKey +" para la asignación de la WO: "+workOrderSdii.getWoCode());
//			return workOrderKey;
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operación updateWorkOrderStatus/CoreWorkOrderServiceBrokerImpl ==");
//			throw manageServiceException(ex);
//		}finally{
//			log.debug("== Termina la operación notifyWorkOrderAssignment/CoreWorkOrderServiceBrokerImpl ==");
//		}
//	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#addServiceToWorkOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addServiceToWorkOrder(String workOrderCode, String serviceCode, String serial, String countryCode)throws BusinessException{
		log.debug("== Inicia la operación addServiceToWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			AddServiceToWorkOrderRequest request = CoreWorkOrderServiceTOA.buildAddServiceToWorkOrderRequest(serial, serviceCode, workOrderCode, countryCode);
			
			AddServiceToWorkOrderResponse response = service.addServiceToWorkOrder(request);
			AddServiceToWorkOrderResult result = response.getAddServiceToWorkOrderResult();
			String workOrderServiceKey = result.getWorkOrderServiceKey();
			
			return workOrderServiceKey;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación addServiceToWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addServiceToWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		}
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#removeServiceFromWorkOrder(java.lang.String, java.lang.String)
	 */
	@Override
	public String removeServiceFromWorkOrder(String workOrderCode, String serviceCode, String countryCode)throws BusinessException{
		log.debug("== Inicia la operación removeServiceFromWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			RemoveWorkOrderServiceRequest request = CoreWorkOrderServiceTOA.buildRemoveServiceFromWorkOrderRequest(serviceCode, workOrderCode, countryCode);
			
			RemoveWorkOrderServiceResponse response = service.removeWorkOrderService(request);
			RemoveWorkOrderServiceResult result = response.getRemoveWorkOrderServiceResult();
			String workOrderServiceKey = result.getWorkOrderServiceKey();
			
			return workOrderServiceKey;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación removeServiceFromWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación removeServiceFromWorkOrder/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#getNewWorkOrderServices()
	 */
	@SuppressWarnings("unused")
	@Override
	public void getNewWorkOrderServices(String countryCode) throws BusinessException{
		log.debug("== Inicia la operación getNewWorkOrderServices/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			GetNewWorkOrderServiceRequest request = buildGetNewWOServiceRequest(countryCode);
			
			GetNewWorkOrderServiceResponse response = service.getNewWorkOrderService(request);
			GetNewWorkOrderServiceResult result = response.getGetNewWorkOrderServiceResult();
			
			ServiceCollection serviceColl = result.getServiceList();
			List<com.directvla.schema.crm.common.v1_1.Service> ibsServices = serviceColl.getService();
			String woCode = null, serviceCode = null, serialNumber = null;
			for (com.directvla.schema.crm.common.v1_1.Service ibsService : ibsServices) {
				woCode = ibsService.getWorkOrderKey();
				serviceCode = ibsService.getServiceKey();
				serialNumber = ibsService.getProductSerialNumber();
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getNewWorkOrderServices/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación getNewWorkOrderServices/CoreWorkOrderServiceBrokerImpl ==");
		}
	}

	/**
	 * Metodo: Construye el request para invocar la operación getNewWorkOrderServices
	 * @return Objeto que encapsula la petición que será enviada al servicio
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private GetNewWorkOrderServiceRequest buildGetNewWOServiceRequest(String countryCode) throws BusinessException {
		GetNewWorkOrderServiceRequest request = new GetNewWorkOrderServiceRequest();
		
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		GetNewWorkOrderService data = new GetNewWorkOrderService();
		request.setGetNewWorkOrderService(data);
		
		return request;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#addActivation(java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unused")
	@Override
	public void addActivation(String workOrderCode,String countryCode, List<String> serials) throws BusinessException{
		log.debug("== Inicia la operación addActivation/CoreWorkOrderServiceBrokerImpl ==");
		try {
			ServiceConfigurationAndActivationPt service=ServiceLocator.getInstance().getServiceConfigurationAndActivationService();
			
			AddActivationRequest request = CoreWorkOrderServiceTOA.buildAddActivationRequest(workOrderCode, serials, countryCode);
			
			AddActivationResponse response = service.addActivation(request);
			/*AddActivationResult result = response.getResponseMetadata();
			
			String workOrderCodeResult = result.getWorkOrderKey();*/
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación addActivation/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addActivation/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWorkOrderServiceBrokerLocal#addIRDChanges(java.lang.String, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unused")
	@Override
	public void addIRDChanges(String workOrderCode, String countryCode, List<String> oldSerials, List<String> newSerials)throws BusinessException{
		log.debug("== Inicia la operación addIRDChanges/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			AddIRDChangesRequest request = buildAddIRDChangesRequest(workOrderCode, countryCode, oldSerials, newSerials);
			
			AddIRDChangesResponse response = service.addIRDChanges(request);
			AddIRDChangesResult result = response.getAddIRDChangesResult();
			
			String workOrderCodeResult = result.getWorkOrderKey();
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación addIRDChanges/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addIRDChanges/CoreWorkOrderServiceBrokerImpl ==");
		}
	}

	/**
	 * Metodo: Construye el objeto que encapsula la petición de reportar cambios en
	 * los IRD's
	 * @param workOrderCode código de la work order
	 * @param oldSerials seriales antíguos
	 * @param newSerials seriales nuevos
	 * @return Objeto que encapsula la petición
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private AddIRDChangesRequest buildAddIRDChangesRequest(String workOrderCode, String countryCode, List<String> oldSerials, List<String> newSerials) throws BusinessException {
		AddIRDChangesRequest request = new AddIRDChangesRequest();
		
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		AddIRDChanges data = new AddIRDChanges();
		data.setWOCode(workOrderCode);
		data.getPreviusSerials().addAll(oldSerials);
		data.getNewSerials().addAll(newSerials);
		
		request.setAddIRDChanges(data);
		
		return request;
	}

	
	/**
	 * Metodo: Construye una petición para la invocación de la operación de agregar
	 * un contacto a un cliente
	 * @param actionCode código de la acción
	 * @param actionDate fecha de la acción
	 * @param categoryCode código de la categoría
	 * @param contactMethodCode código del método de contacto
	 * @param customerCode código del cliente
	 * @param description descripción del nuevo método de contacto
	 * @param workOrderCode código de la orden de trabajo
	 * @return Objeto que encapsula la petición sobre la operación
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private AddCustomerContactRequest buildAddCustomerContactRequest(String actionCode, Date actionDate, String categoryCode, String contactMethodCode, String customerCode, String description, String workOrderCode, String countryCode) throws BusinessException {
		AddCustomerContactRequest request = new AddCustomerContactRequest();
		
		RequestMetadataType requestMetadata = getMetadataType(countryCode);
		request.setRequestMetadata(requestMetadata);
		
		AddCustomerContact data = new AddCustomerContact();
		data.setAction(actionCode);
		GregorianCalendar gregoCal = new GregorianCalendar();
		gregoCal.setTime(actionDate);
		data.setActionDate(new XMLGregorianCalendarImpl(gregoCal));
		data.setCategoryCode(categoryCode);
		data.setContactMethodCode(contactMethodCode);
		data.setCustomerCode(customerCode);
		data.setDescription(description);
		data.setWorkOrderKey(workOrderCode);
		return request;
	}
	
	/**
	 * Metodo: Agrega un contacto a un cliente
	 * @param actionCode código de la acción
	 * @param actionDate fecha de la acción
	 * @param categoryCode código de la categoría
	 * @param contactMethodCode código del método de contacto del cliente
	 * @param customerCode código del cliente
	 * @param description descripción del nuevo método de contacto
	 * @param workOrderCode código de la orden de trabajo
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	@SuppressWarnings("unused")
	public void addCustomerContact(String actionCode, Date actionDate, String categoryCode, String contactMethodCode, String customerCode, String description, String workOrderCode, String countryCode)throws BusinessException{
		log.debug("== Inicia la operación addCustomerContact/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			
			AddCustomerContactRequest request = buildAddCustomerContactRequest(actionCode, actionDate, categoryCode, contactMethodCode, customerCode, description, workOrderCode, countryCode);
			
			AddCustomerContactResponse response = service.addCustomerContact(request);
			AddCustomerContactResult result = response.getAddCustomerContactResult();
			
			String workOrderCodeResult = result.getWorkOrderKey();
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación addCustomerContact/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación addCustomerContact/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal#notifyWoCancelation(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String notifyWoCancelation(String woCode, String comment, String reasonCode, String countryCode) throws BusinessException{
		log.debug("== Inicia la operación notifyWoCancelation/CoreWorkOrderServiceBrokerImpl ==");
		try {
			PtCore service = getService();
			CancelWorkOrderRequest request = CoreWorkOrderServiceTOA.buildCancelWorkOrderRequest(woCode, comment, reasonCode, countryCode);
			CancelWorkOrderResponse response = service.cancelWorkOrder(request);
			log.debug("RequestID = " + response.getResponseMetadata().getRequestID());
			return String.valueOf( response.getCancelWorkOrderResult().getHistoryId() ) ;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación notifyWoCancelation/CoreWorkOrderServiceBrokerImpl ==");
			throw manageServiceException(ex);
		}finally{
			log.debug("== Termina la operación notifyWoCancelation/CoreWorkOrderServiceBrokerImpl ==");
		}
	}

	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones
	 * expuestas por el servicio web de core en ibs
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del rervicio web de core
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private RequestMetadataType getMetadataType(String countryCode) throws BusinessException{
		Random r = new Random();
		RequestMetadataType requestMetadataType = new RequestMetadataType();
		int requestId =  r.nextInt(1000000);
		requestMetadataType.setRequestID(requestId+"");
		requestMetadataType.setSourceID(countryCode);
		requestMetadataType.setAppID(false);
		requestMetadataType.setValidate(false);
		requestMetadataType.setAppID(false);
		try{
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			}catch (Throwable ex) { 
		         ex.printStackTrace();
		         log.debug("== Error al tratar de ejecutar la operaciÃ³n getMetadataType/CoreWorkOrderServiceBrokerImpl ==");
		         throw manageServiceException(ex);
		            } finally{
		         log.debug("== Termina la operaciÃ³n getMetadataType/CoreWorkOrderServiceBrokerImpl ==");
		}
		return requestMetadataType;
	}
//-----------------------------------SI INICIA EL MANEJO DE EXCEPCIONES--------------------------------------------------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		BusinessException be = null;
		//Excepción para la operación addCustomerContact
		if(e instanceof AddCustomerContactException){
			errorMessage = getAddCustomerContactExceptionMessage(((AddCustomerContactException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof UpdateWorkOrderException){
			errorMessage = getUpdateWorkOrderExceptionMessage(((UpdateWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof RemoveWorkOrderServiceException){
			errorMessage = getRemoveWorkOrderServiceExceptionMessage(((RemoveWorkOrderServiceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof GetNewWorkOrderServiceException){
			errorMessage = getGetNewWorkOrderServiceExceptionMessage(((GetNewWorkOrderServiceException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof AddServiceToWorkOrderException){
			errorMessage = getAddServiceToWorkOrderExceptionMessage(((AddServiceToWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof AddIRDChangesException){
			errorMessage = getAddIRDChangesExceptionMessage(((AddIRDChangesException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof AddActivationException){
			errorMessage = getAddActivationExceptionMessage(((AddActivationException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof GetWorkOrderException){
			errorMessage = getGetWorkOrderExceptionMessage(((GetWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof CancelWorkOrderException){
			errorMessage = getCancelWorkOrderExceptionMessage(((CancelWorkOrderException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof BusinessException){
			return (BusinessException)e;
		}else{
			be = super.manageException(e);
		}
		log.error("Excepción al invocar un servicio de IBS, el mensaje retornado es: código: " + be.getMessageCode() + " mensaje: "  + be.getMessage(), be);
		return be;
	}

	private String getAddActivationExceptionMessage(
		com.directvla.schema.businessdomain.serviceconfigurationandactivation.v1_0.AddActivationException faultInfo,
		String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException() != null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}
		
		return errorMessage;
}

	private String getCancelWorkOrderExceptionMessage(com.directvla.schema.crm.customer.v1.CancelWorkOrderException faultInfo, String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getGetWorkOrderExceptionMessage(
			com.directvla.schema.crm.customer.v1.GetWorkOrderException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	/*private String getAddActivationExceptionMessage(
			AddActivationException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}*/

	private String getAddIRDChangesExceptionMessage(
			com.directvla.schema.crm.customer.v1.AddIRDChangesException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getAddServiceToWorkOrderExceptionMessage(
			com.directvla.schema.crm.customer.v1.AddServiceToWorkOrderException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getGetNewWorkOrderServiceExceptionMessage(
			com.directvla.schema.crm.customer.v1.GetNewWorkOrderServiceException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getRemoveWorkOrderServiceExceptionMessage(
			com.directvla.schema.crm.customer.v1.RemoveWorkOrderServiceException faultInfo,
			String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getUpdateWorkOrderExceptionMessage(
			com.directvla.schema.crm.customer.v1.UpdateWorkOrderException faultInfo,
			String defaultMessage) {
		
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}

	private String getAddCustomerContactExceptionMessage(
			com.directvla.schema.crm.customer.v1.AddCustomerContactException faultInfo, String defaultMessage) {
		
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}else if(faultInfo.getServiceUnavailabbleException() != null){
			return faultInfo.getServiceUnavailabbleException().getMessage();
		}
		
		return defaultMessage;
	}
//-----------------------------------SI FINALIZA EL MANEJO DE EXCEPCIONES--------------------------------------------------------------------------------------------------

}
