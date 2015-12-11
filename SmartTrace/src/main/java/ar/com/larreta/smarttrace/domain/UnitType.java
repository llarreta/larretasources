package ar.com.larreta.smarttrace.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.commons.domain.ParametricEntity;

/**
 * Represetna una unidad cuantificadora de medida
 * Por ejm, litros, peso, etc
 */
@Entity
@Table(name = "unitType")
public class UnitType extends ParametricEntity {

}
