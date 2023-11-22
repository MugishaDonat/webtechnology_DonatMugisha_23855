package view;


import controller.BookDAO;
import controller.RoomDAO;
import controller.ShelfDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;
import model.Room;
import model.Shelf;

public class BookShelfAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sbnCode = request.getParameter("sbn");
        String bookCategory = request.getParameter("bookCategory");

        // Fetch Book by ISBN
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getByISBN(sbnCode);
        
        if (book != null) {
            // Fetch Shelf by book category
            ShelfDAO shelfDAO = new ShelfDAO();
            Shelf shelf = shelfDAO.getAll().stream()
                .filter(s -> s.getBook_category().equals(bookCategory))
                .findFirst()
                .orElse(null);

            // If Shelf is not found, create a new Shelf and save it
            if (shelf == null) {
                shelf = new Shelf();
                shelf.setBook_category(bookCategory); // Set the book category
                
                // Save new Shelf (Room is not needed at this point)
                shelfDAO.save(shelf);
            }

            // Assign the book to the found or newly created shelf
            book.setShelf(shelf);

            // Save the updated book
            bookDAO.update(book);

           
            response.sendRedirect("assignshelf.jsp"); 
        } else {
           
             response.setContentType("text/html");  
                     response.getWriter().write("<html><body>");
                     response.getWriter().write("<h2 style='color: red;'>BOOK SBN NOT FOUND!</h2>");
                     response.getWriter().write("</body></html>");
        }
    }
}



/**
 * 
 */
/**
 * @author Donat
 *
 */