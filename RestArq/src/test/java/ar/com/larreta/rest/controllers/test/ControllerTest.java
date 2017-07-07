package ar.com.larreta.rest.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ar.com.larreta.tools.JSONParser;
public abstract class ControllerTest {
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext context;
	@Autowired
	protected JSONParser parser;
	
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    protected ResultActions prepareCall(String url, ResultMatcher ... matchers) throws Exception{
    	ResultActions resultActions = mockMvc.perform(get(url));
    	if (matchers!=null){
    		Collection<ResultMatcher> collectionMatchers = Arrays.asList(matchers);
    		Iterator<ResultMatcher> it = collectionMatchers.iterator();
    		while (it.hasNext()) {
				ResultMatcher resultMatcher = (ResultMatcher) it.next();
				resultActions = resultActions.andExpect(resultMatcher);
			}
    	}
    	return resultActions;
    }

}
