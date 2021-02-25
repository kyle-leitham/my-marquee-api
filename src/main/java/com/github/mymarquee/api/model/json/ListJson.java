package com.github.mymarquee.api.model.json;

import org.springframework.hateoas.RepresentationModel;

import com.github.mymarquee.api.model.entity.MyMarqueeList;

public class ListJson extends RepresentationModel<ListJson> {
	private Long id;
	private String name;
	
	protected ListJson() {}
	
	public ListJson(MyMarqueeList list) {
		this.id = list.getId();
		this.name = list.getName();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
