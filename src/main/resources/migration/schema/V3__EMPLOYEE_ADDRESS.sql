CREATE TABLE IF NOT EXISTS EmployeeAddress (
    AddressId INT NOT NULL,
    EmployeeId INT,
    Address1 VARCHAR(500),
    Address2 VARCHAR(500),
    IsPrimary BIT,
    PRIMARY KEY (AddressId),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
);
