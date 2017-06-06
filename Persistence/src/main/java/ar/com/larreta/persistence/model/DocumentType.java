package ar.com.larreta.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity @Component @Scope("prototype")
@Table(name = "documentType")
public class DocumentType extends ParametricEntity {

}
