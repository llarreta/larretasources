package co.com.directv.sdii.ejb.business.file.exception;

/**
 * 
 * exception que se lanza al final de un procesador de archivo
 * para indicar que ocurrió algún error y así poder dejar el estado del archivo
 * como terminado con errores
 * 
 * Fecha de Creación: 23/09/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class FileProcessException extends RuntimeException {

	public FileProcessException() {
		
	}
	
}
