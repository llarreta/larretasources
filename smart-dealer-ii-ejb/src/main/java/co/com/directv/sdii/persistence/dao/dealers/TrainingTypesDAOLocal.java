package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TrainingType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad TrainingTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrainingTypesDAOLocal {

	
	public TrainingType getTrainingTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public TrainingType getTrainingTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<TrainingType> getAllTrainingTypes() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene una lista de los tipos de entrenamiento o capacitaciones que se ofrecen por país
	 * @param countryId identificador del país
	 * @return lista con los tipos de entrenamiento que los dealers ofrecen a sus empleados
	 * @throws BusinessException en caso de error al tratar de obtener los tipos de entrenamiento
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
	public List<TrainingType> getAllTrainingTypesByCountryId(Long countryId)throws DAOServiceException, DAOSQLException;

}