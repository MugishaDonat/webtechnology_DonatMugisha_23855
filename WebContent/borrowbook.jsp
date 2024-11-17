<%@ page import="java.util.List, model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Borrow Book</title>
    <link rel="stylesheet" href="css/dash.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">AUCA Library - Borrow Book</div>
        <div class="nav-links">
            <a href="StudentTeacherDashboard.jsp">Home</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </header>

    <main class="container">
        <h2>Borrow Book</h2>


        <form action="BorrowingServlet" method="post">
            <label for="bookId">Book ISBN</label>
            <input type="text" id="isbn" name="isbn" required> 

            <label for="pickupDate">Pickup Date:</label>
            <input type="date" id="pickupDate" name="pickupDate" required>

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required placeholder="Enter your username">

            <button type="submit">Borrow</button>
        </form>
    </main>

    <footer>
        <div class="footer-content" id="foot">
            <p>&copy; 2024 AUCA. All rights reserved.</p>
            <div class="social-links">
                <a href="https://www.facebook.com">Facebook</a>
                <a href="https://www.x.com">Twitter</a>
                <a href="https://www.telegram.com">Telegram</a>
                <a href="contact.jsp" style="color: white; margin-left: 10px;">+250 7888 6969</a>
            </div>
        </div>
    </footer>
</body>
</html>

/* Web Content */
