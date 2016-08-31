package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.dto.BuildingElementHistoryVO;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;

/**
 * 
 * Transporta objetos  BuildingElementHistoryVO entre las capas
 * de negocio y servicios.
 * 
 * Fecha de Creación: 9/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class BuildingElementHistoryDTO extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7320859849130569688L;

	private List<BuildingElementHistoryVO> buildingElementsVO;

	public List<BuildingElementHistoryVO> getBuildingElementsVO() {
		return buildingElementsVO;
	}

	public void setBuildingElementsVO(
			List<BuildingElementHistoryVO> buildingElementsVO) {
		this.buildingElementsVO = buildingElementsVO;
	}	
}
