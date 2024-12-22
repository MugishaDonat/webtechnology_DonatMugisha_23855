package model;


import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Membership_type")
public class Membership_type {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_type_id")
    UUID membership_type_id;

    @Column(name = "membership_name")
    String membership_name;

    @Column(name = "max_books")
    int max_books;

    @Column(name = "price")
    int price;

    @OneToMany(mappedBy = "membership_type", cascade = CascadeType.ALL)
    List<Membership> memberships;

    // Default constructor
    public Membership_type() {
        // No argument constructor for Hibernate
    }

    // Existing constructor
    public Membership_type(String membership_name, int max_books, int price) {
        this.membership_name = membership_name;
        this.max_books = max_books;
        this.price = price;
    }

    // Getters and setters
    public UUID getMembership_type_id() {
        return membership_type_id;
    }

    public void setMembership_type_id(UUID membership_type_id) {
        this.membership_type_id = membership_type_id;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public void setMembership_name(String membership_name) {
        this.membership_name = membership_name;
    }

    public int getMax_books() {
        return max_books;
    }

    public void setMax_books(int max_books) {
        this.max_books = max_books;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}
