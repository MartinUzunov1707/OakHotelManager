package bg.oakhotelmanager.repository;

import bg.oakhotelmanager.model.entity.ReservationEntity;
import bg.oakhotelmanager.model.entity.UserEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    public Optional<ReservationEntity> findReservationEntityByReservee(UserEntity user);
}
