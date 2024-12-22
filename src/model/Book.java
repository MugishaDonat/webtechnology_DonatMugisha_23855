package model;

import java.util.*;
import javax.persistence.*;



@Entity
@Table(name = "Book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	UUID book_id;

	@Column(name = "ISBNCode")
	String ISBNCode;

	@Column(name = "title")
	String title;

	@Column(name = "publisher_name")
	String publisher_name;

	@Column(name = "publication_year")
	Date publication_year;

	@Column(name = "edition")
	int edition;

	@Column(name = "available_stock")
	int available_stock;

	@Column(name = "borrowed_number")
	int borrowed_number;

	@Column(name = "initial_stock")
	int initial_stock;

	@Column(name = "book_status")
	Book_status book_status;

	@ManyToOne
	@JoinColumn(name = "shelf_id")
	Shelf shelf;

	@OneToMany(mappedBy = "book")
	List<Borrower> borrower;

	public UUID getBook_id() {
		return book_id;
	}

	public void setBook_id(UUID book_id) {
		this.book_id = book_id;
	}

	public String getISBNCode() {
		return ISBNCode;
	}

	public void setISBNCode(String iSBNCode) {
		ISBNCode = iSBNCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public Date getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(Date publication_year) {
		this.publication_year = publication_year;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public int getAvailable_stock() {
		return available_stock;
	}

	public void setAvailable_stock(int available_stock) {
		this.available_stock = available_stock;
	}

	public int getBorrowed_number() {
		return borrowed_number;
	}

	public void setBorrowed_number(int borrowed_number) {
		this.borrowed_number = borrowed_number;
	}

	public int getInitial_stock() {
		return initial_stock;
	}

	public void setInitial_stock(int initial_stock) {
		this.initial_stock = initial_stock;
	}

	public Book_status getBook_status() {
		return book_status;
	}

	public void setBook_status(Book_status book_status) {
		this.book_status = book_status;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public List<Borrower> getBorrower() {
		return borrower;
	}

	public void setBorrower(List<Borrower> borrower) {
		this.borrower = borrower;
	}
	
	
}
