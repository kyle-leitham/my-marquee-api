package com.github.mymarquee.api.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.mymarquee.api.model.entity.List;

public interface ListRepository extends CrudRepository<List, Long> {}
