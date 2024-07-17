package bg.oakhotelmanager.model.entity;

import bg.oakhotelmanager.model.enums.PaymentTypeEnum;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class PaymentEntity extends BaseEntity {
    @Column(name = "is_complete")
    private boolean isComplete;
    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentType;
    @OneToOne(optional = false)
    private ReservationEntity reservation;

    @Column
    private Double amount;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
