package co.com.directv.sdii.ejb.business.file;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;

/**
 * Interfaz que define los metodos de negocio de la entidad FileDetailProcess.
 * @author Ricardo De la Rosa Rosero
 */
@Local
public interface FileDetailProcessBusinessBeanLocal {

	/**
	 * Retorna los objetos de tipo 'FileDetailProcess' (estos objetos tiene los errores detectados sobre el archivo) 
	 * asociados al archivo con id 'fileId'
	 * @param fileId
	 * @return List<FileDetailProcess>
	 * @throws BusinessException
	 */
	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException  ;
	
	
	/**
	 * Método: Registra el detalle de una fila de un archivo
	 * @param fileDetailProVO - FileDetailProcessVO con el detalle 
	 * @throws BusinessException 
	 * @author gfandino
	 */
	public void ceateFleDetailProccess (FileDetailProcessVO fileDetailProVO) throws BusinessException  ;


	/**
	 * 
	 * Metodo: Guarda error en una nueva transaccion, al procesar el archivo. Se usa una nueva transaccion, 
	 *         para evitar el rollBack cuando genera un error.  
	 * @param row
	 * @param message
	 * @throws BusinessException 
	 * @author hcorredor
	 */
	public void saveFileDetailProces(long row, String message, Long uploafFileWorkingId) throws BusinessException  ;


	/**
	 * Metodo: Consulta la información de los registros de un archivo
	 * @param fileId identificador del archivo
	 * @param requestCollection información de consulta de la paginación
	 * @return datos de paginación
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public FileDetailProcessCollectionDTO findFileDetailProcessByFileId(Long fileId, RequestCollectionInfoDTO requestCollection) throws BusinessException;

}
