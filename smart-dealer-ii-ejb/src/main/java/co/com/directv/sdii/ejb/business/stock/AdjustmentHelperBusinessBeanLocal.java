/**
 * Created 08/03/2016
 */
package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AdjustmentVO;

/**
 * This interface define helper methods for the adjustment process. 
 * 
 * @author Jorge Ariel Silva
 * @version 1.0
 * 
 */
@Local
public interface AdjustmentHelperBusinessBeanLocal {

	/**
	 * Method: Update the information of an AdjustmentVO object.
	 * @param obj contains AdjustmentVO data.
	 * @throws BusinessException in case of error while method operation
	 * @author
	 */
	public void updateAdjustment(AdjustmentVO obj, Long userId) throws BusinessException;
	
}
