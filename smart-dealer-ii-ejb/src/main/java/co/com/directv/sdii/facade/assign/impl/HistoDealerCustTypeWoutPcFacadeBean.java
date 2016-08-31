package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerCustTypeWoutPcFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerCustTypeWoutPc
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerCustTypeWoutPcFacadeLocal
 */
@Stateless(name="HistoDealerCustTypeWoutPcFacadeLocal",mappedName="ejb/HistoDealerCustTypeWoutPcFacadeLocal")
public class HistoDealerCustTypeWoutPcFacadeBean implements HistoDealerCustTypeWoutPcFacadeBeanLocal {

		
    @EJB(name="HistoDealerCustTypeWoutPcBusinessBeanLocal", beanInterface=HistoDealerCustTypeWoutPcBusinessBeanLocal.class)
    private HistoDealerCustTypeWoutPcBusinessBeanLocal businessHistoDealerCustTypeWoutPc;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerCustTypeWoutPcFacadeLocal#getAllHistoDealerCustTypeWoutPcs()
     */
    public List<HistoDealerCustTypeWoutPcVO> getAllHistoDealerCustTypeWoutPcs() throws BusinessException {
    	return businessHistoDealerCustTypeWoutPc.getAllHistoDealerCustTypeWoutPcs();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerCustTypeWoutPcFacadeLocal#getHistoDealerCustTypeWoutPcsByID(java.lang.Long)
     */
    public HistoDealerCustTypeWoutPcVO getHistoDealerCustTypeWoutPcByID(Long id) throws BusinessException {
    	return businessHistoDealerCustTypeWoutPc.getHistoDealerCustTypeWoutPcByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCustTypeWoutPcFacadeLocal#createHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
		businessHistoDealerCustTypeWoutPc.createHistoDealerCustTypeWoutPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCustTypeWoutPcFacadeLocal#updateHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
		businessHistoDealerCustTypeWoutPc.updateHistoDealerCustTypeWoutPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCustTypeWoutPcFacadeLocal#deleteHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
		businessHistoDealerCustTypeWoutPc.deleteHistoDealerCustTypeWoutPc(obj);
	}
}
