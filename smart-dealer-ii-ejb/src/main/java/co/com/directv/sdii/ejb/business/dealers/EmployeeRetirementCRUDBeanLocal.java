package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeeRetirement
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeRetirementCRUDBeanLocal {

	public void createEmployeeRetirement(EmployeeRetirementVO obj, Long employeeStatusId) throws BusinessException;


	public EmployeeRetirementVO getEmployeeRetirementByID(Long id) throws BusinessException;
	
	
	public void updateEmployeeRetirement(EmployeeRetirementVO obj) throws BusinessException;
	
	
	public void deleteEmployeeRetirement(EmployeeRetirementVO obj) throws BusinessException;
	
	
	public List<EmployeeRetirementVO> getAllEmployeeRetirement()  throws BusinessException;
}
