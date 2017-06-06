package ar.com.larreta.school.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.ParametricEntity;

@Entity @Component @Scope("prototype")
@Table(name = "paymentEntity")
public class PaymentEntity extends ParametricEntity {

}
