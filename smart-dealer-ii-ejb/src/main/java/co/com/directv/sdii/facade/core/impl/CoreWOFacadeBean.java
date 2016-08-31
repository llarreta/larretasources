package co.com.directv.sdii.facade.core.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOImporterLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.facade.core.CoreWOFacadeRemote;
import co.com.directv.sdii.model.dto.AssignWorkOrderDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.model.vo.WoStatusHistoryVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderAgendaVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.ws.model.dto.ResponseSendMailDTO;

/**
 * 
 * Clase que implementa las operaciones para el modulo de core
 * asociadas a el componente de negocio de core y relacionadas
 * con los CU:
 * Caso de Uso ADS - 26 - Detalle del Cliente ==> Se debe realizar cuando se haga el m�dulo de Clientes
 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
 * Caso de Uso ADS - 30 - Exportar Bandeja de Work Orders a Excel ==> Todas las tablas se deben exportar a Excel
 * Caso de Uso ADS - 60 - Registrar elementos NO serializados utilizados en la atenci�n del servicio ==> Se debe realizar cuando se haga el m�dulo de Inventarios 
 * Caso de Uso ADS - 61 - Registrar elementos serializados utilizados en la atenci�n del servicio ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 62 - Registrar  cambio  de  elementos serializados donde el cliente  en  la ate ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 63 - Registrar Recuperaci�n  de  elementos serializados  donde el cliente ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 64 - Registrar Recuperaci�n  de  elementos NO serializados  donde el cliente ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 *  
 * 
 * Fecha de Creación: 20/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="CoreWOFacadeLocal")
@Local({CoreWOFacadeLocal.class})
@Remote({CoreWOFacadeRemote.class})
public class CoreWOFacadeBean implements CoreWOFacadeLocal,CoreWOFacadeRemote {

	@EJB(name="CoreWOBusinessLocal",beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal business;
	
	@EJB(name="CoreWOImporterLocal",beanInterface=CoreWOImporterLocal.class)
	private CoreWOImporterLocal coreWoImporterBean;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#addServiceToWorkOrder()
	 */
	@Override
	public void addServiceToWorkOrder(Long workOrderId, Long serviceId, String decoSerialNumber, String countryCode, Long userId) throws BusinessException {
		business.addServiceToWorkOrder(workOrderId,serviceId,decoSerialNumber, countryCode, userId);
	}

	/**
	 * Caso de Uso ADS - 17 - Crear Work Order.
	 * 
	 * Método que se encarga de crear una WorkOrder que proviene
	 * de IBS. Se debe almacenar en la tabla WORK_ORDERS.
	 * Se debe invocar un WebService de IBS que retornará la lista
	 * de WorkOrders a crear en SmartDealer II.
	 * 
	 * Se debe notificar cambio de estado de la WorkOrder a IBS.
	 *   
	 * @throws BusinessException
	 */
	public void addWorkOrder(String countryCode) throws BusinessException {
		coreWoImporterBean.addWorkOrder(countryCode);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#agendaWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	public void agendaWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException {
		business.agendaWorkOrder(trayWOManagmentDTO);
	}

	/**
	 * Method:Operacion para asignar workorders de un
	 * dealer a una cuadrilla.
     * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal
     * @author jalopez
     */
	public boolean assignDynamicCrewToWorkOrder(List<WorkOrderVO> workOrderList,CrewVO crew, Long userId) throws BusinessException {
		return business.assignDynamicCrewToWorkOrder(workOrderList, crew, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#assignFixedCrewToWorkOrder(co.com.directv.sdii.model.vo.WorkOrderVO, co.com.directv.sdii.model.vo.CrewVO)
	 */
	public void assignFixedCrewToWorkOrder(WorkOrderVO workorder, CrewVO crew, Long userId)
			throws BusinessException {
		business.assignFixedCrewToWorkOrder(workorder, crew, userId);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#assignFixedCrewToWorkOrders(java.util.List, co.com.directv.sdii.model.vo.CrewVO)
	 */
	public void assignFixedCrewToWorkOrders(List<WorkOrderInfoServiceVinculationDTO> workordersCrews, Long userId)
			throws BusinessException {
		business.assignFixedCrewToWorkOrders(workordersCrews , userId);

	}

	@Override
	public void assignProgramToWorkOrder(WorkOrderVO workorder, ProgramVO program) throws BusinessException {
		business.assignProgramToWorkOrder(workorder, program);
	}
	
	@Override
	public void assignProgramToWorkOrders(List<WorkOrderVO> workorders, ProgramVO program) throws BusinessException {
		business.assignProgramToWorkOrders(workorders, program);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#assignWorkOrderToDealer(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.WorkOrderVO)
	 */
	@Override
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto)
			throws BusinessException {
		business.assignWorkOrderToDealer(dto);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#assignWorkOrderToDealer(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String)
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, String workOrderReasonCode) throws BusinessException{
		business.assignWorkOrderToDealer(dto, workOrderReasonCode);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#assignWorkOrdersToDealer(co.com.directv.sdii.model.vo.DealerVO, java.util.List)
	 */
	public void assignWorkOrdersToDealer(AssignWorkOrderDTO dto, List<WorkOrderVO> workorders) throws BusinessException{
		business.assignWorkOrdersToDealer( dto, workorders);
	}

	@Override
	public void cancelWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException{
		business.cancelWorkOrder(trayWOManagmentDTO);
	}
	
	@Override
	public void cancelWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException {
		business.cancelWorkOrders(trayWOManagmentDTOList);
		
	}

//	@Override
//	public Long changeRealizationDateIBS(Long customerID, Long workOrderNumer, String newRealizationDate, String workOrderCountryId) throws BusinessException {
//		return business.changeRealizationDateIBS(customerID, workOrderNumer, newRealizationDate, workOrderCountryId);
//	}

	@Override
	public void changeWorkOrderStatus(WorkOrderVO workorder,
			WorkorderStatusVO newWorkOrderStatus, WorkorderReasonVO reason)
			throws BusinessException {
		
	}

	@Override
	public void changeWorkOrderStatus(Long customerId, Long countryId, String workOrderCode, String workOrderReason2ChangeCode, String workOrderReason2CompleteCode, String flag2KnowProcess2Execute, String newWorkOrderStatusCode) throws BusinessException {
		business.changeWorkOrderStatus(customerId, countryId, workOrderCode, workOrderReason2ChangeCode, workOrderReason2CompleteCode, flag2KnowProcess2Execute, newWorkOrderStatusCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#deleteServiceToWorkOrder(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public void deleteServiceToWorkOrder(Long workOrderId, Long serviceId, String decoderSerialNumber, String countryCode) throws BusinessException {
		business.deleteServiceToWorkOrder(workOrderId, serviceId, decoderSerialNumber, countryCode);
	}

	@Override
	public void deleteWorkOrder(WorkOrderVO workorder) throws BusinessException {
		
	}

	@Override
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, CrewVO crew)throws PDFException, BusinessException {
		return business.generateCrewWorkOrdersPDF(workOrderIds, crew);
		
	}
	
	@Override
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, List<Long> crewIds) throws PDFException, BusinessException {
		return business.generateCrewWorkOrdersPDF(workOrderIds, crewIds);		
	}

	@Override
	public List<String> getWorkOrdersFilesByDealer(Long dealerCode)throws BusinessException {
		return business.getWorkOrdersFilesByDealer(dealerCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrdersFilesByCrewId(java.lang.Long)
	 */
	@Override
	public List<String> getWorkOrdersFilesByCrewId(Long crewId)
			throws BusinessException {
		return business.getWorkOrdersFilesByCrewId(crewId);
	}
	
	@Override
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(
			WoTypeVO actualType, WoTypeVO previusType) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(
			DealerVO dealer, WoTypeVO actualType, WoTypeVO previusType)
			throws BusinessException {
		return null;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrdersActiveAndSuspend()
	 */
	@Override
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend() throws BusinessException{
		return business.getWorkOrdersActiveAndSuspend();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrdersActiveAndSuspend()
	 */
	@Override
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return business.getWorkOrdersActiveAndSuspendByCountryId(countryId, requestCollectionInfo);
	}
	
	

	@Override
	public List<WorkOrderVO> getWorkOrderByActualStatus(DealerVO dealer,
			WorkorderStatusVO actualStatus) throws BusinessException {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrderByActualStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
	 */
	@Override
	public List<WorkOrderVO> getWorkOrderByActualStatus(WorkorderStatusVO actualStatus) throws BusinessException {
		return business.getWorkOrderByActualStatus(actualStatus);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByActualType(WoTypeVO actualType)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByActualType(DealerVO dealer,
			WoTypeVO actualType) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByCreationDate(Date initDate,
			Date endDate) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByCustomer(CustomerVO customer)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByCustomerAndCreationDate(
			CustomerVO customer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByCustomerAndProgrammingDate(
			CustomerVO customer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByCustomerAndRealizationDate(
			CustomerVO customer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public WOByDealerPaginationResponse getWorkOrderByDealer(DealerVO dealer, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {		
		return business.getWorkOrderByDealer(dealer, requestCollectionInfo);
	}

	@Override
	public WorkOrderVO getWorkOrderByDealerAndCode(DealerVO dealer, String code)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerAndCreationDate(
			DealerVO dealer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerAndPostalCode(DealerVO dealer,
			PostalCodeVO postalCode) throws BusinessException {
		return null;
	}
	
	@Override
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(Long dealerId, Long workOrderId) throws BusinessException{
		return this.business.getWorkOrderByDealerIdAndWorkOrderId(dealerId, workOrderId);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerAndProgrammingDate(
			DealerVO dealer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerAndRealizationDate(
			DealerVO dealer, Date initDate, Date endDate)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeCreationDate(
			DealerVO dealer, PostalCodeVO postalCode, Date initDate,
			Date endDate) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeProgrammingDate(
			DealerVO dealer, PostalCodeVO postalCode, Date initDate,
			Date endDate) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeRealizationDate(
			DealerVO dealer, PostalCodeVO postalCode, Date initDate,
			Date endDate) throws BusinessException {
		return null;
	}

	@Override
	public WorkOrderVO getWorkOrderByID(Long id) throws BusinessException {
		return this.business.getWorkOrderByID(id);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(
			WorkorderStatusVO previusStatus) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(DealerVO dealer,
			WorkorderStatusVO previusStatus) throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByPreviusType(WoTypeVO previusType)
			throws BusinessException {
		return business.getWorkOrderByPreviusType(previusType);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByPreviusType(DealerVO dealer,
			WoTypeVO previusType) throws BusinessException {
		return business.getWorkOrderByPreviusType(dealer, previusType);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByProgrammingDate(Date initDate,
			Date endDate) throws BusinessException {
		return business.getWorkOrderByProgrammingDate(initDate, endDate);
	}

	@Override
	public List<WorkOrderVO> getWorkOrderByRealizationDate(Date initDate,
			Date endDate) throws BusinessException {
		return business.getWorkOrderByRealizationDate(initDate, endDate);
	}

	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#rejectWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rejectWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO) throws BusinessException {
		for(TrayWOManagmentDTO twmdto: trayWOManagmentDTO){
			business.rejectWorkOrder(twmdto);
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#reportDifficultyToWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void reportDifficultyToWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO)throws BusinessException {
		for(TrayWOManagmentDTO twmdto : trayWOManagmentDTO){
			business.reportDifficultyToWorkOrder(twmdto);
		}
	}

	@Override
	public ResponseSendMailDTO sendWorkOrdersPDFEmail(String[] fileNames, final Long filterCrewId, Long countryId) throws BusinessException {
		return business.sendWorkOrdersPDFEmail(fileNames,filterCrewId, countryId);
	}

	@Override
	public void updateDealerServiceCapacity(DealerVO dealer,
			PostalCodeVO postalCode, ServiceHourVO serviceHour)
			throws BusinessException {
		//business.updateDealerServiceCapacity(dealer, postalCode, serviceHour, workOrderVO, date);
	}

	@Override
	public WOByCustomerQBEPaginationResponse getWorkOrdersByDealerCustomerQBE(Long dealerId,
			String ibsCode, String officePhone, String homePhone,
			String faxPhone, String cellPhone, String idNumber, String name,
			String lastName, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersByDealerCustomerQBE(dealerId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName, requestCollectionInfo);
	}

	@Override
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(Long dealerId,
			Long dateId, Long crewId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersByDealerDateCrewQBE(dealerId, dateId, crewId, requestCollectionInfo);
	}

	@Override
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersByDealerWorkOrderQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, stateId, cityId, postalCodeId, creationDate, programmingDate, requestCollectionInfo);
	}

	@Override
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException {
		return business.getWorkOrdersByCustomerDataQBE(countryId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName,requestCollectionInfo);
	}

	@Override
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId,
			String woCode, Long ServiceTypeId, Long serviceCategoryId,
			Long woStatusId, Long countryId, Long stateId, Long cityId, Long postalCodeId,
			Date creationDate, Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersByWorkOrderDataQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, countryId, stateId, cityId, postalCodeId, creationDate, programmingDate,requestCollectionInfo);
	}

	/**
	 * 
	 * Metodo: Retorna un WoStatusHistory con la workorderreason
	 * del ultimo estado de una workorder.
	 * @param woId Long
	 * @param woStatusId Long
	 * @return WoStatusHistoryVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public WoStatusHistoryVO getWorkorderReasonByWoHistory(Long woId, Long woStatusId) throws BusinessException {		
		return business.getWorkorderReasonByWoHistory(woId, woStatusId);
	}
	
	/**
     * 
     * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws BusinessException
     * @author jalopez
     */
	public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId) throws BusinessException{
		return business.getWorkOrderAgendaByWoAssignment(woId, dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrderReasonByWoStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO, java.lang.Long)
	 */
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus(WorkorderStatusVO woStatus,Long userId) throws BusinessException {
		return business.getWorkOrderReasonByWoStatus(woStatus,userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getCustomerByCode(java.lang.String)
	 */
	@Override
	public CustomerVO getCustomerByCode(String customerCode)
			throws BusinessException {
		return business.getCustomerByCode(customerCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getCustomerById(java.lang.Long)
	 */
	@Override
	public CustomerVO getCustomerById(Long customerId) throws BusinessException {
		return business.getCustomerById(customerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getCustomerByWoIdOrWoCode(java.lang.Long, java.lang.String)
	 */
	@Override
	public CustomerVO getCustomerByWoIdOrWoCode(Long workOrderId,
			String workOrderCode) throws BusinessException {
		return business.getCustomerByWoIdOrWoCode(workOrderId, workOrderCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getLastWorkOrderAssigmentByWoId(java.lang.Long)
	 */
	@Override
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(Long workOrderId)
			throws BusinessException {
		return business.getLastWorkOrderAssigmentByWoId(workOrderId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrderServicesByWoId(java.lang.Long)
	 */
	@Override
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(Long workOrderId)
			throws BusinessException {
		return business.getWorkOrderServicesByWoId(workOrderId);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getWorkOrderByCodeAndCountry(java.lang.String, java.lang.String)
	 */
	@Override
	public WorkOrderVO getWorkOrderByCodeAndCountry(String id, String countryCode) throws BusinessException {
		return this.business.getWorkOrderByCodeAndCountry(id,countryCode);  
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getShippingOrderByCodeAndWOCodeAndCountry(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ShippingOrderVO getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode) throws BusinessException {
		return this.business.getShippingOrderByCodeAndWOCodeAndCountry(shipOrderID, woCode, countryCode);
	}

	 /**
     * Metodo: Permite consultar las work order paginadas
     * resultado
     * @return Lista de work orders
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
	@Override
	public List<WorkOrderVO> getWorkOrdersByPage(Long countryId) throws BusinessException {
		try{
			return business.getWorkOrdersByPage(countryId, null, "'"+CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getCodeEntity()+"'");
		}catch (Throwable ex) {
			throw new BusinessException(ex.getMessage());
		} 
	}
	
	@Override
	public Boolean getValidateWorkOrderScheduled(Long workOrderId, Long dealerId) throws BusinessException{
		return business.getValidateWorkOrderScheduled(workOrderId, dealerId);
	}

	@Override
	public void agendaWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList)throws BusinessException {
		business.agendaWorkOrders(trayWOManagmentDTOList);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#checkAboveScheduled(co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria, int)
	 */
	@Override
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(
			DealerWorkCapacityCriteria dealerWorkCapacityCriteria,
			int quantityWorkOrder) throws BusinessException {
		return business.checkAboveScheduled(dealerWorkCapacityCriteria,quantityWorkOrder);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#updateInfoWorkOrderIBSToHSP(java.lang.String, java.lang.String)
	 */
	public void updateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException{
		coreWoImporterBean.updateInfoWorkOrderIBSToHSP(countryCode,woCode);
	}

	//REQ001 - WO Canceladas.
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#getCanceledWorkOrdersByDealerId(java.lang.Long)
	 */
	@Override
	public List<WorkOrderVO> getCanceledWorkOrdersByDealerId(Long dealerId) throws BusinessException {
		return business.getCanceledWorkOrdersByDealerId(dealerId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.CoreWOFacadeLocal#downloadWorkOrderContact(java.lang.Long, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void downloadWorkOrderContact(Long countryId, Date processDate) throws BusinessException{
		coreWoImporterBean.downloadWorkOrderContact(countryId,processDate);
	}
	
	
	
	
}
