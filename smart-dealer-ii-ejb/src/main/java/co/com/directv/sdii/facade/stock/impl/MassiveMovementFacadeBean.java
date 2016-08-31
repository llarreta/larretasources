package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MassiveMovementFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MassiveMovementVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD MassiveMovement
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.MassiveMovementFacadeLocal
 */
@Stateless(name="MassiveMovementFacadeLocal",mappedName="ejb/MassiveMovementFacadeLocal")
public class MassiveMovementFacadeBean implements MassiveMovementFacadeBeanLocal {

		
    @EJB(name="MassiveMovementBusinessBeanLocal", beanInterface=MassiveMovementBusinessBeanLocal.class)
    private MassiveMovementBusinessBeanLocal businessMassiveMovement;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MassiveMovementFacadeLocal#getAllMassiveMovements()
     */
    public List<MassiveMovementVO> getAllMassiveMovements() throws BusinessException {
    	return businessMassiveMovement.getAllMassiveMovements();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MassiveMovementFacadeLocal#getMassiveMovementsByID(java.lang.Long)
     */
    public MassiveMovementVO getMassiveMovementByID(Long id) throws BusinessException {
    	return businessMassiveMovement.getMassiveMovementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementFacadeLocal#createMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	public void createMassiveMovement(MassiveMovementVO obj) throws BusinessException {
		businessMassiveMovement.createMassiveMovement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementFacadeLocal#updateMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	public void updateMassiveMovement(MassiveMovementVO obj) throws BusinessException {
		businessMassiveMovement.updateMassiveMovement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementFacadeLocal#deleteMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	public void deleteMassiveMovement(MassiveMovementVO obj) throws BusinessException {
		businessMassiveMovement.deleteMassiveMovement(obj);
	}
}
