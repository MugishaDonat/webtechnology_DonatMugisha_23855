package view;

import controller.BookDAO;
import controller.BorrowerDAO;
import controller.UserDAO;
import controller.MembershipDAO;
import model.Book;
import model.Borrower;
import model.User;
import model.Membership;
import model.Membership_type;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BorrowingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String isbnCode = request.getParameter("isbn");
        String username = request.getParameter("username");
        String pickupDateStr = request.getParameter("pickupDate");

        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date pickupDate = null;
        try {
            pickupDate = dateFormat.parse(pickupDateStr);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error parsing dates");
            return;
        }

        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getByISBN(isbnCode);

        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(username);

        if (user != null) {
            
            MembershipDAO membershipDAO = new MembershipDAO();
            Membership membership = membershipDAO.getByUserId(user.getUser_id());

            if (membership == null) {
                response.setContentType("text/html");
                response.getWriter().write("<html><body>");
                response.getWriter().write("<h2 style='color: red;'>Please buy a membership first!</h2>");
                response.getWriter().write("</body></html>");
                return;
            }

          
            Membership_type membershipType = membership.getMembership_type();
            int maxBooksAllowed = membershipType.getMax_books();

            // Get borrowings that are not returned
            BorrowerDAO borrowerDAO = new BorrowerDAO();
            List<Borrower> borrowings = borrowerDAO.getByUser(user.getUser_id());  

            if (borrowings.size() >= maxBooksAllowed) {
                response.setContentType("text/html");
                response.getWriter().write("<html><body>");
                response.getWriter().write("<h2 style='color: red;'>You cannot borrow more than " + maxBooksAllowed + " books!</h2>");
                response.getWriter().write("</body></html>");
                return;
            }

            
            if (book != null) {
                Borrower existingBorrower = borrowerDAO.getByBookISBN(isbnCode); 
                
                if (existingBorrower != null) {
                    if (existingBorrower.getReturnDate() == null) {
                        response.setContentType("text/html");
                        response.getWriter().write("<html><body>");
                        response.getWriter().write("<h2 style='color: red;'>This book is already borrowed and has not been returned!</h2>");
                        response.getWriter().write("</body></html>");
                        return;
                    }
                }

                // Create a new borrower entry
                Borrower borrower = new Borrower();
                borrower.setBook(book);
                borrower.setUser(user);
                borrower.setPickupDate(pickupDate);

                borrowerDAO.save(borrower);

                // Update book availability
                book.setBorrowed_number(book.getBorrowed_number() + 1);
                book.setAvailable_stock(book.getAvailable_stock() - 1);
                bookDAO.update(book);

                // Redirect to the borrow book page
                response.sendRedirect("borrowbook.jsp");  
            } else {
                response.setContentType("text/html"); 
                response.getWriter().write("<html><body>");
                response.getWriter().write("<h2 style='color: red;'>BOOK NOT FOUND!</h2>");
                response.getWriter().write("</body></html>");
            }
        } else {
            response.setContentType("text/html");  
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h2 style='color: red;'>USER NOT FOUND!</h2>");
            response.getWriter().write("</body></html>");
        }
    }
}
