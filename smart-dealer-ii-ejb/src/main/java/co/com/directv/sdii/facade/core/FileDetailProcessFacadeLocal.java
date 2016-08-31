package co.com.directv.sdii.facade.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;

@Local
public interface FileDetailProcessFacadeLocal {

	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException;
	
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
