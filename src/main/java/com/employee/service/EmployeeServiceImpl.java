package com.employee.service;

import com.employee.model.Employees;
import com.employee.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeesRepository employeeRepository;

    @Override
    public Employees createEmployee(Employees employee) {
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
