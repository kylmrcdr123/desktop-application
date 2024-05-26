/**
 * The com.system.demo.appl.facade.employee.impl package contains implementations
 * of facades for managing employee-related operations.
 */
package com.system.demo.appl.facade.employee.impl;

import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.model.employee.Employee;
import com.system.demo.data.employee.dao.EmployeeDao;

import java.util.List;

/**
 * The EmployeeFacadeImpl class is an implementation of the EmployeeFacade interface.
 * It provides methods for managing employee data.
 */
public class EmployeeFacadeImpl implements EmployeeFacade {

    private EmployeeDao employeeDao;

    /**
     * Constructs a new EmployeeFacadeImpl with the provided EmployeeDao.
     *
     * @param employeeDao The data access object for managing employee data.
     */
    public EmployeeFacadeImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }


    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }


    @Override
    public Employee getById(int id) {
        return employeeDao.getById(id);
    }


    @Override
    public boolean addEmployee(Employee employee) throws RuntimeException {
        boolean result = false;
        try {
            Employee targetEmployee = getEmployeeById(employee.getEmployeeNo());
            if (targetEmployee != null) {
                throw new Exception("Employee to add already exists.");
            }
            result = employeeDao.addEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    @Override
    public boolean updateEmployee(Employee employee) throws RuntimeException {
        try {
            Employee existingEmployee = getEmployeeById(employee.getEmployeeNo());
            if (existingEmployee == null) {
                throw new RuntimeException("Employee to update does not exist.");
            }
            return employeeDao.updateEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }
}
