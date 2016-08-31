package co.com.directv.sdii.facade.assign;

import javax.ejb.Remote;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Define las operaciones que serán usadas desde el work de building.
 * 
 * Fecha de Creación: 26/05/2011
 * @author ssanabri <a href="mailto:facundo.sanabria@everis.com">e-mail</a>
 * @version 4.2.0
 * 
 * @see     
 */
@Remote
public interface BuildingFacadeBeanRemote {

	/**
	 * Método: Encargado de obtener la información de la relacion entre <br/>
	 * la compañía y el edificio, y también actualiza la información del edificio en caso de haber sido <br/>
	 * modificada desde ESB y guardarla en HSP.
	 * @param countryId identificador del pais
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void updateDealerBuilding(Long countryId) throws BusinessException;
	
}
