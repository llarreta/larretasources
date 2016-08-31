package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad KpiConfiguration.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiConfigurationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto KpiConfiguration
	 * @param obj - KpiConfigurationVO  objeto que encapsula la información de un KpiConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un KpiConfiguration
	 * @param obj - KpiConfigurationVO  objeto que encapsula la información de un KpiConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un KpiConfiguration
	 * @param obj - KpiConfigurationVO  información del KpiConfigurationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpiConfiguration(KpiConfigurationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un KpiConfiguration por su identificador
	 * @param id - Long identificador del KpiConfiguration a ser consultado
	 * @return objeto con la información del KpiConfigurationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiConfigurationVO getKpiConfigurationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiConfiguration almacenados en la persistencia
	 * @return List<KpiConfigurationVO> Lista con los KpiConfigurationVO existentes, una lista vacia en caso que no existan KpiConfigurationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiConfigurationVO> getAllKpiConfigurations() throws BusinessException;

	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de KPIs y reconfigura el job de cálculo de KPIs
	 * para que utilice los nuevos parámetros de ejecución.
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos los elementos y se actualiza el job
	 * o no se actualizan los registros ni el job.
	 * @param kpiConfigurations listado de configuraciones de KPI que se desean actualizar
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void updateKpiConfigurations(List<KpiConfigurationVO> newKpiConfigurations) throws BusinessException;

	/**
	 * Metodo: Lista las configuraciones de KPI independientemente del estado para el país y
	 * la supercategoría especificada.
	 * Para los KPIs que no tienen configuración, se incluye una configuración por defecto en estado
	 * inactivo.
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la supercategoría
	 * @return List<KpiConfigurationVO> lustado de las configuraciones de KPI que coinciden con los parámetros especificados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long superCategoryId) throws BusinessException;
	
	
	public void calculateAndSaveNextExcecutionDate(Long id) throws BusinessException ;
}
