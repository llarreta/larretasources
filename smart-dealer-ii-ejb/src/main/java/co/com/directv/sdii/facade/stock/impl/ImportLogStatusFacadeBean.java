package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImportLogStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImportLogStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogStatusFacadeLocal
 */
@Stateless(name="ImportLogStatusFacadeLocal",mappedName="ejb/ImportLogStatusFacadeLocal")
public class ImportLogStatusFacadeBean implements ImportLogStatusFacadeBeanLocal {

		
    @EJB(name="ImportLogStatusBusinessBeanLocal", beanInterface=ImportLogStatusBusinessBeanLocal.class)
    private ImportLogStatusBusinessBeanLocal businessImportLogStatus;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImportLogStatusFacadeLocal#getAllImportLogStatuss()
     */
    public List<ImportLogStatusVO> getAllImportLogStatuss() throws BusinessException {
    	return businessImportLogStatus.getAllImportLogStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImportLogStatusFacadeLocal#getImportLogStatussByID(java.lang.Long)
     */
    public ImportLogStatusVO getImportLogStatusByID(Long id) throws BusinessException {
    	return businessImportLogStatus.getImportLogStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogStatusFacadeLocal#createImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	public void createImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
		businessImportLogStatus.createImportLogStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogStatusFacadeLocal#updateImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	public void updateImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
		businessImportLogStatus.updateImportLogStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogStatusFacadeLocal#deleteImportLogStatus(co.com.directv.sdii.model.vo.ImportLogStatusVO)
	 */
	public void deleteImportLogStatus(ImportLogStatusVO obj) throws BusinessException {
		businessImportLogStatus.deleteImportLogStatus(obj);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImportLogStatusFacadeBeanLocal#getImportLogStatusByCode(java.lang.String)
	 */
	public ImportLogStatusVO getImportLogStatusByCode(String code)
			throws BusinessException {
		return businessImportLogStatus.getImportLogStatusByCode(code);
	}
}
