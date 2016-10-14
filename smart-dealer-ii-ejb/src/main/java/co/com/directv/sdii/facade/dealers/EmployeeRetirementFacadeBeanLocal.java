package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;

/**
 * 
 *  Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de EmployeeRetirement 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeRetirementFacadeBeanLocal {

	/**
     * Metodo: Genera el retiro de un empleado
     * @param obj información del retiro del empleado
     * @param statusId identificador del estado del empleado
     * @throws BusinessException en caso de error al tratar de ejecutar la operación
     * @author jalopez
     */
	public void createEmployeeRetirement(EmployeeRetirementVO obj, long employeeStatusId) throws BusinessException;


	 /**
     * 
     * Metodo: Obtiene la información del retiro de un empleado por identificador
     * @param id identificador del registro de retiro del empleado
     * @return Información del retiro del empleado
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jalopez
     */
	public EmployeeRetirementVO getEmployeeRetirementByID(Long id) throws BusinessException;
	
	/**
     * Metodo: Actualiza la información del retiro de un empleado
     * @param obj objeto de información de retiro del empleado a ser actualizado
     * @throws BusinessException en caso de error 
     * @author jalopez
     */
	public void updateEmployeeRetirement(EmployeeRetirementVO obj) throws BusinessException;
	
	/**
     * Metodo: Borra la información del retiro de un empleado
     * @param obj información de retiro del empleado a ser borrada
     * @throws BusinessException en caso de error al borrar la información de retiro
     * @author jalopez
     */
	public void deleteEmployeeRetirement(EmployeeRetirementVO obj) throws BusinessException;
	
	/**
     * Metodo: Obtiene la información de todos los retiros de empleados en el sistema
     * @return lista con la información de todos los retiros de los empleados
     * @throws BusinessException en caso de error al consultar todos los retiros
     * @author jalopez
     */
	public List<EmployeeRetirementVO> getAllEmployeeRetirement()  throws BusinessException;
}
