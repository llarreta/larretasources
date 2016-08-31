package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefIncTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.RefIncTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RefIncType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RefIncTypeFacadeLocal
 */
@Stateless(name="RefIncTypeFacadeLocal",mappedName="ejb/RefIncTypeFacadeLocal")
public class RefIncTypeFacadeBean implements RefIncTypeFacadeBeanLocal {

		
    @EJB(name="RefIncTypeBusinessBeanLocal", beanInterface=RefIncTypeBusinessBeanLocal.class)
    private RefIncTypeBusinessBeanLocal businessRefIncType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefIncTypeFacadeLocal#getAllRefIncTypes()
     */
    public List<RefIncTypeVO> getAllRefIncTypes() throws BusinessException {
    	return businessRefIncType.getAllRefIncTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefIncTypeFacadeLocal#getRefIncTypesByID(java.lang.Long)
     */
    public RefIncTypeVO getRefIncTypeByID(Long id) throws BusinessException {
    	return businessRefIncType.getRefIncTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncTypeFacadeLocal#createRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	public void createRefIncType(RefIncTypeVO obj) throws BusinessException {
		businessRefIncType.createRefIncType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncTypeFacadeLocal#updateRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	public void updateRefIncType(RefIncTypeVO obj) throws BusinessException {
		businessRefIncType.updateRefIncType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncTypeFacadeLocal#deleteRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	public void deleteRefIncType(RefIncTypeVO obj) throws BusinessException {
		businessRefIncType.deleteRefIncType(obj);
	}
}
