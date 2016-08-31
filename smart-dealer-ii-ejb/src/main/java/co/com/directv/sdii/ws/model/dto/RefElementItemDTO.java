package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;

public class RefElementItemDTO implements Serializable {

	private static final long serialVersionUID = -6550959440982000732L;
	
	private Serialized serialized;
	private NotSerialized notSerialized;
	private Long referenceElementItemId;
	private Double quantityInReference;
	
	public RefElementItemDTO() {
	}

	public RefElementItemDTO(Serialized element, Long referenceElementItemId, Double quantityInReference) {
		this.serialized = element;		
		this.referenceElementItemId = referenceElementItemId;
		this.quantityInReference = quantityInReference;
	}
	
	public RefElementItemDTO(NotSerialized element, Long referenceElementItemId, Double quantityInReference) {
		this.notSerialized = element;		
		this.referenceElementItemId = referenceElementItemId;
		this.quantityInReference = quantityInReference;
	}
	
	public Serialized getSerialized() {
		return serialized;
	}

	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}

	public NotSerialized getNotSerialized() {
		return notSerialized;
	}

	public void setNotSerialized(NotSerialized notSerialized) {
		this.notSerialized = notSerialized;
	}

	public Long getReferenceElementItemId() {
		return referenceElementItemId;
	}

	public void setReferenceElementItemId(Long referenceElementItemId) {
		this.referenceElementItemId = referenceElementItemId;
	}

	public Double getQuantityInReference() {
		return quantityInReference;
	}

	public void setQuantityInReference(Double quantityInReference) {
		this.quantityInReference = quantityInReference;
	}

}
