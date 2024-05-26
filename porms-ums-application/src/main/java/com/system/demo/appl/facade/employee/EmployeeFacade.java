/**
 * The com.system.demo.appl.facade.employee package contains interfaces and classes
 * related to the employee facade.
 */
package com.system.demo.appl.facade.employee;

import com.system.demo.appl.model.employee.Employee;

import java.util.List;

/**
 * The EmployeeFacade interface defines methods for managing employee data.
 */
public interface EmployeeFacade {

    /**
     * Retrieves all employees from the database.
     *
     * @return A list of all employees.
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves an employee from the database using their ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The employee with the specified ID, or null if not found.
     */
    Employee getEmployeeById(String id);

    /**
     * Retrieves an employee from the database using their ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The employee with the specified ID, or null if not found.
     */
    Employee getById(int id);

    /**
     * Adds an employee to the database.
     *
     * @param employee The employee to add.
     * @return True if the addition of the employee is successful, false otherwise.
     */
    boolean addEmployee(Employee employee);

    /**
     * Updates an employee's information in the database.
     *
     * @param employee The employee to update.
     * @return True if the update of the employee's information is successful, false otherwise.
     */
    boolean updateEmployee(Employee employee);
}
