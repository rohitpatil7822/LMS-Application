package com.example.lms.service;
import com.example.lms.entity.Subject;

public interface SubjectService {


    public Subject createSubject(Subject subject);

    public Subject getSubject(Long subject_id);

    public Subject updateSubject(Long subject_id , Subject subject);

    public String deleteSubject(Long subject_id);

}
