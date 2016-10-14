package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.Date;
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
import co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.EmployeeVehicleId;
import co.com.directv.sdii.model.pojo.Vehicle;
import co.com.directv.sdii.model.pojo.VehicleStatus;
import co.com.directv.sdii.model.vo.VehicleVO;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Vehicles
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal
 * 
 */
@Stateless(name="VehiclesCRUDBeanLocal",mappedName="ejb/VehiclesCRUDBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VehiclesCRUDBean extends BusinessBase implements VehiclesCRUDBeanLocal {

    @EJB(name="VehiclesDAOLocal",beanInterface=VehiclesDAOLocal.class)
    private VehiclesDAOLocal dAOVehicleBean;

    @EJB(name="EmployeeVehicleDAOLocal",beanInterface=EmployeeVehicleDAOLocal.class)
    private EmployeeVehicleDAOLocal employeeVehicleDAO;
    
    @EJB(name="CrewsDAOLocal",beanInterface=CrewsDAOLocal.class)
    private CrewsDAOLocal crewsDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(VehiclesCRUDBean.class);

    /**
     * Crea un vehiculo en el sistema
     * @param obj - Vehicles
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createVehicles(VehicleVO obj) throws BusinessException {

        log.debug("== Inicio createVehicle/VehicleCRUDBean ==");
        try {
            
        	if (obj == null) {
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
        	
            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            List<Vehicle> bdVehicles = dAOVehicleBean.getVehicleByPlateAndStatus(obj.getPlate(),CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity()); 
            if ( bdVehicles != null && !bdVehicles.isEmpty()) {
            	Object params[] = {obj.getPlate(),bdVehicles.get(0).getDealer().getDealerName()};
                throw new BusinessException(ErrorBusinessMessages.DEALERS_DE012.getCode(),ErrorBusinessMessages.DEALERS_DE012.getMessage(params));
            }
            Vehicle newVehicle = UtilsBusiness.copyObject(Vehicle.class, obj);
            dAOVehicleBean.createVehicle(newVehicle);
            obj.setId(newVehicle.getId());

            deleteVehicleDriver(newVehicle);
            saveVehicleDriver(obj);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion createVehicles/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createVehicle/VehicleCRUDBean ==");
        }

    }

    /**
     * Elimina un vehiculo del sistema
     * @param obj Vehicle
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteVehicles(VehicleVO obj) throws BusinessException {
        log.debug("== Inicio deleteVehicles/VehicleCRUDBean ==");
        try {
            if (obj == null) {
            	log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                //throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),"No se puede borrar el  Vehicle");
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            Vehicle vehicle = UtilsBusiness.copyObject(Vehicle.class, obj);

            deleteVehicleDriver(vehicle);
            dAOVehicleBean.deleteVehicle(vehicle);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deleteVehicles/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteVehicles/VehicleCRUDBean ==");
        }
    }

    /**
     * Metodo: Consultar todos los Vehiculos
     * @return List<Vehicle>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<VehicleVO> getAllVehicles() throws BusinessException {

        log.debug("== Inicio getAllVehicles/VehicleCRUDBean ==");
        try {
            List<VehicleVO> listVehiclesVO = UtilsBusiness.convertList(dAOVehicleBean.getAllVehicle(), VehicleVO.class);
            
            for(VehicleVO vehicle : listVehiclesVO){
                fillVehicleRelationshipNames(vehicle);
            }

            return listVehiclesVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehicles/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehicles/VehicleCRUDBean ==");
        }
    }

    /**
     * Obtiene un vehiculo por el id especificado
     * @param id - Long
     * @return - Vehicles
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleVO getVehiclesByID(Long id) throws BusinessException {
    	log.debug("== Inicia getVehiclesByID/VehicleCRUDBean ==");
        try {
            if (id == null) {
            	log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                //throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode() ,"Parametro id no especificado", new IllegalArgumentException("Param Id"));
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            Vehicle vehicle = dAOVehicleBean.getVehicleByID(id);

            if (vehicle == null) {
                return null;
            }

            VehicleVO vehicleVO = UtilsBusiness.copyObject(VehicleVO.class, vehicle);

            fillVehicleRelationshipNames(vehicleVO);

            return vehicleVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehiclesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByID/VehicleCRUDBean ==");
        }
    }

    /**
     * Actualizar un vehiculo 
     * @param obj - Vehicle
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateVehicles(VehicleVO obj) throws BusinessException {
        try {
        	//1. Si el vehiculo esta activo y se va a inactivar:
        		//1.1 Se valida que no este asociado a una cuadrilla activa
        		//1.2 Se desvincula el colaborador del vehiculo
        		//1.3 Se pone en inactivo el estado del vehiculo
        	//2. Si el vehiculo esta inactivo y se va a activar
        		//2.1 Se modifica el estado del vehiculo
        		//2.2 Se asocia el colaborador al vehiculo
        	log.debug("== Inicia updateVehicles/VehicleCRUDBean ==");
            if (obj == null || obj.getId() == null || obj.getId().longValue() <= 0
            		|| obj.getVehicleStatus() == null || obj.getVehicleStatus().getId() == null
            		|| obj.getVehicleStatus().getId().longValue() <= 0) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            Vehicle vehiclePojo = dAOVehicleBean.getVehicleByID( obj.getId() );
            //1.
            Long inactiveStatusId = CodesBusinessEntityEnum.INACTIVE_VEHICLE.getIdEntity( VehicleStatus.class.getName() );
            if( vehiclePojo.getVehicleStatus().getStatusCode().equals(CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity())){
            	if( obj.getVehicleStatus().getId().equals( inactiveStatusId ) ){
            		List<Crew> crews = crewsDAO.getCrewsByVehicleIdAndCrewStatusCode(obj.getId(), CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
                	//1.1
                	if( crews != null && !crews.isEmpty() ){
                		throw new BusinessException(ErrorBusinessMessages.DEALERS_DE013.getCode(),ErrorBusinessMessages.DEALERS_DE013.getMessage());
                	}
                	//1.2
                	deleteVehicleDriver(vehiclePojo);
                	//1.3
                	VehicleStatus status = new VehicleStatus();
                	status.setId( inactiveStatusId );
                	vehiclePojo.setVehicleStatus(status);
                	dAOVehicleBean.updateVehicle( vehiclePojo );
            	} else{
            		deleteVehicleDriver(vehiclePojo);
            		saveVehicleDriver(obj);
            	}
            //2.
            }else if( vehiclePojo.getVehicleStatus().getStatusCode().equals(CodesBusinessEntityEnum.INACTIVE_VEHICLE.getCodeEntity())){
            	Long activeStats =CodesBusinessEntityEnum.ACTIVE_VEHICLE.getIdEntity( VehicleStatus.class.getName() );
            	if( obj.getVehicleStatus().getId().equals( activeStats ) ){
            		List<Vehicle> bdVehicles = dAOVehicleBean.getVehicleByPlateAndStatus(obj.getPlate(),CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity()); 
                    if ( bdVehicles != null && !bdVehicles.isEmpty()) {
                    	Object params[] = {obj.getPlate(),bdVehicles.get(0).getDealer().getDealerName()};
                        throw new BusinessException(ErrorBusinessMessages.DEALERS_DE012.getCode(),ErrorBusinessMessages.DEALERS_DE012.getMessage(params));
                    }
            		//2.1
            		VehicleStatus status = new VehicleStatus();
                	status.setId( activeStats );
                	vehiclePojo.setVehicleStatus(status);
                	dAOVehicleBean.updateVehicle( vehiclePojo );
                	//2.2
            		saveVehicleDriver(obj);
            	} else {
            		deleteVehicleDriver(vehiclePojo);
            	}
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion updateVehicles/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateVehicles/VehicleCRUDBean ==");
        }
    }

    /**
     * Obtiene un vehiculo por la placa
     * @param id - String
     * @return - Vehicles
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleVO getVehicleByPlate(String plate) throws BusinessException {
    	log.debug("== Inicia getVehicleByPlate/VehicleCRUDBean ==");
        try {
            if (plate == null || plate.equals("")) {
                //throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro plate no especificado", new IllegalArgumentException("Param plate"));
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            Vehicle vehicle = dAOVehicleBean.getVehicleByPlate(plate);

            if (vehicle == null) {
                return null;
            }
            VehicleVO vehicleVO = UtilsBusiness.copyObject(VehicleVO.class, vehicle);
            fillVehicleRelationshipNames(vehicleVO);
            return vehicleVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehicleByPlate/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleByPlate/VehicleCRUDBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<String> getAllVehiclePlates() throws BusinessException {

        log.debug("== Inicia getAllVehiclePlates/VehicleCRUDBean ==");
        List<String> allVehiclePlates = null;
        try{
            allVehiclePlates = dAOVehicleBean.getAllVehiclePlates();
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehiclePlates/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehiclePlates/VehicleCRUDBean ==");
        }

        return allVehiclePlates;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<VehicleVO> getVehiclesByDealerCodeOrDepotCode(long dealerCode, String depotCode) throws BusinessException {

        log.debug("== Inicia getVehiclesByDealerCodeOrDepotCode/VehicleCRUDBean ==");

        List<VehicleVO> resultVehicles = null;
        try{
            List<Vehicle> allDealerVehicles = dAOVehicleBean.getVehiclesByDealerCodeOrDepotCode(dealerCode, depotCode);
            resultVehicles = UtilsBusiness.convertList(allDealerVehicles, VehicleVO.class);

            for(VehicleVO vehicle : resultVehicles){
                fillVehicleRelationshipNames(vehicle);
            }
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehiclesByDealerCodeOrDepotCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerCodeOrDepotCode/VehicleCRUDBean ==");
        }
        return resultVehicles;
    }

    private void saveVehicleDriver(VehicleVO vehicle) throws DAOServiceException, DAOSQLException, BusinessException{
        
        EmployeeVehicle empVehicle = new EmployeeVehicle();
        empVehicle.setAllocationDate(new Date());
        EmployeeVehicleId employeeVehicleId = new EmployeeVehicleId();
        UtilsBusiness.assertNotNull(vehicle.getDriverId(), ErrorBusinessMessages.DRIVER_ID_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.DRIVER_ID_NULL_OR_MISSED.getMessage());
        if(vehicle.getDriverId().longValue() <= 0){
        	throw new BusinessException(ErrorBusinessMessages.DRIVER_ID_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.DRIVER_ID_NULL_OR_MISSED.getMessage());
        }
        employeeVehicleId.setEmployeeId(vehicle.getDriverId());
        employeeVehicleId.setVehicleId(vehicle.getId());
        empVehicle.setId(employeeVehicleId);
        employeeVehicleDAO.createEmployeeVehicle(empVehicle);
    }

    private void deleteVehicleDriver(Vehicle vehicle) throws DAOServiceException, DAOSQLException{
        employeeVehicleDAO.deleteAllEmployeeVehicleByVehicleId(vehicle.getId());
    }


    /**
     * 
     * @param vehicle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void fillVehicleRelationshipNames(VehicleVO vehicle)throws DAOServiceException, DAOSQLException{
        vehicle.populateBean();
        List<EmployeeVehicle> empVehicleList = employeeVehicleDAO.getEmployeeVehicleByVehicleId(vehicle.getId());
        
        if(!empVehicleList.isEmpty()){
            EmployeeVehicle empVehicle = empVehicleList.get(0);
            Long employeeId = empVehicle.getId().getEmployeeId();
            vehicle.setDriverId(employeeId);
            Employee employee = empVehicle.getEmployee();
            vehicle.setDriverName(employee == null ? null : employee.getFirstName() + " " + employee.getLastName());
        }
        
    }

	@Override
	public List<VehicleVO> getAllVehiclesOnlyBasicInfo()
			throws BusinessException {
		log.debug("== Inicio getAllVehiclesOnlyBasicInfo/VehicleCRUDBean ==");
        try {
        	List<Vehicle> vehiclesPojo = dAOVehicleBean.getAllVehiclesOnlyBasicInfo();
            List<VehicleVO> listVehiclesVO = UtilsBusiness.convertList(vehiclesPojo, VehicleVO.class);
            
            return listVehiclesVO;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehiclesOnlyBasicInfo/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehiclesOnlyBasicInfo/VehicleCRUDBean ==");
        }
	}

	public List<VehicleVO> getVehiclesByDealerId(long dealerId)
			throws BusinessException {
		log.debug("== Inicia getVehiclesByDealerId/VehicleCRUDBean ==");

        List<VehicleVO> resultVehicles = null;
        try{
            List<Vehicle> allDealerVehicles = dAOVehicleBean.getVehiclesByDealerId(dealerId);
            resultVehicles = UtilsBusiness.convertList(allDealerVehicles, VehicleVO.class);
            /*
            for(VehicleVO vehicle : resultVehicles){
                fillVehicleRelationshipNames(vehicle);
            }
            */
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehiclesByDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerId/VehicleCRUDBean ==");
        }
        return resultVehicles;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal#getActiveVehiclesByDealerId(long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<VehicleVO> getActiveVehiclesByDealerId(long dealerId) throws BusinessException {
		log.debug("== Inicia getActiveVehiclesByDealerId/VehicleCRUDBean ==");

		List<VehicleVO> resultVehicles = null;
		try{
		    List<Vehicle> allDealerVehicles = dAOVehicleBean.getVehiclesByDealerIdAndStatusCode(dealerId,CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity());
		    resultVehicles = UtilsBusiness.convertList(allDealerVehicles, VehicleVO.class);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operacion getActiveVehiclesByDealerId/MediaContactTypesCRUDBean ==");
			throw this.manageException(ex);
		} finally {
		    log.debug("== Termina getActiveVehiclesByDealerId/VehicleCRUDBean ==");
		}
			return resultVehicles;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<VehicleVO> getVehiclesByDealerIdAndPlate(Long dealerId,String plate)throws BusinessException {
		log.debug("== Inicia getVehiclesByDealerIdAndPlate/VehicleCRUDBean ==");

		List<VehicleVO> resultVehicles = null;
		try{
		    List<Vehicle> allDealerVehicles = dAOVehicleBean.getVehiclesByDealerIdAndPlate(dealerId,plate);
		    resultVehicles = UtilsBusiness.convertList(allDealerVehicles, VehicleVO.class);
		    if( resultVehicles != null && !resultVehicles.isEmpty() ){
		    	for( VehicleVO vehicleVO : resultVehicles ){
				    fillVehicleRelationshipNames(vehicleVO);
		    	}
		    }
			return resultVehicles;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operacion getVehiclesByDealerIdAndPlate/MediaContactTypesCRUDBean ==");
			throw this.manageException(ex);
		} finally {
		    log.debug("== Termina getVehiclesByDealerIdAndPlate/VehicleCRUDBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.VehiclesCRUDBeanLocal#getActiveVehiclesByDealerId(long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<VehicleVO> getVehiclesByDealerIdAndStatusCodeOrPlate(long dealerId, String plate) throws BusinessException {
		log.debug("== Inicia getActiveVehiclesByDealerId/VehicleCRUDBean ==");

		List<VehicleVO> resultVehicles = null;
		try{
		    List<Vehicle> allDealerVehicles = dAOVehicleBean.getVehiclesByDealerIdAndStatusCodeOrPlate(dealerId,CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity(), plate);
		    resultVehicles = UtilsBusiness.convertList(allDealerVehicles, VehicleVO.class);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operacion getActiveVehiclesByDealerId/MediaContactTypesCRUDBean ==");
			throw this.manageException(ex);
		} finally {
		    log.debug("== Termina getActiveVehiclesByDealerId/VehicleCRUDBean ==");
		}
			return resultVehicles;
	}
}
