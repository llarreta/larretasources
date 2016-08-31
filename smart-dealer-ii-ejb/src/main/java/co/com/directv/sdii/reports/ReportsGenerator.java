package co.com.directv.sdii.reports;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
import co.com.directv.sdii.common.util.PdfUtils;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayForPdfDTO;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.MediaContactType;
import co.com.directv.sdii.model.pojo.WoPdfAnnex;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal;

/**
 * Genera los diferentes reportes del aplicativo basados en tecnolog�a JasperReports
 * @author Leonardo Cardozo
 *
 */
@Stateless(mappedName="ejb/ReportGeneratorLocal", name="ReportGeneratorLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportsGenerator implements ReportsGeneratorLocal {

	//private final static String WORK_ORDER_TEMPLATE = "templates/workorder_report1.jasper";
	private final static String VISITS_TEMPLATE = "templates/VisitsTemplate.jasper";
//	private static final String CONTRACT_TEMPLATE_LOCATION = "templates/contrato_suscripcion_DirecTV.pdf";
//	private static final String AUTOMATIC_DEBIT_TEMPLATE_LOCATION = "templates/debito_automatico.pdf";
//	private static final String H_APPENDIX_TEMPLATE_LOCATION = "templates/anexo_h.pdf";
	
	private final static Logger log = Logger.getLogger(ReportsGenerator.class);
	private static final String REPORT_TEMPLATES_SUB_DIR = "templates/";
	
	@EJB(name="WoPdfAnnexDAOLocal", beanInterface=WoPdfAnnexDAOLocal.class)
	private WoPdfAnnexDAOLocal woPdfAnnexDAOLocal;
	
	@EJB(name="TrayWorkOrderManagmentBusinessLocal", beanInterface=TrayWorkOrderManagmentBusinessLocal.class)
	private TrayWorkOrderManagmentBusinessLocal trayWorkOrderManagmentBusinessLocal;
	
	public String generateCrewWorkOrdersPDF(Map<WorkOrder, WorkOrderAgenda> workOrders, Employee employee) throws PDFException{
		log.debug("Exportando PDF de Ordenes de trabajo");

		String reportsPath = getReportsPath();
		Date now = new Date();
		if(employee == null ){
			throw new PDFException(ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED.getCode(), "Empleado nulo, no se puede exportar reporte");
		}
		JasperPrint jasperPrint;
		//Map<String, String> pars = new HashMap<String,String>();

	    //pars.put("PV_TITLE", "PLANTILLA DE VISITAS");
	    //pars.put("PV_RESPONSIBLE", employee.getFirstName()+ " " + employee.getLastName());
	    //pars.put("PV_CODE", employee.getDocumentNumber());
	    //pars.put("PV_CI", ""+employee.getId());
	    //pars.put("PV_DATA", UtilsBusiness.formatDate(now));
	    
	    try{
	       
	    	//i8n ini
	    	Map<String, String> pars = UtilsBusiness.getReportParams();
		    pars.put("PV_TITLE", "PLANTILLA DE VISITAS");
		    pars.put("PV_RESPONSIBLE", employee.getFirstName()+ " " + employee.getLastName());
		    pars.put("PV_CODE", employee.getDocumentNumber());
		    pars.put("PV_CI", ""+employee.getId());
		    pars.put("PV_DATA", UtilsBusiness.formatDate(now));
		    //i8n fin
		    
	        jasperPrint = JasperFillManager.fillReport(reportsPath+VISITS_TEMPLATE, pars, new JRBeanCollectionDataSource(toVisitsReportItemList(workOrders)));
	        
	        String reportName = ApplicationTextEnum.TEMPLATE_SERVICES.getApplicationTextValue()+employee.getDealer().getDealerCode()+"_"+UtilsBusiness.formatYYYYMMDD(now)+"-"+now.getTime();
	        String report = reportsPath+reportName+".pdf"; 
	        
	        JasperExportManager.exportReportToPdfFile(jasperPrint,report);
	    
	        log.debug("PDF de Ordenes de trabajo exportado exitosamente");
	        return report;

	    }catch (Exception e){
	    	throw new PDFException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error generando PDF de WorkOrders", e);
	    }
		
	}
	

	/* (non-Javadoc)
	 * Obtiene los anexos necesarios de un pais para luego encadenar estis a la WO
	 * Si dealer_Type de wo_pdf_annex tiene el valor de ALL, entonces se asignan
	 * a ese pais todos los anexos para cualquier dealertype, servicetype
	 * ciudad y estado, de no tener la palabra ALL entonces se chequea si los atributos
	 * dealerType dealertype, servicetype ciudad y estado esten igual en la WO.
	 * @see co.com.directv.sdii.reports.ReportsGeneratorLocal#generateWorkOrdersPDF(java.util.Collection, java.lang.Long)
	 */
	public String generateWorkOrdersPDF(Collection<WorkOrderReportInfo> workOrders, Long crewId) throws PDFException{
		log.info("Exportando PDF de Ordenes de trabajo");
		String reportsPath = getReportsPath();
		JasperPrint jasperPrint;
	    //Map<String, String> pars = new HashMap<String,String>();
	    List<String> reportNames = new ArrayList<String>();
	    StringBuffer reportName = null;
	    try{
	    	Long telephoneMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long faxMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long mobileMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long workTelephoneMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getIdEntity(MediaContactType.class.getName());
	    	
	    	Map<String, String> pars = UtilsBusiness.getReportParams();
    		pars.put("MEDIA_CONTACT_TYPE_TELEP_CODE", "" + telephoneMediaCTypeId);
    		pars.put("MEDIA_CONTACT_TYPE_FAX_CODE", "" + faxMediaCTypeId);
    		pars.put("MEDIA_CONTACT_TYPE_MOBILE_CODE", "" + mobileMediaCTypeId);
    		pars.put("MEDIA_CONTACT_TYPE_TELEPHWORK_CODE", "" + workTelephoneMediaCTypeId);
    		pars.put("SUBREPORT_DIR", reportsPath + REPORT_TEMPLATES_SUB_DIR);
    		
    		//Obteniendo una conexión con la base de datos
    		Connection connection = getDataBaseConnection();
    		
    		// Variables para ayduar a definir que jasper de WO usar dependiendo del pais

    		List<WoPdfAnnex> woPdfAnnex = null;
    		String woJasperName = null;
    		String builtPdfAnnexQuery = null;
	    	for (WorkOrderReportInfo workOrderInfo : workOrders) {
	    		pars.put("WORK_ORDER_ID", "" + workOrderInfo.getWorkOrderId());
	    		
	    		// Genera el nombre de la WO a crear
	    		woJasperName = UtilsBusiness.generateWoName(workOrderInfo.getCountryId(),woPdfAnnexDAOLocal);
	    		if(woJasperName.equalsIgnoreCase("continue")){
	    			continue;
	    		}
	    		//Crea el PDF y lo llena
	    		jasperPrint = JasperFillManager.fillReport(reportsPath+REPORT_TEMPLATES_SUB_DIR+woJasperName, pars, connection);
	    		reportName = generateSingleWOReportName(reportsPath, null,workOrderInfo.getWorkOrderId(), crewId);
	    		//Crea el PDF en el DD
	    		JasperExportManager.exportReportToPdfFile(jasperPrint,reportName.toString());
	    		reportNames.add(reportName.toString());
	    		
	    		
	    		//Busca el los anexos necesarios para un WO especifica dependiendo del pais
	    		builtPdfAnnexQuery = UtilsBusiness.buildQuery(UtilsBusiness.buildVariablesForWoOrAnnex(workOrderInfo.getCountryId(), CodesBusinessEntityEnum.PDF_ANNEX_CODE.getCodeEntity()), null, null);
	    	
	    		woPdfAnnex = woPdfAnnexDAOLocal.searchWoPdfAnnexByCriteria(builtPdfAnnexQuery);
	    		
	    		if(woPdfAnnex == null){
	    			continue;
	    		}
	    		
	    		if(woPdfAnnex.size()==0){
	    			continue;
	    		}	
	    		/*
	    		 * Obtiene los anexos necesarios de un pais para luego encadenar estos a la WO
	    		 * Si dealer_Type de tiene el valor de ALL, entonces se asignan
	    		 * a ese pais todos los anexos para cualquier dealertype, servicetype
	    		 * ciudad y estado, de no tener la palabra ALL entonces se chequea si los atributos
	    		 * dealerType dealertype, servicetype ciudad y estado esten igual en la WO.
	    		 * 
	    		 */
    			for (WoPdfAnnex annex : woPdfAnnex) {
    				if(annex.getDealerType().equalsIgnoreCase(CodesBusinessEntityEnum.PDF_ALL_ANNEX.getCodeEntity())){
    					reportName = generateSingleWOReportName(reportsPath, annex.getFormatName(), workOrderInfo.getWorkOrderId(), crewId);
		    			PdfUtils.fillPdfForm(workOrderInfo.getContractItems(), reportsPath + annex.getFormatName(), reportName.toString());
		    			reportNames.add(reportName.toString());
    				}else{
    	  				if(annex.getDealerType().equalsIgnoreCase(workOrderInfo.getDealerType()) 
    	  				   && annex.getServiceType().equalsIgnoreCase(workOrderInfo.getServiceType()) 
    	  				   && annex.getDepartmentId().equalsIgnoreCase(workOrderInfo.getState())
    	  				   && annex.getCityId().equalsIgnoreCase(workOrderInfo.getCity())){
    	  					
		    				reportName = generateSingleWOReportName(reportsPath, annex.getFormatName(), workOrderInfo.getWorkOrderId(), crewId);
			    			PdfUtils.fillPdfForm(workOrderInfo.getContractItems(), reportsPath + annex.getFormatName(), reportName.toString());
			    			reportNames.add(reportName.toString());
    	  				}
    				}
	    			
				}
	    	}
	    	
	    	//Liberando la conexión de base de datos
	    	freeDBConnection(connection);
    		
	    	if(reportNames.size() > 1){
		    	reportName = new StringBuffer(reportsPath);
		    	reportName.append("crew_");
	    		reportName.append(crewId);
	    		reportName.append("_");
		    	reportName.append("work_orders");
		    	reportName.append("_");
		    	reportName.append(UtilsBusiness.dateToString(new java.util.Date(), "dd-MM-yyyy_HH-mm-ss"));
	    		reportName.append(".pdf");
	    		
		    	PdfUtils.concatPDFs(reportNames, reportName.toString(), false);
		    	
		    	for (String partReportName : reportNames) {
					FileUtils.forceDelete(new File(partReportName));
				}
	    	}
	        log.info("PDF de Ordenes de trabajo exportado exitosamente con el siguiente nombre: " + reportName);
	        return reportName.toString();

	    }catch (Throwable e){
	    	e.printStackTrace();
	    	throw new PDFException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error generando PDF de WorkOrders", e);
	    }
	}
	
	/* (non-Javadoc)
	 * Obtiene los anexos necesarios de un pais para luego encadenar estis a la WO
	 * Si dealer_Type de wo_pdf_annex tiene el valor de ALL, entonces se asignan
	 * a ese pais todos los anexos para cualquier dealertype, servicetype
	 * ciudad y estado, de no tener la palabra ALL entonces se chequea si los atributos
	 * dealerType dealertype, servicetype ciudad y estado esten igual en la WO.
	 * @see co.com.directv.sdii.reports.ReportsGeneratorLocal#generateWorkOrdersPDF(java.util.Collection, java.lang.Long)
	 *
	 * @see co.com.directv.sdii.reports.ReportsGeneratorLocal#generateWorkOrdersPDF(java.util.Collection)
	 */
	public String generateWorkOrdersPDF(Collection<WorkOrderReportInfo> workOrders) throws PDFException{
		log.info("Exportando PDF de Ordenes de trabajo");
		String reportsPath = getReportsPath();
		JasperPrint jasperPrint;
	    //Map<String, String> pars = new HashMap<String,String>();
	    List<String> reportNames = new ArrayList<String>();
	    StringBuffer reportName = null;
	    try{
	    	Long telephoneMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long faxMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long mobileMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getIdEntity(MediaContactType.class.getName());
	    	Long workTelephoneMediaCTypeId = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE.getIdEntity(MediaContactType.class.getName());
	    	
	    	Map<String, String> pars = UtilsBusiness.getReportParams();
    		pars.put("TELEP_CODE", "" + telephoneMediaCTypeId);
    		pars.put("FAX_CODE", "" + faxMediaCTypeId);
    		pars.put("MOBILE_CODE", "" + mobileMediaCTypeId);
    		pars.put("TELEPHWORK_CODE", "" + workTelephoneMediaCTypeId);
	    	
    		pars.put("SUBREPORT_DIR", reportsPath + REPORT_TEMPLATES_SUB_DIR);
    		
    		// Variables para ayduar a definir que jasper de WO usar dependiendo del pais
    		List<WoPdfAnnex> woPdfAnnex = null;
    		String woJasperName = null;
    		String builtPdfAnnexQuery = null;
	    	for (WorkOrderReportInfo workOrderInfo : workOrders) {
	    		
	    		// Genera el nombre de la WO a crear
	    		woJasperName = UtilsBusiness.generateWoName(workOrderInfo.getCountryId(), woPdfAnnexDAOLocal);
	    		if(woJasperName.equalsIgnoreCase("continue")){
	    			continue;
	    		}
	    		//Crea el PDF y lo llena
//	    		jasperPrint = JasperFillManager.fillReport(reportsPath+REPORT_TEMPLATES_SUB_DIR+woJasperName, pars, connection);
	    		//Se crea objeto generico para obtener los details de una WO
	    		WorkOrderFilterTrayDTO filterTrayDTO = new WorkOrderFilterTrayDTO();
	    		filterTrayDTO.setWoCode(workOrderInfo.getWoCode());
	    		filterTrayDTO.setCountryId(workOrderInfo.getCountryId());
	    		
	    		// Se mete en una lista para poderlo poner como un Data Source en el Japser
	    		ArrayList<WorkOrderTrayForPdfDTO> workOrderTrayList = new ArrayList<WorkOrderTrayForPdfDTO>();
	    		//workOrderTrayList.add(trayWorkOrderManagmentBusinessLocal.getWorkorderDetailForPdf(filterTrayDTO));
	    		
	    		//Se asignan las variables deseadas al reporte y se le asigna un Data Source de java beans
	    		//a la lista creada anteriormente se le asigna un objeto el cual va a ser el punto de partida para
	    		//llenar todo el reporte.
	    		jasperPrint = JasperFillManager.fillReport(reportsPath+REPORT_TEMPLATES_SUB_DIR+woJasperName, pars, new JRBeanCollectionDataSource(workOrderTrayList));
	    		reportName = generateSingleWOReportName(reportsPath, null,workOrderInfo.getWorkOrderId(), null);
	    		//Crea el PDF en el DD
	    		JasperExportManager.exportReportToPdfFile(jasperPrint,reportName.toString());
	    		reportNames.add(reportName.toString());
	    		
	    		
	    		//Busca el los anexos necesarios para un WO especifica dependiendo del pais
	    		builtPdfAnnexQuery = UtilsBusiness.buildQuery(UtilsBusiness.buildVariablesForWoOrAnnex(workOrderInfo.getCountryId(), CodesBusinessEntityEnum.PDF_ANNEX_CODE.getCodeEntity()), null, null);
	    	
	    		woPdfAnnex = woPdfAnnexDAOLocal.searchWoPdfAnnexByCriteria(builtPdfAnnexQuery);
	    		
	    		if(woPdfAnnex == null){
	    			continue;
	    		}
	    		
	    		if(woPdfAnnex.size()==0){
	    			continue;
	    		}	  
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
	    				reportName = generateSingleWOReportName(reportsPath, annex.getFormatName(), workOrderInfo.getWorkOrderId(), null);
		    			PdfUtils.fillPdfForm(workOrderInfo.getContractItems(), reportsPath +REPORT_TEMPLATES_SUB_DIR+ annex.getFormatName(), reportName.toString());
		    			reportNames.add(reportName.toString());
    				}else{
	    				if(annex.getDealerType().equalsIgnoreCase(workOrderInfo.getDealerType()) 
	    				   && annex.getServiceType().equalsIgnoreCase(workOrderInfo.getServiceType()) 
	    				   && annex.getDepartmentId().equalsIgnoreCase(workOrderInfo.getState()) 
	    				   && annex.getCityId().equalsIgnoreCase(workOrderInfo.getCity())){
	    					
		    				reportName = generateSingleWOReportName(reportsPath, annex.getFormatName(), workOrderInfo.getWorkOrderId(), null);
			    			PdfUtils.fillPdfForm(workOrderInfo.getContractItems(), reportsPath +REPORT_TEMPLATES_SUB_DIR+ annex.getFormatName(), reportName.toString());
			    			reportNames.add(reportName.toString());
			    			
	    				}
    				}
	    			
				}
	    	}
	    	
	    	//Liberando la conexión de base de datos
//	    	freeDBConnection(connection);
    		
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
		    	
		    	log.info("PDF de Ordenes de trabajo exportado exitosamente con el siguiente nombre: " + reportName);
		        return reportName.toString();
	    	}else{
	    		throw new PDFException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error generando PDF de WorkOrders");	
	    	}
	    	
	    }catch (Throwable e){
	    	e.printStackTrace();
	    	throw new PDFException(ErrorBusinessMessages.UNKNOW_ERROR.getCode(), "Error generando PDF de WorkOrders", e);
	    }
	}
	
	private StringBuffer generateSingleWOReportName(String reportsPath, String subReportName, Long workOrderId, Long crewId){
		StringBuffer reportName = new StringBuffer(reportsPath);
		if( crewId != null && crewId > 0L ){
			reportName.append("crew_");
			reportName.append(crewId);
			reportName.append("_");
		}	
		reportName.append("work_order_");
		reportName.append(workOrderId);
		reportName.append("_");
		if(subReportName != null){
			reportName.append(subReportName);
			reportName.append("_");
		}
		reportName.append(UtilsBusiness.dateToString(new java.util.Date(), "dd-MM-yyyy_HH-mm-ss"));
		reportName.append(".pdf");
		
		return reportName;
	}
	
	private void freeDBConnection(Connection connection) {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	private Connection getDataBaseConnection() {
		///*
		try {
			InitialContext ic = new InitialContext();
			String jndiName = PropertiesReader.getInstance().getAppKey(Constantes.JNDI_DATA_SOURCE_NAME); 
			DataSource ds = (DataSource)ic.lookup(jndiName);
			Connection con = ds.getConnection();
			return con;
		} catch (NamingException e) {
			e.printStackTrace();
			log.error("Error al tratar de obtener el recurso JNDI de la conexión: " + e.getMessage(),e);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Error al tratar de obtener el recurso JNDI de la conexión: " + e.getMessage(),e);
		} catch (PropertiesException e) {
			e.printStackTrace();
			log.error("Error al tratar de obtener el recurso JNDI de la conexión: " + e.getMessage(),e);
		}
		//*/
		/*
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@sbogor01:1521:ORCL", "sdii", "sdii");
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//*/
		return null;
	}


	/**
	 * Convierte una lista de workOrders a una lista de items del reporte con todos los valores formateados
	 * para que salga en el reporte final como una datasource de pojos
	 * @param workOrders
	 * @return Collection<VisitsReportItem>
	 * @throws PropertiesException 
	 */
	private Collection<VisitsReportItem> toVisitsReportItemList(Map<WorkOrder, WorkOrderAgenda> workOrdersMap) throws PropertiesException{
		if(workOrdersMap==null || workOrdersMap.size() == 0)
			return null;
		
		Collection<VisitsReportItem> items = new ArrayList<VisitsReportItem>();
		Set<WorkOrder> workOrders = workOrdersMap.keySet();
		WorkOrderAgenda woAgenda = null;
		
		for (WorkOrder workOrder : workOrders) {
			Customer currentCustomer = workOrder.getCustomer();
			VisitsReportItem visitsReportItem = new VisitsReportItem();
			visitsReportItem.setAddress(currentCustomer.getCustomeraddress());
			
			visitsReportItem.setClient(currentCustomer.getCustomerCode());
			
			
			
//			if(CodesBusinessEntityEnum.WORKORDER_REASON_REALIZED.getCodeEntity().equals(workOrder.getWorkorderStatusByActualStatusId().getWoStateCode())){
//				visitsReportItem.setEnd(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());	
//			}else{
//				visitsReportItem.setEnd(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
//			}
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
	
	
	public static String getReportsPath() throws PDFException{
		try {
			return PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS);
		} catch (PropertiesException e) {
			throw new PDFException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),"No se pudo localizar la ruta de exportación de los reportes", e);
		}
		
	}

}
