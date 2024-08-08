package mk.ukim.finki.izborzvanje.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.NICriteria.Mentoring;
import mk.ukim.finki.izborzvanje.model.NICriteria.Projects;
import mk.ukim.finki.izborzvanje.model.NICriteria.Publications;
import mk.ukim.finki.izborzvanje.model.Student;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.service.CriteriaService;
import mk.ukim.finki.izborzvanje.service.NI.MentoringService;
import mk.ukim.finki.izborzvanje.service.NI.ProjectsService;
import mk.ukim.finki.izborzvanje.service.NI.PublicationsService;
import mk.ukim.finki.izborzvanje.service.StudentService;
import mk.ukim.finki.izborzvanje.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Criteria/NI")
public class NICriteriaController {
    private final CriteriaService criteriaService;
    private final UserService userService;
    private final MentoringService mentoringService;
    private final PublicationsService publicationsService;
    private final ProjectsService projectsService;

    private final StudentService studentService;

    public NICriteriaController(CriteriaService criteriaService, UserService userService,
                                MentoringService mentoringService, PublicationsService publicationsService,
                                ProjectsService projectsService, StudentService studentService) {
        this.criteriaService = criteriaService;
        this.userService = userService;
        this.mentoringService = mentoringService;
        this.publicationsService = publicationsService;
        this.projectsService = projectsService;
        this.studentService = studentService;
    }

    @GetMapping()
    public String showNICriteria(HttpServletRequest req, @RequestParam(required = false) Long criteriaId, @RequestParam(required = false) Long niCriteriaId, Model model) {
        List<Criteria> criteriaList = criteriaService.listAllNICriteria();
        model.addAttribute("criteriaList", criteriaList);
        model.addAttribute("field", "NI");
        model.addAttribute("specifics", "/ni-criteria/ni-specifics.html");
        if (criteriaId != null) {
            model.addAttribute("criteria", this.criteriaService.findById(criteriaId));
            model.addAttribute("showDetails", true);

        }

        String username = req.getRemoteUser();
        User user = userService.findById(username);
        if (criteriaId != null) {
            if(niCriteriaId != null)
            {
                model.addAttribute("niCriteriaId", niCriteriaId);
            }
            if (criteriaId >= 31  && criteriaId <= 34) {
                Long nid = mentoringService.findCriteriaID(criteriaId).getId();
                if (mentoringService.findByCriteriaIdAndUser(criteriaId, user) != null) {
                    List<Mentoring> mentoring = mentoringService.findByCriteriaIdAndUser(criteriaId, user);
                    if (mentoring != null) {
                        model.addAttribute("mentoring", mentoring);
                    }

                    model.addAttribute("criteriaDetails", "/ni-criteria/mentoring.html");
                    if(this.mentoringService.calculatePointsByUserAndCriteriaId(username, criteriaId) == null)
                        model.addAttribute("calculatedPoints",0.0);
                    else
                        model.addAttribute("calculatedPoints", this.mentoringService.calculatePointsByUserAndCriteriaId(username, criteriaId).points);

                }
            }

            if (criteriaId >= 40 && criteriaId <= 69 ) {
                Long nid = publicationsService.findCriteriaID(criteriaId).getId();
                if (publicationsService.findByCriteriaIdAndUser(criteriaId, user) != null) {
                    List<Publications> publications = publicationsService.findByCriteriaIdAndUser(criteriaId, user);
                    if (publications != null) {
                        model.addAttribute("publications", publications);
                    }

                    model.addAttribute("criteriaDetails", "/ni-criteria/publications.html");

                    if(this.publicationsService.calculatePointsByUserAndCriteriaId(username, criteriaId) == null)
                        model.addAttribute("calculatedPoints",0.0);
                    else
                        model.addAttribute("calculatedPoints", this.publicationsService.calculatePointsByUserAndCriteriaId(username, criteriaId).points);
                }
            }

            if (criteriaId >= 35 && criteriaId <= 39 ) {
                Long nid = projectsService.findCriteriaID(criteriaId).getId();
                if (projectsService.findByCriteriaIdAndUser(criteriaId, user) != null) {
                    List<Projects> projects = projectsService.findByCriteriaIdAndUser(criteriaId, user);
                    if (projects != null) {
                        model.addAttribute("projects", projects);
                    }

                    model.addAttribute("criteriaDetails", "/ni-criteria/projects.html");
                    if(this.projectsService.calculatePointsByUserAndCriteriaId(username, criteriaId) == null)
                        model.addAttribute("calculatedPoints",0.0);
                    else
                        model.addAttribute("calculatedPoints", this.projectsService.calculatePointsByUserAndCriteriaId(username, criteriaId).points);
                }
            }


            System.out.println("Returning criteria page");
        }
        return "criteria";
    }

    @PostMapping("/mentoring/{criteriaId}")
    public String addMentoring(HttpServletRequest req,
                               @PathVariable Long criteriaId,
                               @RequestParam String index,
                               @RequestParam String name,
                               @RequestParam String lastName,
                               @RequestParam String title,
                               RedirectAttributes redirectAttributes) {


        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        if (criteria == null) {
            return "redirect:/error";
        }

        Student student = new Student(index, name, lastName);
        studentService.save(student);

        mentoringService.create(student, criteria, title,user);


        redirectAttributes.addFlashAttribute("addMessage", "Успешно внесена ставка!");

        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }


