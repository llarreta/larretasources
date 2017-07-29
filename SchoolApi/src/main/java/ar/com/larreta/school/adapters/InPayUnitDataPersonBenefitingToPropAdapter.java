package ar.com.larreta.school.adapters;

import org.springframework.stereotype.Component;

import ar.com.larreta.school.persistence.Student;
import ar.com.larreta.stepper.adapters.LongToEntityAdapter;

@Component("InPayUnitDataPersonBenefitingToPropAdapter")
public class InPayUnitDataPersonBenefitingToPropAdapter extends LongToEntityAdapter {

	@Override
	public String getPropertyTarget(String propertyName) {
		return "student";
	}

	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		return super.process(toAdapt, Student.class, generics);
	}
	
}
