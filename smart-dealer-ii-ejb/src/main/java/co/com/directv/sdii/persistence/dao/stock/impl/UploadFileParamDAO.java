package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad UploadFileParam
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.UploadFileParam
 * @see co.com.directv.sdii.model.hbm.UploadFileParam.hbm.xml
 */
@Stateless(name="UploadFileParamDAOLocal",mappedName="ejb/UploadFileParamDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UploadFileParamDAO extends BaseDao implements UploadFileParamDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(UploadFileParamDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamsDAOLocal#createUploadFileParam(co.com.directv.sdii.model.pojo.UploadFileParam)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException {

    	log.debug("== Inicio createUploadFileParam/UploadFileParamDAO ==");
        Session session = getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el UploadFileParam ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createUploadFileParam/UploadFileParamDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamsDAOLocal#updateUploadFileParam(co.com.directv.sdii.model.pojo.UploadFileParam)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateUploadFileParam/UploadFileParamDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el UploadFileParam ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateUploadFileParam/UploadFileParamDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamsDAOLocal#deleteUploadFileParam(co.com.directv.sdii.model.pojo.UploadFileParam)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteUploadFileParam(UploadFileParam obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteUploadFileParam/UploadFileParamDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from UploadFileParam entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el UploadFileParam ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteUploadFileParam/UploadFileParamDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamsDAOLocal#getUploadFileParamsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UploadFileParam getUploadFileParamByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getUploadFileParamByID/UploadFileParamDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(UploadFileParam.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (UploadFileParam) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getUploadFileParamByID/UploadFileParamDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamsDAOLocal#getAllUploadFileParams()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<UploadFileParam> getAllUploadFileParams() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllUploadFileParams/UploadFileParamDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(UploadFileParam.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllUploadFileParams/UploadFileParamDAO ==");
        }
    }



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal#getUploadFileParamsByUploadFile(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UploadFileParam> getUploadFileParamsByUploadFile(
			Long idUploadFile) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getUploadFileParamsByUploadFiel/UploadFileParamDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(UploadFileParam.class.getName());
	        	stringQuery.append(" entity where entity.uploadFile.id = :anId");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", idUploadFile);

	            return  query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getUploadFileParamsByUploadFiel/UploadFileParamDAO ==");
	        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal#getUploadFileParamByUploadFileAndName(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UploadFileParam getUploadFileParamByUploadFileAndName(
			Long idUploadFile, String nameParam) throws DAOServiceException,
			DAOSQLException {
		 log.debug("== Inicio getUploadFileParamByUploadFileAndName/UploadFileParamDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(UploadFileParam.class.getName());
	        	stringQuery.append(" entity where entity.uploadFile.id = :anId and entity.paramName = :paramName");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", idUploadFile);
	            query.setString("paramName", nameParam);

	            return  (UploadFileParam) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getUploadFileParamByUploadFileAndName/UploadFileParamDAO ==");
	        }
	}
	 
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal#getUploadFileParamByFileTypeParamNameAndCode(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UploadFileParamByFileTypeParamNameAndCodeDTO> getUploadFileParamByFileTypeParamNameAndCode(List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters) throws DAOServiceException,
			DAOSQLException {
		 log.debug("== Inicio getUploadFileParamByUploadFileAndName/UploadFileParamDAO ==");
	        Session session = super.getSession();
	        String strWhereOrAnd=" WHERE entityUFP.uploadFile.fileStatus.code IN(:fileStatusPending,:fileStatusProcessing) and ( ";
	        int i;
	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append(" select new " + UploadFileParamByFileTypeParamNameAndCodeDTO.class.getName());
	        	stringQuery.append(" (entityUFP.uploadFile.name, ");
			    stringQuery.append(" entityUFP.uploadFile.loadDate, ");
			    stringQuery.append(" entityUFP.uploadFile.user.name) ");
	        	stringQuery.append(" from ");
	        	stringQuery.append(UploadFileParam.class.getName() + " entityUFP ");
	        	
	        	i=0;
	        	for (FilterUploadFileParamByFileTypeParamNameAndCodeDTO filter : filters) {
	    			stringQuery.append( strWhereOrAnd + " ( entityUFP.uploadFile.fileType.code=:aCode" + i);
	    			stringQuery.append("                    and entityUFP.paramName=:aParamName" + i);
	    			stringQuery.append("					and entityUFP.paramValue=:aParamValue" + i + " ) ");
	   	         	strWhereOrAnd=" or ";
	   	         	i++;
				}
	        	
	        	stringQuery.append( " ) ");
	        	
	        	Query query = session.createQuery(stringQuery.toString());
	        	
	        	query.setString("fileStatusPending", CodesBusinessEntityEnum.FILE_STATUS_PENDING.getCodeEntity());
	        	query.setString("fileStatusProcessing", CodesBusinessEntityEnum.FILE_STATUS_PROCESSING.getCodeEntity());

	        	i=0;
	        	for (FilterUploadFileParamByFileTypeParamNameAndCodeDTO filter : filters) {
	        		query.setString("aCode" + i, filter.getFileTypeCode());
	        		query.setString("aParamName" + i, filter.getParamName());
	        		query.setString("aParamValue" + i, filter.getParamValue());
	        		i++;
				}

				return query.list();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getUploadFileParamByUploadFileAndName/UploadFileParamDAO ==");
	        }
	}


}
