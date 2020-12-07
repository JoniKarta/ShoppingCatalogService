package com.example.demo.data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Need to check: added @Entity, @Table and @Id here
@Entity
@Table(name = "CATEGORIES")
@Embeddable
public class CategoryEntity {
	private String name;
	private String description;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
