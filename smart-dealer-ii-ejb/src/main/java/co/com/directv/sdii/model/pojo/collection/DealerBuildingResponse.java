package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.DealerBulding;
import co.com.directv.sdii.model.vo.DealerBuldingVO;

public class DealerBuildingResponse extends CollectionBase implements Serializable {

	private static final long serialVersionUID = -5403791339901415521L;

	private List<DealerBuldingVO> dealerBuildingsVO;
	private List<DealerBulding> dealerBuildings;
	
	public List<DealerBuldingVO> getDealerBuildingsVO() {
		return dealerBuildingsVO;
	}
	public void setDealerBuildingsVO(List<DealerBuldingVO> dealerBuildingsVO) {
		this.dealerBuildingsVO = dealerBuildingsVO;
	}
	public List<DealerBulding> getDealerBuildings() {
		return dealerBuildings;
	}
	public void setDealerBuildings(List<DealerBulding> dealerBuildings) {
		this.dealerBuildings = dealerBuildings;
	}
	
}
