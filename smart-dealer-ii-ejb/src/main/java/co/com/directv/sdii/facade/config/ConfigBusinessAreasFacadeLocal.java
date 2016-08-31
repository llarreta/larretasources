package co.com.directv.sdii.facade.config;

import java.util.List;

import javax.ejb.Local;
import javax.jws.WebParam;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Esta interfaz define los contratos a utilizar para los servicios web
 * de Configuración de Áreas de Negocio.
 *
 * @author ialessan
 */
@Local
public interface ConfigBusinessAreasFacadeLocal {
	
	public List<BusinessAreaVO> getAllBusinessAreas(@WebParam(name = "countryId") Long countryId) throws BusinessException;
	public BusinessAreaVO getBusinessAreaByServiceCode(String serviceCode) throws BusinessException;
}
