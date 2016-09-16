package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.vo.UploadFileVO;

public class UploadFileResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525464263281762433L;
	
	private List<UploadFile> uploadFile;
	private List<UploadFileVO> uploadFileVO;
	
	public List<UploadFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<UploadFile> uploadFile) {
		this.uploadFile = uploadFile;
	}
	public List<UploadFileVO> getUploadFileVO() {
		return uploadFileVO;
	}
	public void setUploadFileVO(List<UploadFileVO> uploadFileVO) {
		this.uploadFileVO = uploadFileVO;
	}
	
	
	
	

}
