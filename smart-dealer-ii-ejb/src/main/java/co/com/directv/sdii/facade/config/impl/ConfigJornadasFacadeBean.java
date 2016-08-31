package co.com.directv.sdii.facade.config.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal;
import co.com.directv.sdii.model.dto.ServiceHourDTO;
import co.com.directv.sdii.model.vo.ServiceHourVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Jornadas.
 *
 * Caso de Uso CFG - 01 - Gestionar Jornadas de Servicio
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigJornadasFacadeLocal",mappedName="ejb/ConfigJornadasFacadeLocal")
public class ConfigJornadasFacadeBean implements ConfigJornadasFacadeLocal {

    @EJB(name="ConfigJornadasBusinessLocal",beanInterface=ConfigJornadasBusinessLocal.class)
    private ConfigJornadasBusinessLocal business;

    public ServiceHourVO getServiceHoursByID(Long id) throws BusinessException {
        return business.getServiceHoursByID(id);
    }

    public List<ServiceHourVO> getServiceHoursByName(String name) throws BusinessException {
        return business.getServiceHoursByName(name);
    }

    public List<ServiceHourVO> getAll() throws BusinessException {
        return business.getAll();
    }

    public List<ServiceHourVO> getServiceHoursByDate(Date init, Date end) throws BusinessException {
        return business.getServiceHoursByDate(init, end);
    }

    public void createServiceHours(ServiceHourVO obj, Long userId) throws BusinessException {
        business.createServiceHours(obj, userId);
    }

    public void updateServiceHours(ServiceHourVO obj, Long userId) throws BusinessException {
        business.updateServiceHours(obj, userId);
    }

    public void deleteServiceHours(ServiceHourVO obj) throws BusinessException {
        business.deleteServiceHours(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal#getAllServiceHoursByCountryId(java.lang.Long)
     */
	public List<ServiceHourVO> getAllServiceHoursByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllServiceHoursByCountryId(countryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal#getAllActiveServiceHoursByCountryId(java.lang.Long)
	 */
	public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllActiveServiceHoursByCountryId(countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal#getServiceHourDtoByID(java.lang.Long)
	 */
	@Override
	public ServiceHourDTO getServiceHourDtoByID(Long id)
			throws BusinessException {
		return business.getServiceHourDtoByID(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigJornadasFacadeLocal#getServiceHoursDtoByCountryId(java.lang.Long)
	 */
	@Override
	public List<ServiceHourDTO> getServiceHoursDtoByCountryId(Long countryId)
			throws BusinessException {
		return business.getServiceHoursDtoByCountryId(countryId);
	}

}
