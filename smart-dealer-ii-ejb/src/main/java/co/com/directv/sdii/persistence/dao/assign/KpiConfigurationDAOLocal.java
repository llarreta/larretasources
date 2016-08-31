package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.pojo.KpiConfiguration;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad KpiConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiConfigurationDAOLocal {

	/**
	 * Metodo:  persiste la información de un KpiConfiguration
	 * @param obj objeto que encapsula la información de un KpiConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un KpiConfiguration
	 * @param obj objeto que encapsula la información de un KpiConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiConfiguration
	 * @param obj información del KpiConfiguration a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiConfiguration(KpiConfiguration obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un KpiConfiguration por su identificador
	 * @param id identificador del KpiConfiguration a ser consultado
	 * @return objeto con la información del KpiConfiguration dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiConfiguration getKpiConfigurationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiConfiguration almacenados en la persistencia
	 * @return Lista con los KpiConfiguration existentes, una lista vacia en caso que no existan KpiConfiguration en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiConfiguration> getAllKpiConfigurations() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las configuraciones de KPI para un tipo de cálculo específigo
	 * @param string código del tipo de cálculo
	 * @return lista de configuraciones KPI con un tipo de cálculo específico
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<KpiConfiguration> getKpiConfigurationsByCalculationTypeCode(
			String calculationTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene las configuraciones de KPI para un tipo de cálculo específico en el DTO KpiCalculateDTO
	 * @param calculationTypeCode codigo del tipo de calculo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,Long countryId,Long userId) throws DAOServiceException,DAOSQLException;

	public KpiConfiguration getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId, Long zoneTypeId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la configuracion de kpi por pais, super categpria, tipo de zona y codigo de kpi
	 * @param countryId
	 * @param superCategoryId
	 * @param kpiId
	 * @param zoneTypeId
	 * @return KpiConfiguration cofiguracion que cumple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public KpiConfiguration getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(Long countryId, Long superCategoryId, String kpiCode, Long zoneTypeId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de las configuraciones de KPI activas por los parametros definidos
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @return Lista con la configuración de los KPi
	 * @throws DAOServiceException En caso de error al cargar la configuración
	 * @throws DAOSQLException En caso de error al cargar la configuración
	 * @author jjimenezh
	 */
	public List<KpiConfiguration> getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Carga la configuración de un KPI para los parámetros dados
	 * @param countryCode código del país
	 * @param serviceSuperCategoryCode código de la super categoría de servicio
	 * @param zoneTypeCode código del tipo de zona
	 * @param kpiCode código del KPI
	 * @return configuración del KPi nulo en caso que no esté activa o no exista
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public KpiConfiguration getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Lista las configuraciones de KPI independientemente del estado para el país y
	 * la supercategoría especificada
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la supercategoría
	 * @return List<KpiConfigurationVO> lustado de las configuraciones de KPI que coinciden con los parámetros especificados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<KpiConfiguration> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long serviceSuperCategoryId)throws DAOServiceException, DAOSQLException;


}