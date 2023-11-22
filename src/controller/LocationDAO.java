package controller;

import model.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;
import model.Location_type;

public class LocationDAO {

    public void save(Location location) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            

        }
    }

    public Location getById(UUID id) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Location.class, id);
        }
    }
  public UUID getLocationIdByName(String name) {
    try (Session session = HibernateUtil.getSession().openSession()) {
        return session.createQuery("SELECT l.locationId FROM Location l WHERE l.locationName = :name", UUID.class)
                      .setParameter("name", name)
                      .setMaxResults(1)  
                      .uniqueResult();
    }
}




    public List<Location> getAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Location", Location.class).list();
        }
    }

    public void update(Location location) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Location location) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    // Method to retrieve or create a location
    public Location getOrCreateLocation(Session session, String name, Location_type locationType, Location parentLocation) {
        Location location = session.createQuery("FROM Location WHERE locationName = :name AND locationType = :locationType AND parentLocation = :parent", Location.class)
                .setParameter("name", name)
                .setParameter("locationType", locationType)
                .setParameter("parent", parentLocation)
                .uniqueResult();
        
        if (location == null) {
            location = new Location();
            location.setLocationName(name);
            location.setLocationType(locationType);
            location.setParentLocation(parentLocation);
            session.save(location);
        }
        return location;
    }
}
