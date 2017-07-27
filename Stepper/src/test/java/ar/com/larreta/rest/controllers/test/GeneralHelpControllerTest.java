package ar.com.larreta.rest.controllers.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.JsonNode;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.configs.test.RestTestConfig;
import ar.com.larreta.stepper.controllers.GeneralHelpController;
import ar.com.larreta.stepper.messages.status.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestTestConfig.class})
@WebAppConfiguration
public class GeneralHelpControllerTest extends ControllerTest{
	private static @Log Logger LOG;	
	
	@Autowired
	private GeneralHelpController controller;
	
    @Test
    public void test() throws Exception {
    	
    	ResultActions resultActions = prepareCall(
					    							GeneralHelpController.ROOT_MAP, 
					    							status().isOk(), 
					    							content().contentType(TestUtil.APPLICATION_JSON_UTF8),
					    							jsonPath("$[0].state").value(context.getBean(OK.class).getCode())
					    						 );
    	
    	
    	MvcResult result = resultActions.andReturn();
    	
    	JsonNode jsonNode = parser.parse(result.getResponse().getContentAsString());
    	
    	JsonNode state = jsonNode.get("state");
    	
    	
    	
    	
    	LOG.info(result.getResponse().getContentAsString());
    }
    
}
