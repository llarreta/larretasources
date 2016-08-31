package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebParam;

import co.com.directv.sdii.ejb.business.config.ConfigBusinessAreasBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigBusinessAreasFacadeLocal;
import co.com.directv.sdii.facade.config.ConfigBusinessAreasFacadeRemote;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Clase que implementa el contrato de operaciones para los servicios web
 * de Configuración de Áreas de Negocio.
 *
 * Fecha de Creación: Vie 13, 2013
 * @author ialessan
 * @version 1.0
 *
 * @see ConfigBusinessAreasFacadeLocal
 */
@Stateless(name="ConfigBusinessAreasFacadeLocal",mappedName="ejb/ConfigBusinessAreasFacadeLocal")
@Local({ConfigBusinessAreasFacadeLocal.class})
@Remote({ConfigBusinessAreasFacadeRemote.class})
public class ConfigBusinessAreasFacadeBean implements ConfigBusinessAreasFacadeLocal, ConfigBusinessAreasFacadeRemote {

    @EJB(name="ConfigBusinessAreasBusinessLocal",beanInterface=ConfigBusinessAreasBusinessLocal.class)
    private ConfigBusinessAreasBusinessLocal configBusinessAreasBusinessLocal;

    
    public List<BusinessAreaVO> getAllBusinessAreas(@WebParam(name = "countryId") Long countryId) throws BusinessException{
    	return configBusinessAreasBusinessLocal.getAllBusinessAreas(countryId);
    }
    
    public BusinessAreaVO getBusinessAreaByServiceCode(String serviceCode) throws BusinessException {
    	return configBusinessAreasBusinessLocal.getBusinessAreaByServiceCode(serviceCode);
    }
	
}
