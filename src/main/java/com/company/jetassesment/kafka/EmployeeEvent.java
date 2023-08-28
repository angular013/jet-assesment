package com.company.jetassesment.kafka;

import com.company.jetassesment.model.Employee;

public class EmployeeEvent {
    private String action; // "CREATE", "UPDATE", or "DELETE"
    private Employee employee;

    // Constructors, getters, setters
    public EmployeeEvent() {

    }
    public EmployeeEvent(String action, Employee employee) {
        this.action = action;
        this.employee = employee;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}