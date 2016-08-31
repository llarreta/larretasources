package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;

public class NotSerRefElementItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1213959961737867945L;
	
	public NotSerRefElementItemDTO() {
		super();
	}
	
	public NotSerRefElementItemDTO(NotSerialized notSerialized,
			ReferenceElementItem refElementItem) {
		super();
		this.notSerialized = notSerialized;
		this.refElementItem = refElementItem;
	}
	private NotSerialized notSerialized;
	private ReferenceElementItem refElementItem;
	
	public NotSerialized getNotSerialized() {
		return notSerialized;
	}
	public void setNotSerialized(NotSerialized notSerialized) {
		this.notSerialized = notSerialized;
	}
	public ReferenceElementItem getRefElementItem() {
		return refElementItem;
	}
	public void setRefElementItem(ReferenceElementItem refElementItem) {
		this.refElementItem = refElementItem;
	}
	
}
