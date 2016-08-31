package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.SaleChannelSeller;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad SaleChannelSeller
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChannelSellerDAOLocal {

	/**
	 * Metodo:  persiste la información de un SaleChannelSeller
	 * @param obj objeto que encapsula la información de un SaleChannelSeller
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un SaleChannelSeller
	 * @param obj objeto que encapsula la información de un SaleChannelSeller
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SaleChannelSeller
	 * @param obj información del SaleChannelSeller a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un SaleChannelSeller por su identificador
	 * @param id identificador del SaleChannelSeller a ser consultado
	 * @return objeto con la información del SaleChannelSeller dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SaleChannelSeller getSaleChannelSellerByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los SaleChannelSeller almacenados en la persistencia
	 * @return Lista con los SaleChannelSeller existentes, una lista vacia en caso que no existan SaleChannelSeller en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SaleChannelSeller> getAllSaleChannelSellers() throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta los canales en estado normal asociados a un dealer en un pais
	 * @param sellerCode Codigo del dealer vendedor
	 * @param countryId pais por el que se desea filtrar
	 * @return Lista de canales activos asociados a la compañia
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Dealer> getInstallerDealersBySellerCodeAndCountry(Long sellerCode , Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Borra los registros de vendedores de un canal de venta
	 * @param saleChannelId identificador del canal de venta
	 * @throws DAOServiceException en caso de error al tratar de borrar los registros
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public void deleteSellersBySaleChannelId(Long saleChannelId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la lista de vendedores de un canal de venta dado su identificador
	 * @param saleChannelId identificador del canal de venta
	 * @return lista de los vendedores de un canal de venta
	 * @throws DAOServiceException en caso de error al consultar la información de los vendedores
	 * @throws DAOSQLException en caso de error al consultar la información de los vendedores
	 * @author jjimenezh
	 */
	public List<SaleChannelSeller> getSaleChannelSellersBySaleChannelId(Long saleChannelId)throws DAOServiceException, DAOSQLException;


}