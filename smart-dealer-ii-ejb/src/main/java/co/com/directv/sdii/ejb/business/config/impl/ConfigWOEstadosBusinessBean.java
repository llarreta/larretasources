package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

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
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal
 */
@Stateless(name="ConfigWOEstadosBusinessLocal",mappedName="ejb/ConfigWOEstadosBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigWOEstadosBusinessBean extends BusinessBase implements ConfigWOEstadosBusinessLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ConfigWOEstadosBusinessBean.class);
    
    @EJB(name="WorkorderStatusDAOLocal",beanInterface=WorkorderStatusDAOLocal.class)
    private WorkorderStatusDAOLocal serviceWoSDAO;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getWorkorderStatusByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderStatusVO getWorkorderStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getWorkorderStatusByID/ConfigJornadasBusinessBean ==");
        try {
            if (id == null) {
                log.debug("== Error Parametro id con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            WorkorderStatus sh = serviceWoSDAO.getWorkorderStatusByID(id);
            if (sh == null) {
                return null;
            }

            WorkorderStatusVO shVO = UtilsBusiness.copyObject(WorkorderStatusVO.class, sh);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getWorkorderStatusByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByID/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getWorkorderStatusByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkorderStatusVO getWorkorderStatusByCode(String code) throws BusinessException {
        log.debug("== Inicia getWorkorderStatusByCode/ConfigJornadasBusinessBean ==");
        try {
            if (code == null) {
                log.debug("== Error Parametro code con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            WorkorderStatus sh = serviceWoSDAO.getWorkorderStatusByCode(code);
            if (sh == null) {
                return null;
            }

            WorkorderStatusVO shVO = UtilsBusiness.copyObject(WorkorderStatusVO.class, sh);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getWorkorderStatusByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByCode/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getWorkorderStatusByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatusVO> getWorkorderStatusByName(String name) throws BusinessException {
        log.debug("== Inicia getWorkorderStatusByName/ConfigJornadasBusinessBean ==");
        try {
            if (name == null) {
                log.debug("== Error Parametro name con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            List<WorkorderStatus> sh = serviceWoSDAO.getWorkorderStatusByName(name);
            if (sh == null) {
                return null;
            }

            List<WorkorderStatusVO> shVO = UtilsBusiness.convertList(sh, WorkorderStatusVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getWorkorderStatusByName");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByName/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getWorkorderStatusByIBS6Status(co.com.directv.sdii.model.vo.Ibs6StatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatusVO> getWorkorderStatusByIBS6Status(Ibs6StatusVO status) throws BusinessException {
       log.debug("== Inicia getWorkorderStatusByIBS6Status/ConfigJornadasBusinessBean ==");
        try {
            if (status == null) {
                log.debug("== Error Parametro status con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            List<WorkorderStatus> sh = serviceWoSDAO.getWorkorderStatusByIBS6Status(UtilsBusiness.copyObject(Ibs6Status.class, status));
            if (sh == null) {
                return null;
            }

            List<WorkorderStatusVO> shVO = UtilsBusiness.convertList(sh, WorkorderStatusVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getWorkorderStatusByIBS6Status");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByIBS6Status/ConfigJornadasBusinessBean ==");
        }
    }   

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatusVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigJornadasBusinessBean ==");
        try {
            List<WorkorderStatus> sh = serviceWoSDAO.getAll();
            if (sh == null) {
                return null;
            }

            List<WorkorderStatusVO> shVO = UtilsBusiness.convertList(sh, WorkorderStatusVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#createWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
        log.debug("== Inicia createWorkorderStatus/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }
            
            WorkorderStatus woStatus = serviceWoSDAO.getWorkorderStatusByCode(obj.getWoStateCode());
            
            if(woStatus != null){
            	log.error("== Error en la Capa de Negocio debido: se trató de crear un estado de WorkOrder con un código que ya existe ==");
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }

           serviceWoSDAO.createWorkorderStatus(UtilsBusiness.copyObject(WorkorderStatus.class, obj));

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/createWorkorderStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderStatus/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#updateWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateWorkorderStatus/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            WorkorderStatus woStatus = serviceWoSDAO.getWorkorderStatusByCode(obj.getWoStateCode());
            
            if(woStatus != null && (woStatus.getId().longValue() != obj.getId().longValue())){
            	log.error("== Error en la Capa de Negocio debido: se trató de actualizar un estado de WorkOrder asignando el código de otra que ya existe ==");
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }
           woStatus = UtilsBusiness.copyObject(WorkorderStatus.class, obj);
           serviceWoSDAO.updateWorkorderStatus(woStatus);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/updateWorkorderStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkorderStatus/ConfigJornadasBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#deleteWorkorderStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderStatus(WorkorderStatusVO obj) throws BusinessException {
       log.debug("== Inicia deleteWorkorderStatus/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

           serviceWoSDAO.deleteWorkorderStatus(UtilsBusiness.copyObject(WorkorderStatus.class, obj));

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/deleteWorkorderStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkorderStatus/ConfigJornadasBusinessBean ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getWorkOrderStatusForDealerTray()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray() throws BusinessException {
        log.debug("== Inicia getWorkOrderStatusForDealerTray/ConfigJornadasBusinessBean ==");
        try {
            List<WorkorderStatus> sh = serviceWoSDAO.getWorkOrderStatusForDealerTray();
            if (sh == null) {
                return null;
            }

            List<WorkorderStatusVO> shVO = UtilsBusiness.convertList(sh, WorkorderStatusVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getWorkOrderStatusForDealerTray");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderStatusForDealerTray/ConfigJornadasBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOEstadosBusinessLocal#getAllWorkOrdersToAsign()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkorderStatusVO> getAllWorkOrdersToAsign() throws BusinessException {
        log.debug("== Inicia getAllWorkOrdersToAsign/ConfigJornadasBusinessBean ==");
        try {
            List<WorkorderStatus> sh = serviceWoSDAO.getAllWorkOrdersToAsign();
            if (sh == null) {
                return null;
            }

            List<WorkorderStatusVO> shVO = UtilsBusiness.convertList(sh, WorkorderStatusVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOEstadosBusinessBean/getAllWorkOrdersToAsign");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllWorkOrdersToAsign/ConfigJornadasBusinessBean ==");
        }
    }
}
