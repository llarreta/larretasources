package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.CustomerValidationVista360DTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;


/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 3/07/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface Vista360ServiceBrokerLocal {

	/**
	 * Metodo: Método que obtiene la información básica del cliente desde RESB.
	 * @param customerKey
	 * @param sourceId
	 * @param validations
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	CustomerInfoAggregatedDTO getVista360(String customerKey, String sourceId, CustomerValidationVista360DTO validations) throws BusinessException;

	/**
	 * Metodo: Pobla la información de un cliente con los datos obtenidos desde el servicio
	 * de vista360
	 * @param customerCode 
	 * @param country 
	 * @param sdiiCustomer 
	 * @param woCountryCode 
	 * @param woPostalCodeCode 
	 * @param sdiiWorkorder 
	 * @param customerInfoAggregatedDTO 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	CustomerInfoAggregatedDTO populateCustomerFromIBSCust(String customerCode, 
			                                Country country, 
			                                Customer sdiiCustomer, 
			                                String woCountryCode, 
			                                WorkOrderDTO workOrderDto,
			                                CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException;

	/**
	 * Metodo: Pobla la informacion de un edificio con los datos obtenidos desde el servicio de IBS
	 * @param buildingCode 
	 * @param countryCode 
	 * @param sdiiBuilding 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	void populateBuildingFromIbsCust(String buildingCode, 
			                                String countryCode,
			                                Building sdiiBuilding) throws BusinessException;
	

	/**
	 * Metodo: Obtiene el valor de una característica del cliente desde el servicio getCustomerCharacteristic
	 * @param characteristicName 
	 * @param customerInfoAggregatedDTO 
	 * @return String
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	String getCustomerCharacteristicFromIBS(String characteristicName,CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException;

}
