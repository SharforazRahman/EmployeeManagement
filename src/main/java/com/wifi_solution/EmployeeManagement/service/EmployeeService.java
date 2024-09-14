package com.wifi_solution.EmployeeManagement.service;

import com.wifi_solution.EmployeeManagement.constant.EmployeeConstant;
import com.wifi_solution.EmployeeManagement.model.Employee;
import com.wifi_solution.EmployeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> searchEmployees(String searchTerm, Pageable pageable) {
        return employeeRepository.findByFullNameContainingOrEmailContaining(searchTerm, searchTerm, pageable);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setFullName(employeeDetails.getFullName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setMobile(employeeDetails.getMobile());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployeesSortedBy(String field) {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Employee> getEmployeesWithPagination(int offset, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Employee> getEmployeesWithPaginationAndSorting(int offset, int pageSize, String field, String direction) {
        Sort.Direction selectedDirection = Sort.Direction.ASC;
        if (direction.equalsIgnoreCase(EmployeeConstant.DESC))
            selectedDirection = Sort.Direction.DESC;
        return employeeRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(selectedDirection, field)));
    }

    public List<Employee> searchEmployees(String name, String dateOfBirth, String email, String mobile) {
        // Handle empty strings
        LocalDate dateOfBirthParsed = null;
        if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
            dateOfBirthParsed = LocalDate.parse(dateOfBirth);
        }
        return employeeRepository.findEmployeesByCriteria(name, dateOfBirthParsed, email, mobile);
    }
}