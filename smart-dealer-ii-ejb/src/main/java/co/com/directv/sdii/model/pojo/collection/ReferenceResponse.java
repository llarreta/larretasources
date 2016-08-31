/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.vo.ReferenceVO;

/**
 * @author jvelezmu
 *
 */
public class ReferenceResponse extends	CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3699652096021574264L;
	private List<Reference> references;
	private List<ReferenceVO> referencesVO;
	public List<Reference> getReferences() {
		return references;
	}
	public void setReferences(List<Reference> references) {
		this.references = references;
	}
	public List<ReferenceVO> getReferencesVO() {
		return referencesVO;
	}
	public void setReferencesVO(List<ReferenceVO> referencesVO) {
		this.referencesVO = referencesVO;
	}
	
	
	
	
}
