package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeMediaContact
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeMediaContactCRUDBeanLocal {
	
	public void createEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public EmployeeMediaContactVO getEmployeeMediaContactByID(Long id) throws BusinessException;

	public void updateEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public void deleteEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public List<EmployeeMediaContactVO> getAllEmployeeMediaContact() throws BusinessException;

	public List<EmployeeMediaContactVO> getEmployeeMediaContactByEmployeeId(Long id) throws BusinessException;
}
