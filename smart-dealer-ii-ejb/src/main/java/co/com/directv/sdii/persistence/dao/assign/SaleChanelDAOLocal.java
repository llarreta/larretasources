package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SaleChanel;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SaleChanel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChanelDAOLocal {

	/**
	 * Metodo:  persiste la información de un SaleChanel
	 * @param obj objeto que encapsula la información de un SaleChanel
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SaleChanel
	 * @param obj objeto que encapsula la información de un SaleChanel
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SaleChanel
	 * @param obj información del SaleChanel a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SaleChanel por su identificador
	 * @param id identificador del SaleChanel a ser consultado
	 * @return objeto con la información del SaleChanel dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SaleChanel getSaleChanelByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SaleChanel almacenados en la persistencia
	 * @return Lista con los SaleChanel existentes, una lista vacia en caso que no existan SaleChanel en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SaleChanel> getAllSaleChanels() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información paginada de los canales de venta configurados en la aplicación
	 * @param request información del request
	 * @param requestCollInfoPojo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public SaleChannelResponse getSaleChannelsByFilters(GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfo requestCollInfoPojo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta un canal de venta dado su código
	 * @param code código del canal de venta
	 * @return canal de venta con el código especificado
	 * @throws DAOServiceException en caso de error al consultar el canal de venta
	 * @throws DAOSQLException en caso de error al consultar el canal de venta
	 * @author jjimenezh
	 */
	public SaleChanel getSaleChanelByCode(String code)throws DAOServiceException, DAOSQLException;


}