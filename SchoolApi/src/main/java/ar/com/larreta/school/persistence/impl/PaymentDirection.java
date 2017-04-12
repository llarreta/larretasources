package ar.com.larreta.school.persistence.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.larreta.persistence.model.ParametricEntity;

@Entity
@Table(name = "paymentDirection")
public class PaymentDirection extends ParametricEntity{

}
