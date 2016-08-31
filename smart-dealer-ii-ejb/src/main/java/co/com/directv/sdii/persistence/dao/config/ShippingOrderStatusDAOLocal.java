package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ShippingOrderStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@Local
public interface ShippingOrderStatusDAOLocal {
	
    /**
     * Crea SHIPPING_ORDER_STATUS
     * @param obj - ShippingOrderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void createShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta SHIPPING_ORDER_STATUS por ID
     * @param id - Long
     * @return ShippingOrderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public ShippingOrderStatus getShippingOrderStatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza SHIPPING_ORDER_STATUS
     * @param obj - ShippingOrderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void updateShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina SHIPPING_ORDER_STATUS
     * @param obj - ShippingOrderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void deleteShippingOrderStatus(ShippingOrderStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta todas las SHIPPING_ORDER_STATUS
     * @return List<ShippingOrderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<ShippingOrderStatus> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Consulta SHIPPING_ORDER_STATUS por CODE
     * @param code - String
     * @return ShippingOrderStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public ShippingOrderStatus getShippingOrderStatusByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta SHIPPING_ORDER_STATUS por NAME
     * @param name - String
     * @return List<ShippingOrderStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<ShippingOrderStatus> getShippingOrderStatusByName(String name) throws DAOServiceException, DAOSQLException;

}
