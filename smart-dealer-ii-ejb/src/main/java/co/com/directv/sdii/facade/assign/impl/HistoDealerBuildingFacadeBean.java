package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerBuildingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerBuildingFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerBuildingVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerBuilding
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerBuildingFacadeLocal
 */
@Stateless(name="HistoDealerBuildingFacadeLocal",mappedName="ejb/HistoDealerBuildingFacadeLocal")
public class HistoDealerBuildingFacadeBean implements HistoDealerBuildingFacadeBeanLocal {

		
    @EJB(name="HistoDealerBuildingBusinessBeanLocal", beanInterface=HistoDealerBuildingBusinessBeanLocal.class)
    private HistoDealerBuildingBusinessBeanLocal businessHistoDealerBuilding;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerBuildingFacadeLocal#getAllHistoDealerBuildings()
     */
    public List<HistoDealerBuildingVO> getAllHistoDealerBuildings() throws BusinessException {
    	return businessHistoDealerBuilding.getAllHistoDealerBuildings();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerBuildingFacadeLocal#getHistoDealerBuildingsByID(java.lang.Long)
     */
    public HistoDealerBuildingVO getHistoDealerBuildingByID(Long id) throws BusinessException {
    	return businessHistoDealerBuilding.getHistoDealerBuildingByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerBuildingFacadeLocal#createHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	public void createHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
		businessHistoDealerBuilding.createHistoDealerBuilding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerBuildingFacadeLocal#updateHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	public void updateHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
		businessHistoDealerBuilding.updateHistoDealerBuilding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerBuildingFacadeLocal#deleteHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	public void deleteHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
		businessHistoDealerBuilding.deleteHistoDealerBuilding(obj);
	}
}
