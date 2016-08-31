package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * MovCmdLog entity. @author MyEclipse Persistence Tools
 */

public class MovCmdLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -730891515907537206L;
	private Long id;
	private MovCmdQueue movCmdQueue;
	private String description;
	private Date creationDate;

	// Constructors

	/** default constructor */
	public MovCmdLog() {
	}

	/** full constructor */
	public MovCmdLog(MovCmdQueue movCmdQueue, String description,
			Date creationDate) {
		this.movCmdQueue = movCmdQueue;
		this.description = description;
		this.creationDate = creationDate;
	}
	
	

	public MovCmdLog(Long id, String description, Date creationDate) {
		super();
		this.id = id;
		this.description = description;
		this.creationDate = creationDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MovCmdQueue getMovCmdQueue() {
		return this.movCmdQueue;
	}

	public void setMovCmdQueue(MovCmdQueue movCmdQueue) {
		this.movCmdQueue = movCmdQueue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}