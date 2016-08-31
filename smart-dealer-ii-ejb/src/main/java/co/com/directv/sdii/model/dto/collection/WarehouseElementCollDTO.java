/**
 * Creado 3/01/2011 15:07:20
 */
package co.com.directv.sdii.model.dto.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 3/01/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WarehouseElementCollDTO extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7806216813330878030L;

	private List<WarehouseElementVO> collection;

	public List<WarehouseElementVO> getCollection() {
		return collection;
	}

	public void setCollection(List<WarehouseElementVO> collection) {
		this.collection = collection;
	}
}
