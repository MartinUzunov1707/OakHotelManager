package bg.oakhotelmanager.service;

import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.repository.UserRepository;
import bg.oakhotelmanager.service.impl.OakUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OakUserDetailsServiceTest {
    OakUserDetailsService toTest;
    final String EXISTING_EMAIL="test@mail";
    final String NON_EXISTENT_EMAIL="fake@mail";
    @Mock
    UserRepository mockUserRepository;
    @BeforeEach
    void setUp(){
        toTest = new OakUserDetailsService(mockUserRepository);
    }

    @Test
    void loadByUsernameLoadsUser(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setPassword("topsecret");
        user.setFirstName("Martin");
        user.setLastName("Uzunov");
        user.setRoles(new ArrayList<>());

        when(mockUserRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.of(user));

        UserDetails actual = toTest.loadUserByUsername(EXISTING_EMAIL);
        Assertions.assertTrue(user.getEmail().equals(actual.getUsername()));
    }
    @Test
    void loadByUsernameThrowsNotFound(){

        when(mockUserRepository.findByEmail(NON_EXISTENT_EMAIL)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class ,() -> {
            toTest.loadUserByUsername(NON_EXISTENT_EMAIL);
        });
    }
}
