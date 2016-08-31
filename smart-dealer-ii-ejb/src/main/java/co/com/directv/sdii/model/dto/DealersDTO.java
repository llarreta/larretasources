package co.com.directv.sdii.model.dto;


/**
 *
 * Dealers Data Transfer Object
 *
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
public class DealersDTO {

    private static final long serialVersionUID = -5406749752499506589L;

    private String dealerName;
    private String legalRepresentative;
    private String nit;
    private String address;
    private double score;
    private String depotCode;
    private long dealerCode;
    private boolean isPrincipal;
    private int principalDepodCode;
    private String principalDealerCode;
    private String city;
    private String state;
    private String country;
    private Long postalCode;
    private String statusId;
    private String statusName;
    private String channelTypeId;
    private String channelTypeName;
    private String[] emails;
    private String[] celPhones;
    private String[] phones;
    private String[] faxes;
    private String codePostal;

     public DealersDTO(){

     }

    public String getDealerName() {
            return dealerName;
    }
    public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
    }
    public String getLegalRepresentative() {
            return legalRepresentative;
    }
    public void setLegalRepresentative(String legalRepresentative) {
            this.legalRepresentative = legalRepresentative;
    }
    public String getNit() {
            return nit;
    }
    public void setNit(String nit) {
            this.nit = nit;
    }
    public String getAddress() {
            return address;
    }
    public void setAddress(String address) {
            this.address = address;
    }
    public double getScore() {
            return score;
    }
    public void setScore(double score) {
            this.score = score;
    }
    public String getDepotCode() {
            return depotCode;
    }
    public void setDepotCode(String depodCode) {
            this.depotCode = depodCode;
    }
    public long getDealerCode() {
            return dealerCode;
    }
    public void setDealerCode(int dealerCode) {
            this.dealerCode = dealerCode;
    }
    public boolean isPrincipal() {
            return isPrincipal;
    }
    public void setPrincipal(boolean isPrincipal) {
            this.isPrincipal = isPrincipal;
    }
    public int getPrincipalDepodCode() {
            return principalDepodCode;
    }
    public void setPrincipalDepodCode(int principalDepodCode) {
            this.principalDepodCode = principalDepodCode;
    }
    public String getPrincipalDealerCode() {
            return principalDealerCode;
    }
    public void setPrincipalDealerCode(String principalDealerCode) {
            this.principalDealerCode = principalDealerCode;
    }
    public String getCity() {
            return city;
    }
    public void setCity(String city) {
            this.city = city;
    }
    public String getState() {
            return state;
    }
    public void setState(String state) {
            this.state = state;
    }
    public String getCountry() {
            return country;
    }
    public void setCountry(String country) {
            this.country = country;
    }
    public String[] getEmails() {
            return emails;
    }
    public void setEmails(String[] emails) {
            this.emails = emails;
    }
    public String[] getCelPhones() {
            return celPhones;
    }
    public void setCelPhones(String[] celPhones) {
            this.celPhones = celPhones;
    }
    public String[] getPhones() {
            return phones;
    }
    public void setPhones(String[] phones) {
            this.phones = phones;
    }
    public String[] getFaxes() {
            return faxes;
    }
    public void setFaxes(String[] faxes) {
            this.faxes = faxes;
    }

    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }    

    /**
     * @return the statusId
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the channelTypeId
     */
    public String getChannelTypeId() {
        return channelTypeId;
    }

    /**
     * @param channelTypeId the channelTypeId to set
     */
    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    /**
     * @return the channelTypeName
     */
    public String getChannelTypeName() {
        return channelTypeName;
    }

    /**
     * @param channelTypeName the channelTypeName to set
     */
    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }    
    

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
