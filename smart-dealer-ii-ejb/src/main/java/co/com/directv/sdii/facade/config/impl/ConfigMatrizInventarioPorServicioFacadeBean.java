package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigMatrizInventarioPorServicioBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.ServiceVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Matriz de Inventario por Servicio.
 *
 * Caso de Uso CFG - 16 - Administrar Matriz de Iventario Requerido por Servicio
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigMatrizInventarioPorServicioFacadeLocal",mappedName="ejb/ConfigMatrizInventarioPorServicioFacadeLocal")
public class ConfigMatrizInventarioPorServicioFacadeBean implements ConfigMatrizInventarioPorServicioFacadeLocal {

    @EJB(name="ConfigMatrizInventarioPorServicioBusinessLocal",beanInterface=ConfigMatrizInventarioPorServicioBusinessLocal.class)
    private ConfigMatrizInventarioPorServicioBusinessLocal business;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getRequiredServiceElementByID(co.com.directv.sdii.model.pojo.RequiredServiceElementId)
     */
    public RequiredServiceElementVO getRequiredServiceElementByID(RequiredServiceElementId id) throws BusinessException {
        return business.getRequiredServiceElementByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getRequiredServiceElementByService(co.com.directv.sdii.model.vo.ServiceVO)
     */
    public List<RequiredServiceElementVO> getRequiredServiceElementByService(ServiceVO service) throws BusinessException {
        return business.getRequiredServiceElementByService(service);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getRequiredServiceElementByElementType(co.com.directv.sdii.model.vo.ElementTypeVO)
     */
    public List<RequiredServiceElementVO> getRequiredServiceElementByElementType(ElementTypeVO elementType) throws BusinessException {
        return business.getRequiredServiceElementByElementType(elementType);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getRequiredServiceElementByServiceElementType(co.com.directv.sdii.model.vo.ServiceVO, co.com.directv.sdii.model.vo.ElementTypeVO)
     */
    public RequiredServiceElementVO getRequiredServiceElementByServiceElementType(ServiceVO service, ElementTypeVO elementType) throws BusinessException {
        return business.getRequiredServiceElementByServiceElementType(service, elementType);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getAll()
     */
    public List<RequiredServiceElementVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#createRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    public void createRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        business.createRequiredServiceElement(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#updateRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    public void updateRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        business.updateRequiredServiceElement(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#deleteRequiredServiceElement(co.com.directv.sdii.model.vo.RequiredServiceElementVO)
     */
    public void deleteRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException {
        business.deleteRequiredServiceElement(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getElementTypeByID(java.lang.Long)
     */
    public ElementTypeVO getElementTypeByID(Long id) throws BusinessException {
        return business.getElementTypeByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getServiceByID(java.lang.Long)
     */
    public ServiceVO getServiceByID(Long id) throws BusinessException {
        return business.getServiceByID(id);
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigMatrizInventarioPorServicioFacadeLocal#getRequiredServiceElementsByServiceIdOnlySelectedElements(java.lang.Long)
	 */
	@Override
	public List<RequiredServiceElementVO> getRequiredServiceElementsByServiceIdOnlySelectedElements(
			Long serviceId) throws BusinessException {
		return business.getRequiredServiceElementsByServiceIdOnlySelectedElements(serviceId);
	}
 
}
