package co.com.directv.sdii.persistence.dao.stock.impl;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.PorccessLinkClientSerialLog;
import co.com.directv.sdii.model.vo.PorccessLinkClientSerialLogVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.PorccessLinkClientSerialLogDAOLocal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * 
 * DAO para la gestion de registros de vinculacion en cliente de core
 * 
 * Fecha de Creación: 2012/12/04
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PorccessLinkClientSerialLogDAO extends BaseDao implements PorccessLinkClientSerialLogDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(PorccessLinkClientSerialLogDAO.class);
	
    /**
     * Default constructor. 
     */
    public PorccessLinkClientSerialLogDAO() {
    }
    
	/**
	 * Metodo encargado de generar un log del intento de vinculacion de seriales en cliente en core.
	 * @param obj log a guardar
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
    public void createPorccessLinkClientSerialLog(PorccessLinkClientSerialLogVO obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createPorccessLinkClientSerialLog/NotSerializedDAO ==");
        Session session = super.getSession();
        try {
        	PorccessLinkClientSerialLog objSave = new PorccessLinkClientSerialLog(obj);
            session.save(objSave);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando en el proceso createPorccessLinkClientSerialLog ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createPorccessLinkClientSerialLog/NotSerializedDAO ==");
        }
    }
}
