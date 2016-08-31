package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.BusinessArea;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.vo.BusinessAreaVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.BusinessAreaDAOLocal;

/**
 *  
 	* A data access object (DAO) providing persistence and search support for BusinessArea entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .BusinessArea
  * @author MyEclipse Persistence Tools 
 */

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de BusinessArea
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.BusinessArea
 * @see co.com.directv.sdii.persistence.dao.config.BusinessAreaDAOLocal
 */

@Stateless(name="BusinessAreaDAOLocal",mappedName="ejb/BusinessAreaDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BusinessAreaDAO extends BaseDao implements BusinessAreaDAOLocal {
	
	private static final Logger log = LoggerFactory.getLogger(BusinessAreaDAO.class);
    
	public static final String BUSINESS_AREA_CODE = "businessAreaCode";
	public static final String USER_ID_LAST_CHANGE = "userIdLastChange";
	public static final String STATUS = "status";

	@Override
	public List<BusinessArea> getAllBusinessAreas(Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("finding all BusinessArea instances");
		try {
			String queryString = "from BusinessArea";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all BusinessArea failed");
			throw re;
		}
	}
	
	@Override
	public BusinessArea getBusinessAreaByServiceCode(String serviceCode) throws DAOServiceException, DAOSQLException {
	 	log.debug("== Inicio getBusinessAreaByServiceCode/BusinessAreaDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("FROM ");
        	stringQuery.append(BusinessArea.class.getName());
        	stringQuery.append(" ba WHERE ba.id IN ( ");
        	stringQuery.append(" 	SELECT st.businessAreaId FROM " + ServiceType.class.getName());
        	stringQuery.append(" 	st WHERE st.id IN ( ");
        	stringQuery.append(" 		SELECT sc.serviceType.id FROM " + ServiceCategory.class.getName());
        	stringQuery.append(" 		sc WHERE sc.id IN ( ");
        	stringQuery.append(" 			SELECT s.serviceCategory.id FROM " + Service.class.getName());
        	stringQuery.append(" 			s WHERE s.serviceCode = :serviceCode ) ) ) ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("serviceCode", serviceCode);

            return (BusinessArea)query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getBusinessAreaByServiceCode/BusinessAreaDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getBusinessAreaByServiceCode/BusinessAreaDAO ==");
        }
	}
	
	/**
	 * ialessan 
	 * marzo 2014
     * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
     */		
	@Override
	public BusinessAreaVO getBusinessAreaByCode(String businessAreaCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getBusinessAreaByCode/BusinessAreaDAO ==");
        Session session = super.getSession();
        try {

            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" select new "+BusinessAreaVO.class.getName()+"(ba) from "+BusinessArea.class.getName()+" ba ");
            stringQuery.append(" where ba.businessAreaCode = :pBusinessAreaCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("pBusinessAreaCode", businessAreaCode);
            return (BusinessAreaVO) query.uniqueResult();
            
        	

        }catch (Throwable ex) {
            log.error("== Error consultando BusinessArea por businessAreaCode ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getBusinessAreaByCode/BusinessAreaDAO ==");
        }
    }	
}