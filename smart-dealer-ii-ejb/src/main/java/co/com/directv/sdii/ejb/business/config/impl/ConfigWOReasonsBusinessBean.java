package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ResponsibleArea;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderReasonCategory;
import co.com.directv.sdii.model.pojo.WorkorderReasonType;
import co.com.directv.sdii.model.vo.ResponsibleAreaVO;
import co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO;
import co.com.directv.sdii.model.vo.WorkorderReasonTypeVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.persistence.dao.config.ResponsibleAreaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

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
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonTypeDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.WorkorderReasonCategoryDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.ResponsibleAreaDAOLocal
 */
@Stateless(name="ConfigWOReasonsBusinessLocal",mappedName="ejb/ConfigWOReasonsBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigWOReasonsBusinessBean extends BusinessBase implements ConfigWOReasonsBusinessLocal {

    @EJB(name="WorkorderReasonDAOLocal",beanInterface=WorkorderReasonDAOLocal.class)
    private WorkorderReasonDAOLocal workorderReasonDAO;

    @EJB(name="WorkorderReasonTypeDAOLocal",beanInterface=WorkorderReasonTypeDAOLocal.class)
    private WorkorderReasonTypeDAOLocal workorderReasonTypeDAO;

    @EJB(name="WorkorderReasonCategoryDAOLocal",beanInterface=WorkorderReasonCategoryDAOLocal.class)
    private WorkorderReasonCategoryDAOLocal workorderReasonCategoryDAO;

    @EJB(name="ResponsibleAreaDAOLocal",beanInterface=ResponsibleAreaDAOLocal.class)
    private ResponsibleAreaDAOLocal responsibleAreaDAO;

    private final static Logger log = UtilsBusiness.getLog4J(ConfigWOReasonsBusinessBean.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWorkorderReasonByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WorkorderReasonVO getWorkorderReasonByID(Long id) throws BusinessException {
        log.debug("== Inicia getWorkorderReasonByID/ConfigWOReasonsBusinessBean ==");
        try {

            WorkorderReason pojo = workorderReasonDAO.getWorkorderReasonByID(id);
            if(pojo == null){
                return null;
            }

            WorkorderReasonVO vo = UtilsBusiness.copyObject(WorkorderReasonVO.class, pojo);
            convertIsSolveByCiToPresentationLayer(vo);
            return vo;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByID/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWorkorderReasonByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WorkorderReasonVO getWorkorderReasonByCode(String code) throws BusinessException {
        log.debug("== Inicia getWorkorderReasonByCode/ConfigWOReasonsBusinessBean ==");
        try {

            WorkorderReason pojo = workorderReasonDAO.getWorkorderReasonByCode(code);
            if(pojo == null){
                return null;
            }

            WorkorderReasonVO vo = UtilsBusiness.copyObject(WorkorderReasonVO.class, pojo);
            convertIsSolveByCiToPresentationLayer(vo);
            return vo;
        }catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByCode/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWorkorderReasonByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WorkorderReasonVO> getWorkorderReasonByName(String name) throws BusinessException {
        log.debug("== Inicia getWorkorderReasonByName/ConfigWOReasonsBusinessBean ==");
        try {
            List<WorkorderReasonVO> voList = UtilsBusiness.convertList(workorderReasonDAO.getWorkorderReasonByName(name), WorkorderReasonVO.class);
            for( WorkorderReasonVO vo : voList ){
            	convertIsSolveByCiToPresentationLayer(vo);
            }
            return voList;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByName");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByName/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWorkorderReasonByCategory(co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WorkorderReasonVO> getWorkorderReasonByCategory(WorkorderReasonCategoryVO category) throws BusinessException {
        log.debug("== Inicia getWorkorderReasonByCategory/ConfigWOReasonsBusinessBean ==");
        try {
            WorkorderReasonCategory categoryPojo = UtilsBusiness.copyObject(WorkorderReasonCategory.class, category);
            List<WorkorderReasonVO> voList = UtilsBusiness.convertList(workorderReasonDAO.getWorkorderReasonByCategory(categoryPojo), WorkorderReasonVO.class);
            for( WorkorderReasonVO vo : voList ){
            	convertIsSolveByCiToPresentationLayer(vo);
            }
            return voList;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByCategory");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByCategory/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWorkorderReasonByCategoryType(co.com.directv.sdii.model.vo.WorkorderReasonCategoryVO, co.com.directv.sdii.model.vo.WorkorderReasonTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryType(WorkorderReasonCategoryVO category, WorkorderReasonTypeVO type) throws BusinessException {
        log.debug("== Inicia getWorkorderReasonByCategoryType/ConfigWOReasonsBusinessBean ==");
        try {
            WorkorderReasonCategory categoryPojo = UtilsBusiness.copyObject(WorkorderReasonCategory.class, category);
            WorkorderReasonType woReasonTypePojo = UtilsBusiness.copyObject(WorkorderReasonType.class, type);
            List<WorkorderReasonVO> voList = UtilsBusiness.convertList(workorderReasonDAO.getWorkorderReasonByCategoryType(categoryPojo, woReasonTypePojo), WorkorderReasonVO.class);
            for( WorkorderReasonVO vo : voList ){
            	convertIsSolveByCiToPresentationLayer(vo);
            }
            return voList;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByCategoryType");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByCategoryType/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WorkorderReasonVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigWOReasonsBusinessBean ==");
        try {
            List<WorkorderReasonVO> listVO = UtilsBusiness.convertList(workorderReasonDAO.getAll(), WorkorderReasonVO.class);
            for( WorkorderReasonVO vo : listVO ){
            	convertIsSolveByCiToPresentationLayer(vo);
            }
            return listVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#createWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        try {
            log.debug("== Inicia createWorkorderReason/ConfigWOReasonsBusinessBean ==");

            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear CrewById");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el WOReason, porque no se ha asignado uno o mas parámetros obligatorios");
            }

            WorkorderReason oldWor = workorderReasonDAO.getWorkorderReasonByCode(obj.getWorkorderReasonCode());

            if(oldWor != null){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio: ya existe una WOReason con ese código: "+obj.getWorkorderReasonCode()+" ==");
                throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode() , "No se puede crear el WOReason, porque ya existe una WOReason con ese código: " + obj.getWorkorderReasonCode());
            }
            convertIsSolveByCi(obj);
            WorkorderReason wor = UtilsBusiness.copyObject(WorkorderReason.class, obj);
            workorderReasonDAO.createWorkorderReason(wor);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/createWorkorderReason");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderReason/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#updateWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        try {
            log.debug("== Inicia createWorkorderReason/ConfigWOReasonsBusinessBean ==");

            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear el work order reason");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            convertIsSolveByCi(obj);
            WorkorderReason wor = UtilsBusiness.copyObject(WorkorderReason.class, obj);
            workorderReasonDAO.updateWorkorderReason(wor);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/updateWorkorderReason");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderReason/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#deleteWorkorderReason(co.com.directv.sdii.model.vo.WorkorderReasonVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkorderReason(WorkorderReasonVO obj) throws BusinessException {
        try {
            log.debug("== Inicia createWorkorderReason/ConfigWOReasonsBusinessBean ==");

            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear CrewById");
                throw new IllegalArgumentException("Parametro obj nulo. No se puede crear Crew");
            }
            WorkorderReason wor = UtilsBusiness.copyObject(WorkorderReason.class, obj);
            workorderReasonDAO.deleteWorkorderReason(wor);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/deleteWorkorderReason");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createWorkorderReason/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getAllWOReasonTypes()
     */
    public List<WorkorderReasonTypeVO> getAllWOReasonTypes() throws BusinessException {
        log.debug("== Inicia getAllWOReasonTypes/ConfigWOReasonsBusinessBean ==");
        try {
            List<WorkorderReasonType> listPojo = workorderReasonTypeDAO.getAll();
            List<WorkorderReasonTypeVO> listVO = UtilsBusiness.convertList(listPojo, WorkorderReasonTypeVO.class);
            
            return listVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWOReasonCategoriesByReasonTypeId");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllWOReasonTypes/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getWOReasonCategoriesByReasonTypeId(java.lang.Long)
     */
    public List<WorkorderReasonCategoryVO> getWOReasonCategoriesByReasonTypeId(Long woReasonTypeId) throws BusinessException {
        log.debug("== Inicia getWOReasonCategoriesByReasonTypeId/ConfigWOReasonsBusinessBean ==");
        try {
            List<WorkorderReasonCategory> listPojo = workorderReasonCategoryDAO.getWOReasonCategoriesByReasonTypeId(woReasonTypeId);
            List<WorkorderReasonCategoryVO> listVO = UtilsBusiness.convertList(listPojo, WorkorderReasonCategoryVO.class);

            return listVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWOReasonCategoriesByReasonTypeId");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWOReasonCategoriesByReasonTypeId/ConfigWOReasonsBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigWOReasonsBusinessLocal#getAllResponsibleAreas()
     */
    public List<ResponsibleAreaVO> getAllResponsibleAreas() throws BusinessException {
        log.debug("== Inicia getAllResponsibleAreas/ConfigWOReasonsBusinessBean ==");
        try {
            List<ResponsibleArea> listPojo = responsibleAreaDAO.getAll();
            List<ResponsibleAreaVO> listVO = UtilsBusiness.convertList(listPojo, ResponsibleAreaVO.class);

            return listVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getAllResponsibleAreas");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllResponsibleAreas/ConfigWOReasonsBusinessBean ==");
        }
    }
    
    /**
     * Metodo: convierte el parametro que viene de presentacion en caso que envien un valor que no debe
     * almacenarse en la tabla de reaons
     * @param obj
     * @throws PropertiesException
     */
    private void convertIsSolveByCi(WorkorderReasonVO obj) throws PropertiesException{
    	String isSolve = CodesBusinessEntityEnum.REASON_IS_SOLVE_BY_CI.getCodeEntity();
    	String isNotSolve = CodesBusinessEntityEnum.REASON_NOT_SOLVE_BY_CI.getCodeEntity();
    	if( obj != null && obj.getIsSolveByCi() != null 
    			&& !obj.getIsSolveByCi().equalsIgnoreCase( isSolve ) 
    			&& !obj.getIsSolveByCi().equalsIgnoreCase( isNotSolve ) ){
    		if( obj.getIsSolveByCi().equalsIgnoreCase( CodesBusinessEntityEnum.REASON_IS_SOLVE_BY_CI_NET.getCodeEntity() ) ){
    			obj.setIsSolveByCi( isSolve );
    		} else {
    			obj.setIsSolveByCi( isNotSolve );
    		}
    	}
    }
    
    
    /**
     * Metodo: Convierte los valores de la capa de negocio para la capa de presentacion
     * @param obj
     * @throws PropertiesException
     */
    private void convertIsSolveByCiToPresentationLayer(WorkorderReasonVO obj) throws PropertiesException{
    	if( obj != null && obj.getIsSolveByCi() != null ){
    		if( obj.getIsSolveByCi().equalsIgnoreCase( CodesBusinessEntityEnum.REASON_IS_SOLVE_BY_CI.getCodeEntity() ) ){
    			obj.setIsSolveByCi( CodesBusinessEntityEnum.REASON_IS_SOLVE_BY_CI_NET.getCodeEntity() );
    		} else {
    			obj.setIsSolveByCi( CodesBusinessEntityEnum.REASON_NOT_SOLVE_BY_CI_NET.getCodeEntity() );
    		}
    	}
    }
    
    public List<WorkorderReasonVO> getWorkorderReasonByCategoryCode(String categoryCode) throws BusinessException{
        log.debug("== Inicia getWorkorderReasonByCategoryCode/ConfigWOReasonsBusinessBean ==");
        try {

            List<WorkorderReasonVO> voList = UtilsBusiness.convertList(workorderReasonDAO.getWorkOrderReasonByCategoryCode(categoryCode), WorkorderReasonVO.class);           
            if(voList == null){
                return null;
            }
            
            return voList;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigWOReasonsBusinessBean/getWorkorderReasonByCategoryCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderReasonByCategoryCode/ConfigWOReasonsBusinessBean ==");
        }
    }

}
