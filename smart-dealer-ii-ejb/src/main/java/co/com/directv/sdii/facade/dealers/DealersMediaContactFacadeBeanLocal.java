package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;

/**
 * 
 * Iterface para la gestion del CRUD la Entidad DealersMediaContactCRUDBean
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealersMediaContactFacadeBeanLocal {
	
	public void createDealerMediaContact(DealerMediaContactVO obj) throws BusinessException;


	public DealerMediaContactVO getDealersMediaContactByID(Long id) throws BusinessException;
	
	
	public void updateDealersMediaContact(DealerMediaContactVO obj) throws BusinessException;
	
	
	public void deleteDealersMediaContact(DealerMediaContactVO obj) throws BusinessException;
	
	
	public List<DealerMediaContactVO> getAllDealersMediaContact() throws BusinessException;
	
}
