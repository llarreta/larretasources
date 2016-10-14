package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MembershipType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.MembershipTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de MembershipTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MembershipTypes
 * @see co.com.directv.sdii.model.hbm.MembershipTypes.hbm.xml
 */
@Stateless(name="MembershipTypesDAOLocal",mappedName="ejb/MembershipTypesDAOLocal")
public class MembershipTypesDAO extends BaseDao implements MembershipTypesDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(MembershipTypesDAO.class);
   
	/**
	 * Metodo: Consultar MembershipTypes por ID
	 * @param id
	 * @return MembershipTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public MembershipType getMembershipTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMembershipTypesByID/MembershipTypesDAO ==");
        
        MembershipType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MembershipType.class.getName());
        	stringQuery.append(" mt where mt.id= :aMtId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+MembershipType.class.getName()+" mt where mt.id= :aMtId");
            query.setLong("aMtId", id);
            obj = (MembershipType)query.uniqueResult();
            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getMembershipTypesByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getMembershipTypesByID/MembershipTypesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar MembershipTypes por Codigo
	 * @param code
	 * @return MembershipTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public MembershipType getMembershipTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getMembershipTypesByCode/MembershipTypesDAO ==");
        
        MembershipType obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MembershipType.class.getName());
        	stringQuery.append(" mt where mt.membershipTypeCode= :aMtCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + MembershipType.class.getName() + " mt where mt.membershipTypeCode= :aMtCode" );
            query.setString("aMtCode", code);

            obj = (MembershipType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getMembershipTypesByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getMembershipTypesByCode/MembershipTypesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los MembershipTypes
	 * @return List<MembershipTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<MembershipType> getAllMembershipTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllMembershipTypes/MembershipTypesDAO ==");
        
        List<MembershipType> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MembershipType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + MembershipType.class.getName());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllMembershipTypes ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllMembershipTypes/MembershipTypesDAO ==");
        }
    }
}