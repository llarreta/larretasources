package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ContactTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad ContactType.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactTypeCRUDBeanLocal {

	public ContactTypeVO getContactTypeByCode(String code) throws BusinessException;
	
	public ContactTypeVO getContactTypeByID(Long id) throws BusinessException;
	
	public List<ContactTypeVO> getAllContactType() throws BusinessException;
}
