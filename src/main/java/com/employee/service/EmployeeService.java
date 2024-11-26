package com.employee.service;

import com.employee.model.Employees;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<Employees> getAllEmployees();
    public Optional<Employees> getEmployeeById(int id);
    public Employees createEmployee(Employees employees);
    public Employees updateEmployee(int id, Employees employeeDetails);
    public void deleteEmployee(int id);

}
