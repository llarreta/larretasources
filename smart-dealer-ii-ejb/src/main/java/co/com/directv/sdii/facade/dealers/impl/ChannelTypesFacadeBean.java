package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ChannelTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ChannelTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ChannelTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad ChannelTypes 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.ChannelTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ChannelTypesFacadeBeanLocal
 */
@Stateless(name="ChannelTypesFacadeBeanLocal",mappedName="ejb/ChannelTypesFacadeBeanLocal")
public class ChannelTypesFacadeBean implements ChannelTypesFacadeBeanLocal {
	
	@SuppressWarnings("unused")
	@EJB(name="ChannelTypesCRUDBeanLocal",beanInterface=ChannelTypesCRUDBeanLocal.class)
	private ChannelTypesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ChannelTypeVO> getAllChannelTypes() throws BusinessException {
		return null;
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ChannelTypeVO getChannelTypesByCode(String code) throws BusinessException {
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
	public ChannelTypeVO getChannelTypesByID(Long id) throws BusinessException {
		return null;
	}
		

}
