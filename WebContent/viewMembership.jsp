<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Membership</title>
    <link rel="stylesheet" href="css/dash.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">AUCA Library - View Membership</div>
        <div class="nav-links">
            <a href="StudentTeacherDashboard.jsp">Home</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </header>

    <main class="container">

        <!-- Full Membership Form -->
        <h4>Update Membership Information</h4>
        <form action="MembershipManagementServlet" method="post">
            <!-- Personal ID Field (linked to User entity) -->
            <label for="personalId">Username:</label>
            <input type="text" id="personalId" name="personalId" placeholder="Enter your personal ID" required />

            <!-- Membership Type Selection -->
            <label for="membershipType">Membership Type:</label>
            <select id="membershipType" name="membershipType" required>
                <option value="Gold">Gold - 50 Rwf per day, up to 5 books</option>
                <option value="Silver">Silver - 30 Rwf per day, up to 3 books</option>
                <option value="Striver">Striver - 10 Rwf per day, up to 2 books</option>
            </select>

            <!-- Membership Code -->
            <label for="membershipCode">Membership Code:</label>
            <input type="text" id="membershipCode" name="membershipCode" placeholder="Enter membership code" required />

            <!-- Registration Date -->
            <label for="registrationDate"> Date:</label>
            <input type="date" id="registrationDate" name="registrationDate" required />


            <button type="submit">Update Membership</button>
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
