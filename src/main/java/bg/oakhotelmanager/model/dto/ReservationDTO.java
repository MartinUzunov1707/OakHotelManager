package bg.oakhotelmanager.model.dto;

import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDTO {

    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long!")
    public String phoneNumber;

    @NotNull(message = "Select check in date!")
    @Future(message = "Check in date must be in the future!")
    public LocalDate checkInDate;

    @NotNull(message = "Select check out date!")
    @Future(message = "Check out date must be in the future!")
    public LocalDate checkOutDate;

    @NotNull(message = "Select adult amount!")
    @Max(value = 4, message = "Amount of adults must not exceed 4.")
    public Integer adultAmount;

    @NotNull(message = "Select amount of children!")
    @Max(value = 4, message = "Amount of children must not exceed 4.")
    public Integer childrenAmount;

    @NotNull(message = "Select type of room!")
    public String roomType;

    public ReservationDTO(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getAdultAmount() {
        return adultAmount;
    }

    public void setAdultAmount(Integer adultAmount) {
        this.adultAmount = adultAmount;
    }

    public Integer getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(Integer childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
