package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.directv.sdii.model.pojo.ExpirationGrouping;

public class ExpirationGroupingVO extends ExpirationGrouping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8766038883206258840L;
	
	private Long woTotal;
	private Date expirationInitDate;
	private Date expirationEndDate;
	private List<Long> woStatusIds;
	
	public Long getWoTotal() {
		return woTotal;
	}
	public void setWoTotal(Long woTotal) {
		this.woTotal = woTotal;
	}
	public List<Long> getWoStatusIds() {
		return woStatusIds;
	}
	public void setWoStatusIds(List<Long> woStatusIds) {
		this.woStatusIds = woStatusIds;
	}
	public Date getExpirationInitDate() {
		return expirationInitDate;
	}
	public void setExpirationInitDate(Date expirationInitDate) {
		this.expirationInitDate = expirationInitDate;
	}
	public Date getExpirationEndDate() {
		return expirationEndDate;
	}
	public void setExpirationEndDate(Date expirationEndDate) {
		this.expirationEndDate = expirationEndDate;
	}
}
