package controller;

import model.Membership_type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

public class MembershipTypeDAO {
    private EntityManager entityManager;

    public MembershipTypeDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Membership_type membershipType) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (membershipType.getMembership_type_id() == null) {
                entityManager.persist(membershipType); // Insert a new record
            } else {
                entityManager.merge(membershipType); // Update an existing record
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception
        }
    }

    // Find MembershipType by ID
    public Membership_type findById(UUID membershipTypeId) {
        return entityManager.find(Membership_type.class, membershipTypeId);
    }

    // Find all MembershipTypes
    public List<Membership_type> findAll() {
        TypedQuery<Membership_type> query = entityManager.createQuery("SELECT m FROM Membership_type m", Membership_type.class);
        return query.getResultList();
    }

    // Find MembershipType by membership name
    public Membership_type findByName(String membershipName) {
        TypedQuery<Membership_type> query = entityManager.createQuery(
                "SELECT m FROM Membership_type m WHERE m.membership_name = :membershipName", 
                Membership_type.class);
        query.setParameter("membershipName", membershipName);
        return query.getSingleResult();
    }

    // Delete MembershipType
    public void delete(Membership_type membershipType) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(membershipType)) {
                entityManager.remove(membershipType); // Remove the entity if it is managed
            } else {
                entityManager.remove(entityManager.merge(membershipType)); // Merge the entity before removing
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Re-throw the exception
        }
    }

    // Initialize default membership types (Gold, Silver, Striver)
     // Initialize default membership types (Gold, Silver, Striver)
  
}
