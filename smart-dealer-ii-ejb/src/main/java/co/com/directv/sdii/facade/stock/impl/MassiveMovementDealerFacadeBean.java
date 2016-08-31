package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MassiveMovementDealerFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MassiveMovementDealerVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD MassiveMovementDealer
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.MassiveMovementDealerFacadeLocal
 */
@Stateless(name="MassiveMovementDealerFacadeLocal",mappedName="ejb/MassiveMovementDealerFacadeLocal")
public class MassiveMovementDealerFacadeBean implements MassiveMovementDealerFacadeBeanLocal {

		
    @EJB(name="MassiveMovementDealerBusinessBeanLocal", beanInterface=MassiveMovementDealerBusinessBeanLocal.class)
    private MassiveMovementDealerBusinessBeanLocal businessMassiveMovementDealer;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MassiveMovementDealerFacadeLocal#getAllMassiveMovementDealers()
     */
    public List<MassiveMovementDealerVO> getAllMassiveMovementDealers() throws BusinessException {
    	return businessMassiveMovementDealer.getAllMassiveMovementDealers();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MassiveMovementDealerFacadeLocal#getMassiveMovementDealersByID(java.lang.Long)
     */
    public MassiveMovementDealerVO getMassiveMovementDealerByID(Long id) throws BusinessException {
    	return businessMassiveMovementDealer.getMassiveMovementDealerByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementDealerFacadeLocal#createMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	public void createMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
		businessMassiveMovementDealer.createMassiveMovementDealer(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementDealerFacadeLocal#updateMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	public void updateMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
		businessMassiveMovementDealer.updateMassiveMovementDealer(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MassiveMovementDealerFacadeLocal#deleteMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	public void deleteMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
		businessMassiveMovementDealer.deleteMassiveMovementDealer(obj);
	}


	@Override
	public List<MassiveMovementDealerVO> getMassiveMovementDealerByMovementID(
			Long id) throws BusinessException {
		return businessMassiveMovementDealer.getMassiveMovementDealerByMovementID(id);
	}
}
