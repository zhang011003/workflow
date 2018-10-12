package com.misrobot.workflow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WorkflowApplication.class, webEnvironment=WebEnvironment.DEFINED_PORT)
public class Swagger2MarkupTest {
	
	@Autowired  
	private WebApplicationContext context;  
	
	private MockMvc mockMvc;   
	
	@Before   
	public void setupMockMvc() throws Exception {   
	    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   
	} 

    @Test
    public void convertRemoteSwaggerToAsciiDoc() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
                .andReturn();
        String swaggerJson = mvcResult.getResponse().getContentAsString();
        System.out.println(swaggerJson);
        Swagger2MarkupConverter.from(swaggerJson)
	        .withConfig(markupConfig())
	        .build().toFile(Paths.get("target/asciidoc/workflow"));
    }

	private Swagger2MarkupConfig markupConfig() {
		return new Swagger2MarkupConfigBuilder()
				.withMarkupLanguage(MarkupLanguage.ASCIIDOC)
				.withInterDocumentCrossReferences().build();
	}
}