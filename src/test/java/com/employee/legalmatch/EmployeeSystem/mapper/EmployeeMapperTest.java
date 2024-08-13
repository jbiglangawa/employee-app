package com.employee.legalmatch.EmployeeSystem.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static com.employee.legalmatch.EmployeeSystem.util.TestDataGenerator.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeMapperTest {
    @InjectMocks
    EmployeeMapper employeeMapperInTest;

    static ZonedDateTime dateNow;

    @BeforeAll
    static void setUp() {
        dateNow = ZonedDateTime.now();
    }

    @Test
    void testMapEmployeeDTOtoEmployee() {
        var employeeDTO = generateTestEmployeeDTO(dateNow);

        var expected = generateTestEmployee(dateNow);

        var actual = employeeMapperInTest.map(employeeDTO);

        Assertions.assertEquals(expected, actual);
    }

}
