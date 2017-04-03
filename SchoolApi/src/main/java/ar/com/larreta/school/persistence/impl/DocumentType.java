package ar.com.larreta.school.persistence.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.persistence.model.impl.PersistenceParametricEntityImpl;

@Entity
@Table(name = "documentType")
public class DocumentType extends PersistenceParametricEntityImpl {

}
