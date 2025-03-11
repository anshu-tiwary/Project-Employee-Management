package com.jsp.hibernate.employeedb.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Project {
 @Id
 private int projectId;
 private String title;
 private String techStack;
public int getProjectId() {
	return projectId;
}
public void setProjectId(int projectId) {
	this.projectId = projectId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getTechStack() {
	return techStack;
}
public void setTechStack(String techStack) {
	this.techStack = techStack;
}
 
 
}
