package ar.com.larreta.smarttrace.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.commons.domain.ParametricEntity;

/**
 * Represetna un tipo de material o mercaderia
 * Ejm. Yerba, leche, chorizo, arroz
 */
@Entity
@Table(name = "classification")
public class Classification extends ParametricEntity {

}
