package co.com.directv.sdii.ejb.business.report.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Map;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.util.ByteArrayDataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.ExcelGenerator;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.PdfUtils;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayReportsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal;
import co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReferenceBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ReportsStockBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.TransferReasonBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoDetailDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayForPdfDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.DealerMediaContact;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.WoPdfAnnex;
import co.com.directv.sdii.model.pojo.WoPdfElementTypeNotSerialized;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.model.vo.CustomerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;
import co.com.directv.sdii.model.vo.WoPdfElementTypeNotSerializedVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderWorkOrderMarkVO;
import co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoPdfElementTypeNotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.VisitsReportItem;
import co.com.directv.sdii.reports.VisitsReportItemExcel;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.commands.ICommandFactory;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;
import co.com.directv.sdii.reports.dto.WarehouseDTO;

/**
 * 
 * Implementación de negocio para generar reportes 
 * 
 * Fecha de Creación: 14/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ReportGeneratorBusinessBeanLocal",mappedName="ejb/ReportGeneratorBusinessBeanLocal")
@Local({ReportGeneratorBusinessBeanLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportGeneratorBusinessBean extends BusinessBase implements ReportGeneratorBusinessBeanLocal {
	
	private static final String REPORT_TEMPLATES_SUB_DIR = "templates/";
	
	private final static Logger log = UtilsBusiness.getLog4J(ReportGeneratorBusinessBean.class);
	
	@EJB
	private ICommandFactory commandFactory;
	
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB(name="TrayWorkOrderManagmentBusinessLocal",beanInterface=TrayWorkOrderManagmentBusinessLocal.class)
	private TrayWorkOrderManagmentBusinessLocal trayWorkOrderManagmentBusiness;
	
	@EJB(name="CustomerMediaContactDAOLocal", beanInterface=CustomerMediaContactDAOLocal.class)
	private CustomerMediaContactDAOLocal customerMediaContactDAO;
	
	@EJB(name="DealersMediaContactDAOLocal", beanInterface=DealersMediaContactDAOLocal.class)
	private DealersMediaContactDAOLocal dealersMediaContactDAO;
	
	@EJB(name="ContactBusinessBeanLocal", beanInterface=ContactBusinessBeanLocal.class)
	private ContactBusinessBeanLocal contactBusiness;
	
	@EJB(name="WoPdfAnnexDAOLocal", beanInterface=WoPdfAnnexDAOLocal.class)
	private WoPdfAnnexDAOLocal woPdfAnnexDAOLocal;

	@EJB(name="WoPdfElementTypeNotSerializedDAOLocal", beanInterface=WoPdfElementTypeNotSerializedDAOLocal.class)
	private WoPdfElementTypeNotSerializedDAOLocal woPdfElementTypeNotSerializedDAOLocal;
	
	@EJB(name="TrayReportsBusinessLocal", beanInterface=TrayReportsBusinessLocal.class)
	private TrayReportsBusinessLocal trayReportsBusiness;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
	
	@EJB(name="ReferenceBusinessBeanLocal", beanInterface=ReferenceBusinessBeanLocal.class)
    private ReferenceBusinessBeanLocal referenceBusiness;
	
	@EJB(name="TransferReasonBusinessBeanLocal", beanInterface=TransferReasonBusinessBeanLocal.class)
	private TransferReasonBusinessBeanLocal transferReasonBusinessBean;
	
	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="ExcelGeneratorLocal", beanInterface=ExcelGeneratorLocal.class)
	private ExcelGeneratorLocal excelGeneratorLocal;
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal", beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	//CC053 - HSP Reportes de inventarios.
	@EJB(name="ReportsStockBusinessLocal", beanInterface=ReportsStockBusinessLocal.class)
	private ReportsStockBusinessLocal reportsStockBusinessLocal;

	//CC053
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;

	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAOBean;

	@EJB(name="CrewsDAOLocal", beanInterface=CrewsDAOLocal.class)
	private CrewsDAOLocal crewsDAO;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;

	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReport(WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		try{
			
			Long countryId = filter.getCountryId();
			Long idUsuario = filter.getUserId();
			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReport(filter);
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,idUsuario);
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean ==");
		try{

			Long countryId = filter.getCountryId();
			Long idUsuario = filter.getUserId();
			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReportAttentionFinalization(filter);
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,idUsuario);
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReportAttentionFinalization(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(List<String> woCodes, Long countryId)throws BusinessException{
		log.debug("== Inicia getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean ==");
		try{

			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReportAttentionFinalization(woCodes,countryId); 
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,null);	
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportAttentionFinalization/ReportGeneratorBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReport(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportForAllocator(WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean ==");
		try{

			Long countryId = filter.getCountryId();
			Long idUsuario = filter.getUserId();
			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReportForAllocator(filter);
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,idUsuario);
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);
		
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReportForAllocator(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReportForAllocator(List<String> woCodes, Long countryId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean ==");
		try{

			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReportForAllocator(woCodes,countryId); 
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,null);
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportForAllocator/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrdersForReport(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FileResponseDTO getWorkOrdersForReport(List<String> woCodes, Long countryId, Long userId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		try{
			
			WorkOrderResponse daoResponse = trayReportsBusiness.getWorkOrdersForReport(woCodes,countryId, userId); 
			List<ReportWorkOrderDTO> response = trayReportsBusiness.getReportWorkOrderDTOWorkOrdersForReport(daoResponse,countryId,userId);
			return UtilsBusiness.getFileResponseDTOByReportWorkOrderDTOS(response);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReport/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getCustomerMediaContacts(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO getCustomerMediaContacts(String customerCode) throws BusinessException {
		log.debug("== Inicia getCustomerMediaContacts/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			if( customerCode == null || customerCode.equals("") ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			List<CustomerMediaContactVO> customerMediaContacts = UtilsBusiness.convertList( customerMediaContactDAO.getCustomerMediaContactByCustomerCode( customerCode ) , CustomerMediaContactVO.class);
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.CONTACTS_DETAILS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( customerMediaContacts != null && !customerMediaContacts.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(customerMediaContacts, null, null, CodesBusinessEntityEnum.CUSTOMER_MEDIA_CONTACTS_JASPER_FILE.getCodeEntity());
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
			log.debug("== Error al tratar de ejecutar la operación getCustomerMediaContacts/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerMediaContacts/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrderServices(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO getWorkOrderServices(Long woId) throws BusinessException {
		log.debug("== Inicia getWorkOrderServices/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			if( woId == null || woId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			List<WorkOrderServiceVO> servicesByWorkOrder = this.trayWorkOrderManagmentBusiness.getWorkOrderServices(woId , null).getWorkOrderServicesVO();
			
			Date now = new Date();
			String fileName = "Work_Orders_Services" + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( servicesByWorkOrder != null && !servicesByWorkOrder.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(servicesByWorkOrder, null, null, CodesBusinessEntityEnum.WORKORDER_SERVICE_JASPER_FILE.getCodeEntity());
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
			log.debug("== Error al tratar de ejecutar la operación getWorkOrderServices/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderServices/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getShippingOrderElements(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO getShippingOrderElements(Long soId) throws BusinessException {
		log.debug("== Inicia getShippingOrderElements/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			if( soId == null || soId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			List<ShippingOrderElementVO> soElements = this.trayWorkOrderManagmentBusiness.getShippingOrderElements(soId);
			
			Date now = new Date();
			String fileName = "Shipping_Order_Elements_" + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( soElements != null && !soElements.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(soElements, null, null, CodesBusinessEntityEnum.SO_ELEMEMTS_JASPER_FILE.getCodeEntity());
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
			log.debug("== Error al tratar de ejecutar la operación getShippingOrderElements/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getShippingOrderElements/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getDealerMediaContacts(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO getDealerMediaContacts(Long dealerCode) throws BusinessException {
		log.debug("== Inicia getDealerMediaContacts/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			if( dealerCode == null ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			List<DealerMediaContact> pojoList = dealersMediaContactDAO.getDealersMediaContactByDealerCode( dealerCode );
			List<DealerMediaContactVO> dealerMediaContacts = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				dealerMediaContacts = new ArrayList<DealerMediaContactVO>();
				for(DealerMediaContact pojo : pojoList){
					DealerMediaContactVO vo = UtilsBusiness.copyObject(DealerMediaContactVO.class, pojo);
					vo.setDealerId( pojo.getDealer().getId() );
					vo.setDealer( null );
					dealerMediaContacts.add( vo );
				}				
			}
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.MEDIA_CONTACT_SELLER.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( dealerMediaContacts != null && !dealerMediaContacts.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(dealerMediaContacts, null, null, CodesBusinessEntityEnum.DEALER_MEDIA_CONTACTS_JASPER_FILE.getCodeEntity());
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
			log.debug("== Error al tratar de ejecutar la operación getDealerMediaContacts/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getDealerMediaContacts/ReportGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWorkOrderContacts(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO getWorkOrderContacts(String woCode) throws BusinessException {
		log.debug("== Inicia getWorkOrderContacts/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			if( woCode == null || woCode.equals("") ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			ContactDTO contactDto = new ContactDTO();
			contactDto.setWoCode( woCode );
			List<ContactVO> contacts = contactBusiness.getContactsWorkOrderByDealer( contactDto );
			
			Date now = new Date();
			String fileName = "Work_Order_Contacts" + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( contacts != null && !contacts.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(contacts, null, null, CodesBusinessEntityEnum.WO_CONTACTS_JASPER_FILE.getCodeEntity());
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
			log.debug("== Error al tratar de ejecutar la operación getWorkOrderContacts/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderContacts/ReportGeneratorBusinessBean ==");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#generateReport(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO generateReport(String cmd, String args, String fileName, String reportExtension) throws BusinessException {
		log.debug("== Inicia generateReport/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			String fileMimeType;
			Date now = new Date();
			fileName += UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + "." + reportExtension;
			if(fileName == null || fileName.equals("")) {
				fileName="reporte";
			}
			
			ICommand command = commandFactory.getCommand(cmd);
			
			if( reportExtension == null || reportExtension.equals("") ){
				reportExtension = "xls";
			}
			List<String> fieldList = null;//command.getFieldList();	
			ByteArrayOutputStream baos = null;
			
			if(cmd.equals("getWarehouseElementsByWarehouse") && reportExtension.contains("xls")					
				||
				cmd.equals("getQuantityWarehouseElementsSummariesByFilters")	
			){  
				fileName = fileName.replaceFirst(".xls", ".csv") ;
				String directoryName = UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime();
				File directory = new File(ExcelGenerator.getReportsPathTemp()+"/"+directoryName);
				File directoryTemp = new File(ExcelGenerator.getReportsPathTemp());
				if(!directoryTemp.exists()){
					directoryTemp.mkdir();
				}
				directory.mkdir();
				int page=1;
				int pageSize = Integer.parseInt(CodesBusinessEntityEnum.CONSTANT_REPORT.getCodeEntity());
				boolean needOtherCall = true;
				String originalArgs = args;
				// Crear el libro de trabajo
				log.info("va a generar el excel");
				 
				if(cmd.equals("getWarehouseElementsByWarehouse")){
					//NUEVA FORMA DE ESCRIBIR EL FICHERO.
					boolean bCabecera = true;
					while(needOtherCall){
						// Crea la primera fila con los nombres de las columnas
						fieldList = makePoiHeaders(cmd);
						args = originalArgs + "pageIndex="+page+";pageSize="+pageSize+";";
						log.info("va a realizar la consulta del reporte");
						List<Object> dataList = command.execute(args);
						if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
							needOtherCall = false;
						}
						log.info("realizo la consulta del reporte");
						log.info("va a generar las filas");
						excelGeneratorLocal.flushToExcelFile(fieldList,dataList,directoryName+"/"+fileName, (page-1),bCabecera);
						bCabecera = false; 
						++page;
					}
				}else{
					while(needOtherCall){
						// Crear las filas en la hoja de trabajo
						
						// Crea la primera fila con los nombres de las columnas
						if (cmd.equals("getQuantityWarehouseElementsSummariesByFilters")){
							fieldList = makePoiHeadersWarehouseElementsSummariesByFilters(cmd);
						}else {
							fieldList = makePoiHeaders(cmd);
						}
							
						args = originalArgs + "pageIndex="+page+";pageSize="+pageSize+";";
						log.info("va a realizar la consulta del reporte");
						List<Object> dataList = command.execute(args);
						if(dataList== null || dataList.isEmpty() || dataList.size()<pageSize){
							needOtherCall = false;
						}
						log.info("realizo la consulta del reporte");
						log.info("va a generar las filas");

						List<String> rows;
						if (cmd.equals("getQuantityWarehouseElementsSummariesByFilters")){
							rows = makePoiParametersWarehouseElementsSummariesByFilters(dataList);
						}else {
							rows = makePoiParameters(dataList);
						}					
						
						
						log.info("se generan "+rows.size()+" filas");
						log.info("genero las filas");
						excelGeneratorLocal.populateExcelFile(fieldList,rows, directoryName+"/"+fileName, (page-1));
						rows.clear();
						dataList.clear();
						++page;
					}
				}
				
				log.info("genero el excel");
				log.info("va a generar el zip");
				String fileNameZip = fileName.replaceFirst(".csv", ".zip") ;
				createZip(ExcelGenerator.getReportsPathTemp()+fileNameZip, ExcelGenerator.getReportsPathTemp()+directoryName+"/");
				log.info("genero el zip");
				baos = new ByteArrayOutputStream();
				FileInputStream fileInput = new FileInputStream(ExcelGenerator.getReportsPathTemp()+"/"+fileNameZip);
				BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
				byte [] array = new byte[1000];
				int leidos = bufferedInput.read(array);
				log.info("va a escribir el archivo de excel en zip hacia .net");
				while (leidos > 0){
					baos.write(array,0,leidos);
					leidos=bufferedInput.read(array);
				}
				bufferedInput.close();
				fileInput.close();
				fileMimeType = "application/zip";
				log.info("va a eliminar los archivos y carpetas temporales");
				File fileZip = new File(ExcelGenerator.getReportsPathTemp()+"/"+fileNameZip);
				fileZip.delete();
				File directoryFileCvs = new File(ExcelGenerator.getReportsPathTemp()+directoryName+"/"+fileName);
				directoryFileCvs.delete();
				File DirectoryFileZip = new File(ExcelGenerator.getReportsPathTemp()+directoryName+"/");
				DirectoryFileZip.delete();
				log.info("elimino los archivos y carpetas temporales");
				fileName = fileName.replaceFirst(".csv", ".zip") ;
				
			}else{
				log.info("va a realizar la consulta del reporte");
				List<Object> dataList = command.execute(args);
				log.info("realizo la consulta del reporte");
				if( reportExtension.contains("xls") ){
					String[] sheetName = {"Data"};
					baos = excelGenerator.createExcelStreamWithJasper(dataList, fieldList, sheetName ,cmd);
					//excelGenerator.createExcelFileWithJasper(dataList, fieldList, sheetName, cmd);
					fileMimeType = "application/vnd.ms-excel";
				} else {
					baos = excelGenerator.createPdfStreamWithJasper(dataList, cmd);
					fileMimeType = "application/pdf";
				}
			}

			if( baos == null ){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource( baos.toByteArray() , fileMimeType );
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);

			response.setFileName( fileName );
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateReport/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateReport/ReportGeneratorBusinessBean ==");
		}
	}

	private void flushToFile(List<Object> dataList) {
		// TODO Auto-generated method stub
		
	}

	public void createZip(String filename,String carpeta){
		 
		  try {
		 
		  //Nuestro InputStream
		 
		  BufferedInputStream origin = null;
		 
		  //Declaramos el FileOutputStream para guardar el archivo
		 
		  FileOutputStream dest = new FileOutputStream(filename);
		 
		  //Indicamos que será un archivo ZIP
		 
		  ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		 
		  //Indicamos que el archivo tendrá compresión
		 
		  out.setMethod(ZipOutputStream.DEFLATED);
		 
		  //Indicamos que el archivo NO tendrá compresión
		 
		  //out.setMethod(ZipOutputStream.STORED);
		 
		  byte data[] = new byte[1000];
		 
		  // Creamos la referencia de la carpeta a leer
		 
		  File f = new File(carpeta);
		 
		  // Guarda los nombres de los archivos de la carpeta a leer
		 
		  String files[] = f.list();
		 
		 // Muestra el listado de archivos de la carpeta a leer
		 
		  for (int i=0; i<files.length; i++) {
		 
		  System.out.println("Agregando al ZIP: "+files[i]);
		 
		  //Creamos el objeto a guardar para cada uno de los elementos del listado
		 
		  FileInputStream fi = new FileInputStream(carpeta+files[i]);
		 
		  origin = new BufferedInputStream(fi, 1000);
		 
		  ZipEntry entry = new ZipEntry(files[i]);
		 
		  //Guardamos el objeto en el ZIP
		 
		  out.putNextEntry(entry);
		 
		  int count;
		 
		  //Escribimos el objeto en el ZIP
		 
		  while((count = origin.read(data, 0,1000)) != -1) {
		 
		  out.write(data, 0, count);
		 
		  }
		 
		  origin.close();
		 
		  }
		 
		  out.close();
		 
		  } catch(Exception e) {
		 
		  e.printStackTrace();
		 
		  }
		 
	 }
	//Modificado para Requerimiento: CC057
	 private List<String> makePoiHeaders(String comand) {
		List<String> returnValue=new ArrayList<String>();
		if(comand!=null){
			if(comand.equals("getWarehouseElementsByWarehouse")){
				returnValue.add(ApplicationTextEnum.COMPANY.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.WAREHOUSE_TYPE.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.CREW.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.LOCATION.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.MODEL_CODE_NAME.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.TYPE_ELEMENT_CODE.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.SERIAL.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.RID.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.QUANTITY.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.AGE.getApplicationTextValue());
			}
		}
		return returnValue;
	}
	 
		/**
		 * Req.ACM-F-05_HSP_ReportesSC_CC053
		 * 
		 * Metodo: Genera la lista de campos titulo del reporte
		 * Pantalla: Inventarios -> Consultas -> Consulta movimiento compañía por rango de fechas. (Kardex)
		 * (Análogo al reporte asincronico programado por el ususario: 
		 * Reporte movimientos Consolidado (Kardex). / WAREHOUSE_MOVEMENTS_KARDEX / CODE = 'WMK' / )
		 * 
		 * @param comand lista de parametros que llegan desde la invocacion al servicio 
		 * ReportGeneratorWS.java/generateReport(...)
		 * 
		 * @return Lista de campos titulo para el reporte
		 * 
		 */
	 private List<String> makePoiHeadersWarehouseElementsSummariesByFilters(String comand) {
		List<String> returnValue=new ArrayList<String>();
		if(comand!=null){
			if(comand.equals("getQuantityWarehouseElementsSummariesByFilters")){
				returnValue.add(ApplicationTextEnum.COMPANY.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.BRANCH.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.CREW.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.WAREHOUSE_LOCATION.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.INITIAL_QUANTITY.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.ENTRIES.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.DEPARTURES.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.CURRENT_BALANCE.getApplicationTextValue());				
			}
		}
		return returnValue;
	}	 
	 
	 
	 
	//Modificado para Requerimiento: CC057
	private List<String> makePoiParameters(List<Object> dataList) {
		List<String> returnValue=new ArrayList<String>();
		if(dataList!=null && !dataList.isEmpty()){
			Object item = dataList.get(0);
			if(item instanceof QuantityWarehouseElementsDTO){
				for(Object item2 : dataList){
					String rowValues="";
					QuantityWarehouseElementsDTO itemQuantityWarehouseElementsDTO = (QuantityWarehouseElementsDTO)item2;
					rowValues += (itemQuantityWarehouseElementsDTO.getDealerNameCompany()!=null?itemQuantityWarehouseElementsDTO.getDealerNameCompany():" ")+"|"+(itemQuantityWarehouseElementsDTO.getDealerNameBranch()!=null?itemQuantityWarehouseElementsDTO.getDealerNameBranch():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getWhTypeName()!=null?itemQuantityWarehouseElementsDTO.getWhTypeName():" ")+"|"+(itemQuantityWarehouseElementsDTO.getIsResponsibleOut()!=null?itemQuantityWarehouseElementsDTO.getIsResponsibleOut():" ")+"|"+(itemQuantityWarehouseElementsDTO.getWhName()!=null?itemQuantityWarehouseElementsDTO.getWhName():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getModelCode()!=null?itemQuantityWarehouseElementsDTO.getModelCode():" ")+"/"+(itemQuantityWarehouseElementsDTO.getModelName()!=null?itemQuantityWarehouseElementsDTO.getModelName():" ")+"|"+(itemQuantityWarehouseElementsDTO.getTypeElementCode()!=null?itemQuantityWarehouseElementsDTO.getTypeElementCode():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getTypeElementName()!=null?itemQuantityWarehouseElementsDTO.getTypeElementName():" ")+"|"+(itemQuantityWarehouseElementsDTO.getSerialCode()!=null?itemQuantityWarehouseElementsDTO.getSerialCode():" ")+"|"+(itemQuantityWarehouseElementsDTO.getRid()!=null?itemQuantityWarehouseElementsDTO.getRid():" ")+"|"+(itemQuantityWarehouseElementsDTO.getSerialCodeLinked()!=null?itemQuantityWarehouseElementsDTO.getSerialCodeLinked():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getActualQuantity()!=null?itemQuantityWarehouseElementsDTO.getActualQuantity():" ")+"|"+(itemQuantityWarehouseElementsDTO.getAge()!=null?itemQuantityWarehouseElementsDTO.getAge():" ");
					returnValue.add(rowValues);
				}
			}
		}
		return returnValue;
	}


	//Modificado para Requerimiento: CC057
	private List<String> makePoiParametersWarehouseElementsSummariesByFilters(List<Object> dataList) {
		List<String> returnValue=new ArrayList<String>();
		if(dataList!=null && !dataList.isEmpty()){
			Object item = dataList.get(0);
			if(item instanceof QuantityWarehouseElementsDTO){
				for(Object item2 : dataList){
					String rowValues="";
					QuantityWarehouseElementsDTO itemQuantityWarehouseElementsDTO = (QuantityWarehouseElementsDTO)item2;
/*
 * 				returnValue.add(ApplicationTextEnum.COMPANY.getApplicationTextValue()); ok
				returnValue.add(ApplicationTextEnum.BRANCH.getApplicationTextValue()); ok
				returnValue.add(ApplicationTextEnum.CREW.getApplicationTextValue());?
				returnValue.add(ApplicationTextEnum.WAREHOUSE_LOCATION.getApplicationTextValue());?
				returnValue.add(ApplicationTextEnum.MODEL.getApplicationTextValue()); ok
				returnValue.add(ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()); ok
				returnValue.add(ApplicationTextEnum.INITIAL_QUANTITY.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.ENTRIES.getApplicationTextValue());?????????
				returnValue.add(ApplicationTextEnum.DEPARTURES.getApplicationTextValue());
				returnValue.add(ApplicationTextEnum.CURRENT_BALANCE.getApplicationTextValue());				
			}					
 */
					rowValues += 
						 (itemQuantityWarehouseElementsDTO.getDealerNameCompany()!=null?itemQuantityWarehouseElementsDTO.getDealerNameCompany():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getDealerNameBranch()!=null?itemQuantityWarehouseElementsDTO.getDealerNameBranch():" ")					
					+"|"+(itemQuantityWarehouseElementsDTO.getCrewId()!=null?itemQuantityWarehouseElementsDTO.getIsResponsibleOut():" ")					
					+"|"+(itemQuantityWarehouseElementsDTO.getWhName()!=null?itemQuantityWarehouseElementsDTO.getWhName():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getModelCode()!=null?itemQuantityWarehouseElementsDTO.getModelCode():" ")+" / "+(itemQuantityWarehouseElementsDTO.getModelName()!=null?itemQuantityWarehouseElementsDTO.getModelName():" ")										
					+"|"+(itemQuantityWarehouseElementsDTO.getTypeElementName()!=null?itemQuantityWarehouseElementsDTO.getTypeElementCode():" ")+" / "+(itemQuantityWarehouseElementsDTO.getTypeElementName()!=null?itemQuantityWarehouseElementsDTO.getTypeElementName():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getInitialQuantity()!=null?itemQuantityWarehouseElementsDTO.getInitialQuantity():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getInQuantity()!=null?itemQuantityWarehouseElementsDTO.getInQuantity():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getOutQuantity()!=null?itemQuantityWarehouseElementsDTO.getOutQuantity():" ")
					+"|"+(itemQuantityWarehouseElementsDTO.getActualQuantity()!=null?itemQuantityWarehouseElementsDTO.getActualQuantity():" ");

					
					returnValue.add(rowValues);
				}
			}
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#generateWorkOrderPDF(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileResponseDTO generateWorkOrderPDF(List<String> workOrderCodes,Long countryId) throws BusinessException {
		log.debug("== Inicia generateWorkOrderPDF/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			ByteArrayOutputStream baos = null;
			String builtPdfAnnexQuery = null;
			List<WoPdfAnnex> woPdfAnnex = null;
			String fileMimeType = "application/pdf";
			List<String> completeReportNames = new ArrayList<String>();
			
			//CC055 - elemTypeNotSer dinamicos para PDF de WO.
			//Se consultan los tipos de elementos para el pais.
			List<WoPdfElementTypeNotSerialized> elementTypeNotSerializedList = woPdfElementTypeNotSerializedDAOLocal.getWoPdfElementTypeNotSerializedsByCountryId(countryId);
			List<WoPdfElementTypeNotSerializedVO> woPdfElemVOList = UtilsBusiness.convertList(elementTypeNotSerializedList, WoPdfElementTypeNotSerializedVO.class);
			
			//Se consultan los objetos de negocio que mapean la informacion de las WO para generar los pdf
			List<WorkOrderTrayForPdfDTO> workOrderTrayList = trayReportsBusiness.getWorkorderDetailForPdf(workOrderCodes,countryId);
			SystemParameter serviceCustomerNumberParam = this.systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_CUSTOMER_SERVICE_NUMBER.getCodeEntity(), countryId);
			
			//Leer el disclamer de la base de datos, si no existe disclamer se muestra vacio
			SystemParameter systemParamDisclamerWorkOrder = this.systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_REPORT_WO_DISCLAMER.getCodeEntity(), countryId);
			
			//Leer del parametro del sistema de si debe mostrar si es o no un cliente migrado
			SystemParameter systemParamShowCustomerIsMigrated = this.systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_REPORT_WO_SHOW_IS_MIGRATED.getCodeEntity(), countryId);
			boolean showCustomerIsMigrated = false;
			if(systemParamShowCustomerIsMigrated.getValue().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
				showCustomerIsMigrated = true;
			}
			
			SystemParameter systemParameterGetContractSystemExternal = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_GET_CONTRACT_SYSTEM_EXTERNAL.getCodeEntity(), countryId);
			boolean isGetContractSystemExternal = systemParameterGetContractSystemExternal==null ? false : systemParameterGetContractSystemExternal.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ? true : false;
			
			//boolean hasContract = false;
			List<WorkOrderWorkOrderMarkVO> workOrderWorkOrderMarkVOList = null;
			if(isGetContractSystemExternal){				
				Long workOrderMarkId = CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT.getIdEntity(WorkOrderMark.class.getName());
				workOrderWorkOrderMarkVOList = workOrderMarkBusiness.getWorkOrderWorkOrderMarkIsActiveByWoCodesAndCodeWorkOrderMark(workOrderCodes, workOrderMarkId);
			}
			
			
			String serviceCustomerNumber = null;
			if( serviceCustomerNumberParam != null && serviceCustomerNumberParam.getValue() != null ){
				serviceCustomerNumber = serviceCustomerNumberParam.getValue();
			}
			if( workOrderTrayList != null && !workOrderTrayList.isEmpty() ){
				String woJasperName = UtilsBusiness.generateWoName(countryId, woPdfAnnexDAOLocal);
				
				String reportsPath = getReportsPath();
				
				for( WorkOrderTrayForPdfDTO dto : workOrderTrayList ){
					dto.setDummyTelephone(serviceCustomerNumber);
					boolean hasContract = false;					
					//Set del disclamer de la WO
					
					if (systemParamDisclamerWorkOrder.getValue().trim().length()==0)
						dto.getCustomerDTO().setDisclamer(null);
					else
						dto.getCustomerDTO().setDisclamer(systemParamDisclamerWorkOrder.getValue());
					
					//Set de la propiedad para determinar si se muestra o no 
					//si el cliente es o no migrado
					dto.getCustomerDTO().setShowCustomerIsMigrated(showCustomerIsMigrated);
					
					//CC055 - elemTypeNotSer dinamicos para PDF de WO.
					dto.setWoPdfElementTypeNotSerializedVOs(woPdfElemVOList);
					
					List<String> reportNames = new ArrayList<String>();
					List<WorkOrderTrayForPdfDTO> tempList = new ArrayList<WorkOrderTrayForPdfDTO>();
					tempList.add(dto);
					JasperPrint jasperPrint;				
					StringBuffer reportName = null;
					//jasperPrint = JasperFillManager.fillReport(reportsPath+REPORT_TEMPLATES_SUB_DIR+woJasperName, null, new JRBeanCollectionDataSource(tempList));
					jasperPrint = JasperFillManager.fillReport(reportsPath+REPORT_TEMPLATES_SUB_DIR+woJasperName, UtilsBusiness.getReportParams(), new JRBeanCollectionDataSource(tempList));
		    		reportName = UtilsBusiness.generateSingleWOReportName(reportsPath, null);
		    		//Crea el PDF en el DD
		    		JasperExportManager.exportReportToPdfFile(jasperPrint,reportName.toString());
		    		reportNames.add(reportName.toString());
		    		
		    		//Busca el los anexos necesarios para un WO especifica dependiendo del pais
		    		builtPdfAnnexQuery = UtilsBusiness.buildQuery(UtilsBusiness.buildVariablesForWoOrAnnex(countryId, CodesBusinessEntityEnum.PDF_ANNEX_CODE.getCodeEntity()), null, null);
		    	
		    		woPdfAnnex = woPdfAnnexDAOLocal.searchWoPdfAnnexByCriteria(builtPdfAnnexQuery);
		    		
		    		/* (non-Javadoc)
		    		 * Obtiene los anexos necesarios de un pais para luego encadenar estis a la WO
		    		 * Si dealer_Type de wo_pdf_annex tiene el valor de ALL, entonces se asignan
		    		 * a ese pais todos los anexos para cualquier dealertype, servicetype
		    		 * ciudad y estado, de no tener la palabra ALL entonces se chequea si los atributos
		    		 * dealerType dealertype, servicetype ciudad y estado esten igual en la WO.
		    		 * @see co.com.directv.sdii.reports.ReportsGeneratorLocal#generateWorkOrdersPDF(java.util.Collection, java.lang.Long)
		    		 */		
	    			for (WoPdfAnnex annex : woPdfAnnex) {
	    				if(annex.getDealerType().equalsIgnoreCase(CodesBusinessEntityEnum.PDF_ALL_ANNEX.getCodeEntity())){
		    				reportName = UtilsBusiness.generateSingleWOReportName(reportsPath, annex.getFormatName());
			    			PdfUtils.fillPdfForm(dto.getContractInformation(), reportsPath +REPORT_TEMPLATES_SUB_DIR+ annex.getFormatName(), reportName.toString());
			    			reportNames.add(reportName.toString());
	    				}else{
		    				if(annex.getDealerType().equalsIgnoreCase(dto.getDealerType()) 
		    				   && dto.getServiceDTO() != null && !dto.getServiceDTO().isEmpty() 
		    				   && annex.getServiceType().equalsIgnoreCase( dto.getServiceDTO().get(0).getServiceType().getServiceTypeName()) 
		    				   && annex.getDepartmentId().equalsIgnoreCase(dto.getPostalCode().getCity().getState().getId().toString())
		    				   && annex.getCityId().equalsIgnoreCase(dto.getPostalCode().getCityId().toString())){
		    					
			    				reportName = UtilsBusiness.generateSingleWOReportName(reportsPath, annex.getFormatName());
				    			PdfUtils.fillPdfForm(null, reportsPath +REPORT_TEMPLATES_SUB_DIR+ annex.getFormatName(), reportName.toString());
				    			reportNames.add(reportName.toString());
		    				}
	    				}
					}
	    			
	    			
	    			//CC056 adicionar el contrato al pdf
	    			if(isGetContractSystemExternal){
		    			FileResponseDTO workOrderMarkFileResponseDTO = null;
		    			for(WorkOrderWorkOrderMarkVO workOrderWorkOrderMarkVO : workOrderWorkOrderMarkVOList){
		    				if(dto.getCustomerDTO().getCustomerCode().equals(workOrderWorkOrderMarkVO.getWorkOrder().getCustomer().getCustomerCode())){
		    					workOrderMarkFileResponseDTO = workOrderMarkBusiness.downLoadContractWorkOrder(workOrderWorkOrderMarkVO.getWorkOrder().getId());
			    				if(workOrderMarkFileResponseDTO!=null){
			    					OutputStream out = new FileOutputStream(new File(reportsPath +REPORT_TEMPLATES_SUB_DIR+workOrderMarkFileResponseDTO.getFileName()));              
			    					workOrderMarkFileResponseDTO.getDataHandler().writeTo( out );              
			    					out.close();
			    					reportNames.add(reportsPath +REPORT_TEMPLATES_SUB_DIR+workOrderMarkFileResponseDTO.getFileName());
			    				}
			    				hasContract = true;
			    				break;
		    				}
		    			}		    			
	    			}
	    			
	    			if((woPdfAnnex == null || woPdfAnnex.isEmpty()) && hasContract==false){
		    			completeReportNames.add( reportName.toString() );
		    			continue;
		    		}
	    			
	    			if(reportNames.size() >= 1){
	    		    	reportName = new StringBuffer(reportsPath);
	    		    	reportName.append("work_orders");
	    		    	reportName.append("_");
	    		    	reportName.append(UtilsBusiness.dateToString(new java.util.Date(), "dd-MM-yyyy_HH-mm-ss"));
	    	    		reportName.append(".pdf");
	    	    		
	    		    	PdfUtils.concatPDFs(reportNames, reportName.toString(), false);
	    		    	
	    		    	for (String partReportName : reportNames) {
	    					FileUtils.forceDelete(new File(partReportName));
	    				}
	    		    	completeReportNames.add( reportName.toString() );
	    		    	log.debug("PDF de Ordenes de trabajo exportado exitosamente con el siguiente nombre: " + reportName);
	    		    	
	    	    	}else{
	    	    		throw new PDFException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error generando PDF de WorkOrders");	
	    	    	}
				}
	    		
			}
			
			
			if( completeReportNames != null && !completeReportNames.isEmpty() ){
				baos = this.excelGenerator.createPdfStream(completeReportNames);
			}
    		DataSource ds = null;
    		if( baos != null )
    			ds = new  ByteArrayDataSource( baos.toByteArray() , fileMimeType );
    		DataHandler dh = null;
    		if( ds != null )
    			dh = new DataHandler(ds);
    		if( dh != null )
    			response.setDataHandler(dh);
    		else
    			response.setDataHandler(null);
			Date now = new Date();
			String fileName = "WorkOrder"+ UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + "." + "pdf";
			response.setFileName( fileName );
			
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación generateWorkOrderPDF/ReportGeneratorBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateWorkOrderPDF/ReportGeneratorBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Obtiene le path de los reportes
	 * @return
	 * @throws PDFException <tipo> <descripcion>
	 * @author jnova
	 */
	public static String getReportsPath() throws PDFException{
		try {
			return PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS);
		} catch (PropertiesException e) {
			throw new PDFException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),"No se pudo localizar la ruta de exportación de los reportes", e);
		}
		
	}
	
	@Override
	public FileResponseDTO getReferencesByFilterForExcel(ReferencesFilterDTO referenceDTO) throws BusinessException {
		log.debug("== Termina getReferencesByFilterForExcel/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			ReferenceResponse businessResponse = this.referenceBusiness.getReferencesByFilter(referenceDTO,null);
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.REFERRALS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(businessResponse != null && businessResponse.getReferencesVO() != null && !businessResponse.getReferencesVO().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(businessResponse.getReferencesVO(), null, null, CodesBusinessEntityEnum.REFERENCE_EXCEL_JASPER_FILE.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				//excelGenerator.createExcelFileWithJasper(businessResponse.getReferencesVO(), null, null, CodesBusinessEntityEnum.REFERENCE_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getReferencesByFilterForExcel/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getReferencesByFilterForExcel/ReportGeneratorBusinessBean ==");
		}
	}

	@Override
	public FileResponseDTO getTransferReasonByFilter(String transferReasonName) throws BusinessException {
		log.debug("== Termina getTransferReasonByFilter/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			TransferReasonResponse businessResponse = this.transferReasonBusinessBean.getTransferReasonByFilter(transferReasonName, null);
			
			Date now = new Date();
			String fileName = "Causales_De_Traslado_" + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( businessResponse != null && businessResponse.getTransferReason() != null && !businessResponse.getTransferReason().isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(businessResponse.getTransferReason(), null, null, CodesBusinessEntityEnum.TRANSFER_REASON_EXCEL_JASPER_FILE.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				//excelGenerator.createExcelFileWithJasper(businessResponse.getTransferReason(), null, null, CodesBusinessEntityEnum.TRANSFER_REASON_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getTransferReasonByFilter/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getTransferReasonByFilter/ReportGeneratorBusinessBean ==");
		}
	}

	@Override
	public FileResponseDTO getAllWarehousesByCountryId(Long countryId,String code) throws BusinessException {
		log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			WarehouseInfoResponseDetailDTO businessResponse = this.warehouseBusinessBean.getAllWarehousesByCountryId(countryId, code, null);
			
			List<WarehouseDTO> dtoList = new ArrayList<WarehouseDTO>();
			/*if(  businessResponse.getWareHouseVO() != null ){ 
				for( WarehouseInfoDTO vo : businessResponse.getWarehouseInfoDTOList()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWhCode() );
					dto.setDepotCode( vo.getDealerId() == null ? "" : ( vo.getDealerId().getDealer() ==null ? "" : ( vo.getDealerId().getDealer().getDepotCode() == null ? "" : vo.getDealerId().getDealer().getDepotCode() ) ) );
					String branchDealerName = null;
					String principalDealerName = null;
					// caso que la bodega pertenezca a una principal
					if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
						branchDealerName = vo.getDealerId().getDealerName();
					//caso que sea una sucursal
					}else if( vo.getDealerId() != null && vo.getDealerId().getDealer() != null ){
						branchDealerName = vo.getDealerId().getDealer().getDealerName();
						principalDealerName = vo.getDealerId().getDealerName();
					}
					dto.setBranchDealerName( branchDealerName );
					dto.setPrincipalDealerName( principalDealerName );
					dto.setCrewInfo( vo.getCrewResponsable() != null ?  vo.getCrewResponsable() : "" );
					dto.setWhType( vo.getWarehouseType().getWhTypeName() );
					dto.setWhResponsable( vo.getWhResponsible() != null ? vo.getWhResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
					dtoList.add(dto);
				}
			}*/
			if (!businessResponse.getWarehouseInfoDetailDTOList().isEmpty()){
				for( WarehouseInfoDetailDTO vo : businessResponse.getWarehouseInfoDetailDTOList()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWarehouseCode() );
					if(vo.getBranchDealerCode()!=null)
						dto.setDepotCode(vo.getBranchDealerCode());
					else 
						dto.setDepotCode("");
					String branchDealerName = null;
					String principalDealerName = null;
					// caso que la bodega pertenezca a una principal
					//if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
					if(vo.getBranchDealerName()==null){	
						branchDealerName = vo.getDealerName();
					//caso que sea una sucursal
					//}else if( vo.getDealerId() != null && vo.getDealerId().getDealer() != null ){
					}else if( vo.getBranchDealerName() != null ){
						branchDealerName = vo.getBranchDealerName();
						principalDealerName = vo.getDealerName();
					}
					dto.setBranchDealerName( branchDealerName );
					dto.setPrincipalDealerName( principalDealerName );
					dto.setCrewInfo( vo.getCrewName() != null ?  vo.getCrewName() : "" );
					dto.setWhType( vo.getWarehouseType());
					dto.setWhResponsable( vo.getResponsible() != null ? vo.getResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
					dtoList.add(dto);
				}
			}			
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.LOCATIONS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( dtoList != null && !dtoList.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(dtoList, null, null, CodesBusinessEntityEnum.WAREHOUSES_EXCEL_JASPER_FILE.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				excelGenerator.createExcelFileWithJasper(dtoList, null, null, CodesBusinessEntityEnum.WAREHOUSES_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
		}
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#getWhByWhTypeDealerBranchCrewIds(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public FileResponseDTO getWhByWhTypeDealerBranchCrewIds(Long    countryId,
																String  warehouseTypeId, 
																String  dealerId, 
																String  branchId,
																String  crewId 
	) throws BusinessException {
		log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			WarehouseInfoResponseDetailDTO businessResponse = this.warehouseBusinessBean.getWhByWhTypeDealerBranchCrewIds(countryId, 
																															  warehouseTypeId, 
																															  dealerId, 
																															  branchId,
																															  crewId,  
																															  null);
			
			List<WarehouseDTO> dtoList = new ArrayList<WarehouseDTO>();
			/*if(  businessResponse.getWareHouseVO() != null ){ 
				for( WarehouseInfoDTO vo : businessResponse.getWarehouseInfoDTOList()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWhCode() );
					dto.setDepotCode( vo.getDealerId() == null ? "" : ( vo.getDealerId().getDealer() ==null ? "" : ( vo.getDealerId().getDealer().getDepotCode() == null ? "" : vo.getDealerId().getDealer().getDepotCode() ) ) );
					String branchDealerName = null;
					String principalDealerName = null;
					// caso que la bodega pertenezca a una principal
					if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
						branchDealerName = vo.getDealerId().getDealerName();
					//caso que sea una sucursal
					}else if( vo.getDealerId() != null && vo.getDealerId().getDealer() != null ){
						branchDealerName = vo.getDealerId().getDealer().getDealerName();
						principalDealerName = vo.getDealerId().getDealerName();
					}
					dto.setBranchDealerName( branchDealerName );
					dto.setPrincipalDealerName( principalDealerName );
					dto.setCrewInfo( vo.getCrewResponsable() != null ?  vo.getCrewResponsable() : "" );
					dto.setWhType( vo.getWarehouseType().getWhTypeName() );
					dto.setWhResponsable( vo.getWhResponsible() != null ? vo.getWhResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
					dtoList.add(dto);
				}
			}*/
			if (!businessResponse.getWarehouseInfoDetailDTOList().isEmpty()){
				for( WarehouseInfoDetailDTO vo : businessResponse.getWarehouseInfoDetailDTOList()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWarehouseCode() );
					if(vo.getBranchDealerCode()!=null)
						dto.setDepotCode(vo.getBranchDealerCode());
					else 
						dto.setDepotCode("");
					// caso que la bodega pertenezca a una principal
					//if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
					
					/*if(vo.getBranchDealerName()==null){	
						branchDealerName = vo.getDealerName();
					}
					else if( vo.getBranchDealerName() != null ){
						branchDealerName = vo.getBranchDealerName();
						principalDealerName = vo.getDealerName();
					}
					*/
					//.net envia el nombre del dealer en el campo de branch y lo mismo para el branch
					dto.setBranchDealerName( vo.getBranchDealerName()  );
					dto.setPrincipalDealerName( vo.getDealerName());
					dto.setCrewInfo( vo.getCrewName() != null ?  vo.getCrewName() : "" );
					dto.setWhType( vo.getWarehouseType());
					dto.setWhResponsable( vo.getResponsible() != null ? vo.getResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
					dtoList.add(dto);
				}
			}			
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.LOCATIONS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( dtoList != null && !dtoList.isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(dtoList, null, null, CodesBusinessEntityEnum.WAREHOUSES_EXCEL_JASPER_FILE.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				excelGenerator.createExcelFileWithJasper(dtoList, null, null, CodesBusinessEntityEnum.WAREHOUSES_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getAllWarehousesByCountryId/ReportGeneratorBusinessBean ==");
		}
	}	
	@Override
	public FileResponseDTO getWareHouseElementsActualByCustomer(
			WareHouseElementClientFilterRequestDTO request)
			throws BusinessException {
		log.debug("== Termina getWareHouseElementsActualByCustomer/ReportGeneratorBusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			WareHouseElementCustomerResponse elementCustomerResponse = this.businessWarehouseElement.getWarehouseElementsByWarehouseCustomerActual(request, null);
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.EXIST_CLIENT.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if( elementCustomerResponse != null && elementCustomerResponse.getWareHouseElementsDto() != null && !elementCustomerResponse.getWareHouseElementsDto().isEmpty() ){
				baos = excelGenerator.createExcelStreamWithJasper(elementCustomerResponse.getWareHouseElementsDto(), null, null, CodesBusinessEntityEnum.REPORT_WAREHOUSE_ELEMENT_CUSTOMER_ACTUAL.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				//excelGenerator.createExcelFileWithJasper(businessResponse.getTransferReason(), null, null, CodesBusinessEntityEnum.TRANSFER_REASON_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getWareHouseElementsActualByCustomer/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getWareHouseElementsActualByCustomer/ReportGeneratorBusinessBean ==");
		}
	}

	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.report.ReportGeneratorBusinessBeanLocal#generateCrewWorkOrdersExcel(java.lang.Long, java.util.List , java.util.List)
	 */
	@Override
	public FileResponseDTO generateCrewWorkOrdersExcel(Long countryId, List<Long> workOrderIds, List<Long> crewIds ) throws BusinessException {
		log.debug("== Termina generateCrewWorkOrdersExcel/ReportGeneratorBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(workOrderIds, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(crewIds, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			FileResponseDTO response = new FileResponseDTO();
			
			//completar con datos para visita
			Date now = new Date();
			String fileName = ApplicationTextEnum.VISITS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			
			//eliminamos los crewId's duplicados
			Set<Long> crewIdsWithoutDuplicates = new LinkedHashSet<Long>(crewIds);

			//lista que contendra el responsable de la cuadrilla y sus respectivas WO's
			List<VisitsReportItemExcel> visitsReportItemExcelList = new ArrayList<VisitsReportItemExcel>();
			
			//array para almacenar los nombres de las paginas en el excel 
			String[] sheetNames= new String[crewIds.size()];
			int i = 0;
			
			for ( Long crewId : crewIdsWithoutDuplicates ){
				i = i + 1;
				VisitsReportItemExcel visitsReportItemExcel = new VisitsReportItemExcel();
				//buscamos el técnico responsable de la cuadrilla
				visitsReportItemExcel.setEmployeeResponsibleCrew(employeesCrewDAO.getEmployeeResponsibleByCrewId(crewId));
				
				if(visitsReportItemExcel.getEmployeeResponsibleCrew()==null ){
					throw new BusinessException(ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED.getCode() ,"No se encontro ningun empleado responsable de la WorkOrder de los  que tiene ");
				}
				
				Long maxNumberWoPerPdfFile = UtilsBusiness.getNumericSystemParameter(/*utilice el mismo limite que cuando se exporta a PDF*/CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PDF_MAX_WORK_ORDERS_BY_PDF_FILE.getCodeEntity(), countryId, systemParameterDAO);			
				
				Map<WorkOrder, WorkOrderAgenda> workOrders = workOrderDAOBean.getWorkOrdersByIdsAndCrewAssignment(workOrderIds, crewId, maxNumberWoPerPdfFile);

				if(workOrders.isEmpty()){
					log.debug("Se trató de generar la planilla de visitas de varias WO que no están asignadas a la cuadrilla especificada con id: " + crewId);
					throw new BusinessException(ErrorBusinessMessages.NONE_OF_SELECTED_WORK_ORDERS_ARE_ASSIGNED_TO_SELECTED_CREW.getCode(), ErrorBusinessMessages.NONE_OF_SELECTED_WORK_ORDERS_ARE_ASSIGNED_TO_SELECTED_CREW.getMessage());
				}
				
				List<VisitsReportItem> dataSource = toVisitsReportItemList( workOrders );
				
				visitsReportItemExcel.setVisitsReportItems(dataSource);
				
				visitsReportItemExcelList.add(visitsReportItemExcel);
				
				sheetNames[i -1] = "Responsable "+ i;
				
			}
			
			//validamos si esta vacio para luego invocar la creación del jasper con varias hojas
			if( visitsReportItemExcelList != null && !visitsReportItemExcelList.isEmpty() ){
				baos = excelGenerator.createExcelMultipleSheetStreamWithJasper(visitsReportItemExcelList, null, sheetNames, CodesBusinessEntityEnum.VISITS_EXCEL_JASPER_FILE.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray() , "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina generateCrewWorkOrdersExcel/ReportGeneratorBusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina generateCrewWorkOrdersExcel/ReportGeneratorBusinessBean ==");
		}
	}
	
	/**
	 * Convierte una lista de workOrders a una lista de items del reporte con todos los valores formateados
	 * para que salga en el reporte final como una datasource de pojos
	 * @param workOrders
	 * @return Collection<VisitsReportItem>
	 * @throws PropertiesException 
	 */
	private List<VisitsReportItem> toVisitsReportItemList(Map<WorkOrder, WorkOrderAgenda> workOrdersMap) throws PropertiesException{
		if(workOrdersMap==null || workOrdersMap.size() == 0)
			return null;
		
		List<VisitsReportItem> items = new ArrayList<VisitsReportItem>();
		Set<WorkOrder> workOrders = workOrdersMap.keySet();
		WorkOrderAgenda woAgenda = null;
		
		for (WorkOrder workOrder : workOrders) {
			Customer currentCustomer = workOrder.getCustomer();
			VisitsReportItem visitsReportItem = new VisitsReportItem();
			visitsReportItem.setAddress(currentCustomer.getCustomeraddress());
			
			visitsReportItem.setClient(currentCustomer.getCustomerCode());
			visitsReportItem.setEnd("");
			visitsReportItem.setJobCard(workOrder.getWoCode());
			
			woAgenda = workOrdersMap.get(workOrder);
			
			if(woAgenda != null && woAgenda.getAgendationDate() != null){
				visitsReportItem.setMeridian(UtilsBusiness.dateToString(woAgenda.getAgendationDate(), UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS));
				visitsReportItem.setComments(ApplicationTextEnum.CONTACT.getApplicationTextValue()+": " + woAgenda.getContactPerson() + " - "+ApplicationTextEnum.COMMENTS.getApplicationTextValue()+": " + woAgenda.getDescription());
				visitsReportItem.setArriveHour(UtilsBusiness.dateToString(woAgenda.getServiceHour().getInitTime(), "HH:mm:ss"));
				visitsReportItem.setDepartureHour(UtilsBusiness.dateToString(woAgenda.getServiceHour().getEndTime(), "HH:mm:ss"));
			}
			
			visitsReportItem.setName(currentCustomer.getFirstName()+" "+currentCustomer.getLastName());
			visitsReportItem.setServiceType(workOrder.getWoTypeByWoTypeId() == null ? "" : workOrder.getWoTypeByWoTypeId().getWoTypeName());
			items.add(visitsReportItem);
		}
		
		return items;
	}
		
}
