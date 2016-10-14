package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Training;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Training
 * 
 * Fecha de Creación: Mar 4, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrainingDAOLocal {

	public void createTraining(Training obj) throws DAOServiceException,
			DAOSQLException;

	public Training getTrainingByID(Long id) throws DAOServiceException,
			DAOSQLException;

	public void updateTraining(Training obj) throws DAOServiceException,
			DAOSQLException;

	public void deleteTraining(Training obj) throws DAOServiceException,
			DAOSQLException;

	public List<Training> getAllTraining() throws DAOServiceException,
			DAOSQLException;
	
	public List<Training> getTrainingsByEmployeeId(Long idEmployee) throws DAOServiceException, DAOSQLException;
	
	public void deleteTrainingsByEmployeeId(Long employeeId) throws DAOServiceException, DAOSQLException;
}