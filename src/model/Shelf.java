package model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Shelf")
public class Shelf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO if you want to generate UUID automatically
	@Column(name = "shelf_id", columnDefinition = "UUID")
	private UUID shelf_id;

	@Column(name = "book_category")
	private String book_category;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@OneToMany(mappedBy = "shelf")
	private List<Book> book;
	
	public Shelf() {
		
	}
	
	public Shelf(UUID shelf_id, String book_category, Room room) {
	    this.shelf_id = shelf_id;
	    this.book_category = book_category;
	    this.room = room;
	}

	public UUID getShelf_id() {
		return shelf_id;
	}

	public void setShelf_id(UUID shelf_id) {
		this.shelf_id = shelf_id;
	}

	public String getBook_category() {
		return book_category;
	}

	public void setBook_category(String book_category) {
		this.book_category = book_category;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

}
