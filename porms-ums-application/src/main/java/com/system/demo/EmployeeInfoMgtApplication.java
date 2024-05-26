package com.system.demo;

import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.facade.employee.impl.EmployeeFacadeImpl;
import com.system.demo.data.employee.dao.EmployeeDao;
import com.system.demo.data.employee.dao.impl.EmployeeDaoImpl;

public class EmployeeInfoMgtApplication {
    private EmployeeFacade employeeFacade;
    /**
     * This creates a new EmployeeInfoMgtApplication
     * @return the employeeFacade this helps for managing employee data.
     */
    public EmployeeInfoMgtApplication() {
        EmployeeDao employeeDaoImpl = new EmployeeDaoImpl();
        this.employeeFacade = new EmployeeFacadeImpl(employeeDaoImpl);
    }
    /**
     * This gets the Employee Facade.
     * @return the employee facade.
     */
    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }
}