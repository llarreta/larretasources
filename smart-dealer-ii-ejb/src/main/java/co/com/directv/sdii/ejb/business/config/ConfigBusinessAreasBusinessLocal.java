package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessAreaVO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del Configuración de Áreas de Negocio.
 *
 * @author ialessan
 */
@Local
public interface ConfigBusinessAreasBusinessLocal {

    /**
     * Este método retorna una lista de Todas las Áreas de Negocio.
     *
     * @return
     * @throws BusinessException
     */
    public List<BusinessAreaVO> getAllBusinessAreas(Long countryId) throws BusinessException;

    
    /**
     * Metodo que retorna el businessArea dado el service code.
     * @param serviceCode - String
     * @return
     * @throws BusinessException
     */
    public BusinessAreaVO getBusinessAreaByServiceCode(String serviceCode) throws BusinessException;
}
