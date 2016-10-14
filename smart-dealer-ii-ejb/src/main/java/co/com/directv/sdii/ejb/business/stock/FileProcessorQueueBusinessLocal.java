package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.UploadFileVO;

@Local
public interface FileProcessorQueueBusinessLocal {

	public void processFile(UploadFileVO file ) throws BusinessException;
	
}
