package co.com.directv.sdii.ejb.business.assign;

import java.util.List;
import javax.ejb.Local;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad KpiConfiguration.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiConfigurationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiConfigurationVO
	 * @param obj objeto que encapsula la información de un KpiConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiConfigurationVO
	 * @param obj objeto que encapsula la información de un KpiConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiConfigurationVO
	 * @param obj información del KpiConfigurationVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiConfigurationVO por su identificador
	 * @param id identificador del KpiConfigurationVO a ser consultado
	 * @return objeto con la información del KpiConfigurationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiConfigurationVO getKpiConfigurationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiConfigurationVO almacenados en la persistencia
	 * @return Lista con los KpiConfigurationVO existentes, una lista vacia en caso que no existan KpiConfigurationVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiConfigurationVO> getAllKpiConfigurations() throws BusinessException;

	/**
	 * Metodo: Obtiene las configuraciones de KPI para un tipo de cálculo específigo
	 * @param string código del tipo de cálculo
	 * @return lista de configuraciones KPI con un tipo de cálculo específico
	 * @throws BusinessException en caso de error en la consulta
	 * @author wjimenez
	 */
	public List<KpiConfigurationVO> getKpiConfigurationsByCalculationTypeCode(String calculationTypeCode) throws BusinessException;
	
	/**
	 * Metodo:  Obtiene las configuraciones de KPI para un tipo de cálculo específigo almacenandolo en KpiCalculateDTO
	 * @param calculationTypeCode string código del tipo de cálculo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,Long countryId) throws BusinessException;

	/**
	 * 
	 * Metodo: Obtiene las configuraciones de KPI para los parámetros especificados
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la supercategoría
	 * @param kpiId identificador del indicador
	 * @param zoneTypeId identificador del tipo de zona
	 * @return configuración del kpi que se encuentre para los parámetros
	 * @throws BusinessException en caso de error en la consulta
	 * @author
	 */
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId, Long zoneTypeId) throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de las configuraciones de KPI activas por los parametros definidos
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @return Lista con la configuración de los KPi
	 * @throws BusinessException En caso de error al cargar la configuración
	 * @author jjimenezh
	 */
	public List<KpiConfigurationVO> getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)throws BusinessException;

	/**
	 * Metodo: Carga la configuración de un KPI para los parámetros dados
	 * @param countryCode código del país
	 * @param serviceSuperCategoryCode código de la super categoría de servicio
	 * @param zoneTypeCode código del tipo de zona
	 * @param kpiCode código del KPI
	 * @return configuración del KPi nulo en caso que no esté activa o no exista
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public KpiConfigurationVO getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la configuración de un KPI por los criterios de búsqueda
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @param kpiCode código del KPI
	 * @return configuración del KPi, nulo en caso que no se encuentre o no se encuentre activa
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(Long countryId, Long superCategoryId, Long zoneTypeId, String kpiCode)throws BusinessException;

	/**
	 * Metodo: Lista las configuraciones de KPI independientemente del estado para el país y
	 * la supercategoría especificada
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la supercategoría
	 * @return List<KpiConfigurationVO> lustado de las configuraciones de KPI que coinciden con los parámetros especificados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long superCategoryId)throws BusinessException;

	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de KPIs y reconfigura el job de cálculo de KPIs
	 * para que utilice los nuevos parámetros de ejecución.
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos los elementos y se actualiza el job
	 * o no se actualizan ni los registros ni el job.
	 * @param kpiConfigurations listado de configuraciones de KPI que se desean actualizar
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateKpiConfigurations(List<KpiConfigurationVO> newKpiConfigurations)throws BusinessException;
	
}
