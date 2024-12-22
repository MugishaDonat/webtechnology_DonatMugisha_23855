<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Approve Membership Requests</title>
    <link rel="stylesheet" href="css/libr.css">
</head>
<body>
    <header class="navbar">
        <div class="logo">AUCA Library - Approve Requests</div>
        <div class="nav-links">
            <a href="Librarian.jsp">Home</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </header>

    <main class="container">
        <h3>Approve Membership Requests</h3>
        
        <form action="RequestApprovalServlet" method="POST">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required >
                <button type="submit">APPROVE</button>
                <button type="submit">REJECT</button>
                
            </form>
    </main>
    
    <footer>
        <div class="footer-content" id="foot">
            <p>&copy; 2024 AUCA. All rights reserved.</p>
            <div class="social-links">
                <!-- Social icons and contact link -->
            </div>
        </div>
    </footer>

   
</body>
</html>
