package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.model.entity.UserEntity;
import bg.oakhotelmanager.model.entity.UserRoleEntity;
import bg.oakhotelmanager.model.enums.UserRoleEnum;
import bg.oakhotelmanager.model.user.OakUserDetails;
import bg.oakhotelmanager.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OakUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public OakUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(OakUserDetailsService::map)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("User with email " + email + " not found.")
                );
    }

    private static UserDetails map(UserEntity userEntity) {

        return new OakUserDetails(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRoleEntity::getRole).map(OakUserDetailsService::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
