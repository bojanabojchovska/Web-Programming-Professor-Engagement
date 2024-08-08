package mk.ukim.finki.izborzvanje.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.izborzvanje.model.CriteriaDto;
import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.service.NI.MentoringService;
import mk.ukim.finki.izborzvanje.service.NI.ProjectsService;
import mk.ukim.finki.izborzvanje.service.NI.PublicationsService;
import mk.ukim.finki.izborzvanje.service.NOCriteriaService;
import mk.ukim.finki.izborzvanje.service.SPCriteriaService;
import mk.ukim.finki.izborzvanje.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/FinalTemplate")
public class FinalTemplateController {
    private final SPCriteriaService spCriteriaService;
    private final NOCriteriaService noCriteriaService;
    private final MentoringService mentoringService;
    private final PublicationsService publicationsService;
    private final ProjectsService projectsService;
    private final UserService userService;

    public FinalTemplateController(SPCriteriaService spCriteriaService, NOCriteriaService noCriteriaService, MentoringService mentoringService, PublicationsService publicationsService, ProjectsService projectsService, UserService userService) {
        this.spCriteriaService = spCriteriaService;
        this.noCriteriaService = noCriteriaService;
        this.mentoringService = mentoringService;
        this.publicationsService = publicationsService;
        this.projectsService = projectsService;
        this.userService = userService;
    }

    @GetMapping
    public String getPage(HttpServletRequest req, Model model) {
        String username = req.getRemoteUser();
        User user = userService.findById(username);
        double spPoints = 0, noPoints = 0, niPoints = 0;

        // NO
        List<CriteriaDto> noCriteriaList = noCriteriaService.calculateCriteriaPointsByUser(username); //.reversed();
        noPoints = noCriteriaList.stream().mapToDouble(p -> p.points).sum();

        // NI
        List<CriteriaDto> niCriteriaList = mentoringService.calculateCriteriaPointsByUser(username);
        niCriteriaList.addAll(publicationsService.calculatePublishingPoints(username));
        niCriteriaList.addAll(projectsService.calculateProjectPoints(username));
        niPoints = niCriteriaList.stream().mapToDouble(p -> p.points).sum();

        // SP
        List<CriteriaDto> spCriteriaListFalse = spCriteriaService.calculateCriteriaPointsByUser(username)
                .stream().filter(s -> !s.activitiesOfWiderInterest).toList();

        List<CriteriaDto> spCriteriaListTrue = spCriteriaService.calculateCriteriaPointsByUser(username)
                .stream().filter(s -> s.activitiesOfWiderInterest).toList();
        spPoints = spCriteriaService.calculateCriteriaPointsByUser(username).stream().mapToDouble(p -> p.points).sum();

        model.addAttribute("user", user);
        model.addAttribute("noCriteriaList", noCriteriaList);
        model.addAttribute("niCriteriaList", niCriteriaList);
        model.addAttribute("spCriteriaListFalse", spCriteriaListFalse);
        model.addAttribute("spCriteriaListTrue", spCriteriaListTrue);
        model.addAttribute("noPoints", Math.round(noPoints* 100.0) / 100.0);
        model.addAttribute("niPoints", Math.round(niPoints* 100.0) / 100.0);
        model.addAttribute("spPoints", Math.round(spPoints* 100.0) / 100.0);
        model.addAttribute("totalPoints", Math.round((noPoints+niPoints+spPoints)* 100.0) / 100.0);

        return "final-template/index";
    }
}
