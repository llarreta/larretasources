package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrder;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ShippingOrder
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderDAOLocal {
	
	/**
     * Crea una ShippingOrder en el sistema
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un shippingorder con el id especificado
     * @param id - Long
     * @return - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ShippingOrder getShippingOrderByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un shippingorder especificado
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un shippingorder del sistema
     * @param obj - ShippingOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteShippingOrder(ShippingOrder obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los shippingorders del sistema
     * @return - List<ShippingOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ShippingOrder> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de shippingorders por el work order id
     * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
     * @param woId
     * @return List<ShippingOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ShippingOrder> getShippingOrdersByWorkOrder(Long woId) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Metodo: Consulta una shipping order con el codigo, codigo de
     * work order y pais indicados
     * @param shipOrderID Codigo de la shipping order
     * @param woCode Codigo de la work order
     * @param countryCode Codigo del pais
     * @return ShippingOrder Datos de la ShippingOrder consultada
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jforero 09/08/2010
     */
    public ShippingOrder getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Metodo: Consulta una shipping order con el codigo de
     * work order y pais indicados
     * @param woCode Codigo de la work order
     * @param countryCode Codigo del pais
     * @return ShippingOrder Datos de la ShippingOrder consultada
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jforero 11/08/2010
     */
    public List<ShippingOrder> getShippingOrderByWOCodeAndCountry(String woCode, String countryCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Se borra la información de una shipping order asociada a una work order
	 * @param workOrderId identificador de la work order a borrar
	 * @throws DAOServiceException en caso de error al realizar el borrado
	 * @throws DAOSQLException En caso de error al realizar el borrado
	 * @author jjimenezh
	 */
	public void deleteShippingOrderByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException;

}
