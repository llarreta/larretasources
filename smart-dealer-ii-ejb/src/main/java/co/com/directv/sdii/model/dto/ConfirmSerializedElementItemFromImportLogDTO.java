/**
 * Creado 09/08/2011 14:32:44
 */
package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * Encapsula la información para la confirmación 
 * 
 * Fecha de Creación: 09/08/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ConfirmSerializedElementItemFromImportLogDTO implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6736050468309882090L;

	/**
	 * identificador del registro de importación
	 */
	private Long impotLogId;
	
	/**
	 * Identificador de la compañía operador logístico
	 */
	private Long logisticOperatorDealerId;
	
	/**
	 * identificador de la bodega de disponibles
	 */
	private Long availableWhId;
	
	/**
	 * Identificador de la bodega de control de calidad
	 */
	private Long qualityControlWhId;
	
	/**
	 * Identificador del item del registro de importación que se va a confirmar
	 */
	private Long importLogItemId;
	
	/**
	 * identificador del usuario que realiza la operación
	 */
	private Long userId;
	
	/**
	 * Determina si se debe enviar el elemento a la bodega de control de calidad
	 */
	private boolean send2QualityCtrlWh; 

	public Long getImpotLogId() {
		return impotLogId;
	}

	public void setImpotLogId(Long impotLogId) {
		this.impotLogId = impotLogId;
	}

	public Long getLogisticOperatorDealerId() {
		return logisticOperatorDealerId;
	}

	public void setLogisticOperatorDealerId(Long logisticOperatorDealerId) {
		this.logisticOperatorDealerId = logisticOperatorDealerId;
	}

	public Long getAvailableWhId() {
		return availableWhId;
	}

	public void setAvailableWhId(Long availableWhId) {
		this.availableWhId = availableWhId;
	}

	public Long getQualityControlWhId() {
		return qualityControlWhId;
	}

	public void setQualityControlWhId(Long qualityControlWhId) {
		this.qualityControlWhId = qualityControlWhId;
	}

	public Long getImportLogItemId() {
		return importLogItemId;
	}

	public void setImportLogItemId(Long importLogItemId) {
		this.importLogItemId = importLogItemId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isSend2QualityCtrlWh() {
		return send2QualityCtrlWh;
	}

	public void setSend2QualityCtrlWh(boolean send2QualityCtrlWh) {
		this.send2QualityCtrlWh = send2QualityCtrlWh;
	}
}
