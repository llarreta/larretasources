package co.com.directv.sdii.ws.business.assign;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.BuildingDTO;

/**
 * Interfaz que define los métodos utilizados en todo lo referido a edificios. 
 * Serán llamados desde ESB para actualizar información de los buildings y su 
 * relación con los dealers.
 * 
 * @author ssanabri
 *
 */

@WebService(name="BuildingServices",targetNamespace="http://assign.business.ws.sdii.directv.com.co/")
public interface IBuildingWS {

	/**
	 * Método utilizado para actualizar/borrar los buildings y su relacion con los dealers que 
	 * atenderan el building. Esta información se guardará y luego será utilizada por el asignador automático.
	 * 
	 * @param List<BuildingDTO> la lista de buildings que debe actualizarse y un caracter que indicará si es Update/Insert (U) o Delete (D)
	 * Si no se coloca el parámetro de la accion a tomar por default se interpretará como un Update.
	 * @return Boolean devuelve true si la información se guardó correctamente. 
	 * false si la información ha sufrido un inconveniente y no se ha guardado correctamente.
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateBuildings", action="updateBuildings")
	public Boolean updateBuildings(@WebParam(name="buildingDTOList")List<BuildingDTO> buildingDTOList) throws BusinessException;
	
}
