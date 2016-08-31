package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCapacity;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.vo.DealerCapacityVO;
import co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerCapacityDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal
 */
@Stateless(name="DealerCapacityBusinessBeanLocal",mappedName="ejb/DealerCapacityBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCapacityBusinessBean extends BusinessBase implements DealerCapacityBusinessBeanLocal {

    @EJB(name="DealerCapacityDAOLocal", beanInterface=DealerCapacityDAOLocal.class)
    private DealerCapacityDAOLocal daoDealerCapacity;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerCapacityBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#getAllDealerCapacitys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerCapacityVO> getAllDealerCapacitys() throws BusinessException {
        log.debug("== Inicia getAllDealerCapacitys/DealerCapacityBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerCapacity.getAllDealerCapacitys(), DealerCapacityVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerCapacitys/DealerCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerCapacitys/DealerCapacityBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#getDealerCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerCapacityVO getDealerCapacityByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerCapacityByID/DealerCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCapacity objPojo = daoDealerCapacity.getDealerCapacityByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerCapacityVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCapacityByID/DealerCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCapacityByID/DealerCapacityBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#createDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerCapacity(DealerCapacityVO obj) throws BusinessException {
        log.debug("== Inicia createDealerCapacity/DealerCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCapacity objPojo =  UtilsBusiness.copyObject(DealerCapacity.class, obj);
            daoDealerCapacity.createDealerCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerCapacity/DealerCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerCapacity/DealerCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#updateDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerCapacity(DealerCapacityVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerCapacity/DealerCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCapacity objPojo =  UtilsBusiness.copyObject(DealerCapacity.class, obj);
            daoDealerCapacity.updateDealerCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerCapacity/DealerCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerCapacity/DealerCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#deleteDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerCapacity(DealerCapacityVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerCapacity/DealerCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCapacity objPojo =  UtilsBusiness.copyObject(DealerCapacity.class, obj);
            daoDealerCapacity.deleteDealerCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerCapacity/DealerCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerCapacity/DealerCapacityBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal#saveDealerCapacity(co.com.directv.sdii.assign.schedule.DealerWorkLoad)
	 */
	@Override
	public void saveDealerCapacity(DealerWorkLoad dealerCapacity) throws ScheduleException {
		log.debug("== Inicia saveDealerCapacity/DealerCapacityBusinessBean ==");
        try {
        	String paramsNullCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String paramsNullMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
        	if( dealerCapacity == null ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else if( dealerCapacity.getDealerWorkCapacity() == null ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria() == null ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getCountryId() == null || dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getCountryId().longValue() <= 0 ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else  if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getDealerId() == null || dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getDealerId().longValue() <= 0 ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else  if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getSuperCategoryServiceId() == null || dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getSuperCategoryServiceId().longValue() <= 0 ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else  if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId() == null || dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId().longValue() <= 0 ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	} else  if( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getCapacityDate() == null ){
        		throw new ScheduleException(paramsNullCode,paramsNullMessage);
        	}
        	//Se consulta el objeto para saber si se actualiza o si crea una nueva capacidad por dealer
        	DealerCapacity objPojo = this.daoDealerCapacity.getDealerCapacityByCriteria( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria() );
        	//En caso de encontrarse se actualiza la capacidad
        	if( objPojo != null && objPojo.getId() != null && objPojo.getId().longValue() > 0 ){
        		objPojo.setMaxCapacity( Integer.valueOf( dealerCapacity.getDealerWorkCapacity().getMaxCapacity()).longValue() );
        		objPojo.setUsedCapacity( Integer.valueOf( dealerCapacity.getUsedCapacity() ).longValue() );
        		this.daoDealerCapacity.updateDealerCapacity(objPojo);
        	//En caso de no encontrar se crea la nueva capacidad por dealer
        	}else {
        		objPojo = new DealerCapacity();
        		Dealer dealer = new Dealer( dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getDealerId() );
        		ServiceSuperCategory superCategory = new ServiceSuperCategory(dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getSuperCategoryServiceId());
        		Country country = new Country(dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getCountryId());
        		ServiceHour serviceHour = new ServiceHour(dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId());
        		
        		objPojo.setMaxCapacity( Integer.valueOf( dealerCapacity.getDealerWorkCapacity().getMaxCapacity()).longValue() );
        		objPojo.setUsedCapacity( Integer.valueOf( dealerCapacity.getUsedCapacity() ).longValue() );
        		objPojo.setDealer(dealer);
        		objPojo.setCapacityDate(dealerCapacity.getDealerWorkCapacity().getCapacityCriteria().getCapacityDate());
        		objPojo.setCountry(country);
        		objPojo.setServiceHour(serviceHour);
        		objPojo.setServiceSuperCategory(superCategory);
        		this.daoDealerCapacity.createDealerCapacity(objPojo);
        	}
        } catch (ScheduleException e) {
			throw e;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación saveDealerCapacity/DealerCapacityBusinessBean ==");
        	throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL005.getCode(),ErrorBusinessMessages.ALLOCATOR_AL005.getCode());
        } finally {
            log.debug("== Termina saveDealerCapacity/DealerCapacityBusinessBean ==");
        }
	}
}
