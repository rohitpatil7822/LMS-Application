package com.example.lms.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lms.entity.Exam;
import com.example.lms.entity.Student;
import com.example.lms.entity.Subject;
import com.example.lms.exceptions.FieldsCannotBeNullException;
import com.example.lms.exceptions.ResourceAlreadyExistsException;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.repository.ExamRepository;
import com.example.lms.repository.StudentRepository;
import com.example.lms.repository.SubjectRepository;
import com.example.lms.service.ExamService;


@Service
public class ExamServiceImp implements ExamService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamRepository examRepository;

    @Override
    public Exam createExamForSubject(Long subjectId, Exam exam) {

        if (subjectId == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Subject Id cannot be empty or null");
        }

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Subject not found with id: " + subjectId));

        Optional<Exam> existingExam = examRepository.findBySubject_SubjectId(subjectId);
        
        if (existingExam.isPresent()) {
            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT, "An exam for this subject already exists");
        }

        exam.setSubject(subject);

        return examRepository.save(exam);
    }

    @Override
    public Exam getExam(Long exam_id) {

        Exam exam = examRepository.findById(exam_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Exam not found with id: " + exam_id));

        return exam;
    }

    @Override
    public Exam updateExam(Long exam_id, Exam exam) {

        if (exam_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Exam Id cannot be empty or null");
        }

        Exam existingExam = examRepository.findById(exam_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Exam not found with id: " + exam_id));

        existingExam.setSubject(exam.getSubject());

        return examRepository.save(existingExam);
    }

    @Override
    public String deleteExam(Long exam_id) {

        if (exam_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Exam Id cannot be empty or null");
        }

        Exam existingExam = examRepository.findById(exam_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Exam not found with id: " + exam_id));

        examRepository.delete(existingExam);

        return "Exam with id ->  " +exam_id+" successfully deleted";
    }

    @Override
    @Transactional
    public Exam unenrollStudentFromExam(Long examId, Long studentId) {
        Exam exam = getExam(examId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Student not found"));

        if (exam.getEnrolled_students().contains(student)) {
            exam.getEnrolled_students().remove(student);
            student.getRegistered_exams().remove(exam);
            studentRepository.save(student);
        }

        return examRepository.save(exam);
    }

    @Override
    public List<Student> getEnrolledStudents(Long examId) {
        Exam exam = getExam(examId);
        return exam.getEnrolled_students();
    }

    @Override
    public int getEnrolledStudentCount(Long examId) {
        Exam exam = getExam(examId);
        return exam.getEnrolled_students().size();
    }
    
}
