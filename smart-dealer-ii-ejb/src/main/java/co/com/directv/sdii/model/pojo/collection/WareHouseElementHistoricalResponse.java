/**
 * 
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.WareHouseElementHistoricalDTO;

/**
 * 
 * Transporta objetos de tipo WareHouseElementHistoricalDTO entre las
 * capas de Business y Services.
 * 
 * Fecha de Creación: 10/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WareHouseElementHistoricalResponse extends	CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4059149468436551137L;
	
	private List<WareHouseElementHistoricalDTO> wareHouseHistorical;
	
	public List<WareHouseElementHistoricalDTO> getWareHouseHistorical() {
		return wareHouseHistorical;
	}
	public void setWareHouseHistorical(
			List<WareHouseElementHistoricalDTO> wareHouseHistorical) {
		this.wareHouseHistorical = wareHouseHistorical;
	}

	
}
