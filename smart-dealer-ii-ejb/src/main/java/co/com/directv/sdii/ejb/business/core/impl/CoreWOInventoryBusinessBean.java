package co.com.directv.sdii.ejb.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.impl.ExternalInventoryServiceBrokerImpl;
import co.com.directv.sdii.ejb.business.core.CoreWOInventoryBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.vo.CountryVO;

/**
 * 
 * Implementa los métodos de negocio de los servicios de Core
 * que interactuan con el modulo de inventarios.
 * 
 * Fecha de Creación: 1/08/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="CoreWOInventoryBusinessLocal",mappedName="ejb/CoreWOInventoryBusinessLocal")
@Local({CoreWOInventoryBusinessLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreWOInventoryBusinessBean extends BusinessBase implements CoreWOInventoryBusinessLocal {

	private final static Logger log = UtilsBusiness.getLog4J(CoreWOInventoryBusinessBean.class);
	
	private ExternalInventoryServiceBrokerImpl brokerInventoryInterface;
	
	@EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
	private SerializedBusinessBeanLocal serializedBusinessBean;
	
	@EJB(name="CountriesCRUDBeanLocal", beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countriesCRUDBean;
	
	
	@Override
	public ElementDTO getElementBySerialCode(InventoryDTO inventoryDTO)	throws BusinessException {
		log.debug("== Inicia getElementBySerialCode/CoreWOInventoryBusinessBean ==");
		try{

			ElementDTO dto = new ElementDTO();
			CountryVO countryVO = countriesCRUDBean.getCountriesByCode(inventoryDTO.getCountryCode());
			dto.setSerializedVO( serializedBusinessBean.getSerializedBySerialCode( inventoryDTO.getElementDTO().getSerializedVO().getSerialCode(),countryVO.getId() ) );
			return dto;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getElementBySerialCode/CoreWOInventoryBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialCode/CoreWOInventoryBusinessBean ==");
		}		
	}
	
	@Override
	public ElementDTO getElementBySerialCodeExternal(InventoryDTO inventoryDTO)	throws BusinessException {
		log.debug("== Inicia getElementBySerialCode/CoreWOInventoryBusinessBean ==");
		try{
			
			brokerInventoryInterface = new ExternalInventoryServiceBrokerImpl();
			return brokerInventoryInterface.getSerializedResource(inventoryDTO);			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getElementBySerialCode/CoreWOInventoryBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialCode/CoreWOInventoryBusinessBean ==");
		}		
	}

}
