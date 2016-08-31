package co.com.directv.sdii.persistence.dao.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TypeServiceWorkOrders;

/**
 * 
 * DAO encargado de operaciones sobre la entidad TypeServiceWorkOrders 
 * 
 * Fecha de Creación: 9/05/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TypeServiceWorkOrdersDAOLocal{
	
	/**
	 * 
	 * Metodo: consulta todos los tipos de servicios asociados a tipos de de WO 
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<TypeServiceWorkOrders> getAllTypeServiceWorkOrders() throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene los tipos de servicios asociados a un tipo de WO a partir del id del tipo de servicio y id del tipo de WO
	 * @param serviceTypeId Id del tipo de servicio
	 * @param woTypeId Id del tipo de WO
	 * @return TypeServiceWorkOrders Relacion entre tipo de servicio y tipo de wo
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public TypeServiceWorkOrders getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId(Long serviceTypeId, Long woTypeId) throws DAOServiceException, DAOSQLException;

}
