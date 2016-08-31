package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal;
import co.com.directv.sdii.model.vo.ShippingOrderStatusVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración Estados de Shipping Order.
 *
 * Caso de Uso CFG - 20 - Gestionar Códigos de Estado de Shipping Order
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigSOEstadosFacadeLocal",mappedName="ejb/ConfigSOEstadosFacadeLocal")
public class ConfigSOEstadosFacadeBean implements ConfigSOEstadosFacadeLocal {

    @EJB(name="ConfigSOEstadosBusinessLocal",beanInterface=ConfigSOEstadosBusinessLocal.class)
    private ConfigSOEstadosBusinessLocal business;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#getShippingOrderStatusByID(java.lang.Long)
     */
    public ShippingOrderStatusVO getShippingOrderStatusByID(Long id) throws BusinessException {
        return business.getShippingOrderStatusByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#getShippingOrderStatusByCode(java.lang.String)
     */
    public ShippingOrderStatusVO getShippingOrderStatusByCode(String code) throws BusinessException {
        return business.getShippingOrderStatusByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#getShippingOrderStatusByName(java.lang.String)
     */
    public List<ShippingOrderStatusVO> getShippingOrderStatusByName(String name) throws BusinessException {
        return business.getShippingOrderStatusByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#getAll()
     */
    public List<ShippingOrderStatusVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#createShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    public void createShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        business.createShippingOrderStatus(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#updateShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    public void updateShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        business.updateShippingOrderStatus(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOEstadosFacadeLocal#deleteShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    public void deleteShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        business.deleteShippingOrderStatus(obj);
    }

}
