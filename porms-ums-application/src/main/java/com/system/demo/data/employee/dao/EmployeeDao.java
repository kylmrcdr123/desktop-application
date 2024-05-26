package com.system.demo.data.employee.dao;

import com.system.demo.appl.model.employee.Employee;

import java.util.List;

public interface EmployeeDao {

    /**
     * Retrieves all employees.
     *
     * @return a List of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves an employee by ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the Employee object to the given ID, or null if not found
     */
    Employee getEmployeeById(String id);


    Employee getById(int id);

    /**
     * Adds a new employee.
     *
     * @param employee the Employee object to add
     * @return true if the employee is added, false if not
     */
    boolean addEmployee(Employee employee);

    /**
     * Updates an employee.
     *
     * @param employee the Employee object to update
     * @return true if the employee is updated, false if not
     */
    boolean updateEmployee(Employee employee);
}

