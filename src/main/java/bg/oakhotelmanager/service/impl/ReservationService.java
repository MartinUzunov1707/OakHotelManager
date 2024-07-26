package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.model.dto.ReservationDTO;
import bg.oakhotelmanager.model.entity.PaymentEntity;
import bg.oakhotelmanager.model.entity.ReservationEntity;
import bg.oakhotelmanager.model.entity.RoomEntity;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.model.enums.RoomEnum;
import bg.oakhotelmanager.repository.ReservationRepository;
import bg.oakhotelmanager.repository.RoomRepository;
import bg.oakhotelmanager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, ModelMapper mapper, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public ReservationEntity saveReservation(ReservationDTO reservationDTO, UserDetails userDetails){
        List<RoomEntity> roomsByRoomType = roomRepository.findAllByRoomType(RoomEnum.valueOf(reservationDTO.roomType.toUpperCase()));
        RoomEntity room = findSuitableRoom(reservationDTO,roomsByRoomType);
        if(room == null){
            return null;
        }
        ReservationEntity entity = mapper.map(reservationDTO, ReservationEntity.class);

        entity.setReservee(userRepository.findByEmail(userDetails.getUsername()).get());
        entity.setOccupiedRoom(room);
        entity.setPrice(calculatePrice(entity));

        reservationRepository.save(entity);
        return entity;
    }

    private RoomEntity findSuitableRoom(ReservationDTO reservationDTO, List<RoomEntity> roomsByRoomType) {
        boolean isOccupied = false;
        for(RoomEntity current : roomsByRoomType){
            if (current.getReservations().isEmpty()) return current;
            for (ReservationEntity entity : current.getReservations()){
                if((reservationDTO.getCheckInDate().isAfter(entity.getCheckInDate()) && reservationDTO.getCheckInDate().isBefore(entity.getCheckOutDate()))
                || reservationDTO.getCheckOutDate().isAfter(entity.getCheckInDate()) && reservationDTO.getCheckOutDate().isBefore(entity.getCheckOutDate())
                || entity.getCheckInDate().isAfter(reservationDTO.getCheckInDate()) && entity.getCheckInDate().isBefore(reservationDTO.getCheckOutDate())){
                    isOccupied = true;
                    break;
                }
            }
            if(!isOccupied){
                return current;
            }
        }
        return null;
    }
    private double calculatePrice(ReservationEntity entity){
        if(entity.getOccupiedRoom().getRoomType().toString().equals("DOUBLE")){
            if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),6,30))){
                return 140 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays();
            }
            else if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),9,30))){
                return 160 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays();
            }
        }else if(entity.getOccupiedRoom().getRoomType().toString().equals("TRIPLE")){
            if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),6,30))){
                return 155 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays() + (entity.getAdultAmount() + (double) entity.getChildrenAmount() /2) * 15;
            }
            else if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),9,30))){
                return 170 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays() + (entity.getAdultAmount() + (double) entity.getChildrenAmount() /2) * 20;
            }
        }
        else{
            if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),6,30))){
                return 165 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays() + (entity.getAdultAmount() + (double) entity.getChildrenAmount() /2) * 15;
            }
            else if(entity.getCheckInDate().isBefore(LocalDate.of(entity.getCheckInDate().getYear(),9,30))){
                return 180 * Duration.ofDays(DAYS.between(entity.getCheckInDate(),entity.getCheckOutDate())).toDays() + (entity.getAdultAmount() + (double) entity.getChildrenAmount() /2) * 20;
            }
        }
        return 0;
    }
    public Optional<ReservationEntity> findReservationByUser(UserEntity user){
        return reservationRepository.findReservationEntityByReservee(user);
    }
}
