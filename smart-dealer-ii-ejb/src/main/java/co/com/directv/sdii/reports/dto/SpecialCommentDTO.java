package co.com.directv.sdii.reports.dto;

public class SpecialCommentDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7071213361343042102L;
	
	private String commentDate;
	private String userName;
	private String comment;
	
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	

}
