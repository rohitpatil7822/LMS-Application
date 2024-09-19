package com.example.lms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms.entity.Exam;
import com.example.lms.entity.Student;
import com.example.lms.service.ExamService;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamService examService;
    
    @PostMapping("/subjects/{subjectId}")
    public ResponseEntity<Exam> createExamForSubject(@PathVariable Long subjectId, @RequestBody Exam exam) {
        return ResponseEntity.ok(examService.createExamForSubject(subjectId, exam));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExam(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExam(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam examDetails) {
        return ResponseEntity.ok(examService.updateExam(id, examDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        
        return ResponseEntity.ok(examService.deleteExam(id));
    }


    @DeleteMapping("/{examId}/unenroll/{studentId}")
    public ResponseEntity<Exam> unenrollStudentFromExam(@PathVariable Long examId, @PathVariable Long studentId) {
        return ResponseEntity.ok(examService.unenrollStudentFromExam(examId, studentId));
    }

    @GetMapping("/{examId}/enrolled-students")
    public ResponseEntity<List<Student>> getEnrolledStudents(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getEnrolledStudents(examId));
    }

    @GetMapping("/{examId}/enrolled-student-count")
    public ResponseEntity<Integer> getEnrolledStudentCount(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getEnrolledStudentCount(examId));
    }
}

