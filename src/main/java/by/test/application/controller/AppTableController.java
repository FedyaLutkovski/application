package by.test.application.controller;

import by.test.application.dao.AppTableEntity;
import by.test.application.services.AppTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AppTableController {
    private AppTableService appTableService;

    @Autowired
    public void setService(AppTableService appTableService) {
        this.appTableService = appTableService;
    }

    @PostMapping(value = "/add")
    public Map<String, String> add(@RequestBody AppTableEntity model) {
        return appTableService.add(model);
    }

    @PostMapping(value = "/remove")
    public Map<String, String> remove(@RequestBody AppTableEntity model) {
        return appTableService.remove(model);
    }

    @PostMapping(value = "/sum")
    public Map<String, String> remove(@RequestBody Map<String, String> model) {
        return appTableService.sum(model);
    }
}
