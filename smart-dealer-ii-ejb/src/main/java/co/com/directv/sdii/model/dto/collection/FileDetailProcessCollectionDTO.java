/**
 * Creado 30/08/2011 08:38:00
 */
package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.FileDetailProcessVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 30/08/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class FileDetailProcessCollectionDTO extends CollectionBase implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7500406377854356403L;

	private List<FileDetailProcess> fileDetailProcess;
	
	private List<FileDetailProcessVO> fileDetailProcessVos;

	public List<FileDetailProcess> getFileDetailProcess() {
		return fileDetailProcess;
	}

	public void setFileDetailProcess(List<FileDetailProcess> fileDetailProcess) {
		this.fileDetailProcess = fileDetailProcess;
	}

	public List<FileDetailProcessVO> getFileDetailProcessVos() {
		return fileDetailProcessVos;
	}

	public void setFileDetailProcessVos(
			List<FileDetailProcessVO> fileDetailProcessVos) {
		this.fileDetailProcessVos = fileDetailProcessVos;
	}
	
}
