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
import co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Skill;
import co.com.directv.sdii.model.pojo.SkillType;
import co.com.directv.sdii.model.vo.SkillTypeVO;
import co.com.directv.sdii.model.vo.SkillVO;
import co.com.directv.sdii.persistence.dao.config.SkillDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Habilidades.
 *
 * Caso de Uso CFG - 08 - Gestionar Habilidades Determinantes y Eliminantes
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal
 */
@Stateless(name="ConfigHabilidadesBusinessLocal",mappedName="ejb/ConfigHabilidadesBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigHabilidadesBusinessBean extends BusinessBase implements ConfigHabilidadesBusinessLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ConfigWOReasonsBusinessBean.class);
    
    @EJB(name="SkillDAOLocal",beanInterface=SkillDAOLocal.class)
    private SkillDAOLocal skillDAO;
    
    @EJB(name="SkillTypeDAOLocal",beanInterface=SkillTypeDAOLocal.class)
    private SkillTypeDAOLocal skillTypeDAO;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getSkillsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillVO getSkillsByID(Long id) throws BusinessException {
        log.debug("== Inicia getSkillsByID/ConfigHabilidadesBusinessBean ==");
        try {
            Skill skill =  skillDAO.getSkillByID(id);
            if(skill == null){
                return null;
            }
            SkillVO vo = UtilsBusiness.copyObject(SkillVO.class, skill);
            return vo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSkillsByID/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillsByID/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getSkillByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillVO getSkillByCode(String code) throws BusinessException {
        log.debug("== Inicia getSkillByCode/ConfigHabilidadesBusinessBean ==");
        try {
            Skill skill =  skillDAO.getSkillByCode(code);
            if(skill == null){
                return null;
            }
            SkillVO vo = UtilsBusiness.copyObject(SkillVO.class,skill);
            return vo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSkillByCode/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillByCode/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getSkillsByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillVO> getSkillsByName(String name) throws BusinessException {
        log.debug("== Inicia getSkillsByName/ConfigHabilidadesBusinessBean ==");
        try {
            List<Skill> skills =  skillDAO.getSkillByName(name);      
            List<SkillVO> vos = UtilsBusiness.convertList(skills, SkillVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSkillsByName/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillsByName/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getSkillsByType(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillVO> getSkillsByType(Long typeId) throws BusinessException {
        log.debug("== Inicia getSkillsByType/ConfigHabilidadesBusinessBean ==");
        try {
            List<Skill> skills =  skillDAO.getSkillByTypeId(typeId);
            List<SkillVO> vos = UtilsBusiness.convertList(skills, SkillVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getSkillsByType/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillsByType/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigHabilidadesBusinessBean ==");
        try {
            List<Skill> skills =  skillDAO.getAll();
            List<SkillVO> vos = UtilsBusiness.convertList(skills, SkillVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAll/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#createSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkill(SkillVO obj) throws BusinessException {
        log.debug("== Inicia createSkill/ConfigHabilidadesBusinessBean ==");
        try {

            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear Skill");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro obj nulo. No se puede crear Crew");
            }

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el Skill, porque no se ha asignado uno o mas parámetros obligatorios");
            }

            Skill oldSkill = skillDAO.getSkillByCode(obj.getSkillCode());

            if(oldSkill != null){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio: ya existe una habilidad con ese código: \""+obj.getSkillCode()+"\" ==");
                throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , "No se puede crear el Skill, porque ya existe una habilidad con ese código: " + obj.getSkillCode());
            }

            Skill skill = UtilsBusiness.copyObject(Skill.class, obj);
            skillDAO.createSkill(skill);
            
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createSkill/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkill/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#updateSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkill(SkillVO obj) throws BusinessException {
        log.debug("== Inicia updateSkill/ConfigHabilidadesBusinessBean ==");
        try {
            Skill skill = UtilsBusiness.copyObject(Skill.class, obj);
            skillDAO.updateSkill(skill);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateSkill/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkill/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#deleteSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkill(SkillVO obj) throws BusinessException {
        log.debug("== Inicia deleteSkill/ConfigHabilidadesBusinessBean ==");
        try {
            Skill skill = UtilsBusiness.copyObject(Skill.class, obj);
            skillDAO.deleteSkill(skill);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteSkill/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkill/ConfigHabilidadesBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getAllSkillTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SkillTypeVO> getAllSkillTypes() throws BusinessException {
		log.debug("== Inicia getAllSkillTypes/ConfigHabilidadesBusinessBean ==");
        try {
            List<SkillType> skillTypes =  skillTypeDAO.getAll();
            List<SkillTypeVO> vos = UtilsBusiness.convertList(skillTypes, SkillTypeVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllSkillTypes/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillTypes/ConfigHabilidadesBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getAllSkillTypesByCountryId(java.lang.Long)
	 */
	public List<SkillTypeVO> getAllSkillTypesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllSkillTypesByCountryId/ConfigHabilidadesBusinessBean ==");
        try {
            List<SkillType> skillTypes =  skillTypeDAO.getAllSkillTypesByCountryId(countryId);
            List<SkillTypeVO> vos = UtilsBusiness.convertList(skillTypes, SkillTypeVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllSkillTypesByCountryId/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillTypesByCountryId/ConfigHabilidadesBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getAllSkillsByCountryId(java.lang.Long)
	 */
	public List<SkillVO> getAllSkillsByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllSkillsByCountryId/ConfigHabilidadesBusinessBean ==");
        try {
            List<Skill> skills =  skillDAO.getAllSkillsByCountryId(countryId);
            List<SkillVO> vos = UtilsBusiness.convertList(skills, SkillVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllSkillsByCountryId/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillsByCountryId/ConfigHabilidadesBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal#getAllSkillsBySkillTypeAndCountryId(java.lang.Long, java.lang.Long)
	 */
	public List<SkillVO> getAllSkillsBySkillTypeAndCountryId(Long skillTypeId,
			Long countryId) throws BusinessException {
		log.debug("== Inicia getAllSkillsBySkillTypeAndCountryId/ConfigHabilidadesBusinessBean ==");
        try {
            List<Skill> skills =  skillDAO.getAllSkillsBySkillTypeAndCountryId(skillTypeId, countryId);
            List<SkillVO> vos = UtilsBusiness.convertList(skills, SkillVO.class);
            return vos;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllSkillsBySkillTypeAndCountryId/ConfigHabilidadesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillsBySkillTypeAndCountryId/ConfigHabilidadesBusinessBean ==");
        }
	}

}
