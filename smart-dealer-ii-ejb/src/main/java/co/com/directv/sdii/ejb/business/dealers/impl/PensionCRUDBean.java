package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.PensionCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.PensionVO;
import co.com.directv.sdii.persistence.dao.dealers.PensionDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad Pension
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.PensionDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.PensionCRUDBeanLocal
 * 
 */
@Stateless(name="PensionCRUDBeanLocal",mappedName="ejb/PensionCRUDBeanLocal")
public class PensionCRUDBean extends BusinessBase implements PensionCRUDBeanLocal {

	@EJB(name="PensionDAOLocal",beanInterface=PensionDAOLocal.class)
	private PensionDAOLocal dao;

         private final static Logger log = UtilsBusiness.getLog4J(PensionCRUDBean.class);

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<PensionVO> getAllPension() throws BusinessException {
        log.debug("== Inicia getAllPension/PensionCRUDBean ==");
        try {
           return  UtilsBusiness.convertList(dao.getAllPension(), PensionVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPension/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPension/PensionCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PensionVO getPensionByCode(String code) throws BusinessException {
        log.debug("== Inicia getPensionByCode/PensionCRUDBean ==");
        try {
           return UtilsBusiness.copyObject(PensionVO.class,dao.getPensionByCode(code));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPensionByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPensionByCode/PensionCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PensionVO getPensionByID(Long id) throws BusinessException {
        log.debug("== Inicia getPensionByID/PensionCRUDBean ==");
        try {
           return UtilsBusiness.copyObject(PensionVO.class,dao.getPensionByID(id));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPensionByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPensionByID/PensionCRUDBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.PensionCRUDBeanLocal#getAllPensionByCountryId(java.lang.Long)
	 */
	public List<PensionVO> getAllPensionByCountryId(Long countryId)	throws BusinessException {
		log.debug("== Inicia getAllPensionByCountryId/PensionCRUDBean ==");
        try {
           return  UtilsBusiness.convertList(dao.getAllPensionByCountryId(countryId), PensionVO.class);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPensionByCountryId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPensionByCountryId/PensionCRUDBean ==");
        }
	}
}
