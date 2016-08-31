package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * DTO que almacena la información de los dealers
 * @author waguilera
 *
 */
public class DealerInfoDTO implements Serializable{
	private String ibsCode;
	private String depotCode;
	private String dealerStateCode;
	private String dealerState;
	private String ibsCodePrincipalDealer;
	private Long score;
	private Long isPrincipal;
	private String channelCode;
	public DealerInfoDTO(String ibsCode, String depotCode,
			String dealerStateCode, String dealerState,
			String ibsCodePrincipalDealer, Long score, 
			Long isPrincipal, String channelCode) {
		super();
		this.ibsCode = ibsCode;
		this.depotCode = depotCode;
		this.dealerStateCode = dealerStateCode;
		this.dealerState = dealerState;
		this.ibsCodePrincipalDealer = ibsCodePrincipalDealer;
		this.score = score;
		this.isPrincipal = isPrincipal;
		this.channelCode = channelCode;
	}
	public String getIbsCode() {
		return ibsCode;
	}
	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getDealerStateCode() {
		return dealerStateCode;
	}
	public void setDealerStateCode(String dealerStateCode) {
		this.dealerStateCode = dealerStateCode;
	}
	public String getDealerState() {
		return dealerState;
	}
	public void setDealerState(String dealerState) {
		this.dealerState = dealerState;
	}
	public String getIbsCodePrincipalDealer() {
		return ibsCodePrincipalDealer;
	}
	public void setIbsCodePrincipalDealer(String ibsCodePrincipalDealer) {
		this.ibsCodePrincipalDealer = ibsCodePrincipalDealer;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Long getIsPrincipal() {
		return isPrincipal;
	}
	public void setIsPrincipal(Long isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public DealerInfoDTO() {
		super();
	}
	public DealerInfoDTO(DealerInfoDTO copy) {
		super();
		this.ibsCode = copy.ibsCode;
		this.depotCode = copy.depotCode;
		this.dealerStateCode = copy.dealerStateCode;
		this.dealerState = copy.dealerState;
		this.ibsCodePrincipalDealer = copy.ibsCodePrincipalDealer;
		this.score = copy.score;
		this.isPrincipal = copy.isPrincipal;
		this.channelCode = copy.channelCode;
	}
	
	
}
