package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.Subject;
import mk.ukim.finki.izborzvanje.model.exceptions.SubjectNotFoundException;
import mk.ukim.finki.izborzvanje.repository.SubjectRepository;
import mk.ukim.finki.izborzvanje.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> list = subjectRepository.findAll();
        Collections.sort(list, Comparator.comparing(Subject::getName));
        return list;
    }

    @Override
    public Subject getSubjectById(String mainSubjectId) {
        return subjectRepository.findById(mainSubjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Main subject not found with id: " + mainSubjectId));
    }

}