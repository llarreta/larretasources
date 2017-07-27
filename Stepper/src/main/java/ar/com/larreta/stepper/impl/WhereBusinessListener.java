package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.mystic.query.Where;
import ar.com.larreta.stepper.exceptions.BusinessException;

public abstract class WhereBusinessListener<W extends Where> extends StepImpl implements SourcedListener, TargetedListener, ValuedListener{

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		Query query = (Query) args[0];
		
		if ((source!=null) && (query!=null)){
			Object value = getValue(source, target, args);
			if (value==null){
				beanUtils.read(source, getSourceProperty());
			}
			if (!StringUtils.isEmpty((CharSequence) value)){
				//FIXME: Ver como implementar esta parte
				/*Like like = new Like(query, getTargetProperty(), value);
				Collection instanceArgs = new ArrayList<>();
				instanceArgs.add(query);
				instanceArgs.add(getTargetProperty());
				instanceArgs.add(value);
				query.addWhere((Where) beanUtils.newInstance(
						TypedClassesUtils.getGenerics(WhereBusinessListener.class, this, 0),
						instanceArgs.toArray()));*/
			}	
		}
		return null;
	}

	@Override
	public Object getValue(Serializable source, Serializable target, Object... args) {
		return null;
	}
	
}
