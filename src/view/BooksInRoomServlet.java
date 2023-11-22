package view;

import model.Book;
import model.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class BooksInRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("roomCode");

       
        try (Session session = HibernateUtil.getSession().openSession()) {
            Query<Room> roomQuery = session.createQuery("from Room where room_code = :roomCode", Room.class);
            roomQuery.setParameter("roomCode", roomCode);
            Room room = roomQuery.uniqueResult();

            if (room != null) {
                // Fetch books for this room
                Query<Book> booksQuery = session.createQuery("select b from Book b join b.shelf s where s.room = :room", Book.class);
                booksQuery.setParameter("room", room);
                List<Book> books = booksQuery.list();
                
                request.setAttribute("roomCode", roomCode);
                request.setAttribute("books", books);
            } else {
               
                request.setAttribute("error", "Room not found");
            }
        }

        // Forward to the JSP page
        request.getRequestDispatcher("/assignroom.jsp").forward(request, response);
    }
}



/**
 * 
 */
/**
 * @author Donat
 *
 */