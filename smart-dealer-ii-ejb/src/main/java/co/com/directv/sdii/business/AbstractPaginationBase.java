/**
 * Creado 01/02/2011 14:45:59
 */
package co.com.directv.sdii.business;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 01/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public abstract class AbstractPaginationBase {

	private final static Logger log = UtilsBusiness.getLog4J(AbstractPaginationBase.class);
	
	/**
	 * Metodo: Pobla la información de paginación a partir de ciertos parámetros
	 * @param collectionInfo2populate
	 * @param pageSize cantidad de registros por página
	 * @param pageIndex indice de la página actual
	 * @param rowCount cantidad de registros en la página actual
	 * @param totalRowCount cantidad total de registros de todas las páginas
	 * @author jjimenezh
	 */
	public void populatePaginationInfo(CollectionBase collectionInfo2populate, int pageSize, int pageIndex, int rowCount, int totalRowCount){
		collectionInfo2populate.setPageSize(pageSize);
		collectionInfo2populate.setPageIndex(pageIndex);
		collectionInfo2populate.setTotalRowCount(totalRowCount);
		collectionInfo2populate.setRowCount(rowCount);
		//Cantidad de paginas necesarias para mostrar todos los registros existentes.
		collectionInfo2populate.setPageCount(new BigDecimal(collectionInfo2populate.getTotalRowCount()).divide(new BigDecimal(collectionInfo2populate.getPageSize()), BigDecimal.ROUND_UP).intValue());
	}
	
	/**
	 * Metodo: Copia la información de paginación desde un origen hacia un destino
	 * @param collectionBaseSource fuente de la información de paginación
	 * @param collectionBaseTarget destino de la información de paginación
	 * @author jjimenezh
	 */
	public void copyPaginationInfo(CollectionBase collectionBaseSource, CollectionBase collectionBaseTarget){
		try {
			BeanUtils.copyProperties(collectionBaseTarget, collectionBaseSource);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			String message = "Error al tratar de copiar la información de paginación: " + e.getMessage();
			log.error(message, e);
			e.printStackTrace();
			throw new IllegalStateException(message);
		}
	}
	
	/**
	 * 
	 * Metodo: Realiza la paginacion de una lista Generica
	 * @param List
	 * @param pList
	 * @param requestCollInfo
	 * @return List, Retorna la lista paginada
	 * @throws BusinessException
	 * @author jalopez
	 */
	public <T> List<T> getPaginationList(List<T> pList, RequestCollectionInfo requestCollInfo) throws BusinessException{
		
		List<T> pagerList = new ArrayList<T>();
		int totalSize = requestCollInfo.getFirstResult() + requestCollInfo.getPageSize();
		
    	for (int index = requestCollInfo.getFirstResult(); index < totalSize; index++) {    
    			
    		Object array_element = pList.get(index);
    		pagerList.add((T) array_element);
    		if( (index+1) ==  pList.size() )
    			index = totalSize;    	    		
		}
		return pagerList;
	}
	
	/**
	 * Metodo: obtiene la página indicada de la lista
	 * @param completeList listado completo del que se quiere obtener una página
	 * @param requestCollectionInfo información de paginación
	 * @return sublista con los ítems de la página indicada por requestCollectionInfo. Lista vacía si la página
	 * solicitada no es válida
	 * @throws BusinessException
	 * @author wjimenez
	 * wjimenez validar si se puede dejar solo este método y eliminar getPaginationList
	 */
	public <T> List<T> getPaginatedList(List<T> completeList, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		
		List<T> pagedList = new ArrayList<T>();
		if(completeList != null) {
			
			if(requestCollectionInfo.getPageIndex() < 1) {
				throw new BusinessException("el número de página debe ser mayor o igual a 1");
			} 
			if (requestCollectionInfo.getPageSize() < 1) {
				throw new BusinessException("el tamaño de página debe ser mayor o igual a 1");
			}
			
			int fromIndex = requestCollectionInfo.getPageSize() * (requestCollectionInfo.getPageIndex() -1);
			int toIndex = fromIndex + requestCollectionInfo.getPageSize();
			toIndex = (toIndex > completeList.size() ? completeList.size() : toIndex);
			if(toIndex < fromIndex) {
				return pagedList;
			}
			pagedList = completeList.subList(fromIndex, toIndex);
		}
		
		return pagedList;
	}
	
	public int calculateFirstRecord(int pageSize, int pageIndex){
		return (pageIndex - 1) * pageSize;
	}
	
}
