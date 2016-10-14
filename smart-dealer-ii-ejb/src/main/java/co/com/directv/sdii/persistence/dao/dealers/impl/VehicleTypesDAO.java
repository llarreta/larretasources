package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.VehicleType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.VehicleTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de VehicleTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.VehicleTypes
 * @see co.com.directv.sdii.model.hbm.VehicleTypes.hbm.xml
 */
@Stateless(name="VehicleTypesDAOLocal",mappedName="ejb/VehicleTypesDAOLocal")
public class VehicleTypesDAO extends BaseDao implements VehicleTypesDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(VehicleTypesDAO.class);

    /**
     * Metodo: Consultar VehicleTypes por ID
     * @param id
     * @return VehicleTypes
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public VehicleType getVehicleTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleTypesByID/VehicleTypesDAO ==");
        
        VehicleType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleType.class.getName());
        	stringQuery.append(" vht where vht.id= :aVhtId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+VehicleType.class.getName()+" vht where vht.id= :aVhtId");
            query.setLong("aVhtId", id);
            obj = (VehicleType)query.uniqueResult();
            return obj;
        }catch (Throwable ex) {
            log.debug("== Error en createTraining ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getVehicleTypesByID/VehicleTypesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar VehicleTypes por Codigo
	 * @param code
	 * @return VehicleTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public VehicleType getVehicleTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getVehicleTypesByCode/VehicleTypesDAO ==");
        
        VehicleType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleType.class.getName());
        	stringQuery.append(" vht where vht.vehicleTypeCode= :aVhtCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+VehicleType.class.getName()+" vht where vht.vehicleTypeCode= :aVhtCode");
            query.setString("aVhtCode", code);
            obj = (VehicleType)query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getVehicleTypesByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getVehicleTypesByCode/VehicleTypesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los VehicleTypes
	 * @return List<VehicleTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<VehicleType> getAllVehicleTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllVehicleTypes/VehicleTypesDAO ==");
        
        List<VehicleType> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(VehicleType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from " + VehicleType.class.getName());
            //Query query = session.createQuery("from " + VehicleType.class.getName());
            list = query.list();

            return list;
        }catch (Throwable ex) {
            log.debug("== Error en getAllVehicleTypes ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllVehicleTypes/VehicleTypesDAO ==");
        }
    }
}