/**
 * 
 */
package co.com.directv.sdii.reports.commands;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;


/**
 * Interfaz que define la fábrica de comandos para la
 * generación de los archivos de excel.
 * 
 * @author jvelezmu
 *
 */
@Local
public interface ICommandFactory {

	public ICommand getCommand(String cmdName) throws BusinessException;
}
