package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.CommentDTO;
import bg.oakhotelmanager.model.dto.CommentUserDTO;
import bg.oakhotelmanager.model.entity.CommentEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.model.user.OakUserDetails;
import bg.oakhotelmanager.service.impl.CommentService;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.stream.events.Comment;

@Controller
public class HomeController {
    private boolean reportCreated = false;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper mapper;

    public HomeController(CommentService commentService, UserService userService, ModelMapper mapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.mapper = mapper;
    }

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
    @GetMapping("/about-us")
    public String viewAboutUs(Model model){
        List<CommentUserDTO> list = map(commentService.getAllComments());
        if(list.size()>5){
            model.addAttribute("commentList", list.subList(list.size()-6,list.size()));
        }
        else{
            model.addAttribute("commentList", list);
        }
        return "about-us";
    }
    private List<CommentUserDTO>  map(List<CommentDTO> data){

        List<CommentUserDTO> commentUserDTOList = new ArrayList<>();
        for(CommentDTO  current: data){
            CommentUserDTO user = new CommentUserDTO();
            user.setDescription(current.getDescription());
            user.setName(userService.findUserById(current.getCreatorId()).get().getFullName());
            commentUserDTOList.add(user);
        }
        return commentUserDTOList;
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
