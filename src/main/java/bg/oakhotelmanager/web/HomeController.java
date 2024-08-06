package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.model.user.OakUserDetails;
import jakarta.persistence.Column;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private boolean reportCreated = false;
    @GetMapping("/")
    public String viewIndex(@AuthenticationPrincipal UserDetails userDetails, Model model){
        if(userDetails instanceof OakUserDetails){
            model.addAttribute("welcomeMessage", ((OakUserDetails) userDetails).getFullName());
            if(reportCreated && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.ADMIN))){
                model.addAttribute("reportCreated", true);
            }
            else{
                model.addAttribute("reportCreated",false);
            }
        }
        else{
            model.addAttribute("welcomeMessage", "Anonymous");
        }
        return "index";
    }
    @Scheduled(cron = "0 * 22 * * *")
    private void setReportCreated(){
        reportCreated = true;
    }
    @Scheduled(cron = "0 59 23 * * *")
    private void nullifyReportCreated(){
        reportCreated = false;
    }
}
