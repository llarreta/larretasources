package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigHabilidadesBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal;
import co.com.directv.sdii.model.vo.SkillTypeVO;
import co.com.directv.sdii.model.vo.SkillVO;

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
 * @see
 */
@Stateless(name="ConfigHabilidadesFacadeLocal",mappedName="ejb/ConfigHabilidadesFacadeLocal")
public class ConfigHabilidadesFacadeBean implements ConfigHabilidadesFacadeLocal {

    @EJB(name="ConfigHabilidadesBusinessLocal",beanInterface=ConfigHabilidadesBusinessLocal.class)
    private ConfigHabilidadesBusinessLocal business;

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getSkillsByID(java.lang.Long)
     */
    public SkillVO getSkillsByID(Long id) throws BusinessException {
        return business.getSkillsByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getSkillByCode(java.lang.String)
     */
    public SkillVO getSkillByCode(String code) throws BusinessException {
        return business.getSkillByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getSkillsByName(java.lang.String)
     */
    public List<SkillVO> getSkillsByName(String name) throws BusinessException {
        return business.getSkillsByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getSkillsByType(java.lang.Long)
     */
    public List<SkillVO> getSkillsByType(Long typeId) throws BusinessException {
        return business.getSkillsByType(typeId);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getAll()
     */
    public List<SkillVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#createSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    public void createSkill(SkillVO obj) throws BusinessException {
        business.createSkill(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#updateSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    public void updateSkill(SkillVO obj) throws BusinessException {
        business.updateSkill(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#deleteSkill(co.com.directv.sdii.model.vo.SkillVO)
     */
    public void deleteSkill(SkillVO obj) throws BusinessException {
        business.deleteSkill(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getAllSkillTypes()
     */
	public List<SkillTypeVO> getAllSkillTypes() throws BusinessException {
		return business.getAllSkillTypes();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getAllSkillTypesByCountryId(java.lang.Long)
	 */
	public List<SkillTypeVO> getAllSkillTypesByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllSkillTypesByCountryId(countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getAllSkillsByCountryId(java.lang.Long)
	 */
	public List<SkillVO> getAllSkillsByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllSkillsByCountryId(countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigHabilidadesFacadeLocal#getAllSkillsBySkillTypeAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<SkillVO> getAllSkillsBySkillTypeAndCountryId(Long skillTypeId,
			Long countryId) throws BusinessException {
		return business.getAllSkillsBySkillTypeAndCountryId(skillTypeId, countryId);
	}

}
