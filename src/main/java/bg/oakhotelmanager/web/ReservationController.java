package bg.oakhotelmanager.web;

import bg.oakhotelmanager.model.dto.ReservationDTO;
import bg.oakhotelmanager.model.entity.ReservationEntity;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.service.impl.ReservationService;
import bg.oakhotelmanager.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/reservation")
    public String viewReservation(Model model){
        if(!model.containsAttribute("reservationDTO")){
            model.addAttribute("reservationDTO", new ReservationDTO());
        }
        if(!model.containsAttribute("roomCapacityNotEnough")){
            model.addAttribute("roomCapacityNotEnough", "");
        }
        return "reservation";
    }
    @PostMapping("/reservation")
    public String doReservation(@AuthenticationPrincipal UserDetails userDetails, @Valid ReservationDTO reservationDTO, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            attributes.addFlashAttribute("reservationDTO", reservationDTO);
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationDTO", bindingResult);
            return "redirect:/reservation";
        }
        if(reservationDTO.roomType.equals("Double") && (reservationDTO.adultAmount + reservationDTO.childrenAmount/2 > 2.5)){
            attributes.addFlashAttribute("reservationDTO", reservationDTO);
            attributes.addFlashAttribute("roomCapacityNotEnough", String.format("The double room does not have enough capacity for %d adults and %d children. Consider booking to a bigger room.", reservationDTO.adultAmount, reservationDTO.childrenAmount));
            return "redirect:/reservation";
        }
        else if(reservationDTO.roomType.equals("Triple") && (reservationDTO.adultAmount + reservationDTO.childrenAmount/2 > 3.5)){
            attributes.addFlashAttribute("reservationDTO", reservationDTO);
            attributes.addFlashAttribute("roomCapacityNotEnough", String.format("The triple room does not have enough capacity for %d adults and %d children. Consider booking to a bigger room.", reservationDTO.adultAmount, reservationDTO.childrenAmount));
            return "redirect:/reservation";
        }
        else if(reservationDTO.roomType.equals("Apartment") && (reservationDTO.adultAmount + reservationDTO.childrenAmount/2 > 4.5)){
            attributes.addFlashAttribute("reservationDTO", reservationDTO);
            attributes.addFlashAttribute("roomCapacityNotEnough", String.format("The apartment does not have enough capacity for %d adults and %d children. Consider booking multiple rooms.", reservationDTO.adultAmount, reservationDTO.childrenAmount));
            return "redirect:/reservation";
        }
        ReservationEntity entity = reservationService.saveReservation(reservationDTO, userDetails);
        if(entity != null){
            attributes.addFlashAttribute("outOfRooms", true);
            return "redirect:/reservation";
        }
        else{
            return "redirect:/reservation-successful";
        }
    }
    @GetMapping("/reservation-successful")
    private String viewReservationSuccessful(@AuthenticationPrincipal UserDetails userDetails){
        Optional<UserEntity> userByEmail = userService.getUserByEmail(userDetails.getUsername());
        if(reservationService.findReservationByUser(userByEmail.get()).isPresent()){
            return "reservation-successful";
        }
        return "redirect:/reservation";
    }
}
