package com.jsp.hibernate.employeedb.dao;


import java.util.List;
import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.jsp.hibernate.employeedb.Entity.Employee;

public class EmployeeDao {

	
		Configuration cfg= new Configuration().configure().addAnnotatedClass(Employee.class);
		SessionFactory sf= cfg.buildSessionFactory();
		Scanner  sc = new Scanner(System.in);
	
		public void addEmployee() {
		Employee employee= new Employee();
		System.out.println(" enter the Id");
		employee.setEmployeeId(sc.nextInt());
		
		System.out.println(" enter the Name");
		employee.setEmployeeName(sc.next());
		
		System.out.println(" enter the Email");
		employee.setEmail(sc.next());
		
		System.out.println(" enter the Designation "); 
		employee.setDesignation(sc.next());
		
		System.out.println(" enter the salary");
		employee.setSalary(sc.nextInt());
		
		Session session=sf.openSession();
		Transaction tr= session.beginTransaction();
		
		session.save(employee);
		tr.commit();
		session.close();
		System.out.println("data inserted successfully");
	
		}	
		
		public void findEmployeeById() {
			Session session=sf.openSession();
			Transaction tr= session.beginTransaction();
			
			
			System.out.println(" enter the Id");
			
			Employee employee= session.get(Employee.class , sc.nextInt());
			
			if(employee!=null) {
				System.out.println(employee);
			}else {
				System.out.println("employee  not found by id ");
			}
			tr.commit();
			session.close();
			
		}
		
		public void updateEmployeeDescAndSalaryById() {
			Session session=sf.openSession();
			Transaction tr= session.beginTransaction();
			System.out.println("enter  employeee id");
			Employee employee= session.get(Employee.class , sc.nextInt());
			
			if(employee!=null) {
				System.out.println("enter  update Designation");
				employee.setDesignation(sc.next());
				System.out.println("enter the update salary");
				employee.setSalary(sc.nextInt());
				
				session.update(employee);
				
				
				tr.commit();
				session.close();
				System.out.println("data update sucessfully");
				
				
				
			}else {
				System.out.println("employee not found by id");
			}
			
			
			
		}
		public void deleteEmployeeById() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Employee ID");
		    int employeeId = sc.nextInt(); // Store ID in a variable for reuse
		    Employee employee = session.get(Employee.class, employeeId);

		    if (employee != null) {
		        session.delete(employee);
		        System.out.println("Employee successfully deleted");
		    } else {
		        System.out.println("Employee not found by ID");
		    }

