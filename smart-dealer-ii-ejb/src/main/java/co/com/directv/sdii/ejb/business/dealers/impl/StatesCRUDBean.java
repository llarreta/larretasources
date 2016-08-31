package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.StatesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.StateVO;
import co.com.directv.sdii.persistence.dao.dealers.StatesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad States
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.StatesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.StatesCRUDBeanLocal
 * 
 */
@Stateless(name="StatesCRUDBeanLocal",mappedName="ejb/StatesCRUDBeanLocal")
public class StatesCRUDBean extends BusinessBase implements StatesCRUDBeanLocal {

	@EJB(name="StatesDAOLocal",beanInterface=StatesDAOLocal.class)
	private StatesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(StatesCRUDBean.class);

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<StateVO> getAllStates() throws BusinessException {

        log.debug("== Inicia getAllStates/StatesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getAllStates(), StateVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllStates/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllStates/StatesCRUDBean ==");
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
	public StateVO getStatesByCode(String code) throws BusinessException {
        log.debug("== Inicia getStatesByCode/StatesCRUDBean ==");
        try {
            return UtilsBusiness.copyObject(StateVO.class, dao.getStatesByCode(code));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getStatesByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getStatesByCode/StatesCRUDBean ==");
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
	public StateVO getStatesByID(Long id) throws BusinessException {
        log.debug("== Inicia getStatesByCode/StatesCRUDBean ==");
        try {
            return UtilsBusiness.copyObject(StateVO.class, dao.getStatesByID(id));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getStatesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getStatesByCode/StatesCRUDBean ==");
        }
	}

    public List<StateVO> getStatesByCountryID(Long countryId) throws BusinessException {
        log.debug("== Inicia getStatesByCountryID/StatesCRUDBean ==");
        
        try {
            return UtilsBusiness.convertList(dao.getStatesByCountryID(countryId), StateVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getStatesByCountryID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
        	log.debug("== Termina getStatesByCountryID/StatesCRUDBean ==");
        }
    }
}
