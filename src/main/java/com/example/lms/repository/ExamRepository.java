package com.example.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam,Long> {

    Optional<Exam> findBySubject_SubjectId(Long subjectId);
}
