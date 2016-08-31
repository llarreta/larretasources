package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessDetailException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersFacadeBusinessBeanLocal;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * EJB de tipo Stateless que implementa las operaciones
 * de negocio.
 * Entidad Dealers
 * @author Joan Lopez
 * @version 1.0
 * @see DealersBusinessLocal
 */
@Stateless(name="DealersFacadeBusinessBeanLocal",mappedName="ejb/DealersFacadeBusinessBeanLocal")
public class DealersFacadeBusinessBean implements DealersFacadeBusinessBeanLocal {
    
	@EJB(name="DealersBusinessBeanLocal",beanInterface=DealersBusinessBeanLocal.class)
    private DealersBusinessBeanLocal dealersBusinessLocal;

   /*
    * (non-Javadoc)
    * @see co.com.directv.sdii.facade.dealers.DealersFacadeBusinessBeanLocal#getDealerCodes()
    */
    public List<DealerVO> getDealerCodes(Long countryId) throws BusinessException {  
       return  dealersBusinessLocal.getDealerCodes(countryId);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBusinessBeanLocal#getDealer(int, java.lang.String)
     */
    public DealerVO getDealer(Long dealerCode,String depodCode, Long countryId) throws BusinessException{
       
        return dealersBusinessLocal.getDealer(dealerCode, depodCode, countryId);
    }

}
