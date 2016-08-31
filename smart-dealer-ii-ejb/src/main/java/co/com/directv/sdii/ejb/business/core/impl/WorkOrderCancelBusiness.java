package co.com.directv.sdii.ejb.business.core.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WorkOrderCancelBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCancelDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class WorkOrderCancelBusiness
 */
@Stateless
public class WorkOrderCancelBusiness extends BusinessBase implements WorkOrderCancelBusinessLocal {

	private final static Logger log = UtilsBusiness.getLog4J(WorkOrderCancelBusiness.class);
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB
	private WorkOrderCancelDAOLocal workOrderCancelDAOLocal;
	
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB
	private ConfigParametrosBusinessLocal configParametrosBusiness;

	
    /**
     * Default constructor. 
     */
    public WorkOrderCancelBusiness() {
    }

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public WorkOrderCanceledResponse getCanceledWorkOrders(
			WorkOrderCanceledFilterDTO filter, RequestCollectionInfo requestInfo)
			throws BusinessException {
		log.debug("== Inicia getCanceledWorkOrders/WorkOrderCancelBusiness ==");
		try {
			UtilsBusiness.assertNotNull(filter, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(filter.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(filter.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			Date datenow=UtilsBusiness.getDateLastChangeOfUser(filter.getCountryId(), daoUser, systemParameterDAO);
			
			SystemParameter systemParameter = configParametrosBusiness.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY.getCodeEntity(), filter.getCountryId());
			
			/*SystemParameter systemParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY.getCodeEntity()
					, filter.getCountryId());*/
			String valorParametro = systemParameter.getValue();
			Integer days= Integer.parseInt(valorParametro);
			
			User user=daoUser.getUserById(filter.getUserId());
			if(user!=null){
				if(user.getDealer()!=null){
					if(user.getDealer().getDealer()!=null){
						filter.setBranchId(user.getDealer().getId());
						filter.setPrincipalDealerId(user.getDealer().getDealer().getId());
					}
					else{
						filter.setPrincipalDealerId(user.getDealer().getId());
					}
				}
			}
			else{
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			return workOrderCancelDAOLocal.getCanceledWorkOrders(filter, datenow, days, requestInfo);
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaci�n getCanceledWorkOrders/WorkOrderCancelBusiness ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCanceledWorkOrders/WorkOrderCancelBusiness ==");
        } 
		
	}

	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @return numero de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public Long getCanceledWorkOrdersCount(Long countryId, Long userId) throws BusinessException {
		
		log.debug("== Inicia getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
		try {
			Date datenow=UtilsBusiness.getDateLastChangeOfUser(countryId, daoUser, systemParameterDAO);
			SystemParameter systemParameter = configParametrosBusiness.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY.getCodeEntity(), countryId);
			String valorParametro = systemParameter.getValue();
			Integer days= Integer.parseInt(valorParametro);
			User user=daoUser.getUserById(userId);
			Long dealerId = null;
			if(user!=null){
				if(user.getDealer()!=null){
					dealerId=user.getDealer().getId();
				}
			}
			else{
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			return workOrderCancelDAOLocal.getCanceledWorkOrdersCount(countryId, days, datenow, dealerId);
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaci�n getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
        } 
		
	}

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public FileResponseDTO getCanceledWorkOrdersReport(
			WorkOrderCanceledFilterDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			Date datenow=UtilsBusiness.getDateLastChangeOfUser(filter.getCountryId(), daoUser, systemParameterDAO);
			SystemParameter systemParameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.DAY_PAST_CANCELED_WORK_ORDER_FOR_QUERY.getCodeEntity()
					, filter.getCountryId());
			String valorParametro = systemParameter.getValue();
			Integer days= Integer.parseInt(valorParametro);
			
			WorkOrderCanceledResponse workOrderCanceledResponse = workOrderCancelDAOLocal.getCanceledWorkOrders(filter, datenow, days, null);

			String fileName = "Work_Orders_canceled_" + UtilsBusiness.formatYYYYMMDD(datenow) + "-" + datenow.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( workOrderCanceledResponse != null && workOrderCanceledResponse.getListResponse()!=null && !workOrderCanceledResponse.getListResponse().isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(workOrderCanceledResponse.getListResponse(), null, null, CodesBusinessEntityEnum.WORKORDER_CANCELED_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if( baos == null ){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource( baos.toByteArray() , "application/vnd.ms-excel" );
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName( fileName );
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		}
	}

	@Override
	public void manageCanceledWorkOrder(Long workOrderId, Long userId) throws BusinessException {
		log.debug("== Inicia getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
		try {
			workOrderCancelDAOLocal.manageCanceledWorkOrder(workOrderId, userId);
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaci�n getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCanceledWorkOrdersCount/WorkOrderCancelBusiness ==");
        }
		
	}
    
    
}
