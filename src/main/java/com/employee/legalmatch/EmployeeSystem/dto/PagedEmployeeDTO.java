package com.employee.legalmatch.EmployeeSystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class PagedEmployeeDTO {
    private Long totalCount;
    private List<EmployeeDTO> employees;
}
