package view;

import controller.BookDAO;
import controller.BorrowerDAO;
import controller.MembershipDAO;
import model.Book;
import model.Borrower;
import model.Membership;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class ReturnBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String isbnCode = request.getParameter("isbn"); 

        if (isbnCode == null || isbnCode.isEmpty()) {
            response.setContentType("text/html");
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h2 style='color: red;'>ISBN is required!</h2>");
            response.getWriter().write("</body></html>");
            return;
        }

        BorrowerDAO borrowerDAO = new BorrowerDAO();
        Borrower borrower = borrowerDAO.getByBookISBN(isbnCode); 
        
        if (borrower != null) {
            // Set return date to current date
            Date returnDate = new Date();
            borrower.setReturnDate(returnDate);

            // Calculate the number of late days
            long diffInMillies = returnDate.getTime() - borrower.getPickupDate().getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            // If the book is returned after 20 days
            int lateFee = 0;
            if (diffInDays > 20) {
                lateFee = (int)((diffInDays - 20) * 300); 
            }

            // Update the Borrower table with return date and late fee
            borrowerDAO.update(borrower);

            // Get the user who borrowed the book
            User user = borrower.getUser();

            // Get the user's membership and update the fine
            MembershipDAO membershipDAO = new MembershipDAO();
            Membership membership = membershipDAO.getByUserId(user.getUser_id());
            if (membership != null) {
                int updatedFine = membership.getFine() + lateFee;
                membership.setFine(updatedFine);
                membershipDAO.update(membership);
            }

            // Update book availability
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getByISBN(isbnCode);
            if (book != null) {
                book.setAvailable_stock(book.getAvailable_stock() + 1);
                book.setBorrowed_number(book.getBorrowed_number() - 1);
                bookDAO.update(book); // Update book's stock and borrowed number
            }

            // Set the late fee as a request attribute and forward to the JSP page
            request.setAttribute("isLate", true);
            request.setAttribute("lateFee", lateFee);
            request.getRequestDispatcher("ReturnBook.jsp").forward(request, response);

        } else {
            response.setContentType("text/html");
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h2 style='color: red;'>This book has not been borrowed or does not exist!</h2>");
            response.getWriter().write("</body></html>");
        }
    }
}
