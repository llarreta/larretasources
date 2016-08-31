package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad EmployeeStatus.
 * Solo operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeStatusCRUDBeanLocal {

    public List<EmployeeStatusVO> getAllEmployeeStatus() throws BusinessException;

    public EmployeeStatusVO getEmployeeStatusById(Long id) throws BusinessException;
}
