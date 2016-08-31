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
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CoverageType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal;
import co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal;
import co.com.directv.sdii.reports.dto.DealerCoverageDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal
 */
@Stateless(name="DealerCoverageBusinessBeanLocal",mappedName="ejb/DealerCoverageBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCoverageBusinessBean extends BusinessBase implements DealerCoverageBusinessBeanLocal {

    @EJB(name="DealerCoverageDAOLocal", beanInterface=DealerCoverageDAOLocal.class)
    private DealerCoverageDAOLocal daoDealerCoverage;
    
    @EJB(name="CoverageTypeDAOLocal", beanInterface=CoverageTypeDAOLocal.class)
    private CoverageTypeDAOLocal daoCoverageType;
    
    @EJB(name="DealerConfigurationDAOLocal", beanInterface=DealerConfigurationDAOLocal.class)
    private DealerConfigurationDAOLocal dealerConfigurationDAO;

    @EJB(name="DealerConfCoverageDAOLocal", beanInterface=DealerConfCoverageDAOLocal.class)
    private DealerConfCoverageDAOLocal dealerConfCoverageDAO;
    
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerCoverageBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal#getAllDealerCoverages()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerCoverageVO> getAllDealerCoverages() throws BusinessException {
        log.debug("== Inicia getAllDealerCoverages/DealerCoverageBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerCoverage.getAllDealerCoverages(), DealerCoverageVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerCoverages/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerCoverages/DealerCoverageBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal#getDealerCoveragesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerCoverageVO getDealerCoverageByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerCoverageByID/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCoverage objPojo = daoDealerCoverage.getDealerCoverageByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerCoverageVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCoverageByID/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCoverageByID/DealerCoverageBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal#createDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerCoverage(DealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia createDealerCoverage/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCoverage objPojo =  UtilsBusiness.copyObject(DealerCoverage.class, obj);
            daoDealerCoverage.createDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerCoverage/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerCoverage/DealerCoverageBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal#updateDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerCoverage(DealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerCoverage/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCoverage objPojo =  UtilsBusiness.copyObject(DealerCoverage.class, obj);
            daoDealerCoverage.updateDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerCoverage/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerCoverage/DealerCoverageBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCoverageBusinessBeanLocal#deleteDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerCoverage(DealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerCoverage/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCoverage objPojo =  UtilsBusiness.copyObject(DealerCoverage.class, obj);
            daoDealerCoverage.deleteDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerCoverage/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerCoverage/DealerCoverageBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getDealersInMicrozoneWithExMode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersInMicrozoneWithExMode(String executionMode, String postalCode, String countryIso2Code,Long dealerId) throws BusinessException {
        log.debug("== Inicia getDealersInMicrozoneWithExMode/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(executionMode, ErrorBusinessMessages.ALLOCATOR_AL038.getCode(), ErrorBusinessMessages.ALLOCATOR_AL038.getMessage());
        UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.ALLOCATOR_AL041.getCode(), ErrorBusinessMessages.ALLOCATOR_AL041.getMessage());
        UtilsBusiness.assertNotNull(countryIso2Code, ErrorBusinessMessages.ALLOCATOR_AL033.getCode(), ErrorBusinessMessages.ALLOCATOR_AL033.getMessage());
        try {
        	List<Dealer> dealersList = daoDealerCoverage.getDealersInMicrozoneWithExMode(executionMode, postalCode, countryIso2Code,dealerId);
        	return UtilsBusiness.convertList(dealersList, DealerVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersInMicrozoneWithExMode/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersInMicrozoneWithExMode/DealerCoverageBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getDealersInMicrozoneWithExMode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean checkDealersInMicrozoneWithTypePerm(String postalCode, 
			                                        String countryIso2Code,
			                                        Long dealerId) throws BusinessException {
		
        log.debug("== Inicia countDealersInMicrozoneWithTypePerm/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(countryIso2Code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	boolean result = true;
    		//Se consulta si el dealer es permanente
    		Long countDealers = daoDealerCoverage.countDealersInMicrozoneWithTypePerm(postalCode, countryIso2Code,dealerId);
    		//el dealer debe ser permanente
    		if(countDealers.longValue()==0)
    			result = false;
    		
    		return result;
    		
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación countDealersInMicrozoneWithTypePerm/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina countDealersInMicrozoneWithTypePerm/DealerCoverageBusinessBean ==");
        }
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getAllActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCoverageVO> getAllActiveByPostalCode(Long postalCodeId, String isSeller, String isInstaller) throws BusinessException {
		log.debug("== Inicia getAllActiveByPostalCode/DealerCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(postalCodeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<DealerCoverageVO> dealersVO = null;
		try {
			List<DealerCoverage> dealers = daoDealerCoverage.getAllActiveByPostalCodeId(postalCodeId, isSeller, isInstaller);
			dealersVO = UtilsBusiness.convertList(dealers, DealerCoverageVO.class);
		} catch (Throwable e) {
			log.error("== Error getAllActiveByPostalCode/DealerCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getAllActiveByPostalCode/DealerCoverageBusinessBean ==");
		}
		return dealersVO;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#updateDealersCoverage(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealersCoverage(List<DealerCoverageVO> dealersCoverage) throws BusinessException {
		log.debug("== Inicia updateDealersCoverage/DealerCoverageBusinessBean ==");
        
        try {
        	CoverageType coverageTypePermanent = daoCoverageType.getCoverageTypePermanent();
        	CoverageType coverageTypeOccasional = daoCoverageType.getCoverageTypeOccasional();
        	
        	//persistir las modificaciones a las configuraciones
        	for (DealerCoverageVO dealerCoverageVO : dealersCoverage) {
        		DealerCoverage dealerCoverage = UtilsBusiness.copyObject(DealerCoverage.class, dealerCoverageVO);
        		dealerCoverage.setDateLastChange(UtilsBusiness.fechaActual());
        		if(dealerCoverage.getCoverageType() != null) {
        			if(dealerCoverage.getCoverageType().getCode().equals(CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity())) {
        				dealerCoverage.setCoverageType(coverageTypePermanent);
        			} else if (dealerCoverage.getCoverageType().getCode().equals(CodesBusinessEntityEnum.COVERAGE_TYPE_OCASSIONAL.getCodeEntity())) {
        				dealerCoverage.setCoverageType(coverageTypeOccasional);
        			} else  {
        				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL019.getCode(), ErrorBusinessMessages.ALLOCATOR_AL019.getMessage());
        			}
        		}
        		List<DealerCoverage> dealerCoverages= daoDealerCoverage.getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(dealerCoverage.getDealer().getId(), dealerCoverage.getPostalCode().getId(), dealerCoverage.getCountry().getId(), null);
        		if(dealerCoverages != null && !dealerCoverages.isEmpty())
        			dealerCoverage.setId(dealerCoverages.get(0).getId());
        		daoDealerCoverage.updateDealerCoverage(dealerCoverage);
			}

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealersCoverage/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealersCoverage/DealerCoverageBusinessBean ==");
        }
	}
	
	/*
	 *
	 * modificacion ialessan
	 * Spira_29346_Old28780-ReplicarFuncPantallaACargaMasivaCobertura
	 * abril 2014
	 * 
 	 * Si ya existe una cobertura configurada (para la tupla dealer+categoria cliente+area negocio) con 
	 * las mismas caracteristicas:
	 *		- mismo dealer
	 * 		- mismo codigo postal
	 * 	esta cobertura preexistente se actualizara con los nuevos datos:
	 * 	    - tipo de cobertura (permanente: S ó N) - (dato proveniente del excel de conf masiva de coberturas)
	 *		- prioridad del dealer (dato proveniente del excel de conf masiva de coberturas - no obligatorio - si no llega en el excel se inserta 0)
	 * 	    - estado del registro (siempre va en S - no se utiliza el borrado logico)
	 * 
	 * Si no existe cobertura siminar preexistente se creará.  
	 *  
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#updateDealerConfCoverageConfiguration(java.util.List,java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerConfCoverageConfiguration(  List<DealerCoverageVO> dealersCoverages,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
) throws BusinessException {
		log.debug("== Inicia updateDealerConfCoverageConfiguration/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(customerCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(businessAreaId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userIdLastChange, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

        try {
        	
        	DealerConfiguration dealerConfiguration = dealerConfigurationDAO.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, businessAreaId, customerCategoryId);
        	
        	if(dealerConfiguration == null){
        		DealerConfiguration newDealerConfig = new DealerConfiguration();
        		newDealerConfig.setDealerId(dealerId);
        		newDealerConfig.setCustomerCategoryId(customerCategoryId);
        		newDealerConfig.setBusinessAreaId(businessAreaId);
        		newDealerConfig.setDateLastChange(new Date());
        		newDealerConfig.setUserIdLastChange(userIdLastChange);
        		newDealerConfig.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        		
        		dealerConfigurationDAO.createDealerConfiguration(newDealerConfig);
        		dealerConfiguration = newDealerConfig;
        	}
        	
        	//Creo solo los coverages que esten con estado en S.
        	for(DealerCoverageVO dealerCoverageVO : dealersCoverages){
        		if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerCoverageVO.getStatus())){
        			
        			DealerConfCoverage  dealerConfCoverage = new DealerConfCoverage();	    			
	    			dealerConfCoverage.setDealerConfigurationId(dealerConfiguration.getId());
	    			dealerConfCoverage.setCountry(dealerCoverageVO.getCountry());
	    			dealerConfCoverage.setCountryId(dealerCoverageVO.getCountry().getId());	    			
	    			dealerConfCoverage.setDealer(dealerCoverageVO.getDealer());	    			
	    			dealerConfCoverage.setDealerId(dealerCoverageVO.getDealer().getId());	    			
	    			dealerConfCoverage.setPostalCode(dealerCoverageVO.getPostalCode());
	    			dealerConfCoverage.setPostalCodeIds(dealerCoverageVO.getPostalCode().getId());	    			
	    			CoverageType coverageType = daoCoverageType.getCoverageTypePermanent();	    			
	    			dealerConfCoverage.setCoverageType(coverageType);
	    			dealerConfCoverage.setCoverageTypeId(coverageType.getId());
	    			dealerConfCoverage.setDealerPriority(0L);
	    			dealerConfCoverage.setDateLastChange(new Date());
	    			dealerConfCoverage.setUserIdLastChange(userIdLastChange);
	    			dealerConfCoverage.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

	    			DealerConfCoverage coberturaPreexistente =  dealerConfCoverageDAO.getDealerConfCoverageBy(dealerConfiguration.getId(), dealerConfCoverage.getDealerId(), dealerConfCoverage.getPostalCodeIds());	    			
        			
        			 if (coberturaPreexistente == null)
        			 
	        			dealerConfCoverageDAO.createDealerConfCoverage(dealerConfCoverage);
        			else
        			{
        				dealerConfCoverage.setId(coberturaPreexistente.getId());
        				dealerConfCoverageDAO.updateDealerConfCoverage(dealerConfCoverage);
        			}	
        			
        		}else{
        				dealerConfCoverageDAO.deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(dealerConfiguration.getId(), dealerCoverageVO.getPostalCode().getId());
        		}
        	}

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerConfCoverageConfiguration/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerConfCoverageConfiguration/DealerCoverageBusinessBean ==");
        }
	}	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getMicrozonesByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getPostalCodesActiveByDealerId/DealerCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		PostalCodeResponse postalCodeResponse = null;
		try {
			postalCodeResponse = daoDealerCoverage.getPostalCodesActiveByDealerId(dealerId, requestCollectionInfo);
			if (postalCodeResponse != null && postalCodeResponse.getPostalCodes() != null) {
				postalCodeResponse.setPostalCodesVO(UtilsBusiness.convertList(postalCodeResponse.getPostalCodes(), PostalCodeVO.class));
				postalCodeResponse.setPostalCodes(null);
			}
		} catch (Throwable e) {
			log.error("== Error getPostalCodesActiveByDealerId/DealerCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getPostalCodesActiveByDealerId/DealerCoverageBusinessBean ==");
		}
		return postalCodeResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getAllActiveByDealerId/DealerCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(requestCollectionInfo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		DealerCoverageResponse response = null;
		try {
			response = daoDealerCoverage.getAllActiveByDealerId(dealerId, requestCollectionInfo);
			if (response != null && response.getDealerCoverages() != null) {
				response.setDealerCoveragesVO(UtilsBusiness.convertList(response.getDealerCoverages(), DealerCoverageVO.class));
				response.setDealerCoverages(null);
			}
		} catch (Throwable e) {
			log.error("== Error getAllActiveByDealerId/DealerCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getAllActiveByDealerId/DealerCoverageBusinessBean ==");
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getPostalCodesWitoutCoverageByDealerIdAndCityId(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(cityId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(requestCollectionInfo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		PostalCodeResponse response = null;
		try {
			response = daoDealerCoverage.getPostalCodesWithoutCoverageByDealerIdAndCityId(dealerId, cityId, requestCollectionInfo);
			if (response != null && response.getPostalCodes() != null) {
				response.setPostalCodesVO(UtilsBusiness.convertList(response.getPostalCodes(), PostalCodeVO.class));
				response.setPostalCodes(null);
			}
		} catch (Throwable e) {
			log.error("== Error getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageBusinessBean ==");
		}
		return response;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getDealerCoverageByDealerIdAndPostalCodeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws BusinessException {
		log.debug("== Inicia getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(postalCodeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCoverage objPojo = daoDealerCoverage.getDealerCoverageByDealerIdAndPostalCodeId(dealerId, postalCodeId);
            return UtilsBusiness.copyObject(DealerCoverageVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal#getAllDealerCoverageByCountryAndActiveStatus(java.lang.Long)
	 */
	@Override
	public List<DealerCoverageDTO> getAllDealerCoverageByCountryAndActiveStatus(Long countryID)throws BusinessException {
		log.debug("== Inicia getAllDealerCoverageByCountryAndActiveStatus/DealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(countryID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<DealerCoverageDTO> result = new ArrayList<DealerCoverageDTO>();
        	List<DealerCoverageVO> resultVO = new ArrayList<DealerCoverageVO>();
            List<DealerCoverage> listTmp =  daoDealerCoverage.getAllDealerCoveragesByCountryAndStatus(countryID, CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());
            resultVO = UtilsBusiness.convertList(listTmp, DealerCoverageVO.class);
            for(DealerCoverageVO  tmp : resultVO){
            	DealerCoverageDTO tmpDTO= new DealerCoverageDTO();
            	tmpDTO.setStateName(tmp.getPostalCode().getCity().getState().getStateName());
            	tmpDTO.setCityName(tmp.getPostalCode().getCity().getCityName());
            	tmpDTO.setPostalCodeName(tmp.getPostalCode().getPostalCodeName());
            	tmpDTO.setDealerCode(tmp.getDealer().getDealerCode().toString());
            	tmpDTO.setDepotCode(tmp.getDealer().getDepotCode());
            	tmpDTO.setCoverageTypeName(tmp.getCoverageType().getName());
            	result.add(tmpDTO);
            }
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerCoverageByCountryAndActiveStatus/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerCoverageByCountryAndActiveStatus/DealerCoverageBusinessBean ==");
        }
	}
	
	public List<DealerCoverageVO> getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(
			Long dealerId, Long postalCodeId, Long countryId,String statusActive) throws BusinessException {
		
		log.debug("== Inicia getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive/DealerCoverageBusinessBean ==");
        try {
        	
        	return UtilsBusiness.convertList(daoDealerCoverage.getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(dealerId, postalCodeId, countryId, statusActive), DealerCoverageVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive/DealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive/DealerCoverageBusinessBean ==");
        }
	}
	
}
