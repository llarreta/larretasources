package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WoType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoTypeDAOLocal {
	
	/**
     * Crea una WoType en el sistema
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWoType(WoType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un wotype con el id especificado
     * @param id - Long
     * @return - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WoType getWoTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un wotype especificado
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateWoType(WoType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un wotype del sistema
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWoType(WoType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los wotypes del sistema
     * @return - List<WoType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WoType> getAll() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una work order type dado el código único
	 * @param woTypeCode código único de la work order type
	 * @return Datos de la workorder
	 * @author jjimenezh
	 */
	public WoType getWoTypeByCode(String woTypeCode)throws DAOServiceException, DAOSQLException;

}
