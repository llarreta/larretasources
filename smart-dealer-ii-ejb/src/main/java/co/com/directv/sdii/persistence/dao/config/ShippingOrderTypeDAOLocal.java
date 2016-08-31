package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ShippingOrderType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderTypeDAOLocal {
	
    /**
     * Crea SHIPPING_ORDER_TYPE
     * @param obj - ShippingOrderType
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void createShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException;

    /** Consulta SHIPPING_ORDER_TYPE por ID
     * @param id - Long
     * @return ShippingOrderType
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public ShippingOrderType getShippingOrderTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza SHIPPING_ORDER_TYPE
     * @param obj - ShippingOrderType
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void updateShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina SHIPPING_ORDER_TYPE
     * @param obj - ShippingOrderType
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void deleteShippingOrderType(ShippingOrderType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta todos los SHIPPING_ORDER_TYPE
     * @return List<ShippingOrderType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<ShippingOrderType> getAll() throws DAOServiceException, DAOSQLException;

    /**
     * Consulta SHIPPING_ORDER_TYPE por CODe
     * @param code - String
     * @return ShippingOrderType
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public ShippingOrderType getShippingOrderTypeByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Consulta SHIPPING_ORDER_TYPE por NAME
     * @param name - String
     * @return List<ShippingOrderType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<ShippingOrderType> getShippingOrderTypeByName(String name) throws DAOServiceException, DAOSQLException;

}
