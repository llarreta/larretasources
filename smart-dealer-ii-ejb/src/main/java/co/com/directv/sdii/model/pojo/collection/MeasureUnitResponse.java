package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.vo.MeasureUnitVO;

public class MeasureUnitResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 316664934116848557L;
	
	private List<MeasureUnit> measureUnit;
    private List<MeasureUnitVO> measureUnitVO;
    
	public List<MeasureUnit> getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(List<MeasureUnit> measureUnit) {
		this.measureUnit = measureUnit;
	}
	public List<MeasureUnitVO> getMeasureUnitVO() {
		return measureUnitVO;
	}
	public void setMeasureUnitVO(List<MeasureUnitVO> measureUnitVO) {
		this.measureUnitVO = measureUnitVO;
	}
    
    
    

}
