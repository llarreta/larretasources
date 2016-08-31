package co.com.directv.sdii.persistence.dao.file.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.UploadFileFilterDTO;
import co.com.directv.sdii.model.pojo.UploadFile;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.UploadFileResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal;
/**
 * A data access object (DAO) providing persistence and search support for
 * UploadFile entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see co.com.directv.sdii.model.pojo.UploadFile
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="UploadFileDAOLocal",mappedName="ejb/UploadFileDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UploadFileDAO  extends BaseDao implements UploadFileDAOLocal  {
	
	private static final Log log = LogFactory.getLog(UploadFileDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String LOAD_DATE = "loadDate";
	public static final String PROCESS_DATE = "processDate";
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal#findById(java.lang.Long)
     */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UploadFile findById(Long id)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio findById/UploadFileDAO ==" );
			
			session = getSession() ;

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append( UploadFile.class.getName() ) ;
		    queryString.append(" as uploadF ") ;
			queryString.append(" where uploadF.id = :id");
            Query queryObject = session.createQuery(queryString.toString());			
			queryObject.setLong("id", id);			

		    /*
			UploadFile instance = (UploadFile) session.get(
					"co.com.directv.sdii.persistence.dao.file.impl.UploadFile",
					id);
			*/
			return (UploadFile) queryObject.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error findById/UploadFileDAO ==");
			// throw re;}
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina findById/UploadFileDAO ==");			
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal#findByTypeAndStatusAndUploadDate(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UploadFile> findByTypeAndStatusAndUploadDate(String fileTypeCode, String fileStatusCode, Date initialUploadDate, Date finalUploadDate, String  loginNameUser, String countryCode)  throws DAOServiceException, DAOSQLException {
        Session session = null ;
		try {
			log.debug("== Inicio findByTypeAndStatusAndUploadDate/UploadFileDAO ==" );

			StringBuffer queryString = new StringBuffer("from ");
		    queryString.append(UploadFile.class.getName()) ;
		    queryString.append(" as model ") ;
			
			if ( ( fileTypeCode!=null && ! fileTypeCode.equalsIgnoreCase("") )
					|| ( fileStatusCode!=null && ! fileStatusCode.equalsIgnoreCase("") ) 
					|| ( initialUploadDate!=null )					
					|| ( finalUploadDate!=null ) 
			        || ( ( loginNameUser!=null ) && ( !loginNameUser.equalsIgnoreCase("") ) )		
			        || ( ( countryCode!=null  ) && ( !countryCode.equalsIgnoreCase("") ) )  ) {
				queryString.append(" where 1 = 1");
				
				if(fileTypeCode!=null && !fileTypeCode.equalsIgnoreCase("")){
					queryString.append(" and model.fileType.code = :fileTypeCode ");
				}
				if(fileStatusCode!=null && !fileStatusCode.equalsIgnoreCase("")){
					queryString.append(" and model.fileStatus.code = :fileStatusCode ");
				}
				if(initialUploadDate!=null) {
					queryString.append(" and model.loadDate >= :initialUploadDate ");
				}
				if(finalUploadDate!=null){
					queryString.append(" and model.loadDate <= :finalUploadDate ");
				}
				if ( ( loginNameUser!=null ) && ( !loginNameUser.equalsIgnoreCase("") ) ) {
					queryString.append(" and model.users.login = :loginNameUser ");					
				}
				if ( ( countryCode!=null  ) && ( !countryCode.equalsIgnoreCase("") ) ) {
					queryString.append(" and model.countries.countryCode = :countryCode ");					
				}
			}
            session = getSession();
            Query queryObject = session.createQuery(queryString.toString());
			queryObject.setString("fileTypeCode", fileTypeCode);
			queryObject.setString("fileStatusCode", fileStatusCode);
			if ( initialUploadDate!=null ) {
				initialUploadDate = UtilsBusiness.getFirstMomentOfDay(initialUploadDate);
				queryObject.setTimestamp("initialUploadDate", initialUploadDate);
			}
			if ( finalUploadDate!=null ) {
				finalUploadDate = UtilsBusiness.getLastMomentOfDay(finalUploadDate);
				queryObject.setTimestamp("finalUploadDate", finalUploadDate);
			}
			if ( ( loginNameUser!=null ) && ( !loginNameUser.equalsIgnoreCase("") ) ) {
				queryObject.setString("loginNameUser", loginNameUser);
			}
			if ( ( countryCode!=null  ) && ( !countryCode.equalsIgnoreCase("") ) ) {
				queryObject.setString("countryCode", countryCode);
			}
			
			return (List<UploadFile>) queryObject.list();
		}catch (Throwable ex) {
			log.error("== Error findByTypeAndStatusAndUploadDate/UploadFileDAO ==");
			// throw re;}
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina findByTypeAndStatusAndUploadDate/UploadFileDAO ==");			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal#findByTypeAndStatusAndUploadDate(co.com.directv.sdii.model.dto.UploadFileFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UploadFileResponse findByTypeAndStatusAndUploadDate(
			UploadFileFilterDTO filter, RequestCollectionInfo request)
			throws DAOServiceException, DAOSQLException {
		Session session = null;
		Date initialUploadDate = null, finalUploadDate = null;
		try {
			log.debug("== Inicio findByTypeAndStatusAndUploadDate/UploadFileDAO ==" );

			StringBuffer queryString = new StringBuffer("from ");
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");
		    queryString.append( UploadFile.class.getName() ) ;
		    queryString.append(" model where 1 = 1") ;
				
		    if ( filter.getId()!= null && filter.getId().longValue() > 0 ) {
				queryString.append(" and model.id = :aFileId ");
			}
			if ( filter.getFileTypeCode()!=null && ! filter.getFileTypeCode().equalsIgnoreCase("") ) {
				queryString.append(" and model.fileType.code = :fileTypeCode ");
			}
			if ( filter.getFileStatusCode()!=null && ! filter.getFileStatusCode().equalsIgnoreCase("") ) {
				queryString.append(" and model.fileStatus.code = :fileStatusCode ");
			}
			if ( filter.getInitialUploadDate()!=null ) {
				queryString.append(" and model.loadDate >= :initialUploadDate ");
			}
			if ( filter.getFinalUploadDate()!=null ) {
				queryString.append(" and model.loadDate <= :finalUploadDate ");
			}
			if ( (  filter.getLoginNameUser()!=null ) && ( ! filter.getLoginNameUser().equalsIgnoreCase("") ) ) {
				queryString.append(" and ((model.user.login = :loginNameUser) ");
				queryString.append("      or  ");
				queryString.append(" 	  (exists(select 1 "); 
				queryString.append(" 				from User u ");
				queryString.append(" 			   where u.login = :loginNameUser ");
				queryString.append(" 			   		 and u.rol.roleType.roleTypeCode = :aRoleTypeCode))) ");
			}
			if (filter.getCountries()!=null  ) {
				queryString.append(" and model.country.id = :countryId ");					
			}
			queryString.append(" order by  model.id asc ");				
            session = getSession();
            Query queryObject = session.createQuery(queryString.toString());
            if ( filter.getId()!= null && filter.getId().longValue() > 0 ) {
            	queryObject.setLong("aFileId", filter.getId());
			}
            if ( filter.getFileTypeCode()!=null && ! filter.getFileTypeCode().equalsIgnoreCase("") ) {
            	queryObject.setString("fileTypeCode", filter.getFileTypeCode());
			}
            if ( filter.getFileStatusCode()!=null && ! filter.getFileStatusCode().equalsIgnoreCase("") ) {
            	queryObject.setString("fileStatusCode", filter.getFileStatusCode());
			}
            if ( filter.getInitialUploadDate()!=null ) {
            	initialUploadDate = filter.getInitialUploadDate();
            	initialUploadDate = UtilsBusiness.getFirstMomentOfDay(initialUploadDate);
				queryObject.setTimestamp("initialUploadDate", initialUploadDate);
			}
            if ( filter.getFinalUploadDate()!=null ) {
            	finalUploadDate = filter.getFinalUploadDate();
            	finalUploadDate = UtilsBusiness.getLastMomentOfDay(finalUploadDate);
				queryObject.setTimestamp("finalUploadDate", finalUploadDate);
			}
            if ( (  filter.getLoginNameUser()!=null ) && ( ! filter.getLoginNameUser().equalsIgnoreCase("") ) ) {
				queryObject.setString("loginNameUser", filter.getLoginNameUser());
				queryObject.setString("aRoleTypeCode", CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity());
			}
            if (filter.getCountries()!=null  ) {
				queryObject.setLong("countryId", filter.getCountries());
			}
            
          //Paginacion
        	Long recordQty = 0L;
        	if( request != null ){
        		stringCount.append(queryString.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if ( filter.getId()!= null && filter.getId().longValue() > 0 ) {
        			queryCount.setLong("aFileId", filter.getId());
    			}
        		if ( filter.getFileTypeCode()!=null && ! filter.getFileTypeCode().equalsIgnoreCase("") ) {
        			queryCount.setString("fileTypeCode", filter.getFileTypeCode());
    			}
                if ( filter.getFileStatusCode()!=null && ! filter.getFileStatusCode().equalsIgnoreCase("") ) {
                	queryCount.setString("fileStatusCode", filter.getFileStatusCode());
    			}
                if ( filter.getInitialUploadDate()!=null ) {
                	queryCount.setTimestamp("initialUploadDate", initialUploadDate);
    			}
                if ( filter.getFinalUploadDate()!=null ) {
                	queryCount.setTimestamp("finalUploadDate", finalUploadDate);
    			}
                if ( (  filter.getLoginNameUser()!=null ) && ( ! filter.getLoginNameUser().equalsIgnoreCase("") ) ) {
                	queryCount.setString("loginNameUser", filter.getLoginNameUser());
                	queryCount.setString("aRoleTypeCode", CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity());
    			}
                if (filter.getCountries()!=null  ) {
                	queryCount.setLong("countryId", filter.getCountries());
    			}
        		
                recordQty = (Long)queryCount.uniqueResult();			
                queryObject.setFirstResult( request.getFirstResult() );
                queryObject.setMaxResults( request.getMaxResults() );				
        	}
			
        	UploadFileResponse response = new UploadFileResponse();
        	List<UploadFile> uploadFile = queryObject.list();
        	if( request != null )
				populatePaginationInfo( response, request.getPageSize(), request.getPageIndex(), uploadFile.size(), recordQty.intValue() );
        	response.setUploadFile(uploadFile );
			
			return response;
		}catch (Throwable ex) {
			log.error("== Error findByTypeAndStatusAndUploadDate/UploadFileDAO ==");
			// throw re;}
            throw this.manageException(ex);
		}finally {
            log.debug("== Termina findByTypeAndStatusAndUploadDate/UploadFileDAO ==");			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.file.UploadFileDAOLocal#save(co.com.directv.sdii.model.pojo.UploadFile)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UploadFile save(UploadFile uploadFile)  throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
            log.debug("== Inicio save/UploadFileDAO ==");
            session = getSession();
            
            session.save(uploadFile);
            this.doFlush(session);
            return uploadFile;
        } catch (Throwable ex) {
            log.debug("== Error save/UploadFileDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina save/UploadFileDAO ==");
        }		

	}
	
	
	/**
	 * 
	 * 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUploadFile(UploadFile uploadFile) throws DAOServiceException, DAOSQLException {
        
        log.debug("== Inicio updateFileStatus/UploadFileDAO ==");
        Session session = super.getSession();
        try {
            session.merge(uploadFile);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Warehouse ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateFileStatus/UploadFileDAO ==");
        }

	}
	
}