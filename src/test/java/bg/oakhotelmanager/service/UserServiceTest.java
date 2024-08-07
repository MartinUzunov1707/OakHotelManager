package bg.oakhotelmanager.service;

import bg.oakhotelmanager.model.dto.RegisterDTO;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.repository.UserRepository;
import bg.oakhotelmanager.repository.UserRoleRepository;
import bg.oakhotelmanager.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService toTest;
    final String EXISTING_EMAIL="test@mail";
    final String NON_EXISTENT_EMAIL="fake@mail";
    @Mock
    private UserRepository userMockRepository;
    @Mock
    private UserRoleRepository userRoleMockRepository;

    @BeforeEach
    public void setUp(){
        toTest = new UserService(userMockRepository, new ModelMapper(),userRoleMockRepository, new BCryptPasswordEncoder());

    }
    @Test
    void registerUserRegistersUser(){
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.USER);
        role.setId(1L);

        RegisterDTO data = new RegisterDTO();
        data.setEmail(EXISTING_EMAIL);
        data.setFirstName("Martin");
        data.setLastName("Uzunov");
        data.setPassword("topsecret");

        when(userMockRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.empty());

        when(userRoleMockRepository.getUserRoleEntityByRole(UserRoleEnum.USER)).thenReturn(role);

        Assertions.assertTrue(toTest.registerUser(data));
    }
    @Test
    void registerUserNotRegistersExistingUser(){
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.USER);
        role.setId(1L);

        RegisterDTO data = new RegisterDTO();
        data.setEmail(EXISTING_EMAIL);
        data.setFirstName("Martin");
        data.setLastName("Uzunov");
        data.setPassword("topsecret");

        UserEntity expected = new UserEntity();
        expected.setId(1L);
        expected.setFirstName("Martin");
        expected.setLastName("Uzunov");
        expected.setEmail(EXISTING_EMAIL);
        expected.setPassword("topsecret");
        expected.setRoles(List.of(new UserRoleEntity()));

        when(userMockRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.of(expected));

        Assertions.assertFalse(toTest.registerUser(data));

    }
    @Test
    void getUserByEmailReturnsUser(){
        //Arrange
        UserEntity expected = new UserEntity();
        expected.setId(1L);
        expected.setFirstName("Martin");
        expected.setLastName("Uzunov");
        expected.setEmail(EXISTING_EMAIL);
        expected.setPassword("topsecret");
        expected.setRoles(List.of(new UserRoleEntity()));

        when(userMockRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.of(expected));

        //Act
        Optional<UserEntity> actual = toTest.getUserByEmail(EXISTING_EMAIL);

        //Assert
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertTrue(expected.getId() == actual.get().getId());
        Assertions.assertTrue(expected.getFirstName() == actual.get().getFirstName());
        Assertions.assertTrue(expected.getLastName() == actual.get().getLastName());
        Assertions.assertTrue(expected.getEmail() == actual.get().getEmail());
        Assertions.assertTrue(expected.getPassword() == actual.get().getPassword());

    }
    @Test
    void getUserByEmailReturnsNull(){
        //Arrange
        when(userMockRepository.findByEmail(NON_EXISTENT_EMAIL)).thenReturn(Optional.empty());
        //Act
        Optional<UserEntity> actual = toTest.getUserByEmail(NON_EXISTENT_EMAIL);
        //Assert
        Assertions.assertTrue(actual.isEmpty());
    }
}
