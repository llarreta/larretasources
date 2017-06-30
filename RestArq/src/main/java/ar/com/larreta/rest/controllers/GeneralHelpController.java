package ar.com.larreta.rest.controllers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import ar.com.larreta.rest.exceptions.NoHelpException;
import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.MappingEntry;
import ar.com.larreta.rest.messages.Message;
import ar.com.larreta.rest.messages.Response;
import ar.com.larreta.rest.messages.status.OK;
import ar.com.larreta.rest.messages.status.State;
import ar.com.larreta.tools.SpringUtils;

@RestController
@RequestMapping(value=GeneralHelpController.ROOT_MAP)
public class GeneralHelpController {

	public static final String ROOT_MAP = "/help";

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	protected HttpServletRequest servletRequest;

	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePostGet() throws RestException{
		return helpProcess();
	}
	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePostPost() throws RestException{
		return helpProcess();
	}
	
	@RequestMapping(value = "/**", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message help() throws RestException{
		try {
			String url = servletRequest.getRequestURL().toString();
			String contextPath = servletRequest.getContextPath();
			
			String pattern = url.substring(url.indexOf(contextPath) + ROOT_MAP.length() + contextPath.length());
			
			Message bean = (Message) applicationContext.getBean(pattern);
			
			return bean;
		} catch (Exception e){
			throw new NoHelpException();
		}
	}


	private Response helpProcess() {
		JSONableCollection<MappingEntry> body = new JSONableCollection<>();

		Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
		Set<RequestMappingInfo>  setInfo = map.keySet();
		Iterator<RequestMappingInfo> it = setInfo.iterator();
		while (it.hasNext()) {
			RequestMappingInfo requestMappingInfo = (RequestMappingInfo) it.next();
			MappingEntry entry = new MappingEntry();
			entry.setPatterns(requestMappingInfo.getPatternsCondition().toString());
			entry.setMethods(requestMappingInfo.getMethodsCondition().toString());
			entry.setProduces(requestMappingInfo.getProducesCondition().toString());
			body.add(entry);
		}
		
		Response<JSONableCollection<MappingEntry>> response = new Response<>();
		response.setState((State) SpringUtils.getBean(OK.class));
		response.setBody(body);
		
		return response;
	}

}
