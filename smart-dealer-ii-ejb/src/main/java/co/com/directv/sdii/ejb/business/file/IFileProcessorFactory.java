/**
 * 
 */
package co.com.directv.sdii.ejb.business.file;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;


/**
 * Interfaz que define la fábrica de procesadores de archivo
 * 
 * @author jvelezmu
 *
 */
@Local
public interface IFileProcessorFactory {

	public IFileProcessor getFileProcessor(String fileType) throws BusinessException;
}
