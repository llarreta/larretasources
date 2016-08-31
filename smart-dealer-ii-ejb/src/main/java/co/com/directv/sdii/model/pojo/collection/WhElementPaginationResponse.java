/**
 * Creado 01/02/2011 14:12:41
 */
package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.WarehouseElement;

/**
 * Encapsula la información de respuesta de paginación para un conjunto de
 * elementos de bodega
 * 
 * Fecha de Creación: 01/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseElement
 */
public class WhElementPaginationResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5949653715549560886L;
	

	private List<WarehouseElement> collection;

	public List<WarehouseElement> getCollection() {
		return collection;
	}

	public void setCollection(List<WarehouseElement> collection) {
		this.collection = collection;
	}
	
	
	
}
