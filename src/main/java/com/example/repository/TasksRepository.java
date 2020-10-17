package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Task;

public interface TasksRepository extends CrudRepository<Task, Long> {}

