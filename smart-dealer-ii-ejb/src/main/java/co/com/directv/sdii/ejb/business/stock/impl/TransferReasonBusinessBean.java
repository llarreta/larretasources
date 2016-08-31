package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.TransferReasonBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.TransferReason;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.TransferReasonVO;

import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TransferReasonDAOLocal;

@Stateless(name = "TransferReasonBusinessBeanLocal", mappedName = "ejb/TransferReasonBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransferReasonBusinessBean extends BusinessBase implements
		TransferReasonBusinessBeanLocal {
	private final static Logger log = UtilsBusiness.getLog4J(TransferReasonBusinessBean.class);
	
	@EJB(name = "TransferReasonDAOLocal", beanInterface = TransferReasonDAOLocal.class)
	private TransferReasonDAOLocal daoTransferReason;
	
	@EJB(name = "AdjustmentDAOLocal", beanInterface = AdjustmentDAOLocal.class)
	private AdjustmentDAOLocal adjustmentDAO;


	@Override
	public void createTransferReason(TransferReasonVO obj)
			throws BusinessException{
		log.debug("== Inicia createTransferReason/TransferReasonBusinessBean ==");

		try {
			TransferReason objPojo = UtilsBusiness.copyObject(TransferReason.class, obj);
			if(objPojo.getMovTypeIn()!=null){
				if( objPojo.getMovTypeIn().getId()==null ){
					objPojo.setMovTypeIn(null);
				}
			}
			
			if(objPojo.getMovTypeOut()!=null){
				if(objPojo.getMovTypeOut().getId()==null){
					objPojo.setMovTypeOut(null);
				}
			}
			checkAdjustmentType(obj);
			//Valida que no se pueda crear mas un causal de traslado con el mismo nombre
			TransferReasonResponse response = this.getTransferReasonByFilter(objPojo.getTransferReasonName(), null);
			if( response != null && response.getTransferReason() != null && !response.getTransferReason().isEmpty() ){
				Object[] params = {objPojo.getTransferReasonName().toUpperCase()};
				List<String> listParams = new ArrayList<String>();
				listParams.add( objPojo.getTransferReasonName().toUpperCase() );
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN468.getCode(), ErrorBusinessMessages.STOCK_IN468.getMessage(params),listParams);
			}
			
			String isAutomatic = CodesBusinessEntityEnum.TRANSFER_REASON_AUTOMATIC_NO.getCodeEntity();
			objPojo.setTransferReasonAutomatic(isAutomatic);
			//Se modifica el valor del nombre para ponerlo en mayusculas por defecto para la creacion
			objPojo.setTransferReasonName( obj.getTransferReasonName().toUpperCase() );
			
			
			daoTransferReason.createTransferReason(objPojo);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createTransferReason/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementStatus/ElementStatusBusinessBean ==");
        }
		
	}

	@Override
	public void deleteTransferReason(TransferReasonVO obj)
			throws BusinessException {
		log.debug("== Inicia deleteTransferReason/TransferReasonBusinessBean ==");
		try {
			TransferReason objPojo = UtilsBusiness.copyObject(TransferReason.class, obj);
			daoTransferReason.deleteTransferReason(objPojo);
		}  catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteTransferReason/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTransferReason/TransferReasonBusinessBean ==");
        }
		
	}

	@Override
	public List<TransferReasonVO> getAllTransferReasons()
			throws BusinessException {
		log.debug("== Inicia getAllTransferReasons/TransferReasonBusinessBean ==");
		try {
			return UtilsBusiness.convertList(daoTransferReason.getAllTransferReasons(), TransferReasonVO.class);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllTransferReasons/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTransferReasons/TransferReasonBusinessBean ==");
        }

	}

	@Override
	public TransferReasonResponse getTransferReasonByFilter(
			String transferReasonName, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getTransferReasonByFilter/TransferReasonBusinessBean ==");
		try {
			return daoTransferReason.getTransferReasonByFilter(transferReasonName, requestCollInfo);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransferReasonByFilter/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransferReasonByFilter/TransferReasonBusinessBean ==");
        }
	}

	@Override
	public TransferReasonVO getTransferReasonByID(Long id)
			throws BusinessException {
		log.debug("== Inicia getTransferReasonByID/TransferReasonBusinessBean ==");
		try{
			TransferReason objPojo = daoTransferReason.getTransferReasonByID(id);
			if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(TransferReasonVO.class, objPojo); 
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransferReasonByID/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransferReasonByID/TransferReasonBusinessBean ==");
        }
	}

	@Override
	public List<TransferReasonVO> getTransferReasonsByIsActive(String status)
			throws BusinessException {
		log.debug("== Inicia getTransferReasonsByIsActive/TransferReasonBusinessBean ==");
		try {
			return UtilsBusiness.convertList(daoTransferReason.getTransferReasonsByIsActive(status), TransferReasonVO.class);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTransferReasonsByIsActive/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTransferReasonsByIsActive/TransferReasonBusinessBean ==");
        }
	}

	@Override
	public void updateTransferReason(TransferReasonVO obj)
			throws BusinessException {
		log.debug("== Inicia updateTransferReason/TransferReasonBusinessBean ==");
		try {
			
			TransferReason objPojo = UtilsBusiness.copyObject(TransferReason.class, obj);
			if(objPojo.getMovTypeIn()!=null){
				if(objPojo.getMovTypeIn().getId() == null || objPojo.getMovTypeIn().getId().longValue() == 0L){
					objPojo.setMovTypeIn(null);
				}
			}
			
			if(objPojo.getMovTypeOut()!=null){
				if(objPojo.getMovTypeOut().getId() == null || objPojo.getMovTypeOut().getId().longValue() == 0L){
					objPojo.setMovTypeOut(null);
				}
			}
			
			checkAdjustmentType(obj);
			TransferReason temp = this.daoTransferReason.getTransferReasonByID( objPojo.getId() );
			if( temp != null )
				objPojo.setTransferReasonAutomatic(temp.getTransferReasonAutomatic());
			//Valida las dependencias con adjustment
			this.validateTransferReasonDependencies(objPojo);
			daoTransferReason.updateTransferReason(objPojo);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateTransferReason/TransferReasonBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTransferReason/TransferReasonBusinessBean ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Valida que no tenga ningun adjustment asociado y que en caso dado, no se haya modificado
	 * informacion que no se deba modificar
	 * @param objPojo
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	private void validateTransferReasonDependencies( TransferReason objPojo ) throws BusinessException, DAOServiceException, DAOSQLException{
		// se valida que no este vinculado a ajuste
		List<Adjustment> list = this.adjustmentDAO.getAllAdjustmentsByTransferReasonId( objPojo.getId() );
		boolean isModified = false;
		if( list != null && !list.isEmpty() ){
			TransferReason originalPojo = list.get(0).getTransferReason();
			//se valida que hayan modificado el tipo de ajuste
			if( objPojo.getAdjustmentType() != null 
					&& !objPojo.getAdjustmentType().getId().equals( originalPojo.getAdjustmentType().getId() ) ){
				isModified = true;
			//Se valida que hayan modificado el estado
			}else if( !objPojo.getIsActive().equals( originalPojo.getIsActive() ) ){
				isModified = true;
			// se valida que hayan modificado el tipo de movimiento de entrada
			}else if( objPojo.getMovTypeIn() != null 
					&& !objPojo.getMovTypeIn().getId().equals( originalPojo.getMovTypeIn().getId() )){
				isModified = true;
			//se valida que hayan modificado el tipo de movimiento de salida
			}else if( objPojo.getMovTypeOut() != null 
					&& !objPojo.getMovTypeOut().getId().equals( originalPojo.getMovTypeOut().getId() )){
				isModified = true;
			}
		}
		if( isModified ){
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN469.getCode(), ErrorBusinessMessages.STOCK_IN469.getMessage());
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<TransferReasonVO> getAdjustmentReasonbyAdjustmentType(AdjustmentTypeVO adjustmentType)
			throws BusinessException {
		log.debug("== Inicia getAdjustmentReasonbyAdjustmentType/AdjustmentBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentType.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			List<TransferReasonVO> listTransferReasonVOs = new ArrayList<TransferReasonVO>();
			
			listTransferReasonVOs = UtilsBusiness.convertList(daoTransferReason.getTransferReasonByAdjustmentType(adjustmentType.getCode(), CodesBusinessEntityEnum.TRANSFER_REASON_ACTIVE.getCodeEntity()), TransferReasonVO.class);
			return listTransferReasonVOs;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación getAdjustmentReasonbyAdjustmentType/AdjustmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAdjustmentReasonbyAdjustmentType/AdjustmentBusinessBean ==");
		}
		
	}
	
	
	
	private void checkAdjustmentType(TransferReasonVO obj) throws PropertiesException, BusinessException{
		String adjustmentTypeTransfer = CodesBusinessEntityEnum.ADJUSTMENT_TYPE_TRANSFER.getCodeEntity();
		
		if(adjustmentTypeTransfer.equals(obj.getAdjustmentType().getCode())){
			if(obj.getMovTypeIn()==null && obj.getMovTypeOut()==null){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
		}

		
	}

}
