package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.school.persistence.PaymentDirection;
import ar.com.larreta.school.persistence.PaymentEntity;
import ar.com.larreta.school.persistence.PaymentUnit;
import ar.com.larreta.school.persistence.Product;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.impl.AddInnerJoin;
import ar.com.larreta.stepper.impl.BusinessConfig;
import ar.com.larreta.stepper.impl.CallAnotherBusinessListener;
import ar.com.larreta.stepper.impl.EntityAsignBusinessListener;
import ar.com.larreta.stepper.impl.IteratorListener;
import ar.com.larreta.stepper.impl.PropertyAsignBusinessListener;
import ar.com.larreta.stepper.messages.TargetedBody;

@Configuration
public class PaymentsBusinessConfig extends BusinessConfig {

	public static final String OBLIGATION_STATUS_AFTER_ITERATE_LISTENER = "obligationStatusAfterIterateListener";
	public static final String OBLIGATION 								= "obligation";
	public static final String PAID_OFF_LISTENER 						= "paidOffListener";
	public static final String STUDENT_ASIGN_LISTENER 					= "studentAsignListener";
	public static final String PAID_OFF 								= "paidOff";
	public static final String STUDENT 									= "student";
	public static final String PRODUCT_GROUP_ID 						= "productGroup.id";
	public static final String PRODUCT_GROUP 							= "productGroup";
	public static final String PAYMENT_PLAN_STUDENTS_ID 				= "paymentPlan.students.id";
	public static final String PAYMENT_PLAN_STUDENTS 					= "paymentPlan.students";
	public static final String PAYMENT_PLAN 							= "paymentPlan";
	public static final String PAYMENT_ENTITY 							= "paymentEntity";
	public static final String PAYMENT_DIRECTION	 					= "paymentDirection";
	public static final String PRODUCT 									= "product";
	public static final String PAYMENT_UNITS 							= "paymentUnits";
	public static final String PERSON_BENEFITING 						= "personBenefiting";
	public static final String ASIGN_PERSON_LISTENER 					= "asignPersonListener";
	public static final String PAY_UNITS 								= "payUnits";

	public static final String PAID_OFF_ASIGN_LISTENER 					= "paidOffAsignListener";
	public static final String STUDENT_BENEFITING_ASIGN_LISTENER 		= "studentBenefitingAsignListener";
	public static final String OBLIGATION_ASIGN_LISTENER 				= "obligationAsignListener";
	public static final String ITERATE_OBLIGATION_STATUS 				= "iterateObligationStatus";
	public static final String PAYMENTS_AFTER_PERSIST_LISTENER 			= "paymentsAfterPersistListener";
	public static final String CALL_CREATE_OBLIGATION_STATUS_LISTENER 	= "callCreateObligationStatusListener";
	public static final String OBLIGATION_STATUS_AFTER_LOAD_LISTENERS 	= "obligationStatusAfterLoadListeners";
	public static final String PRODUCT_GROUP_ASIGN_LISTENER 			= "productGroupAsignListener";
	public static final String PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER 	= "paymentStudentIdWhereEqualListener";
	public static final String OBLIGATION_STATUS_BEFORE_LOAD_LISTENERS 	= "obligationStatusBeforeLoadListeners";
	public static final String PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER = "paymentPlanStudentInnerJoinListener";
	public static final String PAYMENT_PLAN_INNER_JOIN_LISTENER 		= "paymentPlanInnerJoinListener";
	public static final String ASIGN_PAYMENT_ENTITY_LISTENER 			= "asignPaymentEntityListener";
	public static final String ASIGN_PAYMENT_DIRECTION_LISTENER 		= "asignPaymentDirectionListener";
	public static final String ASIGN_PRODUCT_LISTENER 					= "asignProductListener";
	public static final String PAYMENT_UNIT_BEFORE_PERIST_LISTENERS 	= "paymentUnitBeforePeristListeners";
	public static final String PAYMENT_BEFORE_PERSIST_LISTENERS 		= "paymentBeforePersistListener";
	public static final String PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE 	= "paymentUnitsListenerFrontToService";

