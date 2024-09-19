package com.example.lms.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.lms.entity.Exam;
import com.example.lms.entity.Student;
import com.example.lms.entity.Subject;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.repository.ExamRepository;
import com.example.lms.repository.StudentRepository;
import com.example.lms.repository.SubjectRepository;

public class ExamServiceImpTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createExamForSubject()
    @Test
    public void testCreateExamForSubject_Success() {
        Subject subject = new Subject();
        subject.setSubjectId(1L);
        subject.setSubjectName("Math");

        Exam exam = new Exam();
        exam.setExam_id(1L);

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(examRepository.findBySubject_SubjectId(1L)).thenReturn(Optional.empty());
        when(examRepository.save(exam)).thenReturn(exam);

        Exam createdExam = examService.createExamForSubject(1L, exam);

        assertNotNull(createdExam);
        assertEquals(1L, createdExam.getExam_id());
        assertEquals(subject, createdExam.getSubject());

        verify(examRepository, times(1)).save(exam);
    }

    // Test for getExam()
    @Test
    public void testGetExam_Success() {
        Exam exam = new Exam();
        exam.setExam_id(1L);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        Exam fetchedExam = examService.getExam(1L);

        assertNotNull(fetchedExam);
        assertEquals(1L, fetchedExam.getExam_id());

        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExam_ThrowsResourceNotFoundException() {
        when(examRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            examService.getExam(1L);
        });

        assertEquals("Exam not found with id: 1", exception.getMessage());

        verify(examRepository, times(1)).findById(1L);
    }

    // Test for unenrollStudentFromExam()
    @Test
    public void testUnenrollStudentFromExam_Success() {
        Exam exam = new Exam();
        exam.setExam_id(1L);
        Student student = new Student();
        student.setStudent_id(1L);
        List<Student> enrolledStudents = new ArrayList<>();
        enrolledStudents.add(student);
        exam.setEnrolled_students(enrolledStudents);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(examRepository.save(exam)).thenReturn(exam);

        Exam updatedExam = examService.unenrollStudentFromExam(1L, 1L);

        assertNotNull(updatedExam);
        assertTrue(updatedExam.getEnrolled_students().isEmpty());

        verify(examRepository, times(1)).save(exam);
        verify(studentRepository, times(1)).save(student);
    }
}

