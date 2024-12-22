package controller;

import model.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class BookDAO {

    public void save(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Book getByISBN(String isbnCode) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM Book WHERE ISBNCode = :isbnCode", Book.class)
                           .setParameter("isbnCode", isbnCode)
                           .uniqueResult();
        }
    }

    public List<Book> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Book", Book.class).list();
        }
    }

    public void update(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(String isbnCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            Book bookToDelete = getByISBN(isbnCode);  // Fetch the book first
            if (bookToDelete != null) {
                session.delete(bookToDelete);
                transaction.commit();
            } else {
                // Handle book not found case
                System.out.println("Book with ISBN " + isbnCode + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
