package controller;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class UserDAO {

    // Save a new user to the database
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get a user by username
    public User getByUsername(String username) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("FROM User WHERE user_name = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public User getUserByPhoneNumber(String phoneNumber) {
    try (Session session = HibernateUtil.getSession().openSession()) {
        return session.createQuery("FROM User WHERE phone_number = :phoneNumber", User.class)
                      .setParameter("phoneNumber", phoneNumber)
                      .uniqueResult();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    // Get all users
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    // Update user details
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a user
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Check if username exists
    public boolean isUsernameExists(String username) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Long count = session.createQuery("SELECT COUNT(*) FROM User WHERE user_name = :username", Long.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if phone number exists
    public boolean isPhoneNumberExists(String phoneNumber) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Long count = session.createQuery("SELECT COUNT(*) FROM User WHERE phone_number = :phoneNumber", Long.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if email exists
    public boolean isEmailExists(String email) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Long count = session.createQuery("SELECT COUNT(*) FROM User WHERE email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
