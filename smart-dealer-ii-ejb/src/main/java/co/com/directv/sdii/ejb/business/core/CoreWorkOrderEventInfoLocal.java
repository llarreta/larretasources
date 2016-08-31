package co.com.directv.sdii.ejb.business.core;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;

public interface CoreWorkOrderEventInfoLocal {

	public CustomerInfoAggregatedDTO populateCustomerFromIBSCust(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder, 
			Country country, 
			Customer sdiiCustomer, 
			String woCountryCode, 
			WorkOrderDTO workOrderDto,
			CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException;

	public void populateBuildingFromIbsCustInfo(String buildingCode, 
			String countryCode,
			Building sdiiBuilding, 
			co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder) throws BusinessException;
	
	public String getCustomerCharacteristicFromIBSInfo(String characteristicName,
            CustomerInfoAggregatedDTO customerInfoAggregatedDTO) throws BusinessException;
	
}
