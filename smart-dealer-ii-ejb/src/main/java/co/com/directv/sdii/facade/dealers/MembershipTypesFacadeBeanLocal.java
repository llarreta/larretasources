package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MembershipTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de MembershipTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MembershipTypesFacadeBeanLocal {

	public MembershipTypeVO getMembershipTypesByCode(String code) throws BusinessException;
	
	public MembershipTypeVO getMembershipTypesByID(Long id) throws BusinessException;
	
	public List<MembershipTypeVO> getAllMembershipTypes() throws BusinessException;
}
