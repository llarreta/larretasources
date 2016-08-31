package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DealerStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealerStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerStatusVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad DealerStatus 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.DealerStatusCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DealerStatusFacadeBeanLocal
 */
@Stateless(name="DealerStatusFacadeBeanLocal",mappedName="ejb/DealerStatusFacadeBeanLocal")
public class DealerStatusFacadeBean implements DealerStatusFacadeBeanLocal {

    @EJB(name="DealerStatusCRUDBeanLocal",beanInterface=DealerStatusCRUDBeanLocal.class)
    private DealerStatusCRUDBeanLocal business;

    /**
     * Obtiene los status de los Dealers actuales
     * @return - List<DealerStatusVO>
     * @throws BusinessException
     */
    public List<DealerStatusVO> getAllDealerStatus() throws BusinessException {
        return business.getAllDealerStatus();
    }

    /**
     * Obtiene un StatusDealer con el codigo especificado
     * @param code - String
     * @return - DealerStatusVO
     * @throws BusinessException
     */
    public DealerStatusVO getDealerStatusByCode(String code) throws BusinessException {
        return business.getDealerStatusByCode(code);
    }

     /**
     * Obtiene un StatusDealer con el id especificado
     * @param id - Long
     * @return - DealerStatusVO
     * @throws BusinessException
     */
    public DealerStatusVO getDealerStatusByID(Long id) throws BusinessException {
        return business.getDealerStatusByID(id);
    }
}
