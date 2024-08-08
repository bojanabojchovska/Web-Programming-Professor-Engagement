package mk.ukim.finki.izborzvanje.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.izborzvanje.model.*;
import mk.ukim.finki.izborzvanje.model.Enumerations.Semester;
import mk.ukim.finki.izborzvanje.model.Enumerations.TypeOfClass;
import mk.ukim.finki.izborzvanje.service.CriteriaService;
import mk.ukim.finki.izborzvanje.service.NOCriteriaService;
import mk.ukim.finki.izborzvanje.service.SubjectService;
import mk.ukim.finki.izborzvanje.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Criteria/NO")
public class NOCriteriaController {

    private final CriteriaService criteriaService;
    private final NOCriteriaService noCriteriaService;
    private final UserService userService;
    private final SubjectService subjectService;

    public NOCriteriaController(CriteriaService criteriaService, NOCriteriaService noCriteriaService, UserService userService, SubjectService subjectService) {
        this.criteriaService = criteriaService;
        this.noCriteriaService = noCriteriaService;
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @GetMapping()
    public String showNOCriteria(HttpServletRequest req,
                                 @RequestParam(required = false) Long criteriaId,
                                 @RequestParam(required = false) Long noCriteriaId,
                                 Model model) {
        List<Criteria> criteriaList = criteriaService.listAllNOCriteria();
        List<Subject> subjectList = subjectService.getAllSubjects();
        List<Criteria> cycleCriteriaList = null;
        List<Criteria> membershipCriteriaList = null;
        List<Criteria> workCriteriaList = null;

        if (criteriaList.size() > 3) {
            cycleCriteriaList = criteriaList.subList(0, 3);
            membershipCriteriaList = criteriaList.subList(9,15);
            workCriteriaList = criteriaList.subList(15,29);
        }

        if (criteriaId != null) {
            model.addAttribute("criteria", this.criteriaService.findById(criteriaId));
            model.addAttribute("subjects", subjectList);
            model.addAttribute("typeOfClassOptions", TypeOfClass.values());
            model.addAttribute("criteriaDetails", "/no-criteria/1.html");
            model.addAttribute("showDetails", true);

            String username = req.getRemoteUser();
            User user = userService.findById(username);
            List<NOCriteria> noCriteriaList = noCriteriaService.findByCriteriaIdAndUser(criteriaId, user);

            if(this.noCriteriaService.calculatePointsByUserAndCriteriaId(username, criteriaId) == null)
                model.addAttribute("calculatedNOPoints",0.0);
            else
                model.addAttribute("calculatedNOPoints", Math.round(this.noCriteriaService.calculatePointsByUserAndCriteriaId(username, criteriaId).points* 100.0) / 100.0);

            if (!noCriteriaList.isEmpty()) {
                model.addAttribute("noShow", "true");
                model.addAttribute("noCriteriaList", noCriteriaList);
            }
        } else {
            model.addAttribute("showDetails", false);
        }

        model.addAttribute("noCriteriaId", noCriteriaId);
        model.addAttribute("field", "NO");
        model.addAttribute("criteriaList", cycleCriteriaList);
        model.addAttribute("membershipCriteriaList", membershipCriteriaList);
        model.addAttribute("workCriteriaList", workCriteriaList);
        model.addAttribute("specifics", "/no-criteria/no-specifics.html");

        return "criteria";
    }

    @PostMapping("/{criteriaId}")
    public String addCriteria(HttpServletRequest req,
                              @PathVariable Long criteriaId,
                              @RequestParam String yearOfStudies,
                              @RequestParam String subjectId,
                              @RequestParam TypeOfClass typeOfClass,
                              @RequestParam Integer numberOfClasses,
                              @RequestParam Integer numberOfStudents,
                              RedirectAttributes redirectAttributes){

        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        Subject subject = subjectService.getSubjectById(subjectId);
        String subjectName = subject.getName();
        String semester = subject.getSemester().toString();

        noCriteriaService.create(criteria, user, yearOfStudies, subjectName, semester, typeOfClass, numberOfClasses, numberOfStudents);
        redirectAttributes.addFlashAttribute("successMessageTable", "Успешно внесена ставка!");
        return "redirect:/Criteria/NO?criteriaId=" + criteriaId;
    }

    @PostMapping("/{criteriaId}/{noCriteriaId}")
    public String editCriteria(@PathVariable Long criteriaId,
                               @PathVariable Long noCriteriaId,
                               @RequestParam String yearOfStudies,
                               @RequestParam String subjectId,
                               @RequestParam TypeOfClass typeOfClass,
                               @RequestParam Integer numberOfClasses,
                               @RequestParam Integer numberOfStudents,
                               RedirectAttributes redirectAttributes){
        noCriteriaService.edit(noCriteriaId, yearOfStudies, subjectId, typeOfClass, numberOfClasses, numberOfStudents);
        redirectAttributes.addFlashAttribute("editMessageTable", "Успешно променета ставка!");
        return "redirect:/Criteria/NO?criteriaId=" + criteriaId;
    }

    @PostMapping("/delete/{criteriaId}/{noCriteriaId}")
    public String deleteCriteria(@PathVariable Long criteriaId,
                                 @PathVariable Long noCriteriaId,
                                 RedirectAttributes redirectAttributes){

        noCriteriaService.delete(noCriteriaId);
        redirectAttributes.addFlashAttribute("deleteMessageTable", "Успешно избришана ставка!");
        return "redirect:/Criteria/NO?criteriaId=" + criteriaId;
    }

    @PostMapping("/teaching")
    public String addTeachingCriteria(HttpServletRequest req,
                                      @RequestParam(name = "role", required = false) String role,
                                      @RequestParam(name = "type", required = false) String type,
                                      RedirectAttributes redirectAttributes) {
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        if ("manager".equals(role)){
            noCriteriaService.createTeachingNOCriteria(criteriaService.findById(4L),user);
        } else if ("participant".equals(role)){
            noCriteriaService.createTeachingNOCriteria(criteriaService.findById(5L),user);
        }

        if ("lecture".equals(type)){
            noCriteriaService.createTeachingNOCriteria(criteriaService.findById(7L),user);
        } else if ("exercises".equals(type)){
            noCriteriaService.createTeachingNOCriteria(criteriaService.findById(8L),user);
        }

        if (role!=null || type !=null){
            redirectAttributes.addFlashAttribute("successMessage", "Успешно внесена ставка!");
        }

        return "redirect:/Criteria/NO";
    }

    @PostMapping("/membership")
    public String addMembershipCriteria(HttpServletRequest req,
                                        @RequestParam Long criteriaId,
                                        @RequestParam Integer numberOfStudentsForMentorship,
                                        RedirectAttributes redirectAttributes) {

        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        noCriteriaService.createMentorshipNOCriteria(criteria, user, numberOfStudentsForMentorship);

        redirectAttributes.addFlashAttribute("successMessage", "Успешно внесена ставка!");
        return "redirect:/Criteria/NO?criteriaId=" + criteriaId;
    }

    @PostMapping("/workTitle")
    public String addWorkTitleCriteria(HttpServletRequest req,
                                       @RequestParam Long criteriaId,
                                       @RequestParam String titleOfWork,
                                       RedirectAttributes redirectAttributes) {

        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        noCriteriaService.createWorkNOCriteria(criteria, user, titleOfWork);

        redirectAttributes.addFlashAttribute("successMessage", "Успешно внесена ставка!");
        return "redirect:/Criteria/NO?criteriaId=" + criteriaId;
    }

    // ---------------------------------- Item page functionalities ----------------------------------
    @GetMapping("/items")
    public String showItems(HttpServletRequest req,
                            @RequestParam(required = false) Long noCriteriaId,
                            Model model){

        List<NOCriteria> teachingCriteriaList = null;
        List<NOCriteria> mentorshipCriteriaList = null;
        List<NOCriteria> workCriteriaList = null;

        String username = req.getRemoteUser();
        User user = userService.findById(username);
        List<NOCriteria> noCriteriaList = noCriteriaService.findByUser(user);

        if (noCriteriaId!=null){
            model.addAttribute("noCriteriaId", noCriteriaId);
        }

        List<Long> validTeachingIds = Arrays.asList(4L, 5L, 7L, 8L);
        List<Long> validMentorshipIds = Arrays.asList(10L, 11L, 12L, 13L, 14L, 15L);
        List<Long> validWorkTitleIds = Arrays.asList(16L, 17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L);

        teachingCriteriaList = noCriteriaList.stream()
                .filter(noCriteria -> validTeachingIds.contains(noCriteria.getCriteria().getId()))
                .toList();
        model.addAttribute("teachingCriteriaList", teachingCriteriaList);
        mentorshipCriteriaList = noCriteriaList.stream()
                .filter(noCriteria -> validMentorshipIds.contains(noCriteria.getCriteria().getId()))
                .toList();
        model.addAttribute("mentorshipCriteriaList", mentorshipCriteriaList);
        workCriteriaList = noCriteriaList.stream()
                .filter(noCriteria -> validWorkTitleIds.contains(noCriteria.getCriteria().getId()))
                .toList();
        model.addAttribute("workCriteriaList", workCriteriaList);


        if (!noCriteriaList.isEmpty()) {
            model.addAttribute("noCriteriaList", noCriteriaList);
        }

        return "/no-criteria/no-items";
    }

    @PostMapping("/editItem/{criteriaId}/{noCriteriaId}")
    public String editItem(@PathVariable Long criteriaId,
                               @PathVariable Long noCriteriaId,
                               @RequestParam(required = false) Integer studentsForMentorship,
                               @RequestParam(required = false) String nameOfWorkTitle,
                               RedirectAttributes redirectAttributes){

        if(nameOfWorkTitle!=null){
            noCriteriaService.edit(noCriteriaId, nameOfWorkTitle);
        }else if(studentsForMentorship != null){
            noCriteriaService.edit(noCriteriaId, studentsForMentorship);
        }
        redirectAttributes.addFlashAttribute("editMessage", "Успешно променета ставка!");
        return "redirect:/Criteria/NO/items";
    }

    @PostMapping("/deleteItem/{criteriaId}/{noCriteriaId}")
    public String deleteItem(@PathVariable Long criteriaId,
                             @PathVariable Long noCriteriaId,
                             RedirectAttributes redirectAttributes){

        noCriteriaService.delete(noCriteriaId);
        redirectAttributes.addFlashAttribute("deleteMessage", "Успешно избришана ставка!");
        return "redirect:/Criteria/NO/items";
    }
}