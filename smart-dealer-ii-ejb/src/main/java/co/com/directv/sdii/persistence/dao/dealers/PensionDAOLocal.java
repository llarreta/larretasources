package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Pension;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Pension
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PensionDAOLocal {

	
	public Pension getPensionByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public Pension getPensionByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<Pension> getAllPension() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene las pensiones dado el identificador del país
	 * @param countryId identificador del país
	 * @return lista con objetos de pensión por el país especificado
	 * @throws BusinessException en caso de error al tratar de consultar las pensiones
	 * regionalizadas
	 * @author jjimenezh Se agrega por control de cambios 2010-04-23
	 */
	public List<Pension> getAllPensionByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

}