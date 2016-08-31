package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.SpecialComment;
import co.com.directv.sdii.model.vo.SpecialCommentVO;

public class SpecialCommentResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4143223425611921728L;
	
	private List<SpecialComment> specialComment;
	private List<SpecialCommentVO> specialCommentVO;
	
	public List<SpecialComment> getSpecialComment() {
		return specialComment;
	}
	public void setSpecialComment(List<SpecialComment> specialComment) {
		this.specialComment = specialComment;
	}
	public List<SpecialCommentVO> getSpecialCommentVO() {
		return specialCommentVO;
	}
	public void setSpecialCommentVO(List<SpecialCommentVO> specialCommentVO) {
		this.specialCommentVO = specialCommentVO;
	}
	

	

	
	

}
