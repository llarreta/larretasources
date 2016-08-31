package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DocumentClassVO;

/**
 * Interfaz de las operaciones Tipo CRUD de la Entidad DocumentClass. 
 * 
 * Fecha de Creación: 16/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface DocumentClassBusinessBeanLocal {
	
	/**
	 * Metodo: Obtiene la información de todos los DocumentClassVO almacenados en la persistencia
	 * @return Lista con los DocumentClassVO existentes, una lista vacia en caso que no existan DocumentClassVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author cduarte
	 */
	public List<DocumentClassVO> getAllDocumentClass() throws BusinessException;

}
