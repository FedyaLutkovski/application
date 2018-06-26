package by.test.application.services;

import by.test.application.dao.AppTableEntity;
import by.test.application.utils.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private Session beginTransaction() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            return session;
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
    }

    private void commitTransaction(Session session) {
        if (session == null) {
            return;
        }
        try {
            session.getTransaction().commit();
        } catch (HibernateException he) {
            throw he;
        } finally {
            session.close();
        }
    }


    public int addModel(AppTableEntity model) {
        Session session = beginTransaction();
        session.save(model);
        commitTransaction(session);
        return 0;
    }

    public int removeModel(AppTableEntity model) {
        Session session = beginTransaction();
        Query query = session.createQuery("delete AppTableEntity where name = :name");
        query.setParameter("name", model.getName());
        int result = query.executeUpdate() == 1 ? 0 : 1;
        commitTransaction(session);
        return result;
    }

    public Boolean checkNameModel(AppTableEntity model) {
        Session session = beginTransaction();
        Query query = session.createQuery("from AppTableEntity where name = :name ");
        query.setParameter("name", model.getName());
        int result = query.getResultList().size();
        commitTransaction(session);
        return result == 0;
    }

    public int sumModel(AppTableEntity model) {
        Session session = beginTransaction();
        Query query = session.createQuery("from AppTableEntity where name = :name ");
        query.setParameter("name", model.getName());
        AppTableEntity appTableEntity = (AppTableEntity) query.getSingleResult();
        int result = Math.toIntExact(appTableEntity.getValue());
        commitTransaction(session);
        return result;
    }
}
