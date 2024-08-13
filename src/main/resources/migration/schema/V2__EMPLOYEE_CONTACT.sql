CREATE TABLE IF NOT EXISTS EmployeeContact (
    ContactId INT NOT NULL AUTO_INCREMENT,
    EmployeeId INT,
    ContactInfo VARCHAR(100),
    IsPrimary BIT DEFAULT 0,
    CreatedOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    UpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (ContactId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);
