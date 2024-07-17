package bg.oakhotelmanager.model.entity;

import bg.oakhotelmanager.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
