
package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.broker.MarkWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.dto.WorkOrderMarkDTO;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkOrderWorkOrderMark;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderMarkDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@Stateless(name="WorkOrderMarkBusinessBeanLocal",mappedName="ejb/WorkOrderMarkBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderMarkBusinessBean extends BusinessBase implements WorkOrderMarkBusinessBeanLocal {

    @EJB(name="WorkOrderMarkDAOLocal", beanInterface=WorkOrderMarkDAOLocal.class)
    private WorkOrderMarkDAOLocal workOrderMarkDAO;
    
    @EJB(name="WorkOrderWorkOrderMarkDAOLocal", beanInterface=WorkOrderWorkOrderMarkDAOLocal.class)
    private WorkOrderWorkOrderMarkDAOLocal workOrderWorkOrderMarkDAO;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal userDAO;
    
    @EJB(name="MarkWorkOrderServiceBrokerLocal", beanInterface=MarkWorkOrderServiceBrokerLocal.class)
    private MarkWorkOrderServiceBrokerLocal markWorkOrderServiceBroker;
    
    @EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
    private WorkOrderDAOLocal workOrderDAO;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
    
    @EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDao;
	
    
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderMarkBusinessBean.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#createWorkOrderMark(co.com.directv.sdii.model.vo.WorkOrderMarkVO)
     */
    public void createWorkOrderMark(WorkOrderMarkVO workOrderMarkVO) throws BusinessException {
		log.debug("== Inicia createWorkOrderMark/WorkOrderMarkBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(workOrderMarkVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            workOrderMarkDAO.createWorkOrderMark(workOrderMarkVO);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createWorkOrderMark/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderMark/WorkOrderMarkBusinessBean ==");
        }
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getWorkOrderMarkByID(java.lang.Long)
     */
    public WorkOrderMarkVO getWorkOrderMarkByID(Long id) throws BusinessException {
		log.debug("== Inicia getWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	WorkOrderMark WorkOrderMark =  workOrderMarkDAO.getWorkOrderMarkByID(id);
        	return  UtilsBusiness.copyObject(WorkOrderMarkVO.class, WorkOrderMark);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getCodeWorkOrderMarkByID(java.lang.Long)
     */
    public String getCodeWorkOrderMarkByID(Long id) throws BusinessException {
		log.debug("== Inicia getCodeWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        try {
            return workOrderMarkDAO.getCodeWorkOrderMarkByID(id);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getCodeWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCodeWorkOrderMarkByID/WorkOrderMarkBusinessBean ==");
        }
	}
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getWorkOrderMarkByCode(java.lang.String)
     */
    public WorkOrderMarkVO getWorkOrderMarkByCode(String code) throws BusinessException {
		log.debug("== Inicia getWorkOrderMarkByCode/WorkOrderMarkBusinessBean ==");
        try {
        	WorkOrderMark WorkOrderMark =  workOrderMarkDAO.getWorkOrderMarkByCode(code);
        	return  UtilsBusiness.copyObject(WorkOrderMarkVO.class, WorkOrderMark);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderMarkByCode/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderMarkByCode/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#updateWorkOrderMark(co.com.directv.sdii.model.vo.WorkOrderMarkVO)
     */
    public WorkOrderMarkVO updateWorkOrderMark(WorkOrderMarkVO obj) throws BusinessException {
		log.debug("== Inicia updateWorkOrderMark/WorkOrderMarkBusinessBean ==");
        try {
        	WorkOrderMark WorkOrderMark = (WorkOrderMark) workOrderMarkDAO.updateWorkOrderMark(obj);
        	return  UtilsBusiness.copyObject(WorkOrderMarkVO.class, WorkOrderMark);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateWorkOrderMark/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkOrderMark/WorkOrderMarkBusinessBean ==");
        }
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#deleteWorkOrderMark(co.com.directv.sdii.model.vo.WorkOrderMarkVO)
     */
    public void deleteWorkOrderMark(WorkOrderMarkVO obj) throws BusinessException {
		log.debug("== Inicia deleteWorkOrderMark/WorkOrderMarkBusinessBean ==");
        try {
            workOrderMarkDAO.deleteWorkOrderMark(obj);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteWorkOrderMark/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkOrderMark/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getAllWorkOrderMark()
     */
    public List<WorkOrderMarkVO> getAllWorkOrderMark() throws BusinessException {
		log.debug("== Inicia getAll/WorkOrderMarkBusinessBean ==");
        try {
            List<WorkOrderMark> resultpojo =  workOrderMarkDAO.getAll();
            return UtilsBusiness.convertList(resultpojo, WorkOrderMarkVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAll/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(java.lang.Long)
     */
    public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(Long woId) throws BusinessException {
    	
    	log.debug("== Inicia getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        try {
        	
        	List<WorkOrderWorkOrderMark> workOrderWorkOrderMark =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(woId);
        	return UtilsBusiness.convertList(workOrderWorkOrderMark, WorkOrderWorkOrderMarkVO.class);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        }
    	
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getWorkOrderWorkOrderMarkIsUnMarkAttention(java.lang.Long)
     */
    public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsUnMarkAttention(Long woId) throws BusinessException {
    	
    	log.debug("== Inicia getWorkOrderWorkOrderMarkIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        try {
        	
        	List<WorkOrderWorkOrderMark> workOrderWorkOrderMark =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsUnMarkAttention(woId);
        	return UtilsBusiness.convertList(workOrderWorkOrderMark, WorkOrderWorkOrderMarkVO.class);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderWorkOrderMarkIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkIsUnMarkAttention/WorkOrderMarkBusinessBean ==");
        }
    	
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#getWorkOrderMarkDTOIsActiveByWoId(java.lang.Long)
     */
    public List<WorkOrderMarkDTO> getWorkOrderMarkDTOIsActiveByWoId(Long workOrderId) throws BusinessException {
		log.debug("== Inicia getAll/WorkOrderMarkBusinessBean ==");
        try {
        	List<WorkOrderMarkDTO> workOrderMarkDTOs =  workOrderWorkOrderMarkDAO.getWorkOrderMarkDTOIsActive(workOrderId);
            return workOrderMarkDTOs;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAll/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /**
     * Metodo: Permite marcar una workOrder
     * @param workOrderId
     * @param workOrderMarkId
     * @param userId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public WorkOrderWorkOrderMarkVO markWorkOrder(Long workOrderId, 
	    		                                  Long workOrderMarkId,
	    		                                  Long userId) throws BusinessException {
		log.debug("== Inicia getAll/WorkOrderMarkBusinessBean ==");
        try {
        	WorkOrderWorkOrderMark workOrderWorkOrderMark =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(workOrderId,workOrderMarkId);
            if(workOrderWorkOrderMark==null){
            	
            	workOrderWorkOrderMark = createMarkWorkOrder(workOrderId,workOrderMarkId,userId);
            	
            }
//            //Se implementa para colocar historial si ya existe una marca
//            else{
//            		
//            }
            return  UtilsBusiness.copyObject(WorkOrderWorkOrderMarkVO.class, workOrderWorkOrderMark);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAll/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /**
     * Metodo: Permite desmarcar una workOrder
     * @param workOrderId
     * @param workOrderMarkId
     * @param userId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public WorkOrderWorkOrderMarkVO unMarkWorkOrder(Long workOrderId, 
    		                               Long workOrderMarkId,
    		                               Long userId) throws BusinessException {
		log.debug("== Inicia unMarkWorkOrder/WorkOrderMarkBusinessBean ==");
        try {
        	WorkOrderWorkOrderMark workOrderWorkOrderMark =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark(workOrderId,workOrderMarkId);
            if(workOrderWorkOrderMark!=null){
            	
            	updateUnMarkWorkOrder(workOrderWorkOrderMark,userId);
            	
            }
//            //Se implementa para colocar historial si ya esta desmarcada
//            else{
//            		
//            }
            return  UtilsBusiness.copyObject(WorkOrderWorkOrderMarkVO.class, workOrderWorkOrderMark);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación unMarkWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina unMarkWorkOrder/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#unMarkWorkOrderAttention(java.lang.Long, java.lang.Long)
     */
    public void unMarkWorkOrderAttention(Long workOrderId,Long userId) throws BusinessException {
		log.debug("== Inicia unMarkWorkOrderAttention/WorkOrderMarkBusinessBean ==");
        try {
        	
        	List<WorkOrderWorkOrderMark> workOrderWorkOrderMarks =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsActiveAndIsUnMarkAttention(workOrderId);

        	if(workOrderWorkOrderMarks != null && !workOrderWorkOrderMarks.isEmpty()){
	        	for (WorkOrderWorkOrderMark workOrderWorkOrderMark : workOrderWorkOrderMarks) {
	        		updateUnMarkWorkOrder(workOrderWorkOrderMark,userId);
				}
        	}else{
        		log.info(" La WorkOrder con id " + workOrderId + " no tiene marcas activas para desmarcar en la finalización o atención.");
        	}

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación unMarkWorkOrderAttention/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina unMarkWorkOrderAttention/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /**
     * Metodo: Permite desmarcar una workOrder
     * @param workOrderCodes
     * @param workOrderMarkId
     * @param userId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public List<WorkOrderWorkOrderMarkVO> getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(List<String> workOrderCodes, 
    		                               Long workOrderMarkId) throws BusinessException {
		log.debug("== Inicia getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        try {
        	List<WorkOrderWorkOrderMark> workOrderWorkOrderMarkList =  workOrderWorkOrderMarkDAO.getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(workOrderCodes,workOrderMarkId);
            
        	return  UtilsBusiness.convertList(workOrderWorkOrderMarkList, WorkOrderWorkOrderMarkVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        }
	}


    public List<WorkOrderMarkVO> getWorkOrderMarkByIsUnMarkAttention() throws BusinessException{
    
		log.debug("== Inicia getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        try {
        	List<WorkOrderMark> workOrderMarks =  workOrderMarkDAO.getWorkOrderMarkByIsUnMarkAttention();
            return UtilsBusiness.convertList(workOrderMarks, WorkOrderMarkVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderWorkOrderMarkIsActiveByCodeWorkOrderMark/WorkOrderMarkBusinessBean ==");
        }
	}
    
    /**
     * Metodo: crea la marca de una workOrder
     * @param workOrderWorkOrderMark
     * @param workOrderId
     * @param codeWorkOrderMark
     * @param userId
     * @throws PropertiesException
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    private WorkOrderWorkOrderMark createMarkWorkOrder(Long workOrderId, 
            Long workOrderMarkId,
            Long userId) throws PropertiesException, DAOServiceException, DAOSQLException{
    	
    	Date userMarkDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDAO);
    	WorkOrderWorkOrderMark workOrderWorkOrderMark = new WorkOrderWorkOrderMark();
    	workOrderWorkOrderMark.setIsActive(CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_ACTIVE.getCodeEntity());
    	User user = new User(); 
    	user.setId(userId);
    	workOrderWorkOrderMark.setUserMark(user);
    	workOrderWorkOrderMark.setUserMarkDate(userMarkDate);
    	WorkOrder workOrder = new WorkOrder(); 
    	workOrder.setId(workOrderId);
    	workOrderWorkOrderMark.setWorkOrder(workOrder);
    	WorkOrderMark workOrderMark = new WorkOrderMark();
    	workOrderMark.setId(workOrderMarkId);
    	workOrderWorkOrderMark.setWorkOrderMark(workOrderMark);
    	workOrderWorkOrderMarkDAO.createWorkOrderWorkOrderMark(workOrderWorkOrderMark);
    	return workOrderWorkOrderMark;
    	
    }
    
    /**
     * Metodo: inactiva la marca de la workOrder
     * @param workOrderWorkOrderMark
     * @param userId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @throws PropertiesException <tipo> <descripcion>
     * @author
     */
    private WorkOrderWorkOrderMark updateUnMarkWorkOrder(WorkOrderWorkOrderMark workOrderWorkOrderMark,Long userId) throws DAOServiceException, DAOSQLException, PropertiesException{
    	
    	Date userUnMarkDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDAO);
    	workOrderWorkOrderMark.setIsActive(CodesBusinessEntityEnum.WORK_ORDER_MARK_IS_INACTIVE.getCodeEntity());
    	User user = new User();
    	user.setId(userId);
    	workOrderWorkOrderMark.setUserUnMark(user);
    	workOrderWorkOrderMark.setUserUnMarkDate(userUnMarkDate);
    	workOrderWorkOrderMarkDAO.updateWorkOrderWorkOrderMark(workOrderWorkOrderMark);
    	
    	return workOrderWorkOrderMark;
    	
    }
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#downLoadContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
	 */
	public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
		
		log.debug("== Inicia la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			return markWorkOrderServiceBroker.downLoadContractWorkOrder(request);
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación downLoadContractWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#requiredContractWorkOrder(co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO)
	 */
	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
		
		log.debug("== Inicia la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			return markWorkOrderServiceBroker.requiredContractWorkOrder(request);
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación requiredContractWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#downLoadContractWorkOrder(java.lang.Long)
	 */
	public FileResponseDTO downLoadContractWorkOrder(Long woId) throws BusinessException{
		
		log.debug("== Inicia la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			ContractWorkOrderRequestDTO request = getContractWorkOrder(woId);
			return markWorkOrderServiceBroker.downLoadContractWorkOrder(request);
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación downLoadContractWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#requiredContractWorkOrder(java.lang.Long)
	 */
	public boolean requiredContractWorkOrder(Long woId) throws BusinessException{
		
		log.debug("== Inicia la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			ContractWorkOrderRequestDTO request = getContractWorkOrder(woId);
			return markWorkOrderServiceBroker.requiredContractWorkOrder(request);
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación requiredContractWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/**
	 * Metodo: permite obtener una instancia ContractWorkOrder desde un woId
	 * @param woId
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 */
	private ContractWorkOrderRequestDTO getContractWorkOrder(Long woId) throws DAOServiceException, DAOSQLException, BusinessException{
		
		ContractWorkOrderRequestDTO contractWorkOrder = new ContractWorkOrderRequestDTO();
		WorkOrder workOrder =  workOrderDAO.getWorkOrderByID(woId);
		
		UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
		
		contractWorkOrder = new ContractWorkOrderRequestDTO();
		contractWorkOrder.setCountryCode(workOrder.getCountry().getCountryCode());
		contractWorkOrder.setCustomerCode(workOrder.getCustomer().getCustomerCode());
		
		String serviceIbsCode = getServiceIbsCode(workOrder.getWorkOrderServices());
		contractWorkOrder.setServiceIbsCode(serviceIbsCode);
		
		return contractWorkOrder;
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#markOrUnMarkWorkOrder(java.lang.Long, java.lang.Long, java.lang.Long, boolean)
	 */
	public void markOrUnMarkWorkOrder(Long woId,Long workOrderMarkId,Long userId,boolean markOrUnMark) throws BusinessException{
		log.debug("== Inicia la operación markOrUnMarkWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			if(markOrUnMark){
				this.markWorkOrder(woId,workOrderMarkId,userId);
			}else{
				this.unMarkWorkOrder(woId,workOrderMarkId,userId);
			}
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación markOrUnMarkWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación markOrUnMarkWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	
	public String getServiceIbsCode(Set<WorkOrderService> workOrderServices) throws BusinessException{
		log.debug("== Inicia la operación markOrUnMarkWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			String serviceIbsCode = null;
			String tempServiceIbsCode = null;
			int cantServiceOpen = 0;
			for (WorkOrderService workOrderService : workOrderServices) {
				if(workOrderService.getService().getServiceCode().length()==3){
					cantServiceOpen++;
					tempServiceIbsCode = workOrderService.getService().getServiceIbsCode(); 
				}
			}
			
			//Si no existe o existe mas de un servicio de apertura el serviceIbsCode se retorna en null
			if(cantServiceOpen==1){
				serviceIbsCode = tempServiceIbsCode;
			}
			return serviceIbsCode;
			
		} catch (Throwable e){
			log.error("== Error al tratar de ejecutar la operación markOrUnMarkWorkOrder/WorkOrderMarkBusinessBean ==");
        	throw this.manageException(e);
		} finally {
			log.debug("== Termina la operación markOrUnMarkWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.toa.CoreWorkorderImporterServiceLocalTOA#populateRequiredContract(co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public void populateRequiredContract(WorkOrderDTO workOrderDto)  {

		log.debug("== Inicia la operación populateRequiredContract/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			SystemParameter systemParameterGetContractSystemExternal = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_GET_CONTRACT_SYSTEM_EXTERNAL.getCodeEntity(), workOrderDto.getWorkOrder().getCountry().getId());
			boolean isGetContractSystemExternal = systemParameterGetContractSystemExternal==null ? false : systemParameterGetContractSystemExternal.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ? true : false;
			
			if(isGetContractSystemExternal){
				
				boolean callServiceRequiredContract = false;
				
				ContractWorkOrderRequestDTO contractWorkOrderRequestDTO = new ContractWorkOrderRequestDTO();
				
				for (WorkOrderService workOrderService: workOrderDto.getWorkOrder().getWorkOrderServices()) {
					if(workOrderService.getService().getIsContractRequired()!=null && workOrderService.getService().getIsContractRequired().equals(CodesBusinessEntityEnum.CALL_SERVICE_REQUIRED_CONTRACT.getCodeEntity())){
						callServiceRequiredContract=true;
						break;
					}
				}
				
				if(callServiceRequiredContract){
					contractWorkOrderRequestDTO.setCountryCode(workOrderDto.getWorkOrder().getCountry().getCountryCode());
					contractWorkOrderRequestDTO.setCustomerCode(workOrderDto.getWorkOrder().getCustomer().getCustomerCode());
					contractWorkOrderRequestDTO.setServiceIbsCode(this.getServiceIbsCode(workOrderDto.getWorkOrder().getWorkOrderServices()));
					
					workOrderDto.setMarkWorkOrderRequiredContract(new Boolean(this.requiredContractWorkOrder(contractWorkOrderRequestDTO)));
					
				}else{
					workOrderDto.setMarkWorkOrderRequiredContract(new Boolean(callServiceRequiredContract));
				}
			}
		} catch (Throwable ex){
			workOrderDto.setWarning(true);
			BusinessException e = super.manageException(ex);
			workOrderDto.setErrorCode(e.getMessageCode());
			workOrderDto.setErrorMessage(e.getMessage());
			return;
		} finally {
			log.debug("== Termina la operación populateRequiredContract/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.toa.CoreWorkorderImporterServiceLocalTOA#populateWorkOrderMarkPriorityService(co.com.directv.sdii.dto.esb.WorkOrder, co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	public void populateWorkOrderMarkPriorityService(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,WorkOrderDTO workOrderDto) throws BusinessException {
		try{
			boolean isWorkOrderMarkPriorityService = false;
			for (WorkOrderService workOrderService: workOrderDto.getWorkOrder().getWorkOrderServices()) {
				if(workOrderService.getService().getIsPriority()!=null && workOrderService.getService().getIsPriority().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
					isWorkOrderMarkPriorityService=true;
					break;
				}
			}
			
			workOrderDto.setMarkWorkOrderPriorityService(new Boolean(isWorkOrderMarkPriorityService));
		}catch (Throwable e) {
			log.error("error en el metodo populateWorkOrderMarkPriorityService "+e.getMessage());
			throw manageException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal#populateWorkOrderMarkPriorityNexus(co.com.directv.sdii.dto.esb.WorkOrder, co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	@Override
	public void populateWorkOrderMarkPriorityNexus(co.com.directv.sdii.dto.esb.WorkOrder pIbsWorkOrder,	WorkOrderDTO workOrderDto) throws BusinessException{
		try{
			if(workOrderDto!=null
					&& workOrderDto.getWorkOrder()!=null
					&& workOrderDto.getWorkOrder().getCustomer()!=null
					&& workOrderDto.getWorkOrder().getCustomer().getCustType()!=null
					&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass()!=null
					&& workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass().getIsPriority()!=null){
				if(workOrderDto.getWorkOrder().getCustomer().getCustType().getCustomerClass().getIsPriority().equalsIgnoreCase(CodesBusinessEntityEnum.
						BOOLEAN_TRUE.getCodeEntity())){
					workOrderDto.setMarkPriorityNexus(new Boolean(true));
				}
				else{
					workOrderDto.setMarkPriorityNexus(new Boolean(false));
				}
			}else{
				workOrderDto.setMarkPriorityNexus(new Boolean(false));
			}
		}catch (Throwable e) {
			log.error("error en el metodo populateWorkOrderMarkPriorityService "+e.getMessage());
			throw manageException(e);
		}
	}
	
	
    
	
}
