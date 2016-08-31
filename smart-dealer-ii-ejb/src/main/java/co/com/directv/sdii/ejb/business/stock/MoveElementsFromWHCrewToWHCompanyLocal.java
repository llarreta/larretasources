package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.MassiveMovement;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * Interfaz, de la operacion de mover elementos de las cuadrillas a la bodega de stock de la 
 * compania 
 * 
 * Fecha de Creación: 19/08/2011
 * @author hcorredor <a href="mailto:hcorredor@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MoveElementsFromWHCrewToWHCompanyLocal {

	/**
	 * 
	 * Metodo: Encargado de mover los elementos de las bodegas de las cuadrillas a las bodega de Stock
	 * de la compania. UC Inv131
	 * @param companies
	 * @throws BusinessException
	 * @author
	 */
	public void moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, User user, String WHTypeCode, MassiveMovement process) throws BusinessException;

}
