package mk.ukim.finki.izborzvanje.service;


import mk.ukim.finki.izborzvanje.model.Professor;
import mk.ukim.finki.izborzvanje.model.exceptions.ProfessorNotFoundException;

import java.util.List;

public interface ProfessorService {


    List<Professor> getAllProfessors();

    Professor getProfessorById(String id) throws ProfessorNotFoundException;
}
