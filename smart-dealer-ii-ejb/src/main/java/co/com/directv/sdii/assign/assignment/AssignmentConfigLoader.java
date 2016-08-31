package co.com.directv.sdii.assign.assignment;

import co.com.directv.sdii.assign.assignment.dto.AssignmentConfiguration;
import co.com.directv.sdii.assign.assignment.dto.AssignmentCriteria;

/**
 * Define las operaciones para cargar la configuración del asignador, se desacopla
 * porque se están estudiando dos estrategias de persistencia de esa configuración:
 * 
 * 1. Oracle Business Rules
 * 2. Base de datos SDII
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public interface AssignmentConfigLoader {

	/**
	 * 
	 * @param criteria    criterio de cargue de la configuración
	 */
	public AssignmentConfiguration loadAssignmentConfiguration(AssignmentCriteria criteria);

}