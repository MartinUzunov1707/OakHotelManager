package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
    public List<UserRoleEntity> getAllRoles(){
        return userRoleRepository.findAll();
    }

    public void addRole(UserRoleEntity role) {
        userRoleRepository.save(role);
    }
    public UserRoleEntity getUserRoleEntityByRole(UserRoleEnum role){
       return userRoleRepository.getUserRoleEntityByRole(role);
    }
}
