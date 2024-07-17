package bg.oakhotelmanager.model.entity;

import bg.oakhotelmanager.model.enums.RoomEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomEntity extends BaseEntity{
    @Column(name = "room_name",unique = true,nullable = false)
    private String roomName;
    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "room_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomEnum roomType;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public RoomEnum getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomEnum roomType) {
        this.roomType = roomType;
    }
}
