package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ShippingOrderStatusVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Estados de SO.
 *
 * Caso de Uso CFG - 20 - Gestionar Códigos de Estado de Shipping Order
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigSOEstadosBusinessLocal {

    /**
     * Este método retorna una instancia de SHIPPING_ORDER_STATUS por ID.
     *
     * @param id - Long
     * @return ShippingOrderStatusVO
     * @throws BusinessException
     * @author gfandino
     */
    public ShippingOrderStatusVO getShippingOrderStatusByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de SHIPPING_ORDER_STATUS por SO_STATUS_CODE.
     *
     * @param code - String
     * @return ShippingOrderStatusVO
     * @throws BusinessException
     * @author gfandino
     */
    public ShippingOrderStatusVO getShippingOrderStatusByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de SHIPPING_ORDER_STATUS por SO_STATUS_NAME.
     *
     * @param name - String
     * @return List<ShippingOrderStatusVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<ShippingOrderStatusVO> getShippingOrderStatusByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los SHIPPING_ORDER_STATUS.
     *
     * @return List<ShippingOrderStatusVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<ShippingOrderStatusVO> getAll() throws BusinessException;

    /**
     * Este método crea un SHIPPING_ORDER_STATUS.
     *
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException;

    /**
     * Este método actualiza una SHIPPING_ORDER_STATUS.
     *
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException
     * @author gfandino
     */
    public void updateShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException;

    /**
     * Este método elimina un SHIPPING_ORDER_STATUS.
     *
     * @param obj - ShippingOrderStatusVO
     * @throws BusinessException
     * @author gfandino
     */
    public void deleteShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException;

}
