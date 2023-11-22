package view;

import controller.MembershipDAO;
import controller.UserDAO;
import model.Membership;
import model.Membership_type;
import model.User;
import model.Status; 
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class MembershipManagementServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String personalId = request.getParameter("personalId");
        String membershipType = request.getParameter("membershipType");
        String membershipCode = request.getParameter("membershipCode");
        String registrationDate = request.getParameter("registrationDate");

        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date regDate = null;

        try {
            regDate = sdf.parse(registrationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            response.getWriter().write("Invalid date format.");
            return;
        }

        // Find the corresponding User based on Personal ID
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(personalId);

        if (user == null) {
            response.getWriter().write("User not found.");
            return;
        }

      
        Membership_type membershipTypeEntity = getMembershipTypeByName(membershipType);

        if (membershipTypeEntity == null) {
            response.getWriter().write("Invalid membership type.");
            return;
        }

        
        Membership existingMembership = getMembershipByUserId(user.getUser_id());

        if (existingMembership != null) {
            
            existingMembership.setMembership_type(membershipTypeEntity); 
            existingMembership.setRegistration_date(regDate);
            existingMembership.setMembership_status(Status.PENDING); 
            existingMembership.setUser(user);

          
            existingMembership.setExpiring_date(calculateExpiringDate(regDate));

            
            MembershipDAO membershipDAO = new MembershipDAO();
            membershipDAO.update(existingMembership);

            
            response.sendRedirect("viewMembership.jsp");
        } else {
            
            Membership membership = new Membership();
            membership.setMembership_type(membershipTypeEntity); // Set the Membership_type object
            membership.setRegistration_date(regDate);
            membership.setUser(user);

           
            membership.setMembership_status(Status.PENDING);
            membership.setExpiring_date(calculateExpiringDate(regDate));

            MembershipDAO membershipDAO = new MembershipDAO();
            membershipDAO.save(membership);

            response.sendRedirect("viewMembership.jsp");
        }
    }

   
    private Membership_type getMembershipTypeByName(String membershipType) {
        Session session = HibernateUtil.getSession().openSession();
        Membership_type membershipTypeEntity = null;

        try {
            
            String hql = "FROM Membership_type WHERE membership_name = :membership_name";
            membershipTypeEntity = (Membership_type) session.createQuery(hql)
                .setParameter("membership_name", membershipType)
                .uniqueResult();
        } finally {
            session.close();
        }

        return membershipTypeEntity;
    }

    
    private Membership getMembershipByUserId(UUID userId) {
        Session session = HibernateUtil.getSession().openSession();
        Membership existingMembership = null;

        try {
            String hql = "FROM Membership WHERE user.user_id = :user_id";
            existingMembership = (Membership) session.createQuery(hql)
                .setParameter("user_id", userId)
                .uniqueResult();
        } finally {
            session.close();
        }

        return existingMembership;
    }

   
    private Date calculateExpiringDate(Date registrationDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registrationDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adds 30 days to the registration date
        return calendar.getTime();
    }
}



/**
 * 
 */
/**
 * @author Donat
 *
 */