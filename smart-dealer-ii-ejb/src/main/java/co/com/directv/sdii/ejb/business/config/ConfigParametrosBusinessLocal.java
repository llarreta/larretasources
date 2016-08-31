package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SystemParameterVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Parámetros.
 *
 * En la consulta de un SYSTEM_PARAMETERS se debe recuperar una referencia a
 * su PARAMETER_TYPE.
 *
 * Caso de Uso CFG - 02 - Gestionar Parametros del Sistema
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigParametrosBusinessLocal {

    /**
     * Este método retorna una instancia de SYSTEM_PARAMETERS por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public SystemParameterVO getSystemParameterByID(Long id) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los SYSTEM_PARAMETERS.
     *
     * @return
     * @throws BusinessException
     */
    public List<SystemParameterVO> getAll() throws BusinessException;

    /**
     * Obtiene una lista de los parámetros del sistema regionalizados
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de parámetros del sistema regionalizados
     * @throws BusinessException en caso de error al tratar de obtener los parámetros del sistema
     * @author jjimenezh Agergado por control de cambios 2010-04-26
     */
    public List<SystemParameterVO> getAllSystemParametersByCountryId(Long countryId) throws BusinessException;

    
    /**
     * Este método crea un SYSTEM_PARAMETERS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createSystemParameter(SystemParameterVO obj) throws BusinessException;

    /**
     * Este método actualiza una SYSTEM_PARAMETERS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateSystemParameter(SystemParameterVO obj) throws BusinessException;

    /**
     * Este método elimina un SYSTEM_PARAMETERS.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteSystemParameter(SystemParameterVO obj) throws BusinessException;

	/**
	 * Metodo: Obtiene los parámetros del sistema por código
	 * @param code Código del parámetro del sistema
	 * @param countryId identificador del país
	 * @return Parámetro del systema, nulo en caso que no se encuentre
	 * @throws BusinessException 
	 * @author jjimenezh
	 */
	public SystemParameterVO getSystemParameterByCodeAndCountryId(String code,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene los parámetros del sistema por código
	 * @param name code Código del parámetro del sistema
	 * @param countryId identificador del país
	 * @return Parámetro del systema, nulo en caso que no se encuentre
	 * @throws BusinessException 
	 * @author jjimenezh
	 */
	public List<SystemParameterVO> getSystemParameterByNameAndCountryId(String name,Long countryId) throws BusinessException;

	/**
	 * Metodo: <Descripcion>
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public long getDealerCodeWoutCoverage(Long countryId)throws BusinessException;
}
