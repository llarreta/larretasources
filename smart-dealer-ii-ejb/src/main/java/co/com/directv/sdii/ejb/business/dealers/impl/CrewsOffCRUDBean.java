package co.com.directv.sdii.ejb.business.dealers.impl;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.CrewsOffCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.model.vo.CrewOffVO;
import co.com.directv.sdii.persistence.dao.dealers.CrewsOffDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad CrewOff
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsOffDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.CrewsOffCRUDBeanLocal
 * 
 */
@Stateless(name="CrewsOffCRUDBeanLocal",mappedName="ejb/CrewsOffCRUDBeanLocal")
public class CrewsOffCRUDBean extends BusinessBase implements CrewsOffCRUDBeanLocal {

	@EJB(name="CrewsOffDAOLocal",beanInterface=CrewsOffDAOLocal.class)
	private CrewsOffDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(CrewsOffCRUDBean.class);

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void createCrewOff(CrewOffVO obj) throws BusinessException {
		log.debug("== Termina createCrewOff/CrewStatusCRUDBean ==");
		try {
			CrewOff crewOff = UtilsBusiness.copyObject(CrewOff.class, obj);
            dao.createCrewOff(crewOff);
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCrewOff/CrewStatusCRUDBean ==");
        }
		
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void deleteCrewOff(CrewOffVO obj) throws BusinessException {
		log.debug("== Termina deleteCrewOff/CrewStatusCRUDBean ==");
		try {
			CrewOff crewOff = UtilsBusiness.copyObject(CrewOff.class, obj);
            dao.deleteCrewOff(crewOff);
            
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCrewOff/CrewStatusCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<CrewOffVO> getAllCrewOff() throws BusinessException {
		log.debug("== Termina getAllCrewOff/CrewStatusCRUDBean ==");
		try {
			List<CrewOff> listaOrigen = dao.getAllCrewOff();
            List<CrewOffVO> result = UtilsBusiness.convertList(listaOrigen, CrewOffVO.class);
            return result;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCrewOff/CrewStatusCRUDBean ==");
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
	public CrewOffVO getCrewOffByID(Long id) throws BusinessException {
		log.debug("== Termina getCrewOffByID/CrewStatusCRUDBean ==");
		try {
			CrewOff crewOff = dao.getCrewOffByID(id);
			if(crewOff == null){
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			CrewOffVO result = UtilsBusiness.copyObject(CrewOffVO.class, crewOff);
            return result;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCrewOffByID/CrewStatusCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void updateCrewOff(CrewOffVO obj) throws BusinessException {
		log.debug("== Termina updateCrewOff/CrewStatusCRUDBean ==");
		try {
			CrewOff crewOff = UtilsBusiness.copyObject(CrewOff.class, obj);
            dao.updateCrewOff(crewOff);
            
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateCrewOff/CrewStatusCRUDBean ==");
        }
	}
}