		    tr.commit();
		    session.close();
		}
		// using Hql
		public void findEmployeeByName() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Employee Name");
		    String name = sc.next();

		    String hql = "FROM Employee WHERE employeeName = :name";
		    List<Employee> employees = session.createQuery(hql)
		                                .setParameter("name", name)
		                                .list();

		    if(!employees.isEmpty()) {
		        for(Employee employee : employees) {
		            System.out.println(employee);
		        }
		    } else {
		        System.out.println("Employee not found by Name");
		    }

		    tr.commit();
		    session.close();
		}
		
		 // using Creteria Builder 
		public void findEmployeeByEmail() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Employee Email");
		    String email = sc.next();

		    // Step 1: Create CriteriaBuilder
		    CriteriaBuilder cb = session.getCriteriaBuilder();

		    // Step 2: Create CriteriaQuery
		    CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

		    // Step 3: Define Root for the Query
		    Root<Employee> root = cq.from(Employee.class);

		    // Step 4: Add Predicate (Condition)
		    cq.select(root).where(cb.equal(root.get("email"), email));

		    // Step 5: Execute Query
		    Employee employee = session.createQuery(cq).uniqueResult();

		    if(employee != null) {
		        System.out.println(employee);
		    } else {
		        System.out.println("Employee not found by Email");
		    }

		    tr.commit();
		    session.close();
		}

		// using Hql
		
		public void findEmployeesExperienceGreaterThan() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Minimum Experience in Years");
		    int minExperience = sc.nextInt();

		    String hql = "FROM Employee WHERE experience > :minExperience";
		    List<Employee> employees = session.createQuery(hql)
		                                .setParameter("minExperience", minExperience)
		                                .list();

		    if(!employees.isEmpty()) {
		        for(Employee employee : employees) {
		            System.out.println(employee);
		        }
		    } else {
		        System.out.println("No employees found with experience greater than " + minExperience + " years.");
		    }

		    tr.commit();
		    session.close();
		}
		
    
		public void findEmployeesBetweenSalary() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Minimum Salary");
		    int minSalary = sc.nextInt();

		    System.out.println("Enter Maximum Salary");
		    int maxSalary = sc.nextInt();

		    // Step 1: Create CriteriaBuilder
		    CriteriaBuilder cb = session.getCriteriaBuilder();

		    // Step 2: Create CriteriaQuery
		    CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

		    // Step 3: Define Root for the Query
		    Root<Employee> root = cq.from(Employee.class);

		    // Step 4: Add Predicate for Salary Between Range
		    Predicate salaryBetween = cb.between(root.get("salary"), minSalary, maxSalary);
		    cq.select(root).where(salaryBetween);

		    // Step 5: Execute Query
		    List<Employee> employees = session.createQuery(cq).getResultList();

		    if (!employees.isEmpty()) {
		        for (Employee employee : employees) {
		            System.out.println(employee);
		        }
		    } else {
		        System.out.println("No employees found with salary between " + minSalary + " and " + maxSalary);
		    }

		    tr.commit();
		    session.close();
		}

		
		public void findEmployeesByProjectId() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Project ID");
		    int projectId = sc.nextInt();

		    // Step 1: Write HQL Query
		    String hql = "SELECT e FROM Employee e JOIN e.project p WHERE p.projectId = :projectId";
		    
		    // Step 2: Execute HQL Query
		    List<Employee> employees = session.createQuery(hql, Employee.class)
		                                      .setParameter("projectId", projectId)
		                                      .getResultList();

		    // Step 3: Display Result
		    if (!employees.isEmpty()) {
		        for (Employee employee : employees) {
		            System.out.println(employee);
		        }
		    } else {
		        System.out.println("No employees found for Project ID: " + projectId);
		    }

		    tr.commit();
		    session.close();
		}

		public void updateEmployeesSalaryByProjectTitle() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Project Title");
		    String projectTitle = sc.next();
		    
		    System.out.println("Enter New Salary");
		    int newSalary = sc.nextInt();

		    // Step 1: Create CriteriaBuilder
		    CriteriaBuilder cb = session.getCriteriaBuilder();

		    // Step 2: Create CriteriaUpdate
		    CriteriaUpdate<Employee> cu = cb.createCriteriaUpdate(Employee.class);

		    // Step 3: Define Root for the Query
		    Root<Employee> root = cu.from(Employee.class);

		    // Step 4: Add Predicate for Project Title
		    Predicate projectPredicate = cb.equal(root.get("project").get("projectTitle"), projectTitle);

		    // Step 5: Set the New Salary
		    cu.set(root.get("salary"), newSalary).where(projectPredicate);

		    // Step 6: Execute the Update Query
		    int updatedRows = session.createQuery(cu).executeUpdate();

		    if (updatedRows > 0) {
		        System.out.println("Salary updated successfully for " + updatedRows + " employees.");
		    } else {
		        System.out.println("No employees found for Project Title: " + projectTitle);
		    }

		    tr.commit();
		    session.close();
		}
		public void updateEmployeeExperienceByEmail() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Employee Email:");
		    String email = sc.next();

		    System.out.println("Enter Updated Experience (in years):");
		    int newExperience = sc.nextInt();

		    // ✅ Step 1: Write the HQL Query
		    String hql = "UPDATE Employee e SET e.experience = :exp WHERE e.email = :email";

		    // ✅ Step 2: Create Query Object
		    Query query = session.createQuery(hql);

		    // ✅ Step 3: Set Parameters
		    query.setParameter("exp", newExperience);
		    query.setParameter("email", email);

		    // ✅ Step 4: Execute Update Query
		    int updatedRows = query.executeUpdate();

		    if (updatedRows > 0) {
		        System.out.println("Employee's experience updated successfully.");
		    } else {
		        System.out.println("Employee not found with Email: " + email);
		    }

		    tr.commit();
		    session.close();
		}
		
		// By using Hql
		
		public void deleteEmployeesBySalaryGreaterThan() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Salary Amount:");
		    int salary = sc.nextInt();

		    // ✅ Step 1: Write the HQL Query
		    String hql = "DELETE FROM Employee e WHERE e.salary > :salary";

		    // ✅ Step 2: Create Query Object
		    Query query = session.createQuery(hql);

		    // ✅ Step 3: Set Parameter
		    query.setParameter("salary", salary);

		    // ✅ Step 4: Execute Update Query
		    int deletedRows = query.executeUpdate();

		    if (deletedRows > 0) {
		        System.out.println(deletedRows + " Employees with salary greater than " + salary + " have been deleted.");
		    } else {
		        System.out.println("No employees found with salary greater than " + salary);
		    }

		    tr.commit();
		    session.close();
		}
		
		// using creteria Builder
		public void deleteEmployeeByProjectTitle() {
		    Session session = sf.openSession();
		    Transaction tr = session.beginTransaction();

		    System.out.println("Enter Project Title:");
		    String projectTitle = sc.next();

		    // ✅ Step 1: Create CriteriaBuilder
		    CriteriaBuilder cb = session.getCriteriaBuilder();

		    // ✅ Step 2: Create CriteriaDelete for Employee
		    CriteriaDelete<Employee> delete = cb.createCriteriaDelete(Employee.class);

		    // ✅ Step 3: Define the Root Entity
		    Root<Employee> root = delete.from(Employee.class);

		    // ✅ Step 4: Apply Condition (WHERE Clause)
		    delete.where(cb.equal(root.get("project").get("title"), projectTitle));

		    // ✅ Step 5: Execute the Delete Query
		    int deletedRows = session.createQuery(delete).executeUpdate();

		    if (deletedRows > 0) {
		        System.out.println(deletedRows + " Employee(s) working on project titled '" + projectTitle + "' have been deleted.");
		    } else {
		        System.out.println("No employees found working on project titled '" + projectTitle + "'.");
		    }

		    tr.commit();
		    session.close();
		}
}
	
	
	
	



