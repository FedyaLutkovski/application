package by.test.application.services;

import by.test.application.dao.AppTableEntity;
import by.test.application.dao.Response;
import by.test.application.dao.ResponseEnum;
import by.test.application.dao.ResponseSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppTableService {
    private TransactionService transactionService;

    @Autowired
    public void setService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Response add(AppTableEntity model) {
        if (transactionService.checkNameModel(model)) {
            int result = transactionService.addModel(model);
            if (result == 0) {
                return new Response(ResponseEnum.CODE0.getCode(), ResponseEnum.CODE0.getDescription());
            } else {
                return new Response(ResponseEnum.CODE1.getCode(), ResponseEnum.CODE1.getDescription());
            }
        } else {
            return new Response(ResponseEnum.CODE2.getCode(), ResponseEnum.CODE2.getDescription());
        }
    }


    public Response remove(AppTableEntity model) {
        int result = transactionService.removeModel(model);
        if (result == 0) {
            return new Response(ResponseEnum.CODE0.getCode(), ResponseEnum.CODE0.getDescription());
        } else {
            return new Response(ResponseEnum.CODE1.getCode(), ResponseEnum.CODE1.getDescription());
        }
    }

    public ResponseSum sum(Map<String, String> model) {
        List<Integer> list = new ArrayList<>();
        if (model.size() == 2) {
            for (String key : model.keySet()) {
                AppTableEntity appTableEntity = new AppTableEntity(model.get(key), null);
                if (!transactionService.checkNameModel(appTableEntity)) {
                    list.add(transactionService.sumModel(appTableEntity));
                } else {
                    list.clear();
                    break;
                }
            }
            if (list.size() > 0) {
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                return new ResponseSum(ResponseEnum.CODE0.getCode(), ResponseEnum.CODE0.getDescription(), sum);
            } else {
                return new ResponseSum(ResponseEnum.CODE4.getCode(), ResponseEnum.CODE4.getDescription(), 0);
            }
        } else {
            return new ResponseSum(ResponseEnum.CODE3.getCode(), ResponseEnum.CODE3.getDescription(), 0);
        }

    }

}
