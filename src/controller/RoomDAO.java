package controller;

import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoomDAO {
	 public void save(Room room) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSession().openSession()) {
	            transaction = session.beginTransaction();
	            
	            // If room_id is not set, generate a new UUID
	            if (room.getRoom_id() == null) {
	                room.setRoom_id(UUID.randomUUID());
	            }
	            
	            session.save(room);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	            // Handle the exception appropriately
	        }
	    }

    public Optional<Room> getById(int id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return Optional.ofNullable(session.get(Room.class, id));
        }
    }
    public Optional<Room> getByRoomCode(String roomCode) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Room where room_code = :roomCode", Room.class)
                    .setParameter("roomCode", roomCode)
                    .uniqueResultOptional();
        }
    }



    public List<Room> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        }
    }

    public void update(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            // Use a logging framework here instead of printStackTrace
            System.err.println("Error updating room: " + e.getMessage());
        }
    }

    public void delete(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            // Use a logging framework here instead of printStackTrace
            System.err.println("Error deleting room: " + e.getMessage());
        }
    }

    public Room getById(String roomCode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


/**
 * 
 */
/**
 * @author Donat
 *
 */