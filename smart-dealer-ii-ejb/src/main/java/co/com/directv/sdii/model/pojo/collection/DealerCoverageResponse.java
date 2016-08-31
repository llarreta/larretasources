package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.vo.DealerCoverageVO;

public class DealerCoverageResponse extends CollectionBase implements Serializable {

	private static final long serialVersionUID = -5403791339901415521L;

	private List<DealerCoverageVO> dealerCoveragesVO;
	private List<DealerCoverage> dealerCoverages;
	
	public List<DealerCoverageVO> getDealerCoveragesVO() {
		return dealerCoveragesVO;
	}
	public void setDealerCoveragesVO(List<DealerCoverageVO> dealerCoveragesVO) {
		this.dealerCoveragesVO = dealerCoveragesVO;
	}
	public List<DealerCoverage> getDealerCoverages() {
		return dealerCoverages;
	}
	public void setDealerCoverages(List<DealerCoverage> dealerCoverages) {
		this.dealerCoverages = dealerCoverages;
	}
	
}
