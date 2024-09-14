package com.wifi_solution.EmployeeManagement.controller;

import com.wifi_solution.EmployeeManagement.helper.EmployeeHelper;
import com.wifi_solution.EmployeeManagement.model.Employee;
import com.wifi_solution.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeHelper employeeHelper;

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployee(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile) {
        List<Employee> employees = employeeService.searchEmployees(name, dateOfBirth, email, mobile);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<String> addEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("photo") MultipartFile photo) {

        Employee employee = new Employee();
        employee.setFullName(firstName + " " + lastName);
        employee.setEmail(email);
        employee.setMobile(mobile);
        employee.setDateOfBirth(LocalDate.parse(dateOfBirth));

        try {
            if (!photo.isEmpty()) {
                // TO DO
            }
            employeeService.saveEmployee(employee);
            return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error saving employee", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/sort/{field}")
    public List<Employee> getEmployeeSortBy(@PathVariable String field) {
        return employeeService.getAllEmployeesSortedBy(field);
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public Page<Employee> getEmployeeWithPagination(@PathVariable int offset,
                                                    @PathVariable int pageSize) {
        return employeeService.getEmployeesWithPagination(offset, pageSize);
    }

    @GetMapping("/pagination-and-sort/{offset}/{pageSize}/{field}/{direction}")
    public Page<Employee> getEmployeeWithPaginationAndSort(@PathVariable int offset,
                                                           @PathVariable int pageSize,
                                                           @PathVariable String field,
                                                           @PathVariable String direction) {
        return employeeService.getEmployeesWithPaginationAndSorting(offset, pageSize, field, direction);
    }
}
