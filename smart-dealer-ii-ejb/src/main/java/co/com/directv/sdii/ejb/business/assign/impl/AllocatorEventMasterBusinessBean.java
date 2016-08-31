/**
 * 
 */
package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.Date;

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
import co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal;
import co.com.directv.sdii.exceptions.BaseException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllocatorEventMaster;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.persistence.dao.allocator.AllocatorEventMasterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Encapsula la lógica de negocio relacionada con la información de cargue de work orders 
 * 
 * Fecha de Creación: 29/03/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="AllocatorEventMasterBusinessLocal",mappedName="ejb/AllocatorEventMasterBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllocatorEventMasterBusinessBean extends BusinessBase implements AllocatorEventMasterBusinessLocal {

	@EJB(name="AllocatorEventMasterDAOLocal",beanInterface=AllocatorEventMasterDAOLocal.class)
	private AllocatorEventMasterDAOLocal allocatorEventMasterDao;
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
	private final static Logger log = UtilsBusiness.getLog4J(AllocatorEventMasterBusinessBean.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal#createAllocatorEventMaster(co.com.directv.sdii.model.vo.AllocatorEventMasterVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AllocatorEventMasterVO createAllocatorEventMaster(AllocatorEventMasterVO allocatorEventMaster) throws BusinessException {
		log.debug("== Inicia createAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		try {
			if(! BusinessRuleValidationManager.getInstance().isValid(allocatorEventMaster)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			AllocatorEventMaster allocatorEventMasterPojo = UtilsBusiness.copyObject(AllocatorEventMaster.class, allocatorEventMaster);
			allocatorEventMasterDao.createAllocatorEventMaster(allocatorEventMasterPojo);
			AllocatorEventMasterVO result = UtilsBusiness.copyObject(AllocatorEventMasterVO.class, allocatorEventMasterPojo);
			return result;
		} catch (Throwable ex) {
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal#createAllocatorEventMaster(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AllocatorEventMasterVO createAllocatorEventMaster(Long woCount,Long countryId) throws BusinessException {
		log.debug("== Inicia createAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		try {
			Long id = allocatorEventMasterDao.getMaxAllocatorEventMaster();
			AllocatorEventMaster allocatorEventMasterPojo = new AllocatorEventMaster(id,new Date(), woCount, countryId);
			allocatorEventMasterDao.createAllocatorEventMaster(allocatorEventMasterPojo);
			AllocatorEventMasterVO result = UtilsBusiness.copyObject(AllocatorEventMasterVO.class, allocatorEventMasterPojo);
			return result;
		} catch (Throwable ex) {
			if (ex instanceof  DAOServiceException || ex instanceof  DAOSQLException || ex instanceof  BusinessException){
				String codeError = ErrorBusinessMessages.CONSTRAINT_VIOLATION.getCode();
				String messageCode = ((BaseException)ex).getMessageCode();
				if(messageCode.equals(codeError)){
					throw new BusinessException(ErrorBusinessMessages.CONSTRAINT_VIOLATION.getCode()," ");
				}
			}
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal#updateAllocatorEventMaster(co.com.directv.sdii.model.vo.AllocatorEventMasterVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AllocatorEventMasterVO updateAllocatorEventMaster(AllocatorEventMasterVO allocatorEventMaster) throws BusinessException {
		log.debug("== Inicia updateAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		try {
			if(! BusinessRuleValidationManager.getInstance().isValid(allocatorEventMaster)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			AllocatorEventMaster allocatorEventMasterPojo = UtilsBusiness.copyObject(AllocatorEventMaster.class, allocatorEventMaster);
			allocatorEventMasterDao.updateAllocatorEventMaster(allocatorEventMasterPojo);
			AllocatorEventMasterVO result = UtilsBusiness.copyObject(AllocatorEventMasterVO.class, allocatorEventMasterPojo);
			return result;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion updateAllocatorEventMaster/AllocatorEventMasterBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal#getLastAllocatorEventMaster(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AllocatorEventMaster getLastAllocatorEventMaster(Long countryId) throws BusinessException {
		log.debug("== Inicia getLastAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		try {						
			AllocatorEventMaster allocatorEventMaster = allocatorEventMasterDao.getLastAllocatorEventMaster(countryId);
			return allocatorEventMaster;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion getLastAllocatorEventMaster/AllocatorEventMasterBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getLastAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AllocatorEventMasterBusinessLocal#getLastAllocatorEventMaster(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getMaxAllocatorEventMaster() throws BusinessException {
		log.debug("== Inicia getMaxAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		try {						
			return allocatorEventMasterDao.getMaxAllocatorEventMaster();
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion getMaxAllocatorEventMaster/AllocatorEventMasterBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getMaxAllocatorEventMaster/AllocatorEventMasterBusinessBean ==");
		}
	}
	
}
