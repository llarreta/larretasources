package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerServiceSubCatWithPcFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerServiceSubCatWithPc
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerServiceSubCatWithPcFacadeLocal
 */
@Stateless(name="HistoDealerServiceSubCatWithPcFacadeLocal",mappedName="ejb/HistoDealerServiceSubCatWithPcFacadeLocal")
public class HistoDealerServiceSubCatWithPcFacadeBean implements HistoDealerServiceSubCatWithPcFacadeBeanLocal {

		
    @EJB(name="HistoDealerServiceSubCatWithPcBusinessBeanLocal", beanInterface=HistoDealerServiceSubCatWithPcBusinessBeanLocal.class)
    private HistoDealerServiceSubCatWithPcBusinessBeanLocal businessHistoDealerServiceSubCatWithPc;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerServiceSubCatWithPcFacadeLocal#getAllHistoDealerServiceSubCatWithPcs()
     */
    public List<HistoDealerServiceSubCatWithPcVO> getAllHistoDealerServiceSubCatWithPcs() throws BusinessException {
    	return businessHistoDealerServiceSubCatWithPc.getAllHistoDealerServiceSubCatWithPcs();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerServiceSubCatWithPcFacadeLocal#getHistoDealerServiceSubCatWithPcsByID(java.lang.Long)
     */
    public HistoDealerServiceSubCatWithPcVO getHistoDealerServiceSubCatWithPcByID(Long id) throws BusinessException {
    	return businessHistoDealerServiceSubCatWithPc.getHistoDealerServiceSubCatWithPcByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCatWithPcFacadeLocal#createHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
		businessHistoDealerServiceSubCatWithPc.createHistoDealerServiceSubCatWithPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCatWithPcFacadeLocal#updateHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
		businessHistoDealerServiceSubCatWithPc.updateHistoDealerServiceSubCatWithPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerServiceSubCatWithPcFacadeLocal#deleteHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
		businessHistoDealerServiceSubCatWithPc.deleteHistoDealerServiceSubCatWithPc(obj);
	}
}
