package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.RequiredServiceElementId;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.ServiceVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Matriz de Inventario Requerido por Servicio.
 *
 * Caso de Uso CFG - 16 - Administrar Matriz de Iventario Requerido por Servicio
 *
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigMatrizInventarioPorServicioBusinessLocal {

    /**
     * Este método retorna una instancia de REQUIRED_SERVICE_ELEMENTS por ID.
     *
     * @param id - RequiredServiceElementId
     * @return RequiredServiceElementVO
     * @throws BusinessException
     * @author gfandino
     */
    public RequiredServiceElementVO getRequiredServiceElementByID(RequiredServiceElementId id) throws BusinessException;

    /**
     * Este método retorna una instancia de REQUIRED_SERVICE_ELEMENTS por SERVICE.
     *
     * @param service - ServiceVO
     * @return List<RequiredServiceElementVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<RequiredServiceElementVO> getRequiredServiceElementByService(ServiceVO service) throws BusinessException;
    
    /**
     * Este método retorna una instancia de REQUIRED_SERVICE_ELEMENTS por SERVICE.
     *
     * @param serviceId - ServiceVO, el id
     * @return List<RequiredServiceElementVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<RequiredServiceElementVO> getRequiredServiceElementsByServiceIdOnlySelectedElements(Long serviceId) throws BusinessException;

    /**
     * Este método retorna una lista de REQUIRED_SERVICE_ELEMENTS por ELEMENT_TYPE.
     *
     * @param elementType - ElementTypeVO
     * @return List<RequiredServiceElementVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<RequiredServiceElementVO> getRequiredServiceElementByElementType(ElementTypeVO elementType) throws BusinessException;

    /**
     * Este método retorna una lista de REQUIRED_SERVICE_ELEMENTS por SERVICE, ELEMENT_TYPE.
     *
     * @param service - ServiceVO
     * @param elementType - ElementTypeVO
     * @return RequiredServiceElementVO
     * @throws BusinessException
     * @author gfandino
     */
    public RequiredServiceElementVO getRequiredServiceElementByServiceElementType(ServiceVO service, ElementTypeVO elementType) throws BusinessException;

    /**
     * Este método retorna una lista de Todas las REQUIRED_SERVICE_ELEMENTS.
     *
     * @return - List<RequiredServiceElementVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<RequiredServiceElementVO> getAll() throws BusinessException;


    /**
     * Este método crea un REQUIRED_SERVICE_ELEMENTS.
     *
     * @param obj - RequiredServiceElementVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException;

    /**
     * Este método actualiza una REQUIRED_SERVICE_ELEMENTS.
     *
     * @param obj - RequiredServiceElementVO
     * @throws BusinessException
     * @author gfandino
     */
    public void updateRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException;

    /**
     * Este método elimina un REQUIRED_SERVICE_ELEMENTS.
     *
     * @param obj - RequiredServiceElementVO
     * @throws BusinessException
     * @author gfandino
     */
    public void deleteRequiredServiceElement(RequiredServiceElementVO obj) throws BusinessException;

     /**
     * Este método retorna una instancia de ElementType por ID.
     *
     * @param id - Long
     * @return ElementTypeVO
     * @throws BusinessException
     * @author gfandino
     */
    public ElementTypeVO getElementTypeByID(Long id) throws BusinessException;

     /**
     * Este método retorna una instancia de Service por ID.
     *
     * @param id - Long
     * @return ServiceVO
     * @throws BusinessException
     * @author gfandino
     */
    public ServiceVO getServiceByID(Long id) throws BusinessException;

}
