package bg.oakhotelmanager.repository;

import bg.oakhotelmanager.model.entity.RoomEntity;
import bg.oakhotelmanager.model.enums.RoomEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    List<RoomEntity> findAllByRoomType(RoomEnum roomType);
}
