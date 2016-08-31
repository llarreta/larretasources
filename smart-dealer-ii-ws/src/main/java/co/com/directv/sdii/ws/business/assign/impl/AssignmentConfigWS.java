/**
 * Creado 10/06/2011 16:52:31
 */
package co.com.directv.sdii.ws.business.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.CoverageTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerConfCoverageFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerCustomerTypesWoutPcFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerDetailFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.DealerWeekCapacityFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.KpiCalculationTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.KpiConfigurationFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.SaleChanelFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.ServiceAreWarrantyFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.ServiceSuperCategoryFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.ServiceTypeWarrantyFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.SkillConfigurationFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.SkillEvaluationTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.SkillModeTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.CoverageTypeVO;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * Implementa las operaciones del servicio web de configuración del asignador
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@MTOM
@WebService(serviceName="AssignmentConfigWSService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS",
		targetNamespace="http://assign.business.ws.sdii.directv.com.co/",
		portName="AssignmentConfigWSPort")
@Stateless
public class AssignmentConfigWS implements IAssignmentConfigWS {

	@EJB private ServiceSuperCategoryFacadeBeanLocal serviceSuperCatFacadeBean;
	
	@EJB private SaleChanelFacadeBeanLocal saleChannelFacadeBean;
	
	@EJB private KpiConfigurationFacadeBeanLocal kpiConfigurationFacade;
	
	@EJB private KpiCalculationTypeFacadeBeanLocal kpiCalculationTypeFacade;
	
	@EJB private SkillConfigurationFacadeBeanLocal skillConfigurationFacade;
	
	@EJB private SkillModeTypeFacadeBeanLocal skillModeTypeFacade;
	
	@EJB private SkillEvaluationTypeFacadeBeanLocal skillEvaluationTypeFacade;
	
	@EJB private DealerCoverageFacadeBeanLocal dealerCoverageFacade;
	
	@EJB private DealerConfCoverageFacadeBeanLocal dealerConfCoverageFacade;
		
	@EJB private DealerBuldingFacadeBeanLocal dealerBuildingFacade;
	
	@EJB private DealerDetailFacadeBeanLocal dealerDetailFacade;
	
	@EJB private ConfigJornadasFacadeLocal configJornadasFacade;
	
	@EJB private DealerWeekCapacityFacadeBeanLocal dealerWeekCapacityFacade;
	
	@EJB private DealerCustomerTypesWoutPcFacadeBeanLocal dealerCustomerTypesWoutPcFacade;
	
	@EJB private DealerServiceSubCategoryFacadeBeanLocal dealerServiceSubCategoryFacade;
	
	@EJB private AssignmentConfigurationFacadeBeanLocal assignmentConfigurationFacade;
	
	@EJB private CoverageTypeFacadeBeanLocal coverageTypeFacade;
	
	@EJB private DealerServiceSubCategoryWithPcFacadeBeanLocal dealerServiceSubCategoryWithPcFacade;
	
	@EJB private ServiceTypeWarrantyFacadeBeanLocal serviceTypeWarrantyFacade;
	
