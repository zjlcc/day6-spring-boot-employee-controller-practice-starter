package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1, "a", 20, Gender.MALE, 5000.0));
        employees.add(new Employee(2, "b", 20, Gender.MALE, 5000.0));
        employees.add(new Employee(3, "c", 20, Gender.FEMALE, 5000.0));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Employee> getAllByGender(Gender gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .toList();
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        Employee beUpdateEmployee = getById(id);
        beUpdateEmployee.setAge(employee.getAge());
        beUpdateEmployee.setSalary(employee.getSalary());
        return beUpdateEmployee;
    }
}
