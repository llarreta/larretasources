package co.com.directv.sdii.ejb.business.core;

import javax.ejb.Local;

import co.com.directv.sdii.dto.esb.dispatchtechnician.DispatchWorkOrderResponse;
import co.com.directv.sdii.dto.esb.dispatchtechnician.WithdrawWorkOrderFromTechnicianResponse;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DispatchWorkOrderLog;

@Local
public interface DispatchWorkOrderBusinessLocal {

	/**
	 * MÃ©todo encargado de procesar un <code> {@link DispatchWorkOrderResponse} </code> <br/>
	 * y guardarlo en la BD como un <code> {@link DispatchWorkOrderLog} </code> <br/>
	 * 
	 * @param response
	 * @throws BusinessException
	 */
	public void processDispatchWorkOrderResponse(DispatchWorkOrderResponse response, byte[] responseXml) throws BusinessException ;
	
	/**
	 * MÃ©todo encargado de procesar un <code> {@link WithdrawWorkOrderFromTechnicianResponse} </code> <br/>
	 * y guardarlo en la BD como un <code> {@link DispatchWorkOrderLog} </code> <br/>
	 * 
	 * @param response
	 * @throws BusinessException
	 */
	public void processWithdrawWoFromTechResponse(WithdrawWorkOrderFromTechnicianResponse response, byte[] responseXml) throws BusinessException ;

	
}
