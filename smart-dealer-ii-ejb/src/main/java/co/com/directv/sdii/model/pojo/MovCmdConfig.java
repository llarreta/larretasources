package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * MovCmdConfig entity. @author MyEclipse Persistence Tools
 */

public class MovCmdConfig implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1085598453265698275L;
	private Long id;
	private Date configDate;
	private Country country;
	private String status;
	private String deco;
	private String sc;
	private String isPrepaid;
	private MovElementProcess movProcess;
	private WarehouseType sourceWhType;
	private WarehouseType targetWhType;
	private IbsElementStatus moveResourceReason;
	private IbsElementStatus updateStatusReaons;
	private IbsElementStatus ibsElementStatusRecovery;
	
	public MovCmdConfig() {
	}
	
	public MovCmdConfig(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getConfigDate() {
		return configDate;
	}
	public void setConfigDate(Date configDate) {
		this.configDate = configDate;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeco() {
		return deco;
	}
	public void setDeco(String deco) {
		this.deco = deco;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public MovElementProcess getMovProcess() {
		return movProcess;
	}
	public void setMovProcess(MovElementProcess movProcess) {
		this.movProcess = movProcess;
	}
	public WarehouseType getSourceWhType() {
		return sourceWhType;
	}
	public void setSourceWhType(WarehouseType sourceWhType) {
		this.sourceWhType = sourceWhType;
	}
	public WarehouseType getTargetWhType() {
		return targetWhType;
	}
	public void setTargetWhType(WarehouseType targetWhType) {
		this.targetWhType = targetWhType;
	}
	public IbsElementStatus getMoveResourceReason() {
		return moveResourceReason;
	}
	public void setMoveResourceReason(IbsElementStatus moveResourceReason) {
		this.moveResourceReason = moveResourceReason;
	}
	public IbsElementStatus getUpdateStatusReaons() {
		return updateStatusReaons;
	}
	public void setUpdateStatusReaons(IbsElementStatus updateStatusReaons) {
		this.updateStatusReaons = updateStatusReaons;
	}
	
	public String getIsPrepaid() {
		return isPrepaid;
	}
	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}

	public IbsElementStatus getIbsElementStatusRecovery() {
		return ibsElementStatusRecovery;
	}

	public void setIbsElementStatusRecovery(
			IbsElementStatus ibsElementStatusRecovery) {
		this.ibsElementStatusRecovery = ibsElementStatusRecovery;
	}
	
}