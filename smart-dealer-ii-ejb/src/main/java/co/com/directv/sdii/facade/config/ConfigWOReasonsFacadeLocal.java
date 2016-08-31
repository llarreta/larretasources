package co.com.directv.sdii.facade.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ResponsibleAreaVO;
import co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO;
import co.com.directv.sdii.model.vo.WorkorderReasonTypeVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Reasons de WO.
 *
 * Caso de Uso CFG - 05 - Gestionar Reasons en las Work Orders
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigWOReasonsFacadeLocal {

    /**
     * Este método retorna una instancia de WORKORDER_REASONS por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public WorkorderReasonVO getWorkorderReasonByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de WORKORDER_REASONS por WO_REASON_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public WorkorderReasonVO getWorkorderReasonByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de WORKORDER_REASONS por WO_REASON_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonVO> getWorkorderReasonByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de WORKORDER_REASONS por WORKORDER_REASON_CATEGORY.
     *
     * @param category
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonVO> getWorkorderReasonByCategory(WorkorderReasonCategoryVO category) throws BusinessException;

    /**
     * Este método retorna una lista de WORKORDER_REASONS por WORKORDER_REASON_CATEGORY y WORKORDER_REASON_TYPE.
     *
     * @param category
     * @param type
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryType(WorkorderReasonCategoryVO category, WorkorderReasonTypeVO type) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los WORKORDER_REASONS.
     *
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonVO> getAll() throws BusinessException;

    /**
     * Este método crea un WORKORDER_REASONS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createWorkorderReason(WorkorderReasonVO obj) throws BusinessException;

    /**
     * Este método actualiza un WORKORDER_REASONS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateWorkorderReason(WorkorderReasonVO obj) throws BusinessException;

    /**
     * Este método elimina un WORKORDER_REASONS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteWorkorderReason(WorkorderReasonVO obj) throws BusinessException;

    /**
     * 
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonTypeVO> getAllWOReasonTypes()throws BusinessException;

    /**
     * 
     * @param woReasonTypeId
     * @return
     * @throws BusinessException
     */
    public List<WorkorderReasonCategoryVO> getWOReasonCategoriesByReasonTypeId(Long woReasonTypeId)throws BusinessException;

    /**
     * 
     * @return
     * @throws BusinessException
     */
    public List<ResponsibleAreaVO> getAllResponsibleAreas()throws BusinessException;
    
}
