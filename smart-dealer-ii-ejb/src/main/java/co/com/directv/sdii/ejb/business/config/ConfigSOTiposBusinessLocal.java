package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ShippingOrderTypeVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Tipos de Shipping Orders.
 *
 * Caso de Uso CFG - 19 - Gestionar Códigos de Tipos de Shipping Order
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigSOTiposBusinessLocal {
    
     /**
     * Este método retorna una instancia de SHIPPING_ORDER_TYPES por ID.
     *
     * @param id - Long
     * @return ShippingOrderTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public ShippingOrderTypeVO getShippingOrderTypeByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de SHIPPING_ORDER_TYPES por SO_TYPE_CODE.
     *
     * @param code - String
     * @return - ShippingOrderTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public ShippingOrderTypeVO getShippingOrderTypeByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de SHIPPING_ORDER_TYPES por SO_TYPE_NAME.
     *
     * @param name - String
     * @return List<ShippingOrderTypeVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<ShippingOrderTypeVO> getShippingOrderTypeByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los SHIPPING_ORDER_TYPES.
     *
     * @return List<ShippingOrderTypeVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<ShippingOrderTypeVO> getAll() throws BusinessException;

    /**
     * Este método crea un SHIPPING_ORDER_TYPES.
     *
     * @param obj - ShippingOrderTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException;

    /**
     * Este método actualiza una SHIPPING_ORDER_TYPES.
     *
     * @param obj - ShippingOrderTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public void updateShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException;

    /**
     * Este método elimina un SHIPPING_ORDER_TYPES.
     *
     * @param obj - ShippingOrderTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public void deleteShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException;

}
