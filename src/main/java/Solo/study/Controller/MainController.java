package Solo.study.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String login() {
        return "Login";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}