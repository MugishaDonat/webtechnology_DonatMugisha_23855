package view;

import model.Book;
import model.Book_status;
import controller.BookDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BookManagementServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addBook(request, response);  // Call the addBook method with response to send appropriate message
                    break;
                case "delete":
                    deleteBook(request);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("manageBooks.jsp");
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        String isbnCode = request.getParameter("isbnCode");
        
        // Check if the book with the given ISBNCode already exists
        Book existingBook = bookDAO.getByISBN(isbnCode);
        if (existingBook != null) {
            // Book already exists, send a message and redirect
            response.getWriter().write("<html><body><p>Book with ISBN " + isbnCode + " already exists. Please use a different ISBN.</p></body></html>");
            return;  
        }
        
       
        Book book = new Book();
        setBookDetails(request, book);
        bookDAO.save(book);
    }

    private void deleteBook(HttpServletRequest request) {
        String isbnCode = request.getParameter("isbnCode");
        bookDAO.delete(isbnCode);
    }

    private void setBookDetails(HttpServletRequest request, Book book) throws ParseException {
    // Set book details from the request parameters
    book.setISBNCode(request.getParameter("isbnCode"));
    book.setTitle(request.getParameter("title"));
    book.setPublisher_name(request.getParameter("publisherName"));

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    Date publicationYear = dateFormat.parse(request.getParameter("publicationYear"));
    book.setPublication_year(publicationYear);

  
    int edition = Integer.parseInt(request.getParameter("edition"));
    book.setEdition(edition);

    book.setInitial_stock(10); 
    book.setAvailable_stock(10); 
    book.setBorrowed_number(0); 
}

}
