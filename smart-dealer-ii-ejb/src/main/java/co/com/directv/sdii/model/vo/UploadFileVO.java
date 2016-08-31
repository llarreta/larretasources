package co.com.directv.sdii.model.vo;

import java.util.List;

import co.com.directv.sdii.model.pojo.UploadFile;

public class UploadFileVO extends UploadFile {
	
	private static final long serialVersionUID = 5343552433513220467L;
	
	private List<UploadFileParamVO> uploadFileParam;

	public UploadFileVO() {
	}
	
	public UploadFileVO( UploadFile uploadFile ) {
		this.setId( uploadFile.getId() );
		this.setFileStatus( uploadFile.getFileStatus() );
		this.setFileType( uploadFile.getFileType() );
		this.setCountry(uploadFile.getCountry());
		this.setUser(uploadFile.getUser());
		this.setName(uploadFile.getName());
		this.setLoadDate(uploadFile.getLoadDate());
		this.setProcessDate(uploadFile.getProcessDate());
	}

	public List<UploadFileParamVO> getUploadFileParam() {
		return uploadFileParam;
	}

	public void setUploadFileParam(List<UploadFileParamVO> uploadFileParam) {
		this.uploadFileParam = uploadFileParam;
	}
	
	

}
