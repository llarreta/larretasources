/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.facade.dealers;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.VehicleVO;

/**
 * Interfaz que define la Session Facade de las operaciones a realizar para el módulo de Vehicles
 * @author Jose Andres Casas Rodriguez
 */
public interface VehicleFacadeBeanLocal {
	
	/**
	 * Método que permite crear nuevos Vehiculos.
	 * @param obj 
	 * @throws BusinessException
	 */
    public abstract void createVehicle(VehicleVO obj) throws BusinessException;
}
