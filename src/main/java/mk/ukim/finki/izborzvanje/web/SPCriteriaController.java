package mk.ukim.finki.izborzvanje.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.izborzvanje.model.Criteria;
import mk.ukim.finki.izborzvanje.model.SPCriteria;
import mk.ukim.finki.izborzvanje.repository.CriteriaRepository;
import mk.ukim.finki.izborzvanje.service.CriteriaService;
import mk.ukim.finki.izborzvanje.service.SPCriteriaService;
import mk.ukim.finki.izborzvanje.service.UserService;
import mk.ukim.finki.izborzvanje.model.User;
import org.springframework.security.core.Authentication;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Criteria/SP")
public class SPCriteriaController {
    private final SPCriteriaService spCriteriaService;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaService criteriaService;
    private final UserService userService;

    public SPCriteriaController(SPCriteriaService spCriteriaService, CriteriaService criteriaService, CriteriaRepository criteriaRepository, UserService userService) {
        this.spCriteriaService = spCriteriaService;
        this.criteriaRepository = criteriaRepository;
        this.criteriaService = criteriaService;
        this.userService = userService;
    }

    @GetMapping
    public String getSPCriteriaPage(HttpServletRequest req,
                                    @RequestParam(required = false) Long criteriaId,
                                    @RequestParam(required = false) Long spCriteriaId,
                                    Model model
                                    ){

        List<Criteria> criteriaList = criteriaService.listAllSPCriteria();

        model.addAttribute("criteriaList", criteriaList);
        model.addAttribute("spCriteriaId", spCriteriaId);
        model.addAttribute("field", "SP");
        model.addAttribute("specifics", "/sp-criteria/sp-specifics.html");

        if(criteriaId!= null) {
            model.addAttribute("showDetails", true);
            model.addAttribute("criteriaId", criteriaId);
            model.addAttribute("criteria", this.criteriaService.findById(criteriaId));
            model.addAttribute("criteriaDetails", "/sp-criteria/2.html");

            String username = req.getRemoteUser();
            User user = userService.findById(username);

            if(this.spCriteriaService.calculatePointsByUserAndCriteriaId(username, criteriaId) == null)
                model.addAttribute("calculatedPoints",0.0);
            else
                model.addAttribute("calculatedPoints", this.spCriteriaService.calculatePointsByUserAndCriteriaId(username, criteriaId).points);

            List<SPCriteria> spCriteriaList = spCriteriaService.findByCriteriaIdAndUser(criteriaId, user);

            if (!spCriteriaList.isEmpty()) {
                model.addAttribute("spShow", "true");
                model.addAttribute("spCriteriaList", spCriteriaList);
            }

        }
        else{
            model.addAttribute("showDetails", "false");
        }

        return "criteria";
    }

    @PostMapping("/{criteriaId}")
    public String addCriteria(HttpServletRequest req,
                              @PathVariable Long criteriaId,
                              @RequestParam String name,
                              @RequestParam(required = false) String year,
                              RedirectAttributes redirectAttributes){
        Criteria criteria = this.criteriaService.findById(criteriaId);
        String username = req.getRemoteUser();
        User user = userService.findById(username);

        spCriteriaService.create(criteria,user, name, year);
        redirectAttributes.addFlashAttribute("addMessage", "Успешно внесена ставка!");
        return "redirect:/Criteria/SP?criteriaId=" + criteriaId;
    }

    @PostMapping("/edit/{criteriaId}/{spCriteriaId}")
    public String editCriteria(HttpServletRequest req,
                              @PathVariable Long criteriaId,
                              @PathVariable Long spCriteriaId,
                              String name,
                              String year,
                               RedirectAttributes redirectAttributes){
        spCriteriaService.edit(spCriteriaId,name,year);
        redirectAttributes.addFlashAttribute("editMessage", "Успешно променета ставка!");
        return "redirect:/Criteria/SP?criteriaId=" + criteriaId;

    }

    @PostMapping("/delete/{criteriaId}/{spCriteriaId}")
    public String deleteCriteria(HttpServletRequest req,
                                 @PathVariable Long criteriaId,
                                 @PathVariable Long spCriteriaId,
                                 RedirectAttributes redirectAttributes){

        spCriteriaService.delete(spCriteriaId);
        redirectAttributes.addFlashAttribute("deleteMessage", "Успешно избришана ставка!");
        return "redirect:/Criteria/SP?criteriaId=" + criteriaId;
    }

}
