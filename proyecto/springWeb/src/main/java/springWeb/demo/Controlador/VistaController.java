package springWeb.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springWeb.demo.domain.Servicios.MascotaServiceImpl;

@Controller
public class VistaController {
    @Autowired
    private MascotaServiceImpl mascotaService;

    @GetMapping("/login")
    public String login() {
        System.out.println("✅ ViewController /login EJECUTÁNDOSE");
        return "forward:/index.html";
    }

    @GetMapping("/")
    public String home() {
        System.out.println("✅ ViewController / EJECUTÁNDOSE");
        return "forward:/index.html";
    }

    @GetMapping("/register")
    public String register() {
        System.out.println("✅ ViewController /register EJECUTÁNDOSE");
        return "forward:/index.html";
    }

    @GetMapping("/inicio")
    public String inicio() {
        System.out.println("✅ ViewController /dashboard EJECUTÁNDOSE");
        return "forward:/inicio.html";
    }

    @GetMapping("/ver-mascotas")
    public String verMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.listarMascotasPorDueño(1L));
        return "mascotas";
    }

    @GetMapping("/contact")
    public String contacto() {
        return "forward:/contact.html";
    }

    @PostMapping("/contact-fake")
    public String contactoPostSimulado(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", "Gracias por contactarnos. Te responderemos pronto.");
        return "redirect:/contact";
    }

    @GetMapping("/navbar")
    public String navbar() {
        return "forward:/navbar.html";
    }

}