package co.com.directv.sdii.persistence.dao.config.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderExportData;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderExportDataDAOLocal;


@Stateless(name="WorkOrderExportDataDAO",mappedName="ejb/WorkOrderDAOExportDataLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderExportDataDAO extends BaseDao implements WorkOrderExportDataDAOLocal {

	
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderExportDataDAO.class);
   
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createWorkOrderExportData(List<WorkOrderExportData> obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrderExport/WorkOrderExportDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            for(WorkOrderExportData oAux : obj){
            	session.save(oAux);
            }
            
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando WorkOrderExport ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderExport/WorkOrderExportDAO ==");
        }
    }
	
}

