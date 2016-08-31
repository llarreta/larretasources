package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;
import co.com.directv.sdii.model.pojo.EmployeeRetirement;
import co.com.directv.sdii.model.pojo.EmployeeStatus;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.Training;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;
import co.com.directv.sdii.model.vo.EmployeeRetirementVO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.model.vo.TrainingVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CitiesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeRetirementDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.TrainingDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Employee 
 * 
 * Fecha de Creacion: Mar 3, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Stateless(name="EmployeeCRUDBeanLocal",mappedName="ejb/EmployeeCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeCRUDBean extends BusinessBase implements EmployeeCRUDBeanLocal {

    @EJB(name = "EmployeeDAOLocal", beanInterface = EmployeeDAOLocal.class)
    private EmployeeDAOLocal daoEmployee;
    
    @EJB(name = "EmployeeMediaContactDAOLocal", beanInterface = EmployeeMediaContactDAOLocal.class)
    private EmployeeMediaContactDAOLocal daoEmployeeMediaContact;
    
    @EJB(name = "EmployeeRetirementDAOLocal", beanInterface = EmployeeRetirementDAOLocal.class)
    private EmployeeRetirementDAOLocal daoRetirement;
    
    @EJB(name = "TrainingDAOLocal", beanInterface = TrainingDAOLocal.class)
    private TrainingDAOLocal trainingDAO;
    
    @EJB(name = "EmployeesCrewDAOLocal", beanInterface = EmployeesCrewDAOLocal.class)
    private EmployeesCrewDAOLocal empCrewDAO;
    
    @EJB(name = "EmployeeVehicleDAOLocal", beanInterface = EmployeeVehicleDAOLocal.class)
    private EmployeeVehicleDAOLocal empVehicleDAO;
    
    @EJB(name = "EmployeeStatusDAOLocal", beanInterface = EmployeeStatusDAOLocal.class)
    private EmployeeStatusDAOLocal employeeStatusDAOLocal;
    
    @EJB(name = "CitiesDAOLocal", beanInterface = CitiesDAOLocal.class)
    private CitiesDAOLocal citiesDAO;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
	@EJB(name="ManageWorkForceServiceBrokerLocal",beanInterface=ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal manageWorkForceServiceBroker;
    
	@EJB(name="WarehouseDAOLocal",beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;
	
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeCRUDBean.class);

    /**
     *
     * Metodo: Hace el llamado al DAO para que guarde un colaborador en la BD
     * @param obj
     * @throws BusinessException 
     * @author jcasas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployee(EmployeeVO eVo) throws BusinessException {

        log.debug("== createEmployee/EmployeeCRUDBean ==");
        try {

            if (eVo == null) {
                log.error("Parametro obj nulo. No se puede crear Employee");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            UtilsBusiness.assertNotNull(eVo.getEmployeeStatus(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            UtilsBusiness.assertNotNull(eVo.getEmployeeStatus().getStatusCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            //no se esta enviando desde presentacion sino el status code del employeestatus entonces se obtiene el id asociado
            eVo.setEmployeeStatus(employeeStatusDAOLocal.getEmployeeStatusByCode(eVo.getEmployeeStatus().getStatusCode()));
            
            if( eVo.getMaritalStatus() != null && eVo.getMaritalStatus().getId().longValue() <= 0 ){
            	eVo.setMaritalStatus( null );
            }
            if( eVo.getEducationLevel() != null && eVo.getEducationLevel().getId().longValue() <= 0 ){
            	eVo.setEducationLevel( null );
            }
            if( eVo.getBirthCity() != null && eVo.getBirthCity().getId().longValue() <= 0 ){
            	eVo.setBirthCity( null );
            }
            if( eVo.getBirthDate() != null ){
            	Calendar cal = Calendar.getInstance();
            	cal.setTime( eVo.getBirthDate() );
            	if( cal.get(Calendar.DAY_OF_MONTH) == 1 && cal.get(Calendar.MONTH) == 0 && cal.get(Calendar.YEAR) == 1900  ){
            		eVo.setBirthDate( null );
            	}
            }
            	
            
            if (!BusinessRuleValidationManager.getInstance().isValid(eVo)) {
                log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            String documentNumber = eVo.getDocumentNumber();
			if (eVo.getIbsTechnical() != null) {

				City city = citiesDAO.getCitiesByID(eVo.getCity().getId());
				Employee empIbsTechnical = daoEmployee.getEmployeeByIbsTechnical(null, eVo.getIbsTechnical(),				city.getState().getCountry().getId());
				if (empIbsTechnical != null) {
					log.error("== El IbsTechnical del Empleado ya existe==");
					throw new BusinessException(ErrorBusinessMessages.DEALERS_DE017.getCode(),ErrorBusinessMessages.DEALERS_DE017.getMessage());
				}
			}
            Employee oldEmployee = daoEmployee.getEmployeeByDocumentNumberAndStatus(documentNumber,CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());

            if (oldEmployee != null) {
            	log.error("== Empleado existente en Dealer==");
                throw new BusinessException(ErrorBusinessMessages.EMPLOYEE_ACTIVE_ALREADY_EXIST.getCode(), ErrorBusinessMessages.EMPLOYEE_ACTIVE_ALREADY_EXIST.getMessage());
            } else{
            	
            	if(eVo.getPosition() != null 
            			&& eVo.getPosition().getNotifyIbs().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
            		eVo.setIbsTechnical(manageWorkForceServiceBroker.addTelecomTechnician(eVo));            		
            	}
            	
            	Employee employeePojo = new Employee(eVo);//UtilsBusiness.copyObject(Employee.class, eVo);
            	daoEmployee.createEmployee(employeePojo);
            	createMediaContactsForEmployee(eVo, employeePojo);
            	createTrainingsForEmployee(eVo, employeePojo);
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createEmployee/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployee/EmployeeCRUDBean ==");
        }
    }
/* 
 
 removeServiceFromWorkOrder
 
 **/
    /**
     * Crea los medios de contacto de un empleado
     * @param eVo - EmployeeVO
     * @param employeePojo - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @throws BusinessException
     */
	private void createMediaContactsForEmployee(EmployeeVO eVo,
			Employee employeePojo) throws DAOServiceException, DAOSQLException,
			BusinessException {
		//Creando los medios de contacto del empleado
		List<EmployeeMediaContactVO> contacts  = eVo.getEmployeesMediaContact();
		if(contacts != null && !contacts.isEmpty()){
			daoEmployeeMediaContact.deleteEmployeeMediaContactByEmployeeId(employeePojo.getId());
			for (EmployeeMediaContactVO emcVo : contacts) {
		    	EmployeeMediaContact emc = UtilsBusiness.copyObject(EmployeeMediaContact.class, emcVo);
		    	emc.setEmployee(employeePojo);
		    	daoEmployeeMediaContact.createEmployeeMediaContact(emc);
			}
		}
	}

	 /**
     * Crea las capacitaciones de un empleado
     * @param eVo - EmployeeVO
     * @param employeePojo - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @throws BusinessException
     */
	private void createTrainingsForEmployee(EmployeeVO eVo,
			Employee employeePojo) throws DAOServiceException, DAOSQLException,
			BusinessException {
		//Creando las capacitaciones del empleado
		List<TrainingVO> trainingVOs = eVo.getTrainingVOs();
		if(trainingVOs != null && !trainingVOs.isEmpty()){
			trainingDAO.deleteTrainingsByEmployeeId(employeePojo.getId());
			for (TrainingVO trainingVO : trainingVOs) {
				Training trainingPojo = UtilsBusiness.copyObject(Training.class, trainingVO);
				trainingPojo.setEmployee(employeePojo);
				trainingDAO.createTraining(trainingPojo);
			}
		}
	}

    /**
     * Hace el llamado al DAO para que elimine un colaborador en la BD
     * @param obj
     * @throws BusinessException 
     * @author jcasas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployee(EmployeeVO eVo) throws BusinessException {
        log.debug("== deleteEmployee/EmployeeCRUDBean ==");
        try {
            
        	daoRetirement.deleteEmployeeRetirementByEmployeeId(eVo.getId());
        	
        	daoEmployeeMediaContact.deleteEmployeeMediaContactByEmployeeId(eVo.getId());
        	
        	Employee employeePojo = UtilsBusiness.copyObject(Employee.class, eVo);
        	
        	trainingDAO.deleteTrainingsByEmployeeId(eVo.getId());
        	
        	List<EmployeeCrew> empCrews = empCrewDAO.getEmployeesCrewByEmpId(eVo.getId());
        	
        	if(! empCrews.isEmpty()){
        		String message = "No se puede eliminar el colaborador con id: " + eVo.getId() + " porque existen " + empCrews.size() +  " cuadrillas en las que participa, primero debe remover el empleado de todas las cuadrillas en las que esté asociado";
            	log.error(message);
            	throw new BusinessException(ErrorBusinessMessages.EMPLOYEE_HAS_RELATED_DATA.getCode(), ErrorBusinessMessages.EMPLOYEE_HAS_RELATED_DATA.getMessage() + message);
        	}
        	
        	EmployeeVehicle empVehicle = empVehicleDAO.getEmployeeVehicleByEmployeeId(eVo.getId());
        	
        	if(empVehicle != null){
        		String message = "No se puede eliminar el colaborador con id: " + eVo.getId() + " porque está relacionado al vehículo de placas " + empVehicle.getVehicle().getPlate() +  ", para eliminar el empleado primero debe remover el empleado de ese vehículo";
            	log.error(message);
            	throw new BusinessException(ErrorBusinessMessages.EMPLOYEE_HAS_RELATED_DATA.getCode(), ErrorBusinessMessages.EMPLOYEE_HAS_RELATED_DATA.getMessage() + message);
        	}
            daoEmployee.deleteEmployee(employeePojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteEmployee/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployee/EmployeeCRUDBean ==");
        }
    }

    /**
     * Metodo: Obtiene todos los emplados existentes
     * @return - List<EmployeeVO>
     * @throws BusinessException 
     * @author jcasas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getAllEmployee() throws BusinessException {
        log.debug("== Inicia getAllEmployee/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getAllEmployee();
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllEmployee/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DealersCRUDBean ==");
        }
    }
    
    /**
     * Metodo: Obtiene todos los emplados existentes
     * @return - List<EmployeeVO>
     * @throws BusinessException 
     * @author jcasas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getAllEmployeeOnlyBasicInfo() throws BusinessException {
        log.debug("== Inicia getAllEmployee/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getAllEmployee();
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);
            
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllEmployeeOnlyBasicInfo/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DealersCRUDBean ==");
        }
    }

    /**
     * Obtiene un empleado con el id especificado
     * @param id - Long
     * @return - EmployeeVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByID(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeeByID/EmployeeCRUDBean ==");
        try {
            Employee employeePojo = daoEmployee.getEmployeeByID(id);

            if (employeePojo == null) {
            	log.debug("Employee no encontrado");
                throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            EmployeeVO employee = UtilsBusiness.copyObject(EmployeeVO.class, employeePojo);
            //employee.populateBean();
            fillRelationshipsData(employee);

            return employee;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerByDepotID/DealersCRUDBean ==");
        }
    }

    /**
     * Actualiza un empleado
     * @param eVo - EmployeeVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployee(EmployeeVO eVo) throws BusinessException {
        log.debug("== Inicia updateEmployee/EmployeeCRUDBean ==");
        try {
            if (eVo == null) {
                log.debug("Parametro obj nulo. No se puede actualizar Employee");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            UtilsBusiness.assertNotNull(eVo.getEmployeeStatus(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            UtilsBusiness.assertNotNull(eVo.getEmployeeStatus().getStatusCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            //no se esta enviando desde presentacion sino el status code del employeestatus entonces se obtiene el id asociado
            eVo.setEmployeeStatus(employeeStatusDAOLocal.getEmployeeStatusByCode(eVo.getEmployeeStatus().getStatusCode()));
            //gfandino - 23/05/2011 Agrega: Valida que si el Empleado pasa de estado inactivo a activo; no exista otro activo con el mismo
            //Número de identificación
            
            
            /*List<EmployeeCrew> employeeCrews = empCrewDAO.getEmployeesCrewByEmpIdAndCrewStatus(eVo.getId(), CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            for(EmployeeCrew empCrew : employeeCrews){
            	if(empCrew.getIsResponsible().equalsIgnoreCase(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity())){					
                	List<SystemParameter> optimusSP = systemParameterDAO.getSysParamsByCode(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity());
                	if(optimusSP != null && optimusSP.size() > 0 && optimusSP.get(0) != null
                			&& optimusSP.get(0).getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
                		UtilsBusiness.assertNotNull(eVo.getPin(), ErrorBusinessMessages.CORE_CR106.getCode(), ErrorBusinessMessages.CORE_CR106.getMessage());
                	}
                }
            }*/
            
            Employee empTmp = this.daoEmployee.getEmployeeByID(eVo.getId());
            //Valida que exista
            if (empTmp == null) {
                log.debug("Parametro obj nulo. No se puede actualizar Employee");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            //Si el Employee actual se encuentra en estado inactivo el ingresado por parametro viene en estado activo
            if(empTmp.getEmployeeStatus().getStatusCode().equals(CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_INACTIVE.getCodeEntity()) 
               && eVo.getEmployeeStatus().getStatusCode().equals(CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity())){
            	//Consulta si existen empleados activos actualmente en la BD con el misno número de documento
            	List<Employee> listEmployeeTmp = this.daoEmployee.getEmployeeByDocumentNumberAnDocTypeAndStatus(eVo.getDocumentNumber(), eVo.getDocumentType().getId(), CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());
            	//Si ya existe un empleado activo asociado a ese número de documento lanza la excepción
            	if(listEmployeeTmp.size()>0){
            		throw new BusinessException(ErrorBusinessMessages.EMPLOYEE_ACTIVE_ALREADY_EXIST.getCode(), ErrorBusinessMessages.EMPLOYEE_ACTIVE_ALREADY_EXIST.getMessage());
            	}
            }
            
            if (!BusinessRuleValidationManager.getInstance().isValid(eVo)) {
                log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            if( eVo.getMaritalStatus() != null && eVo.getMaritalStatus().getId().longValue() <= 0 ){
            	eVo.setMaritalStatus( null );
            }
            if( eVo.getEducationLevel() != null && eVo.getEducationLevel().getId().longValue() <= 0 ){
            	eVo.setEducationLevel( null );
            }
            if( eVo.getBirthCity() != null && eVo.getBirthCity().getId().longValue() <= 0 ){
            	eVo.setBirthCity( null );
            }
            if( eVo.getBirthDate() != null ){
            	Calendar cal = Calendar.getInstance();
            	cal.setTime( eVo.getBirthDate() );
            	if( cal.get(Calendar.DAY_OF_MONTH) == 1 && cal.get(Calendar.MONTH) == 0 && cal.get(Calendar.YEAR) == 1900  ){
            		eVo.setBirthDate( null );
            	}
            }
            if(eVo.getIbsTechnical()!=null){
                City city = citiesDAO.getCitiesByID(eVo.getCity().getId());
                Employee empIbsTechnical = daoEmployee.getEmployeeByIbsTechnical(eVo.getId(), eVo.getIbsTechnical(), city.getState().getCountry().getId());
                if(empIbsTechnical != null){
                	log.error("== El IbsTechnical del Empleado ya existe==");
                	throw new BusinessException(ErrorBusinessMessages.DEALERS_DE017.getCode(), ErrorBusinessMessages.DEALERS_DE017.getMessage());
                }
            }

            /*IN375115 si se editó el nombre del empleado entonces controlar*/
            if(!empTmp.getFirstName().equals(eVo.getFirstName()) || !empTmp.getLastName().equals(eVo.getLastName())){
					 String oldNameEmp = empTmp.getFirstName().trim().replace(" ","_")+"_"+empTmp.getLastName().trim().replace(" ", "_");
					 String newNameEmp = eVo.getFirstName().trim().replace(" ","_")+"_"+eVo.getLastName().trim().replace(" ", "_");
				 oldNameEmp = oldNameEmp.toUpperCase();
				 newNameEmp = newNameEmp.toUpperCase();
				 List<EmployeeCrew> empCrewActive = empCrewDAO.getEmployeesCrewByEmpIdAndCrewStatus(empTmp.getId(), CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            	for(EmployeeCrew empCrew : empCrewActive){
            		if( empCrew.getIsResponsible().equals(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity()) ){
            			Object params[] = {empTmp.getId()};
            			throw new BusinessException(ErrorBusinessMessages.EMPLOYEE_IS_ASSOCIATED_ACTIVE_CREW.getMessage(params));
            		}
            	}
            	List<EmployeeCrew> empCrewList = empCrewDAO.getEmployeesCrewByEmpId(empTmp.getId());
            	for(EmployeeCrew employeeCrew : empCrewList){
            		if(employeeCrew.getIsResponsible().equals(CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity())){
            			 List<Warehouse> warehouseList = warehouseDAO.getWarehousesByCrewId(employeeCrew.getCrew().getId());
            			 for(Warehouse warehouse : warehouseList){
            				 String newWhCode = warehouse.getWhCode().replaceAll(oldNameEmp,newNameEmp);
            				 warehouse.setWhCode(newWhCode);
            				 warehouseDAO.updateWarehouse(warehouse);
            			 }
            		}
            	}
            }	
            
            boolean hasNotifyPosition = eVo.getPosition() != null && eVo.getPosition().getNotifyIbs().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            boolean hasIbsTechnicalCode = eVo.getIbsTechnical() != null;
        	
            if(hasNotifyPosition && hasIbsTechnicalCode){
        		manageWorkForceServiceBroker.editTelecomTechnician(eVo);
        	}else if (hasNotifyPosition && !hasIbsTechnicalCode){
        		eVo.setIbsTechnical(manageWorkForceServiceBroker.addTelecomTechnician(eVo));
        	}
        	
            Employee employeePojo = new Employee(eVo);//UtilsBusiness.copyObject(Employee.class, eVo);
            daoEmployee.updateEmployee(employeePojo);
            
            createMediaContactsForEmployee(eVo, employeePojo);
            createTrainingsForEmployee(eVo, employeePojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateEmployee/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployee/EmployeeCRUDBean ==");
        }
    }

    /**
     * Obtiene todos los empleados pertenecientes a un Dealer
     * @param id - Id del Dealer
     * @return - List<Employee>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeVO> getEmployeesByDealerId(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeesByDealerId/EmployeeCRUDBean ==");
        try {

            List<Employee> employeeList = daoEmployee.getEmployeesByDealerId(id);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);
            for (EmployeeVO employeeVO : employeeVoList) {
                //employeeVO.populateBean();
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeesByDealerId/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesByDealerId/EmployeeCRUDBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal#getEmployeesActiveByDealerId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EmployeeVO> getEmployeesActiveByDealerId(Long id)
			throws BusinessException {
    	log.debug("== Inicia getEmployeesActiveByDealerId/EmployeeCRUDBean ==");
        try {

            List<Employee> employeeList = daoEmployee.getEmployeesByDealerIdAndStatus(id,CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);
            for (EmployeeVO employeeVO : employeeVoList) {
                //employeeVO.populateBean();
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeesActiveByDealerId/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesActiveByDealerId/EmployeeCRUDBean ==");
        }
	}
    
    /**
     * Obtiene un empleado con el tipo y numero de documento especificado,
     * filtra por el dealer si este es diferente de null
     * @param idDocType - Id del tipo de documento
     * @param documentNumber - Numero de documento
     * @param Long dealerId
     * @return - Employee
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws BusinessException {
        log.debug("== Inicio getEmployeeByDocTypeAndDocNum/EmployeeCRUDBean ==");

        try {

            if (idDocType == null) {
                log.error("Parametro idDocType nulo");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            if (documentNumber == null || "".equals(documentNumber)) {
                log.error("Parametro documentNumber nulo o vacio");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            Employee employee = daoEmployee.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber,dealerId);

            if (employee == null) {
                return null;
            }

            EmployeeVO eVO = UtilsBusiness.copyObject(EmployeeVO.class, employee);
            fillRelationshipsData(eVO);
            return eVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByDocTypeAndDocNum/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocTypeAndDocNum/EmployeeCRUDBean ==");
        }
    }
    
    /**
     * Obtiene un empleado con el tipo y numero de documento especificado
     * @param idDocType - Id del tipo de documento
     * @param documentNumber - Numero de documento    
     * @return - Employee
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws BusinessException {
        log.debug("== Inicio getEmployeeByDocTypeAndDocNum/EmployeeCRUDBean ==");

        try {

            if (idDocType == null) {
                log.error("Parametro idDocType nulo");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            if (documentNumber == null || "".equals(documentNumber)) {
                log.error("Parametro documentNumber nulo o vacio");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            Employee employee = daoEmployee.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber);

            if (employee == null) {
                return null;
            }

            EmployeeVO eVO = UtilsBusiness.copyObject(EmployeeVO.class, employee);
            fillRelationshipsData(eVO);
            return eVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByDocTypeAndDocNum/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocTypeAndDocNum/EmployeeCRUDBean ==");
        }
    }

    /**
     * Registra un retiro de un empleado 
     * @param employeeVO - EmployeeVO
     * @param description - String 
     * @throws BusinessException
     * @author - jcasas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void retireEmployee(EmployeeVO employeeVO, String description) throws BusinessException{
		 log.debug("== Inicio retireEmployee/EmployeeCRUDBean ==");
	     try {
	    	 if(employeeVO == null){
	    		 log.error("Parametro employeeVO nulo");
	                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    	 }
	    	 
            UtilsBusiness.assertNotNull(employeeVO.getEmployeeStatus(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            UtilsBusiness.assertNotNull(employeeVO.getEmployeeStatus().getStatusCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            //no se esta enviando desde presentacion sino el status code del employeestatus entonces se obtiene el id asociado
            employeeVO.setEmployeeStatus(employeeStatusDAOLocal.getEmployeeStatusByCode(employeeVO.getEmployeeStatus().getStatusCode()));
            //gfandino - 23/05/2011 Agregar: Validación que el empleado exista
	    	 Employee employee = UtilsBusiness.copyObject(Employee.class, employeeVO);
	    	 if(employee == null){
	    		 log.error("Parametro employeeVO nulo");
	                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    	 }
	    	 
	    	 //gfandino - 23/05/2011 Agregar: Consulta si el empleado tiene un vehiculo asignado
	    	 EmployeeVehicle employeeVehicle = this.empVehicleDAO.getEmployeeVehicleByEmployeeId(employee.getId());
	    	 boolean empVehicleTmp =  employeeVehicle!=null;
	    	 boolean empCrewTmp = this.empCrewDAO.getEmployeesCrewByEmpIdAndCrewStatus(employee.getId(),CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity()).size()>0;
	    	 if(empVehicleTmp&&empCrewTmp){
	    		 Object params[] = {employeeVehicle.getVehicle().getPlate()};
	    		 List<String> listParams = new ArrayList<String>();
	    		 listParams.add( employeeVehicle.getVehicle().getPlate() );
	    		 throw new BusinessException (ErrorBusinessMessages.EMPLOYEE_HAS_A_VEHICLE_AND_CREW.getCode(), ErrorBusinessMessages.EMPLOYEE_HAS_A_VEHICLE_AND_CREW.getMessage(params),listParams);
	    	 }else{
	    		 if(empVehicleTmp){
	    			 Object params[] = {employeeVehicle.getVehicle().getPlate()};
	    			 List<String> listParams = new ArrayList<String>();
		    		 listParams.add( employeeVehicle.getVehicle().getPlate() );
	    			 throw new BusinessException (ErrorBusinessMessages.EMPLOYEE_HAS_A_VEHICLE.getCode(), ErrorBusinessMessages.EMPLOYEE_HAS_A_VEHICLE.getMessage(params));
	    		 }
	    		 if(empCrewTmp){
	    			 throw new BusinessException (ErrorBusinessMessages.EMPLOYEE_HAS_A_CREW.getCode(), ErrorBusinessMessages.EMPLOYEE_HAS_A_CREW.getMessage());
	    		 }
	    	 }
	    	 

	    	 EmployeeStatus employeeStatus = this.employeeStatusDAOLocal.getEmployeeStatusByCode(CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_INACTIVE.getCodeEntity());
	    	 employee.setEmployeeStatus(employeeStatus);
	    	 
	    	 if( employee.getMaritalStatus() != null && employee.getMaritalStatus().getId().longValue() <= 0 ){
	    		 employee.setMaritalStatus( null );
	         }
	         if( employee.getEducationLevel() != null && employee.getEducationLevel().getId().longValue() <= 0 ){
	        	 employee.setEducationLevel( null );
	         }
	         if( employee.getBirthCity() != null && employee.getBirthCity().getId().longValue() <= 0 ){
	        	 employee.setBirthCity( null );
	         }
	         if( employee.getBirthDate() != null ){
	        	Calendar cal = Calendar.getInstance();
	            cal.setTime( employee.getBirthDate() );
	            if( cal.get(Calendar.DAY_OF_MONTH) == 1 && cal.get(Calendar.MONTH) == 0 && cal.get(Calendar.YEAR) == 1900  ){
	            	employee.setBirthDate( null );
	            }
	         }
	    	 if(employee.getIbsTechnical()!=null
	    			 &&employee.getIbsTechnical().longValue() ==0L){
	    		 employee.setIbsTechnical(null);
	    	 }
	    	 
	    	 employeeVO.setEmployeeStatus(employeeStatus);
	    	 
	    	 if(employeeVO.getPosition() != null 
	    			 && employeeVO.getPosition().getNotifyIbs().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){	    		 
	    		 manageWorkForceServiceBroker.editTelecomTechnician(employeeVO);
	    	 }
	    	 
	    	 this.daoEmployee.updateEmployee(employee);
	    	 
	    	 EmployeeRetirement retirement = new EmployeeRetirement();
	    	 retirement.setEmployee(employee);
	    	 retirement.setRetirementDate(UtilsBusiness.fechaActual());
	    	 retirement.setRetirementDescription(description);
	    	
	    	 this.daoRetirement.createEmployeeRetirement(retirement);
	    	 
	     } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación retireEmployee/ConfigMatrizCoberturaBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	         log.debug("== Termina retireEmployee/EmployeeCRUDBean ==");
	     }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByDocumentNumber(String documentNumber)
			throws BusinessException {
		log.debug("== Inicia getEmployeeByDocumentNumber/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeeByDocumentNumber(documentNumber);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByDocumentNumber/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentNumber/DealersCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByDocumentTypeId(Long typeId)
			throws BusinessException {
		log.debug("== Inicia getEmployeeByDocumentTypeId/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeeByDocumentTypeId(typeId);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByDocumentTypeId/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentTypeId/DealersCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByFirstName(String firstName)
			throws BusinessException {
		log.debug("== Inicia getEmployeeByFirstName/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeeByFirstName(firstName);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByFirstName/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByFirstName/DealersCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByLastName(String lastName)
			throws BusinessException {
		log.debug("== Inicia getEmployeeByLastName/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeeByLastName(lastName);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByLastName/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByLastName/DealersCRUDBean ==");
        }
	}
    
    
    private void fillRelationshipsData(EmployeeVO eVO) throws BusinessException {
        log.debug(" ==Llenando datos adyacentes al empleado ==");

        eVO.setPositionId(eVO.getPosition().getId());
        eVO.setPositionCode(eVO.getPosition().getPositionCode());
        eVO.setPositionName(eVO.getPosition().getPositionName());
        eVO.setPositionNotifyIbs(eVO.getPosition().getNotifyIbs());
        eVO.setPosition(null); //Null para no enviar esa info al ws
        
        eVO.setDealerId(eVO.getDealer().getId());
        eVO.setDealerCode(eVO.getDealer().getDealerCode());
        eVO.setDealerName(eVO.getDealer().getDealerName());
        eVO.setDealerDepotCode( eVO.getDealer().getDepotCode() );
        eVO.setDealer(null);
        
        List<EmployeeMediaContact> contacts;
        List<Training> trainings;
        List<EmployeeRetirement> retirament;
        try {
            contacts = this.daoEmployeeMediaContact.getEmployeeMediaContactByEmployeeId(eVO.getId());
            if (contacts != null) {
                eVO.setEmployeesMediaContact(UtilsBusiness.convertList(contacts, EmployeeMediaContactVO.class));
            }
            
            trainings = this.trainingDAO.getTrainingsByEmployeeId(eVO.getId());
            if(trainings != null){
            	eVO.setTrainingVOs(UtilsBusiness.convertList(trainings, TrainingVO.class));
            }
            
            retirament = this.daoRetirement.getEmployeeRetirementByEmployee(eVO.getId());
            if(retirament != null && retirament.size()>0){
            	eVO.setLastRetirament(UtilsBusiness.copyObject(EmployeeRetirementVO.class,retirament.get(0)));
            }
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación fillRelationshipsData/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina fillRelationshipsData/DealersCRUDBean ==");
        }
        
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName,
			String lastName) throws BusinessException {
		log.debug("== Inicia getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(typeId, documentNumber, firstName, lastName);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
                fillRelationshipsData(employeeVO);
            }
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN/DealersCRUDBean ==");
        }
	}
		
	/**
	 * Obtiene todos los documentos existentes de forma ordenada
	 * @param Long dealerID
	 * @return List<EmployeeVO> Listado de numero de documentos debidamente ordenados 
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeVO> getEmployeesDocumentsByDealer(Long dealerID) throws BusinessException{
		log.debug("== Inicia getDocumentsOrdered/EmployeeCRUDBean ==");
        try {
            List<Employee> employeeList = daoEmployee.getEmployeesDocumentsByDealer(dealerID);
            if (employeeList == null) {
                return null;
            }
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(employeeList, EmployeeVO.class);
            
            Collections.sort(employeeVoList);
            return employeeVoList;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeesDocumentsByDealer/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDocumentsOrdered/DealersCRUDBean ==");
        }
	}		
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public EmployeePaginationResponse getEmployeeByChriteria(Long typeId, String documentNumber, String firstName,
			String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getEmployeeByChriteria/EmployeeCRUDBean ==");
        try {
        	if( countryId == null || countryId <= 0L ){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	EmployeePaginationResponse response = daoEmployee.getEmployeeByChriteria(typeId, documentNumber, firstName, lastName, depotCode, dealerCode,dealerId,countryId,requestCollInfo); 
        	
            List<EmployeeVO> employeeVoList = UtilsBusiness.convertList(response.getEmployeeList(), EmployeeVO.class);

            for (EmployeeVO employeeVO : employeeVoList) {
            	fillDataForGetEmployeeByChriteria(employeeVO);
            }
            response.setEmployeeVOList(employeeVoList);
            response.setEmployeeList(null);
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getEmployeeByChriteria/EmployeeCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByChriteria/EmployeeCRUDBean ==");
        }
	}
	
	/**
	 * Metodo: elimina informacion innecesaria del objeto employee para ser enviada en una consulta
	 * @param eVO
	 */
	private void fillDataForGetEmployeeByChriteria(EmployeeVO eVO){
		log.debug(" ==Inicia fillDataForGetEmployeeByChriteria/EmployeeCRUDBean ==");
		
		eVO.setDocumentType(null);
		eVO.setPension(null);
		eVO.setEps(null);
		eVO.setCity(null);
		eVO.setDealerId(eVO.getDealer().getId());
		eVO.setDealerCode(eVO.getDealer().getDealerCode());
        eVO.setDealerName(eVO.getDealer().getDealerName());
        eVO.setPositionId(eVO.getPosition().getId());
        eVO.setPositionCode(eVO.getPosition().getPositionCode());
        eVO.setPositionName(eVO.getPosition().getPositionName());
        eVO.setPosition(null);
        eVO.setDealer(null);
        eVO.setContactType(null);
        eVO.setContractType(null);
        eVO.setArp(null);		
        
		log.debug(" ==Termina fillDataForGetEmployeeByChriteria/EmployeeCRUDBean ==");
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal#getEmployeeDetailsByEmployeeId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public EmployeeVO getEmployeeDetailsByEmployeeId(Long employeeId) throws BusinessException{
		log.debug(" ==Inicia getEmployeeDetailsByEmployeeId/EmployeeCRUDBean ==");
		try{
			if( employeeId == null || employeeId <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			EmployeeVO employeeVO = UtilsBusiness.copyObject(EmployeeVO.class, daoEmployee.getEmployeeByID(employeeId));
			fillRelationshipsDataForDetailsById(employeeVO);
			return employeeVO;
		} catch (Throwable e){
			log.error(" ==Error getEmployeeDetailsByEmployeeId/EmployeeCRUDBean ==");
			throw this.manageException(e);
		} finally {
			log.debug(" ==Termina getEmployeeDetailsByEmployeeId/EmployeeCRUDBean ==");
		}
	}
	
	private void fillRelationshipsDataForDetailsById(EmployeeVO eVO) throws BusinessException {
        log.debug(" ==Llenando datos adyacentes al empleado ==");

        eVO.setPositionId(eVO.getPosition().getId());
        eVO.setPositionCode(eVO.getPosition().getPositionCode());
        eVO.setPositionName(eVO.getPosition().getPositionName());
        eVO.setPosition(null); //Null para no enviar esa info al ws
        
        eVO.setDealerId(eVO.getDealer().getId());
        eVO.setDealerCode(eVO.getDealer().getDealerCode());
        eVO.setDealerName(eVO.getDealer().getDealerName());
        eVO.setDealerDepotCode( eVO.getDealer().getDepotCode() );
        eVO.setDealer(null);
        
        List<EmployeeMediaContact> contacts;
        List<Training> trainings;
        try {
            contacts = this.daoEmployeeMediaContact.getEmployeeMediaContactByEmployeeId(eVO.getId());
            if (contacts != null) {
                eVO.setEmployeesMediaContact(UtilsBusiness.convertList(contacts, EmployeeMediaContactVO.class));
            }
            
            trainings = this.trainingDAO.getTrainingsByEmployeeId(eVO.getId());
            if(trainings != null){
            	List<TrainingVO> trainingsVO = new ArrayList<TrainingVO>();
            	for( Training t : trainings ){
            		t.setEmployee(new Employee(t.getEmployee().getId()));
            		trainingsVO.add( UtilsBusiness.copyObject(TrainingVO.class, t));
            	}            	
            	eVO.setTrainingVOs(trainingsVO);
            }
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación fillRelationshipsData/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina fillRelationshipsData/DealersCRUDBean ==");
        }
        
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal#getActiveTechniciansQtyByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long getActiveTechniciansQtyByDealerId(Long dealerId)
			throws BusinessException {
		log.debug(" ==Inicia getActiveTechniciansQtyByDealerId/EmployeeCRUDBean ==");
		try{
			Long techQty = daoEmployee.getActiveTechniciansQtyByDealerId(dealerId);
			return techQty;
		} catch (Throwable e){
			log.error(" ==Error getActiveTechniciansQtyByDealerId/EmployeeCRUDBean ==");
			throw this.manageException(e);
		} finally {
			log.debug(" ==Termina getActiveTechniciansQtyByDealerId/EmployeeCRUDBean ==");
		}
	}

	
}
