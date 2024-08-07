package bg.oakhotelmanager.service;

import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.repository.UserRoleRepository;
import bg.oakhotelmanager.service.impl.UserRoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    UserRoleService toTest;
    @Mock
    UserRoleRepository mockUserRoleRepository;

    @BeforeEach
    void setUp(){
        toTest = new UserRoleService(mockUserRoleRepository);
    }

    @Test
    void userRoleServiceReturnsAllRoles(){
        when(mockUserRoleRepository.findAll()).thenReturn(List.of(new UserRoleEntity()));

        Assertions.assertFalse(toTest.getAllRoles().isEmpty());
    }
    @Test
    void getUserEntityRoleByUserRoleReturn(){
        UserRoleEntity expected = new UserRoleEntity();
        expected.setRole(UserRoleEnum.USER);
        expected.setId(1L);
        when(mockUserRoleRepository.getUserRoleEntityByRole(UserRoleEnum.USER)).thenReturn(expected);

        UserRoleEntity actual = toTest.getUserRoleEntityByRole(UserRoleEnum.USER);
        Assertions.assertTrue(actual.getRole().compareTo(UserRoleEnum.USER) == 0);
        Assertions.assertTrue(actual.getId() == expected.getId());
    }

}
