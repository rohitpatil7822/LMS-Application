package com.example.lms.service.implementation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.lms.entity.Exam;
import com.example.lms.entity.Student;
import com.example.lms.entity.Subject;
import com.example.lms.exceptions.FieldsCannotBeNullException;
import com.example.lms.exceptions.ResourceAlreadyExistsException;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.repository.ExamRepository;
import com.example.lms.repository.StudentRepository;
import com.example.lms.repository.SubjectRepository;
import com.example.lms.service.StudentService;


@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Override
    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long student_id) {

        Student student = studentRepository.findById(student_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found with id: " + student_id));
        student.setEnrolled_subjects(new ArrayList<>());
        student.setRegistered_exams(new ArrayList<>());
        return student;
    }

    @Override
    public Student updateStudent(Long student_id, Student student) {

        if (student_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Student Id cannot be empty or null");
        }

        Student existingStudent = studentRepository.findById(student_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found with id: " + student_id));

        existingStudent.setStudent_name(student.getStudent_name());
        
        return studentRepository.save(existingStudent);
    }

    @Override
    public String deleteStudent(Long student_id) {

        if (student_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Student Id cannot be empty or null");
        }

        Student existingStudent = studentRepository.findById(student_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found with id: " + student_id));

        studentRepository.delete(existingStudent);
        return "Student with id ->  " +student_id+" successfully deleted";
    }

    @Override
    public Student enrolledInSubject(Long student_id, Long subject_id) {

        if (student_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Student Id cannot be empty or null");
        }

        if (subject_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Subject Id cannot be empty or null");
        }

        Subject subject = subjectRepository.findById(subject_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Subject not found with id: " + subject_id));


        Student existingStudent = studentRepository.findById(student_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found with id: " + student_id));

        if (!subject.getRegistered_students().contains(existingStudent)) {
            
            subject.getRegistered_students().add(existingStudent);

            subjectRepository.save(subject);

        }else{

            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT,"Student is already enrolled for this subject");
        }

        existingStudent.getEnrolled_subjects().add(subject);

        return studentRepository.save(existingStudent);
    }

    @Override
    public Student registerInExam(Long student_id, Long exam_id) {

        if (student_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Student Id cannot be empty or null");
        }

        if (exam_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Exam Id cannot be empty or null");
        }


        Exam exam = examRepository.findById(exam_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Exam not found with id: " + exam_id));

        Student existingStudent = studentRepository.findById(student_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found with id: " + student_id));

        if (!existingStudent.getEnrolled_subjects().contains(exam.getSubject())) {
            
            throw new IllegalStateException("Student must be enrolled in the subject to register for the exam");
        }

        if (!exam.getEnrolled_students().contains(existingStudent)) {
            
            exam.getEnrolled_students().add(existingStudent);
            examRepository.save(exam);

        }else{

            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT,"Student is already registered for this exam");

        }

        existingStudent.getRegistered_exams().add(exam);
        return studentRepository.save(existingStudent);
    }


}
