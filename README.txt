Java Swing HR Management System
A complete, stand-alone desktop application for managing Human Resources, built with Java Swing. This project was developed for the Colombo Institute of Studies and emphasizes a clean, layered architecture and strict adherence to Object-Oriented Programming (OOP) principles.

🌟 About The Project
This application provides a user-friendly graphical interface for administrators and HR managers to perform their daily tasks. It features role-based access control, full CRUD (Create, Read, Update, Delete) functionality for all major entities, and persists all data to local text files, making it portable and easy to run without any database setup.

✨ Features
User Authentication: Secure login system for different user roles.

Role-Based Access Control

Admin: Manages HR Manager accounts.

HR Manager: Manages employees, departments, and designations.

Modern GUI: A clean, modern user interface with custom backgrounds and intuitive navigation.

Admin Module:

Full CRUD operations for HR Manager user accounts.

Search and filter capabilities.

HR Manager Module:

Full CRUD operations for Employees.

Full CRUD operations for Departments.

Full CRUD operations for Designations.

User-friendly forms with dropdowns for data integrity.

File-Based Persistence: All application data is saved locally in pipe-delimited .txt files within a data directory.

🛠️ Tech Stack & Architecture
Technologies Used
Language: Java

GUI Framework: Java Swing

Version Control: Git

Architecture
The project is built on a layered architecture to ensure a strong Separation of Concerns, making it scalable and easy to maintain.

gui (Presentation Layer): Contains all Java Swing components. Responsible for everything the user sees and interacts with.

service (Business Logic Layer): Contains the core application logic, validation, and orchestration. It acts as a bridge between the GUI and the data layer.

dao (Data Access Layer): Manages the reading and writing of data to the text files. It completely abstracts the data persistence mechanism from the rest of the application.

model (Domain Layer): Contains the POJO (Plain Old Java Object) classes that represent the core entities of the application (User, Employee, etc.).

This structure is a practical demonstration of key OOP principles like Encapsulation, Inheritance, Abstraction, and Polymorphism, as well as design patterns like the Single Responsibility Principle (SRP) and Dependency Inversion Principle (DIP).



Use the default admin credentials to log in for the first time:

Username: admin

Password: admin

As the admin, you can navigate to "Add HR Manager" to create a new user account for an HR Manager.

Log out and log back in as the newly created HR Manager.

As the HR Manager, you can now manage employees, departments, and designations.

📸 Screenshots
(You can replace these lines with your own screenshots)

👨‍💻 Author
Akif

