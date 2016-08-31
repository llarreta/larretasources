package co.com.directv.sdii.ws.version.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessTierVersionVO;
import co.com.directv.sdii.ws.version.IBusinessTierVersionWS;

/**
 * 
 * Servicio web para consultar las versiones de la capa de negocio. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ws.version.IBusinessTierVersionWS
 */
@MTOM
@WebService(serviceName="BusinessTierVersionService",
		endpointInterface="co.com.directv.sdii.ws.version.IBusinessTierVersionWS",
		targetNamespace="http://directvla.com.contract/ws/sdii/Version",
		portName="BusinessTierVersionPort")
@Stateless()
public class BusinessTierVersionWS implements IBusinessTierVersionWS {

	@EJB
	private BusinessTierVersionFacadelocal facade;
	
	@Override
	public BusinessTierVersionVO getBusinessVersion() throws BusinessException {
		return facade.getBusinessVersion();
	}

}
