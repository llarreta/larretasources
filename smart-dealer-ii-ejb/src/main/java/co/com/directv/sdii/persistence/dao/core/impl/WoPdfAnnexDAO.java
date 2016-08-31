package co.com.directv.sdii.persistence.dao.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoLoadDetail;
import co.com.directv.sdii.model.pojo.WoPdfAnnex;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones
 * de la entidad WoPdfAnnexDAO
 * 
 * Fecha de Creación:Feb 10, 2011
 * @author waltuzarra
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoPdfAnnex
 * @see co.com.directv.sdii.model.hbm.WoPdfAnnex.hbm.xml
 */
@Stateless(name="WoPdfAnnexDAOLocal",mappedName="ejb/WoPdfAnnexDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoPdfAnnexDAO extends BaseDao implements WoPdfAnnexDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoPdfAnnexDAO.class);

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal#getWoLoadByID(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WoPdfAnnex> searchWoPdfAnnexByCriteria(String whereCondition) throws DAOServiceException, DAOSQLException {
    	
    	log.debug("== Inicia getWoLoadByAtributes/WoPdfAnnexDAO ==");
        Session session = super.getSession();

        try {
        	if(whereCondition == null || whereCondition.length() == 0)
        		return null;
        	
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select model from ");
        	stringQuery.append(WoPdfAnnex.class.getName());
        	stringQuery.append(" model ");
        	stringQuery.append(whereCondition);
        	stringQuery.append(" order by model.priority");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            List<WoPdfAnnex> entitiesList = query.list();

            return entitiesList;
            
        } catch (Throwable ex){
			log.error("== Error en  getWoLoadByAtributes/WoPdfAnnexDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoLoadByAtributes/WoPdfAnnexDAO ==");
        }
	}
    
	
}
