package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.VehicleStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.VehicleStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de VehicleStatus
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.VehicleStatus
 * @see co.com.directv.sdii.model.hbm.VehicleStatus.hbm.xml
 */
@Stateless(name="VehicleStatusDAOLocal",mappedName="ejb/VehicleStatusDAOLocal")
public class VehicleStatusDAO extends BaseDao implements VehicleStatusDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(VehicleStatusDAO.class);

    /**
     * Metodo: Consultar VehicleStatus por ID
     * @param id
     * @return VehicleStatus
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public VehicleStatus getVehicleStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleStatusByID/VehicleStatusDAO ==");
        
        VehicleStatus obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleStatus.class.getName());
        	stringQuery.append(" vhs where vhs.id= :aVhsId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+VehicleStatus.class.getName()+" vhs where vhs.id= :aVhsId");
            query.setLong("aVhsId", id);
            obj = (VehicleStatus)query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleStatusByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getVehicleStatusByID/VehicleStatusDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar VehicleStatus por Codigo
	 * @param code
	 * @return VehicleStatus
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public VehicleStatus getVehicleStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleStatusByCode/VehicleStatusDAO ==");
        
        VehicleStatus obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleStatus.class.getName());
        	stringQuery.append(" vhs where vhs.statusCode= :aVhsCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+VehicleStatus.class.getName()+" vhs where vhs.statusCode= :aVhsCode");
            query.setString("aVhsCode", code);
            obj = (VehicleStatus)query.uniqueResult();
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleStatusByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getVehicleStatusByCode/VehicleStatusDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los VehicleStatus
	 * @return List<VehicleStatus>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<VehicleStatus> getAllVehicleStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllVehicleStatus/VehicleStatusDAO ==");
        
        List<VehicleStatus> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleStatus.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + VehicleStatus.class.getName());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllVehicleStatus ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllVehicleStatus/VehicleStatusDAO ==");
        }
    }
}