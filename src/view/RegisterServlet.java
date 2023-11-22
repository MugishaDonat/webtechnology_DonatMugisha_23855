package view;

import controller.LocationDAO;
import controller.UserDAO;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Gender;
import model.Role;
import model.User;
import model.Location;
import org.mindrot.jbcrypt.BCrypt; 

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");
        String genderStr = request.getParameter("gender");
        String email = request.getParameter("email");
        String personalId = request.getParameter("personalId");

        // Additional village parameter
        String villageName = request.getParameter("village");

        if (firstName == null || lastName == null || phoneNumber == null || username == null || 
            password == null || roleStr == null || genderStr == null || email == null || personalId == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Role role;
        Gender gender;
        try {
            role = Role.valueOf(roleStr.toUpperCase());
            gender = Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid role or gender.");
            request.getRequestDispatcher("signup.html").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        if (userDAO.isUsernameExists(username)) {
            response.getWriter().write("<html><body><h2 style='color: red;'>The username is taken.</h2></body></html>");
            return;
        }

        if (userDAO.isPhoneNumberExists(phoneNumber)) {
            response.getWriter().write("<html><body><h2 style='color: red;'>Phone number already in use.</h2></body></html>");
            return;
        }

        if (userDAO.isEmailExists(email)) {
             response.getWriter().write("<html><body><h2 style='color: red;'>Email already in use.</h2></body></html>");
            return;
        }

        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setUser_id(userId);
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setPhone_number(phoneNumber);
        user.setUser_name(username);

        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        user.setRole(role);
        user.setGender(gender);
        user.setEmail(email); 
        user.setPerson_id(personalId); 
        
        
        if (villageName != null && !villageName.isEmpty()) {
            LocationDAO locationDAO = new LocationDAO();
            UUID villageLocationId = locationDAO.getLocationIdByName(villageName); // Get only the ID of the village location

            if (villageLocationId != null) {
                Location villageLocation = locationDAO.getById(villageLocationId); // Fetch the full Location entity by ID
                user.setLocation(villageLocation); // Set village location if found
            } else {
                request.setAttribute("error", "Village location not found.");
                request.getRequestDispatcher("signup.html").forward(request, response);
                return;
            }
        }

        try {
            userDAO.save(user);
        } catch (Exception e) {
            request.getRequestDispatcher("signup.html").forward(request, response);
            return;
        }

        response.sendRedirect("login.html");
    }
}



/**
 * 
 */
/**
 * @author Donat
 *
 */