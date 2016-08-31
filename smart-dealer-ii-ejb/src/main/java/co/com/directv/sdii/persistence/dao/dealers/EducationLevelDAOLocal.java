package co.com.directv.sdii.persistence.dao.dealers;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EducationLevel;

@Local
public interface EducationLevelDAOLocal {

	/**
	 * Metodo: Obtiene todos los niveles de educacion
	 * @return Lista de niveles de educacion
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 */
	public List<EducationLevel> getAllEducationLevel() throws DAOServiceException, DAOSQLException;
}
