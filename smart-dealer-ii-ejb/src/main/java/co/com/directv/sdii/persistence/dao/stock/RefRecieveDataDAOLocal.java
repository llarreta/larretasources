package co.com.directv.sdii.persistence.dao.stock;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RefRecieveData;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefRecieveData 
 * 
 * Fecha de Creación: 18/08/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefRecieveDataDAOLocal {

	/**
	 * 
	 * Metodo: Consulta el dato de ingreso de una remision
	 * @param referenceId identificador de la remision
	 * @return RefRecieveData dato de ingreso de la remision enviada por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public RefRecieveData getRefRecieveDataByReferenceId(Long referenceId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Crea un dato de ingreso 
	 * @param obj dato de ingreso a crear
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @return RefRecieveData objeto creado
	 * @author jnova
	 */
	public RefRecieveData createRefRecieveData(RefRecieveData obj) throws DAOServiceException, DAOSQLException;
}
