package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.model.dto.RegisterDTO;
import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.model.user.OakUserDetails;
import bg.oakhotelmanager.repository.UserRepository;
import bg.oakhotelmanager.repository.UserRoleRepository;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean registerUser(RegisterDTO data){
        if (getUserByEmail(data.getEmail()).isPresent()){
            return false;
        }
        UserEntity user = modelMapper.map(data, UserEntity.class);
        UserRoleEntity role = userRoleRepository.getUserRoleEntityByRole(UserRoleEnum.USER);

        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRoles(List.of(role));

        userRepository.save(user);
        return true;
    }
    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<UserEntity> findUserById(Long id){ return userRepository.findById(id);}
}
