package co.com.directv.sdii.ws.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.ws.business.core.IMailWS;
import co.com.directv.sdii.ws.model.dto.ResponseSendMailDTO;

/**
 * Web Service que expone las operaciones
 * para el envio de reportes(pdf)
 * 
 * Fecha de Creación: 14/05/2010
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 * @version 1.0
 * 
 * @see
 */
@MTOM
@WebService(serviceName="MailService",
		endpointInterface="co.com.directv.sdii.ws.business.core.IMailWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="MailPort")
@Stateless()
public class MailWS implements IMailWS{
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IMailWS#sendWorkOrdersPDFEmail(java.lang.String[], java.lang.Long, java.lang.Long)
	 */
	@Override
	public ResponseSendMailDTO sendWorkOrdersPDFEmail(@WebParam(name = "fileNames")String[] fileNames, @WebParam(name = "filterCrewId")Long filterCrewId,  @WebParam(name = "countryId")Long countryId) throws BusinessException{
		try {
			return coreWOFacade.sendWorkOrdersPDFEmail(fileNames,filterCrewId, countryId);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
