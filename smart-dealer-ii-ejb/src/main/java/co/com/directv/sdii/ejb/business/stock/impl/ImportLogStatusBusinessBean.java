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
import co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;
import co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ImportLogStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal
 */
@Stateless(name="ImportLogStatusBusinessBeanLocal",mappedName="ejb/ImportLogStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogStatusBusinessBean extends BusinessBase implements ImportLogStatusBusinessBeanLocal {

    @EJB(name="ImportLogStatusDAOLocal", beanInterface=ImportLogStatusDAOLocal.class)
    private ImportLogStatusDAOLocal daoImportLogStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImportLogStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#getAllImportLogStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ImportLogStatusVO> getAllImportLogStatuss() throws BusinessException {
        log.debug("== Inicia getAllImportLogStatuss/ImportLogStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImportLogStatus.getAllImportLogStatuss(), ImportLogStatusVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllImportLogStatuss/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImportLogStatuss/ImportLogStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#getImportLogStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ImportLogStatusVO getImportLogStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getImportLogStatusByID/ImportLogStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogStatus objPojo = daoImportLogStatus.getImportLogStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ImportLogStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImportLogStatusByID/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogStatusByID/ImportLogStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#createImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
        log.debug("== Inicia createImportLogStatus/ImportLogStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(daoImportLogStatus.getImportLogStatusByCode(obj.getStatusCode())!=null){
        		UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
            ImportLogStatus objPojo =  UtilsBusiness.copyObject(ImportLogStatus.class, obj);
            daoImportLogStatus.createImportLogStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImportLogStatus/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogStatus/ImportLogStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#updateImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateImportLogStatus/ImportLogStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogStatus objPojo =  UtilsBusiness.copyObject(ImportLogStatus.class, obj);
            daoImportLogStatus.updateImportLogStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateImportLogStatus/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogStatus/ImportLogStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#deleteImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteImportLogStatus/ImportLogStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogStatus objPojo =  UtilsBusiness.copyObject(ImportLogStatus.class, obj);
            daoImportLogStatus.deleteImportLogStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteImportLogStatus/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogStatus/ImportLogStatusBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal#getImportLogStatusByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogStatusVO getImportLogStatusByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getImportLogStatusByCode/ImportLogStatusBusinessBean ==");
		log.debug("== code: == ["+code+"]");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogStatus objPojo = daoImportLogStatus.getImportLogStatusByCode(code);
            return UtilsBusiness.copyObject(ImportLogStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImportLogStatusByCode/ImportLogStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogStatusByCode/ImportLogStatusBusinessBean ==");
        }
	}
}
