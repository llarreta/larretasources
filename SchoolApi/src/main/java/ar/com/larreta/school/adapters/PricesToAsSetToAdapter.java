package ar.com.larreta.school.adapters;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ar.com.larreta.school.messages.DetailData;
import ar.com.larreta.school.persistence.Detail;
import ar.com.larreta.school.persistence.Price;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Adapter;
import ar.com.larreta.tools.BeanUtils;

@Component("PricesToAsSetToAdapter")
public class PricesToAsSetToAdapter implements Adapter {

	public static final String DETAILS = "details";
	
	@Autowired
	private BeanUtils beanUtils;

	@Autowired
	protected ApplicationContext applicationContext;
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) {
		
		Set<Price> prices = (Set<Price>) toAdapt;
		if (prices.size()>0){
			JSONableCollection collection = new JSONableCollection<>();
			Set<Detail> details = (Set<Detail>) beanUtils.read(prices.iterator().next(), DETAILS);
			Iterator<Detail> it = details.iterator();
			while (it.hasNext()) {
				Detail detail = (Detail) it.next();
				DetailData detailData = applicationContext.getBean(DetailData.class);
				beanUtils.copy(detail, detailData);
				collection.add(detailData);
			}
			return collection;
		}
		return null;
	}

	@Override
	public String getPropertyTarget(String propertyName) {
		return DETAILS;
	}

}
