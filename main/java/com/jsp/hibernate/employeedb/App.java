package com.jsp.hibernate.employeedb;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jsp.hibernate.employeedb.Entity.Employee;
import com.jsp.hibernate.employeedb.dao.EmployeeDao;


public class App 
{
	
	public static void main(String[] args) {
		EmployeeDao edo= new EmployeeDao();
		edo.addEmployee();
		//edo.findEmployeeById();
		//edo.deleteEmployeeById();
	   //edo.updateEmployeeDescAndSalaryById();
	}
    
    	}
