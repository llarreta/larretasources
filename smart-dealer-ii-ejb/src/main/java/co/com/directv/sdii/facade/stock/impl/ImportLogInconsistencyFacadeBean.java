package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImportLogInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogInconsistencyFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImportLogInconsistency
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogInconsistencyFacadeLocal
 */
@Stateless(name="ImportLogInconsistencyFacadeLocal",mappedName="ejb/ImportLogInconsistencyFacadeLocal")
public class ImportLogInconsistencyFacadeBean implements ImportLogInconsistencyFacadeBeanLocal {

		
    @EJB(name="ImportLogInconsistencyBusinessBeanLocal", beanInterface=ImportLogInconsistencyBusinessBeanLocal.class)
    private ImportLogInconsistencyBusinessBeanLocal businessImportLogInconsistency;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImportLogInconsistencyFacadeLocal#getAllImportLogInconsistencys()
     */
    public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException {
    	return businessImportLogInconsistency.getAllImportLogInconsistencys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImportLogInconsistencyFacadeLocal#getImportLogInconsistencysByID(java.lang.Long)
     */
    public ImportLogInconsistencyVO getImportLogInconsistencyByID(Long id) throws BusinessException {
    	return businessImportLogInconsistency.getImportLogInconsistencyByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogInconsistencyFacadeLocal#createImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	public void createImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
		businessImportLogInconsistency.createImportLogInconsistency(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogInconsistencyFacadeLocal#updateImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	public void updateImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
		businessImportLogInconsistency.updateImportLogInconsistency(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImportLogInconsistencyFacadeLocal#deleteImportLogInconsistency(co.com.directv.sdii.model.vo.ImportLogInconsistencyVO)
	 */
	public void deleteImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException {
		businessImportLogInconsistency.deleteImportLogInconsistency(obj);
	}
	
}
