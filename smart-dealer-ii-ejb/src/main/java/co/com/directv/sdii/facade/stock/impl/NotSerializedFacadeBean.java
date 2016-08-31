package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.NotSerializedFacadeBeanLocal;
import co.com.directv.sdii.model.vo.NotSerializedVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD NotSerialized
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.NotSerializedFacadeLocal
 */
@Stateless(name="NotSerializedFacadeLocal",mappedName="ejb/NotSerializedFacadeLocal")
public class NotSerializedFacadeBean implements NotSerializedFacadeBeanLocal {

		
    @EJB(name="NotSerializedBusinessBeanLocal", beanInterface=NotSerializedBusinessBeanLocal.class)
    private NotSerializedBusinessBeanLocal businessNotSerialized;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerializedFacadeLocal#getAllNotSerializeds()
     */
    public List<NotSerializedVO> getAllNotSerializeds() throws BusinessException {
    	return businessNotSerialized.getAllNotSerializeds();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerializedFacadeLocal#getNotSerializedsByID(java.lang.Long)
     */
    public NotSerializedVO getNotSerializedByID(Long id) throws BusinessException {
    	return businessNotSerialized.getNotSerializedByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerializedFacadeLocal#createNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	public void createNotSerialized(NotSerializedVO obj) throws BusinessException {
		businessNotSerialized.createNotSerialized(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerializedFacadeLocal#updateNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	public void updateNotSerialized(NotSerializedVO obj) throws BusinessException {
		businessNotSerialized.updateNotSerialized(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerializedFacadeLocal#deleteNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	public void deleteNotSerialized(NotSerializedVO obj) throws BusinessException {
		businessNotSerialized.deleteNotSerialized(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.NotSerializedFacadeBeanLocal#getNotSerializedByImportLogId(java.lang.Long)
	 */
	public List<NotSerializedVO> getNotSerializedByImportLogId(Long importLogId)
			throws BusinessException {
		return businessNotSerialized.getNotSerializedByImportLogId(importLogId);
	}
}
