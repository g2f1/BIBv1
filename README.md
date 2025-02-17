# BIBv1 - Library Book Loan Management System

## About

BIBv1 is a Java-based application designed to manage book loans in a library. It allows library staff to track books, manage user informations, and handle loan transactions. The system supports features such as borrowing and returning books, tracking due dates, and handling user and book records.
This project follows the Model-View-Controller (MVC) pattern, separating the logic for data handling, user interface, and control flow for maintainability and scalability.Additionally, the Data Access Object (DAO) pattern is used to abstract database interactions, providing a clear separation between data access and business logic.

## Features

- Manage books in the library system.
- Track book loans with information about users and due dates.
- Handle book returns and update statuses.
- Easy-to-use interface for managing user and book data.
## Database Setup
Ensure that you have MySQL set up, then execute the following SQL commands to create the required tables:
```
CREATE TABLE Utilisateur (
    CIN VARCHAR(20) PRIMARY KEY,
    Nom VARCHAR(100),
    Prenom VARCHAR(100),
    DateDeNaissance DATE
);

CREATE TABLE Livre (
    ISBN INT PRIMARY KEY,
    Titre VARCHAR(255),
    Auteur VARCHAR(100),
    Categorie VARCHAR(100),
    NbExemplaire INT
);

CREATE TABLE Emprunt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    CIN VARCHAR(20) NOT NULL,
    ISBN INT NOT NULL,
    DateEmprunt DATETIME NOT NULL,
    DateRetour DATETIME DEFAULT '9999-12-31 23:59:59',
    Etat ENUM('R', 'E') NOT NULL DEFAULT 'E',
    FOREIGN KEY (CIN) REFERENCES Utilisateur(CIN) ON DELETE CASCADE,
    FOREIGN KEY (ISBN) REFERENCES Livre(ISBN) ON DELETE CASCADE
);

```
## Installation on Linux

The application has been successfully tested in a Linux environment and functions as expected. To install it, follow these steps:

### 1. Download and Extract
- Download the `.tar` archive and extract its contents.

### 2. Install Dependencies
- **MySQL Connector/J (v9.1.0)**:  
  Download it from [here](https://downloads.mysql.com/archives/c-j/) and place the resulting `.jar` file in its designated directory.
- **JavaFX SDK**:  
  Download it from [here](https://gluonhq.com/products/javafx/) (select the SDK version) and place the extracted files accordingly.

### 3. Run the Application
- Grant execution permissions to the `run.sh` script:
  ```bash
  chmod +x run.sh
  ```
- Then run the application
  ```bash
  ./run.sh
  ```

