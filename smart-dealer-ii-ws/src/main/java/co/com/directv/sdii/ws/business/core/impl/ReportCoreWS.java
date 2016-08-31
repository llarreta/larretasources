package co.com.directv.sdii.ws.business.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.ReportCoreFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;
import co.com.directv.sdii.ws.business.core.IReportCoreWS;

/**
 * Web Service que expone las operaciones
 * para la generacion de reportes de Core
 * 
 * Fecha de Creación: 10/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@MTOM(threshold=3072)
@WebService(serviceName="ReportCoreService",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		endpointInterface="co.com.directv.sdii.ws.business.core.IReportCoreWS",
		portName="ReportCorePort")
@Stateless()
public class ReportCoreWS implements IReportCoreWS{
	
	@EJB(name="ReportCoreFacadeBeanLocal",beanInterface=ReportCoreFacadeBeanLocal.class)
	private ReportCoreFacadeBeanLocal reportCoreFacadeBean;
	
    /**
     * Default constructor. 
     */
    public ReportCoreWS() {
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.core.ReportCoreFacadeBeanLocal#getActivityBacklog()
     */
    public List<ActivityBacklogResponseDTO> getActivityBacklog(String countryCode) throws BusinessException{
    	return reportCoreFacadeBean.getActivityBacklog(countryCode);
    }
	
	
}
