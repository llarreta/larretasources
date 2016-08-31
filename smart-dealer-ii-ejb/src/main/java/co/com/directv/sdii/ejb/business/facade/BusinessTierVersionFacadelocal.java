package co.com.directv.sdii.ejb.business.facade;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessTierVersionVO;

/**
 * 
 * Interfaz de la fachada para invocar las operaciones de 
 * negocio para obtener la version de la capa de negocio. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface BusinessTierVersionFacadelocal {

	/**
	 * 
	 * Metodo: Retorna la version de la capa de negocio
	 * @return BusinessTierVersionVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public BusinessTierVersionVO getBusinessVersion() throws BusinessException;
	
	public void test6008(String woCode) throws BusinessException;
	
}
