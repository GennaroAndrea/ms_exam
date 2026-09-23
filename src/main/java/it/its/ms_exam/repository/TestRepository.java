package it.its.ms_exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import it.its.ms_exam.domain.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

    public Optional<TestEntity> findById(Long id);

}
