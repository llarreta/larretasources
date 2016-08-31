package co.com.directv.sdii.model.dto;

import co.com.directv.sdii.model.vo.ReferenceVO;

/**
 * Representa el objeto a enviar entre el cliente del web service y el server para encapsular
 * los nativos de los procesos de envio de remisiones
 * 
 * Fecha de Creacion: Aug 9, 2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 */

public class ReferenceShipmentDTO {

	private ReferenceVO reference;;
	
	public ReferenceShipmentDTO() {
		
	}

	public ReferenceVO getReference() {
		return reference;
	}

	public void setReference(ReferenceVO reference) {
		this.reference = reference;
	}

}
