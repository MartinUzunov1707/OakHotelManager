package bg.oakhotelmanager.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class ReservationEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    private UserEntity reservee;

    @Column(name = "check_in_date",nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date",nullable = false)
    private LocalDate checkOutDate;

    @ManyToOne(optional = false)
    private RoomEntity occupiedRoom;

    @Column(nullable = false)
    private Double price;
//    @OneToOne
//    private PaymentEntity payment;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "amount_of_children")
    private Integer childrenAmount;

    @Column(name = "amount_of_adults")
    private Integer adultAmount;

    @Column(name = "created_on")
    private LocalDate createdOn;

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public UserEntity getReservee() {
        return reservee;
    }

    public void setReservee(UserEntity reservee) {
        this.reservee = reservee;
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

    public RoomEntity getOccupiedRoom() {
        return occupiedRoom;
    }

    public void setOccupiedRoom(RoomEntity occupiedRoom) {
        this.occupiedRoom = occupiedRoom;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

//    public PaymentEntity getPayment() {
//        return payment;
//    }
//
//    public void setPayment(PaymentEntity payment) {
//        this.payment = payment;
//    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(Integer childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public Integer getAdultAmount() {
        return adultAmount;
    }

    public void setAdultAmount(Integer adultAmount) {
        this.adultAmount = adultAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
