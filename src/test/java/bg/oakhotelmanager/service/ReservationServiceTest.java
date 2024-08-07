package bg.oakhotelmanager.service;

import bg.oakhotelmanager.model.dto.ReservationDTO;
import bg.oakhotelmanager.model.entity.ReservationEntity;
import bg.oakhotelmanager.model.entity.RoomEntity;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.model.enums.RoomEnum;
import bg.oakhotelmanager.model.user.OakUserDetails;
import bg.oakhotelmanager.repository.ReservationRepository;
import bg.oakhotelmanager.repository.RoomRepository;
import bg.oakhotelmanager.repository.UserRepository;
import bg.oakhotelmanager.service.impl.ReservationService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    final LocalDate EXISTING_LOCAL_DATE = LocalDate.of(2024,8,8);
    final LocalDate NON_EXISTANT_LOCAL_DATE = LocalDate.of(2024,7,17);
    final String EXISTING_EMAIL="test@mail";
    ReservationService toTest;
    @Mock
    ReservationRepository mockReservationRepository;
    @Mock
    RoomRepository mockRoomRepository;
    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        toTest = new ReservationService(mockReservationRepository,mockRoomRepository,new ModelMapper(),mockUserRepository);
    }
    @Test
    void findAllByCreatedOnReturns(){
        ReservationEntity expected = new ReservationEntity();
        expected.setId(1L);
        when(mockReservationRepository.findAllByCreatedOn(EXISTING_LOCAL_DATE)).thenReturn(List.of(expected));

        List<ReservationEntity> actualList = toTest.findAllByCreatedOn(EXISTING_LOCAL_DATE);

        Assertions.assertTrue(actualList.get(0).getId() == expected.getId());
    }
    @Test
    void findAllByCreatedOnReturnsNull(){
        when(mockReservationRepository.findAllByCreatedOn(NON_EXISTANT_LOCAL_DATE)).thenReturn(new ArrayList<>());

        List<ReservationEntity> actualList = toTest.findAllByCreatedOn(NON_EXISTANT_LOCAL_DATE);

        Assertions.assertTrue(actualList.isEmpty());
    }
    @Test
    void findSuitableRoomReturnsRoomWithNoReservations(){
        RoomEntity expected = new RoomEntity();
        expected.setId(1L);
        expected.setReservations(new ArrayList<>());
        expected.setCapacity(4);
        expected.setRoomName("101");
        expected.setRoomType(RoomEnum.DOUBLE);

        ReservationDTO data = new ReservationDTO();
        data.setCheckInDate(LocalDate.of(2024,8,7));
        data.setCheckOutDate(LocalDate.of(2024,8,9));

        RoomEntity actual = toTest.findSuitableRoom(data,List.of(expected));

        Assertions.assertTrue(expected.getId() == actual.getId());
    }
    @Test
    void findSuitableRoomReturnsRoomWithReservations(){
        ReservationEntity reservation = new ReservationEntity();
        reservation.setCheckInDate(LocalDate.of(2024,7,1));
        reservation.setCheckOutDate(LocalDate.of(2024,7,2));

        RoomEntity expected = new RoomEntity();
        expected.setId(1L);
        expected.setReservations(List.of(reservation));
        expected.setCapacity(4);
        expected.setRoomName("101");
        expected.setRoomType(RoomEnum.DOUBLE);

        ReservationDTO data = new ReservationDTO();
        data.setCheckInDate(LocalDate.of(2024,8,7));
        data.setCheckOutDate(LocalDate.of(2024,8,9));

        RoomEntity actual = toTest.findSuitableRoom(data,List.of(expected));

        Assertions.assertTrue(expected.getId() == actual.getId());
    }
    @Test
    void findSuitableRoomReturnsNullWithReservations(){
        ReservationEntity reservation = new ReservationEntity();
        reservation.setCheckInDate(LocalDate.of(2024,8,9));
        reservation.setCheckOutDate(LocalDate.of(2024,8,10));

        RoomEntity expected = new RoomEntity();
        expected.setId(1L);
        expected.setReservations(List.of(reservation));
        expected.setCapacity(4);
        expected.setRoomName("101");
        expected.setRoomType(RoomEnum.DOUBLE);

        ReservationDTO data = new ReservationDTO();
        data.setCheckInDate(LocalDate.of(2024,8,7));
        data.setCheckOutDate(LocalDate.of(2024,8,10));

        RoomEntity actual = toTest.findSuitableRoom(data,List.of(expected));

        Assertions.assertNull(actual);
    }
    @Test
    void saveReservationSaves(){
        ReservationDTO data = new ReservationDTO();
        data.setRoomType("DOUBLE");
        data.setCheckInDate(LocalDate.of(2024,8,7));
        data.setCheckOutDate(LocalDate.of(2024,8,10));

        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setReservations(new ArrayList<>());
        room.setRoomName("101");
        room.setRoomType(RoomEnum.DOUBLE);
        room.setCapacity(4);

        UserDetails details = new OakUserDetails("test@mail","topsecret",new ArrayList<>(),"Martin","Uzunov");

        when(mockUserRepository.findByEmail(details.getUsername())).thenReturn(Optional.of(new UserEntity()));

        when(mockRoomRepository.findAllByRoomType(RoomEnum.DOUBLE)).thenReturn(List.of(room));

        ReservationEntity expected = new ReservationEntity();
        expected.setCheckInDate(LocalDate.of(2024,8,7));
        expected.setCheckOutDate(LocalDate.of(2024,8,10));

        ReservationEntity actual = toTest.saveReservation(data,details);
        Assertions.assertTrue(expected.getCheckInDate().isEqual(actual.getCheckInDate()));
        Assertions.assertTrue(expected.getCheckOutDate().isEqual(actual.getCheckOutDate()));
    }
    @Test
    void findReservationByUserReturns(){
        UserEntity user = new UserEntity();
        user.setEmail(EXISTING_EMAIL);

        ReservationEntity entity = new ReservationEntity();
        entity.setPhoneNumber("0888040372");

        when(mockReservationRepository.findReservationEntityByReservee(user)).thenReturn(Optional.of(entity));
        Optional<ReservationEntity> actual = toTest.findReservationByUser(user);
        Assertions.assertTrue(actual.get().getPhoneNumber().equals(entity.getPhoneNumber()));
    }
    @Test
    void findAllByReserveeReturns(){
        UserEntity user = new UserEntity();
        user.setEmail(EXISTING_EMAIL);

        ReservationEntity entity = new ReservationEntity();
        entity.setPhoneNumber("0888040372");

        when(mockReservationRepository.findAllByReservee(user)).thenReturn(List.of(entity));
        List<ReservationEntity> actual = toTest.findAllByReservee(user);
        Assertions.assertTrue(actual.get(0).getPhoneNumber().equals(entity.getPhoneNumber()));

    }
}
