package model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "borrower")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrower_id")
    private UUID borrower_id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private User user;

    @Column(name = "pickup_date")
    private Date pickupDate;

    @Column(name = "return_date")
    private Date returnDate;

    // Getter and Setter for book
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for pickupDate
    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    // Getter and Setter for returnDate
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    // Other fields and methods
}
