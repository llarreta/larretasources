package ar.com.larreta.stepper.controllers;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.ResolvableType;

import ar.com.larreta.stepper.StepElement;
import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.messages.Message;
import ar.com.larreta.stepper.messages.NotRequestNeeded;
import ar.com.larreta.stepper.messages.Request;
import ar.com.larreta.stepper.messages.TargetedBody;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class HelpConfig <UpdateBodyRequest extends Body, LoadBodyRequest extends Body> extends StepElement {

	public Message getCreateHelp(){
		Class<?>[] generics = ResolvableType.forClass(HelpConfig.class, getClass()).resolveGenerics();
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		Body body = (Body) applicationContext.getBean(generics[0]);
		
		JSONable json = body;
		
		instanceJsonables(json);

		request.setBody(body);
		return request;
	}

	private void instanceJsonables(JSONable json) {
		Collection<PropertyDescriptor> descriptors = Arrays.asList(PropertyUtils.getPropertyDescriptors(json));
		Iterator<PropertyDescriptor> it = descriptors.iterator();
		
		while (it.hasNext()) {
			PropertyDescriptor propertyDescriptor = (PropertyDescriptor) it.next();
			Class type = propertyDescriptor.getPropertyType();
				
			if (JSONable.class.isAssignableFrom(propertyDescriptor.getPropertyType())){
				JSONable asignable = null;
				if (JSONableCollection.class.isAssignableFrom(type)){
					asignable = new JSONableCollection<>();
					Class collectionType = TypedClassesUtils.getGeneric(json.getClass(), propertyDescriptor.getName(), 0);
					if (JSONable.class.isAssignableFrom(collectionType)){
						JSONable unitOfCollection = (JSONable) applicationContext.getBean(collectionType);
						((JSONableCollection)asignable).add(unitOfCollection);
						instanceJsonables(unitOfCollection);
					}
				} else {
					asignable = (JSONable) applicationContext.getBean(propertyDescriptor.getPropertyType());
					instanceJsonables(asignable);
				}
				beanUtils.write(json, propertyDescriptor.getName(), asignable);
			}
		}
	}

	public Message getUpdateHelp(){
		return getCreateHelp();
	}
	
	public Message getTargetedRequest(){
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		request.setBody(applicationContext.getBean(TargetedBody.class));
		return request;
	}
	
	public Message getLoadHelp(){
		Class<?>[] generics = ResolvableType.forClass(HelpConfig.class, getClass()).resolveGenerics();
		Request<Body> request = (Request<Body>) applicationContext.getBean(Request.COMPONENT_NAME);
		request.setBody((Body) applicationContext.getBean(generics[1]));
		return request;
	}
	
	public Message getNotRequestNeeded(){
		return (Message) applicationContext.getBean(NotRequestNeeded.COMPONENT_NAME);
	}
	
}
