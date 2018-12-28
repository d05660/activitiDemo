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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({  
    @ContextConfiguration(name = "parent", locations = "classpath:spring/applicationContext.xml"),  
    @ContextConfiguration(name = "child", locations = "classpath:spring/spring-mvc.xml")  
}) 
public class IndexControllerTest {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private IndexController indexController;

    @Resource
    private SessionDAO sessionDAO;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Resource
    private org.apache.shiro.mgt.SecurityManager securityManager;

    private Subject subject;
    private MockMvc mockMvc;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    private void login(String username, String password) {
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse).buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        subject.login(token);
        ThreadContext.bind(subject);
    }

    @Before
    public void before() throws Exception {
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        login("admin", "123456");
    }

    @Test
    public void test() throws Exception {
        LOGGER.error("-------------shiro基本权限测试-------------");
        LOGGER.error("get page result:" + mockMvc
                .perform(MockMvcRequestBuilders.get("/manager/details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        LOGGER.error("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));
        LOGGER.error("-------------测试同一用户异地登录将另一session踢出,该过程在CredentialsMatcher进行处理-------------");
        login("admin", "123456");
        LOGGER.error("get page result:" + mockMvc
                .perform(MockMvcRequestBuilders.get("/manager/details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        LOGGER.error("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));
        LOGGER.error("-------------测试登出后权限-------------");
        subject.logout();
        LOGGER.error("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));
        LOGGER.error("logout page result:" + mockMvc
                .perform(MockMvcRequestBuilders.get("/manager/details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
       

    }
}
