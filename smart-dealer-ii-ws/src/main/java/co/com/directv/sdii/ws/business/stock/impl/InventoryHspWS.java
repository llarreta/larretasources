package co.com.directv.sdii.ws.business.stock.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.InventoryFacadeHspBeanLocal;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.ws.business.stock.IInventoryHspWS;

/**
 * Servicio web que expone las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author jjimenezh <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * 
 */
@MTOM
@WebService(serviceName="InventoryHspWSService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IInventoryHspWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="InventoryHspWSPort")
@Stateless()
public class InventoryHspWS implements IInventoryHspWS {

	@EJB
    private InventoryFacadeHspBeanLocal ejbRef;
	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupStock(String countryCode, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRef.getElementGroupStock(countryCode, requestCollInfo);
	}
	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupTransit(String countryCode, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRef.getElementGroupTransit(countryCode, requestCollInfo);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IInventoryHspWS#sendWorkOrdersForLastDayReport(java.lang.Long)
	 */
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException{
		ejbRef.sendWorkOrdersForLastDayReport(countryId);
	}
	
	
}
