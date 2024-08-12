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

CREATE TABLE IF NOT EXISTS EmployeeContact (
    ContactId INT NOT NULL,
    EmployeeId INT,
    ContactInfo VARCHAR(100),
    IsPrimary BIT,
    PRIMARY KEY (ContactId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);

CREATE TABLE IF NOT EXISTS EmployeeAddress (
    AddressId INT NOT NULL,
    EmployeeId INT,
    Address1 VARCHAR(500),
    Address2 VARCHAR(500),
    IsPrimary BIT,
    PRIMARY KEY (AddressId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);
