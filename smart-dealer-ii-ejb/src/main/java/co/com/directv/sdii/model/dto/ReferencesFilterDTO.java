package co.com.directv.sdii.model.dto;

import java.util.List;

public class ReferencesFilterDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781211330002198185L;
	
	private Long id;
	private String rnNumber;
	private Long dealer;
	private Long principalDealerSource;
	private Long principalDealerTarget;
	private Long branchDealerSource;
	private Long branchDealerTarget;
	private Long crewSource;
	private Long crewTarget;
	private Long whTypeSource;
	private Long whTypeTarget;
	private Long whSource;
	private Long whTarget;
	private String isPrepaidRef;
	private String isPreloadRef;
	private List<Long> referenceStatusIds;
	private Long countryId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPrincipalDealerSource() {
		return principalDealerSource;
	}
	public void setPrincipalDealerSource(Long principalDealerSource) {
		this.principalDealerSource = principalDealerSource;
	}
	public Long getPrincipalDealerTarget() {
		return principalDealerTarget;
	}
	public void setPrincipalDealerTarget(Long principalDealerTarget) {
		this.principalDealerTarget = principalDealerTarget;
	}
	public Long getBranchDealerSource() {
		return branchDealerSource;
	}
	public void setBranchDealerSource(Long branchDealerSource) {
		this.branchDealerSource = branchDealerSource;
	}
	public Long getBranchDealerTarget() {
		return branchDealerTarget;
	}
	public void setBranchDealerTarget(Long branchDealerTarget) {
		this.branchDealerTarget = branchDealerTarget;
	}
	public Long getCrewSource() {
		return crewSource;
	}
	public void setCrewSource(Long crewSource) {
		this.crewSource = crewSource;
	}
	public Long getCrewTarget() {
		return crewTarget;
	}
	public void setCrewTarget(Long crewTarget) {
		this.crewTarget = crewTarget;
	}
	public Long getWhTypeSource() {
		return whTypeSource;
	}
	public void setWhTypeSource(Long whTypeSource) {
		this.whTypeSource = whTypeSource;
	}
	public Long getWhTypeTarget() {
		return whTypeTarget;
	}
	public void setWhTypeTarget(Long whTypeTarget) {
		this.whTypeTarget = whTypeTarget;
	}
	public Long getWhSource() {
		return whSource;
	}
	public void setWhSource(Long whSource) {
		this.whSource = whSource;
	}
	public Long getWhTarget() {
		return whTarget;
	}
	public void setWhTarget(Long whTarget) {
		this.whTarget = whTarget;
	}
	public Long getDealer() {
		return dealer;
	}
	public void setDealer(Long dealer) {
		this.dealer = dealer;
	}
	public List<Long> getReferenceStatusIds() {
		return referenceStatusIds;
	}
	public void setReferenceStatusIds(List<Long> referenceStatusIds) {
		this.referenceStatusIds = referenceStatusIds;
	}
	public String getRnNumber() {
		return rnNumber;
	}
	public void setRnNumber(String rnNumber) {
		this.rnNumber = rnNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIsPrepaidRef() {
		return isPrepaidRef;
	}
	public void setIsPrepaidRef(String isPrepaidRef) {
		this.isPrepaidRef = isPrepaidRef;
	}
	public String getIsPreloadRef() {
		return isPreloadRef;
	}
	public void setIsPreloadRef(String isPreloadRef) {
		this.isPreloadRef = isPreloadRef;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}	
}
