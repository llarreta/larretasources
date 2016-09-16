package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.EmployeesCrewCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.CrewMovement;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.model.pojo.CrewStatus;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.EmployeeCrewId;
import co.com.directv.sdii.model.pojo.EmployeeRol;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.CrewOffVO;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.config.CrewMovementDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsOffDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.impl.CrewsOffDAO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ReferenceDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de
 * la Entidad Crews
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * 
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.CrewssDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.CrewssCRUDBeanLocal
 * 
 */
@Stateless(name = "CrewsCRUDBeanLocal", mappedName = "ejb/CrewsCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CrewsCRUDBean extends BusinessBase implements CrewsCRUDBeanLocal {

	@EJB(name = "CrewsDAOLocal", beanInterface = CrewsDAOLocal.class)
	private CrewsDAOLocal daoCrew;
	@EJB(name = "EmployeesCrewDAOLocal", beanInterface = EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal daoEmployeeCrew;
	@EJB(name = "EmployeesCrewCRUDBeanLocal", beanInterface = EmployeesCrewCRUDBeanLocal.class)
	private EmployeesCrewCRUDBeanLocal employeeCrewBusiness;
	@EJB(name = "WoAssignmentDAOLocal", beanInterface = WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAOLocal;
	@EJB(name = "EmployeesCrewDAOLocal", beanInterface = EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;
	@EJB(name = "CrewStatusDAOLocal", beanInterface = CrewStatusDAOLocal.class)
	private CrewStatusDAOLocal crewStatusDAO;

	@EJB(name = "CrewsOffDAOLocal", beanInterface = CrewsOffDAOLocal.class)
	private CrewsOffDAOLocal crewsOffDAO;

	@EJB(name = "WarehouseDAOLocal", beanInterface = WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;

	@EJB(name = "WarehouseBusinessBeanLocal", beanInterface = WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal bussinesWarehouse;
	
    @EJB(name = "EmployeeDAOLocal", beanInterface = EmployeeDAOLocal.class)
    private EmployeeDAOLocal daoEmployee;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;
	
    @EJB(name="ReferenceDAOLocal", beanInterface=ReferenceDAOLocal.class)
    private ReferenceDAOLocal daoReference;
	
    @EJB(name="AdjustmentDAOLocal", beanInterface=AdjustmentDAOLocal.class)
    private AdjustmentDAOLocal daoAdjustment;

    @EJB(name="CrewMovementDAOLocal", beanInterface=CrewMovementDAOLocal.class)
    private CrewMovementDAOLocal crewMovementDAO;

    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal dealersDAO;
    
	@EJB(name="CrewsDAOLocal", beanInterface=CrewsDAOLocal.class)
	private CrewsDAOLocal crewsDAO;
	
	private final static Logger log = UtilsBusiness.getLog4J(CrewsCRUDBean.class);

	/**
	 * Llama al metodo createCrews de la clase CrewsDAOLocal para la creación de
	 * una cuadrilla en el sistema
	 * 
	 * @param obj
	 *            - CrewsVO
	 * @throws BusinessException
	 * @author jcasas
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createCrews(CrewVO obj) throws BusinessException {
		try {

			if (obj == null) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			if (obj.getCrewStatus() == null
					|| obj.getCrewStatus().getStatusCode() == null) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			CrewStatus crewStatus = this.crewStatusDAO.getCrewStatusByCode(obj
					.getCrewStatus().getStatusCode());
			if (crewStatus == null) {
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			obj.setCrewStatus(crewStatus);

			if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
				throw new BusinessException(
						ErrorBusinessMessages.CREW_NOT_UPDATE_PARAMS_NULL_OR_MISSED
								.getCode(),
						ErrorBusinessMessages.CREW_NOT_UPDATE_PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			obj.setInitDate(UtilsBusiness.dateTo12am(obj.getInitDate()));
			// La fecha de finalizacion no es obligatoria en la creacion
			if (obj.getEndDate() != null) {
				obj.setEndDate(UtilsBusiness.dateTo12pm(obj.getEndDate()));
			}

			this.validateCrewsRules(obj);

			// Creacion de cuadrilla y asociacion de empleados a la cuadrilla
			Crew crew = UtilsBusiness.copyObject(Crew.class, obj);
			daoCrew.createCrew(crew);
			for (EmployeeCrewVO ecVO : obj.getEmployeesCrew()) {
				EmployeeCrewId empCrewId = ecVO.getId();
				if (empCrewId == null) {
					throw new BusinessException(
							ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
									.getCode(),
							ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
									.getMessage()
									+ " No se haespecificado el identificador de una de los empleados asociados a la cuadrilla");
				}
				empCrewId.setCrewId(crew.getId());
				daoEmployeeCrew.createEmployeesCrew(UtilsBusiness.copyObject(
						EmployeeCrew.class, ecVO));
			}

		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createCrews/CrewsCRUDBean ==");
		}
	}

	/**
	 * Elimina una cuadrilla del sistema
	 * 
	 * @param obj
	 *            - CrewsVO
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCrews(CrewVO obj) throws BusinessException {
		try {
			if (obj == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			daoCrew.deleteCrew(UtilsBusiness.copyObject(Crew.class, obj));
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteCrews/CrewsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Llama al metodo getAll de la clase DAO para obtener todas las
	 * cuadriallas existentes en el sistema
	 * 
	 * @return - List<CrewsVO>
	 * @throws BusinessException
	 * @author Jose Andres Casas Rodriguez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CrewVO> getAllCrews() throws BusinessException {
		try {
			List<CrewVO> resultList = UtilsBusiness.convertList(daoCrew
					.getAll(), CrewVO.class);
			for (CrewVO crewVO : resultList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllCrews/CrewsCRUDBean ==");
		}
	}

	/**
	 * Obtiene una cuadrilla por placa o documento y dealer
	 * 
	 * @return - List
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CrewVO> getCrewsByPlateOrDocumentOrDealer(String plate,
			String document, Long dealerId) throws BusinessException {
		log
				.debug("== Inicia getCrewsByPlateOrDocumentOrDealer/CrewsCRUDBean ==");
		try {
			if (dealerId == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}

			List<Crew> crewsList = daoCrew.getCrewsByPlateOrDocumentOrDealer(
					plate, document, dealerId);
			if (crewsList == null)
				return null;
			List<CrewVO> crewsVoList = UtilsBusiness.convertList(crewsList,
					CrewVO.class);

			for (CrewVO crewVO : crewsVoList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return crewsVoList;

		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getCrewsByPlateOrDocumentOrDealer/CrewsCRUDBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getCrewsByPlateOrDocumentOrDealer/CrewsCRUDBean ==");
		}
	}

	/**
	 * Obtiene una cuadrilla por placa o documento
	 * 
	 * @return - List
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CrewVO> getCrewsByPlateOrDocument(String plate, String document)
			throws BusinessException {
		try {

			List<Crew> crewsList = daoCrew.getCrewsByPlateOrDocument(plate,
					document);
			if (crewsList == null)
				return null;
			List<CrewVO> crewsVoList = UtilsBusiness.convertList(crewsList,
					CrewVO.class);

			for (CrewVO crewVO : crewsVoList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return crewsVoList;

		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllCrews/CrewsCRUDBean ==");
		}
	}

	/**
	 * Obtiene una cuadrilla con el id especificado
	 * 
	 * @param id
	 *            - Long
	 * @return - CrewsVO
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public CrewVO getCrewsByID(Long id) throws BusinessException {
		try {
			if (id == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			Crew crewID = daoCrew.getCrewById(id);
			if (crewID != null) {
				CrewVO result = UtilsBusiness.copyObject(CrewVO.class, crewID);
				result.populateBean();
				this.fillRelationships(result);
				return result;
			}
			return null;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByID/CrewsCRUDBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCrews(CrewVO obj, String description, UserVO userVO) throws BusinessException {
		log.debug("== Inicia updateCrews/CrewsCRUDBean ==");
		try {
			if (obj == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}

			//Consultar el responsable actual (antes de la modificación)
			Employee employeeOldResponsibleForCrew = this.daoEmployeeCrew.getEmployeeResponsibleByCrewId(obj.getId());
			

			if (obj.getCrewStatus() == null
					|| obj.getCrewStatus().getStatusCode() == null) {
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}

			CrewStatus crewStatus = this.crewStatusDAO.getCrewStatusByCode(obj.getCrewStatus().getStatusCode());
			if (crewStatus == null) {
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			obj.setCrewStatus(crewStatus);
			// Se valida que esta pasando de activo a inactivo para guardar el
			// crew off
			if (crewStatus.getStatusCode().equals(CodesBusinessEntityEnum.CREW_STATUS_INACTIVE.getCodeEntity())) {
				Crew crewTmp = daoCrew.getCrewById(obj.getId());
				List<Warehouse> warehousesByCrewId=bussinesWarehouse.getNoVirtualWarehousesByCrewId(obj.getId());
				for(Warehouse wh: warehousesByCrewId){
					if(wh.getIsActive().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity())){
						Object[] errorParams=new Object[1];
						errorParams[0]=wh.getWhCode();
						throw new BusinessException(ErrorBusinessMessages.DEALERS_DE016.getMessage(), ErrorBusinessMessages.DEALERS_DE016.getMessage(errorParams));
					}
				}
				if (crewTmp.getCrewStatus().getStatusCode().equals(CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity())) {
					if (description == null || description.equals("")) {
						throw new BusinessException(ErrorBusinessMessages.DEALERS_DE014.getCode(),ErrorBusinessMessages.DEALERS_DE014.getMessage());
					}
					validateCrewAssignment(obj.getId(),true);
					CrewsOffDAO crewOffDAO = new CrewsOffDAO();
					CrewOff newCrewOff = new CrewOff(obj, new Date(),description);
					crewOffDAO.createCrewOff(newCrewOff);
				}

			}
			validateCrewsRulesToUpdateCrew(obj);
			// Creacion de cuadrilla y asociacion de empleados a la cuadrilla

			daoCrew.updateCrew(UtilsBusiness.copyObject(Crew.class, obj));

			if (obj.getEmployeesCrew() != null) {
				log.debug("Actualizando los " + obj.getEmployeesCrew().size() + " employee crews");
				daoEmployeeCrew.deleteEmployeesCrewByCrewId(obj.getId());
				// gfandino - 24/05/2011 Valida si hay usaurio asociados a otras
				// cuadrillas en estado activo
				List<String> emError = new ArrayList<String>();
				boolean printError = false;
				for (EmployeeCrewVO employeeCrewVO : obj.getEmployeesCrew()) {
					List<EmployeeCrew> listaCuadrillasTmp = this.daoEmployeeCrew
							.getEmployeesCrewByEmpIdAndCrewStatus(
									employeeCrewVO.getId().getEmployeeId(),
									CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
					if (listaCuadrillasTmp.size() > 0) {
						emError.add(listaCuadrillasTmp.get(0).getEmployee()
								.getFirstName()
								+ " "
								+ listaCuadrillasTmp.get(0).getEmployee().getLastName());
						printError = true;
					}
				}
				if (printError) {
					List<String> listNameList = new ArrayList<String>();
					StringBuffer listName = new StringBuffer();
					for (int i = 0; i < emError.size(); i++) {
						if (i == emError.size() - 1) {
							listName.append(emError.get(i));
						} else {
							listName.append(emError.get(i) + ",");
						}
					}
					listNameList.add(listName.toString());
					log.debug(ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getMessage(listNameList.toArray()));
					throw new BusinessException(
							ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getCode(),
							ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getMessage(listNameList.toArray()),
							listNameList);

				}
				for (EmployeeCrewVO ecVO : obj.getEmployeesCrew()) {
					EmployeeCrewId empCrewId = ecVO.getId();
					if (empCrewId == null) {
						log.error("no se obtuvo el empCrewId");
						throw new BusinessException(
								ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
								ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					}
					empCrewId.setCrewId(obj.getId());
					
					/*if(ecVO.getIsResponsible().equalsIgnoreCase(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity())){
						UtilsBusiness.assertNotNull(userVO.getCountry().getCountryCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());					
						SystemParameter optimusSP = systemParameterDAO.getSysParamByCodeAndCountryCode(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), userVO.getCountry().getCountryCode());
						if(optimusSP != null 
								&& optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
							Employee employee = daoEmployee.getEmployeeByID(ecVO.getId().getEmployeeId());
							UtilsBusiness.assertNotNull(employee.getPin(), ErrorBusinessMessages.CORE_CR106.getCode(), ErrorBusinessMessages.CORE_CR106.getMessage());							
						}
					}*/
					
					daoEmployeeCrew.createEmployeesCrew(UtilsBusiness.copyObject(EmployeeCrew.class, ecVO));
				}
			}
			
			//Consultar el responsable modificado (despues de la modificación)
			Employee employeeNewResponsibleForCrew = this.daoEmployeeCrew.getEmployeeResponsibleByCrewId(obj.getId());
			
			//Si cambión el empleado responsable se cambia el nombre de
			//las ubicaciones de la cuadrilla.
			if(employeeNewResponsibleForCrew.getId().longValue() != employeeOldResponsibleForCrew.getId().longValue()){
				// Actualizacion de la bodega de la cuadrilla
				bussinesWarehouse.changeTheWhCodeForWareHousesByCrewCode(obj.getId());
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación updateCrews/CrewsCRUDBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateCrews/CrewsCRUDBean ==");
		}
	}

	
	public void registerModificationsCrewForReport(CrewVO obj, UserVO userVO, Boolean isNewResponsible, Employee employeeNewResponsibleForCrew, List<Employee> employeesOldsCrew) throws BusinessException {
		try {
			log.debug("== Inicia registerModificationsCrewForReport/CrewsCRUDBean ==");			
			CrewMovement crewMovement = new CrewMovement();		
			crewMovement.setIdCrew(obj.getId());
			crewMovement.setUserId(UtilsBusiness.copyObject(User.class, userVO));
			crewMovement.setIdVehicle(obj.getVehicle());
			crewMovement.setStatusCrew(obj.getCrewStatus().getStatusName());
			crewMovement.setCrewMoficationDate(new Date());
			crewMovement.setCreationCrew(obj.getInitDate());
			crewMovement.setCrewActivationDate(obj.getInitDate());
			
			List<Warehouse> wareHouses = daoWarehouse.getWarehousesByCrewId(obj.getId());
			
			for(Warehouse wh: wareHouses){
				if (wh.getWarehouseType().getWhTypeCode().equals(CodesBusinessEntityEnum.WAREHOUSE_TYPE_S01.getCodeEntity())) {
					crewMovement.setLocationVinculateCode(wh.getWhCode());
				}
			}
			
			if (isNewResponsible) {
				crewMovement.setNewResponsibleCrew(employeeNewResponsibleForCrew.getFirstName()+", "+ employeeNewResponsibleForCrew.getLastName());
			}else {
				crewMovement.setNewResponsibleCrew("");
			}
						
			//campos compa�ia y compañia principal
			Dealer dealer = dealersDAO.getDealerByID(obj.getDealer().getId());
			crewMovement.setDealerName(dealer.getDealerName());
			if (dealer.getDealer() == null) {
				crewMovement.setDealerMain(dealer.getDealerName());
			}else {
				crewMovement.setDealerMain(dealer.getDealer().getDealerName());
			}

			String membersCrew ="";
			String rolMembersCrew="";
			String documentNumberMembersCrew="";
			Integer flag = 1;
			//miembros de la cuadrilla
			
			// si no hay nuevo empleado inicializo los campos
			crewMovement.setNewEmployee("");
			crewMovement.setRolNewEmployee("");
			crewMovement.setDocumentNumberNewEmployee("");	
			
			for (EmployeeCrewVO employeeCrew : obj.getEmployeesCrew()) {
				
				for (Employee employee : employeesOldsCrew) {
					if (employee.getId().equals(employeeCrew.getId().getEmployeeId())) {
						flag = 0;
						membersCrew = membersCrew + employee.getFirstName() + ", " + employee.getLastName() + "; ";
						rolMembersCrew = rolMembersCrew + employeeCrew.getEmployeeRol().getRolName() + "; ";
						documentNumberMembersCrew = documentNumberMembersCrew + employee.getDocumentNumber() + "; ";
						if (employeeCrew.getIsResponsible().equals(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity())) {
							crewMovement.setResponsibleCrew(employee.getFirstName() +", "+ employee.getLastName());
						}
						break;
					}
				}
				if(flag == 1 ){
					Employee employee = daoEmployee.getEmployeeByID(employeeCrew.getId().getEmployeeId());
					crewMovement.setDocumentNumberNewEmployee(employee.getDocumentNumber());
					crewMovement.setRolNewEmployee(employeeCrew.getEmployeeRol().getRolName());
					crewMovement.setNewEmployee(employee.getFirstName() +", "+ employee.getLastName());
					flag = 0;
				}
				flag = 1;
			}
			
			crewMovement.setMembersCrew(membersCrew);
			crewMovement.setRoleEmployeesCrew(rolMembersCrew);
			crewMovement.setDocumentNumber(documentNumberMembersCrew);
			
			crewMovementDAO.createCrewMovement(crewMovement);
		} catch (Exception ex) {
			log.debug("== Error al tratar de ejecutar la operación registerModificationsCrewForReport/CrewsCRUDBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina registerModificationsCrewForReport/CrewsCRUDBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void detachedEmployeeCrew(CrewVO obj, EmployeeCrewVO employeeCrew , UserVO user) throws BusinessException{
		log.debug("== Inicia detachedEmployeeCrew/CrewsCRUDBean ==");		
		try{
			
			if (obj == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if (employeeCrew == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//consultar los empleados responsables antes de la modificación
			List<Employee> employeesOldsCrew = this.daoEmployeeCrew.getAllEmployeeCrewByCrewId(obj.getId());
			
			//pregunto si es el colaborador es técnico
			//if (employeeCrew.getEmployeeRol().getRolCode().equals(CodesBusinessEntityEnum.CREW_ROL_TECNICIAN.getCodeEntity())){
			if (employeeCrew.getIsResponsible().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
				//T-2 valido que no tenga ninguna WO asiganda en estados diferentes a Cancelada o Finalizada
				validateCrewAssignment(obj.getId(),false);
				//T-3  
				//valido que no tenga ninguna Remisión en estado distinta de Recepcionada
				validateCrewReferenecesPendings(obj.getId());				
				//valido que no tenga ningun Ajuste en estado distinto de Procesada o Autorizada
				validateCrewAdjustmentsPendings(obj.getId());
				//T-1 pregunto si es responsable de la cuadrilla
				validateIsResponsibleCrew(employeeCrew);
				//encaso de cumplir las tres reglas desasocio el colaborador
				daoEmployeeCrew.deleteEmployeeCrewsByEmployeeId(employeeCrew.getEmployee().getId());
			}else{
				//valido que no tenga ninguna WO asiganda en estados diferentes a Cancelada o Finalizada
				validateCrewAssignment(obj.getId(),false);
				//encaso de cumplir las tres reglas desasocio el colaborador
				daoEmployeeCrew.deleteEmployeeCrewsByEmployeeId(employeeCrew.getEmployee().getId());
				}
			
			//se guarda las modificaciones de las cuadrilas
			this.registerModificationsCrewForReport(obj,user , false, null, employeesOldsCrew);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación detachedEmployeeCrew/CrewsCRUDBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina detachedEmployeeCrew/CrewsCRUDBean ==");
		}
	}
	
	public void validateIsResponsibleCrew(EmployeeCrewVO employeeCrew) throws BusinessException {
		log.debug("== Inicia validateIsResponsibleCrew/CrewsCRUDBean ==");
		try{
			
			if( employeeCrew.getIsResponsible().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
				throw new BusinessException(ErrorBusinessMessages.DEALERS_DE020.getCode(),ErrorBusinessMessages.DEALERS_DE020.getMessage());
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación validateIsResponsibleCrew/CrewsCRUDBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateIsResponsibleCrew/CrewsCRUDBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Valida que la cuadrilla no se encuentre a WO en estado Asignadas,
	 * Agendadas, Reasignadas, Pendientes, Reagendadas, Atendidas
	 * 
	 * @param crewID
	 *            Identificador de la cuadrilla que se va a validar
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	public void validateCrewAssignment(Long crewID, boolean isInactivateCrew) throws BusinessException {
		log.debug("== Inicia validateCrewAssignment/CrewsCRUDBean ==");
		try {
			// Se valida que la cuadrilla no se encuentre asignada a ninguna WO
			// en los siguientes estados:
			//
			List<String> woStatuss = new ArrayList<String>();
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN
					.getCodeEntity());
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN
					.getCodeEntity());
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED
					.getCodeEntity());
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED
					.getCodeEntity());
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING
					.getCodeEntity());
			woStatuss.add(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED
					.getCodeEntity());
			List<WoAssignment> asignments = this.woAssignmentDAOLocal
					.getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus(
							crewID, woStatuss);
			if (asignments != null && !asignments.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (WoAssignment assignment : asignments) {
					sb.append(assignment.getWorkOrder().getWoCode());
					sb.append(",");
				}
				Object params[] = { StringUtils.removeEnd(sb.toString(), ",") };
				List<String> list = new ArrayList<String>();
				list.add((String) params[0]);
				
				//desasociar técnico
				String errorBusinessMessagesCode = "";
				String errorBusinessMessages = "";
				if(isInactivateCrew == true){
					errorBusinessMessagesCode = ErrorBusinessMessages.DEALERS_DE015.getCode();
					errorBusinessMessages = ErrorBusinessMessages.DEALERS_DE015.getMessage(params);
				}else{
					errorBusinessMessagesCode = ErrorBusinessMessages.DEALERS_DE018.getCode();
					errorBusinessMessages = ErrorBusinessMessages.DEALERS_DE018.getMessage(params);
				}
				throw new BusinessException(errorBusinessMessagesCode, errorBusinessMessages, list);
			}
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación validateCrewAssignment/CrewsCRUDBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateCrewAssignment/CrewsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Valida que la cuadrilla no teng Remisiones sin autorizar
	 * 
	 * @param crewID
	 *            Identificador de la cuadrilla que se va a validar
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	public void validateCrewReferenecesPendings(Long crewID) throws BusinessException {
		log.debug("== Inicia validateCrewRefernecePending/CrewsCRUDBean ==");
		try {
			// Se valida que la cuadrilla no se encuentre con remisiones pendientes de autorizar
			
			List<String> statusCodes = new ArrayList<String>();

			statusCodes.add(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
							
			List<ReferenceVO> references = UtilsBusiness.convertList(this.daoReference.getReferencesByCrewIdAndDistinctReferneceStatus(statusCodes, crewID), ReferenceVO.class);		
				
			if ( references != null  && !references.isEmpty() ){
				StringBuilder sb = new StringBuilder();
				for (ReferenceVO reference : references){
					sb.append(reference.getId());
					sb.append(",");
				}
				Object parametros[] = { StringUtils.removeEnd(sb.toString(), ",") };
				List<String> list = new ArrayList<String>();
				list.add((String) parametros[0]);
								
				throw new BusinessException(ErrorBusinessMessages.DEALERS_DE019.getCode(), ErrorBusinessMessages.DEALERS_DE019.getMessage(parametros), list);
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación validateCrewAssignment/CrewsCRUDBean ==", ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateCrewRefernecePending/CrewsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Valida que la cuadrilla no tenga ajustes con estados distintos a Procesado o Autorizado
	 * 
	 * @param crewID
	 *            Identificador de la cuadrilla que se va a validar
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	public void validateCrewAdjustmentsPendings(Long crewID) throws BusinessException {
		log.debug("== Inicia validateCrewRefernecePending/CrewsCRUDBean ==");
		try {
			// Se valida que la cuadrilla no tenga ajustes pendientes de autorizar
			
			List<String> adStatuss = new ArrayList<String>();

			adStatuss.add(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_PROCESS.getCodeEntity());
			adStatuss.add(CodesBusinessEntityEnum.ADJUSTMENT_STATUS_AUTHORIZED.getCodeEntity());				
			
			List<AdjustmentVO> adjustments =  UtilsBusiness.convertList(this.daoAdjustment.getAdjustmentsByCrewIdAndDistinctAdjustmentStatus(adStatuss, crewID), AdjustmentVO.class);		
				
			if (adjustments != null && !adjustments.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (AdjustmentVO adjustment : adjustments) {
					sb.append(adjustment.getId());
					sb.append(",");
				}
				Object parametros[] = { StringUtils.removeEnd(sb.toString(), ",") };
				List<String> list = new ArrayList<String>();
				list.add((String) parametros[0]);

				throw new BusinessException(ErrorBusinessMessages.DEALERS_DE021.getCode(),ErrorBusinessMessages.DEALERS_DE021
								.getMessage(parametros), list);
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación validateCrewAssignment/CrewsCRUDBean ==", ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateCrewRefernecePending/CrewsCRUDBean ==");
		}
	}
	
	/**
	 * Desactiva una cuadrilla y coloca en el log el detalle de la desactivacion
	 * 
	 * @param crewVO
	 *            - CrewsVO
	 * @param description
	 *            - String
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deactivateCrew(CrewVO crewVO, String description)
			throws BusinessException {
		try {
			if (crewVO == null || description == null) {
				log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());
			}
			Crew crewToDeactivate = daoCrew.getCrewById(crewVO.getId());

			CrewStatus crewStatus = new CrewStatus();
			Long statusId = CodesBusinessEntityEnum.CREW_STATUS_INACTIVE
					.getIdEntity(CrewStatus.class.getName());
			crewStatus.setId(statusId);

			crewToDeactivate.setCrewStatus(crewStatus);
			daoCrew.updateCrew(crewToDeactivate);

			CrewsOffDAO crewOffDAO = new CrewsOffDAO();
			CrewOff newCrewOff = new CrewOff(crewToDeactivate, new Date(),
					description);
			crewOffDAO.createCrewOff(newCrewOff);

		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deactivateCrew/CrewsCRUDBean ==");
		}
	}

	/**
	 * Valida las reglas de negocio de los campos de una cuadrilla
	 * 
	 * @param obj
	 *            - CrewsVO
	 * @throws Throwable
	 */
	private void validateCrewsRulesToUpdateCrew(CrewVO obj) throws Throwable {
		int noTecnics = 0, noResponsibles = 0;

		// Validation V01
		EmployeeCrewVO employeeCrewResponsible = null;
		for (EmployeeCrewVO employeeCrewVO : obj.getEmployeesCrew()) {

			if (obj.getEmployeesCrew() == null
					|| obj.getEmployeesCrew().isEmpty()) {
				throw new BusinessException(
						ErrorBusinessMessages.CREW_NOT_ASSIGNMENT_EMPLOYEE
								.getCode(),
						ErrorBusinessMessages.CREW_NOT_ASSIGNMENT_EMPLOYEE
								.getMessage());
			}
			if (employeeCrewVO.getIsResponsible().equals(
					CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
				// Si es responsable
				noResponsibles++;
				employeeCrewResponsible = employeeCrewVO;
			}
			if (employeeCrewVO.getEmployeeRol().getId().equals(
					CodesBusinessEntityEnum.CREW_ROL_TECNICIAN
							.getIdEntity(EmployeeRol.class.getName()))) {
				// Si es "Tecnico"
				noTecnics++;
			}
		}
		if (noResponsibles == 0 || employeeCrewResponsible == null) {
			log.debug(ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
							.getCode(),
					ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
							.getMessage());
		}
		if (noResponsibles > 1) {
			log.debug(ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
							.getCode(),
					ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
							.getMessage());
		}
		// Validation V03
		if (noTecnics == 0) {
			log.debug(ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED.getCode(),
					ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED
							.getMessage());
		}
		// Validation V02
		log.info("Validando rango de fechas por responsable-");

		// Validation V02
		log.debug("Buscanado cuadrillas entre '" + obj.getInitDate() + "' - '"
				+ obj.getEndDate() + "' y empId: "
				+ employeeCrewResponsible.getId().getEmployeeId());

		/*
		 * List<CrewVO> crewsList =
		 * UtilsBusiness.convertList(daoCrew.getCrewsBetweenDatesAndEmployeeAndCrew
		 * (UtilsBusiness.addDate(obj.getInitDate(), -1) ,
		 * UtilsBusiness.addDate(obj.getEndDate(), 1),
		 * employeeCrewResponsible.getId().getEmployeeId(),obj.getId()),
		 * CrewVO.class);
		 * 
		 * if(crewsList!=null){log.info("se obtuvieron "+crewsList.size()+
		 * " cuadrillas con ese rango de fechas"); } if(crewsList!=null &&
		 * crewsList.size() > 0){ throw new
		 * BusinessException(ErrorBusinessMessages
		 * .CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getCode(),
		 * ErrorBusinessMessages
		 * .CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage()); }
		 */
		//        
		// for (CrewVO crew : crewsList) {
		// log.info("comparando: "+crew.getId()+" con "+employeeCrewResponsible.getId().getCrewId());
		// if (crew.getId().equals(employeeCrewResponsible.getId().getCrewId()))
		// {
		// log.error(ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage());
		// throw new
		// BusinessException(ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getCode(),ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage());
		// }else{
		// log.debug("Fecha cumple con parametro");
		// }
		// }

		// Validation V04
		Date initialDate = obj.getInitDate();
		Date endDate = obj.getEndDate();
		if (initialDate != null && endDate != null) {
			if (initialDate.compareTo(endDate) > 0) {
				log.debug(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
								.getCode(),
						ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
								.getMessage());
			}
		}

		log.debug("== Crews Reglas de negocio validas ==");
	}

	/**
	 * Valida las reglas de negocio de los campos de una cuadrilla
	 * 
	 * @param obj
	 *            - CrewsVO
	 * @throws Throwable
	 */
	private void validateCrewsRules(CrewVO obj) throws Throwable {
		int noTecnics = 0, noResponsibles = 0;

		if (obj.getCrewStatus() == null
				|| CodesBusinessEntityEnum.CREW_STATUS_INACTIVE.getCodeEntity()
						.equals(obj.getCrewStatus().getId() + "")) {
			log.error(ErrorBusinessMessages.CREW_CANNOT_CREATE_INACTIVE
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_CANNOT_CREATE_INACTIVE.getCode(),
					ErrorBusinessMessages.CREW_CANNOT_CREATE_INACTIVE
							.getMessage());
		} else {
			log.debug("Creando cuadrilla con estado activo: "
					+ obj.getCrewStatus().getId());
		}

		// Validation V01
		EmployeeCrewVO employeeCrewResponsible = null;
		for (EmployeeCrewVO employeeCrewVO : obj.getEmployeesCrew()) {

			if (obj.getEmployeesCrew() == null
					|| obj.getEmployeesCrew().isEmpty()) {
				throw new BusinessException(
						ErrorBusinessMessages.CREW_NOT_ASSIGNMENT_EMPLOYEE
								.getCode(),
						ErrorBusinessMessages.CREW_NOT_ASSIGNMENT_EMPLOYEE
								.getMessage());
			}
			if (employeeCrewVO.getIsResponsible().equals(
					CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
				// Si es responsable
				noResponsibles++;
				employeeCrewResponsible = employeeCrewVO;
			}
			if (employeeCrewVO.getEmployeeRol().getId().equals(
					CodesBusinessEntityEnum.CREW_ROL_TECNICIAN
							.getIdEntity(EmployeeRol.class.getName()))) {
				// Si es "Tecnico"
				noTecnics++;
			}

		}
		if (noResponsibles == 0 || employeeCrewResponsible == null) {
			log.debug(ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
							.getCode(),
					ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED
							.getMessage());
		}
		if (noResponsibles > 1) {
			log.debug(ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
							.getCode(),
					ErrorBusinessMessages.CREW_MULTIPLE_RESPONSIBLE_SPECIFIED
							.getMessage());
		}
		// Validation V03
		if (noTecnics == 0) {
			log.debug(ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED
					.getMessage());
			throw new BusinessException(
					ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED.getCode(),
					ErrorBusinessMessages.CREW_NO_TECNICIAN_SPECIFIED
							.getMessage());
		}
		// Validation V02
		log.info("Validando rango de fechas por responsable-");

		// Validation V02
		log.debug("Buscanado cuadrillas entre '" + obj.getInitDate() + "' - '"
				+ obj.getEndDate() + "' y empId: "
				+ employeeCrewResponsible.getId().getEmployeeId());

		/*
		 * List<CrewVO> crewsList =
		 * UtilsBusiness.convertList(daoCrew.getCrewsBetweenDatesAndEmployee
		 * (UtilsBusiness.addDate(obj.getInitDate(), -1) ,
		 * UtilsBusiness.addDate(obj.getEndDate(), 1),
		 * employeeCrewResponsible.getId().getEmployeeId()), CrewVO.class);
		 * 
		 * if(crewsList!=null){log.info("se obtuvieron "+crewsList.size()+
		 * " cuadrillas con ese rango de fechas"); } if(crewsList!=null &&
		 * crewsList.size() > 0){ throw new
		 * BusinessException(ErrorBusinessMessages
		 * .CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getCode(),
		 * ErrorBusinessMessages
		 * .CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage()); }
		 */
		//        
		// for (CrewVO crew : crewsList) {
		// log.info("comparando: "+crew.getId()+" con "+employeeCrewResponsible.getId().getCrewId());
		// if (crew.getId().equals(employeeCrewResponsible.getId().getCrewId()))
		// {
		// log.error(ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage());
		// throw new
		// BusinessException(ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getCode(),ErrorBusinessMessages.CREW_EMPLOYEE_RESPONSIBLE_IN_OTHER_CREW.getMessage());
		// }else{
		// log.debug("Fecha cumple con parametro");
		// }
		// }

		// Validation V04
		Date initialDate = obj.getInitDate();
		Date endDate = obj.getEndDate();
		if (initialDate != null && endDate != null) {
			if (initialDate.compareTo(endDate) > 0) {
				log.debug(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
						.getMessage());
				throw new BusinessException(
						ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
								.getCode(),
						ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID
								.getMessage());
			}
		}

		// gfandino - 24/05/2011 Valida si hay usaurio asociados a otras
		// cuadrillas en estado activo
		List<String> emError = new ArrayList<String>();
		boolean printError = false;
		for (EmployeeCrewVO employeeCrewVO : obj.getEmployeesCrew()) {
			List<EmployeeCrew> listaCuadrillasTmp = this.daoEmployeeCrew
					.getEmployeesCrewByEmpIdAndCrewStatus(employeeCrewVO
							.getId().getEmployeeId(),
							CodesBusinessEntityEnum.CREW_STATUS_ACTIVE
									.getCodeEntity());
			if (listaCuadrillasTmp.size() > 0) {
				emError
						.add(listaCuadrillasTmp.get(0).getEmployee()
								.getFirstName()
								+ " "
								+ listaCuadrillasTmp.get(0).getEmployee()
										.getLastName());
				printError = true;
			}
		}
		if (printError) {
			List<String> listNameList = new ArrayList<String>();
			StringBuffer listName = new StringBuffer();
			for (int i = 0; i < emError.size(); i++) {
				if (i == emError.size() - 1) {
					listName.append(emError.get(i));
				} else {
					listName.append(emError.get(i) + ",");
				}
			}
			listNameList.add(listName.toString());
			log.debug(ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW
					.getMessage(listNameList.toArray()));
			// throw new BusinessDetailException(
			// ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getCode(),
			// ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getMessage(),listNameList);
			throw new BusinessException(
					ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW
							.getCode(),
					ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW
							.getMessage(listNameList.toArray()), listNameList);

		}

		log.debug("== Crews Reglas de negocio validas ==");
	}

	private void fillRelationships(CrewVO cVO) throws BusinessException {
		try {
			log.debug("== Intentando llenar los empleados de la cuadrilla ==");
			cVO.setEmployeesCrew(employeeCrewBusiness
					.getEmployeesCrewByCrewID(cVO.getId()));
			cVO.setResponsable(fillResponableByCrewId(employeesCrewDAO
					.getEmployeeResponsibleByCrewId(cVO.getId())));
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CrewVO> getCrewsByDealerIdStartDateAndEndDate(Long dealerId,
			Date startDate, Date endDate) throws BusinessException {
		log
				.debug("== Inicia getCrewsByDealerIdStartDateAndEndDate/CrewsCRUDBean ==");
		try {
			List<Crew> crewsPojo = daoCrew
					.getCrewsByDealerIdStartDateAndEndDate(dealerId, startDate,
							endDate);
			List<CrewVO> resultList = UtilsBusiness.convertList(crewsPojo,
					CrewVO.class);
			for (CrewVO crewVO : resultList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getCrewsByDealerIdStartDateAndEndDate/CrewsCRUDBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CrewVO> getAllCrewsOnlyBasicInfo() throws BusinessException {
		try {
			List<Crew> crewPojo = daoCrew.getAllCrewsOnlyBasicInfo();
			List<CrewVO> resultList = UtilsBusiness.convertList(crewPojo,
					CrewVO.class);
			for (CrewVO crewVO : resultList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllCrews/CrewsCRUDBean ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CrewVO> getCrewsByVehicleId(long vehicleId)
			throws BusinessException {
		log.debug("== Inicia getCrewsByVehicleId/CrewsCRUDBean ==");
		try {
			List<Crew> crewPojo = daoCrew.getCrewsByVehicleId(vehicleId);
			List<CrewVO> resultList = UtilsBusiness.convertList(crewPojo,
					CrewVO.class);
			for (CrewVO crewVO : resultList) {
				crewVO.populateBean();
				this.fillRelationships(crewVO);
			}
			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByVehicleId/CrewsCRUDBean ==");
		}
	}

	/**
	 * Method: Retorna un listado de Crews por el id del Dealer
	 * 
	 * @param Long
	 *            dealerId
	 * @return - List<CrewVO>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CrewVO> getCrewsByDealerId(Long dealerId)
			throws BusinessException {

		log.debug("== Inicia getCrewsByDealerId/CrewsCRUDBean ==");
		try {

			List<CrewVO> resultList = new ArrayList<CrewVO>();
			List<CrewVO> resultListTmp;
			List<Crew> crewsList;

			WoAssignment woAssignment = null;
			Long amount = null;

			if (dealerId == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			crewsList = daoCrew.getCrewsByDealerId(dealerId);

			if (!crewsList.isEmpty()) {

				resultListTmp = UtilsBusiness.convertList(crewsList,
						CrewVO.class);

				for (CrewVO crewVo : resultListTmp) {
					woAssignment = new WoAssignment();
					woAssignment.setDealerId(dealerId);
					woAssignment.setCrewId(crewVo.getId());
					amount = (Long) woAssignmentDAOLocal
							.getAmountWoAssigment(woAssignment);
					crewVo.setAmountWoAssigment(amount);

					crewVo.populateBean();
					this.fillRelationships(crewVo);
					crewVo.setResponsable(UtilsBusiness.copyObject(
							EmployeeVO.class, employeesCrewDAO
									.getEmployeeResponsibleByCrewId(crewVo
											.getId())));
					resultList.add(crewVo);
				}
			}
			fillFullNameResponsable(resultList);
			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByDealerId/CrewsCRUDBean ==");
		}
	}

	private void fillFullNameResponsable(List<CrewVO> listVo) {

		if (listVo != null && !listVo.isEmpty()) {
			for (CrewVO crewVO : listVo) {
				crewVO.generateFullName();
			}
		}

	}

	@Override
	public List<CrewVO> getCrewsByResponsibleNameAndDealerId(
			String responsibleName, Long idDealer) throws BusinessException {

		log.debug("== Inicia getCrewsByDealerId/CrewsCRUDBean ==");
		try {

			List<Object[]> crews = this.daoCrew
					.getCrewsByResponsibleNameAndDealerId(responsibleName,
							idDealer);

			if (crews == null)
				return null;

			List<CrewVO> crewVOs = new ArrayList<CrewVO>();

			for (Object[] obj : crews) {
				CrewVO crewVO = UtilsBusiness.copyObject(CrewVO.class,
						(Crew) obj[0]);
				crewVO.setLoadWo((Long) obj[1]);
				crewVO.populateBean();
				this.fillRelationships(crewVO);
				crewVOs.add(crewVO);
			}

			return crewVOs;

		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByDealerId/CrewsCRUDBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal#
	 * getCrewsByDealerIdAndType(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<CrewVO> getCrewsByDealerIdAndType(Long dealerId, Long crewType)
			throws BusinessException {
		log.debug("== Inicia getCrewsByDealerIdAndType/CrewsCRUDBean ==");
		try {

			List<CrewVO> resultList = new ArrayList<CrewVO>();
			List<CrewVO> resultListTmp;
			List<Crew> crewsList;

			WoAssignment woAssignment = null;
			Long amount = null;

			if (dealerId == null || crewType == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			// crewsList = daoCrew.getCrewsByDealerIdAndType(dealerId,crewType);
			// 22-06-2011 jnova CC se consultan las cuadrillas que estan activas
			// y tienen un vehiculo activo
			crewsList = daoCrew.getActiveCrewsByDealerIdAndType(dealerId,
					crewType);

			if (!crewsList.isEmpty()) {

				resultListTmp = UtilsBusiness.convertList(crewsList,
						CrewVO.class);

				for (CrewVO crewVo : resultListTmp) {
					woAssignment = new WoAssignment();
					woAssignment.setDealerId(dealerId);
					woAssignment.setCrewId(crewVo.getId());
					amount = (Long) woAssignmentDAOLocal
							.getAmountWoAssigment(woAssignment);
					crewVo.setAmountWoAssigment(amount);

					crewVo.populateBean();
					Employee responsable = employeesCrewDAO
							.getEmployeeResponsibleByCrewId(crewVo.getId());
					if (responsable != null)
						crewVo
								.setResponsable(fillResponableByCrewId(responsable));

					resultList.add(crewVo);
				}
			}

			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getCrewsByDealerIdAndType/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByDealerIdAndType/CrewsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Le da valor la responsable de una cuadrilla elinanado informaicon
	 * innecesaria para asignacion de cuadrilla
	 * 
	 * @param crewId
	 * @return
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jnova
	 */
	private EmployeeVO fillResponableByCrewId(Employee responsable)
			throws BusinessException {
		log.debug("== Inicia fillResponableByCrewId/CrewsCRUDBean ==");
		try {
			EmployeeVO response = null;
			if (responsable != null) {
				response = UtilsBusiness.copyObject(EmployeeVO.class,
						responsable);
				response.setArp(null);
				response.setBirthCity(null);
				response.setCity(null);
				response.setContactType(null);
				response.setContractType(null);
				response.setDealer(null);
				response.setDocumentType(null);
				response.setEducationLevel(null);
				response.setEmployeeStatus(null);
				response.setEps(null);
				response.setPension(null);
				response.setPosition(null);
				response.setMaritalStatus(null);
			}
			return response;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación fillResponableByCrewId/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina fillResponableByCrewId/CrewsCRUDBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal#
	 * getCrewsByDealerIdAndTypeAndResponsableName(java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<CrewVO> getCrewsByDealerIdAndTypeAndResponsableName(
			Long dealerId, Long crewType, String responsableName)
			throws BusinessException {
		log
				.debug("== Inicia getCrewsByDealerIdAndTypeAndResponsableName/CrewsCRUDBean ==");
		try {

			List<CrewVO> crewsList = null;

			WoAssignment woAssignment = null;
			Long amount = null;

			if (dealerId == null || crewType == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			List<EmployeeCrew> employeeCrewList = employeesCrewDAO
					.getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(
							dealerId, CodesBusinessEntityEnum.BOOLEAN_TRUE
									.getCodeEntity(), crewType, responsableName);

			if (employeeCrewList != null && !employeeCrewList.isEmpty()) {
				crewsList = new ArrayList<CrewVO>();
				for (EmployeeCrew employeeCrewPojo : employeeCrewList) {
					CrewVO crewVo = UtilsBusiness.copyObject(CrewVO.class,
							employeeCrewPojo.getCrew());
					woAssignment = new WoAssignment();
					woAssignment.setDealerId(dealerId);
					woAssignment.setCrewId(crewVo.getId());
					amount = (Long) woAssignmentDAOLocal
							.getAmountWoAssigment(woAssignment);
					crewVo.setAmountWoAssigment(amount);
					crewVo.populateBean();
					crewVo
							.setResponsable(fillResponableByCrewId(employeeCrewPojo
									.getEmployee()));
					crewsList.add(crewVo);
				}
			}

			return crewsList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getCrewsByDealerIdAndTypeAndResponsableName/CrewsCRUDBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getCrewsByDealerIdAndTypeAndResponsableName/CrewsCRUDBean ==");
		}
	}

	/**
	 * Method: Retorna un listado de Crews por el id del Dealer con la carga de
	 * la cuadrilla
	 * 
	 * @param Long
	 *            dealerId
	 * @param Long
	 *            crewId
	 * @return - CrewVO
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public CrewVO getCrewAmountByDealerId(Long crewId, Long dealerId)
			throws BusinessException {

		log.debug("== Inicia getCrewAmountByDealerId/CrewsCRUDBean ==");
		try {

			CrewVO crewVo = new CrewVO();

			WoAssignment woAssignment = null;
			Long amount = null;

			if (crewId == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			if (dealerId == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			woAssignment = new WoAssignment();
			woAssignment.setDealerId(dealerId);
			woAssignment.setCrewId(crewId);
			amount = (Long) woAssignmentDAOLocal
					.getAmountWoAssigment(woAssignment);
			crewVo.setId(crewId);
			crewVo.setAmountWoAssigment(amount);

			crewVo.populateBean();
			this.fillRelationships(crewVo);

			return crewVo;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operaci�n getCrewAmountByDealerId/CrewsCRUDBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewAmountByDealerId/CrewsCRUDBean ==");
		}
	}

	@Override
	public List<CrewVO> getActiveCrewsByDealerId(Long dealerId)
			throws BusinessException {
		log.debug("== Inicia getCrewsByDealerId/CrewsCRUDBean ==");
		try {

			List<CrewVO> resultList = new ArrayList<CrewVO>();
			List<CrewVO> resultListTmp;
			List<Crew> crewsList;

			WoAssignment woAssignment = null;
			Long amount = null;

			if (dealerId == null)
				throw new BusinessException(
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),
						ErrorBusinessMessages.PARAMS_NULL_OR_MISSED
								.getMessage());

			crewsList = daoCrew.getActiveCrewsByDealerId(dealerId);

			if (!crewsList.isEmpty()) {

				resultListTmp = UtilsBusiness.convertList(crewsList,
						CrewVO.class);

				for (CrewVO crewVo : resultListTmp) {
					woAssignment = new WoAssignment();
					woAssignment.setDealerId(dealerId);
					woAssignment.setCrewId(crewVo.getId());
					amount = (Long) woAssignmentDAOLocal
							.getAmountWoAssigment(woAssignment);
					crewVo.setAmountWoAssigment(amount);
					crewVo.populateBean();
					this.fillRelationships(crewVo);
					crewVo.setResponsable(UtilsBusiness.copyObject(
							EmployeeVO.class, employeesCrewDAO
									.getEmployeeResponsibleByCrewId(crewVo
											.getId())));
					crewVo.generateFullName();
					resultList.add(crewVo);
				}
			}

			return resultList;
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewsByDealerId/CrewsCRUDBean ==");
		}
	}

	/**
	 * 
	 * Metodo: Retorna un listado de cuadrillas desactivadas
	 * 
	 * @param crewOff
	 *            CrewOff
	 * @return List<CrewOff>
	 * @throws BusinessException
	 * @author jalopez
	 */
	@Override
	public List<CrewOffVO> getCrewOffByCrew(CrewOff crewOff)
			throws BusinessException {
		log.debug("== Termina getCrewOffByCrew/CrewStatusCRUDBean ==");
		try {
			List<CrewOff> listCrewOff = crewsOffDAO.getCrewOffByCreId(crewOff);
			return UtilsBusiness.convertList(listCrewOff, CrewOffVO.class);
		} catch (Throwable ex) {
			log
					.debug(
							"== Error al tratar de ejecutar la operación getCrewOffByCrew/CrewStatusCRUDBean ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCrewOffByCrew/CrewStatusCRUDBean ==");
		}
	}
}
