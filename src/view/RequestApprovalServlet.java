package view;

import controller.MembershipDAO;
import controller.UserDAO;
import model.User;
import model.Membership;
import model.Status;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RequestApprovalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String username = request.getParameter("username");
        String action = request.getParameter("action"); 

        
        UserDAO userDAO = new UserDAO();
        MembershipDAO membershipDAO = new MembershipDAO();

        
        User user = userDAO.getByUsername(username);
        if (user != null) {
            
            Membership membership = membershipDAO.getByUserId(user.getUser_id());

            if (membership != null && membership.getMembership_status() == Status.PENDING) {
                
                if ("approve".equals(action)) {
                    membership.setMembership_status(Status.APPROVED);
                    membershipDAO.update(membership);
                    response.sendRedirect("Librarian.jsp?message=Membership Approved");
                }
                
                else if ("reject".equals(action)) {
                    membership.setMembership_status(Status.REJECTED);
                    membershipDAO.update(membership);
                    response.sendRedirect("Librarian.jsp?message=Membership Rejected");
                }
            } else {
                
                response.sendRedirect("Librarian.jsp?message=Membership not found or already processed");
            }
        } else {
            
            response.sendRedirect("Librarian.jsp?message=User not found");
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