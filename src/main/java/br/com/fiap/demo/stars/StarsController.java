package br.com.fiap.demo.stars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class StarsController {

    @Autowired
    private StarsService starsService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        List<Stars> stars = starsService.findAllStars();
        model.addAttribute("stars", stars);


        if (principal != null) {
            model.addAttribute("username", principal.getAttribute("login"));
            model.addAttribute("avatar_url", principal.getAttribute("avatar_url"));
        }

        return "stars/index";
    }

    @GetMapping("/new")
    public String createForm(Stars feedback) {
        return "stars/form";
    }

    @PostMapping
    public String createFeedback(@Valid Stars stars, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "stars/form";
        }

        starsService.save(stars);
        redirectAttributes.addFlashAttribute("success", "Feedback cadastrado com sucesso");
        return "redirect:/stars";
    }
}
