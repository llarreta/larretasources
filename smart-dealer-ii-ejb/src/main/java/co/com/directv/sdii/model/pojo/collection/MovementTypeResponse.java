package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.vo.MovementTypeVO;

public class MovementTypeResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3519497857829482535L;
	
	private List<MovementType> movType;
	private List<MovementTypeVO> movTypeVO;
	
	
	public List<MovementType> getMovType() {
		return movType;
	}
	public void setMovType(List<MovementType> movType) {
		this.movType = movType;
	}
	public List<MovementTypeVO> getMovTypeVO() {
		return movTypeVO;
	}
	public void setMovTypeVO(List<MovementTypeVO> movTypeVO) {
		this.movTypeVO = movTypeVO;
	}
	
	
	
	

}
