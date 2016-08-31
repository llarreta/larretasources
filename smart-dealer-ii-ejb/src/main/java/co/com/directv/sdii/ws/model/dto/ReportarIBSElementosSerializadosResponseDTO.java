/**
 * Creado 15/03/2011 16:11:26
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;

/**
 * Encapsula la información de los elementos utilizados y recuperados y notificados efectivamente a IBS
 * 
 * Fecha de Creación: 15/03/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ReportarIBSElementosSerializadosResponseDTO implements
		Serializable {
	
	public ReportarIBSElementosSerializadosResponseDTO() {
		super();
		serializedUsedAndNotified2Ibs = new ArrayList<IBSMoveResourceBetweenCustAndDealerDTO>();
		serializedRecoveredAndNotified2Ibs = new ArrayList<IBSMoveResourceBetweenCustAndDealerDTO>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1514325261699465775L;

	private List<IBSMoveResourceBetweenCustAndDealerDTO> serializedUsedAndNotified2Ibs;
	
	private List<IBSMoveResourceBetweenCustAndDealerDTO> serializedRecoveredAndNotified2Ibs;

	public List<IBSMoveResourceBetweenCustAndDealerDTO> getSerializedUsedAndNotified2Ibs() {
		return serializedUsedAndNotified2Ibs;
	}

	public void setSerializedUsedAndNotified2Ibs(
			List<IBSMoveResourceBetweenCustAndDealerDTO> serializedUsedAndNotified2Ibs) {
		this.serializedUsedAndNotified2Ibs = serializedUsedAndNotified2Ibs;
	}

	public List<IBSMoveResourceBetweenCustAndDealerDTO> getSerializedRecoveredAndNotified2Ibs() {
		return serializedRecoveredAndNotified2Ibs;
	}

	public void setSerializedRecoveredAndNotified2Ibs(
			List<IBSMoveResourceBetweenCustAndDealerDTO> serializedRecoveredAndNotified2Ibs) {
		this.serializedRecoveredAndNotified2Ibs = serializedRecoveredAndNotified2Ibs;
	}
}
