package controller;
import java.util.ArrayList;
import model.Borrower;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;
import org.hibernate.query.Query;

public class BorrowerDAO {
    public void save(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(borrower);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            
        }
    }

    public Borrower getById(int id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Borrower.class, id);
        }
    }

    public List<Borrower> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Borrower", Borrower.class).list();
        }
    }

    public void update(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(borrower);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public Borrower getByBookISBN(String isbnCode) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            
            return session.createQuery("FROM Borrower b WHERE b.book.ISBNCode = :isbnCode", Borrower.class)
                           .setParameter("isbnCode", isbnCode)
                           .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
  public List<Borrower> getByUser(UUID userId) {
    Session session = HibernateUtil.getSession().openSession();
    List<Borrower> borrowings = new ArrayList<>(); // Initialize as empty list

    try {
        String hql = "FROM Borrower WHERE reader_id = :userId AND returnDate IS NULL";
        Query<Borrower> query = session.createQuery(hql, Borrower.class);
        query.setParameter("userId", userId);
        borrowings = query.list(); 
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        session.close();
    }

    return borrowings; // Return the empty list if no borrowings are found
}

    public void delete(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(borrower);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
}
