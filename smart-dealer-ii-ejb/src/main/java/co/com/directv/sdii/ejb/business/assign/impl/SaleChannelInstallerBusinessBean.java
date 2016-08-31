package co.com.directv.sdii.ejb.business.assign.impl;

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
import co.com.directv.sdii.ejb.business.assign.SaleChannelInstallerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SaleChannelInstaller;
import co.com.directv.sdii.model.vo.SaleChannelInstallerVO;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD SaleChannelInstaller
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal
 */
@Stateless(name="SaleChannelInstallerBusinessBeanLocal",mappedName="ejb/SaleChannelInstallerBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChannelInstallerBusinessBean extends BusinessBase implements SaleChannelInstallerBusinessBeanLocal {

    @EJB(name="SaleChannelInstallerDAOLocal", beanInterface=SaleChannelInstallerDAOLocal.class)
    private SaleChannelInstallerDAOLocal daoSaleChannelInstaller;
    
    private final static Logger log = UtilsBusiness.getLog4J(SaleChannelInstallerBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal#getAllSaleChannelInstallers()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChannelInstallerVO> getAllSaleChannelInstallers() throws BusinessException {
        log.debug("== Inicia getAllSaleChannelInstallers/SaleChannelInstallerBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSaleChannelInstaller.getAllSaleChannelInstallers(), SaleChannelInstallerVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSaleChannelInstallers/SaleChannelInstallerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSaleChannelInstallers/SaleChannelInstallerBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal#getSaleChannelInstallersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChannelInstallerVO getSaleChannelInstallerByID(Long id) throws BusinessException {
        log.debug("== Inicia getSaleChannelInstallerByID/SaleChannelInstallerBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChannelInstaller objPojo = daoSaleChannelInstaller.getSaleChannelInstallerByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SaleChannelInstallerVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSaleChannelInstallerByID/SaleChannelInstallerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSaleChannelInstallerByID/SaleChannelInstallerBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal#createSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
        log.debug("== Inicia createSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SaleChannelInstaller objPojo =  UtilsBusiness.copyObject(SaleChannelInstaller.class, obj);
            daoSaleChannelInstaller.createSaleChannelInstaller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal#updateSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
        log.debug("== Inicia updateSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SaleChannelInstaller objPojo =  UtilsBusiness.copyObject(SaleChannelInstaller.class, obj);
            daoSaleChannelInstaller.updateSaleChannelInstaller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SaleChannelInstallerBusinessBeanLocal#deleteSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
        log.debug("== Inicia deleteSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SaleChannelInstaller objPojo =  UtilsBusiness.copyObject(SaleChannelInstaller.class, obj);
            daoSaleChannelInstaller.deleteSaleChannelInstaller(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChannelInstaller/SaleChannelInstallerBusinessBean ==");
        }
	}
}
