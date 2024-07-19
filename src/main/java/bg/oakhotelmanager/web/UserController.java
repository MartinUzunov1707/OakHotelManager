package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.RegisterDTO;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public RegisterDTO registerDTO(){
        return new RegisterDTO();
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }
    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }
    @PostMapping("/register")
    public String doRegister(@Valid RegisterDTO data){
        userService.RegisterUser(data);
        return "redirect:/";
    }
}
