
package co.com.directv.sdii.ejb.business.assign.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ReportWorkOrdersLastDayDTO;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.FtpConfiguration;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.file.FtpConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

@Stateless(name="WorkOrderBusinessBeanLocal",mappedName="ejb/WorkOrderBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderBusinessBean extends BusinessBase implements WorkOrderBusinessBeanLocal {

    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDAO;

	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
    private WorkOrderDAOLocal workOrderDAO;

	@EJB(name="FtpConfigurationDAOLocal", beanInterface=FtpConfigurationDAOLocal.class)
    private FtpConfigurationDAOLocal ftpConfigurationDAOLocal;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="CustomerClassDAOLocal", beanInterface=CustomerClassDAOLocal.class)
	private CustomerClassDAOLocal customerClassDAO;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;

    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@EJB
	private ConfigParametrosBusinessLocal configParametrosBusiness;
	
	@EJB
    private WarehouseElementDAOLocal warehouseElementDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderBusinessBean.class);
    
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal#getWorkOrderByStateIbsWoCodeIbsWoCustCodeServiceCategoryCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerVO otherServices2CustmerSkill(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String addressCode, String address) throws BusinessException {
		List<WorkOrder> workOrders = null ;
		WorkOrder unaWorkOrder = null ;
		Long dealerId = null ;
		Dealer dealer = null ;
		DealerVO dealerVO = null ;
		
		log.debug("== Inicia otherServices2CustmerSkill/WorkOrderBusinessBean ==");
		UtilsBusiness.assertNotNull(countryIso2Code, ErrorBusinessMessages.ALLOCATOR_AL033.getCode(), ErrorBusinessMessages.ALLOCATOR_AL033.getMessage());
        UtilsBusiness.assertNotNull(ibsCustomerCode, ErrorBusinessMessages.ALLOCATOR_AL039.getCode(), ErrorBusinessMessages.ALLOCATOR_AL039.getMessage());
        
        UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.ALLOCATOR_AL041.getCode(), ErrorBusinessMessages.ALLOCATOR_AL041.getMessage());
        // UtilsBusiness.assertNotNull(scheduleDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(addressCode, ErrorBusinessMessages.ALLOCATOR_AL029.getCode(), ErrorBusinessMessages.ALLOCATOR_AL029.getMessage());
        // UtilsBusiness.assertNotNull(address, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

        try {
        	
        	workOrders = workOrderDAO.getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress(countryIso2Code, ibsCustomerCode, postalCode, scheduleDate, addressCode, address);
        	if ( workOrders.size()>0 ) { 
        	   unaWorkOrder = (WorkOrder)(workOrders.get(0)) ;
        	   dealerId = unaWorkOrder.getDealerId();
        	   dealer = dealersDAO.getDealerByID(dealerId);
        	   // sdii_CODE_dealer_satus_active   "N"
               if ( dealer.getDealerStatus().getStatusCode().equalsIgnoreCase( CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity() ) ) {
        	      dealerVO = UtilsBusiness.copyObject(DealerVO.class, dealer);
               }
        	}
            return dealerVO ; 
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación otherServices2CustmerSkill/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina otherServices2CustmerSkill/DealerServiceSubCategoryBusinessBean ==");
        }        
	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal#getDealerFromLastWoFromCustomer(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode) throws BusinessException {
		log.debug("== Inicia getDealerFromLastWoFromCustomer/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(ibsCustomerCode, ErrorBusinessMessages.ALLOCATOR_AL039.getCode(), ErrorBusinessMessages.ALLOCATOR_AL039.getMessage());
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        try {
            List<Dealer> resultpojo =  workOrderDAO.getDealerFromLastWoFromCustomer(ibsCustomerCode, countryCode);
            List<DealerVO> result = UtilsBusiness.convertList(resultpojo, DealerVO.class);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerFromLastWoFromCustomer/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerFromLastWoFromCustomer/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderVO getWorkOrderByCode(String woCode) throws BusinessException {
		log.debug("== Inicia getWorkOrderByCode/DealerCustomerTypesWoutPcBusinessBean ==");
        try {
            WorkOrder workOrder =  workOrderDAO.getWorkOrderByCode(woCode);
            return UtilsBusiness.copyObject(WorkOrderVO.class, workOrder);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrderByCode/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByCode/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal#getDealerFromWoWithWarranty(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealerFromWoWithWarranty(String ibsCustomerCode,
			Long countryCode, List<String> serviceCodes) throws BusinessException {
		log.debug("== Inicia getDealerFromWoWithWarranty/DealerCustomerTypesWoutPcBusinessBean ==");
//		serviceCodes = new ArrayList()<String>();
//		serviceCodes.add("SA06");
//		serviceCodes.add("SM03");
//		serviceCodes.add("SD11");	
        UtilsBusiness.assertNotNull(ibsCustomerCode, ErrorBusinessMessages.ALLOCATOR_AL039.getCode(), ErrorBusinessMessages.ALLOCATOR_AL039.getMessage());
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        UtilsBusiness.assertNotNull(serviceCodes, ErrorBusinessMessages.ALLOCATOR_AL043.getCode(), ErrorBusinessMessages.ALLOCATOR_AL043.getMessage());
        List<Dealer> dealersList = new ArrayList<Dealer>();
        try {
//			List<WorkOrder> lastWorkOrderList =  workOrderDAO.getLastWoFromCustomerFinisherOrRealized(ibsCustomerCode, countryCode);
//			if(lastWorkOrderList==null){
//				return new ArrayList<DealerVO>();
//			}
//			if(lastWorkOrderList.size()==0 && lastWorkOrderList.isEmpty()){
//				return new ArrayList<DealerVO>();
//			}
//			WorkOrder lastWo = lastWorkOrderList.get(0);
//			
//			List<Service> servicesList = workOrderServiceDAO.getServicesOfWorkOrderServiceByWorkorderId(lastWo.getId());
//			if(servicesList==null){
//				return new ArrayList<DealerVO>();
//			}
//			if(servicesList.size()==0 && servicesList.isEmpty()){
//				return new ArrayList<DealerVO>();
//			}
//			
//			List<ServiceTypeWarranty> serviceTypeWarrantyList = null;
//			List<ServiceAreWarranty> serviceAreWarrantiesList = null; 
//			ServiceTypeWarranty serviceTypeWarranty = null;
//			ServiceType serviceType = null;
//			ServiceCategory serviceCategory = null;
//			Date attendedWo = null;
//			Long warrantyPeriod = null;
//			
//			for (Service service : servicesList) {
//				serviceCategory = service.getServiceCategory();
//				serviceType = serviceCategory.getServiceType();
//				serviceTypeWarrantyList = serviceTypeWarrantyDAO.getServiceTypeWarrantiesByServiceType(serviceType.getId(), countryCode);
//				if(serviceTypeWarrantyList==null){
//					continue;
//				}
//				if(serviceTypeWarrantyList.size()==0 && serviceTypeWarrantyList.isEmpty()){
//					continue;
//				}
//				serviceTypeWarranty = serviceTypeWarrantyList.get(0);
//				
//				serviceAreWarrantiesList = serviceAreWarrantyDAO.getServiceAreWarrantyByServiceWarranty(serviceTypeWarranty.getId());
//				if(serviceAreWarrantiesList==null){
//					continue;
//				}
//				if(serviceAreWarrantiesList.size()==0 && serviceAreWarrantiesList.isEmpty()){
//					continue;
//				}
//        for (ServiceAreWarranty serviceAreWarranty : serviceAreWarrantiesList) {
//			for (String serviceCode : serviceCodes) {
//				if(serviceAreWarranty.getService().getServiceCode().equalsIgnoreCase(serviceCode)){
//					warrantyPeriod = serviceTypeWarranty.getWarrrantyPeriod();
//					attendedWo = lastWo.getWoRealizationDate();
//					attendedWo = UtilsBusiness.dateWithoutHour(attendedWo);
//					if(UtilsBusiness.addDate(attendedWo, warrantyPeriod.intValue()).after(UtilsBusiness.actualDateWithoutHour())){
//						Dealer dealer = dealersDAO.getDealerByID(lastWo.getDealerId());
//						dealersList.add(dealer);
//					}
//				}
//			}
//		}
        	List<Object[]> serviceWithWarrantys =  workOrderDAO.getServiceWithWarranty(ibsCustomerCode, countryCode);

		    Date attendedWo = null;
			Long warrantyPeriod = null;
    		for (Object[] serviceWithWarranty : serviceWithWarrantys) {
				for (String serviceCode : serviceCodes) {
					String serviceCodeWarranty = (String) serviceWithWarranty[0];
					if(serviceCodeWarranty.equalsIgnoreCase(serviceCode)){
						warrantyPeriod = ((BigDecimal) serviceWithWarranty[1]).longValue();
						attendedWo = (Date) serviceWithWarranty[2];
						attendedWo = UtilsBusiness.dateWithoutHour(attendedWo);
						if(UtilsBusiness.addDate(attendedWo, warrantyPeriod.intValue()).after(UtilsBusiness.actualDateWithoutHour())){
							Dealer dealer = dealersDAO.getDealerByID(((BigDecimal) serviceWithWarranty[3]).longValue());
							dealersList.add(dealer);
						}
					}
				}
			}
//			}
			List<DealerVO> resultDto = UtilsBusiness.convertList(dealersList, DealerVO.class);
            return resultDto;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerFromWoWithWarranty/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerFromWoWithWarranty/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal#getDealerFromLastWoFromCustomer(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws BusinessException {
		log.debug("== Inicia getWorkorderStatusByIBS6StatusCode/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(ibs6StatusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        Long result = null;
        try {
        	WorkorderStatus workorderStatus =  workorderStatusDAO.getWorkorderStatusByIBS6StatusCode(ibs6StatusCode);
            return workorderStatus;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkorderStatusByIBS6StatusCode/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkorderStatusByIBS6StatusCode/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}
	
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForLastDay/WorkOrderBusinessBean ==");
        try {
    		log.debug("== Inicia getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
    		try{
    			List<ReportWorkOrdersLastDayDTO> wosld = workOrderDAO.getWorkOrdersForLastDay(countryId);
    			Date now = new Date();
    			co.com.directv.sdii.model.pojo.Country country = countryDao.getCountriesByID(countryId);
    			Date dateNow = UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO);
    			Calendar calendarNow = Calendar.getInstance();
    			calendarNow.setTimeInMillis(UtilsBusiness.getDateLastChangeOfUser(countryId, userDao, systemParameterDAO).getTime());
    			String fileName = country.getCountryCode()+"_"+calendarNow.get(Calendar.YEAR)+"_"+(calendarNow.get(Calendar.MONTH)+1)+"_"+calendarNow.get(Calendar.DAY_OF_MONTH)+"_HSP_"+calendarNow.get(Calendar.HOUR_OF_DAY) + ".xls";
    			ByteArrayOutputStream baos = null;
    			baos = excelGenerator.createExcelStreamWithJasper(wosld, null, null, CodesBusinessEntityEnum.JASPER_FILE_NAME_REPORT_WORKORDER_LAST_DAY.getCodeEntity());

    			if( baos == null ){
    				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
    			}
    			DataSource ds = new  ByteArrayDataSource( baos.toByteArray() , "application/vnd.ms-excel" );
    			DataHandler dh = new DataHandler(ds);
    			List<FtpConfiguration> ftpConfigurations=this.ftpConfigurationDAOLocal.getFtpConfigurationByCountryIdAndCode(countryId, CodesBusinessEntityEnum.CODE_REPORT_LAST_DAY_WO.getCodeEntity());
    			
    			for(FtpConfiguration ftpc: ftpConfigurations){
    			   if(ftpc.getIsSecurity().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
    				   sendFileSFtp(fileName, dh.getInputStream(),ftpc.getHost(),ftpc.getPort().intValue(),ftpc.getFtpConfigurationUser(),ftpc.getPassword(),ftpc.getPath());
    			   }else{
    				   sendFileFtp(fileName, dh.getInputStream(),ftpc.getHost(),ftpc.getPort().intValue(),ftpc.getFtpConfigurationUser(), ftpc.getPassword(),ftpc.getPath());
    			   }
    			}
    			
    		} catch (Throwable ex) {
    			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForReport/ReportGeneratorBusinessBean");
    			throw super.manageException(ex);
    		} finally {
    			log.debug("== Termina getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
    		}
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWorkOrdersForLastDay/WorkOrderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrdersForLastDay/WorkOrderBusinessBean ==");
        }
	}
	
	public void sendFileFtp(String localPath, InputStream file,
			String server, int port, String user, String pass, String remotePath) throws BusinessException {

		try {
			UtilsBusiness.sendFileFtp(localPath, file, server, port, user, pass, remotePath);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación sendFileFtp/WorkOrderBusinessBean ==");
        	throw this.manageException(ex);
		}

	}
	
	public void sendFileSFtp(String fileName, InputStream file,String ip,int puerto,String sUsername,String sPassword,String directorio)  throws BusinessException {
		try {
			UtilsBusiness.sendFileSFtp(fileName, file, ip, puerto, sUsername, sPassword, directorio);
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación sendFileSFtp/WorkOrderBusinessBean ==");
			throw this.manageException(ex);
		}
	}
	
	public List<WorkOrderTrayDTO> getWorkOrdersByWoIdForSimilarState(List<Long> workOrderIds, String codesStates) throws BusinessException{
		try {
			List<WorkOrder> WorkOrders = workOrderDAO.getWorkOrdersByIds(workOrderIds);
			String[] codesStatesArray=codesStates.split(",");
			List<String> woStates=Arrays.asList(codesStatesArray);
			List<WorkOrderTrayDTO> workOrders=workOrderDAO.getWorkOrdersByStatesAndCustomerAndDealer(woStates,WorkOrders);
			return workOrders;
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersByWoIdForDificulty/WorkOrderBusinessBean ==");
			throw this.manageException(ex);
		}
		
	}
	
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public WorkOrder getWorkOrderByID(Long id) throws BusinessException {
        log.debug("== Inicio getWorkOrderByID/WorkOrderDAO ==");
        try {
            return this.workOrderDAO.getWorkOrderByID(id);
        }catch (Throwable ex) {
            log.error("== Error consultando WorkOrder por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderByID/WorkOrderDAO ==");
        }
    }
	
	public List<WorkOrderVO> getWorkOrdersByWoIdForSimilarStateByCode(List<String> workOrderCodes, String codesStates) throws BusinessException{
		try {
			List<WorkOrder> WorkOrders = workOrderDAO.getWorkOrdersByCodes(workOrderCodes);
			//String codesStates=CodesBusinessEntityEnum.INDIVIDUAL_DIFICULTAD_BANDEJAAUTO.getCodeEntity();
			String[] codesStatesArray=codesStates.split(",");
			List<String> woStates=Arrays.asList(codesStatesArray);
			List<WorkOrder> workOrders=workOrderDAO.getWorkOrdersByStatesAndCustomerAndDealerForPdf(woStates,WorkOrders);
			List<WorkOrderVO> workOrdersVO=UtilsBusiness.convertList(workOrders, WorkOrderVO.class);
			return workOrdersVO;
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersByWoIdForDificulty/WorkOrderBusinessBean ==");
			throw this.manageException(ex);
		}
		
	}

	/**
	 * Metodo encargado de extraer el serial que se trajo de ibs para el servicio de una wo, si la wo no es de desconexion, retorna un String vacio
	 * @param woId
	 * @param woServiceId
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public String getSerialForAttentionDesconectionWO(Long woId, Long woServiceId) throws BusinessException {
		try {
			UtilsBusiness.assertNotNull(woId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+" el id de la work order es requerido ");
			UtilsBusiness.assertNotNull(woServiceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+" el id del servicio de la work order es requerido ");
		
			WorkOrder wo=workOrderDAO.getWorkOrderByID(woId);
			
			if(wo==null){
				throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
						.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
			}
			
			if(!wo.getWoTypeByWoTypeId().getWoTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum
					.WORKORDER_TYPE_DISCONNECTION.getCodeEntity())){
				return "";
			}
			else{
				WorkOrderService workOrderService = null;
				for(WorkOrderService wos: wo.getWorkOrderServices()){
					if(wos.getId().equals(woServiceId) ){
						workOrderService = wos;
						break;
					}
				}
				if(workOrderService!=null){
					return workOrderService.getSerialNumber();
				}
				else{
					throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
							.CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST.getMessage());
				}
			}
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getSerialForAttentionDesconectionWO/WorkOrderBusinessBean ==");
			throw this.manageException(ex);
		}
	}
    /**
     * Metodo que verifica los seriales que se agregaran a un servicio de desconexion en la atencion
     * @param request parametros necesarios para la validacion, id de work order, seriales ingresados, id del servicio asociado a la work order, id del 
     * 			cliente, codigo del cliente
     * @throws BusinessException
     * @author aharker
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void verifyDesconectionSerials(
			VerifyDesconectionSerialsDTO request) throws BusinessException {
		try {

			UtilsBusiness.assertNotNull(request.getPrincipalSerial(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " el serial principal es requerido ");
			UtilsBusiness.assertNotNull(request.getCustomerCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+ " el codigo del cliente es requerido ");
			UtilsBusiness.assertNotNull(request.getLinkedSerial(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+" el serial vinculado es requerido ");
			UtilsBusiness.assertNotNull(request.getWorkOrderId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+" el id de la work order es requerido ");
			UtilsBusiness.assertNotNull(request.getWorkOrderServiceId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()+" el id del servicio de la work order es requerido ");
			
			SystemParameter invokeInternalInventory = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), request.getCountryId());
			if (invokeInternalInventory == null) {
				log.fatal("No se ha encontrado el parametro del sistema INVOKE_HSP_INVENTORY_SERVICE");
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
			}
			
			if(invokeInternalInventory.getValue().equals( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
				return;
			}
			
			WorkOrder wo=workOrderDAO.getWorkOrderByID(request.getWorkOrderId());
			
			if(wo==null){
				throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
						.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
			}
			
			if(!wo.getWoTypeByWoTypeId().getWoTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum
					.WORKORDER_TYPE_DISCONNECTION.getCodeEntity())){
				return;
			}
			
			WarehouseElement warehouseElementSerialized=warehouseElementDAO.getElementBySerialAndCustomerCode(request.getPrincipalSerial(), request.getCustomerCode());
			
			Serialized serialized;
			
			if(warehouseElementSerialized==null 
					|| warehouseElementSerialized.getSerialized()==null){
				Object[] params = null;
				params = new Object[2];
				params[0] = request.getPrincipalSerial();
				params[1] = request.getCustomerCode();
				
				List<String> paramsString = new ArrayList<String>();
				paramsString.add(request.getPrincipalSerial());
				paramsString.add(request.getCustomerCode());

				String exceptionCode = ErrorBusinessMessages.STOCK_IN484.getCode();
				String exceptionMsj = ErrorBusinessMessages.STOCK_IN484.getMessage(params);
				
				throw new BusinessException(exceptionCode,exceptionMsj,paramsString) ;
			}
			
			serialized=warehouseElementSerialized.getSerialized();
			
			if(serialized.getSerialized()==null || (request!=null && request.getLinkedSerial()!=null && serialized.getSerialized() != null
					&& !serialized.getSerialized().getSerialCode().equalsIgnoreCase(request.getLinkedSerial().trim()))){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN477.getCode(), ErrorBusinessMessages
						.STOCK_IN477.getMessage());
			}
			
			if(wo!=null
					&& wo.getWoTypeByWoTypeId() != null
					&& wo.getWoTypeByWoTypeId().getWoTypeCode()!=null){
				if(wo.getWoTypeByWoTypeId().getWoTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum
						.WORKORDER_TYPE_DISCONNECTION.getCodeEntity())){
					WorkOrderService workOrderService = null;
					for(WorkOrderService wos: wo.getWorkOrderServices()){
						if(wos.getId().equals(request.getWorkOrderServiceId()) ){
							workOrderService = wos;
							break;
						}
					}
					if(workOrderService != null 
							&& workOrderService.getService()!=null
							&& workOrderService.getService().getAllowRetrieveElement()!=null
							&& workOrderService.getService().getAllowRetrieveElement().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
						if(workOrderService.getSerialNumber()!=null || workOrderService.getLinkedSerialNumber()!=null){
							if(workOrderService.getSerialNumber()!=null){
								if (!workOrderService.getSerialNumber().equalsIgnoreCase(request.getPrincipalSerial()) && !workOrderService
										.getSerialNumber().equalsIgnoreCase(request.getLinkedSerial())){
									throw new BusinessException(ErrorBusinessMessages.CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL.getCode(), ErrorBusinessMessages
											.CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL.getMessage());
								}
							}
							if(workOrderService.getLinkedSerialNumber()!=null){
								if (!workOrderService.getLinkedSerialNumber().equalsIgnoreCase(request.getPrincipalSerial()) && !workOrderService
										.getLinkedSerialNumber().equalsIgnoreCase(request.getLinkedSerial())){
									throw new BusinessException(ErrorBusinessMessages.CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL.getCode(), ErrorBusinessMessages
											.CORE_ATTENTION_DESCONECTION_WO_NO_MATCH_SERIAL.getMessage());
								}
							}
						}
						else{
							throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_SERVICE_HAS_NOT_SERIALS.getCode(), ErrorBusinessMessages
									.CORE_THE_WORK_ORDER_SERVICE_HAS_NOT_SERIALS.getMessage());
						}
					}
					else{
						throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
								.CORE_THE_WORK_ORDER_SERVICE_DOES_NOT_EXIST.getMessage());
					}
				}
			}
			else{
				throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages
						.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
			}
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación verifyDesconectionSerials/WorkOrderBusinessBean ==");
			throw this.manageException(ex);
		}
	}

}
