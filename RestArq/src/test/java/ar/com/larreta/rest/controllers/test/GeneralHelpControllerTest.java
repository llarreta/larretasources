package ar.com.larreta.rest.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;

import ar.com.larreta.rest.controllers.GeneralHelpController;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, RestConfig.class})
@WebAppConfiguration*/
public class GeneralHelpControllerTest {

	 private MockMvc mockMvc;
	 
	 
	   // @Test
	    public void test() throws Exception {
	    	mockMvc.perform(get(GeneralHelpController.ROOT_MAP))
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
	    }
	
}
