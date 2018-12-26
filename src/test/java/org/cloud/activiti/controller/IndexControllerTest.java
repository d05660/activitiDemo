package org.cloud.activiti.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
        "classpath:spring/spring-mvc.xml" })
public class IndexControllerTest {

    @Autowired
    private IndexController indexController;

    @Autowired
    private LoginController loginController;

    private MockMvc mockMvc1;
    private MockMvc mockMvc2;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {       
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver(); //在test中重新配置视图解析器
        resolver.setPrefix("/WEB_INF/views");
        resolver.setSuffix(".jsp");
        mockMvc1 = MockMvcBuilders.standaloneSetup(indexController).setViewResolvers(resolver).build();
        mockMvc2 = MockMvcBuilders.standaloneSetup(loginController).build();
        this.session = doLogin();
    }

    /**
     * 获取用户登录的Session
     * 
     * @return MockHttpSession
     * @throws Exception 异常
     */
    private MockHttpSession doLogin() throws Exception {
        ResultActions resultActions = this.mockMvc2.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "admin").param("password", "123456"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult result = resultActions.andReturn();
        session = (MockHttpSession) result.getRequest().getSession();
        return session;
    }

    @Test
    public void test() throws Exception {
        ResultActions resultActions = this.mockMvc1.perform(MockMvcRequestBuilders.get("/index"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
