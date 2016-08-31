package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.PostalCodeVO;

public class PostalCodeResponse extends CollectionBase implements Serializable {

	private static final long serialVersionUID = -5403791339901415521L;

	private List<PostalCodeVO> postalCodesVO;
	private List<PostalCode> postalCodes;
	
	public List<PostalCodeVO> getPostalCodesVO() {
		return postalCodesVO;
	}
	public void setPostalCodesVO(List<PostalCodeVO> postalCodesVO) {
		this.postalCodesVO = postalCodesVO;
	}
	public List<PostalCode> getPostalCodes() {
		return postalCodes;
	}
	public void setPostalCodes(List<PostalCode> postalCodes) {
		this.postalCodes = postalCodes;
	}
	
	
	
}
