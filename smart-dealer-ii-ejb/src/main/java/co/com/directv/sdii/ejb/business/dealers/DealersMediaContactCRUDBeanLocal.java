package co.com.directv.sdii.ejb.business.dealers;

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
public interface DealersMediaContactCRUDBeanLocal {
	
	public void createDealerMediaContact(DealerMediaContactVO obj) throws BusinessException;


	public DealerMediaContactVO getDealersMediaContactByID(Long id) throws BusinessException;
	
	
	public void updateDealersMediaContact(DealerMediaContactVO obj) throws BusinessException;
	
	
	public void deleteDealersMediaContact(DealerMediaContactVO obj) throws BusinessException;
	
	
	public List<DealerMediaContactVO> getAllDealersMediaContact() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los medios de contacto de un dealer poniendo en nulo la relacion con el dealer
	 * @param dealerId id del dealer
	 * @return List<DealerMediaContactVO> Lista con el id del dealer en nulo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerMediaContactVO> fillDealerMediaContacts(Long dealerId) throws BusinessException;
}
