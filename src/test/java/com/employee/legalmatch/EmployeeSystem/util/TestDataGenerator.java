package com.employee.legalmatch.EmployeeSystem.util;

import com.employee.legalmatch.EmployeeSystem.dto.EmployeeDTO;
import com.employee.legalmatch.EmployeeSystem.entity.Employee;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeAddress;
import com.employee.legalmatch.EmployeeSystem.entity.EmployeeContact;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TestDataGenerator {
    public static EmployeeDTO generateTestEmployeeDTO(ZonedDateTime dateToUse) {
        var dummyData = new EmployeeDTO();
        dummyData.setFirstName("test");
        dummyData.setLastName("test");
        dummyData.setMiddleName("test");
        dummyData.setBirthDate(dateToUse);
        dummyData.setHireDate(dateToUse);
        dummyData.setGender("test");
        dummyData.setMaritalStatus("test");
        dummyData.setCurrentPosition("test");

        var dummyContact = new EmployeeContact();
        dummyContact.setContactInfo("test");
        dummyContact.setIsPrimary(false);
        var dummyContactList = new ArrayList<EmployeeContact>();
        dummyContactList.add(dummyContact);
        dummyData.setContacts(dummyContactList);

        var dummyAddress = new EmployeeAddress();
        dummyAddress.setAddress1("test");
        dummyAddress.setAddress2("test");
        dummyAddress.setIsPrimary(false);
        var dummyAddressList = new ArrayList<EmployeeAddress>();
        dummyAddressList.add(dummyAddress);
        dummyData.setAddresses(dummyAddressList);
        return dummyData;
    }

    public static Employee generateTestEmployee(ZonedDateTime dateToUse) {
        var dummyData = new Employee();
        dummyData.setFirstName("test");
        dummyData.setLastName("test");
        dummyData.setMiddleName("test");
        dummyData.setBirthDate(dateToUse);
        dummyData.setHireDate(dateToUse);
        dummyData.setGender("test");
        dummyData.setMaritalStatus("test");
        dummyData.setCurrentPosition("test");

        var dummyContact = new EmployeeContact();
        dummyContact.setContactInfo("test");
        dummyContact.setIsPrimary(false);
        var dummyContactList = new ArrayList<EmployeeContact>();
        dummyContactList.add(dummyContact);
        dummyData.setContacts(dummyContactList);

        var dummyAddress = new EmployeeAddress();
        dummyAddress.setAddress1("test");
        dummyAddress.setAddress2("test");
        dummyAddress.setIsPrimary(false);
        var dummyAddressList = new ArrayList<EmployeeAddress>();
        dummyAddressList.add(dummyAddress);
        dummyData.setAddresses(dummyAddressList);
        return dummyData;
    }

    public static Employee generateTestPersistedEmployee(ZonedDateTime dateToUse) {
        var dummy = generateTestEmployee(dateToUse);
        dummy.setEmployeeId(1);
        dummy.setCreatedOn(dateToUse);
        dummy.setUpdatedOn(dateToUse);
        dummy.setContacts(dummy.getContacts().stream().peek(c -> {
            c.setContactId(1);
            c.setIsPrimary(false);
            c.setCreatedOn(dateToUse);
            c.setUpdatedOn(dateToUse);
        }).collect(Collectors.toList()));
        dummy.setAddresses(dummy.getAddresses().stream().peek(c -> {
            c.setAddressId(1);
            c.setAddress1("test");
            c.setAddress2("test");
            c.setIsPrimary(false);
            c.setCreatedOn(dateToUse);
            c.setUpdatedOn(dateToUse);
        }).collect(Collectors.toList()));
        return dummy;
    }

    public static EmployeeDTO generateTestPersistedEmployeeDTO(ZonedDateTime dateToUse) {
        var dummy = generateTestEmployeeDTO(dateToUse);
        dummy.setEmployeeId(1);
        dummy.setContacts(dummy.getContacts().stream().peek(c -> {
            c.setContactId(1);
            c.setIsPrimary(false);
            c.setCreatedOn(dateToUse);
            c.setUpdatedOn(dateToUse);
        }).collect(Collectors.toList()));
        dummy.setAddresses(dummy.getAddresses().stream().peek(c -> {
            c.setAddressId(1);
            c.setAddress1("test");
            c.setAddress2("test");
            c.setIsPrimary(false);
            c.setCreatedOn(dateToUse);
            c.setUpdatedOn(dateToUse);
        }).collect(Collectors.toList()));
        return dummy;
    }
}
