/**
 * Creado 3/01/2011 13:08:45
 */
package co.com.directv.sdii.model.dto.collection;

import java.util.List;

import co.com.directv.sdii.model.dto.NotSerializedAjustmentVO;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 3/01/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class NotSerializedAjustmentCollDTO extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2297174474612221717L;

	private List<NotSerializedAjustmentVO> collection;

	public List<NotSerializedAjustmentVO> getCollection() {
		return collection;
	}

	public void setCollection(List<NotSerializedAjustmentVO> collection) {
		this.collection = collection;
	}

	public NotSerializedAjustmentCollDTO() {
		super();
	}
	
}
