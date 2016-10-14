package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Dealers
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeMediaContactFacadeBeanLocal {
	
	public void createEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public EmployeeMediaContactVO getEmployeeMediaContactByID(Long id) throws BusinessException;

	public void updateEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public void deleteEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException;

	public List<EmployeeMediaContactVO> getAllEmployeeMediaContact() throws BusinessException;
}
