package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Serialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Serialized
 * @see co.com.directv.sdii.model.hbm.Serialized.hbm.xml
 */
@Stateless(name="SerializedDAOLocal",mappedName="ejb/SerializedDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SerializedDAO extends BaseDao implements SerializedDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SerializedDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SerializedsDAOLocal#createSerialized(co.com.directv.sdii.model.pojo.Serialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSerialized(Serialized obj) throws DAOServiceException, DAOSQLException, BusinessException {

        log.debug("== Inicio createSerialized/SerializedDAO ==");
        Session session = super.getSession();
        try {
        	if(obj!=null){
            	if(obj.getSerialCode()!=null){
            		obj.setSerialCode(obj.getSerialCode().toUpperCase());
            	}
            	if(obj.getSerialized()!=null){
            		if(obj.getSerialized().getSerialCode()!=null){
            			obj.getSerialized().setSerialCode(obj.getSerialized().getSerialCode().toUpperCase());
            		}
            	}
            }
        	boolean guardarPorRid=true;
        	if(obj.getIrd()!=null){
            	if(!(obj.getIrd().trim().equalsIgnoreCase("") || !existTheSameRid(obj.getIrd()))){
            		guardarPorRid=false;
            	}
        	}
        	if(guardarPorRid){
        		session.save(obj);
                super.doFlush(session);           		
        	}else{
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN501.getCode(), ErrorBusinessMessages.STOCK_IN501.getMessage());
        	}

        } catch (Throwable ex) {
            log.error("== Error creando el Serialized ==");
            if(ex instanceof BusinessException){
				try {
					throw (BusinessException)ex;
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
            }
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSerialized/SerializedDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SerializedsDAOLocal#updateSerialized(co.com.directv.sdii.model.pojo.Serialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSerialized(Serialized obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSerialized/SerializedDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el Serialized ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSerialized/SerializedDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SerializedsDAOLocal#deleteSerialized(co.com.directv.sdii.model.pojo.Serialized)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSerialized(Serialized obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSerialized/SerializedDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Serialized entity where entity.elementId = :anEntityId");
            query.setLong("anEntityId", obj.getElementId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el Serialized ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSerialized/SerializedDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SerializedsDAOLocal#getSerializedsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Serialized getSerializedByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSerializedByID/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where entity.elementId = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Serialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedByID/SerializedDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SerializedsDAOLocal#getAllSerializeds()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Serialized> getAllSerializeds() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSerializeds/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSerializeds/SerializedDAO ==");
        }
    }

    public boolean existTheSameRid(String rid) throws DAOServiceException, DAOSQLException{
    	//select count(atributo) from Entidad entidad where entidad.atributo=:variable
        log.debug("== Inicia existTheSameRid/SerializedDAO ==");
        Session session = super.getSession();
        try{
        	Query q = session.createQuery("from Serialized s where s.ird=:rid");
        	q.setString("rid", rid);
        	List result = q.list();

        	Integer cuantosRegistros = 0;
        	if(result!=null){
        		cuantosRegistros = result.size();
        	}
        	if(cuantosRegistros!=0)
        		return true;
        	return false;
        }catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina existTheSameRid/SerializedDAO ==");
		}
    }   


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Serialized getSerializedBySerial(String serial,Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerial/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from " + Serialized.class.getName() + " entity ");
        	stringQuery.append(" where entity.serialCode = :aSerial ");
        	stringQuery.append(" and entity.element.country.id = :aCountryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aSerial", serial.toUpperCase());
            query.setLong("aCountryId", countryId);

            return (Serialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerial/SerializedDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Serialized getSerializedBySerial(String serial,String countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerial/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from " + Serialized.class.getName() + " entity ");
        	stringQuery.append(" where entity.serialCode = :aSerial ");
        	stringQuery.append(" and entity.element.country.countryCode = :aCountryCode ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aSerial", serial.toUpperCase());
            query.setString("aCountryCode", countryCode);

            return (Serialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerial/SerializedDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Map<String, Object[]> getSerialExists(List<String> serialCodes,Long countrId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerialExists/SerializedDAO ==");
        Session session = super.getSession();
        Map<String, Object[]> returnValue = new HashMap<String, Object[]>();
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" SELECT ET.TYPE_ELEMENT_CODE typeElementCode, ");
        	stringQuery.append("        SL.SERIAL_CODE serialCodeLink, ");
        	stringQuery.append("        S.ELEMENT_ID elementId,");
        	stringQuery.append("        S.SERIAL_CODE serialCode");
        	stringQuery.append("   FROM SERIALIZED s inner join ELEMENTS e on(E.ID=S.ELEMENT_ID) ");
        	stringQuery.append("        inner join ELEMENT_TYPES et on(ET.ID=E.ELEMENT_TYPE_ID) ");
        	stringQuery.append("        left join SERIALIZED sl on(SL.ELEMENT_ID=S.LINKED_SERIAL_CODE) ");
        	stringQuery.append(" WHERE e.COUNTRY_ID = :aCountryId ");
        	
        	boolean isFirstCondition = true;
        	int tam=serialCodes.size();
        	for(int i=0; i<tam; ++i){
        		if(isFirstCondition){
        			isFirstCondition=false;
        			stringQuery.append(" AND ( S.SERIAL_CODE = :aSerial"+i+" ");
        		}else{
        			stringQuery.append(" OR S.SERIAL_CODE = :aSerial"+i+" ");
        		}
        	}
    		if(!isFirstCondition){
    			stringQuery.append(" ) ");
    		}

        	Query query = session.createSQLQuery(stringQuery.toString());
        	
        	query.setLong("aCountryId",countrId);
        	
        	int count=0;
    		for(String code: serialCodes){
    			query.setString("aSerial"+count, code.toUpperCase());
    			++count;
    		}
    		List<Object[]> serializedElements =query.list();
    		for(Object[] typeAndLinked : serializedElements){
//    			Object[] typeAndLinked = new Object[3];
//    			typeAndLinked[0] = "";
//    			typeAndLinked[1] = "";
//    			typeAndLinked[2] = UtilsBusiness.copyObject( ElementVO.class, serialized.getElement());
//        		if(serialized.getElement() != null && serialized.getElement().getElementType() != null && serialized.getElement().getElementType().getTypeElementCode() != null) {
//        			typeAndLinked[0] = serialized.getElement().getElementType().getTypeElementCode();
//        			if(serialized.getSerialized()!=null){
//        				typeAndLinked[1] = serialized.getSerialized().getSerialCode();
//        			}
//        		}
        		returnValue.put((String) typeAndLinked[3], typeAndLinked);
    		}

            return returnValue;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerial/SerializedDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean isSerializedBySerial(String serial) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio isSerializedBySerial/SerializedDAO ==");
        Session session = super.getSession();
        BigDecimal count = new BigDecimal(0);
        boolean result = false;
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" SELECT count(1) ");
        	stringQuery.append(" FROM SERIALIZED ");
        	stringQuery.append(" WHERE SERIAL_CODE = :aSerial ");

        	Query query = session.createSQLQuery(stringQuery.toString());
            query.setString("aSerial", serial.toUpperCase());

            count = (BigDecimal) query.uniqueResult();
            
            if(count != null && count.longValue() > 0)
            	result = true;
            return result;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina isSerializedBySerial/SerializedDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerial(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedByListSerial(StringBuffer stringSerials) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerial/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" SELECT new " + Serialized.class.getName() + "(entity.elementId, ");
        	stringQuery.append("        entity.serialized.id, ");
        	stringQuery.append("        entity.serialCode, ");
        	stringQuery.append("        entity.ird, ");
        	stringQuery.append("        entity.registrationDate) ");
        	stringQuery.append(" FROM " + Serialized.class.getName() + " entity ");
        	stringQuery.append(stringSerials.toString());
        	
        	Query query = session.createQuery(stringQuery.toString());
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerial/SerializedDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedByElementId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedByElementId(Long elementId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedByElementId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" se where se.elementId = :aElementId");         	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aElementId", elementId);
        	
            List<Serialized> list = query.list();
			return list;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedByElementId/SerializedDAO ==");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedByElementIdUnconfirmed(Long elementId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedByElementIdUnconfirmed/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" se join se.element as el"); 
        	stringQuery.append(" where se.elementId = :aElementId "); 
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aElementId", elementId);
            
            List<Serialized> list = query.list();
			return list;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedByElementIdUnconfirmed/SerializedDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedsByWareHouseId(co.com.directv.sdii.model.pojo.Warehouse)
	 */
	public List<Serialized> getSerializedsByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getSerializedsByWareHouseId/NotSerializedDAO ==");
        Session session = super.getSession();
        List<Serialized> lista = null;
        
        try {
        	
        	StringBuffer buffer = new StringBuffer();
        	buffer.append("select distinct whouse.serialized from ");
        	buffer.append( WarehouseElement.class.getName());
        	buffer.append( " as whouse where whouse.warehouseId.id = :idWareHouse " );
        	buffer.append( "and whouse.recordStatus.recordStatusCode = :recordStatus " );
        	
        	//garantizar que un elemento que ya se retornó como vinculado, no se retorne como un registro adicional
        	buffer.append(" and whouse.serialized.elementId not in (select distinct we.serialized.serialized.elementId from ")
        		.append(WarehouseElement.class.getName()).append(" we")
	        	.append(" where we.warehouseId.id = :idWareHouse and we.recordStatus.recordStatusCode = :recordStatus")
	        	.append(" and we.serialized.serialized.elementId is not null)");
        	
        	Query query = session.createQuery(buffer.toString());
        	
        	query.setParameter( "idWareHouse",warehouseId.getId() );
        	query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
        	
        	lista = query.list();
            
        } catch (Throwable ex){
        	log.error("== Error =="+ ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedsByWareHouseId/NotSerializedDAO ==");
        }
		return lista;
	}
	
	/**
	 * Metodo: Regresa una instancia de Serialized consultada por el IRD
	 * @return una instancia de Serialized consultada por el IRD
	 * @param  el IRD involucrado en la busqueda 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la tarea
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public Serialized getSerializedByIRD(String ird) throws DAOServiceException, DAOSQLException
	{
		 log.debug("== Inicio getSerializedByIRD/SerializedDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Serialized.class.getName());
	        	stringQuery.append(" entity where entity.ird = :irdRegistry");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setParameter("irdRegistry", ird);

	            return (Serialized) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getSerializedByIRD/SerializedDAO ==");
	        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerialAndElementType(java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Serialized getSerializedBySerialAndElementType (String serialCode,
			                                               Long elementType,
			                                               Long countryId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedBySerialAndElementType/SerializedDAO ==");
        Session session = super.getSession();
        
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append( " entity where  " );
        	stringQuery.append(" entity.serialCode = :serialCode ");
        	stringQuery.append(" and entity.element.elementType.id = :elementType ");
        	stringQuery.append(" and entity.element.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
    		query.setString( "serialCode" , serialCode.toUpperCase());
    		query.setLong( "elementType" , elementType );
    		query.setLong("countryId", countryId );
            
        	return (Serialized) query.uniqueResult();
            
        } catch (Throwable ex){
			log.error("== Error en getSerializedBySerialAndElementType/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerialAndElementType/SerializedDAO ==");
        }	
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerialAndElementType(java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Serialized getSerializedBySerialAndElementsTypes (String serialCode,
			                                                 String elementsTypes,
			                                                 Long countryId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedBySerialAndElementType/SerializedDAO ==");
        Session session = super.getSession();
        
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append( " entity where  " );
        	stringQuery.append(" entity.serialCode = :serialCode ");
        	stringQuery.append(" and entity.element.elementType.id in ("+elementsTypes+")");
        	stringQuery.append(" and entity.element.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
    		query.setString( "serialCode" , serialCode.toUpperCase());
    		query.setLong("countryId", countryId );
            
        	return (Serialized) query.uniqueResult();
            
        } catch (Throwable ex){
			log.error("== Error en getSerializedBySerialAndElementType/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerialAndElementType/SerializedDAO ==");
        }	
	}
	
	@SuppressWarnings("unchecked")
	public List<Serialized> getSerialLinkedBySerialAndElementType (String serialCode,Long elementType,Long countryId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerialLinkedBySerialAndElementType/SerializedDAO ==");
        Session session = super.getSession();
        
        try {
        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append( " entity where  " );
        	stringQuery.append(" entity.serialized.serialCode = :serialCode ");
        	stringQuery.append(" and entity.serialized.element.elementType.id = :elementType ");
        	stringQuery.append(" and entity.serialized.element.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
    		query.setString( "serialCode" , serialCode.toUpperCase());
    		query.setLong( "countryId" , countryId );
            
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error en getSerialLinkedBySerialAndElementType/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerialLinkedBySerialAndElementType/SerializedDAO ==");
        }	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedByElementIdBySerialCode(Long elementId,String serialCode) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedByElementId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select serialized ");
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" serialized join serialized.element element");         	
        	stringQuery.append(" where element.isSerialized= :pIsSerialized and serialized.element.id= :pElementId and serialized.serialCode= :pSerialCode "); 
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setString("pIsSerialized", CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() );
        	query.setLong("pElementId", elementId);
        	query.setString("pSerialCode", serialCode.toUpperCase());
        	
            List<Serialized> list = query.list();
			return list;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedByElementId/SerializedDAO ==");
        }
	}
	
	public List<Object[]> getSerializedsAndWhEntryDateByWareHouseId( Warehouse warehouseId ) throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicia getSerializedsAndWhEntryDateByWareHouseId/NotSerializedDAO ==");
        Session session           = super.getSession();
        List<Object[]> lista = null;
        
        try {
        	
        	StringBuffer buffer = new StringBuffer();
        	//buffer.append("select distinct whouse.serialized,whouse.entryDate from ");
        	buffer.append("select distinct whouse.serialized,whouse.movementDate from ");
        	buffer.append( WarehouseElement.class.getName());
        	buffer.append( " as whouse where whouse.warehouseId.id = :idWareHouse " );
        	//buffer.append( "and whouse.exitDate = null " );
        	buffer.append( "and whouse.recordStatus.recordStatusCode = :recordStatus " );
        	buffer.append( "and whouse.notSerialized = null " );
        	Query query = session.createQuery(buffer.toString());
        	query.setLong( "idWareHouse",warehouseId.getId() );
        	query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
        	lista = query.list();
            
        } catch (Throwable ex){
        	log.error("== Error =="+ ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedsAndWhEntryDateByWareHouseId/NotSerializedDAO ==");
        }
		return lista;
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getLinkedSerializedBySerializedId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> getLinkedSerialExists(List<String> serialized,Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLinkedSerializedBySerializedId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	        	
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append(" SELECT DISTINCT SL.SERIAL_CODE serialCodeLink");
        	stringQuery.append("   FROM SERIALIZED s inner join SERIALIZED sl on(SL.ELEMENT_ID=S.LINKED_SERIAL_CODE) ");
        	stringQuery.append("                     INNER JOIN elements e ON (E.ID = S.ELEMENT_ID) ");
        	stringQuery.append("  WHERE e.COUNTRY_ID = :countryId ");
        	 
        	boolean isFirstCondition = true;
        	int tam=serialized.size();
        	for(int i=0; i<tam; ++i){
        		if(isFirstCondition){
        			isFirstCondition=false;
        			stringQuery.append(" and ( SL.SERIAL_CODE = :anId"+i+" ");
        		}else{
        			stringQuery.append(" or SL.SERIAL_CODE = :anId"+i+" ");
        		}
        	}
    		if(!isFirstCondition){
    			stringQuery.append(" ) ");
    		}

        	Query query = session.createSQLQuery(stringQuery.toString());
        	query.setLong("countryId", countryId);
        	
        	int count=0;
    		for(String code: serialized){
    			query.setString("anId"+count, code.toUpperCase());
    			++count;
    		}
        	
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getLinkedSerializedBySerializedId/SerializedDAO ==");
        }        
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getLinkedSerializedBySerializedId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getLinkedSerializedBySerializedId(Long serialized)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLinkedSerializedBySerializedId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	        	
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where entity.serialized.elementId = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", serialized);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getLinkedSerializedBySerializedId/SerializedDAO ==");
        }        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getSerializedElementsByImportLogId(Long idImportLog) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedByElementId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select serialize from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" serialize, ");
        	stringQuery.append(ImportLogItem.class.getName());
        	stringQuery.append(" ili where ");
        	stringQuery.append("ili.importLog.id = :idImportLog ");         	
        	stringQuery.append("and ili.element = serialize.elementId ");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("idImportLog", idImportLog);
        	
            List<Serialized> list = query.list();
			return list;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedByElementId/SerializedDAO ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Serialized getSerializedElementByElementId(Long elementId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getSerializedElementByElementId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" se where se.elementId = :aElementId");         	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aElementId", elementId);
        	
        	return (Serialized) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error en la operacion getSerializedElementByElementId/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedElementByElementId/SerializedDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal#getSerializedBySerialAndTypeId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Serialized getSerializedBySerialAndTypeId(String serial,Long typeId,Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSerializedBySerialAndTypeId/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where entity.serialCode = :aSerial ");
        	stringQuery.append(" and entity.element.elementType.id = :typeId");
        	stringQuery.append(" and entity.element.country.id = :countryId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aSerial", serial.toUpperCase());
            query.setLong("typeId", typeId);
            query.setLong("countryId", countryId);

            return (Serialized) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSerializedBySerialAndTypeId/SerializedDAO ==");
        }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Serialized> getLinkedSerializedBySerialCode(String linkedSerialCode,Long countryId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getLinkedSerializedBySerialCode/SerializedDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Serialized.class.getName());
        	stringQuery.append(" entity where entity.serialized.serialCode = :aLinkedSerialCode ");
        	stringQuery.append(" and entity.serialized.element.country.id = :countryId ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aLinkedSerialCode", linkedSerialCode.toUpperCase());
            query.setLong("countryId", countryId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error en la operacion getLinkedSerializedBySerialCode/SerializedDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getLinkedSerializedBySerialCode/SerializedDAO ==");
        }    
	}
	
}
