package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigParametrosFacadeLocal;
import co.com.directv.sdii.jobs.JobTimeManagerExcecuteBeanLocal;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Parametros.
 *
 * Caso de Uso CFG - 02 - Gestionar Parametros del Sistema
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigParametrosFacadeLocal",mappedName="ejb/ConfigParametrosFacadeLocal")
public class ConfigParametrosFacadeBean implements ConfigParametrosFacadeLocal {

    @EJB(name="ConfigParametrosBusinessLocal",beanInterface=ConfigParametrosBusinessLocal.class)
    private ConfigParametrosBusinessLocal business;

	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	@EJB(name="JobTimeManagerExcecuteLocal", beanInterface=JobTimeManagerExcecuteBeanLocal.class)
	private JobTimeManagerExcecuteBeanLocal jobTimeManagerExcecuteLocal;

	@Override
	public void runTimeManager() throws BusinessException {
		jobTimeManagerExcecuteLocal.runTimeManager();
	}

    public SystemParameterVO getSystemParameterByID(Long id) throws BusinessException {
        return business.getSystemParameterByID(id);
    }

    public List<SystemParameterVO> getAll() throws BusinessException {
        return business.getAll();
    }

    public void createSystemParameter(SystemParameterVO obj) throws BusinessException {
        business.createSystemParameter(obj);
    }

    public void updateSystemParameter(SystemParameterVO obj) throws BusinessException {
        business.updateSystemParameter(obj);
    }

    public void deleteSystemParameter(SystemParameterVO obj) throws BusinessException {
        business.deleteSystemParameter(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigParametrosFacadeLocal#getAllSystemParametersByCountryId(java.lang.Long)
     */
	public List<SystemParameterVO> getAllSystemParametersByCountryId(
			Long countryId) throws BusinessException {
		return business.getAllSystemParametersByCountryId(countryId);
	}

	@Override
	public SystemParameterVO getSystemParameterByCodeAndCountryId(String code,
			Long countryId) throws BusinessException {
		return business.getSystemParameterByCodeAndCountryId(code, countryId);
	}

	@Override
	public List<SystemParameterVO> getSystemParameterByNameAndCountryId(
			String name, Long countryId) throws BusinessException {
		return business.getSystemParameterByNameAndCountryId(name, countryId);
	} 
}
