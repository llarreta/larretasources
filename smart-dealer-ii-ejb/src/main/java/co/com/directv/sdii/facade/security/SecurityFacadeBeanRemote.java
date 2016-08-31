package co.com.directv.sdii.facade.security;

import javax.ejb.Remote;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 7/10/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Remote
public interface SecurityFacadeBeanRemote {

	/**
	 * Método que permite consultar un usuario por ID.
	 * @param id
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public UserVO getUserById(Long id) throws BusinessException;
		
}
