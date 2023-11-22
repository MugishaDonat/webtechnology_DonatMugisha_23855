package controller;

import model.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class ShelfDAO {
    public void save(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(shelf);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Shelf getById(UUID shelf_id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Shelf.class, shelf_id);
        }
    }

    public List<Shelf> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Shelf", Shelf.class).list();
        }
    }

    public void update(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(shelf);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(shelf);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}



/**
 * 
 */
/**
 * @author Donat
 *
 */