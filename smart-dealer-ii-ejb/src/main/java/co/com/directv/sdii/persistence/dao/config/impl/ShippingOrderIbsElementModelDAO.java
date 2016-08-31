package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderIbsElementModel;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderIbsElementModelDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ShippingOrderIbsElementModel
 * 
 * Fecha de Creación: 15/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ShippingOrderIbsElementModel
 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderIbsElementModelDAOLocal
 */
@Stateless(name="ShippingOrderIbsElementModelDAOLocal",mappedName="ejb/ShippingOrderIbsElementModelDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderIbsElementModelDAO extends BaseDao implements ShippingOrderIbsElementModelDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderIbsElementModelDAO.class);
    
    /**
     * Crea una WorkOrderCSR en el sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderIbsElementModel(ShippingOrderIbsElementModel obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createShippingOrderIbsElementModel/ShippingOrderIbsElementModelDAO ==");
        Session session = ConnectionFactory.getSession();
		
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error creando ShippingOrderIbsElementModel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrderIbsElementModel/ShippingOrderIbsElementModelDAO ==");
        }
    }
    
	@Override
	public ShippingOrderIbsElementModel getShippingOrderIbsElementModelByID(
			Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getShippingOrderIbsElementModelByID/ShippingOrderIbsElementModelDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select entity ");
            stringQuery.append("from " + ShippingOrderIbsElementModel.class.getName() + " entity ");
            stringQuery.append("where entity.id = :aId ");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aId", id);
            return (ShippingOrderIbsElementModel) query.uniqueResult();

        } catch (Throwable ex) {
            log.debug("== Error consultando ShippingOrderIbsElementModel por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getShippingOrderIbsElementModelByID/ShippingOrderIbsElementModelDAO ==");
        }
	}
    
}
