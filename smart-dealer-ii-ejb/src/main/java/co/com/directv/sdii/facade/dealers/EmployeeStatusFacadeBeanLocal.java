package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeStatusVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de EmployeeStatus.
 * Solo operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeStatusFacadeBeanLocal {

	/**
     * 
     * Metodo: Obtiene la lista de los posibles estados para los empleados
     * @return lista de los posibles estados para los empleados
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
    public List<EmployeeStatusVO> getAllEmployeeStatus() throws BusinessException;

    public EmployeeStatusVO getEmployeeStatusById(Long id) throws BusinessException;
}
