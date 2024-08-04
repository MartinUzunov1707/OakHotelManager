package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.AddCommentDTO;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.service.impl.CommentService;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.hibernate.boot.archive.spi.AbstractArchiveDescriptor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @ModelAttribute("addCommentDTO")
    private AddCommentDTO addCommentDTO(){
        return new AddCommentDTO();
    }
    @GetMapping("/add-comment")
    private String viewAddComment(){
        return "add-comment";
    }
    @PostMapping("/add-comment")
    private String doAddComment(@AuthenticationPrincipal UserDetails userDetails, @Valid AddCommentDTO data, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addCommentDTO", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCommentDTO", bindingResult);
            return "redirect:/add-comment";
        }
        UserEntity user = userService.getUserByEmail(userDetails.getUsername()).get();
        data.setCreatorId(user.getId());
        commentService.createComment(data);
        return "redirect:/";
    }


}