    @PostMapping("/edit/mentoring/{criteriaId}/{niCriteriaId}")
    public String editMentoring(@PathVariable Long criteriaId,
                                @PathVariable Long niCriteriaId,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String title,
                                            RedirectAttributes redirectAttributes
                                ) {

        Mentoring mentoring = mentoringService.findById(niCriteriaId);
        if (mentoring == null) {
            return "redirect:/error";
        }

        Student student = mentoring.getStudent();
        student.setName(name);
        student.setLastName(lastName);

        studentService.save(student);

        mentoring.setTitle(title);
        mentoring.setStudent(student);

        mentoringService.edit(niCriteriaId, title, student);

        redirectAttributes.addFlashAttribute("editMessage", "Успешно променета ставка!");

        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }

    @PostMapping("/delete/mentoring/{criteriaId}/{niCriteriaId}")
    public String deleteMentoring(@PathVariable Long criteriaId,
                                  @PathVariable Long niCriteriaId,
                                  RedirectAttributes redirectAttributes){

        mentoringService.delete(niCriteriaId);
        redirectAttributes.addFlashAttribute("deleteMessage", "Успешно избришана ставка!");
        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }

    @PostMapping("/publications/{criteriaId}")
    public String addPublications(HttpServletRequest req,
                                  @PathVariable Long criteriaId,
                                  @RequestParam String authors,
                                  @RequestParam String Title,
                                  @RequestParam Integer year,
                                  @RequestParam String locationName,

                                  @RequestParam Integer NoOfAuthors,
                                  RedirectAttributes redirectAttributes) {



        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        if (criteria == null) {
            return "redirect:/error";
        }

        publicationsService.create( authors,  Title,  year,  locationName,   criteria,  NoOfAuthors, user);

        redirectAttributes.addFlashAttribute("addMessage", "Успешно внесовте ставка!");

        // Redirect back to the mentoring details page with updated data
        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }


    @PostMapping("/edit/publications/{criteriaId}/{niCriteriaId}")
    public String editPublications(@PathVariable Long criteriaId,
                                   @PathVariable Long niCriteriaId,
                                   @RequestParam String authors,
                                   @RequestParam String Title,
                                   @RequestParam Integer year,
                                   @RequestParam String locationName,
                                   @RequestParam Integer NoOfAuthors,
                                   RedirectAttributes redirectAttributes) {

        Publications publications = publicationsService.findById(niCriteriaId);
        if (publications == null) {
            return "redirect:/error";
        }


        publicationsService.edit(niCriteriaId, authors,  Title,  year,  locationName,  NoOfAuthors);



        redirectAttributes.addFlashAttribute("editMessage", "Успешно променивте ставка!");

        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }

    @PostMapping("/delete/publications/{criteriaId}/{niCriteriaId}")
    public String deletePublications(@PathVariable Long criteriaId,
                                     @PathVariable Long niCriteriaId,
                                     RedirectAttributes redirectAttributes){

        publicationsService.delete(niCriteriaId);
        redirectAttributes.addFlashAttribute("deleteMessage", "Успешно избришавте ставка!");
        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }
    @PostMapping("/projects/{criteriaId}")
    public String addProjects(HttpServletRequest req,
                              @PathVariable Long criteriaId,
                              @RequestParam String Title,
                              @RequestParam String fundedBy,
                              @RequestParam Integer yearStart,
                              @RequestParam Integer yearEnd,
                              RedirectAttributes redirectAttributes) {



        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        if (criteria == null) {
            return "redirect:/error";
        }

        projectsService.create(Title,  fundedBy,  criteria,  yearStart,  yearEnd, user);

        redirectAttributes.addFlashAttribute("addMessage", "Успешно додадовте ставка!");

        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }


    @PostMapping("/edit/projects/{criteriaId}/{niCriteriaId}")
    public String editProjects(@PathVariable Long criteriaId,
                               @PathVariable Long niCriteriaId,
                               @RequestParam String Title,
                               @RequestParam String fundedBy,
                               @RequestParam Integer yearStart,
                               @RequestParam Integer yearEnd,
                               RedirectAttributes redirectAttributes) {

        Projects projects = projectsService.findById(niCriteriaId);
        if (projects == null) {
            return "redirect:/error";
        }


        projectsService.edit(niCriteriaId,Title,  fundedBy,  yearStart,  yearEnd);


        redirectAttributes.addFlashAttribute("editMessage", "Успешно променивте ставка!");

        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }
    @PostMapping("/delete/projects/{criteriaId}/{niCriteriaId}")
    public String deleteProjects(@PathVariable Long criteriaId,
                                 @PathVariable Long niCriteriaId,
                                 RedirectAttributes redirectAttributes){

        projectsService.delete(niCriteriaId);

        redirectAttributes.addFlashAttribute("deleteMessage", "Успешно избришавте ставка!");
        return "redirect:/Criteria/NI?criteriaId=" + criteriaId;
    }

}
