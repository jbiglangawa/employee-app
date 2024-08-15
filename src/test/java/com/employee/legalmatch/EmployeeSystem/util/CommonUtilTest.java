package com.employee.legalmatch.EmployeeSystem.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CommonUtilTest {

    @Test
    void testStringNotNullOrEmpty() {
        String given = "test";

        boolean result = CommonUtil.isNotNullOrEmpty(given);

        assertTrue(result);
    }

    @Test
    void testStringNull() {
        String given = null;

        boolean result = CommonUtil.isNotNullOrEmpty(given);

        assertFalse(result);
    }

    @Test
    void testStringEmpty() {
        String given = "";

        boolean result = CommonUtil.isNotNullOrEmpty(given);

        assertFalse(result);
    }

}
