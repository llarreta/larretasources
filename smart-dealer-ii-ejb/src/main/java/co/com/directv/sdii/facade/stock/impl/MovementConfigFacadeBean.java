/**
 * 
 */
package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

/**
 * Clase que implementa los métodos que permiten informar el estado de los elementos
 * y los movimientos que se realizan entre bodegas de SmartDealerII a IBS.
 * 
 * @author Jimmy Vélez Muñoz
 *
 */
@Stateless(name="MovementConfigFacadeBeanLocal",mappedName="ejb/MovementConfigFacadeBeanLocal")
@Local({MovementConfigFacadeBeanLocal.class})
public class MovementConfigFacadeBean implements MovementConfigFacadeBeanLocal {

	@EJB(name="MovementConfigBusinessBeanLocal", beanInterface=MovementConfigBusinessBeanLocal.class)
	private MovementConfigBusinessBeanLocal businessMovementConfig;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#createMovementCommandQueue(co.com.directv.sdii.model.dto.ElementMovementDTO)
	 */
	@Override
	public void createMovementCommandQueue(ElementMovementDTO dto) throws BusinessException {
		businessMovementConfig.createMovementCommandQueue( dto );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#getPendingMovCmdQueueRecords()
	 */
	@Override
	public List<MovCmdQueueVO> getPendingMovCmdQueueRecords() throws BusinessException {
		return businessMovementConfig.getPendingMovCmdQueueRecords();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#getErrorMovCmdQueueRecords()
	 */
	@Override
	public List<MovCmdQueueVO> getErrorMovCmdQueueRecords() throws BusinessException {
		return businessMovementConfig.getErrorMovCmdQueueRecords();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#getCompletedMovCmdQueueRecords()
	 */
	@Override
	public List<MovCmdQueueVO> getCompletedMovCmdQueueRecords() throws BusinessException {
		return businessMovementConfig.getCompletedMovCmdQueueRecords();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#getAllMovCmdQueueRecords()
	 */
	@Override
	public List<MovCmdQueueVO> getAllMovCmdQueueRecords() throws BusinessException {
		return businessMovementConfig.getAllMovCmdQueueRecords();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#callStockService(co.com.directv.sdii.model.vo.MovCmdQueueVO, co.com.directv.sdii.model.vo.MovStatusConfigVO)
	 */
	@Override
	public void callStockService(MovCmdQueueVO record) throws BusinessException {
		businessMovementConfig.callStockService(record);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#createMovCmdLogRecord(java.lang.Long, java.lang.String)
	 */
	@Override
	public void createMovCmdLogRecord(Long queueRecordId, String message, User user)
			throws BusinessException {
		businessMovementConfig.createMovCmdLogRecord(queueRecordId, message, user);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#changeMovCmdQueueRecordStatusToError(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	public void changeMovCmdQueueRecordStatusToError(MovCmdQueueVO record, User user)
			throws BusinessException {
		businessMovementConfig.changeMovCmdQueueRecordStatusToError(record, user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#changeMovCmdQueueRecordStatusToNoConfig(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	public void changeMovCmdQueueRecordStatusToNoConfig(MovCmdQueueVO record, User user)
			throws BusinessException {
		businessMovementConfig.changeMovCmdQueueRecordStatusToNoConfig(record, user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementConfigFacadeBeanLocal#changeMovCmdQueueRecordStatusToProcessing(co.com.directv.sdii.model.vo.MovCmdQueueVO)
	 */
	@Override
	public void changeMovCmdQueueRecordStatusToProcessing(MovCmdQueueVO record, User user)
			throws BusinessException {
		businessMovementConfig.changeMovCmdQueueRecordStatusToProcessing(record, user);
		
	}

}
