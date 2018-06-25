package by.test.application.services;

import by.test.application.dao.AppTableEntity;
import by.test.application.dao.OperationType;
import by.test.application.dao.Response;
import by.test.application.dao.ResponseSum;
import by.test.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.*;

@Service
public class AppTableService {

    private int makeQuery(AppTableEntity model, OperationType operationType) {
        try {
            int result = 1;
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            if (operationType.equals(OperationType.add)) {
                session.save(model);
                result = 0;
            }
            if (operationType.equals(OperationType.remove)) {
                Query query = session.createQuery("delete AppTableEntity where name = :name");
                query.setParameter("name", model.getName());
                if (query.executeUpdate() == 1) {
                    result = 0;
                } else result = 1;

            }
            if (operationType.equals(OperationType.checkName)) {
                Query query = session.createQuery("from AppTableEntity where name = :name ");
                query.setParameter("name", model.getName());
                result = query.getResultList().size();
            }
            if (operationType.equals(OperationType.sum)) {
                Query query = session.createQuery("from AppTableEntity where name = :name ");
                query.setParameter("name", model.getName());
                AppTableEntity appTableEntity = (AppTableEntity) query.getSingleResult();
                result = Math.toIntExact(appTableEntity.getValue());
            }
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (Exception e) {
            return 5;
        }
    }

    public Response add(AppTableEntity model) {
        if (checkName(model)) {
            int result = makeQuery(model, OperationType.add);
            return responseForm(result);
        } else {
            return responseForm(2);
        }
    }


    public Response remove(AppTableEntity model) {
        int result = makeQuery(model, OperationType.remove);
        return responseForm(result);
    }

    public Response sum(Map<String, String> model) {
        List<Integer> list = new ArrayList<>();
        if (model.size() == 2) {
            for (String key : model.keySet()) {
                AppTableEntity appTableEntity = new AppTableEntity(model.get(key), null);
                if (!checkName(appTableEntity)) {
                    list.add(makeQuery(appTableEntity, OperationType.sum));
                } else {
                    list.clear();
                    break;
                }
            }
            if (list.size() > 0) {
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                return responseSumForm(0, sum);
            } else {
                return responseSumForm(4, 0);
            }
        } else {
            return responseSumForm(3, 0);
        }

    }

    private Boolean checkName(AppTableEntity model) {
        int result = makeQuery(model, OperationType.checkName);
        return result == 0;
    }

    private Response responseForm(int code) {
        Response response = new Response();
        response.setCode(code);
        response.setDescription(getDescription(code));
        return response;
    }

    private ResponseSum responseSumForm (int code, int sum){
        ResponseSum responseSum = new ResponseSum();
        responseSum.setCode(code);
        responseSum.setSum(sum);
        responseSum.setDescription(getDescription(code));
        return responseSum;
    }

    private String getDescription (int code){
        String description;
        switch (code) {
            case 0:
                description = "OK";
                break;
            case 1:
                description = "Operation not performed";
                break;
            case 2:
                description = "An entry with this name exists";
                break;
            case 3:
                description = "Arguments are more than two";
                break;
            case 4:
                description = "One or more items are missing in the database";
                break;
            case 5:
                description = "Session lost";
                break;
            default:
                description = "Invalid code";
                break;
        }
        return description;
    }

}
