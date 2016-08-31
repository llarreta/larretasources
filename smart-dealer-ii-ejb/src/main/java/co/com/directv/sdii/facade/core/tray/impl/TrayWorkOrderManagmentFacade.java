package co.com.directv.sdii.facade.core.tray.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal;
import co.com.directv.sdii.ejb.business.config.ServiceTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.core.tray.TrayWorkOrderManagmentFacadeLocal;
import co.com.directv.sdii.model.dto.EmployeeCrewDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderTrayResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ExpirationGroupingVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;


/**
 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora
 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación
 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
 * Caso de Uso ADS - 65 - Atencion y Finalizacion Work Orders
 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
 * Facahada que invoca las operaciones de negocio para la gestion de
 * WorkOrders
 * 
 * Fecha de Creación: 6/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="TrayWorkOrderManagmentFacadeLocal",mappedName="ejb/TrayWorkOrderManagmentFacadeLocal")
@Local({TrayWorkOrderManagmentFacadeLocal.class})
public class TrayWorkOrderManagmentFacade implements TrayWorkOrderManagmentFacadeLocal{
	
	@EJB(name = "TrayWorkOrderManagmentBusinessLocal", beanInterface = TrayWorkOrderManagmentBusinessLocal.class)
	private  TrayWorkOrderManagmentBusinessLocal business;
	
	@EJB(name = "ConfigWOEstadosBusinessLocal", beanInterface = ConfigWOEstadosBusinessLocal.class)
	private ConfigWOEstadosBusinessLocal cfgWoEstadosBusiness;
	
	@EJB(name = "ServiceTypeBusinessBeanLocal", beanInterface = ServiceTypeBusinessBeanLocal.class)
	private ServiceTypeBusinessBeanLocal serviceTypeBean;
	
	@EJB(name = "CoreWOBusinessLocal", beanInterface = CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusinessLocal;
	
	@EJB	
	private WorkOrderBusinessBeanLocal workOrderBusiness; 
	
	@Override
	public WorkOrderServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {
		return business.getWorkOrderServices(woId, requestCollectionInfo);
	}
	
	@Override
	public WorkOrderServiceResponse getWorkOrderServicesAttention(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrderServicesAttention(woId, requestCollectionInfo);
	}

	@Override
	public WorkOrderServiceResponse getWorkOrderServicesFinalization(Long woId, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {
		return business.getWorkOrderServicesFinalization(woId, requestCollectionInfo);
	}

	@Override
	public WorkOrderTrayResponse getWorkOrdersAttentionFinalization(WorkOrderFilterTrayDTO filterDTO, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersAttentionFinalization(filterDTO, requestCollectionInfo);
	}

	@Override
	public WorkOrderTrayResponse getWorkOrdersForDealerTray( WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return business.getWorkOrdersForDealerTray(filter, requestCollectionInfo);
	}

	@Override
	public WorkOrderTrayResponse getWorkOrdersForAllocator(	WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo)	throws BusinessException {
		return business.getWorkOrdersForAllocator(filter, requestCollectionInfo);
	}
	@Override
	public void attentionWorkOrder(TrayWOManagmentDTO request)	throws BusinessException {
		business.attentionWorkOrder(request);
	}
	
//	@Override
//	public WarehouseElementVO getElementBySerialAndDealerId(String elementSerial,Long dealerId) throws BusinessException {
//		return business.getElementBySerialAndDealerId(elementSerial, dealerId);
//	}

	@Override
	public List<ServiceVO> getServicesByWoTypeId(Long woTypeId) throws BusinessException {
		return business.getServicesByWoTypeId(woTypeId);
	}

//	@Override
//	public WarehouseElementVO getElementBySerialAndCustomerId(String elementSerial, Long customerId) throws BusinessException {
//		return business.getElementBySerialAndCustomerId(elementSerial, customerId);
//	}

	@Override
	public List<WorkorderStatusVO> getDealerTrayFilterStatuss() throws BusinessException {
		return business.getDealerTrayFilterStatuss();
	}

	@Override
	public WorkOrderTrayDTO getWorkorderDetail(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return business.getWorkorderDetail(filter);
	}	
	
	@Override
	public List<ExpirationGroupingVO> getAllExpirationGroupingVO(WorkOrderFilterTrayDTO filter) throws BusinessException {
		return business.getAllExpirationGroupingVO(filter);
	}

	@Override
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId, Long dealerId) throws BusinessException {
		return business.getAllResponsableEmployeeCrewByCountryAndDealerId(countryId, dealerId);
	}

	@Override
	public List<ServiceCategoryVO> getAllServiceCategories()throws BusinessException {
		return business.getAllServiceCategories();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.tray.TrayWorkOrderManagmentFacadeLocal#getServiceCategoryByTypesId(java.util.List)
	 */
	@Override
	public List<ServiceCategoryVO> getServiceCategoryByTypesId(
			List<Long> typesId) throws BusinessException {
		return business.getServiceCategoryByTypesId(typesId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.core.tray.TrayWorkOrderManagmentFacadeLocal#getActiveServicesByServiceCategories(java.util.List)
	 */
	@Override
	public List<ServiceVO> getActiveServicesByServiceCategories(
			List<Long> categoriesId) throws BusinessException {
		return business.getActiveServicesByServiceCategories(categoriesId);
	}

	@Override
	public List<WoTypeVO> getAllWorkorderTypes() throws BusinessException {
		return business.getAllWorkorderTypes();
	}

	@Override
	public List<ProgramVO> getActiveProgramsByDealerId(Long dealerId)throws BusinessException {
		return business.getActiveProgramsByDealerId(dealerId);
	}

	@Override
	public List<DealerVO> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId, Long countryId) throws BusinessException {
		return business.getSaleDealersByWoAsignDealerIdAndCountry(dealerId, countryId);
	}

	@Override
	public RequiredServiceElementVO getRequiredServiceElements(TrayWOManagmentDTO request) throws BusinessException {
		return business.getRequiredServiceElements(request);
	}

	@Override
	public EnvelopeEncapsulateResponse workOrderFinalization(List<TrayWOManagmentDTO> trayRequest) throws BusinessException {
		return business.finalizationWorkOrder(trayRequest);
	}

	@Override
	public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray()
			throws BusinessException {
		return cfgWoEstadosBusiness.getWorkOrderStatusForDealerTray();
	}

	@Override
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException {
		return serviceTypeBean.getAllServiceType();
	}

	@Override
	public SerializedVO getElementBySerialCode(TrayWOManagmentDTO request)	throws BusinessException {		
		return business.getElementBySerialCode(request);
	}	
	
	@Override
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException{
		return business.getCrewsByDealerId(dealerId);
	}

	@Override
	public void validateWorkOrdersAgenda(List<Long> workOrderIds) throws BusinessException {
		business.validateWorkOrdersAgenda(workOrderIds);
	}

	@Override
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(List<Long> woIds, Long countryId) throws BusinessException {
		return coreWOBusinessLocal.validateWoBeforeGeneratingPdf(woIds, countryId);
	}

	@Override
	public List<WorkOrderTrayDTO> getWorkordersFinalization(TrayWOManagmentDTO trayRequest) throws BusinessException {		
		return business.getWorkordersFinalization(trayRequest);
	}

	@Override
	public List<WorkOrderTrayDTO> getWorkordersToSchedule(TrayWOManagmentDTO trayRequest) throws BusinessException {
		return business.getWorkordersToSchedule(trayRequest);
	}

	@Override
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId, List<Long> dealerIds) throws BusinessException {
		return business.getAllResponsableEmployeeCrewByCountryAndDealerIds(countryId, dealerIds);
	}
	
	@Override
	public List<DealerVO> getSaleDealersByWoAsignDealerIdsAndCountry(List<Long> dealerIds, Long countryId,
			String isSeller, String isInstaller) throws BusinessException {
		return business.getSaleDealersByWoAsignDealerIdsAndCountry(dealerIds, countryId, isSeller, isInstaller);
	}
	
	@Override
	public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException {
		return business.getActiveProgramsByDealerIds(dealerIds);
	}

	
	@Override
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException {
		return business.getCustomerResources(customerCode, userId);
	}
	
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForDificulty(Long woId) throws BusinessException{
		try{
			List<Long> woIds = new ArrayList<Long>();
			woIds.add(woId);
			return workOrderBusiness.getWorkOrdersByWoIdForSimilarState(woIds,CodesBusinessEntityEnum.INDIVIDUAL_DIFICULTAD_BANDEJAAUTO.getCodeEntity());
		}catch (Throwable ex) {
			if(ex instanceof BusinessException){
				throw (BusinessException)ex;
			}
			else{
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForReject(Long woId) throws BusinessException{
		try{
			List<Long> woIds = new ArrayList<Long>();
			woIds.add(woId);
			return workOrderBusiness.getWorkOrdersByWoIdForSimilarState(woIds,CodesBusinessEntityEnum.INDIVIDUAL_RECHAZAR_BANDEJAAUTO.getCodeEntity());
		}catch (Throwable ex) {
			if(ex instanceof BusinessException){
				throw (BusinessException)ex;
			}
			else{
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignDinamicCrew(List<Long> woIds) throws BusinessException{
		try{
			return workOrderBusiness.getWorkOrdersByWoIdForSimilarState(woIds,CodesBusinessEntityEnum.GRUPAL_ASIGNARCUADRILLADINAMICA_BANDEJAAUTO.getCodeEntity());
		}catch (Throwable ex) {
			if(ex instanceof BusinessException){
				throw (BusinessException)ex;
			}
			else{
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignStaticCrew(List<Long> woIds) throws BusinessException{
		try{
			return workOrderBusiness.getWorkOrdersByWoIdForSimilarState(woIds,CodesBusinessEntityEnum.GRUPAL_ASIGNARCUADRILLA_BANDEJAAUTO.getCodeEntity());
		}catch (Throwable ex) {
			if(ex instanceof BusinessException){
				throw (BusinessException)ex;
			}
			else{
				throw new BusinessException(ex.getMessage());
			}
		}
	}

	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	@Override
	public void verifyDesconectionSerials(
			VerifyDesconectionSerialsDTO request) throws BusinessException {
		workOrderBusiness.verifyDesconectionSerials(request);
	}
	
	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	@Override
	public String getSerialForAttentionDesconectionWO(Long woId, Long woServiceId) throws BusinessException {
		return workOrderBusiness.getSerialForAttentionDesconectionWO(woId, woServiceId);
	}
	
}
