/**
 * 
 */
package co.com.directv.sdii.reports.commands;

import java.util.List;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Interfaz que define los métodos de ejecución
 * del patrón comando.
 * 
 * Fecha: 15-02-2011 
 * @author jvelezmu
 *
 */
public interface ICommand {
	
	/**
	 * Retorna la lista de registros que se visualizan en la hoja de Excel
	 *  
	 * @param args
	 * @return List<String>
	 * @throws BusinessException
	 */
	//public List<String> execute(String args) throws BusinessException;
	
	/**
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 * @throws BusinessException
	 */
	public <T> List<T> execute(String args) throws BusinessException;
	
	/**
	 * Retorna la lista de Campos que se visualizan en la hoja de Excel
	 * 
	 * @return List<String>
	 */
	public List<String> getFieldList();
	
	/**
	 * Metodo: Campos que se visualizan en la hoja de Excel
	 * @param fieldList <tipo> <descripcion>
	 * @author
	 */
	public void setFieldList(List<String> fieldList);
}
