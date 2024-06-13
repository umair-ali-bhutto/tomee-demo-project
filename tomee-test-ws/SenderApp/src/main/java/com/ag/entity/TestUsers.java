package com.ag.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TEST_USERS")
@NamedQueries({
	@NamedQuery(name = "TestUsers.findAll", query = "SELECT t FROM TestUsers t"),
    @NamedQuery(name = "TestUsers.findById", query = "SELECT u FROM TestUsers u WHERE u.id = :id")
})

public class TestUsers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
