package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import co.com.directv.sdii.ejb.business.assign.DealerConfCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.DealerConfCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerConfCoverageVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * EJB que implementa las operaciones Tipo CRUD DealerConfCoverage
 * 
 * Fecha de Creación: Vier 4, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerConfCoverageDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerConfCoverageBusinessBeanLocal
 */
@Stateless(name="DealerConfCoverageBusinessBeanLocal",mappedName="ejb/DealerConfCoverageBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfCoverageBusinessBean extends BusinessBase implements DealerConfCoverageBusinessBeanLocal {

	   @EJB(name="DealerConfCoverageDAOLocal", beanInterface=DealerConfCoverageDAOLocal.class)
	    private DealerConfCoverageDAOLocal daoDealerConfCoverage;
	    
	   @EJB(name="DealerConfigurationDAOLocal", beanInterface=DealerConfigurationDAOLocal.class)
	    private DealerConfigurationDAOLocal daoDealerConfiguration;
	   
	   
	    private final static Logger log = UtilsBusiness.getLog4J(DealerCoverageBusinessBean.class);

	    
	 private DealerCoverageResponse dealerConfCoverageResponseToDealerCoverageResponse(DealerConfCoverageResponse dealerConfCoverageResponse){
		 
		 DealerCoverageResponse dealerCoverageResponse = new DealerCoverageResponse();
		 
		 List<DealerCoverage> dealerCoveragesList = new ArrayList();
		 
		 for (DealerConfCoverage dealerConfCoverage : dealerConfCoverageResponse.getDealerConfCoverages()){
			 
			 DealerCoverage dealerCoverage = new DealerCoverage();
			 
			 dealerCoverage.setId(dealerConfCoverage.getId()); //por si UI necesita retornar el id, usale el id de la nueva tabla
			 
			 dealerCoverage.setUser(new User()); //se necesita en el response?
			 dealerCoverage.setUserId(dealerConfCoverage.getUserId());
			 
			 dealerCoverage.setCoverageType(dealerConfCoverage.getCoverageType());
			 dealerCoverage.setCoverageTypeId(dealerConfCoverage.getCoverageTypeId());
			 
			 dealerCoverage.setDealer(dealerConfCoverage.getDealer());
			 dealerCoverage.setDealerId(dealerConfCoverage.getDealer().getId());
			 
			 dealerCoverage.setCountry(dealerConfCoverage.getCountry());
			 dealerCoverage.setCountryId(dealerConfCoverage.getCountry().getId());
			 
			 dealerCoverage.setPostalCode(dealerConfCoverage.getPostalCode());
			 //dealerCoverage.setPostalCodeIds(dealerConfCoverage.getPostalCode().getId()); //null pointer?
			 dealerCoverage.setPostalCodeIds(dealerConfCoverage.getPostalCodeIds());
			 
			 dealerCoverage.setDealerPriority(dealerConfCoverage.getDealerPriority()); 
			 
			 dealerCoverage.setDateLastChange(new Date());
			 dealerCoverage.setStatus("S"); //aqui el status siempre es ok porque si esta el registro en la tabla pertenece a la configuracion
			 
			 dealerCoveragesList.add(dealerCoverage);
			 
		 }
		 
		 dealerCoverageResponse.setDealerCoverages(dealerCoveragesList);
		 dealerCoverageResponse.setPageCount(dealerConfCoverageResponse.getPageCount());
		 dealerCoverageResponse.setPageIndex(dealerConfCoverageResponse.getPageIndex());
		 dealerCoverageResponse.setPageSize(dealerConfCoverageResponse.getPageSize());
		 dealerCoverageResponse.setRowCount(dealerConfCoverageResponse.getRowCount());
		 dealerCoverageResponse.setTotalRowCount(dealerConfCoverageResponse.getTotalRowCount());
		 
		 return dealerCoverageResponse;
	 } 
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 *
     * Spira 28731 – Quitar filtros de Departamento y Ciudad al “Cargar Configuración” – solapa de cobertura
     * modificación
     * ialessan
     * marzo 2014
     *
     * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(  Long dealerId,
																							//Long cityId,
																							RequestCollectionInfo requestCollectionInfo,
																							Long  customerCategoryId ,
																							Long  businessAreaId
   ) throws BusinessException{
		log.debug("== Inicia getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId/DealerConfCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId             , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(requestCollectionInfo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customerCategoryId   , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(businessAreaId       , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		DealerCoverageResponse response = null;
		DealerConfCoverageResponse dealerConfCoverageResponse = null;
		try {
			 
			DealerConfiguration dealerConfiguration =  daoDealerConfiguration.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, businessAreaId, customerCategoryId);
			if (dealerConfiguration != null){
				//dealerConfCoverageResponse = daoDealerConfCoverage.getDealerConfCoverageByDealerConfId( dealerConfiguration.getId(), cityId, requestCollectionInfo);
				dealerConfCoverageResponse = daoDealerConfCoverage.getDealerConfCoverageByDealerConfId( dealerConfiguration.getId(), requestCollectionInfo);
				
				if((dealerConfCoverageResponse.getDealerConfCoverages() != null)||(!dealerConfCoverageResponse.getDealerConfCoverages().isEmpty())){
					response=dealerConfCoverageResponseToDealerCoverageResponse(dealerConfCoverageResponse);
				}
			}
			
			if (response != null && response.getDealerCoverages() != null) {
				response.setDealerCoveragesVO(UtilsBusiness.convertList(response.getDealerCoverages(), DealerCoverageVO.class));
				response.setDealerCoverages(null);
			}
			
		} catch (Throwable e) {
			log.error("== Error getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId/DealerConfCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId/DealerConfCoverageBusinessBean ==");
		}
		return response;

	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getPostalCodesWitoutCoverageByDealerIdAndCityId(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo, Long customerCategoryId, Long businessAreaId) throws BusinessException {
		
		log.debug("== Inicia getPostalCodesWithoutConfCoverageByDealerIdAndCityId/DealerConfCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(cityId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(requestCollectionInfo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customerCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(businessAreaId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		PostalCodeResponse response = null;
		try {
			DealerConfiguration dealerConfiguration = daoDealerConfiguration.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, businessAreaId, customerCategoryId);
			
			Long dealerConfigId = dealerConfiguration == null ? null : dealerConfiguration.getId();
			
			response = daoDealerConfCoverage.getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId(dealerConfigId, cityId, requestCollectionInfo);
			if (response != null && response.getPostalCodes() != null) {
				response.setPostalCodesVO(UtilsBusiness.convertList(response.getPostalCodes(), PostalCodeVO.class));
				response.setPostalCodes(null);
			}
		} catch (Throwable e) {
			log.error("== Error getPostalCodesWithoutConfCoverageByDealerIdAndCityId/DealerConfCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getPostalCodesWithoutConfCoverageByDealerIdAndCityId/DealerConfCoverageBusinessBean ==");
		}
		return response;
	}

	/* 
	 * modificacion ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
	 *  
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerConfCoverageBusinessBeanLocal#updateDealerConfCoverage(co.com.directv.sdii.model.vo.DealerConfCoverageVO)
	 */
	@Override
	public DealerConfCoverageVO getDealerConfCoverageBy(Long dealerConfigurationId, Long dealerId, Long postalCodeId) throws BusinessException {
		log.debug("== Inicia getDealerConfCoverageBy/DealerConfCoverageBusinessBean ==");
		
    	String paramsRequired = new String("");
    	
        if (  dealerConfigurationId == null 
            ||dealerId == null         	
        	||postalCodeId == null 
        	) 
        {
            
            if (dealerConfigurationId == null)                	
            	paramsRequired = paramsRequired.concat( " Dealer Conf Id ");                
            else if (dealerId == null)
            	paramsRequired = paramsRequired.concat( " Dealer Id ");
            else if (postalCodeId == null)
            	paramsRequired = paramsRequired.concat( " Postal Code Id "); //validar antes
            
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage().concat(paramsRequired));
        }
		
        try {
        	DealerConfCoverage dealerConfCoverage = daoDealerConfCoverage.getDealerConfCoverageBy(dealerConfigurationId, dealerId, postalCodeId);
        	DealerConfCoverageVO dealerConfCoverageVO = UtilsBusiness.copyObject(DealerConfCoverageVO.class, dealerConfCoverage);
        	return dealerConfCoverageVO;
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerConfCoverageBy/DealerConfCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerConfCoverageBy/DealerConfCoverageBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerConfCoverageBusinessBeanLocal#updateDealerConfCoverage(co.com.directv.sdii.model.vo.DealerConfCoverageVO)
	 */
	@Override
	public void updateDealerConfCoverage(DealerConfCoverageVO dealerConfCoverageVO) throws BusinessException {
        log.debug("== Inicia updateDealerConfCoverage/DealerConfCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerConfCoverageVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(dealerConfCoverageVO)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	DealerConfCoverage dealerConfCoverage = UtilsBusiness.copyObject(DealerConfCoverage.class, dealerConfCoverageVO);
        	daoDealerConfCoverage.updateDealerConfCoverage(dealerConfCoverage);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerConfCoverage/DealerConfCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerConfCoverage/DealerConfCoverageBusinessBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getAllDealerCoverageByCountryAndActiveStatus(java.lang.Long)
	 */
	@Override
	public List<DealerConfCoverageDTO> getAllDealerConfCoverageByCountryAndActiveStatus(Long countryID) throws BusinessException {
		log.debug("== Inicia getAllDealerConfCoverageByCountryAndActiveStatus/DealerConfCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(countryID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            List<DealerConfCoverageDTO> list =  daoDealerConfCoverage.getAllDealerConfCoveragesByCountryAndStatus(countryID, CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());

            return list;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerConfCoverageByCountryAndActiveStatus/DealerConfCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerConfCoverageByCountryAndActiveStatus/DealerConfCoverageBusinessBean ==");
        }
	}
}
