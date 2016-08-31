package co.com.directv.sdii.assign.schedule;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacity;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * implementación del mecanismo de cargue de la capacidad de una compañía instaladora 
 * con base en la fecha (día de la semana), la jornada y la super categoría de 
 * servicio consulta la información persistida en dealers_week_capacity
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public interface DealerWorkCapacityLoader {

	/**
	 * carga la capacidad de una compañía instaladora
	 * 
	 * @param dealerWorkCapacityCriteria    filtro de búsqueda para la capacidad de
	 * una compañía
	 */
	public DealerWorkCapacity loadCapacity(DealerWorkCapacityCriteria dealerWorkCapacityCriteria)throws BusinessException;

}