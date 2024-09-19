package com.example.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Long> {

    Optional<Subject> findBySubjectName(String subjectName);
}
