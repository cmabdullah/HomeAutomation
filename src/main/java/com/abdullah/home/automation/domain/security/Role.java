package com.abdullah.home.automation.domain.security;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
	
	@Id
	private int roleId;
	private String name;
	
	@OneToMany(mappedBy = "role", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<UserRole> userRole = new HashSet<>();
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", userRole=" + userRole + "]";
	}
	
	

}
