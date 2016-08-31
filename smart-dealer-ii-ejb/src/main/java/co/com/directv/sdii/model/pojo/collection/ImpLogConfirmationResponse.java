package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;

public class ImpLogConfirmationResponse extends CollectionBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045221223311755184L;

	private List<ImpLogConfirmation> impLogConfirmations;
	private List<ImpLogConfirmationVO> impLogConfirmationsVO;
	
	public List<ImpLogConfirmation> getImpLogConfirmations() {
		return impLogConfirmations;
	}
	public void setImpLogConfirmations(List<ImpLogConfirmation> impLogConfirmations) {
		this.impLogConfirmations = impLogConfirmations;
	}
	public List<ImpLogConfirmationVO> getImpLogConfirmationsVO() {
		return impLogConfirmationsVO;
	}
	public void setImpLogConfirmationsVO(
			List<ImpLogConfirmationVO> impLogConfirmationsVO) {
		this.impLogConfirmationsVO = impLogConfirmationsVO;
	}
}
