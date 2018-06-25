package by.test.application;

import by.test.application.dao.AppTableEntity;
import by.test.application.services.AppTableService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private AppTableService appTableService;

    @Autowired
    public void setService(AppTableService appTableService) {
        this.appTableService = appTableService;
    }

    @Before
    public void before() {
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
    public void responseFormTest() {
        Map<String, String> expected = new HashMap<>();
        expected.put("code", "1");
        expected.put("description", "OK");
        Map<String, String> actual = appTableService.responseForm(1);
        assertEquals(expected, actual);
    }

    @Test
    public void removeTest() {
        AppTableEntity appTableEntity1 = new AppTableEntity("!test1", (long) 15);
        Map<String, String> actual = appTableService.remove(appTableEntity1);
        Map<String, String> expected = new HashMap<>();
        expected.put("code", "1");
        expected.put("description", "OK");
        assertEquals(expected, actual);
        AppTableEntity appTableEntity2 = new AppTableEntity("!test2", (long) 666);
        actual = appTableService.remove(appTableEntity2);
        assertEquals(expected, actual);
    }

    @Test
    public void addTest() {
        AppTableEntity appTableEntity1 = new AppTableEntity("!test1", (long) 15);
        Map<String, String> actual = appTableService.add(appTableEntity1);
        Map<String, String> expected = new HashMap<>();
        expected.put("code", "2");
        expected.put("description", "An entry with this name exists");
        assertEquals(expected, actual);
        AppTableEntity appTableEntity2 = new AppTableEntity("!test2", (long) 666);
        actual = appTableService.add(appTableEntity2);
        assertEquals(expected, actual);
    }

    @Test
    public void addAndRemoveTest() {
        Map<String, String> expected = new HashMap<>();
        expected.put("code", "1");
        expected.put("description", "OK");
        AppTableEntity appTableEntity = new AppTableEntity("!test3", (long) 19);
        Map<String, String> actual = appTableService.add(appTableEntity);
        assertEquals(expected, actual);
        actual = appTableService.remove(appTableEntity);
        assertEquals(expected, actual);
    }

    @Test
    public void sumTest() {
        Map<String, String> expected = new HashMap<>();
        expected.put("sum", "681");
        expected.put("code", "1");
        expected.put("description", "OK");
        Map<String, String> model = new HashMap<>();
        model.put("first","!test1");
        model.put("second","!test2");
        Map<String, String> actual = appTableService.sum(model);
        assertEquals(expected, actual);
    }
}
