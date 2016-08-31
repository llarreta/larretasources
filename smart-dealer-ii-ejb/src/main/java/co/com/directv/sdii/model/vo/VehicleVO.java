package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Vehicle;

/**
 * 
 * Vehicless Value Object   
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Vehicles
 */
public class VehicleVO extends Vehicle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7934963983356583057L;

    private String cityName;

    private String countryName;

    private String stateName;

    private Long driverId;

    private String driverName;

    private String membershipTypeName;

    private String dealerName;

    private long dealerCode;

    private String dealerDepotCode;

    private String vehicleTypeName;

    private String vehicleStatusName;

   
    private Long cityId;
    private Long vehicleTypeId;
    private Long membershipTypeId;
    private Long peopleCapacity;
    private Long statusId;
    private Long dealerId;
    private Long countryId;
    private Long stateId;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(long dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerDepotCode() {
        return dealerDepotCode;
    }

    public void setDealerDepotCode(String dealerDepotCode) {
        this.dealerDepotCode = dealerDepotCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMembershipTypeName() {
        return membershipTypeName;
    }

    public void setMembershipTypeName(String membershipTypeName) {
        this.membershipTypeName = membershipTypeName;
    }

    public String getVehicleStatusName() {
        return vehicleStatusName;
    }

    public void setVehicleStatusName(String vehicleStatusName) {
        this.vehicleStatusName = vehicleStatusName;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the cityId
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the vehicleTypeId
     */
    public Long getVehicleTypeId() {
        return vehicleTypeId;
    }

    /**
     * @param vehicleTypeId the vehicleTypeId to set
     */
    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    /**
     * @return the membershipTypeId
     */
    public Long getMembershipTypeId() {
        return membershipTypeId;
    }

    /**
     * @param membershipTypeId the membershipTypeId to set
     */
    public void setMembershipTypeId(Long membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    /**
     * @return the peopleCapacity
     */
    @Override
    public Long getPeopleCapacity() {
        return peopleCapacity;
    }

    /**
     * @param peopleCapacity the peopleCapacity to set
     */
    @Override
    public void setPeopleCapacity(Long peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    /**
     * @return the statusId
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the dealerId
     */
    public Long getDealerId() {
        return dealerId;
    }

    /**
     * @param dealerId the dealerId to set
     */
    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public void populateBean(){
        cityId = getCity() == null ? 0L : getCity().getId();
        cityName = getCity() == null ? null : getCity().getCityName();
        if(getCity() != null){
            if(getCity().getState() != null){
                stateName = getCity().getState().getStateName();
                stateId = getCity().getState().getId();
                countryName = getCity().getState().getCountry() == null ? null : getCity().getState().getCountry().getCountryName();
                countryId  = getCity().getState().getCountry() == null ? null : getCity().getState().getCountry().getId();
            }
        }

        if(getDealer() != null){
            dealerCode = getDealer().getDealerCode();
            dealerDepotCode = getDealer().getDepotCode();
            dealerId = getDealer().getId();
            dealerName = getDealer().getDealerName();
        }

        membershipTypeId = getMembershipType() == null ? null : getMembershipType().getId();
        membershipTypeName = getMembershipType() == null ? null : getMembershipType().getMembershipTypeName();

        statusId = getVehicleStatus() == null ? 0L : getVehicleStatus().getId();
        vehicleStatusName = getVehicleStatus() == null ? null : getVehicleStatus().getStatusName();
        stateName = null;

        vehicleTypeId = getVehicleType() == null ? 0L : getVehicleType().getId();
        vehicleTypeName = getVehicleType() == null ? null : getVehicleType().getVehicleTypeName();
    }

}
