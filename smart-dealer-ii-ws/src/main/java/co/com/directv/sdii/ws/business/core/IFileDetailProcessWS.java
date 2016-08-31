package co.com.directv.sdii.ws.business.core;

import java.util.List;

import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;

@WebService(name="FileDetailProcessWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IFileDetailProcessWS {
	/**
	 * Permite recuperar los objetos de tipo FileDetailProcessVO del archivo con 
	 * identificador fileId 
	 * @param fileId Identificador del archivo para el cual estamos buscando los detalles
	 * @return
	 * @throws BusinessException
	 */
	public List<FileDetailProcessVO> findByFileId(Long fileId)  throws BusinessException;
}
