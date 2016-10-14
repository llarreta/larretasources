package co.com.directv.sdii.model.pojo;

public class WorkOrderExportDataId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long expId;
	private Long woId;

	public WorkOrderExportDataId() {
	}
	
	public WorkOrderExportDataId(Long expId, Long woId) {
		this.expId = expId;
		this.woId = woId;
	}

	public Long getExpId() {
		return this.expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public Long getWoId() {
		return this.woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WorkOrderExportDataId))
			return false;
		WorkOrderExportDataId castOther = (WorkOrderExportDataId) other;

		return ((this.getExpId() == castOther.getExpId()) || (this.getExpId() != null && castOther.getExpId() != null
				&& this.getExpId().equals(castOther.getExpId())))
				&& ((this.getWoId() == castOther.getWoId()) || (this.getWoId() != null && castOther.getWoId() != null
						&& this.getWoId().equals(castOther.getWoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getExpId() == null ? 0 : this.getExpId().hashCode());
		result = 37 * result + (getWoId() == null ? 0 : this.getWoId().hashCode());
		return result;
	}

}
