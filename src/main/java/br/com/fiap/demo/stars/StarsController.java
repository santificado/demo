package br.com.fiap.demo.stars;

import java.util.Locale;

import br.com.fiap.demo.stars.Stars;
import br.com.fiap.demo.stars.StarsService;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.demo.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/star")
public class StarsController {

    @Autowired
    StarsService service;

    @Autowired
    MessageSource message;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("stars", service.findAll());
        return "star/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", message.getMessage("star.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error", "Tarefa não encontrada");
        }
        return "redirect:/star";
    }

    @GetMapping("new")
    public String form(Star star, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "star/form";
    }

    @PostMapping
    public String create(@Valid Star star, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/star/form";

        service.save(new Stars());
        redirect.addFlashAttribute("success", "Tarefa cadastrada com sucesso");
        return "redirect:/star";
    }

    @GetMapping("/inc/{id}")
    public String incrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.increment(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da tarefa");
        }
        return "redirect:/star";
    }

    @GetMapping("/dec/{id}")
    public String decrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.decrement(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da tarefa");
        }
        return "redirect:/star";
    }

    @GetMapping("/catch/{id}")
    public String CatchStar(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.catchStar(id, User.convert(user));
        return "redirect:/star";
    }



}