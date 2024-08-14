package com.employee.legalmatch.EmployeeSystem.mapper;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;

import static com.employee.legalmatch.EmployeeSystem.util.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(expected, actual);
    }

    @Test
    void testMapEmployeeToEmployeeDTO() {
        var employee = generateTestPersistedEmployee(dateNow);

        var expected = generateTestPersistedEmployeeDTO(dateNow);

        var actual = employeeMapperInTest.map(employee);

        assertEquals(expected, actual);
    }

    @Test
    void testMapEmployeesAndTotalCountToPagedEmployeeDTO() {
        var employee = generateTestPersistedEmployee(dateNow);

        var expected = new PagedEmployeeDTO();
        expected.setEmployees(List.of(generateTestPersistedEmployeeDTO(dateNow)));
        expected.setTotalCount(1L);

        var actual = employeeMapperInTest.map(List.of(employee), 1L);

        assertEquals(expected, actual);
    }
}
