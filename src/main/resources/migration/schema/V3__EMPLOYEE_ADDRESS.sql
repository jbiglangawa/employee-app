CREATE TABLE IF NOT EXISTS EmployeeAddress (
    AddressId INT NOT NULL AUTO_INCREMENT,
    EmployeeId INT NOT NULL,
    Address1 VARCHAR(500) NOT NULL,
    Address2 VARCHAR(500) NOT NULL,
    IsPrimary BIT DEFAULT 0,
    CreatedOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    UpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (AddressId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);
