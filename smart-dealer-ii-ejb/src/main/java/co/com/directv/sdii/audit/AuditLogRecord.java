package co.com.directv.sdii.audit;

import java.util.Date;

/**
 * Clase persistente que representa el registro de auditoria para las
 * clases que son Auditables.
 * 
 * @author Jimmy Vélez Muñoz
 */

public class AuditLogRecord {
    // place holder for storing the current entity
	 private Auditable entity;
     private Long id;
     private String message;
     private String entityName;
     private String entityId;
     private String entityAttribute;
     private String oldValue;
     private String newValue;
     private String updatedBy;
     private Date updatedDate;

    /**
     * @return Returns the entity.
     */
    public Auditable getEntity() {
        return entity;
    }

    // Constructors

    /** default constructor */
    public AuditLogRecord() {
    }

    /**
     * 
     * @param id
     */
    public AuditLogRecord(Long id) {
        this.id = id;
    }


    // Property accessors

    /**
     *
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     */
    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     *
     */
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     *
     */
    public String getEntityAttribute() {
        return this.entityAttribute;
    }

    public void setEntityAttribute(String entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     *
     */
    public String getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     *
     */
    public String getNewValue() {
        return this.newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     *
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     */
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }


    /**
     * @param entity The entity to set.
     */
    public void setEntity(Auditable entity) {
        this.entity = entity;
    }

}
