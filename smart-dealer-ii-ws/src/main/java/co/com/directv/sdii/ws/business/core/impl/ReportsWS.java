package co.com.directv.sdii.ws.business.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.ws.business.core.IReportsWS;


/**
 * Web Service que expone las operaciones
 * para la generacion de reportes (pdf)
 * 
 * Fecha de Creación: 14/05/2010
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 * @version 1.0
 * 
 * @see
 */
@MTOM(threshold=3072)
@WebService(serviceName="ReportsService",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		endpointInterface="co.com.directv.sdii.ws.business.core.IReportsWS",
		portName="ReportsPort")
@Stateless()
public class ReportsWS implements IReportsWS{
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IReportsWS#generateCrewWorkOrdersPDF(java.util.List, co.com.directv.sdii.model.vo.CrewVO)
	 */
	@Override
	public void generateCrewWorkOrdersPDF(@WebParam(name = "workOrderIds")List<Long> workOrderIds, @WebParam(name = "crew") CrewVO crew) throws BusinessException{
		try {
			coreWOFacade.generateCrewWorkOrdersPDF(workOrderIds, crew);
		} catch (PDFException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
}
