package co.com.directv.sdii.ejb.business.config.impl;

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
import co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ProgramStatus;
import co.com.directv.sdii.model.vo.ProgramStatusVO;
import co.com.directv.sdii.persistence.dao.config.ProgramStatusDAOLocal;

/**
 * Session Bean implementation class ProgramStatusCRUDBean
 */
@Stateless(name="ProgramStatusCRUDBeanLocal",mappedName="ejb/ProgramStatusCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProgramStatusCRUDBean extends BusinessBase implements ProgramStatusCRUDBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ProgramStatusCRUDBean.class);
	
	@EJB(beanInterface=ProgramStatusDAOLocal.class, name="ProgramStatusCRUDBeanLocal")
	private ProgramStatusDAOLocal statusDAO;
	
	/**
	 * @see co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createProgramStatus(ProgramStatusVO obj) throws BusinessException {
		log.debug("== Inicia createProgramStatus/ProgramStatusCRUDBean ==");
		try {
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    
			ProgramStatus programStatus = UtilsBusiness.copyObject(ProgramStatus.class, obj);
			statusDAO.createProgramStatus(programStatus);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ProgramStatusCRUDBean/createProgramStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createProgramStatus/ProgramStatusCRUDBean ==");
        }
	}

	/**
	 * @see co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProgramStatus(ProgramStatusVO obj) throws BusinessException {
		log.debug("== Inicia deleteProgramStatus/ProgramStatusCRUDBean ==");
		try {
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    
			ProgramStatus programStatus = UtilsBusiness.copyObject(ProgramStatus.class, obj);
			statusDAO.deleteProgramStatus(programStatus);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ProgramStatusCRUDBean/deleteProgramStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteProgramStatus/ProgramStatusCRUDBean ==");
        }
	}

	/**
	 * @see co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramStatusVO> getAll() throws BusinessException {
		log.debug("== Inicia getAll/ProgramStatusCRUDBean ==");
		try {
			List<ProgramStatus> listStatus = statusDAO.getAll();
			
			if(listStatus == null)
				return null;
			
			return UtilsBusiness.convertList(listStatus, ProgramStatusVO.class);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ProgramStatusCRUDBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ProgramStatusCRUDBean ==");
        }
	}

	/**
	 * @see co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProgramStatusVO getProgramStatusByID(Long id) throws BusinessException {
		
		log.debug("== Inicia getProgramStatusByID/ProgramStatusCRUDBean ==");
		try {
			
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			ProgramStatus status = statusDAO.getProgramStatusByID(id);
			
			if(status == null)
				return null;
			
			return UtilsBusiness.copyObject(ProgramStatusVO.class, status);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ProgramStatusCRUDBean/getProgramStatusByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getProgramStatusByID/ProgramStatusCRUDBean ==");
        }
	}

	/**
	 * @see co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateProgramStatus(ProgramStatusVO obj) throws BusinessException {
		log.debug("== Inicia updateProgramStatus/ProgramStatusCRUDBean ==");
		try {
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    
			ProgramStatus programStatus = UtilsBusiness.copyObject(ProgramStatus.class, obj);
			statusDAO.updateProgramStatus(programStatus);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ProgramStatusCRUDBean/updateProgramStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateProgramStatus/ProgramStatusCRUDBean ==");
        }	
	}
}
