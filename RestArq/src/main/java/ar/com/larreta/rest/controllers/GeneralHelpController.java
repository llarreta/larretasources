package ar.com.larreta.rest.controllers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import ar.com.larreta.rest.exceptions.RestException;
import ar.com.larreta.rest.messages.JSONableCollectionBody;
import ar.com.larreta.rest.messages.MappingEntry;
import ar.com.larreta.rest.messages.Response;

@RestController
@RequestMapping(value="/help")
public class GeneralHelpController {

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	
	@RequestMapping(value = StringUtils.EMPTY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response sourcePost() throws RestException{
		JSONableCollectionBody<MappingEntry> body = new JSONableCollectionBody<>();
		
		Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
		Set<RequestMappingInfo>  setInfo = map.keySet();
		Iterator<RequestMappingInfo> it = setInfo.iterator();
		while (it.hasNext()) {
			RequestMappingInfo requestMappingInfo = (RequestMappingInfo) it.next();
			MappingEntry entry = new MappingEntry();
			entry.setPatterns(requestMappingInfo.getPatternsCondition().toString());
			entry.setVerbs(requestMappingInfo.getMethodsCondition().toString());
			entry.setProduces(requestMappingInfo.getProducesCondition().toString());
			body.add(entry);
		}
		
		Response<JSONableCollectionBody<MappingEntry>> response = new Response<>();
		response.setBody(body);
		
		return response;
	}

}
