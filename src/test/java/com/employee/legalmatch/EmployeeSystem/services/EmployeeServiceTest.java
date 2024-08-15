package com.employee.legalmatch.EmployeeSystem.services;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.dto.PageSize;
import com.employee.legalmatch.EmployeeSystem.dto.PagedEmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;
import com.employee.legalmatch.EmployeeSystem.mapper.IEmployeeMapper;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeAddressRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeContactRepository;
import com.employee.legalmatch.EmployeeSystem.repository.IEmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.employee.legalmatch.EmployeeSystem.util.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    IEmployeeRepository employeeRepository;
    @Mock
    IEmployeeContactRepository employeeContactRepository;
    @Mock
    IEmployeeAddressRepository employeeAddressRepository;
    @Mock
    IEmployeeMapper employeeMapper;
    @Mock
    EntityManager entityManager;

    @InjectMocks
    EmployeeService employeeServiceInTest;

    static ZonedDateTime dateNow;

    @BeforeAll
    static void setUp() {
        dateNow = ZonedDateTime.now();
    }

    @Test
    void testGetEmployees() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);
        var databaseResponse = new PageImpl<>(List.of(employeeFromDB), PageRequest.of(0, 10), 1);

        var mapped = new PagedEmployeeDTO();
        mapped.setEmployees(List.of(generateTestPersistedEmployeeDTO(dateNow)));
        mapped.setTotalCount(1L);

        var expected = new PagedEmployeeDTO();
        expected.setEmployees(List.of(generateTestPersistedEmployeeDTO(dateNow)));
        expected.setTotalCount(1L);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(databaseResponse);
        when(employeeRepository.count()).thenReturn(1L);
        when(employeeMapper.map(any(), anyLong())).thenReturn(mapped);

        var pageSizeRequest = new PageSize(0, 10);
        var actual = employeeServiceInTest.getEmployees(pageSizeRequest);

        assertEquals(expected, actual);
    }

    @Test
    void testGetEmployeeById() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);
        var employeeDto = generateTestPersistedEmployeeDTO(dateNow);

        var expected = generateTestPersistedEmployeeDTO(dateNow);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeFromDB));
        when(employeeMapper.map(any(Employee.class))).thenReturn(employeeDto);

        var actual = employeeServiceInTest.getEmployeeById(1);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateEmployee() {
        var testData = generateTestEmployeeDTO(dateNow);
        var afterMappingDTOToEntity = generateTestEmployee(dateNow);
        var afterSavingEntity = generateTestPersistedEmployee(dateNow);
        var expected = generateTestPersistedEmployee(dateNow);

        when(employeeMapper.map(eq(testData))).thenReturn(afterMappingDTOToEntity);
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(afterSavingEntity);

        Employee actual = employeeServiceInTest.createEmployee(testData);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateEmployeeIdNotFound() {
        var request = new EmployeeDTO();
        request.setEmployeeId(2);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeServiceInTest.updateEmployee(request));
    }

    @Test
    void testUpdateGranularFirstNameEmployee() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);

        var request = new EmployeeDTO();
        request.setEmployeeId(1);
        request.setFirstName("Changed");

        var afterSave = generateTestPersistedEmployee(dateNow);
        afterSave.setFirstName("Changed");

        var expected = generateTestPersistedEmployee(dateNow);
        expected.setFirstName("Changed");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeFromDB));
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(afterSave);

        Employee actual = employeeServiceInTest.updateEmployee(request);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateGranularChangedAllEmployee() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);

        var request = new EmployeeDTO();
        request.setEmployeeId(1);
        request.setFirstName("Changed");
        request.setLastName("Changed");
        request.setMiddleName("Changed");
        request.setBirthDate(dateNow);
        request.setHireDate(dateNow);
        request.setGender("Changed");
        request.setMaritalStatus("Changed");
        request.setCurrentPosition("Changed");

        var afterSave = generateTestPersistedEmployee(dateNow);
        afterSave.setFirstName("Changed");
        afterSave.setFirstName("Changed");
        afterSave.setLastName("Changed");
        afterSave.setMiddleName("Changed");
        afterSave.setBirthDate(dateNow);
        afterSave.setHireDate(dateNow);
        afterSave.setGender("Changed");
        afterSave.setMaritalStatus("Changed");
        afterSave.setCurrentPosition("Changed");

        var expected = generateTestPersistedEmployee(dateNow);
        expected.setFirstName("Changed");
        expected.setFirstName("Changed");
        expected.setLastName("Changed");
        expected.setMiddleName("Changed");
        expected.setBirthDate(dateNow);
        expected.setHireDate(dateNow);
        expected.setGender("Changed");
        expected.setMaritalStatus("Changed");
        expected.setCurrentPosition("Changed");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeFromDB));
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(afterSave);

        Employee actual = employeeServiceInTest.updateEmployee(request);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateGranularClearContactAndAddressEmployee() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);

        var request = new EmployeeDTO();
        request.setEmployeeId(1);
        request.setClearContacts(true);
        request.setClearAddresses(true);
        request.setContacts(new ArrayList<>());
        request.setAddresses(new ArrayList<>());

        var afterSave = generateTestPersistedEmployee(dateNow);
        afterSave.getContacts().clear();
        afterSave.getAddresses().clear();

        var expected = generateTestPersistedEmployee(dateNow);
        expected.getContacts().clear();
        expected.getAddresses().clear();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeFromDB));
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(afterSave);

        Employee actual = employeeServiceInTest.updateEmployee(request);

        assertEquals(expected, actual);
    }

    /**
     * Covers adding, updating, and deleting a contact/address at the same time.
     * Contact/Address with ID 1 - Update
     * Contact/Address with ID 2 - Delete
     * Contact/Address with ID 3 - Create
     */
    @Test
    void testUpdateGranularEditContactAndAddressEmployee() {
        var employeeFromDB = generateTestPersistedEmployee(dateNow);
        var contactForDeletion = new EmployeeContact();
        contactForDeletion.setContactId(2);
        contactForDeletion.setContactInfo("test");
        contactForDeletion.setCreatedOn(dateNow);
        contactForDeletion.setUpdatedOn(dateNow);
        employeeFromDB.getContacts().add(contactForDeletion);

        var addressForDeletion = new EmployeeAddress();
        addressForDeletion.setAddressId(2);
        addressForDeletion.setAddress1("test");
        addressForDeletion.setAddress2("test");
        addressForDeletion.setCreatedOn(dateNow);
        addressForDeletion.setUpdatedOn(dateNow);
        employeeFromDB.getAddresses().add(addressForDeletion);


        var request = new EmployeeDTO();
        request.setEmployeeId(1);

        var newContact = new EmployeeContact();
        newContact.setContactInfo("test");
        var updateContact = new EmployeeContact();
        updateContact.setContactId(1);
        updateContact.setContactInfo("test");
        var contacts = new ArrayList<EmployeeContact>();
        contacts.add(newContact);
        contacts.add(updateContact);
        request.setContacts(contacts);

        var newAddress = new EmployeeAddress();
        newAddress.setAddress1("test");
        newAddress.setAddress2("test");
        var updateAddress = new EmployeeAddress();
        updateAddress.setAddressId(1);
        updateAddress.setAddress1("test");
        updateAddress.setAddress2("test");
        var addresses = new ArrayList<EmployeeAddress>();
        addresses.add(newAddress);
        addresses.add(updateAddress);
        request.setAddresses(addresses);


        var mergedContact = new EmployeeContact();
        mergedContact.setContactId(3);
        mergedContact.setContactInfo("test");
        mergedContact.setCreatedOn(dateNow);
        mergedContact.setUpdatedOn(dateNow);

        var mergedAddress = new EmployeeAddress();
        mergedAddress.setAddressId(3);
        mergedAddress.setAddress1("test");
        mergedAddress.setAddress2("test");
        mergedAddress.setCreatedOn(dateNow);
        mergedAddress.setUpdatedOn(dateNow);


        var afterSave = generateTestPersistedEmployee(dateNow);
        var createdContact = new EmployeeContact();
        createdContact.setContactId(3);
        createdContact.setContactInfo("test");
        createdContact.setCreatedOn(dateNow);
        createdContact.setUpdatedOn(dateNow);
        createdContact.setEmployee(afterSave);
        afterSave.getContacts().add(createdContact);

        var createdAddress = new EmployeeAddress();
        createdAddress.setAddressId(3);
        createdAddress.setAddress1("test");
        createdAddress.setAddress2("test");
        createdAddress.setCreatedOn(dateNow);
        createdAddress.setUpdatedOn(dateNow);
        createdAddress.setEmployee(afterSave);
        afterSave.getAddresses().add(createdAddress);


        var expected = generateTestPersistedEmployee(dateNow);
        var expectedCreatedContact = new EmployeeContact();
        expectedCreatedContact.setContactId(3);
        expectedCreatedContact.setContactInfo("test");
        expectedCreatedContact.setCreatedOn(dateNow);
        expectedCreatedContact.setUpdatedOn(dateNow);
        expectedCreatedContact.setEmployee(expected);
        expected.getContacts().add(expectedCreatedContact);

        var expectedCreatedAddress = new EmployeeAddress();
        expectedCreatedAddress.setAddressId(3);
        expectedCreatedAddress.setAddress1("test");
        expectedCreatedAddress.setAddress2("test");
        expectedCreatedAddress.setCreatedOn(dateNow);
        expectedCreatedAddress.setUpdatedOn(dateNow);
        expectedCreatedAddress.setEmployee(expected);
        expected.getAddresses().add(expectedCreatedAddress);


        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employeeFromDB));
        when(entityManager.merge(any(EmployeeContact.class))).thenReturn(mergedContact);
        when(entityManager.merge(any(EmployeeAddress.class))).thenReturn(mergedAddress);
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(afterSave);

        Employee actual = employeeServiceInTest.updateEmployee(request);

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteEmployee() {
        Integer expected = 1;

        Integer actual = employeeServiceInTest.deleteEmployee(1);

        assertEquals(expected, actual);
    }
}
