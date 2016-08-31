package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.TransferReasonBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.TransferReasonFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.TransferReasonVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author mrugeles <a href="mailto:mrugeles@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.TransferReasonFacadeLocal
 */
@Stateless(name="TransferReasonFacadeLocal",mappedName="ejb/TransferReasonFacadeLocal")
public class TransferReasonFacadeBean implements TransferReasonFacadeBeanLocal {

		
    @EJB(name="TransferReasonBusinessBeanLocal", beanInterface=TransferReasonBusinessBeanLocal.class)
    private TransferReasonBusinessBeanLocal businessTransferReason;

	@Override
	public void createTransferReason(TransferReasonVO obj) throws BusinessException {
		businessTransferReason.createTransferReason(obj);
		
	}

	@Override
	public void deleteTransferReason(TransferReasonVO obj)
			throws BusinessException {
		businessTransferReason.deleteTransferReason(obj);
		
	}

	@Override
	public List<TransferReasonVO> getAllTransferReasons() throws BusinessException {		
			return businessTransferReason.getAllTransferReasons();
	}

	@Override
	public TransferReasonResponse getTransferReasonByFilter(
			String transferReasonName, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		
		return businessTransferReason.getTransferReasonByFilter(transferReasonName, requestCollInfo);
	}

	@Override
	public TransferReasonVO getTransferReasonByID(Long id)
			throws BusinessException {
		return businessTransferReason.getTransferReasonByID(id);
	}

	@Override
	public List<TransferReasonVO> getTransferReasonsByIsActive(String status)
			throws BusinessException {
		return businessTransferReason.getTransferReasonsByIsActive(status);
	}

	@Override
	public void updateTransferReason(TransferReasonVO obj)
			throws BusinessException {
		businessTransferReason.updateTransferReason(obj);
		
	}

	@Override
	public List<TransferReasonVO> getAdjustmentReasonbyAdjustmentType(
			AdjustmentTypeVO adjustmentType) throws BusinessException {
		return businessTransferReason.getAdjustmentReasonbyAdjustmentType(adjustmentType);
	}
    
    
}
