package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.pojo.ReportedElement;

public class ReportedElementVO extends ReportedElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -320457613963475563L;

	private SerializedVO serialized;
	private NotSerializedVO notSerialized;
	
	//elementos que se adicionan a la remisión a causa del elemento reportado
	private List<ReferenceElementItemVO> addedRefElements = new ArrayList<ReferenceElementItemVO>();
	
	public SerializedVO getSerialized() {
		return serialized;
	}
	public void setSerialized(SerializedVO serialized) {
		this.serialized = serialized;
	}
	public NotSerializedVO getNotSerialized() {
		return notSerialized;
	}
	public void setNotSerialized(NotSerializedVO notSerialized) {
		this.notSerialized = notSerialized;
	}
	
	
	
	public List<ReferenceElementItemVO> getAddedRefElements() {
		return addedRefElements;
	}
	public void setAddedRefElements(List<ReferenceElementItemVO> addedRefElements) {
		this.addedRefElements = addedRefElements;
	}
	public void clearAddedElements() {
		addedRefElements.clear();
	}
	public void addAddedElement(ReferenceElementItemVO refElementVO) {
		addedRefElements.add(refElementVO);
	}
	
}
