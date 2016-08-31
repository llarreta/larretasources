package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * SpecialComment entity. @author MyEclipse Persistence Tools
 */

public class SpecialComment implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4758354700820972246L;
	// Fields

	private Long id;
	private Reference reference;
	private Date commentDate;
	private String commentDescription;
	private Long userId;

	// Constructors

	/** default constructor */
	public SpecialComment() {
	}

	/** minimal constructor */
	public SpecialComment(Reference reference, Date commentDate,
			String commentDescription) {
		this.reference = reference;
		this.commentDate = commentDate;
		this.commentDescription = commentDescription;
	}

	/** full constructor */
	public SpecialComment(Reference reference, Date commentDate,
			String commentDescription, Long userId) {
		this.reference = reference;
		this.commentDate = commentDate;
		this.commentDescription = commentDescription;
		this.userId = userId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reference getReference() {
		return this.reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public Date getCommentDate() {
		return this.commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentDescription() {
		return this.commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SpecialComment [commentDescription=" + commentDescription
				+ ", id=" + id + "]";
	}
}