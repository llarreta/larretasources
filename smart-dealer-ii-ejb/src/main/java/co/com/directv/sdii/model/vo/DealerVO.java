package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import co.com.directv.sdii.model.pojo.ChannelType;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerStatus;
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.State;

/**
 * Dealer Value Object
 * @author Jimmy Velez Mu�oz
 * @see co.com.directv.sdii.model.pojo.Dealers
 */
public class DealerVO extends Dealer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 628703491554502287L;


    //Agrgando las etiquetas de las entidades relacionadas:

    private String statusName;
    private String channelTypeName;
    private String dealerTypeName;
    private String postalCodeName;
    private String dealerKey;

    
    private Long statusId;
    private Long channelTypeId;
    private Long dealerTypeId;
    private Long dealerCode;
    private Long postalCodeId;
    private Long countryId;
    private String countryName;
    private Long stateId;
    private String stateName;
    private Long cityId;
    private String cityName;
    private String depotPlusName;

    private List<DealerMediaContactVO> mediaContacts = new ArrayList<DealerMediaContactVO>();
    private List<DealerVO> dealerBranches = new ArrayList<DealerVO>();

    public List<DealerVO> getDealerBranches() {
        return dealerBranches;
    }

    public void setDealerBranches(List<DealerVO> dealerBranches) {
        this.dealerBranches = dealerBranches;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<DealerMediaContactVO> getMediaContacts() {
        return mediaContacts;
    }

    public void setMediaContacts(List<DealerMediaContactVO> mediaContacts) {
        this.mediaContacts = mediaContacts;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getDealerTypeName() {
        return dealerTypeName;
    }

    public void setDealerTypeName(String dealerTypeName) {
        this.dealerTypeName = dealerTypeName;
    }

    public String getPostalCodeName() {
        return postalCodeName;
    }

    public void setPostalCodeName(String postalCodeName) {
        this.postalCodeName = postalCodeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
     * @return the channelTypeId
     */
    public Long getChannelTypeId() {
        return channelTypeId;
    }

    /**
     * @param channelTypeId the channelTypeId to set
     */
    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    /**
     * @return the dealerTypeId
     */
    public Long getDealerTypeId() {
        return dealerTypeId;
    }

    /**
     * @param dealerTypeId the dealerTypeId to set
     */
    public void setDealerTypeId(Long dealerTypeId) {
        this.dealerTypeId = dealerTypeId;
    }

    /**
     * @return the dealerCode
     */
    public Long getDealerCode() {
        return dealerCode;
    }

    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(Long dealerCode) {
        this.dealerCode = dealerCode;
    }

    /**
     * @return the postalCodeId
     */
    public Long getPostalCodeId() {
        return postalCodeId;
    }

    /**
     * @param postalCodeId the postalCodeId to set
     */
    public void setPostalCodeId(Long postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

	public void populateBean(){
        ChannelType channelType = this.getChannelType();
        DealerStatus dealerStatus = this.getDealerStatus();
        DealerType dealerType = this.getDealerType();
        PostalCode postalCode = this.getPostalCode();
        
        if(channelType != null){
            this.setChannelTypeId(channelType.getId());
            this.setChannelTypeName(channelType.getCanalName());
        }

        if(dealerStatus != null){
            this.setStatusId(dealerStatus.getId());
            this.setStatusName(dealerStatus.getStatusName());
        }
        
        
        if(dealerType != null){
            this.setDealerTypeId(dealerType.getId());
            this.setDealerTypeName(dealerType.getDealerTypeName());
        }
        
        if(postalCode != null){
            this.setPostalCodeId(postalCode.getId());
            this.setPostalCodeName(postalCode.getPostalCodeName());
            City city = this.getPostalCode().getCity();
            if(city != null){
                this.setCityId(city.getId());
                this.setCityName(city.getCityName());

                State state = city.getState();
                if(state != null){
                    this.setStateId(state.getId());
                    this.setStateName(state.getStateName());

                    Country country = state.getCountry();
                    if(country != null){
                        this.setCountryId(country.getId());
                        this.setCountryName(country.getCountryName());
                    }
                }
            }
        }
        
    }

	public void setDepotPlusName(String depotPlusName) {
		this.depotPlusName = depotPlusName;
	}

	public String getDepotPlusName() {
		return depotPlusName;
	}
	
	public void generateDepotPlusName() {
		StringBuffer sb = new StringBuffer();
		if( !StringUtils.isBlank( getDepotCode() )) {
			sb.append(getDepotCode()).append(" - ");	
		}
		if( !StringUtils.isBlank( getDealerName() )) {
			sb.append(getDealerName());	
		}
		depotPlusName = sb.toString();
	}
	
	@Override
	public String toString() {
		return "DealerVO [getId()=" + getId() + ", dealerCode=" + dealerCode
				+ ", dealerKey=" + dealerKey + ", getDealerName()="
				+ getDealerName() + ", getDepotCode()=" + getDepotCode()+ "]";
	}
	
    
   
}
