package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal;
import co.com.directv.sdii.model.vo.ResponsibleAreaVO;
import co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO;
import co.com.directv.sdii.model.vo.WorkorderReasonTypeVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Reasons de Ordenes de Trabajo.
 *
 * Caso de Uso CFG - 05 - Gestionar Reasons en las Work Orders
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigWOReasonsFacadeLocal",mappedName="ejb/ConfigWOReasonsFacadeLocal")
public class ConfigWOReasonsFacadeBean implements ConfigWOReasonsFacadeLocal {

    @EJB(name="ConfigWOReasonsBusinessLocal",beanInterface=ConfigWOReasonsBusinessLocal.class)
    private ConfigWOReasonsBusinessLocal business;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByID(java.lang.Long)
     */
    public WorkorderReasonVO getWorkorderReasonByID(Long id) throws BusinessException {
        return business.getWorkorderReasonByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByCode(java.lang.String)
     */
    public WorkorderReasonVO getWorkorderReasonByCode(String code) throws BusinessException {
        return business.getWorkorderReasonByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByName(java.lang.String)
     */
    public List<WorkorderReasonVO> getWorkorderReasonByName(String name) throws BusinessException {
        return business.getWorkorderReasonByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByCategory(co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO)
     */
    public List<WorkorderReasonVO> getWorkorderReasonByCategory(WorkorderReasonCategoryVO category) throws BusinessException {
         return business.getWorkorderReasonByCategory(category);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByCategoryType(co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO, co.com.directv.sdii.model.vo.WorkorderReasonTypeVO)
     */
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryType(WorkorderReasonCategoryVO category, WorkorderReasonTypeVO type) throws BusinessException {
        return business.getWorkorderReasonByCategoryType(category, type);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getAll()
     */
    public List<WorkorderReasonVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#createWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    public void createWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        business.createWorkorderReason(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#updateWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    public void updateWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        business.updateWorkorderReason(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#deleteWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    public void deleteWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        business.deleteWorkorderReason(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getAllWOReasonTypes()
     */
    public List<WorkorderReasonTypeVO> getAllWOReasonTypes() throws BusinessException {
        List<WorkorderReasonTypeVO> result = business.getAllWOReasonTypes();
        return result;
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWOReasonCategoriesByReasonTypeId(java.lang.Long)
     */
    public List<WorkorderReasonCategoryVO> getWOReasonCategoriesByReasonTypeId(Long woReasonTypeId) throws BusinessException {
        List<WorkorderReasonCategoryVO> result = business.getWOReasonCategoriesByReasonTypeId(woReasonTypeId);
        return result;
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getAllResponsibleAreas()
     */
    public List<ResponsibleAreaVO> getAllResponsibleAreas() throws BusinessException {
        List<ResponsibleAreaVO> result = business.getAllResponsibleAreas();
        return result;
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOReasonsFacadeLocal#getWorkorderReasonByCategoryCode()
     */
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryCode(String categoryCode) throws BusinessException {
        return business.getWorkorderReasonByCategoryCode(categoryCode);
    }

}
