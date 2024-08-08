# Web-Programming
# Professor Engagement System

## Description
This is a Spring Boot application that allows professors to manage and track activities. The application follows the Model-View-Controller (MVC) pattern and is built using Java with Spring Boot. It leverages Thymeleaf for the view layer and connects to a database for data persistence.

## Features

- **Add Activities:** Professors can add activities they have done.
- **Edit Activities:** Professors can modify the details of their existing activities.
- **Delete Activities:** Professors can delete activities.

## Steps
- **Login:** Professors have to log in to the system with their credentials.
- **Manage Work:** Professors can add, edit and delete work they've completed in different areas.
- **View Total Points:** Professors can click on the "Образец" button to view the total points earned from all activities across all areas. Points are calculated dynamically using database queries.

## Technologies Used
- **Spring Boot:** For building the application and managing the MVC architecture.
- **Thymeleaf:** For rendering views and handling the front-end logic.
- **Spring Data JPA:** For interacting with the database, with queries written in the repository layer.
- **PostgreSQL:** The database used to store professor and activity data.
