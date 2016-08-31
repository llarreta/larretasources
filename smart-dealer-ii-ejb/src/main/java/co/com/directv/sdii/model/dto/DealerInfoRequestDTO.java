package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

public class DealerInfoRequestDTO extends RequestCollectionInfo implements Serializable {
	
	private String countryCode;
	private Boolean isCsvOrSoapResponse;
	public DealerInfoRequestDTO(String countryCode,
			Boolean isCsvOrSoapResponse) {
		super();
		this.countryCode = countryCode;
		this.isCsvOrSoapResponse = isCsvOrSoapResponse;
	}
	public DealerInfoRequestDTO() {
		super();
	}
	public DealerInfoRequestDTO(DealerInfoRequestDTO copy) {
		super();
		this.countryCode = copy.countryCode;
		this.isCsvOrSoapResponse = copy.isCsvOrSoapResponse;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Boolean getIsCsvOrSoapResponse() {
		return isCsvOrSoapResponse;
	}
	public void setIsCsvOrSoapResponse(Boolean isCsvOrSoapResponse) {
		this.isCsvOrSoapResponse = isCsvOrSoapResponse;
	}
}
