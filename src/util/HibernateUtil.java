package util;

import java.util.Properties;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import model.Book;
import model.Borrower;
import model.Location;
import model.Membership;
import model.Membership_type;
import model.Room;
import model.Shelf;
import model.User;



public class HibernateUtil {

	
	private static SessionFactory sessionFactory = null;
	
	public static SessionFactory getSession() {
		
		if(sessionFactory == null) {
			Configuration conf = new Configuration();
			Properties settings = new Properties();
			
			settings.setProperty(Environment.DRIVER, "org.postgresql.Driver");
			settings.setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/Auca_library_db");
			settings.setProperty(Environment.USER, "postgres");
			settings.setProperty(Environment.PASS, "     69");
			settings.setProperty(Environment.HBM2DDL_AUTO, "update");
			settings.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
			settings.setProperty(Environment.SHOW_SQL, "true");
			
			conf.setProperties(settings);
			
			conf.addAnnotatedClass(User.class);
			conf.addAnnotatedClass(Book.class);
			conf.addAnnotatedClass(Membership.class);
			conf.addAnnotatedClass(Borrower.class);
			conf.addAnnotatedClass(Membership_type.class);
			conf.addAnnotatedClass(Room.class);
			conf.addAnnotatedClass(Location.class);
			conf.addAnnotatedClass(Shelf.class);
			
			sessionFactory = conf.buildSessionFactory();
			
			return sessionFactory;
			
		}else {
			return sessionFactory;
		}
	}
}