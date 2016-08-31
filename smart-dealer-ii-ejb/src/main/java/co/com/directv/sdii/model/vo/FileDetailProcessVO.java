package co.com.directv.sdii.model.vo;

import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFile;

public class FileDetailProcessVO extends FileDetailProcess {
	
	public FileDetailProcessVO() {
	}

	public FileDetailProcessVO(FileDetailProcess fileDetailProcess) {
		this.setId( fileDetailProcess.getId() );
		this.setFdprow( fileDetailProcess.getFdprow() );
		this.setUploadFile(fileDetailProcess.getUploadFile());
		this.setMessage(fileDetailProcess.getMessage());
	}
	
}


