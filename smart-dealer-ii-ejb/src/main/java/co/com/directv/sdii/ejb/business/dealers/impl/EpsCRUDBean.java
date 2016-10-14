package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EpsCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Eps;
import co.com.directv.sdii.model.vo.EpsVO;
import co.com.directv.sdii.persistence.dao.dealers.EpsDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad Eps
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EpsDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.EpsCRUDBeanLocal
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EpsCRUDBean extends BusinessBase implements EpsCRUDBeanLocal {

	@EJB(name="EpsDAOLocal",beanInterface=EpsDAOLocal.class)
	private EpsDAOLocal epsDAO;

	private final static Logger log = UtilsBusiness.getLog4J(EpsCRUDBean.class);

	/**
	 * Obtiene todas las EPSs
	 * @return List<EpsVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EpsVO> getAllEps() throws BusinessException {
		log.debug("== Inicia getAllEmployee/EmployeeCRUDBean ==");
		try {
			return  UtilsBusiness.convertList(epsDAO.getAllEps(), EpsVO.class);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEps/EpsCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllEps/EpsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Obtiene una EPS por su ID
	 * @param code
	 * @return EpsVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EpsVO getEpsByCode(String code) throws BusinessException {
		log.debug("== Inicia getEpsByCode/EmployeeCRUDBean ==");
		try {
			return UtilsBusiness.copyObject(EpsVO.class,epsDAO.getEpsByCode(code));
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEpsByCode/EpsCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getEpsByCode/EpsCRUDBean ==");
		}
	}

	/**
	 * Obtiene una EPS por su ID
	 * @param id
	 * @return EpsVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EpsVO getEpsByID(Long id) throws BusinessException {
		log.debug("== Inicia getEpsByID/EmployeeCRUDBean ==");
		try {
			return UtilsBusiness.copyObject(EpsVO.class,epsDAO.getEpsByID(id));
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEpsByID/EpsCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getEpsByID/EpsCRUDBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.EpsCRUDBeanLocal#getAllEpsByCountryId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EpsVO> getAllEpsByCountryId(Long countryId) throws BusinessException {
		log.debug("== Inicia getAllEpsByCountryId/EmployeeCRUDBean ==");
		try {
			List<Eps> resultPojo =  epsDAO.getAllEpsByCountryId(countryId);
			List<EpsVO> result = UtilsBusiness.convertList(resultPojo, EpsVO.class);
			return result;
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEpsByCountryId/EpsCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllEpsByCountryId/EpsCRUDBean ==");
		}
	}

}
