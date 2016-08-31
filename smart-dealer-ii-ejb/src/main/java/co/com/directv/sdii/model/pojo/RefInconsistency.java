package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * RefInconsistency entity. @author MyEclipse Persistence Tools
 */

public class RefInconsistency implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5231784466214372897L;
	private Long id;
	private RefIncStatus refIncStatus;
	private RefIncType refIncType;
	private Reference reference;
	private String refIncComments;
	private Long createUserId;
	private Date refIncDate;
	private String answer;
	private Long answerUserId;
	private Date answerDate;
	
	/**
	 * remisión que se genera para cerrar la inconsistencia de menos elementos
	 */
	private Reference generatedReference;

	// Constructors

	/** default constructor */
	public RefInconsistency() {
	}

	/** minimal constructor */
	public RefInconsistency(RefIncStatus refIncStatus, RefIncType refIncType,
			Reference reference, String refIncComments, Long createUserId,
			Date refIncDate) {
		this.refIncStatus = refIncStatus;
		this.refIncType = refIncType;
		this.reference = reference;
		this.refIncComments = refIncComments;
		this.createUserId = createUserId;
		this.refIncDate = refIncDate;
	}

	/** full constructor */
	public RefInconsistency(RefIncStatus refIncStatus, RefIncType refIncType,
			Reference reference, String refIncComments, Long createUserId,
			Date refIncDate, String answer, Long answerUserId,
			Date answerDate) {
		this.refIncStatus = refIncStatus;
		this.refIncType = refIncType;
		this.reference = reference;
		this.refIncComments = refIncComments;
		this.createUserId = createUserId;
		this.refIncDate = refIncDate;
		this.answer = answer;
		this.answerUserId = answerUserId;
		this.answerDate = answerDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RefIncStatus getRefIncStatus() {
		return this.refIncStatus;
	}

	public void setRefIncStatus(RefIncStatus refIncStatus) {
		this.refIncStatus = refIncStatus;
	}

	public RefIncType getRefIncType() {
		return this.refIncType;
	}

	public void setRefIncType(RefIncType refIncType) {
		this.refIncType = refIncType;
	}

	public Reference getReference() {
		return this.reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public String getRefIncComments() {
		return this.refIncComments;
	}

	public void setRefIncComments(String refIncComments) {
		this.refIncComments = refIncComments;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getRefIncDate() {
		return this.refIncDate;
	}

	public void setRefIncDate(Date refIncDate) {
		this.refIncDate = refIncDate;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getAnswerUserId() {
		return this.answerUserId;
	}

	public void setAnswerUserId(Long answerUserId) {
		this.answerUserId = answerUserId;
	}

	public Date getAnswerDate() {
		return this.answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public Reference getGeneratedReference() {
		return generatedReference;
	}

	public void setGeneratedReference(Reference generatedReference) {
		this.generatedReference = generatedReference;
	}

	@Override
	public String toString() {
		return "RefInconsistency [id=" + id + "]";
	}
}