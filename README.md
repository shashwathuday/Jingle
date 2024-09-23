# Jingle Music App

Jingle is a dynamic music streaming app that integrates a modern MVC architecture with personalized recommendations and a user-friendly interface. Built with JSP for the frontend, Java servlets as the controller, and a MySQL database for the backend, Jingle offers an engaging and tailored music experience for its users.

## Features

### 1. **User Authentication & Personalization**
- **Login/Logout:** Secure login with validated credentials. Users can safely log out of their accounts.
- **Account Management:** New users can register with a unique email. Duplicate credentials trigger alerts.
- **Personalized Greeting:** Custom greetings based on the time of day.

### 2. **Music Browsing & Interaction**
- **Search:** Search functionality to easily find songs and albums.
- **Recently Played & Trending:** Displays the last 10 songs played and top 5 trending tracks.
- **Song Interaction:** Includes play/pause, next/previous song controls, loop, mute, and playlist management.

### 3. **User Menu**
- **Profile Picture:** Displays the user's profile picture, with a default option if none is set.
- **User Information:** Shows user details from the database (currently read-only).

### 4. **Song Card**
- A dynamically updating card showing the currently playing song with detailed information, song controls, and progress indicators.

## Architecture Overview

Jingle follows the **Model-View-Controller (MVC)** design pattern:

- **Frontend:** Built with JSP, HTML, CSS for dynamic rendering and styling, and JavaScript for handling interactions.
- **Controller:** Java Servlets act as the controller, managing the app flow and interacting with the backend.
- **Backend:** A MySQL database stores user and music data. The server runs on Apache Tomcat, handling requests and responses.

## Recommendation System

Jingle provides music recommendations through multiple methods:

1. **Popularity-Based Recommendations:** Tracks the top 5 most played songs using SQL queries.
2. **Collaborative Filtering:** Uses user behavior to recommend songs. If users with similar interests listen to different tracks, the app suggests those tracks to the user.
   - **Implementation:** Uses Flask and Python, employing matrix factorization and alternating least squares to generate suggestions.

## Setup and Deployment

### Requirements:
- **Java 11+**
- **Apache Tomcat 9+**
- **MySQL Workbench**
- **Flask (for Recommendation System)**

### Steps:
1. Clone the repository.
2. Set up MySQL and import the necessary tables.
3. Deploy the app on Apache Tomcat.
4. Run the Flask server for recommendations.

## Future Enhancements
- Add user profile update functionality.
- Improve content-based recommendation accuracy.
