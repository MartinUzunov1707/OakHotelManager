package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.user.OakUserDetails;
import jakarta.persistence.Column;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String viewIndex(@AuthenticationPrincipal UserDetails userDetails, Model model){
        if(userDetails instanceof OakUserDetails){
            model.addAttribute("welcomeMessage", ((OakUserDetails) userDetails).getFullName());
        }
        else{
            model.addAttribute("welcomeMessage", "Anonymous");
        }
        return "index";
    }


}
