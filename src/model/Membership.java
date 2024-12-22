package model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Membership")
public class Membership {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "membership_id")
	UUID membership_id;

	@ManyToOne
	@JoinColumn(name="membership_type_id")
	Membership_type membership_type;

	@Column(name = "membership_status")
	Status membership_status;

	@Column(name = "registration_date")
	Date registration_date;

	@Column(name = "expiring_date")
	Date expiring_date;

	@Column(name = "fine")
	int fine;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	
	
	public Membership() {
		super();
	}



	public Membership(Membership_type membership_type, Date registration_date, Date expiring_date, int fine,
			User user) {
		super();
		this.membership_type = membership_type;
		this.registration_date = registration_date;
		this.expiring_date = expiring_date;
		this.fine = fine;
		this.user = user;
	}



	public UUID getMembership_id() {
		return membership_id;
	}



	public void setMembership_id(UUID membership_id) {
		this.membership_id = membership_id;
	}



	public Membership_type getMembership_type() {
		return membership_type;
	}



	public void setMembership_type(Membership_type membership_type) {
		this.membership_type = membership_type;
	}



	public Status getMembership_status() {
		return membership_status;
	}



	public void setMembership_status(Status membership_status) {
		this.membership_status = membership_status;
	}



	public Date getRegistration_date() {
		return registration_date;
	}



	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}



	public Date getExpiring_date() {
		return expiring_date;
	}



	public void setExpiring_date(Date expiring_date) {
		this.expiring_date = expiring_date;
	}



	public int getFine() {
		return fine;
	}



	public void setFine(int fine) {
		this.fine = fine;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
	
}
