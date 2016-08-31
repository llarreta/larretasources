package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.BuildingAddresses;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.BuildingAddressesDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

@Stateless(name="BuildingAddressesDAOLocal", mappedName="ejb/BuildingAddressesDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BuildingAddressesDAO extends BaseDao implements BuildingAddressesDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(BuildingAddressesDAO.class);
	
	@Override
	public void createBuildingAddresses(BuildingAddresses obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createBuildingAddresses/BuildingAddressesDAO ==");
        Session session = super.getSession();
        try {
        	Long caExist = getBuildingAddressesByCodeAndBuildingId(obj.getAddressCode(), obj.getBuildingId());
        	obj.setActive(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	if(caExist!=null){
        		obj.setId(caExist);
        		session.merge(obj);
        	} else{
        		session.save(obj);
        	}
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el createBuildingAddresses ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createBuildingAddresses/BuildingAddressesDAO ==");
        }
		
	}
	
	private Long getBuildingAddressesByCodeAndBuildingId(String code, Long buildingID) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getBuildingAddressesByID/BuildingAddressesDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select ba.id from "+BuildingAddresses.class.getName()+" ba ");
        	stringQuery.append("where ");
        	stringQuery.append("ba.addressCode = '");
        	stringQuery.append(code+"' ");
        	stringQuery.append("and ba.buildingId = :buildingId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setParameter("buildingId", buildingID, Hibernate.LONG);
        	List<Object> obj = query.list();
            if (obj != null && obj.size()>=1) {
                return (Long) obj.get(0);
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error getBuildingAddressesByID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getBuildingAddressesByID/BuildingAddressesDAO ==");
        }
	}

	@Override
	public void updateBuildingAddresses(BuildingAddresses obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateBuildingAddresses/BuildingAddressesDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error updateBuildingAddresses ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateBuildingAddresses/BuildingAddressesDAO ==");
        }
	}

	@Override
	public void deleteBuildingAddressesByBuildingId(Long buildingId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteBuildingAddressesByBuildingId/BuildingAddressesDAO ==");
        Session session = ConnectionFactory.getSession();
        
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" UPDATE BUILDING_ADDRESSES SET IS_ACTIVE = '"+CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()+"' WHERE BUILDING_ID = :buildingId");
            Query query = session.createSQLQuery(stringQuery.toString());
            query.setParameter("buildingId", buildingId, Hibernate.LONG);
            query.executeUpdate();
        }catch (Throwable ex) {
            log.error("== Error borrando el deleteBuildingAddressesByBuildingId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteBuildingAddressesByBuildingId/BuildingAddressesDAO ==");
        }
	}
	
}
