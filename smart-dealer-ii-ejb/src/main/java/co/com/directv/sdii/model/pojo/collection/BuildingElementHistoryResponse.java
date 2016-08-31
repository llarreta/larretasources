/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

/**
 * 
 * Transporta objetos  BuildingElementHistoryVO entre las capas
 * de DAO y business.
 * 
 * Fecha de Creación: 9/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class BuildingElementHistoryResponse extends CollectionBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5832781211383598728L;
	
	
	private  List<Object[]> buildingElements;
	
	public List<Object[]> getBuildingElements() {
		return buildingElements;
	}

	public void setBuildingElements(List<Object[]> buildingElements) {
		this.buildingElements = buildingElements;
	}	
}
