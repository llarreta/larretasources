package co.com.directv.sdii.ejb.business.core.tray.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayReportsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.CustomerDTO;
import co.com.directv.sdii.model.dto.CustomerResourcesDTO;
import co.com.directv.sdii.model.dto.PdfServiceDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayForPdfDTO;
import co.com.directv.sdii.model.pojo.Contact;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderExport;
import co.com.directv.sdii.model.pojo.WorkOrderExportData;
import co.com.directv.sdii.model.pojo.WorkOrderExportDataId;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerMediaContactVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderExportDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderExportDataDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;
import co.com.directv.sdii.reports.dto.WorkorderDescriptionDTO;

/**
 * 
 * Implementacion de la logica de negocio para las consultas de reportes de
 * excel y pdf
 * 
 * Fecha de Creación: 5/09/2011
 * 
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "TrayReportsBusinessLocal", mappedName = "ejb/TrayReportsBusinessLocal")
@Local({ TrayReportsBusinessLocal.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrayReportsBusiness extends BusinessBase implements
		TrayReportsBusinessLocal {

	private final static Logger log = UtilsBusiness
			.getLog4J(TrayReportsBusiness.class);

	@EJB
	private WorkOrderBusinessBeanLocal workOrderBusiness;

	@EJB(name = "WorkOrderDAOLocal", beanInterface = WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;
	
	@EJB(name = "WorkOrderDAOExportLocal", beanInterface = WorkOrderExportDAOLocal.class)
	private WorkOrderExportDAOLocal workOrderExportDAO;
	
	@EJB(name = "WorkOrderDAOExportDataLocal", beanInterface = WorkOrderExportDataDAOLocal.class)
	private WorkOrderExportDataDAOLocal workOrderExportDataDAO;

	@EJB(name = "DocumentTypesCRUDBeanLocal", beanInterface = DocumentTypesCRUDBeanLocal.class)
	private DocumentTypesCRUDBeanLocal documentTypesCRUD;

	@EJB(name = "CustomerMediaContactDAOLocal", beanInterface = CustomerMediaContactDAOLocal.class)
	private CustomerMediaContactDAOLocal customerMediaContactDAO;

	@EJB(name = "WorkOrderAgendaDAOLocal", beanInterface = WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;

	@EJB(name = "WoAssignmentDAOLocal", beanInterface = WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAO;

	@EJB(name = "DealersDAOLocal", beanInterface = DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;

	@EJB(name = "EmployeesCrewDAOLocal", beanInterface = EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;

	@EJB(name = "WoCrewAssigmentsDAOLocal", beanInterface = WoCrewAssigmentsDAOLocal.class)
	private WoCrewAssigmentsDAOLocal woCrewAssigmentsDAO;

	@EJB(name = "EmployeeMediaContactDAOLocal", beanInterface = EmployeeMediaContactDAOLocal.class)
	private EmployeeMediaContactDAOLocal employeeMediaContactDAO;

	@EJB(name = "EmployeeVehicleDAOLocal", beanInterface = EmployeeVehicleDAOLocal.class)
	private EmployeeVehicleDAOLocal employeeVehicleDAO;

	@EJB(name = "DealersMediaContactCRUDBeanLocal", beanInterface = DealersMediaContactCRUDBeanLocal.class)
	private DealersMediaContactCRUDBeanLocal dealersMediaContactCRUDBean;

	@EJB(name = "SystemParameterDAOLocal", beanInterface = SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;

	@EJB(name = "WoStatusHistoryDAOLocal", beanInterface = WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal woStatusHistoryDAO;

	@EJB(name = "TechnologyDAOLocal", beanInterface = TechnologyDAOLocal.class)
	private TechnologyDAOLocal technologyDAO;

	@EJB(name = "CustomerServiceBrokerLocal", beanInterface = CustomerServiceBrokerLocal.class)
	private CustomerServiceBrokerLocal customerServiceBroker;

	@EJB(name = "WorkOrderServiceDAOLocal", beanInterface = WorkOrderServiceDAOLocal.class)
	private WorkOrderServiceDAOLocal woServiceDAO;

	@EJB(name = "TrayWorkOrderManagmentBusinessLocal", beanInterface = TrayWorkOrderManagmentBusinessLocal.class)
	private TrayWorkOrderManagmentBusinessLocal trayWorkOrderManagmentBusiness;

	@EJB(name = "ShippingOrderDAOLocal", beanInterface = ShippingOrderDAOLocal.class)
	private ShippingOrderDAOLocal shippingOrderDAO;

	@EJB(name = "TechnologyBusinessBeanLocal", beanInterface = TechnologyBusinessBeanLocal.class)
	private TechnologyBusinessBeanLocal technologyBusinessBean;

	@EJB(name = "WorkorderStatusDAOLocal", beanInterface = WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workOrderStatusDAO;

	@EJB(name = "CountriesDAOLocal", beanInterface = CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;

	@EJB(name = "UserDAOLocal", beanInterface = UserDAOLocal.class)
	private UserDAOLocal userDao;

	@EJB(name = "ContactDAOLocal", beanInterface = ContactDAOLocal.class)
	private ContactDAOLocal contactDAO;
	// para probar el export a PDF
	@EJB(name = "OptimusStatusDAOLocal", beanInterface = OptimusStatusDAOLocal.class)
	private OptimusStatusDAOLocal optimusDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.
	 * TrayWorkOrderManagmentBusinessLocal
	 * #getWorkorderDetailForPdf(co.com.directv
	 * .sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<WorkOrderTrayForPdfDTO> getWorkorderDetailForPdf(
			List<String> workOrderCodes, Long countryId)
			throws BusinessException {
		log.debug("== Inicia getWorkorderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
		try {

			List<WorkOrderVO> workOrdersVO = workOrderBusiness
					.getWorkOrdersByWoIdForSimilarStateByCode(
							workOrderCodes,
							CodesBusinessEntityEnum.STATES_FOR_SEARCH_FOR_PDF_CORE
									.getCodeEntity());

			for (WorkOrderVO woVO : workOrdersVO) {
				if (!workOrderCodes.contains(woVO.getWoCode())) {
					workOrderCodes.add(woVO.getWoCode());
				}
			}

			ArrayList<WorkOrderTrayForPdfDTO> workOrderTrayList = new ArrayList<WorkOrderTrayForPdfDTO>();
			Map<String, List<WorkOrder>> map = new HashMap<String, List<WorkOrder>>();
			for (String woCode : workOrderCodes) {
				WorkOrder workOrder = this.workOrderDAO
						.getWorkOrderByCode(woCode);
				if (workOrder != null && workOrder.getCustomer() != null
						&& workOrder.getCustomer().getCustomerCode() != null) {
					if (!map.containsKey(workOrder.getCustomer()
							.getCustomerCode())) {
						List<WorkOrder> woList = new ArrayList<WorkOrder>();
						woList.add(workOrder);
						map.put(workOrder.getCustomer().getCustomerCode(),
								woList);
					} else {
						map.get(workOrder.getCustomer().getCustomerCode()).add(
								workOrder);
					}
				}
			}
			Iterator<Entry<String, List<WorkOrder>>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, List<WorkOrder>> entry = (Map.Entry<String, List<WorkOrder>>) iterator.next();
				workOrderTrayList.add(this.fillWorkOrderDetailForPdf(
						(List<WorkOrder>) entry.getValue(), countryId));
			}
			return workOrderTrayList;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la tarea getWorkorderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWorkorderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Obtiene la informacion de la primera WO de la lista y si la lista
	 * es mayor a uno, adiciona las WO en la lista de servicios para motrarlas
	 * en la grilla de servicios
	 * 
	 * @param woList
	 * @return WorkOrderTrayForPdfDTO objeto que mapea la informacion para
	 *         jasper
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private WorkOrderTrayForPdfDTO fillWorkOrderDetailForPdf(
			List<WorkOrder> woList, Long countryId) throws BusinessException {
		log.debug("== Inicia fillWorkOrderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
		try {
			WorkOrderTrayForPdfDTO dto = new WorkOrderTrayForPdfDTO();
			if (woList != null && !woList.isEmpty()) {
				WorkOrder workOrder = woList.get(0);
				Long woId = workOrder.getId();
				/**
				 * Se le asignan los valores al la respuesta
				 * */

				/* SECCION PARA AGREGAR EL NOMBRE DEL CLIENTE */
				if (workOrder.getCustomer() != null) {
					CustomerDTO customer = new CustomerDTO();
					customer.setFirstNames(workOrder.getCustomer()
							.getFirstName() != null ? workOrder.getCustomer()
							.getFirstName() : null);
					customer.setLastNames(workOrder.getCustomer().getLastName() != null ? workOrder
							.getCustomer().getLastName() : null);
					customer.setAddress((workOrder.getCustomerAddress()
							.getStreetName() != null ? workOrder
							.getCustomerAddress().getStreetName() : "")
							+ " "
							+ (workOrder.getCustomerAddress()
									.getStreetNrFirst() != null ? workOrder
									.getCustomerAddress().getStreetNrFirst()
									: "")
							+ " "
							+ (workOrder.getCustomerAddress().getStreetNrLast() != null ? workOrder
									.getCustomerAddress().getStreetNrLast()
									: "")
							+ " "
							+ (workOrder.getCustomerAddress().getStreetSufix() != null ? workOrder
									.getCustomerAddress().getStreetSufix() : "")
							+ " "
							+ (workOrder.getCustomerAddress().getNeighborhood() != null ? workOrder
									.getCustomerAddress().getNeighborhood()
									: "")
							+ " "
							+ (workOrder.getCustomerAddress()
									.getExtraIndications() != null ? workOrder
									.getCustomerAddress().getExtraIndications()
									: ""));
					customer.setCustomerCode(workOrder.getCustomer()
							.getCustomerCode() != null ? workOrder
							.getCustomer().getCustomerCode() : null);
					customer.setDocumentNumber(workOrder.getCustomer()
							.getDocumentNumber() != null ? workOrder
							.getCustomer().getDocumentNumber() : null);
					customer.setType(UtilsBusiness.copyObject(
							CustomerClassTypeVO.class, workOrder.getCustomer()
									.getCustType()));
					customer.setDocumentType(documentTypesCRUD
							.getDocumentTypesByID(workOrder.getCustomer()
									.getDocumentTypeId()));
					customer.setCustomerMediaContacts(UtilsBusiness.convertList(
							customerMediaContactDAO
									.getCustomerMediaContactByCustomerId(workOrder
											.getCustomer().getId()),
							CustomerMediaContactVO.class));
					customer.setLegalRepresentative(workOrder.getCustomer()
							.getLegalRepresentative());
					customer.setIsMigrated(workOrder.getCustomer()
							.getIsMigrated());
					if (customer.getIsMigrated() != null
							&& customer
									.getIsMigrated()
									.equals(CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_MIGRATED
											.getCodeEntity())) {
						customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_MIGRATED);
					} else {
						customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_NOT_MIGRATED);
					}
					/* Paquete de programación del cliente */
					customer.setProduct(workOrder.getCustomer().getProduct());
					dto.setCustomerDTO(customer);
				}

				/*
				 * SECCION PARA DARLE VALOR AL CODIGO POSTAL Y LA DIRECCION DE
				 * LA WO
				 */
				dto.setPostalCode(UtilsBusiness.copyObject(PostalCodeVO.class,
						workOrder.getPostalCode()));
				dto.setAddress(workOrder.getCustomer().getCustomeraddress());

				/* SECCION PARA DARLE VALOR A INFORMACION DE LA WO */
				dto.setWorkorderCode(workOrder.getWoCode());
				dto.setCreationDate(workOrder.getCreationDate() != null ? workOrder
						.getCreationDate() : null);
				dto.setWoId(woId);
				dto.setImportDate(workOrder.getImportDate());

				StringBuffer sbDescription = new StringBuffer("");
				String sDescripcion = "";
				List<WorkorderDescriptionDTO> workOrderDescriptions = new ArrayList<WorkorderDescriptionDTO>();
				for (WorkOrder workOrder2 : woList) {
					sDescripcion = workOrder2.getWoDescription() != null ? workOrder2
							.getWoDescription() : null;
					if (sDescripcion != null) {
						sbDescription.insert(0, sDescripcion + " - ");
					}
				}
				sbDescription.deleteCharAt(sbDescription.lastIndexOf("-"));
				workOrderDescriptions.add(0, new WorkorderDescriptionDTO(
						sbDescription.toString()));
				dto.setWorkOrderDescription(sbDescription.toString());
				dto.setWorkOrderDescriptions(workOrderDescriptions);

				/* SECCION PARA ASIGNAR DATOS DEL SERVICIO */
				dto.setServiceDTO(this.getServiceDTOForPdf(woList));
				// En todos los servicios se consultan los elementos del cliente
				boolean isService = false;
				if (dto.getServiceDTO() != null
						&& !dto.getServiceDTO().isEmpty()) {
					for (PdfServiceDTO service : dto.getServiceDTO()) {
						if (service
								.getServiceType()
								.getServiceTypeCode()
								.equals(CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE
										.getCodeEntity())) {
							isService = true;
						}
					}
				}

				this.getCustomerResourcesFromIbs(dto.getCustomerDTO()
						.getCustomerCode(), dto, countryId);

				/* agrega la cantidad de wo's en estado finalizado */
				Long last90WosFinishedQuantity = this.workOrderDAO
						.getLast90DaysWosByCustomerAndServiceTypeStatusInFinishedStatus(workOrder
								.getCustomer().getId());
				if ((last90WosFinishedQuantity == null || last90WosFinishedQuantity
						.longValue() <= 0) && !isService)
					dto.setLast90WosFinishedQuantity(null);
				else if ((last90WosFinishedQuantity == null || last90WosFinishedQuantity
						.longValue() <= 0) && isService)
					dto.setLast90WosFinishedQuantity(0L);
				else
					dto.setLast90WosFinishedQuantity(last90WosFinishedQuantity);

				/* SECCION PARA DARLE VALOR A LOS DATOS DE LA WO EN OPTIMUS */

				if (workOrder.getOptimusStatus() != null) {
					dto.setOptimusStatus(workOrder.getOptimusStatus()
							.getDescription());
					dto.setOptimusDate(workOrder.getOptimusStatusDate());
				}

				/*
				 * SECCION PARA DARLE VALOR AL COMENTARIO DE AGENDAMIENTO -
				 * REAGENDAMIENTO
				 */
				dto.setAgendaComments(this.fillAgendaComment(workOrder));

				/*
				 * SECCION PARA DARLE VALOR A INFORMACION DE AGENDAMIENTO Y
				 * JORNADA
				 */
				WorkOrderAgenda workOrderagenda = workOrderAgendaDAO
						.getLastWorkOrderAgendaByWoID(woId);
				if (workOrderagenda != null) {
					dto.setAgendationDate(workOrderagenda.getAgendationDate() != null ? workOrderagenda
							.getAgendationDate() : null);
					if (workOrderagenda.getServiceHour() != null)
						dto.setServiceHourVO(UtilsBusiness.copyObject(
								ServiceHourVO.class,
								workOrderagenda.getServiceHour()));
				}
				/* FECHA DE ATENCION */
				dto.setAttentionDate(workOrder.getWoRealizationDate() != null ? workOrder
						.getWoRealizationDate() : null);
				/* FECHA DE FINALIZACION */
				dto.setFinalizationDate(workOrder.getFinalizationDate() != null ? workOrder
						.getFinalizationDate() : null);

				/* SECCION para agregar informacion adicional de dealer */
				WoAssignment woAssignment = this.woAssignmentDAO
						.getLastDealerAssignmentByWoId(woId);
				if (woAssignment != null) {
					dto.setAssignmentDate(woAssignment
							.getDealerAssignmentDate());
					if (woAssignment.getDealerId() != null
							&& woAssignment.getDealerId().longValue() > 0) {
						Dealer assignDealer = this.dealersDAO
								.getDealerByID(woAssignment.getDealerId());
						// Caso que la compania es principal
						if (assignDealer.getDealer() == null) {
							dto.setDealerCode(assignDealer.getDealerCode());
							dto.setDealerName(assignDealer.getDealerName());
							dto.setDealerId(woAssignment.getDealerId());
							dto.setDealerDepotCode(assignDealer.getDepotCode());
							dto.setDealerType(assignDealer.getDealerType()
									.getDealerTypeCode());

							dto.setBranchCode(assignDealer.getDealerCode());
							dto.setBranchId(woAssignment.getDealerId());
							dto.setBranchName(assignDealer.getDealerName());
							dto.setBranchType(assignDealer.getDealerType()
									.getDealerTypeCode());
							dto.setBranchDepotCode(assignDealer.getDepotCode());
						} else {
							dto.setDealerCode(assignDealer.getDealer()
									.getDealerCode());
							dto.setDealerName(assignDealer.getDealer()
									.getDealerName());
							dto.setDealerId(assignDealer.getDealer().getId());
							dto.setDealerDepotCode(assignDealer.getDealer()
									.getDepotCode());
							dto.setDealerType(assignDealer.getDealer()
									.getDealerType().getDealerTypeCode());

							dto.setBranchCode(assignDealer.getDealerCode());
							dto.setBranchId(woAssignment.getDealerId());
							dto.setBranchName(assignDealer.getDealerName());
							dto.setBranchType(assignDealer.getDealerType()
									.getDealerTypeCode());
							dto.setBranchDepotCode(assignDealer.getDepotCode());
						}
					}
				}
				/* SECCION para agregar informacion adicional de dealer */
				WoCrewAssigments woCrewAssigment = this.woCrewAssigmentsDAO
						.getLastWoCrewAssigments(woId);
				if (woCrewAssigment != null) {
					Employee employee = employeesCrewDAO
							.getEmployeeResponsibleByCrewId(woCrewAssigment
									.getCrewId().getId());
					if (employee != null) {
						dto.setResponsableName(employee.getFirstName() + " "
								+ employee.getLastName());
						dto.setResponsableDocumentNumber(employee
								.getDocumentNumber());
						EmployeeMediaContact emc = this.employeeMediaContactDAO
								.getEmployeeMediaContactByEmployeeIdAndMediaContCode(
										employee.getId(),
										CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE
												.getCodeEntity());
						if (emc != null)
							dto.setResponsableMobileNumber(emc
									.getMediaContactValue());
						EmployeeVehicle ev = this.employeeVehicleDAO
								.getEmployeeVehicleByEmployeeId(employee
										.getId());
						if (ev != null)
							dto.setPlate(ev.getVehicle().getPlate());
					}
					// Consulta los empleados que no son responsables
					List<Employee> employeesNotResponsable = employeesCrewDAO
							.getEmployeeNotResponsibleByCrewId(woCrewAssigment
									.getCrewId().getId());
					if (employeesNotResponsable != null
							&& !employeesNotResponsable.isEmpty()) {
						dto.setResponsableHelperName(employeesNotResponsable
								.get(0).getFirstName()
								+ " "
								+ employeesNotResponsable.get(0).getLastName());
						dto.setResponsableHelperDocumentNumber(employeesNotResponsable
								.get(0).getDocumentNumber());
					}
				}

				/* AGREGA INFORMACION DEL VENDEDOR */
				if (workOrder.getSaleDealer() != null) {
					dto.setSaleDealerCode(workOrder.getSaleDealer()
							.getDealerCode());
					dto.setSaleDealerName(workOrder.getSaleDealer()
							.getDealerName());
					dto.setSaleDealerMediaContacts(this.dealersMediaContactCRUDBean
							.fillDealerMediaContacts(workOrder.getSaleDealer()
									.getId()));
				}

				/*
				 * SECCION PARA AGREGAR INFORMACION DE ULTIMAS WORKORDERS EN
				 * ESTADO ATENTIDO ASIGNADAS AL CLIENTE
				 */
				dto.setLastServices(this.getLastCustomerServices(dto
						.getCustomerDTO().getCustomerCode()));
				if (dto.getLastServices() != null
						&& dto.getLastServices().size() == 1) {
					dto.setFirstService(dto.getLastServices().get(0));
					dto.setTechnicianObservations(dto.getLastServices().get(0)
							.getTechnicianObservations());
				} else if (dto.getLastServices() != null
						&& dto.getLastServices().size() > 1) {
					dto.setFirstService(dto.getLastServices().get(1));
					dto.setSecondService(dto.getLastServices().get(0));
					dto.setTechnicianObservations(dto.getLastServices().get(0)
							.getTechnicianObservations());
				}

				/* AGREGA LA INFORMACION DEL DEALER DUMMY */
				boolean putNullvalues = false;
				SystemParameter dealerDummyParam = this.systemParameterDao
						.getSysParamByCodeAndCountryId(
								CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_DUMMY_CODE
										.getCodeEntity(), workOrder
										.getCountry().getId());
				if (dealerDummyParam != null
						&& dealerDummyParam.getValue() != null) {
					Long dealerDummyCodeLong = Long.parseLong(dealerDummyParam
							.getValue());
					Dealer dealer = this.dealersDAO
							.getDealerByDealerCodeAndCountryId(
									dealerDummyCodeLong, workOrder.getCountry()
											.getId());
					if (dealer != null) {
						dto.setDummyDealerAddress(dealer.getAddress());
						dto.setDummyDealerCity(dealer.getPostalCode().getCity()
								.getCityName()
								+ " - "
								+ dealer.getPostalCode().getCity().getState()
										.getStateName());
						dto.setDummyDealerName(dealer.getDealerName());
						dto.setDummyTelephone(null);
					} else {
						putNullvalues = true;
					}
				} else {
					putNullvalues = true;
				}
				if (putNullvalues) {
					dto.setDummyDealerAddress(null);
					dto.setDummyDealerCity(null);
					dto.setDummyDealerName(null);
					dto.setDummyTelephone(null);
				}
			}
			this.extractContractInformation(dto);
			return dto;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la tarea fillWorkOrderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina fillWorkOrderDetailForPdf/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Obtiene los valores necesarios para poblar el formulario de
	 * contrato con el cliente
	 * 
	 * @param dto
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private void extractContractInformation(WorkOrderTrayForPdfDTO dto)
			throws DAOServiceException, DAOSQLException, BusinessException,
			PropertiesException {
		Map<String, String> contractItems = new HashMap<String, String>();

		if (dto.getCustomerDTO() != null) {
			contractItems.put("customer_name", dto.getCustomerDTO()
					.getFirstNames()
					+ " "
					+ dto.getCustomerDTO().getFirstNames());
			contractItems.put("customer_contact_person", dto.getCustomerDTO()
					.getLegalRepresentative());
			contractItems.put("contact_person_relationship", "");

			if (dto.getCustomerDTO().getCustomerMediaContacts() != null
					&& !dto.getCustomerDTO().getCustomerMediaContacts()
							.isEmpty()) {
				String phoneMediaContactCode = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE
						.getCodeEntity();
				String emailMediaContactCode = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE
						.getCodeEntity();
				for (CustomerMediaContactVO vo : dto.getCustomerDTO()
						.getCustomerMediaContacts()) {
					if (vo.getMediaContactType().getMediaCode()
							.equalsIgnoreCase(phoneMediaContactCode)) {
						contractItems.put("contact_person_phone",
								vo.getMediaContactValue());
					} else if (vo.getMediaContactType().getMediaCode()
							.equalsIgnoreCase(emailMediaContactCode)) {
						contractItems.put("customer_email",
								vo.getMediaContactValue());
					}
				}
			}

			contractItems.put("cust_born_date_day", "");
			contractItems.put("cust_born_date_month", "");
			contractItems.put("cust_born_date_year", "");
			contractItems.put("how_does_cust_know", "");

			if (dto.getCustomerDTO()
					.getDocumentType()
					.getDocumentTypeCode()
					.equalsIgnoreCase(
							CodesBusinessEntityEnum.DOCUMENT_TYPE_CC
									.getCodeEntity())) {
				contractItems.put("cust_doc_type_cc", "Yes");
			} else if (dto
					.getCustomerDTO()
					.getDocumentType()
					.getDocumentTypeCode()
					.equalsIgnoreCase(
							CodesBusinessEntityEnum.DOCUMENT_TYPE_NIT
									.getCodeEntity())) {
				contractItems.put("cust_doc_type_nit", "Yes");
			}

			contractItems.put("customer_id_number", dto.getCustomerDTO()
					.getDocumentNumber());
			contractItems.put("customer_doc_expedition_loc", "");
			contractItems.put("cust_commercial_activity", "");
			contractItems.put("cust_enterprise_name", "");
			contractItems.put("cust_commercial_address", dto.getCustomerDTO()
					.getAddress());
			contractItems.put("cust_phone_number", "");
		}

		dto.setContractInformation(contractItems);
	}

	/**
	 * 
	 * Metodo: Obtiene el ultimo comentario del historial de contactos
	 * 
	 * @param woId
	 * @param woStatusIds
	 * @return
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author
	 */
	private String fillAgendaComment(WorkOrder wo) throws BusinessException {
		log.debug("== Inicia fillAgendaComment/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<Long> woStatusIds = new ArrayList<Long>();
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds
					.add(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
							.getIdEntity(WorkorderStatus.class.getName()));

			StringBuffer agendaComment = new StringBuffer("");
			WoStatusHistory woStatusHistory = null;
			if (woStatusIds != null && !woStatusIds.isEmpty()) {
				for (int i = 0; i < woStatusIds.size(); i++) {
					Long woStatusId = woStatusIds.get(i);
					WoStatusHistory temp = woStatusHistoryDAO
							.getWoStatusHistoryByWoIdDescNotNull(wo.getId(),
									woStatusId);
					if ((woStatusHistory == null || woStatusHistory
							.getStatusDate() == null)
							&& temp != null
							&& temp.getStatusDate() != null) {
						woStatusHistory = UtilsBusiness.copyObject(
								WoStatusHistory.class, temp);
					} else if (woStatusHistory != null
							&& woStatusHistory.getStatusDate() != null
							&& temp != null
							&& temp.getStatusDate() != null
							&& woStatusHistory.getStatusDate().before(
									temp.getStatusDate())) {
						woStatusHistory = UtilsBusiness.copyObject(
								WoStatusHistory.class, temp);
					}
				}
			}
			if (woStatusHistory != null
					&& woStatusHistory.getDescription() != null) {
				agendaComment.append(woStatusHistory.getDescription());
			}

			// agrego los intentos de contacto
			agendaComment.append(" - ");
			List<Contact> contacts = contactDAO.getContactsTriesByWorkOrder(wo
					.getWoCode());
			for (Contact contact : contacts) {
				agendaComment.append(contact.getProblemDescription());
				agendaComment.append(" (");
				agendaComment.append(ApplicationTextEnum.TRY_TO_CONTACT
						.getApplicationTextValue());
				agendaComment.append(") - ");
			}
			agendaComment.deleteCharAt(agendaComment.lastIndexOf("-"));

			return agendaComment.toString();
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación fillAgendaComment/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina fillAgendaComment/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Pone las lista de elementos de cliente en vacio para que se
	 * impriman cuando no es de tipo mudanza
	 * 
	 * @param dto
	 *            <tipo> <descripcion>
	 * @author jnova
	 */
	/*private void fillEmptyCustomerResources(WorkOrderTrayForPdfDTO dto) {
		List<CustomerResourcesDTO> list = new ArrayList<CustomerResourcesDTO>();
		for (int i = 0; i < 3; i++) {
			CustomerResourcesDTO obj = new CustomerResourcesDTO();
			obj.setDeviceModelName("");
			obj.setSerialNumber("");
			list.add(obj);
		}
		dto.setCustomerResourcesLeft(list);
		dto.setCustomerResourcesRight(list);
	}*/

	/**
	 * 
	 * Metodo: Hace el llamado al servicio de cliente para obtener los elementos
	 * de este y vincularlos entre si
	 * 
	 * @param customerCode
	 *            codigo del cliente
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private void getCustomerResourcesFromIbs(String customerCode,
			WorkOrderTrayForPdfDTO dto, Long countryId)
			throws BusinessException {
		log.debug("== Inicia getCustomerResourcesFromIbs/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<CustomerResourcesDTO> response = null;
			Technology technology = technologyDAO
					.getTechnologyByCode(CodesBusinessEntityEnum.TECHNOLOGY_SC
							.getCodeEntity());
			if (technology != null && technology.getIbsCode() != null) {
				/*
				 * Se valida que el countryid NO venga nulo antes de consultar
				 */
				if (countryId == null) {
					List<String> params = new ArrayList<String>();
					params.add("");
					throw new BusinessException(
							ErrorBusinessMessages.CORE_CR065.getCode(),
							ErrorBusinessMessages.CORE_CR065.getMessage(),
							params);
				}
				Country country = countryDao.getCountriesByID(countryId);
				if (country == null || country.getCountryCode() == null) {
					List<String> params = new ArrayList<String>();
					params.add(countryId.toString());
					throw new BusinessException(
							ErrorBusinessMessages.CORE_CR065.getCode(),
							ErrorBusinessMessages.CORE_CR065.getMessage(),
							params);
				}
				List<CustomerResourcesDTO> serviceResponse = this.customerServiceBroker
						.getActiveCustomerResourcesFromIBSIntoSDModel(
								customerCode, country.getCountryCode());
				if (serviceResponse != null && !serviceResponse.isEmpty()) {
					response = new ArrayList<CustomerResourcesDTO>();
					for (int i = 0; i < serviceResponse.size(); i++) {
						CustomerResourcesDTO decoAuxDto = serviceResponse
								.get(i);
						// Si no es una SC
						if (!decoAuxDto.getTechnicalProdcutId().equals(
								Integer.valueOf(technology.getIbsCode()))) {
							response.add(decoAuxDto);
							serviceResponse.remove(i);
							i = 0;
						} else {
							continue;
						}
					}
				}
				if (response != null && !response.isEmpty()) {
					for (CustomerResourcesDTO deco : response) {
						for (int i = 0; i < serviceResponse.size(); i++) {
							CustomerResourcesDTO sc = serviceResponse.get(i);
							if (sc.getProductId().equals(deco.getProductId())) {
								deco.setLinkedResource(sc);
								serviceResponse.remove(i);
								i = 0;
							}
						}
					}
				}
			}
			// Se llenan las dos listas de elementos
			if (response == null || response.isEmpty()) {
				response = new ArrayList<CustomerResourcesDTO>();
				for (int i = 0; i < 3; i++) {
					CustomerResourcesDTO obj = new CustomerResourcesDTO();
					obj.setDeviceModelName("");
					obj.setSerialNumber("");
					response.add(obj);
				}
				dto.setCustomerResourcesLeft(response);
				dto.setCustomerResourcesRight(response);
				// En el caso que la la cantidad de elementos sea inferior a 3
			} else if (response != null && response.size() < 3) {
				for (int i = response.size(); i < 3; i++) {
					CustomerResourcesDTO obj = new CustomerResourcesDTO();
					obj.setDeviceModelName("");
					obj.setSerialNumber("");
					response.add(obj);
				}
				dto.setCustomerResourcesLeft(response);
				List<CustomerResourcesDTO> right = new ArrayList<CustomerResourcesDTO>();
				for (int i = 0; i < 3; i++) {
					CustomerResourcesDTO obj = new CustomerResourcesDTO();
					obj.setDeviceModelName("");
					obj.setSerialNumber("");
					right.add(obj);
				}
				dto.setCustomerResourcesRight(right);
				// En el caso que la cantidad de elementos sea mayor a 3
			} else if (response != null && response.size() >= 3) {
				List<CustomerResourcesDTO> right = new ArrayList<CustomerResourcesDTO>();
				for (int i = 3; i < response.size(); i++) {
					CustomerResourcesDTO obj = response.get(i);
					right.add(obj);
				}
				dto.setCustomerResourcesLeft(response);
				dto.setCustomerResourcesRight(right);
			}

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la tarea getCustomerResourcesFromIbs/TrayWorkOrderManagmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerResourcesFromIbs/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Consulta las ultimas WO es estado finalizado de un cliente
	 * 
	 * @param customerIbs
	 *            Codigo ib del cliente del cual se estan consultan las WO
	 * @return Lista de objetos para mostrar en el pdf
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private List<PdfServiceDTO> getLastCustomerServices(String customerIbs)
			throws BusinessException {
		log.debug("== Inicia getLastCustomerServices/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<PdfServiceDTO> response = null;
			List<WoAssignment> lastWoAssignments = this.woAssignmentDAO
					.getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate(customerIbs);
			if (lastWoAssignments != null && !lastWoAssignments.isEmpty()) {
				response = new ArrayList<PdfServiceDTO>();
				for (WoAssignment assignment : lastWoAssignments) {
					PdfServiceDTO dto = new PdfServiceDTO();
					List<Service> services = this.woServiceDAO
							.getServicesOfWorkOrderServiceByWorkorderId(assignment
									.getWorkOrder().getId());
					if (services != null && !services.isEmpty())
						dto.setServiceName(services.get(0).getServiceName());
					dto.setWoCode(assignment.getWorkOrder().getWoCode());
					dto.setFinalizationDate(assignment.getWorkOrder()
							.getFinalizationDate());
					if (assignment.getCrewId() != null) {
						Employee crewReaponsable = employeesCrewDAO
								.getEmployeeResponsibleByCrewId(assignment
										.getCrewId());
						if (crewReaponsable != null)
							dto.setTechnicianName(crewReaponsable
									.getFirstName()
									+ " "
									+ crewReaponsable.getLastName());
					}
					dto.setTechnicianObservations(assignment.getWorkOrder()
							.getWoDescription());
					dto.setWoAction(assignment.getWorkOrder().getWoAction());
					response.add(dto);
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la tarea getLastCustomerServices/TrayWorkOrderManagmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getLastCustomerServices/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Obtiene la informacion del servicio de la WO para visualizarlo en
	 * el pd
	 * 
	 * @param woId
	 *            identificador de la WO
	 * @return PdfServiceDTO informacion del servicio
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private List<PdfServiceDTO> getServiceDTOForPdf(List<WorkOrder> woList)
			throws BusinessException {
		log.debug("== Inicia getServiceDTOForPdf/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<PdfServiceDTO> response = null;
			if (woList != null && !woList.isEmpty()) {
				response = new ArrayList<PdfServiceDTO>();
				for (WorkOrder workorder : woList) {
					List<WorkOrderServiceVO> woServices = this.trayWorkOrderManagmentBusiness
							.getWorkOrderServices(workorder.getId(), null)
							.getWorkOrderServicesVO();
					if (woServices != null && !woServices.isEmpty()) {
						for (WorkOrderServiceVO service : woServices) {
							PdfServiceDTO dto = new PdfServiceDTO();
							dto.setWoCode(workorder.getWoCode());
							dto.setServiceName(service.getService()
									.getServiceName());
							dto.setSerialNumber(service.getSerialNumber());
							dto.setLinkedSerialNumber(service
									.getLinkedSerialNumber());
							if (service.getService().getServiceCategory()
									.getServiceType() != null) {
								dto.setServiceType(UtilsBusiness.copyObject(
										ServiceTypeVO.class, service
												.getService()
												.getServiceCategory()
												.getServiceType()));
								// Se verifica que se de instalacion para buscar
								// la shipping order
								if (service
										.getService()
										.getServiceCategory()
										.getServiceType()
										.getServiceTypeCode()
										.equals(CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL
												.getCodeEntity())) {
									List<ShippingOrder> shippingOrderList = shippingOrderDAO
											.getShippingOrdersByWorkOrder(workorder
													.getId());
									if (shippingOrderList != null
											&& !shippingOrderList.isEmpty()) {
										dto.setShippingOrderCode(shippingOrderList
												.get(0).getShippingOrderCode() != null ? shippingOrderList
												.get(0).getShippingOrderCode()
												: null);
										List<TechnologyVO> technologies = this.technologyBusinessBean
												.getShippingorderTechnology(shippingOrderList
														.get(0).getId());
										if (technologies != null
												&& !technologies.isEmpty()) {
											dto.setTechnology(technologies
													.get(0));
										}
									}
								}
							}
							response.add(dto);
						}
					}
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la tarea getServiceDTOForPdf/TrayWorkOrderManagmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getServiceDTOForPdf/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.
	 * TrayWorkOrderManagmentBusinessLocal
	 * #getWorkOrdersForReport(co.com.directv.
	 * sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReport(
			WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean ==");
		try {
			if (filter == null || filter.getCountryId() == null
					|| filter.getCountryId() <= 0L) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			List<String> processSourceCodes = new ArrayList<String>();
			if (filter.isScheduledCSR()) {
				for (Long woStatusId : filter.getWoStatusIds()) {
					WorkorderStatus scheduleStatus = workOrderStatusDAO
							.getWorkorderStatusByID(woStatusId);
					if (scheduleStatus != null
							&& scheduleStatus
									.getWoStateCode()
									.equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
											.getCodeEntity())) {
						processSourceCodes
								.add(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_AG
										.getCodeEntity());
					} else { // Es RESCHEDULED
						processSourceCodes
								.add(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_REAG
										.getCodeEntity());
					}
				}
				filter.setProcessSourceCodes(processSourceCodes);
			}

			// En caso que no tenga filtro por estado, filtra por los estados
			// permitidos en esta bandeja
			if (filter.getWoStatusIds() == null
					|| filter.getWoStatusIds().isEmpty()) {
				WorkorderStatus asignStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN
								.getCodeEntity());
				WorkorderStatus reAsignStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN
								.getCodeEntity());
				WorkorderStatus scheduleStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
								.getCodeEntity());
				WorkorderStatus reScheduleStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
								.getCodeEntity());
				WorkorderStatus realizedStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
								.getCodeEntity());
				WorkorderStatus pendingStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING
								.getCodeEntity());
				List<Long> woStatusIds = new ArrayList<Long>();
				if (asignStatus != null)
					woStatusIds.add(asignStatus.getId());
				if (reAsignStatus != null)
					woStatusIds.add(reAsignStatus.getId());
				if (scheduleStatus != null)
					woStatusIds.add(scheduleStatus.getId());
				if (reScheduleStatus != null)
					woStatusIds.add(reScheduleStatus.getId());
				if (realizedStatus != null)
					woStatusIds.add(realizedStatus.getId());
				if (pendingStatus != null)
					woStatusIds.add(pendingStatus.getId());
				filter.setWoStatusIds(woStatusIds);
			}

			// Validacion de rangos de fechas
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoAgendationDateInit(),
					filter.getWoAgendationDateEnd(), 2);
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoAttentionDateInit(),
					filter.getWoAttentionDateEnd(), 2);
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoCreationDateInit(),
					filter.getWoCreationDateEnd(), 2);
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoFinalizationDateInit(),
					filter.getWoFinalizationDateEnd(), 2);

			// Se valida que se envie una sucursal o una principal para darle
			// valor a los ids de dealers por los cuales se filtra
			boolean isDealerId = (filter.getDealersIds() == null || filter
					.getDealersIds().isEmpty()) ? false : true;
			boolean isBranchId = (filter.getBranchIds() == null || filter
					.getBranchIds().isEmpty()) ? false : true;
			if (isBranchId) {
				filter.setDealersIds(null);
			} else if (isDealerId) {
				List<Long> tempList = new ArrayList<Long>();
				for (Long dealerId : filter.getDealersIds()) {
					List<Dealer> dealers = dealersDAO
							.getDealerByBranchId(dealerId);
					if (dealers != null && !dealers.isEmpty()) {
						for (Dealer d : dealers) {
							tempList.add(d.getId());
						}
					}
				}
				filter.getDealersIds().addAll(tempList);
				filter.setBranchIds(null);
			}

			WorkOrderResponse daoResponse = null;
			if (!filter.isFromExpirationGrouping())
				daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,
						null, true);
			else
				daoResponse = workOrderDAO
						.getWorkOrdersForDealerTrayWithExpirationGrouping(
								filter, null);

			if (daoResponse.getWorkOrdersIds().size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) daoResponse.getWorkOrdersIds().size())
						.toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}

			return daoResponse;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ReportWorkOrderDTO> getReportWorkOrderDTOWorkOrdersForReport(
			WorkOrderResponse daoResponse, Long countryId,Long idUsuario)
			throws BusinessException {

		List<ReportWorkOrderDTO> response = null;
		if (daoResponse != null && daoResponse.getWorkOrdersIds() != null
				&& !daoResponse.getWorkOrdersIds().isEmpty()) {
			response = new ArrayList<ReportWorkOrderDTO>();
			List<Long> listaIDs = new ArrayList<Long>();
			for (Object[] object : daoResponse.getWorkOrdersIds()) {
				Long woId = ((BigDecimal) object[0]).longValue();
				listaIDs.add(woId);
			}

			// Envio de listado de wordorders a exportar
			response.addAll(this.fillWorkOrderInformationForReport(listaIDs,countryId,idUsuario));
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.
	 * TrayWorkOrderManagmentBusinessLocal
	 * #getWorkOrdersForReport(co.com.directv.
	 * sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReportAttentionFinalization(
			WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");

		try {
			if (filter == null || filter.getCountryId() == null
					|| filter.getCountryId() <= 0L) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			if (filter.getWoStatusIds() == null
					|| filter.getWoStatusIds().isEmpty()) {
				/*
				 * ASIGNA LOS ESTADOS PERMITIDOS EN LA BANDEJA DE ATENCION Y
				 * FINALIZACION
				 */
				WorkorderStatus scheduleStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
								.getCodeEntity());
				WorkorderStatus reScheduleStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
								.getCodeEntity());
				WorkorderStatus realizedStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
								.getCodeEntity());
				List<Long> woStatusIds = new ArrayList<Long>();
				if (scheduleStatus != null)
					woStatusIds.add(scheduleStatus.getId());
				if (reScheduleStatus != null)
					woStatusIds.add(reScheduleStatus.getId());
				if (realizedStatus != null)
					woStatusIds.add(realizedStatus.getId());
				filter.setWoStatusIds(woStatusIds);
			}
			// Se valida que se envie una sucursal o una principal para darle
			// valor a los ids de dealers por los cuales se filtra
			boolean isDealerId = (filter.getDealersIds() == null || filter
					.getDealersIds().isEmpty()) ? false : true;
			boolean isBranchId = (filter.getBranchIds() == null || filter
					.getBranchIds().isEmpty()) ? false : true;
			if (isBranchId) {
				filter.setDealersIds(null);
			} else if (isDealerId) {
				List<Long> tempList = new ArrayList<Long>();
				for (Long dealerId : filter.getDealersIds()) {
					List<Dealer> dealers = dealersDAO
							.getDealerByBranchId(dealerId);
					if (dealers != null && !dealers.isEmpty()) {
						for (Dealer d : dealers) {
							tempList.add(d.getId());
						}
					}
				}
				filter.getDealersIds().addAll(tempList);
				filter.setBranchIds(null);
			} else {
				filter.setDealersIds(new ArrayList<Long>());
				List<Dealer> dealers = dealersDAO.getAllDealersByCountryId(
						filter.getCountryId(), null, null);
				if (dealers != null && !dealers.isEmpty()) {
        			for(@SuppressWarnings("unused") Dealer d : dealers){
        				//filter.getDealersIds().add( d.getId() );
        				// en el query del reporte reemplazamos los ids por una subquery 
        				filter.getDealersIds().add( new Long(-1) ); 
        				break;
        			}
				}
				filter.setBranchIds(null);
			}

			WorkOrderResponse daoResponse = null;
			if (!filter.isFromExpirationGrouping())
				daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,
						null, false);
			else
				daoResponse = workOrderDAO
						.getWorkOrdersForDealerTrayWithExpirationGrouping(
								filter, null);

			if (daoResponse.getWorkOrdersIds().size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) daoResponse.getWorkOrdersIds().size())
						.toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}

			// List<ReportWorkOrderDTO> response = null;
			// if( daoResponse != null && daoResponse.getWorkOrdersIds() != null
			// && !daoResponse.getWorkOrdersIds().isEmpty() ){
			// response = new ArrayList<ReportWorkOrderDTO>();
			// List<Long> listaIDs = new ArrayList<Long>();
			// for( Object[] object : daoResponse.getWorkOrdersIds() ){
			// Long woId = ( (BigDecimal) object[0] ).longValue();
			// listaIDs.add(woId);
			// }
			//
			// //Envio de listado de wordorders a exportar
			// response.addAll( this.fillWorkOrderInformationForReport(
			// listaIDs,filter.getCountryId() ) );
			// }

			return daoResponse;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayReportsBusinessLocal#
	 * getWorkOrdersForReportAttentionFinalization(java.util.List,
	 * java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReportAttentionFinalization(
			List<String> woCodes, Long countryId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");

		try {
			if (woCodes == null || woCodes.isEmpty() || countryId == null
					|| countryId.longValue() <= 0) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			if (woCodes.size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) woCodes.size()).toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}
			/*
			 * CONSULTA LOS ESTADOS PERMITIDOS EN LA BANDEJA DE ATENCION Y
			 * FINALIZACION
			 */
			WorkorderStatus scheduleStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
							.getCodeEntity());
			WorkorderStatus reScheduleStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
							.getCodeEntity());
			WorkorderStatus realizedStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
							.getCodeEntity());
			List<Long> woStatusIds = new ArrayList<Long>();
			if (scheduleStatus != null)
				woStatusIds.add(scheduleStatus.getId());
			if (reScheduleStatus != null)
				woStatusIds.add(reScheduleStatus.getId());
			if (realizedStatus != null)
				woStatusIds.add(realizedStatus.getId());

			WorkOrderResponse daoResponse = new WorkOrderResponse();
			List<Object[]> workOrdersIds = new ArrayList<Object[]>();
			daoResponse.setWorkOrdersIds(workOrdersIds);

			for (String woCode : woCodes) {
				WorkOrderFilterTrayDTO filter = new WorkOrderFilterTrayDTO();
				filter.setWoCode(woCode);
				filter.setCountryId(countryId);
				filter.setWoStatusIds(woStatusIds);
				WorkOrderResponse daoResponseFor = workOrderDAO
						.getWorkOrdersForDealerTray(filter, null, false);

				if (daoResponseFor != null
						&& daoResponseFor.getWorkOrdersIds() != null
						&& !daoResponseFor.getWorkOrdersIds().isEmpty()) {
					daoResponse.getWorkOrdersIds().addAll(
							daoResponseFor.getWorkOrdersIds());
				}

				// if( daoResponse.getWorkOrdersIds() != null &&
				// !daoResponse.getWorkOrdersIds().isEmpty() ){
				// List<Long> listaIDs = new ArrayList<Long>();
				// for( Object[] object : daoResponse.getWorkOrdersIds() ){
				// Long woId = ( (BigDecimal) object[0] ).longValue();
				// listaIDs.add(woId);
				// }
				// //Envio de listado de wordorders a exportar
				// response.addAll( this.fillWorkOrderInformationForReport(
				// listaIDs,countryId ) );
				// }
			}
			return daoResponse;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.
	 * TrayWorkOrderManagmentBusinessLocal
	 * #getWorkOrdersForReport(co.com.directv.
	 * sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReportForAllocator(
			WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean ==");

		try {
			if (filter == null || filter.getCountryId() == null
					|| filter.getCountryId() <= 0L) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			// Se coloca el indicador de que el dealer tiene que ir contra
			// WorkOrders
			filter.setDealerToWO(true);

			// En caso que no tenga filtro por estado, filtra por los estados
			// permitidos en esta bandeja
			if (filter.getWoStatusIds() == null
					|| filter.getWoStatusIds().isEmpty()) {
				WorkorderStatus activeStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE
								.getCodeEntity());
				WorkorderStatus rejectedStatus = workOrderStatusDAO
						.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED
								.getCodeEntity());
				List<Long> woStatusIds = new ArrayList<Long>();
				if (activeStatus != null)
					woStatusIds.add(activeStatus.getId());
				if (rejectedStatus != null)
					woStatusIds.add(rejectedStatus.getId());
				filter.setWoStatusIds(woStatusIds);
			}
			// Validacion de rangos de fechas
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoCreationDateInit(),
					filter.getWoCreationDateEnd(), 2);
			this.trayWorkOrderManagmentBusiness.validateTrayDates(
					filter.getWoCancelationDateInit(),
					filter.getWoCancelationDateEnd(), 2);

			this.fillDealerAndBranchIds(filter);

			WorkOrderResponse daoResponse = null;
			if (!filter.isFromExpirationGrouping())
				daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,
						null, true);
			else
				daoResponse = workOrderDAO
						.getWorkOrdersForDealerTrayWithExpirationGrouping(
								filter, null);

			if (daoResponse.getWorkOrdersIds().size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) daoResponse.getWorkOrdersIds().size())
						.toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}

			// List<ReportWorkOrderDTO> response = null;
			// if( daoResponse != null && daoResponse.getWorkOrdersIds() != null
			// && !daoResponse.getWorkOrdersIds().isEmpty() ){
			// response = new ArrayList<ReportWorkOrderDTO>();
			// List<Long> listaIDs = new ArrayList<Long>();
			// for( Object[] object : daoResponse.getWorkOrdersIds() ){
			// Long woId = ( (BigDecimal) object[0] ).longValue();
			// listaIDs.add(woId);
			// }
			//
			// //Envio de listado de wordorders a exportar
			// response.addAll( this.fillWorkOrderInformationForReport(
			// listaIDs,filter.getCountryId() ) );
			// }

			return daoResponse;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayReportsBusinessLocal#
	 * getWorkOrdersForReportForAllocator(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReportForAllocator(
			List<String> woCodes, Long countryId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean ==");

		try {
			if (woCodes == null || woCodes.isEmpty() || countryId == null
					|| countryId.longValue() <= 0) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			if (woCodes.size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) woCodes.size()).toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}
			/* CONSULTA LOS ESTADOS VALIDOS PARA LA BANDEJA MANUAL */
			WorkorderStatus activeStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE
							.getCodeEntity());
			WorkorderStatus rejectedStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED
							.getCodeEntity());
			List<Long> woStatusIds = new ArrayList<Long>();
			if (activeStatus != null)
				woStatusIds.add(activeStatus.getId());
			if (rejectedStatus != null)
				woStatusIds.add(rejectedStatus.getId());

			WorkOrderResponse daoResponse = new WorkOrderResponse();
			List<Object[]> workOrdersIds = new ArrayList<Object[]>();
			daoResponse.setWorkOrdersIds(workOrdersIds);

			for (String woCode : woCodes) {
				WorkOrderFilterTrayDTO filter = new WorkOrderFilterTrayDTO();
				filter.setWoCode(woCode);
				filter.setCountryId(countryId);
				filter.setWoStatusIds(woStatusIds);
				// Se coloca el indicador de que el dealer tiene que ir contra
				// WorkOrders
				filter.setDealerToWO(true);
				WorkOrderResponse daoResponseFor = null;
				if (!filter.isFromExpirationGrouping())
					daoResponseFor = workOrderDAO.getWorkOrdersForDealerTray(
							filter, null, false);
				else
					daoResponseFor = workOrderDAO
							.getWorkOrdersForDealerTrayWithExpirationGrouping(
									filter, null);

				if (daoResponseFor != null
						&& daoResponseFor.getWorkOrdersIds() != null
						&& !daoResponseFor.getWorkOrdersIds().isEmpty()) {
					daoResponse.getWorkOrdersIds().addAll(
							daoResponseFor.getWorkOrdersIds());
				}

				// if( daoResponse != null && daoResponse.getWorkOrdersIds() !=
				// null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				// List<Long> listaIDs = new ArrayList<Long>();
				// for( Object[] object : daoResponse.getWorkOrdersIds() ){
				// Long woId = ( (BigDecimal) object[0] ).longValue();
				// listaIDs.add(woId);
				// }
				// //Envio de listado de wordorders a exportar
				// response.addAll( this.fillWorkOrderInformationForReport(
				// listaIDs,countryId ) );
				// }
			}
			return daoResponse;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReportForAllocator/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.ejb.business.core.tray.
	 * TrayWorkOrderManagmentBusinessLocal
	 * #getWorkOrdersForReport(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderResponse getWorkOrdersForReport(List<String> woCodes,
			Long countryId, Long... userId) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean ==");

		try {
			if (woCodes.size() > Integer
					.parseInt(CodesBusinessEntityEnum.MAX_ROWS_EXPORT
							.getCodeEntity())) {
				Object[] params = new Object[2];
				params[0] = CodesBusinessEntityEnum.MAX_ROWS_EXPORT
						.getCodeEntity();
				params[1] = ((Integer) woCodes.size()).toString();
				throw new BusinessException(
						ErrorBusinessMessages.CORE_CR058.getCode(),
						ErrorBusinessMessages.CORE_CR058.getMessage(params),
						UtilsBusiness.getParametersWS(params));
			}
			if (woCodes == null || woCodes.isEmpty() || countryId == null
					|| countryId.longValue() <= 0) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			WorkorderStatus asignStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN
							.getCodeEntity());
			WorkorderStatus reAsignStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN
							.getCodeEntity());
			WorkorderStatus scheduleStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
							.getCodeEntity());
			WorkorderStatus reScheduleStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
							.getCodeEntity());
			WorkorderStatus realizedStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
							.getCodeEntity());
			WorkorderStatus pendingStatus = workOrderStatusDAO
					.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING
							.getCodeEntity());
			List<Long> woStatusIds = new ArrayList<Long>();
			if (asignStatus != null)
				woStatusIds.add(asignStatus.getId());
			if (reAsignStatus != null)
				woStatusIds.add(reAsignStatus.getId());
			if (scheduleStatus != null)
				woStatusIds.add(scheduleStatus.getId());
			if (reScheduleStatus != null)
				woStatusIds.add(reScheduleStatus.getId());
			if (realizedStatus != null)
				woStatusIds.add(realizedStatus.getId());
			if (pendingStatus != null)
				woStatusIds.add(pendingStatus.getId());

			WorkOrderResponse daoResponse = new WorkOrderResponse();
			List<Object[]> workOrdersIds = new ArrayList<Object[]>();
			daoResponse.setWorkOrdersIds(workOrdersIds);
			for (String woCode : woCodes) {
				WorkOrderFilterTrayDTO filter = new WorkOrderFilterTrayDTO();
				filter.setWoCode(woCode);
				filter.setCountryId(countryId);
				filter.setWoStatusIds(woStatusIds);
				if (userId != null && userId.length > 0) {
					filter.setUserId(userId[0]);
				}
				WorkOrderResponse daoResponseFor = workOrderDAO
						.getWorkOrdersForDealerTray(filter, null, false);

				if (daoResponseFor != null
						&& daoResponseFor.getWorkOrdersIds() != null
						&& !daoResponseFor.getWorkOrdersIds().isEmpty()) {
					daoResponse.getWorkOrdersIds().addAll(
							daoResponseFor.getWorkOrdersIds());
				}

				// if( daoResponse.getWorkOrdersIds() != null &&
				// !daoResponse.getWorkOrdersIds().isEmpty() ){
				// List<Long> listaIDs = new ArrayList<Long>();
				// for( Object[] object : daoResponse.getWorkOrdersIds() ){
				// Long woId = ( (BigDecimal) object[0] ).longValue();
				// listaIDs.add(woId);
				// }
				// //Envio de listado de wordorders a exportar
				// response.addAll( this.fillWorkOrderInformationForReport(
				// listaIDs,countryId ) );
				// }
			}
			return daoResponse;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForReport/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Obtiene la informacion para el reporte a partir del id de una WO
	 * 
	 * @param woId
	 * @return ReportWorkOrderDTO objeto con informacion para generar excel
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author aquevedo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ReportWorkOrderDTO> fillWorkOrderInformationForReport(
			List<Long> woIDs, Long countryId, Long idUsuario) throws BusinessException {
		log.debug("== Inicia fillWorkOrderInformationForReport/TrayWorkOrderManagmentBusinessBean ==");

		try {

			boolean isViewCustomerDocument = true;
			boolean isViewCustomerMail = true;

			List<ReportWorkOrderDTO> response = new ArrayList<ReportWorkOrderDTO>();
			Date sysdate = UtilsBusiness.getDateLastChangeOfUser(countryId,
					userDao, systemParameterDao);
			// Iterator<Object[]> results =
			// this.workOrderDAO.getWorkOrderByIDReport(woIDs,sysdate,
			// countryId).iterator();
			/*
			 * tmp = ""; if(row[4]!=null){ tmp = row[4].toString(); }
			 * dto.setCustomerTypeName(tmp);
			 *//* CUSTOMER_TYPE_NAME */
			Timestamp aSysdate = new Timestamp(sysdate.getTime());
			String activeStatus = CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE
					.getCodeEntity();
			String anIsResponsible = CodesBusinessEntityEnum.BOOLEAN_TRUE
					.getCodeEntity();
			String anIsNotResponsible = CodesBusinessEntityEnum.BOOLEAN_FALSE
					.getCodeEntity();
			String statusActiva = CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE
					.getCodeEntity();
			String statusAsignada = CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN
					.getCodeEntity();
			String statusAgendada = CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
					.getCodeEntity();
			String statusReagendada = CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
					.getCodeEntity();
			String statusRealizada = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
					.getCodeEntity();
			String statusFinalizada = CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED
					.getCodeEntity();
			String statusPendiente = CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING
					.getCodeEntity();
			String statusRechazada = CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED
					.getCodeEntity();
			String statusCancelada = CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED
					.getCodeEntity();
			String statusReasignada = CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN
					.getCodeEntity();
			String recordStatusLast = CodesBusinessEntityEnum.RECORD_STATUS_LAST
					.getCodeEntity();
			String elementClassDecoder = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER
					.getCodeEntity();
			String workOrderTypeService = CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE
					.getCodeEntity();
			String woStatusWorkOrderTypeChangeHsp = CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP
					.getCodeEntity();
			String workOrderStatusRealized = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
					.getCodeEntity();
			String woManagmentElementClassDeco = CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO
					.getCodeEntity();
			String woManagmentElementClassSC = CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_SC
					.getCodeEntity();
			String woAttentionRer = CodesBusinessEntityEnum.WO_ATTENTION_RER
					.getCodeEntity();
			String woAttentionReu = CodesBusinessEntityEnum.WO_ATTENTION_REU
					.getCodeEntity();
			String elementIsSerialized = CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED
					.getCodeEntity();
			String mediaContactTypeTelepCode = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE
					.getCodeEntity();
			String mediaContactTypeTelephWorkCode = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEPHWORK_CODE
					.getCodeEntity();
			String mediaContactTypeMobileCode = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE
					.getCodeEntity();
			String mediaContactTypeMail = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE
					.getCodeEntity();
			String mediaContactTypeFax = CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_FAX_CODE
					.getCodeEntity();
			String codeUserControlTower = CodesBusinessEntityEnum.CODE_USER_CONTROL_TOWER
					.getCodeEntity();

			String activeWorkOrderMark = CodesBusinessEntityEnum.BOOLEAN_TRUE
					.getCodeEntity();
			String codeRequiredcontractMark = CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT
					.getCodeEntity();

			// Consultar la reason del parametro del sistema
			SystemParameter sPShowFieldCustomerDocument = systemParameterDao
					.getSysParamByCodeAndCountryId(
							CodesBusinessEntityEnum.CODE_SHOW_FIELD_CUSTOMER_DOCUMENT_REPORT_EXCEL
									.getCodeEntity(), countryId);
			SystemParameter sPShowFieldCustomerMail = systemParameterDao
					.getSysParamByCodeAndCountryId(
							CodesBusinessEntityEnum.CODE_SHOW_FIELD_CUSTOMER_MAIL_REPORT_EXCEL
									.getCodeEntity(), countryId);

			if (sPShowFieldCustomerDocument != null) {
				String strShowFieldCustomerDocument = sPShowFieldCustomerDocument
						.getValue();
				if (strShowFieldCustomerDocument
						.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE
								.getCodeEntity())) {
					isViewCustomerDocument = false;
				}
			}

			if (sPShowFieldCustomerMail != null) {
				String strShowFieldCustomerMail = sPShowFieldCustomerMail
						.getValue();
				if (strShowFieldCustomerMail
						.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE
								.getCodeEntity())) {
					isViewCustomerMail = false;
				}
			}

			List<TechnologyVO> technologies = this.technologyBusinessBean
					.getAllIRDTechnologies();

			this.doWorkOrderByIDReport(woIDs, isViewCustomerDocument,
					isViewCustomerMail, response, aSysdate, activeStatus,
					anIsResponsible, anIsNotResponsible, statusActiva,
					statusAsignada, statusAgendada, statusReagendada,
					statusRealizada, statusFinalizada, statusPendiente,
					statusRechazada, statusCancelada, statusReasignada,
					recordStatusLast, elementClassDecoder,
					workOrderTypeService, woStatusWorkOrderTypeChangeHsp,
					workOrderStatusRealized, woManagmentElementClassDeco,
					woManagmentElementClassSC, woAttentionRer, woAttentionReu,
					elementIsSerialized, mediaContactTypeTelepCode,
					mediaContactTypeTelephWorkCode, mediaContactTypeMobileCode,
					mediaContactTypeMail, mediaContactTypeFax,
					codeUserControlTower, activeWorkOrderMark,
					codeRequiredcontractMark, technologies,idUsuario);
			
			for (ReportWorkOrderDTO wos : response) {
					
					// ####!####	
					//Enmascarado de datos
					
					SystemParameter sp = systemParameterDao
							.getSysParamByCodeAndCountryId(
									CodesBusinessEntityEnum.SYSTEM_PARAM_IS_CUSTOMER_INFO_MASK
											.getCodeEntity(),countryId);
					String isCustomerMask = sp.getValue();
					// Se enmascara el numero de documento y el tipo de documento
					if (CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()
							.equals(isCustomerMask)) {
						
						
					wos.setCustomerDocument(
								UtilsBusiness.maskString(wos.getCustomerDocument()));
					}
					// ####!####
				    
				}
			
			return response;

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación fillWorkOrderInformationForReport/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina fillWorkOrderInformationForReport/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	
	// private methods

	private void doWorkOrderByIDReport(List<Long> woIDs,
			boolean isViewCustomerDocument, boolean isViewCustomerMail,
			List<ReportWorkOrderDTO> response, Timestamp aSysdate,
			String activeStatus, String anIsResponsible,
			String anIsNotResponsible, String statusActiva,
			String statusAsignada, String statusAgendada,
			String statusReagendada, String statusRealizada,
			String statusFinalizada, String statusPendiente,
			String statusRechazada, String statusCancelada,
			String statusReasignada, String recordStatusLast,
			String elementClassDecoder, String workOrderTypeService,
			String woStatusWorkOrderTypeChangeHsp,
			String workOrderStatusRealized, String woManagmentElementClassDeco,
			String woManagmentElementClassSC, String woAttentionRer,
			String woAttentionReu, String elementIsSerialized,
			String mediaContactTypeTelepCode,
			String mediaContactTypeTelephWorkCode,
			String mediaContactTypeMobileCode, String mediaContactTypeMail,
			String mediaContactTypeFax, String codeUserControlTower,
			String activeWorkOrderMark, String codeRequiredcontractMark,
			List<TechnologyVO> technologies, Long idUsuario) throws DAOServiceException,
			DAOSQLException {
		
		
		//Generamos los datos para la SubQuery.
		WorkOrderExport woExp = new WorkOrderExport();
		woExp.setCreationDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		woExp.setIdUsuario(idUsuario);
		woExp.setNumWo(new Long(woIDs.size()));
		workOrderExportDAO.createWorkOrderExport(woExp);
		
		List<WorkOrderExportData> listaWoExpData = new ArrayList<WorkOrderExportData>();
		for (Long id : woIDs) {
			WorkOrderExportData woExpData = new WorkOrderExportData();
			WorkOrderExportDataId idWoData = new WorkOrderExportDataId(woExp.getId(),id);
			woExpData.setId(idWoData);
			listaWoExpData.add(woExpData);
		}
		workOrderExportDataDAO.createWorkOrderExportData(listaWoExpData);
		
		response.addAll(this.workOrderDAO.getWorkOrderByIDReport(
				aSysdate, technologies, activeStatus,
				anIsResponsible, anIsNotResponsible, statusActiva,
				statusAsignada, statusAgendada, statusReagendada,
				statusRealizada, statusFinalizada, statusPendiente,
				statusRechazada, statusCancelada, statusReasignada,
				recordStatusLast, elementClassDecoder,
				workOrderTypeService, woStatusWorkOrderTypeChangeHsp,
				workOrderStatusRealized, woManagmentElementClassDeco,
				woManagmentElementClassSC, woAttentionRer,
				woAttentionReu, elementIsSerialized,
				mediaContactTypeTelepCode,
				mediaContactTypeTelephWorkCode,
				mediaContactTypeMobileCode, mediaContactTypeMail,
				mediaContactTypeFax, codeUserControlTower,
				isViewCustomerDocument, isViewCustomerMail,
				activeWorkOrderMark, codeRequiredcontractMark, true,woExp.getId()));
	}

	

	/**
	 * 
	 * Metodo: Consulta el comentario de gestion de la WorkOrder
	 * 
	 * @param woId
	 * @return String
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	/*private String getManagmentComment(Long woId) throws BusinessException {
		log.debug("== Inicia fillFinalizationComment/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<Long> woStatusIds = new ArrayList<Long>();
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds
					.add(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
							.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED
					.getIdEntity(WorkorderStatus.class.getName()));
			woStatusIds.add(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED
					.getIdEntity(WorkorderStatus.class.getName()));

			String finishedComment = null;
			WoStatusHistory woStatusHistory = null;
			if (woStatusIds != null && !woStatusIds.isEmpty()) {
				for (int i = 0; i < woStatusIds.size(); i++) {
					Long woStatusId = woStatusIds.get(i);
					WoStatusHistory temp = woStatusHistoryDAO
							.getWorkorderReasonByWoHistory(woId, woStatusId);
					if ((woStatusHistory == null || woStatusHistory
							.getStatusDate() == null)
							&& temp != null
							&& temp.getStatusDate() != null) {
						woStatusHistory = UtilsBusiness.copyObject(
								WoStatusHistory.class, temp);
					} else if (woStatusHistory != null
							&& woStatusHistory.getStatusDate() != null
							&& temp != null
							&& temp.getStatusDate() != null
							&& woStatusHistory.getStatusDate().before(
									temp.getStatusDate())) {
						woStatusHistory = UtilsBusiness.copyObject(
								WoStatusHistory.class, temp);
					}
				}
			}
			if (woStatusHistory != null
					&& woStatusHistory.getDescription() != null) {
				finishedComment = woStatusHistory.getDescription();
			}
			return finishedComment;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación fillFinalizationComment/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina fillFinalizationComment/TrayWorkOrderManagmentBusinessBean ==");
		}
	}*/

	/**
	 * 
	 * Metodo: Centraliza llenar la lista de delaers o branches para la consulta
	 * de WO
	 * 
	 * @param filter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private void fillDealerAndBranchIds(WorkOrderFilterTrayDTO filter)
			throws DAOServiceException, DAOSQLException {
		// Se valida que se envie una sucursal o una principal para darle valor
		// a los ids de dealers por los cuales se filtra
		boolean isDealerId = (filter.getDealersIds() == null || filter
				.getDealersIds().isEmpty()) ? false : true;
		boolean isBranchId = (filter.getBranchIds() == null || filter
				.getBranchIds().isEmpty()) ? false : true;
		if (isBranchId) {
			filter.setDealersIds(null);
		} else if (isDealerId) {
			List<Long> tempList = new ArrayList<Long>();
			for (Long dealerId : filter.getDealersIds()) {
				List<Dealer> dealers = dealersDAO.getDealerByBranchId(dealerId);
				if (dealers != null && !dealers.isEmpty()) {
					for (Dealer d : dealers) {
						tempList.add(d.getId());
					}
				}
			}
			filter.getDealersIds().addAll(tempList);
			filter.setBranchIds(null);
		}
	}
}
