CREATE TABLE IF NOT EXISTS Employee (
    EmployeeId INT NOT NULL,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    MiddleName VARCHAR(255),
    Age INT,
    BirthDate TIMESTAMP,
    HireDate TIMESTAMP,
    Gender VARCHAR(255),
    MaritalStatus VARCHAR(255),
    CurrentPosition VARCHAR(255),
    PRIMARY KEY (EmployeeId)
);
