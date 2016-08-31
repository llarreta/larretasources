package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.TrainingTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad TrainingTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrainingTypesCRUDBeanLocal {

	public TrainingTypeVO getTrainingTypesByCode(String code) throws BusinessException;
	
	public TrainingTypeVO getTrainingTypesByID(Long id) throws BusinessException;
	
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
