package com.example.lms.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.lms.entity.Subject;
import com.example.lms.exceptions.FieldsCannotBeNullException;
import com.example.lms.exceptions.ResourceAlreadyExistsException;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.repository.SubjectRepository;
import com.example.lms.service.SubjectService;

@Service
public class SubjectServiceImp implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject createSubject(Subject subject) {

        Optional<Subject> existingSubject = subjectRepository.findBySubjectName(subject.getSubjectName());

        if (existingSubject.isPresent()) {
            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT, "Subject already exists");
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject getSubject(Long subject_id) {

        if (subject_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Subject Id cannot be empty or null");
        }

        Subject subject = subjectRepository.findById(subject_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Subject not found with id: " + subject_id));

        return subject;
    }

    @Override
    public Subject updateSubject(Long subject_id, Subject subject) {

        if (subject_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Subject Id cannot be empty or null");
        }

        Subject existingSubject = subjectRepository.findById(subject_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Subject not found with id: " + subject_id));

        Optional<Subject> existingSubjectWithName = subjectRepository.findBySubjectName(subject.getSubjectName());

        if (existingSubjectWithName.isPresent()) {
            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT, "Subject already exists");
        }

        existingSubject.setSubjectName(subject.getSubjectName());

        return subjectRepository.save(existingSubject);
    }

    @Override
    public String deleteSubject(Long subject_id) {

        if (subject_id == null) {
            throw  new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, "Subject Id cannot be empty or null");
        }

        Subject existingSubject = subjectRepository.findById(subject_id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Subject not found with id: " + subject_id));

        subjectRepository.delete(existingSubject);

        return "Subject with id ->  " +subject_id+" successfully deleted";
    }
}
