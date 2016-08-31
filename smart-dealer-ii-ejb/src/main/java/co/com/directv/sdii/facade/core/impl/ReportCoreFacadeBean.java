package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.ReportsCoreBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.ReportCoreFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;

/**
 * Session Bean implementation class ReportCoreFacadeBean
 */
@Stateless(name="ReportCoreFacadeBeanLocal",mappedName="ejb/ReportCoreFacadeBeanLocal")
public class ReportCoreFacadeBean extends BusinessBase implements ReportCoreFacadeBeanLocal {

	@EJB(name="ReportsCoreBusinessLocal",beanInterface=ReportsCoreBusinessLocal.class)
	private ReportsCoreBusinessLocal reportsCoreBusinessLocal;
	
    /**
     * Default constructor. 
     */
    public ReportCoreFacadeBean() {}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.core.ReportCoreFacadeBeanLocal#getActivityBacklog()
     */
    public List<ActivityBacklogResponseDTO> getActivityBacklog(String countryCode) throws BusinessException{
    	return reportsCoreBusinessLocal.getActivityBacklog(countryCode);
    }
    
    

}