package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.ReservationDTO;
import bg.oakhotelmanager.model.entity.ReservationEntity;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.service.impl.ReservationService;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @GetMapping("/double-room")
    public String viewDoubleRoom(){
        return "double-room";
    }
    @GetMapping("/triple-room")
    public String viewTripleRoom(){
        return "triple-room";
    }
    @GetMapping("/apartment")
    public String viewApartment(){
        return "apartment";
    }
}