	@EJB private ServiceAreWarrantyFacadeBeanLocal serviceAreWarrantyFacade;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#createSaleChannel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	@Override
	public void createSaleChannel(SaleChanelVO saleChannel)
			throws BusinessException {
		saleChannelFacadeBean.createSaleChanel(saleChannel);
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#deleteSaleChannelById(java.lang.Long)
	 */
	@Override
	public void deleteSaleChannelById(Long saleChannelId)
			throws BusinessException {
		saleChannelFacadeBean.deleteSaleChanelById(saleChannelId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getSaleChannelById(java.lang.Long)
	 */
	@Override
	public SaleChanelVO getSaleChannelById(Long saleChannelId)
			throws BusinessException {
		return saleChannelFacadeBean.getSaleChanelByID(saleChannelId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getSaleChannelsByFilters(co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public SaleChannelResponse getSaleChannelsByFilters(
			GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfoDTO requestCollectionInfo)
			throws BusinessException {
		return saleChannelFacadeBean.getSaleChannelsByFilters(request, requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateSaleChannel(co.com.directv.sdii.model.vo.SaleChanelVO)
	 */
	@Override
	public void updateSaleChannel(SaleChanelVO saleChannel)
			throws BusinessException {
		saleChannelFacadeBean.updateSaleChanel(saleChannel);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllServiceSuperCategories()
	 */
	@Override
	public List<ServiceSuperCategoryVO> getAllServiceSuperCategories()
			throws BusinessException {
		return serviceSuperCatFacadeBean.getAllServiceSuperCategorys();
	}
	
	//INICIO Configuración de garantías por categorías de servicio
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getServiceTypeWarrantiesConfigurationByCountryId(java.lang.Long)
	 */
	public List<ServiceTypeWarrantyVO> getServiceTypeWarrantiesConfigurationByCountryId(Long countryId) throws BusinessException {
		return serviceTypeWarrantyFacade.getServiceTypeWarrantiesConfigurationByCountryId(countryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateServiceTypeWarrantiesConfiguration(java.util.List)
	 */
	public void updateServiceTypeWarrantiesConfiguration(List<ServiceTypeWarrantyVO> serviceTypeWarranties) throws BusinessException {
		serviceTypeWarrantyFacade.updateServiceTypeWarrantiesConfiguration(serviceTypeWarranties);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getServiceAreWarrantiesByServiceTypeWarrantyId(java.lang.Long)
	 */
	public List<ServiceAreWarrantyVO> getServiceAreWarrantiesByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws BusinessException {
		return serviceAreWarrantyFacade.getServiceAreWarrantiesByServiceTypeWarrantyId(serviceTypeWarrantyId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateServiceAreWarrantiesConfiguration(java.lang.Long, java.util.List)
	 */
	@Override
	public void updateServiceAreWarrantiesConfiguration(Long serviceTypeWarrantyId, List<ServiceAreWarrantyVO> serviceAreWarranties) throws BusinessException {
		serviceAreWarrantyFacade.updateServiceAreWarrantiesConfiguration(serviceTypeWarrantyId, serviceAreWarranties);
	}
	
	//FIN Configuración de garantías por categorías de servicio
	
	
	//INICIO Configuración de Atención de sub categorías de servicio por cada cobertura de la compañía (config-dealers-cobertura-sub-cat-ser)
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerServiceSubCategoriesWithPcTree(java.lang.Long)
	 */
	@Override
    public List<ServiceTypeVO> getDealerServiceSubCategoriesWithPcTree(Long dealerCoverageId) throws BusinessException {
		return dealerServiceSubCategoryWithPcFacade.getDealerServiceSubCategoriesWithPcTree(dealerCoverageId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerServiceSubCategoriesWithPcConfiguration(java.util.List)
	 */
	@Override
	public void updateDealerServiceSubCategoriesWithPcConfiguration(List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc) throws BusinessException {
		dealerServiceSubCategoryWithPcFacade.updateDealerServiceSubCategoriesWithPcConfiguration(dealerServiceSubCategoriesWithPc);
	}
	
	//FIN Configuración de Atención de sub categorías de servicio por cada cobertura de la compañía (config-dealers-cobertura-sub-cat-ser)
	
	
	//INICIO Configuración de dealers
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerDetailByDealerId(java.lang.Long)
	 */
	@Override
	public DealerDetailVO getDealerDetailByDealerId(Long dealerId) throws BusinessException {
		return dealerDetailFacade.getDealerDetailByID(dealerId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllActiveServiceHoursByCountryId(java.lang.Long)
	 */
	@Override
	public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(Long countryId) throws BusinessException {
		return configJornadasFacade.getAllActiveServiceHoursByCountryId(countryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerWeekCapacityByDealerIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		return dealerWeekCapacityFacade.getDealerWeekCapacityByDealerIdAndCountryId(dealerId, countryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllCoverageTypes()
	 */
	public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException {
		return coverageTypeFacade.getAllCoverageTypes();
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getPostalCodesWitoutCoverageByDealerIdAndCityId(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(Long dealerId, Long cityId, RequestCollectionInfo requestCollectionInfo)
		throws BusinessException {
		return dealerCoverageFacade.getPostalCodesWithoutCoverageByDealerIdAndCityId(dealerId, cityId, requestCollectionInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllActiveByDealerId(java.lang.Long)
	 */
	public DealerCoverageResponse getAllDealerCoverageActiveByDealerId(	Long dealerId, 
																		RequestCollectionInfo requestCollectionInfo
    ) throws BusinessException {
		return dealerCoverageFacade.getAllActiveByDealerId(dealerId, requestCollectionInfo);
	}

	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(java.lang.Long, 
	 * 																															co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo,
	 * 																															java.lang.Long,
	 * 																														    java.lang.Long)
	 */
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(	Long dealerId, 
																							Long cityId,
																							RequestCollectionInfo requestCollectionInfo,
																							Long  customerCategoryId ,
																							Long  businessAreaId 
    ) throws BusinessException {
		//return dealerCoverageFacade.getAllActiveByDealerId(dealerId, requestCollectionInfo);
		  return dealerConfCoverageFacade.getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId( dealerId, 
				  																				   cityId,
																								   requestCollectionInfo,
																								   customerCategoryId ,
																								   businessAreaId );
	}
	
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo, Long customerCategoryId, Long businessAreaId) throws BusinessException{
		return dealerConfCoverageFacade.getPostalCodesWithoutConfCoverageByDealerIdAndCityId(dealerId, cityId, requestCollectionInfo, customerCategoryId, businessAreaId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllConfigurationActiveByDealerId(java.lang.Long)
	 */
	public List<DealerServiceSubCategoryVO> getAllDealerServiceSubCategoryConfiguration(Long dealerId, Long countryId) throws BusinessException {
		return dealerServiceSubCategoryFacade.getAllConfigurationActiveByDealerIdAndCountryId(dealerId, countryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerServiceSubCategoriesTree(java.lang.Long, java.lang.Long)
	 */
	@Override
    public List<ServiceTypeVO> getDealerServiceSubCategoriesTree(Long dealerId, Long countryId) throws BusinessException {
		return dealerServiceSubCategoryFacade.getDealerServiceSubCategoriesTree(dealerId, countryId);
	}

	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerServiceSubCategoriesTreeByBusinessAreaId(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override 
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTreeByBusinessAreaId(Long dealerId,
    																			 Long countryId,  
    																			 Long businessAreaId,
    																			 Long customerCategoryId) 
     throws BusinessException {
		return dealerServiceSubCategoryFacade.getDealerServiceSubCategoriesTreeByBusinessAreaId( dealerId, 
																								 countryId ,
																								 businessAreaId,
																								 customerCategoryId);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllDealerCustomerTypesByDealerIdAndCustomerClassId(java.lang.Long)
	 */
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcConfiguration(Long dealerId, Long countryId) throws BusinessException {
		return dealerCustomerTypesWoutPcFacade.getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(dealerId, countryId);
	}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Nueva Pantalla de Configuracion de Dealers por Tipo de Cliente Carga solapa del acordeon  “Tipos de cliente”
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllDealerCustomerTypesByCustomerCategoryId(java.lang.Long)
	 */
	@Override
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesByCustomerCategoryId(	Long dealerId, 
																							Long countryId, 
																							Long customerCategoryId,
																							Long businessAreaId)
   throws BusinessException {
		return dealerCustomerTypesWoutPcFacade.getAllDealerCustomerTypesByCustomerCategoryId(	dealerId,
																								countryId,
																								customerCategoryId,
																								businessAreaId);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void updateDealerConfiguration(	DealerDetailVO dealerDetail, 
											List<DealerWeekCapacityVO> dealerWeekCapacity, 
											List<DealerCoverageVO> dealerCoverage,
											List<DealerServiceSubCategoryVO> dealerServiceSubCategories, 
											List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc) 
     throws BusinessException {
		assignmentConfigurationFacade.updateDealerConfiguration(dealerDetail, dealerWeekCapacity, dealerCoverage,
				dealerServiceSubCategories, dealerCustomerTypesWoutPc);
	}
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Nueva Pantalla de Configuración de Dealers por Tipo de Cliente
	 * Botón "Aceptar configuración
	 * Servicio			:   AssignmentConfigWS
	 * Nueva operación	:   updateDealerByCustomerClassConfiguration(...)

	 * Nueva Pantalla de Configuración de Dealers por Tipo de Cliente
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerByCustomerCategoryAndAreaConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void updateDealerByCustomerCategoryAndAreaConfiguration(	DealerDetailVO 						dealerDetail			  , //no se visualiza en pantalla info solapa Detalles
																	List<DealerWeekCapacityVO> 			dealerWeekCapacity		  , //no se visualiza en pantalla info solapa Capacidad operatoria disponible
																	List<DealerCoverageVO> 				dealerCoverage			  , //info solapa Cobertura
																	List<DealerServiceSubCategoryVO> 	dealerServiceSubCategories, //info solapa Sub categoria de servicio
																	List<DealerCustomerTypesWoutPcVO> 	dealerCustomerTypesWoutPc , //info solapa Tipo de cliente
																	Long 								userIdLastChange		  ,
																	Long 								dealerId				  ,
																	Long 								customerCategoryId		  ,
																	Long 								businessAreaId) 
	throws BusinessException {
	 
		assignmentConfigurationFacade.updateDealerByCustomerCategoryAndAreaConfiguration(  dealerCoverage				,
																						   dealerServiceSubCategories	, 
																						   dealerCustomerTypesWoutPc	,
																						   userIdLastChange			    ,
																						   dealerId					    ,																				
																						   customerCategoryId			,
																						   businessAreaId);
	}	
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerConfiguration(java.util.List)
	 */
	@Override
	public void updateDealerCoverageConfiguration(List<DealerCoverageVO> dealerCoverage) throws BusinessException {
		assignmentConfigurationFacade.updateDealerConfiguration(dealerCoverage);
	}
	
	//FIN Configuración de dealers

	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerConfCoverageConfiguration(java.util.List)
	 */
	@Override
	public void updateDealerConfCoverageConfiguration(  List<DealerCoverageVO> dealerCoverages,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
	) throws BusinessException {
		//assignmentConfigurationFacade.updateDealerConfiguration(dealerCoverage);
		assignmentConfigurationFacade.updateDealerConfCoverageConfiguration(dealerCoverages,
																			dealerId, 
																			customerCategoryId ,
																			businessAreaId,
																			userIdLastChange);
		
	}
	
	
	
	
	
	//INICIO config-edificios (Atención en edificios)
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getPostalCodesActiveByDealerId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return dealerCoverageFacade.getPostalCodesActiveByDealerId(dealerId, requestCollectionInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerCoverageByDealerIdAndPostalCodeId(java.lang.Long, java.lang.Long)
	 */
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws BusinessException {
		return dealerCoverageFacade.getDealerCoverageByDealerIdAndPostalCodeId(dealerId, postalCodeId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealerBuildingsByPostalCodeId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId, RequestCollectionInfo requestCollectionInfo)throws BusinessException {
		return dealerBuildingFacade.getDealerBuildingsByPostalCodeId(postalCodeId, requestCollectionInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealerBuildings(java.util.List)
	 */
	@Override
	public void updateDealerBuildings(List<DealerBuldingVO> dealerBuildings) throws BusinessException {
		dealerBuildingFacade.updateDealerBuildings(dealerBuildings);
	}
	
	//FIN config-edificios (Atención en edificios)
	
	//INICIO -config-prioridad (Configuración de prioridad de dealers por microzona)
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getDealersCoverageActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@Override
	public List<DealerCoverageVO> getDealersCoverageActiveByPostalCode(Long postalCodeId, String isSeller, String isInstaller)throws BusinessException {
		return dealerCoverageFacade.getAllActiveByPostalCode(postalCodeId, isSeller, isInstaller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateDealersCoverage(java.util.List)
	 */
	@Override
	public void updateDealersCoverage(List<DealerCoverageVO> dealersCoverage) throws BusinessException {
		dealerCoverageFacade.updateDealersCoverage(dealersCoverage);
	}
	
	//FIN -config-prioridad (Configuración de prioridad de dealers por microzona)
	
	
	//INICIO config-habilidades_a_evaluar
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getSkillConfigurationsByCountryIdAndCategoryId(java.lang.Long, java.lang.Long)
	 */
	public List<SkillConfigurationVO> getSkillConfigurationsByCountryIdAndCategoryId(Long countryId, Long serviceTypeId) throws BusinessException {
		return skillConfigurationFacade.getSkillConfigurationsByCountryIdAndCategoryId(countryId, serviceTypeId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getSkillModeTypes()
	 */
	public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException {
		return skillModeTypeFacade.getAllSkillModeTypes();
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getAllSkillEvaluationTypes()
	 */
	public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException {
		return skillEvaluationTypeFacade.getAllSkillEvaluationTypes();
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#updateSkillConfigurations(java.util.List)
	 */
	public void updateSkillConfigurations(List<SkillConfigurationVO> skillConfigurations) throws BusinessException {
		skillConfigurationFacade.updateSkillConfigurations(skillConfigurations);
	}
	
	//FIN config-habilidades_a_evaluar
	
	
	//INICIO config-KPI's
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getKPIConfigurationsByCountryIdAndSupercategoryId(java.lang.Long, java.lang.Long)
	 */
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(Long countryId, Long superCategoryId) throws BusinessException {
		return kpiConfigurationFacade.getKPIConfigurationsByCountryIdAndSupercategoryId(countryId, superCategoryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#saveKPIConfigurations(java.util.List)
	 */
	public void updateKPIConfigurations(List<KpiConfigurationVO> newKpiConfigurations) throws BusinessException {
		kpiConfigurationFacade.updateKpiConfigurations(newKpiConfigurations);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IAssignmentConfigWS#getKpiCalculationTypes()
	 */
	public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException {
		return kpiCalculationTypeFacade.getAllKpiCalculationTypes();
	}
	
	//FIN config-KPI's
	
}
