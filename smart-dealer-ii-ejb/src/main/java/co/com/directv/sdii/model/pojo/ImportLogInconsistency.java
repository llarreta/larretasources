package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ImportLogInconsistency entity. @author MyEclipse Persistence Tools
 */

public class ImportLogInconsistency implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8968282584279932136L;
	// Fields

	private Long id;
	private Long importLog;
	private InconsistencyStatus inconsistencyStatus;
	private InconsistencyType inconsistencyType;
	private Date inconsistencyDate;
	private String comments;
	private Long user;
	private String answer;
	private Long importLogItem;
	private Long expectedAmount;
	private Long userClose;
	private String adicion;
	private Date answerDate;

	// Constructors

	/** default constructor */
	public ImportLogInconsistency() {
	}

	/** minimal constructor */
	public ImportLogInconsistency(Long importLog,
			InconsistencyStatus inconsistencyStatus,
			InconsistencyType inconsistencyType, Date inconsistencyDate,
			String comments, Long user) {
		this.setImportLog(importLog);
		this.inconsistencyStatus = inconsistencyStatus;
		this.inconsistencyType = inconsistencyType;
		this.inconsistencyDate = inconsistencyDate;
		this.comments = comments;
		this.setUser(user);
	}

	/** full constructor */
	public ImportLogInconsistency(Long importLog,
			InconsistencyStatus inconsistencyStatus,
			InconsistencyType inconsistencyType, Date inconsistencyDate,
			String comments, Long user, String answer) {
		this.setImportLog(importLog);
		this.inconsistencyStatus = inconsistencyStatus;
		this.inconsistencyType = inconsistencyType;
		this.inconsistencyDate = inconsistencyDate;
		this.comments = comments;
		this.setUser(user);
		this.answer = answer;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InconsistencyStatus getInconsistencyStatus() {
		return this.inconsistencyStatus;
	}

	public void setInconsistencyStatus(InconsistencyStatus inconsistencyStatus) {
		this.inconsistencyStatus = inconsistencyStatus;
	}

	public InconsistencyType getInconsistencyType() {
		return this.inconsistencyType;
	}

	public void setInconsistencyType(InconsistencyType inconsistencyType) {
		this.inconsistencyType = inconsistencyType;
	}

	public Date getInconsistencyDate() {
		return this.inconsistencyDate;
	}

	public void setInconsistencyDate(Date inconsistencyDate) {
		this.inconsistencyDate = inconsistencyDate;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	@Override
	public String toString() {
		return "ImportLogInconsistency [id=" + id + "]";
	}

	public void setImportLog(Long importLog) {
		this.importLog = importLog;
	}

	public Long getImportLog() {
		return importLog;
	}

	public void setImportLogItem(Long importLogItem) {
		this.importLogItem = importLogItem;
	}

	public Long getImportLogItem() {
		return importLogItem;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getUser() {
		return user;
	}

	public void setExpectedAmount(Long expectedAmount) {
		this.expectedAmount = expectedAmount;
	}

	public Long getExpectedAmount() {
		return expectedAmount;
	}
	
	public String getAdicion() {
		return adicion;
	}
	
	public void setAdicion(String adicion) {
		this.adicion = adicion;
	}

	
	public Long getUserClose() {
		return userClose;
	}

	
	public void setUserClose(Long userClose) {
		this.userClose = userClose;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
}