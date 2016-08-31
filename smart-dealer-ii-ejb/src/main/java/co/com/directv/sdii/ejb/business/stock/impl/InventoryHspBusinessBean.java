package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.InventoryHspBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * 
 * Implementación de la capa de negocio con las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Stateless(name="InventoryHspBusinessBeanLocal",mappedName="ejb/InventoryHspBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InventoryHspBusinessBean extends BusinessBase implements InventoryHspBusinessBeanLocal {

   
    @EJB(name = "WarehouseElementDAOLocal", beanInterface = WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name = "CountriesDAOLocal", beanInterface = CountriesDAOLocal.class)
	private CountriesDAOLocal daoCountries;
    
    private final static Logger log = UtilsBusiness.getLog4J(InventoryHspBusinessBean.class);

	@Override
	public InventoryElementGroupDTOResponse getElementGroupStock(
			String countryCode, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getElementGroupStock/InventoryHspBusinessBean ==");
        try {
        	//Validar que el pais exista
        	Country country = daoCountries.getCountriesByCode(countryCode); 
        	
        	if(country == null){
        		List<String> params = new ArrayList<String>();
        		params.add(countryCode);
        		throw new BusinessException(ErrorBusinessMessages.CORE_CR065.getCode(), ErrorBusinessMessages.CORE_CR065.getMessage(), params);
        	}
        	
        	List<String> warehouseTypesStrings = new ArrayList<String>();
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01P.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S03.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S06.getCodeEntity());
        	InventoryElementGroupDTOResponse inventoryElementGroupDTOResponse = this.daoWarehouseElement.getElementGroupInventory(country,warehouseTypesStrings, requestCollInfo);
            return inventoryElementGroupDTOResponse;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getElementGroupStock/InventoryHspBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementGroupStock/InventoryHspBusinessBean ==");
        }
	}
	
	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupTransit(
			String countryCode, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getElementGroupTransit/InventoryHspBusinessBean ==");
        try {
        	//Validar que el pais exista
        	Country country = daoCountries.getCountriesByCode(countryCode); 
        	
        	if(country == null){
        		List<String> params = new ArrayList<String>();
        		params.add(countryCode);
        		throw new BusinessException(ErrorBusinessMessages.CORE_CR065.getCode(), ErrorBusinessMessages.CORE_CR065.getMessage(), params);
        	}
        	
        	//Se arma la lista con los tipos de bodega indicados
        	List<String> warehouseTypesStrings = new ArrayList<String>();
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRD723.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_WHTRO722.getCodeEntity());
        	warehouseTypesStrings.add(CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
        	InventoryElementGroupDTOResponse inventoryElementGroupDTOResponse = this.daoWarehouseElement.getElementGroupInventoryWithDate(country, warehouseTypesStrings, requestCollInfo);
            return inventoryElementGroupDTOResponse;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getElementGroupTransit/InventoryHspBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementGroupTransit/InventoryHspBusinessBean ==");
        }
	}
    
   
    
	
}
