package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.TrainingType;
import co.com.directv.sdii.model.vo.TrainingTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de TrainingTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrainingTypesFacadeBeanLocal {

	/**
     * 
     * Metodo: Obtiene una lista de tipos de entrenamiento por código
     * @param code código de los tipos de entrenamiento
     * @return tipo de entrenamiento cuyo código coincide con el especificado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public TrainingType getTrainingTypesByCode(String code) throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene un tipo de entrenamiento por tipo de identificador
     * @param id identificador del tipo de entrenamiento
     * @return tipo de entrenamiento
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public TrainingType getTrainingTypesByID(Long id) throws BusinessException;
	
	/**
     * 
     * Metodo: Obtiene una lista con todos los tipos de entrenamiento del sistema
     * @return lista con los tipos de entrenamiento que existen en el sistema
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jalopez
     */
	public List<TrainingTypeVO> getAllTrainingTypes() throws BusinessException;
	
	/**
	 * Obtiene una lista de los tipos de entrenamiento o capacitaciones que se ofrecen por país
	 * @param countryId identificador del país
	 * @return lista con los tipos de entrenamiento que los dealers ofrecen a sus empleados
	 * @throws BusinessException en caso de error al tratar de obtener los tipos de entrenamiento
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
	public List<TrainingTypeVO> getAllTrainingTypesByCountryId(Long countryId) throws BusinessException;
}
