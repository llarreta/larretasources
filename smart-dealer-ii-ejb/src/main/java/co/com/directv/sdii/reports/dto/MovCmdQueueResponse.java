package co.com.directv.sdii.reports.dto;

import java.util.List;

import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;


public class MovCmdQueueResponse extends CollectionBase implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MovCmdQueue> movCmdQueues;
	private List<MovCmdQueueVO> movCmdQueueVOs;
	public List<MovCmdQueue> getMovCmdQueues() {
		return movCmdQueues;
	}
	public void setMovCmdQueues(List<MovCmdQueue> movCmdQueues) {
		this.movCmdQueues = movCmdQueues;
	}
	public List<MovCmdQueueVO> getMovCmdQueueVOs() {
		return movCmdQueueVOs;
	}
	public void setMovCmdQueueVOs(List<MovCmdQueueVO> movCmdQueueVOs) {
		this.movCmdQueueVOs = movCmdQueueVOs;
	}
	
}
