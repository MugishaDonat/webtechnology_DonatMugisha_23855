package controller;

import model.Membership;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;
import model.Status;

public class MembershipDAO {
    public void save(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(membership);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Membership getById(int id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Membership.class, id);
        }
    }
    

    public List<Membership> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Membership", Membership.class).list();
        }
    }

    public void update(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(membership);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(membership);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // New method to get a Membership by User ID
   public Membership getByUserId(UUID userId) {
    try (Session session = HibernateUtil.getSession().openSession()) {
        String hql = "FROM Membership WHERE user.user_id = :user_id";
        return (Membership) session.createQuery(hql)
                                   .setParameter("user_id", userId)
                                   .setMaxResults(1) 
                                   .uniqueResult();
    }
    
}
  public List<Membership> getPendingRequests() {
    try (Session session = HibernateUtil.getSession().openSession()) {
        String hql = "FROM Membership WHERE membership_status = :status";
        return session.createQuery(hql, Membership.class)
                      .setParameter("status", Status.PENDING.name())
                      .list();
    }
}



}
