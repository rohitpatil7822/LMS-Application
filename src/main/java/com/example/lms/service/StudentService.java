package com.example.lms.service;

import com.example.lms.entity.Student;

public interface StudentService {

    public Student createStudent(Student student);

    public Student getStudent(Long student_id);

    public Student updateStudent(Long student_id , Student student);

    public String deleteStudent(Long student_id);

    public Student enrolledInSubject(Long student_id , Long subject_id);

    public Student registerInExam(Long student_id , Long exam_id);
}
