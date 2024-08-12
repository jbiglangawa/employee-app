CREATE TABLE IF NOT EXISTS EmployeeContact (
    ContactId INT NOT NULL AUTO_INCREMENT,
    EmployeeId INT,
    ContactInfo VARCHAR(100),
    IsPrimary BIT,
    PRIMARY KEY (ContactId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);
