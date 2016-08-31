package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.TrainingVO;

/**
 *
 * Interfaz que define la Session Facade de las operaciones
 * a realizar para el modulo de Training
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Local
public interface TrainingFacadeBeanLocal {

	public void createTraining(TrainingVO obj) throws BusinessException;

	public TrainingVO getTrainingByID(Long id) throws BusinessException;

	public void updateTraining(TrainingVO obj) throws BusinessException;

	public void deleteTraining(TrainingVO obj) throws BusinessException;

	/**
     * 
     * Metodo: Obtiene la información de todos los tipos de entrenamiento
     * @return Lista con los entrenamientos para los empleados
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<TrainingVO> getAllTraining() throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene la información de capacitaciones para un empleado
     * @param idEmployee identificador del empleado al que se le consultará 
     * la información de capacitación
     * @return lista con los entrenamientos que aplican a ese empleado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<TrainingVO> getTrainingsByEmployeeId(Long idEmployee) throws BusinessException;
	
	public void deleteTrainingsByEmployeeId(Long employeeId) throws BusinessException;
}
