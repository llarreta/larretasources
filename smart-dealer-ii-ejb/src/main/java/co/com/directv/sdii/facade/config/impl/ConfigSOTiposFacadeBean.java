package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal;
import co.com.directv.sdii.model.vo.ShippingOrderTypeVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración Tipos de Shipping Order.
 *
 * Caso de Uso CFG - 19 - Gestionar Códigos de Tipos de Shipping Order
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigSOTiposFacadeLocal",mappedName="ejb/ConfigSOTiposFacadeLocal")
public class ConfigSOTiposFacadeBean implements ConfigSOTiposFacadeLocal {

    @EJB(name="ConfigSOTiposBusinessLocal",beanInterface=ConfigSOTiposBusinessLocal.class)
    private ConfigSOTiposBusinessLocal business;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#getShippingOrderTypeByID(java.lang.Long)
     */
    public ShippingOrderTypeVO getShippingOrderTypeByID(Long id) throws BusinessException {
        return business.getShippingOrderTypeByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#getShippingOrderTypeByCode(java.lang.String)
     */
    public ShippingOrderTypeVO getShippingOrderTypeByCode(String code) throws BusinessException {
        return business.getShippingOrderTypeByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#getShippingOrderTypeByName(java.lang.String)
     */
    public List<ShippingOrderTypeVO> getShippingOrderTypeByName(String name) throws BusinessException {
        return business.getShippingOrderTypeByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#getAll()
     */
    public List<ShippingOrderTypeVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#createShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    public void createShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        business.createShippingOrderType(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#updateShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    public void updateShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        business.updateShippingOrderType(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigSOTiposFacadeLocal#deleteShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    public void deleteShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        business.deleteShippingOrderType(obj);
    }

}
