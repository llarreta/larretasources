package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.BuildingFacadeBeanLocal;
import co.com.directv.sdii.facade.assign.BuildingFacadeBeanRemote;
import co.com.directv.sdii.model.vo.BuildingVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Building
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.BuildingFacadeLocal
 */
@Stateless(name="BuildingFacadeBean")
@Local({BuildingBusinessBeanLocal.class})
@Remote({BuildingFacadeBeanRemote.class})
public class BuildingFacadeBean implements BuildingFacadeBeanLocal, BuildingFacadeBeanRemote {

		
    @EJB(name="BuildingBusinessBeanLocal", beanInterface=BuildingBusinessBeanLocal.class)
    private BuildingBusinessBeanLocal businessBuilding;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.BuildingFacadeLocal#getAllBuildings()
     */
    public List<BuildingVO> getAllBuildings() throws BusinessException {
    	return businessBuilding.getAllBuildings();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.BuildingFacadeLocal#getBuildingsByID(java.lang.Long)
     */
    public BuildingVO getBuildingByID(Long id) throws BusinessException {
    	return businessBuilding.getBuildingByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.BuildingFacadeLocal#createBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	public void createBuilding(BuildingVO obj) throws BusinessException {
		businessBuilding.createBuilding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.BuildingFacadeLocal#updateBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	public void updateBuilding(BuildingVO obj) throws BusinessException {
		businessBuilding.updateBuilding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.BuildingFacadeLocal#deleteBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	public void deleteBuilding(BuildingVO obj) throws BusinessException {
		businessBuilding.deleteBuilding(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.BuildingFacadeBeanRemote#updateDealerBuilding(java.lang.Long)
	 */
	public void updateDealerBuilding(Long countryId) throws BusinessException {
		businessBuilding.updateDealerBuilding(countryId);
	}
}