	private EntityAsignBusinessListener<Person> 	  				asignPersonListener;
	private EntityAsignBusinessListener<Product> 	  				asignProductListener;
	private EntityAsignBusinessListener<PaymentDirection> 	  		asignPaymentDirectionListener;
	private EntityAsignBusinessListener<PaymentEntity> 	  			asignPaymentEntityListener;
	
	private IteratorListener<PaymentUnit> 							paymentUnitsListenerServiceToFront;
	
	private AddInnerJoin									paymentPlanInnerJoinListener;
	private AddInnerJoin									paymentPlanStudentInnerJoinListener;
	//private WhereBusinessListener<Equal>					paymentStudentIdWhereEqualListener;

	private PropertyAsignBusinessListener							productGroupAsignListener;
	private PropertyAsignBusinessListener							studentAsignListener;
	private PropertyAsignBusinessListener							paidOffListener;
	
	private CallAnotherBusinessListener 							callCreateObligationStatusListener;
	//private IteratorCallBusinessListener<ObligationStatus> 			iterateObligationStatus;
	
	private PropertyAsignBusinessListener							obligationAsignListener;
	private PropertyAsignBusinessListener							studentBenefitingAsignListener;
	private PropertyAsignBusinessListener							paidOffAsignListener;

	@Bean(name=PAID_OFF_ASIGN_LISTENER)
	public PropertyAsignBusinessListener paidOffAsignListener(){
		paidOffAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public Object getValue(Serializable source, Serializable target, Object... args) {
				return Boolean.TRUE;
			}

			@Override
			public String getTargetProperty() {
				return PAID_OFF;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return paidOffAsignListener;
	}
	
	@Bean(name=STUDENT_BENEFITING_ASIGN_LISTENER)
	public PropertyAsignBusinessListener studentBenefitingAsignListener(){
		studentBenefitingAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return STUDENT;
			}
			
			@Override
			public String getSourceProperty() {
				return PERSON_BENEFITING;
			}
		};
		return studentBenefitingAsignListener;
	}
	
	@Bean(name=OBLIGATION_ASIGN_LISTENER)
	public PropertyAsignBusinessListener obligationAsignListener(){
		obligationAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return OBLIGATION;
			}
			
			@Override
			public String getSourceProperty() {
				return PRODUCT;
			}
		};
		return obligationAsignListener;
	}
	
	/*@Bean(name=PAYMENTS_AFTER_PERSIST_LISTENER) 
	@DependsOn(value={ITERATE_OBLIGATION_STATUS})
	public Set<BusinessListener> paymentsAfterPersistListener(){
		return getSet(iterateObligationStatus);
	}
	
	@Bean(name=OBLIGATION_STATUS_AFTER_ITERATE_LISTENER) 
	@DependsOn(value={PAID_OFF_ASIGN_LISTENER, STUDENT_BENEFITING_ASIGN_LISTENER, OBLIGATION_ASIGN_LISTENER})
	public Set<BusinessListener> obligationStatusAfterIterateListener(){
		return getSet(paidOffAsignListener, studentBenefitingAsignListener, obligationAsignListener);
	}*/

	/*@Bean(name=ITERATE_OBLIGATION_STATUS) 
	@DependsOn(value={OBLIGATION_STATUS_AFTER_ITERATE_LISTENER})
	public 	IteratorCallBusinessListener<ObligationStatus> iterateObligationStatus(){
		iterateObligationStatus = new IteratorCallBusinessListener<ObligationStatus>(){
			@Override
			@Autowired @Qualifier(OBLIGATION_STATUS_AFTER_ITERATE_LISTENER)
			public void setAfterIterateListeners(Set<BusinessListener> afterIterateListeners) {
				super.setAfterIterateListeners(afterIterateListeners);
			}
			@Override
			public Serializable getSource(Serializable source, Serializable target, Object... args) {
				return target;
			}
			@Override
			@Autowired @Qualifier(CALL_CREATE_OBLIGATION_STATUS_LISTENER)
			public void setCallBusinessListeners(Set<BusinessListener> callBusinessListeners) {
				super.setCallBusinessListeners(callBusinessListeners);
			}
			@Override
			public String getSourceProperty() {
				return PAYMENT_UNITS;
			}
			@Override
			public String getTargetProperty() {
				return null;
			}
		};
		return iterateObligationStatus;
	}*/
	
	@Bean(name=CALL_CREATE_OBLIGATION_STATUS_LISTENER) 
	public CallAnotherBusinessListener callCreateObligationStatusListener(){
		callCreateObligationStatusListener = new CallAnotherBusinessListener() {
			@Override
			@Autowired @Qualifier(CreateObligationStatusBusiness.BUSINESS_NAME)
			public void setBusiness(Step business) {
				super.setBusiness(business);
			}
			@Override
			public Serializable getParam(Serializable source, Serializable target, Object... args) {
				return target;
			}
		};
		return callCreateObligationStatusListener;
	}
	
	/*@Bean(name=OBLIGATION_STATUS_AFTER_LOAD_LISTENERS) 
	@DependsOn(value={PRODUCT_GROUP_ASIGN_LISTENER, STUDENT_ASIGN_LISTENER, PAID_OFF_LISTENER})
	public Set<BusinessListener> obligationStatusAfterLoadListeners(){
		return getSet(productGroupAsignListener, studentAsignListener, paidOffListener);
	}*/
	
	@Bean(name=PAID_OFF_LISTENER) 
	public PropertyAsignBusinessListener paidOffListener(){
		paidOffListener = new PropertyAsignBusinessListener() {
			
			@Override
			public Object getValue(Serializable source, Serializable target, Object... args) {
				TargetedBody body = (TargetedBody) args[0];
				Obligation obligation = (Obligation) source;
				/*LoadArguments loadArgs = new LoadArguments(ObligationStatus.class);
				loadArgs.addWhereEqual("student.id", body.getTarget());
				loadArgs.addWhereEqual("obligation.id", obligation.getId());
				try {
					ObligationStatus obligationStatus = (ObligationStatus) standardDAO.getEntity(loadArgs);
					if (obligationStatus!=null){
						return obligationStatus.getPaidOff();
					}
				} catch (CantBuildQueryException e) {
					LOG.error("Ocurrio un error asignando paidOff", e);
				}*/
				return null;
			}

			@Override
			public String getTargetProperty() {
				return PAID_OFF;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return paidOffListener;
	}
	
	@Bean(name=STUDENT_ASIGN_LISTENER) 
	public PropertyAsignBusinessListener studentAsignListener(){
		studentAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public Object getValue(Serializable source, Serializable target, Object... args) {
				TargetedBody body = (TargetedBody) args[0];
				return body.getTarget();
			}

			@Override
			public String getTargetProperty() {
				return STUDENT;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return studentAsignListener;
	}
	
	@Bean(name=PRODUCT_GROUP_ASIGN_LISTENER) 
	public PropertyAsignBusinessListener productGroupAsignListener(){
		productGroupAsignListener = new PropertyAsignBusinessListener() {
			@Override
			public String getTargetProperty() {
				return PRODUCT_GROUP;
			}
			
			@Override
			public String getSourceProperty() {
				return PRODUCT_GROUP_ID;
			}
		};
		return productGroupAsignListener;
	}
	
	/*@Bean(name=OBLIGATION_STATUS_BEFORE_LOAD_LISTENERS) 
	@DependsOn(value={PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER, PAYMENT_PLAN_INNER_JOIN_LISTENER, PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER})
	public Set<BusinessListener> obligationStatusBeforeLoadListeners(){
		return getSet(paymentUnitsListenerServiceToFront);
	}
	
	@Bean(name=PAYMENT_STUDENT_ID_WHERE_EQUAL_LISTENER)
	public WhereBusinessListener<Equal> paymentStudentIdWhereEqualListener(){
		paymentStudentIdWhereEqualListener = new WhereBusinessListener<Equal>() {
			@Override
			public Object getValue(Serializable source, Serializable target, Object... args) {
				TargetedBody body = (TargetedBody) source;
				return body.getTarget();
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_PLAN_STUDENTS_ID;
			}
			
			@Override
			public String getSourceProperty() {
				return null;
			}
		};
		return paymentStudentIdWhereEqualListener;
	}*/
	

	@Bean(name=PAYMENT_PLAN_STUDENT_INNER_JOIN_LISTENER)
	public AddInnerJoin paymentPlanStudentInnerJoinListener(){
		paymentPlanStudentInnerJoinListener = new AddInnerJoin() {
			@Override
			public String getInnerJoin() {
				return PAYMENT_PLAN_STUDENTS;
			}
		};
		return paymentPlanStudentInnerJoinListener;
	}
	
	@Bean(name=PAYMENT_PLAN_INNER_JOIN_LISTENER)
	public AddInnerJoin paymentPlanInnerJoinListener(){
		paymentPlanInnerJoinListener = new AddInnerJoin() {
			@Override
			public String getInnerJoin() {
				return PAYMENT_PLAN;
			}
		};
		return paymentPlanInnerJoinListener;
	}
	
	/*@Bean(name=PAYMENT_BEFORE_PERSIST_LISTENERS) @DependsOn(value={PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE})
	public Set<BusinessListener> paymentBeforePersistListeners(){
		return getSet(paymentUnitsListenerServiceToFront);
	}
	
	@Bean(name=PAYMENT_UNIT_BEFORE_PERIST_LISTENERS) 
	@DependsOn(value={ASIGN_PERSON_LISTENER, ASIGN_PRODUCT_LISTENER, ASIGN_PAYMENT_DIRECTION_LISTENER, ASIGN_PAYMENT_ENTITY_LISTENER})
	public Set<BusinessListener> paymentUnitBeforePeristListeners(){
		return getSet(asignPersonListener, asignProductListener, asignPaymentDirectionListener, asignPaymentEntityListener);
	}*/

	@Bean(name=ASIGN_PAYMENT_ENTITY_LISTENER)
	public EntityAsignBusinessListener<PaymentEntity> asignPaymentEntityListener(){
		asignPaymentEntityListener = new EntityAsignBusinessListener<PaymentEntity>() {
			@Override
			public String getSourceProperty() {
				return PAYMENT_ENTITY;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_ENTITY;
			}
		};
		return asignPaymentEntityListener;
	}
	
	@Bean(name=ASIGN_PAYMENT_DIRECTION_LISTENER)
	public EntityAsignBusinessListener<PaymentDirection> asignPaymentDirectionListener(){
		asignPaymentDirectionListener = new EntityAsignBusinessListener<PaymentDirection>() {
			@Override
			public String getSourceProperty() {
				return PAYMENT_DIRECTION;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_DIRECTION;
			}
		};
		return asignPaymentDirectionListener;
	}
	
	@Bean(name=ASIGN_PRODUCT_LISTENER)
	public EntityAsignBusinessListener<Product> asignProductListener(){
		asignProductListener = new EntityAsignBusinessListener<Product>() {
			@Override
			public String getSourceProperty() {
				return PRODUCT;
			}

			@Override
			public String getTargetProperty() {
				return PRODUCT;
			}
		};
		return asignProductListener;
	}
	
	@Bean(name=ASIGN_PERSON_LISTENER)
	public EntityAsignBusinessListener<Person> asignPersonListener(){
		asignPersonListener = new EntityAsignBusinessListener<Person>() {
			@Override
			public String getSourceProperty() {
				return PERSON_BENEFITING;
			}

			@Override
			public String getTargetProperty() {
				return PERSON_BENEFITING;
			}
		};
		return asignPersonListener;
	}
	
	@Bean(name=PAYMENT_UNITS_LISTENER_FRONT_TO_SERVICE)
	public IteratorListener<PaymentUnit> paymentUnitsListenerFrontToService(){
		paymentUnitsListenerServiceToFront =  new IteratorListener<PaymentUnit>() {
			/*@Override
			@Autowired @Qualifier(PaymentsBusinessConfig.PAYMENT_UNIT_BEFORE_PERIST_LISTENERS)
			public void setBeforeIterateListeners(Set<BusinessListener> beforePersistListeners) {
				super.setBeforeIterateListeners(beforePersistListeners);
			}*/

			@Override
			public String getSourceProperty() {
				return PAY_UNITS;
			}

			@Override
			public String getTargetProperty() {
				return PAYMENT_UNITS;
			}
		};
		return paymentUnitsListenerServiceToFront;
	}
	
}
