package by.test.application;

import by.test.application.dao.AppTableEntity;
import by.test.application.services.AppTableService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ApplicationTests {
    private MockMvc mockMvc;
    private AppTableService appTableService;

    @Autowired
    public void setService(AppTableService appTableService) {
        this.appTableService = appTableService;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        AppTableEntity appTableEntity1 = new AppTableEntity("!test1", (long) 15);
        AppTableEntity appTableEntity2 = new AppTableEntity("!test2", (long) 666);
        appTableService.add(appTableEntity1);
        appTableService.add(appTableEntity2);
    }

    @After
    public void after() {
        AppTableEntity appTableEntity1 = new AppTableEntity("!test1", (long) 15);
        AppTableEntity appTableEntity2 = new AppTableEntity("!test2", (long) 666);
        appTableService.remove(appTableEntity1);
        appTableService.remove(appTableEntity2);
    }

    @Test
    public void removeTest() throws Exception {
        mockMvc.perform(post("/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test1\",\"value\":15}"))
                .andExpect(content().json("{\"code\":0,\"description\":\"OK\"}"));

        mockMvc.perform(post("/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test2\",\"value\":666}"))
                .andExpect(content().json("{\"code\":0,\"description\":\"OK\"}"));
    }

    @Test
    public void addTest() throws Exception {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test1\",\"value\":15}"))
                .andExpect(content().json("{\"code\":2,\"description\":\"An entry with this name exists\"}"));

        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test2\",\"value\":666}"))
                .andExpect(content().json("{\"code\":2,\"description\":\"An entry with this name exists\"}"));
    }

    @Test
    public void addAndRemoveTest() throws Exception {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test3\",\"value\":19}"))
                .andExpect(content().json("{\"code\":0,\"description\":\"OK\"}"));

        mockMvc.perform(post("/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test3\",\"value\":19}"))
                .andExpect(content().json("{\"code\":0,\"description\":\"OK\"}"));
    }

    @Test
    public void sumTest() throws Exception {
        mockMvc.perform(post("/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"first\":\"!test1\",\"second\":\"!test2\"}"))
                .andExpect(content().json("{\"sum\":681,\"code\":0,\"description\":\"OK\"}"));
    }

    @Test
    public void sumTestArgumentsAreMoreThanTwo() throws Exception {
        mockMvc.perform(post("/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"first\":\"!test1\",\"second\":\"!test2\",\"third\":\"!test3\"}"))
                .andExpect(content().json("{\"sum\":0,\"code\":3,\"description\":\"Arguments are more than two\"}"));
    }

    @Test
    public void sumTestOneOrMoreItemsAreMissingInTheDatabase() throws Exception {
        mockMvc.perform(post("/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"!test1\",\"value\":15}"))
                .andExpect(content().json("{\"code\":0,\"description\":\"OK\"}"));
        mockMvc.perform(post("/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"first\":\"!test1\",\"second\":\"!test2\"}"))
                .andExpect(content().json("{\"sum\":0,\"code\":4,\"description\":\"One or more items are missing in the database\"}"));
    }

    @Test
    public void sessionLostTest() throws Exception {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"first\":\"!test1\",\"second\":\"!test2\"}"))
                .andExpect(content().json("{\"code\":5,\"description\":\"Session lost\"}"));
    }

}
