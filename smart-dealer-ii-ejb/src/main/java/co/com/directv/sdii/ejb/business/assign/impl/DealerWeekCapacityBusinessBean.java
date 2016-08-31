package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
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
import co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerWeekCapacity;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerWeekCapacityDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal
 */
@Stateless(name="DealerWeekCapacityBusinessBeanLocal",mappedName="ejb/DealerWeekCapacityBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerWeekCapacityBusinessBean extends BusinessBase implements DealerWeekCapacityBusinessBeanLocal {

    @EJB(name="DealerWeekCapacityDAOLocal", beanInterface=DealerWeekCapacityDAOLocal.class)
    private DealerWeekCapacityDAOLocal daoDealerWeekCapacity;
    
    @EJB(name="ServiceSuperCategoryDAOLocal", beanInterface=ServiceSuperCategoryDAOLocal.class)
    private ServiceSuperCategoryDAOLocal daoServiceSupercategory;
    
    @EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
    private ServiceHourDAOLocal daoServiceHour;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerWeekCapacityBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal#getAllDealerWeekCapacitys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerWeekCapacityVO> getAllDealerWeekCapacitys() throws BusinessException {
        log.debug("== Inicia getAllDealerWeekCapacitys/DealerWeekCapacityBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerWeekCapacity.getAllDealerWeekCapacitys(), DealerWeekCapacityVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerWeekCapacitys/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerWeekCapacitys/DealerWeekCapacityBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal#getDealerWeekCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerWeekCapacityVO getDealerWeekCapacityByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerWeekCapacityByID/DealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerWeekCapacity objPojo = daoDealerWeekCapacity.getDealerWeekCapacityByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerWeekCapacityVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerWeekCapacityByID/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerWeekCapacityByID/DealerWeekCapacityBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal#createDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia createDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerWeekCapacity objPojo =  UtilsBusiness.copyObject(DealerWeekCapacity.class, obj);
            daoDealerWeekCapacity.createDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal#updateDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerWeekCapacity objPojo =  UtilsBusiness.copyObject(DealerWeekCapacity.class, obj);
            objPojo.setDateLastChange(UtilsBusiness.fechaActual());
            daoDealerWeekCapacity.updateDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerWeekCapacityBusinessBeanLocal#deleteDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerWeekCapacity objPojo =  UtilsBusiness.copyObject(DealerWeekCapacity.class, obj);
            daoDealerWeekCapacity.deleteDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerWeekCapacity/DealerWeekCapacityBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal#getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerWeekCapacityVO getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(
			Long countryId, Long dealerId, Long serviceHourId,
			Long serviceSuperCategoryId) throws BusinessException {
		log.debug("== Inicia getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat/DealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceHourId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceSuperCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerWeekCapacity objPojo = daoDealerWeekCapacity.getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(countryId, dealerId, serviceHourId, serviceSuperCategoryId);
            return UtilsBusiness.copyObject(DealerWeekCapacityVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat/DealerWeekCapacityBusinessBean ==");
        }
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal#getDealerWeekCapacityByDealerIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		log.debug("== Inicia getDealerWeekCapacityByDealerId/DealerWeekCapacityBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<DealerWeekCapacity> dealerWeekCapacity = daoDealerWeekCapacity.getDealerWeekCapacityByDealerId(dealerId);
        	
        	List<ServiceSuperCategory> superCategories = daoServiceSupercategory.getAllServiceSuperCategorys();
        	List<ServiceHour> serviceHours = daoServiceHour.getAllServiceHoursByCountryIdAndStatusCode(countryId, CodesBusinessEntityEnum.SERVICE_HOUR_STATUS_ACTIVE.getCodeEntity());
        	appendMissingKpiConfigurations(dealerWeekCapacity, superCategories, serviceHours, dealerId, countryId);
        	
        	List<DealerWeekCapacityVO> kpiConfigurationsVO = UtilsBusiness.convertList(dealerWeekCapacity, DealerWeekCapacityVO.class);
        	
        	return kpiConfigurationsVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerWeekCapacityByDealerId/DealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerWeekCapacityByDealerId/DealerWeekCapacityBusinessBean ==");
        }
	}


	private void appendMissingKpiConfigurations(
			List<DealerWeekCapacity> dealerWeekCapacity, List<ServiceSuperCategory> superCategories,
			List<ServiceHour> serviceHours, Long dealerId, Long countryId) throws PropertiesException {
		
		for (ServiceSuperCategory serviceSuperCategory : superCategories) {
			List<DealerWeekCapacity> especificDealerWeekCapacity = findInConfigurationList(dealerWeekCapacity, serviceSuperCategory);

			DealerWeekCapacity firstDealerWeekCapacity = (DealerWeekCapacity) UtilsBusiness.getFirstItem(especificDealerWeekCapacity);
			
			Country country = null;
			Dealer dealer = null;
			if(firstDealerWeekCapacity != null) {
				country = firstDealerWeekCapacity.getCountry();
				dealer = firstDealerWeekCapacity.getDealer();
			} else {
				country = new Country(countryId);
				dealer = new Dealer(dealerId);
			}
			
			for (ServiceHour serviceHour : serviceHours) {
				DealerWeekCapacity dealerWeekCap =  findInConfigurationList(especificDealerWeekCapacity, serviceHour);
				
				if(dealerWeekCap == null) {
					dealerWeekCap = new DealerWeekCapacity(serviceSuperCategory, dealer, country, serviceHour);
					dealerWeekCapacity.add(dealerWeekCap);
				}
			}
			
		}
		
	}


	private DealerWeekCapacity findInConfigurationList(List<DealerWeekCapacity> dealerWeekCapacities, ServiceHour serviceHour) {
		if(dealerWeekCapacities != null) {
			for (DealerWeekCapacity dealerWeekCapacity : dealerWeekCapacities) {
				if(serviceHour.getId().equals(dealerWeekCapacity.getServiceHour().getId())) {
					return dealerWeekCapacity;
				}
			}
		}
		return null;
	}


	private List<DealerWeekCapacity> findInConfigurationList(List<DealerWeekCapacity> dealerWeekCapacities, ServiceSuperCategory serviceSuperCategory) {
		List<DealerWeekCapacity> confs = new ArrayList<DealerWeekCapacity>();
		if(dealerWeekCapacities != null) {
			for (DealerWeekCapacity kpiConfiguration : dealerWeekCapacities) {
				if(serviceSuperCategory.getId().equals(kpiConfiguration.getServiceSuperCategory().getId())) {
					confs.add(kpiConfiguration);
				}
			}
		}
		return confs;
	}
	
}
