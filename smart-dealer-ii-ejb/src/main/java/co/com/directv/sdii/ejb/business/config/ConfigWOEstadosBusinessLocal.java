package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Estados de la WO.
 *
 * Caso de Uso CFG - 04 - Gestionar Códigos de Estado de las Work Orders
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigWOEstadosBusinessLocal {

    /**
     * Este método retorna una instancia de WORKORDER_STATUS por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public WorkorderStatusVO getWorkorderStatusByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de WORKORDER_STATUS por WO_STATUS_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public WorkorderStatusVO getWorkorderStatusByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de WORKORDER_STATUS por WO_STATUS_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<WorkorderStatusVO> getWorkorderStatusByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de WORKORDER_STATUS por IBS_6_STATUS.
     *
     * @param status
     * @return
     * @throws BusinessException
     */
    public List<WorkorderStatusVO> getWorkorderStatusByIBS6Status(Ibs6StatusVO status) throws BusinessException;

    
    /**
     * Este método retorna una lista de Todas los WORKORDER_STATUS.
     *
     * @return
     * @throws BusinessException
     */
    public List<WorkorderStatusVO> getAll() throws BusinessException;

    /**
     * Este método crea un WORKORDER_STATUS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createWorkorderStatus(WorkorderStatusVO obj) throws BusinessException;

    /**
     * Este método actualiza un WORKORDER_STATUS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateWorkorderStatus(WorkorderStatusVO obj) throws BusinessException;

    /**
     * Este método elimina un WORKORDER_STATUS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteWorkorderStatus(WorkorderStatusVO obj) throws BusinessException;
    
    /**
     * Este método retorna una lista de Todas los WORKORDER_STATUS para la lista de work orders.
     *
     * @return
     * @throws BusinessException
     * @author jnova
     */
    public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray() throws BusinessException;
    
    /**
     * Este método retorna una lista de Todas los WORKORDER_STATUS para modificar work orders.
     *
     * @return
     * @throws BusinessException
     * @author jnova
     */
    public List<WorkorderStatusVO> getAllWorkOrdersToAsign() throws BusinessException;
}
