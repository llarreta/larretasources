package co.com.directv.sdii.persistence.dao.dealers.impl;

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
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Employee
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Employee
 */
@Stateless(name="EmployeeDAOLocal",mappedName="ejb/EmployeeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeDAO extends BaseDao implements EmployeeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeDAO.class);

    /**
     * Almacena un empleado en el sistema
     * @param employee - Objeto con la información básica del empleado
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployee(Employee obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createEmployee/EmployeeDAO ==");
        Session session = getSession();

        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error createEmployee ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployee/EmployeeDAO ==");
        }
    }

    /**
     * Obtiene un empleado por su identificador único
     * @param id - Long
     * @return Employee
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee getEmployeeByID(Long id) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio getEmployeeByID/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select e from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.id = :id order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("select e from " + Employee.class.getName() + " e where e.id = :id order by e.lastName");
            query.setString("id", id.toString());
            Object obj = query.uniqueResult();

            if (obj != null && obj instanceof Employee) {
                return (Employee) obj;
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByID/EmployeeDAO ==");
        }
    }

    /**
     * Actualiza un colaborador en el sistema
     * @param employee - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployee(Employee obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateEmployee/EmployeeDAO ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error updateEmployee ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployee/EmployeeDAO ==");
        }
    }

    /**
     * Borra un colaborador del sistema
     * @param employee - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployee(Employee obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployee/EmployeeDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.id = :anEmpId order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Employee.class.getName() + " e where e.id = :anEmpId order by e.lastName");
            query.setLong("anEmpId", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el Employee ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployee/EmployeeDAO ==");
        }

    }

    /**
     * Obtiene todos los empleados existentes en el sistema
     * @return - List<Employee>
     * @throws DAOServiceException
     * @throws DAOSQLException      
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Employee> getAllEmployee() throws DAOServiceException, DAOSQLException {

        log.debug("== Inicia getAll/EmployeeDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select e from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e order by e.lastName");
        	List<Employee> list = session.createQuery(stringQuery.toString()).list();
        	//List<Employee> list = session.createQuery("select e from " + Employee.class.getName() + " e order by e.lastName").list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getAllEmployee ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployee/EmployeeDAO ==");
        }
    }

    /**
     * Obtiene todos los empleados pertenecientes a un Dealer
     * @param dealerId - Id del Dealer
     * @return - List<Employee>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Employee> getEmployeesByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getEmployeesBiDealerId/EmployeeDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select e from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.dealer.id = :dealerId order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("select e from " + Employee.class.getName() + " e where e.dealer.id = :dealerId order by e.lastName");
            query.setLong("dealerId", dealerId);

            List<Employee> list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeesByDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesByDealerId/EmployeeDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal#getEmployeesByDealerIdAndStatus(java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeesByDealerIdAndStatus(Long idDealer,
			String codeStatus) throws DAOServiceException, DAOSQLException {
    	 log.debug("== Inicia getEmployeesByDealerIdAndStatus/EmployeeDAO ==");
         Session session = getSession();

         try {
         	StringBuffer stringQuery = new StringBuffer();
         	stringQuery.append("select e from ");
         	stringQuery.append(Employee.class.getName());
         	stringQuery.append(" e where e.dealer.id = :dealerId and e.employeeStatus.statusCode = :codeStatus  order by e.lastName");
         	Query query = session.createQuery(stringQuery.toString());
         	//Query query = session.createQuery("select e from " + Employee.class.getName() + " e where e.dealer.id = :dealerId order by e.lastName");
             query.setLong("dealerId", idDealer);
             query.setString("codeStatus", codeStatus);

             List<Employee> list = query.list();
 			return list;

         } catch (Throwable ex) {
             log.debug("== Error getEmployeesByDealerIdAndStatus ==");
             throw this.manageException(ex);
         } finally {
             log.debug("== Termina getEmployeesByDealerIdAndStatus/EmployeeDAO ==");
         }
	}

    /**
     * Obtiene un empleado correspondiente al numero de documento y Dealer especificado
     * @param documentNumber - String
     * @param dealerId - Long
     * @return - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee getEmployeeByDocNumAndTypeDealerId(Long idDocType, String documentNumber, Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeByID/EmployeeDAO ==");
        Session session = getSession();
        Employee emp = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select e from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.documentType.id = :idDocType and e.documentNumber = :aDocNumber and e.dealer.id = :aDealerId order by e.lastName ");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("select e from " + Employee.class.getName() + " e where e.documentType.id = :idDocType and e.documentNumber = :aDocNumber and e.dealer.id = :aDealerId order by e.lastName ");
            query.setLong("idDocType", idDocType);
            query.setString("aDocNumber", documentNumber);
            query.setLong("aDealerId", dealerId);

            emp = (Employee) query.uniqueResult();

            return emp;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocNumAndDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocNumAndDealerId/EmployeeDAO ==");
        }
    }

    /**
     * Obtiene un empleado con el tipo y numero de documento especificado,
     * filtra por el dealer si este es diferente de null
     * @param idDocType - Id del tipo de documento
     * @param documentNumber - Numero de documento
     * @param Long dealerId
     * @return - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeByDocTypeAndDocNum/EmployeeDAO ==");
        Session session = getSession();
        Employee emp = null;
        try {
        	StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" e where e.documentNumber = :aDocNumber and e.documentType.id = :adocumentTypeId");
            if(dealerId != null)
            	queryBuffer.append(" and e.dealer.id = :aDealerId");
            queryBuffer.append(" order by e.documentNumber");
            
            Query query = session.createQuery(queryBuffer.toString());
            if(dealerId != null)
            	query.setLong("aDealerId", dealerId);
            query.setString("aDocNumber", documentNumber);
            query.setLong("adocumentTypeId", idDocType);

            emp = (Employee) query.uniqueResult();

            return emp;
        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocTypeAndDocNum ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocTypeAndDocNum/EmployeeDAO ==");
        }
    }
    
    /**
     * Obtiene un empleado con el tipo y numero de documento especificado
     * @param idDocType - Id del tipo de documento
     * @param documentNumber - Numero de documento    
     * @return - Employee
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeByDocTypeAndDocNum/EmployeeDAO ==");
        Session session = getSession();
        Employee emp = null;
        try {
        	StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" e where e.documentNumber = :aDocNumber and e.documentType.id = :adocumentTypeId");           	
            queryBuffer.append(" order by e.documentNumber");
            
            Query query = session.createQuery(queryBuffer.toString());            
            query.setString("aDocNumber", documentNumber);
            query.setLong("adocumentTypeId", idDocType);

            emp = (Employee) query.uniqueResult();

            return emp;
        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocTypeAndDocNum ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocTypeAndDocNum/EmployeeDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByDocumentNumber(String documentNumber)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeByDocumentNumber/EmployeeDAO ==");
        Session session = getSession();
        List<Employee> list = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.documentNumber = :anEmpDocNumber order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from " + Employee.class.getName() + " e where e.documentNumber = :anEmpDocNumber order by e.lastName");
            query.setString("anEmpDocNumber", documentNumber);
            list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocumentNumber ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentNumber/EmployeeDAO ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee getEmployeeByDocumentNumberAndStatus(String documentNumber,String employeeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeByDocumentNumberAndStatus/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.documentNumber = :anEmpDocNumber ");
        	stringQuery.append("and e.employeeStatus.statusCode = :employeeStatus ");
        	stringQuery.append("order by e.lastName ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setString("anEmpDocNumber", documentNumber);
            query.setString("employeeStatus", employeeStatus);
            
            
            return (Employee) query.uniqueResult();
			

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocumentNumberAndStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentNumberAndStatus/EmployeeDAO ==");
        }
	}
    
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByDocumentNumberAnDocTypeAndStatus(
			String documentNumber, Long idType, String employeeStatus)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getEmployeeByDocumentNumberAnDocTypeAndStatus/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.documentNumber = :anEmpDocNumber ");
        	stringQuery.append("and e.documentType.id = :idType ");
        	stringQuery.append("and e.employeeStatus.statusCode = :employeeStatus ");
        	stringQuery.append("order by e.lastName ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
            query.setString("anEmpDocNumber", documentNumber);
            query.setLong("idType", idType);
            query.setString("employeeStatus", employeeStatus);
            
            
            return query.list();
			

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocumentNumberAnDocTypeAndStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentNumberAnDocTypeAndStatus/EmployeeDAO ==");
        }
	}

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByDocumentTypeId(Long typeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeByDocumentTypeId/EmployeeDAO ==");
        Session session = getSession();
        List<Employee> list = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName() );
        	stringQuery.append(" e where e.documentType.id = :anEmpDocTypeId order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("from " + Employee.class.getName() + " e where e.documentType.id = :anEmpDocTypeId order by e.lastName");
            query.setLong("anEmpDocTypeId", typeId);
            list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByDocumentTypeId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByDocumentTypeId/EmployeeDAO ==");
        }
	}

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByFirstName(String firstName)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeByFirstName/EmployeeDAO ==");
        Session session = getSession();

        try {
        	List<Employee> list = null;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.firstName = :anEmpFirstName order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Employee.class.getName() + " e where e.firstName = :anEmpFirstName order by e.lastName");
            query.setString("anEmpFirstName", firstName);
            list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByFirstName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByFirstName/EmployeeDAO ==");
        }
	}
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByLastName(String lastName)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeByLastName/EmployeeDAO ==");
        Session session = getSession();

        try {
        	List<Employee> list = null;
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.lastName = :anEmpLastName order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Employee.class.getName() + " e where e.lastName = :anEmpLastName order by e.lastName");
            query.setString("anEmpLastName", lastName);
            list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByLastName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByLastName/EmployeeDAO ==");
        }
	}
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName,
			String lastName) throws DAOServiceException, DAOSQLException {
		try{
			log.debug("== Inicio getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN/EmployeeDAO ==");
			return 	getEmployeeByChriteria(typeId, documentNumber, firstName, lastName, null, null,null,null,null).getEmployeeList();
		}finally{
			log.debug("== Termina getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN/EmployeeDAO ==");
		}
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeePaginationResponse getEmployeeByChriteria(
			Long typeId, String documentNumber, String firstName,
			String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
    	
		log.debug("== Inicio getEmployeeByChriteria/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringCount = new StringBuffer();
        	stringCount.append("select count(*) ");        	
        	
            StringBuffer queryStr = new StringBuffer("from ");            
            queryStr.append(Employee.class.getName());
            
            boolean docTypeIdEmpty = typeId == null || typeId == 0L;
            boolean docNumberEmpty = documentNumber == null || documentNumber.trim().isEmpty();
            boolean firstNameEmpty = firstName == null || firstName.trim().isEmpty();
            boolean lastNameEmpty = lastName == null || lastName.trim().isEmpty();
            
            boolean depotCodeEmpty = depotCode == null || depotCode.trim().isEmpty();
            boolean dealerCodeEmpty = dealerCode == null ||dealerCode == 0L;
            
            boolean dealerIdEmpty = dealerId == null || dealerId <= 0L;
            
            boolean countryIdEmpty = countryId == null || countryId <= 0L;
            
            queryStr.append(" e where 1 = 1 ");
            
            if(!docTypeIdEmpty){
            	queryStr.append(" and e.documentType.id = :aDocTypeId ");
            }
            if(!docNumberEmpty){
            	queryStr.append(" and e.documentNumber = :aDocNumber ");
            }
            if(!firstNameEmpty){
            	queryStr.append(" and upper(e.firstName) like '%'||:aFirstName||'%' ");
            }
            if(!lastNameEmpty){
            	queryStr.append(" and upper(e.lastName) like '%'||:aLastName||'%' ");
            }
            if(!dealerCodeEmpty){
            	queryStr.append(" and e.dealer.dealerCode = :aDealerCode ");	
            }
            if(!depotCodeEmpty){
            	queryStr.append(" and e.dealer.depotCode = :aDepotCode ");
            }
            if(!dealerIdEmpty){
            	queryStr.append(" and e.dealer.id = :adealerId ");
            }
            if(!countryIdEmpty){
            	queryStr.append(" and e.city.state.country.id = :acountryId ");
            }
            stringCount.append(queryStr.toString());
            queryStr.append(" order by e.lastName ");
            
            String finalQuery = queryStr.toString();
            
            Query query = session.createQuery(finalQuery);
            Query countQuery = session.createQuery(stringCount.toString());
            
            if(!docTypeIdEmpty){
            	query.setLong("aDocTypeId", typeId);
            	countQuery.setLong("aDocTypeId", typeId);
            }
            if(!docNumberEmpty){
            	query.setString("aDocNumber", documentNumber);
            	countQuery.setString("aDocNumber", documentNumber);
            }
            if(!firstNameEmpty){
            	query.setString("aFirstName", firstName.toUpperCase());
            	countQuery.setString("aFirstName", firstName.toUpperCase());
            }
            if(!lastNameEmpty){
            	query.setString("aLastName", lastName.toUpperCase());
            	countQuery.setString("aLastName", lastName.toUpperCase());
            }
            if(!depotCodeEmpty){
            	query.setString("aDepotCode", depotCode);
            	countQuery.setString("aDepotCode", depotCode);
            }
            if(!dealerCodeEmpty){
            	query.setLong("aDealerCode", dealerCode);
            	countQuery.setLong("aDealerCode", dealerCode);
            }
            if(!dealerIdEmpty){
            	query.setLong("adealerId", dealerId);
            	countQuery.setLong("adealerId", dealerId);
            }
            if(!countryIdEmpty){
            	query.setLong("acountryId", countryId);
            	countQuery.setLong("acountryId", countryId);
            }
            
            
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}	
        	EmployeePaginationResponse response = new EmployeePaginationResponse();
            response.setEmployeeList( query.list() );
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), response.getEmployeeList().size(), recordQty.intValue() );
            
            return response;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByChriteria ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByChriteria/EmployeeDAO ==");
        }
	}

    /**
	 * Obtiene todos los documentos existentes de forma ordenada
	 * @return List<EmployeeVO> Listado de numero de documentos debidamente ordenados 
	 * @param Long dealerID
	 * @throws DAOServiceException 
	 * @throws DAOSQLException
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Employee> getEmployeesDocumentsByDealer(Long dealerID) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDocumentsOrdered/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select new ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" (e.id, e.documentNumber) from ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" e");
            queryBuffer.append(" where e.dealer.id = ?");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong(0, dealerID);
            
            return query.list();
            	
        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByLastName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDocumentsOrdered/EmployeeDAO ==");
        }
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> getEmployeesByPositionId(Long positionId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeesByPositionId/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select new ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" (e.id, e.documentNumber) from ");
            queryBuffer.append(Employee.class.getName());
            queryBuffer.append(" e");
            queryBuffer.append(" where e.position.id = :aPositionId");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("aPositionId", positionId);
            
            return query.list();
            	
        } catch (Throwable ex) {
            log.debug("== Error getEmployeesByPositionId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesByPositionId/EmployeeDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal#getActiveTechniciansQtyByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long getActiveTechniciansQtyByDealerId(Long dealerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveTechniciansQtyByDealerId/EmployeeDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select count(e.*) from ");
        	stringQuery.append(Employee.class.getName());
        	stringQuery.append(" e where e.dealer.id = :dealerId and e.employeeStatus.statusCode = :codeStatus  order by e.lastName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("codeStatus", CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());

            Long qty = (Long)query.uniqueResult();
			return qty;
        } catch (Throwable ex) {
            log.debug("== Error getActiveTechniciansQtyByDealerId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveTechniciansQtyByDealerId/EmployeeDAO ==");
        }
	}

    /**
     * Metodo: Obtiene un empleado por su identificador ibsTechnical
     * @param employeeId <optional>
     * @param ibsTechnical
     * @param countryId
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee getEmployeeByIbsTechnical(Long employeeId,Long ibsTechnical,Long countryId) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio getEmployeeByIbsTechnical/EmployeeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" select e ");
        	stringQuery.append("   from " + Employee.class.getName() + " e inner join e.city c "); 
        	stringQuery.append("                                           inner join c.state s  ");
        	stringQuery.append("                                           inner join s.country co ");
        	stringQuery.append("  where e.ibsTechnical = :ibsTechnical ");
        	stringQuery.append("    and co.id = :countryId ");
        	
        	if(employeeId!=null && employeeId.longValue()>0){
        		stringQuery.append("    and e.id != :employeeId ");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("ibsTechnical", ibsTechnical);
            query.setLong("countryId", countryId);
            
            if(employeeId!=null && employeeId.longValue()>0){
            	query.setLong("employeeId", employeeId);
            }
            Object obj = query.uniqueResult();

            if (obj != null && obj instanceof Employee) {
                return (Employee) obj;
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error getEmployeeByIbsTechnical ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeByIbsTechnical/EmployeeDAO ==");
        }
    }
    
}
