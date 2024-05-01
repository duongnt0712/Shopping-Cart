package se2project.controller.admin;

import se2project.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping(value = "")
    public String adminHome(Model model) {
        return "adminHome";
    }
}
