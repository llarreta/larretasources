package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.vo.DealerConfCoverageVO;

/*
 * Req-0096 - Requerimiento Consolidado Asignador
 */
public class DealerConfCoverageResponse extends CollectionBase implements Serializable {


	private static final long serialVersionUID = -1430254941006882468L;
	private List<DealerConfCoverageVO> dealerConfCoveragesVO;
	private List<DealerConfCoverage> dealerConfCoverages;
	
	public List<DealerConfCoverageVO> getDealerConfCoveragesVO() {
		return dealerConfCoveragesVO;
	}
	public void setDealerConfCoveragesVO(
			List<DealerConfCoverageVO> dealerConfCoveragesVO) {
		this.dealerConfCoveragesVO = dealerConfCoveragesVO;
	}
	public List<DealerConfCoverage> getDealerConfCoverages() {
		return dealerConfCoverages;
	}
	public void setDealerConfCoverages(List<DealerConfCoverage> dealerConfCoverages) {
		this.dealerConfCoverages = dealerConfCoverages;
	}
	
    
}
