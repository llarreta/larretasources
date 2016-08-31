/**
 * Creado 3/01/2011 13:14:50
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;

import co.com.directv.sdii.business.AbstractPaginationBase;

/**
 * Encapsula la información de request para operaciones con paginación 
 * 
 * Fecha de Creación: 3/01/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class RequestCollectionInfo extends AbstractPaginationBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3097677344454965851L;

	private int pageSize;
	
	private int pageIndex;
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {		
		this.pageSize = pageSize;
	}

	public int getPageIndex() {		
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getMaxResults(){
		return this.pageSize;		
	}

	public int getFirstResult() {
		return this.calculateFirstRecord(this.pageSize, this.pageIndex);
	}
}
