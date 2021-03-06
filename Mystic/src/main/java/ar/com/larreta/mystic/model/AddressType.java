package ar.com.larreta.mystic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "addressType")
public class AddressType extends ParametricEntity {

}
