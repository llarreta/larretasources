package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;

/**
 * 
 * Transporta objetos de tipo ReferenceElementItemsResponse
 * 
 * Fecha de Creación: 18/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse
 */
public class ReferenceElementItemsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5853569780880806360L;

	private ReferenceElementItemsResponse referenceResponseSerializesVO;
	private ReferenceElementItemsResponse referenceResponseNotSerializesVO;
	
	public ReferenceElementItemsResponse getReferenceResponseSerializesVO() {
		return referenceResponseSerializesVO;
	}
	public void setReferenceResponseSerializesVO(
			ReferenceElementItemsResponse referenceResponseSerializesVO) {
		this.referenceResponseSerializesVO = referenceResponseSerializesVO;
	}
	public ReferenceElementItemsResponse getReferenceResponseNotSerializesVO() {
		return referenceResponseNotSerializesVO;
	}
	public void setReferenceResponseNotSerializesVO(
			ReferenceElementItemsResponse referenceResponseNotSerializesVO) {
		this.referenceResponseNotSerializesVO = referenceResponseNotSerializesVO;
	}
}
