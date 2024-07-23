package bg.oakhotelmanager.init;

import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.service.impl.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitialiser implements CommandLineRunner {
    private final UserRoleService userRoleService;

    public RoleInitialiser(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRoleService.getAllRoles().size()==0){
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIM);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);

            userRoleService.addRole(userRole);
            userRoleService.addRole(adminRole);
        }
    }
}
