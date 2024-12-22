<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/dash.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500&display=swap" rel="stylesheet"> 
</head>
<body>
    <header class="navbar">
        <div class="logo">AUCA Library - Admin</div>
        <div class="nav-links">
            <a href="#foot">Contact</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </header>

    <main class="container">
        <h2>Welcome to the Admin Dashboard</h2>
        
        <!-- Tabs for Admin Actions -->
        <div class="tabs">
            <button onclick="showSection('viewDetails')">Books Details</button>
            <button onclick="showSection('viewBorrowers')">View Borrowers</button>
        </div>

        <!-- View All Details Section -->
        <section id="viewDetails" class="content-section" style="display:block;">
            <h2>Books List</h2>
            <table style="border-spacing: 20px; border-collapse: separate;">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>ISBN</th>
                        <th>Publisher</th>
                        <th>Publication Year</th>
                        <th>Edition</th>
                        <th>Available Stock</th>
                       
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>${book.title}</td>
                            <td>${book.ISBNCode}</td>
                            <td>${book.publisher_name}</td>
                            <td>${book.publication_year}</td>
                            <td>${book.edition}</td>
                            <td>${book.available_stock}</td>
                            <td>${book.book_status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
        </section>

        <!-- View Borrowers Section -->
        <section id="viewBorrowers" class="content-section" style="display: none;">
           <c:if test="${not empty borrowers}">
                <h4>Borrowers and Borrowed Books</h4>
                <table  style="border-spacing: 20px; border-collapse: separate;" >
                    <thead>
                        <tr>
                            <th>Borrower Name</th>
                            <th>Book Title</th>
                            <th>Borrowed Date</th>
                            <th>Membership Type</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="borrower" items="${borrowers}">
                            <tr>
                                <td>${borrower.user.user_name}</td>
                                <td>${borrower.book.title}</td>
                                <td>${borrower.pickupDate}</td>
                                <td>
                                    <c:forEach var="membership" items="${memberships}">
                                        <c:if test="${membership.user.user_id == borrower.user.user_id}">
                                            ${membership.membership_type.membership_name}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </main>

    <script>
        function showSection(sectionId) {
            document.querySelectorAll('.content-section').forEach(section => {
                section.style.display = 'none';
            });
            document.getElementById(sectionId).style.display = 'block';
        }
    </script>

    <footer>
        <div class="footer-content" id="foot">
            <p>&copy; 2024 AUCA. All rights reserved.</p>
            <div class="social-links">
                <a href="https://www.facebook.com">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path fill="white" d="M24 12c0-6.627-5.373-12-12-12S0 5.373 0 12c0 5.985 4.388 10.965 10.125 11.874v-8.407H7.078v-3.467h3.047V10.3c0-3.016 1.791-4.67 4.478-4.67 1.312 0 2.688.237 2.688.237v3.02h-1.513c-1.488 0-1.95.925-1.95 1.873v2.188h3.35l-.536 3.467h-2.814v8.407C19.612 22.965 24 17.985 24 12z"/>
                    </svg>
                </a>
                <a href="https://www.x.com">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path fill="white" d="M19.635 3.538c-.689.305-1.435.511-2.195.601A4.207 4.207 0 0019.155 2c-1.592 0-2.896 1.372-2.896 3.068 0 .24.027.473.085.699-2.41-.124-4.548-1.249-5.965-2.988-.249.428-.39.932-.39 1.465 0 1.014.516 1.905 1.301 2.427a4.137 4.137 0 01-1.312-.357v.036c0 1.415 1.005 2.59 2.334 2.862-.243.066-.505.102-.769.102-.188 0-.373-.018-.55-.052.373 1.16 1.457 2 2.746 2.028A8.427 8.427 0 010 18.541a11.844 11.844 0 006.43 1.884c7.716 0 11.925-6.393 11.925-11.925 0-.181-.004-.362-.014-.541A8.455 8.455 0 0020.12 5c-1.09.49-2.268.828-3.513.979a4.165 4.165 0 001.832-2.293z"/>
                    </svg>
                </a>
                <a href="https://www.telegram.com">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path fill="white" d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12S18.627 0 12 0zm0 22.62c-5.828 0-10.62-4.791-10.62-10.62S6.172 1.38 12 1.38 22.62 6.171 22.62 12 17.828 22.62 12 22.62zm-1.5-15h-3v6h3v8h3v-8h3l.5-6h-3V7.62C10.5 7.622 10.5 7.63 10.5 7.62z"/>
                    </svg>
                </a>
                <a href="contact.jsp" style="color: white; margin-left: 10px;">+250 7888 6969</a>
            </div>
        </div>
    </footer>

</body>
</html>
