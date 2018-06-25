package by.test.application.services;

import by.test.application.dao.AppTableEntity;
import by.test.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppTableService {
    private Long sum;
    private Boolean sumOperation = false;

    public Map<String, String> add(AppTableEntity model) {
        if (checkName(model.getName())) {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(model);
            session.getTransaction().commit();
            session.close();
            return responseForm(1);
        } else {
            return responseForm(2);
        }
    }

    public Map<String, String> remove(AppTableEntity model) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete AppTableEntity where name = :name");
        query.setParameter("name", model.getName());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return responseForm(result);
    }

    public Map<String, String> sum(Map<String, String> model) {
        sum = (long) 0;
        if (model.size() == 2) {
            model.forEach((k, v) -> {
                if (!checkName(v)) {
                    Session session = HibernateSessionFactory.getSessionFactory().openSession();
                    session.beginTransaction();
                    Query query = session.createQuery("from AppTableEntity where name = :name ");
                    query.setParameter("name", v);
                    AppTableEntity result = (AppTableEntity) query.getSingleResult();
                    session.getTransaction().commit();
                    session.close();
                    sum += result.getValue();
                }
            });
            sumOperation = true;
            return responseForm(1);
        } else {
            return responseForm(3);
        }

    }


    public Map<String, String> responseForm(int code) {
        Map<String, String> response = new HashMap<>();
        if (sumOperation) {
            response.put("sum", String.valueOf(sum));
            sumOperation = false;
        }
        response.put("code", String.valueOf(code));
        switch (code) {
            case 0:
                response.put("description", "!OK");
                break;
            case 1:
                response.put("description", "OK");
                break;
            case 2:
                response.put("description", "An entry with this name exists");
                break;
            case 3:
                response.put("description", "Arguments are more than two");
                break;
            default:
                response.put("description", "Invalid code");
                break;
        }
        return response;
    }

    private Boolean checkName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from AppTableEntity where name = :name ");
        query.setParameter("name", name);
        int result = query.getResultList().size();
        session.getTransaction().commit();
        session.close();
        return result == 0;
    }

}
