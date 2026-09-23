package it.its.ms_exam.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.its.ms_exam.domain.TestEntity;
import it.its.ms_exam.repository.TestRepository;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final TestRepository repository;

    @GetMapping("/{id}")
    public TestEntity getMethodName(@PathVariable Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping
    public TestEntity save(@RequestBody String name) {
        TestEntity entity = new TestEntity();
        entity.setName(name);
        repository.save(entity);
        return entity;
    }
    
    

}
