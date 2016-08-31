package co.com.directv.sdii.ws.business.assign.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.BuildingDTO;
import co.com.directv.sdii.ws.business.assign.IBuildingWS;


/**
 * Interfaz que define los métodos utilizados en todo lo referido a edificios. 
 * Serán llamados desde ESB para actualizar información de los buildings y su 
 * relación con los dealers.
 * 
 * @author ssanabri
 *
 */
@MTOM
@WebService(serviceName="BuildingServices",
		endpointInterface="co.com.directv.sdii.ws.business.assign.IBuildingWS",
		targetNamespace="http://assign.business.ws.sdii.directv.com.co/",
		portName="BuildingPort")
@Stateless()
public class BuildingWS implements IBuildingWS{

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IBuildingWS#updateBuildings(java.util.List)
	 */
	@Override
	public Boolean updateBuildings(List<BuildingDTO> buildingDTOList) throws BusinessException {
		//Este servicio debe ser implementado. Se ha creado asi para ya tener la definicion del servicio.
		
		return new Boolean(true);
	}

}