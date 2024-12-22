<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search Result</title>
    <link rel="stylesheet" href="css/libr.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">AUCA Library - Librarian</div>
        <div class="nav-links">
            <a href="Librarian.jsp">Home</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </header>

    <main class="container">
        <h2>Search Result</h2>
        
  <%
            String phoneNumber = request.getParameter("phoneNumber");

            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                controller.UserDAO userDAO = new controller.UserDAO();
                model.User user = userDAO.getUserByPhoneNumber(phoneNumber);
                if (user != null) {
                    model.Location location = user.getLocation();
                    if (location != null) {
                        out.println("<h3>User Found</h3>");
                        out.println("<p>User: " + user.getUser_name() + "</p>");
                        out.println("<p>Phone Number: " + user.getPhone_number() + "</p>");
                        out.println("<p>Village: " + location.getLocationName() + "</p>");
                    } else {
                         response.getWriter().write("Location not present.");
                    }
                } else {
                     response.setContentType("text/html");  // Set the response content type to HTML
                     response.getWriter().write("<html><body>");
                     response.getWriter().write("<h2 style='color: red;'>Error: Location not present!</h2>");
                     response.getWriter().write("</body></html>");

                }
            } else {
                out.println("<p>Please enter a valid phone number.</p>");
            }
        %>

    </main>

    <footer>
        <div class="footer-content" id="foot">
            <p>&copy; 2024 AUCA. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>
