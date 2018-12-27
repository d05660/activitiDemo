package org.cloud.activiti.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
        "classpath:spring/spring-mvc.xml" })
public class IndexControllerTest {

    @Autowired
    private IndexController indexController;

//    @Autowired
//    private LoginController loginController;
    @Resource
    private SessionDAO sessionDAO;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Resource
    private org.apache.shiro.mgt.SecurityManager securityManager;

    // private MockMvc mockMvc1;
//    private MockMvc mockMvc2;
//    private MockHttpSession session;

    private Subject subject;
    private MockMvc mockMvc;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    private void login(String username, String password) {
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Before
    public void setUp() throws Exception {
        mockHttpServletRequest = new MockHttpServletRequest(
                webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(
                webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        login("admin", "123456");
    }

    /**
     * 获取用户登录的Session
     * 
     * @return MockHttpSession
     * @throws Exception 异常
     */
//    private MockHttpSession doLogin() throws Exception {
//        ResultActions resultActions = this.mockMvc2.perform(MockMvcRequestBuilders.post("/login")
//                .param("username", "admin").param("password", "123456"));
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
//        MvcResult result = resultActions.andReturn();
//        session = (MockHttpSession) result.getRequest().getSession();
//        return session;
//    }

    @Test
    public void test() throws Exception {
        System.out.println("-------------shiro基本权限测试-------------");
        System.out.println("get page result:" + mockMvc
                .perform(MockMvcRequestBuilders.get("/manager/details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        System.err.println("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));
        login("admin", "123456");
        System.out.println("get page result:" + mockMvc
                .perform(MockMvcRequestBuilders.get("/manager/details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        System.err.println("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));

    }
}
