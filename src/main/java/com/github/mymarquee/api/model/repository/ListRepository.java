package com.github.mymarquee.api.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.mymarquee.api.model.entity.MyMarqueeList;

public interface ListRepository extends CrudRepository<MyMarqueeList, Long> {}
