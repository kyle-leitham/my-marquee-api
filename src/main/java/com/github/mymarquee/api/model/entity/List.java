package com.github.mymarquee.api.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="lists")
public class List {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
    @OneToMany(mappedBy="relatedList", orphanRemoval=true, cascade=CascadeType.ALL)
    private Set<ListMovie> movies;
	
	protected List() {}
	
	public List(String name) {
		this.name = name;
	}
	  
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<ListMovie> getMovies(){
		return this.movies;
	}
}
