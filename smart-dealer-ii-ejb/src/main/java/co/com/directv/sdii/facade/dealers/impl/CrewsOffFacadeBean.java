package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CrewsOffCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CrewsOffFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CrewOffVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad CrewOff 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CrewsOffCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewsOffFacadeBeanLocal
 */
@Stateless(name="CrewsOffFacadeBeanLocal",mappedName="ejb/CrewsOffFacadeBeanLocal")
public class CrewsOffFacadeBean implements CrewsOffFacadeBeanLocal {
	
	@SuppressWarnings("unused")
	@EJB(name="CrewsOffCRUDBeanLocal",beanInterface=CrewsOffCRUDBeanLocal.class)
	private CrewsOffCRUDBeanLocal business;
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void createCrewOff(CrewOffVO obj) throws BusinessException {
		
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void deleteCrewOff(CrewOffVO obj) throws BusinessException {
		
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<CrewOffVO> getAllCrewOff() throws BusinessException {
		return null;
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
		return null;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void updateCrewOff(CrewOffVO obj) throws BusinessException {
		
	}
}
