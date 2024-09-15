<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ page
language="java" session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Jingle | Login</title>
        <link rel="icon" href="images/jingle_logo.jpeg" type="image/x-icon" />
        <link rel="stylesheet" href="css/login_style.css" />
        <link
            rel="stylesheet"
            href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
        />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
        />
    </head>

    <body>
        <div class="wrapper">
            <div class="square"><img src="images/jingle_logo.jpeg" /></div>
            <form action="LoginServlet" method="POST">
                <input type="hidden" name="command" value="LOGIN" />
                <h1>Login</h1>
                <p>Sign in to continue</p>
                <c:if test="${not empty requestScope.loginError}">
                    <p style="color: red">${requestScope.loginError}</p>
                </c:if>
                <div class="input-box">
                    <input
                        type="text"
                        name="email"
                        id="email"
                        placeholder="Email"
                        required
                    />
                    <i class="material-symbols-outlined">person</i>
                </div>
                <div class="input-box">
                    <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="Password"
                        required
                    />
                    <i class="bi bi-eye-slash" id="togglePassword"></i>
                </div>
                <button type="submit" id="submit" class="btn">Login</button>
                <div class="register-link">
                    <p>
                        Don't have an account?
                        <a href="RegisterServlet">Register</a>
                    </p>
                </div>
            </form>
        </div>
        <script>
            const togglePassword = document.querySelector("#togglePassword");
            const password = document.querySelector("#password");

            togglePassword.addEventListener("click", function () {
                const type =
                    password.getAttribute("type") === "password"
                        ? "text"
                        : "password";
                password.setAttribute("type", type);

                this.classList.toggle("bi-eye");
            });
        </script>
    </body>
</html>
