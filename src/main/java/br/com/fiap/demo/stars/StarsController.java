package br.com.fiap.demo.stars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stars")
public class StarsController {

    private final StarsService service;

    @Autowired
    public StarsController(StarsService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("stars", service.findAll());
        return "stars/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (service.delete(id)) {
            redirect.addFlashAttribute("success", "Avaliação removida com sucesso");
        } else {
            redirect.addFlashAttribute("error", "Avaliação não encontrada");
        }
        return "redirect:/stars";
    }
}
