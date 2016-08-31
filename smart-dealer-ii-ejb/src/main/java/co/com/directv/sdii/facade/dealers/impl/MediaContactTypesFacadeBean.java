package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.MediaContactTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MediaContactTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad MediaContactTypes 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.MediaContactTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal
 */
@Stateless(name="MediaContactTypesFacadeBeanLocal",mappedName="ejb/MediaContactTypesFacadeBeanLocal")
public class MediaContactTypesFacadeBean implements MediaContactTypesFacadeBeanLocal {
	
	@EJB(name="MediaContactTypesCRUDBeanLocal",beanInterface=MediaContactTypesCRUDBeanLocal.class)
	private MediaContactTypesCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal#getAllMediaContactTypes()
	 */
	public List<MediaContactTypeVO> getAllMediaContactTypes() throws BusinessException {
		return business.getAllMediaContactTypes();
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.MediaContactTypesFacadeBeanLocal#getMediaContactTypesByID(java.lang.Long)
	 */
	public MediaContactTypeVO getMediaContactTypesByID(Long id) throws BusinessException {
		return business.getMediaContactTypesByID(id);
	}
		
}
