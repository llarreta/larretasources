package ar.com.larreta.colegio.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.commons.domain.ParametricEntity;

@Entity
@Table(name = "productGroup")
public class ProductGroup extends ParametricEntity {

	public ProductGroup() {
		super();
	}

}
