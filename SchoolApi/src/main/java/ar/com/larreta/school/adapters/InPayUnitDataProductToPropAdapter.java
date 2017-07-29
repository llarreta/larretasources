package ar.com.larreta.school.adapters;

import org.springframework.stereotype.Component;

import ar.com.larreta.school.persistence.Obligation;
import ar.com.larreta.stepper.adapters.LongToEntityAdapter;

@Component("InPayUnitDataProductToPropAdapter")
public class InPayUnitDataProductToPropAdapter extends LongToEntityAdapter {

	@Override
	public String getPropertyTarget(String propertyName) {
		return "obligation";
	}

	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		return super.process(toAdapt, Obligation.class, generics);
	}

}
