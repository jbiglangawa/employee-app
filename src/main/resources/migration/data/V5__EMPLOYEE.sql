INSERT INTO Employee
    (EmployeeId, FirstName, LastName, MiddleName, BirthDate, HireDate, Gender, MaritalStatus, CurrentPosition)
VALUES
    (1, 'Juan', "Dela Cruz", "Martin", "1998-03-20 16:00:00", "2020-01-01 00:00:00", "Male", "Single", "Software Developer");
INSERT INTO Employee
    (EmployeeId, FirstName, LastName, MiddleName, BirthDate, HireDate, Gender, MaritalStatus, CurrentPosition)
VALUES
    (2, 'Lorem', "Dolor", "Ipsum", "1998-03-20 16:00:00", "2020-01-01 00:00:00", "Male", "Single", "Software Developer");
INSERT INTO Employee
    (EmployeeId, FirstName, LastName, MiddleName, BirthDate, HireDate, Gender, MaritalStatus, CurrentPosition)
VALUES
    (3, 'John', "Doe", "James", "1998-03-20 16:00:00", "2020-01-01 00:00:00", "Male", "Single", "Software Developer");
INSERT INTO Employee
    (EmployeeId, FirstName, LastName, MiddleName, BirthDate, HireDate, Gender, MaritalStatus, CurrentPosition)
VALUES
    (4, 'John Marvie', "Biglang-awa", "Elcana", "1998-03-20 16:00:00", "2020-01-01 00:00:00", "Male", "Single", "Senior Java Developer");

INSERT INTO EmployeeContact
    (ContactInfo, IsPrimary, EmployeeId)
VALUES
    ('09123456789', 1, 1);
INSERT INTO EmployeeContact
    (ContactInfo, IsPrimary, EmployeeId)
VALUES
    ('09417914584', 1, 3);
INSERT INTO EmployeeContact
    (ContactInfo, IsPrimary, EmployeeId)
VALUES
    ('09411489744', 1, 4);
INSERT INTO EmployeeContact
    (ContactInfo, IsPrimary, EmployeeId)
VALUES
    ('09480131739', 0, 4);

INSERT INTO EmployeeAddress
    (Address1, Address2, IsPrimary, EmployeeId)
VALUES
    ('Mother Ignacia Street,Sgt. E.A. Esguerra Avenue Corner', 'Quezon City 1100,Metro Manila', 1, 1);
INSERT INTO EmployeeAddress
    (Address1, Address2, IsPrimary, EmployeeId)
VALUES
    ('Malvar St, Manila', 'Metro Manila, 1004', 1, 3);
INSERT INTO EmployeeAddress
    (Address1, Address2, IsPrimary, EmployeeId)
VALUES
    ('297 Santolan Rd, Quezon City', 'Metro Manila 1504', 1, 4);
INSERT INTO EmployeeAddress
    (Address1, Address2, IsPrimary, EmployeeId)
VALUES
    ('2090 Dr M.L. Carreon St, Manila', 'Metro Manila, 1009', 0, 4);
