package ar.com.larreta.rest.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ar.com.larreta.rest.configs.test.RestTestConfig;
import ar.com.larreta.rest.controllers.GeneralHelpController;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestTestConfig.class})
@WebAppConfiguration*/
public class GeneralHelpControllerTest {

	private MockMvc mockMvc;
	
	//@InjectMocks
	private GeneralHelpController generalHelpControllerTest;
	
	 
    //@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(generalHelpControllerTest).build();
    }
 
    //@Test
    public void test() throws Exception {
    	mockMvc.perform(get(GeneralHelpController.ROOT_MAP))
        .andExpect(status().isOk())
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }
	
}
