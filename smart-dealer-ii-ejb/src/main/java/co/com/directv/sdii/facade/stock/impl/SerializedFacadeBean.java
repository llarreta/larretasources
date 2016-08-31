package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.SerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SerializedVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Serialized
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.SerializedFacadeLocal
 */
@Stateless(name="SerializedFacadeLocal",mappedName="ejb/SerializedFacadeLocal")
public class SerializedFacadeBean implements SerializedFacadeBeanLocal {

		
    @EJB(name="SerializedBusinessBeanLocal", beanInterface=SerializedBusinessBeanLocal.class)
    private SerializedBusinessBeanLocal businessSerialized;
    
          
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SerializedFacadeLocal#getAllSerializeds()
     */
    public List<SerializedVO> getAllSerializeds() throws BusinessException {
    	return businessSerialized.getAllSerializeds();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SerializedFacadeLocal#getSerializedsByID(java.lang.Long)
     */
    public SerializedVO getSerializedByID(Long id) throws BusinessException {
    	return businessSerialized.getSerializedByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SerializedFacadeLocal#createSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	public void createSerialized(SerializedVO obj) throws BusinessException {
		businessSerialized.createSerialized(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SerializedFacadeLocal#updateSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	public void updateSerialized(SerializedVO obj) throws BusinessException {
		businessSerialized.updateSerialized(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SerializedFacadeLocal#deleteSerialized(co.com.directv.sdii.model.vo.SerializedVO)
	 */
	public void deleteSerialized(SerializedVO obj) throws BusinessException {
		businessSerialized.deleteSerialized(obj);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal#getSerializedBySerial(java.lang.String)
	 */
	public SerializedVO getSerializedBySerial(String serial,Long countryId) throws BusinessException {
    	return businessSerialized.getSerializedBySerial(serial,countryId);
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal#getSerializedByElementId(java.lang.String)
	 */
	public List<SerializedVO> getSerializedByElementId(Long id)	throws BusinessException {
		return businessSerialized.getSerializedByElementId(id);
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal#saveSerializedList(java.util.List)
	 */
	public void saveSerializedList(List<SerializedVO> listSerialized,Long countryId) throws BusinessException {
    	businessSerialized.saveSerializedList(listSerialized,countryId);
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal#getLinkedSerializedBySerializedId(java.lang.Long)
	 */
	public List<SerializedVO> getLinkedSerializedBySerializedId(Long serialized)
			throws BusinessException {
		return businessSerialized.getLinkedSerializedBySerializedId(serialized) ;
	}

}
