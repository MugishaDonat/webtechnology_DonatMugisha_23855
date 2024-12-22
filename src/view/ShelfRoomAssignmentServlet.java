package view;


import controller.RoomDAO;
import controller.ShelfDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Room;
import model.Shelf;


public class ShelfRoomAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("room");
        String bookCategory = request.getParameter("bookCategory");

        // Fetch Room by room code
        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.getAll().stream()
                .filter(r -> r.getRoom_code().equals(roomCode))
                .findFirst()
                .orElse(null);

        // If room is not found, create it (optional, if needed)
        if (room == null) {
            room = new Room();
            room.setRoom_code(roomCode);
            roomDAO.save(room);
        }

        // Fetch Shelf by book category
        ShelfDAO shelfDAO = new ShelfDAO();
        Shelf shelf = shelfDAO.getAll().stream()
                .filter(s -> s.getBook_category().equals(bookCategory))
                .findFirst()
                .orElse(null);

        // If Shelf doesn't exist, create a new Shelf and assign Room
        if (shelf == null) {
            shelf = new Shelf();
            shelf.setBook_category(bookCategory); // Set the book category
            shelf.setRoom(room); // Assign the room
            shelfDAO.save(shelf); // Save new shelf
        } else {
            // If Shelf exists, update the Room reference
            shelf.setRoom(room);
            shelfDAO.update(shelf); 
        }

        
        response.sendRedirect("assignroom.jsp"); 
    }
}
