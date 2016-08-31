package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Estados de Ordenes de Trabajo.
 *
 * Caso de Uso CFG - 04 - Gestionar Códigos de Estado de las Work Orders
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal
 */
@Stateless(name="ConfigWOEstadosFacadeLocal",mappedName="ejb/ConfigWOEstadosFacadeLocal")
public class ConfigWOEstadosFacadeBean implements ConfigWOEstadosFacadeLocal {

    @EJB(name="ConfigWOEstadosBusinessLocal",beanInterface=ConfigWOEstadosBusinessLocal.class)
    private ConfigWOEstadosBusinessLocal business;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getWorkorderStatusByID(java.lang.Long)
     */
    public WorkorderStatusVO getWorkorderStatusByID(Long id) throws BusinessException {
        return business.getWorkorderStatusByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getWorkorderStatusByCode(java.lang.String)
     */
    public WorkorderStatusVO getWorkorderStatusByCode(String code) throws BusinessException {
        return business.getWorkorderStatusByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getWorkorderStatusByName(java.lang.String)
     */
    public List<WorkorderStatusVO> getWorkorderStatusByName(String name) throws BusinessException {
        return business.getWorkorderStatusByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getWorkorderStatusByIBS6Status(co.com.directv.sdii.model.vo.Ibs6StatusVO)
     */
    public List<WorkorderStatusVO> getWorkorderStatusByIBS6Status(Ibs6StatusVO status) throws BusinessException {
        return business.getWorkorderStatusByIBS6Status(status);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getAll()
     */
    public List<WorkorderStatusVO> getAll() throws BusinessException {
       return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#createWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    public void createWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
        business.createWorkorderStatus(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#updateWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    public void updateWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
        business.updateWorkorderStatus(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#deleteWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    public void deleteWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
        business.deleteWorkorderStatus(obj);
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getAllWorkOrderList()
     */
    public List<WorkorderStatusVO> getAllWorkOrderList() throws BusinessException {
       return business.getWorkOrderStatusForDealerTray();
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigWOEstadosFacadeLocal#getAllWorkOrdersToAsign()
     */
    public List<WorkorderStatusVO> getAllWorkOrdersToAsign() throws BusinessException {
       return business.getAllWorkOrdersToAsign();
    }
 
}
