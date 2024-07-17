package bg.oakhotelmanager.model.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservations")
public class ReservationEntity extends BaseEntity {
    @OneToOne(optional = false)
    private UserEntity reservee;

    @Column(name = "check_in_date",nullable = false)
    private Date checkInDate;

    @Column(name = "check_out_date",nullable = false)
    private Date checkOutDate;

    @OneToOne(optional = false)
    private RoomEntity occupiedRoom;

    @Column(nullable = false)
    private Double price;
    @OneToOne(optional = false)
    private PaymentEntity payment;

    public UserEntity getReservee() {
        return reservee;
    }

    public void setReservee(UserEntity reservee) {
        this.reservee = reservee;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
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

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }
}
