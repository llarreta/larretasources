package ar.com.larreta.school.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.persistence.model.ParametricEntity;

@Entity
@Table(name = "year")
public class Year extends ParametricEntity{

}
