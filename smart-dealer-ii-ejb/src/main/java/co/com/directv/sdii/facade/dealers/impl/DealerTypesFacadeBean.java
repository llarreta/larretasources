package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealerTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad DealerTypes 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.DealerTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DealerTypesFacadeBeanLocal
 */
@Stateless(name="DealerTypesFacadeBeanLocal",mappedName="ejb/DealerTypesFacadeBeanLocal")
public class DealerTypesFacadeBean implements DealerTypesFacadeBeanLocal {

    @EJB(name="DealerTypesCRUDBeanLocal",beanInterface=DealerTypesCRUDBeanLocal.class)
    private DealerTypesCRUDBeanLocal business;

   /**
     * Obtiene todos los tipos de dealers del sistema
     * @return - List<DealerTypesVO>
     * @throws BusinessException
     */
    public List<DealerTypeVO> getAllDealerTypes() throws BusinessException {
        return business.getAllDealerTypes();
    }

   /**
     * Obtiene un tipo de dealer de acuerdo al codigo especificado
     * @param code - String
     * @return - DealerTypesVO
     * @throws BusinessException
     */
    public DealerTypeVO getDealerTypesByCode(String code) throws BusinessException {
        return business.getDealerTypesByCode(code);
    }

   /**
     * Obtiene un tipo de dealer con el id especificado
     * @param id - Long
     * @return - DealerTypesVO
     * @throws BusinessException
     * @author jalopez
     */
    public DealerTypeVO getDealerTypesByID(Long id) throws BusinessException {
        return business.getDealerTypesByID(id);
    }

	/**
	 * Crea un dealer type en el sistema
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param obj
	 */
    @Override
	public void createDealerType(DealerTypeVO obj) throws BusinessException {
		 business.createDealerType(obj);
		
	}
	/**
	 * elimina un dealer type en el sistema
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param obj
	 */
	@Override
	public void deleteDealerType(DealerTypeVO obj) throws BusinessException {
		business.deleteDealerType(obj);
		
	}
	/**
	 * actualiza un dealer type en el sistema
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param obj
	 */
	@Override
	public void updateDealerType(DealerTypeVO obj) throws BusinessException {
		business.updateDealerType(obj);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealerTypesFacadeBeanLocal#getDealerTypesByIsAlloc(java.lang.String)
	 */
	@Override
	public List<DealerTypeVO> getDealerTypesByIsAlloc(String isAlloc)
			throws BusinessException {
		return business.getDealerTypesByIsAlloc(isAlloc);
	}
}
