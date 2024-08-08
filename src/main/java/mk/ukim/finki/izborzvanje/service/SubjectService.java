package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.Subject;
import mk.ukim.finki.izborzvanje.model.exceptions.SubjectNotFoundException;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectById(String mainSubjectId) throws SubjectNotFoundException;

}
