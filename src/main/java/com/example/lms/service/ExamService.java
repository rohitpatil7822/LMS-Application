package com.example.lms.service;

import java.util.List;

import com.example.lms.entity.Exam;
import com.example.lms.entity.Student;

public interface ExamService {

    public Exam getExam(Long exam_id);

    public Exam updateExam(Long exam_id , Exam exam);

    public String deleteExam(Long exam_id);

    public Exam createExamForSubject(Long subjectId, Exam exam);

    public List<Student> getEnrolledStudents(Long examId);

    public int getEnrolledStudentCount(Long examId);

    public Exam unenrollStudentFromExam(Long examId, Long studentId);
}
