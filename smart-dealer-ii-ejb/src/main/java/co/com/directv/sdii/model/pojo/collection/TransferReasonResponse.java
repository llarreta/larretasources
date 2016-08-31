package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.vo.TransferReasonVO;

public class TransferReasonResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -411535626632758775L;
	
	private List<TransferReason> transferReason;
	private List<TransferReasonVO> transferReasonVO;
	
	public List<TransferReason> getTransferReason() {
		return transferReason;
	}
	public void setTransferReason(List<TransferReason> transferReason) {
		this.transferReason = transferReason;
	}
	public List<TransferReasonVO> getTransferReasonVO() {
		return transferReasonVO;
	}
	public void setTransferReasonVO(List<TransferReasonVO> transferReasonVO) {
		this.transferReasonVO = transferReasonVO;
	}

}
