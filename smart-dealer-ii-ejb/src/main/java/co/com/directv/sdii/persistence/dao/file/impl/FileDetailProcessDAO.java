package co.com.directv.sdii.persistence.dao.file.impl;

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

import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.FileDetailProcessCollectionDTO;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * FileDetailProcess entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see co.com.directv.sdii.model.pojo.FileDetailProcess
 * @author MyEclipse Persistence Tools
 */


@Stateless(name="FileDetailProcessDAOLocal",mappedName="ejb/FileDetailProcessDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileDetailProcessDAO  extends BaseDao implements FileDetailProcessDAOLocal {
	private static final Log log = LogFactory.getLog(FileDetailProcessDAO.class);
	// property constants
	// public static final String MESSAGE = "message";

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAO#save(co.com.directv.sdii.model.pojo.FileDetailProcess)
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void save(FileDetailProcess transientInstance) throws DAOServiceException, DAOSQLException {
        Session session = null ;

        try {
            log.debug("== Inicio save/FileDetailProcessDAO ==");
            session = getSession();
            
            session.save(transientInstance);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error save/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina save/FileDetailProcessDAO ==");
        }		
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void save(List<FileDetailProcess> fileDetailProcess)  throws DAOServiceException, DAOSQLException  {
		Session session = null ;
		
		//Obtenemos la sesion
		try{
			session = getSession();
		}catch (Throwable ex) {
            log.debug("== Error save/FileDetailProcessDAO ==");
            log.debug("== Termina save/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }
		
		//Guardamos todo.
		int numSaves = 0;
		for(FileDetailProcess fDetaPros : fileDetailProcess){
			try{
				if(fDetaPros == null){
					log.error("se intentó guardar un FileDetailProcess nulo");
				}else{
					session.save(fDetaPros);
					numSaves++;
					if(numSaves >= Constantes.BATCH_INSERT_SIZE){
						numSaves = 0;
						this.doFlush(session);
					}
				}
			}catch (Throwable ex) {
				log.error("no fue posible guardar en la base de datos un error en procesamiento de archivo. Causa: " + ex.getMessage());
				if(fDetaPros != null) {
					log.error("id archivo con error: " + fDetaPros.getId() != null ? fDetaPros.getId() : "null");
					log.error(" fila: " + fDetaPros.getFdprow() != null ? fDetaPros.getFdprow() : "null");
					log.error(" mensaje: " + fDetaPros.getMessage() != null ? fDetaPros.getMessage() : "null");
				}
	        }
		}

		//Flusheamos los cambios.
        try {
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error save/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina save/FileDetailProcessDAO ==");
        }		
	}
	
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.file.FileDetailProcessDAOLocal#findByFileId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FileDetailProcess> findByFileId(Long fileId)  throws DAOServiceException, DAOSQLException  {
        Session session = null ;
		try {
			
			log.debug("== Inicio findByFileId/FileDetailProcessDAO ==");
			
			String queryString = "from FileDetailProcess model where model.uploadFile.id = :fileId";
			
			session = getSession();
			Query queryObject = session.createQuery(queryString);
			queryObject.setLong("fileId", fileId);
			
			return queryObject.list();
		}catch (Throwable ex) {
            log.debug("== Error findByFileId/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina findByFileId/FileDetailProcessDAO ==");        	
        }
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public FileDetailProcessCollectionDTO findFileDetailByFileId(Long fileId, RequestCollectionInfo requestCollection)  throws DAOServiceException, DAOSQLException  {
        Session session = null ;
		try {
			
			log.debug("== Inicio findFileDetailByFileId/FileDetailProcessDAO ==");
			
			FileDetailProcessCollectionDTO response = new FileDetailProcessCollectionDTO();
			StringBuffer queryString = new StringBuffer("from FileDetailProcess model where model.uploadFile.id = :fileId");
			queryString.append(" order by model.fdprow ");
			String queryCountString = "select count(model) from FileDetailProcess model where model.uploadFile.id = :fileId";
			
			session = getSession();
			Query queryObject = session.createQuery(queryString.toString());
			Query queryCount = session.createQuery(queryCountString);
			queryObject.setLong("fileId", fileId);  
			queryCount.setLong("fileId", fileId);
			
			Long recordQty = 0L;
			if( requestCollection != null ){
				recordQty = (Long)queryCount.uniqueResult();			
                queryObject.setFirstResult( requestCollection.getFirstResult() );
                queryObject.setMaxResults( requestCollection.getMaxResults() );
			}
			
			List<FileDetailProcess> fileDetailList = queryObject.list();
			
			if( requestCollection != null ){
				populatePaginationInfo( response, requestCollection.getPageSize(), requestCollection.getPageIndex(), fileDetailList.size(), recordQty.intValue() );
			}
        	
			response.setFileDetailProcess(fileDetailList);
			
			return response;
		}catch (Throwable ex) {
            log.debug("== Error findByFileId/FileDetailProcessDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina findByFileId/FileDetailProcessDAO ==");        	
        }
	}

	/*
	public void delete(FileDetailProcess persistentInstance) {
		log.debug("deleting FileDetailProcess instance");
		try {
			// getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public FileDetailProcess findById(java.math.BigDecimal id) {
		log.debug("getting FileDetailProcess instance with id: " + id);
		try {
			FileDetailProcess instance = (FileDetailProcess) getSession().get(
							"co.com.directv.sdii.persistence.dao.file.impl.FileDetailProcess",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}


	public List findByExample(FileDetailProcess instance) {
		log.debug("finding FileDetailProcess instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"co.com.directv.sdii.persistence.dao.file.impl.FileDetailProcess")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed");
			throw re;
		}
	}

	
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FileDetailProcess instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FileDetailProcess as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed");
			throw re;
		}
	}

	public List findByMessage(Object message) {
		return findByProperty(MESSAGE, message);
	}

	public List findAll() {
		log.debug("finding all FileDetailProcess instances");
		try {
			String queryString = "from FileDetailProcess";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public FileDetailProcess merge(FileDetailProcess detachedInstance) {
		log.debug("merging FileDetailProcess instance");
		try {
			FileDetailProcess result = (FileDetailProcess) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(FileDetailProcess instance) {
		log.debug("attaching dirty FileDetailProcess instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(FileDetailProcess instance) {
		log.debug("attaching clean FileDetailProcess instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}
	*/	
	
}