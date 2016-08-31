package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInfoIndividualIdentifiedDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	private String cardNr;
	private String scan;
	public String getCardNr() {
		return cardNr;
	}
	public void setCardNr(String cardNr) {
		this.cardNr = cardNr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}	
}
