package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Vehicle;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Vehicle
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Vehicle
 * @see co.com.directv.sdii.model.hbm.Vehicle.hbm.xml
 */
@Stateless(name="VehiclesDAOLocal",mappedName="ejb/VehiclesDAOLocal")
public class VehiclesDAO extends BaseDao implements VehiclesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(VehiclesDAO.class);

    /**
     * Crea un vehiculo en el sistema
     * @param obj - Vehicles
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createVehicle(Vehicle obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createVehicle/DAOVehicleBean ==");
        
        try {
        	Session session = this.getSession();
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en createVehicle ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealer/DAODealerBean ==");
        }
    }

    /**
     * Obtiene un vehiculo por el id especificado
     * @param id - Long
     * @return - Vehicles
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Vehicle getVehicleByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleByID/VehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where vehicle.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Vehicle.class.getName()+" vehicle where vehicle.id = :id");
            query.setString("id", id.toString());
            Object obj = query.uniqueResult();

            if (obj != null && obj instanceof Vehicle) {
                return (Vehicle) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleByID/VehicleDAO ==");
        }
    }

    /**
     * Obtiene un vehiculo por la placa 
     * @param id - String
     * @return - Vehicles
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Vehicle getVehicleByPlate(String plate) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleByID/VehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where upper(vehicle.plate) = :plate");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Vehicle.class.getName()+" vehicle where upper(vehicle.plate) = :plate");
            query.setString("plate", plate.toUpperCase());
            Object obj = query.uniqueResult();

            if (obj != null && obj instanceof Vehicle) {
                return (Vehicle) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleByPlate ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleByID/VehicleDAO ==");
        }
    }

    /**
     * Metodo: Actualizar Vehicle
     * @param obj Vehicle
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateVehicle(Vehicle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateVehicle/VehicleDAO ==");
       
        try {
        	Session session = this.getSession();
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en updateVehicle ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateVehicle/VehicleDAO ==");
        }
    }

    /**
     * Metodo: Eliminar Vehicle
     * @param obj Vehicle
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteVehicle(Vehicle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteVehicle/VehicleDAO ==");
        
        try {
        	Session session = this.getSession();
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteVehicle ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteVehicle/VehicleDAO ==");
        }

    }

    /**
     * Metodo: Consultar todos los Vehiculos
     * @return List<Vehicle>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getAllVehicle() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllVehicle/VehicleDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append("(vh.id, vh.plate, vh.color, vh.model, vh.brand) from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vh");
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("select new "+ Vehicle.class.getName() +" (vh.id, vh.plate, vh.color, vh.model, vh.brand) from " + Vehicle.class.getName() + " vh" ).list();

        } catch (Throwable ex) {
            log.debug("== Error en getAllVehicle ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehicle/VehicleDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> getAllVehiclePlates()  throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllVehiclePlates/VehicleDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new java.lang.String(vh.plate) from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vh order by vh.plate");
        	List<String> result = session.createQuery(stringQuery.toString()).list();
            //List<String> result = session.createQuery("select new java.lang.String(vh.plate) from "+Vehicle.class.getName()+" vh order by vh.plate").list();
            return result;

        } catch (Throwable ex) {
            log.debug("== Error en getAllVehiclePlates ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehiclePlates/VehicleDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getVehiclesByDealerCodeOrDepotCode(long dealerCode, String depotCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllVehiclePlates/VehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select vh from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vh where ");
            //String queryStr = "select vh from "+Vehicle.class.getName()+" vh where ";

            if(dealerCode != 0){
            	stringQuery.append("vh.dealer.dealerCode= :aDealerCode");
                //queryStr += "vh.dealer.dealerCode= :aDealerCode";
            } else if(depotCode != null && depotCode.trim().length() > 0){
            	stringQuery.append("vh.dealer.depotCode= :aDepotCode");
                //queryStr += "vh.dealer.depotCode= :aDepotCode";
            }else{
                throw new DAOSQLException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            stringQuery.append(" order by vh.plate");
            //queryStr += " order by vh.plate";

            Query query = session.createQuery(stringQuery.toString());

            if(dealerCode != 0){
                query.setLong("aDealerCode", dealerCode);
            } else if(depotCode != null && depotCode.trim().length() > 0){
                query.setString("aDepotCode", depotCode);
            }

            List<Vehicle> result = query.list();

            return result;

        } catch (Throwable ex) {
            log.debug("== Error en getVehiclesByDealerCodeOrDepotCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerCodeOrDepotCode/VehicleDAO ==");
        }
    }

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getAllVehiclesOnlyBasicInfo()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllVehiclesOnlyBasicInfo/VehicleDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" (vh.id, vh.plate, vh.color, vh.model, vh.brand) from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vh");
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("select new "+ Vehicle.class.getName() +" (vh.id, vh.plate, vh.color, vh.model, vh.brand) from " + Vehicle.class.getName() + " vh" ).list();

        } catch (Throwable ex) {
            log.debug("== Error en getAllVehiclesOnlyBasicInfo ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehiclesOnlyBasicInfo/VehicleDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getVehiclesByDealerId(long dealerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getVehiclesByDealerId/VehicleDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where vehicle.dealer.id = :aDealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Vehicle.class.getName()+" vehicle where vehicle.dealer.id = :aDealerId");
            query.setLong("aDealerId", dealerId);
            List<Vehicle> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error en getVehiclesByDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerId/VehicleDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal#getVehiclesByDealerIdAndStatusCode(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getVehiclesByDealerIdAndStatusCode(long dealerId,String statusCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where vehicle.dealer.id = :aDealerId");
        	stringQuery.append(" and vehicle.vehicleStatus.statusCode = :statusCode");
        	stringQuery.append(" and vehicle.id not in (select c.vehicle.id from "+Crew.class.getName()+" c where c.dealer.id = :aDealerId and c.crewStatus.statusCode = :statusCodeCrew) ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerId", dealerId);
            query.setString("statusCode", statusCode);
            query.setString("statusCodeCrew", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            List<Vehicle> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error en getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal#getVehicleByPlateAndStatus(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Vehicle> getVehicleByPlateAndStatus(String plate,String statusCode) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio getVehicleByPlateAndStatus/VehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where upper(vehicle.plate) = :plate ");
        	stringQuery.append("and vehicle.vehicleStatus.statusCode = :statusCode ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("plate", plate.toUpperCase());
            query.setString("statusCode", statusCode);
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleByPlateAndStatus/VehicleDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleByPlateAndStatus/VehicleDAO ==");
        }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal#getActiveVehiclesByDealerIdAndPlate(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Vehicle> getVehiclesByDealerIdAndPlate(Long dealerId,String plate) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio getVehiclesByDealerIdAndPlate/VehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where 1=1 and");
        	if( dealerId != null && dealerId.longValue() > 0 ){
        		stringQuery.append(" vehicle.dealer.id = :dealerId and");
        	}
        	if( plate != null && !plate.equals("") ){
        		stringQuery.append(" upper(vehicle.plate) = :plate");
        	}
        	Query query = session.createQuery(StringUtils.removeEnd(stringQuery.toString(), "and"));
        	
        	if( dealerId != null && dealerId.longValue() > 0 ){
        		query.setLong("dealerId", dealerId);
        	}
        	if( plate != null && !plate.equals("") ){
        		query.setString("plate", plate.toUpperCase());
        	}
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error en getVehiclesByDealerIdAndPlate/VehicleDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerIdAndPlate/VehicleDAO ==");
        }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.VehiclesDAOLocal#getVehiclesByDealerIdAndStatusCode(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Vehicle> getVehiclesByDealerIdAndStatusCodeOrPlate(long dealerId,String statusCode, String plate)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Vehicle.class.getName());
        	stringQuery.append(" vehicle where vehicle.dealer.id = :aDealerId");
        	stringQuery.append(" and vehicle.vehicleStatus.statusCode = :statusCode");
        	stringQuery.append(" and vehicle.id not in (select c.vehicle.id from "+Crew.class.getName()+" c where c.dealer.id = :aDealerId and c.crewStatus.statusCode = :statusCodeCrew) ");
            if( plate != null && !plate.equals("") ){
                stringQuery.append(" or upper(vehicle.plate) = :plate");
            }
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerId", dealerId);
            query.setString("statusCode", statusCode);
            query.setString("statusCodeCrew", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            if( plate != null && !plate.equals("") ){
                query.setString("plate", plate.toUpperCase());
            }
            List<Vehicle> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error en getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehiclesByDealerIdAndStatusCode/VehicleDAO ==");
        }
	}
}
