package co.com.directv.sdii.ejb.business.stock.impl;

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
import co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ImpLogModification;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ImpLogModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal
 */
@Stateless(name="ImpLogModificationBusinessBeanLocal",mappedName="ejb/ImpLogModificationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogModificationBusinessBean extends BusinessBase implements ImpLogModificationBusinessBeanLocal {

    @EJB(name="ImpLogModificationDAOLocal", beanInterface=ImpLogModificationDAOLocal.class)
    private ImpLogModificationDAOLocal daoImpLogModification;
    
    @EJB(name="ImpLogModificationTypeDAOLocal", beanInterface=ImpLogModificationTypeDAOLocal.class)
	private ImpLogModificationTypeDAOLocal daoImpLogModType;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImpLogModificationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#getAllImpLogModifications()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ImpLogModificationVO> getAllImpLogModifications() throws BusinessException {
        log.debug("== Inicia getAllImpLogModifications/ImpLogModificationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImpLogModification.getAllImpLogModifications(), ImpLogModificationVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllImpLogModifications/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImpLogModifications/ImpLogModificationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#getImpLogModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ImpLogModificationVO getImpLogModificationByID(Long id) throws BusinessException {
        log.debug("== Inicia getImpLogModificationByID/ImpLogModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModification objPojo = daoImpLogModification.getImpLogModificationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ImpLogModificationVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImpLogModificationByID/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImpLogModificationByID/ImpLogModificationBusinessBean ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#getImpLogModificationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List <ImpLogModificationVO> getImpLogModificationByImportLogID(Long id) throws BusinessException {
        log.debug("== Inicia getImpLogModificationByImportLogID/ImpLogModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            return UtilsBusiness.convertList(daoImpLogModification.getImpLogModificationByImportLogID(id), ImpLogModificationVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImpLogModificationByImportLogID/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImpLogModificationByImportLogID/ImpLogModificationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#createImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImpLogModification(ImpLogModificationVO obj) throws BusinessException {
        log.debug("== Inicia createImpLogModification/ImpLogModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModification objPojo =  UtilsBusiness.copyObject(ImpLogModification.class, obj);
            daoImpLogModification.createImpLogModification(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImpLogModification/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogModification/ImpLogModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#updateImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImpLogModification(ImpLogModificationVO obj) throws BusinessException {
        log.debug("== Inicia updateImpLogModification/ImpLogModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModification objPojo =  UtilsBusiness.copyObject(ImpLogModification.class, obj);
            daoImpLogModification.updateImpLogModification(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateImpLogModification/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogModification/ImpLogModificationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal#deleteImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImpLogModification(ImpLogModificationVO obj) throws BusinessException {
        log.debug("== Inicia deleteImpLogModification/ImpLogModificationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModification objPojo =  UtilsBusiness.copyObject(ImpLogModification.class, obj);
            daoImpLogModification.deleteImpLogModification(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteImpLogModification/ImpLogModificationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogModification/ImpLogModificationBusinessBean ==");
        }
	}
	
	@Override
	public Long importLogStatusToImportLogModification(ImportLogStatus importLogStatus) throws BusinessException {
		log.debug("== Inicia ImportLogStatusToImportLogModification/ImpLogModificationBusinessBean ==");
		try {
			String estado = null;
			if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_CREATED.getCodeEntity() ) ){
				estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_TYPE_STATUS_CREATED_PROCESS.getCodeEntity();
			}else{
				if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_CREATED.getCodeEntity() ) ){
					estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_TYPE_STATUS_CREATED.getCodeEntity();
				}else{
					if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity() ) ){
						estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_STATUS_SENDED.getCodeEntity();
					}else{
						if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity() ) ){
							estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_PARTIALLY_CONFIRMED.getCodeEntity();
						}else{
							if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity() ) ){
								estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_RECEPTED.getCodeEntity();
							}else{
								if ( importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity() ) ){
									estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_INCONSISTENCY_PROCESS.getCodeEntity();
								}else{
									if (importLogStatus.getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_DELETED.getCodeEntity())){
										estado = CodesBusinessEntityEnum.IMPORT_LOG_MODIFICATION_INCONSISTENCY_DELETE.getCodeEntity();
									}
								}
							}
						}
					}
				}
			}
			ImpLogModificationType impLogModificationType = daoImpLogModType.getImpLogModificationTypeByCode(estado);
			return impLogModificationType.getId();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación ImportLogStatusToImportLogModification/ImpLogModificationBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina ImportLogStatusToImportLogModification/ImpLogModificationBusinessBean ==");
		}
	}
}
