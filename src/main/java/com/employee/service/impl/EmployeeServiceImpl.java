package com.employee.service.impl;

import com.employee.model.Employees;
import com.employee.repository.EmployeesRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeesRepository employeeRepository;

    @Override
    public Employees createEmployee(Employees employee) {
        Employees emp = new Employees();
        emp.setName(employee.getName());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employees> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employees updateEmployee(int id, Employees updatedEmployee) {
        if (employeeRepository.existsById(id)) {
            updatedEmployee.setId(id);
            return employeeRepository.save(updatedEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
