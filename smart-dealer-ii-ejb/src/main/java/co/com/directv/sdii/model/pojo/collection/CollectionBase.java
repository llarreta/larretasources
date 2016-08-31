/**
 * Creado 3/01/2011 13:03:49
 */
package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;

/**
 * Define los atributos comunes con la información de paginación de resultados
 * 
 * Fecha de Creación: 3/01/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CollectionBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -114680745552133471L;

	/**
	 * Determina la cantidad de registros a retornar por página
	 */
	private int pageSize;
	
	/**
	 * Determina el número de página de la respuesta
	 */
	private int pageIndex;
	
	/**
	 * Determina la cantidad de registros que se devuelven en la página actual
	 * por ejemplo cuando es la última página, es posible que esta cantidad sea inferior
	 * al tamaño de la página 
	 */
	private int rowCount;
	
	/**
	 * Determina el número de páginas que retorna el resultado
	 */
	private int pageCount;
	
	/**
	 * Determina la cantidad total de registros que retorna la consulta, es últil
	 * para realizar cálculos de paginación
	 */
	private int totalRowCount;

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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	
}
