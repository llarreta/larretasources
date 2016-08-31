package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.InconsistencyTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD InconsistencyType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.InconsistencyTypeFacadeLocal
 */
@Stateless(name="InconsistencyTypeFacadeLocal",mappedName="ejb/InconsistencyTypeFacadeLocal")
public class InconsistencyTypeFacadeBean implements InconsistencyTypeFacadeBeanLocal {

		
    @EJB(name="InconsistencyTypeBusinessBeanLocal", beanInterface=InconsistencyTypeBusinessBeanLocal.class)
    private InconsistencyTypeBusinessBeanLocal businessInconsistencyType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.InconsistencyTypeFacadeLocal#getAllInconsistencyTypes()
     */
    public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException {
    	return businessInconsistencyType.getAllInconsistencyTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.InconsistencyTypeFacadeLocal#getInconsistencyTypesByID(java.lang.Long)
     */
    public InconsistencyTypeVO getInconsistencyTypeByID(Long id) throws BusinessException {
    	return businessInconsistencyType.getInconsistencyTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyTypeFacadeLocal#createInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	public void createInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
		businessInconsistencyType.createInconsistencyType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyTypeFacadeLocal#updateInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	public void updateInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
		businessInconsistencyType.updateInconsistencyType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyTypeFacadeLocal#deleteInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	public void deleteInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
		businessInconsistencyType.deleteInconsistencyType(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.InconsistencyTypeFacadeBeanLocal#getInconsistencyTypeByCode(java.lang.String)
	 */
	public InconsistencyTypeVO getInconsistencyTypeByCode(String code)
			throws BusinessException {
		return businessInconsistencyType.getInconsistencyTypeByCode(code);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.InconsistencyTypeFacadeBeanLocal#getActiveInconsistencyTypes()
	 */
	@Override
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes()
			throws BusinessException {
		return businessInconsistencyType.getActiveInconsistencyTypes();
	}
}
