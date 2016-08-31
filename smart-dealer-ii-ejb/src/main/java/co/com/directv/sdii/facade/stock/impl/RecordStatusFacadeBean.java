package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RecordStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.RecordStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RecordStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RecordStatusFacadeLocal
 */
@Stateless(name="RecordStatusFacadeLocal",mappedName="ejb/RecordStatusFacadeLocal")
public class RecordStatusFacadeBean implements RecordStatusFacadeBeanLocal {

		
    @EJB(name="RecordStatusBusinessBeanLocal", beanInterface=RecordStatusBusinessBeanLocal.class)
    private RecordStatusBusinessBeanLocal businessRecordStatus;
    
  
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RecordStatusFacadeLocal#getAllRecordStatuss()
     */
    public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException {
    	return businessRecordStatus.getAllRecordStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RecordStatusFacadeLocal#getRecordStatussByID(java.lang.Long)
     */
    public RecordStatusVO getRecordStatusByID(Long id) throws BusinessException {
    	return businessRecordStatus.getRecordStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RecordStatusFacadeLocal#createRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	public void createRecordStatus(RecordStatusVO obj) throws BusinessException {
		businessRecordStatus.createRecordStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RecordStatusFacadeLocal#updateRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	public void updateRecordStatus(RecordStatusVO obj) throws BusinessException {
		businessRecordStatus.updateRecordStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RecordStatusFacadeLocal#deleteRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	public void deleteRecordStatus(RecordStatusVO obj) throws BusinessException {
		businessRecordStatus.deleteRecordStatus(obj);
	}
}
