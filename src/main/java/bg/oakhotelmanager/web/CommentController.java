package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.AddCommentDTO;
import bg.oakhotelmanager.model.dto.CommentDTO;
import bg.oakhotelmanager.model.dto.EditCommentDTO;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.service.impl.CommentService;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.hibernate.boot.archive.spi.AbstractArchiveDescriptor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @ModelAttribute("addCommentDTO")
    private AddCommentDTO addCommentDTO() {
        return new AddCommentDTO();
    }
    @ModelAttribute("minLengthFlag")
    private boolean minLengthFlag(){return false;}

    @GetMapping("/add-comment")
    private String viewAddComment() {
        return "add-comment";
    }

    @PostMapping("/add-comment")
    private String doAddComment(@AuthenticationPrincipal UserDetails userDetails, @Valid AddCommentDTO data, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCommentDTO", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCommentDTO", bindingResult);
            return "redirect:/add-comment";
        }
        UserEntity user = userService.getUserByEmail(userDetails.getUsername()).get();
        data.setCreatorId(user.getId());
        commentService.createComment(data);
        return "redirect:/";
    }

    @GetMapping("/all-comments")
    public String viewAllComments(@AuthenticationPrincipal UserDetails user, Model model) {
        UserEntity userEntity = userService.getUserByEmail(user.getUsername()).get();
        List<CommentDTO> comments = commentService.getUserComments(userEntity.getId());
        model.addAttribute("editCommentDTO", new EditCommentDTO());
        model.addAttribute("comments", comments);
        return "all-comments";
    }

    @DeleteMapping("/delete-comment/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/all-comments";
    }

    @PostMapping("edit-comment/{id}")
    public String editComment(@PathVariable Long id, @Valid EditCommentDTO data, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("minLength", true);
        }
        else{
            commentService.editComment(id,data);
        }
        return "redirect:/all-comments";
    }


}
