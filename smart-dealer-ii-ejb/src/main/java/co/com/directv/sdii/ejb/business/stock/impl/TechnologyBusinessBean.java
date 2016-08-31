package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Technology
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.CauseAdjustmentDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.CauseAdjustmentBusinessBeanLocal
 */
@Stateless(name="TechnologyBusinessBeanLocal",mappedName="ejb/TechnologyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TechnologyBusinessBean extends BusinessBase implements TechnologyBusinessBeanLocal {

    @EJB(name="TechnologyDAOLocal", beanInterface=TechnologyDAOLocal.class)
    private TechnologyDAOLocal daoTechnology;
    
    @EJB(name="ShippingOrderDetailDAOLocal",beanInterface=ShippingOrderDetailDAOLocal.class)
	private ShippingOrderDetailDAOLocal shippingOrderDetailDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(TechnologyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#getAllTechnologys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<TechnologyVO> getAllTechnologies() throws BusinessException {
        log.debug("== Inicia getAllTechnologies/TechnologyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoTechnology.getAllTechnologies(), TechnologyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllTechnologies/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTechnologies/TechnologyBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#getActiveTechnologys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<TechnologyVO> getActiveTechnologies() throws BusinessException {
        log.debug("== Inicia getActiveTechnologies/TechnologyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoTechnology.getActiveTechnologies(), TechnologyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveTechnologies/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveTechnologies/TechnologyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#getTechnologysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public TechnologyVO getTechnologyByID(Long id) throws BusinessException {
        log.debug("== Inicia getTechnologyByID/TechnologyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Technology objPojo = daoTechnology.getTechnologyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(TechnologyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTechnologyByID/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTechnologyByID/TechnologyBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#createTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createTechnology(TechnologyVO obj) throws BusinessException {
        log.debug("== Inicia createTechnology/TechnologyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Technology objPojo = daoTechnology.getTechnologyByCode(obj.getCode());
        	if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(Technology.class, obj);
            daoTechnology.createTechnology(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createTechnology/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTechnology/TechnologyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#updateTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTechnology(TechnologyVO obj) throws BusinessException {
        log.debug("== Inicia updateTechnology/TechnologyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Technology objPojo = daoTechnology.getTechnologyByCode(obj.getCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(Technology.class, obj);
            daoTechnology.updateTechnology(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateTechnology/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTechnology/TechnologyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#deleteTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTechnology(TechnologyVO obj) throws BusinessException {
        log.debug("== Inicia deleteTechnology/TechnologyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Technology objPojo =  UtilsBusiness.copyObject(Technology.class, obj);
            daoTechnology.deleteTechnology(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteTechnology/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTechnology/TechnologyBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal#getTechnologyByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TechnologyVO getTechnologyByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getTechnologyByCode/TechnologyBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Technology objPojo = daoTechnology.getTechnologyByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(TechnologyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTechnologyByCode/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTechnologyByCode/TechnologyBusinessBean ==");
        }
	}
	
	public List<TechnologyVO> getShippingorderTechnology(Long soId) throws BusinessException{
		log.debug("== Inicia getShippingorderTechnology/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<TechnologyVO> response = null;
			
			List<TechnologyVO> tecnologies = getAllIRDTechnologies();
			
			for(TechnologyVO t: tecnologies){
				List<Technology> unique = this.shippingOrderDetailDAO.getShippingOrderTechnologiesBySOIdAndTecCode(soId, t.getCode());
				if(unique != null && !unique.isEmpty()){
					response = UtilsBusiness.convertList(unique, TechnologyVO.class);
					break;
				}
			}
			
			return response;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la tarea getShippingorderTechnology/TrayWorkOrderManagmentBusinessBean ==", e);
	    	throw this.manageException(e);
		} finally {
			log.debug("== Termina getShippingorderTechnology/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	@Override
	public List<TechnologyVO> getAllIRDTechnologies() throws BusinessException {
        log.debug("== Inicia getAllIRDTechnologies/TechnologyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoTechnology.getAllIRDTechnologies(), TechnologyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllIRDTechnologies/TechnologyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllIRDTechnologies/TechnologyBusinessBean ==");
        }
	}
}
