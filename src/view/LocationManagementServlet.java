package view;

import controller.LocationDAO;
import model.Location;
import model.Location_type;
import util.HibernateUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/manageLocation")
public class LocationManagementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationName = request.getParameter("locationName");
        String locationTypeStr = request.getParameter("locationType");
        String parentName = request.getParameter("parentName");

        Location_type locationType = Location_type.valueOf(locationTypeStr); // Convert string to enum
        Session session = HibernateUtil.getSession().openSession();
        LocationDAO locationDAO = new LocationDAO();
        Location parentLocation = null;

        try {
            session.beginTransaction();

            
            if (parentName != null && !parentName.trim().isEmpty()) {
                UUID parentLocationId = locationDAO.getLocationIdByName(parentName); // Get only the ID of the parent
                if (parentLocationId == null) {
                    request.setAttribute("errorMessage", "Parent location not found.");
                    request.getRequestDispatcher("manageLocation.jsp").forward(request, response);
                    return;
                } else {
                    
                    parentLocation = locationDAO.getById(parentLocationId);
                }
            }

           
            Location newLocation = new Location();
            newLocation.setLocationName(locationName);
            newLocation.setLocationType(locationType);
            newLocation.setParentLocation(parentLocation);

           
            locationDAO.save(newLocation);

            session.getTransaction().commit();

            // Redirect back to the management page with success message
            request.setAttribute("successMessage", "Location added successfully!");
            request.getRequestDispatcher("manageLocation.jsp").forward(request, response);

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to add location.");
            request.getRequestDispatcher("manageLocation.jsp").forward(request, response);

        } finally {
            session.close();
        }
    }
}
