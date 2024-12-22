package view;

import controller.BookDAO;
import controller.BorrowerDAO;
import controller.MembershipDAO;
import controller.UserDAO;
import model.User;
import model.Role;
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt for password verification

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Book;
import model.Borrower;
import model.Membership;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Find the user by username
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) { // Verify the password
            // User is authenticated
            HttpSession session = request.getSession();
            session.setAttribute("user", user);               
            session.setAttribute("user_id", user.getUser_id());    // Store the user ID in session

           if (user.getRole() == Role.MANAGER || user.getRole() == Role.DEAN || user.getRole() == Role.HOD) {
    BookDAO bookDAO = new BookDAO();
    List<Book> books = bookDAO.getAll();

   
    BorrowerDAO borrowerDAO = new BorrowerDAO();
    List<Borrower> borrowers = borrowerDAO.getAll();

    // Fetch membership types for each borrower
    MembershipDAO membershipDAO = new MembershipDAO();
    List<Membership> memberships = membershipDAO.getAll();

    // Set attributes to be available in the JSP
    request.setAttribute("books", books);
    request.setAttribute("borrowers", borrowers);
    request.setAttribute("memberships", memberships);

    request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
}
 else if (user.getRole() == Role.LIBRARIAN) {
                response.sendRedirect("Librarian.jsp");
            } else {
    // Retrieve borrowed books for the logged-in user
    BorrowerDAO borrowerDAO = new BorrowerDAO();
    List<Borrower> borrowings = borrowerDAO.getByUser(user.getUser_id());
    
    session.setAttribute("borrowedBooksList", borrowings);

    // Retrieve membership details for the logged-in user
    MembershipDAO membershipDAO = new MembershipDAO();
    Membership membership = membershipDAO.getByUserId(user.getUser_id());

    if (membership != null) {
        session.setAttribute("membershipType", membership.getMembership_type().getMembership_name());
        session.setAttribute("borrowLimit", membership.getMembership_type().getMax_books());
    } else {
        session.setAttribute("membershipType", "No Membership");
        session.setAttribute("borrowLimit", 0);
    }

    response.sendRedirect("StudentTeacherDashboard.jsp");
}

        } else {
            response.setContentType("text/html");
            response.getWriter().write("<html><body>");
            response.getWriter().write("<h2 style='color: red;'>ERROR!</h2>");
            response.getWriter().write("</body></html>");
        }
    }
}
