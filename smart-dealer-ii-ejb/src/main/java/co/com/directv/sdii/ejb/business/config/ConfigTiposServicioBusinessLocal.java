package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceStatusVO;
import co.com.directv.sdii.model.vo.ServiceVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Tipos de Servicio.
 *
 * En las consultas de SERVICES se deben recuperar las referencias a SERVICE_STATUS,
 * SERVICE_CATEGORY y las colecciones de REQUIRED_CAPACITY_SERVICE, REQUIRED_SERVICE_ELEMENTS
 *
 * Caso de Uso CFG - 03 - Gestionar Códigos y Tipos Servicios
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigTiposServicioBusinessLocal {

    /**
     * Este método retorna una instancia de SERVICES por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public ServiceVO getServiceByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de SERVICES por SERVICE_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public ServiceVO getServiceByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de SERVICES por SERVICE_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<ServiceVO> getServiceByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los SERVICES.
     *
     * @return
     * @throws BusinessException
     */
    public List<ServiceVO> getAll() throws BusinessException;

    /**
     * Este método retorna una lista de Todos los servicios de tipo apertura.
     * @param serviceCodeOpening
     * @return
     * @throws BusinessException
     */
    public List<ServiceVO> getAllByServiceCodeOpening(boolean serviceCodeOpening) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los SERVICES prestados por un DEALER.
     *
     * @param dealer
     * @return
     * @throws BusinessException
     */
    public List<ServiceVO> getServicesByDealer(DealerVO dealer) throws BusinessException;

    /**
     * Este método crea un SERVICES.
     * @param obj
     * @param userId
     * @throws BusinessException
     */
    public void createService(ServiceVO obj, Long userId) throws BusinessException;

    /**
     * Este método actualiza un SERVICES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateService(ServiceVO obj) throws BusinessException;

    /**
     * Este método elimina un SERVICES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteService(ServiceVO obj) throws BusinessException;

    /**
     * 
     * Metodo: <Descripcion>
     * @return
     * @throws BusinessException 
     * @author
     */
    public List<ServiceStatusVO> getAllServicesStatus()throws BusinessException;
    
    /**
     * @return
     * @throws BusinessException
     */
    public List<ServiceVO> getActiveServices() throws BusinessException;
    
}
