package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.RegisterDTO;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/login-error")
    public String errorLogin(RedirectAttributes attr){
        attr.addFlashAttribute("loginError", true);
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }
    @PostMapping("/register")
    public String doRegister(@Valid RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("registerDTO", registerDTO);
            attr.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/register";
        }
        if(!userService.registerUser(registerDTO)){
            attr.addFlashAttribute("usernameTaken",true);
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
