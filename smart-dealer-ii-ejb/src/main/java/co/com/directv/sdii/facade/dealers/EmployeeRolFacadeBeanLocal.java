package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeRolVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de EmployeeRol
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeRolFacadeBeanLocal {

	/**
     * 
     * Metodo: Obtiene la información del rol de un empleado
     * @param code código del rol del empleado
     * @return información del rol de un empleado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public EmployeeRolVO getEmployeeRolByCode(String code) throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene la información de un rol por identificador
     * @param id identificador del rol a consultar
     * @return información del rol consultado, nulo en caso que no se encuentre
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public EmployeeRolVO getEmployeeRolByID(Long id) throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene todos los roles de empleados registrados en el sistema
     * @return lista con la información de los roles
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<EmployeeRolVO> getAllEmployeeRol() throws BusinessException;
}
