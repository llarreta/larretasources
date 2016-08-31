package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeRolVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad EmployeeRol.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeRolCRUDBeanLocal {

	public EmployeeRolVO getEmployeeRolByCode(String code) throws BusinessException;
	
	public EmployeeRolVO getEmployeeRolByID(Long id) throws BusinessException;
	
	public List<EmployeeRolVO> getAllEmployeeRol() throws BusinessException;
}
